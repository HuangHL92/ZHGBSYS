package shudu;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.utils.kingbs.EncryptUtil;

//import utils.system;

public class Cell {

	/**
	 * 可选的数字
	 */
	private List<Integer> optionalNumbers = new ArrayList<Integer>();
	
	/**
	 * 当前填选的数字
	 */
	private Integer currentNumber;
	
	/**
	 * 当前选择的数字
	 */
	private Integer selectedNumber;
	
	/**
	 * 是否可填
	 */
	private final boolean isWriteable;

	
	/**
	 * 获取下一个可选的数字
	 * @return
	 */
	public Integer getNextOptionalNumber(){
		int i = this.getOptionalNumbers().indexOf(this.getSelectedNumber());
		if((i+1)<this.getOptionalNumbers().size()){
			this.setSelectedNumber(this.getOptionalNumbers().get(i+1));
			return this.getSelectedNumber();
		}
		return -1;
	}
	
	/**
	 * 获取上一个可选的数字
	 * @return
	 */
	public Integer getPrevOptionalNumber(){
		int i = this.getOptionalNumbers().indexOf(this.getSelectedNumber());
		if(i>0){
			this.setSelectedNumber(this.getOptionalNumbers().get(i-1));
			return this.getSelectedNumber();
		}
		return -1;
	}
	
	public Cell(Integer currentNumber) {
		this.currentNumber = currentNumber;
		if(currentNumber == 0){
			this.isWriteable = true;
		}else{
			this.isWriteable = false;
		}
	}
	
	
	
	public List<Integer> getOptionalNumbers() {
		return optionalNumbers;
	}

	public void setOptionalNumbers(List<Integer> optionalNumbers) {
		this.optionalNumbers = optionalNumbers;
	}

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}

	public boolean isWriteable() {
		return isWriteable;
	}

	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String a = "dsdsa";
		
		a = a.replaceAll("\u001a", "");
		
		System.out.println((int)'');
		System.out.println((char)0x1a);
		
		/*String.format("%05s", "dsasa");*/
		//System.out.println(Integer.valueOf("-1111111111111111111111111111111", 2));
		//System.out.println(System.nanoTime());
		
		/*Object obj = new Object();
		WeakReference<Object> wf = new WeakReference<Object>(obj);
		obj = null;
		wf.get();//有时候会返回null
		System.out.println(wf.get());*/
		
		
		/*Date dd = new Date();
		dd.setMonth(10);
		System.out.println(new SimpleDateFormat("yyyy年M月dd日").format(dd));
		
		String aa=EncryptUtil.decrypt("8988#@$6642#@$6228#@$11196#@$6297#@$7746#@$6366#@$9540#@$6435#@$11196#@$6504#@$9678#@$6228#@$6366#@$9540#@$6573#@$");
		String aa1=EncryptUtil.decrypt("10989#@$10230#@$9954#@$6366#@$6435#@$6228#@$6573#@$6228#@$6642#@$");
		
		System.out.println(aa);
		System.out.println("dsa23csfd".indexOf(2+""));
		
		
		
		System.err.println((int)Math.sqrt(4)) ;
		
		Date d = new Date(-88, 6, 11);
		
		System.out.println(d.toString());*/
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Integer getSelectedNumber() {
		return selectedNumber;
	}

	public void setSelectedNumber(Integer selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	@Override
	public String toString() {
		return "Cell [optionalNumbers=" + optionalNumbers + ", currentNumber=" + currentNumber + ", selectedNumber="
				+ selectedNumber + ", isWriteable=" + isWriteable + "]";
	}
	
	
}
