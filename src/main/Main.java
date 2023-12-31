package main;

import com.sun.net.httpserver.HttpServer;
import main.Customer.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

/**
 * @author Naureen Imran
 * @version 1.0
 * @link Please check the <a href="https://trello.com/b/mApzMSAF/food-store-project">Trello</a>  board to track the workflow.
 * @since 2023-12-03
 */


public class Main {
    private static final int PORT = 8084;

    public static void main(String[] args) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        RootHandler rh = new RootHandler();
        ProductHandler ph = new ProductHandler();
        DeleteHandler dh = new DeleteHandler();
        EditHandler eh = new EditHandler();
        InputProductHandler ih = new InputProductHandler();
        FormProcessHandler fh = new FormProcessHandler();
        EditFormHandler efh = new EditFormHandler();
        CustomersHandler ch = new CustomersHandler();
        EachCustomerHandler ech = new EachCustomerHandler();
        DeleteCustomerHandler dch = new DeleteCustomerHandler();
        EditCustomerHandler ecuh= new EditCustomerHandler();
        CustEditFormHandler cefh = new CustEditFormHandler();
        AddCustomerHandler ach = new AddCustomerHandler();
        CustomerFormProcessHandler cfph = new CustomerFormProcessHandler();

        Main main = new Main();
        main.startServer(server, rh, ph, dh, eh, ih, fh, efh, ch, ech, dch,ecuh, cefh, ach, cfph);

        MenuConsole menuConsole = new MenuConsole();
        menuConsole.displayMenu();

        CustomerMenuConsole customerMenuConsole= new CustomerMenuConsole();
        customerMenuConsole.displayMenu();
    }

    public void startServer(HttpServer server, RootHandler rh, ProductHandler ph, DeleteHandler dh,
                             EditHandler eh, InputProductHandler ih, FormProcessHandler fh, EditFormHandler efh,
                            CustomersHandler ch, EachCustomerHandler ech, DeleteCustomerHandler dch, EditCustomerHandler ecuh,
                            CustEditFormHandler cefh, AddCustomerHandler ach,CustomerFormProcessHandler cfph) {

        server.createContext("/", rh);
        server.createContext("/customers", ch);
        server.createContext("/product", ph);
        server.createContext("/delete", dh);
        server.createContext("/edit", eh);
        server.createContext("/add", ih);
        server.createContext("/formaction", fh);
        server.createContext("/editformaction", efh);
        server.createContext("/customer", ech);
        server.createContext("/deletecustomer", dch);
        server.createContext("/editcustomer", ecuh);
        server.createContext("/custeditformaction", cefh);
        server.createContext("/addcustomer", ach);
        server.createContext("/customerformaction",cfph);


        server.setExecutor(null);
        server.start();
        System.out.println("The server is listening on port " + PORT);
    }
}