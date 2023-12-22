import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class ProductHandler implements HttpHandler {
        public void handle(HttpExchange he) throws IOException {
            he.sendResponseHeaders(200, 0);
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(he.getResponseBody()));

            String request = he.getRequestURI().getQuery();
            HashMap<String, String> map = Util.requestStringToMap(request);
            String response = "failed";
            int id = Integer.parseInt(map.get("id"));
            FoodProductDAO foodProducts = new FoodProductDAO();
            FoodProduct product = null;
            try {
                product = foodProducts.selectProduct(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
                    "    <title>Product</title>"+
                    "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>"+
                    "  </head>"+
                    "  <body"+
                    "    class=\"p-3 m-0 border-0 bd-example m-0 border-0\""+
                    "    style=\"background-color: pink\""+
                    "  >"+
                    "    <div class=\"card\" style=\"width: 18rem\">"+
                    "      <img"+
                    "        src=\"https://images.pexels.com/photos/161559/background-bitter-breakfast-bright-161559.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\""+
                    "        class=\"card-img-top\""+
                    "        alt=\"fruit\""+
                    "      />"+
                    "      <div class=\"card-body\">");
            out.write(product.toProductHTMLString());

            out.write(
                    "      </div>"+
                    "    </div>"+
                    "  </body>"+
                    "</html>"
            );
            out.close();
        }
    }
//
////            List<FoodProduct> allProducts = foodProducts.listProduct();
//
////            out.write(
////                    "<html>" +
////                            "<head> <title>The Food Store</title> " +
////                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
////                            +
////                            "</head>" +
////                            "<body>" +
////                            "<h1> All Products !</h1>" +
////                            "<table class=\"table\">" +
////                            "<thead>" +
////                            "  <tr>" +
////                            "    <th>ID</th>" +
////                            "    <th>SKU</th>" +
////                            "    <th>Description</th>" +
////                            "    <th>Category</th>" +
////                            "    <th>Price</th>" +
////                            "  </tr>" +
////                            "</thead>" +
////                            "<tbody>");
////
////            for (FoodProduct p : allProducts) {
////                out.write(p.toHTMLString());
////            }
////            out.write(
////                    "</tbody>" +
////                            "</table>" +
////                            "</body>" +
////                            "</html>"
////            );
////
//            out.close();
//
//        }
//    }
//}
