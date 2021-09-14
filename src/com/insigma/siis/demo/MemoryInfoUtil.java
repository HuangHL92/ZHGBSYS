package com.insigma.siis.demo;

public class MemoryInfoUtil {

	public static String getCurrentMemoryInfo(){
		  Runtime currRuntime = Runtime.getRuntime();
		  int nFreeMemory = (int) currRuntime.freeMemory() / 1024 / 1024;
		  int nTotalMemory = (int)currRuntime.totalMemory() / 1024 / 1024;

		  System.out.println("当前剩余内存: " + nFreeMemory+"====总内存："+nTotalMemory);
		  return nFreeMemory+"===="+nTotalMemory; 
	}
}
