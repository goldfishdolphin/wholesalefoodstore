import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Naureen Imran
 * @version 1.0
 * @since 2023-12-03
 * @link Please check the <a href="https://trello.com/b/mApzMSAF/food-store-project">Trello</a>  board to track the workflow.
 */


class Main {
    private static final int PORT = 8080;

    private void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/product", new ProductHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("The server is listening on port " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {

        Main main = new Main();
        main.startServer();

        MenuConsole  menuConsole = new MenuConsole();
        menuConsole.displayMenu();
    }

}