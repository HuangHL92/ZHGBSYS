package com.insigma.siis.local.pagemodel.fxyp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.publicServantManage.AddPersonPageModel;

import net.sf.json.JSONArray;
    
public class TPRYXXZSPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@Synchronous(true)
	public int initX() throws RadowException {
		String a0000 = this.getPageElement("a0000").getValue();
		String location = this.getPageElement("location").getValue();
		String sql="select a01.A0101,a01.A0184,"
				+ "(select code_name from code_value where code_type = 'GB2261' and code_value.code_value = a01.a0104) A0104,"
				+ "substr(a01.a0107, 0, 4)||'.'||substr(a01.a0107, 5, 2) as a0107, substr(to_char(sysdate,'yyyyMM')-substr(a01.a0107,1,6),1,2) age, "
				+ "(select code_name  from code_value where code_type = 'GB3304'  and code_value.code_value = a01.a0117) A0117,"
				+ "a01.A0111a, a01.A0114a, "
				+ "(select code_name from code_value where code_type = 'ZB09'  and code_value.code_value = a01.a0148) A0148,"
				+ "(select code_name from code_value  where code_type = 'GB4762' and code_value.code_value = a01.a0141) A0141,"
				+ "a01.A0140, substr(a01.A0134, 0, 4)||'.'||substr(a01.A0134, 5, 2) as A0134, a01.A0128, a01.A0196, a01.A0187A, a01.QRZXL, a01.QRZXW,"
				+ " a01.QRZXLXX, a01.QRZXWXX,a01.ZZXL,a01.ZZXW, a01.ZZXLXX,a01.ZZXWXX,a01.A0192a,to_char(a01.A1701) as a1701,"
				+ "(case a01.A14z101 when '无' then '(无)' else nvl(a01.A14z101, '(无)') end) as A14z101,"
				+ " nvl(a01.A15Z101, '(无)') as A15Z101,nvl(b.a0193z,'(无)') as a0193z ,nvl(b.a0194z,'(无)') as a0194z,nvl(a01.dbwy,'(无)') as dbwy "
				+ "	 from A01 a01,extra_tags b  where a01.a0000 = '"+a0000+"' and a01.a0000=b.a0000(+)";
		try{
			CommQuery commQuery =new CommQuery();
			List<HashMap<String, Object>> list = commQuery.getListBySQL(sql);
			HashMap<String, Object> map=list.get(0);
			String qrzxl="";
			String qrzxlyx="";
			String zzxl="";
			String zzxlyx="";
			if(!"".equals(isnull(map.get("qrzxl")))&&!"".equals(isnull(map.get("qrzxw")))) {
				qrzxl=isnull(map.get("qrzxl"))+"<br>"+isnull(map.get("qrzxw"));
			}else if(!"".equals(isnull(map.get("qrzxl")))) {
				qrzxl=isnull(map.get("qrzxl"));
			}else if(!"".equals(isnull(map.get("qrzxw")))) {
				qrzxl=isnull(map.get("qrzxw"));
			}
			
			if(!"".equals(isnull(map.get("zzxl")))&&!"".equals(isnull(map.get("zzxw")))) {
				zzxl=isnull(map.get("zzxl"))+"<br>"+isnull(map.get("zzxw"));
			}else if(!"".equals(isnull(map.get("zzxl")))) {
				zzxl=isnull(map.get("zzxl"));
			}else if(!"".equals(isnull(map.get("zzxw")))) {
				zzxl=isnull(map.get("zzxw"));
			}
			
			if(!"".equals(isnull(map.get("qrzxlxx")))&&!"".equals(isnull(map.get("qrzxwxx")))&&!(isnull(map.get("qrzxlxx")).equals(map.get("qrzxwxx")))) {
				qrzxlyx=isnull(map.get("qrzxlxx"))+"<br>"+isnull(map.get("qrzxwxx"));
			}else if(!"".equals(isnull(map.get("qrzxlxx")))) {
				qrzxlyx=isnull(map.get("qrzxlxx"));
			}else if(!"".equals(isnull(map.get("qrzxwxx")))) {
				qrzxlyx=isnull(map.get("qrzxwxx"));
			}
			
			if(!"".equals(isnull(map.get("zzxlxx")))&&!"".equals(isnull(map.get("zzxwxx")))&&!(isnull(map.get("zzxlxx")).equals(map.get("zzxwxx")))) {
				zzxlyx=isnull(map.get("zzxlxx"))+"<br>"+isnull(map.get("zzxwxx"));
			}else if(!"".equals(isnull(map.get("zzxlxx")))) {
				zzxlyx=isnull(map.get("zzxlxx"));
			}else if(!"".equals(isnull(map.get("zzxwxx")))) {
				zzxlyx=isnull(map.get("zzxwxx"));
			}
			
			String a1701="";
			if(map.get("a1701")!=null&&!"".equals(map.get("a1701"))){
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				
				a1701 = formatJL(isnull(map.get("a1701")),originaljl);
				a1701=a1701.replaceAll(" ", "&ensp;");
				//a1701 = jianli.replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n", "<br>");
			}
			
			this.getExecuteSG().addExecuteCode("document.getElementById('iA0101i').innerHTML='"+isnull(map.get("a0101"))+"';"//姓名
			+ "document.getElementById('iA0104i').innerHTML='"+isnull(map.get("a0104"))+"';"//性别
			+ "document.getElementById('iA0107_1i').innerHTML='"+isnull(map.get("a0107"))+"<br>("+isnull(map.get("age"))+"岁)';"//出生年月
			+ "document.getElementById('iA0117i').innerHTML='"+isnull(map.get("a0117"))+"';"//民族
			+ "document.getElementById('iA0111Ai').innerHTML='"+isnull(map.get("a0111a"))+"';"//籍贯
			+ "document.getElementById('iA0114Ai').innerHTML='"+isnull(map.get("a0114a"))+"';"//出生地
			+ "document.getElementById('iA0140i').innerHTML='"+isnull(map.get("a0140"))+"';"//入党时间
			+ "document.getElementById('iA0134_1i').innerHTML='"+isnull(map.get("a0134"))+"';"//参加工作时间
			+ "document.getElementById('iA0128i').innerHTML='"+isnull(map.get("a0128"))+"';"//健康状况
			+ "document.getElementById('iA0196i').innerHTML='"+isnull(map.get("a0196"))+"';"//专业技术职务
			+ "document.getElementById('iA0187Ai').innerHTML='"+isnull(map.get("a0187a"))+"';"//特长
			+ "document.getElementById('iQRZXLi').innerHTML='"+qrzxl+"';"//全日制学历学位
			+ "document.getElementById('iQRZXLXXi').innerHTML='"+qrzxlyx+"';"//全日制毕业院校
			+ "document.getElementById('iZZXLi').innerHTML='"+zzxl+"';"//在职学历
			+ "document.getElementById('iZZXLXXi').innerHTML='"+zzxlyx+"';"//在职毕业院校
			+ "document.getElementById('iA0192Ai').innerHTML='"+isnull(map.get("a0192a"))+"';"//工作单位及职务
			+ "document.getElementById('iA1701i').innerHTML='"+a1701+"';"//简历
			+ "document.getElementById('iA14Z101i').innerHTML='"+isnull(map.get("a14z101"))+"';"//奖惩综述
			+ "document.getElementById('iA15Z101i').innerHTML='"+isnull(map.get("a15z101"))+"';"//考核综述
			+ "document.getElementById('a0193z').innerHTML='&nbsp;&nbsp;"+isnull(map.get("a0193z"))+"';"//经历标签
			+ "document.getElementById('a0194z').innerHTML='&nbsp;&nbsp;"+isnull(map.get("a0194z"))+"';"//熟悉领域标签
			+ "document.getElementById('dbwy').innerHTML='&nbsp;&nbsp;"+isnull(map.get("dbwy"))+"';");//代表委员
			
			
			String sql_36="select a3604a,a3601,case when a3611 like '%去世%' then '' else GET_BIRTHDAY(a36.a3607,to_char(sysdate,'yyyyMMdd')) end a3607,a3627,a3611"
					+ "  from A36 a36 where a0000 = '"+a0000+"' order by SORTID";
			List<HashMap<String, Object>> list_36 = commQuery.getListBySQL(sql_36);
			if(list_36!=null&&list_36.size()>0) {
				int x=7;
				if(list_36.size()<=7) {
					x=list_36.size();
				}
				for(int i=0;i<x;i++) {
					HashMap<String, Object> map_36=list_36.get(i);
					this.getExecuteSG().addExecuteCode("document.getElementById('iA3604A_"+(i+1)+"i').innerHTML='"+isnull(map_36.get("a3604a"))+"';"
							+ "document.getElementById('iA3601_"+(i+1)+"i').innerHTML='"+isnull(map_36.get("a3601"))+"';"
							+ "document.getElementById('iA3607_"+(i+1)+"i').innerHTML='"+isnull(map_36.get("a3607"))+"';"
							+ "document.getElementById('iA3627_"+(i+1)+"i').innerHTML='"+isnull(map_36.get("a3627"))+"';"
							+ "document.getElementById('iA3611_"+(i+1)+"i').innerHTML='"+isnull(map_36.get("a3611"))+"';");
				}
			}
			String sql_dldr="select a.a0000,nvl(substr(a0107,1,4)*12+substr(a0107,5,2),0) a0107,"
				+ " case when a0165 like '%10%' then '10' when a0165 like '%11%' then '11' else '12' end  a0165,"
				+ "	nvl(to_char(sysdate,'yyyy')*12+to_char(sysdate,'MM')-substr(a0192f,1,4)*12-substr(a0192f,5,2),0) a0192f, a0201b"
				+ "  from a01 a,(select a0000,max(case when substr(a0201b,1,15) in ('001.001.002.01O','001.001.002.01Q','001.001.002.02O') then '1' else '2' end) a0201b from a02 where a02.a0000='"+a0000+"' and a0281='true' group by a0000 ) b where a.a0000=b.a0000 and a.a0000='"+a0000+"'";
			List<HashMap<String, Object>> list_dldr = commQuery.getListBySQL(sql_dldr);
			if(list_dldr!=null&&list_dldr.size()>0) {
				HashMap<String, Object> map_dldr=list_dldr.get(0);
				int sys_month= 0;
				SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
				String date=df.format(new Date());
				String year=date.substring(0,4);
				String month=date.substring(4);
				sys_month=Integer.valueOf(year)*12+Integer.valueOf(month)+6;
				String dldr="";
				if("1".equals(map_dldr.get("a0201b"))){
					if( Integer.valueOf(map_dldr.get("a0107").toString())+59*12<sys_month){
						dldr=dldr+"半年内到龄，";
					}
				}else if("10".equals(map_dldr.get("a0165"))){
					if( Integer.valueOf(map_dldr.get("a0107").toString())+58*12<sys_month){
						dldr=dldr+"半年内到龄，";
					}
				}else if("11".equals(map_dldr.get("a0165"))){
					if( Integer.valueOf(map_dldr.get("a0107").toString())+57*12<sys_month){
						dldr=dldr+"半年内到龄，";
					}
				}
				if(Integer.valueOf(map_dldr.get("a0192f").toString())>54){
					dldr=dldr+"半年内到任，";
				}
				if(!"".equals(dldr)) {
					dldr=dldr.substring(0,dldr.length()-1);
				}else {
					dldr="(无)";
				}
				this.getExecuteSG().addExecuteCode("document.getElementById('dldr').innerHTML='&nbsp;&nbsp;"+dldr+"';");
			}
			String to_jd="@TO_GBDJD";
			String sql_jd = "select to_char(t.createtime, 'yyyyMMdd') time, a.reportcontent nr, t.punishtype type from xf_punish"+to_jd+" t left join xf_main"+to_jd+" a on t.p_oid = t.oid left join xf_breporter"+to_jd+" b on t.breporertoid = b.oid where b.a00 = '"+a0000+"' and a.status = '06'"
					+"  union select to_char(begintime,'yyyyMMdd') time,content nr,'诫勉' type from cy_punish"+to_jd+" where psnkey='"+a0000+"'"
					+"  union select to_char(a.createtime, 'yyyyMMdd') time, c.QUEINFO nr, '提醒' type from cy_tip"+to_jd+" a left join questioninfo"+to_jd+" c on c.REMIDID = a.oid where a.psnkey='"+a0000+"'"
					+"  union select to_char(sendtime,'yyyyMMdd') time,checkcontent nr,'函询' type from cy_docquery"+to_jd+" where psnkey='"+a0000+"'  order by time";
			List<HashMap<String, Object>> list_jd=commQuery.getListBySQL(sql_jd);
			
			if(list_jd!=null&&list_jd.size()>0){
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list_jd);
				this.getExecuteSG().addExecuteCode("Add("+updateunDataStoreObject1+");");
			}
			
			this.getExecuteSG().addExecuteCode("updateImg('"+URLEncoder.encode(URLEncoder.encode(a0000,"UTF-8"),"UTF-8")+"');");
			this.getExecuteSG().addExecuteCode("document.getElementById('sp"+location+"').click();");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}[ ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1);
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	//originaljl.append(jl);
			        	originaljl.append(jl).append("<br>");//一段简历结束拼上回车
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("<br>");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("<br>");
		        }
			}
			if(jlsb.lastIndexOf("\r\n")==jlsb.length()-2 ){
				jlsb.delete(jlsb.length()-2, jlsb.length());
        	}
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		String Reg="^[\u4e00-\u9fa5]{1}|[（）]{1}$";//正则
	    int result=0;
	    float ansiCharNumber = 0;
	    for(int i=0;i<line2.length();i++){
	        String b=Character.toString(line2.charAt(i));
	        if(b.matches(Reg))result++;
	        else ansiCharNumber+=0.5;//modify zepeng 20171223 原计算字符个数的办法存在问题忽略了半角字符的宽度
	     }
	    int hzlength=result+Math.round(ansiCharNumber)+1;
		
		int llength = line2.length();
		//25个字一行。
		int oneline = 26;
    	if(hzlength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 50个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//25个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//25个字一行但不足50个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行51个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				//jlsb.append(l);
    				jlsb.append(l).append("<br>");
    				hass = true;
    			}else{
    				//jlsb.append("                  ").append(l);
    				jlsb.append("                  ").append(l).append("<br>");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			//jlsb.append(line2);
    			jlsb.append(line2).append("<br>");
    		}else{
    			//jlsb.append("                  ").append(line2);
    			jlsb.append("                  ").append(line2).append("<br>");
    		}
    	}
	}
	
	public static int gbkCount(String text) {
	    String Reg="^[\u4e00-\u9fa5]{1}|[（）]{1}$";//正则
	    int result=0;
	    float ansiCharNumber = 0;
	    for(int i=0;i<text.length();i++){
	        String b=Character.toString(text.charAt(i));
	        if(b.matches(Reg))result++;
	        else ansiCharNumber+=0.5;//modify zepeng 20171223 原计算字符个数的办法存在问题忽略了半角字符的宽度
	     }
	    return result+Math.round(ansiCharNumber)+1;
    }
	
	public String isnull(Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;
	}
	
	
}
