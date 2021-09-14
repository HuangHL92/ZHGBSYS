package com.insigma.siis.local.pagemodel.sysorg.org;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.sysorg.org.StatisticsWinBS;
import com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis.TwoDStatisticsShowPageModel;


public class TwoDimensionStatisticsPageModel extends PageModel{

	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	} 
	 
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		//String groupid = this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("first_load();");
		this.setNextEventName("query");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//��ѯ������ʾ��ҳ����
	@SuppressWarnings("unchecked")
	@PageEvent("query")
	public int doquery() throws RadowException{
		TwoDStatisticsShowPageModel.table_data_str = "";
		String str = "" ;
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		String[] param_arr = param.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];
		String SQ001 = param_arr[1];
		String tran_name = "'";
		String vert_name = "[";
		String ld_tran_name = "[";
		String qxdata = "[";
		String zzdata = "[";
		String lddata = "[";
		LinkedHashMap<String ,Object[]> map=new LinkedHashMap<String,Object[]>();
		if("6".equals(SQ001)){
			str = "Ů@��@��$3$���Ҽ���ְ@���Ҽ���ְ@ʡ������ְ@ʡ������ְ@���ּ���ְ@���ּ���ְ@�ش�����ְ@�ش�����ְ@��Ƽ���ְ@��Ƽ���ְ@��Ա@����Ա@��������Ա@����$14$"; 
			tran_name = "['Ů��','��������','���й���Ա']";
			vert_name = "['���Ҽ���ְ','���Ҽ���ְ','ʡ������ְ','ʡ������ְ','���ּ���ְ','���ּ���ְ','�ش�����ְ',"
					+ "'�ش�����ְ','��Ƽ���ְ','��Ƽ���ְ','��Ա','����Ա','��������Ա','����']";
			String sql0101="SELECT  SUM(CASE WHEN a.a0104 = 2 THEN 1 ELSE 0 END) nv,"+
					  " SUM(CASE WHEN a.a0117 = 01 THEN 0 ELSE 1 END) sm,"+
					  " SUM(CASE WHEN a.a0141 <> 01 THEN 1 ELSE 0 END) fdy"+
				" FROM (SELECT a0000, a0104, a0117, a0141 FROM A01 a01 WHERE a01.STATUS = '1'"+
						" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.a0255 = '1' "+groupid+")"+
						" AND a01.a0148= '0101') a";
			String sql0102=sql0101.replace("0101", "0102");//���Ҽ���ְ
			String sql0111=sql0101.replace("0101", "0111");//ʡ������ְ
			String sql0112=sql0101.replace("0101", "0112");//ʡ������ְ
			String sql0121=sql0101.replace("0101", "0121");//���ּ���ְ
			String sql0122=sql0101.replace("0101", "0122");//���ּ���ְ
			String sql0131=sql0101.replace("0101", "0131");//�ش�����ְ
			String sql0132=sql0101.replace("0101", "0132");//�ش�����ְ
			String sql0141=sql0101.replace("0101", "0141");//��Ƽ���ְ
			String sql0142=sql0101.replace("0101", "0142");//��Ƽ���ְ
			String sql0150=sql0101.replace("0101", "0150");//��Ա
			String sql0160=sql0101.replace("0101", "0160");//����Ա
			String sql0198=sql0101.replace("0101", "0198");//��������Ա
			String sql0199=sql0101.replace("0101", "0199");//����
			Object[] obj0101=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0101).uniqueResult();
			Object[] obj0102=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0102).uniqueResult();
			Object[] obj0111=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0111).uniqueResult();
			Object[] obj0112=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0112).uniqueResult();
			Object[] obj0121=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0121).uniqueResult();
			Object[] obj0122=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0122).uniqueResult();
			Object[] obj0131=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0131).uniqueResult();
			Object[] obj0132=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0132).uniqueResult();
			Object[] obj0141=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0141).uniqueResult();
			Object[] obj0142=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0142).uniqueResult();
			Object[] obj0150=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0150).uniqueResult();
			Object[] obj0160=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0160).uniqueResult();
			Object[] obj0198=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0198).uniqueResult();
			Object[] obj0199=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0199).uniqueResult();
			map.put("���Ҽ���ְ", obj0101);
			map.put("���Ҽ���ְ", obj0102);
			map.put("ʡ������ְ", obj0111);
			map.put("ʡ������ְ", obj0112);
			map.put("���ּ���ְ", obj0121);
			map.put("���ּ���ְ", obj0122);
			map.put("�ش�����ְ", obj0131);
			map.put("�ش�����ְ", obj0132);
			map.put("��Ƽ���ְ", obj0141);
			map.put("��Ƽ���ְ", obj0142);
			map.put("��Ա", obj0150);
			map.put("����Ա", obj0160);
			map.put("��������Ա", obj0198);
			map.put("����", obj0199);
			String data_arr1 = "";
			int i = 0;
			for(Map.Entry<String, Object[]> entry:map.entrySet()){
				for(int s=0;s<entry.getValue().length;s++){
					if(entry.getValue()[s]==null){
						entry.getValue()[s]=0;
					}
				}
				
			    data_arr1 += entry.getValue()[0]+",";
				data_arr1 += entry.getValue()[1]+",";
				data_arr1 += entry.getValue()[2];
				qxdata += "{name:'"+entry.getKey()+"',type:'line',data:["+data_arr1+"]},";
				zzdata += "{name:'"+entry.getKey()+"',type:'bar',data:["+data_arr1+"],"
						+ "itemStyle:{normal:{label:{show: true,'position': 'top'}}}},";
				lddata += "{value:["+data_arr1+"],name:'"+entry.getKey()+"'},";
				this.getPageElement("vert"+i).setValue(entry.getKey());
				for(int j=0;j<3;j++){
					String[] data_add = data_arr1.split(",");
					this.getPageElement("vert"+i+"_tran"+j).setValue(data_add[j]);
					str += data_add[j]+"@";
				}
				str = str.substring(0,str.length()-1);
				str += "#";
				i++;
				data_arr1 ="";
			}  
			TwoDStatisticsShowPageModel.table_data_str = str.substring(0,str.length()-1);
			ld_tran_name += "{name:'Ů��'},{name:'��������'},{name:'���й���Ա'}]";
			this.getPageElement("tran0").setValue("Ů��");
			this.getPageElement("tran1").setValue("��������");
			this.getPageElement("tran2").setValue("���й���Ա");
    		qxdata = qxdata.substring(0,qxdata.length()-1)+"]";
    		zzdata = zzdata.substring(0,zzdata.length()-1)+"]";
    		lddata = lddata.substring(0,lddata.length()-1)+"]";
			this.getPageElement("tran_name").setValue(tran_name);
	        this.getPageElement("vert_name").setValue(vert_name);
	        this.getPageElement("qxdata").setValue(qxdata);
	        this.getPageElement("zzdata").setValue(zzdata);
	        this.getPageElement("lddata").setValue(lddata);
	        this.getPageElement("ld_tran_name").setValue(ld_tran_name);
	        this.getPageElement("tran_length").setValue("3");
	        
			this.getExecuteSG().addExecuteCode("def();");
	        this.getExecuteSG().addExecuteCode("createEwTable();");
			this.getExecuteSG().addExecuteCode("setQuxian();");
			this.getExecuteSG().addExecuteCode("setZhuzhuang();");
			this.getExecuteSG().addExecuteCode("setLeida();");	
			this.getExecuteSG().addExecuteCode("loding();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("8".equals(SQ001)){
			str = "30������@31-35��@36-40��@41-45��@46-50��@51-54��@55-59��@60������$8$���Ҽ���ְ@���Ҽ���ְ@ʡ������ְ@ʡ������ְ@���ּ���ְ@���ּ���ְ@�ش�����ְ@�ش�����ְ@��Ƽ���ְ@��Ƽ���ְ@��Ա@����Ա@��������Ա@����$14$"; 
			tran_name = "['30������','31-35��','36-40��','41-45��','46-50��','51-54��','55-59��','60������']";
			vert_name = "['���Ҽ���ְ','���Ҽ���ְ','ʡ������ְ','ʡ������ְ','���ּ���ְ','���ּ���ְ','�ش�����ְ',"
					+ "'�ش�����ְ','��Ƽ���ְ','��Ƽ���ְ','��Ա','����Ա','��������Ա','����']";
			String sql0101="SELECT a0107 FROM A01 a01 WHERE a01. STATUS = '1' AND a01.a0000 IN ("+
	                              " SELECT a02.a0000 FROM a02 WHERE a02.a0255 = '1' "+groupid+")"+
                                   " AND a01.a0148= '0101'";
			String sql0102=sql0101.replace("0101", "0102");//���Ҽ���ְ
			String sql0111=sql0101.replace("0101", "0111");//ʡ������ְ
			String sql0112=sql0101.replace("0101", "0112");//ʡ������ְ
			String sql0121=sql0101.replace("0101", "0121");//���ּ���ְ
			String sql0122=sql0101.replace("0101", "0122");//���ּ���ְ
			String sql0131=sql0101.replace("0101", "0131");//�ش�����ְ
			String sql0132=sql0101.replace("0101", "0132");//�ش�����ְ
			String sql0141=sql0101.replace("0101", "0141");//��Ƽ���ְ
			String sql0142=sql0101.replace("0101", "0142");//��Ƽ���ְ
			String sql0150=sql0101.replace("0101", "0150");//��Ա
			String sql0160=sql0101.replace("0101", "0160");//����Ա
			String sql0198=sql0101.replace("0101", "0198");//��������Ա
			String sql0199=sql0101.replace("0101", "0199");//����
			List<Object> list0101=HBUtil.getHBSession().createSQLQuery(sql0101).list();
			List<Object> list0102=HBUtil.getHBSession().createSQLQuery(sql0102).list();
			List<Object> list0111=HBUtil.getHBSession().createSQLQuery(sql0111).list();
			List<Object> list0112=HBUtil.getHBSession().createSQLQuery(sql0112).list();
			List<Object> list0121=HBUtil.getHBSession().createSQLQuery(sql0121).list();
			List<Object> list0122=HBUtil.getHBSession().createSQLQuery(sql0122).list();
			List<Object> list0131=HBUtil.getHBSession().createSQLQuery(sql0131).list();
			List<Object> list0132=HBUtil.getHBSession().createSQLQuery(sql0132).list();
			List<Object> list0141=HBUtil.getHBSession().createSQLQuery(sql0141).list();
			List<Object> list0142=HBUtil.getHBSession().createSQLQuery(sql0142).list();
			List<Object> list0150=HBUtil.getHBSession().createSQLQuery(sql0150).list();
			List<Object> list0160=HBUtil.getHBSession().createSQLQuery(sql0160).list();
			List<Object> list0198=HBUtil.getHBSession().createSQLQuery(sql0198).list();
			List<Object> list0199=HBUtil.getHBSession().createSQLQuery(sql0199).list();
			Object[] value0101=StatisticsWinBS.agearr(list0101);
			Object[] value0102=StatisticsWinBS.agearr(list0102);
			Object[] value0111=StatisticsWinBS.agearr(list0111);
			Object[] value0112=StatisticsWinBS.agearr(list0112);
			Object[] value0121=StatisticsWinBS.agearr(list0121);
			Object[] value0122=StatisticsWinBS.agearr(list0122);
			Object[] value0131=StatisticsWinBS.agearr(list0131);
			Object[] value0132=StatisticsWinBS.agearr(list0132);
			Object[] value0141=StatisticsWinBS.agearr(list0141);
			Object[] value0142=StatisticsWinBS.agearr(list0142);
			Object[] value0150=StatisticsWinBS.agearr(list0150);
			Object[] value0160=StatisticsWinBS.agearr(list0160);
			Object[] value0198=StatisticsWinBS.agearr(list0198);
			Object[] value0199=StatisticsWinBS.agearr(list0199);
			map.put("���Ҽ���ְ", value0101);
			map.put("���Ҽ���ְ", value0102);
			map.put("ʡ������ְ", value0111);
			map.put("ʡ������ְ", value0112);
			map.put("���ּ���ְ", value0121);
			map.put("���ּ���ְ", value0122);
			map.put("�ش�����ְ", value0131);
			map.put("�ش�����ְ", value0132);
			map.put("��Ƽ���ְ", value0141);
			map.put("��Ƽ���ְ", value0142);
			map.put("��Ա", value0150);
			map.put("����Ա", value0160);
			map.put("��������Ա", value0198);
			map.put("����", value0199);
			String data_arr1 = "";
			int i = 0;
			for(Map.Entry<String, Object[]> entry:map.entrySet()){
				for(int s=0;s<entry.getValue().length;s++){
					if(entry.getValue()[s]==null){
						entry.getValue()[s]=0;
					}
				}
				
			    data_arr1 += entry.getValue()[0]+",";
				data_arr1 += entry.getValue()[1]+",";
				data_arr1 += entry.getValue()[2]+",";
				data_arr1 += entry.getValue()[3]+",";
				data_arr1 += entry.getValue()[4]+",";
				data_arr1 += entry.getValue()[5]+",";
				data_arr1 += entry.getValue()[6]+",";	
				data_arr1 += entry.getValue()[7];
				qxdata += "{name:'"+entry.getKey()+"',type:'line',data:["+data_arr1+"]},";
				zzdata += "{name:'"+entry.getKey()+"',type:'bar',data:["+data_arr1+"],"
						+ "itemStyle:{normal:{label:{show: true,'position': 'top'}}}},";
				lddata += "{value:["+data_arr1+"],name:'"+entry.getKey()+"'},";
				this.getPageElement("vert"+i).setValue(entry.getKey());
				for(int j=0;j<8;j++){
					String[] data_add = data_arr1.split(",");
					this.getPageElement("vert"+i+"_tran"+j).setValue(data_add[j]);
					str += data_add[j]+"@";
				}
				str = str.substring(0,str.length()-1);
				str += "#";
				i++;
				data_arr1 ="";
			}  
			TwoDStatisticsShowPageModel.table_data_str = str.substring(0,str.length()-1);
			ld_tran_name += "{name:'30������'},{name:'31-35��'},{name:'36-40��'},{name:'41-45��'},{name:'46-50��'},"
						+ "{name:'51-54��'},{name:'55-59��'},{name:'60������'}]";
			this.getPageElement("tran0").setValue("30������");
			this.getPageElement("tran1").setValue("31-35��");
			this.getPageElement("tran2").setValue("36-40��");
			this.getPageElement("tran3").setValue("41-45��");
			this.getPageElement("tran4").setValue("46-50��");
			this.getPageElement("tran5").setValue("51-54��");
			this.getPageElement("tran6").setValue("55-59��");
			this.getPageElement("tran7").setValue("60������");
    		qxdata = qxdata.substring(0,qxdata.length()-1)+"]";
    		zzdata = zzdata.substring(0,zzdata.length()-1)+"]";
    		lddata = lddata.substring(0,lddata.length()-1)+"]";
			this.getPageElement("tran_name").setValue(tran_name);
	        this.getPageElement("vert_name").setValue(vert_name);
	        this.getPageElement("qxdata").setValue(qxdata);
	        this.getPageElement("zzdata").setValue(zzdata);
	        this.getPageElement("lddata").setValue(lddata);
	        this.getPageElement("ld_tran_name").setValue(ld_tran_name);
	        this.getPageElement("tran_length").setValue("8");
	       
	        this.getExecuteSG().addExecuteCode("def();");
	        this.getExecuteSG().addExecuteCode("createEwTable();");
			this.getExecuteSG().addExecuteCode("setQuxian();");
			this.getExecuteSG().addExecuteCode("setZhuzhuang();");
			this.getExecuteSG().addExecuteCode("setLeida();");	
			this.getExecuteSG().addExecuteCode("loding();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("7".equals(SQ001)){
			str = "�о���@��ѧ����@��ѧר��@��ר@���м�����@δ��д$5$���Ҽ���ְ@���Ҽ���ְ@ʡ������ְ@ʡ������ְ@���ּ���ְ@���ּ���ְ@�ش�����ְ@�ش�����ְ@��Ƽ���ְ@��Ƽ���ְ@��Ա@����Ա@��������Ա@����$14$"; 
			tran_name = "['�о���','��ѧ����','��ѧר��','��ר','���м�����','δ��д']";
			vert_name = "['���Ҽ���ְ','���Ҽ���ְ','ʡ������ְ','ʡ������ְ','���ּ���ְ','���ּ���ְ','�ش�����ְ',"
					+ "'�ش�����ְ','��Ƽ���ְ','��Ƽ���ְ','��Ա','����Ա','��������Ա','����']";
			String sql0101="SELECT SUM(CASE WHEN a.a0801b LIKE '1_' THEN 1 ELSE 0 END) �о���,"+
                    " SUM(CASE WHEN a.a0801b LIKE '2_' THEN 1 ELSE 0 END) ��ѧ����,"+
                    " SUM(CASE WHEN a.a0801b LIKE '3_' THEN 1 ELSE 0 END) ��ѧר��,"+
                    " SUM(CASE WHEN a.a0801b = '41' THEN  1 ELSE 0 END) ��ר,"+
                    " SUM(CASE WHEN a.a0801b = '61' OR a.a0801b = '71' OR a.a0801b = '81' THEN 1 ELSE  0  END) ���м�����,"+
                    " SUM(CASE WHEN a.a0801b IS NULL THEN 1 ELSE 0 END) δ��д"+
               " FROM (SELECT * FROM A08 WHERE a0834 = '1') a "+
               " RIGHT OUTER JOIN  (SELECT a0000 FROM A01 a01  WHERE a01.STATUS = '1'  AND a01.a0000 IN "+
                      " (SELECT a02.a0000  FROM a02  WHERE a02.a0255 = '1' "+groupid+")"+
                          " AND a01.a0148= '0101'"+
                          ")  b ON a.a0000 = b.a0000";
			String sql0102=sql0101.replace("0101", "0102");//���Ҽ���ְ
			String sql0111=sql0101.replace("0101", "0111");//ʡ������ְ
			String sql0112=sql0101.replace("0101", "0112");//ʡ������ְ
			String sql0121=sql0101.replace("0101", "0121");//���ּ���ְ
			String sql0122=sql0101.replace("0101", "0122");//���ּ���ְ
			String sql0131=sql0101.replace("0101", "0131");//�ش�����ְ
			String sql0132=sql0101.replace("0101", "0132");//�ش�����ְ
			String sql0141=sql0101.replace("0101", "0141");//��Ƽ���ְ
			String sql0142=sql0101.replace("0101", "0142");//��Ƽ���ְ
			String sql0150=sql0101.replace("0101", "0150");//��Ա
			String sql0160=sql0101.replace("0101", "0160");//����Ա
			String sql0198=sql0101.replace("0101", "0198");//��������Ա
			String sql0199=sql0101.replace("0101", "0199");//����
			Object[] obj0101=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0101).uniqueResult();
			Object[] obj0102=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0102).uniqueResult();
			Object[] obj0111=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0111).uniqueResult();
			Object[] obj0112=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0112).uniqueResult();
			Object[] obj0121=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0121).uniqueResult();
			Object[] obj0122=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0122).uniqueResult();
			Object[] obj0131=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0131).uniqueResult();
			Object[] obj0132=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0132).uniqueResult();
			Object[] obj0141=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0141).uniqueResult();
			Object[] obj0142=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0142).uniqueResult();
			Object[] obj0150=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0150).uniqueResult();
			Object[] obj0160=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0160).uniqueResult();
			Object[] obj0198=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0198).uniqueResult();
			Object[] obj0199=(Object[]) HBUtil.getHBSession().createSQLQuery(sql0199).uniqueResult();
			map.put("���Ҽ���ְ", obj0101);
			map.put("���Ҽ���ְ", obj0102);
			map.put("ʡ������ְ", obj0111);
			map.put("ʡ������ְ", obj0112);
			map.put("���ּ���ְ", obj0121);
			map.put("���ּ���ְ", obj0122);
			map.put("�ش�����ְ", obj0131);
			map.put("�ش�����ְ", obj0132);
			map.put("��Ƽ���ְ", obj0141);
			map.put("��Ƽ���ְ", obj0142);
			map.put("��Ա", obj0150);
			map.put("����Ա", obj0160);
			map.put("��������Ա", obj0198);
			map.put("����", obj0199);
			String data_arr1 = "";
			int i = 0;
			for(Map.Entry<String, Object[]> entry:map.entrySet()){
				for(int s=0;s<entry.getValue().length;s++){
					if(entry.getValue()[s]==null){
						entry.getValue()[s]=0;
					}
				}
				
			    data_arr1 += entry.getValue()[0]+",";
				data_arr1 += entry.getValue()[1]+",";
				data_arr1 += entry.getValue()[2]+",";
				data_arr1 += entry.getValue()[3]+",";
				data_arr1 += entry.getValue()[4]+",";
				data_arr1 += entry.getValue()[5];
				qxdata += "{name:'"+entry.getKey()+"',type:'line',data:["+data_arr1+"]},";
				zzdata += "{name:'"+entry.getKey()+"',type:'bar',data:["+data_arr1+"],"
						+ "itemStyle:{normal:{label:{show: true,'position': 'top'}}}},";
				lddata += "{value:["+data_arr1+"],name:'"+entry.getKey()+"'},";
				this.getPageElement("vert"+i).setValue(entry.getKey());
				for(int j=0;j<6;j++){
					String[] data_add = data_arr1.split(",");
					this.getPageElement("vert"+i+"_tran"+j).setValue(data_add[j]);
					str += data_add[j]+"@";
				}
				str = str.substring(0,str.length()-1);
				str += "#";
				i++;
				data_arr1 ="";
			}  
			TwoDStatisticsShowPageModel.table_data_str = str.substring(0,str.length()-1);  
			ld_tran_name += "{name:'�о���'},{name:'��ѧ����'},{name:'��ѧר��'},{name:'��ר'},{name:'���м�����'},{name:'δ��д'}]";
			this.getPageElement("tran0").setValue("�о���");
			this.getPageElement("tran1").setValue("��ѧ����");
			this.getPageElement("tran2").setValue("��ѧר��");
			this.getPageElement("tran3").setValue("��ר");
			this.getPageElement("tran4").setValue("���м�����");
			this.getPageElement("tran5").setValue("δ��д");
    		qxdata = qxdata.substring(0,qxdata.length()-1)+"]";
    		zzdata = zzdata.substring(0,zzdata.length()-1)+"]";
    		lddata = lddata.substring(0,lddata.length()-1)+"]";
    		
			this.getPageElement("tran_name").setValue(tran_name);
	        this.getPageElement("vert_name").setValue(vert_name);
	        this.getPageElement("qxdata").setValue(qxdata);
	        this.getPageElement("zzdata").setValue(zzdata);
	        this.getPageElement("lddata").setValue(lddata);
	        this.getPageElement("ld_tran_name").setValue(ld_tran_name);
	        this.getPageElement("tran_length").setValue("6");
	        
	        this.getExecuteSG().addExecuteCode("def();");
	        this.getExecuteSG().addExecuteCode("createEwTable();");
			this.getExecuteSG().addExecuteCode("setQuxian();");
			this.getExecuteSG().addExecuteCode("setZhuzhuang();");
			this.getExecuteSG().addExecuteCode("setLeida();");	
			this.getExecuteSG().addExecuteCode("loding();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*
		this.getPageElement("tran_name").setValue(tran_name);
        this.getPageElement("vert_name").setValue(vert_name);
        this.getPageElement("qxdata").setValue(qxdata);
        this.getPageElement("zzdata").setValue(zzdata);
        this.getPageElement("lddata").setValue(lddata);
        this.getPageElement("ld_tran_name").setValue(ld_tran_name);
        
        this.getExecuteSG().addExecuteCode("createEwTable();");
		this.getExecuteSG().addExecuteCode("setQuxian();");
		this.getExecuteSG().addExecuteCode("setZhuzhuang();");
		this.getExecuteSG().addExecuteCode("setLeida();");	*/
		return EventRtnType.NORMAL_SUCCESS;
		}

}