package controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		LoginService loginServ = new LoginService();
		
		boolean result = loginServ.authenticate(userid, password);
		if(result){
			request.getSession().setAttribute("username", loginServ.getUsername(userid));
			//response.sendRedirect("success.jsp");//Servlet Path
			RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");//saving the redirect in the request itsself
			dispatcher.forward(request, response);//forward to the new ressource
			//Advantage: all the informations stored in request will be available after the next call
						
			return;
		}else{
			response.sendRedirect("login.jsp");
		}
	}

}
