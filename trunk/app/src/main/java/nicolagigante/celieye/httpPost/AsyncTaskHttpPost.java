package nicolagigante.celieye.httpPost;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nicolagigante.celieye.R;
import nicolagigante.celieye.model.ApplicationProdotti;
import nicolagigante.celieye.model.Prodotto;

public class AsyncTaskHttpPost extends Activity {
    EditText etResponse;
    TextView isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_http_post);
        etResponse = (EditText) findViewById(R.id.etResponse);
        isConnected = (TextView) findViewById(R.id.isConnected);
        Intent i=getIntent();
        String url=i.getStringExtra("url");
        if(isConnected()){
            isConnected.setBackgroundColor(0xFF00CC00);
            isConnected.setText("Sei connesso.   " + url);
        }
        else{
            isConnected.setText("Non sei connesso.");
        }

        new HttpAsyncTask().execute(url);

    }
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Non funge :/";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }
private static String convertInputStreamToString(InputStream inputStream) throws IOException{
    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
    String line = "";
    String result = "";
    while((line = bufferedReader.readLine()) != null)
        result += line;

    inputStream.close();
    return result;


}

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Ricevuto!", Toast.LENGTH_LONG).show();
            etResponse.setText(result);
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                JSONArray prodottoArray = json.getJSONArray("prodotto"); //da cambiare con chiamata ricevuta da json
                Prodotto prodotto = new Prodotto();
                prodotto.setBarcode(prodottoArray.getJSONObject(0).getString("barcode"));
                prodotto.setDescrizioneprodotto(prodottoArray.getJSONObject(0).getString("descrizione"));
                prodotto.setNomeprodotto(prodottoArray.getJSONObject(0).getString("nome"));
                ApplicationProdotti applicationProdotti = (ApplicationProdotti) getApplication();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert json != null;
            try {
                etResponse.setText(json.toString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
