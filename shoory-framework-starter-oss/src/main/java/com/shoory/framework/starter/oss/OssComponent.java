package com.shoory.framework.starter.oss;

import java.io.InputStream;

public interface OssComponent {
	public String upload(String bucketName, String resourcePath, String mimeType, InputStream is);
	public InputStream download(String bucketName, String resourcePath);
	public void delete(String bucketName, String resourcePath);
	public boolean isExisted(String bucketName, String resourcePath);
}
