package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

/**
 * This class creates a webpage for displaying all customers.
 */
public class CustomersHandler implements HttpHandler {
    /**
     * This method creates a display for the customers list.
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        String sessionId = getCurrentSessionId(he);

        String loggedInUser = null;
        if (sessionId != null) {
            loggedInUser = SessionManager.getLoggedInUser(sessionId);
            System.out.println("Logged in as: " + loggedInUser);
        } else {
            System.out.println("No Session ID found.");
        }

        CustomerDAO customers = new CustomerDAO();
        List<Customer> allCustomers = customers.listCustomer();

        out.write(
                "<html>" +

                        "<head>" +
                        "    <meta charset=\"utf-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                        "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                        "    <link href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\" rel=\"stylesheet\">" +
                        "    <title>The Food Store</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "<body>");
        if (!Objects.equals(loggedInUser, "admin")) {
            out.write("<h4> No access !</h4>"+
                    "<a href=\"/login\" class=\"btn btn-dark p-3 m-2\"> Log In</a>"
            );
        } else {
            out.write("<div class=\"card \">" +
                    "      <img class=\"card-img\" src=\"https://cdn.pixabay.com/photo/2017/08/05/12/33/flat-lay-2583213_1280.jpg\"  alt=\"Card image\">" +
                    "      <div class=\"card-img-overlay text-end\">" +
                    "<h1 class=\"text-center\"> The Food Store !</h1>" +
                    "    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                    "      <div class=\"container-fluid\">" +
                    "        <a class=\"navbar-brand\" href=\"/\">Food Store</a>" +
                    "        <button class=\"navbar-toggler\" type=\"button\" " +
                    "data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                    "          <span class=\"navbar-toggler-icon\"></span>" +
                    "        </button>" +
                    "        <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown\">" +
                    "          <ul class=\"navbar-nav\">" +
                    "            <li class=\"nav-item\">" +
                    "              <a class=\"nav-link\" href=\"/\">Home</a>" +
                    "            </li>" +
                    "            <li class=\"nav-item\">" +
                    "<a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Customers</a>" +
                    "            </li>" +
                    "            <li class=\"nav-item\">" +
                    "              <a class=\"nav-link\" href=\"/stock\">Stock</a>" +
                    "            </li>" +
                    "            </li>" +
                    "          </ul>" +
                    "        </div>" +
                    "<nav class=\"navbar bg-body-tertiary navbar-dark bg-dark \">" +
                    "      <div class=\"container-fluid \">" +
                    "<form class=\"d-flex\" method=\"GET\" action=\"/search\">" +
                    "          <input" +
                    "            class=\"form-control me-2\"" +
                    "            type=\"search\"" +
                    "            name=\"query\"" +
                    "            placeholder=\"Search Products\"" +
                    "            aria-label=\"Search\"" +
                    "          />" +
                    "          <button class=\"btn btn-outline-success\" type=\"submit\">Search</button>" +
                    "        </form>" +
                    "      </div>" +
                    "    </nav>" +
                    "      </div>" +
                    "    </nav>" +

                    "<div class=\"hstack gap-3\">" +

                    "  <div class=\"p-2 ms-auto\">");
            if (loggedInUser != null) {
                out.write("<a href=\"/logout\" class=\"btn btn-dark p-3 mt-2\"> Log Out</a>");
            } else {
                out.write("<a href=\"/login\" class=\"btn btn-dark p-3 m-2\"> Log In</a>");


            }
            out.write("  <div class=\"p-2 ms-auto\">" +
                    "</div>" +
                    " <div class=\"vr\"></div>" +
                    "<div class=\"p-2\">" +
                    "<table class=\"table  table-light table-striped\">" +
                    "<thead>" +
                    "  <tr>" +
                    "    <th>ID</th>" +
                    "    <th>SKU</th>" +
                    "    <th>Description</th>" +
                    "    <th>Category</th>" +
                    "    <th>Price</th>" +
                    "  </tr>" +
                    "</thead>" +
                    "<tbody>");

            for (Customer c : allCustomers) {
                out.write(c.toHTMLString());
            }
            out.write(
                    "</tbody>" +
                            "</table>" +
                            "<a href=\"/addcustomer\" class=\"btn btn-success\"> Add New Customer </a>"
            );
            out.write(
                    "<footer  class=\"text-center p-4 bg-dark text-light fixed-bottom\">" +
                            "      <p>© Naureen Imran | Manchester Metropolitan University | <span id=\"currentYear\"></span></p>" +
                            "<script>" +
                            "        document.getElementById(\"currentYear\").innerHTML = new Date().getFullYear();" +
                            "    </script>" +
                            "  </footer>" +

                            "</body>" +
                            "</html>"
            );}

            out.close();

        }
    }