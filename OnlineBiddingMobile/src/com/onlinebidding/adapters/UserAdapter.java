package com.onlinebidding.adapters;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlinebidding.R;
import com.onlinebidding.interfaces.ListUsers;
import com.onlinebidding.model.User;

public class UserAdapter extends ArrayAdapter<User> {

	private Context context;
	private LayoutInflater inflater;
	private List<User> list;
	
	private int[] colors = new int[] { 0xAAf6ffc8, 0xAA538d00 };

	public UserAdapter(Context context, List<User> users) {
		super(context, R.layout.user_list_item, users);
		this.context = context;
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
		final User user = list.get(position);
		holder.userName.setText("Корисничко име: " + user.getUserName());
		holder.email.setText("Е-пошта: " + user.getMail());
		
		final int i = position;
		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {				
				final ListUsers parent = (ListUsers) context;

				AlertDialog.Builder adb = new AlertDialog.Builder(context);
				adb.setTitle("Внеси кредит");
				final EditText input = new EditText(context);
				input.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
				adb.setView(input);
				adb.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								String result = input.getText().toString();
								try {
									Integer.parseInt(result);
									user.setCredit(user.getCredit() + Integer.parseInt(result));
									user.setActive(true);
									parent.activate(i);
								} catch (Exception e) {
									Toast.makeText(context, "Не е внесен валиден кредит", Toast.LENGTH_SHORT).show();
								}
							}
						});

				adb.setNegativeButton("Cancel", null);
				adb.create().show();
			}
		});
		
		int colorPos = position % colors.length;
	    convertView.setBackgroundColor(colors[colorPos]);

		return convertView;
	}
}
