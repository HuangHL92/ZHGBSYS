package com.insigma.siis.demo;

public class MemoryInfoUtil {

	public static String getCurrentMemoryInfo(){
		  Runtime currRuntime = Runtime.getRuntime();
		  int nFreeMemory = (int) currRuntime.freeMemory() / 1024 / 1024;
		  int nTotalMemory = (int)currRuntime.totalMemory() / 1024 / 1024;

		  System.out.println("��ǰʣ���ڴ�: " + nFreeMemory+"====���ڴ棺"+nTotalMemory);
		  return nFreeMemory+"===="+nTotalMemory; 
	}
}
