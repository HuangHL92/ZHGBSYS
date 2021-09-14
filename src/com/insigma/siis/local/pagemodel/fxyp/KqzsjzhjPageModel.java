package com.insigma.siis.local.pagemodel.fxyp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.base.CommonSelSQL;
import com.insigma.siis.local.pagemodel.fxyp.base.JZHJ;
import com.insigma.siis.local.pagemodel.fxyp.base.MNTPUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KqzsjzhjPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		
		String en = this.getCueEventName();
		if("EChartsInfo".equals(en)){
			return 0;
		}
		
		this.setNextEventName("noticeSetgrid.dogridquery");
		this.setNextEventName("queryByNameAndIDS_ZCQ");
		
		
		
		try {
			//����ѡ�����ε�λ
			CommQuery cqbs=new CommQuery();
			String dwSQL = CommonSelSQL.getMNTPDW();
			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL(dwSQL);
			
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String b0101 = m.get("b0101")==null?"":m.get("b0101").toString();
				String qxb01id = m.get("qxb01id")==null?"":m.get("qxb01id").toString();
				String b0131 = m.get("b0131")==null?"":m.get("b0131").toString();
				mapCode.put(qxb01id+"@"+b0131+"@x", b0101);
			}
			((Combo)this.getPageElement("dwb01")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	
	
	/**
	 *  ��ѯְ����Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		CommonSelSQL comsql = new CommonSelSQL(this);
		String sql = comsql.getNoticeSetgrid();
		this.pageQuery(sql, "SQL", start, 300);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ɾ���ݴ���Ա
	 *
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("DeletePgridBuffer")
	@GridDataRange
	public int deletePgridBuffer(String a0000) throws Exception{ 
		String mntp00 = this.getPageElement("mntp00").getValue();
		HBUtil.executeUpdate("delete HZ_MNTP_PGRIDBUFFER where a0000=? and mntp00=?",
				new Object[]{a0000,mntp00});
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		//��õ�ǰҳ�������  ����  �༭  ������
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();
		//String mntp00 = this.getPageElement("mntp00").getValue();
		//String b01id=this.getPageElement("noticeSetgrid").getValue("b01id",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ��λ����
	 * @param a0200NRs
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("addInfo")
	@Transaction
	public int addInfo(String a0200NRs) throws RadowException, AppException{
		String userID = SysManagerUtils.getUserId();
		try {
			//String[] txtArray = txt.split("@@");
			String mntp00 = this.getPageElement("mntp00").getValue();
			String b01id = this.getPageElement("b01idkq").getValue();
			if(StringUtils.isEmpty(b01id)){
				this.setMainMessage("��ѡ�����ε�λ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String fxyp06 = this.getPageElement("fxyp06").getValue();
			String a0201e = this.getPageElement("a0201e").getValue();
			String a0501b = this.getPageElement("a0501b").getValue();
			
			
			//String fxyp00 = this.getPageElement("yxgwSel").getValue();//�õ�λ�������θ�λ
			String zwqc01 = this.getPageElement("zwqc01").getValue();//��λ����
			
			String dqnrzwqcid = this.getPageElement("dqnrzwqcid").getValue();//��ǰ����ְ��ȫ��id
			
			//String fxyp02 = this.getPageElement("yxgwSel_combo").getValue();
			String fxyp02 = this.getPageElement("dwmckqgw").getValue().replaceAll("\r|\n|\r\n", "");//�������θ�λ
			
			String a0192aMN = this.getPageElement("a0192aMN").getValue().replaceAll("\r|\n|\r\n", "");
			
			
			
			//ԭ��λ
			String gwa02 = this.getPageElement("gwa02").getValue();
			
			
			if(!StringUtils.isEmpty(gwa02)&&!"1".equals(gwa02)){//ѡ����ԭ��λ����Ϊ����������ѡ
				//String code = a0200+"@@"+fxyp07+"@@"+fxyp00+"@@"+tp0100+"@@"+a0215a+"@@"+a0192a+"@@"+zwqc00;
				String[] ygwInfo = gwa02.split("@@");
				String fxyp07 = ygwInfo[1];
				String a0200 = ygwInfo[0];
				String zwqc00Select = ygwInfo[6];
				String[] a0200NRsArray;
				//��ȡѡ�����Ϣ
				if(a0200NRs==null){
					a0200NRs = "";
					a0200NRsArray = new String[0];
				}else{
					a0200NRsArray = a0200NRs.split(",");
				}
				//ְ��ȫ�ƣ� ͳ������ѡ��ĸ�λ �ж��ٸ�ȫ�ư��ˡ�   ����ж�������������ķ�ʽ��
				Map<String, String> zwqc00Map = new HashMap<String, String>();
				//��λ�󶨵�ְ��ȫ��id
				Map<String, String> a0200sMap = new HashMap<String, String>();
				//ѡ��ĸ�λid
				//String a0200NRS = a0200 + ",";
				String a0200NRS = "";
				for(int i=0; i<a0200NRsArray.length;i++){
					String a0200NRInfo = a0200NRsArray[i];
					//a0200+":"+(fxyp07||'')+":"+(zwqc00||'')
					String[] a0200NRInfoArray = a0200NRInfo.split(":");
					String a0200CKbox = a0200NRInfoArray[0];
					
					a0200NRS = a0200NRS + a0200CKbox +",";
					
					String fxyp07CKbox = a0200NRInfoArray[1];
					String zwqc00 = a0200NRInfoArray[2];
					
					
					if(!StringUtils.isEmpty(zwqc00)){
						zwqc00Map.put(zwqc00, fxyp07CKbox);
						a0200sMap.put(a0200CKbox, zwqc00);
					}
					
				}
				if(a0200NRS.length()>0){
					a0200NRS = a0200NRS.substring(0,a0200NRS.length()-1);
				}
				
				
				//�ϲ�ѡ��ĸ�λ�͸�ѡ�ĸ�λ
				//ע��  ѡ��ĸ�λҲ�г����ˣ����Բ��úϲ�
				/*if(!StringUtils.isEmpty(zwqc00Select)){
					zwqc00Map.put(zwqc00Select, "");
					a0200sMap.put(a0200, zwqc00Select);
				}*/
				//�漰����ȫ��
				int zwqc00MapSize = zwqc00Map.size();
				int a0200NRSize = a0200NRsArray.length;
				
				
				
				
				//fxyp07=1 �ø�λ��������λ  =-1 �ø�λ�Ǳ���λ��λ���������
				if((StringUtils.isEmpty(fxyp07)||"-1".equals(fxyp07))){//ѡ��ԭ��λ  ȫ��û�д�ȫ��
					HBSession sess = HBUtil.getHBSession();
					String zwqc00 = UUID.randomUUID().toString();
					List<HashMap<String, Object>> nrgwList = MNTPUtil.getGWByA0200(a0200NRS);
					
					//û��ȫ��   ����ȫ���кü���
					if(zwqc00MapSize==0||zwqc00MapSize>1){
						
						//����ȫ��
						HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01,a0501b) values(?,?,?,1,?,?)",
								new Object[]{mntp00,a0192aMN,zwqc00,zwqc01,a0501b});
					}else{
						zwqc00 = zwqc00Map.keySet().iterator().next();
					}
					
					String a0000 = "";
					for(HashMap<String, Object>  nrgwMap : nrgwList){
						String fxyp00 = UUID.randomUUID().toString();
						String a0215a = nrgwMap.get("a0215a")==null?"":nrgwMap.get("a0215a").toString();
						String b0131 = nrgwMap.get("b0131")==null?"":nrgwMap.get("b0131").toString();
						String a0201b = nrgwMap.get("a0201b")==null?"":nrgwMap.get("a0201b").toString();
						String zrrx = nrgwMap.get("zrrx")==null?"":nrgwMap.get("zrrx").toString();
						String b01idNR = nrgwMap.get("b01id")==null?"":nrgwMap.get("b01id").toString();
						String a0225 = nrgwMap.get("a0225")==null?"":nrgwMap.get("a0225").toString();
						a0201e = nrgwMap.get("a0201e")==null?"":nrgwMap.get("a0201e").toString();
						try {
							a0225 = Integer.valueOf(a0225)+"";
						} catch (Exception e) {
							a0225 = "";
						}
						a0000 = nrgwMap.get("a0000")==null?"":nrgwMap.get("a0000").toString();
						//�󶨵�a0200
						String a0200BD = nrgwMap.get("a0200")==null?"":nrgwMap.get("a0200").toString();
						//������a02�еĵ�λid Ҫ�ж��Ƿ���������  ������������ƽ̨��
						if(a0201b!=null&&a0201b.length()>=15){
							if(a0201b.substring(0,11).equals("001.001.004")){//����
								if("1001,1003,1004,1005,1006,1007".contains(b0131)){
									zrrx = b0131;
									b01idNR = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+a0201b.substring(0,15)+"'");
								}
								
							}
						}
						
						//�󶨵� ����Ҫ����
						
						
						//zwqc00MapSize==0 ������ְ�� ����      ֻ��һ��ȫ�ƣ���һЩû�а󶨵���Ҫ��
						if(zwqc00MapSize==0||(zwqc00MapSize==1&&a0200sMap.get(a0200BD)==null)){
							HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
									new Object[]{fxyp00,a0215a,userID,zrrx,b01idNR,mntp00,a0201e,zwqc00});
							//��������
							HBUtil.executeUpdate("insert into GWZWREF(mntp00,zwqc00,fxyp00,a0200,a0000) values(?,?,?,?,?)",
									new Object[]{mntp00,zwqc00,fxyp00,a0200BD,a0000});
						}
						
						//��ʼ������
						CommonSelSQL comsql = new CommonSelSQL(this,mntp00,b01idNR,zrrx);
						comsql.setGWSort();
						//���������
						HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx)values(?,?,?,?,?,?) ",
								new Object[]{mntp00,"2",fxyp00,b01idNR,a0225,zrrx});
					}
					String a0000s = this.getPageElement("a0000s").getValue();
					if(!"-1".equals(fxyp07)){//ѡ�����θ�λ ���ĸ�λ����ԱҲ����ѡ
						this.getPageElement("a0000s").setValue(a0000s+","+a0000);
					}
					sess.flush();
					this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+zwqc00+"');");
				}else if("1".equals(fxyp07)){
					String zwqc00 = ygwInfo[6];
					//����ȫ��   �˴������޸�ȫ��
					HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=?,zwqc01=?,a0501b=? where zwqc00=?",
							new Object[]{a0192aMN,zwqc01,a0501b,zwqc00});
					this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+zwqc00+"');");
				}
			}else if(StringUtils.isEmpty(dqnrzwqcid)){//û��ѡ����δ���ι��������������ְ�� ��ǰ���� ְ��ȫ��id
				String fxyp00 = UUID.randomUUID().toString();
				fxyp02 = this.getPageElement("dwmckqgw").getValue();//�������θ�λ
				if(StringUtils.isEmpty(a0192aMN)){//(Ĭ�����λ����һ��)
					a0192aMN = fxyp02;
				}
				
				HBSession sess = HBUtil.getHBSession();
				//����ȫ��
				HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01,a0501b) values(?,?,?,1,?,?)",
						new Object[]{mntp00,a0192aMN,fxyp00,zwqc01,a0501b});
				
				HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
						new Object[]{fxyp00,fxyp02,userID,fxyp06,b01id,mntp00,a0201e,fxyp00});
				sess.flush();
				this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+fxyp00+"');");
			}else if(!StringUtils.isEmpty(dqnrzwqcid)){//�����и�λ    ����µĸ�λ��ȫ����   ���ȫ��
				String fxyp00 = UUID.randomUUID().toString();
				
				HBSession sess = HBUtil.getHBSession();
				//����ȫ��
				HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=?,zwqc01=?,a0501b=? where zwqc00=?",
						new Object[]{a0192aMN,zwqc01,a0501b,dqnrzwqcid});
				
				HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
						new Object[]{fxyp00,fxyp02,userID,fxyp06,b01id,mntp00,a0201e,dqnrzwqcid});
				sess.flush();
				this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+dqnrzwqcid+"');");
			}
			
			
			
			
			
			
			
			
			
			
			
			/*if(!StringUtils.isEmpty(fxyp00)){
				List<Object> lo = HBUtil.getHBSession().createSQLQuery("select 1 from hz_mntp_zwqc where zwqc00='"+fxyp00+"'").list();
				if(lo.size()==0){
					this.setMainMessage("��ְ���ڿ����Ѿ������ڣ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}*/
			
			
			
			
			
			
			//if((StringUtils.isEmpty(fxyp00)||"1".equals(fxyp00))&&StringUtils.isEmpty(dqnrzwqcid)){//������λ  ��  ȫ��     1Ϊ���˵Ŀ�ѡ��
				
				
				
				/*fxyp00 = UUID.randomUUID().toString();
				fxyp02 = this.getPageElement("dwmckqgw").getValue();//�������θ�λ
				if(StringUtils.isEmpty(a0192aMN)){//(Ĭ�����λ����һ��)
					a0192aMN = fxyp02;
				}
				String userID = SysManagerUtils.getUserId();
				HBSession sess = HBUtil.getHBSession();
				//����ȫ��
				HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01) values(?,?,?,1,?)",
						new Object[]{mntp00,a0192aMN,fxyp00,zwqc01});
				
				HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
						new Object[]{fxyp00,fxyp02,userID,fxyp06,b01id,mntp00,a0201e,fxyp00});
				sess.flush();
				this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+fxyp00+"');");*/
				
			//}else if((!StringUtils.isEmpty(fxyp00)&&!"1".equals(fxyp00))&&StringUtils.isEmpty(dqnrzwqcid)){//�����޸�λ    ��ӵ����еĸ�λ��   ��Ϊ��������ѡ
				//����ȫ��   �˴������޸�ȫ��
				/*HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=?,zwqc01=? where zwqc00=?",
						new Object[]{a0192aMN,zwqc01,fxyp00});
				this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+fxyp00+"');");*/
				
			//}else if((StringUtils.isEmpty(fxyp00)||"1".equals(fxyp00))&&!StringUtils.isEmpty(dqnrzwqcid)){//�����и�λ    ����µĸ�λ��ȫ����   ���ȫ��
				/*fxyp00 = UUID.randomUUID().toString();
				String userID = SysManagerUtils.getUserId();
				
				HBSession sess = HBUtil.getHBSession();
				//����ȫ��
				HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=?,zwqc01=? where zwqc00=?",
						new Object[]{a0192aMN,zwqc01,dqnrzwqcid});
				
				HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
						new Object[]{fxyp00,fxyp02,userID,fxyp06,b01id,mntp00,a0201e,dqnrzwqcid});
				sess.flush();
				this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+dqnrzwqcid+"');");*/
			//}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//��ѯ��������ԱIDs  �����ݴ���
	@PageEvent("queryByNameAndIDS_ZCQ")
	public int queryByNameAndIDS_ZCQ(String a0000s) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		String mntp00 = this.getPageElement("mntp00").getValue();
		
		
		//���ݴ���Ա��Ϣ�浽���ݿ�
		if(!StringUtils.isEmpty(a0000s)){
			
			HBUtil.executeUpdate("insert into HZ_MNTP_PGRIDBUFFER(mntp00,a0000) "
					+ "select * from (SELECT '"+mntp00+"', REGEXP_SUBSTR('"+a0000s+"', '[^,]+', 1, LEVEL) a0000 FROM DUAL CONNECT BY REGEXP_SUBSTR('"+a0000s+"', '[^,]+', 1, LEVEL) IS NOT NULL"
					+ ") x where not exists (select 1 from HZ_MNTP_PGRIDBUFFER t where t.a0000=x.a0000 and t.mntp00='"+mntp00+"') "
					);
		}
		
		//a0000s = a0000s.replaceAll(",", "','");
		String Sql = "select t.a0201b,t.a0200,t.a0200 id,t.a0000,a01.a0101,"
		  		+ " a01.a0192a,t.a0215a,"
		  		+ "nvl((select max('2') from fxyp f where f.a0200=t.a0200 and mntp00='"+mntp00+"'),'34') personStatus,"
		  				+ "'' fxyp07,"
		  		+ " (nvl(t.a0201e,'Z')||'01') zrrx,"
		  		+ " (select b01id from b01 where b0111 = t.a0201b) b01id from a02 t, a01 "
		  		+ " where t.a0000=a01.a0000  AND t.a0281 = 'true' AND t.a0255 = '1'   and "
		  		+ " a01.a0000 in (select a0000 from HZ_MNTP_PGRIDBUFFER where mntp00='"+mntp00+"')";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(Sql.toString());
		JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
		this.getExecuteSG().addExecuteCode("doAddPerson.addPgridBuffer("+updateunDataStoreObject.toString()+");");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����Ӧ�ĸ�λ��������ԱIDs//����
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String fxyp00) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		String a0000s = this.getPageElement("a0000s").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		
		//����
		if(a0000s!=null&&!"".equals(a0000s)){//�����λ��Ա
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'"+fxyp00+"' fxyp00 from a01 t");
		
			sql.append(" where t.a0000 in ('"+a0000s+"') ");
			sql.append(" and not exists (select 1 from RXFXYP p where p.a0000=t.a0000");
			sql.append(" and zwqc00='"+fxyp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into RXFXYP(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115,zwqc00 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					String maxsortnum = HBUtil.getValueFromTab("max(sortnum)", "RXFXYP", "zwqc00='"+fxyp00+"'");
					int i=Integer.valueOf(maxsortnum==null?"0":maxsortnum);
					ps = conn.prepareStatement(insertsql);
					for(HashMap m : listCode){
						//�������´���
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//����ְʱ�䴦��
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//����ְʱ�䴦��
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						
						ps.setString(1, m.get("tp0100").toString());
						ps.setString(2, textFormat(m.get("a0000")));
						ps.setString(3, m.get("type").toString());
						ps.setString(4, textFormat(m.get("tp0101")));
						ps.setString(5, textFormat(m.get("tp0102")));
						ps.setString(6, textFormat(m.get("tp0103")));
						ps.setString(7, textFormat(m.get("tp0104")));
						ps.setString(8, textFormat(m.get("tp0105")));
						ps.setString(9, textFormat(m.get("tp0106")));
						ps.setString(10, textFormat(m.get("tp0107")));
						ps.setInt(11, ++i);
						ps.setString(12, fxyp00);
						ps.setString(13, "");
						ps.setString(14, textFormat(m.get("tp0111")));
						ps.setString(15, textFormat(m.get("tp0112")));
						ps.setString(16, textFormat(m.get("tp0115")));
						ps.setString(17, fxyp00);
						ps.addBatch();
						
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					
					
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("����ʧ�ܣ�");
					e.printStackTrace();
				}
				this.setMainMessage("��ѯʧ�ܣ�");
				e.printStackTrace();
			}
			
			int add = -1;
			/*//��������λ
			if(!"".equals(this.getPageElement("yxgwSel").getValue())){
				add = 0;
			}*/
			
			//ԭ��λ
			String gwa02 = this.getPageElement("gwa02").getValue();
			if(!StringUtils.isEmpty(gwa02)&&!"1".equals(gwa02)){//ѡ����ԭ��λ����Ϊ����������ѡ
				//String code = a0200+"@@"+fxyp07+"@@"+fxyp00+"@@"+tp0100+"@@"+a0215a+"@@"+a0192a+"@@"+zwqc00;
				String[] ygwInfo = gwa02.split("@@");
				String fxyp07 = ygwInfo[1];
				if("1".equals(fxyp07)){//׷����ѡ
					add = 0;
				}
			}
			
			this.getExecuteSG().addExecuteCode("refreshPageData.refresh('"+fxyp00+"',"+(add)+");");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			//this.setMainMessage("�޷���ѯ������Ա��");
			//return EventRtnType.NORMAL_SUCCESS;
		}
			
		
		this.getExecuteSG().addExecuteCode("refreshPageData.refresh();");	
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	//����
	@PageEvent("movePB_by_nm")
	public int movePB_by_nm(String isNMOnly) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		//����
		String mntp00 = this.getPageElement("mntp00").getValue();
		String a0200s = this.getPageElement("a0200s").getValue();
		
		String nmzwqc = this.getPageElement("nmzwqc").getValue().replaceAll("\r|\n|\r\n", "");//����ȫ������
		String dqnmzwqcid = this.getPageElement("dqnmzwqcid").getValue();//����ȫ��id
		if("1".equals(isNMOnly)){//ֻ������  �Ȼ�ȡa0000,�ж�����ְ�����������������  ���������Ϣ  �������� �����������Ա��Ϣ��������Ϣ
			String[] a0200M = a0200s.split(":");
			String a0000 = a0200M[4];
			String Sql = "select distinct a.a0192a,a.zwqc00,a.fxyp07,b.tp0100 from hz_mntp_zwqc a,rxfxyp b "
					+ "where a.zwqc00=b.zwqc00 and b.a0000 in('"+a0000+"') and a.mntp00='"+mntp00+"' and a.fxyp07=-1";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(Sql);
			if(listCode.size()>0){
				HashMap<String, Object> m = listCode.get(0);
				String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
				String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
				nmzwqc = a0192a;
				dqnmzwqcid = zwqc00;
			}else{
				nmzwqc = "";
				dqnmzwqcid = "";
			}
		}
		StringBuffer sql = new StringBuffer();
		if(a0200s!=null&&!"".equals(a0200s)){
			Map<String, String[]> a0200Map = new HashMap<String, String[]>();
			String[] a0200arr = a0200s.split(",");
			a0200s = "";
			for(int i=0;i<a0200arr.length;i++){
				String[] a0200M = a0200arr[i].split(":");
				//������
				if(a0200M[3].indexOf("2")==-1){
					a0200Map.put(a0200M[0], new String[]{a0200M[1],a0200M[2]});
					a0200s += a0200M[0]+",";
				}
				
			}
			a0200s = a0200s.substring(0,a0200s.length()-1);
			a0200s = a0200s.replaceAll(",", "','");
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,a02.a0215a,a02.a0200,"
					+ " '' tp0111,a02.a0215a tp0112,'' tp0113,'' tp0114,'' tp0115,a02.a0201e,a02.a0201b  "
					+ " from a01 t,a02");
		
			sql.append(" where t.a0000=a02.a0000 and a02.a0200 in ('"+a0200s+"') ");
			sql.append(" and not exists (select 1 from FXYP p where p.a0200=a02.a0200");
			sql.append(" and mntp00='"+mntp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into RXFXYP(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115,zwqc00 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					ps = conn.prepareStatement(insertsql);
					int c = 0;//���� �� ȫ�ƺ���Աֻ��¼һ��
					String fxyp00_nm = UUID.randomUUID().toString();//����ְ��ȫ��id
					
					for(HashMap m : listCode){
						
						//�������´���
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//����ְʱ�䴦��
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//����ְʱ�䴦��
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						
						
						if(c==0){
							//����ȫ��
							if(!StringUtils.isEmpty(dqnmzwqcid)){//��������ȫ��   ����Ҫ���ȫ��ְ��  �� ��Ա��Ϣ��
								fxyp00_nm = dqnmzwqcid;
							}else{//nmzwqc���Ϊ�� ���ǵ����������� Ĭ��a0192a��λ
								HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01) values(?,?,?,-1,-1)",
										new Object[]{mntp00,"".equals(nmzwqc)?textFormat(m.get("tp0106")):nmzwqc,fxyp00_nm});
								ps.setString(1, m.get("tp0100").toString());
								ps.setString(2, textFormat(m.get("a0000")));
								ps.setString(3, m.get("type").toString());
								ps.setString(4, textFormat(m.get("tp0101")));
								ps.setString(5, textFormat(m.get("tp0102")));
								ps.setString(6, textFormat(m.get("tp0103")));
								ps.setString(7, textFormat(m.get("tp0104")));
								ps.setString(8, textFormat(m.get("tp0105")));
								ps.setString(9, textFormat(m.get("tp0106")));
								ps.setString(10, textFormat(m.get("tp0107")));
								ps.setInt(11, 1);
								ps.setString(12, fxyp00_nm);
								ps.setString(13, "");
								ps.setString(14, textFormat(m.get("tp0111")));
								ps.setString(15, textFormat(m.get("tp0112")));
								ps.setString(16, textFormat(m.get("tp0115")));
								ps.setString(17, fxyp00_nm);
								ps.addBatch();
							}
							
						}
						
						
						
						//�ж��Ƿ��Լ���Ϊ��ѡ�������ˣ��Լ���������ѡ
						List<HashMap<String, Object>> nrrxList = MNTPUtil.getNRRXByA0200(m.get("a0200").toString(),mntp00);
						for(HashMap<String, Object> nrrxMap : nrrxList){
							this.getExecuteSG().addExecuteCode("radow.doEvent('DeleteP','"+nrrxMap.get("tp0100")+"');");
						}
						
						
						
						String userID = SysManagerUtils.getUserId();
						
						String b0111 = textFormat(m.get("a0201b"));
						String b01id = a0200Map.get(m.get("a0200"))[1];
						String zrrx = a0200Map.get(m.get("a0200"))[0];
						if(b0111!=null&&b0111.length()>=15){
							if(b0111.substring(0,11).equals("001.001.004")){//����
								String b0131 = HBUtil.getValueFromTab("b0131", "b01", "b0111='"+b0111+"'");
								if("1001,1003,1004,1005,1006,1007".contains(b0131)){
									zrrx = b0131;
									b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111.substring(0,15)+"'");
								}
								
							}
						}
						
						//��ʼ������
						final CommonSelSQL comsql = new CommonSelSQL(this,mntp00,b01id,zrrx);
						Thread thread = new Thread(){
							public void run() {
								try {
									comsql.setGWSort();
								} catch (RadowException e) {
									e.printStackTrace();
								} catch (AppException e) {
									e.printStackTrace();
								}
							}
						};
						thread.start();
						thread.join();
						
						//�����Ҫ�����ְ��
						HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,fxyp07,a0200,zwqc00,a0201e) values(sys_guid(),?,?,?,?,?,-1,?,?,?)",
								new Object[]{textFormat(m.get("a0215a")),userID,
										zrrx,b01id,mntp00,textFormat(m.get("a0200")),fxyp00_nm,textFormat(m.get("a0201e"))});
						sess.flush();
						c++;
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					
					if("1".equals(isNMOnly)){
						this.setNextEventName("pgrid.dogridquery");
						this.setNextEventName("pgrid2.dogridquery");
						this.getExecuteSG().addExecuteCode("setNoticeSetgridScrollParm();");
						this.setNextEventName("noticeSetgrid.dogridquery");
					}
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("����ʧ�ܣ�");
					e.printStackTrace();
				}
				this.setMainMessage("��ѯʧ�ܣ�");
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String textFormat(Object v){
		String value = null;
		if(v!=null){
			if("null".equals(v.toString())){
				return null;
			}
			value = v.toString().replace("{/n}", "\n");
		}
		return value;
	}
	private String getFTime(Object tex){
		String text = null;
		if(tex!=null){
			text = tex.toString();
			if(text.length()>=6){
				return text.substring(0, 4)+"."+text.substring(4, 6);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	/**
	 * ��ְ��Ա
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid.dogridquery")
	public int pgrid(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  /*String b01id = this.getPageElement("b01id").getValue();
	  String sql = "select a.a0000,a.a0101,a.a0192a,m.fxyp02 yxgw,r.tp0100 from a01 a,hz_mntp_gw m,rxfxyp r "
	  		+ " where m.b01id='"+b01id+"' and m.fxyp00=r.fxyp00 and m.mntp00='"+mntp00+"' and a.a0000=r.a0000";*/
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//�����쵼����
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
		  		+ "(select gw.zwqc00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) zwqc00,"
		  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
		  		
		  		+ "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"

		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ "'"+b01id+"' b01id,b.rybz,b.bmd "
		  		+ " FROM a02, a01 ,(select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b"
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and b.a01bzid(+) = a02.a0200"
		  		//+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a01.a0163='1'"
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//����ƽ̨
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
       +"(select gw.zwqc00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) zwqc00,"
       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
       
       + "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"
       
       + " nvl(a02.a0201e,'Z')||'01' zrrx, '"+b01id+"' b01id,b.rybz,b.bmd"
       +"   from a01, a02 ,(select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b"
       + " where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'  and b.a01bzid(+) = a02.a0200"
       +"    AND a02.a0255 = '1' "
       //+ " and a02.a0201e in ('1','3')"
       + " and a01.a0163='1'"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'";
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  /*String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,tp0100,"
			
			+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = t.tp0100 and bz.mntp00='"+mntp00+"' and a01bztype='2') a01bzdesc,"
	  		
	  		+ " t.fxyp06 zrrx,'"+b01id+"' b01id from v_mntp_gw_ry t, a01 "
	  		+ " where t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 ";	*/
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	
	/**
	 * �����
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid2.dogridquery")
	public int pgrid2(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//�����쵼����
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
		  		+ "(select gw.zwqc00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) zwqc00,"
		  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
		  		
		  		+ "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"

		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ " '"+b01id+"' b01id ,null zwqc01,c.sortnum,null ps,b.rybz,b.bmd"
		  		+ " FROM a02, a01,"
		  		+ " (select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b, "
		  		+ " (select * from GWSORT where mntp00 = '"+mntp00+"' and sorttype = '1') c"
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and b.a01bzid(+) = a02.a0200 "
		  		//+ " and a02.a0201e in('1','3') "
		  		+ "  and  c.SORTID(+) = a02.a0200"
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')"
		  		+" and not exists (select 1 from GWZWREF gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"')"
		  		+ " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//����ƽ̨
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
       +"(select gw.zwqc00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) zwqc00,"
       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
       
       + "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"
       
       + " nvl(a02.a0201e,'Z')||'01' zrrx, '"+b01id+"' b01id"
       + " ,null zwqc01,c.sortnum,null ps,b.rybz,b.bmd"
       +"   from a01, a02 ,"
       + " (select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b,"
       + " (select * from GWSORT where mntp00 = '"+mntp00+"' and sorttype = '1') c"
       + " where a01.A0000 = a02.a0000 AND a02.a0281 = 'true' and b.a01bzid(+) = a02.a0200"
       +"    AND a02.a0255 = '1' "
       //+ " and a02.a0201e in ('1','3')"
       + "  and  c.SORTID(+) = a02.a0200"
       + " and a01.a0163='1'"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'"
       +" and not exists (select 1 from GWZWREF gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"')"
       + " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";;
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,zwqc00,tp0100,"
			  
			+ "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"
	  		
	  		+ " t.fxyp06 zrrx,'"+b01id+"' b01id"
	  		+ ",t.zwqc01 ,c.sortnum,t.sortnum ps,b.rybz,b.bmd"
	  		+ " from v_mntp_gw_ry t, a01 ,"
	  		+ "(select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='2') b,"
	  		+ "(select * from GWSORT where mntp00 = '"+mntp00+"' and sorttype = '2') c"
	  		+ " where t.a0000=a01.a0000(+) and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " and b.a01bzid(+) = t.tp0100"
	  				+ "  and  c.SORTID(+) = t.fxyp00"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 order by sortnum,nvl2(sortnum,ps,null)";	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	
	
	
	
	@PageEvent("DeleteP")
	@Transaction
	public int DeleteP(String tp0100s) throws RadowException, AppException{
		try {
			String[] param = tp0100s.split("@@");
			String tp0100 = param[0];
			//String fxyp07 = param[1];
			//û����   ��ɾ����λ  ����������
			if(StringUtils.isEmpty(tp0100)){
				if(param.length>1){
					String zwqc00 = param[1];
					HBUtil.executeUpdate("delete from fxyp t where "
							+ " zwqc00=? ",new Object[]{zwqc00});
					HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00=?",new Object[]{zwqc00});
					//ɾ������
					HBUtil.executeUpdate("delete from gwzwref where zwqc00=?",new Object[]{zwqc00});
				}
				//this.setMainMessage("��ʱ�޷�ɾ��");
				this.setNextEventName("pgrid.dogridquery");
				this.setNextEventName("pgrid2.dogridquery");
				this.getExecuteSG().addExecuteCode("setNoticeSetgridScrollParm();");
				this.setNextEventName("noticeSetgrid.dogridquery");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String mntp00 = this.getPageElement("mntp00").getValue();
			HBSession sess = HBUtil.getHBSession();
			
			Object count = sess.createSQLQuery("select count(1) from rxfxyp where zwqc00="
					+ "(select zwqc00 from rxfxyp where tp0100=?)").setString(0, tp0100).uniqueResult();
			int totalcount =0;
			if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}
			if(totalcount==1){//����϶���1  ���������1 ��ɾ����λ    ɾ��ȫ��
				//���ֻ����һ��������tp0100 ��ȡ����Ա���ڵ������λa0200���Ƿ���������ѡ  �� ����������ѡ��  �ж��Ƿ�����Լ������������������Ӹ�����Ϊ������ѡ
				List<HashMap<String, Object>> nmrxList = MNTPUtil.getNMRXByTP0100(tp0100,mntp00);
				for(HashMap<String, Object> nmrxMap : nmrxList){
					String a0200 = nmrxMap.get("a0200").toString();
					
					
					List<HashMap<String, Object>> nrgwAllList = MNTPUtil.getNRRXALLByA0200(a0200,mntp00);
					if(nrgwAllList.size()>0){//��������ѡ
						String zwqc00 = nrgwAllList.get(0).get("zwqc00").toString();
						List<HashMap<String, Object>> nrgwList = MNTPUtil.getNRRXByA0200(a0200,mntp00);
						if(nrgwList.size()==0){//û���Լ�
							this.getPageElement("a0000s").setValue(nmrxMap.get("a0000").toString());
							this.getPageElement("gwa02").setValue("");
							this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+zwqc00+"');");
							//������ѡ
						}
					}
				}
				
				
				//���ɾ���������θ�λ ��ɾ���ø�λ��Ӧ �� ���ι���   ���������� ������䲻��ɾ�����ݣ�  �����ȫ�Ʋ��������GWZWREF  ֻɾ�������  ���θ�λ����
				/*HBUtil.executeUpdate("delete from GWZWREF t where "
						+ " exists (select fxyp07 hz_mntp_zwqc h where  t.zwqc00=h.zwqc00 and zwqc00="
						+ "(select zwqc00 from rxfxyp where tp0100=?) and fxyp07=-1)",new Object[]{tp0100});*/
				//ֻɾ�������  ���θ�λ����
				HBUtil.executeUpdate("delete from fxyp t where "
						+ " exists (select fxyp07 from hz_mntp_zwqc h where  t.zwqc00=h.zwqc00 and zwqc00="
						+ "(select zwqc00 from rxfxyp where tp0100=?) and fxyp07=-1)",new Object[]{tp0100});
				
				
				
				
				//ֻɾ�������  ���θ�λ����
				HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00="
						+ "(select zwqc00 from rxfxyp where tp0100=?) and fxyp07=-1",new Object[]{tp0100});
		
				HBUtil.executeUpdate("delete from rxfxyp where tp0100=?",new Object[]{tp0100});
			}else{//�����Ƕ���ѡ ��ɾ����ѡ �������
				HBUtil.executeUpdate("update rxfxyp set sortnum=sortnum-1 where zwqc00=(select zwqc00 from rxfxyp where tp0100=?) and sortnum>(select sortnum from  rxfxyp where tp0100=?)",
						new Object[]{tp0100,tp0100});
				HBUtil.executeUpdate("delete from rxfxyp where tp0100=?",new Object[]{tp0100});
			}
			
			HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid=? and a01bztype=? ",
					new Object[]{mntp00,tp0100,"2"});
			sess.flush();
			this.setNextEventName("pgrid.dogridquery");
			this.setNextEventName("pgrid2.dogridquery");
			this.getExecuteSG().addExecuteCode("setNoticeSetgridScrollParm();");
			this.setNextEventName("noticeSetgrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	//��ʼ����֯������
	@SuppressWarnings("unchecked")
	@PageEvent("org_config_tree")
	public int org_config_tree() throws PrivilegeException {
		String userid = this.getCurUserid();
		String mntp00 = this.request.getParameter("mntp00");
		String mntp05 = this.request.getParameter("mntp05");
		//String dwmc = this.request.getParameter("dwmc");
		StringBuilder sb_tree = new StringBuilder("[");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "";
			
			if("2".equals(mntp05)){//����
				  sql = "select s.b01id,s.b0101 jgmc,nvl2(d.b01id,'false','true') from QXSLDBZHZB s"
				  		+ "  left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("1".equals(mntp05)){//��ֱ
				  sql = "select s.b01id,jgmc,nvl2(d.b01id,'false','true') vali from" 
						  +" SZDWHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("4".equals(mntp05)){//����
				  sql = "select s.b01id,jgmc,nvl2(d.b01id,'false','true') from" 
						  +" GQGXHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("3".equals(mntp05)){//ƽ̨
				  
				  sql = "select s.b01id,s.b0101 jgmc,nvl2(d.b01id,'false','true') from" 
						  +" QXSPTHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }	
			list = sess.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(Object[] o : list){
			String name = o[0].toString();
			//String editor = o[5].toString().toLowerCase();
			//String header = o[2].toString();
			String desc = o[1].toString();
			//String width = o[3].toString();
			//String codeType = o[4]==null?"":o[4].toString();
			//String renderer = o[7]==null?"":o[7].toString();
			sb_tree.append(" {text: '"+desc+"',id:'"+name+"',leaf:true,checked:"+o[2]+"},");
		}
		sb_tree.append("]");
		this.setSelfDefResData(sb_tree.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	@PageEvent("saveOrg_config")
	public int saveOrg_config() throws RadowException {
		String jsonp = this.request.getParameter("jsonp");
		String mntp00 = this.request.getParameter("mntp00");
		String mntp05 = this.request.getParameter("mntp05");
		//System.out.println(jsonp);
		if(jsonp!=null&&!"".equals(jsonp)){
			JSONObject jsonObject = JSONObject.fromObject(jsonp);
			Iterator<String> it = jsonObject.keys();
			
			HBSession sess = HBUtil.getHBSession();
			Connection con = sess.connection();
			try {
				
				con.setAutoCommit(false);
				con.createStatement().execute("delete from hz_mntp_dwref where mntp00='"+mntp00+"' and mntp05='"+mntp05+"'");
				PreparedStatement pstat = con.prepareStatement("insert into hz_mntp_dwref(mntp00,b01id,mntp05) values(?,?,?)");
				// ����jsonObject���ݣ���ӵ�Map����
				int i = 0;
				while (it.hasNext()) {
					String nodeid = it.next();
					String isvali =  jsonObject.get(nodeid).toString();
					if("false".equals(isvali)){
						pstat.setString(1, mntp00);
						pstat.setString(2, nodeid);
						pstat.setString(3, mntp05);
						pstat.addBatch();
					}
					i++;
				}
				pstat.executeBatch();
				con.commit();
				
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
		this.setMainMessage("����ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("showOrgInfo")
	@Transaction
	public int showOrgInfo() throws RadowException, AppException{
		JZHJ jzhj = new JZHJ(this);
		jzhj.setPageModelParser(this.getPageModelParser());
		return jzhj.showOrgInfo();
	}
	
	/**
	 * ͼ������
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("EChartsInfo")
	@Transaction
	public int EChartsInfo() throws RadowException, AppException{
		JZHJ jzhj = new JZHJ(this);
		jzhj.setPageModelParser(this.getPageModelParser());
		//�ж��ظ�
		Map<String,Object> a0000sMap = new HashMap<String, Object>();
		//ͳ��
		Map<String,Object> mapCount = new HashMap<String, Object>();
		//����
		Map<String,List<String>> reverseSearchMap = new HashMap<String, List<String>>();
		jzhj.getEChartsInfo(a0000sMap,mapCount,reverseSearchMap);
		//רҵ����
		jzhj.getEChartsZYLX(a0000sMap,mapCount,reverseSearchMap);
		
		
		//����ǰ
		//�ж��ظ�
		Map<String,Object> a0000sMap2 = new HashMap<String, Object>();
		//ͳ��
		Map<String,Object> mapCount2 = new HashMap<String, Object>();
		//����
		Map<String,List<String>> reverseSearchMap2 = new HashMap<String, List<String>>();
		jzhj.getEChartsInfo2(a0000sMap2,mapCount2,reverseSearchMap2);
		//רҵ����
		jzhj.getEChartsZYLX(a0000sMap2,mapCount2,reverseSearchMap2);
		
		
		Map<String,Map<String,Object>> retData = new HashMap<String, Map<String,Object>>();
		retData.put("data1", mapCount);
		retData.put("data2", mapCount2);
		this.setSelfDefResData(retData);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("setGWInfo")
	@Transaction
	public int setGWInfo() throws RadowException, AppException{
		
		String mntp00 = this.getPageElement("mntp00").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fxyp06 = this.getPageElement("fxyp06").getValue();
		String b01idkq = this.getPageElement("b01idkq").getValue();
		String a0000s = this.getPageElement("a0000s").getValue();//Ŀǰֻ֧��һ����
		
		
		
		
		//List<A02> a02list = HBUtil.getHBSession().createQuery("").list();
		//������Ҫ�����ְ����Ϣ
		//(select b0131 from b01 where b0111=a02.a0201b)
		//a0201e
		a0000s = a0000s.replaceAll(",", "','");
		String Sql = "select t.a0201a,t.a0201b,t.a0200,t.a0000,a01.a0101,"
		  		+ " a01.a0192a,t.a0215a,"
		  		+ "nvl((select max('2') from fxyp f where f.a0200=t.a0200 and mntp00='"+mntp00+"'),'34') personStatus,"
		  				//+ "(select max('2') from fxyp f,hz_mntp_zwqc h where f.a0200=t.a0200 and f.zwqc00=h.zwqc00 and h.mntp00='"+mntp00+"')"
		  				+ "'' fxyp07,"
		  		+ " nvl(t.a0201e,'Z')||'01' zrrx,(select b01id from b01 where b0111=t.a0201b) b01id from a02 t, a01 "
		  		+ " where t.a0000=a01.a0000   AND t.a0281 = 'true' AND t.a0255 = '1'  and a01.a0000 in('"+a0000s+"') order by a0223";
		CommQuery cqbs=new CommQuery();
		
		//ƴ������ְ������  �Ѿ�������û�  ��ʾ�Ѿ���������
		StringBuilder html = new StringBuilder();
		//<input type="checkbox" name="mxz" id="mxz" style="border: none!important;" checked="checked" />
		//<label for="mxz">�Ƿ�����ְ</label></td>
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(Sql.toString());
		for(int i=0;i<nmzwList.size();i++){
			HashMap<String, Object> m = nmzwList.get(i);
			
			//����Ĭ�������ȫ��
			if(i==0){
				this.getPageElement("nmzwqc").setValue(m.get("a0192a")==null?"":m.get("a0192a").toString());
			}
			
			
			if(m.get("a0201b")==null){
				continue;
			}
			
			
			
			
			//'001.001.004.001'
			String b0111 = m.get("a0201b").toString();
			String b01id = m.get("b01id").toString();
			String zrrx = m.get("zrrx")==null?"Z01":m.get("zrrx").toString();
			if(b0111!=null&&b0111.length()>=15){
				if(b0111.substring(0,11).equals("001.001.004")){//����
					String b0131 = HBUtil.getValueFromTab("b0131", "b01", "b0111='"+b0111+"'");
					if("1001,1003,1004,1005,1006,1007".contains(b0131)){
						zrrx = b0131;
						b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111.substring(0,15)+"'");
					}
					
				}
			}
			
			String[] info = new String[]{"",""};
			if("2".equals(m.get("personstatus"))){
				info[0] = " disabled='disabled'  title='��ְ���Ѿ��������⣡'";
			}
			info[1] = " pt='"+m.get("personstatus")+"'" +
					  " zrrx='"+zrrx+"'"  +
					  " b01id='"+b01id+"'"
					  ;
			html.append("<input type='checkbox' name='NMZW' id='mxz"+i+"' style='border: none!important;' "+info[0]+info[1]+"  checked='checked' value='"+m.get("a0200")+"'/>");
			html.append("<label  "+info[0]+"   for='mxz"+i+"'>"+m.get("a0201a")+"&nbsp;&nbsp;&nbsp;&nbsp;"+m.get("a0215a")+"</label>");
			html.append("<br/>");
		}
		
		this.getExecuteSG().addExecuteCode("$('.NMinfo').html(\""+html.toString()+"\")");
		
		
		//����ҳ������������ȫ��
		//����Ĭ�ϵ�����ȫ��   ���û������ȫ�ƣ���Ѷ�Ӧ��id�ÿա�  ���û������ȫ�ƣ���Ѷ�Ӧ��id�ÿա�
		boolean exists = setNRMQC(mntp00,a0000s);
				
		
		/*//��ȡ�õ�λȫ��ְ��
		String selSqL = "select distinct zwqc00,a0192a,zwqc01 from hz_mntp_gw m where m.b01id='"+b01idkq+"' and "
				+ " m.fxyp06='"+fxyp06+"' and fxyp07=1 and m.mntp00='"+mntp00+"'";
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();*/
		
		
		
		//List<HashMap<String, Object>> listCode=cqbs.getListBySQL(selSqL);
		
		/*for(int i=0;i<listCode.size();i++){
			mapCode.put(listCode.get(i).get("zwqc00").toString(), listCode.get(i).get("a0192a"));
		}
		if(listCode.size()>0){
			mapCode.put("1", "��");
		}*/
		if(!exists){
			this.getExecuteSG().addExecuteCode("Ext.getCmp('gwa02_combo').setDisabled(false)");
		}else{
			this.getExecuteSG().addExecuteCode("Ext.getCmp('gwa02_combo').setDisabled(true)");
		}
		
		//((Combo)this.getPageElement("yxgwSel")).setValueListForSelect(mapCode);
		
		
		
		//��ȡ�õ�λ���θ�λ
		CommonSelSQL comsql = new CommonSelSQL(this);
		//��ʼ������
		comsql.setGWSort();
		String TPQSQL = comsql.getTPQSQL();
		List<HashMap<String, Object>> TPQCode=cqbs.getListBySQL(TPQSQL);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(HashMap<String, Object> m : TPQCode){
			
			String a0200 = m.get("a0200")==null?"":m.get("a0200").toString();
			String a0215a = m.get("a0215a")==null?"":m.get("a0215a").toString();
			String a0201e = m.get("a0201e")==null?"":m.get("a0201e").toString();
			String a0101 = m.get("a0101")==null?"":m.get("a0101").toString();
			String fxyp07 = m.get("fxyp07")==null?"":m.get("fxyp07").toString();
			String fxyp00 = m.get("fxyp00")==null?"":m.get("fxyp00").toString();
			String tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
			String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
			String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
			String zwqc01 = m.get("zwqc01")==null?"":m.get("zwqc01").toString();
			String a0101nm = m.get("a0101nm")==null?"":m.get("a0101nm").toString();
			//ְ��
			String a0501b = m.get("a0501b")==null?"":m.get("a0501b").toString();
			String nrm = "";
			if("-1".equals(fxyp07)){
				nrm = " ԭ";
			}else if("1".equals(fxyp07)){
				nrm = " ��";
			}
			String nm = "";
			if(!StringUtils.isEmpty(a0101nm)){
				nm = " ԭ"+"��"+a0101nm+"�� ";
			}
			String code = (a0200+"@@"+fxyp07+"@@"+fxyp00+"@@"
			+tp0100+"@@"+a0215a+"@@"+a0192a+"@@"+zwqc00+"@@"+zwqc01+"@@"+a0201e+"@@"+a0501b+"@@x").replaceAll("\r|\n|\r\n", "");
			mapCode.put(code, (a0215a+(nm)+nrm+"��"+a0101+"��").replaceAll("\r|\n|\r\n", ""));
		}
		
		if(TPQCode.size()>0){
			mapCode.put("1", "��");
		}
		((Combo)this.getPageElement("gwa02")).setValueListForSelect(mapCode);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	private boolean setNRMQC(String mntp00, String a0000s) throws AppException, RadowException {
		a0000s = a0000s.replaceAll(",", "','");
		String Sql = "select distinct a.a0192a,a.zwqc00,a.fxyp07,b.tp0100,zwqc01,a.a0501b from hz_mntp_zwqc a,rxfxyp b "
				+ "where a.zwqc00=b.zwqc00 and b.a0000 in('"+a0000s+"') and a.mntp00='"+mntp00+"'";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(Sql);
		boolean nmqc = false, nrqc = false;
		String zwqc00s = "",tp0100 = "",a0501b = "";
		//����һ��   ����һ��
		for(int i=0;i<listCode.size();i++){
			HashMap<String, Object> m = listCode.get(i);
			String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
			String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
			String zwqc01 = m.get("zwqc01")==null?"":m.get("zwqc01").toString();
			a0501b = m.get("a0501b")==null?"":m.get("a0501b").toString();
			
			if("".equals(zwqc00)){
				continue;
			}
			
			
			if("1".equals(m.get("fxyp07"))){
				nrqc = true;
				this.getPageElement("a0192aMN").setValue(a0192a);
				this.getPageElement("dqnrzwqcid").setValue(zwqc00);
				this.getPageElement("zwqc01").setValue(zwqc01);
				//ְ��
				this.getPageElement("a0501b").setValue(a0501b);
				
				zwqc00s = zwqc00;
				tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
			}else if("-1".equals(m.get("fxyp07"))){
				nmqc = true;
				this.getPageElement("nmzwqc").setValue(a0192a);
				this.getPageElement("dqnmzwqcid").setValue(zwqc00);
			}
		}
		if(!nmqc){
			this.getPageElement("dqnmzwqcid").setValue("");
		}
		if(!nrqc){
			//this.getPageElement("a0192aMN").setValue("");
			this.getPageElement("dqnrzwqcid").setValue("");
			this.getPageElement("zwqc01").setValue("1");
			//ְ��
			this.getPageElement("a0501b").setValue("");
		}
		
		//���������θ�λ
		if(!"".equals(zwqc00s)){
			String nrgwSQL = "select fxyp02,fxyp00 from fxyp b where b.zwqc00='"+zwqc00s+"'";
			StringBuilder html = new StringBuilder();
			
			List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(nrgwSQL);
			for(int i=0;i<nmzwList.size();i++){
				HashMap<String, Object> m = nmzwList.get(i);
				html.append("<input type='checkbox' name='NRZW' id='nrzw"+i+"' style='border: none!important;'  disabled='disabled'  checked='checked' value='"+m.get("fxyp00")+"'/>");
				html.append("<label      for='nrzw"+i+"'>"+m.get("fxyp02")+"</label>");
			}
			html.append("<font color=blue> <a style='cursor:pointer;' onclick='GW_moveP(&quot;"+tp0100+"&quot;);'>ȡ������Ա������ְ��</a></font>");
			this.getExecuteSG().addExecuteCode("$('.dqnrzw').html(\""+html.toString()+"\")");
		}else{
			this.getExecuteSG().addExecuteCode("$('.dqnrzw').html(\"�ޣ�\")");
		}
		
		return nrqc;
	}



	@PageEvent("tpbj.onclick")
	public int tpbj() throws RadowException {
		LinkedHashSet<String> selected = new LinkedHashSet<String>();
		// ��cookie�еĻ�ȡ֮ǰѡ�����Աid
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("jggl.tpbj.ids".equals(cookie.getName())) {
					String cookieValue = cookie.getValue();
					String[] ids = cookieValue.split("#");
					for (String id : ids) {
						if (!StringUtils.isEmpty(id)) {
							selected.add(id);
						}
					}
				}
			}
		}
		// ��Աid
		String a0000s = this.getPageElement("a0000sBD").getValue();
		if (!StringUtils.isEmpty(a0000s)) {
			String[] a0000Array = a0000s.split(",");
			for (int i = 0; i < a0000Array.length; i++) {
				selected.add(a0000Array[i]);
			}
		}

		if (selected.size() == 0) {
//			this.setMainMessage("��ѡ����Ա");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
			return EventRtnType.FAILD;
		} else {
			String json = JSON.toJSONString(selected);
			this.getExecuteSG()
					.addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','ͬ���Ƚ�',1500,731,null,'"
							+ this.request.getContextPath() + "',null,{"
							+ "maximizable:false,resizable:false,RMRY:'ͬ���Ƚ�',addPerson:true,data:" + json + ",mntp:true},true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	
	@PageEvent("getZwqc01")
	@Transaction
	public int getZwqc01(String zwqc00) throws Exception{
		String zwqc01 = HBUtil.getValueFromTab("zwqc01", "hz_mntp_zwqc", "zwqc00='"+zwqc00+"'");
		if(StringUtils.isEmpty(zwqc01)){
			this.getPageElement("zwqc01").setValue("1");
		}else{
			this.getPageElement("zwqc01").setValue(zwqc01);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addETCInfo")
	@Transaction
	public int addInfo() throws Exception{
		
		//ְ��ά��
		String zwqc00WH = this.getPageElement("zwqc00WH").getValue();
		if(!StringUtils.isEmpty(zwqc00WH)){
			String a0192aWH = this.getPageElement("a0192aWH").getValue();
			//String a0201eWH = this.getPageElement("a0201eWH").getValue();
			String a0501bWH = this.getPageElement("a0501bWH").getValue();
			HBUtil.executeUpdate("update  hz_mntp_zwqc set a0192a=?,a0501b=? where zwqc00=? ",
					new Object[]{a0192aWH,a0501bWH,zwqc00WH});
			String gwmxInfo = this.getPageElement("gwmxInfo").getValue();
			if(!StringUtils.isEmpty(gwmxInfo)){
				String[] gwmxInfoRowsArray = gwmxInfo.split("@@");
				for(int i=0;i<gwmxInfoRowsArray.length;i++){
					String gwmxInfoRow = gwmxInfoRowsArray[i];
					String[] gwmxInfoRowArray = gwmxInfoRow.split(";_;");
					HBUtil.executeUpdate("update  fxyp set fxyp02=? where fxyp00=? ",
							new Object[]{gwmxInfoRowArray[1],gwmxInfoRowArray[0]});
				}
			}
		}
		
		
		
		
		
		//��עά��
		String a01bzdesc = this.getPageElement("a01bzdesc").getValue();
		String rybz = this.getPageElement("rybz").getValue();
		String bmd = this.getPageElement("bmd").getValue();
		String a01bztype = this.getPageElement("a01bztype").getValue();
		String a01bzid = this.getPageElement("a01bzid").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		
		String a0201bSeclect = this.getPageElement("a0201bSeclect").getValue();
		String a0201bSeclect_combo = this.getPageElement("a0201bSeclect_combo").getValue();
		
		if(!StringUtils.isEmpty(a01bzid)){
			HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid=? and a01bztype=? ",
					new Object[]{mntp00,a01bzid,a01bztype});
			//if(!StringUtils.isEmpty(a01bzdesc)){
				if(!StringUtils.isEmpty(a0201bSeclect)){
					a0201bSeclect = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+a0201bSeclect+"'");
				}
				HBUtil.executeUpdate("insert into HZ_MNTP_BZ(mntp00,a01bzid,a01bztype,a01bzdesc,b01id,b0101,rybz,bmd) values(?,?,?,?,?,?,?,?)",
						new Object[]{mntp00,a01bzid,a01bztype,a01bzdesc,a0201bSeclect,a0201bSeclect_combo,rybz,bmd});
			//}
		}
		
		
		
		this.setNextEventName("pgrid.dogridquery");
		this.setNextEventName("pgrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setBZOrgInfo")
	@Transaction
	public int setBZOrgInfo(String parm) throws Exception{
		String mntp00 = this.getPageElement("mntp00").getValue();
		
		String[] parray = parm.split("@");
		String a0000 = parray[0];
		String fxyp07 = parray[1];
		
		
		
		
		String b01id = HBUtil.getValueFromTab("max(b01id)", "v_mntp_gw_ry", "mntp00='"+mntp00+"' and fxyp07='"+fxyp07+"' and a0000='"+a0000+"'");
		if(!StringUtils.isEmpty(b01id)){
			String b0111 = HBUtil.getValueFromTab("b0111", "b01", "b01id='"+b01id+"'");
			
			String b0101 = HBUtil.getValueFromTab("b0101", "b01", "b01id='"+b01id+"'");
			this.getPageElement("a0201bSeclect").setValue(b0111);
			this.getPageElement("a0201bSeclect_combo").setValue(b0101);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("setNRinfo")
	@Transaction
	public int setNRinfo(String a0200) throws Exception{
		String mntp00 = this.getPageElement("mntp00").getValue();
		List<HashMap<String, Object>> nrgwList = MNTPUtil.getGWNRInfoByA0200(a0200,mntp00);
		//ƴ�ӿ������ε�ְ�� 
		StringBuilder html = new StringBuilder();
		//<input type="checkbox" name="mxz" id="mxz" style="border: none!important;" checked="checked" />
		//<label for="mxz">�Ƿ�����ְ</label></td>
		if(nrgwList.size()>0){
			if(nrgwList.size()==1){
				HashMap<String, Object> m = nrgwList.get(0);
				String a0200Self = m.get("a0200").toString();
				if(!a0200Self.equals(a0200)){
					html.append("<span>����ְ��</span>");
				}
			}else{
				html.append("<span>����ְ��</span>");
			}
			
		}
		for(int i=0;i<nrgwList.size();i++){
			HashMap<String, Object> m = nrgwList.get(i);
			String a0200Self = m.get("a0200").toString();
			
			//��ѡ����Ƿ���ʾ
			if(a0200Self.equals(a0200)){
				//continue;
			}
			
			if(m.get("a0201b")==null){
				continue;
			}
			//'001.001.004.001'
			String b0111 = m.get("a0201b").toString();
			String b0101 = m.get("b0101")==null?"":m.get("b0101").toString();
			String b01id = m.get("b01id").toString();
			String zrrx = m.get("a0201e")==null?"":m.get("a0201e").toString();
			String fxyp07 = m.get("fxyp07")==null?"":m.get("fxyp07").toString();
			String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
			if(b0111!=null&&b0111.length()>=15){
				if(b0111.substring(0,11).equals("001.001.004")){//����
					String b0131 = HBUtil.getValueFromTab("b0131", "b01", "b0111='"+b0111+"'");
					if("1001,1003,1004,1005,1006,1007".contains(b0131)){
						zrrx = b0131;
						b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111.substring(0,15)+"'");
						b0101 = HBUtil.getValueFromTab("b0101", "b01", "b0111='"+b0111.substring(0,15)+"'");
					}
					
				}
			}
			
			String param = " fxyp07='"+fxyp07+"' zwqc00='"+zwqc00+"' ";
					  
			html.append("<input type='checkbox' name='NRZW' id='rz"+i+"' style='border: none!important;'   checked='checked' "+param+" value='"+m.get("a0200")+"'/>");
			html.append("<label for='rz"+i+"'>"+b0101+"&nbsp;&nbsp;"+m.get("a0215a")+"</label>");
			//html.append("<br/>");
		}
		
		this.getExecuteSG().addExecuteCode("$('.NRinfo').html(\""+html.toString()+"\")");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setGWWHInfo")
	@Transaction
	public int setGWWHInfo(String fxyp00) throws Exception{
		if(StringUtils.isEmpty(fxyp00)){
			this.getExecuteSG().addExecuteCode("$('#zwqc00WH').val(''); $('.gwwh').hide();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		String mntp00 = this.getPageElement("mntp00").getValue();
		
		List<HashMap<String, Object>> nrgwList = MNTPUtil.getGWXXByFXYP00(fxyp00,mntp00);
		StringBuilder html = new StringBuilder();
		if(nrgwList.size()>0){
			for(int i=0;i<nrgwList.size();i++){
				HashMap<String, Object> m = nrgwList.get(i);
				if(i==0){
					String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
					String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
					//Z01  ����
					String fxyp07 = m.get("fxyp07")==null?"":m.get("fxyp07").toString();
					if("-1".equals(fxyp07)){
						//zwqcwh ְ��
						this.getExecuteSG().addExecuteCode("$('.zwqcwh').hide();"
								+ "$('#a0192aWHSpanId').html('����ְ��ȫ��')");
					}else{
						this.getExecuteSG().addExecuteCode("$('.zwqcwh').show();"
								+ "$('#a0192aWHSpanId').html('����ְ��ȫ��')");
					}
					//String a0201e = m.get("a0201e")==null?"":m.get("a0201e").toString();
					String a0501b = m.get("a0501b")==null?"":m.get("a0501b").toString();
					this.getPageElement("a0192aWH").setValue(a0192a);
					this.getPageElement("zwqc00WH").setValue(zwqc00);
					//this.getPageElement("a0201eWH").setValue(a0201e);
					this.getPageElement("a0501bWH").setValue(a0501b);
				}
				String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
				String fxyp00gw = m.get("fxyp00")==null?"":m.get("fxyp00").toString();
				String fxyp02 = m.get("fxyp02")==null?"":m.get("fxyp02").toString();
				
				String b0101 = HBUtil.getValueFromTab("b0101", "b01", "b01id='"+b01id+"'");
				if(!StringUtils.isEmpty(b0101)){
					html.append("<label for='gwmx"+i+"'>"+b0101+"&nbsp;&nbsp;</label>");
					html.append("<input type='text' name='gwmx' id='gwmx"+i+"' style='width:200px;' fieldv='"+fxyp00gw+"' value='"+fxyp02+"'/>");
					html.append("<br/>");
					html.append("<br/>");
				}
			}
			this.getExecuteSG().addExecuteCode("$('.zwwh').html(\""+html.toString()+"\")");
		}else{
			this.getExecuteSG().addExecuteCode("$('#zwqc00WH').val(''); $('.zwwh').html('');$('.gwwh').hide();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("clearList")
	@Transaction
	public int clearList() throws Exception{
		String mntp00 = this.getPageElement("mntp00").getValue();
		HBUtil.executeUpdate("delete HZ_MNTP_PGRIDBUFFER where mntp00=?",
				new Object[]{mntp00});
		this.getExecuteSG().addExecuteCode("Ext.getCmp('pgridBuffer').getStore().removeAll()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
