package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class EditHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));
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
                        "    <title>Edit Product</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "  <body class=\"p-3 m-0 border-0 m-0 border-0\">" +
                        "    <h1 class=\"text-center\">The Food Store</h1>" +
                        "    <h3 class=\"text-center\">Insert Product Details</h3>" +
                        "    <p class=\"text-left\">Enter the new values for the fields you want to update:</p>" +
                        "<form method=\"Post\" action=\"/editformaction?id=" + id + "\">" +
                        "<div class=\"mb-3\">" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"SKU\" class=\"form-label\">New SKU:</label>" +
                        "        <input name=\"SKU\" type=\"text\" class=\"form-control\" id=\"SKU\" />" +
                        "      </div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"description\" class=\"form-label\">New Description:</label>" +
                        "        <textarea name=\"description\" class=\"form-control\" id=\"description\" rows=\"2\"></textarea>" +
                        "</div>" +
                        "<div>" +
                        "        <select name=\"category\" class=\"form-select\" aria-label=\"Default category menu\">" +
                        "          <option selected>Change Category</option>" +
                        "          <option value=\"Fruit\">Fruit</option>" +
                        "          <option value=\"Vegetable\">Vegetable</option>" +
                        "          <option value=\"Rice\">Rice</option>" +
                        "          <option value=\"Cold Drink\">Cold Drinks</option>" +
                        "          <option value=\"Frozen Food\">Frozen Food</option>" +
                        "          <option value=\"Snack\">Snack</option>" +
                        "        </select>" +
                        "      </div>" +

                        "<div class=\"mb-3 mt-3\">" +
                        "        <label for=\"price\" class=\"form-label\">New Price:</label>" +
                        "        <input name=\"price\" type=\"number\" class=\"form-control\" id=\"price\" />" +
                        "      </div>" +
                        "      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>" +
                        "    </form>" +
                        "  </body>" +
                        "</html>"
        );
        out.close();

    }

}
