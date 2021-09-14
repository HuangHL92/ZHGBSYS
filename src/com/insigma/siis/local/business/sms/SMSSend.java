package com.insigma.siis.local.business.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/*import org.apache.tomcat.util.codec.binary.Base64;

import com.alibaba.fastjson.JSON;*/

public class SMSSend {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		SubmitReq submit = new SubmitReq();
		
		submit.setEcname("�й�������������ίԱ����֯��"); //���ſͻ�����
		submit.setApid("zzbmes"); //�û���
		submit.setSecreKey("1nte@t!dEcm_2015"); //����
		submit.setMobiles("15267434925"); //��������,�ָ�
		submit.setContent("��ԱXxx���ã�Xxx��������ĵ���֯���ƣ�����X��X��Xx��Xx��Xx���ص㣩�ٿ�Xxxx������ͣ�������ΪXxxx��������ʱ�μӣ�"); //�������ݣ����Զ����϶���ǰ�� ������ί��֯�d
		submit.setSign("wlyA9zMH5"); //����ǩ��
		submit.setAddSerial(""); //��չ�m
		
		StringBuffer sb = new StringBuffer();
		sb.append(submit.getEcname());
		sb.append(submit.getApid());
		sb.append(submit.getSecreKey());
		sb.append(submit.getMobiles());
		sb.append(submit.getContent());
		sb.append(submit.getSign());
		sb.append(submit.getAddSerial());
		System.out.println(sb);
		
		submit.setMac(encryption(sb.toString()));

		
		String data = JSONObject.fromObject(submit).toString();

		String encode = new BASE64Encoder().encode(data.getBytes("utf-8"));
		
		String result = sendPost("http://2.20.114.84:1992/sms/norsubmit",encode);
		//String result = sendPost("http://2.20.114.84:1992/sms/norsubmit",encode);
		System.out.println(result);
	}
	
	
	public static String send(String account,String content) throws UnsupportedEncodingException{
		SubmitReq submit = new SubmitReq();
		
		
		
		submit.setEcname("�й�������������ίԱ����֯��"); //���ſͻ�����
		submit.setApid("zzbmes"); //�û���
		submit.setSecreKey("1nte@t!dEcm_2015"); //����
		submit.setMobiles(account); //��������,�ָ�
		submit.setContent(content); //�������ݣ����Զ����϶���ǰ�� ������ί��֯�d
		submit.setSign("wlyA9zMH5"); //����ǩ��
		submit.setAddSerial(""); //��չ�m
		
		StringBuffer sb = new StringBuffer();
		sb.append(submit.getEcname());
		sb.append(submit.getApid());
		sb.append(submit.getSecreKey());
		sb.append(submit.getMobiles());
		sb.append(submit.getContent());
		sb.append(submit.getSign());
		sb.append(submit.getAddSerial());
		
		submit.setMac(encryption(sb.toString()));
		
		
		String data = JSONObject.fromObject(submit).toString();

		String encode = new BASE64Encoder().encode(data.getBytes("utf-8"));
		
		String result = sendPost("http://2.20.114.84:1992/sms/norsubmit",encode);
		
		System.out.println(result);
		return result;
	}
	
	

	
	/**
	 * MD5����
	 * @param plainText  ����
	 * @return 32λ�ܔ�
	 */
	public static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	
	/**
     * ��ָ�� URL ��POST��������
     *
     * @param url   ������� URL
     * @param param ����������������Ӧ���� name1=value1&name2=value2 ����ʽ
     * @return �д���Զ����Դ����Ӧ��
     */
    public static  String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ��POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // ������Δ�
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL�����
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("�� POST ��������쳣��" + e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر������������
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
