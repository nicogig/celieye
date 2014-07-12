package nicolagigante.celieye.jsonService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by genio_2 on 12/07/2014.
 */
public class WriteJson {
    public WriteJson() {
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
