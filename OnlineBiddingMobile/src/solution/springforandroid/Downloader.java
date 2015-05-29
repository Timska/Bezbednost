package solution.springforandroid;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;

@SuppressWarnings("deprecation")
public class Downloader<T> extends AsyncTask<String, Void, T> {
	
	private Class<T> type;
	private DownloadListener<T> listener;
	private Context context;
	
	public Downloader(Class<T> type, DownloadListener<T> listener, Context context) {
		this.type = type;
		this.listener = listener;
		this.context = context;
	}
	
	@Override
	protected T doInBackground(String... params) {
		RestTemplate restTemplate = new RestTemplate();
		// For HTTPS requests
		// HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(new MyHttpClient(context));
		// restTemplate.setRequestFactory(requestFactory);
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
		listener.onLoadFinished(result);
	}
	
}
