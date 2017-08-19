package cvasseli.umich.edu.wssp_lab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cheyenne on 8/19/2017.
 */

public class Photos {
    static String jsonPhotoUrl = "https://jsonplaceholder.typicode.com/photos";
    static ArrayList<Photos> photosArrayList = new ArrayList<>();
    static ArrayList<Bitmap> bitMapThumbArrayList = new ArrayList<>();
    static HashMap<String, Bitmap> bitMapMapThumb = new HashMap<>();

    Integer id;
    String title;
    URL url;
    URL thumbnailUrl;


    public Photos(Integer id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public static JSONArray getJsonPhotos() throws IOException, JSONException {
        JsonParser jsonParser = new JsonParser();
        URL userUrl = new URL(jsonPhotoUrl);
        URLConnection uc = userUrl.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        JsonElement jsonElement = jsonParser.parse(reader);
        JSONArray jsonArray = new JSONArray(jsonElement.toString());

        return jsonArray;
    }

    public static ArrayList<Photos> populatePhotoArrayList(JSONArray jsonArray) throws JSONException, MalformedURLException {

        for (int i = 0; i < jsonArray.length() && i < Users.usersArrayList.size(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Photos pic = new Photos(jsonObject.getInt("id"), jsonObject.getString("title"));
            URL fullUrl = new URL(jsonObject.getString("url"));
            URL thumbUrl = new URL(jsonObject.getString("thumbnailUrl"));
            pic.url = fullUrl;
            pic.thumbnailUrl = thumbUrl;
            photosArrayList.add(pic);
        }

        return photosArrayList;
    }

    public static HashMap<String, Bitmap> getThumbImages() throws IOException {
        for(Photos p : photosArrayList)
        {
            Bitmap bMap = BitmapFactory.decodeStream(p.thumbnailUrl.openConnection().getInputStream());
            bitMapThumbArrayList.add(bMap);
            bitMapMapThumb.put(p.id.toString(), bMap);
        }

        return bitMapMapThumb;
    }
}
