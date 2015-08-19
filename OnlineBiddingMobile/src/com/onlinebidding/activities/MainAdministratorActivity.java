package com.onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.onlinebidding.R;
import com.onlinebidding.adapters.UserAdapter;
import com.onlinebidding.interfaces.DialogShower;
import com.onlinebidding.interfaces.ListUsers;
import com.onlinebidding.model.User;
import com.onlinebidding.server.DownloadListener;
import com.onlinebidding.server.Downloader;

public class MainAdministratorActivity extends Activity implements DownloadListener<User[]>, ListUsers, DialogShower {
	
	private ListView allUsersView;
	private ArrayAdapter<User> allUsersAdapter;
	private List<User> listUsers;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_administrator);
		
		getAllUsers();
	}
	
	private void getAllUsers(){
		Downloader<User[]> downloader = new Downloader<User[]>(User[].class, this, this, this);
		downloader.execute(getResources().getString(R.string.url_address)+"/getallusers");
	}

	public void onLoadFinished(User[] data) {
		setData(data);
	}
	
	public void logoutAdmin(View view) {
		SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("adminName", null);
		editor.commit();
		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void setData(User[] data){
		listUsers = new ArrayList<User>();
		if(data != null){
			listUsers = Arrays.asList(data);
		}
		
		initAuctionsView();
	}
	
	private void initAuctionsView(){
		allUsersView = (ListView) findViewById(R.id.users_list);
		allUsersAdapter = new UserAdapter(this, listUsers);
		allUsersView.setAdapter(allUsersAdapter);
	}

	public void activate(int position) {
		new PostUser().execute(getResources().getString(R.string.url_address)+"/updateuser", String.valueOf(position));
	}
	
	public void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(MainAdministratorActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();
    }
	
	 public void dismissProgressDialog() {
		 if (dialog != null && dialog.isShowing()) {
			 dialog.dismiss();
		 }
	 }
	 
	 @Override
	 protected void onDestroy() {
		 dismissProgressDialog();
		 super.onDestroy();
	 }
	
	private class PostUser extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}
		
		@Override
		protected String doInBackground(String... params) {
			User u = listUsers.get(Integer.parseInt(params[1]));
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(MainAdministratorActivity.this));
			String response = restTemplate.postForObject(params[0], u, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (!isFinishing()) {
				dismissProgressDialog();
			}
			if (result.equals("correct")) {
				Toast.makeText(MainAdministratorActivity.this, "Успешно внесен кредит", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
