import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "selectactionservlet",
        urlPatterns = "/AccountAction"
)
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String actionType = req.getParameter("Type");
        String page;

        switch (actionType) {
            case "Open new account": {
                page = "openAccount.jsp";
                break;
            }
            case "Withdraw money": {
                page = "withdraw.jsp";
                break;
            }
            case "Put money on account": {
                page = "deposit.jsp";
                break;
            }
            case "View Account": {
                page = "viewAccount.jsp";
                break;
            }
            default: {
                throw new RuntimeException("No such action.");
            }
        }
        RequestDispatcher view = req.getRequestDispatcher(page);
        view.forward(req, resp);

    }
}