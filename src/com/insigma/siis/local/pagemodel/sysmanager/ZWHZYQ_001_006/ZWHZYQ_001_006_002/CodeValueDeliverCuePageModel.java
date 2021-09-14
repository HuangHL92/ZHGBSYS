package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;

public class CodeValueDeliverCuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
		String filename = dateStr+"_代码信息集导出.xml";
		this.getPageElement("fileName").setValue(filename);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		StringBuffer jsonStr = new StringBuffer();                            //创建字符串容器对象，用来传递给EXT树
		//学历学位
		jsonStr.append("[{\"text\" :\"学历学位信息\" ,\"id\" :\"menu_xlxw\",\"cls\" :\"folder\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"学历代码\" ,\"id\" :\"leaf_ZB64\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"学位代码\" ,\"id\" :\"leaf_GB6864\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"教育类别代码\" ,\"id\" :\"leaf_ZB123\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"专业代码\" ,\"id\" :\"leaf_GB16835\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		
		//职务信息
		jsonStr.append("{\"text\" :\"职务信息\" ,\"id\" :\"menu_zw\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"职务层次代码\" ,\"id\" :\"leaf_ZB09\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职务类别代码\" ,\"id\" :\"leaf_ZB42\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职动类型代码\" ,\"id\" :\"leaf_ZB13\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"免职类型代码\" ,\"id\" :\"leaf_ZB16\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"任职状态代码\" ,\"id\" :\"leaf_ZB14\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流去向代码\" ,\"id\" :\"leaf_ZB74\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流方式代码\" ,\"id\" :\"leaf_ZB72\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流原因代码\" ,\"id\" :\"leaf_ZB73\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选拔任用方式代码\" ,\"id\" :\"leaf_ZB122\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"专业技术资格代码\" ,\"id\" :\"leaf_GB8561\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"取得资格途径代码\" ,\"id\" :\"leaf_ZB24\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"职务名称代码\" ,\"id\" :\"leaf_ZB08\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职级代码\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"岗位类别代码\" ,\"id\" :\"leaf_ZB127\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职务等级代码\" ,\"id\" :\"leaf_ZB136\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		
		jsonStr.append("{\"text\" :\"成员类别代码\" ,\"id\" :\"leaf_ZB129\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//机构信息
		jsonStr.append("{\"text\" :\"机构信息\" ,\"id\" :\"menu_jg\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"机构级别代码\" ,\"id\" :\"leaf_ZB03\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"机构级别性质代码\" ,\"id\" :\"leaf_ZB04\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"单位隶属关系代码\" ,\"id\" :\"leaf_ZB87\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//人员信息
		jsonStr.append("{\"text\" :\"人员信息\" ,\"id\" :\"menu_ry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"籍贯出生地代码\" ,\"id\" :\"leaf_ZB01\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"政治身份代码\" ,\"id\" :\"leaf_GB4762\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"人员类别代码\" ,\"id\" :\"leaf_ZB125\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"人员状态代码\" ,\"id\" :\"leaf_ZB126\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训类别代码\" ,\"id\" :\"leaf_ZB29\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训离岗状态代码\" ,\"id\" :\"leaf_ZB30\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训机构类别代码\" ,\"id\" :\"leaf_ZB27\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"奖惩名称代码\" ,\"id\" :\"leaf_ZB65\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"进入本单位方式代码\" ,\"id\" :\"leaf_ZB77\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"退出管理方式代码\" ,\"id\" :\"leaf_ZB78\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"离退类别代码\" ,\"id\" :\"leaf_ZB132\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"称谓代码\" ,\"id\" :\"leaf_GB4761\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"批准机关性质代码\" ,\"id\" :\"leaf_ZB128\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"管理类别代码\" ,\"id\" :\"leaf_ZB130\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职级代码\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"级别代码\" ,\"id\" :\"leaf_ZB134\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"编制类型代码\" ,\"id\" :\"leaf_ZB135\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选调生类别代码\" ,\"id\" :\"leaf_ZB137\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选调生来源类别代码\" ,\"id\" :\"leaf_ZB138\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"专业技术类公务员任职资格代码\" ,\"id\" :\"leaf_ZB139\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"工作单位性质代码\" ,\"id\" :\"leaf_ZB140\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"考核结果代码\" ,\"id\" :\"leaf_ZB18\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//公开遴选选调信息
		jsonStr.append("{\"text\" :\"遴选选调信息\" ,\"id\" :\"menu_lxxd\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"工作单位层次代码\" ,\"id\" :\"leaf_ZB141\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"遴选或选调类别\" ,\"id\" :\"leaf_ZB142\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"遴选方式\" ,\"id\" :\"leaf_ZB143\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"原单位类别\" ,\"id\" :\"leaf_ZB144\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职称代码\" ,\"id\" :\"leaf_ZB145\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//考试录用人员信息
		jsonStr.append("{\"text\" :\"考试录用人员信息\" ,\"id\" :\"menu_kslyry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"人员来源情况代码\" ,\"id\" :\"leaf_ZB146\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"基层项目人员代码\" ,\"id\" :\"leaf_ZB147\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
	}
	/**
	 * 单击代码集显示到第一个grid上
	 * @param nodeId
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("exportList")
	public int exportList(String nodeId) throws RadowException{
		String[] nodeIds = nodeId.split("_");
		String type = nodeIds[0];
		String codeType = nodeIds[1];
		if("menu".equals(type)) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();       //存参数信息
		Grid grid = (Grid)this.createPageElement("list1", ElementType.GRID, false);
		List<CodeValue> list = bs6.getCustomizeCodeValueById(codeType);
		for(int i = 0; i < list.size(); i++){
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("codeType", list.get(i).getCodeType());
			hm.put("codeValue", list.get(i).getCodeValue());
			hm.put("codeName", list.get(i).getCodeName());
   		    gridlist.add(hm);
		}
		grid.setValueList(gridlist);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("add")
	public int add() throws RadowException {
		Grid grid = (Grid)this.getPageElement("list1");
		Grid grid1 = (Grid)this.createPageElement("list1", ElementType.GRID, false);              //点新增后的grid1
		List<HashMap<String, Object>> gridlist = grid.getValueList();                             //所有的值
		List<HashMap<String, Object>> gridlist1 = new ArrayList<HashMap<String, Object>>();       //没有被选中的值
		List<HashMap<String, Object>> gridlist2 = new ArrayList<HashMap<String, Object>>();       //被选中的值
		for(HashMap<String, Object> hm : gridlist) {                                              //遍历list1这个grid
			if(hm.get("selected")==null || "".equals(hm.get("selected"))) {                       //如果没有被选中
				gridlist1.add(hm);
			}
			if(hm.get("selected") instanceof Boolean) {                                           
				if(!(Boolean)hm.get("selected")){        //如果没有被选中
					gridlist1.add(hm);
				} else {
					gridlist2.add(hm);
				}
			}
		}
		grid1.setValueList(gridlist1);
		CodeValueDeliverCuePageModel.gridlist=gridlist2;
		this.setNextEventName("showList2");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	static List<HashMap<String, Object>> gridlist;
	
	@PageEvent("showList2")
	public int showList2() throws RadowException {
		Grid grid2 = (Grid)this.getPageElement("list2");
		List<HashMap<String, Object>> gridlist = grid2.getValueList();
		//将第二个表格的数据和第一个表格的数据合并
		gridlist.addAll(CodeValueDeliverCuePageModel.gridlist);
		grid2.setValueList(gridlist);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("remove")
	public int remove() throws RadowException{
		Grid grid2 = (Grid)this.getPageElement("list2");
		List<HashMap<String, Object>> gridlist = grid2.getValueList();                            //list2所有的值
		List<HashMap<String, Object>> gridlist1 = new ArrayList<HashMap<String, Object>>();       //没有被选中的值，最后要赋给list1
		List<HashMap<String, Object>> gridlist2 = new ArrayList<HashMap<String, Object>>();       //选中的值，要留下的
		for(HashMap<String, Object> hm : gridlist) {                                              //遍历list1这个grid
			if(hm.get("selected")==null || "".equals(hm.get("selected"))) {                       //如果没有被选中
				gridlist2.add(hm);
			}
			if(hm.get("selected") instanceof Boolean) {                                           
				if(!(Boolean)hm.get("selected")){        //如果没有被选中
					gridlist2.add(hm);
				} else {
					gridlist1.add(hm);                   //被选中的给list1
				}
			}
		}
		grid2.setValueList(gridlist2);
		CodeValueDeliverCuePageModel.gridlist=gridlist1;
		this.setNextEventName("showList1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("showList1")
	public int showList1() throws RadowException {
		Grid grid1 = (Grid)this.getPageElement("list1");
		List<HashMap<String, Object>> gridlist = grid1.getValueList();
		//将第二个表格的数据和第一个表格的数据合并
		gridlist.addAll(CodeValueDeliverCuePageModel.gridlist);
		grid1.setValueList(gridlist);
		return EventRtnType.NORMAL_SUCCESS;
	}
}
