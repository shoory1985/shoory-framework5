package com.shoory.framework.starter.oss;

import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.List;

public interface OssComponent {
	public String upload(String bucketName, String resourcePath, String mimeType, InputStream is);
	public InputStream download(String bucketName, String resourcePath);
	public void delete(String bucketName, String resourcePath);
	public boolean isExisted(String bucketName, String resourcePath);
	public List<String> listFiles(String bucketName, String dirPath);
}
