package onlinebidding.timers;

import onlinebidding.activities.SingleAuctionActivity;
import onlinebidding.model.Auction;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import solution.springforandroid.R;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;

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
		// TODO Auto-generated method stub
		
	}

	private class PostForPriceUpdate extends AsyncTask<String, Void, Auction> {
		
		@Override
		protected Auction doInBackground(String... params) {
			RestTemplate restTemplate = new RestTemplate();
			// For bug fixing I/O POST requests
			
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			return restTemplate.postForObject(params[0], ((SingleAuctionActivity) ctx).getActiveAuction().getAuctionID(), Auction.class);
		}
		
		@Override
		protected void onPostExecute(Auction result) {
			((SingleAuctionActivity) ctx).showResult(result);
		}
	}
}
