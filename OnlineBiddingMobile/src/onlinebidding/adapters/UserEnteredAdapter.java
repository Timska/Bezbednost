package onlinebidding.adapters;

import java.util.List;

import onlinebidding.model.User;
import solution.springforandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserEnteredAdapter extends ArrayAdapter<User> {

	private LayoutInflater inflater;
	private List<User> list;
	
	private int[] colors = new int[] { 0xAAf6ffc8, 0xAA538d00 };
	
	public UserEnteredAdapter(Context context, List<User> users){
		super(context, R.layout.user_list_item, users);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = users;
	}
	
	private class UserHolder {
		private RelativeLayout itemLayout;
		private TextView userName;
		private TextView email;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		UserHolder holder = null;
		if (convertView == null) {
			holder = new UserHolder();
			holder.itemLayout = (RelativeLayout) inflater.inflate(R.layout.user_list_item, parent, false);
			holder.userName = (TextView) holder.itemLayout.findViewById(R.id.txt_UserName);
			holder.email = (TextView) holder.itemLayout.findViewById(R.id.txt_Email);
			convertView = holder.itemLayout;
			convertView.setTag(holder);
		}
		
		holder = (UserHolder) convertView.getTag();
		User user = list.get(position);
		holder.userName.setText("Корисничко име: " + user.getUserName());
		holder.email.setText("Е-пошта: " + user.getMail());
		
		int colorPos = position % colors.length;
	    convertView.setBackgroundColor(colors[colorPos]);
		
		return convertView;
   }
}
