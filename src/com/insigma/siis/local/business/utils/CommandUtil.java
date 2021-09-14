package com.insigma.siis.local.business.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class CommandUtil {
	// 保存进程的输入流信息
	private List<String> stdoutList = new ArrayList<String>();
	// 保存进程的错误流信息
	private List<String> erroroutList = new ArrayList<String>();

	public int executeCommand(String command) {
		// 先清空
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//默认运行为运行错误
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			// 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// 启动线程读取缓冲区数据
			stdoutUtil.start();
			erroroutUtil.start();
			p.waitFor();
			status = p.exitValue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public int executeCommand(String[] command) {
		// 先清空
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//默认运行为运行错误
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);

			// 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// 启动线程读取缓冲区数据
			stdoutUtil.start();
			erroroutUtil.start();
			p.waitFor();
			status = p.exitValue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public int executeCommand(String cmd, String[] envp, File file) {
		// 先清空
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//默认运行为运行错误
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd, envp);

			// 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// 启动线程读取缓冲区数据
			stdoutUtil.start();
			erroroutUtil.start();
			p.waitFor();
			status = p.exitValue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<String> getStdoutList() {
		return stdoutList;
	}

	public List<String> getErroroutList() {
		return erroroutList;
	}

	public static void printList(List<String> list) {
		for (String string : list) {
			CommonQueryBS.systemOut(string);
		}
	}


}

class ThreadUtil implements Runnable {
	// 设置读取的字符编码
	private String character = "GB2312";
	private List<String> list;
	private InputStream inputStream;

	public ThreadUtil(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.list = list;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);// 将其设置为守护线程
		thread.start();
	}

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream,
					character));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					list.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				inputStream.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}