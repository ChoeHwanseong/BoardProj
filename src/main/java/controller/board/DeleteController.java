package controller.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FileUtils;
import model.PagingUtil;
import model.board.BoardDao;
import model.board.BoardDto;

@WebServlet("/board/Delete.chs")
public class DeleteController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//페이지 사이즈 -삭제용 
		String selectPageSize = "5";
		if(req.getSession().getAttribute("sessionPageSize") !=null) {		
			selectPageSize=req.getSession().getAttribute("sessionPageSize").toString();
		}
		
		int pageSize = Integer.parseInt(selectPageSize);
		
		String inputid = req.getParameter("id");
		BoardDao dao = new BoardDao(getServletContext());
		BoardDto dto = dao.findById(inputid);
		int nowPage  = PagingUtil.getNowPage(dao.getRankById(inputid), req);
		int affected = dao.delete(dto);
		int totalRecordCount = dao.getTotalRecordCount(null);
		int totalPage = (int)(Math.ceil(((double)totalRecordCount/pageSize)));
		dao.close();
		if(totalPage<nowPage) nowPage=totalPage;
		if(affected==1 && dto.getFiles()!=null) {
			FileUtils.deletes(new StringBuffer(dto.getFiles()), getServletContext().getRealPath("/boardfile"), ",");
		}
		resp.sendRedirect(String.format("%s/board/List.chs?nowPage=%s", req.getContextPath(),nowPage));
	}
}
