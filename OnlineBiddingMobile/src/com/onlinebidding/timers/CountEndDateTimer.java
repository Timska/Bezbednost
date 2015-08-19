package com.onlinebidding.timers;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.onlinebidding.R;
import com.onlinebidding.activities.SingleAuctionActivity;

public class CountEndDateTimer extends CountDownTimer{

	private TextView view;
	private Context context;
	
	public CountEndDateTimer(long millisInFuture, long countDownInterval, TextView view, Context context) {
		super(millisInFuture, countDownInterval);
		this.view = view;
		this.context = context;
		System.out.println("created"+millisInFuture);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		int hours = (int) (millisUntilFinished / (60 * 60 * 1000));
		millisUntilFinished -= hours * 60 * 60 * 1000;
		int minutes = (int) (millisUntilFinished / (60 * 1000));
		millisUntilFinished -= minutes * 60 * 1000;
		int seconds = (int) (millisUntilFinished / 1000);
		view.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
		if(hours < 1){
			view.setTextColor(context.getResources().getColor(R.color.red));
		}
	}

	@Override
	public void onFinish() {
		SingleAuctionActivity saa = (SingleAuctionActivity) context;
		saa.auctionFinished();
	}

}
