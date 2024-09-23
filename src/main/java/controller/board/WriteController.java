package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.oracle.wls.shaded.org.apache.xalan.lib.Redirect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.FileUtils;
import model.JWT;
import model.Vaildate;
import model.board.BoardDao;
import model.board.BoardDto;
import model.member.MemberDao;
import model.member.MemberDto;
import taglib.MyTagLib;

@WebServlet("/board/Write.chs")
@MultipartConfig(maxFileSize = 1024*500, maxRequestSize = 1024*500*5)
public class WriteController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/board/Write.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!Vaildate.boardValidate(req, resp)){
			req.getRequestDispatcher("/WEB-INF/board/Write.jsp").forward(req, resp);
			return;
		}
		else {
			String saveDir = req.getServletContext().getRealPath("/boardfile");
			String title = req.getParameter("title");
			String content = req.getParameter("content");

			MemberDto mDto = MyTagLib.getUser(req);
			
			BoardDao dao = new BoardDao(getServletContext());
			BoardDto dto = BoardDto.builder()
								.username(mDto.getUsername())
								.title(title)
								.content(content)
								.build();
			Collection<Part> part = req.getParts();
			StringBuffer buffer = FileUtils.upload(part,saveDir);
			if(buffer!=null) {//파일 첨부시
				dto.setFiles(buffer.toString());
			}
			int affected = dao.insert(dto);
			dao.close();
			if(affected == 0) {
				resp.setContentType("text/html; charset=UTF-8");
		        PrintWriter out = resp.getWriter();
		        out.println("<script>");
		        out.println("alert('등록 실패! 다시 시도해주세요.');");
	            out.println("history.back();");
	            out.println("</script>");
		        out.close();
			}
			
			req.getRequestDispatcher("/board/View.chs").forward(req, resp);
		}
		
	}
}
