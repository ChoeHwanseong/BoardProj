package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/error.chs")
public class ErrorController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("ErrorMsg","에러가 발생했습니다. 관리자에게 문의해주세요<br/> E-mail) newo8995@gmail.com");
		req.getRequestDispatcher("/publicpage/Error.jsp").forward(req, resp);
	}
}
