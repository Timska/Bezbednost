package com.onlinebidding.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.onlinebidding.R;
import com.onlinebidding.adapters.AuctionAdapter;
import com.onlinebidding.db.AuctionContentProvider;
import com.onlinebidding.interfaces.ListAuctions;
import com.onlinebidding.model.Auction;

public class ActiveAuctionsActivity extends Activity implements LoaderCallbacks<Cursor>, ListAuctions {
	
	private LoaderManager loaderManager;
	private ListView auctionsView;
	private ArrayAdapter<Auction> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_auctions);
		
		Toast.makeText(this, "Немате пристап до интернет. Ова се последно зачуваните аукции во вашиот уред.", Toast.LENGTH_LONG).show();
		loaderManager = getLoaderManager();
		auctionsView = (ListView) findViewById(R.id.auctions_active);
		
		loaderManager.initLoader(0, null, this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loaderManager.restartLoader(0, null, this);
	}
	
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, AuctionContentProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (adapter != null) {
			adapter.clear();
		}
		List<Auction> auctions = new ArrayList<Auction>();
		while (data.moveToNext()) {
			auctions.add(AuctionContentProvider.cursorToAuction(data));
		}
		adapter = new AuctionAdapter(this, auctions);
		auctionsView.setAdapter(adapter);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		
	}

	public void startAuctionActivity(Intent intent) {
		
	}

}
