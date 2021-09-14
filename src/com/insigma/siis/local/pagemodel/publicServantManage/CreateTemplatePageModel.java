package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;

public class CreateTemplatePageModel extends PageModel{
	
	public static String tpid = "";
	public CreateTemplatePageModel(){

	}
	
	@Override
	public int doInit() throws RadowException {
//		String tpname = this.request.getSession().getAttribute("tpname").toString();
		String tpname = (String)this.request.getSession().getAttribute("tpname");
		this.getPageElement("isedit").setValue((this.request.getSession().getAttribute("isedit")).toString());
//		System.out.println((this.request.getSession().getAttribute("temtype")).toString());
		this.getPageElement("temtype").setValue((this.request.getSession().getAttribute("temtype")).toString());
		if(!"1".equals((this.request.getSession().getAttribute("temtype")).toString())){
			String sql = "select tpid from listoutput2 where tpname='"+tpname+"' group by tpid";
			try{
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
				while(rs.next()){
					this.getPageElement("tpname").setValue(rs.getString(1));
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else{
			this.getPageElement("tpname").setValue(tpname);
		}
		this.getPageElement("tpkind").setValue((this.request.getSession().getAttribute("tpkind")).toString());
		this.getExecuteSG().addExecuteCode("pageinit();");
		this.request.getSession().removeAttribute("isphoto");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//解析单行无用
	/*	@PageEvent("openDataVerifyWin")
		public void  openDataVerifyWin(String xmlDoc) throws RadowException{
			System.out.println(xmlDoc);
			//创建一个新的字符串
	        StringReader read = new StringReader(xmlDoc);
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	        InputSource source = new InputSource(read);
	        //创建一个新的SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	        try {
	            //通过输入源构造一个Document
	            Document doc = sb.build(source);
	            //取的根元素
	            Element root = doc.getRootElement();
	            System.out.println(root.getName());//输出根元素的名称（测试）
	            //得到根元素所有子元素的集合
	            List jiedian = root.getChildren();
	            Namespace ns = root.getNamespace();
	            System.out.println(jiedian);
	            Element et = null;
//	            for(int i=0;i<jiedian.size();i++){
//	                et = (Element) jiedian.get(i);//循环依次得到子元素
//	               System.out.println(et);
	//
//	            }
	           //获得Worksheet节点
	            et = (Element) jiedian.get(1);
	            //获得Worksheet节点下的子节点Table节点
	            List Table = et.getChildren();
	            //获取Table节点
	            et = (Element) Table.get(0);
	            System.out.println(et.getAttributeValue("Index", ns));
	            //获取Table节点下的子节点row节点
	            List row = et.getChildren();
	            //如果row>0就说明有数据
	            if(row.size()>0){
	            	for(int i =0;i<row.size();i++){}
	            	//获取row节点
	            	et = (Element) row.get(0);
	            	System.out.println(et.getAttributeValue("Index", ns)+"行标");
	            	//获取row节点下面的子节点
	            	List cell = et.getChildren();
	            	System.out.println(cell.size());
	            	for(int j=0;j<cell.size();j++){
	            		//获取cell节点
	            		et = (Element) cell.get(j);
	            		System.out.println(et.getAttributeValue("Index", ns)+"列标");
	            		System.out.println(et.getChild("Data",ns).getText()+"内容");
	            		//获取cell节点下面的子节点Data
	            		List Data = et.getChildren();
	            		//获取Data节点
//	            		et = (Element) Data.get(0);
//	            		System.out.println(et.getChild("Data",ns).getText());
	            	}
	            	
	            }else{
	            	System.out.println("无数据");
	            }
	            
	            
	            
	        } catch (JDOMException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        }
			
			
			
			
		}*/
	
	/**
	 * 给模板名加上日期yyyy年MM月
	 * @throws RadowException
	 */
	public String addCurDate() throws RadowException{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		String date = sdf.format(cal.getTime());
		return date;
	}
	/*
	 * 多行解析
	 * xmlDoc 传入的xml字符串参数
	 * TPname   模板名称
	 * TPtype  模板类型
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String xmlDoc) throws RadowException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String time = sf.format(date);
        time = time.substring(0,time.length()-1);
//		System.out.println(xmlDoc);
		//创建一个新的字符串
		StringReader read = new StringReader(xmlDoc);
		//创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		//创建一个新的SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		//一个格子由三个map确定，k（1行，2列，3内容）v（行标，列表，内容）
		//Map<String, String> map = new HashMap<String, String>();
		try {
			//通过输入源构造一个Document
			Document doc = sb.build(source);
			//取的根元素
			Element root = doc.getRootElement();
			//输出根元素的名称（测试）
//			System.out.println(root.getName());
			//得到根元素所有子元素的集合
			List jiedian = root.getChildren();
			Namespace ns = root.getNamespace();
			int n = 0;
			n = jiedian.size();
			String uuid = UUID.randomUUID().toString();
			int p = 1;
			int a = 0;//控制删除的次数，在jiexi方法里面
			String TPname = this.getPageElement("tname").getValue();
			String houzhui = (String)request.getSession().getAttribute("namehouzhui");
			if(houzhui != null && (!TPname.contains("【表格】")&&!TPname.contains("【标准名册】")&&!TPname.contains("【花名册】"))){
				TPname += houzhui;
				request.getSession().removeAttribute("namehouzhui");
			}
			if(houzhui != null && "【花名册】".equals(houzhui)){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
				String date1 = sdf.format(cal.getTime());
				TPname = date1+TPname;
			}
			String TPname1 = this.getPageElement("tpname").getValue();
			String isedit = this.getPageElement("isedit").getValue();
			String tpkind = this.getPageElement("tpkind").getValue();
			if("1".equals(isedit)){
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput t where t.tpname='"+TPname1+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("模板名重复！请更改命名！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput2 t where t.tpname='"+TPname+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("模板名重复！请更改命名！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			for(int m = 0;m < jiedian.size()-1;m++ ){
				//list里面存放map
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				n--;
				Element et = null;
				//获得Worksheet节点
				et = (Element) jiedian.get(n);
				 //获取页数
	            String page = et.getAttributeValue("Name", ns);
	            String PageNu = n+"";
				//获得Worksheet节点下的子节点Table节点
				List Table = et.getChildren();
				//获取Table节点
				et = (Element) Table.get(0);
				//获取Table节点下的子节点row节点
				List row = et.getChildren();
				//定义连个变量拼接字符串
				String messageE = "";
				String MessageA = "";
				//如果row>0就说明有数据
				if (row.size() > 0) {
					//循环遍历row获取每一个row
					for (int i = 0; i < row.size(); i++) {
						//获取row节点
						et = (Element) row.get(i);
						//获取标签里面的内容
						String row1 = et.getAttributeValue("Index", ns);
						//1表示行
						//获取row节点下面的子节点
						List cell = et.getChildren();
						for (int j = 0; j < cell.size(); j++) {
							//获取cell节点
							et = (Element) cell.get(j);
							//获取标签里面的内容
							String cell1 = et.getAttributeValue("Index", ns);
							//2表示列
							//获取数据信息
							String message = et.getChild("Data", ns).getText();
							//判断内容是不是需要的保存的内容（<>内的数据是要保存到数据库的）
							if (message.contains("<")) {
								//获取的内容去掉最后的 >
								String Message1 = message.substring(message.indexOf("<"), message.lastIndexOf(">"));
								//按>进行截取
								String[] split1 = Message1.replace("<", "").split(">");
								for(int q = 0; q < split1.length ; q++){
									//遍历数组获取内容进行拼接字符串
									MessageA += split1[q]+",";
									//获取每一个信息项
									String Message = split1[q];
									//每一个信息项与定义好信息行进行匹配，获取去信息项英文之后拼接英文信息项
									
/*									if("姓名".equals(Message)){
										messageE += "a01.a0101,"; 
									}else if("民族（名）".equals(Message)){
										messageE += "mzm,";
									}else if("性别（名）".equals(Message)){
										messageE += "xbm,";
									}else if("人员状态".equals(Message)){
										messageE += "ryzt,";
									}else if("人员类别".equals(Message)){
										messageE += "a01.a0160,";
									}else if("身份证号".equals(Message)){
										messageE += "a01.a0184,";
									}else if("性别".equals(Message)){
										messageE += "a01.a0104a,";
									}else if("出生年月".equals(Message)){//??0k
										messageE += "csny,";
									}else if("出生日期".equals(Message)){
										messageE += "a01.a0107,";
									}else if("年龄".equals(Message)){//??ok
										messageE += "nl,";
									}else if("照片".equals(Message)){//??ok
										messageE += "zp,";
									}else if("民族".equals(Message)){   
										messageE += "a01.a0117a,";
									}else if("籍贯".equals(Message)){ 
										messageE += "a01.a0111a,";
									}else if("出生地".equals(Message)){
										messageE += "a01.a0114a,";
									}else if("第一党派".equals(Message)){
										messageE += "a01.a0141,";
									}else if("入党时间".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("第二党派".equals(Message)){
										messageE += "dedp,";
									}else if("第三党派".equals(Message)){
										messageE += "dsdp,";
									}else if("参加工作时间".equals(Message)){
										messageE += "a01.a0134,";
									}else if("健康状况".equals(Message)){
										messageE += "a01.a0128,";
									}else if("专业技术职务".equals(Message)){
										messageE += "a01.a0196,";
									}else if("熟悉专业有何专长".equals(Message)){
										messageE += "a01.a0187a,";
									}else if("学历".equals(Message)){//-----------------
										messageE += "zgxl,";
									}else if("毕业学校（学历）".equals(Message)){
										messageE += "zgxlbyxx,";
									}else if("所学专业（学历）".equals(Message)){
										messageE += "zgxlsxzy,";
									}else if("入学时间（学历）".equals(Message)){
										messageE += "zgxlrxsj,";
									}else if("毕业时间（学历）".equals(Message)){
										messageE += "zgxlbisj,";
									}else if("学位".equals(Message)){
										messageE += "zgxw,";
									}else if("毕业学校（学位）".equals(Message)){
										messageE += "zgxwbyxx,";
									}else if("所学专业（学位）".equals(Message)){
										messageE += "zgxwsxzy,";
									}else if("入学时间（学位）".equals(Message)){
										messageE += "zgxwrxsj,";
									}else if("毕业时间（学位）".equals(Message)){
										messageE += "zgxwbisj,";
									}else if("学历（全日制）".equals(Message)){//------OK
										messageE += "xlqrz,";
									}else if("入学时间（全日制）".equals(Message)){//??OK
										messageE += "rxsjqrz,";
									}else if("毕业时间（全日制）".equals(Message)){//??OK
										messageE += "bysjqrz,";
									}else if("学位（全日制）".equals(Message)){//OK
										messageE += "xwqrz,";
									}else if("毕业学校（全日制学历）".equals(Message)){//ok
										messageE += "xxjyxql,";
									}else if("毕业学校（全日制学位）".equals(Message)){//ok
										messageE +="xxjyxqw,";
									}else if("所学专业（全日制学历）".equals(Message)){//??ok
										messageE +="sxzyql,";
									}else if("所学专业（全日制学位）".equals(Message)){//??ok
										messageE +="sxzyqw,";
									}else if("学历（在职）".equals(Message)){//ok
										messageE +="xlzz,";
									}else if("入学时间（在职）".equals(Message)){//??ok
										messageE +="rxsjzz,";
									}else if("毕业时间（在职）".equals(Message)){//??ok
										messageE +="bysjzz,";
									}else if("学位（在职）".equals(Message)){//ok
										messageE +="xwzz,";
									}else if("毕业学校（在职学历）".equals(Message)){//??ok
										messageE +="xxjyxzl,";
									}else if("毕业学校（在职学位）".equals(Message)){//??ok
										messageE +="xxjyxzw,";
									}else if("所学专业（在职学历）".equals(Message)){//??ok
										messageE +="sxzyzl,";
									}else if("所学专业（在职学位）".equals(Message)){//??ok
										messageE +="sxzyzw,";
									}else if("全日制学历（任免表）".equals(Message)){//??ok
										messageE += "qrzxlrb,";
									}else if("全日制学历信息（任免表）".equals(Message)){//??ok
										messageE += "qrzxlxxrb,";
									}else if("全日制学位（任免表）".equals(Message)){//??ok
										messageE += "qrzxwrb,";
									}else if("全日制学位信息（任免表）".equals(Message)){//??ok
										messageE += "qrzxwxxrb,";
									}else if("在职学历（任免表）".equals(Message)){//ok
										messageE += "zzxlrb,";
									}else if("在职学历信息（任免表）".equals(Message)){//??ok
										messageE += "zzxixxrb,";
									}else if("在职学位（任免表）".equals(Message)){//ok
										messageE += "zzxwrb,";
									}else if("在职学位信息（任免表）".equals(Message)){//??ok
										messageE += "zzxwxxrb,";
									}else if("工作单位及职务（全）".equals(Message)){
										messageE += "a01.a0192a,";
									}else if("工作单位及职务（简）".equals(Message)){
										messageE += "a01.a0192,";
									}else if("统计关系所在单位".equals(Message)){
										messageE += "a01.a0195,";//
									}else if("机构外职务".equals(Message)){//??""
										messageE += "jgwpx,";//
									}else if("选拔任用方式".equals(Message)){
										messageE += "a02.a0247,";
									}else if("任职文号".equals(Message)){
										messageE += "a02.a0245,";
									}else if("是否领导职务".equals(Message)){
										messageE += "a02.a0219,";//
									}else if("机构名称".equals(Message)){
										messageE += "a02.a0201a,";
									}else if("职务名称".equals(Message)){
										messageE += "a02.a0216a,";
									}else if("现职务层次".equals(Message)){//?? o k
										messageE += "zwcc,";
									}else if("任职时间".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("任现职务层次时间".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("层次时间括号处理".equals(Message)){//??ok
										messageE += "ccsjkhcl,";//-------/
									}else if("职动类型".equals(Message)){
										messageE += "a02.a0251,";
									}else if("免职时间".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("基层工作经历年限".equals(Message)){
										messageE += "jcnx,";
									}else if("简历".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("奖惩情况".equals(Message)){
										messageE += "a01.a14z101,";
									}else if("年度考核结果".equals(Message)){ 
										messageE += "a01.a15z101,";
									}else if("备注".equals(Message)){
										messageE += "a01.a0180,";
									}else if("称谓（家庭成员）".equals(Message)){//??ok
										messageE += "cw,";
									}else if("姓名（家庭成员）".equals(Message)){//??ok
										messageE += "xm,";
									}else if("出生年月（家庭成员）".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("年龄（家庭成员）".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("政治面貌（家庭成员）".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("工作单位及职务（家庭成员）".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("进入本单位方式".equals(Message)){
										messageE += "a29.a2911,";
									}else if("进入本单位日期".equals(Message)){
										messageE += "a29.a2907,";
									}else if("原单位名称".equals(Message)){
										messageE += "a29.a2921a,";
									}else if("在原单位职务".equals(Message)){
										messageE += "a29.a2941,";
									}else if("在原单位职务层次".equals(Message)){
										messageE += "a29.a2944,";
									}else if("进入公务员队伍时间".equals(Message)){
										messageE += "a29.a2947,";
									}else if("公务员登记时间".equals(Message)){
										messageE += "a29.a2949,";
									}else if("拟任职务".equals(Message)){
										messageE += "a53.a5304,";
									}else if("拟免职务".equals(Message)){
										messageE += "a53.a5315,";
									}else if("任免理由".equals(Message)){
										messageE += "a53.a5317,";
									}else if("呈报单位".equals(Message)){
										messageE += "a53.a5319,";
									}else if("填表时间".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("填表人".equals(Message)){
										messageE += "a53.a5327,";
									}else if("计算年龄时间".equals(Message)){
										messageE += "a53.a5321,";
									}else if("退出管理方式".equals(Message)){
										messageE += "a30.a3001,";
									}else if("调往单位".equals(Message)){
										messageE += "a30.a3007a,";
									}else if("退出管理时间".equals(Message)){
										messageE += "a30.a3004,";
									}else if("退出单位".equals(Message)){
										messageE += "a01.orgid,";
									}else if("修改人".equals(Message)){
										messageE += "a01.xgr,";
									}else if("修改日期".equals(Message)){
										messageE += "a01.xgsj,";
									}else if("当前日期".equals(Message)){//??ok
										messageE += "dqsj,";//----
									}else if("当前用户名".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
*/								
									if("姓名".equals(Message)){
										messageE += "a0101-a01.a0101,"; 
									}else if("人员状态".equals(Message)){
										messageE += "a0163-select code_name from code_value where  code_type = 'ZB126' and code_value = (select a0163 from a01 where a0000 = 'id' ) ',";
									}else if("人员类别".equals(Message)){
										messageE += "a0160-select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='id',";
									}else if("身份证号".equals(Message)){
										messageE += "a0184-a01.a0184,";
									}else if("性别".equals(Message)){
										messageE += "a0104-select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='id' and cv.code_value=a.a0104,";
									}else if("出生年月".equals(Message)){//??0k
										messageE += "a0107-substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2),";
									}else if("年龄".equals(Message)){//??ok
										messageE += "nl,";
									}else if("照片".equals(Message)){//??ok
										messageE += "zp,";
									}else if("民族".equals(Message)){   
										messageE += "a0117-select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='id' and cv.code_value=a.a0117,";
									}else if("籍贯".equals(Message)){ 
										messageE += "a0111a-a01.a0111a,";
									}else if("出生地".equals(Message)){
										messageE += "a0114a-a01.a0114a,";
									}else if("第一党派".equals(Message)){
										messageE += "a0141-select cv.code_name from a01 a01,code_value cv where a01.a0000 ='id' and cv.code_type='GB4762' and cv.code_value=a01.a0141,";
									}else if("入党时间".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("第二党派".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("第三党派".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("参加工作时间".equals(Message)){
										messageE += "a0134-substr(a01.a0134,1,4)||'.'||substr(a01.a0134,5,2),";
									}else if("健康状况".equals(Message)){
										messageE += "a0128-a01.a0128,";
									}else if("专业技术职务".equals(Message)){
										messageE += "a0196-a01.a0196,";
									}else if("熟悉专业有何专长".equals(Message)){
										messageE += "a0187a-a01.a0187a,";
									}else if("学历".equals(Message)){//-----------------
										messageE += "a0801a-select a08.a0801a from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("毕业学校（学历）".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("所学专业（学历）".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("入学时间（学历）".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("毕业时间（学历）".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("学位".equals(Message)){
										messageE += "a0901a-select a08.a0901a from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("毕业学校（学位）".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("所学专业（学位）".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("入学时间（学位）".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("毕业时间（学位）".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("学历（全日制）".equals(Message)){//------OK
										messageE += "QRZXL-a01.QRZXL,";
									}else if("入学时间（全日制）".equals(Message)){//??OK
										messageE += "A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '' group by a0804,";
									}else if("毕业时间（全日制）".equals(Message)){//??OK
										messageE += "a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = 'id' group by a0807,";
									}else if("学位（全日制）".equals(Message)){//OK
										messageE += "QRZXW-a01.QRZXW,";
									}else if("毕业学校（全日制学历）".equals(Message)){//ok
										messageE += "a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("毕业学校（全日制学位）".equals(Message)){//ok
										messageE +="a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（全日制学历）".equals(Message)){//??ok
										messageE +="a0824-select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（全日制学位）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("学历（在职）".equals(Message)){//ok
										messageE +="ZZXL-a01.ZZXL,";
									}else if("入学时间（在职）".equals(Message)){//??ok
										messageE +="A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = 'id' group by a0804,";
									}else if("毕业时间（在职）".equals(Message)){//??ok
										messageE +="a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = 'id' group by a0807,";
									}else if("学位（在职）".equals(Message)){//ok
										messageE +="ZZXW-a01.ZZXW,";
									}else if("毕业学校（在职学历）".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("毕业学校（在职学位）".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（在职学历）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（在职学位）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("全日制学历（任免表）".equals(Message)){//??ok
										messageE += "QRZXL-a01.QRZXL,";
									}else if("全日制学历信息（任免表）".equals(Message)){//??ok
										messageE += "QRZXLXX-a01.QRZXLXX,";
									}else if("全日制学位（任免表）".equals(Message)){//??ok
										messageE += "QRZXW-a01.QRZXW,";
									}else if("全日制学位信息（任免表）".equals(Message)){//??ok
										messageE += "QRZXWXX-a01.QRZXWXX,";
									}else if("在职学历（任免表）".equals(Message)){//ok
										messageE += "ZZXL-a01.ZZXL,";
									}else if("在职学历信息（任免表）".equals(Message)){//??ok
										messageE += "ZZXLXX-a01.ZZXLXX,";
									}else if("在职学位（任免表）".equals(Message)){//ok
										messageE += "ZZXW-a01.ZZXW,";
									}else if("在职学位信息（任免表）".equals(Message)){//??ok
										messageE += "ZZXWXX-a01.ZZXWXX,";
									}else if("工作单位及职务（全）".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("工作单位及职务（简）".equals(Message)){
										messageE += "a0192-a01.a0192,";
									}else if("登记后所任职务".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("登记后所定级别".equals(Message)){
										messageE += "a0120-a01.a0120,";
									}else if("统计关系所在单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a01.a0000= a02.a0000 ) a on  a.a0195 = a02.a0201b  where a02.a0000 = 'id',";//
									}else if("选拔任用方式".equals(Message)){
										messageE += "a0247-select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = 'id',";
									}else if("任职文号".equals(Message)){
										messageE += "a0245-a02.a0245,";
									}else if("是否领导职务".equals(Message)){
										messageE += "a0219-a02.a0219,";//
									}else if("机构名称".equals(Message)){
										messageE += "a0201a-select a02.a0201a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("职务名称".equals(Message)){
										messageE += "a0216a-select a02.a0216a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("现职务层次".equals(Message)){//?? o k=========================================
										messageE += "a0221-select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = 'id' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09',";
									}else if("任职时间".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("任现职务层次时间".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("职动类型".equals(Message)){
										messageE += "a0251-a02.a0251,";
									}else if("免职时间".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("基层工作经历年限".equals(Message)){
										messageE += "a0197-select (case when a0197 = '1' then '是' else '否' end ) from a01 where a0000 = 'id',";
									}else if("简历".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("奖惩情况".equals(Message)){
										messageE += "a14z101-a01.a14z101,";
									}else if("年度考核结果".equals(Message)){ 
										messageE += "a15z101-a01.a15z101,";
									}else if("备注".equals(Message)){
										messageE += "a0180-a01.a0180,";
									}else if("称谓（家庭成员）".equals(Message)){//??ok
										messageE += "cw,";
									}else if("姓名（家庭成员）".equals(Message)){//??ok
										messageE += "xm,";
									}else if("出生年月（家庭成员）".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("年龄（家庭成员）".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("政治面貌（家庭成员）".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("工作单位及职务（家庭成员）".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("进入本单位方式".equals(Message)){
										messageE += "a2911-select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = 'id',";
									}else if("进入本单位日期".equals(Message)){
										messageE += "a2907-a29.a2907,";
									}else if("原单位名称".equals(Message)){
										messageE += "a2921a-a29.a2921a,";
									}else if("在原单位职务".equals(Message)){
										messageE += "a2941-a29.a2941,";
									}else if("在原单位职务层次".equals(Message)){
										messageE += "a2944-a29.a2944,";
									}else if("进入公务员队伍时间".equals(Message)){
										messageE += "a2947-a29.a2947,";
									}else if("公务员登记时间".equals(Message)){
										messageE += "a2949-a29.a2949,";
									}else if("拟任职务".equals(Message)){
										messageE += "a5304-a53.a5304,";
									}else if("拟免职务".equals(Message)){
										messageE += "a5315-a53.a5315,";
									}else if("任免理由".equals(Message)){
										messageE += "a5317-a53.a5317,";
									}else if("呈报单位".equals(Message)){
										messageE += "a5319-a53.a5319,";
									}else if("填表时间".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("填表人".equals(Message)){
										messageE += "a5327-a53.a5327,";
									}else if("退出管理方式".equals(Message)){
										messageE += "a3001-select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = 'id',";
									}else if("调往单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = 'id' group by a02.a0201a,";
									}else if("退出管理时间".equals(Message)){
										messageE += "a3004-a30.a3004,";
									}else if("退出单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = 'id' group by a02.a0201a,";
									}else if("修改人".equals(Message)){
										messageE += "xgr-a01.xgr,";
									}else if("修改日期".equals(Message)){
										messageE += "xgsj,";
									}else if("当前日期".equals(Message)){//??ok
										messageE += "dqsj,";//----?????
									}else if("当前用户名".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
									 								
								}
								//获取到的内容把前后<>去掉
							//	String Message = message.substring(1, message.length() - 1);
								//3表示数据
								Map<String, String> map = new HashMap<String, String>();
								//1表示行
								map.put("1", row1);
								//2表示列
								map.put("2", cell1);
								//3表示数据
								MessageA =	MessageA.substring(0, MessageA.length()-1);
								map.put("3", MessageA);
								//英文数据项
//									System.out.println("44------------>"+messageE);
									if(messageE == null || "".equals(messageE)){
										this.setMainMessage("保存失败！");
										return EventRtnType.NORMAL_SUCCESS;
									}else {
										messageE =	messageE.substring(0, messageE.length()-1);
									}
								
								map.put("4", messageE);
								list.add(map);
								 messageE = "";
								 MessageA = "";
								List Data = et.getChildren();
								//获取Data节点
								//et = (Element) Data.get(0);
								//System.out.println(et.getChild("Data",ns).getText());
							}
						}
					}
					if("1".equals(isedit)){
						jiexi(list,TPname1,uuid,PageNu,"2",tpkind,"1",a);
						a++;
					}else{
						jiexi(list,TPname,uuid,PageNu,"2",tpkind,"2",a);
						a++;
					}
				} else {
					System.out.println("无数据");
				}
				p++;
			}
			this.getPageElement("savescript").setValue(uuid);
			tpid = uuid;
			this.getExecuteSG().addExecuteCode("tishi()");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.remove(thistab.tabid);");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (JDOMException e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		} catch (IOException e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		} catch (SQLException e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
	}
	
	

	public void jiexi(List list,String TPname,String TPID,String PageNu,String TPType,String TPKind,String type,int a ) {
		int tn = 0;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String sql = "";
		PreparedStatement Stemt = null;
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String tpname = "";
		try {
		if("1".equals(type)){
			String tptype = "";
			String endtime = "";
			String tpkind = "";
//			System.out.println("---->"+TPname);
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpname,tptype,endtime,tpkind from listoutput2 where tpid='"+TPname+"'").executeQuery();
			while(res.next()){
				tpname = res.getString(1);
				tptype = res.getString(2);
				endtime = res.getString(3);
				tpkind = res.getString(4);
			}
			try {
				if(a == 0){
					HBUtil.executeUpdate("delete from listoutput where tpid='"+TPname+"'");
				}
				
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,PageNu,MessageE,TPName,TPID,TPType,EndTime,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, PageNu);
				Stemt.setString(5, MessageE);
				Stemt.setString(6, tpname);
				Stemt.setString(7, TPname);
				Stemt.setString(8, tptype);
				Stemt.setString(9, endtime);
				Stemt.setString(10, tpkind);
				Stemt.setString(11, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}else{
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,TPName,TPID,PageNu,TPType,MessageE,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, TPname);
				Stemt.setString(5, TPID);
				Stemt.setString(6, PageNu);
				Stemt.setString(7, TPType);
				Stemt.setString(8, MessageE);
				Stemt.setString(9, TPKind);
				Stemt.setString(10, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template(user_template_id,tpid,userid) values('"+uuid+"','"+TPID+"','"+userid+"')";
			HBUtil.getHBSession().connection().prepareStatement(sql1).executeUpdate();
			if (tn % 200 != 0) {
				Stemt.executeBatch();
				Stemt.clearBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (Stemt != null) {
					Stemt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e) {
			}

		}
//		System.out.println("保存完成");
	}

	@PageEvent("uploadfile")
	public int uploadfile() throws RadowException{
		String savePath = "";
		if(this.request.getSession().getAttribute("tpid")!=null&&!"0".equals(this.request.getSession().getAttribute("tpid").toString())){
			String tpid = this.request.getSession().getAttribute("tpid").toString().replace("|", "").replace("|", "");
//			System.out.println("----->"+tpid);
			savePath = request.getSession().getServletContext().getRealPath("/template")+"/"+tpid+".cll";
		}else{
			savePath = request.getSession().getServletContext().getRealPath("/template")+"/"+tpid+".cll";
		}
		try {
//			System.out.println("save----->"+savePath);
			File file = new File(savePath);
			file.createNewFile();
			ServletInputStream in = request.getInputStream();
			FileOutputStream out = new FileOutputStream(savePath);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("success");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
