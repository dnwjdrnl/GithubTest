package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.HobbyDAO;
import model.MemberVO;

public class FindByIdController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberVO vo=HobbyDAO.getInstance().detailInfo(request.getParameter("id"));
		request.setAttribute("vo", vo);
		return "detail.jsp";
	}

}
