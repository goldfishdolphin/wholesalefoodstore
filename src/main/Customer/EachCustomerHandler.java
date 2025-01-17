package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class display the information of the each customer.
 */
public class EachCustomerHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);

        int id = Integer.parseInt(map.get("id"));
        CustomerDAO customers= new CustomerDAO();
        Customer customer = null;
        try {
            customer= customers.selectCustomer(id);
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
                        "    <title>Customer</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "  <body" +
                        "    class=\"p-3 m-0 border-0 bd-example m-0 border-0\"" +
                        "    style=\"background-color: lightblue\"" +
                        "  >" +
                        "    <div class=\"card\" style=\"width: 18rem\">" +
                        "      <img" +
                        "        src=\"https://images.pexels.com/photos/161559/background-bitter-breakfast-bright-161559.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\"" +
                        "        class=\"card-img-top\"" +
                        "        alt=\"fruit\"" +
                        "      />" +
                        "      <div class=\"card-body\">");
        out.write(customer.toCustomertHTMLString());

        out.write(
                "      </div>" +
                        "    </div>" +
                        "  </body>" +
                        "</html>"
        );
        out.close();
    }
}
