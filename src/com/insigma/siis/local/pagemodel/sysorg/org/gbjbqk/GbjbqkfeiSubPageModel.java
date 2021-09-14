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

public class GbjbqkfeiSubPageModel extends PageModel{

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
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//ƴ��sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		//ƴ��sql from 
		sb.append(" "
          +" FROM A01 a01 left join ");//��Ա������Ϣ��
		boolean xueli=false; 
		boolean xuewei=false;
		for(int i=0;i<param_arr_col.length;i++){
			if(Integer.parseInt( param_arr_col[i])<=19||Integer.parseInt( param_arr_col[i])>=26){
	    		 xueli=true;
	    	 }else{
	    		 xuewei=true;
	    	 }
		}
		if(xueli==true&&xuewei==true){
			sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by A0835 ) rank from a08 where A0835='1' and A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		}else if(xueli==true){
			sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by A0834 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		}else{
			sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by A0835 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
		}

        sb.append( " on a01.a0000=a08.a0000 "
        		);
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        sb.append(""
        		+ " and a01.a0141!='01' " //���й���Ա
        		+ "");
        //���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb);
        
        for(int i=0;i<param_arr_row.length;i++){
        	  new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
        }
        if(DBType.ORACLE==DBUtil.getDBType()){
        	for(int i=0;i<param_arr_col.length;i++){
          	  if("3".equals(param_arr_col[i])){//�ϼ�
   	        	 
   	         }else if("4".equals(param_arr_col[i])||"6".equals(param_arr_col[i])){
   		    	  sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
   		      }else{
   	        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])+4]);
   	         }
          }
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			for(int i=0;i<param_arr_col.length;i++){
	        	  if("3".equals(param_arr_col[i])){//�ϼ�
	 	        	 
	 	         }else if("4".equals(param_arr_col[i])||"6".equals(param_arr_col[i])){
	 		    	  sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])]);
	 		      }else{
	 	        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])+4]);
	 	         }
	        }
		}else{
			throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
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
		boolean xueli=false; 
		boolean xuewei=false;
		for(int i=0;i<param_arr_col.length;i++){
			if(Integer.parseInt( param_arr_col[i])<=19||Integer.parseInt( param_arr_col[i])>=26){
	    		 xueli=true;
	    	 }else{
	    		 xuewei=true;
	    	 }
		}
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"fei");
		//ƴ��sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11_sub(sb,xueli,xuewei);
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		sb.append(" and a01.a0141!='01' ");//���й���Ա
		//���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb);
        //�� ����
		for(int i=0;i<param_arr_row.length;i++){
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
		}
		if(DBType.ORACLE==DBUtil.getDBType()){
			//������
			for(int i=0;i<param_arr_col.length;i++){
				if("3".equals(param_arr_col[i])){//�ϼ�
		        	 
			      }else if("4".equals(param_arr_col[i])||"6".equals(param_arr_col[i])){
			    	  sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
			      }else{
			        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])+4]);
			      }
			}
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			//������
			for(int i=0;i<param_arr_col.length;i++){
				if("3".equals(param_arr_col[i])){//�ϼ�
		        	 
			      }else if("4".equals(param_arr_col[i])||"6".equals(param_arr_col[i])){
			    	  sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])]);
			      }else{
			        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])+4]);
			      }
			}
		}else{
			throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
		}
		gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����    
        sb.append(" ) a group by A0221 "//��ǰְ���� ����
     
          );
		
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		StringBuffer sb1=new StringBuffer();
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//ƴ��sqlfrom
		gbjbqksqlzs.returnGbjbqkSqlZs21_sub(sb1,xueli,xuewei);
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		sb1.append(" and a01.a0141!='01' ");//���й���Ա
		//���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb1);
        //�� ����
		for(int i=0;i<param_arr_row.length;i++){
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb1);
		}
		if(DBType.ORACLE==DBUtil.getDBType()){
			//������
			for(int i=0;i<param_arr_col.length;i++){
				if("3".equals(param_arr_col[i])){//�ϼ�
			       	 
			     }else if("4".equals(col)||"6".equals(col)){
			   	  sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
			     }else{
			       	 sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])+4]);
			     }
			}
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			//������
			for(int i=0;i<param_arr_col.length;i++){
				if("3".equals(param_arr_col[i])){//�ϼ�
			       	 
			     }else if("4".equals(col)||"6".equals(col)){
			   	  sb1.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])]);
			     }else{
			       	 sb1.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])+4]);
			     }
			}
		}else{
			throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
		}
		gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����     
	    sb1.append(" ) a group by A0221 "//��ǰְ���� ����
	     );
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> list1�е����ݺϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list1==null||list1.size()==0||list.size()==0){
			return list;
		}
		String temp="";
		if(list.size()<=list1.size()){
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
					continue;
				}
				temp=(String)list.get(i).get("a0221");
				if(temp.equals((String)list1.get(i).get("a0221"))){
					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
				}else{
					combine_jy(list,list1,temp,i);
				}
			}
		}else{
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
					combine_jy_f(list,list1,temp,i);
				}
			}
		}
		return list;
	}
	
	public void combine_jy_f(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bs", list1.get(i).get("bs"));//��ʿ
				list.get(j).put("ss", list1.get(i).get("ss"));//˶ʿ
				list.get(j).put("xs", list1.get(i).get("xs"));//ѧʿ
			}
		}
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//��ʿ
				list.get(i).put("ss", list1.get(j).get("ss"));//˶ʿ
				list.get(i).put("xs", list1.get(j).get("xs"));//ѧʿ
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
