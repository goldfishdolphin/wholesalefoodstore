package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Product.FoodProduct;
import main.Product.FoodProductDAO;
import main.User.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

/**
 * This class implements Http Handler to process a request to display the home page.
 */
public class RootHandler implements HttpHandler {
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
        FoodProductDAO foodProducts = new FoodProductDAO();
        List<FoodProduct> allProducts = foodProducts.listProduct();

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
                        "              <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Home</a>" +
                        "            </li>" +
                        "            <li class=\"nav-item\">" +
                        "              <a class=\"nav-link\" href=\"/customers\">Customers</a>" +
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
                        "<div class=\"dropdown\">" +
                        "      <button class=\"btn btn-success dropdown-toggle p-3 mt-2\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                        "        Choose Category" +
                        "      </button>" +
                        "      <ul class=\"dropdown-menu\" style=\"\">" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Fruit\">Fruit</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Vegetable\">Vegetable</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Cold Drink\">Cold Drink</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Frozen Food\">Frozen Food</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Snack\">Snack</a></li>" +
                        "<li><a class=\"dropdown-item\" href=\"/\">All</a></li>" +
                        "      </ul>" +
                        "    </div>" +

                        "  <div class=\"p-2 ms-auto\">");
        if (loggedInUser != null) {
            out.write("<a href=\"/logout\" class=\"btn btn-dark p-3 mt-2\"> Log Out</a>");
        } else {
            out.write("<a href=\"/login\" class=\"btn btn-dark p-3 m-2\"> Log In</a>");


        }
        out.write("</div>" +
                " <div class=\"vr\"></div>" +
                "<div class=\"p-2\">" +
                "<a href=\"/viewbasket\" class=\"btn btn-dark mt-2 p-3\"> " +

                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-basket-fill\" viewBox=\"0 0 16 16\">" +
                "  <path d=\"M5.071 1.243a.5.5 0 0 1 .858.514L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15.5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H15v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9H.5a.5.5 0 0 1-.5-.5v-2A.5.5 0 0 1 .5 6h1.717zM3.5 10.5a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0z\"/>" +
                "</svg>" +
                "   Shopping Basket </a>" +
                "</div>" +
                "    </div >" +
                "<br>" +
                "<br>" +
                "        <h5 class=\"card-title h3 text-success\">Welcome to Food Store</h5>" +
                "        <p class=\"text-dark\">Wholesale Trade for your business!</p>" +
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
        for (FoodProduct p : allProducts) {
            if (Objects.equals(loggedInUser, "admin")) {
                out.write(p.toHTMLString());
            } else {
                out.write(p.toCustomerHTMLString());
            }
        }
        out.write(
                "</tbody>" +
                        "</table>");
        if (Objects.equals(loggedInUser, "admin")) {
            out.write("<a href=\"/add\" class=\"btn btn-dark\"> Add New Product </a>"
            );
        }

        out.write(
                "<footer  class=\"text-center p-4 bg-dark text-light fixed-bottom\">" +
                        "      <p>© Naureen Imran | Manchester Metropolitan University | <span id=\"currentYear\"></span></p>" +
                        "<script>" +
                        "        document.getElementById(\"currentYear\").innerHTML = new Date().getFullYear();" +
                        "    </script>" +
                        "  </footer>"
        );

        out.write("      </div>" +
                "    </div>");


        out.write("</body>" +
                "</html>"
        );

        out.close();

    }
}