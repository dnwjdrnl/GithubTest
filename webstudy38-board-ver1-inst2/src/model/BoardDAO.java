package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class BoardDAO {
	private static BoardDAO dao=new BoardDAO();	
	private DataSource dataSource;
	private BoardDAO(){
		dataSource=DataSourceManager.getInstance().getDataSource();
	}
	public static BoardDAO getInstance(){
		return dao;
	}
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	public void closeAll(PreparedStatement pstmt,Connection con) throws SQLException{
		if(pstmt!=null)
			pstmt.close();
		if(con!=null)
			con.close(); 
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,Connection con) throws SQLException{
		if(rs!=null)
			rs.close();
		closeAll(pstmt,con);
	}	
	/**
	 * 페이지 번호에 해당하는 게시물 목록 리스트를 반환하는 메서드 
	 * LIST SQL 
	 * SELECT b.no,b.title,b.hits,to_char(time_posted,'YYYY.MM.DD') as time_posted,m.id,m.name 
		FROM board_inst b , board_member m
		WHERE b.id=m.id
		order by no desc
	 * @param pageNo
	 * @return
	 * @throws SQLException
	 */
	
	
	/**
	 * getPostList에 update
	 * select P.no,P.title,P.content,P.time_posted,P.id
from(
select row_number() over(order by no desc) as rnum, no ,title,content,time_posted,id from board_inst
) P, board_member M
where rnum between 1 and 5 and P.id=M.id;
order by P.no desc;
	 */
	
	//매개변수에 PagingBean을 명시해야 함 
	public ArrayList<BoardVO> getPostingList(PagingBean pb) throws SQLException{
		ArrayList<BoardVO> list=new ArrayList<BoardVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int startRowNum=pb.getStartRowNumber();
		int endRowNum=pb.getEndRowNumber();
		try{
			con=getConnection(); 
			StringBuilder sql=new StringBuilder();
			sql.append("select P.no,P.title,P.time_posted,P.hits,M.id,M.name ");
			sql.append("from(select row_number() over(order by no desc) as rnum, no ,title,content,time_posted,id,hits from board_inst) P, board_member M ");
			sql.append("where rnum between ? and ? and P.id=M.id ");		
			sql.append("order by P.no desc ");
			pstmt=con.prepareStatement(sql.toString());		
			pstmt.setInt(1, startRowNum);
			pstmt.setInt(2,endRowNum);
			rs=pstmt.executeQuery();	
			//목록에서 게시물 content는 필요없으므로 null로 setting
			//select no,title,time_posted,hits,id,name
			while(rs.next()){		
				BoardVO bvo=new BoardVO();
				bvo.setNo(rs.getInt(1));
				bvo.setTitle(rs.getString(2));
				bvo.setTimePosted(rs.getString(3));
				bvo.setHits(rs.getInt(4));
				MemberVO mvo=new MemberVO();
				mvo.setId(rs.getString(5));
				mvo.setName(rs.getString(6));
				bvo.setMemberVO(mvo);
				list.add(bvo);			
			}			
		}finally{
			closeAll(rs,pstmt,con);
		}
		return list;
	}
    /**
     * Sequence 글번호로 게시물을 검색하는 메서드 
     * @param no
     * @return
     * @throws SQLException
     */
	public BoardVO getPostingByNo(int no) throws SQLException{
		BoardVO bvo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			StringBuilder sql=new StringBuilder();
			sql.append("select b.title,to_char(b.time_posted,'YYYY.MM.DD  HH24:MI:SS') as time_posted");
			sql.append(",b.content,b.hits,b.id,m.name");
			sql.append(" from board_inst b,board_member m");
			sql.append(" where b.id=m.id and b.no=?");		
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
		
			if(rs.next()){
				bvo=new BoardVO();
				bvo.setNo(no);
				bvo.setTitle(rs.getString("title"));
				bvo.setContent(rs.getString("content"));				
				bvo.setHits(rs.getInt("hits"));
				bvo.setTimePosted(rs.getString("time_posted"));
				MemberVO mvo=new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setName(rs.getString("name"));
				bvo.setMemberVO(mvo);
			}
			//System.out.println("dao getContent:"+bvo);
		}finally{
			closeAll(rs,pstmt,con);
		}
		return bvo;
	}
	
	/**
	 * 조회수 증가 
	 * @param no
	 * @throws SQLException
	 */
	public void updateHit(int no) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=getConnection(); 
			String sql="update board_inst set hits=hits+1 where no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);	
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
	/**
	 * 게시물 등록 메서드  
	 * 게시물 등록 후 생성된 시퀀스를 BoardVO에 setting 한다. 
	 * @param vo
	 * @throws SQLException
	 */
	public void posting(BoardVO vo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			//insert into board_inst(no,title,content,id,time_posted) values(board_inst_seq.nextval,?,?,?,sysdate)
			StringBuilder sql=new StringBuilder();
			sql.append("insert into board_inst(no,title,content,id,time_posted)");
			sql.append(" values(board_inst_seq.nextval,?,?,?,sysdate)");			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getMemberVO().getId());
			pstmt.executeUpdate();			
			pstmt.close();
			pstmt=con.prepareStatement("select board_inst_seq.currval from dual");
			rs=pstmt.executeQuery();
			if(rs.next())
			vo.setNo(rs.getInt(1));			
		}finally{
			closeAll(rs,pstmt,con);
		}
	}	

	/**
	 * 글번호에 해당하는 게시물을 삭제하는 메서드
	 * @param no
	 * @throws SQLException
	 */
	public void deletePosting(int no) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=getConnection(); 
			pstmt=con.prepareStatement("delete from board_inst where no=?");
			pstmt.setInt(1, no);		
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
	/**
	 * 게시물 정보 업데이트하는 메서드 
	 * @param vo
	 * @throws SQLException
	 */
	public void updatePosting(BoardVO vo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=getConnection();
			pstmt=con.prepareStatement("update board_inst set title=?,content=? where no=?");
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());	
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
	
	public int countAllPost() throws SQLException{
		int total=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection(); 
			StringBuilder sql=new StringBuilder();
			sql.append("select count(*) from board_inst");
			pstmt=con.prepareStatement(sql.toString());		
			rs=pstmt.executeQuery();	
			//목록에서 게시물 content는 필요없으므로 null로 setting
			//select no,title,time_posted,hits,id,name
		if(rs.next())
			total=rs.getInt(1);
		}finally{
			closeAll(rs,pstmt,con);
		}
		return total;
	}

}



















