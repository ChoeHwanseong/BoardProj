package model.board.likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import model.board.BoardDto;

public class LikeyDao {
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	
	public LikeyDao(ServletContext context) {
		try {
			Class.forName(context.getInitParameter("ORACLE-DRIVER"));
			DataSource src=(DataSource)context.getAttribute("DATA-SOURCE");
			conn= src.getConnection();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void close() {
		try {
			if(conn!=null) conn.close();
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	//좋아요 조회
	public boolean isLike(LikeyDto dto) {
		boolean isLike = false;
		try {
			psmt = conn.prepareStatement("SELECT * FROM likey WHERE username=? AND board_id =?");
			psmt.setString(1, dto.getUsername());
			psmt.setLong(2, dto.getBoard_id());
			rs = psmt.executeQuery();
			isLike = rs.next();
			
		} catch (SQLException e) {e.printStackTrace();}
		return isLike;
	}
	
	//좋아요 누르기
	public int insert(LikeyDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("UPDATE board SET likey= likey+1 WHERE id=?");
			psmt.setLong(1, dto.getBoard_id());
			psmt.executeUpdate();
			
			psmt = conn.prepareStatement("INSERT INTO likey VALUES(?,?)");
			psmt.setString(1, dto.getUsername());
			psmt.setLong(2, dto.getBoard_id());
			
			
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}
	
	//좋아요 삭제
	public int delete(LikeyDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("UPDATE board SET likey= likey-1 WHERE id=?");
			psmt.setLong(1, dto.getBoard_id());
			psmt.executeUpdate();
			
			psmt = conn.prepareStatement("DELETE FROM likey WHERE username=? AND board_id=?");
			psmt.setString(1, dto.getUsername());
			psmt.setLong(2, dto.getBoard_id());		
			affected = psmt.executeUpdate();
			
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	public int getLikeyCount(LikeyDto dto) {
		int totalRecordCount=0;
		String sql="SELECT count(*) from likey WHERE board_id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setLong(1, dto.getBoard_id());
			rs = psmt.executeQuery();
			rs.next();
			totalRecordCount = rs.getInt(1);
		}
		catch (SQLException e) {e.printStackTrace();}
		return totalRecordCount;
	}


	
}
