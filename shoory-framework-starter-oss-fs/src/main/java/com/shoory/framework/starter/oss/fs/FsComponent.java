package com.shoory.framework.starter.oss.fs;

import com.shoory.framework.starter.oss.OssComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FsComponent implements OssComponent {
	@Value("${oss.fs.basePath}")
	public String basePath;

	public String upload(String prefix, String path, String mimeType, InputStream is) {
		try {
			File file = new File(basePath + prefix + path);
			OutputStream os = new FileOutputStream(file);

			byte[] b = new byte[1024];
			while ((is.read(b)) != -1) {
				os.write(b);// 写入数据
			}
			is.close();
			os.close();// 保存数据

			return path;
		} catch (Throwable e) {
			e.printStackTrace();
			return "";
		}
	}

	public InputStream download(String prefix, String path) {
		try {
			File file = new File(basePath + prefix + path);

			return new FileInputStream(file);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void delete(String prefix, String path) {
		// 指定要删除的 bucket 和对象键
		try {
			File file = new File(basePath + prefix + path);
			if (file.exists()) {
				file.delete();
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isExisted(String prefix, String path) {
		// TODO Auto-generated method stub
		try {
			File file = new File(basePath + prefix + path);
			return file.exists();
		} catch (Throwable e) {
		}
		return false;
	}

}
