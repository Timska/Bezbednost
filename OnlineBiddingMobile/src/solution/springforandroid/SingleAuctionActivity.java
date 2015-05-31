package solution.springforandroid;
import java.util.Date;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tables.Auction;
import tables.User;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleAuctionActivity extends Activity {

	private User currentUser;
	private Auction auction;
	private TextView txtPrice;
	private EditText txtNewPrice;
	private Button btnRise;
	private int newPrice;
	private TextView txtAuctionCreator;
	private TextView txtAuctionItemName;
	private TextView txtAuctionItemDescription;
	private CountDownTimer timer;
	private TextView txtTimer;
	private TextView txtWinner;
	private Button btnViewEntrants;
	private boolean entered;
	private Button btnEnterAuction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_auction);
		
		init();
		
		setTitle(auction.getAuctionName());
		
		checkEntered();
		
		initViews();
		
	}
	
	private void checkEntered(){
		if(auction.getEntrants() == null){
			entered = false;
			return;
		}
		for(int i=0;i<auction.getEntrants().size();++i){
			if(auction.getEntrants().get(i).getUserName().equals(currentUser.getUserName())){
				entered = true;
				return;
			}
		}
		entered = false;
	}
	
	private void initViews(){
		txtPrice = (TextView) findViewById(R.id.txt_price);
		txtPrice.setText(auction.getCurrentPrice()+"ден");
		
		txtNewPrice = (EditText) findViewById(R.id.txt_new_price);
		newPrice = Integer.parseInt(auction.getCurrentPrice()) + 100;
		txtNewPrice.setText(newPrice+"");
		
		
		btnRise = (Button) findViewById(R.id.btn_rise);
		btnRise.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				int price = Integer.parseInt(txtNewPrice.getText().toString());
				if(price < Integer.parseInt(auction.getCurrentPrice()) + 100){
					txtNewPrice.setText(String.valueOf(Integer.parseInt(auction.getCurrentPrice())+ 100));
					Toast.makeText(SingleAuctionActivity.this, "Внесената цена мора да биде барем за 100ден поголема од претходната.", Toast.LENGTH_SHORT).show();
					return;
				}
				
				newPrice = price;
				new PostPrice().execute("http://192.168.0.101:8080/HelloWorld/updateauctionprice");
			}
		});
		
		btnViewEntrants = (Button) findViewById(R.id.btn_view_entrants);
		btnViewEntrants.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnEnterAuction = (Button) findViewById(R.id.btn_enter_auction);
		
		checkCreator();
		
		txtAuctionCreator = (TextView) findViewById(R.id.txt_auction_creator);
		txtAuctionCreator.setText("Креатор: "+auction.getCreator().getUserName());
		
		txtAuctionItemName = (TextView) findViewById(R.id.txt_auction_item_name);
		txtAuctionItemName.setText(auction.getItem().getItemName());
		
		txtAuctionItemDescription = (TextView) findViewById(R.id.txt_auction_item_description);
		txtAuctionItemDescription.setText(auction.getItem().getDescription());
		
		txtWinner = (TextView) findViewById(R.id.txt_current_price);
		
		txtTimer = (TextView) findViewById(R.id.txt_timer);
		initTimer();
	}
	
	private void enterOrExitAuction(View view){
		entered = !entered;
		if(entered){
			btnEnterAuction.setText(getResources().getString(R.string.exit_auction));
			new PostForEnterOrExitAuction().execute("http://192.168.0.101:8080/HelloWorld/enterauction");
		}
		else{
			btnEnterAuction.setText(getResources().getString(R.string.enter_auction));
			new PostForEnterOrExitAuction().execute("http://192.168.0.101:8080/HelloWorld/exitauction");
		}
	}
	
	private void checkCreator(){
		if(currentUser.getUserName().equals(auction.getCreator().getUserName())){
			btnRise.setEnabled(false);
			btnEnterAuction.setEnabled(false);
		}
		else{
			btnViewEntrants.setEnabled(false);
		}
	}
	
	private void initTimer(){
		Date now = new Date();
		long milliseconds;
		if(now.compareTo(auction.getEndDate()) > 0){
			milliseconds = 0;
		}
		else{
			milliseconds = now.getTime() - auction.getEndDate().getTime();
		}
		timer = new CountEndDate(milliseconds, (long)1000, txtTimer, this);
		timer.start();
	}
	
	private void init(){
		Intent intent = getIntent();
		currentUser = (User) intent.getExtras().get("user");
		auction = (Auction) intent.getExtras().get("auction");
	}
	
	public void auctionFinished(){
		txtTimer.setText("Завршено");
		btnRise.setEnabled(false);
		txtWinner.setText("Победник е: "+auction.getWinner().getUserName());
		txtWinner.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(SingleAuctionActivity.this, ProfileActivity.class);
				intent.putExtra("user", auction.getWinner());
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_auction, menu);
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
	
	
	private class PostPrice extends AsyncTask<String, Void, Auction> {
		
		@Override
		protected Auction doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("auctionID", String.valueOf(auction.getAuctionID()));
			credentials.add("userName", currentUser.getUserName());
			credentials.add("auctionPrice", String.valueOf(newPrice));
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			return restTemplate.postForObject(params[0], credentials, Auction.class);
		}
		
		@Override
		protected void onPostExecute(Auction result) {
			showResult(result);
		}
	}
	
	private class PostForEnterOrExitAuction extends AsyncTask<String, Void, Auction> {
		
		@Override
		protected Auction doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("auctionID", String.valueOf(auction.getAuctionID()));
			credentials.add("userName", currentUser.getUserName());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			return restTemplate.postForObject(params[0], credentials, Auction.class);
		}
		
		@Override
		protected void onPostExecute(Auction result) {
			setResult(result);
		}
	}
	
	
	private void setResult(Auction result){
		auction = result;
	}
	
	private void showResult(Auction result){
		setResult(auction);
		txtPrice.setText(result.getCurrentPrice()+"ден");
		newPrice = Integer.parseInt(auction.getCurrentPrice()) + 100;
		txtNewPrice.setText(newPrice+"");
	}
}
