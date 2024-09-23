package controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.JWT;
import model.member.MemberDao;
@WebServlet("/Homepage/Login.chs")
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그아웃 처리
		if(req.getParameter("log")!=null && req.getParameter("log").equals("out")) {
			Cookie cookie = new Cookie(req.getServletContext().getInitParameter("TOKEN-NAME"), null);
			cookie.setPath(req.getContextPath());
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			resp.sendRedirect(req.getContextPath()+"/Homepage/Home.chs");
			return;
		}
		else req.getRequestDispatcher("/WEB-INF/login/Login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		if(username=="") {
			req.setAttribute("errorMsg", "아이디를 입력해주세요.");
			req.getRequestDispatcher("/WEB-INF/login/Login.jsp").forward(req, resp);
		}
		if(password=="") {
			req.setAttribute("errorMsg", "비밀번호를 입력해주세요.");
			req.getRequestDispatcher("/WEB-INF/login/Login.jsp").forward(req, resp);
		}
		MemberDao dao = new MemberDao(getServletContext());
		boolean isMember = dao.isMember(username, password);
		dao.close();
		if(isMember) {
			Map<String, Object> payloads = new HashMap();
			long expTime = 1000*60*60*3;
			String token = JWT.createToken(username, payloads, expTime);
			
			Cookie cookie = new Cookie(getServletContext().getInitParameter("TOKEN-NAME"), token);
			cookie.setPath(req.getContextPath());
			resp.addCookie(cookie);
						
			resp.sendRedirect(req.getContextPath()+"/Homepage/MyPage.chs");
			return;
		}
		else {
			req.setAttribute("errorMsg", "아이디/ 비밀번호 불일치");
			req.getRequestDispatcher("/WEB-INF/login/Login.jsp").forward(req, resp);
			return;
			
		}
	}
}
