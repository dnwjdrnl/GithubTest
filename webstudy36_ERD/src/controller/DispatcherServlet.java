package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
		}
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			handleRequest(request, response);
		}
		public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try{ 
				String command=request.getParameter("command");
				System.out.println(command);
				Controller c=HandlerMapping.getInstance().create(command);
				String url=c.execute(request, response);			
				if(url.startsWith("redirect:"))
					response.sendRedirect(url.substring(9));
				else
					request.getRequestDispatcher(url).forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
				response.sendRedirect("error.jsp");
			}			
		}

}
