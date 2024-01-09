package main.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

/**
 * This class creates a display for a single food product.
 */
public class ProductHandler implements HttpHandler {
    /**
     *This method creates a web page to display a single product.
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
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);

        int id = Integer.parseInt(map.get("id"));
        FoodProductDAO foodProducts = new FoodProductDAO();
        FoodProduct product = null;
        try {
            product = foodProducts.selectProduct(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        out.write(
                "<html lang=\"en\">" +
                        "  <head>" +
                        "    <meta charset=\"utf-8\" />" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />" +
                        "    <link" +
                        "      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\"" +
                        "      rel=\"stylesheet\"" +
                        "    />" +
                        "    <link" +
                        "      href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\"" +
                        "      rel=\"stylesheet\"" +
                        "    />" +
                        "    <title>Product</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "  <body" +
                        "    class=\"p-3 m-0 border-0 bd-example m-0 border-0 position-relative\"" +
                        "    style=\"background-color: green\"" +
                        "  >" );

        out.write(
                "    <div class=\"card position-absolute top-50 start-50 translate-middle\"  style=\"width: 50rem\">" +
                        "      <img" +
                        "        src=\"https://cdn.pixabay.com/photo/2020/05/25/02/35/groceries-5216715_1280.jpg\"" +
                        "        class=\"card-img-top\"" +
                        "        alt=\"fruit\"" +
                        "      />" +
                        "      <div class=\"card-body\">"
        );
        if (Objects.equals(loggedInUser, "admin")) {
        out.write(product.toAdminHTMLString());
        }else {
            out.write(product.toProductHTMLString());
        }


        out.write( " <div>" +

                "    </div>" +

                "      </div>" +
                "<br/>"+
                "<footer  class=\"text-center p-4 bg-dark text-light \">" +
                "      <p>© Naureen Imran |Manchester Metropolitan University| <span id=\"currentYear\"></span></p>" +
                "<script>" +
                "        document.getElementById(\"currentYear\").innerHTML = new Date().getFullYear();" +
                "    </script>" +
                "  </footer>"+

                        "    </div>" +
                        "    </div>" +

                        "  </body>" +

                        "</html>"
        );
        out.close();
    }
}
