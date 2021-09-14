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
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GbjbqkxwxlPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		//��λid
		String subWinIdBussessId = this.getPageElement("subWinIdBussessId").getValue();
		
		
		String[] param_arr = subWinIdBussessId.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];

		
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//ƴ��sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		//ƴ��sql from 
		sb.append(" "
	          +" FROM A01 a01 "
				);
		gbjbqksqlpj.sqlPjExists(sb, groupid);
   
        gbjbqksqlpj.sqlPjA01(sb);//���� a0221 ����

	         sb.append(" ");
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ͳ�Ʒ���
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
		
		this.getPageElement("xy_zwlb").setValue("1");
		
		
		//��λid
		String subWinIdBussessId = this.getPageElement("subWinIdBussessId").getValue();
		
		
		String[] param_arr = subWinIdBussessId.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];

		/////////////���ѧ��///////////////
		StringBuffer sb=new StringBuffer();
		GbjbqkxwxlSql gbjbqkxwxlsql=new GbjbqkxwxlSql();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect(sb);
		sb.append( " (select * from  ( select A0801B,a0000,A0834,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		gbjbqksqlpj.sqlPjA01(sb);

	    sb.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		///////////���ѧλ///////////
		StringBuffer sb_zgxw=new StringBuffer();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect_zgxw(sb_zgxw);
		sb_zgxw.append( " (select * from  ( select A0901B,a0000,A0835,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zgxw.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zgxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zgxw);

        sb_zgxw.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list_zgxw=cq.getListBySQL(sb_zgxw.toString());
		combine_zgxw(list,list_zgxw);
		
		//////////////ȫ����ѧ��//////////////////////////
		StringBuffer sb_zrzxl=new StringBuffer();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect_qrzxl(sb_zrzxl);
		sb_zrzxl.append( " (select * from  ( select A0801B,a0000,A0831,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0831='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zrzxl.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zrzxl, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zrzxl);

        sb_zrzxl.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list_qzrxl=cq.getListBySQL(sb_zrzxl.toString());
		combine_qrzxl(list,list_qzrxl);
		
		/////////////ȫ����ѧλ//////////////////
		StringBuffer sb_zrzxw=new StringBuffer();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect_qrzxw(sb_zrzxw);
		sb_zrzxw.append( " (select * from  ( select A0901B,a0000,A0832,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0832='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zrzxw.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zrzxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zrzxw);

        sb_zrzxw.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list_qzrxw=cq.getListBySQL(sb_zrzxw.toString());
		combine_qrzxw(list,list_qzrxw);
		
		/////////////////��ְѧ��///////////////
		StringBuffer sb_zzxl=new StringBuffer();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect_zzxl(sb_zzxl);
		sb_zzxl.append( " (select * from  ( select A0801B,a0000,A0838,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0838='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zzxl.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zzxl, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zzxl);

        sb_zzxl.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list_zzxl=cq.getListBySQL(sb_zzxl.toString());
		combine_zzxl(list,list_zzxl);
		
		/////////////��ְѧλ////////////
		StringBuffer sb_zzxw=new StringBuffer();
		// ƴ�� select
		gbjbqkxwxlsql.returnSqlSelect_zzxw(sb_zzxw);
		sb_zzxw.append( " (select * from  ( select A0901B,a0000,A0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0839='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zzxw.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zzxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zzxw);

        sb_zzxw.append(" ) a group by A0221 "//��ǰְ���� ����
	      );
		List<HashMap<String, Object>> list_zzxw=cq.getListBySQL(sb_zzxw.toString());
		combine_zzxw(list,list_zzxw);
		
		//�ϼ�
		StringBuffer sb1=new StringBuffer();
		sb1.append(
				" select a.A0221,"
					  +" count(a0000) heji "//�ϼ�
				 +" FROM (SELECT a01.a0000, "//��Աͳһ��ʶ��
				  		+ " a01.A0221 "//��ǰְ����
			          +" FROM A01 a01 "//��Ա������Ϣ��
			       //  +" WHERE a01.status = '1' "
			         );//��ְ״̬ 1 ����
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		gbjbqksqlpj.sqlPjA01(sb1);
        sb1.append(" ) a group by A0221 "//��ǰְ���� ����
			);
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		combine(list,list1);
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ��ְѧλ�ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
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
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bsz", list1.get(j).get("bsz"));//��ʿ
				list.get(i).put("ssz", list1.get(j).get("ssz"));//˶ʿ
				list.get(i).put("xsz", list1.get(j).get("xsz"));//ѧʿ
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ��ְѧ���ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
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
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("yjsz", list1.get(j).get("yjsz"));//�о���
				list.get(i).put("dxbkz", list1.get(j).get("dxbkz"));//��ѧ����
				list.get(i).put("dxzzz", list1.get(j).get("dxzzz"));//��ѧר��
				list.get(i).put("zzz", list1.get(j).get("zzz"));//��ר
				list.get(i).put("gzjyxz", list1.get(j).get("gzjyxz"));//���м�����
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ȫ����ѧλ�ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
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
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bsq", list1.get(j).get("bsq"));//��ʿ
				list.get(i).put("ssq", list1.get(j).get("ssq"));//˶ʿ
				list.get(i).put("xsq", list1.get(j).get("xsq"));//ѧʿ
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ȫ����ѧ���ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
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
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("yjsq", list1.get(j).get("yjsq"));//�о���
				list.get(i).put("dxbkq", list1.get(j).get("dxbkq"));//��ѧ����
				list.get(i).put("dxzzq", list1.get(j).get("dxzzq"));//��ѧר��
				list.get(i).put("dxzzq", list1.get(j).get("zzq"));//��ר
				list.get(i).put("dxzzq", list1.get(j).get("gzjyxq"));//���м�����
			}
		}
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> ���ѧλ�ϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zgxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
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
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//��ʿ
				list.get(i).put("ss", list1.get(j).get("ss"));//˶ʿ
				list.get(i).put("xs", list1.get(j).get("xs"));//ѧʿ
			}
		}
	}
	/**
	 * �ϲ����� List<HashMap<String, Object>> �ϼƺϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("heji", list1.get(i).get("heji"));//�ϼ�
			}else{
				combine_jy(list,list1,temp,i);
			}
		}
		return list;
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("heji", list1.get(j).get("heji"));//�ϼ�
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
		//��������ѡ��ѡ
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
	
	/**
	 * ְ���������ѡ ��ѡ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		//���ø�ѡ��ѡ��
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
