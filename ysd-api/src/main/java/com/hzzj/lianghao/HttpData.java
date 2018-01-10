package com.hzzj.lianghao;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class HttpData {

	private List<NameValuePair> paramList;

	private String charset;

	public HttpData(List<NameValuePair> paramList, String charset) {
		this.paramList = paramList;
		this.charset = charset;
	}

	public String getData() throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < paramList.size(); i++) {
			NameValuePair nameValuePair = paramList.get(i);
			buffer.append(nameValuePair.getName()).append('=').append(URLEncoder.encode(nameValuePair.getValue(), charset)).append('&');
		}
		return buffer.substring(0, buffer.length() - 1);
	}
}
