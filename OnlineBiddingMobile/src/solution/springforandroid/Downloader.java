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
		MappingJacksonHttpMessageConverter jacksonConverter= new MappingJacksonHttpMessageConverter();
		restTemplate.getMessageConverters().add(jacksonConverter);
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		// For HTTPS requests
		// HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(new MyHttpClient(context));
		// restTemplate.setRequestFactory(requestFactory);
		System.out.println("tukaaa eeee");
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
		System.out.println("vlezeeee");
		listener.onLoadFinished(result);
	}
	
}
