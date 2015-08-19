package com.onlinebidding.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.onlinebidding.R;
import com.onlinebidding.adapters.AuctionAdapter;
import com.onlinebidding.db.AuctionContentProvider;
import com.onlinebidding.interfaces.DialogShower;
import com.onlinebidding.interfaces.ListAuctions;
import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;
import com.onlinebidding.server.DownloadListener;
import com.onlinebidding.server.Downloader;

public class MainActivity extends Activity implements DownloadListener<Auction[]>, ListAuctions, DialogShower {

	private ListView auctionsView;
	private ArrayAdapter<Auction> auctionsAdapter;
	private List<Auction> listAuctions;
	private User currentUser;
	private ContentResolver resolver;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resolver = getContentResolver();
		// getUser();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		getUserFromServer();
		getAuctionsFromServer();
	}
	
	private void getUserFromServer(){
		new PostUsername().execute(getResources().getString(R.string.url_address)+"/getuser");
	}
	
	private void initAuctionsView(){
		auctionsView = (ListView) findViewById(R.id.auctionsView);
		auctionsAdapter = new AuctionAdapter(this, listAuctions);
		auctionsView.setAdapter(auctionsAdapter);
	}
	
	/*private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}*/
	
	public void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(MainActivity.this);
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
	
	private void getAuctionsFromServer(){
		Downloader<Auction[]> downloader = new Downloader<Auction[]>(Auction[].class, this, this, this);
		downloader.execute(getResources().getString(R.string.url_address)+"/notfinishedauctions");
	}
	
	public void startMyProfileActivity(View view){
		Intent intent =  new Intent(MainActivity.this, ProfileActivity.class);
		// intent.putExtra("user", currentUser.getUserName());
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	public void logOut(View view){
		SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("userName", null);
		editor.commit();
		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void startMyAuctions(View view){
		Intent intent = new Intent(this, MyAuctionsActivity.class);
		// intent.putExtra("user", currentUser.getUserName());
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}

	public void onLoadFinished(Auction[] data) {
		if(data != null){
			listAuctions = Arrays.asList(data);
			Collections.sort(listAuctions);
			
			int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.TUESDAY || day == Calendar.FRIDAY) {
				fillAuctionsDB(data);
			}
		}
		else{
			listAuctions = new ArrayList<Auction>();
		}
		initAuctionsView();
	}
	
	private void fillAuctionsDB(Auction[] data) {
		resolver.delete(AuctionContentProvider.CONTENT_URI, null, null);
		for (Auction auction : data) {
			resolver.insert(AuctionContentProvider.CONTENT_URI, AuctionContentProvider.auctionToContentValues(auction));
		}
	}

	public void startAuctionActivity(Intent intent) {
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	private class PostUsername extends AsyncTask<String, Void, User> {
		
		/*private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(MainActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}*/
		
		@Override
		protected User doInBackground(String... params) {
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(MainActivity.this));
			
			SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
			String userName = prefs.getString("userName", null);
			
			// return restTemplate.postForObject(params[0], currentUser.getUserName(), User.class);
			return restTemplate.postForObject(params[0], userName, User.class);
		}
		
		@Override
		protected void onPostExecute(User result) {
			/*if (dialog.isShowing()) {
				dialog.dismiss();
			}*/
			currentUser = result;
		}
	}

}


