package main.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class creates an input form for the login interface.
 */
public class LoginHandler implements HttpHandler {
    /**
     * This class takes input from the users to log-in
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

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
                        "    <title>Log In</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "  <body class=\"p-3 m-0 border-0 bd-example m-0 border-0\">" +
                        "    <!-- Example Code -->" +
                        "    <h1>The Food Store</h1>" +
                        "    <br />" +
                        "<form method=\"Post\" action=\"/loginformaction\">" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"username\" class=\"form-label\">Username</label>" +
                        "        <input name=\"username\"" +
                        "type=\"text\"" +
                        "          class=\"form-control\"" +
                        "          id=\"username\"" +
                        "          aria-describedby=\"usernameHelp\"" +
                        "        />" +
                        "        <div id=\"usernameHelp\" class=\"form-text\"></div>" +
                        "      </div>" +
                        "      <div class=\"mb-3\">" +
                        "        <label for=\"exampleInputPassword1\" class=\"form-label\">Password</label>" +
                        "        <input name=\"password\"" +
                        "          type=\"password\"" +
                        "          class=\"form-control\"" +
                        "          id=\"exampleInputPassword1\"" +
                        "        />" +
                        "      </div>" +
                        "      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>" +
                        "    </form>" +
                        "<a href=\"/\" class=\"btn btn-dark\"> Home </a>"+
                        "  </body>" +
                        "</html>");

        out.close();

    }
}
