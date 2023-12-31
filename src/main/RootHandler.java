package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class RootHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        FoodProductDAO foodProducts = new FoodProductDAO();
        List<FoodProduct> allProducts = foodProducts.listProduct();

        out.write(
                "<html>" +
                        "<head> <title>The Food Store</title> " +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
                        +
                        "</head>" +
                        "<body style={background-color:grey;}>" +
                        "<h1> All Products !</h1>" +

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

        for (FoodProduct p : allProducts) {
            out.write(p.toHTMLString());
        }
        out.write(
                "</tbody>" +
                        "</table>" +
                        "<a href=\"/add\" class=\"btn btn-dark\"> Add New Product </a>" +
                        "</body>" +
                        "</html>"
        );

        out.close();

    }
}