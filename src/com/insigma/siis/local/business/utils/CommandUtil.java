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
	// ������̵���������Ϣ
	private List<String> stdoutList = new ArrayList<String>();
	// ������̵Ĵ�������Ϣ
	private List<String> erroroutList = new ArrayList<String>();

	public int executeCommand(String command) {
		// �����
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//Ĭ������Ϊ���д���
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			// ����2���̣߳��ֱ��ȡ�������������ʹ�����������
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// �����̶߳�ȡ����������
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
		// �����
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//Ĭ������Ϊ���д���
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);

			// ����2���̣߳��ֱ��ȡ�������������ʹ�����������
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// �����̶߳�ȡ����������
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
		// �����
		stdoutList.clear();
		erroroutList.clear();
		int status = 1;			//Ĭ������Ϊ���д���
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd, envp);

			// ����2���̣߳��ֱ��ȡ�������������ʹ�����������
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// �����̶߳�ȡ����������
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
	// ���ö�ȡ���ַ�����
	private String character = "GB2312";
	private List<String> list;
	private InputStream inputStream;

	public ThreadUtil(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.list = list;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);// ��������Ϊ�ػ��߳�
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
				// �ͷ���Դ
				inputStream.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}