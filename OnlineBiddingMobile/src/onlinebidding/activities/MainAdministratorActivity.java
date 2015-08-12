package onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import onlinebidding.adapters.UserAdapter;
import onlinebidding.interfaces.ListUsers;
import onlinebidding.model.User;
import onlinebidding.server.DownloadListener;
import onlinebidding.server.Downloader;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainAdministratorActivity extends Activity implements DownloadListener<User[]>, ListUsers {

	
	private ListView allUsersView;
	private ArrayAdapter<User> allUsersAdapter;
	private List<User> listUsers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_administrator);
		
		getAllUsers();
	}
	
	private void getAllUsers(){
		Downloader<User[]> downloader = new Downloader<User[]>(User[].class, this, this);
		downloader.execute(getResources().getString(R.string.url_address)+"/getallusers");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_administrator, menu);
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

	public void onLoadFinished(User[] data) {
		// TODO Auto-generated method stub
		setData(data);
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
		// TODO Auto-generated method stub
		new PostUser().execute(getResources().getString(R.string.url_address)+"/updateuser", String.valueOf(position));
	}
	
	private class PostUser extends AsyncTask<String, Void, String> {
		
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
		}
	}
}
