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
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkSqlZs;

public class formanalysis_zhPageModel extends PageModel{
	public final static String row[]={" 1=0 ",
		" 1=0 ",//1
		" 1=0 ",//2
		"",//3
		"",
		"1A",//5�� С�� �ۺϹ����๫��Ա
		"1A01",//���Ҽ���ְ
		"1A02",//���Ҽ���ְ
		"1A11",//ʡ������ְ
		"1A12",//ʡ������ְ
		"1A21",//���ּ���ְ
		"1A22",//���ּ���ְ
		"1A31",//�ش�����ְ 11��
		"1A32",//�ش�����ְ
		"1A41",//��Ƽ���ְ
		"1A42",//��Ƽ���ְ
		"1A50",//��Ա 
		"1A60",//����Ա 
		"1A98",//��������Ա
		"1A99",//���� 
		
		"1B",//С�� 01B רҵ�����๫��Ա 
		 "1B01",//һ���ܼ�     
		 "1B02",//�����ܼ�     
		 "1B03",//һ���߼�����
		 "1B04",//�����߼�����
		 "1B05",//�����߼�����
		 "1B06",//�ļ��߼�����
		 "1B07",//һ������
		 "1B08",//��������
		 "1B09",//��������
		 "1B10",//�ļ�����
		 "1B11",//רҵ����Ա
		 "1B98",//��������Ա
		 "1B99",//����
		 
		 "1C",//����ִ���๫��Ա С��
		 "1C01",//����  
		 "1C02",//һ���߼�����
		 "1C03",//�����߼�����
		 "1C04",//�����߼�����
		 "1C05",//�ļ��߼�����
		 "1C06",//һ������
		 "1C07",//��������
		 "1C08",//��������
		 "1C09",//�ļ�����
		 "1C10",//һ������ִ��Ա
		 "1C11",//��������ִ��Ա
		 "1C98",//��������Ա
		 "1C99",//���� 
		 
		 "2",//���񾯲�ְ������ С�� 
		 "20",//һ������
		 "21",//��������
		 "22",//��������
		 "23",//�ļ�����
		 "24",//һ����Ա
		 "25",//������Ա
		 "26",//������Ա
		 "27",//��������Ա
		 "28",//���� 
		 
		 "3",// ���ٵȼ� С��
		 "301", //��ϯ�󷨹�
		 "302", //һ���󷨹�
		 "303", //�����󷨹�
		 "304", //һ���߼�����
		 "305", //�����߼�����
		 "306", //�����߼�����"
		 "307", //�ļ��߼�����
		 "308", //һ������
		 "309", //��������
		 "310", //��������
		 "311", //�ļ�����
		 "312", //�弶����
		 
		 "4",//���ٵȼ� С�� ��A
		 "401", //��ϯ�����
		 "402", //һ�������
		 "403", //���������
		 "404", //һ���߼�����
		 "405", //�����߼�����
		 "406", //�����߼�����
		 "407", //�ļ��߼�����
		 "408", //һ������
		 "409", //��������
		 "410", //��������
		 "411", //�ļ�����
		 "412", //�弶����
		 
		 "5",//�������ȼ�  С��
		 "501", //������һ���ܼ�
		 "502", //�����������ܼ�
		 "503", //������һ������
		 "504", //��������������
		 "505", //��������������
		 "506", //�������ļ�����
		 "507", //������һ������
		 "508", //��������������
		 "509", //��������������
		 "510", //�������ļ�����
		 "511", //������Ա
		 
		 "6",//ִ������Աְ��ȼ� С��
		 "601", //һ������רԱ
		 "602", //��������רԱ
		 "603", //һ���߼�����
		 "604", //�����߼�����
		 "605", //�����߼�����
		 "606", //�ļ��߼�����
		 "607", //һ������
		 "608", //��������
		 "609", //��������
		 "610", //�ļ�����
		 "611", //һ����Ա
		 "612", //������Ա
		 
		 "71",//������ִ��Ա  С�� 
		 "7101",//�߼�ִ��Ա
		 "7102",//һ��ִ��Ա
		 "7103",//����ִ��Ա
		 "7104",//����ִ��Ա
		 "7105",//�ļ�ִ��Ա
		 "7106",//�弶ִ��Ա
		 "7107",//����ִ��Ա
		 "7108",//�߼�ִ��Ա
		 "7109",//����ִ��Ա
		 "7110",//��ϰִ��Ա
		 
		 "72",//�����о�Ա  С��
		 "7201",//һ���߼�����
		 "7202",//�����߼�����
		 "7203",//һ������
		 "7204",//��������
		 "7205",//��������
		 "7206",//�ļ�����
		 "7207",//һ����Ա
		 "7208",//������Ա
		 "7209",//������Ա
		 "7210",//�ļ���Ա
		 "7211",//������Ա
		 "7212",//��ϰ��Ա
		 
		 "74",
		"7401",//����Ԥ��������
		"7402",//����Ԥ���߼�����
		"7403",//����Ԥ������
		"7404",//����Ԥ��һ������
		"7405",//����Ԥ����������
		"7406",//����Ԥ����������
		"7407",//����Ԥ������
		
		"75",
		"7501",//������Ϣ�߼�����
		"7502",//������Ϣ����
		"7503",//������Ϣһ������
		"7504",//������Ϣ��������
		"7505",//������Ϣ��������
		"7506",//������Ϣ����
		
		 "73",//���ھ�����ְ��  С��
		 "7301",//һ����������
		 "7302",//������������
		 "7303",//������������
		 "7304",//�ļ���������
		 "7305",//�弶��������
		 "7306",//������������
		 "7307",//�߼���������
		 "7308",//�˼���������
		 "7309",//�ż���������
		 "7310",//ʮ����������
		 
		 "9",//��ҵ��λ����ȼ� С��
		 "901",//һ��ְԱ
		 "902",//����ְԱ
		 "903",//����ְԱ
		 "904",//�ļ�ְԱ
		 "905",//�弶ְԱ
		 "906",//����ְԱ
		 "907",//�߼�ְԱ
		 "908",//�˼�ְԱ
		 "909",//�ż�ְԱ
		 "910",//ʮ��ְԱ
		 "911",//��������Ա
		 "912",//����
		 
		 "C",//��ҵ��λרҵ������λ
		 "C01",// ����һ�������߼���
		 "C02",//�������������߼���
		 "C03",//�������������߼���
		 "C04",//�����ļ������߼���
		 "C05",//�����弶�����߼���
		 "C06",//�������������߼���
		 "C07",//�����߼������߼���
		 "C08",//�����˼����м���
		 "C09",//�����ż����м���
		 "C10",//����ʮ�����м���
		 "C11",//����ʮһ����������
		 "C12",//����ʮ������������
		 "C13",// ����ʮ������������
		 "C98",// ��������Ա
		 "C99",//����
		 
		 "D",//���ؼ������˸�λ
		 "D01",//�߼���ʦ
		 "D02",//��ʦ
		 "D03",//�߼���������
		 "D04",//�м���������
		 "D05",//������������
		 "D09",//ѧͽ��������
		 
		 "E",//������ͨ���˸�λ
		 "E01",//������ͨ����
		 "E09",//����
		 
		 "F",//��ҵ��λ�������˸�λ
		 "F01",//������һ�����߼���ʦ��
		 "F02",//��������������ʦ��
		 "F03",//�������������߼�����
		 "F04",//�������ļ����м�����
		 "F05",//�������弶����������
		 "F09",//ѧͽ��������
		 
		 "G",//��ҵ��λ��ͨ���˸�λ
		 "G01",//������ͨ����
		 "G09"//����
	};
	public final static String xioaji=",5,20,34,48,58,71,84,96,109,120,133,141,148,159,172,188,195,198,205,";
	public formanalysis_zhPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("init")
	public int init() throws RadowException, AppException{
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
		this.getPageElement("zwlb").setValue("all");
		this.getPageElement("zwlb_l").setValue("all");
		this.getPageElement("tjxm_col").setValue("all");
		//���ø�ѡ��ѡ��
		this.getPageElement("xianyin").setValue("1");
		this.getPageElement("xy_zwlb").setValue("1");//�������ø�ѡ��ѡ�� ��־
		
		//����ռ��ѡ��
		this.getPageElement("yczb").setValue("1");
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
		boolean xueli=true; //Ĭ�ϣ�����һ��ͳ�ƣ���Ϊѧ��
		boolean xuewei=false;
		if(col_arr!=null){
			for(int i=0;i<col_arr.length;i++){
				if(Integer.parseInt( col_arr[i])>=12&&Integer.parseInt( col_arr[i])<=21){
					xueli=true;
				}else if(Integer.parseInt( col_arr[i])>=22&&Integer.parseInt( col_arr[i])<=27){
					xuewei=true;
				}
			}
		}
		
		
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"");
		//ƴ��sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11_sub(sb,xueli,xuewei);
		
		String where="";
		if(sql==null||"".equals(sql)||sql.length()==0){//�б�δ��ѯ����
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			if("system".equals(username)){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
						);
			}else{
				sb.append(" ,(select  a02.a0000 "
						+ " from a02, competence_userdept cu "
						+ " where "
						+ " a02.a0255 = '1' "
						
                     + " and a02.A0201B = cu.b0111 "
                     + " and cu.userid = '"+userid+"' "
                     + " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
						);
			}
		}else{//�б��ѯ����
			String [] arrPersonnelList=sql.split("@@");
			if(arrPersonnelList.length==2){
			}else{
				this.setMainMessage("�����쳣!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("tempForCount".equals(arrPersonnelList[0])){//��ʱ��
				String sid = this.request.getSession().getId();
				where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
				sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 ");
			}else if("conditionForCount".equals(arrPersonnelList[0])){//��ѯ����
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
	    			  sb.append(" and a01.a0221 is null ");
	    		  }else{
	    			  new formanalysisSql().sqlPj(row_arr[i], sb);
	    		  }
	    	  }
	    	  //������
	    	  if(DBType.ORACLE==DBUtil.getDBType()){
	        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
	            	  if("3".equals(col_arr[i])){//�ϼ�
	     	        	 
	     	         }else{
	     	        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(col_arr[i])]);
	     	         }
	              }
		        }else if(DBType.MYSQL==DBUtil.getDBType()){
		        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
		            	  if("3".equals(col_arr[i])){//�ϼ�
		     	        	 
		     	         }else{
		     	        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(col_arr[i])]);
		     	         }
		              }
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
	    					  sb.append(" and  a01.a0221 is null ) ");
	    					  break;
	    				  }
	    			  }
	    		  }
	    		
	    	  }
	    }
	    if(rylb!=null&&rylb.indexOf("\'")!=-1){
	    	sb.append(" and a01.A0165 not in ( "+rylb+") ");
	    }
        sb.append(" ) a group by A0221 "//��ǰְ���� ����
          );
        System.out.println(sb.toString());
		//�����ѯ(����ѧ��)()
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		StringBuffer sb1=new StringBuffer();
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//ƴ��sqlfrom a08
		xueli=false; 
		xuewei=true;//Ĭ�ϣ�����һ��ͳ�ƣ���Ϊѧλ
		if(col_arr!=null){
			for(int i=0;i<col_arr.length;i++){
				if(Integer.parseInt( col_arr[i])>=12&&Integer.parseInt( col_arr[i])<=21){
					xueli=true;
				}else if(Integer.parseInt( col_arr[i])>=22&&Integer.parseInt( col_arr[i])<=27){
					xuewei=true;
				}
			}
		}
		gbjbqksqlzs.returnGbjbqkSqlZs21_sub(sb1,xueli,xuewei);
		
		//����  ����������
		if(sql==null||"".equals(sql)||sql.length()==0){
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			if("system".equals(username)){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
						);
			}else{
				sb1.append(" ,(select  a02.a0000 "
						+ " from a02, competence_userdept cu "
						+ " where a02.a0255 = '1' "
						+ " and a02.a0281='true' "
						+ " and a02.A0201B = cu.b0111 "
						+ " and cu.userid = '"+userid+"' "
						+ " group by a02.a0000"
						+ " ) a02 where "
						+ " a01.a0163='1' "//��Ա����״̬   �� 1 ��ְ
						+ " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
						+ " and a02.a0000=a01.a0000  "
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
				String sid = this.request.getSession().getId();
				where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
				sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 ");
			}else if("conditionForCount".equals(arrPersonnelList[0])){//��ѯ����
				where=sql.substring(sql.indexOf("from A01 a01"), sql.length());
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000";
				sb1.append(where);
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
	    			  sb1.append(" and a01.a0221 is null ");
	    		  }else{
	    			  new formanalysisSql().sqlPj(row_arr[i], sb1);
	    		  }
	    	  }
	    	  //������
	    	  if(DBType.ORACLE==DBUtil.getDBType()){
	        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
	            	  if("3".equals(col_arr[i])){//�ϼ�
	     	        	 
	     	         }else{
	     	        	 sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(col_arr[i])]);
	     	         }
	              }
		        }else if(DBType.MYSQL==DBUtil.getDBType()){
		        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
		            	  if("3".equals(col_arr[i])){//�ϼ�
		     	        	 
		     	         }else{
		     	        	 sb1.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(col_arr[i])]);
		     	         }
		              }
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
	    			  sb1.append(" and a01.a0221 in"
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
	    					  sb1.append(" and a01.a0221 is null ");
	    					  break;
	    				  }
	    			  }
	    		  }
	    		
	    	  }
	    }
	    if(rylb!=null&&rylb.indexOf("\\'")!=-1){
	    	sb1.append(" and a01.A0165 not in ( "+rylb+") ");
	    }
        sb1.append(" ) a group by A0221 "//��ǰְ���� ����);
        		);
	         //�����ѯ ѧλ ����ͳ��
        System.out.println(sb1.toString());
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		//list map ����ת���� jsonString
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
//	@PageEvent("xianyin.onclick")
//	public int xianyin() throws RadowException{
//		//��������ѡ��ѡ
//		String xy=this.getPageElement("xianyin").getValue();
//		this.getPageElement("xy_zwlb").setValue(xy);
//		if("1".equals(xy)){//����
//			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
//			//this.getExecuteSG().addExecuteCode("distot()");
//		}else{//��ʾ
//			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * ����ռ��
	 * @throws RadowException 
	 */
//	@PageEvent("yczb.onclick")
//	public int yczb() throws RadowException{
//		String xy=this.getPageElement("yczb").getValue();
//		if("1".equals(xy)){//����
//			this.getExecuteSG().addExecuteCode("yincangzb()");
//		}else{//��ʾ
//			this.getExecuteSG().addExecuteCode("xszhanbi()");
//		}
//		
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * ְ���������ѡ ��ѡ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		//���ø�ѡ��ѡ��
		//this.getPageElement("xianyin").setValue("0");
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		//String[] arr=zwlb_l.split(",");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ͳ����Ŀ����ѡ ��ѡ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("tjxm_col.onchange")
	public int tjxm() throws RadowException{
		//���ø�ѡ��ѡ��
		//this.getPageElement("xianyin").setValue("0");
		String tjxm_h=this.getPageElement("tjxm_col").getValue();
		if(tjxm_h==null||"".equals(tjxm_h)||tjxm_h.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("tjxm_col_h").setValue(tjxm_h);
		this.getExecuteSG().addExecuteCode("tjxm_xy()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("tongji.onclick")
	public int tongji(){
		this.setNextEventName("init");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
