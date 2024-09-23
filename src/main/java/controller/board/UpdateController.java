package controller.board;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

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

@WebServlet("/board/Update.chs")
@MultipartConfig(maxFileSize = 1024*500, maxRequestSize = 1024*500*5)
public class UpdateController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inputid = req.getParameter("id");
		req.setAttribute("nowPage", req.getParameter("nowPage"));
		BoardDao dao = new BoardDao(getServletContext());
		BoardDto dto = dao.findById(inputid);
		dao.close();
		req.setAttribute("dto", dto);
		
		req.getRequestDispatcher("/WEB-INF/board/Update.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!Vaildate.boardValidate(req, resp)){
			req.getRequestDispatcher("/WEB-INF/board/Write.jsp").forward(req, resp);
			return;
		}
		else {
			String saveDir = req.getServletContext().getRealPath("/boardfile");
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String originalFileName = req.getParameter("originalFileName");
			
			BoardDao dao = new BoardDao(getServletContext());
			BoardDto dto = BoardDto.builder()
								.id(Long.parseLong(id))
								.title(title)
								.content(content)
								.build();
			Collection<Part> part = req.getParts();
			StringBuffer buffer = FileUtils.upload(part,saveDir);
			boolean fileChange = buffer==null?false:true;
			if(!fileChange) buffer= new StringBuffer(originalFileName);
			dto.setFiles(buffer.toString());			
			int affected = dao.update(dto);
			if(affected==0 && fileChange) {
				FileUtils.deletes(buffer, saveDir, ",");
			}
			else if(affected==1 && fileChange){
				FileUtils.deletes(new StringBuffer(originalFileName), saveDir, ",");
			}
			dao.close();
			
			resp.sendRedirect(String.format("%s/board/View.chs?id=%s", req.getContextPath(),id));
		}
	}
}
