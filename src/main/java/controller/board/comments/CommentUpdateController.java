package controller.board.comments;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.comments.CommentsDao;
import model.board.comments.CommentsDto;
import taglib.MyTagLib;

@WebServlet("/comments/update")
public class CommentUpdateController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파라미터 받기
		ObjectMapper mapper = new ObjectMapper();
		CommentsDto requestDto = mapper.readValue(req.getInputStream(), CommentsDto.class);
		String content = requestDto.getContent();
        long boardId = requestDto.getBoard_id();

        CommentsDto dto = CommentsDto.builder()
        		.id(requestDto.getId())
                .board_id(boardId)
                .content(content)
                .build();
		CommentsDao dao = new CommentsDao(getServletContext());
		dao.update(dto);
		dao.close();
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getWriter(), dto);
	}
}
