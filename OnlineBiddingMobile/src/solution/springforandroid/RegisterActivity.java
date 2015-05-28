package solution.springforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText txtPassword;
	private EditText txtRepeatPassword;
	private EditText lastSelectedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		comparePasswords();
	}
	
	private void comparePasswords(){
		txtPassword = (EditText) findViewById(R.id.passwordRegisterText);
		txtRepeatPassword = (EditText) findViewById(R.id.passwordRepeatText);
		
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
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
