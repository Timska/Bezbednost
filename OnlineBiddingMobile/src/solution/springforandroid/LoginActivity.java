package solution.springforandroid;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private EditText txtUsername;
	private EditText txtPassword;
	private static final String unexistingUser = "1";
	private static final String wrongPassword = "2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		txtUsername = (EditText) findViewById(R.id.usernameText);
		txtPassword = (EditText) findViewById(R.id.passwordText);
		btnLogin = (Button) findViewById(R.id.login_button);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//new PostUserCredentials().execute("http://192.168.0.106:8080/HelloWorld/sendmessagemap");
				new PostUserCredentials().execute("http://192.168.0.102:8080/HelloWorld/checkforlogin");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	
	public void startRegisterProcess(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	private void showResult(String result) {
		if(result.equals("correct")){
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		else{
			if(result.equals(unexistingUser)){
				Toast.makeText(this, "Корисничкото име не постои", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals(wrongPassword)){
				Toast.makeText(this, "Внесена погрешна лозинка", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class PostUserCredentials extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("userName", txtUsername.getText().toString());
			credentials.add("password", txtPassword.getText().toString());
			// UserCredentials credentials = new UserCredentials(txtUserName.getText().toString(), txtPassword.getText().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			String response = restTemplate.postForObject(params[0], credentials, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			showResult(result);
		}
	}
}
