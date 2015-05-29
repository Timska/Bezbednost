package solution.springforandroid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tables.Auction;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements DownloadListener<Auction[]>{

	private ListView auctionsView;
	private ArrayAdapter<Auction> auctionsAdapter;
	private List<Auction> listAuctions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getAuctionsFromServer();
	}
	
	private void initAuctionsView(){
		auctionsView = (ListView) findViewById(R.id.auctionsView);
		auctionsAdapter = new ArrayAdapter<Auction>(this, R.layout.simple_list_item_auction, listAuctions);
		auctionsView.setAdapter(auctionsAdapter);
	}
	
	private void getAuctionsFromServer(){
		Downloader<Auction[]> downloader = new Downloader<Auction[]>(Auction[].class, this, this);
		downloader.execute("http://192.168.0.102:8080/HelloWorld/getallauctions");
	}
	
	public void startMyProfileActivity(View view){
		Intent intent =  new Intent(MainActivity.this, ProfileActivity.class);
		startActivity(intent);
	}
	
	public void logOut(View view){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
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

}


