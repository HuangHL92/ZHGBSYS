package org.dom4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * ����̳���InputStreamReader�� ��Ҫ��� dom4j����xmlʱ���ַǷ��ַ���<br/>
 * xml�ٷ�����ķǷ��ַ���ΧΪ�� [0x00 - 0x08], [0x0b - 0x0c], [0x0e - 0x1f], ��Щ�����޷���ӡ�ĵͽ�
 * assii���š� <br/>
 * ʵ��ԭ��Ϊ�� SAXReader ��read()������Ҫһ�� Reader ���� ��SAXReader���ڲ��������
 * ����Reader��read(char cbuf[], int offset, int length) ������ȡ���ݣ� ��ˣ��������� ��װ�� read
 * �������ԷǷ��ַ����й��ˣ�ʵ��Ϊ�滻(�ÿո��滻)�������滻���ܻ����һ�����⣬���磺�Ƿ��ַ������������м䣬���ܻᵼ�½����쳣�������ù��˻�
 * read(char cbuf[], int offset, int length)
 * ��װ�ϴ������ѣ����磺���˺��������δ���Ŀǰ����1Ϊ����read()�����滻�� ����2. ���ٶ�ȡ�ַ����� read�����������١�
 * 
 * <br/>
 * ����������������۴���
 * 
 * @author xujg
 * @date 2012-1-13
 * 
 */
public class FilterInputStreamReader extends InputStreamReader {
	private static final int replaceChar = 0x20;// �ո�
	private int L = 0;

	public FilterInputStreamReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
		super(in, charsetName);
	}

	//
	public int read() throws IOException {
		int ch = super.read();
		CommonQueryBS.systemOut("read" + ch);
		if (ch > 0x1f)
			return ch;
		if (ch == 0x0d || ch == -1 || (ch > 0x08 && ch < 0x0b))
			return ch;
		return replaceChar;
	}

	public int read(char cbuf[], int offset, int length) throws IOException {
		L++;
		int count = super.read(cbuf, offset, length);
		if (L == 1) {
			for (int i = 0; i < count; i++) {
				if (i == 0 && cbuf[0] != '<') {
					cbuf[0] = replaceChar;
					continue;
				}
				if(cbuf[i]==0xffff){//0xffff
            		cbuf[i]=replaceChar;
            		continue; 
				}
				if (cbuf[i] == '&' && (i + 4) < count) {// &#31;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '3' && (cbuf[i + 3] == '1' || cbuf[i + 3] == '0') && cbuf[i + 4] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
							cbuf[i + 4] = replaceChar;

						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 4) < count) {// &#11;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '1' && (cbuf[i + 3] == '1' || cbuf[i + 3] == '0') && cbuf[i + 4] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
							cbuf[i + 4] = replaceChar;

						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 3) < count) {// &#7;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '7' && cbuf[i + 3] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 3) < count) {// &#1;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '1' || cbuf[i + 2] == 'l')
							if (cbuf[i + 3] == ';') {
								cbuf[i] = replaceChar;
								cbuf[i + 1] = replaceChar;
								cbuf[i + 2] = replaceChar;
								cbuf[i + 3] = replaceChar;
							}
					}
					continue;
				}
				if (cbuf[i] > 0x1f)
					continue;
				if (cbuf[i] == 0x0d || cbuf[i] == -1 || (cbuf[i] > 0x08 && cbuf[i] < 0x0b))
					continue;

				cbuf[i] = replaceChar;
			}
		} else {
			for (int i = 0; i < count; i++) {
				if(cbuf[i]==0xffff){//0xffff
            		cbuf[i]=replaceChar;
            		continue; 
            	}
				if (cbuf[i] == '&' && (i + 4) < count) {// &#31;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '3' && (cbuf[i + 3] == '1' || cbuf[i + 3] == '0') && cbuf[i + 4] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
							cbuf[i + 4] = replaceChar;
						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 4) < count) {// &#11;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '1' && (cbuf[i + 3] == '1' || cbuf[i + 3] == '0') && cbuf[i + 4] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
							cbuf[i + 4] = replaceChar;
						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 3) < count) {// &#7;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '7' && cbuf[i + 3] == ';') {
							cbuf[i] = replaceChar;
							cbuf[i + 1] = replaceChar;
							cbuf[i + 2] = replaceChar;
							cbuf[i + 3] = replaceChar;
						}
					}
					continue;
				}
				if (cbuf[i] == '&' && (i + 3) < count) {// &#1;
					if (cbuf[i + 1] == '#') {
						if (cbuf[i + 2] == '1' || cbuf[i + 2] == 'l')
							if (cbuf[i + 3] == ';') {
								cbuf[i] = replaceChar;
								cbuf[i + 1] = replaceChar;
								cbuf[i + 2] = replaceChar;
								cbuf[i + 3] = replaceChar;
							}
					}
					continue;
				}
				if (cbuf[i] > 0x1f)
					continue;
				if (cbuf[i] == 0x0d || cbuf[i] == -1 || (cbuf[i] > 0x08 && cbuf[i] < 0x0b))
					continue;

				cbuf[i] = replaceChar;
			}
		}

		return count;
	}
}