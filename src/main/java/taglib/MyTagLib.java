package taglib;

import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import model.JWT;
import model.member.MemberDao;
import model.member.MemberDto;

public class MyTagLib {
	public static MemberDto getUser(HttpServletRequest req) {
		if (req == null) {
            return null;
        }

        ServletContext context = req.getServletContext();
        String token = JWT.getToken(req, context.getInitParameter("TOKEN-NAME"));

        if (token == null || token.isEmpty()) {
            return null;
        }

        Map<String, Object> claims = JWT.getPayloads(token);
        req.setAttribute("token", claims);
        MemberDao dao = new MemberDao(context);
        MemberDto dto = dao.findMyUser(claims.get("sub").toString());
        dao.close();
        return dto;
    }
}