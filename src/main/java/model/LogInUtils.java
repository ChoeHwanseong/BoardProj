package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.member.MemberDao;
import model.member.MemberDto;

public class LogInUtils {
	
	/**
	 * 
	 */
	public static MemberDto getDtobytoken(HttpServletRequest req) {
		String token = JWT.getToken(req, req.getServletContext().getInitParameter("TOKEN-NAME"));
		Map<String, Object> claims = JWT.getPayloads(token);
		req.setAttribute("token", claims);
		MemberDao dao = new MemberDao(req.getServletContext());
		MemberDto dto = dao.findMyUser(claims.get("sub").toString());
		dao.close();
		return dto;
	}
	
}
