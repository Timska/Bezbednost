package onlinebidding.activities;
import java.text.DateFormat;

import onlinebidding.model.User;
import solution.springforandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ProfileActivity extends Activity{//  implements  DownloadListener<User>  {

	private EditText txtName;
	private EditText txtSurname;
	private EditText txtUsername;
	private EditText txtEmail;
	private EditText txtDate;
	private User currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		getUser();
		
		setTitle(currentUser.getUserName());
		
		txtName = (EditText) findViewById(R.id.editTxtName);
		txtName.setText(currentUser.getFirstName());
		txtSurname = (EditText) findViewById(R.id.editTxtSurname);
		txtSurname.setText(currentUser.getLastName());
		txtUsername = (EditText) findViewById(R.id.editTxtUsername);
		txtUsername.setText(currentUser.getUserName());
		txtEmail = (EditText) findViewById(R.id.editTxtEmail);
		txtEmail.setText(currentUser.getMail());
		txtDate = (EditText) findViewById(R.id.editTxtDate);
		txtDate.setText(DateFormat.getDateInstance().format(currentUser.getBirth()));
	}
	
	public void startMyAuctions(View view){
		Intent intent = new Intent(this, MyAuctionsActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
	
}