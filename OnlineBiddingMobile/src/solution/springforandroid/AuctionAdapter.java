package solution.springforandroid;

import java.util.List;

import tables.Auction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AuctionAdapter extends ArrayAdapter<Auction> {

	private Context context;
	
	public AuctionAdapter(Context context, List<Auction> auctions){
		super(context, 0, auctions);
		this.context = context;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Auction auction = getItem(position);    


       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item_auction, parent, false);
       }
       
       TextView txtAuctionNameAndDate = (TextView) convertView.findViewById(R.id.txtAuctionNameAndDate);
       Button btnAuctionEnter = (Button) convertView.findViewById(R.id.btnAuctionEnter);
       // Populate the data into the template view using the data object
       
       txtAuctionNameAndDate.setText(auction.toString());
       btnAuctionEnter.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Intent intent = new Intent(context, LoginActivity.class);
			
		}
	});
       // Return the completed view to render on screen
       return convertView;
   }
}
