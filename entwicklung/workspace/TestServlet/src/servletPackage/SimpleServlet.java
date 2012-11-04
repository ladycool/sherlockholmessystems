package servletPackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet(description = "A simple servlet", urlPatterns = { "/SimpleServletPath" })
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		//How to use a session
		String name = request.getParameter("inputtest");
		HttpSession session = request.getSession();
		if(!name.isEmpty()){
			session.setAttribute("savedName", name);
		}
		name = (String)session.getAttribute("savedName");
		
		/*How to use a context
		ServletContext context = request.getServletContext();
		if(!name.isEmpty()){
			context.setAttribute("savedName", name);
		}
		name = (String)context.getAttribute("savedName");
		*/
		
		PrintWriter writer = response.getWriter();
		writer.println("<h3>Hey hey there "+name+" </h3>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("inputtest");
		String damm = request.getParameter("selecttest");
		PrintWriter writer = response.getWriter();
		writer.println("<h3>Hey hey "+ name +" der Prof. Dr. Damm sagt: "+
				damm +"</h3>");
	}
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
