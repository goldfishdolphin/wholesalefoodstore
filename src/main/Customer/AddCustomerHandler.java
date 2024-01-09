package main.Customer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class adds a new customer.
 */
public class AddCustomerHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        out.write(
                "<html lang=\"en\">"+
                        "  <head>"+
                        "    <meta charset=\"utf-8\" />"+
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />"+
                        "    <link"+
                        "      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\""+
                        "      rel=\"stylesheet\""+
                        "    />"+
                        "    <link"+
                        "      href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\""+
                        "      rel=\"stylesheet\""+
                        "    />"+
                        "    <title>Add Customer</title>"+
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>"+
                        "  </head>"+
                        "  <body class=\"p-3 m-0 border-0 m-0 border-0\">"+
                        "    <h1 class=\"text-center\">The Food Store</h1>"+
                        "    <h3 class=\"text-center\">Insert Customer Details</h3>"+
                        "<form method=\"Post\" action=\"/customerformaction\">"+
                        "<div class=\"mb-3\">"+
                        "        <label for=\"id\" class=\"form-label\">Customer ID:</label>"+
                        "        <input name=\"id\" type=\"number\" class=\"form-control\" id=\"id\" />"+
                        "      </div>"+
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"businessName\" class=\"form-label\">Business Name:</label>" +
                        "        <input name=\"businessName\" type=\"text\" class=\"form-control\" id=\"businessName\" />" +
                        "      </div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine1\" class=\"form-label\">First Line of Address</label>" +
                        "        <textarea name=\"addressLine1\" class=\"form-control\" id=\"addressLine1\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine2\" class=\"form-label\">Second Line of Address</label>" +
                        "        <textarea name=\"addressLine2\" class=\"form-control\" id=\"addressLine2\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"addressLine3\" class=\"form-label\">Third Line of Address</label>" +
                        "        <textarea name=\"addressLine3\" class=\"form-control\" id=\"addressLine3\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"country\" class=\"form-label\">Country</label>" +
                        "        <textarea name=\"country\" class=\"form-control\" id=\"country\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"postCode\" class=\"form-label\">Postcode</label>" +
                        "        <textarea name=\"postCode\" class=\"form-control\" id=\"postCode\" rows=\"1\"></textarea>" +
                        "</div>" +
                        " <div class=\"mb-3\">" +
                        "        <label for=\"phoneNumber\" class=\"form-label\">Phone Number</label>" +
                        "        <textarea name=\"phoneNumber\" class=\"form-control\" id=\"phoneNumber\" rows=\"1\"></textarea>" +
                        "</div>" +
                        "      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>"+
                        "    </form>"+
                        "  </body>"+
                        "</html>"
        );
        out.close();

    }

}
