package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.*;
import java.util.HashMap;

/**
 * This class process the input of the new customer's form data and put it in the database.
 */
public class CustomerFormProcessHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));

        String line;
        String request = "";
        while ((line = in.readLine()) != null) {
            request = request + line;
        }
        HashMap<String, String> map = Util.requestStringToMap(request);

        System.out.println(map);
        int id = Integer.parseInt(map.get("id"));
        String businessName = map.get("businessName");
        System.out.println(businessName);
        String addressLine1 = map.get("addressLine1");
        System.out.println(addressLine1);
        String addressLine2 = map.get("addressLine2");
        System.out.println(addressLine2);
        String addressLine3 = map.get("addressLine3");
        System.out.println(addressLine3);
        String country = map.get("country");
        System.out.println(country);
        String postCode = map.get("postCode");
        System.out.println(postCode);
        String phoneNumber = map.get("phoneNumber");
        System.out.println(phoneNumber);
        var address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(he.getResponseBody())));

        he.sendResponseHeaders(200, 0);
        CustomerDAO customers = new CustomerDAO();
        customers.upsert(new Customer(id, businessName, address, phoneNumber));

        pw.println("<html>" +
                "<head> <title>Confirmation</title> </head>" +
                "<body>" +
                "<h1> Congratulations!</h1>" + "<h1> " + businessName+
                " is added successfully.</h1>" + "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                "</body>" +
                "</html>");
        pw.close();

    }

}