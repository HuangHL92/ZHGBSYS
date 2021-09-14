package com.insigma.siis.local.pagemodel.meeting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.TPRYXXZSPageModel;

import net.sf.json.JSONArray;
    
public class MeetingPreviewPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX(String publishid) throws RadowException {
		String a0000 = this.getPageElement("a0000").getValue();
		String sql="select a01.A0101,a01.A0184,"
				+ "(select code_name from code_value where code_type = 'GB2261' and code_value.code_value = a01.a0104) A0104,"
				+ "substr(a01.a0107, 0, 4)||'.'||substr(a01.a0107, 5, 2) as a0107, substr(to_char(sysdate,'yyyyMM')-substr(a01.a0107,1,6),1,2) age, "
				+ "(select code_name  from code_value where code_type = 'GB3304'  and code_value.code_value = a01.a0117) A0117,"
				+ "a01.A0111a, a01.A0114a, "
				+ "(select code_name from code_value where code_type = 'ZB09'  and code_value.code_value = a01.a0148) A0148,"
				+ "(select code_name from code_value  where code_type = 'GB4762' and code_value.code_value = a01.a0141) A0141,"
				+ "a01.A0140, substr(a01.A0134, 0, 4)||'.'||substr(a01.A0134, 5, 2) as A0134, a01.A0128, a01.A0196, a01.A0187A, a01.QRZXL, a01.QRZXW,"
				+ " a01.QRZXLXX, a01.QRZXWXX,a01.ZZXL,a01.ZZXW, a01.ZZXLXX,a01.ZZXWXX,a01.A0192a,to_char(a01.A1701) as a1701,"
				+ "(case a01.A14z101 when '��' then '(��)' else nvl(a01.A14z101, '(��)') end) as A14z101,"
				+ " nvl(a01.A15Z101, '(��)') as A15Z101,cbdw "
				+ "	 from A01 a01  where a01.a0000 = '"+a0000+"' ";
		try{
			CommQuery commQuery =new CommQuery();
			List<HashMap<String, Object>> list = commQuery.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
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
					//������ʽ��
					StringBuffer originaljl = new StringBuffer("");
					
					TPRYXXZSPageModel tpryxx=new TPRYXXZSPageModel();
					a1701 = tpryxx.formatJL(isnull(map.get("a1701")),originaljl);
					a1701=a1701.replaceAll(" ", "&ensp;");
					//a1701 = jianli.replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n", "<br>");
				}
				
				this.getExecuteSG().addExecuteCode("document.getElementById('iA0101i').innerHTML='"+isnull(map.get("a0101"))+"';"//����
				+ "document.getElementById('iA0104i').innerHTML='"+isnull(map.get("a0104"))+"';"//�Ա�
				+ "document.getElementById('iA0107_1i').innerHTML='"+isnull(map.get("a0107"))+"<br>("+isnull(map.get("age"))+"��)';"//��������
				+ "document.getElementById('iA0117i').innerHTML='"+isnull(map.get("a0117"))+"';"//����
				+ "document.getElementById('iA0111Ai').innerHTML='"+isnull(map.get("a0111a"))+"';"//����
				+ "document.getElementById('iA0114Ai').innerHTML='"+isnull(map.get("a0114a"))+"';"//������
				+ "document.getElementById('iA0140i').innerHTML='"+isnull(map.get("a0140"))+"';"//�뵳ʱ��
				+ "document.getElementById('iA0134_1i').innerHTML='"+isnull(map.get("a0134"))+"';"//�μӹ���ʱ��
				+ "document.getElementById('iA0128i').innerHTML='"+isnull(map.get("a0128"))+"';"//����״��
				+ "document.getElementById('iA0196i').innerHTML='"+isnull(map.get("a0196"))+"';"//רҵ����ְ��
				+ "document.getElementById('iA0187Ai').innerHTML='"+isnull(map.get("a0187a"))+"';"//�س�
				+ "document.getElementById('iQRZXLi').innerHTML='"+qrzxl+"';"//ȫ����ѧ��ѧλ
				+ "document.getElementById('iQRZXLXXi').innerHTML='"+qrzxlyx+"';"//ȫ���Ʊ�ҵԺУ
				+ "document.getElementById('iZZXLi').innerHTML='"+zzxl+"';"//��ְѧ��
				+ "document.getElementById('iZZXLXXi').innerHTML='"+zzxlyx+"';"//��ְ��ҵԺУ
//				+ "document.getElementById('iA0192Ai').innerHTML='"+isnull(map.get("a0192a"))+"';"//������λ��ְ��
				+ "document.getElementById('iA1701i').innerHTML='"+a1701+"';"//����
				+ "document.getElementById('iA14Z101i').innerHTML='"+isnull(map.get("a14z101"))+"';"//��������
				+ "document.getElementById('iA15Z101i').innerHTML='"+isnull(map.get("a15z101"))+"';"//��������
				+ "document.getElementById('itbri').innerHTML='"+isnull(map.get("cbdw"))+"';");//�����
				
				
				String sql_36="select a3604a,a3601,case when a3611 like '%ȥ��%' then '' else GET_BIRTHDAY(a36.a3607,to_char(sysdate,'yyyyMMdd')) end a3607,a3627,a3611"
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
				

				String sh000 = this.getPageElement("sh000").getValue();
				String sql_nrm="select tp0111,tp0112,tp0121,tp0122,a0192a,tp0113 from hz_sh_a01 where sh000='"+sh000+"' ";
				List<HashMap<String, Object>> list_nrm=commQuery.getListBySQL(sql_nrm);
				HashMap<String, Object> map_nrm=list_nrm.get(0);
				String tp0111=map_nrm.get("tp0111")==null?"":map_nrm.get("tp0111").toString();
				String tp0112=map_nrm.get("tp0112")==null?"":map_nrm.get("tp0112").toString();
				String tp0121=map_nrm.get("tp0121")==null?"":map_nrm.get("tp0121").toString();
				String tp0122=map_nrm.get("tp0122")==null?"":map_nrm.get("tp0122").toString();
				String tp0113=map_nrm.get("tp0113")==null?"":map_nrm.get("tp0113").toString();
				if(!"".equals(tp0111)) {
					if("2".equals(tp0121)) {
						tp0111=tp0111+"����ְ��";
					}else if("3".equals(tp0121)) {
						tp0111="����"+tp0111;
					}else if("4".equals(tp0121)) {
						tp0111="����ת��";
					}
				}else if("".equals(tp0111)&&("3".equals(tp0122)||"4".equals(tp0122))) {
					tp0111="��������";
				}else if("".equals(tp0111)&&"4".equals(tp0121)) {
					tp0111="����ת��";
				}else {
					tp0111="";
				}
				if("2".equals(tp0122)||"3".equals(tp0122)) {
					if("".equals(tp0112)) {
						tp0112= map_nrm.get("a0192a").toString();
					}
				}
				
				this.getExecuteSG().addExecuteCode("document.getElementById('iA0192Ai').innerHTML='"+isnull(map_nrm.get("a0192a"))+"';document.getElementById('iRMLYi').innerHTML='"+isnull(tp0113)+"';document.getElementById('iNRZWi').innerHTML='"+isnull(tp0111)+"';document.getElementById('iNMZWi').innerHTML='"+isnull(tp0112)+"';"
						+ "updateImg('"+URLEncoder.encode(URLEncoder.encode(a0000,"UTF-8"),"UTF-8")+"');");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryMeetingTime")
	@NoRequiredValidate
	public String queryMeetingTime(String publishid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		String time="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from meetingtheme where meetingid =(select meetingid from publish where publishid='"+publishid+"')";
			list = commQuery.getListBySQL(sql); 
			time=list.get(0).get("time").toString();
			String time1="";
			if(time.length()==6) {
				if(time.substring(4).indexOf("0")==0) {
					time1=time.substring(0,4)+"��"+time.substring(4).replace("0", "")+"��";
				}else {
					time1=time.substring(0,4)+"��"+time.substring(4)+"��";
				}
			}else if(time.length()==8) {
				time1=time.substring(0,4)+"��";
				if(time.substring(4,6).indexOf("0")==0) {
					time1=time1+time.substring(4,6).replace("0", "")+"��";
				}else {
					time1=time1+time.substring(4,6)+"��";
				}
				if(time.substring(6).indexOf("0")==0) {
					time1=time1+time.substring(6).replace("0", "")+"��";
				}else {
					time1=time1+time.substring(6)+"��";
				}
			}
			time=time1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return time;
	}
	
	@PageEvent("commQuerySQL")
	@NoRequiredValidate
	public List<HashMap<String, Object>> commQuerySQL(String sql) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryMeeting")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryMeeting(String publishid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		String time="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from meetingtheme a,publish b where a.meetingid =(select meetingid from publish where publishid='"+publishid+"') and  b.publishid='"+publishid+"'";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryPublish")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish(String publishid) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select publishid,meetingid,agendatype,agendaname,sort,"
					+ "case when userid='"+userid+"' then 'update'  when (select count(1) from publishuser c where a.publishid=c.publishid and c.userid='"+userid+"' and c.ischange='1')=1 then 'update' else 'look' end qx_type"
					+ " from publish a where publishid='"+publishid+"'";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryTitle")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryTitle(String publishid,String title04) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from hz_sh_title where publishid='"+publishid+"' and title04='"+title04+"' order by sortid";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //����ļ��в������򴴽�
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //��ѹ·��
        return upload_file;
    }
	
	public final static String disk = getPath();
	
	@PageEvent("queryPenson")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPenson(String publishid,String titleid,String qx_type) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			String condition="";
			if("update".equals(qx_type)) {
				condition="type ";
			}else {
				condition="'3' type ";
			}
			CommQuery commQuery =new CommQuery();
			String sql="select sh000,a0000,tp0111||tp0123 as tp0111,decode(tp0122,'1',tp0112||tp0124,'',tp0112||tp0124,tp0124) tp0112,tp0114,a0101,case when a0192a like '%ԭ%' then a0192a||tp0125 else '����'||a0192a||tp0125 end a0192a,case when a0192a like '%ԭ%' then a0192a||decode(tp0125,'','','��'||tp0125) else '����'||a0192a||decode(tp0125,'','','��'||tp0125) end a0192a_dy,"
					+ " (select c.code_name from code_value c where code_type='NIRENTYPE' and c.code_value=t.tp0121) tp0121,"
					+ " (select c.code_name from code_value c where code_type='NIMIANTYPE' and c.code_value=t.tp0122) tp0122,"
					+ " '��'||decode(a0104,'1','��','2','Ů')||'��'||substr(a0107,1,4)||'.'||substr(a0107,5,2)||'��'||"
					+ " zgxl||"
					+ "case when zgxldj>zgxwdj then '��'||zgxw else '' end"
					+ "||decode(a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=t.a0141))||'��' a0102,"+condition
					+ "	,(select meetingname||' ����� '||agendaname from publish c,meetingtheme d where c.meetingid=d.meetingid and c.publishid=t.publishid) publishname,titleid"
					+ ",zgxl||case when zgxldj>zgxwdj then '��'||zgxw else '' end xlxw,substr(a0107,1,4)||'.'||substr(a0107,5,2) a0107"
					+ " ,(select title01 from hz_sh_title c where c.titleid=t.titleid) titlename "
					+ " from "
					+ "(select sh000,a.a0000,a.tp0111,a.tp0112,a.tp0114,a.tp0121,a.tp0122,a.tp0123,a.tp0124,a.tp0125,a.a0101,a.a0192a,a0104,a0107,zgxl,a0141,sh001,"
					+ "case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "case when (zgxl like '%�о���%' or zgxl like '%˶ʿ%') and zgxl not like '%��У�о���%' then '2' when zgxl like '%��У�о���%' or zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj,type,publishid,titleid"
					+ "    from (select sh000,a0000,tp0111,tp0112,tp0114,tp0121,tp0122,tp0123,tp0124,tp0125,a0101,a0192a,a0104,a0107,a0141,sh001,zgxw,zgxl,'1' type,publishid,titleid from hz_sh_a01 where titleid='"+titleid+"' "
					+ " 	union (select a.sh000,a.a0000,a.tp0111,a.tp0112,a.tp0114,a.tp0121,a.tp0122,a.tp0123,a.tp0124,a.tp0125,a.a0101,a.a0192a,a0104,a0107,a0141,b.sh001,zgxw,zgxl,'2' type,b.publishid_old,titleid_new from hz_sh_a01 a,personcite b  where a.sh000=b.sh000 and titleid_new='"+titleid+"')) a ) t  order by sh001,a0101";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryDYPenson")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryDYPenson(String publishid,String titleid,String qx_type) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			String condition="";
			if("update".equals(qx_type)) {
				condition="type ";
			}else {
				condition="'3' type ";
			}
			CommQuery commQuery =new CommQuery();
			String sql="select sh000,a.a0000,b.a0101,b.a0192a,"
					+ " decode(a.a0104,'1','��','2','Ů')||'��'||substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2)||'��'||decode(a.a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=b.a0141))||'��'||a.zgxl||"
					+ "case when zgxldj>zgxwdj then '��'||a.zgxw else '' end||'��'"
					+ " ||(case when a.a0192a like '%ԭ%' then a.a0192a else '����'||a.a0192a end )||decode(a0221,'','','��'||substr(a0288,1,4)||'.'||substr(a0288,5,2)||'��'||(select c.code_name from code_value c where code_type='ZB09' and c.code_value=b.a0221)||'��'||decode(a.tp0125,'','','��'||a.tp0125))  a0102,tp0114,"+condition
					+ "	,(select meetingname||' ����� '||agendaname from publish c,meetingtheme d where c.meetingid=d.meetingid and c.publishid=a.publishid) publishname,titleid"
					+ " ,(select title01 from hz_sh_title c where c.titleid=a.titleid) titlename "
					+ "    from "
					+ " (select sh000,a0000,a0101,a0192a,a0104,a0107,a0141,sh001,zgxl,tp0114,publishid,titleid,'1' type,tp0125,"
					+ "  case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "	case when (zgxl like '%�о���%' or zgxl like '%˶ʿ%') and zgxl not like '%��У�о���%' then '2' when zgxl like '%��У�о���%' or zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "	case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj from "
					+ "	  (select sh000,a0000,a0101,a0192a,a0104,a0107,a0141,sh001,zgxw,zgxl,tp0114,publishid,titleid,'1' type,tp0125 from hz_sh_a01 where titleid='"+titleid+"' "
					+ " 	union (select a.sh000,a.a0000,a.a0101,a.a0192a,a0104,a0107,a0141,b.sh001,zgxw,zgxl,tp0114,publishid,titleid_new,'2' type,tp0125 from hz_sh_a01 a,personcite b  where a.sh000=b.sh000 and titleid_new='"+titleid+"')) a) a,a01 b where a.a0000=b.a0000 order by sh001,b.a0101";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryNum")
	@NoRequiredValidate
	public String queryNum(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // ��
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// ʮ
        {
            if (si.substring(0, 1).equals("1"))
                sd += "ʮ";
            else
                sd += (GetCH(intInput / 10) + "ʮ");
            sd += queryNum(intInput % 10);
        } else if (si.length() == 3)// ��
        {
            sd += (GetCH(intInput / 100) + "��");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "��";
            sd += queryNum(intInput % 100);
        } else if (si.length() == 4)// ǧ
        {
            sd += (GetCH(intInput / 1000) + "ǧ");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "��";
            sd += queryNum(intInput % 1000);
        } else if (si.length() == 5)// �f
        {
            sd += (GetCH(intInput / 10000) + "�f");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "��";
            sd += queryNum(intInput % 10000);
        }

        return sd;
    }

    private  String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "һ";
                break;
            case 2:
                sd = "��";
                break;
            case 3:
                sd = "��";
                break;
            case 4:
                sd = "��";
                break;
            case 5:
                sd = "��";
                break;
            case 6:
                sd = "��";
                break;
            case 7:
                sd = "��";
                break;
            case 8:
                sd = "��";
                break;
            case 9:
                sd = "��";
                break;
            default:
                break;
        }
        return sd;
    }
	
	@PageEvent("queryImg")
	@NoRequiredValidate
	public void queryImg(String wordPath,String perpath,String imgPath) throws Exception {

		FontSettings.getDefaultInstance().setFontsFolder("D:/fonts", true);
		File file1 = new File(perpath);
		if(!file1.isDirectory()){
			file1.mkdirs();
		}
		File f = new File(imgPath);
		if(f.isDirectory()){
			f.delete();
		}
		
		File file2 = new File(wordPath);
		//String location=wordPath.replace("/", "\\\\");
		//String str = java.net.URLDecoder.decode(java.net.URLDecoder.decode(wordPath,"UTF-8"),"UTF-8");
		InputStream inStream = new FileInputStream(file2);
//		List<BufferedImage> wordToImg = wordToImg(inStream,20,imgPath);
//		BufferedImage mergeImage = mergeImage(false, wordToImg);
//		ImageIO.write(mergeImage, "jpg", new File(imgPath));
		wordToImg3(inStream,20,imgPath);
		//for(int i=1;i<=wordToImg.size();i++) {
			//ImageIO.write(wordToImg.get(i-1), "jpg", new File(imgPath+"_"+i));
		//}
	}
	
	/**    
	* @Description: word��txt�ļ�ת��ͼƬ
	*/ 
	private static List<BufferedImage> wordToImg3(InputStream inputStream, int pageNum, String imgPath) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			Document doc = new Document(inputStream); 
			//int pageCount = doc.getPageCount();  
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.SVG); 
			
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			
			/*if (pageCount > pageNum) {
				//����ǰpageCount�� 
				pageCount = pageNum; 
			}         
			options.setPageCount(pageCount);*/
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			OutputStream output = new FileOutputStream(imgPath);
			
			options.setScale(1);
			doc.save(output, options); 
			output.close();         
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	} 
	
	/**    
	* @param imgPath 
	 * @Description: word��txt�ļ�ת��ͼƬ
	*/ 
	private static List<BufferedImage> wordToImg(InputStream inputStream, int pageNum, String imgPath) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			long old = System.currentTimeMillis();
			Document doc = new Document(inputStream); 
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG); 
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			//options.setResolution(128);
			//options.setPageCount(8);
			//options.setScale(2);
			int pageCount = doc.getPageCount();  
			//int pageCount =options.getPageCount();
			if (pageCount > pageNum) {
				//����ǰpageCount�� 
				//pageCount = pageNum; 
			}           
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			for (int i = 0; i < pageCount; i++) {
				OutputStream output = new ByteArrayOutputStream();
				options.setPageIndex(i); 
				//options.setScale(10);
				//options.setResolution(132);
				options.setScale(2);
				doc.save(output, options); 
				ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output)); 
				imageList.add(javax.imageio.ImageIO.read(imageInputStream));
				output.close();  
			}   
			//OutputStream output = new FileOutputStream(imgPath);
			//options.setPageCount(pageCount);
			//options.setResolution(202);
			//options.setScale(2);
			//doc.save(output, options); 
			//ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output)); 
			//imageList.add(javax.imageio.ImageIO.read(imageInputStream));
			//output.close();
			
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	}
	
	/**   
	* �ϲ���������ͼƬ��һ��ͼƬ
	*  
	* @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
	* @param imgs         ���ϲ���ͼƬ����    
	* @return    
	* @throws IOException 
	*/    
	public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException { 
		// ������ͼƬ   
		BufferedImage destImage = null; 
		// ������ͼƬ�ĳ��͸� 
		int allw = 0, allh = 0, allwMax = 0, allhMax = 0; 
		// ��ȡ�ܳ����ܿ������� 
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);    
			allw += img.getWidth();    
			if (imgs.size() != i + 1) {  
				allh += img.getHeight()+5; 
			} else {     
				allh += img.getHeight(); 
			}        
			if (img.getWidth() > allwMax) {  
				allwMax = img.getWidth();     
			}   
			if (img.getHeight() > allhMax) { 
				allhMax = img.getHeight();  
			}    
		}     
		// ������ͼƬ 
		if (isHorizontal) {  
			destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);  
		} else {  
			destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB); 
		}     
		Graphics2D g2 = (Graphics2D) destImage.getGraphics(); 
		g2.setBackground(Color.LIGHT_GRAY);  
		g2.clearRect(0, 0, allw, allh);   
		g2.setPaint(Color.RED);  
		// �ϲ�������ͼƬ����ͼƬ  
		int wx = 0, wy = 0;   
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);  
			int w1 = img.getWidth();    
			int h1 = img.getHeight();
			// ��ͼƬ�ж�ȡRGB   
			int[] ImageArrayOne = new int[w1 * h1];  
			ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // ����ɨ��ͼ���и������ص�RGB�������� 
			if (isHorizontal) { // ˮƽ����ϲ� 
				destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
			} else { // ��ֱ����ϲ�  
				destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
			}    
			wx += w1;    
			wy += h1 + 5;    
		}     
		return destImage; 
	} 
	
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xmlӦ����..\WebRoot\WEB-INF\classes·����
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ByteArrayInputStream parse(OutputStream out) throws Exception { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		baos = (ByteArrayOutputStream) out; 
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray()); 
		return swapStream;    
	}   
	
	public String isnull (Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;	
	}
	
	public String repNum(Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString().replaceAll("1", "<span class='rmfont'>1</span>").replaceAll("2", "<span class='rmfont'>2</span>")
				.replaceAll("3", "<span class='rmfont'>3</span>").replaceAll("4", "<span class='rmfont'>4</span>")
				.replaceAll("5", "<span class='rmfont'>5</span>").replaceAll("6", "<span class='rmfont'>6</span>")
				.replaceAll("7", "<span class='rmfont'>7</span>").replaceAll("8", "<span class='rmfont'>8</span>")
				.replaceAll("9", "<span class='rmfont'>9</span>").replaceAll("0", "<span class='rmfont'>0</span>");
		}
		return str;
	}
	
	public static String exportWord(String content,String publishid) throws Exception {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/"+publishid+"/";
		 //���� POIFSFileSystem ����  
       POIFSFileSystem poifs = new POIFSFileSystem();    
       //��ȡDirectoryEntry  
       DirectoryEntry directory = poifs.getRoot();    
       try {  
    	   File file = new File(upload_file);
           //����ļ��в������򴴽�
           if (!file.exists() && !file.isDirectory()) {
               file.mkdirs();
           }
           File file2 = new File(upload_file+publishid+".doc");
           //����ļ�������ɾ��
           if (file2.exists() ||file2.isDirectory()) {
               file2.delete();
           }
           //���������  
           OutputStream out = new FileOutputStream(upload_file+publishid+".doc"); 
    	   content=content.replaceAll("&nbsp;", "&ensp;");
           //�����ĵ�,1.��ʽ,2.HTML�ļ�������  
    	   ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(content.getBytes());
    	   directory.createDocument("WordDocument",tInputStringStream); 
           //д��  
           poifs.writeFilesystem(out);  
           //�ͷ���Դ  
           out.close();  
           System.out.println("success");  
       } catch (IOException e) {  
           e.printStackTrace();  
       }    
       return upload_file+publishid+".doc";
	}
	
	/**
	 * �޸���Ա�����
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("openRmb")
	@GridDataRange
	public int openRmb() throws RadowException, AppException{ 
		String a0000=this.getPageElement("a0000").getValue();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();

			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
	
}
