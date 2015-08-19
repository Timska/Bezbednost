package com.onlinebidding.timers;

import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;

import com.onlinebidding.R;
import com.onlinebidding.activities.SingleAuctionActivity;
import com.onlinebidding.model.Auction;
import com.onlinebidding.server.CustomHttpRequestFactory;

public class CheckPriceChangedTimer extends CountDownTimer{
	
	private Context ctx;

	public CheckPriceChangedTimer(long millisInFuture, long countDownInterval, Context context) {
		super(millisInFuture, countDownInterval);
		ctx = context;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		new PostForPriceUpdate().execute(ctx.getResources().getString(R.string.url_address)+"/getauction");
	}

	@Override
	public void onFinish() {

	}

	private class PostForPriceUpdate extends AsyncTask<String, Void, Auction> {
		
		@Override
		protected Auction doInBackground(String... params) {
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			// restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// For HTTPS requests
			restTemplate.setRequestFactory(new CustomHttpRequestFactory(ctx));
			return restTemplate.postForObject(params[0], ((SingleAuctionActivity) ctx).getActiveAuction().getAuctionID(), Auction.class);
		}
		
		@Override
		protected void onPostExecute(Auction result) {
			((SingleAuctionActivity) ctx).showResult(result);
		}
	}
}
