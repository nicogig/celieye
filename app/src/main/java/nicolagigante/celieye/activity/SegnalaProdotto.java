package nicolagigante.celieye.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import nicolagigante.celieye.R;
import nicolagigante.celieye.httpPost.AsyncHttpModule;

public class SegnalaProdotto extends Activity {
    private String barcode;
    private String description;
    private String brandname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnala_prodotto);
        Intent i = getIntent();
        barcode = i.getStringExtra("barcode");
        EditText description = (EditText)findViewById(R.id.description);
        EditText brandname = (EditText)findViewById(R.id.textfield);
        this.description = description.toString();
        this.brandname = brandname.toString();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.segnala_prodotto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Inviarichiesta(View view){
        // Server Request URL
      //  String serverURL = "http://www.celieye.tk/prodottidavalidare.php";
        // Create Object and call AsyncTask execute Method
      //  new AsyncHttpModule().execute(serverURL);

        // Server Request URL
        String serverURL = "http://www.celieye.tk/prodottidavalidare.php?id_barcode="+barcode+"&description="+description+"&brand_name="+brandname;

        // Create Object and call AsyncTask execute Method
        new LongOperation().execute(serverURL);
    }
    // Class with extends AsyncTask class
    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SegnalaProdotto.this);

        TextView uiUpdate = (TextView) findViewById(R.id.output);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            uiUpdate.setText("Output : ");
            Dialog.setMessage("Downloading source..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
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

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                uiUpdate.setText("Output : "+Error);

            } else {

                uiUpdate.setText("Output : "+Content);

            }
        }

    }
}
