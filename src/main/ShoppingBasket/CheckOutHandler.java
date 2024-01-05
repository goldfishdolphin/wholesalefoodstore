package main.ShoppingBasket;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CheckOutHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        out.write(" <html>" +

                "<head>" +
                "    <meta charset=\"utf-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "    <link href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\" rel=\"stylesheet\">" +
                "    <title>The Food Store</title>" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                "  </head>" +
                "<body>" +
                "<h1 class= text-center> Order Placed !</h1>" +
                "<h5> Thank you for placing the order. We'll send you an invoice shortly. :)</h5>" +
                "<a href=\"/\" class=\"btn btn-primary\"> Home </a>" +
                "</body>" +
                "</html>");
        out.close();
    }

}
