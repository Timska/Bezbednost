package solution.springforandroid;
import java.text.DateFormat;

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

public class ProfileActivity extends Activity{//  implements  DownloadListener<User>  {

	private EditText txtName;
	private EditText txtSurname;
	private EditText txtUsername;
	private EditText txtEmail;
	private EditText txtDate;
	private Button btnMyAuctions;
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
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
}
