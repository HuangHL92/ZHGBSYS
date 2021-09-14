package com.insigma.siis.local.pagemodel.zj.slabel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class JSGLBS {
	
	private PageModel pm;
	
	private static String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		return upload_file;
	}
	public final static String HZBPATH = getPath();
	public PageModel getPm() {
		return pm;
	}
	public void setPm(PageModel pm) {
		this.pm = pm;
	}
	public JSGLBS() {
		
	}
	public JSGLBS(PageModel pm) {
		super();
		this.pm = pm;
	}

	@SuppressWarnings({ "unchecked" })
	public String getPersonInfo(List<Object[]>  list,HBSession sess){
		String data = "(lt;span style=\"color:rgb(121,0,255);\">当前人员：(lt;/span>";
		Object[] info=list.get(0);
		Object a0000=info[0];
		Object a0101=info[1];//姓名
		if(a0101!=null){
			data = data+a0101.toString()+"，";
		}
		Object a0104=info[2];//性别
		if(a0104!=null){
			String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '"+a0104+"'";
			a0104 = sess.createSQLQuery(s).uniqueResult();
			data = data + a0104.toString() + "，";
		}
		Object a0107=info[3];//出生年月
		if(a0107!=null){
			String reg = "^[0-9]{6}$";
			String reg2 = "^[0-9]{8}$";
			if(a0107.toString().matches(reg) || a0107.toString().matches(reg2)){
				String msg = getAgeNew(a0107.toString());
				data = data + msg + "，";
			}
		}
		Object a0117=info[4];//民族
		if(a0117!=null){
			String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
			a0117 = sess.createSQLQuery(s).uniqueResult();
			data = data + a0117.toString() + "，";
		}
		Object a0111=info[5];//籍贯
		if(a0111!=null){
			data = data + a0111.toString() + "人，";
		}
		Object a0140=info[6];//入党时间
		if(a0140!=null){
			String reg = "[0-9]{4}\\.[0-9]{2}";
			Pattern p1 = Pattern.compile(reg); 
		    Matcher matcher = p1.matcher(a0140.toString());
		    if (matcher.find()) {
		    	String s = matcher.group();
		    	s = s.replace(".", "年");
		    	data = data + s + "月入党，";
			}
		}
		Object a0134=info[7];//参加工作时间
		if(a0134!=null){
			String reg = "^[0-9]{6}$";
			String reg2 = "^[0-9]{8}$";
			if(a0134.toString().matches(reg) || a0134.toString().matches(reg2)){
				String year = a0134.toString().substring(0, 4);
				String month = a0134.toString().substring(4, 6);
				data = data + year + "年" + month + "月参加工作";
			}
			
		}
		
		//最高学历
		String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '"+a0000+"'";
		
		List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();
		
		if(zgxlS.size() > 0){
			Object zgxl = zgxlS.get(0);
			
			if(zgxl != null){
				data = data  + "，" + zgxl.toString()+"学历";
			}
		}
		
		//最高学位
		String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a0000+"'";
		List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();
		if(zgxwS.size() > 0){
			Object zgxw = zgxwS.get(0);
			
			if(zgxw != null){
				zgxw = zgxw.toString().replace("学位", "");
				data = data + "，" + zgxw.toString() + "。";
			}else{
				data = data + "。";
			}
		}else{
			data = data + "。";
		}
		//专业技术职务
		Object zyjs = info[8];	
		String zyjsStr = "";
		if(zyjs != null && !zyjs.equals("")){
			zyjsStr = zyjs.toString();
		}
		zyjsStr = zyjsStr.replace(" ", "");
		if(zyjsStr != null && !zyjsStr.equals("")){
			data = data + zyjsStr.toString() + "。(lt;br/>";
		}
		Object zwOb = info[9];		//工作单位及职务
		String zw = "";
		if(zwOb != null && !zwOb.equals("")){
			zw = zwOb.toString();
		}
		zw = zw.replace(" ", "");
		if(zw!=null && !zw.equals("")){
			data = data + "现任"+zw.toString() + "，";
		}
		data = data.substring(0, data.length()-1) + "。";
		return data;
	}
	
	public static String getAgeNew(String value) {
		int returnAge;

		String birthYear = value.toString().substring(0, 4);
		String birthMonth = value.toString().substring(4, 6);
		String birthDay = "";
		if(value.length()==6){
			birthDay = "01";
		}
		if(value.length()==8){
			birthDay = value.toString().substring(6, 8);
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(d);
		String nowYear = s.toString().substring(0, 4);
		String nowMonth = s.toString().substring(4, 6);
		String nowDay = s.toString().substring(6, 8);
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // 同年返回0岁
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // 年只差
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// 出生日期错误 晚于今年
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "岁)";
		String msg = "" + returnAge + "岁(" + birthYear + "年" + birthMonth + "月生)";
		return msg;
	}
	
	public static void outPrintln(PrintWriter out, String message) {
		out.println(message + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public static void outPrint(PrintWriter out, String message) {
		out.println(message);
		out.flush();
	}
	public static void outPrintlnErr(PrintWriter out, String message) {
		out.println("<FONT  COLOR=\"#FF0066\">" + message + "</FONT><br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public static void outPrintlnSuc(PrintWriter out, String message) {
		out.println("<FONT  COLOR=\"#669900\">" + message + "</FONT>" + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public static void outDownZip(PrintWriter out, String downloadUUID) {
		out.println("<script type=\"text/javascript\">function downloadFile(){"
				+ "window.location='PublishFileServlet?method=downloadFile&uuid="+downloadUUID+"';"
				+ "}</script>");
		out.println("<button onclick='downloadFile()'>点击下载数据包</button>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public static PrintWriter initOutPut(HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>wu</title><style type=\"text/css\">\t.{\t\tfont-size: 12px;\t}\t.red{\t\tcolor: red;\t}</style></head><body>");
		out.println("<script type=\"text/javascript\">document.oncontextmenu=rightMouse;function rightMouse() {return false;} </script>");
		out.flush();
		return out;
	}
	public static void endOutPut(PrintWriter out) throws IOException {
		out.println("</body></html>");
	}
	
	
	
	 /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	
	 /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        /*// 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;*/
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            //System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
	public void deletePersonInfoByRB_ID(String rb_id, HBSession sess) throws Exception {
		//
		
		HBUtil.executeUpdate("delete from js02 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js03 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js04 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js05 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js06 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js07 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js08 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js09 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js10 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js11 where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js_hj where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});

		HBUtil.executeUpdate("delete from deploy_classify where rb_id=? and dc005!='3'",new Object[]{rb_id});
		HBUtil.executeUpdate("delete from js_att where js0100 in(select js0100 from js01 where rb_id=?)",new Object[]{rb_id});

		HBUtil.executeUpdate("delete from js01 where rb_id=?",new Object[]{rb_id});
		
		HBUtil.executeUpdate("delete from record_batch where rb_id=?",new Object[]{rb_id});
	}
}
