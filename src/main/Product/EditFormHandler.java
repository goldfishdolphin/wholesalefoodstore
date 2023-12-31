package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EditFormHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        int id;
        String description;
        String SKU;
        String category;
        long price;

        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        id = Integer.parseInt(map.get("id"));
        FoodProductDAO foodProducts = new FoodProductDAO();
        FoodProduct currentProduct = null;

        try {
            currentProduct = foodProducts.selectProduct(id);
            if (currentProduct == null) {
                System.out.println("The product does not exist with the id " + id);
            } else {
                System.out.println("currentProduct" + currentProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String productString = String.valueOf(currentProduct);
        Map<String, String> productMap = Util.productKeyValuePairs(productString);
        SKU = productMap.get("SKU");
        description = productMap.get("Description");
        category = productMap.get("Category");
        price = Long.parseLong(productMap.get("Price"));


        String line;
        StringBuilder inputBuilder = new StringBuilder();
        while ((line = in.readLine()) != null) {
            inputBuilder.append(line);
        }
        String input = inputBuilder.toString();
        HashMap<String, String> inputMap = Util.editStringToMap(input);
        try {
            inputMap = Util.editStringToMap(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String newSKU = inputMap.get("SKU");
        if (newSKU != null && !newSKU.isEmpty()) {
            SKU = newSKU;
        }

        String newDescription = inputMap.get("description");
        if (newDescription != null && !newDescription.isEmpty()) {
            description = newDescription;
        }

        String newCategory = inputMap.get("category");
        if (newCategory != null && !newCategory.isEmpty()) {
            category = newCategory;
        }

        String newPriceStr = inputMap.get("price");
        Long newPrice = (newPriceStr != null && !newPriceStr.isEmpty()) ? Long.parseLong(newPriceStr) : null;
        if (newPrice != null) {
            price = newPrice;
        }

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody())))) {

            he.getResponseHeaders().set("Content-Type", "text/html");
            he.sendResponseHeaders(200, 0);
            foodProducts.upsert(new FoodProduct(id, SKU, description, category, price));

            pw.println(
                    "<html>" +
                            "<head> <title>Confirmation</title> </head>" +
                            "<body>" +
                            "<h1> Congratulations!</h1>" +
                            "<h1> The product with id " + id + " is updated successfully.</h1>" +
                            "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                            "</body>" +
                            "</html>");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
