package controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LogInUtils;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/Homepage/Resign.chs")
public class ResignController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDto dto = LogInUtils.getDtobytoken(req);
		MemberDao dao = new MemberDao(getServletContext());
		int affected = dao.delete(dto);
		dao.close();
		
		resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<script>");
        if (affected == 1) {
            out.println("alert('탈퇴 완료! 다음에 다시 만나면 좋겠어요.');");
            out.println("location.href='" + req.getContextPath() + "/Homepage/Login.chs?log=out';");
        } else {
            out.println("alert('탈퇴 실패! 다시 시도해주세요.');");
            out.println("history.back();");
        }
        out.println("</script>");
        out.close();
	}
}
