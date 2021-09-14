package com.insigma.siis.local.pagemodel.amain;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.util.Hash;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

public class GBMainZBXQMatePageModel extends PageModel{ 

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  ������Ա��Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	  String b0111 = this.getPageElement("b0111").getValue();
	  String query_type = this.getPageElement("query_type").getValue();
	  String colIndex = this.getPageElement("colIndex").getValue();
	  
	  String sql = "";
	  
	  if("��ֱ��λ�쵼�ɲ��й�ָ�ָ꣨��2��3��".equals(query_type)){
		  String[] condition = new String[]{"","","","","","","","","","","",""};
		  condition[3] = " and age<47 ";
		  condition[5] = " and age<42 ";
		  condition[9] = " and age<40 ";
		  condition[11] = " and age<37 ";
		  String[] condition2 = new String[]{"ȫ��","��ֱȫ��","����ȫ��","��Уȫ��"};
		  condition2[0] = "  ";
		  condition2[1] = " and b0111 like '001.001.002.%' ";
		  condition2[2] = " and b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511' ";
		  condition2[3] = " and b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43' ";
		  //��ֱ�йܸɲ� ��ֱ�йܸɲ�45 ��ֱ�йܸɲ�40
		  if("2,3,5".contains(colIndex)){
			  sql = "select  a0101,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,         a01.a0000 ,substr(a0201b,0,15) b0111    from A01 a01,v_hz_a02 a02          where a01.a0000 = a02.a0000            and a02.a0281 = 'true'            and a0255='1'            and ( a02.A0201B like '001.001.003%' or a02.A0201B like '001.001.002%')            and decode(a0248, null, '0', '', '0', a0248) = '0'            and a0201d='1'  and a0201e in ('1','3','31')           and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%')            and a0163 = '1'            group by a0101,a01.a0000,a0107,substr(a0201b,0,15)";
		  }else if("8,9,11".contains(colIndex)){//��ֱ�����ɲ� ��ֱ�����ɲ�40 ��ֱ�����ɲ�35
			  sql = "select  a0101,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,         a01.a0000 ,substr(a0201b,0,15) b0111    from A01 a01,v_hz_a02 a02          where a01.a0000 = a02.a0000            and a02.a0281 = 'true'            and a0255='1'            and (a02.A0201B like '001.001.003%' or a02.A0201B like '001.001.002%')            and a01.a0221 in('0131','013105','0132','013202','QY04','QY07','QY08')            and a0163 = '1'            group by a0101,a01.a0000,a0107,substr(a0201b,0,15)   ";
		  }
		  if(b0111.length()==1){
			  sql = " select a0000 from ("+sql+") t where 1=1 "+condition2[Integer.valueOf(b0111)];
		  }else{
			  sql = " select a0000 from ("+sql+") t where b0111 like '"+b0111+"%' ";
		  }
		 
		  sql = sql + condition[Integer.valueOf(colIndex)];
	  }else if("�����أ��У������й�ָ�ָ꣨��4��5��16��".equals(query_type)){
		  
		  if("3".contains(colIndex)){
			  sql = "select count(a0000),a0000, substr(a02.a0201b, 0, 15) b0111 from a02 where (select b0131 from b01 s where s.b0111 = a02.a0201b) in ('1001', '1004') and length(a02.a0201b) = 19 and a0255 = '1' and decode(a0248, null, '0', '', '0', a0248) = '0' and a0201e in ('1', '3', '31') group by substr(a02.a0201b, 0, 15), a0000 having count(a0000) >= 2";
		  }else{
			  sql = "select a0101, substr(A0201B, 0, 15) b0111, (select max('1') from hz_a17 b where b.a0000 = a01.a0000 and a1705 in ('0901', '0902', '0818')) dzzz, a01.a0000, substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age, qrzxl, a01.a0288, min(a0201e) a0201e,a0165 from A01 a01,v_hz_a02 a02 where a01.a0000 = a02.a0000 and a02.A0201B like '001.001.004%' and length(a02.A0201B) = 19 and (select b0131 from b01 where b01.b0111 = a02.A0201B) in ('1001', '1004') and a02.a0281 = 'true' and a0255 = '1' and a0201d = '1' and a0163 = '1' and decode(a0248, null, '0', '', '0', a0248) = '0' and a0201e in ('1', '3', '31') group by a0101, substr(A0201B, 0, 15), a01.a0000, a0107, qrzxl, a01.a0288,a0165";
		  }
		  String[] condition = new String[]{"","","","","","","","","","","",""};
		  condition[2] = " ";
		  condition[4] = " and a0165 like '%02%' and age < 42 ";
		  condition[6] = " and age < 42 ";
		  condition[8] = " and dzzz = '1' ";
		 
		  if(b0111.length()==1){
			  sql = " select a0000 from ("+sql+") t where 1=1 ";
		  }else{
			  sql = " select a0000 from ("+sql+") t where b0111 like '"+b0111+"%' ";
		  }
		 
		  sql = sql + condition[Integer.valueOf(colIndex)];
	  }else if(query_type.contains("���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��")){
		  Map<String, String> m = new HashMap<String, String>();
		  m.put("���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��������ֵ���", " and (select b0124 from b01 where b01.b0111 = a02.A0201B) in ('81', '82', '83') ");
		  m.put("���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��������", " and (select b0124 from b01 where b01.b0111 = a02.A0201B) in ('81', '82') ");
		  m.put("���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9�����ֵ���", " and (select b0124 from b01 where b01.b0111 = a02.A0201B) in ('83') ");
		  sql = "select a0101, substr(A0201B, 0, 15) b0111, a01.a0000, substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age, qrzxl, a01.a0288, min(a0201e) a0201e, a0132, a0133 from A01 a01,v_hz_a02 a02 where a01.a0000 = a02.a0000 and a02.A0201B like '001.001.004%' "+m.get(query_type)+" and a02.a0281 = 'true' and a0255 = '1' and a0201d = '1' and a0163 = '1' and decode(a0248, null, '0', '', '0', a0248) = '0' and a0201e in ('1', '3', '31') group by a0101, substr(A0201B, 0, 15), a01.a0000, a0107, qrzxl, a01.a0288, a0132, a0133";
		  String[] condition = new String[]{"","","","","","","","","","","",""};
		  condition[1] = " ";
		  condition[2] = " and (a0132 = '1' or a0133 = '1') ";
		  condition[3] = " and age < 35 ";
		  condition[5] = " and (a0132 = '1' or a0133 = '1') and age < 35 ";
		  condition[7] = " and (a0132 = '1') and age < 35 ";
		  condition[8] = " and (a0133 = '1') and age < 35 ";
		  condition[9] = " and age < 30 ";
		  
		  if(b0111.length()==1){
			  sql = " select a0000 from ("+sql+") t where 1=1 ";
		  }else{
			  sql = " select a0000 from ("+sql+") t where b0111 like '"+b0111+"%' ";
		  }
		  sql = sql + condition[Integer.valueOf(colIndex)];
	  }else if(query_type.contains("�ṹ�Ըɲ���Ů�ɲ�������ɲ����й�ָ�ָ꣨��17��18��19��20��")){
		  String[] condition = new String[]{"","","","","","","","","","","",""};
		  condition[1] = " and a0104 = '2' and b0131 in ('1001', '1004') ";
		  condition[2] = " and a0104 = '2' and b0131 in ('1001') ";
		  condition[3] = " and a0104 = '2' and b0131 in ('1004') ";
		  condition[7] = " and b0131 in ('1005') ";
		  condition[8] = " and a0141 != '01' and b0131 in ('1005') ";
		  //�м�
		  if("4,5".contains(colIndex)){
			  sql = "";
		  }else if("1,2,3,7,8".contains(colIndex)){//����
			  sql = "select a0101, substr(A0201B, 0, 15) b0111, (select b0131 from b01 where b01.b0111 = a02.A0201B) b0131, a01.a0000, min(a0201e) a0201e, a0104, a0141 from A01 a01,v_hz_a02 a02 where a01.a0000 = a02.a0000 and a02.A0201B like '001.001.004%' and length(a02.A0201B) = 19 and (select b0131 from b01 where b01.b0111 = a02.A0201B) in ('1001', '1004', '1005') and a02.a0281 = 'true' and a0255 = '1' and a0201d = '1' and a0163 = '1' and decode(a0248, null, '0', '', '0', a0248) = '0' and a0201e in ('1', '3', '31') group by a0101, a02.A0201B, a01.a0000, a0104, a0141";
		  }
		  if(b0111.length()==1){
			  sql = " select a0000 from ("+sql+") t where 1=1 ";
		  }else{
			  sql = " select a0000 from ("+sql+") t where b0111 like '"+b0111+"%' ";
		  }
		  sql = sql + condition[Integer.valueOf(colIndex)];
	  }else if(query_type.contains("�ɲ��ճ������й�ָ�ָ꣨��10��12��13��21��22��27��28��")){
		  String[] condition = new String[]{"","","","","","","","","","","",""};
		  condition[1] = " and  age<40 and a0288 like TO_CHAR(add_months(trunc(sysdate),-12) ,'yyyy')||'%' and(instr(qrzxl,'�о���')>0 or instr(qrzxl,'��ѧ')>0) ";
		  condition[7] = " and (instr(qrzxl,'�о���')>0 or instr(qrzxl,'��ѧ')>0) ";
		  condition[3] = " and age<40 and (instr(qrzxl,'�о���')>0 or instr(qrzxl,'��ѧ')>0) and (b0111 like '001.001.003.%' or b0111 like '001.001.002.%')";
		  condition[5] = " and age<40 and (instr(qrzxl,'�о���')>0 or instr(qrzxl,'��ѧ')>0) and b0111 like '001.001.004.%'";
		 
		  String[] condition2 = new String[]{"ȫ��","��ֱȫ��","����ȫ��","��Уȫ��","����ȫ��"};
		  condition2[0] = "  ";
		  condition2[1] = " and b0111 like '001.001.002.%' ";
		  condition2[2] = " and b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511' ";
		  condition2[3] = " and b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43' ";
		  condition2[4] = " and b0111 like '001.001.004.%' ";
		  
		  //��������
		  if("1,7".contains(colIndex)){
			  sql = " select a0101,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age, a01.a0000 , qrzxl,a01.a0288,substr(a0201b,0,15) b0111 from A01 a01, v_hz_a02 a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a0255='1' and (a02.A0201B like '001.001.004%' or a02.A0201B like '001.001.003%' or a02.A0201B like '001.001.002%') and decode(a0248, null, '0', '', '0', a0248) = '0' and a0201d='1' and a0201e in ('1','3','31') and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%') and a0163 = '1' group by a0101,a01.a0000,a0107,qrzxl,a01.a0288,substr(a0201b,0,15) ";
		  }else if("3,5".contains(colIndex)){//����
			  sql = "select a0101,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age, a01.a0000 , qrzxl,a01.a0288,substr(a0201b,0,15) b0111 from A01 a01,v_hz_a02 a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a0255='1' and (a02.A0201B like '001.001.004%' or a02.A0201B like '001.001.003%' or a02.A0201B like '001.001.002%') and a01.a0221 in('0131','013105','0132','013202','QY04','QY07','QY08') and a0163 = '1' and a01.a0288 like TO_CHAR(add_months(trunc(sysdate),-12) ,'yyyy')||'%' group by a0101,a01.a0000,a0107,qrzxl,a01.a0288,substr(a0201b,0,15) ";
		  }
		  
		  if(b0111.length()==1){
			  sql = " select a0000 from ("+sql+") t where 1=1 "+condition2[Integer.valueOf(b0111)];
		  }else{
			  sql = " select a0000 from ("+sql+") t where b0111 like '"+b0111+"%' ";
		  }
		 
		  sql = sql + condition[Integer.valueOf(colIndex)];
	  }
	  String userid = SysManagerUtils.getUserId();

	  sql = "select "+CommSQL.getComFields(userid, "")+" from a01 where a0000 in  ("+sql+") ";
	  
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  sql += " order by " + ordersql;	
	  this.pageQuery(sql, "SQL", start, limit);
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

		String a0000=this.getPageElement("noticeSetgrid").getValue("a0000",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("�Ѿ�����");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
}
