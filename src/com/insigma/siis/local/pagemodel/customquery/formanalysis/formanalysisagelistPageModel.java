package com.insigma.siis.local.pagemodel.customquery.formanalysis;

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
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkSqlPj;

public class formanalysisagelistPageModel extends PageModel{

	public formanalysisagelistPageModel() {
		
	}
	
	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		CommQuery cq=new CommQuery();
		List<HashMap<String, Object>> list_user=cq.getListBySQL(" select rate,empid from smt_user t where t.userid = '"+userid+"' ");
		String rylb="";
		if(list_user!=null&&list_user.size()>0){
			String temp=(String)list_user.get(0).get("rate");//��Ա��� ���������
			if(temp!=null&&temp.length()>0){
				rylb=temp;
			}
			temp=(String)list_user.get(0).get("empid");//��Ա��� ����ά����
			if(temp!=null&&temp.length()>0){
				rylb=rylb+","+temp;
			}
		}
		String sql_tj_h=(String) this.request.getSession().getAttribute("ry_tj_zy");
		
		String param=this.getPageElement("subWinIdBussessId2").getValue();
		if(param==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String arr[]=param.split("\\$");
		if(arr==null||arr.length==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String col="";//�в���
		String row="";//�в���
		String zwlb="";//ְ�����
		String sql="";
		sql=arr[0];
		if("0".equals(sql)){//��ҳ���б�Ϊ��
			sql="";
		}else{//��Ϊ��
			sql=sql_tj_h;
		}
		if(arr.length<=1){//��һ������
			
		}else if(arr.length>1){//����1������
			for(int i=0;i<(arr.length-1)/4;i++){
					zwlb=zwlb+arr[1+i*4]+",";
					col=col+arr[2+i*4]+",";
					row=row+arr[3+i*4]+",";
			}
		}
		String col_arr[]=null;
		String row_arr[]=null;
		String zwlb_arr[]=null;
		if(!"".equals(row)){
			col_arr=col.substring(0, col.length()-1).split("\\,");
			row_arr=row.substring(0, row.length()-1).split("\\,");
			zwlb=zwlb.replace("����ѡ��...", "");
			zwlb=zwlb.replace("ȫ��", "");
			zwlb=zwlb.replace(" ", "");
			zwlb_arr=zwlb.substring(0, zwlb.length()-1).split("\\,");
		}
		boolean flag=false;
		if(zwlb_arr!=null){
			for(int i=0;i<zwlb_arr.length;i++){
				if(!"".equals(zwlb_arr[i])){
					flag=true;//����ְ�����������־
				}
			}
		}
		
		
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//ƴ��sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		//ƴ��sql from 
		sb.append(" "
	          +" FROM A01 a01 "//��Ա������Ϣ��
				);
		
       String where="";
       if(sql==null||"".equals(sql)||sql.length()==0){
    	   String username=SysUtil.getCacheCurrentUser().getLoginname();
			if("system".equals(username)){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "//
                   + " group by a02.a0000"
                   + " ) a02 where "
                   + " a01.a0163='1' "//��Ա����״̬   ��
                   + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                   + " and a02.a0000=a01.a0000  "
						);
			}else{
				sb.append(" ,(select  a02.a0000 "
						+ " from a02, competence_userdept cu "
						+ " where "
						+ " a02.a0255 = '1' "//����ְ���־
						+ " and a02.A0201B = cu.b0111 "
						+ " and cu.userid = '"+userid+"' "
						+ " group by a02.a0000"
						+ " ) a02 where a01.a0163='1' "//��Ա����״̬   ��
						+ " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
						+ "  and a02.a0000=a01.a0000  "
						);
			}
		}else{
			String [] arrPersonnelList=sql.split("@@");
			if(arrPersonnelList.length==2){
			}else{
				this.setMainMessage("�����쳣!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("tempForCount".equals(arrPersonnelList[0])){//��ʱ��
//				where=sql.substring(sql.indexOf("where"), sql.length());
//				if(where.indexOf("order by")==-1){
//				}else{
//					where = where .substring(0, where.lastIndexOf("order by"));
//				}
//				where = where.substring(19, where.length());
				String sid = this.request.getSession().getId();
				where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
				sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 ");
			}else if("conditionForCount".equals(arrPersonnelList[0])){//��ѯ����
//				where=sql.substring(sql.indexOf("where"), sql.length());
//				where = ",( select a0000 from a01 a01 "+where + " )  a02 where a02.a0000=a01.a0000";
				where=sql.substring(sql.indexOf("from A01 a01"), sql.length());
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000";
				sb.append(where);
			}else{
				this.setMainMessage("�����쳣!");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
       
     //ƴ��sql where ����
	    if(row_arr!=null){
	    	//ƴ����������
	    	  for(int i=0;i<row_arr.length;i++){
	    		  if("208".equals(row_arr[i])){//��Ҫ����дsql
	    			  sb.append(" and (a01.a0221 is null or a01.a0221='') ");
	    		  }else{
	    			  new formanalysisSql().sqlPj(row_arr[i], sb);
	    		  }
	    	  }
	    	  if(DBType.ORACLE==DBUtil.getDBType()){
	          	for(int i=0;i<col_arr.length;i++){
	              	if("".equals(col_arr[i])||col_arr[i]==null){
	              		throw new RadowException("�����쳣��");
	              	}
	              	if(Integer.parseInt(col_arr[i])<3){//��
	              		sb.append(" and 1=0 ");
	      	         }else if(Integer.parseInt(col_arr[i])==3){//�ϼ�
	      	        	 //����������
	      	         }else if(Integer.parseInt(col_arr[i])==4){//С�ڵ��� 20
	      	        	 sb.append(" and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(add_months(sysdate,-264),'yyyy-mm')),'yyyy-mm'))/12)<=20 ");
	      	         }else if(Integer.parseInt(col_arr[i])>=5&&Integer.parseInt(col_arr[i])<=48){
	      	        	 sb.append(" and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(sysdate,'yyyy-mm')),'yyyy-mm'))/12)="+(Integer.parseInt(col_arr[i])+16)+" ");
	      	         }else if(Integer.parseInt(col_arr[i])==49){//���ڵ���60
	      	        	 sb.append(" and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(sysdate,'yyyy-mm')),'yyyy-mm'))/12)>=65 ");
	      	         } else{///��
	      	        	 sb.append(" and 1=0 ");
	      	         }
	              }
	  		}else if(DBType.MYSQL==DBUtil.getDBType()){
	  			for(int i=0;i<col_arr.length;i++){
	  	        	if("".equals(col_arr[i])||col_arr[i]==null){
	  	        		throw new RadowException("�����쳣��");
	  	        	}
	  	        	if(Integer.parseInt(col_arr[i])<3){//��
	  	        		sb.append(" and 1=0 ");
	  		         }else if(Integer.parseInt(col_arr[i])==3){//�ϼ�
	  		        	 //����������
	  		         }else if(Integer.parseInt(col_arr[i])==4){//С�ڵ��� 20
	  		        	 sb.append(" and floor(timestampdiff(month,if(a01.a0107 is null or a01.a0107='',DATE_FORMAT(date_sub(curdate(),INTERVAL 264 month),'%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)),DATE_FORMAT(CURDATE(),'%y%m%d'))/12)<=20 ");
	  		         }else if(Integer.parseInt(col_arr[i])>=5&&Integer.parseInt(col_arr[i])<=48){
	  		        	 sb.append(" and floor(timestampdiff(month,if(a01.a0107 is null or a01.a0107='',DATE_FORMAT(curdate(),'yyyy-mm'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)),DATE_FORMAT(CURDATE(),'%y%m%d'))/12)="+(Integer.parseInt(col_arr[i])+16)+" ");
	  		         }else if(Integer.parseInt(col_arr[i])==49){//���ڵ���60
	  		        	 sb.append(" and floor(timestampdiff(month,if(a01.a0107 is null or a01.a0107='',DATE_FORMAT(curdate(),'yyyy-mm'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)),DATE_FORMAT(CURDATE(),'%y%m%d'))/12)>=65 ");
	  		         } else{///��
	  		        	 sb.append(" and 1=0 ");
	  		         }
	  	        }
	  		}else{
	  			throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
	  		}
	    	  //ְ������ѯ����
	    	  if(flag==false){//������ְ�����������
	    		  
	    	  }else{//����ְ���������
	    		  //ƴ�Ӳ�ѯ����
	    		  String tj_zwlb="";
	    		  for(int i=0;i<zwlb_arr.length;i++){
	    			  if(!"".equals(zwlb_arr[i])){
	    				  if(!"����".equals(zwlb_arr[i])){//�ų�������������дsql
	    					  tj_zwlb=tj_zwlb+zwlb_arr[i]+",";
	    				  }
	    			  }
	    		  }
	    		  if(!"".equals(tj_zwlb)){
	    			  tj_zwlb=tj_zwlb.substring(0, tj_zwlb.length()-1);
	    			  tj_zwlb=tj_zwlb.replace(",", "','");
	    			  tj_zwlb="'"+tj_zwlb+"'";
	    			  sb.append(" and a01.a0221 in"
	    					  + " ( select code_value from code_value s where s.sub_code_value "
	    					  + " in( select t.code_value from code_value t where t.code_name in ( " 
	    					  + tj_zwlb
	    					  + " ) and t.code_type='ZB09' and t.code_status='1'"
	    					  + " ) "
	    					  + " )");
	    		  }
	    		  for(int i=0;i<zwlb_arr.length;i++){
	    			  if(!"".equals(zwlb_arr[i])){
	    				  if("����".equals(zwlb_arr[i])){//����,����ģ���е�ְ�����
	    					  sb.append(" and (a01.a0221 is null or a01.a0221='' ) ");
	    					  break;
	    				  }
	    			  }
	    		  }
	    		
	    	  }	
	    }
	    if(rylb!=null&&rylb.indexOf("\'")!=-1){
	    	sb.append(" and a01.A0165 not in ( "+rylb+") ");
		}
	    sb.append(" order by a01.a0221 asc ");
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

}
