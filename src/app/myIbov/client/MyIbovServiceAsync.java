package app.myIbov.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyIbovServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
