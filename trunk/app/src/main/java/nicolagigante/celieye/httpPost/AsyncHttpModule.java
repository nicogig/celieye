package nicolagigante.celieye.httpPost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by genio_2 on 12/08/2014.
 */
public class AsyncHttpModule extends AsyncTask<String, Void, String> {

    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
    private String response;
   // private ProgressDialog Dialog = new ProgressDialog(AsyncronoustaskAndroidExample.this);

    String uiUpdate ;

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //UI Element
        uiUpdate="Output : ";
       // Dialog.setMessage("Downloading source..");
        //Dialog.show();
    }

    // Call after onPreExecute method
    protected String doInBackground(String... urls) {
        try {

            // Call long running operations here (perform background computation)
            // NOTE: Don't call UI Element here.

            // Server url call by GET method
            HttpGet httpget = new HttpGet(urls[0]);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            Content = Client.execute(httpget, responseHandler);

        } catch (ClientProtocolException e) {
            Error = e.getMessage();
            cancel(true);
        } catch (IOException e) {
            Error = e.getMessage();
            cancel(true);
        }

        return null;
    }

    protected void onPostExecute(String response) {
        // NOTE: You can call UI Element here.

        // Close progress dialog
       // Dialog.dismiss();
        this.response=response;
        if (Error != null) {

            uiUpdate="Output : "+Error;

        } else {

            uiUpdate="Output : "+Content;

        }
    }

}
