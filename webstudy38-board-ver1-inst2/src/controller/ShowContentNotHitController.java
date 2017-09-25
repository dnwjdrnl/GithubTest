package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardVO;

public class ShowContentNotHitController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null){
			return "redirect:index.jsp";
		}
		int no=Integer.parseInt(request.getParameter("no"));		
		// 개별 게시물 조회  
		BoardVO vo = BoardDAO.getInstance().getPostingByNo(no);	
		request.setAttribute("bvo", vo);
		request.setAttribute("url", "/board/show_content.jsp");
		return "/template/home.jsp";
	}
}







