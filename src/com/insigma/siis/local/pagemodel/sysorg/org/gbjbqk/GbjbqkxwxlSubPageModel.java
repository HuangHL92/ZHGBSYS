package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.GbjbqkSubPageModel;

public class GbjbqkxwxlSubPageModel extends PageModel{

	public final static String col[]={" 1=0 "," 1=0 "," 1=0 ","",
		" (a08.A0801B like '1%' and a08.a0834='1') ",         //4�о���
		" 1=0 ",//
		" (a08.A0801B like '2%' and a08.a0834='1') ",        //6��ѧ����
		" 1=0 ",//
		" (a08.A0801B like '3%' and a08.a0834='1') ",         //8��ѧר��
		" 1=0 ",//
		" (a08.A0801B like '4%' and a08.a0834='1') ",         //��ר
		" 1=0 ",
		" (a08.A0801B in('61','71','81','91') and a08.a0834='1') ",          //���м�����
		" 1=0 ",//
		" ((a08.A0901B like '2%' or a08.A0901B=1) and a08.a0835='1') ",          //��ʿ
		" 1=0 ",//
		" (a08.A0901B like '3%' and a08.a0835='1') ",          //˶ʿ
		" 1=0 ",//
		" (a08.A0901B like '4%' and a08.a0835='1') ",          //ѧʿ
		" 1=0 ",
		
		" (a08.A0801B like '1%' and a08.A0831='1') ",         //�о���
		" 1=0 ",//
		" (a08.A0801B like '2%' and a08.A0831='1') ",        //6��ѧ����
		" 1=0 ",//
		" (a08.A0801B like '3%' and a08.A0831='1') ",         //8��ѧר��
		" 1=0 ",//
		" (a08.A0801B like '4%' and a08.A0831='1') ",         //��ר
		" 1=0 ",
		" (a08.A0801B in('61','71','81','91') and a08.A0831='1') ",          //���м�����
		" 1=0 ",//
		" ((a08.A0901B like '2%' or a08.A0901B=1) and a08.A0832='1') ",          //��ʿ
		" 1=0 ",//
		" (a08.A0901B like '3%' and a08.A0832='1') ",          //˶ʿ
		" 1=0 ",//
		" (a08.A0901B like '4%' and a08.A0832='1') ",          //ѧʿ
		" 1=0 ",
		
		" (a08.A0801B like '1%' and a08.A0838='1') ",         //�о���
		" 1=0 ",//
		" (a08.A0801B like '2%' and a08.A0838='1') ",        //6��ѧ����
		" 1=0 ",//
		" (a08.A0801B like '3%' and a08.A0838='1') ",         //8��ѧר��
		" 1=0 ",//
		" (a08.A0801B like '4%' and a08.A0838='1') ",         //��ר
		" 1=0 ",
		" (a08.A0801B in('61','71','81','91') and a08.A0838='1') ",          //���м�����
		" 1=0 ",//
		" ((a08.A0901B like '2%' or a08.A0901B=1) and a08.A0839='1') ",          //��ʿ
		" 1=0 ",//
		" (a08.A0901B like '3%' and a08.A0839='1') ",          //˶ʿ
		" 1=0 ",//
		" (a08.A0901B like '4%' and a08.A0839='1') ",          //ѧʿ
		" 1=0 "
		
		};
	

	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("initX")
	public int initX() throws RadowException{
		this.getPageElement("xy_zwlb").setValue("1");
		String subWinIdBussessId2 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(subWinIdBussessId2==null||subWinIdBussessId2.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr = subWinIdBussessId2.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];

		
		String col="";
		String row="";
		String query_tj="";
		col=param_arr[param_arr.length-3];
		row=param_arr[param_arr.length-2];
		query_tj=param_arr[param_arr.length-4];
		String title=param_arr[param_arr.length-1];
		if(param_arr.length==6){//1 ��һ�η���
		}else{//����һ�η���
			int m=(param_arr.length-6)/4;
			for(int i=1;i<=m;i++){
				col=col+","+param_arr[param_arr.length-3-4*i];
				row=row+","+param_arr[param_arr.length-2-4*i];
				title=title+"-"+param_arr[param_arr.length-1-4*i];
				query_tj=query_tj+","+param_arr[param_arr.length-4-4*i];
			}
			
		}

		this.getPageElement("title_h").setValue(title);
		this.getPageElement("dwid_h").setValue(groupid);
		this.getPageElement("col_num_h").setValue(col);
		this.getPageElement("row_num_h").setValue(row);
		this.getPageElement("query_tj_h").setValue(query_tj);
		String userid=SysUtil.getCacheCurrentUser().getId();
		this.getPageElement("userid_h").setValue(userid);
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("����ѡ��...", "");
		query_tj=query_tj.replace("ȫ��", "");
		query_tj=query_tj.replace(" ", "");
		if(col==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(row==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		if(param_arr_col.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT "
				+ " a01.a0101, "//����   ��
				  + " a01.a0104a, "//�Ա�
				  + " a01.a0184, "//������ݺ���   ��
				  + " a01.a0117a, "//����
				  + " a01.a0141, "//������ò
				  + " a01.a0149,"//ְ���� ����
				  + " a01.a0192a,"//��ְ��������   ��
				  + " a01.a0221, "//��ǰְ����
				
				  + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a01.a0107, "//��������
				  + " a01.A0288, "//����ְ����ʱ��   ��
				  + " a01.A0197 "//�Ƿ�����������ϻ��㹤������
	          + " FROM A01 a01 "
	          //+ " left join "
	          );//��Ա������Ϣ��

		boolean xueli=false; 
		boolean xuewei=false;
		boolean xueliq=false; 
		boolean xueweiq=false;
		boolean xueliz=false; 
		boolean xueweiz=false;
		for(int i=0;i<param_arr_col.length;i++){
	    	 if(Integer.parseInt( param_arr_col[i])<=13&&Integer.parseInt( param_arr_col[i])>=4){//���ѧ���� 
	    		 xueli=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=19&&Integer.parseInt( param_arr_col[i])>=14){//ѧλ�� 
	    		 xuewei=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=29&&Integer.parseInt( param_arr_col[i])>=20){//ȫ����ѧ����
	    		 xueliq=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=35&&Integer.parseInt( param_arr_col[i])>=30){
	    		 xueweiq=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=45&&Integer.parseInt( param_arr_col[i])>=36){//��ְѧ�� ��
	    		 xueliz=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=51&&Integer.parseInt( param_arr_col[i])>=46){
	    		 xueweiz=true;
	    	 }
	     }
		String tj="";
		if(xueli==true){
			tj=tj+" A0834='1' and ";
		}
		if(xuewei==true){
			tj=tj+" A0835='1' and ";
		}
		if(xueliq==true){
			tj=tj+" a0831='1' and ";
		}
		if(xueweiq==true){
			tj=tj+" a0832='1' and ";
		}
		if(xueliz==true){
			tj=tj+" a0838='1' and ";
		}
		if(xueweiz==true){
			tj=tj+" a0839='1' and ";
		}
			
		if("".equals(tj)){
			//sb.append( " (select * from  ( select A0901B,a0000,A0835,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08  ");
		}else{
			sb.append( " left join  (select * from ( select A0834,A0835,a0831,a0832,a0838,a0839,A0901B,A0801B,a0000,row_number() over(partition by a0000 order by A0901B ) rank from a08 where  "+tj.substring(0, tj.length()-4)+" ) a08_1 where  a08_1.rank=1)a08 on a01.a0000=a08.a0000 ");//ѧ��ѧλ��
		}

        GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        //���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb);
        for(int i=0;i<param_arr_row.length;i++){
        	new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
        }
	       
    	for(int i=0;i<param_arr_col.length;i++){
        	if("3".equals(param_arr_col[i])){//�ϼ�
	        	 
	         }else{
	        	 sb.append(" and " +GbjbqkxwxlSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
	         }
        }
    	GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
        gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����
        sb.append(" ");
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ͳ�ƣ�������
	 * @return
	 * @throws RadowException 
	 * @throws UnsupportedEncodingException 
	 * @throws AppException 
	 */
	@PageEvent("initTj")
	public int init() throws RadowException, UnsupportedEncodingException, AppException{
		CommQuery cq=new CommQuery();
		//���ø�ѡ��ѡ��
		this.getPageElement("xianyin").setValue("1");
		//����ռ��ѡ��
		this.getPageElement("yczb").setValue("1");
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("����ѡ��...", "");
		query_tj=query_tj.replace(" ", "");
		query_tj=query_tj.replace("ȫ��", "");
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		if(param_arr_col.length>6){
			this.setMainMessage("�벻Ҫѭ��������ķ���!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_col.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		StringBuffer sb=new StringBuffer();
		StringBuffer sb_zgxw=new StringBuffer();
		StringBuffer sb_zrzxl=new StringBuffer();
		StringBuffer sb_zrzxw=new StringBuffer();
		StringBuffer sb_zzxl=new StringBuffer();
		StringBuffer sb_zzxw=new StringBuffer();
		GbjbqkxwxlSql gbjbqkxwxlsql=new GbjbqkxwxlSql();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect(sb);
		gbjbqkxwxlsql.returnSqlSelect_zgxw(sb_zgxw);
		gbjbqkxwxlsql.returnSqlSelect_qrzxl(sb_zrzxl);
		gbjbqkxwxlsql.returnSqlSelect_qrzxw(sb_zrzxw);
		gbjbqkxwxlsql.returnSqlSelect_zzxl(sb_zzxl);
		gbjbqkxwxlsql.returnSqlSelect_zzxw(sb_zzxw);
		boolean xueli=false; 
		boolean xuewei=false;
		boolean xueliq=false; 
		boolean xueweiq=false;
		boolean xueliz=false; 
		boolean xueweiz=false;
		for(int i=0;i<param_arr_col.length;i++){
	    	 if(Integer.parseInt( param_arr_col[i])<=13&&Integer.parseInt( param_arr_col[i])>=4){//���ѧ���� 
	    		 xueli=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=19&&Integer.parseInt( param_arr_col[i])>=14){//ѧλ�� 
	    		 xuewei=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=29&&Integer.parseInt( param_arr_col[i])>=20){//ȫ����ѧ����
	    		 xueliq=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=35&&Integer.parseInt( param_arr_col[i])>=30){
	    		 xueweiq=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=45&&Integer.parseInt( param_arr_col[i])>=36){//��ְѧ�� ��
	    		 xueliz=true;
	    	 }else if(Integer.parseInt( param_arr_col[i])<=51&&Integer.parseInt( param_arr_col[i])>=46){
	    		 xueweiz=true;
	    	 }
	     }
		String tj="";
		if(xueli==true){
			tj=tj+" A0834='1' and ";
		}
		if(xuewei==true){
			tj=tj+" A0835='1' and ";
		}
		if(xueliq==true){
			tj=tj+" a0831='1' and ";
		}
		if(xueweiq==true){
			tj=tj+" a0832='1' and ";
		}
		if(xueliz==true){
			tj=tj+" a0838='1' and ";
		}
		if(xueweiz==true){
			tj=tj+" a0839='1' and ";
		}
		if("".equals(tj)){
			sb.append( " (select * from  ( select a0000,A0834,A0835,a0831,a0832,a0838,a0839,A0901B,A0801B,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 "); 
			sb_zgxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zrzxl.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0831='1' ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zrzxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0832='1' ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zzxl.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0838='1' ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zzxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0839='1' ) a08_1 where  a08_1.rank=1) a08  ");
		}else{
			sb.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0834='1' and "+tj.substring(0, tj.length()-4)+") a08_1 where  a08_1.rank=1) a08 "); 
			sb_zgxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1'  and "+tj.substring(0, tj.length()-4)+") a08_1 where  a08_1.rank=1) a08  ");
			sb_zrzxl.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0831='1'  and "+tj.substring(0, tj.length()-4)+") a08_1 where  a08_1.rank=1) a08  ");
			sb_zrzxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0832='1'  and "+tj.substring(0, tj.length()-4)+" ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zzxl.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0838='1'  and "+tj.substring(0, tj.length()-4)+" ) a08_1 where  a08_1.rank=1) a08  ");
			sb_zzxw.append( " (select * from  ( select A0901B,A0801B,a0000,A0834,A0835,a0831,a0832,a0838,a0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0839='1'  and "+tj.substring(0, tj.length()-4)+" ) a08_1 where  a08_1.rank=1) a08  ");
		}

        sb.append( " "
        		+ " on a01.a0000=a08.a0000 "
        		);
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		//���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb);
		for(int i=0;i<param_arr_row.length;i++){
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
		}
		for(int i=0;i<param_arr_col.length;i++){
			if("3".equals(param_arr_col[i])){//�ϼ�
	        	 
		      }else{
		        	 sb.append(" and " +GbjbqkxwxlSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		      }
		}
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
        gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����
         sb.append(" ) a group by A0221 "//��ǰְ���� ����
          );
		
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		//StringBuffer sb_zgxw=new StringBuffer();
		//StringBuffer sb_zrzxl=new StringBuffer();
		//StringBuffer sb_zrzxw=new StringBuffer();
		//StringBuffer sb_zzxl=new StringBuffer();
		//StringBuffer sb_zzxw=new StringBuffer();
		int num=list.size();
		

		StringBuffer sb1=new StringBuffer();
		sb1.append(
				" select a.A0221,"
					  +" count(a0000) heji "//�ϼ�
				 +" FROM (SELECT a01.a0000, "//��Աͳһ��ʶ��
				  		+ " a01.A0221 "//��ǰְ����
			          +" FROM a01 a01  "//��Ա������Ϣ��
			);  
		
		if("".equals(tj)){
			//sb.append( " (select * from  ( select A0901B,a0000,A0835,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08  ");
		}else{
			sb1.append( " left join  (select * from ( select a0000,A0834,A0835,a0831,a0832,a0838,a0839,A0901B,A0801B,row_number() over(partition by a0000 order by A0901B ) rank from a08 where  "+tj.substring(0, tj.length()-4)+" ) a08_1 where  a08_1.rank=1)a08 on a01.a0000=a08.a0000 ");//ѧ��ѧλ��
		}
		
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		//���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb1);
		for(int i=0;i<param_arr_row.length;i++){
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb1);   	 
		}
		for(int i=0;i<param_arr_col.length;i++){
			if("3".equals(param_arr_col[i])){//�ϼ�
		       	 
		     }else{
		       	 sb1.append(" and " +GbjbqkxwxlSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		     }
		}
		gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����
		     sb1.append(" ) a group by A0221 "//��ǰְ���� ����
		     );
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		combine(list,list1,num);
		
		
		bj_sql( sb_zgxw, groupid, param_arr_row,param_arr_col, query_tj,gbjbqksqlzs);//���ѧλ
		List<HashMap<String, Object>> list_zgxw=cq.getListBySQL(sb_zgxw.toString());
		combine_zgxw(list,list_zgxw,num);
		
		bj_sql( sb_zrzxl, groupid, param_arr_row,param_arr_col, query_tj,gbjbqksqlzs);//ȫ����ѧ��
		List<HashMap<String, Object>> list_zrzxl=cq.getListBySQL(sb_zrzxl.toString());
		combine_qrzxl(list,list_zrzxl,num);
		
		bj_sql( sb_zrzxw, groupid, param_arr_row,param_arr_col, query_tj,gbjbqksqlzs);//ȫ����ѧλ
		List<HashMap<String, Object>> list_zrzxw=cq.getListBySQL(sb_zrzxw.toString());
		combine_qrzxw(list,list_zrzxw,num);
		
		bj_sql( sb_zzxl, groupid, param_arr_row,param_arr_col, query_tj,gbjbqksqlzs);//��ְѧ��
		List<HashMap<String, Object>> list_zzxl=cq.getListBySQL(sb_zzxl.toString());
		combine_zzxl(list,list_zzxl,num);
		
		bj_sql( sb_zzxw, groupid, param_arr_row,param_arr_col, query_tj,gbjbqksqlzs);//��ְѧ��
		List<HashMap<String, Object>> list_zzxw=cq.getListBySQL(sb_zzxw.toString());
		combine_zzxw(list,list_zzxw,num);
		
		
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void bj_sql(StringBuffer sb,String groupid,String[] param_arr_row,String[] param_arr_col,String query_tj,GbjbqkSqlZs gbjbqksqlzs) throws RadowException{
		 sb.append( " "
	        		+ " on a01.a0000=a08.a0000 "
	        		);
			GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
			gbjbqksqlpj.sqlPjExists(sb, groupid);
			//���� a0221 ����
	  	    gbjbqksqlpj.sqlPjA01(sb);
			for(int i=0;i<param_arr_row.length;i++){
				new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
			}
			for(int i=0;i<param_arr_col.length;i++){
				if("3".equals(param_arr_col[i])){//�ϼ�
		        	 
			      }else{
			        	 sb.append(" and " +GbjbqkxwxlSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
			      }
			}
			gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����    
	         sb.append(" ) a group by A0221 "//��ǰְ���� ����
	          );
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ��ְѧλ�ϲ���list��
	 * @param list �ܼ���
	 * @param list1 �ּ���
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
	
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list1.get(i).get("a0221");
			if(temp.equals((String)list.get(i).get("a0221"))){
				list.get(i).put("bsz", list1.get(i).get("bsz"));//��ʿ
				list.get(i).put("ssz", list1.get(i).get("ssz"));//˶ʿ
				list.get(i).put("xsz", list1.get(i).get("xsz"));//ѧʿ
			}else{
				combine_zzxw(list,list1,temp,i);
			}
		}

		return list;
	}
	public void combine_zzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bsz", list1.get(i).get("bsz"));//��ʿ
				list.get(j).put("ssz", list1.get(i).get("ssz"));//˶ʿ
				list.get(j).put("xsz", list1.get(i).get("xsz"));//ѧʿ
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ��ְѧ���ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
				continue;
			}
			
			temp=(String)list1.get(i).get("a0221");
			
			if(temp.equals((String)list.get(i).get("a0221"))){
				list.get(i).put("yjsz", list1.get(i).get("yjsz"));//�о���
				list.get(i).put("dxbkz", list1.get(i).get("dxbkz"));//��ѧ����
				list.get(i).put("dxzzz", list1.get(i).get("dxzzz"));//��ѧר��
				list.get(i).put("zzz", list1.get(i).get("zzz"));//��ר
				list.get(i).put("gzjyxz", list1.get(i).get("gzjyxz"));//���м�����
			}else{
				combine_zzxl(list,list1,temp,i);
			}
		}
		
		return list;
	}
	public void combine_zzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("yjsz", list1.get(i).get("yjsz"));//�о���
				list.get(j).put("dxbkz", list1.get(i).get("dxbkz"));//��ѧ����
				list.get(j).put("dxzzz", list1.get(i).get("dxzzz"));//��ѧר��
				list.get(j).put("zzz", list1.get(i).get("zzz"));//��ר
				list.get(j).put("gzjyxz", list1.get(i).get("gzjyxz"));//���м�����
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ȫ����ѧλ�ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list1.get(i).get("a0221");
			
			if(temp.equals((String)list.get(i).get("a0221"))){
				list.get(i).put("bsq", list1.get(i).get("bsq"));//��ʿ
				list.get(i).put("ssq", list1.get(i).get("ssq"));//˶ʿ
				list.get(i).put("xsq", list1.get(i).get("xsq"));//ѧʿ
			}else{
				combine_qrzxw(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_qrzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bsq", list1.get(i).get("bsq"));//��ʿ
				list.get(j).put("ssq", list1.get(i).get("ssq"));//˶ʿ
				list.get(j).put("xsq", list1.get(i).get("xsq"));//ѧʿ
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ȫ����ѧ���ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list1.get(i).get("a0221");
			
			if(temp.equals((String)list.get(i).get("a0221"))){
				list.get(i).put("yjsq", list1.get(i).get("yjsq"));//�о���
				list.get(i).put("dxbkq", list1.get(i).get("dxbkq"));//��ѧ����
				list.get(i).put("dxzzq", list1.get(i).get("dxzzq"));//��ѧר��
				list.get(i).put("dxzzq", list1.get(i).get("zzq"));//��ר
				list.get(i).put("dxzzq", list1.get(i).get("gzjyxq"));//���м�����
			}else{
				combine_qrzxl(list,list1,temp,i);
			}
		}
		
		return list;
	}
	public void combine_qrzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("yjsq", list1.get(i).get("yjsq"));//�о���
				list.get(j).put("dxbkq", list1.get(i).get("dxbkq"));//��ѧ����
				list.get(j).put("dxzzq", list1.get(i).get("dxzzq"));//��ѧר��
				list.get(j).put("dxzzq", list1.get(i).get("zzq"));//��ר
				list.get(j).put("dxzzq", list1.get(i).get("gzjyxq"));//���м�����
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ���ѧλ�ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zgxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list1.get(i).get("a0221");
			
			if(temp.equals((String)list.get(i).get("a0221"))){
				list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
				list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
				list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
			}else{
				combine_zgxw(list,list1,temp,i);
			}
		}
		
		return list;
	}
	
	
	public void combine_zgxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bs", list1.get(i).get("bs"));//��ʿ
				list.get(j).put("ss", list1.get(i).get("ss"));//˶ʿ
				list.get(j).put("xs", list1.get(i).get("xs"));//ѧʿ
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> �ϼ� �� list ���ѧ��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("heji", list1.get(i).get("heji"));//�ϼ�
			}else{
				combine_jy(list,list1,temp,i);
			}
		}
		combine_list1_list_heji(list,list1,num);

		return list;
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("heji", list1.get(j).get("heji"));//�ϼ�
			}
		}
	}
	
	public void combine_list1_list_heji(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,int num){
		String str_a0021="";
		if(list1.size()>num){
			for(int m=0;m<list.size();m++){
				str_a0021=str_a0021+","+(String)list.get(m).get("a0221");
			}
		}
		loop1:for(int n=0;n<list1.size()-num;n++){
			for(int j=0;j<list1.size();j++){
				String temp_a0221=(String)list1.get(j).get("a0221");
				
				if(!"".equals(str_a0021)&&str_a0021.indexOf(temp_a0221)==-1){
					HashMap<String, Object> map=new HashMap<String, Object>();
					//{gzjyx=0, yjs=1, dxbkq=1, dxzzz=0, zzz=0, bsz=0, xs=1, 
							//yjsq=0, heji=1, dxbk=0, dxzzq=0},
					map.put("gzjyx", "0");
					map.put("yjs", "0");
					map.put("dxbkq", "0");
					map.put("dxzzz", "0");
					map.put("zzz", "0");
					map.put("bsz", "0");
					map.put("xs", "0");
					//bs=0, zzq=0, gzjyxq=0, dxbkz=0, bsq=0, ssq=0, ss=0, ssz=0, 
					map.put("bs", "0");
					map.put("zzq", "0");
					map.put("gzjyxq", "0");
					map.put("dxbkz", "0");
					map.put("bsq", "0");
					map.put("ssq", "0");
					map.put("ss", "0");
					map.put("ssz", "0");
					//yjsz=0, xsq=1, dxzz=0, zz=0, a0221=0131, xsz=0, gzjyxz=0, 
					map.put("yjsz", "0");
					map.put("xsq", "0");
					map.put("dxzz", "0");
					map.put("zz", "0");
					map.put("xsz", "0");
					map.put("gzjyxz", "0");
					//yjsq=0, heji=1, dxbk=0, dxzzq=0},
					map.put("yjsq", "0");
					map.put("dxbk", "0");
					map.put("dxzzq", "0");
					
					map.put("heji", (String)list1.get(j).get("heji"));
					map.put("a0221",temp_a0221 );
					list.add(map);
				}
			}
			if(n<list1.size()-num){
				continue loop1;
			}
		}
	}
	
	/**
	 * ����ȫ����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("xianyin.onclick")
	public int xianyin() throws RadowException{
		
		String xy=this.getPageElement("xianyin").getValue();
		this.getPageElement("xy_zwlb").setValue(xy);
		if("1".equals(xy)){//����
			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
		}else{//��ʾ
			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ְ���������ѡ ��ѡ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ռ��
	 * @throws RadowException 
	 */
	@PageEvent("yczb.onclick")
	public int yczb() throws RadowException{
		String xy=this.getPageElement("yczb").getValue();
		if("1".equals(xy)){//����
			this.getExecuteSG().addExecuteCode("yincangzb()");
		}else{//��ʾ
			this.getExecuteSG().addExecuteCode("xszhanbi()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
