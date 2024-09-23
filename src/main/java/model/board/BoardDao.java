package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import model.PagingUtil;
import service.DaoService;

public class BoardDao implements DaoService<BoardDto>{
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	
	public BoardDao(ServletContext context) {
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
	public int insert(BoardDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("INSERT INTO board VALUES(seq_board.nextval,?,?,?,0,0,?,DEFAULT)");
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getFiles());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int update(BoardDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("UPDATE board set title=?, content=?, files=? WHERE id=?");
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getFiles());
			psmt.setLong(4, dto.getId());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int delete(BoardDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("DELETE FROM comments WHERE board_id=?");
			psmt.setLong(1, dto.getId());
			psmt.executeUpdate();
			
			psmt = conn.prepareStatement("DELETE FROM likey WHERE board_id=?");
			psmt.setLong(1, dto.getId());
			psmt.executeUpdate();
			
			psmt = conn.prepareStatement("DELETE FROM board WHERE id=?");
			psmt.setLong(1, dto.getId());
			affected = psmt.executeUpdate();
			
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	public List<BoardDto> findAll(Map<String, String> map) {
		List<BoardDto> items = new Vector();
		//페이징 적용 쿼리
		String sql="SELECT * FROM (SELECT b.*,(SELECT COUNT(*) FROM comments c WHERE c.board_id = b.id) AS comment_count, RANK() OVER (ORDER BY id DESC) AS idRank FROM board b";
		//검색시 아래 쿼리 추가
		if(map.get("searchColumn") != null) {
			sql+=" WHERE "+map.get("searchColumn")+" LIKE '%"+map.get("searchWord")+"%'";
		}
		sql +=") WHERE idRank BETWEEN ? AND ? ";
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1, map.get(PagingUtil.START));
			psmt.setString(2, map.get(PagingUtil.END));
			rs= psmt.executeQuery();
			while(rs.next()) {
				BoardDto item = BoardDto.builder()
						.id(rs.getLong(1))
						.username(rs.getString(2))
						.title(rs.getString(3))
						.content(rs.getString(4))
						.hitcount(rs.getInt(5))
						.likey(rs.getInt(6))
						.files(rs.getString(7))
						.postDate(rs.getDate(8))
						.commentscount(rs.getLong(9))
						.build();
				items.add(item);				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public BoardDto findById(String id) {
		BoardDto dto = new BoardDto();
		String sql = "SELECT * FROM board WHERE id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs= psmt.executeQuery();
			while(rs.next()) {
				dto = BoardDto.builder()
						.id(rs.getLong(1))
						.username(rs.getString(2))
						.title(rs.getString(3))
						.content(rs.getString(4))
						.hitcount(rs.getInt(5))
						.likey(rs.getInt(6))
						.files(rs.getString(7))
						.postDate(rs.getDate(8))
						.build();			
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	//총 레코드수 얻기용
	@Override
	public int getTotalRecordCount(Map<String, String> map) {
		int totalRecordCount=0;
		String sql="SELECT COUNT(*) FROM board b";
		if(map != null && map.get("searchColumn") != null) {
			sql+=" WHERE "+map.get("searchColumn")+" LIKE '%"+map.get("searchWord")+"%'";
		}
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			rs.next();
			totalRecordCount = rs.getInt(1);
		}
		catch (SQLException e) {e.printStackTrace();}
		return totalRecordCount;
	}

	public BoardDto findRecentRecord(String username) {
		BoardDto dto = new BoardDto();
		String sql = "SELECT * FROM board WHERE id = (SELECT MAX(id) FROM board WHERE username=?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			rs= psmt.executeQuery();
			while(rs.next()) {
				dto = BoardDto.builder()
						.id(rs.getLong(1))
						.username(rs.getString(2))
						.title(rs.getString(3))
						.content(rs.getString(4))
						.hitcount(rs.getInt(5))
						.likey(rs.getInt(6))
						.files(rs.getString(7))
						.postDate(rs.getDate(8))
						.build();			
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public Map<String, BoardDto> prevNext(String currentId){
		Map<String, BoardDto> map = new HashMap<>();
		
		try {
			//이전글 얻기
			psmt = conn.prepareStatement("SELECT id,title,files,(SELECT COUNT(*) FROM comments c WHERE c.board_id = b.id) AS comment_count FROM board  b WHERE id=(SELECT MAX(id) FROM board WHERE id < ?)");
			psmt.setString(1, currentId);
			rs = psmt.executeQuery();
			if(rs.next()) {
				map.put("PREV", new BoardDto(rs.getLong(1), null,rs.getString(2), null, 0,0 , rs.getString(3),null,rs.getLong(4)));
			}
			//다음글 얻기
			psmt = conn.prepareStatement("SELECT id,title,files,(SELECT COUNT(*) FROM comments c WHERE c.board_id = b.id) AS comment_count FROM board  b WHERE id=(SELECT MIN(id) FROM board WHERE id > ?)");
			psmt.setString(1, currentId);
			rs = psmt.executeQuery();
			if(rs.next()) {
				map.put("NEXT", new BoardDto(rs.getLong(1), null,rs.getString(2), null, 0,0 , rs.getString(3),null,rs.getLong(4)));
			}
			
		} 
		catch (SQLException e) {e.printStackTrace();}
		return map;
	}

	public void hitcountUp(BoardDto dto) {
		try {
			psmt = conn.prepareStatement("UPDATE board SET hitcount= hitcount+1 WHERE id=?");
			psmt.setLong(1, dto.getId());
			psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		
	}

	public int getRankById(String id) {
		int nowPage=0;
		String sql="SELECT rank FROM (SELECT b.*, RANK() OVER (ORDER BY id DESC)AS rank FROM board b) WHERE id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			rs.next();
			nowPage = rs.getInt(1);
		}
		catch (SQLException e) {e.printStackTrace();}
		return nowPage;
	}
}
