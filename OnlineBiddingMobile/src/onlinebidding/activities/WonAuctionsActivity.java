package onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import onlinebidding.adapters.AuctionAdapter;
import onlinebidding.interfaces.ListAuctions;
import onlinebidding.model.Auction;
import onlinebidding.model.User;
import solution.springforandroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WonAuctionsActivity extends Activity implements ListAuctions {

	private User currentUser;
	private List<Auction> listMyAuctions;
	private ListView myAuctionsView;
	private ArrayAdapter<Auction> myAuctionsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_won_auctions);
		
		currentUser = (User) getIntent().getExtras().get("user");
		setTitle(currentUser.getUserName());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new PostUsername().execute(getResources().getString(R.string.url_address)+"/wonuserauctions", currentUser.getUserName());
	}
	
	private class PostUsername extends AsyncTask<String, Void, Auction[]> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(WonAuctionsActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}
		
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
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			showResult(result);
		}
	}
	
	private void showResult(Auction[] data) {
		if (data != null) {
			listMyAuctions = Arrays.asList(data);
			Collections.sort(listMyAuctions);
		}
		else {
			listMyAuctions = new ArrayList<Auction>();
		}
		initAuctionsView();
	}
	
	private void initAuctionsView() {
		myAuctionsView = (ListView) findViewById(R.id.won_auctions_view);
		myAuctionsAdapter = new AuctionAdapter(this, listMyAuctions);
		myAuctionsView.setAdapter(myAuctionsAdapter);
	}

	public void startAuctionActivity(Intent intent) {
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
}






