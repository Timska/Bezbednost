package com.onlinebidding.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onlinebidding.R;
import com.onlinebidding.model.User;

public class RegisterActivity extends Activity {

	private EditText txtFirstName;
	private EditText txtLastName;
	private EditText txtUsername;
	private EditText txtEmail;
	private TextView txtBirth;
	private DatePickerDialog.OnDateSetListener date;
	private Calendar myCalendar;
	private EditText txtPassword;
	private EditText txtRepeatPassword;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		setDatePicker();
		
		init();
		
		comparePasswords();
	}
	
	private void init(){
		txtPassword = (EditText) findViewById(R.id.passwordRegisterText);
		txtRepeatPassword = (EditText) findViewById(R.id.passwordRepeatText);
		txtBirth = (TextView) findViewById(R.id.birthText);
		
		txtBirth.setOnClickListener(new View.OnClickListener() {

	        @SuppressWarnings("deprecation")
			public void onClick(View v) {
	        	DatePickerDialog dpd = new DatePickerDialog(RegisterActivity.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH));
	        	Date date = new Date();
	        	date.setYear(new Date().getYear() - 18);
				dpd.getDatePicker().setMaxDate(date.getTime());
				dpd.show();
	        }
	    });
		
		txtEmail = (EditText) findViewById(R.id.emailText);
		txtFirstName = (EditText) findViewById(R.id.firstNameText);
		txtLastName = (EditText) findViewById(R.id.lastNameText);
		txtUsername = (EditText) findViewById(R.id.usernameRegisterText);
	}
	
	private void setDatePicker(){
		myCalendar = Calendar.getInstance();

		date = new DatePickerDialog.OnDateSetListener() {

		    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

	            updateLabel();
		    }

		};
	}
	
	private void updateLabel() {

	    String myFormat = "yyyy-MM-dd"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    
	    txtBirth.setText(sdf.format(myCalendar.getTime()));
	    
	}
	
	private void comparePasswords(){
		txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String password = txtRepeatPassword.getText().toString();
					if(!password.isEmpty() && !password.equals(txtPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), "Лозинките не се идентични", Toast.LENGTH_SHORT).show();
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

	public void registerNewUser(View view){
		
		if(validate()){
			new PostUserCredentials().execute(getResources().getString(R.string.url_address)+"/registeruser");
		}
	}
	
	private boolean validate(){
		boolean valid = true;
		if(txtFirstName.getText().toString().trim().isEmpty()){
			Toast.makeText(this, "Внесете име", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if(txtLastName.getText().toString().trim().isEmpty()){
			Toast.makeText(this, "Внесете презиме", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if(txtUsername.getText().toString().trim().isEmpty()){
			Toast.makeText(this, "Внесете корисничко име", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if(!txtPassword.getText().toString().matches("^(?=.*[!@#$&*])(?=.*[0-9]).{6,15}$")){
			Toast.makeText(this, "Лозинката треба да содржи барем еден "
					+ "специјален знак, една цифра и минимум должина шест", Toast.LENGTH_LONG).show();
			valid = false;
		}
		else if(!txtRepeatPassword.getText().toString().equals(txtPassword.getText().toString())){
			Toast.makeText(this, "Лозинките се разликуваат", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if(!txtEmail.getText().toString().matches("^([A-Za-z0-9.-_])+@([A-Za-z0-9.-_])+.([A-Za-z]{2,4})$")){
			Toast.makeText(this, "Внесете валиден мejл", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if(txtBirth.getText().toString().equals(getResources().getString(R.string.birth_hint))) {
			Toast.makeText(this, "Внесете валиден датум", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		return valid;
	}
	
	private void showResult(String result) {
		if(result.equals("correct")){
			Toast.makeText(this, "Успешна регистрација", Toast.LENGTH_SHORT).show();
			finish();
		}
		else{
			Toast.makeText(this, "Корисничкото име веќе постои", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();
    }
	
	 private void dismissProgressDialog() {
		 if (dialog != null && dialog.isShowing()) {
			 dialog.dismiss();
		 }
	 }
	 
	 @Override
	 protected void onDestroy() {
		 dismissProgressDialog();
		 super.onDestroy();
	 }

	private class PostUserCredentials extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}
		
		@Override
		protected String doInBackground(String... params) {
			User u = new User(txtUsername.getText().toString(), txtPassword.getText().toString(),
					txtFirstName.getText().toString(), txtLastName.getText().toString(), 
					txtEmail.getText().toString(), txtBirth.getText().toString(), false, 0);
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(RegisterActivity.this));
			String response = restTemplate.postForObject(params[0], u, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (!isFinishing()) {
				dismissProgressDialog();
			}
			showResult(result);
		}
	}
}
