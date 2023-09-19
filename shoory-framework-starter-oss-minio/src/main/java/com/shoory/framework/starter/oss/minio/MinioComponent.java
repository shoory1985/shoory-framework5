package com.shoory.framework.starter.oss.minio;

import com.shoory.framework.starter.oss.OssComponent;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MinioComponent implements OssComponent {

	@Autowired
	private MinioClient client;

	public String upload(String bucketName, String path, String mimeType, InputStream is) {
		try {
			client.putObject(bucketName, path, is, mimeType);
			return path;
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException
				| NoResponseException | ErrorResponseException | InternalException | InvalidArgumentException
				| IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public InputStream download(String bucketName, String resourcePath) {
		try {
			return client.getObject(bucketName, resourcePath);
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException
				| NoResponseException | ErrorResponseException | InternalException | InvalidArgumentException
				| IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}

	public void delete(String bucketName, String resourcePath) {
		// 指定要删除的 bucket 和对象键
		try {
			client.removeObject(bucketName, resourcePath);
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | NoResponseException | ErrorResponseException | InternalException | IOException | XmlPullParserException | InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isExisted(String bucketName, String resourcePath) {
		// TODO Auto-generated method stub
		try {
			return client.getObject(bucketName, resourcePath) != null;
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException
				| NoResponseException | ErrorResponseException | InternalException | InvalidArgumentException
				| IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> listFiles(String bucketName, String dirPath) {
		List<String> files = new ArrayList<>();
		try {
			client.listObjects(bucketName, dirPath, false)
					.forEach(itemResult -> {
						try {
							files.add(itemResult.get().objectName());
						} catch (Throwable e) {
							e.printStackTrace();
						}
					});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return files;
	}

}
