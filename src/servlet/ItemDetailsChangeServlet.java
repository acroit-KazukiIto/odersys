package servlet;

import java.io.IOException;

import dao.OrderListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ItemDetailsChangeLogic;
import model.OrderListInfo;

@WebServlet("/ItemDetailsChangeServlet")
public class ItemDetailsChangeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		OrderListDAO olDAO = new OrderListDAO();
		String orderId =
				request.getParameter("oid");
		System.out.println("おーだーあいでぃーーー" + orderId);
		int oId = Integer.parseInt(orderId);

		OrderListInfo ol = olDAO.findorderDetailsByorderFlag2(oId);
		request.setAttribute("ol", ol);


		/*List<ItemDetailsInfo> tList =
				dao.findToppingList(category);

		ItemDetailsLogic logic =
				new ItemDetailsLogic();

		int subTotal =
				logic.calcSubTotal(price, tList);

		request.setAttribute(
				"productId",
				productId
				);

		request.setAttribute(
				"selectedPName",
				name
				);

		request.setAttribute(
				"selectedPPrice",
				price
				);

		request.setAttribute(
				"currentCategory",
				category
				);

		request.setAttribute(
				"orderId",
				orderId
				);

		request.setAttribute(
				"subTotal",
				subTotal
				);

		request.setAttribute(
				"toppingList",
				tList
				);*/

		request.getRequestDispatcher(
				"WEB-INF/jsp/itemDetailsChange.jsp"
				).forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		OrderListDAO dao = new OrderListDAO();
		ItemDetailsChangeLogic logic = new ItemDetailsChangeLogic();

		HttpSession session =
				request.getSession();

		String Button =
				request.getParameter("Button");
		
		String Oid = request.getParameter("oid");
		int oid = Integer.parseInt(Oid);
		
		String Op = request.getParameter("op");
		int op = Integer.parseInt(Op);
		
		String Pp = request.getParameter("pp");
		int pp = Integer.parseInt(Pp);
		
		String Pn = request.getParameter("pn");
		int pn = Integer.parseInt(Pn);
		
		String Tq = request.getParameter("tq");
		int tq = Integer.parseInt(Tq);
		
		String Cn = request.getParameter("cn");
		int cn = Integer.parseInt(Cn);
		
		String Tid = request.getParameter("tid");
		int tid = Integer.parseInt(Tid);
		




		//イベント処理
		if("minus".equals(Button)){
			if(tq == 0) {
				}else if(tq == 1) {
					dao.deteleTopping(oid);
				}else {
					dao.updateTopping(-1, oid, tid);
				}
			
		}else if("plus".equals(Button)) {
			//プラス処理
			if(tq == 0) {
				dao.insertTopping(tid, oid);
				}else if(tq == 1 ) {
					dao.updateTopping(1, oid, tid);
				}else if(tq == 20) {
					tq = 20;
				}
			
		}
	}
	}