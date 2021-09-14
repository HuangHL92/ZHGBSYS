package com.insigma.siis.local.pagemodel.templateconf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.ComprehensiveSet;
import com.insigma.siis.local.business.entity.Structure;
import com.insigma.siis.local.business.entity.TroupeScore;

public class TemplateConfPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String orgId = this.getPageElement("gid").getValue();
		String sql = "select f.jggwconfid,f.b0111,f.b0101,f.gwcode,f.gwname,f.gwnum,f.zjcode,f.zwcode,a.countnum "
				+ " from Jggwconf f,(select A0215A_c,count(1) countnum from a02 where a0255='1' and a0201b='"
				+ orgId + "' group by A0215A_c) a where f.gwcode=a.A0215A_c(+) and b0111='" + orgId + "' order by gwcode";
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		PageElement pageElement = this.getPageElement("gid");
		String gid=pageElement.getValue();
		HBSession sess =HBUtil. getHBSession();
		String sql = " select id,order_number,project,duty_category,(select code_name from code_value where code_type='ZB42' and code_value=duty_category) duty_category_cn,duty_rank,(select code_name from code_value where code_type='ZB03' and code_value=duty_rank) duty_rank_cn,quantity,remark,one_ticket_veto from duty_num where unit='"+gid+"' order by order_number";
		
		 //List list = sess.createQuery(sql).list();
	     //this.setSelfDefResData( this .getPageQueryData(list, start, limit));
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("TrainingInfoGrid2.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGrid2Query(int start,int limit) throws RadowException{
		String gid = this.getPageElement("gid").getValue();
		HBSession sess =HBUtil. getHBSession();
		String sql = "select * from position_explain where unit='"+gid+"'";
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("showjgtz")
	public int showjgtz(String gid){
		HBSession sess =HBUtil. getHBSession ();
		String sql="select bzf,xbf,dpf,mzf,nlf,xlf,zyf,dyf,knowf,jlf,remarks,redf,greenf from troupe_score where b0111 = '"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			Object[]  obj = list.get(0);
			String parem = "";
			for (Object object : obj) {
				parem+=object+",";
			}
			parem= parem.substring(0,parem.length()-1);
			this.getExecuteSG().addExecuteCode("comprevalue17('"+parem+"');");
		}else{
			this.getExecuteSG().addExecuteCode("comprevalue17('null');");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("LegalEntitySaveBtn")
	public int LegalEntitySaveBtn(String jgtz){
	     String[] jgtz1 = jgtz.split(",");
		 String bzf = jgtz1[0];
		 String xbf = jgtz1[1];
		 String dpf = jgtz1[2];
		 String mzf = jgtz1[3];
		 String nlf = jgtz1[4];
		 String xlf = jgtz1[5];
		 String zyf = jgtz1[6];
		 String dyf = jgtz1[7];
		 String knowf = jgtz1[8];
		 String jlf = jgtz1[9];
		 
		 //判断分值是否小于100分
		 int fs = Integer.parseInt(bzf)+Integer.parseInt(xbf)+Integer.parseInt(dpf)+Integer.parseInt(mzf)+Integer.parseInt(nlf)
				 +Integer.parseInt(xlf)+Integer.parseInt(zyf)+Integer.parseInt(dyf)+Integer.parseInt(knowf)+Integer.parseInt(jlf);
		 if(fs<1){
			 this.setMainMessage("分值之和不能小于1分！");
			 return 0;
		 }
		 if(fs>100){
			 this.setMainMessage("分值之和不能大于100分！");
			 return 0;
		 }
		 
		 String remarks = jgtz1[10];
		 
		 String redf = jgtz1[11];
		 String greenf = jgtz1[12];
		 if(Integer.parseInt(redf)>100){
			 this.setMainMessage("红色分值不能大于100分！");
			 return 0;
		 }
		 if(Integer.parseInt(greenf)>100){
			 this.setMainMessage("绿色分值不能大于100分！");
			 return 0;
		 }
		 if(Integer.parseInt(redf)>Integer.parseInt(greenf)){
			 this.setMainMessage("红分值不能大于绿分值！");
			 return 0;
		 }
		 
		 
		 String b0111 = jgtz1[13];
		 
		HBSession sess =HBUtil. getHBSession ();
		String sql1= "select id from troupe_score where b0111='"+b0111+"'";
		String id = (String) sess.createSQLQuery(sql1).uniqueResult();
		TroupeScore score = new TroupeScore();
		score.setBzf(bzf);
		score.setXbf(xbf);
		score.setDpf(dpf);
		score.setMzf(mzf);
		score.setNlf(nlf);
		score.setXlf(xlf);
		score.setZyf(zyf);
		score.setDyf(dyf);
		score.setKnowf(knowf);
		score.setJlf(jlf);
		score.setRemarks(remarks);
		score.setRedf(redf);
		score.setGreenf(greenf);
		score.setB0111(b0111);

		score.setId(id);

		sess.saveOrUpdate(score);
		sess.flush();
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("TrainingInfoGrid4.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery4(int start,int limit) throws RadowException{
		String type1 = this.getPageElement("type").getValue();
		String gid = this.getPageElement("gid").getValue();
		//HBSession sess =HBUtil. getHBSession ();
		String type="";
		if("nl".equals(type1)||"banzi".equals(type1)||"dy".equals(type1)){
			type="meiy";
		}else if("xl".equals(type1)){
			type="ZB64";
		}else if("sxly".equals(type1)){
			type="SXLY";
		}else if("xb".equals(type1)){
			type="GB2261";
		}else if("dp".equals(type1)){
			type="GB4762";
		}else if("mz".equals(type1)){
			type="GB3304";
		}else if("jl".equals(type1)){
			type="TAGZB131";
		}
		String sql="";
		if("meiy".equals(type)){
			sql="select id,order_number, project,project project_cn,replace(quantity,'&','') quantity,category,one_ticket_veto from natural_structure  where unit='"+gid+"' and category='"+type1+"' order by order_number";
		}else{
			sql = "select id,order_number, (select code_name from code_value where code_type='"+type+"' and code_value=project) project_cn,project,replace(quantity,'&','') quantity,category,one_ticket_veto from natural_structure  where unit='"+gid+"' and category='"+type1+"' order by order_number ";
		}
		
		//List list = sess.createQuery(sql).list();
		//this.setSelfDefResData( this .getPageQueryData(list, start, limit));
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("TrainingInfoGrid17.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery17(int start,int limit) throws RadowException{
		String gid = this.getPageElement("gid").getValue();
		HBSession sess =HBUtil. getHBSession ();
		String sql = " select id,zktype,typeadjustment,displayorder,remarks,unit from structure_adjustment";
		
		 //List list = sess.createQuery(sql).list();
	     //this.setSelfDefResData( this .getPageQueryData(list, start, limit));
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("delete1")
   public int delete(String ids){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("realDelete");
		ne.setNextEventParameter(ids);
		this.addNextEvent(ne);
		
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("确定要删除这些记录?"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("delete2")
	public int delete2(String ids){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("realDelete2");
		ne.setNextEventParameter(ids);
		this.addNextEvent(ne);
		
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("确定要删除这些记录?"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("delete4")
	public int delete4(String ids){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("realDelete4");
		ne.setNextEventParameter(ids);
		this.addNextEvent(ne);
		
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("确定要删除这些记录?"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("realDelete")
	public int realDelete(String ids){
		
		String[] idsArr = ids.split(",");
		String idStr="";
		for (String id : idsArr) {
			id="'"+id+"'";
			idStr += id+",";
		}
		idStr = idStr.substring(0,idStr.length()-1);
		
		HBSession sess = HBUtil.getHBSession();
		String sql="delete from duty_num where id in ("+idStr+")";
		
		sess.createSQLQuery(sql).executeUpdate();
		sess.flush();
		
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("realDelete2")
	public int realDelete2(String ids){
		
		String[] idsArr = ids.split(",");
		String idStr="";
		for (String id : idsArr) {
			id="'"+id+"'";
			idStr += id+",";
		}
		idStr = idStr.substring(0,idStr.length()-1);
		
		HBSession sess = HBUtil.getHBSession();
		String sql="delete from position_explain where id in ("+idStr+")";
		
		sess.createSQLQuery(sql).executeUpdate();
		sess.flush();
		
		this.setNextEventName("TrainingInfoGrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("realDelete4")
	public int realDelete4(String ids){
		
		String[] idsArr = ids.split(",");
		String idStr="";
		for (String id : idsArr) {
			id="'"+id+"'";
			idStr += id+",";
		}
		idStr = idStr.substring(0,idStr.length()-1);
		
		HBSession sess = HBUtil.getHBSession();
		String sql="delete from natural_structure where id in ("+idStr+")";
		 
		sess.createSQLQuery(sql).executeUpdate();
		sess.flush();
		
		this.setNextEventName("TrainingInfoGrid4.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("subm2")
	public int subm2(String arr){
		String[] arr1 = arr.split(",");
		//table_type+","+team_responsibility+","+general_evaluation+","+optimize_direction;
		String table_type = arr1[0];//类型
		String team_responsibility = arr1[1];//领导班子职责
		if(team_responsibility==null){
			team_responsibility = "";
		}
		String general_evaluation = arr1[2];//整体评价
		if(general_evaluation==null){
			general_evaluation = "";
		}
		String optimize_direction = arr1[3];//优化方向措施
		if(optimize_direction==null){
			optimize_direction = "";
		}
		String unit = arr1[4];//所属单位
		String typeid = arr1[5];
		ComprehensiveSet compre = new ComprehensiveSet();
		compre.setTableType(table_type);
		compre.setTeamResponsibility(team_responsibility);
		compre.setGeneralEvaluation(general_evaluation);
		compre.setOptimizeDirection(optimize_direction);
		compre.setUnit(unit);
		if(typeid!=null&&!"".equals(typeid)&&!"hhhh".equals(typeid)){
			compre.setId(typeid);
		}
		HBSession sess =HBUtil. getHBSession ();
		sess.saveOrUpdate(compre);
		sess.flush();
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
