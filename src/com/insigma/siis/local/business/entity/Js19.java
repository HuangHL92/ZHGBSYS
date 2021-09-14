package com.insigma.siis.local.business.entity;

public class Js19 implements  java.io.Serializable,Comparable<Js19>{

	private String JS0100;
	private String DC001;
	private String RB_ID;
	private String JS1902;
	private String JS1903;
	private String JS1904;
	private String JS1905;
	private String JS1906;
	private String JS1801;
	
	
	
	public Js19(String jS0100, String dC001, String rB_ID, String a0000,String jS1902, String jS1903, String jS1904,
			String jS1905, String jS1906, String jS1801) {
		super();
		JS0100 = jS0100;
		DC001 = dC001;
		RB_ID = rB_ID;
		JS1902 = jS1902;
		JS1903 = jS1903;
		JS1904 = jS1904;
		JS1905 = jS1905;
		JS1906 = jS1906;
		JS1801 = jS1801;
	}



	public String getJS0100() {
		return JS0100;
	}



	public void setJS0100(String jS0100) {
		JS0100 = jS0100;
	}



	public String getDC001() {
		return DC001;
	}



	public void setDC001(String dC001) {
		DC001 = dC001;
	}



	public String getRB_ID() {
		return RB_ID;
	}



	public void setRB_ID(String rB_ID) {
		RB_ID = rB_ID;
	}


	public String getJS1902() {
		return JS1902;
	}



	public void setJS1902(String jS1902) {
		JS1902 = jS1902;
	}



	public String getJS1903() {
		return JS1903;
	}



	public void setJS1903(String jS1903) {
		JS1903 = jS1903;
	}



	public String getJS1904() {
		if(this.JS1904==null || this.JS1904==""){
			return "0";
		}
		return JS1904;
	}



	public void setJS1904(String jS1904) {
		JS1904 = jS1904;
	}



	public String getJS1905() {
		if(this.JS1905==null || this.JS1905==""){
			return "0";
		}
		return JS1905;
	}



	public void setJS1905(String jS1905) {
		JS1905 = jS1905;
	}



	public String getJS1906() {
		return JS1906;
	}



	public void setJS1906(String jS1906) {
		JS1906 = jS1906;
	}



	public String getJS1801() {
		return JS1801;
	}



	public void setJS1801(String jS1801) {
		JS1801 = jS1801;
	}



	@Override
	public int compareTo(Js19 o) {
		return (stringtoInt(o.getJS1904())+stringtoInt(o.getJS1905()))-(stringtoInt(this.getJS1904())+stringtoInt(this.getJS1905()));
	}
	
	private int stringtoInt(String str){
		if(str==null || str==""){
			return 0;
		}else{
			return Integer.parseInt(str);
		}
	}
	
	
}
