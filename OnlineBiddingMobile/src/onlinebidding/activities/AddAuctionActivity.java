package onlinebidding.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import onlinebidding.model.Auction;
import onlinebidding.model.Item;
import onlinebidding.model.User;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddAuctionActivity extends Activity {

	private TextView txtStartDate;
	private TextView txtEndDate;
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

	private void initViews() {

		txtAuctionName = (EditText) findViewById(R.id.auction_name_text);
		txtAuctionPrice = (EditText) findViewById(R.id.auction_current_price);
		txtAuctionItemName = (EditText) findViewById(R.id.auction_item_name);
		txtAuctionItemDescription = (EditText) findViewById(R.id.auction_item_description);

		txtStartDate = (TextView) findViewById(R.id.auction_start_date);

		txtStartDate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(AddAuctionActivity.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH));
				dpd.show();

				new TimePickerDialog(AddAuctionActivity.this, time, myCalendar
						.get(Calendar.HOUR_OF_DAY), myCalendar
						.get(Calendar.MINUTE), true).show();

				start = true;
			}
		});

		txtEndDate = (TextView) findViewById(R.id.auction_end_date);

		txtEndDate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(AddAuctionActivity.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH));
				dpd.show();

				System.out.println("pomina");

				new TimePickerDialog(AddAuctionActivity.this, time, myCalendar
						.get(Calendar.HOUR_OF_DAY), myCalendar
						.get(Calendar.MINUTE), true).show();

				start = false;
			}
		});

	}

	private void setDatePicker() {
		myCalendar = Calendar.getInstance();

		date = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				updateLabel();
			}

		};

		time = new TimePickerDialog.OnTimeSetListener() {

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				myCalendar.set(Calendar.MINUTE, minute);
			}
		};

	}

	private void updateLabel() {

		String myFormat = "yyyy-MM-dd kk:mm"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		TextView ev;
		if (start) {
			ev = txtStartDate;
		} else {
			ev = txtEndDate;
		}

		ev.setText(sdf.format(myCalendar.getTime()));

	}

	public void addNewAuction(View view) {
		if (validate()) {
			new PostAuction().execute(getResources().getString(
					R.string.url_address)
					+ "/addauction");
		}
	}

	private boolean validate() {
		boolean valid = true;
		if (txtAuctionName.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Внесете име на аукцијата", Toast.LENGTH_SHORT)
					.show();
			valid = false;
		}
		else if (txtAuctionPrice.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Внесете почетна цена", Toast.LENGTH_SHORT)
					.show();
			valid = false;
		} 
		else if (txtStartDate.getText().toString().equals(getResources().getString(R.string.auction_start_date_hint))) {
			Toast.makeText(this, "Внесете почетна дата",
					Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if (txtEndDate.getText().toString().equals(getResources().getString(R.string.auction_end_date_hint))) {
			Toast.makeText(this, "Внесете крајна дата",
					Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if (getDateFromString(txtStartDate.getText().toString()).after(
				getDateFromString(txtEndDate.getText().toString()))) {
			Toast.makeText(this, "Почетната дата треба да е пред крајната",
					Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if (txtAuctionItemName.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Внесете име на предметот што е на аукција",
					Toast.LENGTH_SHORT).show();
			valid = false;
		}
		else if (txtAuctionItemDescription.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Внесете опис на предметот што е на аукција",
					Toast.LENGTH_SHORT).show();
			valid = false;
		}

		return valid;
	}

	private void getUser() {
		currentUser = (User) getIntent().getExtras().get("user");
	}

	@SuppressWarnings("deprecation")
	private Date getDateFromString(String s) {
		System.out.println("vo get date" + s);
		StringTokenizer st = new StringTokenizer(s);
		StringTokenizer date = new StringTokenizer(st.nextToken(), "-");
		StringTokenizer time = new StringTokenizer(st.nextToken(), ":");
		int year = Integer.parseInt(date.nextToken());
		int month = Integer.parseInt(date.nextToken());
		int day = Integer.parseInt(date.nextToken());
		int hours = Integer.parseInt(time.nextToken());
		int minutes = Integer.parseInt(time.nextToken());
		Date dateFromString = new Date(year - 1900, month - 1, day, hours, minutes);
		System.out.println(dateFromString);
		return dateFromString;
	}

	private void showResult(String result) {
		if (result.equals("correct")) {
			Toast.makeText(this, "Успешно додадена аукција", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private class PostAuction extends AsyncTask<String, Void, String> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(AddAuctionActivity.this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Item item = new Item(txtAuctionItemName.getText().toString(), null,
					txtAuctionItemDescription.getText().toString());
			
			Auction a = new Auction(txtAuctionName.getText().toString(),
					currentUser, currentUser, item,
					getDateFromString(txtStartDate.getText().toString()),
					getDateFromString(txtEndDate.getText().toString()),
					txtAuctionPrice.getText().toString());
			
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			
			// For HTTPS requests
			// restTemplate.setRequestFactory(new CustomHttpRequestFactory(AddAuctionActivity.this));
			
			return restTemplate.postForObject(params[0], a, String.class);
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			showResult(result);
		}
	}

}
