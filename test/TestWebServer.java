package test.java;

import com.sun.net.httpserver.HttpServer;
import main.Customer.*;
import main.*;
import main.Product.*;
import main.User.LoginFormHandler;
import main.User.LoginHandler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This class tests different paths at http server  using JUnit and mockito.
 */
public class TestWebServer {
    @Test
    public void httpServer() {
        HttpServer httpServer = mock(HttpServer.class);
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
        EditCustomerHandler ecuh = new EditCustomerHandler();
        CustEditFormHandler cefh = new CustEditFormHandler();
        AddCustomerHandler ach = new AddCustomerHandler();
        LoginHandler lh = new LoginHandler();
        CustomerFormProcessHandler cfph = new CustomerFormProcessHandler();
        LoginFormHandler lfh = new LoginFormHandler();
        Main main = new Main();
        main.startServer(httpServer, rh, ph, dh, eh, ih, fh, efh, ch, ech, dch, ecuh, cefh, ach, cfph, lh, lfh);

        verify(httpServer).createContext("/", rh);
        verify(httpServer).createContext("/product", ph);
        verify(httpServer).createContext("/delete", dh);
        verify(httpServer).createContext("/edit", eh);
        verify(httpServer).createContext("/add", ih);
        verify(httpServer).createContext("/formaction", fh);
        verify(httpServer).createContext("/editformaction", efh);
        verify(httpServer).createContext("/customers", ch);
        verify(httpServer).createContext("/customer", ech);
        verify(httpServer).createContext("/deletecustomer", dch);
        verify(httpServer).createContext("/editcustomer", ecuh);
        verify(httpServer).createContext("/addcustomer", ach);
        verify(httpServer).createContext("/customerformaction", cfph);
        verify(httpServer).createContext("/login", lh);
        verify(httpServer).createContext("/loginformaction", lfh);
    }
}
