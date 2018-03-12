package mitter.lal.adhish.mitter.networks.sockethelper;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import mitter.lal.adhish.mitter.app.MitterApplication;
import mitter.lal.adhish.mitter.helpers.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


/**
 * Created by Adhish on 11/03/18.
 */


public class SocketHelper extends WebSocketListener {

    /**
     * Messages sent and received in socket connection
     */

    public final static int INIT_TYPE = 0;

    OkHttpClient mOkHttpClient;

    Context mContext;

    public SocketHelper(Context context) {
        this.mContext = context;
    }


    public void connect_socket() {



            mOkHttpClient = new OkHttpClient.Builder()
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .build();



                String socketURL = "www.mitter.io";

                Request request = new Request.Builder()
                        .url(socketURL)
                        .header("Accept-Encoding", "identity")
                        .build();
                mOkHttpClient.newWebSocket(request, this);



    }



    public SocketHelper() {
        super();
    }


    @Override
    public void onOpen(WebSocket webSocket, Response response) {


        /**
         * The socket has opened, send the socket sendInitMessage message
         */
        sendInitMessage();


    }

    private void sendInitMessage() {

        String message = createInitMessage(mContext);

        sendMessage(message);

    }



    @Override
    public void onMessage(WebSocket webSocket, String response) {

        Logger.d("Response",response);

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

        Logger.d("socket", "got_message");

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {


        webSocket.close(1000, null);

        Logger.d("socket", "closed");


    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {

            connect_socket();
    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {

        Logger.d("socket", "closed");

    }

    public String createInitMessage(Context ctx) {

        JSONObject dataMessage = new JSONObject();
        try {

            dataMessage.put("sample_key", "sample_value");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return putHeader(INIT_TYPE, dataMessage);

    }


    public String putHeader(int type, JSONObject data) {

        JSONObject message = new JSONObject();
        try {
            message.put("header_key", "header_value");
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return message.toString();

    }



    public void closeSocket(int code, String reason){


        if(MitterApplication.webSocket != null){


            Logger.d("SocketStatus","Socket closed ");

            MitterApplication.webSocket.close(code, reason);
        }
        else
        {
            Logger.d("SocketStatus","Socket connected");
        }
    }


    public void sendMessage(String message){

        if(MitterApplication.webSocket != null){

            MitterApplication.webSocket.send(message);

        }
        else{
            Toast.makeText(mContext, "Problem connecting to server", Toast.LENGTH_LONG).show();
        }
    }

}
