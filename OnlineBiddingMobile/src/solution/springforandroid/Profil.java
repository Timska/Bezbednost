package solution.springforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class Profil extends Activity {

	EditText txtIme;
	EditText txtPrezime;
	EditText txtPassword;
	EditText txtUsername;
	EditText txtEmail;
	Button saveChanges;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		txtIme = (EditText) findViewById(R.id.txtName);
		txtPrezime = (EditText) findViewById(R.id.txtSurname);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		
		
	}
}
