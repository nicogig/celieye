package nicolagigante.celieye.jsonService;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import nicolagigante.celieye.R;

/**
 * Created by genio_2 on 12/07/2014.
 */
public class ParseJSON {
    private String uri;
    private JSONArray response;
    public ParseJSON(String uri){
        this.uri=uri;
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //ReadJson readJson = new ReadJson(uri);
        try {
            JSONArray jsonArray = new JSONArray(uri);
            this.response=jsonArray;
            Log.i(ParseJSON.class.getName(),
                    "Number of entries " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i(ParseJSON.class.getName(), jsonObject.getString("text"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public JSONArray getJSONArray(){
        return this.response;
    }
}
