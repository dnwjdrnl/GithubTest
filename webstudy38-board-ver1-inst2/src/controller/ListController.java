package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import model.BoardVO;
import model.ListVO;
import model.PagingBean;

/**
 * DAO에서 총게시물수를 반환
 * 
 * client로 부터 페이지 번호를 전달받는다
 * 만약pageNo가 없으면 1페이지로 처리
 * 
 * PaginBean을 생성
 * DAO에서 현 페이지에 해당하는 게시물 리스트를 반환
 * (이 때 PagingBean 객체를 전달)
 * ListVO를 생성해서 request에 공유
 * 
 * @author kosta
 *
 */
public class ListController  implements Controller {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {				
		int NumberOfPost=BoardDAO.getInstance().countAllPost();
		int pageNum=-1;
		PagingBean pb=null;
		if(request.getParameter("pageNum")!=null)
			pageNum=Integer.parseInt(request.getParameter("pageNum"));
		if(pageNum==-1) 
			pb= new PagingBean(NumberOfPost);
		else
			pb= new PagingBean(NumberOfPost,pageNum);
		ArrayList<BoardVO> list=BoardDAO.getInstance().getPostingList(pb);
		ListVO vo=new ListVO(list,pb);
		request.setAttribute("url", "/board/list.jsp");
		request.setAttribute("vo", vo);
	//list와 pb를 받아서 ListVO에 저장한 후 request.setAttribute에 ListVO를 저장
		
		return "/template/home.jsp";
	}
}







