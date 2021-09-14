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
		"1A",//5行 小计 综合管理类公务员
		"1A01",//国家级正职
		"1A02",//国家级副职
		"1A11",//省部级正职
		"1A12",//省部级副职
		"1A21",//厅局级正职
		"1A22",//厅局级副职
		"1A31",//县处级正职 11行
		"1A32",//县处级副职
		"1A41",//乡科级正职
		"1A42",//乡科级副职
		"1A50",//科员 
		"1A60",//办事员 
		"1A98",//试用期人员
		"1A99",//其他 
		
		"1B",//小计 01B 专业技术类公务员 
		 "1B01",//一级总监     
		 "1B02",//二级总监     
		 "1B03",//一级高级主管
		 "1B04",//二级高级主管
		 "1B05",//三级高级主管
		 "1B06",//四级高级主管
		 "1B07",//一级主管
		 "1B08",//二级主管
		 "1B09",//三级主管
		 "1B10",//四级主管
		 "1B11",//专业技术员
		 "1B98",//试用期人员
		 "1B99",//其他
		 
		 "1C",//行政执法类公务员 小计
		 "1C01",//督办  
		 "1C02",//一级高级主办
		 "1C03",//二级高级主办
		 "1C04",//三级高级主办
		 "1C05",//四级高级主办
		 "1C06",//一级主办
		 "1C07",//二级主办
		 "1C08",//三级主办
		 "1C09",//四级主办
		 "1C10",//一级行政执法员
		 "1C11",//二级行政执法员
		 "1C98",//试用期人员
		 "1C99",//其他 
		 
		 "2",//人民警察职务序列 小计 
		 "20",//一级警长
		 "21",//二级警长
		 "22",//三级警长
		 "23",//四级警长
		 "24",//一级警员
		 "25",//二级警员
		 "26",//三级警员
		 "27",//试用期人员
		 "28",//其他 
		 
		 "3",// 法官等级 小计
		 "301", //首席大法官
		 "302", //一级大法官
		 "303", //二级大法官
		 "304", //一级高级法官
		 "305", //二级高级法官
		 "306", //三级高级法官"
		 "307", //四级高级法官
		 "308", //一级法官
		 "309", //二级法官
		 "310", //三级法官
		 "311", //四级法官
		 "312", //五级法官
		 
		 "4",//检察官等级 小计 行A
		 "401", //首席大检察官
		 "402", //一级大检察官
		 "403", //二级大检察官
		 "404", //一级高级检察官
		 "405", //二级高级检察官
		 "406", //三级高级检察官
		 "407", //四级高级检察官
		 "408", //一级检察官
		 "409", //二级检察官
		 "410", //三级检察官
		 "411", //四级检察官
		 "412", //五级检察官
		 
		 "5",//警务技术等级  小计
		 "501", //警务技术一级总监
		 "502", //警务技术二级总监
		 "503", //警务技术一级主任
		 "504", //警务技术二级主任
		 "505", //警务技术三级主任
		 "506", //警务技术四级主任
		 "507", //警务技术一级主管
		 "508", //警务技术二级主管
		 "509", //警务技术三级主管
		 "510", //警务技术四级主管
		 "511", //警务技术员
		 
		 "6",//执法勤务警员职务等级 小计
		 "601", //一级警务专员
		 "602", //二级警务专员
		 "603", //一级高级警长
		 "604", //二级高级警长
		 "605", //三级高级警长
		 "606", //四级高级警长
		 "607", //一级警长
		 "608", //二级警长
		 "609", //三级警长
		 "610", //四级警长
		 "611", //一级警员
		 "612", //二级警员
		 
		 "71",//深圳市执法员  小计 
		 "7101",//高级执法员
		 "7102",//一级执法员
		 "7103",//二级执法员
		 "7104",//三级执法员
		 "7105",//四级执法员
		 "7106",//五级执法员
		 "7107",//六级执法员
		 "7108",//七级执法员
		 "7109",//助理执法员
		 "7110",//见习执法员
		 
		 "72",//深圳市警员  小计
		 "7201",//一级高级警长
		 "7202",//二级高级警长
		 "7203",//一级警长
		 "7204",//二级警长
		 "7205",//三级警长
		 "7206",//四级警长
		 "7207",//一级警员
		 "7208",//二级警员
		 "7209",//三级警员
		 "7210",//四级警员
		 "7211",//初级警员
		 "7212",//见习警员
		 
		 "74",
		"7401",//气象预报总主任
		"7402",//气象预报高级主任
		"7403",//气象预报主任
		"7404",//气象预报一级主管
		"7405",//气象预报二级主管
		"7406",//气象预报三级主管
		"7407",//气象预报助理
		
		"75",
		"7501",//气象信息高级主任
		"7502",//气象信息主任
		"7503",//气象信息一级主管
		"7504",//气象信息二级主管
		"7505",//气象信息三级主管
		"7506",//气象信息助理
		
		 "73",//深圳警务技术职务  小计
		 "7301",//一级技术警察
		 "7302",//二级技术警察
		 "7303",//三级技术警察
		 "7304",//四级技术警察
		 "7305",//五级技术警察
		 "7306",//六级技术警察
		 "7307",//七级技术警察
		 "7308",//八级技术警察
		 "7309",//九级技术警察
		 "7310",//十级技术警察
		 
		 "9",//事业单位管理等级 小计
		 "901",//一级职员
		 "902",//二级职员
		 "903",//三级职员
		 "904",//四级职员
		 "905",//五级职员
		 "906",//六级职员
		 "907",//七级职员
		 "908",//八级职员
		 "909",//九级职员
		 "910",//十级职员
		 "911",//试用期人员
		 "912",//其他
		 
		 "C",//事业单位专业技术岗位
		 "C01",// 技术一级（正高级）
		 "C02",//技术二级（正高级）
		 "C03",//技术三级（正高级）
		 "C04",//技术四级（正高级）
		 "C05",//技术五级（副高级）
		 "C06",//技术六级（副高级）
		 "C07",//技术七级（副高级）
		 "C08",//技术八级（中级）
		 "C09",//技术九级（中级）
		 "C10",//技术十级（中级）
		 "C11",//技术十一级（助理级）
		 "C12",//技术十二级（助理级）
		 "C13",// 技术十三级（助理级）
		 "C98",// 试用期人员
		 "C99",//其他
		 
		 "D",//机关技术工人岗位
		 "D01",//高级技师
		 "D02",//技师
		 "D03",//高级技术工人
		 "D04",//中级技术工人
		 "D05",//初级技术工人
		 "D09",//学徒工及其他
		 
		 "E",//机关普通工人岗位
		 "E01",//机关普通工人
		 "E09",//其他
		 
		 "F",//事业单位技术工人岗位
		 "F01",//技术工一级（高级技师）
		 "F02",//技术工二级（技师）
		 "F03",//技术工三级（高级工）
		 "F04",//技术工四级（中级工）
		 "F05",//技术工五级（初级工）
		 "F09",//学徒工及其他
		 
		 "G",//事业单位普通工人岗位
		 "G01",//机关普通工人
		 "G09"//其他
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
			String temp=(String)list_user.get(0).get("rate");//人员类别 不可浏览项
			if(temp!=null&&temp.length()>0){
				rylb=temp;
			}
			temp=(String)list_user.get(0).get("empid");//人员类别 不可维护项
			if(temp!=null&&temp.length()>0){
				rylb=rylb+","+temp;
			}
		}
		this.getPageElement("zwlb").setValue("all");
		this.getPageElement("zwlb_l").setValue("all");
		this.getPageElement("tjxm_col").setValue("all");
		//设置复选框选中
		this.getPageElement("xianyin").setValue("1");
		this.getPageElement("xy_zwlb").setValue("1");//隐藏设置复选框选中 标志
		
		//隐藏占比选中
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
		String col="";//行参数
		String row="";//列参数
		String zwlb="";//职务类别
		String sql="";
		sql=arr[0];
		if("0".equals(sql)){//父页面列表为空
			sql="";
		}else{//不为空
			sql=sql_tj_h;
		}
		if(arr.length<=1){//第一个窗口
			
		}else if(arr.length>1){//大于1个窗口
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
			zwlb=zwlb.replace("请您选择...", "");
			zwlb=zwlb.replace("全部", "");
			zwlb=zwlb.replace(" ", "");
			zwlb_arr=zwlb.substring(0, zwlb.length()-1).split("\\,");
		}
		boolean flag=false;
		if(zwlb_arr!=null){
			for(int i=0;i<zwlb_arr.length;i++){
				if(!"".equals(zwlb_arr[i])){
					flag=true;//存在职务类别条件标志
				}
			}
		}
		boolean xueli=true; //默认（及第一次统计），为学历
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
		
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"");
		//拼接sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11_sub(sb,xueli,xuewei);
		
		String where="";
		if(sql==null||"".equals(sql)||sql.length()==0){//列表未查询数据
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			if("system".equals(username)){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
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
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a02.a0000=a01.a0000  "
						);
			}
		}else{//列表查询数据
			String [] arrPersonnelList=sql.split("@@");
			if(arrPersonnelList.length==2){
			}else{
				this.setMainMessage("数据异常!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("tempForCount".equals(arrPersonnelList[0])){//临时表
				String sid = this.request.getSession().getId();
				where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
				sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 ");
			}else if("conditionForCount".equals(arrPersonnelList[0])){//查询条件
				where=sql.substring(sql.indexOf("from A01 a01"), sql.length());
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000";
				sb.append(where);
			}else{
				this.setMainMessage("数据异常!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		//拼接sql where 条件
	    if(row_arr!=null){
	    	//拼接行列条件
	    	  for(int i=0;i<row_arr.length;i++){
	    		  if("208".equals(row_arr[i])){//需要单独写sql
	    			  sb.append(" and a01.a0221 is null ");
	    		  }else{
	    			  new formanalysisSql().sqlPj(row_arr[i], sb);
	    		  }
	    	  }
	    	  //列条件
	    	  if(DBType.ORACLE==DBUtil.getDBType()){
	        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
	            	  if("3".equals(col_arr[i])){//合计
	     	        	 
	     	         }else{
	     	        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(col_arr[i])]);
	     	         }
	              }
		        }else if(DBType.MYSQL==DBUtil.getDBType()){
		        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
		            	  if("3".equals(col_arr[i])){//合计
		     	        	 
		     	         }else{
		     	        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(col_arr[i])]);
		     	         }
		              }
		        }
	    	  //职务类别查询条件
	    	  if(flag==false){//不存在职务类表条件，
	    		  
	    	  }else{//存在职务类别条件
	    		  //拼接查询条件
	    		  String tj_zwlb="";
	    		  for(int i=0;i<zwlb_arr.length;i++){
	    			  if(!"".equals(zwlb_arr[i])){
	    				  if(!"其他".equals(zwlb_arr[i])){//排除其他，其他另写sql
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
	    				  if("其他".equals(zwlb_arr[i])){//其他,不在模板中的职务类别
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
        sb.append(" ) a group by A0221 "//当前职务层次 分组
          );
        System.out.println(sb.toString());
		//分组查询(包含学历)()
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		StringBuffer sb1=new StringBuffer();
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//拼接sqlfrom a08
		xueli=false; 
		xuewei=true;//默认（即第一次统计），为学位
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
		
		//条件  关联条件表
		if(sql==null||"".equals(sql)||sql.length()==0){
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			if("system".equals(username)){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
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
						+ " a01.a0163='1' "//人员管理状态   等 1 现职
						+ " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
						+ " and a02.a0000=a01.a0000  "
						);
			}
		}else{
			String [] arrPersonnelList=sql.split("@@");
			if(arrPersonnelList.length==2){
			}else{
				this.setMainMessage("数据异常!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("tempForCount".equals(arrPersonnelList[0])){//临时表
				String sid = this.request.getSession().getId();
				where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
				sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 ");
			}else if("conditionForCount".equals(arrPersonnelList[0])){//查询条件
				where=sql.substring(sql.indexOf("from A01 a01"), sql.length());
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000";
				sb1.append(where);
			}else{
				this.setMainMessage("数据异常!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		//拼接sql where 条件
	    if(row_arr!=null){
	    	//拼接行列条件
	    	  for(int i=0;i<row_arr.length;i++){
	    		  if("208".equals(row_arr[i])){//需要单独写sql
	    			  sb1.append(" and a01.a0221 is null ");
	    		  }else{
	    			  new formanalysisSql().sqlPj(row_arr[i], sb1);
	    		  }
	    	  }
	    	  //列条件
	    	  if(DBType.ORACLE==DBUtil.getDBType()){
	        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
	            	  if("3".equals(col_arr[i])){//合计
	     	        	 
	     	         }else{
	     	        	 sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(col_arr[i])]);
	     	         }
	              }
		        }else if(DBType.MYSQL==DBUtil.getDBType()){
		        	  for(int i=0;i<col_arr.length;i++){//this.row[Integer.valueOf(row)]
		            	  if("3".equals(col_arr[i])){//合计
		     	        	 
		     	         }else{
		     	        	 sb1.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(col_arr[i])]);
		     	         }
		              }
		        }
	    	//职务类别查询条件
	    	  if(flag==false){//不存在职务类表条件，
	    		  
	    	  }else{//存在职务类别条件
	    		  //拼接查询条件
	    		  String tj_zwlb="";
	    		  for(int i=0;i<zwlb_arr.length;i++){
	    			  if(!"".equals(zwlb_arr[i])){
	    				  if(!"其他".equals(zwlb_arr[i])){//排除其他，其他另写sql
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
	    				  if("其他".equals(zwlb_arr[i])){//其他,不在模板中的职务类别
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
        sb1.append(" ) a group by A0221 "//当前职务层次 分组);
        		);
	         //分组查询 学位 单独统计
        System.out.println(sb1.toString());
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		//list map 数据转换成 jsonString
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> list1中的数据合并到list中
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
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
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
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
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
				list.get(j).put("bs", list1.get(i).get("bs"));//博士
				list.get(j).put("ss", list1.get(i).get("ss"));//硕士
				list.get(j).put("xs", list1.get(i).get("xs"));//学士
			}
		}
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//博士
				list.get(i).put("ss", list1.get(j).get("ss"));//硕士
				list.get(i).put("xs", list1.get(j).get("xs"));//学士
			}
		}
	}
	/**
	 * 隐藏全零行
	 * @return
	 * @throws RadowException 
	 */
//	@PageEvent("xianyin.onclick")
//	public int xianyin() throws RadowException{
//		//设置下拉选不选
//		String xy=this.getPageElement("xianyin").getValue();
//		this.getPageElement("xy_zwlb").setValue(xy);
//		if("1".equals(xy)){//隐藏
//			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
//			//this.getExecuteSG().addExecuteCode("distot()");
//		}else{//显示
//			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * 隐藏占比
	 * @throws RadowException 
	 */
//	@PageEvent("yczb.onclick")
//	public int yczb() throws RadowException{
//		String xy=this.getPageElement("yczb").getValue();
//		if("1".equals(xy)){//隐藏
//			this.getExecuteSG().addExecuteCode("yincangzb()");
//		}else{//显示
//			this.getExecuteSG().addExecuteCode("xszhanbi()");
//		}
//		
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * 职务类别下拉选 复选
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		//设置复选框不选中
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
	 * 统计项目下拉选 复选
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("tjxm_col.onchange")
	public int tjxm() throws RadowException{
		//设置复选框不选中
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
