package onlinebidding.activities;

import java.util.Date;

import onlinebidding.model.Auction;
import onlinebidding.model.User;
import onlinebidding.model.UserAuction;
import onlinebidding.timers.CountEndDateTimer;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
	private static CountDownTimer timer;
	// private static CheckPriceChangedTimer timerUpdate;
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
		// timerUpdate.cancel();
	}
	
	private void checkEntered(){
		new PostCheckUserInAuction().execute(getResources().getString(R.string.url_address)+"/hasuserentered");
	}
	
	private void initViews(){
		txtPrice = (TextView) findViewById(R.id.txt_price);
		txtPrice.setText(auction.getCurrentPrice()+" ден.");
		
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
				if(currentUser.getUserName().equals(auction.getWinner().getUserName())){
					if(price - Integer.parseInt(auction.getCurrentPrice()) > currentUser.getCredit()){
						txtNewPrice.setText(String.valueOf(Integer.parseInt(auction.getCurrentPrice())+ 100));
						Toast.makeText(SingleAuctionActivity.this, "Немате доволно кредит", Toast.LENGTH_SHORT).show();
						return;
					}
					currentUser.setCredit(currentUser.getCredit() - (price - Integer.parseInt(auction.getCurrentPrice())));
					System.out.println(currentUser.getCredit());
				}
				else{
					if(price > currentUser.getCredit()){
						txtNewPrice.setText(String.valueOf(Integer.parseInt(auction.getCurrentPrice())+ 100));
						Toast.makeText(SingleAuctionActivity.this, "Немате доволно кредит", Toast.LENGTH_SHORT).show();
						return;
					}
					currentUser.setCredit(currentUser.getCredit() - price);
					/*if(!auction.getWinner().getUserName().equals(auction.getCreator().getUserName())){
						auction.getWinner().setCredit(auction.getWinner().getCredit() + Integer.parseInt(auction.getCurrentPrice()));
						new PostUser().execute(getResources().getString(R.string.url_address)+"/updateuser", String.valueOf(false));
					}*/
					//
					auction.getWinner().setCredit(auction.getWinner().getCredit() + Integer.parseInt(auction.getCurrentPrice()));
					new PostUser().execute(getResources().getString(R.string.url_address)+"/updateuser", String.valueOf(false));
					//
				}
				new PostUser().execute(getResources().getString(R.string.url_address)+"/updateuser", String.valueOf(true));
				new PostPrice().execute(getResources().getString(R.string.url_address)+"/updateauctionprice");
			}
		});
		
		
		btnViewEntrants = (Button) findViewById(R.id.btn_view_entrants);
		btnViewEntrants.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(SingleAuctionActivity.this, ListEnteredActivity.class);
				intent.putExtra("auction", auction);
				startActivity(intent);
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
		initTimers();
	}
	
	public void enterOrExitAuction(View view){
		entered = !entered;

		Date dateNow = new Date();
		if (auction.getStartDate().after(dateNow)) {
			btnRise.setEnabled(false);
			txtNewPrice.setEnabled(false);
		} else {
			btnRise.setEnabled(entered);
			txtNewPrice.setEnabled(entered);
		}
		
		if(entered){
			btnEnterAuction.setText(getResources().getString(R.string.exit_auction));
			new PostForEnter().execute(getResources().getString(R.string.url_address)+"/enterauction");
		}
		else{
			btnEnterAuction.setText(getResources().getString(R.string.enter_auction));
			new PostForExit().execute(getResources().getString(R.string.url_address)+"/exitauction");
		}
	}
	
	private void checkCreator(){
		if(currentUser.getUserName().equals(auction.getCreator().getUserName())){
			btnRise.setEnabled(false);
			txtNewPrice.setEnabled(false);
			btnEnterAuction.setEnabled(false);
		}
		else{
			btnViewEntrants.setEnabled(false);
		}
	}
	
	private void initTimers(){
		Date now = new Date();
		long milliseconds;
		System.out.println(auction.getEndDate().toString());
		if(now.compareTo(auction.getEndDate()) > 0){
			milliseconds = 0;
		}
		else{
			milliseconds = auction.getEndDate().getTime() - now.getTime();
		}
		timer = new CountEndDateTimer(milliseconds, (long)1000, txtTimer, this);
		timer.start();
		
		// timerUpdate = new CheckPriceChangedTimer(60 * 60 * 1000, 1000 * 60, this);
		// timerUpdate.start();
	}
	
	private void init(){
		Intent intent = getIntent();
		currentUser = (User) intent.getExtras().get("user");
		auction = (Auction) intent.getExtras().get("auction");
	}
	
	public void auctionFinished(){
		txtTimer.setText("Завршено");
		btnRise.setEnabled(false);
		txtNewPrice.setEnabled(false);
		btnEnterAuction.setEnabled(false);
		btnEnterAuction.setText(getResources().getString(R.string.enter_auction));
		txtWinner.setText("Победник е: "+auction.getWinner().getUserName());
	}
	
	private class PostPrice extends AsyncTask<String, Void, Auction> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SingleAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}
		
		@Override
		protected Auction doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("auctionID", String.valueOf(auction.getAuctionID()));
			credentials.add("userName", currentUser.getUserName());
			credentials.add("auctionPrice", String.valueOf(newPrice));
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(SingleAuctionActivity.this));
			return restTemplate.postForObject(params[0], credentials, Auction.class);
		}
		
		@Override
		protected void onPostExecute(Auction result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			showResult(result);
		}
	}
	
	private class PostForEnter extends AsyncTask<String, Void, String> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SingleAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			UserAuction ua = new UserAuction(currentUser, auction);
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(SingleAuctionActivity.this));
			return restTemplate.postForObject(params[0], ua, String.class);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
	
	private class PostForExit extends AsyncTask<String, Void, String> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SingleAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("userName", currentUser.getUserName());
			credentials.add("auctionID", auction.getAuctionID().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(SingleAuctionActivity.this));
			return restTemplate.postForObject(params[0], credentials, String.class);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
	
	private class PostCheckUserInAuction extends AsyncTask<String, Void, Boolean> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SingleAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			MultiValueMap<String, String> credentials = new LinkedMultiValueMap<String, String>();
			credentials.add("userName", currentUser.getUserName());
			credentials.add("auctionID", auction.getAuctionID().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(SingleAuctionActivity.this));
			return restTemplate.postForObject(params[0], credentials, Boolean.class);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (dialog.isShowing()) {
	            dialog.dismiss();
	        }
			setEntered(result);
		}
	}
	
	private class PostUser extends AsyncTask<String, Void, String> {
		
		/*private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SingleAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}*/
		
		@Override
		protected String doInBackground(String... params) {
			User u;
			System.out.println(Boolean.parseBoolean(params[1]));
			if(Boolean.parseBoolean(params[1])){
				u = currentUser;
			}
			else{
				u = auction.getWinner();
			}
			System.out.println("curr" + currentUser.getCredit());
			System.out.println("vo post user" + u.getCredit());
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(SingleAuctionActivity.this));
			String response = restTemplate.postForObject(params[0], u, String.class);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			/*if (dialog.isShowing()) {
				dialog.dismiss();
			}*/
		}
	}
	
	public Auction getActiveAuction(){
		return auction;
	}
	
	private void setEntered(boolean result){
		entered = result;
		if(entered){
			btnEnterAuction.setText(getResources().getString(R.string.exit_auction));
		}
		else{
			btnEnterAuction.setText(getResources().getString(R.string.enter_auction));
		}
		
		Date dateNow = new Date();
		if (auction.getEndDate().before(dateNow)) {
			auctionFinished();
		} else if (auction.getStartDate().after(dateNow)) {
			btnRise.setEnabled(false);
			txtNewPrice.setEnabled(false);
		} else {
			btnRise.setEnabled(entered);
			txtNewPrice.setEnabled(entered);
		}
	}
	
	public void showResult(Auction result){
		auction = result;
		txtPrice.setText(result.getCurrentPrice()+" ден.");
		newPrice = Integer.parseInt(auction.getCurrentPrice()) + 100;
		txtNewPrice.setText(newPrice+"");
	}
}
