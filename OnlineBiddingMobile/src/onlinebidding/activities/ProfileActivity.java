package onlinebidding.activities;

import java.text.DateFormat;

import onlinebidding.model.User;
import solution.springforandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends Activity{

	private EditText txtName;
	private EditText txtSurname;
	private EditText txtUsername;
	private EditText txtEmail;
	private EditText txtDate;
	private User currentUser;
	private EditText txtCredit;
	private TextView txtActivated;
	
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
		txtCredit = (EditText) findViewById(R.id.editTxtCredit);
		txtCredit.setText(String.valueOf(currentUser.getCredit()) + " ден.");
		txtActivated = (TextView) findViewById(R.id.txtActivated);
		if(currentUser.isActive()){
			txtActivated.setText("Профилот е активиран");
		}
		else{
			txtActivated.setText("Профилот не е активиран");
		}
	}
	
	public void startWonAuctions(View view){
		Intent intent = new Intent(this, WonAuctionsActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
}
