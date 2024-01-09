package main;

import com.sun.net.httpserver.HttpServer;
import main.Customer.*;
import main.Product.*;
import main.ShoppingBasket.BasketHandler;
import main.ShoppingBasket.BasketViewHandler;
import main.ShoppingBasket.CheckOutHandler;
import main.ShoppingBasket.ClearBasketHandler;
import main.Stock.ExpiryHandler;
import main.Stock.StockHandler;
import main.User.LoginFormHandler;
import main.User.LoginHandler;
import main.User.LogoutHandler;
import main.User.SessionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

/**
 * @author Naureen Imran
 * @version 1.0
 * @link Please check the <a href="https://trello.com/b/mApzMSAF/food-store-project">Trello</a>
 * board to track the workflow.
 * @since 2023-12-03
 */

/**
 * This the main class to manage the food store.
 * It consists of a Menu Console, Session Manager and a Http Server
 */
public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        RootHandler rh = new RootHandler();
        ProductHandler ph = new ProductHandler();
        DeleteHandler dh = new DeleteHandler();
        EditHandler eh = new EditHandler();
        InputProductHandler ih = new InputProductHandler();
        FormProcessHandler fph = new FormProcessHandler();
        EditFormHandler efh = new EditFormHandler();
        CustomersHandler ch = new CustomersHandler();
        EachCustomerHandler ech = new EachCustomerHandler();
        DeleteCustomerHandler dch = new DeleteCustomerHandler();
        EditCustomerHandler ecuh = new EditCustomerHandler();
        CustEditFormHandler cefh = new CustEditFormHandler();
        AddCustomerHandler ach = new AddCustomerHandler();
        CustomerFormProcessHandler cfph = new CustomerFormProcessHandler();
        LoginHandler lh = new LoginHandler();
        LoginFormHandler lfh = new LoginFormHandler();
        LogoutHandler loh = new LogoutHandler();
        SearchHandler sh = new SearchHandler();
        FilterHandler fh = new FilterHandler();
        StockHandler sth = new StockHandler();
        ExpiryHandler exh = new ExpiryHandler();
        BasketHandler bh = new BasketHandler();
        BasketViewHandler bvh = new BasketViewHandler();
        CheckOutHandler coh = new CheckOutHandler();
        ClearBasketHandler cbh = new ClearBasketHandler();


        Main main = new Main();
        main.startServer(server, rh, ph, dh, eh, ih, fph, efh, ch, ech, dch, ecuh, cefh, ach, cfph, lh, lfh, loh, sh,
                fh, sth, exh, bh, bvh, coh, cbh);

        MenuConsole menuConsole = new MenuConsole();
        menuConsole.displayMenu();

        SessionManager sessionManager = new SessionManager();


    }

    public void startServer(HttpServer server, RootHandler rh, ProductHandler ph, DeleteHandler dh,
                            EditHandler eh, InputProductHandler ih, FormProcessHandler fph, EditFormHandler efh,
                            CustomersHandler ch, EachCustomerHandler ech, DeleteCustomerHandler dch,
                            EditCustomerHandler ecuh, CustEditFormHandler cefh, AddCustomerHandler ach,
                            CustomerFormProcessHandler cfph, LoginHandler lh,
                            LoginFormHandler lfh, LogoutHandler loh, SearchHandler sh, FilterHandler fh,
                            StockHandler sth, ExpiryHandler exh, BasketHandler bh,
                            BasketViewHandler bvh, CheckOutHandler coh, ClearBasketHandler cbh) {

        server.createContext("/", rh);
        server.createContext("/customers", ch);
        server.createContext("/product", ph);
        server.createContext("/delete", dh);
        server.createContext("/edit", eh);
        server.createContext("/add", ih);
        server.createContext("/formaction", fph);
        server.createContext("/editformaction", efh);
        server.createContext("/customer", ech);
        server.createContext("/deletecustomer", dch);
        server.createContext("/editcustomer", ecuh);
        server.createContext("/custeditformaction", cefh);
        server.createContext("/addcustomer", ach);
        server.createContext("/customerformaction", cfph);
        server.createContext("/login", lh);
        server.createContext("/loginformaction", lfh);
        server.createContext("/logout", loh);
        server.createContext("/search", sh);
        server.createContext("/filter", fh);
        server.createContext("/stock", sth);
        server.createContext("/status", exh);
        server.createContext("/basket", bh);
        server.createContext("/viewbasket", bvh);
        server.createContext("/checkout", coh);
        server.createContext("/clear", cbh);


        server.setExecutor(null);
        server.start();
        System.out.println("The server is listening on port " + PORT);
    }
}