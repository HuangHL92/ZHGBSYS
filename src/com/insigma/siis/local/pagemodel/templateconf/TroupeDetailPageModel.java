package com.insigma.siis.local.pagemodel.templateconf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class TroupeDetailPageModel extends PageModel {
	
	public static String sid = "";
	
	public TroupeDetailPageModel() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException{
		String type = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("type").setValue(type);
		
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		String b0111 = this.getPageElement("gridB0111").getValue();
		String personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
				+ "where a0255 = '1' and a0201b = '"+b0111+"' and A0215A_c is not null) a "
				+ "where f.gwcode = a.A0215A_c and b0111 = '"+b0111+"'";//实配人员SQL
		HBSession sess =HBUtil. getHBSession();
		String sql = "  select distinct A01.A0101,A01.A0192A,(SUBSTR(a01.a0107, 1, 4)) || '.' ||(SUBSTR(a01.a0107, -4, 2)) A0107, A01.a0111A,A01.a0221,A01.a0288,A01.a0192e,A01.a0192c from a01 inner join ("+personSql+") cc on a01.a0000 = cc.a0000";
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int queryById(int start,int limit)throws RadowException{
		String type = this.getPageElement("type").getValue();
		//反查SQL
		String sql = "";
		if("1".equals(type)){
			sql = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='0' order by length(b0111)";
		}else if("2".equals(type)){
			sql = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='1' order by length(b0111)";
		}else if("3".equals(type)){
			sql = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='2' order by length(b0111)";
		}else if("4".equals(type)){
			sql = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='3' order by length(b0111)";
		}else if("5".equals(type)){
			sql = "select b0111, b0101 from b01 where b01.b0194 = '1' and b01.b0111 != '-1' and not exists (select 1 from WHOLESTATUS t where t.unit = b01.b0111) order by length(b0111)";
		}
		System.out.println("----------------"+sql);
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.rowclick")
	@GridDataRange
	public int persongridOnRowClick() throws RadowException, AppException{  //打开窗口的实例
		int row = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		String b0111=this.getPageElement("peopleInfoGrid").getValue("b0111",row).toString();
		this.getPageElement("cueRowIndex").setValue(""+row);
		
		showView(row,b0111);
		
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/*@Override
	public void closeCueWindow(String arg0) {
	this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}*/
	
	private String isStrNULL(String str){
		if(str==null||"".equals(str)||"null".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	@PageEvent("up.onclick")
	public int up() throws RadowException, AppException{
		//selectRow
		Grid pe = (Grid) this.getPageElement("peopleInfoGrid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("到顶了！");
			}
		}
		//int row = pe.getCueRowIndex();
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(""+(cueRowIndex-1));
		String gridA0000=this.getPageElement("peopleInfoGrid").getValue("b0111",cueRowIndex-1).toString();
		showView(cueRowIndex-1,gridA0000);
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("next.onclick")
	public int next() throws RadowException, AppException{
		//selectRow
		int size = this.getPageElement("peopleInfoGrid").getStringValueList().size();
		Grid pe = (Grid) this.getPageElement("peopleInfoGrid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(size-1)){
				throw new AppException("到底了！");
			}
		}
		//int row = pe.getCueRowIndex();
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(""+(cueRowIndex+1));
		String gridA0000=this.getPageElement("peopleInfoGrid").getValue("b0111",cueRowIndex+1).toString();
		showView(cueRowIndex+1,gridA0000);
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void showView(int cueRowIndex,String b0111) throws RadowException{
		this.getPageElement("gridB0111").setValue(b0111);
		//System.out.println(this.getPageElement("gridA0000").getValue());
		//StringBuffer buffer = new StringBuffer();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String, String> scoreMap = null;
		Map<String, String> colMap = null;
		HBSession sess = HBUtil.getHBSession();
		
		//回显 分值详情
		List<Object[]> scores = sess.createSQLQuery("select s.*,t.redf,t.greenf from wholestatus s left join troupe_score t on s.unit = t.b0111 where s.unit = '"+b0111+"'").list();
		if(scores!=null&&scores.size()>0){
			for(Object[] objs : scores){
				String unit = ""+objs[0];
				String statustype = ""+objs[1];
				String bzf = ""+objs[2];
				String xbf = ""+objs[3];
				String dpf = ""+objs[4];
				String mzf = ""+objs[5];
				String nlf = ""+objs[6];
				String xlf = ""+objs[7];
				String zyf = ""+objs[8];
				String dyf = ""+objs[9];
				String knowf = ""+objs[10];
				String jlf = ""+objs[11];
				String score = ""+objs[12];
				String redf = ""+objs[13];
				String greenf = ""+objs[14];
				String max = getMax(bzf,xbf,dpf,mzf,nlf,xlf,zyf,dyf,knowf,jlf);
				
				scoreMap = new HashMap<String, String>();
				scoreMap.put("statustype", isStrNULL(statustype));
				scoreMap.put("bzf", isStrNULL(isFUYI(bzf)));
				scoreMap.put("xbf", isStrNULL(isFUYI(xbf)));
				scoreMap.put("dpf", isStrNULL(isFUYI(dpf)));
				scoreMap.put("mzf", isStrNULL(isFUYI(mzf)));
				scoreMap.put("nlf", isStrNULL(isFUYI(nlf)));
				scoreMap.put("xlf", isStrNULL(isFUYI(xlf)));
				scoreMap.put("zyf", isStrNULL(isFUYI(zyf)));
				scoreMap.put("dyf", isStrNULL(isFUYI(dyf)));
				scoreMap.put("knowf", isStrNULL(isFUYI(knowf)));
				scoreMap.put("jlf", isStrNULL(isFUYI(jlf)));
				scoreMap.put("score", isStrNULL(isFUYI(score)));
				scoreMap.put("redf", isStrNULL(isFUYI(redf)));
				scoreMap.put("greenf", isStrNULL(isFUYI(greenf)));
				scoreMap.put("max", max);
			}
		}
		JSONObject jsonObjectScore = JSONObject.fromObject(scoreMap);
		
		this.getExecuteSG().addExecuteCode("appendScore('"+jsonObjectScore+"')");
		
		//回显 自然结构
		List<Object[]> list = sess.createSQLQuery("select t.category, t.project, t.quantity, t.one_ticket_veto,t.count, t.id  from NATURAL_STRUCTURE t where t.unit = '"+b0111+"' order by t.category").list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = list.get(i);
				String category = ""+objs[0];
				String project = ""+objs[1];
				String quantity = ""+objs[2];
				String one_ticket_veto = ""+objs[3];
				String count = ""+objs[4];
				String id = ""+objs[5];
				
				colMap = new HashMap<String, String>();
				colMap.put("category", isStrNULL(changeCategory(category)));
				colMap.put("project", isStrNULL(changeProject(category,project)));
				colMap.put("quantity", isStrNULL(quantity.replaceAll("\\&", "")));
				colMap.put("one_ticket_veto", isStrNULL(changeOne_ticket_veto(one_ticket_veto)));
				colMap.put("count", isStrNULL(count));
				colMap.put("id", isStrNULL(id));
				
				map.put("tr"+i, colMap);
				
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			
			this.getExecuteSG().addExecuteCode("appendTable('"+list.size()+"','"+jsonObject+"')");
		}
	}
	
	private String getMax(String bzf, String xbf, String dpf, String mzf,
			String nlf, String xlf, String zyf, String dyf, String knowf,
			String jlf) {
		//初始化数组
        List<Integer> nums = new ArrayList<Integer>();
        nums.add(Integer.parseInt(isStrNULL(bzf)));
        nums.add(Integer.parseInt(isStrNULL(xbf)));
        nums.add(Integer.parseInt(isStrNULL(dpf)));
        nums.add(Integer.parseInt(isStrNULL(mzf)));
        nums.add(Integer.parseInt(isStrNULL(nlf)));
        nums.add(Integer.parseInt(isStrNULL(xlf)));
        nums.add(Integer.parseInt(isStrNULL(zyf)));
        nums.add(Integer.parseInt(isStrNULL(dyf)));
        nums.add(Integer.parseInt(isStrNULL(knowf)));
        nums.add(Integer.parseInt(isStrNULL(jlf)));
        //设置最大值Max
        int Max = Collections.max(nums);
		return ""+Max;
	}
	private String isFUYI(String val){
		if("-1".equals(val)){
			val = "0";
		}
		return val;
	}
	
	private String changeOne_ticket_veto(String one_ticket_veto){
		if("1".equals(one_ticket_veto)){
			one_ticket_veto = "否";
		}else if("0".equals(one_ticket_veto)){
			one_ticket_veto = "是";
		}
		return one_ticket_veto;
	}
	
	private String changeCategory(String category){
		if("banzi".equals(category)){
			category = "领带班子人数";
		}else if("xb".equals(category)){
			category = "性别";
		}else if("dp".equals(category)){
			category = "党派";
		}else if("mz".equals(category)){
			category = "民族";
		}else if("nl".equals(category)){
			category = "年龄";
		}else if("xl".equals(category)){
			category = "学历";
		}else if("zy".equals(category)){
			category = "专业";
		}else if("dy".equals(category)){
			category = "地域";
		}else if("sxly".equals(category)){
			category = "熟悉领域";
		}else if("jl".equals(category)){
			category = "经历";
		}
		return category;
	}
	
	private String changeProject(String category,String project){
		HBSession sess = HBUtil.getHBSession();
		String codeType = "";
		if("banzi".equals(category)){

		}else if("xb".equals(category)){
			codeType = "GB2261";
		}else if("dp".equals(category)){
			codeType = "GB4762";
		}else if("mz".equals(category)){
			codeType = "GB3304";
		}else if("nl".equals(category)){
			
		}else if("xl".equals(category)){
			codeType = "ZB64";
		}else if("zy".equals(category)){
			codeType = "GB16835";
		}else if("dy".equals(category)){
			
		}else if("sxly".equals(category)){
			codeType = "SXLY";
		}else if("jl".equals(category)){
			codeType = "TAGZB131";
		}
		
		if(!"".equals(codeType)){
			Object obj = sess.createSQLQuery("select c.code_name from code_value c where  c.code_type = '"+codeType+"' and c.code_status = '1' and c.code_value = '"+project+"'").uniqueResult();
			if(obj!=null&&!"".equals(obj)){
				project = ""+obj;
			}
		}
		return project;
	}
}
