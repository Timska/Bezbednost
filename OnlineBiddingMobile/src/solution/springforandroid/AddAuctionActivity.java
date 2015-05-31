package solution.springforandroid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import tables.Auction;
import tables.Item;
import tables.User;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddAuctionActivity extends Activity {

	private EditText txtStartDate;
	private EditText txtEndDate;
	private DatePickerDialog.OnDateSetListener date;
	private TimePickerDialog.OnTimeSetListener time;
	private Calendar myCalendar;
	private EditText txtAuctionName;
	private EditText txtAuctionPrice;
	private EditText txtAuctionItemName;
	private EditText txtAuctionItemDescription;
	private User currentUser;
	private boolean start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_auction);
		
		start = false;
		
		getUser();
		
		setDatePicker();
		
		initViews();
	}
	
	private void initViews(){
		
		txtAuctionName = (EditText) findViewById(R.id.auction_name_text);
		txtAuctionPrice = (EditText) findViewById(R.id.auction_current_price);
		txtAuctionItemName = (EditText) findViewById(R.id.auction_item_name);
		txtAuctionItemDescription = (EditText) findViewById(R.id.auction_item_description);
		
		
		txtStartDate = (EditText) findViewById(R.id.auction_start_date);
		
		txtStartDate.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            new DatePickerDialog(AddAuctionActivity.this, date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	            
	            new TimePickerDialog(AddAuctionActivity.this, time, 
	            		myCalendar.get(Calendar.HOUR_OF_DAY), 
	            		myCalendar.get(Calendar.MINUTE), true).show();
	            
	            start = true;
	        }
	    });
		
		
		txtEndDate = (EditText) findViewById(R.id.auction_end_date);
		
		txtEndDate.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            new DatePickerDialog(AddAuctionActivity.this, date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	            
	            System.out.println("pomina");
	            
	            new TimePickerDialog(AddAuctionActivity.this, time, 
	            		myCalendar.get(Calendar.HOUR_OF_DAY), 
	            		myCalendar.get(Calendar.MINUTE), true).show();
	            
	            start = false;
	        }
	    });
		
		
		
		
	}
	
	private void setDatePicker(){
		myCalendar = Calendar.getInstance();

		date = new DatePickerDialog.OnDateSetListener() {

		    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        // TODO Auto-generated method stub
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        

	            updateLabel();
		    }

		};
		
		time = new TimePickerDialog.OnTimeSetListener() {
			
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		        myCalendar.set(Calendar.MINUTE, minute);
			}
		};
		
	}
	
	private void updateLabel() {

	    String myFormat = "yyyy-MM-dd kk:mm"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

	    EditText ev;
	    if(start){
	    	ev = txtStartDate;
	    }
	    else{
	    	ev = txtEndDate;
	    }
	    
	    ev.setText(sdf.format(myCalendar.getTime()));
	    
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_auction, menu);
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
	
	public void addNewAuction(View view){
		new PostAuction().execute(getResources().getString(R.string.url_address)+"/addauction");
	}
	
	private void getUser(){
		currentUser = (User) getIntent().getExtras().get("user");
	}
	
	private Date getDateFromString(String s){
		System.out.println("vo get date" + s);
		StringTokenizer st = new StringTokenizer(s);
		StringTokenizer date = new StringTokenizer(st.nextToken(), "-");
		StringTokenizer time = new StringTokenizer(st.nextToken(), ":");
		int year = Integer.parseInt(date.nextToken());
		int month = Integer.parseInt(date.nextToken());
		int day = Integer.parseInt(date.nextToken());
		int hours = Integer.parseInt(time.nextToken());
		int minutes = Integer.parseInt(time.nextToken());;
		Date dateFromString = new Date(year-1900, month-1, day, hours, minutes);
		System.out.println(dateFromString);
		return dateFromString;
	}
	
	private void showResult(){
		Toast.makeText(this, "Успешно додадена аукција", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
	private class PostAuction extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			Item item = new Item(txtAuctionItemName.getText().toString(), null, txtAuctionItemDescription.getText().toString());
			Auction a = new Auction(txtAuctionName.getText().toString(), currentUser, currentUser, 
					new ArrayList<User>(), item, 
					getDateFromString(txtStartDate.getText().toString()), 
					getDateFromString(txtEndDate.getText().toString()), 
					txtAuctionPrice.getText().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			return restTemplate.postForObject(params[0], a, String.class);
		}
		
		@Override
		protected void onPostExecute(String result) {
			System.out.println("vo post execute");
			showResult();
		}
	}
	
	
}
