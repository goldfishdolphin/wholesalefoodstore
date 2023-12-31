package main.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.*;
import java.util.HashMap;

public class FormProcessHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));

        String line;
        String request = "";
        while ((line = in.readLine()) != null) {
            request = request + line;
        }
        HashMap<String, String> map = Util.requestStringToMap(request);

        int id = Integer.parseInt(map.get("id"));
        String SKU = map.get("SKU");
        String description = map.get("description");
        String category = map.get("category");
        long price = Long.parseLong(map.get("price"));

        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(he.getResponseBody())));

        he.sendResponseHeaders(200, 0);
        FoodProductDAO foodProducts = new FoodProductDAO();
        foodProducts.upsert(new FoodProduct(id, SKU, description, category, price));
        pw.println("<html>" +
                "<head> <title>Confirmation</title> </head>" +
                "<body>" +
                "<h1> Congratulations!</h1>" + "<h1> " + description +
                " is added successfully.</h1>" + "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                "</body>" +
                "</html>");
        pw.close();

    }

}