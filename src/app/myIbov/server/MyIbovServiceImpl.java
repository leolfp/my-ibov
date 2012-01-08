package app.myIbov.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import app.myIbov.client.MyIbovService;

public class MyIbovServiceImpl extends RemoteServiceServlet implements MyIbovService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
