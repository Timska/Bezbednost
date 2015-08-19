package com.onlinebidding.adapters;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onlinebidding.R;
import com.onlinebidding.activities.SingleAuctionActivity;
import com.onlinebidding.interfaces.ListAuctions;
import com.onlinebidding.model.Auction;

public class AuctionAdapter extends ArrayAdapter<Auction> {

	private Context context;
	private LayoutInflater inflater;
	private List<Auction> list;
	
	private int[] colors = new int[] { 0xAAf6ffc8, 0xAA538d00 };

	public AuctionAdapter(Context context, List<Auction> auctions) {
		super(context, R.layout.auction_list_item, auctions);
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = auctions;
	}
	
	@Override
	public void clear() {
		list.clear();
		notifyDataSetInvalidated();
	}
	
	@Override
	public void addAll(Collection<? extends Auction> auctions) {
		list.addAll(auctions);
		notifyDataSetChanged();
	}

	private class AuctionHolder {
		private RelativeLayout itemLayout;
		private TextView txtName;
		private TextView txtAuctionPrice;
		private TextView txtAuctionStartDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		AuctionHolder holder = null;
		if (convertView == null) {
			holder = new AuctionHolder();
			holder.itemLayout = (RelativeLayout) inflater.inflate(R.layout.auction_list_item, parent, false);
			holder.txtName = (TextView) holder.itemLayout.findViewById(R.id.txtAuctionName);
			holder.txtAuctionPrice = (TextView) holder.itemLayout.findViewById(R.id.txtAuctionCurrentPrice);
			holder.txtAuctionStartDate = (TextView) holder.itemLayout.findViewById(R.id.txtAuctionStartDate);
			convertView = holder.itemLayout;
			convertView.setTag(holder);
		}
		
		holder = (AuctionHolder) convertView.getTag();
		Auction auction = list.get(position);
		holder.txtName.setText("Име: " + auction.getAuctionName());
		holder.txtAuctionPrice.setText("Моментална цена: " + auction.getCurrentPrice() + " ден.");
		holder.txtAuctionStartDate.setText("Дата на почеток: " + auction.convertStartDateToString());
		
		final int i = position;
		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(context, SingleAuctionActivity.class);
				intent.putExtra("auction", list.get(i));
				ListAuctions ctx = (ListAuctions) context;
				ctx.startAuctionActivity(intent);
			}
		});
		int colorPos = position % colors.length;
	    convertView.setBackgroundColor(colors[colorPos]);

		return convertView;
	}
}
