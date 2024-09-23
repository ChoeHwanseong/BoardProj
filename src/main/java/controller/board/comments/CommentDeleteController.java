package controller.board.comments;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.comments.CommentsDao;
import model.board.comments.CommentsDto;

@WebServlet("/comments/Delete.chs")
public class CommentDeleteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, java.io.IOException {
            CommentsDao dao = new CommentsDao(getServletContext());
            CommentsDto dto = dao.findByCommentsId(req.getParameter("commentId"));
            dao.delete(dto);
            dao.close();
            
            resp.sendRedirect(String.format("%s/board/View.chs?id=%s", req.getContextPath(),req.getParameter("bId")));
    }
}
