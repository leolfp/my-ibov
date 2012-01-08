package app.myIbov.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MyIbovService")
public interface MyIbovService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use MyIbovService.App.getInstance() to access static instance of MyIbovServiceAsync
     */
    public static class App {
        private static MyIbovServiceAsync ourInstance = GWT.create(MyIbovService.class);

        public static synchronized MyIbovServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
