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
        name = "openAccountServlett",
        urlPatterns = "/OpenAccount"
)
public class OpenAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        String ownerId = req.getParameter("ownerId");
        try {
            double deposit = Double.valueOf(req.getParameter("amount"));
            String type = req.getParameter("Type");

            BaseBankAccount account = new BankAccountService().openBankAccount(type, deposit, ownerId);

            if (account == null) {
                message = "Couldn't create duplicated user id";
            } else {
                message = "Account for Owner ID " + String.valueOf(account.ownerID) + " was created successfully";
            }
        } catch (Throwable e) {
            message = "Not possible to create such account";
        }
        PrintWriter out = resp.getWriter();
        createOutputPage(out, message);
    }


    private void createOutputPage(PrintWriter out, String message) throws IOException {


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
        out.println(message);
        out.println("       </center>");
        out.println("</body>");
        out.println("</html>");
    }
}
