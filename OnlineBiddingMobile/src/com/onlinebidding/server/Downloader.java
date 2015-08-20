package com.onlinebidding.server;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.onlinebidding.interfaces.DialogShower;

import android.app.Activity;
import android.os.AsyncTask;

public class Downloader<T> extends AsyncTask<String, Void, T> {
	
	private Class<T> type;
	private DownloadListener<T> listener;
	private Activity context;
	private DialogShower shower;
	
	public Downloader(Class<T> type, DownloadListener<T> listener, Activity context, DialogShower shower) {
		this.type = type;
		this.listener = listener;
		this.context = context;
		this.shower = shower;
	}
	
	@Override
	protected void onPreExecute() {
		shower.showProgressDialog();
	}
	
	@Override
	protected T doInBackground(String... params) {
		RestTemplate restTemplate = new RestTemplate();
		
		// For bug fixing I/O POST requests
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
					
		// For HTTPS requests
		// restTemplate.setRequestFactory(new CustomHttpRequestFactory(context));
		
		try{
			return restTemplate.getForObject(params[0], type);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(T result) {
		if (!context.isFinishing()) {
			shower.dismissProgressDialog();
		}
		listener.onLoadFinished(result);
	}
	
}
