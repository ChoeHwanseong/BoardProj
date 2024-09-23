package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LogInUtils;
import model.PagingUtil;
import model.JWT;
import model.board.BoardDao;
import model.board.BoardDto;

@WebServlet("/board/List.chs")
public class ListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String searchColumn = req.getParameter("searchColumn");
		String searchWord = req.getParameter("searchWord");
		BoardDao dao = new BoardDao(getServletContext());
		Map<String, String> map = new HashMap();
		String linkUrl = req.getContextPath()+"/board/List.chs?";
		String searchQuery="";
		if(searchColumn != null && searchWord.length() != 0){
			map.put("searchColumn", searchColumn);
			map.put("searchWord", searchWord);
			searchQuery=String.format("searchColumn=%s&searchWord=%s&", searchColumn, searchWord);
			linkUrl+=searchQuery;
		}
		//페이징
		String selectPageSize = req.getParameter("selectPageSize");
		if(selectPageSize==null) {
			selectPageSize = "5";
			if(req.getSession().getAttribute("sessionPageSize") !=null) {		
				selectPageSize=req.getSession().getAttribute("sessionPageSize").toString();
			}
		}
		req.getSession().setAttribute("sessionPageSize", selectPageSize);
		
		map.put(PagingUtil.PAGE_SIZE, selectPageSize);
		map.put(PagingUtil.BLOCK_PAGE, req.getServletContext().getInitParameter("BLOCK-PAGE"));
		PagingUtil.setMapForPaging(map, dao, req);
			

		int totalRecordCount = Integer.parseInt(map.get(PagingUtil.TOTAL_RECORD_COUNT));
		int pageSize = Integer.parseInt(map.get(PagingUtil.PAGE_SIZE));
		int blockPage = Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE));
		int nowPage = Integer.parseInt(map.get(PagingUtil.NOWPAGE));
		
		String paging= PagingUtil.pagingBootStrapStyle(totalRecordCount, pageSize, blockPage, nowPage, linkUrl);
		
		List<BoardDto> records = dao.findAll(map);
		dao.close();
		req.setAttribute("paging", paging);
		req.setAttribute("records", records);
		req.getRequestDispatcher("/WEB-INF/board/List.jsp").forward(req, resp);
		
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
