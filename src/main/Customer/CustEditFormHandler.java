package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class impletments http request to process the edit input to edit the customer object using web interface.
 */
public class CustEditFormHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        int id;
        String businessName;
        String addressLine1;
        String addressLine2;
        String addressLine3;
        String country;
        String postCode;
        String phoneNumber;
        Address address;

        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        id = Integer.parseInt(map.get("id"));
        CustomerDAO customers = new CustomerDAO();
        Customer customer = null;

        try {
            customer = customers.selectCustomer(id);
            if (customer == null) {
                System.out.println("The customer does not exist with the id " + id);
            } else {
                System.out.println("Current Customer " + customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String customerString = String.valueOf(customer);
        Map<String, String> customerMap = Util.customerKeyValuePairs(customerString);
        businessName = customerMap.get("Business Name");
        phoneNumber = customerMap.get("Phone Number");
        String addressString = String.valueOf(customer.getAddress());
        String[] addressParts = addressString.split(",\\s*");
        if (addressParts.length >= 5) {
            addressLine1 = addressParts[0].trim();
            addressLine2 = addressParts[1].trim();
            addressLine3 = addressParts[2].trim();
            country = addressParts[3].trim();
            postCode = addressParts[4].trim();

            String line;
            StringBuilder inputBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                inputBuilder.append(line);
            }
            System.out.println(inputBuilder);
            String input = inputBuilder.toString();
            HashMap<String, String> inputMap = Util.editStringToMap(input);
            try {
                inputMap = Util.editStringToMap(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String newBusinessName = inputMap.get("businessName");
            if (newBusinessName != null && !newBusinessName.isEmpty()) {
                businessName = newBusinessName;
            }
            String newAddressLine1 = inputMap.get("addressLine1");
            if (newAddressLine1 != null && !newAddressLine1.isEmpty()) {
                addressLine1 = newAddressLine1;
            }
            String newAddressLine2 = inputMap.get("addressLine2");
            if (newAddressLine2 != null && !newAddressLine2.isEmpty()) {
                addressLine2 = newAddressLine2;
            }
            String newAddressLine3 = inputMap.get("addressLine3");
            if (newAddressLine2 != null && !newAddressLine2.isEmpty()) {
                addressLine2 = newAddressLine2;
            }
            String newCountry = inputMap.get("country");
            if (newCountry != null && !newCountry.isEmpty()) {
                country = newCountry;
            }
            String newPostcode = inputMap.get("postCode");
            if (newPostcode != null && !newPostcode.isEmpty()) {
                postCode = newPostcode;
            }

            String newPhoneNumber = inputMap.get("phoneNumber");
            if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
                phoneNumber = newPhoneNumber;
            }

            try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(he.getResponseBody())))) {

                he.getResponseHeaders().set("Content-Type", "text/html");
                he.sendResponseHeaders(200, 0);

                address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                customers.upsert(new Customer(id, businessName, address, phoneNumber));


                pw.println(
                        "<html>" +
                                "<head> <title>Confirmation</title> </head>" +
                                "<body>" +
                                "<h1> Congratulations!</h1>" +
                                "<h1> The customer with id " + id + " is updated successfully.</h1>" +
                                "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                                "</body>" +
                                "</html>");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}