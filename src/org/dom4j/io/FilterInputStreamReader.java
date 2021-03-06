package org.dom4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * 该类继承自InputStreamReader， 主要解决 dom4j解析xml时出现非法字符。<br/>
 * xml官方定义的非法字符范围为： [0x00 - 0x08], [0x0b - 0x0c], [0x0e - 0x1f], 这些都是无法打印的低阶
 * assii符号。 <br/>
 * 实现原理为： SAXReader 的read()方法需要一个 Reader 对象， 在SAXReader的内部，会调用
 * 输入Reader的read(char cbuf[], int offset, int length) 方法读取内容， 因此，本过滤器 包装了 read
 * 方法，对非法字符进行过滤，实际为替换(用空格替换)，采用替换可能会带来一定问题，例如：非法字符正好在名称中间，可能会导致解析异常。但采用过滤会
 * read(char cbuf[], int offset, int length)
 * 包装上带来困难，例如：过滤后的缩进如何处理，目前方案1为，用read()方法替换。 方案2. 减少读取字符数。 read返回数量减少。
 * 
 * <br/>
 * 具体后续遇到再讨论处理
 * 
 * @author xujg
 * @date 2012-1-13
 * 
 */
public class FilterInputStreamReader extends InputStreamReader {
	private static final int replaceChar = 0x20;// 空格
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