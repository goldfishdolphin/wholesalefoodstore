package main.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

/**
 * This class filters the products by their category and display them on web.
 */
public class FilterHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        String category = map.get("Category").toLowerCase();

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
        List<FoodProduct> filteredProducts = new ArrayList<>();
        for (FoodProduct foodProduct : allProducts) {
            if (foodProduct.toString().toLowerCase().contains(category.toLowerCase())) {
                filteredProducts.add(foodProduct);
            }
        }
        filteredProducts.forEach(System.out::println);

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody())))) {

            he.getResponseHeaders().set("Content-Type", "text/html");
            he.sendResponseHeaders(200, 0);


            pw.println(
                    "<html>" +
                            "<head> " +
                            "    <meta charset=\"utf-8\">" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                            "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                            "    <link href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\" rel=\"stylesheet\">" +
                            "    <title>The Food Store</title>" +
                            "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                            "  </head>" +
                            "<body>" +
                            "<h1> Food Products !</h1>" +
                         "   <nav class=\"navbar navbar-expand-lg bg-body-tertiary\">" +
                    "      <div class=\"container-fluid\">" +
                    "        <a class=\"navbar-brand\" href=\"/\">Food Store</a>" +
                    "        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                    "          <span class=\"navbar-toggler-icon\"></span>" +
                    "        </button>" +
                    "        <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown\">" +
                    "          <ul class=\"navbar-nav\">" +
                    "            <li class=\"nav-item\">" +
                    "              <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Home</a>" +
                    "            </li>" +
                    "            <li class=\"nav-item\">" +
                    "              <a class=\"nav-link\" href=\"#\">Customers</a>" +
                    "            </li>" +
                    "            <li class=\"nav-item\">" +
                    "              <a class=\"nav-link\" href=\"#\">Pricing</a>" +
                    "            </li>" +
                    "            <li class=\"nav-item dropdown\">" +
                    "              <a class=\"nav-link dropdown-toggle\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                    "                Dropdown link" +
                    "              </a>" +
                    "              <ul class=\"dropdown-menu\">" +
                    "                <li><a class=\"dropdown-item\" href=\"#\">Action</a></li>" +
                    "                <li><a class=\"dropdown-item\" href=\"#\">Another action</a></li>" +
                    "                <li><a class=\"dropdown-item\" href=\"#\">Something else here</a></li>" +
                    "              </ul>" +
                    "            </li>" +
                    "          </ul>" +

                    "<nav class=\"navbar bg-body-tertiary \">" +
                    "      <div class=\"container-fluid\">" +
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
                    "        </div>" +
                    "      </div>" +
                    "    </nav>" +


                    "<div class=\"hstack gap-3\">" +
                    "<div class=\"dropdown\">" +
                    "      <button class=\"btn btn-success dropdown-toggle\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
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

                    "  <div class=\"p-2 ms-auto\">" +
                    "</div>" +
                    " <div class=\"vr\"></div>" +
                    "<div class=\"p-2\">" +
                    "<a href=\"/viewbasket\" class=\"btn btn-dark\"> " +

                    "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-basket-fill\" viewBox=\"0 0 16 16\">" +
                    "  <path d=\"M5.071 1.243a.5.5 0 0 1 .858.514L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15.5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H15v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9H.5a.5.5 0 0 1-.5-.5v-2A.5.5 0 0 1 .5 6h1.717zM3.5 10.5a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0z\"/>" +
                    "</svg>" +
                    "   Shopping Basket </a>" +
                    "</div>" +
                    "    </div >" +
                            "<table class=\"table\">" +
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
            for (FoodProduct p : filteredProducts) {
                if (Objects.equals(loggedInUser, "admin")) {
                    pw.write(p.toHTMLString());
                } else {
                    pw.write(p.toCustomerHTMLString());
                }
            }
            pw.write(
                    "</tbody>" +
                            "</table>");
            if (Objects.equals(loggedInUser, "admin")) {
                pw.write("<a href=\"/add\" class=\"btn btn-dark\"> Add New Product </a>" +
                        "<a href=\"/customers\" class=\"btn btn-dark\"> Customer</a>");
            }

            if (loggedInUser != null) {
                pw.write("<a href=\"/logout\" class=\"btn btn-dark\"> Log Out</a>");
            } else {
                pw.write("<a href=\"/login\" class=\"btn btn-dark\"> Log In</a>");
            }

            pw.write("</body>" +
                    "</html>"
            );
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}