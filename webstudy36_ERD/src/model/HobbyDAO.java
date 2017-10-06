package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class HobbyDAO {
	private static HobbyDAO instance=new HobbyDAO();
	private DataSource dataSource;
	private HobbyDAO(){
		dataSource=DataSourceManager.getInstance().getDataSource();
	}
	public static HobbyDAO getInstance() throws ClassNotFoundException{
		return instance;
	}
	public void closeAll(PreparedStatement pstmt,Connection con) throws SQLException{
		closeAll(null, pstmt,con);
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,
			Connection con) throws SQLException{
		if(rs!=null)
			rs.close();
		if(pstmt!=null)
			pstmt.close();
		if(con!=null)
			con.close();
	}
	public ArrayList<HobbyVO> selectHobbyList() throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<HobbyVO> list=new ArrayList<HobbyVO>();
		try{
			con=dataSource.getConnection();
			String sql="select hid,name from hobby_type";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(
					new HobbyVO(rs.getString(1),rs.getString(2)));
				}
		}finally{
			closeAll(rs, pstmt, con);
		}
		return list;		
	}
/**
 * 회원 정보등록 업무단위에 대해 트랜잭션 처리
 * 회원의 일반 정보 insert (h_member table)
 * 회원의 취미 정보 insert (member_hobby table)
 * 위의 insert 작업에서 하나라도 문제발생시에는 등록작업이 
 * 모두 취소 --> rollback
 * 문제없이 모든 작업이 정상적으로 실행될 때 실제 db에 반영
 * -> commit
 * 
 * JDBC는 AutoCommit이 기본
 * try{
 * connection.setAutoCommit(false); //커밋을 수동모드로 전환
 * ...
 * ...
 * try 구문의 가장 마지막 지점에서 (작업단위 내 모든 작업이 정상적으로 
 * 수행되었을 때)
 * connection.commit(); //실제 db에 반영
 * }catch(){
 * connection.rollback(); //문제 발생시 작업단위내 모든 작업취소 
 * }
 * 
 */
	public void register(MemberVO vo) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=dataSource.getConnection();
			con.setAutoCommit(false);//오토커밋 해제, 수동모드 전환
			String sql="insert into h_member(id,password,name) values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.executeUpdate();
			pstmt.close();
			if(vo.getHobby().size()!=0) {
			for(int i=0;i<vo.getHobby().size();i++) {
			sql="insert into member_hobby(id,hid) values(?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getHobby().get(i).getHid());
			pstmt.executeUpdate();
			}
			}
			con.commit(); // try 마지막 지점,문제 없이 모두 수행되었으므로 
						  // 실제 db에 반영한다 
			System.out.println("commit");
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();//문제발생시에는 작업 단위내 모든 작업이 취소 
			System.out.println("rollback");
			throw e;//호출한 컨트롤러로 현재 Exception 객체를 발생시켜 
			//전달한다 
		}finally{
			closeAll(pstmt, con);
		}
	}

	public MemberVO detailInfo(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		MemberVO vo=null;
		ArrayList<HobbyVO> list= new ArrayList<HobbyVO>();
		String mid=null;
		String mname=null;
		
		try{
			con=dataSource.getConnection();
			String sql="select m.id,m.name,h.hid,t.name from h_member m, member_hobby h, hobby_type t where m.id=h.id and h.hid=t.hid and m.id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(mid==null) {
				 mid=rs.getString(1);
				 mname=rs.getString(2);
				}
				list.add(new HobbyVO(rs.getString(3),rs.getString(4)));
			}
			vo= new MemberVO(mid,mname,list);
			
		}finally{
			closeAll(rs, pstmt, con);
		}
		return vo;		
	}
}
