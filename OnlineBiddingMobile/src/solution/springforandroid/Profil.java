package solution.springforandroid;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;






import tables.User;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profil extends Activity  implements  DownloadListener<User>  {

	EditText txtIme;
	EditText txtPrezime;
	EditText txtPassword;
	EditText txtUsername;
	EditText txtEmail;
	EditText txtDatum;
	Button btnSaveChanges;
	Button btnGoBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		txtIme = (EditText) findViewById(R.id.txtName);
		txtPrezime = (EditText) findViewById(R.id.txtSurname);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtDatum = (EditText) findViewById(R.id.txtBirthDate);
		
		Downloader<User> downloader = new Downloader<User>(User.class, this, getApplicationContext());
		String url = "https://spiritbreakers.com.mk:8443/HelloWorld/json";
		downloader.execute(url);
		
		btnSaveChanges=(Button) findViewById(R.id.btnSaveChanges);
		btnSaveChanges.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PostUserCredentials().execute("https://spiritbreakers.com.mk:8443/HelloWorld/sendmessagemap");
			
			}
		});
		btnGoBack=(Button) findViewById(R.id.btnNazad);
		btnGoBack.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//da se otvori main activity
			}
		});
	}

	public void onLoadFinished(User data) {
		// TODO Auto-generated method stub
		
		txtIme.setText(data.getFirstName());
		txtPrezime.setText(data.getLastName());
		txtUsername.setText(data.getUserName());
		txtPassword.setText(data.getPassword());
		txtEmail.setText(data.getMail());
		txtDatum.setText(data.getBirth().toString());
	}
	

private class PostUserCredentials extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("userName", txtUsername.getText().toString());
			credentials.add("password", txtPassword.getText().toString());
			credentials.add("firstName", txtIme.getText().toString());
			credentials.add("lastName", txtPrezime.getText().toString());
			credentials.add("birth", txtDatum.getText().toString());
			credentials.add("email", txtEmail.getText().toString());
			// UserCredentials credentials = new UserCredentials(txtUserName.getText().toString(), txtPassword.getText().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			String response = restTemplate.postForObject(params[0], credentials, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			//showResult(result);
		}
	}
		
	
}
