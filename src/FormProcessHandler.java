import java.io.OutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FormProcessHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {


        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));

        String line;
        String request = "";
        while ((line = in.readLine()) != null) {
            request = request + line;
        }
        System.out.println(request);
        HashMap<String, String> map = Util.requestStringToMap(request);

        int id = Integer.parseInt(map.get("id"));
        String SKU = map.get("SKU");
        String description = map.get("description");
        String category = map.get("category");
        Long price = Long.parseLong(map.get("price"));

        System.out.println("descript " + description);
        System.out.println("SKU " + SKU);
        System.out.println("id " + id);
        System.out.println(price);
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody())));

        he.sendResponseHeaders(200, 0);
        FoodProductDAO foodProduct = new FoodProductDAO();
        foodProduct.upsert(new FoodProduct(id, SKU, description, category, price));
        System.out.println(foodProduct.upsert(new FoodProduct(id, SKU, description, category, price)));
        pw.println(
                "<html>" +
                        "<head> <title>Confirmation</title> </head>" +
                        "<body>" +

                        "<h1> Congratulations!</h1>" +
                        "<h1> " + description + " is added successfully.</h1>" +
                        "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                        "</body>" +
                        "</html>");

        // System.out.println("Sending response...");

        pw.close();

    }

}