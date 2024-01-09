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

/**
 * This class object searches the products.
 */
public class SearchHandler implements HttpHandler {
    /**
     * This method responds with search results.
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
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

            pw.write("</body>" +
                    "</html>"
            );
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}