package model.board.comments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import service.DaoService;

public class CommentsDao implements DaoService<CommentsDto> {
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	
	public CommentsDao(ServletContext context) {
		try {
			Class.forName(context.getInitParameter("ORACLE-DRIVER"));
			DataSource src=(DataSource)context.getAttribute("DATA-SOURCE");
			conn= src.getConnection();
		} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void close() {
		try {
			if(conn!=null) conn.close();
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			
		} catch (SQLException e) {e.printStackTrace();}
		
	}

	@Override
	public int insert(CommentsDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("INSERT Into comments values(seq_comments.nextval,?,sysdate,?,?)");
			psmt.setString(1, dto.getContent());
			psmt.setLong(2, dto.getBoard_id());
			psmt.setString(3, dto.getUsername());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int update(CommentsDto dto) {
		int affected = 0;
		try {
			psmt = conn.prepareStatement("UPDATE comments set content=? WHERE id=?");
			psmt.setString(1, dto.getContent());
			psmt.setLong(2, dto.getId());
			affected = psmt.executeUpdate();
		} catch(SQLException e){e.printStackTrace();}
		return affected;
	}

	@Override
	public int delete(CommentsDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("DELETE FROM comments WHERE id=?");
			psmt.setLong(1, dto.getId());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int getTotalRecordCount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<CommentsDto> findByBoardId (String board_id) {
		List<CommentsDto> items = new Vector<>();
		//페이징 적용 쿼리
		String sql="SELECT * FROM comments WHERE board_id = ? ORDER BY postdate DESC";
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1,board_id);
			rs= psmt.executeQuery();
			while(rs.next()) {
				CommentsDto item = CommentsDto.builder()
						.id(rs.getLong(1))
						.content(rs.getString(2))
						.postDate(rs.getDate(3))
						.board_id(rs.getLong(4))
						.username(rs.getString(5))
						.build();
				items.add(item);				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public CommentsDto findByCommentsId (String id) {
		CommentsDto dto = new CommentsDto();
		//페이징 적용 쿼리
		String sql="SELECT * FROM comments WHERE id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1,id);
			rs= psmt.executeQuery();
			while(rs.next()) {
				dto = CommentsDto.builder()
						.id(rs.getLong(1))
						.content(rs.getString(2))
						.postDate(rs.getDate(3))
						.board_id(rs.getLong(4))
						.username(rs.getString(5))
						.build();				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public CommentsDto findRecentCommentsByUsername(String username) {
		CommentsDto dto = new CommentsDto();
		String sql = "SELECT * FROM comments WHERE id = (SELECT MAX(id) FROM comments WHERE username=?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			rs= psmt.executeQuery();
			while(rs.next()) {
				dto = CommentsDto.builder()
						.id(rs.getLong(1))
						.content(rs.getString(2))
						.postDate(rs.getDate(3))
						.board_id(rs.getLong(4))
						.username(rs.getString(5))
						.build();			
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
}
