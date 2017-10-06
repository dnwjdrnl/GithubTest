package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.HobbyDAO;
import model.HobbyVO;
import model.MemberVO;

public class RegisterController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id= request.getParameter("id");
		String password=request.getParameter("password");
		String name= request.getParameter("name");
		String[] hobby= request.getParameterValues("hobby");
		ArrayList<HobbyVO> list=new ArrayList<HobbyVO>();
		if(hobby!=null) {
		for(int i=0;i<hobby.length;i++)
			list.add(new HobbyVO(hobby[i]));
		}
		MemberVO vo= new MemberVO(id,password,name,list);
		HobbyDAO.getInstance().register(vo);

		return "register_result.jsp";
	}

}
