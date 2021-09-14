package com.insigma.siis.local.pagemodel.dataverify;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class DeleteTempThread implements Runnable {
	
	private String temp;
	
	public DeleteTempThread(String temp) {
		this.temp = temp;
	}

	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		try {
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B01_EXT", "INFO_EXTEND"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" delete from " + tables1[i] + "_temp where imprecordid='"
						+ temp + "'").executeUpdate();
				
			}
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
