package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * This class create a form for editing the customer's information.
 */
public class EditCustomerHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));
        out.write(
                "<html lang=\"en\">" +
                        "  <head>" +
                        "    <meta charset=\"utf-8\" />" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />" +
                        "    <link" +
                        "      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\"" +
                        "      rel=\"stylesheet\"" +
                        "    />" +
                        "    <link" +
                        "      href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\"" +
                        "      rel=\"stylesheet\"" +
                        "    />" +
                        "    <title>Edit Customer</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "  <body class=\"p-3 m-0 border-0 m-0 border-0\">" +
                        "    <h1 class=\"text-center\">The Food Store</h1>" +
                        "    <h3 class=\"text-center\">Edit Customer Details</h3>" +
                        "    <p class=\"text-left\">Enter the new values for the fields you want to update:</p>" +
                        "<form method=\"Post\" action=\"/custeditformaction?id=" + id + "\">" +
                        "<div class=\"mb-3\">" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"businessName\" class=\"form-label\">New Business Name:</label>" +
                        "        <input name=\"businessName\" type=\"text\" class=\"form-control\" id=\"businessName\" />" +
                        "      </div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine1\" class=\"form-label\">Edit First Line of Address</label>" +
                        "        <textarea name=\"addressLine2\" class=\"form-control\" id=\"addressLine1\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine2\" class=\"form-label\">Edit Second Line of Address</label>" +
                        "        <textarea name=\"addressLine2\" class=\"form-control\" id=\"addressLine2\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine3\" class=\"form-label\">Edit Third Line of Address</label>" +
                        "        <textarea name=\"addressLine3\" class=\"form-control\" id=\"addressLine3\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"country\" class=\"form-label\">Edit Country</label>" +
                        "        <textarea name=\"country\" class=\"form-control\" id=\"country\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"postCode\" class=\"form-label\">Edit Postcode</label>" +
                        "        <textarea name=\"postCode\" class=\"form-control\" id=\"postCode\" rows=\"1\"></textarea>" +
                        "</div>" +
                        " <div class=\"mb-3\">" +
                        "        <label for=\"phoneNumber\" class=\"form-label\">Edit Phone Number</label>" +
                        "        <textarea name=\"phoneNumber\" class=\"form-control\" id=\"phoneNumber\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>" +
                        "    </form>" +
                        "  </body>" +
                        "</html>"
        );
        out.close();

    }

}