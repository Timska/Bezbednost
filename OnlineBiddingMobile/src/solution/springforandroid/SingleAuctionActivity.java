package solution.springforandroid;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SingleAuctionActivity extends Activity {

	ListView listActiveUsers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_auction);
		
		listActiveUsers= (ListView) findViewById(R.id.listActiveUsers);
		
		String[] values = new String[] { "Android List View", 
                 "Adapter implementation",
                 "Simple List View In Android",
                 "Create List View Android", 
                 "Android Example", 
                 "List View Source Code", 
                 "List View Array Adapter", 
                 "Adapter implementation",
                 "Simple List View In Android",
                 "Create List View Android", 
                 "Android Example", 
                 "List View Source Code", 
                 "List View Array Adapter", 
                 "Android Example List View" 
                };
		
		ArrayAdapter<String> adapterListMyAuctions = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, values);
		listActiveUsers.setAdapter(adapterListMyAuctions);
		System.out.println(adapterListMyAuctions.getCount());
		
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
}
