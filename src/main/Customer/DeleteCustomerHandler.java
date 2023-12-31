package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Product.FoodProductDAO;
import main.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class DeleteCustomerHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        FoodProductDAO foodProducts = new FoodProductDAO();
        CustomerDAO customers = new CustomerDAO();
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));
        customers.deleteCustomer(id);
        try {
            Optional<Customer> optionalCustomer = Optional.ofNullable(customers.selectCustomer(id));
            System.out.println(optionalCustomer);
            if (optionalCustomer.isEmpty())
                System.out.println("Customer Not found");
            else {
               customers.deleteCustomer(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
