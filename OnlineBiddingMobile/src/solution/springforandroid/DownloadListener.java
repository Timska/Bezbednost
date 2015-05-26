package solution.springforandroid;

public interface DownloadListener<T> {
	
	public void onLoadFinished(T data);
}
