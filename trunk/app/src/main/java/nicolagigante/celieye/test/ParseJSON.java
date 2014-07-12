package nicolagigante.celieye.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
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
import nicolagigante.celieye.jsonService.ReadJson;

public class ParseJSON extends Activity {
private String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_json);

        ReadJson readJson=new ReadJson(uri);
        String response=readJson.getRead();
        nicolagigante.celieye.jsonService.ParseJSON parserJson=new nicolagigante.celieye.jsonService.ParseJSON(response);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parse_json, menu);
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

    public void writeJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", "Jack Hack");
            object.put("score", new Integer(200));
            object.put("current", new Double(152.32));
            object.put("nickname", "Hacker");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }
}
