package com.redpois0n.gscrot;

public class UploadResponse {

	private String url;
	private String removeUrl;
	
	public UploadResponse() {
		
	}
	
	public UploadResponse(String url, String removeUrl) {
		this.url = url;
		this.removeUrl = removeUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}

	public void setRemoveUrl(String removeUrl) {
		this.removeUrl = removeUrl;
	}

}
