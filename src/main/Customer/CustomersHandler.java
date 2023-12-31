package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CustomersHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        CustomerDAO customers = new CustomerDAO();
        List<Customer> allCustomers = customers.listCustomer();

        out.write(
                "<html>" +
                        "<head> <title>The Food Store</title> " +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
                        +
                        "</head>" +
                        "<body style={background-color:grey;}>" +
                        "<h1> All Customers !</h1>" +

                        "<table class=\"table\">" +
                        "<thead>" +
                        "  <tr>" +
                        "    <th>Customer ID</th>" +
                        "    <th>Business Name</th>" +
                        "    <th>Phone Number</th>" +
                        "    <th>Address</th>" +
                        "  </tr>" +
                        "</thead>" +
                        "<tbody>");

        for (Customer c : allCustomers) {
            out.write(c.toHTMLString());
        }
        out.write(
                "</tbody>" +
                        "</table>" +
                        "<a href=\"/addcustomer\" class=\"btn btn-dark\"> Add New Customer </a>" +
                        "</body>" +
                        "</html>"
        );

        out.close();

    }
}