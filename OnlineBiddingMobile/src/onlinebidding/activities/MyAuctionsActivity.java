package onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import onlinebidding.adapters.AuctionAdapter;
import onlinebidding.interfaces.ListAuctions;
import onlinebidding.model.Auction;
import onlinebidding.model.User;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyAuctionsActivity extends Activity implements ListAuctions {

	private User currentUser;
	private List<Auction> listMyAuctions;
	private ListView myAuctionsView;
	private ArrayAdapter<Auction> myAuctionsAdapter;
	private boolean shownFinished;
	private Button btnChangeShownAuctions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_auctions);
		
		btnChangeShownAuctions = (Button) findViewById(R.id.btn_change_shown_auctions);
		
		getUser();
		
		setTitle(currentUser.getUserName());
		
		if (savedInstanceState != null && savedInstanceState.containsKey("shownFinished")) {
			shownFinished = savedInstanceState.getBoolean("shownFinished");
		} else {
			shownFinished = false;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("shownFinished", shownFinished);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(!shownFinished){
			getActiveAuctionsFromUser(currentUser.getUserName());
			btnChangeShownAuctions.setText(R.string.btn_show_finished);
		}
		else{
			getAllAuctionsFromUser(currentUser.getUserName());
			btnChangeShownAuctions.setText(R.string.btn_hide_finished);
		}
	}
	
	private void getAllAuctionsFromUser(String username){
		new PostUsername().execute(getResources().getString(R.string.url_address)+"/userauctions", username);
	}
	
	private void getActiveAuctionsFromUser(String username){
		new PostUsername().execute(getResources().getString(R.string.url_address)+"/usernotfinishedauctions", username);
	}
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
	private class PostUsername extends AsyncTask<String, Void, Auction[]> {
		
		@Override
		protected Auction[] doInBackground(String... params) {
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(MyAuctionsActivity.this));
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
			Collections.sort(listMyAuctions);
		}
		else{
			listMyAuctions = new ArrayList<Auction>();
		}
		initAuctionsView();
	}
	
	public void showAllMyAuctions(View view){
		shownFinished = !shownFinished;
		if(shownFinished){
			getAllAuctionsFromUser(currentUser.getUserName());
			btnChangeShownAuctions.setText(R.string.btn_hide_finished);
		}
		else{
			getActiveAuctionsFromUser(currentUser.getUserName());
			btnChangeShownAuctions.setText(R.string.btn_show_finished);
		}
	}
	
	private void initAuctionsView(){
		myAuctionsView = (ListView) findViewById(R.id.my_auctions_view);
		myAuctionsAdapter = new AuctionAdapter(this, listMyAuctions);
		myAuctionsView.setAdapter(myAuctionsAdapter);
	}

	public void startAuctionActivity(Intent intent) {
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	public void startAddAuctionActivtiy(View view){
		Intent intent = new Intent(this, AddAuctionActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
}
