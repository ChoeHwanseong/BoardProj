package controller.board;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LogInUtils;
import model.PagingUtil;
import model.board.BoardDao;
import model.board.BoardDto;
import model.board.comments.CommentsDao;
import model.board.comments.CommentsDto;
import model.board.likey.LikeyDao;
import model.board.likey.LikeyDto;
import model.member.MemberDto;
import taglib.MyTagLib;
@WebServlet("/board/View.chs")
public class ViewController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		BoardDao dao = new BoardDao(getServletContext());
		BoardDto dto = dao.findById(id);
		
		//조회수 증가용
		String referer = req.getHeader("referer");
		String prevPage = referer.substring(referer.lastIndexOf("/")+1);
		if(!prevPage.startsWith("Update") || !prevPage.startsWith("Delete")) {
			dao.hitcountUp(dto);
			dto.setHitcount(dto.getHitcount());
		}
		
		//좋아요 확인용
		LikeyDto lDto = LikeyDto.builder().board_id(Long.parseLong(id)).username(MyTagLib.getUser(req).getUsername()).build();
		LikeyDao lDao = new LikeyDao(getServletContext());
		boolean isLikey =  lDao.isLike(lDto);
		lDao.close();
		req.setAttribute("isLikey", isLikey);
		
		//현재 페이지 유지용

		int nowPage  = PagingUtil.getNowPage(dao.getRankById(id), req);
		req.setAttribute("nowPage", nowPage);	
		req.setAttribute("pageSize", req.getParameter("pageSize"));
		Map<String, BoardDto> map = dao.prevNext(id);
		req.setAttribute("prevnext", map);
		dao.close();
		dto.setContent(dto.getContent().replace("\r\n", "<br/>"));
		req.setAttribute("dto", dto);		
		MemberDto loginUser = LogInUtils.getDtobytoken(req);
		req.setAttribute("user", loginUser);
		//댓글
		CommentsDao cDao = new CommentsDao(getServletContext());

		List<CommentsDto> cDtos = cDao.findByBoardId(id);
		cDao.close();
		req.setAttribute("comments", cDtos);
		
		
		req.getRequestDispatcher("/WEB-INF/board/View.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("id")==null) {//작성한 글이라면
			BoardDao dao = new BoardDao(getServletContext());
			MemberDto loginUser = LogInUtils.getDtobytoken(req);
			BoardDto dto = dao.findRecentRecord(loginUser.getUsername());
			Map<String, BoardDto> map = dao.prevNext(String.valueOf(dto.getId()));
			req.setAttribute("prevnext", map);
			dao.close();
			req.setAttribute("user", loginUser);
			if(dto.getContent()!=null) {
			dto.setContent(dto.getContent().replace("\r\n", "<br/>"));
			}
			req.setAttribute("dto", dto);
			req.getRequestDispatcher("/WEB-INF/board/View.jsp").forward(req, resp);
			return;
		}
		else {//수정한 글이라면
			doGet(req, resp);
		}
	}
}
