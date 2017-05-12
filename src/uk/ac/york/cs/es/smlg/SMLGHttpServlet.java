package uk.ac.york.cs.es.smlg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SMLGHttpServlet
 */
@WebServlet("/SMLGHttpServlet")
public class SMLGHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected HttpSession session = null;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null){
			response.sendRedirect("Login");
			return;
		}
		String username = session.getAttribute("username").toString();
		request.setAttribute("username", username);
		
		super.service(request, response);
	}
}
