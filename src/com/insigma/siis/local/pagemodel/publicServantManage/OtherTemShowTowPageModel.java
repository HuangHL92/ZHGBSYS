package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.struts.util.AppException;
import org.hibernate.Session;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilder;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilderBcZdy;


public class OtherTemShowTowPageModel extends PageModel {
	public static int i = 0;
	public static int k = 0;
	private static final HttpServletRequest HttpServletRequest = null;
	public static Map<Integer, String> map = new HashMap<Integer, String>();
	private String sScript;
	
	@Override
	public int doInit() throws RadowException {
		i = 0;
		k = 0;
		this.setNextEventName("information");
		this.getExecuteSG().addExecuteCode("showdata()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String aa(HttpServletRequest request) throws RadowException {
		String viewType = (String) request.getSession().getAttribute("yviewType");
		String tempType = (String) request.getSession().getAttribute("ytempType");
		if("6".equals(tempType) || "8".equals(tempType) || "21".equals(viewType) || "22".equals(viewType) || "23".equals(viewType)){
		//-------------------------------------
			
			
			return sScript;
		} else {
		//-------------------------------------
		// �����ж�
		Map<String, String> map = new HashMap<String, String>();
		String num = request.getSession().getAttribute("viewType").toString();
		List<Map<String,String>> bzmclist = (List)request.getSession().getAttribute("bzbclist");
		String id = (String)request.getSession().getAttribute("personids");	
		String[] id1 = id.replace("|", "'").split("@");
		String id2 = id.replace("|", "'").replace("@",",");
		String tpid = request.getSession().getAttribute("tpid").toString();
		//------------------------------------��׼�����Զ���չʾ��������������û�õ�
		
		//1.�������޻�������
		if("11".equals(num)){
			String tpidw = (String) request.getSession().getAttribute("tpid");//ģ������
			String sScript="";
			int pnum = 0 ;//��
			String ZBRow = "0";
			String ZBLine = "0";
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript+="document.getElementById('DCellWeb1').InsertRow(50,500,'2');";//������
		    sScript += "var cell = document.getElementById('DCellWeb1');";
		    int m = 0;
		    int n = 0;
		    int sheet = 0;
		    sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    int numm = 0;//������¼��һ����¼���зŵ�ǰ̨
		    //��ȡ�ж���ҳ
		    List  listtj2 = HBUtil.getHBSession().createSQLQuery("select PageNu from listoutput where TPID = ('"+tpid+"') group by PageNu order by PageNu asc").list();
//		    List  pxa0000 = HBUtil.getHBSession().createSQLQuery("select a0000 from a02 where a0000 in ("+id2+") order by a0201b").list();
		    System.out.println("select b0101,b0111 from b01 where b0111 in (select a0201b from a02 a  where a.a0000 in ("+id2+") and a.a0255 = '1') order by b0111");
//		    List<Object[]>  pxjg = HBUtil.getHBSession().createSQLQuery("select a0201a,a0201b from a02 where a0000 in ("+id2+") and a0255= '1' group by a0201a,a0201b order by a0201b").list();
		    List<Object[]>  pxjg = HBUtil.getHBSession().createSQLQuery("select b0101,b0111 from b01 where b0111 in (select a0201b from a02 a  where a.a0000 in ("+id2+") and a.a0255 = '1') order by b0111").list();
		    List<Map<String,String>> jgandp  = new ArrayList<Map<String,String>>();
		    Map<String,List> mapto = new HashMap<String, List>();
		    for(int a= 0;a<pxjg.size();a++){
		    	String a0201a = pxjg.get(a)[0].toString();
		    	String a0201b = pxjg.get(a)[1].toString();
			    List<Object[]>  id3 = HBUtil.getHBSession().createSQLQuery("select a0000,a0200,a0201b from a02 where a0201b = '"+a0201b+"' and a0000 in ("+id2+") and a0255 = '1' group by a0000,a0200,a0201b order by a0201b ").list();
			    if(id3 != null && id3.size() > 0){
			    	mapto.put(a0201a, id3);
			    }

		    }
		    for(int q = 0 ;q < listtj2.size();q++ ){//ѭ��ÿҳ�ĸ�ʽ
		    	sScript +="endline = 0;";
			    sScript +="mathrow1 = 0;";
		    	String s = listtj2.get(q).toString();
		    	  //1����ȡ����е���Ϣ��
			    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')  and PageNu = '"+s+"'").list();
			    //�����е����ֵ
			    int max = maxZbrow(listtj);
			    //�������������ֵ�Ĳ�ѯ����
				List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow='"+max+"' and PageNu = '"+s+"'").list();
				sheet = 0;
				for(int i=0;i<listtj1.size();i++){
					Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
					sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
					String messageE = obj[1].toString();
					String endtime = "";
					if(obj[5]==null||"".equals((String)obj[5])){
						endtime = DateUtil.getcurdate();
					}else{
						endtime = obj[5].toString();
					}
					ZBRow = obj[2].toString();
					ZBLine = obj[3].toString();
					//================================================
					//�еĲ�ֵ
					sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
					sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";

				}
				int t = 0;
				int d = sheet;//d�����ж��ǲ��Ƿ���
				if(0 == sheet){
					t = 1;//����Ƿ����ѭ��һ��
				}else{
					t = pxjg.size();
				}
				if(0 == sheet){
					for(m = 0; m < t;m++){//ѡ�е���Ա��������
				    	//2����������е�ÿһ����Ϣ�������������ƥ��
				    	String a0201a = pxjg.get(m)[0].toString();
				    	List<Object[]> personid = mapto.get(a0201a);
				    	for(int p = 0 ;p < t; p++){
				    		String personid2 = (String)personid.get(p)[0];
				    		String personid3 = (String)personid.get(p)[1];
				    		for(int i=0;i<listtj1.size();i++){
								Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
								sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//�еĲ�ֵ
								sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
								//�еĲ�ֵ
								sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								//������ֵ
								sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
								sScript += "if(endline == 0){endline = "+ZBRow+"};";
								//�ϲ���Ԫ��
								sScript += "cell.MergeCells("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+ZBLine+"+mathline,"+ZBRow+"+(mathrow1+1)*"+m+"+mathrow);";
								//================================================
								String mes = returnMes(request,messageE, personid2.toString(), endtime,personid3);
								if(mes !=null && !mes.equals("null") && !mes.equals("")){
									sScript += "var hight ='';";
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
										
										
									}else{
										sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����

									}
								}else{
									mes = "";
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
										
									}else{
//										sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
										sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
							
									}
								}
								//���浱ǰʱ���ʽ������
								if(d == 0){
									sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//����
									sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',36);";//����
									sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',14);";//�����С
								}
								//�Ѻ�̨��һ����¼�������ŵ�ǰ̨ҳ��
								if(numm==0 && d ==2){
									request.getSession().setAttribute("rownum", ZBRow);
									numm++;
								}
								
						    }
				    	}
					    
//					    pnum++;
				    }
			}
				if(2 == sheet){
					for(m = 0; m < t;m++){//id1ѡ�е���Աid
						//2����������е�ÿһ����Ϣ�������������ƥ��
						String a0201a = pxjg.get(m)[0].toString();
						List<Object[]> personid = mapto.get(a0201a);
				    	for(int p = 0 ;p < personid.size(); p++){
				    		String personid2 = (String)personid.get(p)[0];
				    		String personid3 = (String)personid.get(p)[1];
							for(int i=0;i<listtj1.size();i++){
								Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
								sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//�еĲ�ֵ
								sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
								//�еĲ�ֵ
								sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								//������ֵ
								sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
								sScript += "if(endline == 0){endline = "+ZBRow+"};";
								//�ϲ���Ԫ��
								sScript += "cell.MergeCells("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+ZBLine+"+mathline,"+ZBRow+"+(mathrow1+1)*"+m+"+mathrow);";
								//================================================
								String mes = returnMes(request,messageE, personid2.toString(), endtime,personid3);
								if(mes !=null && !mes.equals("null") && !mes.equals("")){
									sScript += "var hight ='';";
									if(getcharnu1(mes, "@")==2){
										if(p == 0 && i==0 ){
											
											sScript+="document.getElementById('DCellWeb1').DeleteRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//ɾ�����м����ŵ����ݡ�
											sScript+="document.getElementById('DCellWeb1').InsertRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//����һ�С�
//											sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','"+a0201a+"');";
											//n++;
											if(m == 0){
												n++;
											}
											sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','');";
											
										}
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����

										
									}else{
										if(p == 0 && i==0 ){
											sScript+="document.getElementById('DCellWeb1').DeleteRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//ɾ�����м����ŵ����ݡ�
											sScript+="document.getElementById('DCellWeb1').InsertRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//����һ�С�

											if(m == 0){
												n++;
											}
										}
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
										sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

									}
								}else{
									mes = "";
									if(getcharnu1(mes, "@")==2){
										if(p == 0 && i==0 ){
											sScript+="document.getElementById('DCellWeb1').DeleteRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//ɾ�����м����ŵ����ݡ�
											sScript+="document.getElementById('DCellWeb1').InsertRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//����һ�С�

											if(m == 0){
												n++;
											}
											
										}
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
									
									}else{
										if(p == 0 && i==0 ){
											sScript+="document.getElementById('DCellWeb1').DeleteRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//ɾ�����м����ŵ����ݡ�
											sScript+="document.getElementById('DCellWeb1').InsertRow("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'1','"+sheet+"');";//����һ�С�

											if(m == 0){
												n++;
											}
											
										}
//										sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
										sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";
										sScript += "document.getElementById('DCellWeb1').SetCellTextStyle("+ZBLine+","+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//�Զ�����
	
									}
								}
								//���浱ǰʱ���ʽ������
								if(d == 0){
									sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',2);";//����
									sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',36);";//����
									sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"',14);";//�����С
								}
								//�Ѻ�̨��һ����¼�������ŵ�ǰ̨ҳ��
								if(numm==0 && d ==2){
									request.getSession().setAttribute("rownum", ZBRow);
									numm++;
								}
								
							}
							sScript += "hight = document.getElementById('DCellWeb1').GetRowBestHeight("+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+");";//�õ�����ʵ��и�
							sScript += "document.getElementById('DCellWeb1').SetRowHeight(1,hight,"+(Integer.parseInt(ZBRow)+n)+"+(mathrow1+1)*"+m+",'"+sheet+"');";//�����и�
							n++;
						}
					}
				}
			
		    }
		    sScript += "cell.DeleteRow("+(Integer.parseInt(ZBRow)+3+m)+"+((mathrow1+1)*"+m+"),(550 - ("+(Integer.parseInt(ZBRow)+3+m)+"+((mathrow1+1)*"+m+"))),'"+sheet+"');";//ɾ���������	

	    	//�����һ����¼����ǰ̨
//		    sScript+="cell.PrintSetMargin('150','100','200','100');";//����ҳ�߾�
		    sScript+=" cell.setCurSheet(2);";//��ȡ����ҳ�ķ���
		    sScript+="cell.PrintSetFoot('','��&Pҳ','');";//�ڵ���ҳ��ҳ��
		    sScript+="cell.SetColHidden(17,20);";//�ڵ���ҳ��ҳ��
			sScript+="}";
			sScript+="</script>";
			return sScript;	
			}
		
		//2.��������й�����
		if("12".equals(num)){
			String tpidw = (String) request.getSession().getAttribute("tpid");//ģ������
			String sScript="";
			String ZBRow = "0";
			String ZBLine = "0";
			int pnum = 0 ;
			int rownumber  = 0 ;
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		  //1����ȡ����е���Ϣ��
		    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
		    int max = maxZbrow(listtj);
		    List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow='"+max+"'").list();
		    for(int i=0;i<listtj1.size();i++){
				Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
				int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
				String messageE = obj[1].toString();
				String endtime = "";
				if(obj[5]==null||"".equals((String)obj[5])){
					endtime = DateUtil.getcurdate();
				}else{
					endtime = obj[5].toString();
				}
				ZBRow = obj[2].toString();
				ZBLine = obj[3].toString();
				//================================================
				//�еĲ�ֵ
				sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
				sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";

			}
		    for(int m = 0; m< id1.length;m++){
		    	//2����������е�ÿһ����Ϣ�������������ƥ��
			    for(int i=0;i<listtj1.size();i++){
			    	Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
			    	
					int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
					String messageE = obj[1].toString();
					String endtime = "";
					if(obj[5]==null||"".equals((String)obj[5])){
						endtime = DateUtil.getcurdate();
					}else{
						endtime = obj[5].toString();
					}
					ZBRow = obj[2].toString();
					ZBLine = obj[3].toString();
					//================================================
					//�еĲ�ֵ
					sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
					//�еĲ�ֵ
					sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
					//������ֵ
					sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
					sScript += "if(endline == 0){endline = "+ZBRow+"};";
					//�ϲ���Ԫ��
					sScript += "cell.MergeCells("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+ZBLine+"+mathline,"+ZBRow+"+(mathrow1+1)*"+m+"+mathrow);";
					//================================================
					List listjg = HBUtil.getHBSession().createSQLQuery("select t.a0201a from (select a02.a0201a from a02 where a0000 =("+id1[m].toString()+")) t").list();
					sScript += "cell.SetCellString(1,"+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'"+listjg.get(0)+"');";
					String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime,"");
					
					if(mes !=null && !mes.equals("null") && !mes.equals("")){
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

						}

					}else{
						mes = "";
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

						}
					}
				}
			    pnum++;
		    }
		    sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "," +  (65050 - (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//ɾ���������	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//�п�
		    sScript+="cell.PrintSetMargin('150','100','200','100');";//����ҳ�߾�
		    sScript+="cell.SetColUnhidden('1','1');";//ȡ���е�����
			sScript+="}";
			sScript+="</script>";
			return sScript;	
			}
		

		//3.����������
		if ("13".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//ģ������
			String sScript="";
			int pnum = 0 ;
			int add = 0;
			int pnum2 = 0 ;
			String ZBRow = "0";
			String ZBLine = "0";
			int sheet  = 0;
			String id3 = request.getSession().getAttribute("checkList").toString();
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";

		    //1����ȡ����е���Ϣ��
			//������Աid��ȡȥ�غ�Ļ������ࣨ��˳��������
			String sql = "select t.a0201a from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+id_3+") order by length(a0201b),a0201b asc) t";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sql).list();
			//������Աid����ȡ��Ӧ�Ļ���������
			int rownumber = 0;
			int pcount = 0;
			int a = 0;
			for(;a<disA0201a.size();a++){
				for(int m = 0; m< id1.length;m++){
				//������Աid��ѯ����Ӧ�Ļ���������
				String sql1 = "select a02.a0201a ,a01.a0101 from A01 a01,A02 a02 where a01.a0000 = a02.a0000 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000,a02.a0201,a01.a0101,a02.a0201a";
				List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
				//��˳�������һһ�Ա�
					for(int w = 0;w <list.size();w++){
						if(list.get(w)[0].toString().equals(disA0201a.get(a).toString())){
							   List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
							   int max = maxZbrow(listtj);
							   List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
							   
							   for(int i=0;i<listtj1.size();i++){
									Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
									sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
									String messageE = obj[1].toString();
									String endtime = "";
									if(obj[5]==null||"".equals((String)obj[5])){
										endtime = DateUtil.getcurdate();
									}else{
										endtime = obj[5].toString();
									}
									ZBRow = obj[2].toString();
									ZBLine = obj[3].toString();
									//================================================
									//�еĲ�ֵ
									sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
									sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
								}
							   
							   //2����������е�ÿһ����Ϣ�������������ƥ��
							    for(int i=0;i<listtj1.size();i++){
							    	Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
									sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
									String messageE = obj[1].toString();
									String endtime = "";
									if(obj[5]==null||"".equals((String)obj[5])){
										endtime = DateUtil.getcurdate();
									}else{
										endtime = obj[5].toString();
									}
									ZBRow = obj[2].toString();
									ZBLine = obj[3].toString();
									
									//================================================
									//�еĲ�ֵ
									sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
									//�еĲ�ֵ
									sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
									//������ֵ
									sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
									sScript += "if(endline == 0){endline = "+ZBRow+"};";
									//�ϲ���Ԫ��
									sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
									//================================================
									
									
									String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime,"");
									if(mes !=null && !mes.equals("null") && !mes.equals("")){
										if(getcharnu1(mes, "@")==2){
											sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
											sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

										}else{
											sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
										}

									}else{
										mes = "";
										if(getcharnu1(mes, "@")==2){
											sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
											sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

										}else{
											sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
										}
									}
							    }
							    pnum++;
							    pnum2++;
						    }
					}
				 
			}
//				sScript += "cell.SetCellString(1,"+(pnum+(Integer.parseInt(ZBRow))+rownumber-pcount-1)+",0,'"+disA0201a.get(a).toString()+"');";
//				sScript += "cell.SetCellString(1,"+ Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+ (pnum) +"+"+ (rownumber +"-(mathrow1+1)*"+ pnum +"-"+1)+",0,'"+disA0201a.get(a).toString()+"');";
				if(pcount==0){
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +","+sheet+",'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}else{
					add++;
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,"+sheet+",'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}
				rownumber = a+1;
				pcount = 1;
			
			}
			sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//ɾ���������	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//�п�
		    sScript+="cell.PrintSetMargin('150','100','200','100');";//����ҳ�߾�
		    sScript+="cell.SetColUnhidden('1','1');";//ȡ��������
		    sScript+="cell.SetRowUnhidden('1','1');";//ȡ��������
			sScript+="}";
			sScript+="</script>";
			return sScript;	
		}
		
		//4.��ְ���η���
		if ("14".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//ģ������
			String sScript="";
			int pnum = 0 ;
			int pnum2 = 0;//ͳ��ÿ��������м�����
			int add = 0 ;//���Ʋ�����Ƶ�λ��
			String ZBRow = "0";
			String ZBLine = "0";
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    //1����ȡ����е���Ϣ��
			//������Աid��ȡȥ�غ�Ļ������ࣨ��˳��������
			String sql = "select  cv.code_name from code_value cv,(select a01.a0148 from a01 a01 where a01.a0000 in("+id_3+") group by a01.a0148 order by a01.a0148) a where cv.code_type='ZB09' and cv.code_value=a.a0148";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sql).list();
			disA0201a.removeAll(Collections.singleton(null));
			//������Աid����ȡ��Ӧ�Ļ���������
			int rownumber = 0;
			int pcount = 0;
			for(int a=0;a<disA0201a.size();a++){
				for(int m = 0; m< id1.length;m++){
					//������Աid��ѯ����Ӧ�Ļ���������
					String sql1 = "select cv.code_name ,a01.a0101 from code_value cv,A01 a01 where cv.code_type='ZB09' and cv.code_value=a01.a0148 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000 ,cv.code_name,a01.a0101";
					List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
					//��˳�������һһ�Ա�
					if(list.get(0)[0]!=null&&!"".equals(list.get(0)[0])){
						 if(list.get(0)[0].toString().equals(disA0201a.get(a).toString())){
						   List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
						   int max = maxZbrow(listtj);
						   List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
						   
						   for(int i=0;i<listtj1.size();i++){
								Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
								int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//�еĲ�ֵ
								sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
							}
						   
						   //2����������е�ÿһ����Ϣ�������������ƥ��
						    for(int i=0;i<listtj1.size();i++){
						    	Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
								int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//�еĲ�ֵ
								sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
								//�еĲ�ֵ
								sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								//������ֵ
								sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
								sScript += "if(endline == 0){endline = "+ZBRow+"};";
								//�ϲ���Ԫ��
								sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
								//================================================
								String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime,"");
								if(mes !=null && !mes.equals("null") && !mes.equals("")){
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

									}else{
										sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
									}

								}else{
									mes = "";
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

									}else{
										sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
									}
								}
						    }
						    pnum++;
						    pnum2++;
					    }
					
					}
			
				
				}
				if(pcount==0){//����������ƣ�������һ���˵ĸ������Բ�ֵ
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +",0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}else{
					add++;
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}
				rownumber = a+1;
				pcount = 1;
			
			}
			sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//ɾ���������	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//�п�
		    sScript+="cell.PrintSetMargin('150','100','200','100');";//����ҳ�߾�
		    sScript+="cell.SetColUnhidden('1','1');";//ȡ��������
		    sScript+="cell.SetRowUnhidden('1','1');";//ȡ��������
			sScript+="}";
			sScript+="</script>";
			return sScript;	
		}
		
		//5.��ְ���η��飬����������
		if ("15".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//ģ������
			String sScript="";
			int pnum = 0 ;
			int pnum2 = 0 ;
			int add = 0;
			String ZBRow = "0";
			String ZBLine = "0";
			String id3 = request.getSession().getAttribute("checkList").toString();
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    //1����ȡ����е���Ϣ��
			//������Աid��ȡȥ�غ��ְ�����ࣨ��˳��������
			String sql = "select  cv.code_name from code_value cv,(select a01.a0148 from a01 a01 where a01.a0000 in("+id_3+") group by a01.a0148 order by a01.a0148) a where cv.code_type='ZB09' and cv.code_value=a.a0148";
			List disA0149 = HBUtil.getHBSession().createSQLQuery(sql).list();
			disA0149.removeAll(Collections.singleton(null));
			//������Աid��ȡȥ�غ�Ļ������ࣨ��˳��������
			String sqljg = "select t.a0201a from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+id_3+") order by length(a0201b),a0201b asc) t";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sqljg).list();
			disA0201a.removeAll(Collections.singleton(null));
					int rownumber = 0;
					int rownumberw = 0; //���ƻ�����λ��
					int pcount = 0;
						for(int a=0;a<disA0149.size();a++){
							for(int b=0;b<disA0201a.size();b++){
							for(int m = 0; m< id1.length;m++){
									String sql1 = "select cv.code_name,a01.a0101,a02.a0201a from code_value cv,A01 a01,A02 a02 where cv.code_type='ZB09' and cv.code_value=a01.a0148 and a01.a0000=a02.a0000 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000,cv.code_name ,a01.a0101,a02.a0201a";
									List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
									//��˳����ְ��һһ�Ա�
								if(list.get(0)[0]!=null&&!"".equals(list.get(0)[0])){	
									if(list.get(0)[0].toString().equals(disA0149.get(a).toString())){
										//��˳�������һһ�Ա�
										if(list.get(0)[2].toString().equals(disA0201a.get(b).toString())){
											List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
											int max = maxZbrow(listtj);
											List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
											for(int i=0;i<listtj1.size();i++){
												Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
												int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
												String messageE = obj[1].toString();
												String endtime = "";
												if(obj[5]==null||"".equals((String)obj[5])){
													endtime = DateUtil.getcurdate();
												}else{
													endtime = obj[5].toString();
												}
												ZBRow = obj[2].toString();
												ZBLine = obj[3].toString();
												//================================================
												//�еĲ�ֵ
												sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
												sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
											}
											//2����������е�ÿһ����Ϣ�������������ƥ��
											for(int i=0;i<listtj1.size();i++){
												Object[] obj = listtj1.get(i);//��ȡÿ�������������Ҫ������
												int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
												String messageE = obj[1].toString();
												String endtime = "";
												if(obj[5]==null||"".equals((String)obj[5])){
													endtime = DateUtil.getcurdate();
												}else{
													endtime = obj[5].toString();
												}
												ZBRow = obj[2].toString();
												ZBLine = obj[3].toString();
												//================================================
												//�еĲ�ֵ
												sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
												//�еĲ�ֵ
												sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
												//������ֵ
												sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
												sScript += "if(endline == 0){endline = "+ZBRow+"};";
												//�ϲ���Ԫ��
												sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
												//================================================
//												sScript += "cell.SetCellString(1,"+(pnum+rownumberw+max)+","+sheet+",'"+list.get(0)[2].toString()+"');";
												sScript += "cell.SetCellString(1,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+list.get(0)[2].toString()+"');";
												sScript+="document.getElementById('DCellWeb1').SetCellAlign(1,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'36');";//���䷽ʽ
												String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime,"");
												if(mes !=null && !mes.equals("null") && !mes.equals("")){
													if(getcharnu1(mes, "@")==2){
														sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
														sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

													}else{
														sScript += "cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";

													}

												}else{
													mes = "";
													if(getcharnu1(mes, "@")==2){
														sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
														sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

													}else{
														sScript += "cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";

													}
												}
											}
											pnum++;
										    pnum2++;
										}
									}
								}
							}
						}
							if(pcount==0){//����������ƣ�������һ���˵ĸ������Բ�ֵ
								sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +",0,'"+disA0149.get(a).toString()+"');";
								pnum2 = 0;
							}else{
								add++;
								sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,0,'"+disA0149.get(a).toString()+"');";
								pnum2 = 0;
							}
							rownumber = a+1;
							pcount = 1;
							rownumberw++;
							
//							sScript += "document.getElementById('DCellWeb1').SetCellString(1,"+(pnum+(Integer.parseInt(ZBRow))+rownumber-pcount-1)+",0,'"+disA0149.get(a).toString()+"');";
//							rownumber++;	
//							pcount = 0;	
						}
						sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//ɾ���������	
						sScript += "cell.SetColWidth (1, 200,1 , 0);";//�п�
						sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//����ҳ�߾�
						sScript+="cell.SetColUnhidden('1','1');";//ȡ��������
						sScript+="cell.SetRowUnhidden('1','1');";//ȡ��������
						sScript+="}";
						sScript+="</script>";
						return sScript;	
			}
			}
		
	
			return null;

		}
		


	private int maxZbrow(List<Object[]> listtj) {
		int max = Integer.parseInt(listtj.get(0)[2].toString());
		for(Object[] o:listtj){
			int zbrow = Integer.parseInt(o[2].toString());
			int c = zbrow - max;
			if(c>0){
				max=zbrow;
			}
		}
		return max;
	}

	private String yangshi2(String sScript, int rowNumber) {
		sScript += "cell.MergeCells(1, "+ rowNumber + ", 20, "+ rowNumber + ");";// �ϲ���Ԫ��
		sScript += "cell.SetCellFontSize(1,"+ rowNumber + ",0,17);";// ���������С
		sScript += "cell.SetCellFontStyle(1,"+ rowNumber + ",0,2);";// ����������ʽ
		sScript += "cell.SetCellAlign(1, "+ rowNumber  + ", 0, 32 + 4);";// ���õ�Ԫ����뷽ʽ
		sScript += "cell.SetRowHeight(1, 41, "+ rowNumber  + ", 0);";// ���õ�Ԫ���и�
		
		
		sScript += "cell.SetCellString(2,"+ (rowNumber + 1) + ",0,'ְ��');";//2
		sScript += "cell.SetCellFontStyle(2,"+ (rowNumber + 1) + ",0,2);";// ����������ʽ
		sScript += "cell.SetCellFontSize(2,"+ (rowNumber + 1) + ",0,12);";// ���������С
		sScript += "cell.SetCellAlign(2, "+ (rowNumber + 1) + ", 0, 64 + 32);";// ���õ�Ԫ����뷽ʽ
		sScript += "cell.SetRowHeight(1, 18, "+ (rowNumber + 1) + ", 0);";// ���õ�Ԫ���и�
		sScript += "cell.SetRowHeight(1, 22, "+ (rowNumber + 2) + ", 0);";
		sScript += "cell.MergeCells(2, "+ (rowNumber + 1) + ", 2, "+ (rowNumber + 2) + ");";
		
		
		sScript += "cell.SetCellString(4,"+ (rowNumber + 1) + ",0,'����');";
		sScript += "cell.SetCellFontStyle(4,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(4,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(4, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(4, "+ (rowNumber + 1) + ", 4, "+ (rowNumber + 2) + ");";
		
		sScript += "cell.SetCellString(6,"+ (rowNumber + 1) + ",0,'����');";
		sScript += "cell.SetCellFontStyle(6,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(6,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(6, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(6, "+ (rowNumber + 1) + ", 6, "+ (rowNumber + 2) + ");";
		
		sScript += "cell.SetCellString(8,"+ (rowNumber + 1) + ",0,'����');";
		sScript += "cell.SetCellString(8,"+ (rowNumber + 2) + ",0,'����');";
		sScript += "cell.SetCellFontStyle(8,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(8,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(8,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(8,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(8, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(8, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		
		sScript += "cell.SetCellString(10,"+ (rowNumber + 1) + ",0,'ȫ����');";
		sScript += "cell.SetCellString(10,"+ (rowNumber + 2) + ",0,'ѧ��');";
		sScript += "cell.SetCellFontStyle(10,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(10,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(10,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(10,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(10, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(10, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(12,"+ (rowNumber + 1) + ",0,'��ְ');";
		sScript += "cell.SetCellString(12,"+ (rowNumber + 2) + ",0,'ѧ��');";
		sScript += "cell.SetCellFontStyle(12,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(12,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(12,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(12,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(12, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(12, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(14,"+ (rowNumber + 1) + ",0,'�뵳');";
		sScript += "cell.SetCellString(14,"+ (rowNumber + 2) + ",0,'ʱ��');";
		sScript += "cell.SetCellFontStyle(14,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(14,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(14,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(14,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(14, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(14, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(16,"+ (rowNumber + 1) + ",0,'�μӹ�');";
		sScript += "cell.SetCellString(16,"+ (rowNumber + 2) + ",0,'��ʱ��');";
		sScript += "cell.SetCellFontStyle(16,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(16,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(16,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(16,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(16, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(16, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(18,"+ (rowNumber + 1) + ",0,'��ְ');";
		sScript += "cell.SetCellString(18,"+ (rowNumber + 2) + ",0,'ʱ��');";
		sScript += "cell.SetCellFontStyle(18,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(18,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(18,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(18,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(18, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(18, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(20,"+ (rowNumber + 1) + ",0,'��ע');";
		sScript += "cell.SetCellFontStyle(20,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(20,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(20, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(20, "+ (rowNumber + 1) + ", 20, "+ (rowNumber + 2) + ");";
		sScript += "cell.DrawGridLine(1,"+ (rowNumber + 2) + ",20,"+ (rowNumber + 2) + ",5,4,9);";
		sScript += "cell.SetRowHeight (1, 9,"+ (rowNumber + 3) + " , 0);";
		return sScript;
	}

	private String yangshi1(String sScript) {
		sScript += "cell.SetColWidth (1, 11,1 , 0);";
		sScript += "cell.SetColWidth (1, 80,2 , 0);";
		sScript += "cell.SetColWidth (1, 16,3 , 0);";
		sScript += "cell.SetColWidth (1, 82,4 , 0);";
		sScript += "cell.SetColWidth (1, 16,5 , 0);";
		sScript += "cell.SetColWidth (1, 73,6 , 0);";
		sScript += "cell.SetColWidth (1, 16,7 , 0);";
		sScript += "cell.SetColWidth (1, 64,8 , 0);";
		sScript += "cell.SetColWidth (1, 16,9 , 0);";
		sScript += "cell.SetColWidth (1, 70,10 , 0);";
		sScript += "cell.SetColWidth (1, 16,11 , 0);";
		sScript  += "cell.SetColWidth (1, 70,12 , 0);";
		sScript += "cell.SetColWidth (1, 16,13 , 0);";
		sScript += "cell.SetColWidth (1, 64,14 , 0);";
		sScript += "cell.SetColWidth (1, 16,15 , 0);";
		sScript += "cell.SetColWidth (1, 64,16 , 0);";
		sScript += "cell.SetColWidth (1, 16,17 , 0);";
		sScript += "cell.SetColWidth (1, 64,18 , 0);";
		sScript += "cell.SetColWidth (1, 16,19 , 0);";
		sScript += "cell.SetColWidth (1, 121,20 , 0);";
		return sScript;
	}

	/*private Map<String, String> nsf(List<Map<String, String>> bzmclist, int i) {
		
		Map<String, String> b = bzmclist.get(i);
		String zhiwu = "";
		zhiwu += b.get("a0216a")+"\\r\\n";
		
		String d1 = "";
		String d2 = "";
		if(DBUtil.getDBType() == DBType.MYSQL){ 
			d1 = b.get("code_name");// �Ա�
			d2 = b.get("a0117a");// ����
		} else {
			 d1 = b.get("a0104");// �Ա�
			 d2 = b.get("a0117");// ����
		}
		
		String d3 = b.get("a0141a");// ��һ����
		//------����Ů�ٷǵ��ж�_wx
		if (d1 != null && d1.equals("Ů") && d2.equals("����")) {
			String d11 = b.get("a0101")+"\\r\\n"+"(Ů)";
			b.put("a0101", d11);
		}
		if (d1 != null && d1.equals("Ů") && !d2.equals("����")) {
			String d12 = b.get("a0101")+"\\r\\n"+"(Ů," + d2+ ")";
			b.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("Ů") && !d2.equals("����")) {
			String d13 = b.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			b.put("a0101", d13);
		}
		if(!"".equals(d3)&&d3!=null){
			if("�й���Ա".equals(d3)){
				try {
					String	mes60 = b.get("a0144");//�뵳ʱ��
					if(mes60 != null ){
					String mes61 = mes60.replace(".", "");
					String mes62 = mes61.substring(0,4)+".";
					String mes63 = mes61.substring(4, 6);
					String	mes6 = mes62 + mes63;
					b.put("a0144", mes6);
					}
				} catch (Exception e1) {
					String	mes60 = b.get("a0144");//�뵳ʱ��
					b.put("a0144", mes60);
					
				}
			}else{
				b.put("a0144", b.get("a0141a"));
			}
		}else{
			b.put("a0144", "");
		}
		try {
			String	mes40 = b.get("a0107");//����
			String mes41 = mes40.replace(".", "");
			String mes42 = mes41.substring(0,4)+".";
			String mes43 = mes41.substring(4, 6);
			String	mes4 = mes42 + mes43;
			b.put("a0107", mes4);
			
		} catch (Exception e) {
			// TODO: handle exception
			String	mes40 = b.get("a0107");//����
			b.put("a0107", mes40);

		}
		
		try {
			String	mes30 = b.get("a0243");//��ְʱ��
			String mes31 = mes30.replace(".", "");
			String mes32 = mes31.substring(0,4)+".";
			String mes33 = mes31.substring(4, 6);
			String	mes3 = mes32 + mes33;
			b.put("a0243", mes3);
		} catch (Exception e) {
			String	mes30 = b.get("a0243");//��ְʱ��
			b.put("a0243", mes30);
		}
		
		try {
			String	mes60 = b.get("a0144");//�뵳ʱ��
			
			String mes61 = mes60.replace(".", "");
			String mes62 = mes61.substring(0,4)+".";
			String mes63 = mes61.substring(4, 6);
			String	mes6 = mes62 + mes63;
			b.put("a0144", mes6);
			
		} catch (Exception e) {
			// TODO: handle exception
			String	mes60 = b.get("a0144");//�뵳ʱ��
			b.put("a0144", mes60);

		}

		
		String	mes7 = "";
		String	mes78 ="";
		try {
			String	mes70 = b.get("a0134");//�μӹ���ʱ��
			String mes71 = mes70.replace(".", "");
			String mes72 = mes71.substring(0,4)+".";
			String mes73 = mes71.substring(4, 6);
			mes7 += mes72 + mes73+"\\r\\n";
			b.put("a0134", mes7);
		} catch (Exception e) {
			// TODO: handle exception
			mes78 += b.get("a0134")+"\\r\\n";//�μӹ���ʱ��
			b.put("a0134", mes78);
		}
		
		return b;
	}*/
		
	/*private Map<String, String> nsf1(List<Map<String, String>> bzmclist, int i) {
		
		Map<String, String> b = bzmclist.get(i);
		
		
		String d1 = "";
		String d2 = "";
		if(DBUtil.getDBType() == DBType.MYSQL){ 
			d1 = b.get("code_name");// �Ա�
			d2 = b.get("a0117a");// ����
		} else {
			d1 = b.get("a0104");// �Ա�
			d2 = b.get("a0117");// ����
		}

		String d3 = b.get("a0141a");// ��һ����
		//------����Ů�ٷǵ��ж�_wx
		if (d1 != null && d1.equals("Ů") && d2.equals("����")) {
			String d11 = b.get("a0101")+"\\r\\n"+"(Ů)";
			b.put("a0101", d11);
	
		}
		if (d1 != null && d1.equals("Ů") && !d2.equals("����")) {
			String d12 = b.get("a0101")+"\\r\\n"+"(Ů," + d2+ ")";
			b.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("Ů") && !d2.equals("����")) {
			String d13 = b.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			b.put("a0101", d13);
		}
		if(!"".equals(d3)&&d3!=null){
			if("�й���Ա".equals(d3)){
				try {
					String	mes60 = b.get("a0144");//�뵳ʱ��
					if(mes60 != null ){
					String mes61 = mes60.replace(".", "");
					String mes62 = mes61.substring(0,4)+".";
					String mes63 = mes61.substring(4, 6);
					String	mes6 = mes62 + mes63;
					b.put("a0144", mes6);
					}
				} catch (Exception e1) {
					String	mes60 = b.get("a0144");//�뵳ʱ��
					b.put("a0144", mes60);
					
				}
			}else{
				b.put("a0144", b.get("a0141a"));
			}
		}else{
			b.put("a0144", "");
		}
		String	mes30 = b.get("a0243");//��ְʱ��
		if(mes30.length() == 8 && mes30!=null){
			String mes31 = mes30.replace(".", "");
			String mes32 = mes31.substring(0,4)+".";
			String mes33 = mes31.substring(4, 6);
			String	mes3 = mes32 + mes33;
			b.put("a0243", mes3);
		}
		try {
			String	mes40 = b.get("a0107");//����
			String mes41 = mes40.replace(".", "");
			String mes42 = mes41.substring(0,4)+".";
			String mes43 = mes41.substring(4, 6);
			String	mes4 = mes42 + mes43;
			b.put("a0107", mes4);
		} catch (Exception e) {
			String	mes40 = b.get("a0107");//����
			
			b.put("a0107", mes40);
		}
		try {
			String	mes60 = b.get("a0144");//�뵳ʱ��
			String mes61 = mes60.replace(".", "");
			String mes62 = mes61.substring(0,4)+".";
			String mes63 = mes61.substring(4, 6);
			String	mes6 = mes62 + mes63;
			b.put("a0144", mes6);
		} catch (Exception e) {
			String	mes60 = b.get("a0144");//�뵳ʱ��
			b.put("a0144", mes60);
		}
		try {
			String	mes70 = b.get("a0134");//�μӹ���ʱ��
			String mes71 = mes70.replace(".", "");
			String mes72 = mes71.substring(0,4)+".";
			String mes73 = mes71.substring(4, 6);
			String	mes7 = mes72 + mes73;
			b.put("a0134", mes7);
		} catch (Exception e) {
			String	mes70 = b.get("a0134");//�μӹ���ʱ��
			b.put("a0134", mes70);
		}
		return b;
	}*/
	
	//y�Զ�����������ݹ��õķ���
	public  String  insertmess(String mes,Object[] obj,int pnum, String ZBRow ,int sheet){
		String sScript = "";
		 //4, ��ȡ�����������Ϣֵ���в���
		String ZBLine = obj[3].toString();
		ZBRow = obj[2].toString();
		//��������
		sScript+="document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+(pnum+(Integer.parseInt(ZBRow)))+","+sheet+",'"+mes+"');";
		
		return sScript;	
	}
	

	
	
	//�Զ�����������ݹ��õķ���
	public  String  insertmess1(List listj,Object[] obj,int row, String ZBRow,int rownumber,int count){
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "������";
		}else{
			mes = (String) listj.get(0);//��ѯ���ݿ�����Ϣ
		}
		System.out.println(mes);
		 //4, ��ȡ�����������Ϣֵ���в���
//		String ZBRow = obj[2].toString();
//		System.out.println(ZBRow);
		String ZBLine = obj[3].toString();
//		System.out.println(ZBLine);
		//��������
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow))+rownumber)+"','0','"+mes+"');";
		return sScript;	
	}
	
	
	
	//ɸѡ����
	
	
	//y�Զ�����������ݹ��õķ���
	public  String  insertmess1(List listj,Object[] obj,int row, String ZBRow ){
		int j = 0;
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "������";
		}else if(!"".equals((String) listj.get(0))&&(String) listj.get(0)!=null){
			mes = (String) listj.get(0);//��ѯ���ݿ�����Ϣ
			mes = mes.replace("\n", "\\r\\n");
		}
		 //4, ��ȡ�����������Ϣֵ���в���
		String ZBLine = obj[3].toString();
		//��������
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow)))+"','0','"+mes+"');";
		return sScript;	
	}
	
	//y�Զ�����������ݹ��õķ���
	//j�ж��Ƿ��һ�β��� u ���Ʋ���Ļ�����
	
	public  String  insert(List listj,Object[] obj,int row, String ZBRow ){
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "������";
		}else{
			for(int a =0;a<listj.size();a++){
				mes += (String) listj.get(a)+"\\r\\n";//��ѯ���ݿ�����Ϣ
			}
			
		}
		//4, ��ȡ�����������Ϣֵ���в���
		String ZBLine = obj[3].toString();
		String sheet = obj[4].toString();
		//��������
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow)))+"','"+sheet+"','"+mes+"');";
		return sScript;	
	}
	//һ�в�����
	public String yihangbufz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"0");
		List<Map<String,String>> listy = returnlist(id,"0");
		Map<String, String> map = new HashMap<String, String>();
		String sScript="";
		int size = listy.size();//����
		int count = size*11; // ��Ҫ������
		int y =0; //������
		y =count;
		sScript+="<script type='text/javascript' >";
	    sScript+="function a(){";
	    sScript+="var cell = document.getElementById('DCellWeb1');";
	    sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//���ɱ༭
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
	    int row1 = 1;//��
	    int row2 = 4;//��
	    int row3 = 9;//��
	    int row4 = 3;//��
	    int rowj1 = 1;//��
	    int rowj2 = 4;//��
	    for(int i = 0;i < size;i++){
	    	//�ϲ���Ԫ��
		    sScript+="cell.MergeCells('1','"+row1+"','2','"+row3+"');";//��Ƭ��
		    sScript+="cell.MergeCells('3','"+row1+"','5','"+row4+"');";//ְ���
		    sScript+="cell.SetCellFontSize('3','"+row1+"','0','12');";//�����С
		    sScript+="cell.SetCellFontStyle('3','"+row1+"','0',2);";//����
		    sScript+="cell.SetCellAlign('3','"+row1+"','0',33);";//���䷽ʽ
		    sScript+="cell.SetCellTextStyle('3','"+row1+"','0','2');";//�Զ�����
		    sScript+="cell.MergeCells('6','"+row1+"','8','"+row4+"');";//������
		    sScript+="cell.SetCellFontSize('6','"+row1+"','0','12');";//�����С
		    sScript+="cell.SetCellFontStyle('6','"+row1+"','0',2);";//����
		    sScript+="cell.SetCellAlign('6','"+row1+"','0',36);";//���䷽ʽ
		    sScript+="cell.SetCellTextStyle('6','"+row1+"','0','2');";//�Զ�����
		    countRow = row3;
		    sScript+="cell.MergeCells('3','"+row2+"','8','"+row3+"');";//��Ϣ��
		    sScript+="cell.SetCellFontSize('3','"+row2+"','0','12');";//�����С
		    sScript+="cell.SetCellAlign('3','"+row2+"','0','9');";//���䷽ʽ
		    sScript+="cell.SetCellTextStyle('3','"+row2+"','0','2');";//�Զ�����
		     row1 += 11;//ƽ��
		     row2 += 11;//ƽ��
		     row3 += 11;//ƽ��
		     row4 += 11;//ƽ��
	    	//ѭ������ֵ
	    	 map = listy.get(i);
	    	 String message ="";
	    		String photopath = "";//ƴ����Ƭ����·��
	    		String photoname = "";//ƴ����Ƭ����·��
	    		photopath = map.get("photopath").toUpperCase();
	    		photoname = map.get("photoname");
	    		String birthmes = "";
	    		String xlmes = "";
	    		String joinmes = "";
	    		String workmes = "";
	    		String rzmes = "";
	    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
	    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"���� ";
	    		}
	    		if(map.get("qrzxl")!=null){
	    			xlmes += map.get("qrzxl")+"�� ";
	    		}
	    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
	    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"�뵳�� ";
	    		}
	    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
	    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"�μӹ����� ";
	    		}
	    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
	    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
	    		}
	    		message += map.get("a0111a")+"�ˣ� ";// ������
				message += birthmes;// ����
				message += xlmes;// ѧ��
				message += joinmes;// �뵳
				message += workmes;// �μӹ���
				message += rzmes+"����ְ��";// ��ְʱ��
				//����ͼƬ
	    		sScript+="cell.SetCellImage('1','"+rowj1+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
	    		//����ְ��
	    		sScript+="cell.SetCellString('3','"+rowj1+"','0','"+map.get("a0192")+"');";
	    		//��������
	    		sScript+="cell.SetCellString('6','"+rowj1+"','0','"+map.get("a0101")+"');";
	    		//������Ϣ
	    		sScript+="cell.SetCellString('3','"+rowj2+"','0','"+message+"');";
	    		//ˢ��
	    		sScript+="cell.Invalidate();";
	    		message ="";
	    		photopath ="";
	    		photoname ="";
	    	rowj1 += 11;//ƽ��
	    	rowj2 += 11;//ƽ��
	    }
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//ɾ���������	
	    sScript+="cell.PrintSetOrient('0');";//���ý�ֽ����
	    sScript+="cell.PrintRange('1','1','8','"+y+"');";//���ô�ӡ����
	    sScript+="cell.PrintSetMargin('150','250','350','250');";//����ҳ�߾�
	    sScript+="cell.PrintSetAlign('0','0');";//���õڶ��䷽ʽ
	    sScript+="cell.PrintPageBreak('0');";//���
	    sScript+="}";
		sScript+="</script>";
	    return sScript;	
	}
	//һ�а���������
	public  String yihangjgfz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"0");
		List<Map<String,String>> listy = returnlist(id,"0");
		int size = listy.size();//������
		int count = size*13; // ��Ҫ������
		int z = count - 50; // ��Ҫ��ӵ�����
		int y =0; //������
		y =count;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//���ɱ༭
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������
		String sql = "select t.a0201a from (select a.a0201a,a.b0111,a.b0114 from (select a02.a0201a,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+ids+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201a,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t"; // ��ȡ����
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// ��ȡ�Ļ�������list
		int row1 = 1;//����Ĭ��ֵ
		int row2 = 2;//����Ĭ��ֵ
		int geshu = 0;
		int jigoushu = 0;
		int falsecount = 0;
		int zhaopian = 0;
		int fenqu = 0;//����Ӳ��ҳ
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// ��ȡѡ��ÿһ����������
			Map<String, String> map = new HashMap<String, String>();
			for (int m = 0; m < size; m++) {
				String message = "";
				map = listy.get(m);// ��ȡÿ���˵���Ϣ
				if (jigmc1.equals(map.get("a0201a"))) {// �жϵ�ǰ���Ƿ��ǵ�ǰ����
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ row1 + "','7','" + (row1+1) + "');";// ������
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+row1+"','0','16');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+row1+"','0','12');";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+row1+"','0','2');";//�Զ�����
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+row1+"','0','2');";//����
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+ row1 + "','0','" + map.get("a0201a") + "');";// �����������
					String photopath = "";//ƴ����Ƭ����·��
					String photoname = "";//ƴ����Ƭ����·��
		    		photopath = map.get("photopath").toUpperCase();
		    		photoname = map.get("photoname");
		    		String birthmes = "";
		    		String xlmes = "";
		    		String joinmes = "";
		    		String workmes = "";
		    		String rzmes = "";
		    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
		    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"���� ";
		    		}
		    		if(map.get("qrzxl")!=null){
		    			xlmes += map.get("qrzxl")+"�� ";
		    		}
		    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
		    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"�뵳�� ";
		    		}
		    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
		    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"�μӹ����� ";
		    		}
		    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
		    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
		    		}
		    		message += map.get("a0111a")+"�ˣ� ";// ������
					message += birthmes;// ����
					message += xlmes;// ѧ��
					message += joinmes;// �뵳
					message += workmes;// �μӹ���
					message += rzmes+"����ְ��";// ��ְʱ��
					// �ϲ���Ԫ��
					// ��Ƭ��
					sScript += "document.getElementById('DCellWeb1').MergeCells('1','"+(row2+1+geshu*11+jigoushu*2)+"','2','"+(row2+3+6+geshu*11+jigoushu*2)+"');";
					zhaopian = (row2+3+6+geshu*11+jigoushu*2);
					fenqu++;
					if(fenqu == 4){//��Ӳ��ҳ��
						sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((row2+3+6+geshu*11+jigoushu*2)+1)+"','1');";
						fenqu=0;
					}
					// ְ����
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+(row2+1+geshu*11+jigoushu*2)+"','6','"+(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',2);";//����
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',33);";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
					// ������
					sScript += "document.getElementById('DCellWeb1').MergeCells('7','"+(row2+1+geshu*11+jigoushu*2)+"','9','" +(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('7','"+(row2+1+geshu*11+jigoushu*2)+"','0',36);";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//����
					// ��Ϣ��
					countRow  = ( row2+3+6+geshu*11 +jigoushu*2);
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ (row2 + 4+geshu*11+jigoushu*2) + "','9','" +( row2+3+6+geshu*11 +jigoushu*2)+ "');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','12');";//�����С
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','9');";//���䷽ʽ
				    sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
					// д����Ϣ
				    //��Ƭ��
				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
				    // ְ����
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0192")+ "');";
					// ������
					sScript += "document.getElementById('DCellWeb1').SetCellString('7','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0101")+ "');";
					// ��Ϣ��
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+4+geshu*11+jigoushu*2) + "','0','" + message + "');";
					message = "";
					photopath = "";
					geshu++;	
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				jigoushu++;
				row1 = 1;
				row1 += geshu*11;
				row1 += jigoushu*2;
			}
			falsecount = 0;
		}
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//ɾ���������	
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('0');";//���ý�ֽ����
	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//����ҳ�߾�
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//���õڶ��䷽ʽ
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//���
	    sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ zhaopian +"');";//���ô�ӡ����
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}
	//һ�в�η���
	public  String yihangchengc(HttpServletRequest request) {
		int countRow = 0 ;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"1");
		List<Map<String,String>> listy = returnlist(id,"1");
		int size = listy.size();//������
		int count = size*13; // ��Ҫ������
//		int z = count - 50; // ��Ҫ��ӵ�����
		int y =0; //������
		y =count;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//���ɱ༭
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//������

		/*if(count - 50 > 0){
    		y=50+z;
    	 sScript+="document.getElementById('DCellWeb1').InsertRow(50,"+z+",'0');";//������
    	
    }*/
//		String message = "";
		String sql = "select a.code_name from (select cv.code_name,k.a0148 from code_value cv,(select t.a0148 from (select a01.a0149,a01.a0148 from a01 a01 where a01.a0000 in ("+ids+") group by a01.a0149,a01.a0148) t order by t.a0148) k where cv.code_type='ZB09' and cv.code_value=k.a0148 group by cv.code_name,k.a0148 order by k.a0148) a"; // ��ȡ����
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// ��ȡ�Ļ�������list
		jigmc.removeAll(Collections.singleton(null));
		int row1 = 1;//����Ĭ��ֵ
		int row2 = 2;//����Ĭ��ֵ
		int geshu = 0;
		int jigoushu = 0;
		int falsecount = 0;
		int zhaopian = 0;
		int fenqu = 0;//����Ӳ��ҳ
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// ��ȡѡ��ÿһ����������
			Map<String, String> map = new HashMap<String, String>();
			for (int m = 0; m < size; m++) {
				String message = "";
				map = listy.get(m);// ��ȡÿ���˵���Ϣ
				if (jigmc1.equals(map.get("a0149"))) {// �жϵ�ǰ���Ƿ��ǵ�ǰ����
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ row1 + "','7','" + (row1+1) + "');";// ������
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+row1+"','0','16');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+row1+"','0','12');";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+row1+"','0','2');";//�Զ�����
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+row1+"','0','2');";//����
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+ row1 + "','0','" + map.get("a0149") + "');";// �����������
					String photopath = "";//ƴ����Ƭ����·��
					String photoname = "";//ƴ����Ƭ����·��
		    		photopath = map.get("photopath").toUpperCase();
		    		photoname = map.get("photoname");
		    		String birthmes = "";
		    		String xlmes = "";
		    		String joinmes = "";
		    		String workmes = "";
		    		String rzmes = "";
		    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
		    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"���� ";
		    		}
		    		if(map.get("qrzxl")!=null){
		    			xlmes += map.get("qrzxl")+"�� ";
		    		}
		    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
		    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"�뵳�� ";
		    		}
		    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
		    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"�μӹ����� ";
		    		}
		    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
		    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
		    		}
		    		message += map.get("a0111a")+"�ˣ� ";// ������
					message += birthmes;// ����
					message += xlmes;// ѧ��
					message += joinmes;// �뵳
					message += workmes;// �μӹ���
					message += rzmes+"����ְ��";// ��ְʱ��
					// �ϲ���Ԫ��
					// ��Ƭ��
					sScript += "document.getElementById('DCellWeb1').MergeCells('1','"+(row2+1+geshu*11+jigoushu*2)+"','2','"+(row2+3+6+geshu*11+jigoushu*2)+"');";
					zhaopian = (row2+3+6+geshu*11+jigoushu*2);
					fenqu++;
					if(fenqu == 4){//��Ӳ��ҳ��
						sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((row2+3+6+geshu*11+jigoushu*2)+1)+"','1');";
						fenqu=0;
					}
//					sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ (row2+3+6+geshu*11+jigoushu*2) +"');";//���ô�ӡ����
					// ְ����
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+(row2+1+geshu*11+jigoushu*2)+"','6','"+(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',33);";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//����
					// ������
					sScript += "document.getElementById('DCellWeb1').MergeCells('7','"+(row2+1+geshu*11+jigoushu*2)+"','9','" +(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//�����С
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('7','"+(row2+1+geshu*11+jigoushu*2)+"','0',36);";//���䷽ʽ
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//����

					// ��Ϣ��
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ (row2 + 4+geshu*11+jigoushu*2) + "','9','" +( row2+3+6+geshu*11 +jigoushu*2)+ "');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','12');";//�����С
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','9');";//���䷽ʽ
				    sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//�Զ�����
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//����
				    countRow = ( row2+3+6+geshu*11 +jigoushu*2);
					// д����Ϣ
				    //��Ƭ��
//				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+photopath+"'),'1','1','1');";
				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
				    // ְ����
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0192")+ "');";
					// ������
					sScript += "document.getElementById('DCellWeb1').SetCellString('7','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0101")+ "');";
					// ��Ϣ��
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+4+geshu*11+jigoushu*2) + "','0','" + message + "');";
					message = "";
					photopath = "";
					geshu++;
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				jigoushu++;
				row1 = 1;
				row1 += geshu*11;
				row1 += jigoushu*2;
			}
			falsecount = 0;
		}
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('0');";//���ý�ֽ����
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//ɾ���������	
	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//����ҳ�߾�
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//���õڶ��䷽ʽ
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//���
//	    sScript+="document.getElementById('DCellWeb1').SetColPageBreak('10','1');";//lie
//	    int o = (row2+3+6+geshu*11+jigoushu*2+1);
//	    if(fenqu >0){
	    	sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ zhaopian +"');";//���ô�ӡ����
//	    }
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}
	
	public List<Map<String, String>> returnlist(String[] id,String type){
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		List<Object[]> sqllist =  null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<id.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			sql = "select count(1) from a57 a57 where  a57.a0000="+id[i]+"";
			sql2 = "select a01.a0117 from a01 a01 where  a01.a0000="+id[i]+"";
			sql3 = "select a01.a0104 from a01 a01 where  a01.a0000="+id[i]+"";
			List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
			List sqllist2 = HBUtil.getHBSession().createSQLQuery(sql2).list();
			List sqllist3 = HBUtil.getHBSession().createSQLQuery(sql3).list();
			if("0".equals(sqllist1.get(0).toString())){
				if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a02.a0000 a02a0000,                                                        "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c2 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", sqllist3.get(0).toString());
				}else if((!"".equals(sqllist2.get(0)) &&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c1 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +" and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0104", "");
					map.put("a0117", sqllist2.get(0).toString());
				}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +" and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", "");
				}else{
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
				}
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				map.put("photopath", "");
				map.put("photoname", "");
//				System.out.println("---->"+sql);
				sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				/*map.put("photopath", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
				map.put("photoname", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");*/
			}else{
				if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))||!"null".equals(sqllist3.get(0))||sqllist3.get(0)!=null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104",sqllist3.get(0).toString());
				}else if((!"".equals(sqllist2.get(0))||!"null".equals(sqllist2.get(0))||sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +" and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0104", "");
					map.put("a0117", sqllist2.get(0).toString());
				}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +" and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", "");
				}else{
					//------------------
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
				}
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				System.out.println(sqllist.toString());
				map.put("photopath", ((String) sqllist.get(0)[20] != null  && !((String) sqllist.get(0)[20].toString()).equals("null"))?(String) sqllist.get(0)[20]:"");
				map.put("photoname", ((String) sqllist.get(0)[21] != null  && !((String) sqllist.get(0)[21].toString()).equals("null"))?(String) sqllist.get(0)[21]:"");
			}
			map.put("a0000", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
			map.put("a0101", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");
//			map.put("a0117", ((String) sqllist.get(0)[2] != null  && !((String) sqllist.get(0)[2].toString()).equals("null"))?(String) sqllist.get(0)[2]:"");
			map.put("a0111a",((String) sqllist.get(0)[3] != null  && !((String) sqllist.get(0)[3].toString()).equals("null"))?(String) sqllist.get(0)[3]:"");
//			map.put("a0104", ((String) sqllist.get(0)[4] != null  && !((String) sqllist.get(0)[4].toString()).equals("null"))?(String) sqllist.get(0)[4]:"");
			map.put("a0107", ((String) sqllist.get(0)[5] != null  && !((String) sqllist.get(0)[5].toString()).equals("null"))?(String) sqllist.get(0)[5]:"");
			map.put("a0216a",((String) sqllist.get(0)[6] != null  && !((String) sqllist.get(0)[6].toString()).equals("null"))?(String) sqllist.get(0)[6]:"");
			map.put("qrzxl", ((String) sqllist.get(0)[7] != null  && !((String) sqllist.get(0)[7].toString()).equals("null"))?(String) sqllist.get(0)[7]:"");
			map.put("zzxl", ((String) sqllist.get(0)[8] != null  && !((String) sqllist.get(0)[8].toString()).equals("null"))?(String) sqllist.get(0)[8]:"");
			map.put("a0144", ((String) sqllist.get(0)[9] != null  && !((String) sqllist.get(0)[9].toString()).equals("null"))?(String) sqllist.get(0)[9]:"");
			map.put("a0148", ((String) sqllist.get(0)[10] != null  && !((String) sqllist.get(0)[10].toString()).equals("null"))?(String) sqllist.get(0)[10]:"");
			map.put("a0192", (((String) sqllist.get(0)[16] != null  && !((String) sqllist.get(0)[16].toString()).equals("null"))?(String) sqllist.get(0)[16]:"")+(((String) sqllist.get(0)[6] != null  && !((String) sqllist.get(0)[6].toString()).equals("null"))?(String) sqllist.get(0)[6]:""));
			map.put("a0149", ((String) sqllist.get(0)[12] != null  && !((String) sqllist.get(0)[12].toString()).equals("null"))?(String) sqllist.get(0)[12]:"");
			map.put("a0134", ((String) sqllist.get(0)[13] != null  && !((String) sqllist.get(0)[13].toString()).equals("null"))?(String) sqllist.get(0)[13]:"");
			map.put("a0243", ((String) sqllist.get(0)[14] != null  && !((String) sqllist.get(0)[14].toString()).equals("null"))?(String) sqllist.get(0)[14]:"");
			map.put("a0288", ((String) sqllist.get(0)[15] != null  && !((String) sqllist.get(0)[15].toString()).equals("null"))?(String) sqllist.get(0)[15]:"");
			map.put("a0201a", ((String) sqllist.get(0)[16] != null  && !((String) sqllist.get(0)[16].toString()).equals("null"))?(String) sqllist.get(0)[16]:"");
			map.put("a0201b", ((String) sqllist.get(0)[17] != null  && !((String) sqllist.get(0)[17].toString()).equals("null"))?(String) sqllist.get(0)[17]:"");
			map.put("a0180", ((String) sqllist.get(0)[18] != null  && !((String) sqllist.get(0)[18].toString()).equals("null"))?(String) sqllist.get(0)[18]:"");
			map.put("a0223", (( sqllist.get(0)[19] != null  && !( sqllist.get(0)[19]+"").equals("null"))? sqllist.get(0)[19]+"":"")+"");
			
			list.add(map);
		}
		System.out.println(list.toString());
		return list;
	}
	
	//���ĳһ�ַ�������ĳһ�ַ��ĸ���
	public int getcharnu1(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			k++;
			getcharnu1(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return k;
		}
		return 0;
	}
	
	//���ĳһ�ַ�������ĳһ�ַ��ĸ���
	public int getcharnu(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			i++;
			getcharnu(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return i;
		}
		return 0;
	}
	public String returnMes(HttpServletRequest request,String messageE,String id,String endtime,String personid3){
		String mesvalue = "";
		int count = getcharnu1(messageE,",");
		if(count > 0){
			String ms = messageE+",";
			for(int i1=0;i1<=count;i1++){
				messageE = ms.substring(0,ms.indexOf(","));
				if(messageE.contains("a01")){
					//System.out.println("1-->"+messageE);
					if("a01.a0101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}else if("a01.a0192a".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}else if("a01.a0134".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes = (String) listj.get(0);
						}
						mes = mes.replace(".", "");
						 if(mes.length() == 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else if("a01.a14z101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						mesvalue+=mes.replace("\n", "\\\\r\\\\n");
					}else if("a01.a15z101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						mesvalue+=mes.replace("\n", "\\\\r\\\\n");
					}else if("a01.a0141".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
						String mes = "";
						if(listj1==null || listj1.size()==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.orgid".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
						String mes = "";
						if(listj1==null || listj1.size()==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0160".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
						String mes = "";
						if(listj1==null || listj1.size()==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0104a".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
						String mes = "";
						if(listj1==null || listj1.size()==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0117a".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
						String mes = "";
						if(listj1==null || listj1.size()==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.xgsj".equals(messageE)){
						String mes = "";
						List listj1 = null;
						if(DBUtil.getDBType()==DBType.MYSQL){
							listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
						}else{
							listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
						}
						if(listj1.size()>0){
							mes += listj1.get(0); 
						}
						mes = mes.replace(".", "");
						 if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj1.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}
				}else if(messageE.contains("a02")){
					if(personid3 != null && !"".equals(personid3)){
						if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a0255= '1'   and a0200 = '"+personid3+"'").list();
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mesvalue += listj.get(0);
							}
						}else if("a02.a0247".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mesvalue += listj.get(0);
							}
						}else if("a02.a0219".equals(messageE)){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 a02 where a02.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mes = (String)listj.get(0);
								if("1".equals(mes)){
									mesvalue += "��";
								}
								if("2".equals(mes)){
									mesvalue += "��";
								}
							}
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a0200 = '"+personid3+"'").list();
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mesvalue += listj.get(0);
							}
						}
					}else{
						if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a02.a0255='1' ").list();
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mesvalue += listj.get(0);
							}
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
							if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mesvalue += "";
							}else{
								mesvalue += listj.get(0);
							}
						}
					}
				}else if(messageE.contains("a08")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
					if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a29")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
					if(messageE.contains("a29.a2907")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2947")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2949")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2911")){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0||"".equals(((String)listj1.get(0)))||listj1.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += (String)listj1.get(0);
						}
					}else{
						if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if(messageE.contains("a30")){
					if(messageE.equals("a30.a3001")){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
						if(listj1.size()==0){
							listj1.add("");
						}
						if(listj1==null || listj1.size()==0||"".equals(((String)listj1.get(0)))||listj1.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj1.get(0);
						}
					}else if("a30.a3004".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj.size()>0||listj != null||"".equals(listj.get(0))||"null".equals(listj.get(0))){
							mes += listj.get(0); 
						}
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else {
						List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
						if(listj1.size()==0){
							listj1.add("");
						}
						if(listj1==null || listj1.size()==0 ||"".equals(((String)listj1.get(0)))||listj1.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj1.get(0);
						}
					}
				}else if(messageE.contains("a31")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
					if("a31.a3104".equals(messageE)){
						String mes = "";
						if(listj.size()>0){
							mes += listj.get(0); 
						}
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a37")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a53")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
					if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("csny".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
					if(listj==null || listj.size() ==0|| "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes = "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}
						//					mesvalue += mes;
				}else if("nl".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
					if(listj==null || listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zp".equals(messageE) ){
					String mes = "";
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
					if(listj.size() ==0){
						mes = "";
					}else{
						Object[] sz = listj.get(0);
						Object photopath = sz[0].toString().toUpperCase();
						Object photoname = sz[1];
						//String path = PhotosUtil.PHOTO_PATH+photopath+photoname;
						//��������
						//2017.04.19 yinl �޸�ͼƬ��ַ
						String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname;
						mesvalue += "@"+imagepath+"@";
					}
				}else if("ryzt".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null ||"".equals(listj.get(0))){
						mes = "";
					}else{
						mes = (String) listj.get(0);
						if("1".equals(mes)){
							mes="��ְ��Ա";
						}else if("2".equals(mes)){
							mes="������Ա";
						}else if("3".equals(mes)){
							mes="������Ա";
						}else if("4".equals(mes)){
							mes="��ȥ��";
						}else{
							mes="������Ա";
						}
					}
					mesvalue += mes;
				}else if("rdsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						int length = mes.length();
						if(mes.contains(".")){
							mes = ((String)listj.get(0)).replace(".", "");
							int indexOf = mes.indexOf("(");
							int indexOf2 = mes.indexOf(")");
							String sub = mes.substring(indexOf, length);
							String year = sub.substring(1,5)+".";
							String yue = sub.substring(5,7);
							mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
						}else if(length>=6){
							mes = ((String)listj.get(0)).replace(".", "");
							mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mes = ((String)listj.get(0));
						}
					}
					mesvalue += mes;
				}else if("zgxl".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxlbyxx".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxlsxzy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
				}else if("zgxlrxsj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxlbisj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
				}else if("zgxwbyxx".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
				}else if("zgxwsxzy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
				}else if("zgxwrxsj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxwbisj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("xlqrz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rxsjqrz".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("bysjqrz".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("xwqrz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("xxjyxql".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xxjyxqw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyql".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyqw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08  where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xlzz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rxsjzz".equals(messageE) ){
					String mes ="";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("bysjzz".equals(messageE) ){
					String mes ="";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							for(int a = 0;a<listj.size();a++){
								mes = (String) listj.get(a);
								mes = mes.replace(".", "");
								if(mes.length() >= 6){
									mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
								}else{
									mesvalue +=(String) listj.get(a)+"\\r\\n";
								}
							}
						}
				}else if("xwzz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("xxjyxzl".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08  a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xxjyxzw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyzl".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyzw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("qrzxlrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("qrzxlxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("qrzxwrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("qrzxwxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zzxlrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zzxixxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zzxwrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zzxwxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("dedp".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
					if(listj.size()>0){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("dsdp".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
					if(listj.size()>0){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zwcc".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0149 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
					if(listj==null ||listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rzsj".equals(messageE) ){
					String mes =  "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("rgzwccsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("mzsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("jl".equals(messageE) ){
					ResultSet rs;
					try {
						rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
						while(rs.next()){
							mesvalue +=  formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if("cw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("xm".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("csnyjy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					String mes1 = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes = listj.get(i1)+"\\r\\n";
							mes = (String) listj.get(i1);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mes1 = mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mes1 =(String) listj.get(i1)+"\\r\\n";
							}
						}
						mesvalue += mes1;
						j++;
						if(j==9)
							break;
					}
				}else if("nljy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("zzmmjy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("gzdwjzw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("tbsjn".equals(messageE) ){
					String mes =  "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
					if(listj.size() ==0){
						mes = "";
					}else{
						if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							for(int a = 0;a<listj.size();a++){
								mes = (String) listj.get(a);
								mes = mes.replace(".", "");
								 if(mes.length() >= 6){
									mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
								}else{
									mesvalue +=(String) listj.get(a)+"\\r\\n";
								}
							}
						}
					}
				}else if("dqsj".equals(messageE) ){
					String sysDate = DateUtil.getTimeString();
					sysDate = sysDate.substring(0, 4)+"��"+ sysDate.substring(6, 8)+"��";
					mesvalue += sysDate;
				}else if("dqyhm".equals(messageE) ){
					UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
					String loginnname=user.getLoginname();
					mesvalue += loginnname;
				}else if("jcnx".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
					if("0".equals(listj.get(0))){
						mes = "��";
					}else if("1".equals(listj.get(0))){
						mes = "��";
					}else{
						mes = "";
					}
					mesvalue += mes;
				}else if("mzm".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
					if(listj==null ||listj.size() ==0){
						mes = "";
					}else{
						if(!"����".equals(listj.get(0))){
							mes = listj.get(0).toString();
						}else{
							mes = "";
						}
					}
					mesvalue += mes;
				}else if("xbm".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
					if(listj==null ||listj.size() ==0){
						mes = "";
					}else{
						if("��".equals(listj.get(0))){
							mes = "";
						}else{
							mes = listj.get(0).toString();
						}
					}
					mesvalue += mes;
				}
				ms = ms.substring(ms.indexOf(",")+1,ms.length());
				//System.out.println("2--->"+ms);
			} 
//			mesvalue = "";
			k = 0;
		}else{
			//System.out.println("no");
		if(messageE.contains("a01")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
			if(listj.size()==0){
				listj.add("");
			}
			
			if("a01.a0192a".equals(messageE)){
				if(listj.size()>0){
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mesvalue += "";
					}else{
						mesvalue +=listj.get(0);
					}
				}
			}else if("a01.a0134".equals(messageE)){
				String mes ="";
				if(listj.size()>0){
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 8){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
						}else if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mesvalue +=(String) listj.get(0)+"\\r\\n";
						}
					}//��ѯ���ݿ�����Ϣ
					
				}
			}else if("a01.a0107".equals(messageE)){
				String mes ="";
				if(listj.size()>0){
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 8){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
						}else if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mesvalue +=(String) listj.get(0)+"\\r\\n";
						}
					}//��ѯ���ݿ�����Ϣ
					
				}
			}else if("a01.a14z101".equals(messageE)){
				mesvalue = (String) listj.get(0);
			}else if("a01.a15z101".equals(messageE)){
				mesvalue = (String) listj.get(0);
			}else if("a01.a0141".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0160".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0104a".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.orgid".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0117a".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.xgsj".equals(messageE)){
				List listj1 = null;
				if(DBUtil.getDBType()==DBType.MYSQL){
					listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
				}else{
					listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
				}
				mesvalue += returnmesvalue2(listj1);
			}else if("a01.a0195".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a0000= '"+id+"') a on  a.a0195 = a02.a0201b and a02.a0255='1' where a02.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else{
				mesvalue += returnmesvalue(listj);
			}
		}else if(messageE.contains("a02")){
			if(personid3 != null && !"".equals(personid3)){
				if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a02.a0255='1' and a0200 = '"+personid3+"'").list();
					mesvalue += returnmesvalue(listj);
				}else{
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a0200 = '"+personid3+"'").list();
					mesvalue += returnmesvalue(listj);
				}
			}else if("a02.a0247".equals(messageE)){
				List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else if("a02.a0219".equals(messageE)){
				List list = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 where a0000 = '"+id+"'  ").list();
				if(list.size()>0){
					for(int a = 0;a<list.size();a++){
						mesvalue = (String)list.get(a);
					}
				}
				if("1".equals(messageE)){
					mesvalue = "��";
				}
				if("2".equals(messageE)){
					mesvalue = "��";
				}
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if(messageE.contains("a08")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
			mesvalue += returnmesvalue(listj);
		}else if(messageE.contains("a29")){
			if(messageE.contains("a29.a2907")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2947")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2949")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2911")){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else   if(messageE.contains("a30")){
			if(messageE.equals("a30.a3001")){
				List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else if(messageE.equals("a30.a3007a")){
				List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
				mesvalue += returnmesvalue(listj);
			}else{
				if("a30.a3004".equals(messageE)){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
					mesvalue += returnmesvalue2(listj);
				}
			}
		}else if(messageE.contains("a31")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
			if("a31.a3104".equals(messageE)){
				mesvalue += returnmesvalue2(listj);
			}else{
				mesvalue += returnmesvalue(listj);
			}
		}else if(messageE.contains("a37")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
			mesvalue += returnmesvalue(listj);
		}else if(messageE.contains("a53")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
			if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
				String mes = "";
				if(listj.size()>0){
					if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
				}
				mesvalue += mes;
			}else{
			mesvalue += returnmesvalue(listj);
			}
		}else if("csny".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes = (String) listj.get(0);
				mes = mes.replace(".", "");
				if(mes.length() >= 6){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
				}else{
					mesvalue +=(String) listj.get(0);
				}
			}
		}else if("nl".equals(messageE) ){
			String nowdata = DateUtil.getcurdate();
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zp".equals(messageE) ){
			String mes = "";
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
			if(listj.size() ==0){
				mes = "";
			}else{
				//System.out.println(listj.size());
				Object[] sz = listj.get(0);
				Object photopath = sz[0];
				Object photoname = sz[1];
				//String path = PhotosUtil.PHOTO_PATH+photopath+photoname;
				//System.out.println("photopath------>"+path);
				//��������
				//2017.04.19 yinl �޸�ͼƬ��ַ
				String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname;
				mesvalue += "@"+imagepath+"@";
			}
		}else if("ryzt".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
			if(listj.size()==0){
				listj.add("");
			}
			if(listj==null ||listj.size() ==0||"".equals(((String)listj.get(0)))||listj.get(0) == null || "".equals(listj.get(0))){
				mes = "";
			}else{
				mes = (String) listj.get(0);
				if("1".equals(mes)){
					mes="��ְ��Ա";
				}else if("2".equals(mes)){
					mes="������Ա";
				}else if("3".equals(mes)){
					mes="������Ա";
				}else if("4".equals(mes)){
					mes="��ȥ��";
				}else{
					mes="������Ա";
				}
			}
			mesvalue += mes;
		}else if("rdsj".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
			if(listj.size()==0){
				listj.add("");
			}
			if(listj==null ||listj.size() ==0||"".equals(((String)listj.get(0)))||listj.get(0) == null){
				mes = "";
			}else{
				mes = ((String)listj.get(0)).replace(".", "");
				int length = mes.length();
				if(mes.contains(".")){
					int indexOf = mes.indexOf("(");
					int indexOf2 = mes.indexOf(")");
					String sub = mes.substring(indexOf, length);
					String year = sub.substring(1,5)+".";
					String yue = sub.substring(5,7);
					mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
				}else if(length>=6){
					mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
				}else{
					mes = ((String)listj.get(0));
				}
			}
			mesvalue += mes;
		}else if("xlqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rxsjqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
			mesvalue += returnmesvalue2(listj);
		}else if("bysjqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
			mesvalue += returnmesvalue2(listj);
		}else if("xwqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("xxjyxql".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xxjyxqw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyql".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyqw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xlzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rxsjzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
			mesvalue += returnmesvalue2(listj);
		}else if("bysjzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
			mesvalue += returnmesvalue2(listj);
		}else if("xwzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("xxjyxzl".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xxjyxzw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyzl".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyzw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("qrzxlrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("zgxl".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlbyxx".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlsxzy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlrxsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxlbisj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwbyxx".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwsxzy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwrxsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxwbisj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("qrzxlxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("qrzxwrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("qrzxwxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zzxlrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("zzxixxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zzxwrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("zzxwxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("dedp".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
			mesvalue += returnmesvalue(listj);
		}else if("dsdp".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3927 and cv.code_type='GB4762'").list();
			mesvalue += returnmesvalue(listj);
		}else if("zwcc".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0149 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rzsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("rgzwccsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("mzsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("jl".equals(messageE) ){
			ResultSet rs;
			try {
				rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
				while(rs.next()){
					String mes = "";
					if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
						mes = "";
					}else{
						mes = formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
					}
					mesvalue += mes;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("cw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("xm".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("csnyjy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			String mes1 = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = ""+"\\r\\n";
					}else{
//						mes = listj.get(i1)+"\\r\\n";
						mes = (String) listj.get(i1);
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mes1 = mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mes1 =(String) listj.get(i1)+"\\r\\n";
						}
					}
					mesvalue += mes1;
					j++;
					if(j==9)
						break;
				}
			}else{
				mesvalue="";
				
			}
		}else if("nljy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("zzmmjy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()!=0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("gzdwjzw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("tbsjn".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
			mesvalue += returnmesvalue2(listj);
		}else if("dqsj".equals(messageE) ){
			String sysDate = DateUtil.getTimeString();
			sysDate = sysDate.substring(0, 4)+"��"+ sysDate.substring(6, 8)+"��";
			mesvalue = sysDate;
		}else if("dqyhm".equals(messageE) ){
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String loginnname=user.getLoginname();
			mesvalue += loginnname;
		}else if("jcnx".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
			if("0".equals(listj.get(0))){
				mes = "��" ;
			}else if("1".equals(listj.get(0))){
				mes = "��";//��ѯ���ݿ�����Ϣ
			}else{
				mes = "";
			}
			mesvalue += mes;
		}else if("mzm".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
			if(listj.size()==0){
				mes = "";
			}else{
				if(!"����".equals(listj.get(0))){
					mes = listj.get(0).toString();
				}else{
					mes = "";
				}
			}
			mesvalue += mes;
		}else if("xbm".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
			if(listj==null ||listj.size() ==0){
				mes = "";
			}else{
				if("��".equals(listj.get(0))){
					mes = "";
				}else{
					mes = listj.get(0).toString();
				}
			}
			mesvalue += mes;
		}
		}
		if(mesvalue.contains("null")){
			mesvalue = mesvalue.replaceAll("null", "");
		}
		return mesvalue;
	
	}
	
	public String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//�����ڿ�ͷ  (һ��)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	originaljl.append(jl).append("\r\n");
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	private void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//�ܳ�
		//32����һ�С�
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//���� 64���ֽ�����ƫ�ƣ�ֱ���㹻Ϊֹ��
    		boolean hass = false;
    		while((end+offset)<llength){//32����һ�У����з��ָ�
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32����һ�е�����64���ֽ� ������
    				loffset++;
    				if((end+offset+loffset)>llength){//�����ܳ��� �˳�ѭ��
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//���ܻ����һ��65���ֽڣ���ǰ��һ��
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	public String returnmesvalue(List list){
		String mesvalue="";
		if(list.size()>0){
			for(int a = 0;a<list.size();a++){
				mesvalue += list.get(a)+"\\r\\n";
			}
			
		}
		return mesvalue;
	}
	public String returnmesvalue2(List list){
		String mesvalue="";
		String mes="";
		if(list.size() ==0){
			mes = "";
		}else{
			for(int a = 0;a<list.size();a++){
				System.out.println(list.get(a));
			if("null".equals(list.get(a))||list.get(a)==null||"".equals(list.get(a))){
				mes = "";
			}else{
				mes = (String) list.get(a);
				mes = mes.replace(".", "");
				if(mes.length() >= 8){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
				}else if(mes.length() >= 6){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
				}else{
					mesvalue +=(String) list.get(0)+"\\r\\n";
				}
					
				}
			}
		}
		return mesvalue;
	}
	public String returnmesvalue3(List list){
		String mesvalue="";
		String mes="";
		if(list.size() ==0){
			mes = "";
		}else{
			for(int a = 0;a<list.size();a++){
				System.out.println(list.get(a));
				if("null".equals(list.get(a))||list.get(a)==null||"".equals(list.get(a))){
					mes = "";
				}else{
					mes = (String) list.get(a);
					mes = mes.replace(".", "");
					 if(mes.length() >= 6){
						mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
					}else{
						mesvalue +=(String) list.get(0)+"\\r\\n";
					}
					
				}
			}
		}
		return mesvalue;
	}
	
	//��׼����õĹ��÷���
	public List<String> getPersonID(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			sqly = "select  t.a0000 from ( select a01.a0000,a02.a0223,row_number()over(partition by a01.a0000 order by a02.a0223 desc) rn from A01 a01, A02 a02, Code_Value c1, Code_Value c2 where a02.a0000 = a01.a0000 and a02.A0255 = '1' and a01.status = '1' and c1.code_value = a01.a0117 AND c1.code_type = 'GB3304' and c2.code_value = a01.a0104 AND c2.code_type = 'GB2261' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ) t where t.rn = 1";
		}else{
			sqly = "select distinct a02.a0000                     "		
				       +"from A01 a01, A02 a02              "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
			sqly = sqly + " order by a02.a0223 desc";
		}
//		String sqly = "select distinct a02.a0000                     "		
//			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
//			       +"where a02.a0000 = a01.a0000                                     "
//			       +"and a02.A0255 = '1'                                             "
//			       +"and a01.status = '1'                                            "
//			       +"AND c1.code_type = 'GB3304'                                     "
//			       +"and c1.code_value = a01.a0117                                   "
//			       +"AND c2.code_type = 'GB2261'                                     "
//			       +"and c2.code_value = a01.a0104                                   "
//			       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//		sqly = sqly + " order by a0223 desc";
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
	public List<String> getPersonIDWucc(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			sqly = "select  t.a0000 from ( select a01.a0000,a02.a0223,row_number()over(partition by a01.a0000 order by a02.a0223 desc) rn from A01 a01, A02 a02 where a02.a0000 = a01.a0000 and a02.a0221 is null and a02.A0255 = '1' and a01.status = '1' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ) t where t.rn = 1";

		}else{
			sqly = "select distinct a02.a0000                     "		
				+"from A01 a01, A02 a02              "
				+"where a02.a0000 = a01.a0000                                     "
				+"and a02.A0255 = '1'                                             "
				+"and a01.status = '1'                                            "
				+"and a02.a0221 = '' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
			sqly = sqly + " order by a02.a0223 desc";
		}
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
	//��׼�Զ������õĹ��÷���
	public List<String> getPersonIDZ(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		
			sqly = "select  a01.a0000                     "		
				+"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
				+"where a02.a0000 = a01.a0000                                     "
				+"and a02.A0255 = '1'                                             "
				+"and a01.status = '1'                                            "
				+"AND c1.code_type = 'GB3304'                                     "
				+"and c1.code_value = a01.a0117                                   "
				+"AND c2.code_type = 'GB2261'                                     "
				+"and c2.code_value = a01.a0104                                   "
				+"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//			sqly = sqly + " order by a02.a0223 desc";
		System.out.println(sqly);
//		String sqly = "select distinct a02.a0000                     "		
//			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
//			       +"where a02.a0000 = a01.a0000                                     "
//			       +"and a02.A0255 = '1'                                             "
//			       +"and a01.status = '1'                                            "
//			       +"AND c1.code_type = 'GB3304'                                     "
//			       +"and c1.code_value = a01.a0117                                   "
//			       +"AND c2.code_type = 'GB2261'                                     "
//			       +"and c2.code_value = a01.a0104                                   "
//			       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//		sqly = sqly + " order by a0223 desc";
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
//��׼��ṫ�÷���2
public List<Map<String, String>>  getPersonMess(List cunid, String jgbm,String type,String type2,int o){
	System.out.println(cunid.size());
	List<Map<String,String>> bzmclist = new ArrayList<Map<String,String>>();
	List<Map<String, String>> personlist = new ArrayList<Map<String,String>>();
	List<Object[]> sqllist =  null;
	for (int a = 0 ; a<cunid.size();a++){
		Map<String, String> sqlmap = new HashMap<String, String>();
		String sql ="";
		
		String sql2 = "";
		String sql3 = "";
		sql = "select count(1) from a57 a57 where  a57.a0000 = '"+ cunid.get(a) + "'";
		sql2 = "select a01.a0117a from a01 a01 where  a01.a0000 = '"+ cunid.get(a) + "'";
		sql3 = "select a01.a0104 from a01 a01 where  a01.a0000 = '"+ cunid.get(a) + "'";
		List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
		List sqllist2 = HBUtil.getHBSession().createSQLQuery(sql2).list();
		List sqllist3 = HBUtil.getHBSession().createSQLQuery(sql3).list();
		if("0".equals(sqllist1.get(0).toString())){
			if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a02.a0000 a02a0000,                                                        "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c2.code_type = 'GB2261'                                     "
				       +"and c2.code_value = a01.a0104          ";
				if(0==0){
					sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
				}else{
					sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
				}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", sqllist3.get(0).toString());
			}else if((!"".equals(sqllist2.get(0)) &&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0104", "");
				sqlmap.put("a0117", sqllist2.get(0).toString());
			}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", "");
			}else{
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
			}
//			sqlmap.put("photopath", "");
//			sqlmap.put("photoname", "");
//			System.out.println("---->"+sql);
			sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
			/*map.put("photopath", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
			map.put("photoname", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");*/
		}else{
			if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                    "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104",sqllist3.get(0).toString());
			}else if((!"".equals(sqllist2.get(0))&&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0104", "");
				sqlmap.put("a0117", sqllist2.get(0).toString());
			}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                      "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", "");
			}else{
				//------------------
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                       "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(o==0){
							sql  +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
			}
			sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
//			sqlmap.put("photopath", ((String) sqllist.get(0)[20] != null  && !((String) sqllist.get(0)[20].toString()).equals("null"))?(String) sqllist.get(0)[20]:"");
//			sqlmap.put("photoname", ((String) sqllist.get(0)[21] != null  && !((String) sqllist.get(0)[21].toString()).equals("null"))?(String) sqllist.get(0)[21]:"");
		}
		if("1".equals(type)){
			sql =  sql+"and a02.a0201b = '"+jgbm+"'" ;
		}
		
		if("5".equals(type2)){
			sql = sql + " order by a0148,length(a0201b),a0201b";
		}else{
			sql = sql + " order by a0148,a0223 asc";
		}
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list.size()>0){
			String	mes3 = "";
			String zhiwu = "";
			String a0201b = "";
			for(int i=0;i<list.size();i++){
				String zhizhu = (String)list.get(i)[6];
				if(zhizhu != null && !zhizhu.equals("null")){
					zhiwu += ((String)list.get(i)[6]+"\\r\\n");
				}else{
					zhiwu += (""+"\\r\\n");
				}
				try {
					String mes30 = (String) list.get(i)[14];//��ְʱ��
					String mes31 = mes30.replace(".", "");
					String mes32 = mes31.substring(0,4)+".";
					String mes33 = mes31.substring(4, 6);
					String mes34 = (mes32 + mes33+"\\r\\n");
					mes3 += mes34;
				} catch (Exception e) {
					mes3 += ""+"\\r\\n";
				}
			}
			sqlmap.put("a0216a", zhiwu);
			sqlmap.put("a0243", mes3);
			sqlmap.put("a0101", ((String) list.get(0)[1] != null  && !((String) list.get(0)[1]).equals("null"))?(String) list.get(0)[1]:"");
//			sqlmap.put("a0117", ((String) list.get(0)[2] != null  && !((String) list.get(0)[2]).equals("null"))?(String) list.get(0)[2]:"");
			sqlmap.put("a0111a",((String) list.get(0)[3] != null  && !((String) list.get(0)[3]).equals("null"))?(String) list.get(0)[3]:"");
//			sqlmap.put("a0104", ((String) list.get(0)[4] != null  && !((String) list.get(0)[4]).equals("null"))?(String) list.get(0)[4]:"");
			sqlmap.put("a0107", ((String) list.get(0)[5] != null  && !((String) list.get(0)[5]).equals("null"))?(String) list.get(0)[5]:"");
			sqlmap.put("qrzxl", ((String) list.get(0)[7] != null  && !((String) list.get(0)[7]).equals("null"))?(String) list.get(0)[7]:"");
			sqlmap.put("zzxl", ((String) list.get(0)[8] != null  && !((String) list.get(0)[8]).equals("null"))?(String) list.get(0)[8]:"");
			sqlmap.put("a0144", ((String) list.get(0)[9] != null  && !((String) list.get(0)[9]).equals("null"))?(String) list.get(0)[9]:"");
			sqlmap.put("a0148", ((String) list.get(0)[10] != null  && !((String) list.get(0)[10]).equals("null"))?(String) list.get(0)[10]:"");
			sqlmap.put("a0192", ((String) list.get(0)[11] != null  && !((String) list.get(0)[11]).equals("null"))?(String) list.get(0)[11]:"");
			sqlmap.put("a0149", ((String) list.get(0)[12] != null  && !((String) list.get(0)[12]).equals("null"))?(String) list.get(0)[12]:"");
			sqlmap.put("a0134", ((String) list.get(0)[13] != null  && !((String) list.get(0)[13]).equals("null"))?(String) list.get(0)[13]:"");
			sqlmap.put("A0243", ((String) list.get(0)[14] != null  && !((String) list.get(0)[14]).equals("null"))?(String) list.get(0)[14]:"");
			sqlmap.put("A0288", ((String) list.get(0)[15] != null  && !((String) list.get(0)[15]).equals("null"))?(String) list.get(0)[15]:"");
			sqlmap.put("a0201a",((String) list.get(0)[16] != null  && !((String) list.get(0)[16]).equals("null"))?(String) list.get(0)[16]:"");
			sqlmap.put("A0201b",((String) list.get(0)[17] != null  && !((String) list.get(0)[17]).equals("null"))?(String) list.get(0)[17]:"");
			sqlmap.put("a0180",((String) list.get(0)[18] != null  && !((String) list.get(0)[18]).equals("null"))?(String) list.get(0)[18]:"");
//			sqlmap.put("a0223",(String) list.get(0)[19]);
			sqlmap.put("a0141a", ((String) list.get(0)[20] != null  && !((String) list.get(0)[20]).equals("null"))?(String) list.get(0)[20]:"");
			personlist.add(sqlmap);
		}
		bzmclist = personlist;
	}
	return bzmclist;
}
//��׼��ṫ�÷���3 ���������
public Map<String, String> special(Map map ){
	Map<String, String> map1 = map;
	String d1 = "";
	String d2 = "";
	/*if(DBUtil.getDBType() == DBType.MYSQL){ 
		d1 = map1.get("code_name");// �Ա�
		d2 = map1.get("a0117a");// ����
	} else {
		d1 = map1.get("a0104");// �Ա�
		d2 = map1.get("a0117");// ����
	}*/
	d1 = map1.get("a0104");// �Ա�
	d2 = map1.get("a0117");// ����

	String d3 = map1.get("a0141a");// ��һ����
	//------����Ů�ٷǵ��ж�_wx
	if((d1 == null ||"".equals(d1)) && (d2 != null && !"".equals(d2))){
		String d11 = "";
		if(d2.equals("����")){
			d11 = map1.get("a0101");
		}else{
			d11 = map1.get("a0101")+"\\r\\n"+"(" + d2+ ")";
		}
		map1.put("a0101", d11);
	}else if((d1 != null && !"".equals(d1)) && (d2 == null ||"".equals(d2))){
		String d11 = "";
		if(d1.equals("Ů")){
			d11 = map1.get("a0101")+"\\r\\n"+"(Ů)";
		}else{
			d11 = map1.get("a0101");
		}
		map1.put("a0101", d11);
	}else if((d1 == null || "".equals(d1)) && (d2 == null || "".equals(d2))){
		String d11 = map1.get("a0101");
		map1.put("a0101", d11);
	}else{
		
		if (d1 != null && d1.equals("Ů") && d2.equals("����")) {
			String d11 = map1.get("a0101")+"\\r\\n"+"(Ů)";
			map1.put("a0101", d11);
		}
		if (d1 != null && d1.equals("Ů") && !d2.equals("����")) {
			String d12 = map1.get("a0101")+"\\r\\n"+"(Ů," + d2+ ")";
			map1.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("Ů") && !d2.equals("����")) {
			String d13 = map1.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			map1.put("a0101", d13);
		}
	}
	if(!"".equals(d3)&&d3!=null){
		if("�й���Ա".equals(d3)){
			try {
				String	mes60 = map1.get("a0144");//�뵳ʱ��
				if(mes60 != null ){
				String mes61 = mes60.replace(".", "");
				String mes62 = mes61.substring(0,4)+".";
				String mes63 = mes61.substring(4, 6);
				String	mes6 = mes62 + mes63;
				map1.put("a0144", mes6);
				}
			} catch (Exception e1) {
				String	mes60 = map1.get("a0144");//�뵳ʱ��
				map1.put("a0144", mes60);
				
			}
		}else{
			map1.put("a0144", map1.get("a0141a"));
		}
	}else{
		map1.put("a0144", "");
	}
	
	try {
		String	mes40 = map1.get("a0107");//����
		String mes41 = mes40.replace(".", "");
		String mes42 = mes41.substring(0,4)+".";
		String mes43 = mes41.substring(4, 6);
		String	mes4 = mes42 + mes43;
		map1.put("a0107", mes4);
	} catch (Exception e) {
		String	mes40 = map1.get("a0107");//����
		
		map1.put("a0107", mes40);
	}
	try {
		String	mes60 = map1.get("a0144");//�뵳ʱ��
		String mes61 = mes60.replace(".", "");
		String mes62 = mes61.substring(0,4)+".";
		String mes63 = mes61.substring(4, 6);
		String	mes6 = mes62 + mes63;
		map1.put("a0144", mes6);
	} catch (Exception e) {
		String	mes60 = map1.get("a0144");//�뵳ʱ��
		map1.put("a0144", mes60);
	}
	try {
		String	mes70 = map1.get("a0134");//�μӹ���ʱ��
		String mes71 = mes70.replace(".", "");
		String mes72 = mes71.substring(0,4)+".";
		String mes73 = mes71.substring(4, 6);
		String	mes7 = mes72 + mes73;
		map1.put("a0134", mes7);
	} catch (Exception e) {
		String	mes70 = map1.get("a0134");//�μӹ���ʱ��
		map1.put("a0134", mes70);
	}
	return map1;
}

@PageEvent("information")
public int information() throws RadowException, SQLException {
	//��ȡѡ���˵�id
	String id = (String)request.getSession().getAttribute("personids");	
	String id1 = id.replace("|", "'").replace("@",",");
	//��ѯѡ�е������ڵĻ���

	String sql = "select b01.b0121,b01.b0111, b01.b0101,b01.num, rownum from ( select b0121,b0111,b0101,"
			+ "(select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b = b0111 and b.a0255='1' and b.a0000 in "
				+ "("+id1+")) as num"
			+ " from b01 where b0111 in (select a0201b from a02 a where a.a0000 in ("+id1+") and a.a0255 = '1') order by b0111 ) b01";
/*	
	String sql = "select b0121,b0111,b0101,"
		+ "(select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b = b0111 and b.a0255='1' and b.a0000 in "
		+ "("+id1+")) as num,"
		+ "rownum from b01 where b0111 in (select a0201b from a02 a where a.a0000 in ("+id1+") and a.a0255 = '1') order by b0111";
*/	
	//��ò�ѯ�����ַ���
	List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
	
	//ƴ���ַ���
	StringBuffer jsonStr = new StringBuffer("[");
	int countTemp = list.size();
	int i=1;
	for(Object[] obj:list){
		if(i<countTemp){
		//	jsonStr.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+obj[4].toString()+" "+obj[2].toString()+"\",\"B0121\":\""+obj[0].toString()+"\"},");
			jsonStr.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+obj[4].toString()+" "+obj[2].toString()+"\"},");
		}else{
		//	jsonStr.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+obj[4].toString()+" "+obj[2].toString()+"\",\"B0121\":\""+obj[0].toString()+"\"}");
			jsonStr.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+obj[4].toString()+" "+obj[2].toString()+"\"}");
		}
		i++;
	}
	jsonStr.append("]");
    String value = jsonStr.toString();
    System.out.println(value);
	//��ҳ�洫��
	this.getExecuteSG().addExecuteCode("show('"+value+"')");
	//һ�����ڴ�CustomQueryPageModel ������д��ȥ��
	
	/*//listofname�Ǵ�CustomQueryPageModel �� ������ �������룻
	String  groupid = (String) request.getSession().getAttribute("listofname");
	if(groupid != null && !"".equals(groupid)){
		//��ȡ״̬
		String type = (String) request.getSession().getAttribute("isContain");
		StringBuffer str=new StringBuffer();
		try {
			//��ȡ��Ҫ�ֶ�
			String sql="";
			if("1".equals(type)){
				sql = "select * from ( select b0121,b0111,b0101,(select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b = b0111 and b.a0255='1') as num,ROW_NUMBER() OVER(partition by b0121 order by b0111) as RN from b01 where b0111 like '"+groupid+"%' ) order by b0111";
				
			}else{
				sql = "select b0121,b0111,b0101,(select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b = b0111 and b.a0255='1') as num,'1' as rn  from b01 where b0111='"+groupid+"'";
			}
			//��ò�ѯ�����ַ���
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			//������Ӧ�����
			Map<String,String> map = new HashMap<String, String>();
			//��ȡ��ʼ�ַ�������
			int firstLong=-1;
			int i = 0;
			for(Object[] obj:list){
				System.out.println(obj[0].toString());
				map.put(obj[1].toString(), obj[4].toString());
				if(i==0){
					firstLong = obj[1].toString().length();
				}
				i++;
			}
			//ƴ���ַ���
			str.append("[");
			int countTemp = list.size();
			i=1;
			for(Object[] obj:list){
				String temp = order(obj[1].toString(),map,firstLong);
				if(i<countTemp){
//					str.append("{'B0121':'"+obj[0].toString()+"','B0111':'"+obj[1].toString()+"','B0101':'"+temp+" "+obj[2].toString()+"','num':'"+obj[3].toString()+"'},");
					str.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+temp+" "+obj[2].toString()+"\",\"B0121\":\""+obj[0].toString()+"\"},");
				}else{
//					str.append("{'B0121':'"+obj[0].toString()+"','B0111':'"+obj[1].toString()+"','B0101':'"+temp+" "+obj[2].toString()+"','num':'"+obj[3].toString()+"'}");
					str.append("{\"num\":\""+obj[3].toString()+"\",\"B0111\":\""+obj[1].toString()+"\",\"B0101\":\""+temp+" "+obj[2].toString()+"\",\"B0121\":\""+obj[0].toString()+"\"}");
				}
				i++;
				
			}
			str.append("]");
		} catch (Exception e) {
			str.append("-1");
		}
		String value = str.toString();
		int indexOf1 = value.indexOf("B0121");
		int indexOf2 = value.indexOf("}");
		String substring2 = value.substring(0, indexOf1+8);
		String substring3 = value.substring(indexOf2-1);
		System.out.println(substring2+substring3);
		String valuea = value.substring(0, indexOf1+8) + value.substring(indexOf2-1);
		//��ҳ�洫��
		this.getExecuteSG().addExecuteCode("show('"+valuea+"')");
		//һ�����ڴ�CustomQueryPageModel ������д��ȥ��
		request.getSession().removeAttribute("listofname");
		request.getSession().removeAttribute("personids");
		request.getSession().removeAttribute("isContain");
	}else{
		this.setMainMessage("��˫��ѡ�����");
	}*/
	return EventRtnType.NORMAL_SUCCESS;
}
/**
 * ���ݻ���ID��������Ľṹ���
 * @param groupID ����ID
 * @param map �����������ֵ��
 * @param firstLong ��������ĳ���
 * @return ���
 */
public String order(String groupID, Map<String, String> map, int firstLong) {
	//����ַ���
	String str = "";
	//��ȡ�ַ���
	String subStr = "";
	for(int i=firstLong;i<=groupID.length();i+=4){
		subStr = groupID.substring(0, i);
		if(i==firstLong){
			str =" "+map.get(subStr);
		}else{
			str =" "+str+"."+map.get(subStr);
		}
	}
	return str;
}
 
@PageEvent("openExpPathWin2")
 public int openExpPathWin() throws RadowException{
	this.setRadow_parent_data("ExpPathWin2"); 
	this.openWindow("ExpPathWin2", "pages.publicServantManage.PDFExpPath");
	return EventRtnType.NORMAL_SUCCESS;
 }
/*@PageEvent("information")
public int information() throws RadowException, SQLException{
	//{"B0111":"001.001.002.001.001","B0101":"�㽭ʡ�����ִ�������","B0121":""}
	//listofname�Ǵ�CustomQueryPageModel �� ������ �������룻
	String  Encoder = (String) request.getSession().getAttribute("listofname");
	//��ȡ״̬
	String isContain = (String) request.getSession().getAttribute("isContain");
	String sql = "";
	if("1".equals(isContain)){//�ж��Ƿ�����¼�
		//ѭ����ѯѡ�л����������¼�����
		sql = "select distinct b0111,b0121,b0101 from b01 connect by prior b0111=b0121 start with b0111 = '"+Encoder+"' order by b0121";
	}else{
		sql = "select distinct b0111,b0121,b0101 from b01 where b0111 = '"+Encoder+"' order by b0121";
	}
	if(Encoder != null && !"".equals(Encoder)){
		StringBuffer stringsb =new StringBuffer();
		//ƴ��json��
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map map = new HashMap<String, String>();
		ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
		JSONArray array = new JSONArray();  
		   // ��ȡ����  
		   ResultSetMetaData metaData = rs.getMetaData();  
		   int columnCount = metaData.getColumnCount();  
		   // ����ResultSet�е�ÿ������  
		    while (rs.next()) {  
		        JSONObject jsonObj = new JSONObject();  
		        // ����ÿһ��  
		        for (int i = 1; i <= columnCount; i++) {  
		            String columnName =metaData.getColumnLabel(i);  
		            String value = rs.getString(columnName);  
		            jsonObj.put(columnName, value); 
		        }   
		        array.put(jsonObj);   
		    }  
		System.out.println(array.toString());
		//��ȥѡ�л����ĸ���������
		String value = array.toString();
		int indexOf1 = value.indexOf("B0121");
		int indexOf2 = value.indexOf("}");
		String substring2 = value.substring(0, indexOf1+8);
		String substring3 = value.substring(indexOf2-1);
		System.out.println(substring2+substring3);
		String valuea = value.substring(0, indexOf1+8) + value.substring(indexOf2-1);
		//��ҳ�洫��
		this.getExecuteSG().addExecuteCode("show('"+valuea+"')");
	}else{
		this.setMainMessage("��˫��ѡ�����");
	}
	//һ�����ڴ�CustomQueryPageModel ������д��ȥ��
	request.getSession().removeAttribute("listofname");
	request.getSession().removeAttribute("personids");
	request.getSession().removeAttribute("isContain");
	return EventRtnType.NORMAL_SUCCESS;
} 
*/


}
	
