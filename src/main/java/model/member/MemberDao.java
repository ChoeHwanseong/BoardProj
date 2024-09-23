package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import service.DaoService;

public class MemberDao implements DaoService<MemberDto> {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	
	public MemberDao(ServletContext context) {
		try {
			Class.forName(context.getInitParameter("ORACLE-DRIVER"));
			DataSource src=(DataSource)context.getAttribute("DATA-SOURCE");
			conn= src.getConnection();
		} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {e.printStackTrace();}
		
	}

	@Override
	public int insert(MemberDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("INSERT INTO members values(?,?,?,default,?,?,?,?,?,?,null)");
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getPassword());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getAddress());
			psmt.setString(5, dto.getTelNumber());
			psmt.setString(6, dto.getGender());
			psmt.setString(7, dto.getInterest());
			psmt.setString(8, dto.getEdu());
			psmt.setString(9, dto.getIntroduce());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int update(MemberDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("UPDATE members SET password=?, name=?, address=?, telnumber=?, gender=?, interest=?, edu=?, intro=?, profile=? WHERE username=?");
			psmt.setString(10, dto.getUsername());
			psmt.setString(1, dto.getPassword());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getAddress());
			psmt.setString(4, dto.getTelNumber());
			psmt.setString(5, dto.getGender());
			psmt.setString(6, dto.getInterest());
			psmt.setString(7, dto.getEdu());
			psmt.setString(8, dto.getIntroduce());
			psmt.setString(9, dto.getProfile());
			affected = psmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}

	@Override
	public int delete(MemberDto dto) {
		int affected =0;
		try {
			psmt = conn.prepareStatement("SELECT * FROM members WHERE username='탈퇴한 계정입니다.'");
			rs =psmt.executeQuery();
			if(!rs.next()) {
				psmt = conn.prepareStatement("INSERT INTO members values('탈퇴한 계정입니다.','1234','탈퇴한 계정입니다.',default,null,null,'탈퇴 계정','탈퇴한 계정입니다.','탈퇴한 계정입니다.','탈퇴한 계정입니다.',null)");
				psmt.executeUpdate();
			}
			//게시판 탈퇴회원으로 변경
			psmt = conn.prepareStatement("UPDATE board set username='탈퇴한 계정입니다.' WHERE username=?");
			psmt.setString(1, dto.getUsername());
			psmt.executeUpdate();
			
			//좋아요 삭제
			psmt = conn.prepareStatement("DELETE FROM likey WHERE username=?");
			psmt.setString(1, dto.getUsername());
			psmt.executeUpdate();
			
			//댓글 탈퇴회원으로 변경
			psmt = conn.prepareStatement("UPDATE comments set username='탈퇴한 계정입니다.' WHERE username=?");
			psmt.setString(1, dto.getUsername());
			psmt.executeUpdate();		
			
			//회원 탈퇴
			psmt = conn.prepareStatement("DELETE FROM members WHERE username=?");
			psmt.setString(1, dto.getUsername());
			affected = psmt.executeUpdate();
			
		} catch (SQLException e) {e.printStackTrace();}
		return affected;
	}
	
	public boolean isMember(String username, String password) {
		try {
			psmt = conn.prepareStatement("SELECT COUNT(*) FROM members WHERE username=? AND password=?");
			psmt.setString(1, username);
			psmt.setString(2, password);
			rs = psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//중복 아이디 방지
	public boolean isDuplicate(String username) {
		try {
			psmt = conn.prepareStatement("SELECT COUNT(*) FROM members WHERE username=?");
			psmt.setString(1, username);
			rs = psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==1) return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public MemberDto findMyUser(String username) {
		MemberDto user = null;
		try {	
			psmt = conn.prepareStatement("SELECT * FROM members WHERE username=?");
			psmt.setString(1, username);
			rs= psmt.executeQuery();
			if(rs.next()) {
				user = MemberDto.builder()
						.username(rs.getString(1))
						.password(rs.getString(2))
						.name(rs.getString(3))
						.regiDate(rs.getDate(4))
						.address(rs.getString(5))
						.telNumber(rs.getString(6))
						.gender(rs.getString(7))
						.interest(rs.getString(8))
						.edu(rs.getString(9))
						.introduce(rs.getString(10))
						.profile(rs.getString(11))
						.build();
				}
		}
		catch (SQLException e) {e.printStackTrace();}
		return user;
	}

	@Override
	public int getTotalRecordCount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return 0;
	}


}
