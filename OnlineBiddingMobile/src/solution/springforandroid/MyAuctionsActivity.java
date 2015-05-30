package solution.springforandroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tables.Auction;
import tables.User;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAuctionsActivity extends Activity {

	
	private User currentUser;
	private List<Auction> listMyAuctions;
	private ListView myAuctionsView;
	private ArrayAdapter<Auction> myAuctionsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_auctions);
		
		getUser();
		
		setTitle(currentUser.getUserName());
		
		getActiveAuctionsFromUser(currentUser.getUserName());
		
		
	}
	
	private void getAllAuctionsFromUser(String username){
		new PostUsername().execute("http://192.168.0.101:8080/HelloWorld/userauctions", username);
	}
	
	private void getActiveAuctionsFromUser(String username){
		new PostUsername().execute("http://192.168.0.101:8080/HelloWorld/usernotfinishedauctions", username);
	}

	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_auctions, menu);
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
	
	
	private class PostUsername extends AsyncTask<String, Void, Auction[]> {
		
		@Override
		protected Auction[] doInBackground(String... params) {
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			Auction[] response = restTemplate.postForObject(params[0], params[1], Auction[].class);
			return response;
		}
		
		@Override
		protected void onPostExecute(Auction[] result) {
			showResult(result);
		}
	}
	
	private void showResult(Auction[] data){
		if(data != null){
			listMyAuctions = Arrays.asList(data);
		}
		else{
			listMyAuctions = new ArrayList<Auction>();
		}
		initAuctionsView();
	}
	
	public void showAllMyAuctions(View view){
		getAllAuctionsFromUser(currentUser.getUserName());
	}
	
	private void initAuctionsView(){
		myAuctionsView = (ListView) findViewById(R.id.my_auctions_view);
		myAuctionsAdapter = new AuctionAdapter(this, listMyAuctions);
		myAuctionsView.setAdapter(myAuctionsAdapter);
	}
	
}
