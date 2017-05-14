package uk.ac.york.cs.es.smlg;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.york.cs.es.smlg.model.User;

/**
 * Servlet implementation class LoggingIn
 */
@WebServlet("/LoggingIn")
public class LoggingIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoggingIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String path = getServletContext().getRealPath(".");

		if (User.userExists(path, username)) {

			User user = User.getUserByUsername(path, username);
			if (user == null) {
				request.setAttribute("Message", "Username doesn't exist! Try another username!");
				RequestDispatcher rd = request.getRequestDispatcher("Login");
				rd.forward(request, response);
				return;
			}

			if (!user.getPassword().equals(password)) {
				request.setAttribute("Message", "Password doesn't match!");
				RequestDispatcher rd = request.getRequestDispatcher("Login");
				rd.forward(request, response);
				return;
			}

			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			
//			RequestDispatcher rd = request.getRequestDispatcher("Main");
//			rd.forward(request, response);
			response.sendRedirect("Main");
			return;
		} else {
			request.setAttribute("Message", "User doesn't exist! Try another username!");
			RequestDispatcher rd = request.getRequestDispatcher("Login");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
