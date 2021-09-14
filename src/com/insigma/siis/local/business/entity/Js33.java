package com.insigma.siis.local.business.entity;

public class Js33 implements  java.io.Serializable,Comparable<Js33>{
	   private String js3301;
	   private String js0100;
	   private String dc001;
	   private String rb_id;
	   private String js1801;
	   private String js3302;
	   private String js3303;
	   private String js3304;
	   private String js3305;
	   private String js3306;
	   private String js3307;
	   private String js3308;
	   private String js3309;
	   private String js3310;
	   private String js3311;
	   private String js3312;
	   
		public String getJs3301() {
			return js3301;
		}
		public void setJs3301(String js3301) {
			this.js3301 = js3301;
		}
		public String getJs0100() {
			return js0100;
		}
		public void setJs0100(String js0100) {
			this.js0100 = js0100;
		}
		public String getDc001() {
			return dc001;
		}
		public void setDc001(String dc001) {
			this.dc001 = dc001;
		}
		public String getRb_id() {
			return rb_id;
		}
		public void setRb_id(String rb_id) {
			this.rb_id = rb_id;
		}
		public String getJs1801() {
			return js1801;
		}
		public void setJs1801(String js1801) {
			this.js1801 = js1801;
		}
		public String getJs3302() {
			return js3302;
		}
		public void setJs3302(String js3302) {
			this.js3302 = js3302;
		}
		public String getJs3303() {
			return js3303;
		}
		public void setJs3303(String js3303) {
			this.js3303 = js3303;
		}
		public String getJs3304() {
			return js3304;
		}
		public void setJs3304(String js3304) {
			this.js3304 = js3304;
		}
		public String getJs3305() {
			return js3305;
		}
		public void setJs3305(String js3305) {
			this.js3305 = js3305;
		}
		public String getJs3306() {
			return js3306;
		}
		public void setJs3306(String js3306) {
			this.js3306 = js3306;
		}
		public String getJs3307() {
			return js3307;
		}
		public void setJs3307(String js3307) {
			this.js3307 = js3307;
		}
		public String getJs3308() {
			return js3308;
		}
		public void setJs3308(String js3308) {
			this.js3308 = js3308;
		}
		public String getJs3309() {
			return js3309;
		}
		public void setJs3309(String js3309) {
			this.js3309 = js3309;
		}
		public String getJs3310() {
			return js3310;
		}
		public void setJs3310(String js3310) {
			this.js3310 = js3310;
		}
		public String getJs3311() {
			return js3311;
		}
		public void setJs3311(String js3311) {
			this.js3311 = js3311;
		}
		public String getJs3312() {
			return js3312;
		}
		public void setJs3312(String js3312) {
			this.js3312 = js3312;
		}
		
		@Override
		public int compareTo(Js33 o) {
			return (stringtoInt(o.getJs3305())+stringtoInt(o.getJs3308()))-(stringtoInt(this.getJs3305())+stringtoInt(this.getJs3308()));
		}
		
		private int stringtoInt(String str){
			if(str==null || str==""){
				return 0;
			}else{
				return Integer.parseInt(str);
			}
		}
   
}
