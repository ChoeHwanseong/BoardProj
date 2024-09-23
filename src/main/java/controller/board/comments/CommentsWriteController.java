package controller.board.comments;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LogInUtils;
import model.board.comments.CommentsDao;
import model.board.comments.CommentsDto;
import taglib.MyTagLib;

@WebServlet("/comments/write")
public class CommentsWriteController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			ObjectMapper mapper = new ObjectMapper();
			CommentsDto requestDto = mapper.readValue(req.getInputStream(), CommentsDto.class);
			String content = requestDto.getContent();
            long boardId = requestDto.getBoard_id();

            CommentsDto dto = CommentsDto.builder()
                    .board_id(boardId)
                    .content(content)
                    .username(MyTagLib.getUser(req).getUsername())
                    .build();
			CommentsDao dao = new CommentsDao(getServletContext());
			int affected = dao.insert(dto);
			if(affected==1) {
				dto = dao.findRecentCommentsByUsername(LogInUtils.getDtobytoken(req).getUsername());
			}
			else {
				dto.setContent("내용을 입력해주세요.(댓글이 입력되지 않았습니다.)");
			}
			dao.close();
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
            mapper.writeValue(resp.getWriter(), dto);

	}
}
