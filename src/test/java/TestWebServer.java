package test.java;

import static org.mockito.Mockito.*;
import com.sun.net.httpserver.HttpServer;
import main.*;
import org.junit.jupiter.api.Test;

/**
 * This class tests different paths at http server  using JUnit and mockito.
 */
public class TestWebServer {
    @Test
    public void httpServer(){
        HttpServer httpServer =mock(HttpServer.class);
        RootHandler rh = new RootHandler();
        ProductHandler ph = new ProductHandler();
        DeleteHandler dh = new DeleteHandler();
        EditHandler eh = new EditHandler();
        InputProductHandler ih = new InputProductHandler();
        FormProcessHandler fh = new FormProcessHandler();
        EditFormHandler efh = new EditFormHandler();
        Main main = new Main();
        main.startServer(httpServer, rh, ph, dh, eh, ih, fh, efh);

        verify(httpServer).createContext("/", rh);
        verify(httpServer).createContext("/product", ph);
        verify(httpServer).createContext("/delete", dh);
        verify(httpServer).createContext("/edit", eh);
        verify(httpServer).createContext("/add", ih);
        verify(httpServer).createContext("/formaction", fh);
        verify(httpServer).createContext("/editformaction", efh);
    }
}
