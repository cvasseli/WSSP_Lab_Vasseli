package cvasseli.umich.edu.wssp_lab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Users
{
    static ArrayList<Users> usersArrayList = new ArrayList<Users>();
    static ArrayList<String> userName = new ArrayList<>();
    static HashMap<String, ArrayList<String>> map = new HashMap<>();
    Integer id;
    String name;
    String email;
    String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Users(Integer id, String name, String email, String phone)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static void populateUserArrayList(JSONArray jsonArray) throws JSONException {
        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Users user = new Users(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("email"), jsonObject.getString("phone"));
            usersArrayList.add(user);
        }
    }

    public static void populateHashMap()
    {
        ArrayList<Users> usersArrayList = Users.usersArrayList;
        for(int a = 0; a < usersArrayList.size(); a++)
        {
            userName.add(usersArrayList.get(a).name);
        }
        for(int b = 0; b < usersArrayList.size(); b++)
        {
            ArrayList<String> data = new ArrayList<>();
            data.add("ID: " + usersArrayList.get(b).id.toString());
            data.add("Phone: " + usersArrayList.get(b).phone);
            data.add("Email: "+ usersArrayList.get(b).email);
            map.put(userName.get(b), data);
        }
    }

    public class Address extends android.location.Address
    {
        Integer id;
        String street;
        String suite;
        String city;
        String zipcode;

        /**
         * Constructs a new Address object set to the given Locale and with all
         * other fields initialized to null or false.
         *
         * @param locale
         */
        public Address(Locale locale)
        {
            super(locale);
        }
    }
}
