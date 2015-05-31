package solution.springforandroid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tables.Auction;
import tables.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements DownloadListener<Auction[]>, ListAuctions {

	private ListView auctionsView;
	private ArrayAdapter<Auction> auctionsAdapter;
	private List<Auction> listAuctions;
	private User currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getUser();
		
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		System.out.println("OnResume entered!");
		getAuctionsFromServer();
	}
	
	private void initAuctionsView(){
		auctionsView = (ListView) findViewById(R.id.auctionsView);
		auctionsAdapter = new AuctionAdapter(this, listAuctions);
		auctionsView.setAdapter(auctionsAdapter);
	}
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
	private void getAuctionsFromServer(){
		Downloader<Auction[]> downloader = new Downloader<Auction[]>(Auction[].class, this, this);
		downloader.execute(getResources().getString(R.string.url_address)+"/notfinishedauctions");
	}
	
	public void startMyProfileActivity(View view){
		Intent intent =  new Intent(MainActivity.this, ProfileActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	public void logOut(View view){
		finish();
	}
	
	public void startMyAuctions(View view){
		Intent intent = new Intent(this, MyAuctionsActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}

	public void onLoadFinished(Auction[] data) {
		// TODO Auto-generated method stub
		if(data != null){
			listAuctions = Arrays.asList(data);
			Collections.sort(listAuctions);
		}
		else{
			listAuctions = new ArrayList<Auction>();
		}
		initAuctionsView();
	}

	public void startAuctionActivity(Intent intent) {
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}

}


