package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.HobbyDAO;
import model.HobbyVO;

public class SelectHobbyController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("controller ");
		ArrayList<HobbyVO> list=HobbyDAO.getInstance().selectHobbyList();
		System.out.println(list);
		request.setAttribute("hobbyList", list);
		return "register_member.jsp";
	}

}
