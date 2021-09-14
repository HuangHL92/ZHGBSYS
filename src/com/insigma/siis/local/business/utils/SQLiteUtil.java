package com.insigma.siis.local.business.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.helpers.AttributesImpl;

public class SQLiteUtil {
	private Connection conn = null;
	private String filepath;

	public SQLiteUtil(String filepath) throws Exception {
		try {
			Class.forName("org.sqlite.JDBC");
			this.filepath = filepath;
			this.conn = DriverManager.getConnection("jdbc:sqlite:"
					+ this.filepath);
		} catch (Exception e) {
			throw e;
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void close() throws SQLException {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outbuff.flush();
		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
	}

	// 创建空的db文件
	public static void copyFileByPath(String sourcePath, String targetPath)
			throws IOException {
		FileInputStream input = null;
		BufferedInputStream bufInput = null;
		BufferedOutputStream bufOutput = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(sourcePath);
			bufInput = new BufferedInputStream(input);
			File file = new File(targetPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			output = new FileOutputStream(file);
			bufOutput = new BufferedOutputStream(output);
			byte[] b = new byte[1025 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				bufOutput.write(b, 0, len);
			}
			bufOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufOutput.close();
			bufInput.close();
			output.close();
			input.close();
		}
	}

}
