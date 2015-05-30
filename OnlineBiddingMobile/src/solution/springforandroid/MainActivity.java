package solution.springforandroid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tables.Auction;
import tables.User;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import solution.springforandroid.DownloadListener;

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
		downloader.execute("http://192.168.0.101:8080/HelloWorld/notfinishedauctions");
	}
	
	public void startMyProfileActivity(View view){
		Intent intent =  new Intent(MainActivity.this, ProfileActivity.class);
		intent.putExtra("user", currentUser);
		startActivity(intent);
	}
	
	public void logOut(View view){
		finish();
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
		startActivity(intent);
	}

}


