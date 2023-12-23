
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

    public class InputProductHandler implements HttpHandler {
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
                            "    <title>Add Product</title>"+
                            "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>"+
                            "  </head>"+
                            "  <body class=\"p-3 m-0 border-0 m-0 border-0\">"+
                            "    <h1 class=\"text-center\">The Food Store</h1>"+
                            "    <h3 class=\"text-center\">Insert Product Details</h3>"+
                            "<form method=\"Post\" action=\"/formaction\">"+
                            "<div class=\"mb-3\">"+
                            "        <label for=\"id\" class=\"form-label\">Product ID:</label>"+
                            "        <input name=\"id\" type=\"number\" class=\"form-control\" id=\"id\" />"+
                            "      </div>"+
                            "      <div class=\"mb-3\">"+
                            "        <label for=\"SKU\" class=\"form-label\">SKU:</label>"+
                            "        <input name=\"SKU\" type=\"text\" class=\"form-control\" id=\"SKU\" />"+
                            "      </div>"+
                            "      <div class=\"mb-3\">"+
                            "        <label for=\"description\" class=\"form-label\">Description:</label>"+
                            "        <textarea name=\"description\" class=\"form-control\" id=\"description\" rows=\"2\"></textarea>"+
                            "</div>"+
                            "<div>"+
                            "        <select name=\"category\" class=\"form-select\" aria-label=\"Default category menu\">"+
                            "          <option selected>Choose Category</option>"+
                            "          <option value=\"fruit\">Fruit</option>"+
                            "          <option value=\"vegetable\">Vegetable</option>"+
                            "          <option value=\"rice\">Rice</option>"+
                            "          <option value=\"cold drink\">Cold Drinks</option>"+
                            "          <option value=\"frozen food\">Frozen Food</option>"+
                            "          <option value=\"snack\">Snack</option>"+
                            "        </select>"+
                            "      </div>"+

                            "<div class=\"mb-3 mt-3\">"+
                            "        <label for=\"price\" class=\"form-label\">Price:</label>"+
                            "        <input name=\"price\" type=\"number\" class=\"form-control\" id=\"price\" />"+
                            "      </div>"+
                            "      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>"+
                            "    </form>"+
                            "  </body>"+
                            "</html>"






//                    "<html>" +
//                            "<head> <title>My new Web Page</title> </head>" +
//                            "<body>" +
//                            "<form method=\"Post\" action=\"/formaction\">" +
//                            "<label>Number 1</label>" +
//                            "<input name=\"num1\"> <br \\> " +
//                            "<label>Number 2</label>" +
//                            "<input name=\"num2\">  " +
//                            "<input type=\"submit\" value=\"Submit\">  " +
//                            "</form>" +
//                            "<h1> Page2!</h1>" +
//                            "<a href=\"/\"> click here </a" +
//                            "</body>" +
//                            "</html>");
            );
            out.close();

        }

    }
