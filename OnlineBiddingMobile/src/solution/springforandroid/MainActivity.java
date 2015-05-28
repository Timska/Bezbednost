package solution.springforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements DownloadListener<String> {

	private TextView resultTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		resultTextView = (TextView) findViewById(R.id.result_text);
		Downloader<String> downloader = new Downloader<String>(String.class, this, getApplicationContext());
		// Downloader<Category[]> downloader = new Downloader<Category[]>(Category[].class, this);
		// Downloader<IfConfigMeJson> downloader = new Downloader<IfConfigMeJson>(IfConfigMeJson.class, this);
		// String url = "http://192.168.0.100:8080/HelloWorld/message";
		// String url = "http://192.168.43.237:8080/HelloWorld/message";
		// String url = "http://192.168.0.102:9966/web-programming/categories";
		// String url = "http://ifconfig.me/all.json";
		// String url = "https://127.0.0.2:8443/HelloWorld/message";
		String url = "http://127.0.0.1:8080/HelloWorld/";
		// String url = "https://10.0.2.2:8443/HelloWorld/message";
		// String url = "https://192.168.0.103:8443/HelloWorld/message";
		System.out.println("alohaaa");
		downloader.execute(url);
		System.out.println("daaaaaaa");
	}
	
	public void onLoadFinished(String data) {
		resultTextView.setText(data);
	}
	
	/*public void onLoadFinished(Category[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i].getName() + " ");
		}
		resultTextView.setText(sb.toString());
	}*/

	/*public void onLoadFinished(IfConfigMeJson data) {
		resultTextView.setText("Your IP address is: " + data.getIpAddr());
	}*/
}


