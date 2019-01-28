package by.htp.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final CommandProvider provider = new CommandProvider();

	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	 @Override
	 public void init() throws ServletException {
	 // инициализирующие действия до запуска сервлета
	 // даже закоменченный он все равно вызовется
	 super.init(); 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception ex) {
			//out.println("Exception: " + ex.getMessage() + "oracle.jdbc.driver.OracleDriver");
			ex.printStackTrace();
		}
	 }
	// @Override
	// protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
	// throws ServletException, IOException {
	// // жизненный цикл веб приложения
	// // после INIT запрос передается TomCATом в метод сервлета service
	// // две ссылки HttpServletRequest arg0, HttpSer vletResponse arg1 - приходят в
	// сервис
	// // если мы посылали запрос с методом GET то выполнится doGet
	// // если мы посылали запрос с методом POST то выполнится doPost
	// // МЕТОД service ЗА ЧАСТУЮ НЕ ПЕРЕОПРЕДЕЛЯЮТ
	// super.service(arg0, arg1);
	// }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		Command commandObject = provider.getCommand(command);
		commandObject.execute(request, response);
		doGet(request, response);

	}

}
