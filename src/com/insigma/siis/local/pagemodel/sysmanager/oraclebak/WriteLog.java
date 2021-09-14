package com.insigma.siis.local.pagemodel.sysmanager.oraclebak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WriteLog implements Runnable{
	ExpData ed;
	InputStream is = null;
	public volatile boolean exit = false; 
	public WriteLog(ExpData ed,InputStream is) {
		this.ed = ed;
		this.is = is;
	}
	@Override
	public void run() {
		BufferedReader in = new BufferedReader(new InputStreamReader(is)); 
		try {
			
			String line = null;
			while ((line = in.readLine()) != null) {
				//b.append(line + "\n");
				ed.outPrintlnErr(line);
			}
			in.close();
            
		} catch (Exception e) {
			e.printStackTrace();
			
			ed.outPrintlnErr(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
		
		
		
		/*
		File f = new File(ed.logPath);
		File f1 = null;
		BufferedReader br = null;
		long from = 0;
		try {
			while(true){
				Thread.sleep(1000);
				if(f.isFile()){
					break;
				}else{
					//f = new File(ed.logPath);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			while(!exit){
				//f1 = new File(ed.logPath);
				//br.skip(from);
				// StringBuffer b = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					//b.append(line + "\n");
					ed.outPrintln(line);
				}
				Thread.sleep(400);
				//from = f.length();
				//System.out.println(from);
			}
			
			
            //br.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			ed.outPrintlnErr(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	*/
	}

}
