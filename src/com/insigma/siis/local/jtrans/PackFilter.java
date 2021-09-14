package com.insigma.siis.local.jtrans;

import java.io.File;
import java.io.FilenameFilter;
/**
 * �ļ���������
 * @author gezh
 *
 */
public class PackFilter implements FilenameFilter {
	private String sw; // ��ʼ��
	private String ew; // ������
	public String getSw() {
		return sw;
	}
	public void setSw(String sw) {
		this.sw = sw;
	}
	public String getEw() {
		return ew;
	}
	public void setEw(String ew) {
		this.ew = ew;
	}
	public PackFilter(String sw, String ew) {
		super();
		this.sw = sw;
		this.ew = ew;
	}
	public boolean accept(File dir, String name) {
		if (name.toLowerCase().startsWith(sw.toLowerCase())
				&& name.toLowerCase().endsWith(ew.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
}