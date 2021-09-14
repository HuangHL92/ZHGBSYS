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
		
		submit.setEcname("中国共产党无锡市委员会组织部"); //集团客户名称
		submit.setApid("zzbmes"); //用户吿
		submit.setSecreKey("1nte@t!dEcm_2015"); //密码
		submit.setMobiles("15267434925"); //多个号码仿,分割
		submit.setContent("党员Xxx您好，Xxx（创建活动的党组织名称）将于X月X日Xx：Xx在Xx（地点）召开Xxxx（活动类型），主题为Xxxx，请您按时参加！"); //短信内容，会自动加上短信前面 无锡市委组织郿
		submit.setSign("wlyA9zMH5"); //网关签名
		submit.setAddSerial(""); //扩展砿
		
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
		
		
		
		submit.setEcname("中国共产党无锡市委员会组织部"); //集团客户名称
		submit.setApid("zzbmes"); //用户吿
		submit.setSecreKey("1nte@t!dEcm_2015"); //密码
		submit.setMobiles(account); //多个号码仿,分割
		submit.setContent(content); //短信内容，会自动加上短信前面 无锡市委组织郿
		submit.setSign("wlyA9zMH5"); //网关签名
		submit.setAddSerial(""); //扩展砿
		
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
	 * MD5加密
	 * @param plainText  明文
	 * @return 32位密斿
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
     * 向指宿 URL 发POST方法的请
     *
     * @param url   发请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @return 承代表远程资源的响应结
     */
    public static  String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连掿
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属怿
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发请求参敿
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响庿
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发 POST 请求出现异常＿" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流输入流
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
