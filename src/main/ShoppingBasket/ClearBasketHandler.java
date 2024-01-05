package main.ShoppingBasket;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ClearBasketHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        ShoppingBasketDAO basket = new ShoppingBasketDAO();
        basket.emptyBasket();
        he.getResponseHeaders().set("Location", "/viewbasket");
        he.sendResponseHeaders(302, 0);
        he.getResponseBody().close();
    }
    }

