package main.ShoppingBasket;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static main.Util.getCurrentSessionId;

/**
 * This a basket view object to display basket items and value.
 */

public class BasketViewHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        double totalBasketValue = 0;

        ShoppingBasketDAO basketDAO = new ShoppingBasketDAO();
        List<ShoppingBasket> basketList = basketDAO.listBasketItems();


        String sessionId = getCurrentSessionId(he);
        String loggedInUser = null;
        if (sessionId != null) {
            loggedInUser = SessionManager.getLoggedInUser(sessionId);
            System.out.println("Logged in as: " + loggedInUser);
        } else {
            System.out.println("No Session ID found.");
        }


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
                        "<body>" +

                        "<div class=\"card \">" +
                        "      <img class=\"card-img\" src=\"https://cdn.pixabay.com/photo/2017/08/05/12/33/flat-lay-2583213_1280.jpg\"  alt=\"Card image\">" +
                        "      <div class=\"card-img-overlay text-end\">" +
                        "<h1 class=\"text-center\"> Shopping basket !</h1>" +
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
                        "<table class=\"table\">" +
                        "<thead>" +
                        "  <tr>" +
                        "    <th>Product ID</th>" +
                        "    <th>Product</th>" +
                        "    <th>Quantity</th>" +
                        "    <th>Unit Price</th>" +
                        "    <th>Total Price</th>" +
                        "  </tr>" +
                        "</thead>" +
                        "<tbody>");
        for (ShoppingBasket b : basketList) {
            out.write(b.toHTMLString());
            totalBasketValue = totalBasketValue + b.getTotalPrice();
        }

        out.write(
                "</tbody>" +
                        "</table>" +

                        "<div class=\"grid display-6 text-center text-lg text-success\">" +
                        "  <div class=\"g-col-3 display-6 g-start-6\">Total Basket Amount: " + totalBasketValue + "</div>" +
                        "</div>" +

                        "<a href=\"/\" class=\"btn btn-success\"> Continue Shopping</a>" +
                        "<a href=\"/checkout\" class=\"btn btn-dark\">Check Out</a>" +
                        "<a href=\"/clear\" class=\"btn btn-danger\">Clear Basket</a>" +
                        "<footer  class=\"text-center p-4 bg-dark text-light fixed-bottom\">" +
                        "      <p>© Naureen Imran | Manchester Metropolitan University | <span id=\"currentYear\"></span></p>" +
                        "<script>" +
                        "        document.getElementById(\"currentYear\").innerHTML = new Date().getFullYear();" +
                        "    </script>" +
                        "  </footer>"+
                        "</body>" +
                        "</html>"
        );

        out.close();

    }

}

