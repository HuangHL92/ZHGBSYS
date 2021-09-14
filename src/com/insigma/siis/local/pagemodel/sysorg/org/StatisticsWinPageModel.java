package com.insigma.siis.local.pagemodel.sysorg.org;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.sysorg.org.StatisticsWinBS;


public class StatisticsWinPageModel extends PageModel {
	
	
	public StatisticsWinPageModel() {
		
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		String groupid = this.getPageElement("subWinIdBussessId2").getValue();
		String jigouName = StatisticsWinBS.queryName(groupid);
		this.getPageElement("jigou").setValue(jigouName);
		this.setNextEventName("query");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//实时查询机构数据并存入或更新
	@PageEvent("create.onclick")
	public int create() throws RadowException{
//		this.getExecuteSG().addExecuteCode("openDiv()");
		String time;
		boolean live;
		boolean live2;
		if(DBUtil.getDBType()==DBType.ORACLE){
			time="sysdate";
		}else{
			time="current_timestamp";
		}	
			String groupid = this.getPageElement("subWinIdBussessId2").getValue();
			live = StatisticsWinBS.live(groupid);
			live2= StatisticsWinBS.live2(groupid);
			Integer nv=null;
			Integer sm=null;
			Integer zgdy=null;
			Integer zrstj01=null;
			Integer yjs=null;
			Integer db=null;
			Integer dz=null;
			Integer zz=null;
			Integer gz=null;
			Integer wtx=null;
			Integer zrstj03=null;
			try{
				Object[] tj01 = StatisticsWinBS.doQuerySS(groupid);
				nv= Integer.valueOf(tj01[0].toString());
				sm= Integer.valueOf(tj01[1].toString());
				zgdy=Integer.valueOf(tj01[2].toString());
				zrstj01= Integer.valueOf(tj01[3].toString());
				
				Object[] tj03 = StatisticsWinBS.doQuerySE(groupid);
				yjs= Integer.valueOf(tj03[0].toString());
				db= Integer.valueOf(tj03[1].toString());
				dz= Integer.valueOf(tj03[2].toString());
				zz= Integer.valueOf(tj03[3].toString());
				gz= Integer.valueOf(tj03[4].toString());
				zrstj03= Integer.valueOf(tj03[6].toString());
				wtx=Integer.valueOf(tj03[5].toString());
			}catch(Exception e){
				this.setMainMessage("该机构人数为空!");
				return EventRtnType.NORMAL_SUCCESS;
			}		
			if(live){
				StatisticsWinBS.updateSS(nv,sm,zgdy,zrstj01,time,groupid);		
			}else{
				StatisticsWinBS.insertSS(nv,sm,zgdy,zrstj01,time,groupid);
				
			}
			if(live2){
				StatisticsWinBS.updateSE(yjs,db,dz,zz,gz,wtx,zrstj03,time,groupid);
			}else{
				StatisticsWinBS.insertSE(yjs,db,dz,zz,gz,wtx,zrstj03,time,groupid);
			}
		this.setNextEventName("update");
		this.getPageElement("dsq").setValue("1");
		this.getPageElement("biaozhi").setValue("1");
		this.setNextEventName("query");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//查询数据显示在页面上
	@PageEvent("query")
	public int doquery() throws RadowException{
		String biaozhi=null;
		Integer nv=null;
		String nvp=null;
		Integer sm=null;
		String smp=null;
		Integer zgdy=null;
		String zgdyp=null;
		Integer zrs=null;
		Integer yjs=null;
		String yjsp=null;
		Integer db=null;
		String dbp=null;
		Integer dz=null;
		String dzp=null;
		Integer zz=null;
		String zzp=null;
		Integer gz=null;
		String gzp=null;
		Integer wtx=null;
		String wtxp=null;
		boolean live;
			String groupid = this.getPageElement("subWinIdBussessId2").getValue();
			biaozhi = this.getPageElement("biaozhi").getValue();
			live = StatisticsWinBS.live(groupid); 
			if(live){
				zrs = StatisticsWinBS.selectZrsCount(groupid);
				this.getPageElement("a1").setValue(""+zrs);
				
				nv = StatisticsWinBS.selectNvCount(groupid);
				this.getPageElement("a2").setValue(""+nv);
				nvp = StatisticsWinBS.percent(nv, zrs);
				this.getPageElement("a3").setValue(nvp+"%");
				
				sm = StatisticsWinBS.selectSmCount(groupid);
				this.getPageElement("a4").setValue(""+sm);
				smp = StatisticsWinBS.percent(sm, zrs);
				this.getPageElement("a5").setValue(smp+"%");
				
				zgdy = StatisticsWinBS.selectZgdyCount(groupid);
				this.getPageElement("a6").setValue(""+zgdy);
				zgdyp = StatisticsWinBS.percent(zgdy, zrs);
				this.getPageElement("a7").setValue(zgdyp+"%");
				
				yjs = StatisticsWinBS.selectYjsCount(groupid);
				this.getPageElement("c2").setValue(""+yjs);
				yjsp = StatisticsWinBS.percent(yjs, zrs);
				this.getPageElement("c3").setValue(yjsp+"%");
				
				db = StatisticsWinBS.selectDbCount(groupid);
				this.getPageElement("c4").setValue(""+db);
				dbp = StatisticsWinBS.percent(db, zrs);
				this.getPageElement("c5").setValue(dbp+"%");
				
				dz = StatisticsWinBS.selectDzCount(groupid);
				this.getPageElement("c6").setValue(""+dz);
				dzp = StatisticsWinBS.percent(dz, zrs);
				this.getPageElement("c7").setValue(dzp+"%");
				
				zz = StatisticsWinBS.selectZzCount(groupid);
				this.getPageElement("c8").setValue(""+zz);
				zzp = StatisticsWinBS.percent(zz, zrs);
				this.getPageElement("c9").setValue(zzp+"%");
				
				gz = StatisticsWinBS.selectGzCount(groupid);
				this.getPageElement("c10").setValue(""+gz);
				gzp = StatisticsWinBS.percent(gz, zrs);
				this.getPageElement("c11").setValue(gzp+"%");
				
				wtx=StatisticsWinBS.selectWtxCount(groupid);
				this.getPageElement("c12").setValue(""+wtx);
				wtxp = StatisticsWinBS.percent(wtx, zrs);
				this.getPageElement("c13").setValue(wtxp+"%");
				
				
			}else{
				this.getPageElement("a1").setValue("");
				this.getPageElement("a2").setValue("");
				this.getPageElement("a3").setValue("");
				this.getPageElement("a4").setValue("");
				this.getPageElement("a5").setValue("");
				this.getPageElement("a6").setValue("");
				this.getPageElement("a7").setValue("");
				
				this.getPageElement("c2").setValue("");
				this.getPageElement("c3").setValue("");
				this.getPageElement("c4").setValue("");
				this.getPageElement("c5").setValue("");
				this.getPageElement("c6").setValue("");
				this.getPageElement("c7").setValue("");
				this.getPageElement("c8").setValue("");
				this.getPageElement("c9").setValue("");
				this.getPageElement("c10").setValue("");
				this.getPageElement("c11").setValue("");
				this.getPageElement("c12").setValue("");
				this.getPageElement("c13").setValue("");
			}
						
		String option = "{"
				+"title : {"
					+"text : '性别、民族、政治情况分析',"
					+"x:'center',"
				       +"y:'top',"
				       +"textAlign:'left'"
				+"},"
				+"tooltip : {},"
				+"xAxis : {"
					+"data : [ '女', '少数民族', '中共党员'],"
					+"'axisLabel':{"  
						+"interval: 0"  
					+"}" 
				+"},"	
				+"yAxis : {},"
				+"series : [ {"
					+"name : '人数',"
					+"type : 'bar',"
					+"label : {"
						+"normal : {"
							+"show : true,"
							+"position : 'top'"
						+"}"
					+"},"
					+"data : [ "+nv+","+sm+","+zgdy+" ]"
				+"} ],"
				+"animation: false "
			+"}";
		String option3 = "{"
					+"color: ['#d14a61'],"
					+"title : {"
						+"text : '最高学历情况分析',"
						+"x:'center',"
				        +"y:'top',"
				        +"textAlign:'left'"
					+"},"
					+"tooltip : {},"
					+"xAxis : {"
						+"data : [ '研究生', '大学本科', '大学专科', '中专', '高中及以下'],"
						+"'axisLabel':{"  
							+"interval: 0"
						+"}" 
					+"},"
					+"yAxis : {},"
					+"series : [ {"
						+"name : '人数',"
						+"type : 'bar',"
						+"label : {"
							+"normal : {"
								+"show : true,"
								+"position : 'top'"
							+"}"
						+"},"
						+"data : [ "+yjs+","+db+","+dz+","+zz+","+gz+" ]"
					+"} ],"
					   +"animation: false "
				+"}";
		String agesql="select SA002,SA003,SA004,SA005,SA006,SA007,SA008,SA009,totalnumber from statistics_age where b0111='"+groupid+"'"; 
		Object[] ageObject=(Object[])HBUtil.getHBSession().createSQLQuery(agesql).uniqueResult();
		String postsql="select SH002,SH003,SH004,SH005,SH006,SH007,SH008,SH009,SH010,SH011,SH012,SH013,SH014,SH015,SH016 from statistics_highestpostlevel where b0111='"+groupid+"'"; 
		Object[] postObject=(Object[])HBUtil.getHBSession().createSQLQuery(postsql).uniqueResult();
		Integer SA002=null; Integer SA003=null; Integer SA004=null; Integer SA005=null; Integer SA006=null; Integer SA007=null; Integer SA008=null; Integer SA009=null;
		Integer SH002=null; Integer SH003=null; Integer SH004=null; Integer SH005=null; Integer SH006=null; Integer SH007=null; Integer SH008=null; Integer SH009=null;
		Integer SH010=null; Integer SH011=null; Integer SH012=null; Integer SH013=null; Integer SH014=null; Integer SH015=null; Integer SH016=null; Integer totalnumber=null;
		if(ageObject!=null&&postObject!=null){
			 SA002=Integer.valueOf(ageObject[0].toString());
			 SA003=Integer.valueOf(ageObject[1].toString());
			 SA004=Integer.valueOf(ageObject[2].toString());
			 SA005=Integer.valueOf(ageObject[3].toString());
			 SA006=Integer.valueOf(ageObject[4].toString());
			 SA007=Integer.valueOf(ageObject[5].toString());
			 SA008=Integer.valueOf(ageObject[6].toString());
			 SA009=Integer.valueOf(ageObject[7].toString());
			 totalnumber=Integer.valueOf(ageObject[8].toString());
			this.getPageElement("b2").setValue(""+SA002);
			this.getPageElement("b3").setValue(StatisticsWinBS.percent(SA002, totalnumber)+"%");
			this.getPageElement("b4").setValue(""+SA003);
			this.getPageElement("b5").setValue(StatisticsWinBS.percent(SA003, totalnumber)+"%");
			this.getPageElement("b6").setValue(""+SA004);
			this.getPageElement("b7").setValue(StatisticsWinBS.percent(SA004, totalnumber)+"%");
			this.getPageElement("b8").setValue(""+SA005);
			this.getPageElement("b9").setValue(StatisticsWinBS.percent(SA005, totalnumber)+"%");
			this.getPageElement("b10").setValue(""+SA006);
			this.getPageElement("b11").setValue(StatisticsWinBS.percent(SA006, totalnumber)+"%");
			this.getPageElement("b12").setValue(""+SA007);
			this.getPageElement("b13").setValue(StatisticsWinBS.percent(SA007, totalnumber)+"%");
			this.getPageElement("b14").setValue(""+SA008);
			this.getPageElement("b15").setValue(StatisticsWinBS.percent(SA008, totalnumber)+"%");
			this.getPageElement("b16").setValue(""+SA009);
			this.getPageElement("b17").setValue(StatisticsWinBS.percent(SA009, totalnumber)+"%");
			 SH002=Integer.valueOf(postObject[0].toString());
			 SH003=Integer.valueOf(postObject[1].toString());
			 SH004=Integer.valueOf(postObject[2].toString());
			 SH005=Integer.valueOf(postObject[3].toString());
			 SH006=Integer.valueOf(postObject[4].toString());
			 SH007=Integer.valueOf(postObject[5].toString());
			 SH008=Integer.valueOf(postObject[6].toString());
			 SH009=Integer.valueOf(postObject[7].toString());
			 SH010=Integer.valueOf(postObject[8].toString());
			 SH011=Integer.valueOf(postObject[9].toString());
			 SH012=Integer.valueOf(postObject[10].toString());
			 SH013=Integer.valueOf(postObject[11].toString());
			 SH014=Integer.valueOf(postObject[12].toString());
			 SH015=Integer.valueOf(postObject[13].toString());
			 SH016=Integer.valueOf(postObject[14].toString());
			this.getPageElement("d8").setValue(""+SH005);
			this.getPageElement("d9").setValue(StatisticsWinBS.percent(SH005, totalnumber)+"%");
			this.getPageElement("d10").setValue(""+SH006);
			this.getPageElement("d11").setValue(StatisticsWinBS.percent(SH006, totalnumber)+"%");
			this.getPageElement("d12").setValue(""+SH007);
			this.getPageElement("d13").setValue(StatisticsWinBS.percent(SH007, totalnumber)+"%");
			this.getPageElement("d14").setValue(""+SH008);
			this.getPageElement("d15").setValue(StatisticsWinBS.percent(SH008, totalnumber)+"%");
			this.getPageElement("d16").setValue(""+SH009);
			this.getPageElement("d17").setValue(StatisticsWinBS.percent(SH009, totalnumber)+"%");
			this.getPageElement("d18").setValue(""+SH015);
			this.getPageElement("d19").setValue(StatisticsWinBS.percent(SH015, totalnumber)+"%");
			this.getPageElement("d20").setValue(""+SH010);
			this.getPageElement("d21").setValue(StatisticsWinBS.percent(SH010, totalnumber)+"%");
			this.getPageElement("d22").setValue(""+SH011);
			this.getPageElement("d23").setValue(StatisticsWinBS.percent(SH011, totalnumber)+"%");
			this.getPageElement("d24").setValue(""+SH012);
			this.getPageElement("d25").setValue(StatisticsWinBS.percent(SH012, totalnumber)+"%");
			this.getPageElement("d26").setValue(""+SH013);
			this.getPageElement("d27").setValue(StatisticsWinBS.percent(SH013, totalnumber)+"%");
			
		}else{
			this.getPageElement("b2").setValue("");
			this.getPageElement("b3").setValue("");
			this.getPageElement("b4").setValue("");
			this.getPageElement("b5").setValue("");
			this.getPageElement("b6").setValue("");
			this.getPageElement("b7").setValue("");
			this.getPageElement("b8").setValue("");
			this.getPageElement("b9").setValue("");
			this.getPageElement("b10").setValue("");
			this.getPageElement("b11").setValue("");
			this.getPageElement("b12").setValue("");
			this.getPageElement("b13").setValue("");
			this.getPageElement("b14").setValue("");
			this.getPageElement("b15").setValue("");
			this.getPageElement("b16").setValue("");
			this.getPageElement("b17").setValue("");
			this.getPageElement("d8").setValue("");
			this.getPageElement("d9").setValue("");
			this.getPageElement("d10").setValue("");
			this.getPageElement("d11").setValue("");
			this.getPageElement("d12").setValue("");
			this.getPageElement("d13").setValue("");
			this.getPageElement("d14").setValue("");
			this.getPageElement("d15").setValue("");
			this.getPageElement("d16").setValue("");
			this.getPageElement("d17").setValue("");
			this.getPageElement("d18").setValue("");
			this.getPageElement("d19").setValue("");
			this.getPageElement("d20").setValue("");
			this.getPageElement("d21").setValue("");
			this.getPageElement("d22").setValue("");
			this.getPageElement("d23").setValue("");
			this.getPageElement("d24").setValue("");
			this.getPageElement("d25").setValue("");
			this.getPageElement("d26").setValue("");
			this.getPageElement("d27").setValue("");
			
		}
		String data2=SA002+","+SA003+","+SA004+","+SA005+","+SA006+","+SA007+","+SA008+","+SA009;
		String data4=SH002+","+SH003+","+SH004+","+SH005+","+SH006+","+SH007+","+SH008+","+SH009+","+SH010+","+SH011+","+SH012+","+SH013+","+SH014+","+SH015;
		String option2 = "{"
				+ "color: ['#5793f3'],"
				+ "title : {"
				+ "text : '年龄情况分析',"
				+ "x:'center',"
				+ "y:'top',"
				+ "textAlign:'left'"
				+ "},"
				+ "tooltip : {},"
				+ "xAxis : {"
				+ "data : [ '30及以下', '31-35', '36-40','41-45','46-50','51-54','55-59','60及以上'],"
				+ "'axisLabel':{" + "interval: 0" + "}" + "}," + "yAxis : {},"
				+ "series : [ {" + "name : '人数'," + "type : 'bar'," + "label : {"
				+ "normal : {" + "show : true," + "position : 'top'" + "}" + "},"
				+ "data : [ "+ data2 +" ]" + "} ]," 
				 +"animation: false "
				+ "}";
		if(SA002==null){
			SA002=0;SA003=0;SA004=0;SA005=0;SA006=0;SA007=0;SA008=0;SA009=0;
		}
		String optionbing = "{"
			    +"title : {"
	        +"text: '年龄情况分析',"
	        +"x:'center'"
	    +"},"
	    +"tooltip : {"
	    +"},"
	    +"series : ["
	        +"{"
	            +"name: '人数',"
	            +"type: 'pie',"
	            +"radius : '55%',"
	            +"center: ['50%', '60%'],"
	            +"data:["
	                +"{value:"+SA002+", name:'30及以下'},"
	                +"{value:"+SA003+", name:'31-35'},"
	                +"{value:"+SA004+", name:'36-40'},"
	                +"{value:"+SA005+", name:'41-45'},"
	                +"{value:"+SA006+", name:'46-50'},"
	                +"{value:"+SA007+", name:'51-54'},"
	                +"{value:"+SA008+", name:'55-59'},"
	                +"{value:"+SA009+", name:'60及以上'}"
	            +"],"
	            +"itemStyle: {"
	                +"emphasis: {"
	                    +"shadowBlur: 10,"
	                    +"shadowOffsetX: 0,"
	                    +"shadowColor: 'rgba(0, 0, 0, 0.5)'"
	                +"}"
	            +"}"
	        +"}"
	    +"],"
	    +"animation: false "
	+"}";

		
		String option4 = "{"
				+ "color: ['#675bba'],"
				+ "title : {"
				+ "text : '最高职务层次情况分析',"
				+ "x:'center',"
				+ "y:'top',"
				+ "textAlign:'left'"
				+ "},"
				+ "tooltip : {},"
				+ "xAxis : {"
				+ "data : [ '国家级正职', '国家级副职', '省部级正职', '省部级副职', '厅局级正职', '厅局级副职',"
				+ "'县处级正职', '县处级副职', '乡科级正职', '乡科级副职', '科员', '办事员', '试用期', '其他'],"
				+ "'axisLabel':{"
				+ "interval:0,"
				+ "rotate:60,"
				+ "margin:2,"
				+ "textStyle:{"
				+ "color:'#222'"
				+ "}"
				+ "}"
				+ "},"
				+ "yAxis : {},"
				+ "series : [ {"
				+ "name : '人数',"
				+ "type : 'bar',"
				+ "label : {"
				+ "normal : {"
				+ "show : true,"
				+ "position : 'top'"
				+ "}"
				+ "},"
				+ "data : [ "+ data4 +" ]"
				+ "} ]," 
				 +"animation: false "
				+ "}";
		//String biaozhi = this.getPageElement("biaozhi").getValue();
		if(biaozhi.equals("1")){
			this.getExecuteSG().addExecuteCode("disabled()");
			this.getExecuteSG().addExecuteCode("change()");
			this.getExecuteSG().addExecuteCode("set("+option+");");
			this.getExecuteSG().addExecuteCode("set3("+option3+");");
			this.getExecuteSG().addExecuteCode("set2("+option2+");");
			this.getExecuteSG().addExecuteCode("set4("+option4+");");
			this.getExecuteSG().addExecuteCode("setbing("+optionbing+");");
			this.getExecuteSG().addExecuteCode("jiazai()");
					
		}else{
			this.getExecuteSG().addExecuteCode("setOption("+option+");");
			this.getExecuteSG().addExecuteCode("setOption3("+option3+");");
			this.getExecuteSG().addExecuteCode("setOption2("+option2+");");
			this.getExecuteSG().addExecuteCode("setOption4("+option4+");");
			this.getExecuteSG().addExecuteCode("setOptionbing("+optionbing+");");
			
		}
		this.getPageElement("dsq").setValue("0");
		return EventRtnType.NORMAL_SUCCESS;
		
			
	}
	//年龄与职务层次的更新
	@PageEvent("update")
	public void update() throws RadowException{
		String groupid = this.getPageElement("subWinIdBussessId2").getValue();
		HBSession sess=HBUtil.getHBSession();
		String sql="SELECT a01.a0000,a0107,a0148 FROM A01 a01 WHERE a01. STATUS = '1' AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.a0255 = '1' AND a02.a0201b like '"+groupid+"%' )";
		List<Object[]> objects=sess.createSQLQuery(sql).list();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		int SA002=0;
		int SA003=0;
		int SA004=0;
		int SA005=0;
		int SA006=0;
		int SA007=0;
		int SA008=0;
		int SA009=0;
		int SH002=0;int SH003=0;int SH004=0;int SH005=0;int SH006=0;int SH007=0;int SH008=0;int SH009=0;
		int SH010=0;int SH011=0;int SH012=0;int SH013=0;int SH014=0;int SH015=0;int SH016=0;
		int totalnumber=objects.size();
		Date date=new Date();
		String now=sdf.format(date);
		try {
			date=sdf.parse(now);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Object[] obj:objects){
			//年龄判断统计
			String bir=(String) obj[1];
			if(bir!=null){
				Date a=null;
				if(bir.length()==6){
					bir=bir+"01";
				}
				try {
					a = sdf.parse(bir);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
			long birth=a.getTime();
	 		
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.YEAR, -31);
			Date under30=c.getTime();
			if(birth>under30.getTime()){
				SA002+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under35=c.getTime();
			if(birth>under35.getTime()&&birth<=under30.getTime()){
				SA003+=1;
				
			}
			c.add(Calendar.YEAR, -5);
			Date under40=c.getTime();
			if(birth>under40.getTime()&&birth<=under35.getTime()){
				SA004+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under45=c.getTime();
			if(birth>under45.getTime()&&birth<=under40.getTime()){
				SA005+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under50=c.getTime();
			if(birth>under50.getTime()&&birth<=under45.getTime()){
				SA006+=1;
			}
			c.add(Calendar.YEAR, -4);
			Date under54=c.getTime();
			if(birth>under54.getTime()&&birth<=under50.getTime()){
				SA007+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under59=c.getTime();
			if(birth>under59.getTime()&&birth<=under54.getTime()){
				SA008+=1;
			}
			if(birth<=under59.getTime()){
				SA009+=1;
			}
		}
			//最高职务层次判断统计
			String post=(String)obj[2];
			if(post==null){
				SH016+=1;
			}else if(post.equals("0101")){
				SH002+=1;
			}else if(post.equals("0102")){
				SH003+=1;
			}else if(post.equals("0111")){
				SH004+=1;
			}else if(post.equals("0112")){
				SH005+=1;
			}else if(post.equals("0121")){
				SH006+=1;
			}else if(post.equals("0122")){
				SH007+=1;
			}else if(post.equals("0131")){
				SH008+=1;
			}else if(post.equals("0132")){
				SH009+=1;
			}else if(post.equals("0141")){
				SH010+=1;
			}else if(post.equals("0142")){
				SH011+=1;
			}else if(post.equals("0150")){
				SH012+=1;
			}else if(post.equals("0160")){
				SH013+=1;
			}else if(post.equals("0198")){
				SH014+=1;
			}else if(post.equals("0199")){
				SH015+=1;
			}
		}
		String agesql="select SA001 from statistics_age where b0111='"+groupid+"'"; 
		Object ageObject=HBUtil.getHBSession().createSQLQuery(agesql).uniqueResult();
		String saveAgeSql="";
		String time;
		if(DBUtil.getDBType()==DBType.ORACLE){
			time="sysdate";
		}else{
			time="current_timestamp";
		}
		if(ageObject==null){
			String id=UUID.randomUUID().toString();
			saveAgeSql="insert into statistics_age values('"+id+"','"+groupid+"',"+SA002+","+SA003+","+SA004+","+SA005+","+SA006+","+SA007+","+SA008+","+SA009+","+totalnumber+","+time+")";
		}else{
			saveAgeSql="update Statistics_age set SA002="+SA002+",SA003="+SA003+",SA004="+SA004+",SA005="+SA005+",SA006="+SA006+",SA007="+SA007+",SA008="+SA008+",SA009="+SA009+",totalnumber="+totalnumber+",stime="+time+"  where b0111='"+groupid+"'";
		}
		String postsql="select SH001 from statistics_highestpostlevel where b0111='"+groupid+"'"; 
		Object postObject=HBUtil.getHBSession().createSQLQuery(postsql).uniqueResult();
		String savePostSql="";
		if(postObject==null){
			String sid=UUID.randomUUID().toString();
			savePostSql="insert into statistics_highestpostlevel values('"+sid+"','"+groupid+"',"+SH002+","+SH003+","+SH004+","+SH005+","+SH006+","+SH007+","+SH008+","+SH009+","+SH010+","+SH011+","+SH012+","+SH013+","+SH014+","+SH015+","+SH016+","+totalnumber+","+time+")";
			
		}else{
			savePostSql="update statistics_highestpostlevel set SH002="+SH002+",SH003="+SH003+",SH004="+SH004+",SH005="+SH005+",SH006="+SH006+",SH007="+SH007+",SH008="+SH008+",SH009="+SH009+",SH010="+SH010+",SH011="+SH011+",SH012="+SH012+",SH013="+SH013+",SH014="+SH014+",SH015="+SH015+",SH016="+SH016+",totalnumber="+totalnumber+",stime="+time+" where b0111='"+groupid+"'";
		}
		
		sess.createSQLQuery(saveAgeSql).executeUpdate();
		sess.createSQLQuery(savePostSql).executeUpdate();
	}

}
