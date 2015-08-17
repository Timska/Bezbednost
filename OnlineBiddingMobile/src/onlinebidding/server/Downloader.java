package onlinebidding.server;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class Downloader<T> extends AsyncTask<String, Void, T> {
	
	private Class<T> type;
	private DownloadListener<T> listener;
	private Context context;
	
	public Downloader(Class<T> type, DownloadListener<T> listener, Context context) {
		this.type = type;
		this.listener = listener;
		this.context = context;
	}
	
	private ProgressDialog dialog;
	
	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		this.dialog.setMessage("Loading...");
		this.dialog.show();
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
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		listener.onLoadFinished(result);
	}
	
}
