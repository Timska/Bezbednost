package onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import onlinebidding.adapters.UserEnteredAdapter;
import onlinebidding.model.Auction;
import onlinebidding.model.User;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListEnteredActivity extends Activity{
	
	
	private ListView usersEnteredView;
	private ArrayAdapter<User> usersEnteredAdapter;
	private List<User> listUsers;
	private Auction opennedAuction;
	private ProgressDialog dialog;

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
		usersEnteredAdapter = new UserEnteredAdapter(this, listUsers);
		usersEnteredView.setAdapter(usersEnteredAdapter);
	}
	
	private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(ListEnteredActivity.this);
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

	private class PostAuction extends AsyncTask<String, Void, User[]> {
		
		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}
		
		@Override
		protected User[] doInBackground(String... params) {
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(ListEnteredActivity.this));
			
			return restTemplate.postForObject(params[0], opennedAuction.getAuctionID(), User[].class);
		}
		
		@Override
		protected void onPostExecute(User[] result) {
			if (!isFinishing()) {
				dismissProgressDialog();
			}
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
