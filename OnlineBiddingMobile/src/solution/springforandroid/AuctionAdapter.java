package solution.springforandroid;

import java.util.List;

import tables.Auction;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AuctionAdapter extends ArrayAdapter<Auction> {

	private Context context;
	private List<Auction> list;
	
	public AuctionAdapter(Context context, List<Auction> auctions){
		super(context, 0, auctions);
		this.context = context;
		this.list = auctions;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Auction auction = getItem(position);    
       final int i = position;

       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item_auction, parent, false);
       }
       
       TextView txtAuctionNameAndDate = (TextView) convertView.findViewById(R.id.txtAuctionNameAndDate);
       // Populate the data into the template view using the data object
       
       txtAuctionNameAndDate.setText(auction.toString());
       
       convertView.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Auction a = list.get(i);
			Intent intent = new Intent(context, SingleAuctionActivity.class);
			intent.putExtra("auction", a);
			ListAuctions ctx = (ListAuctions) context;
			ctx.startAuctionActivity(intent);
		}
	});
       
       
       // Return the completed view to render on screen
       return convertView;
   }
}
