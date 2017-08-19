package cvasseli.umich.edu.wssp_lab;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cheyenne on 8/18/2017.
 */

public class CustomExpListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    static ArrayList<Bitmap> bitMapThumbArrayList = Photos.bitMapThumbArrayList;
    private ArrayList<Users> usersArrayList = Users.usersArrayList;
    private HashMap<String, ArrayList<String>> map = Users.map;
    private GroupViewHolder groupViewHolder;

    public CustomExpListAdapter(Context context, ArrayList<Users> usersArrayList, HashMap<String, ArrayList<String>> map)
    {
        this.context = context;
        this.usersArrayList = usersArrayList;
        this.map = map;
    }


    @Override
    public int getGroupCount()
    {
        return this.usersArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.usersArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.map.get(this.usersArrayList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String userName = usersArrayList.get(groupPosition).name;

        if(convertView == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_name, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.name = (TextView) convertView.findViewById(R.id.listName);
            convertView.setTag(groupViewHolder);
            convertView.findViewById(R.id.image);
        }
        ImageView thumbImage;
        thumbImage = (ImageView) convertView.findViewById(R.id.image);
        thumbImage.setImageBitmap(bitMapThumbArrayList.get(groupPosition));
        convertView.getTag();
        groupViewHolder.name.setText(userName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        String id = "ID: " + usersArrayList.get(groupPosition).getId().toString();
        String phone = "Phone: " + usersArrayList.get(groupPosition).getPhone();
        String email = "Email: " + usersArrayList.get(groupPosition).getEmail();

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_data, parent, false);
        }
        TextView idTv = (TextView) convertView.findViewById(R.id.userID);
        TextView phoneTv = (TextView) convertView.findViewById(R.id.userPhone);
        TextView emailTv = (TextView) convertView.findViewById(R.id.userEmail);
        idTv.setText(id);
        phoneTv.setText(phone);
        emailTv.setText(email);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

    public class GroupViewHolder
    {
        TextView name;
    }
}
