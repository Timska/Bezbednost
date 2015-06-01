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

public class UserAdapter extends ArrayAdapter<User> {

	private Context context;
	private List<User> list;
	
	public UserAdapter(Context context, List<User> users){
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
       txtItemInListView.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final ListUsers parent = (ListUsers) context;
			
			AlertDialog.Builder adb = new AlertDialog.Builder(context);
			adb.setTitle("Внеси кредит");
			final EditText input = new EditText(context);
			adb.setView(input);
			adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String result = input.getText().toString();
					try{
						Integer.parseInt(result);
						user.setCredit(user.getCredit() + Integer.parseInt(result));
						user.setActive(true);
						parent.activate(i);
					}
					catch(Exception e){
						Toast.makeText(context, "Не е внесен валиден кредит", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			adb.setNegativeButton("Cancel", null);
			adb.create().show();
		}
	});
       
       
       
       
       // Return the completed view to render on screen
       return convertView;
   }
}
