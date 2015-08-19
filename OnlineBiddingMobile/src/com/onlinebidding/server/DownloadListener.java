package com.onlinebidding.server;

public interface DownloadListener<T> {
	
	public void onLoadFinished(T data);
}
