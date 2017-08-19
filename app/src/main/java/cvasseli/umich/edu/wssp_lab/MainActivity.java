package cvasseli.umich.edu.wssp_lab;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    HashMap<String, ArrayList<String>> userData = new HashMap<>();
    CustomExpListAdapter listAdapter = new CustomExpListAdapter(this, Users.usersArrayList, userData);
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.listViewExp);

        AsyncTask<Void, Void,  Boolean> task = new getList();
        try {
            task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                if(parent.isGroupExpanded(groupPosition))
                {
                    parent.collapseGroup(groupPosition);
                }
                else
                {
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });
    }

    private class getList extends AsyncTask<Void, Void, Boolean>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(MainActivity.this, "", "Fetching User Data From Server...");
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                JsonParser jsonParser = new JsonParser();
                URL userUrl = new URL("https://jsonplaceholder.typicode.com/users");
                URLConnection uc = userUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                JsonElement jsonElement = jsonParser.parse(reader);
                JSONArray jsonArray = new JSONArray(jsonElement.toString());
                Users.populateUserArrayList(jsonArray);
                Users.populateHashMap();
                Photos.populatePhotoArrayList(Photos.getJsonPhotos());
                Photos.getThumbImages();

                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (MalformedURLException f)
            {
                f.printStackTrace();
            }
            catch (IOException g)
            {
                g.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            dialog.dismiss();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
