package bb;

public class TestNative {
	
	static { 

		System.loadLibrary("TestNative");  //���ӿ������

	} 
	public native int testnnnn(String str1,String str2,int a,int b);
	

}

