package onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import onlinebidding.adapters.AuctionAdapter;
import onlinebidding.adapters.UserAdapter;
import onlinebidding.interfaces.ListUsers;
import onlinebidding.model.Auction;
import onlinebidding.model.User;
import solution.springforandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListEnteredActivity extends Activity implements ListUsers {
	
	
	private ListView usersEnteredView;
	private ArrayAdapter<User> usersEnteredAdapter;
	private List<User> listUsers;
	private Auction opennedAuction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_entered);
		
		getAuction();
		
		
		getEnteredInAuction();
	}
	
	private void getEnteredInAuction(){
		new PostAuction().execute(getResources().getString(R.string.url_address)+"/auctionenteredusers");
	}
	
	private void getAuction(){
		opennedAuction = (Auction)getIntent().getExtras().get("auction");
	}

	private void initAuctionsView(){
		usersEnteredView = (ListView) findViewById(R.id.users_entered_in_auction);
		usersEnteredAdapter = new UserAdapter(this, listUsers);
		usersEnteredView.setAdapter(usersEnteredAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_entered, menu);
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

	public void startMyProfileActivity(Intent intent) {
		startActivity(intent);
	}
	
	private class PostAuction extends AsyncTask<String, Void, User[]> {
		
		@Override
		protected User[] doInBackground(String... params) {
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			return restTemplate.postForObject(params[0], opennedAuction.getAuctionID(), User[].class);
		}
		
		@Override
		protected void onPostExecute(User[] result) {
			setData(result);
		}
	}
	
	private void setData(User[] data){
		listUsers = new ArrayList<User>();
		if(data != null){
			listUsers = Arrays.asList(data);
		}
		
		initAuctionsView();
	}
}
