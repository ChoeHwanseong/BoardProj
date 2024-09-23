package controller.board;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.likey.LikeyDao;
import model.board.likey.LikeyDto;
import taglib.MyTagLib;

@WebServlet("/board/Likey.chs")
public class LikeyController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long postId = Long.parseLong(req.getParameter("id")); 
		String username = MyTagLib.getUser(req).getUsername();
		
		LikeyDto dto = new LikeyDto(username, postId);
		LikeyDao dao = new LikeyDao(getServletContext());
		boolean isLike = dao.isLike(dto);

		if(isLike) {
			dao.delete(dto);
			isLike = false;
		}
		else {
			dao.insert(dto);
			isLike = true;
		}
		int likeyCount = dao.getLikeyCount(dto);
		dao.close();
        // JSON 형식으로 응답을 생성하여 클라이언트에 전송합니다.
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"isLike\": " + isLike + ", \"likeyCount\": " + likeyCount + "}");
        out.flush();
    }
}
