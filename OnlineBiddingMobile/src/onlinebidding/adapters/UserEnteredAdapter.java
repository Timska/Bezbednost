package onlinebidding.adapters;

import java.util.List;

import onlinebidding.activities.ProfileActivity;
import onlinebidding.activities.SingleAuctionActivity;
import onlinebidding.interfaces.ListAuctions;
import onlinebidding.interfaces.ListUsers;
import onlinebidding.model.Auction;
import onlinebidding.model.User;
import solution.springforandroid.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserEnteredAdapter extends ArrayAdapter<User> {

	private Context context;
	private List<User> list;
	
	public UserEnteredAdapter(Context context, List<User> users){
		super(context, 0, users);
		this.context = context;
		this.list = users;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final User user = getItem(position);    
       final int i = position;

       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item, parent, false);
       }
       
       TextView txtItemInListView = (TextView) convertView.findViewById(R.id.txtItemInListView);
       // Populate the data into the template view using the data object
       
       txtItemInListView.setText(user.toString());
       
       
       
       
       
       // Return the completed view to render on screen
       return convertView;
   }
}
