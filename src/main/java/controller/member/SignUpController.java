package controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Vaildate;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/Homepage/SignUp.chs")
public class SignUpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login/SignUpPage.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!Vaildate.signupValidate(req, resp)){
			if(req.getParameterValues("inter")!=null)
				req.setAttribute("inter",Arrays.stream(req.getParameterValues("inter")).collect(Collectors.joining(", ")));
			req.getRequestDispatcher("/WEB-INF/login/SignUpPage.jsp").forward(req, resp);
			return;
		}

		else {
			
			Map<String,	String[]> map = req.getParameterMap();
			MemberDao dao = new MemberDao(getServletContext());
			if(dao.isDuplicate(map.get("username")[0])) {
				req.setAttribute("usernameError", "중복된 아이디입니다. 다른 아이디를 입력해주세요.");
				dao.close();
				req.getRequestDispatcher("/WEB-INF/login/SignUpPage.jsp").forward(req, resp);
				return;
			}
			
			String address = "";
			if(map.get("addrnum")[0]!="" || map.get("roadaddr")[0] !="" || map.get("detailaddr")[0] !="") {
				address= map.get("addrnum")[0]+"_"+map.get("roadaddr")[0]+"_"+map.get("detailaddr")[0];
			}
				
			MemberDto dto = MemberDto.builder()
									.username(map.get("username")[0])
									.password(map.get("password")[0])
									.name(map.get("name")[0])
									.address(address)
									.telNumber(map.get("tel")[0])
									.gender(map.get("gender")[0])
									.interest(Arrays.stream(map.get("inter")).collect(Collectors.joining(", ")))
									.edu(map.get("grade")[0])
									.introduce(map.get("self")[0]).build();
	
			
			
			int affected = dao.insert(dto);
			dao.close();
			
			resp.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.println("<script>");
	        if (affected == 1) {
	            out.println("alert('가입 완료! 가입한 계정으로 로그인해주세요.');");
	            out.println("location.href='" + req.getContextPath() + "/Homepage/Login.chs';");
	        } else {
	            out.println("alert('가입 실패! 다시 가입해주세요.');");
	            out.println("history.back();");
	        }
	        out.println("</script>");
	        out.close();
		}
	}
}
