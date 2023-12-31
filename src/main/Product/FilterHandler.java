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

public class FilterHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        System.out.println("hello1");
        String request = he.getRequestURI().getQuery();
        System.out.println(request);
        HashMap<String, String> map = Util.requestStringToMap(request);
        System.out.println(map);
        String category = map.get("Category").toLowerCase();
        System.out.println(category);
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
            if (foodProduct.toString().toLowerCase().contains(category)) {
                filteredProducts.add(foodProduct);
            }
        }
        System.out.println("filtered");
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
                            "<div class=\"dropdown\">" +
                            "      <button class=\"btn btn-success dropdown-toggle\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                            "        Choose Category" +
                            "      </button>" +
                            "      <ul class=\"dropdown-menu\" style=\"\">" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Fruit\">Fruit</a></li>" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Vegetable\">Vegetable</a></li>" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Rice\">Rice</a></li>" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Cold Drink\">Cold Drink</a></li>" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Frozen Food\">Frozen Food</a></li>" +
                            "        <li><a class=\"dropdown-item\" href=\"filter?Category=Snack\">Snack</a></li>" +
                            "<li><a class=\"dropdown-item\" href=\"/\">All</a></li>" +
                            "      </ul>" +
                            "    </div>" +
                            "<nav class=\"navbar bg-body-tertiary\">" +
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