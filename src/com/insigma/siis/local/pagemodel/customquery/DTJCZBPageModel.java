package com.insigma.siis.local.pagemodel.customquery;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.amain.DTZBUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class DTJCZBPageModel extends PageModel{
	
	
	@Override
	public int doInit() throws RadowException {
		CommQuery cqbs=new CommQuery();
		DecimalFormat df = new DecimalFormat("0.00%");
		StringBuilder js = new StringBuilder();
		try {
			
			
			//ָ��2,3
			String sql_2_3 = DTZBUtils.getv_hz_dtzb_2()[0];
			List<HashMap<String, String>> ret_2_3 = cqbs.getListBySQL2(sql_2_3);
			if(ret_2_3.size()>0){
				HashMap<String, String> countMap = ret_2_3.get(0);
				double ageCount = Double.valueOf(countMap.get("��ֱ�йܸɲ�"));
				double age47 = Integer.valueOf(countMap.get("��ֱ�йܸɲ�45"));
				double age42 = Integer.valueOf(countMap.get("��ֱ�йܸɲ�40"));
				String r1 = df.format(age47/ageCount);
				String r2 = df.format(age42/ageCount);
				js.append("$('.count2').text('"+r1+" / " + r2+"');");
				
				double ��ֱ�����ɲ� = Double.valueOf(countMap.get("��ֱ�����ɲ�"));
				double age40 = Integer.valueOf(countMap.get("��ֱ�����ɲ�40"));
				double age37 = Integer.valueOf(countMap.get("��ֱ�����ɲ�35"));
				String r3 = df.format(age40/��ֱ�����ɲ�);
				String r4 = df.format(age37/��ֱ�����ɲ�);
				js.append("$('.count3').text('"+r3+" / " + r4+"');");
			}
			
			
			
			
			//ָ��6,7,8,9
			String sql_6 = DTZBUtils.getSQLZB_QXSXZ()[3];
			List<HashMap<String, String>> ret6 = cqbs.getListBySQL2(sql_6);
			if(ret6.size()>0){
				HashMap<String, String> countMap = ret6.get(0);
				double ageCount = Double.valueOf(countMap.get("ʵ��"));
				double age35 = Integer.valueOf(countMap.get("��ʮ��������"));
				String r1 = df.format(age35/ageCount);
				js.append("$('.count6').text('"+r1+"');");
				
				String age30 = countMap.get("��ʮ������");
				js.append("$('.count7').text('"+age30+"');");
				
				double dzzz = Double.valueOf(countMap.get("������ְ"));
				double dzzzage35 = Integer.valueOf(countMap.get("��ʮ���굳����ְ"));
				String r2 = df.format(dzzzage35/dzzz);
				js.append("$('.count8').text('"+r2+"');");
				
				double dzzzage35sj = Integer.valueOf(countMap.get("��ʮ���굳����ְ��ί���"));
				String r3 = df.format(dzzzage35sj/dzzzage35);
				js.append("$('.count9').text('"+r3+"');");
			}
			
			
			
			//9�ɲ��ճ������й�ָ�ָ꣨��10��12��13��14��15��21��22��27��28��ȫ��
			String sql_9 = DTZBUtils.getv_hz_dtzb_9()[0];
			List<HashMap<String, String>> ret9 = cqbs.getListBySQL2(sql_9);
			if(ret9.size()>0){
				HashMap<String, String> countMap = ret9.get(0);
				//ָ��13
				double �й�������40 = Integer.valueOf(countMap.get("�й�������40"));
				int �й�������40ȫ���� = Integer.valueOf(countMap.get("�й�������40ȫ����"));
				String r1 = df.format(�й�������40ȫ����/�й�������40);
				js.append("$('.count13').text('"+r1+"');");
				//ָ��14
				double ��ֱ����������40 = Integer.valueOf(countMap.get("������40����"));
				int ��ֱ��40����ȫ���� = Integer.valueOf(countMap.get("��40����ȫ����"));
				String r2 = df.format(��ֱ��40����ȫ����/��ֱ����������40);
				js.append("$('.count14').text('"+r2+"');");
				//ָ��15
				double ���ش���������40 = Integer.valueOf(countMap.get("���ش���������40"));
				int ������40����ȫ���� = Integer.valueOf(countMap.get("������40����ȫ����"));
				String r3 = df.format(������40����ȫ����/���ش���������40);
				js.append("$('.count15').text('"+r3+"');");
				
				//ָ��12
				double �йܸɲ� = Integer.valueOf(countMap.get("�йܸɲ�"));
				int �й�ȫ���� = Integer.valueOf(countMap.get("�й�ȫ����"));
				String r4 = df.format(�й�ȫ����/�йܸɲ�);
				js.append("$('.count12').text('"+r4+"');");
				//ָ��21
				double ��ְ�� = Integer.valueOf(countMap.get("��ְ��"));
				int �ܿ�ȱ = Integer.valueOf(countMap.get("�ܿ�ȱ"));
				String r5 = df.format(�ܿ�ȱ/��ְ��);
				js.append("$('.count21').text('"+r5+"');");
			}
			
			//ָ��4,5,16
			String sql_16 = DTZBUtils.getSQLZB_QXS()[1];
			List<HashMap<String, String>> ret16 = cqbs.getListBySQL2(sql_16);
			if(ret16.size()>0){
				HashMap<String, String> countMap = ret16.get(0);
				double ageCount = Double.valueOf(countMap.get("��λ��"));
				double perc = Integer.valueOf(countMap.get("����ֵ�������ְ��굥λ"));
				String r1 = df.format(perc/ageCount);
				js.append("$('.count16').text('"+r1+"');");
				//ָ��4
				int qxdzzz = Integer.valueOf(countMap.get("������ְ"));
				double qxdzzzage = Integer.valueOf(countMap.get("��ʮ�����ҵ�����ְ"));
				String r4 = df.format(qxdzzzage/qxdzzz);
				js.append("$('.count4').text('"+r4+"');");
				//ָ��5
				double sp = Double.valueOf(countMap.get("ʵ��"));
				int age42 = Integer.valueOf(countMap.get("��ʮ������"));
				String r5 = df.format(age42/sp);
				js.append("$('.count5').text('"+r5+"');");
			}
			
			String sql_17 = DTZBUtils.getSQLZB_QXS_NVDW()[1];
			List<HashMap<String, String>> ret17 = cqbs.getListBySQL2(sql_17);
			if(ret17.size()>0){
				HashMap<String, String> countMap = ret17.get(0);
				String r1 = countMap.get("��ίŮ�ɲ�����");
				js.append("$('.count17').text('"+r1+"');");
				
				String r2 = countMap.get("����Ů�ɲ�����");
				js.append("$('.count18').text('"+r2+"');");
				
				double ageCount = Double.valueOf(countMap.get("��Э�ɲ�����"));
				double dwgb = Integer.valueOf(countMap.get("��Э����ɲ�����"));
				String r3 = df.format(dwgb/ageCount);
				js.append("$('.count20').text('"+r3+"');");
			}
			
			
			String sql_19 = DTZBUtils.getSQLZB_QXS_NVDW()[2];
			List<HashMap<String, String>> ret19 = cqbs.getListBySQL2(sql_19);
			if(ret19.size()>0){
				HashMap<String, String> countMap = ret19.get(0);
				double ageCount = Double.valueOf(countMap.get("��������"));
				double ngb = Integer.valueOf(countMap.get("Ů�ɲ���������"));
				String r1 = df.format(ngb/ageCount);
				js.append("$('.count19').text('"+r1+"');");
			}
			
			
			
			this.getExecuteSG().addExecuteCode(js.toString());
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static void main(String[] args) {
        double d = 0.2365566789;
 
        //����ķ���������DecimalFormat��
        DecimalFormat df = new DecimalFormat("0.00%");
        System.out.println(df.format(d));
    }

	
}
