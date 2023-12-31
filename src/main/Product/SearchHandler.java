package main.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

import static main.Util.getCurrentSessionId;

public class SearchHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        String query = map.get("query").toLowerCase();

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
            if (foodProduct.toString().toLowerCase().contains(query)) {
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
                            "<head> <title>The Food Store</title> " +
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
                            +
                            "</head>" +
                            "<body style={background-color:grey;}>" +
                            "<h1> Food Products !</h1>" +
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