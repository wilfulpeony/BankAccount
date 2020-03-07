import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "depositAccountServlett",
        urlPatterns = "/DepositAction"
)
public class DepositServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String ownerId = req.getParameter("ownerId");
        String balance;
        try {
        double deposit = Double.valueOf(req.getParameter("amount"));


            BaseBankAccount account = new BankAccountService().putMoneyOnAccount(ownerId, deposit);
            balance = String.valueOf(account.balance);

            req.setAttribute("balance", balance);
        } catch (Throwable e) {
            balance = "Not possible transaction";
        }
        PrintWriter out = resp.getWriter();
        createOutputPage(out, balance);
    }


    private void createOutputPage(PrintWriter out, String balance) throws IOException {


        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Bank Account</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<center>");
        out.println("<h1>");
        out.println("Please, enter your ID to view account:");
        out.println("</h1>");
        out.println("<form method=\"post\" action=\"DisplayAccount\">");
        out.println("<br>");
        out.println("Enter Owner ID");
        out.println("<input name=\"ownerId\" type=\"text\"><br>");
        out.println("<br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("<br><br>");
        out.println(balance);
        out.println("       </center>");
        out.println("</body>");
        out.println("</html>");
    }
}
