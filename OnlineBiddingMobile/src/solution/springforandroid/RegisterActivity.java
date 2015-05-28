package solution.springforandroid;


import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tables.User;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText txtFirstName;
	private EditText txtLastName;
	private EditText txtUsername;
	private EditText txtEmail;
	private EditText txtBirth;
	private EditText txtPassword;
	private EditText txtRepeatPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		init();
		
		comparePasswords();
	}
	
	private void init(){
		txtPassword = (EditText) findViewById(R.id.passwordRegisterText);
		txtRepeatPassword = (EditText) findViewById(R.id.passwordRepeatText);
		txtBirth = (EditText) findViewById(R.id.birthText);
		txtEmail = (EditText) findViewById(R.id.emailText);
		txtFirstName = (EditText) findViewById(R.id.firstNameText);
		txtLastName = (EditText) findViewById(R.id.lastNameText);
		txtUsername = (EditText) findViewById(R.id.usernameRegisterText);
	}
	
	private void comparePasswords(){
		
		txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String password = txtRepeatPassword.getText().toString();
					if(!password.isEmpty() && !password.equals(txtPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();;
					}
				}
			}
		});
		
		txtRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String password = txtPassword.getText().toString();
					if(!password.isEmpty() && !password.equals(txtRepeatPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();;
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void registerNewUser(View view){
		new PostUserCredentials().execute("http://192.168.0.106:8080/HelloWorld/sendmessagebject");
	}
	
	private void showResult(String result) {
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		if(result.equals("correct")){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}
	
	private class PostUserCredentials extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			User u = new User(txtFirstName.getText().toString(), txtLastName.getText().toString(),
					txtUsername.getText().toString(), txtPassword.getText().toString(), 
					txtEmail.getText().toString(), txtBirth.getText().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			String response = restTemplate.postForObject(params[0], u, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			showResult(result);
		}
	}
}
