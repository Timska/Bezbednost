package solution.springforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	private EditText txtPassword;
	private EditText txtRepeatPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		comparePasswords();
	}
	
	private void comparePasswords(){
		txtPassword = (EditText) findViewById(R.id.passwordRegisterText);
		txtRepeatPassword = (EditText) findViewById(R.id.passwordRepeatText);
		
		
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
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
