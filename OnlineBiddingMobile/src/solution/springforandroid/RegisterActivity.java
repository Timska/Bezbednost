package solution.springforandroid;


import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
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
						Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		txtRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String password = txtPassword.getText().toString();
					if(!password.isEmpty() && !password.equals(txtRepeatPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), "Лозинките не се идентични", Toast.LENGTH_SHORT).show();
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
		
		if(validate()){
			new PostUserCredentials().execute("http://192.168.0.102:8080/HelloWorld/registeruser");
		}
	}
	
	private boolean validate(){
		boolean valid = true;
		if(txtFirstName.getText().toString().isEmpty()){
			Toast.makeText(this, "Внесете име", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(txtLastName.getText().toString().isEmpty()){
			Toast.makeText(this, "Внесете презиме", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(txtUsername.getText().toString().isEmpty()){
			Toast.makeText(this, "Внесете корисничко име", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		String regExp = "^(?=.*[!@#$&*])(?=.*[0-9]).{6,15}$";
		if(txtPassword.getText().toString().isEmpty()){
			Toast.makeText(this, "Внесете лозинка", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(txtRepeatPassword.getText().toString().isEmpty()){
			Toast.makeText(this, "Повторете ја лозинката", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(!txtPassword.getText().toString().matches(regExp)){
			Toast.makeText(this, "Лозинката треба да содржи барем еден "
					+ "специјален знак, една цифра и минимум должина шест", Toast.LENGTH_LONG).show();
			valid = false;
		}
		if(!txtRepeatPassword.getText().toString().equals(txtPassword.getText().toString())){
			Toast.makeText(this, "Лозинките се разликуваат", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(!txtEmail.getText().toString().matches("^([A-Za-z0-9.-_])+@([A-Za-z0-9.-_])+.([A-Za-z]{2,4})$")){
			Toast.makeText(this, "Внесете валиден маил", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(!txtBirth.getText().toString().matches("^(19[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")){
			Toast.makeText(this, "Внесете валиден датум", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		return valid;
	}
	
	private void showResult(String result) {
		if(result.equals("correct")){
			Toast.makeText(this, "Успешна регистрација", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		else{
			Toast.makeText(this, "Корисничкото име веќе постои", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class PostUserCredentials extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			User u = new User(txtUsername.getText().toString(), txtPassword.getText().toString(),
					txtFirstName.getText().toString(), txtLastName.getText().toString(), 
					txtEmail.getText().toString(), txtBirth.getText().toString(), null);
			
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
