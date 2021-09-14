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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String dateStr = sdf.format(new Date());
		String filename = dateStr+"_������Ϣ������.xml";
		this.getPageElement("fileName").setValue(filename);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //��ȡ���еĲ�����Ϣ���ͼ���
		StringBuffer jsonStr = new StringBuffer();                            //�����ַ������������������ݸ�EXT��
		//ѧ��ѧλ
		jsonStr.append("[{\"text\" :\"ѧ��ѧλ��Ϣ\" ,\"id\" :\"menu_xlxw\",\"cls\" :\"folder\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"ѧ������\" ,\"id\" :\"leaf_ZB64\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѧλ����\" ,\"id\" :\"leaf_GB6864\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB123\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"רҵ����\" ,\"id\" :\"leaf_GB16835\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		
		//ְ����Ϣ
		jsonStr.append("{\"text\" :\"ְ����Ϣ\" ,\"id\" :\"menu_zw\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"ְ���δ���\" ,\"id\" :\"leaf_ZB09\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ��������\" ,\"id\" :\"leaf_ZB42\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ�����ʹ���\" ,\"id\" :\"leaf_ZB13\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ְ���ʹ���\" ,\"id\" :\"leaf_ZB16\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ְ״̬����\" ,\"id\" :\"leaf_ZB14\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����ȥ�����\" ,\"id\" :\"leaf_ZB74\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������ʽ����\" ,\"id\" :\"leaf_ZB72\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����ԭ�����\" ,\"id\" :\"leaf_ZB73\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ�����÷�ʽ����\" ,\"id\" :\"leaf_ZB122\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"רҵ�����ʸ����\" ,\"id\" :\"leaf_GB8561\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ȡ���ʸ�;������\" ,\"id\" :\"leaf_ZB24\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"ְ�����ƴ���\" ,\"id\" :\"leaf_ZB08\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ������\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��λ������\" ,\"id\" :\"leaf_ZB127\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ��ȼ�����\" ,\"id\" :\"leaf_ZB136\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		
		jsonStr.append("{\"text\" :\"��Ա������\" ,\"id\" :\"leaf_ZB129\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//������Ϣ
		jsonStr.append("{\"text\" :\"������Ϣ\" ,\"id\" :\"menu_jg\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"�����������\" ,\"id\" :\"leaf_ZB03\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�����������ʴ���\" ,\"id\" :\"leaf_ZB04\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��λ������ϵ����\" ,\"id\" :\"leaf_ZB87\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//��Ա��Ϣ
		jsonStr.append("{\"text\" :\"��Ա��Ϣ\" ,\"id\" :\"menu_ry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"��������ش���\" ,\"id\" :\"leaf_ZB01\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������ݴ���\" ,\"id\" :\"leaf_GB4762\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��Ա������\" ,\"id\" :\"leaf_ZB125\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��Ա״̬����\" ,\"id\" :\"leaf_ZB126\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ������\" ,\"id\" :\"leaf_ZB29\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ���״̬����\" ,\"id\" :\"leaf_ZB30\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ����������\" ,\"id\" :\"leaf_ZB27\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������ƴ���\" ,\"id\" :\"leaf_ZB65\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"���뱾��λ��ʽ����\" ,\"id\" :\"leaf_ZB77\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�˳�����ʽ����\" ,\"id\" :\"leaf_ZB78\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB132\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ν����\" ,\"id\" :\"leaf_GB4761\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"��׼�������ʴ���\" ,\"id\" :\"leaf_ZB128\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB130\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ������\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������\" ,\"id\" :\"leaf_ZB134\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������ʹ���\" ,\"id\" :\"leaf_ZB135\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ����������\" ,\"id\" :\"leaf_ZB137\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ������Դ������\" ,\"id\" :\"leaf_ZB138\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"רҵ�����๫��Ա��ְ�ʸ����\" ,\"id\" :\"leaf_ZB139\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������λ���ʴ���\" ,\"id\" :\"leaf_ZB140\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"���˽������\" ,\"id\" :\"leaf_ZB18\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//������ѡѡ����Ϣ
		jsonStr.append("{\"text\" :\"��ѡѡ����Ϣ\" ,\"id\" :\"menu_lxxd\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"������λ��δ���\" ,\"id\" :\"leaf_ZB141\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѡ��ѡ�����\" ,\"id\" :\"leaf_ZB142\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѡ��ʽ\" ,\"id\" :\"leaf_ZB143\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ԭ��λ���\" ,\"id\" :\"leaf_ZB144\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ�ƴ���\" ,\"id\" :\"leaf_ZB145\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//����¼����Ա��Ϣ
		jsonStr.append("{\"text\" :\"����¼����Ա��Ϣ\" ,\"id\" :\"menu_kslyry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"��Ա��Դ�������\" ,\"id\" :\"leaf_ZB146\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������Ŀ��Ա����\" ,\"id\" :\"leaf_ZB147\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		this.setSelfDefResData(jsonStr.toString());                           //���ַ������󴫵ݸ������ķ���
		return EventRtnType.XML_SUCCESS;
	}
	/**
	 * �������뼯��ʾ����һ��grid��
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
		List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();       //�������Ϣ
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
		Grid grid1 = (Grid)this.createPageElement("list1", ElementType.GRID, false);              //���������grid1
		List<HashMap<String, Object>> gridlist = grid.getValueList();                             //���е�ֵ
		List<HashMap<String, Object>> gridlist1 = new ArrayList<HashMap<String, Object>>();       //û�б�ѡ�е�ֵ
		List<HashMap<String, Object>> gridlist2 = new ArrayList<HashMap<String, Object>>();       //��ѡ�е�ֵ
		for(HashMap<String, Object> hm : gridlist) {                                              //����list1���grid
			if(hm.get("selected")==null || "".equals(hm.get("selected"))) {                       //���û�б�ѡ��
				gridlist1.add(hm);
			}
			if(hm.get("selected") instanceof Boolean) {                                           
				if(!(Boolean)hm.get("selected")){        //���û�б�ѡ��
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
		//���ڶ����������ݺ͵�һ���������ݺϲ�
		gridlist.addAll(CodeValueDeliverCuePageModel.gridlist);
		grid2.setValueList(gridlist);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("remove")
	public int remove() throws RadowException{
		Grid grid2 = (Grid)this.getPageElement("list2");
		List<HashMap<String, Object>> gridlist = grid2.getValueList();                            //list2���е�ֵ
		List<HashMap<String, Object>> gridlist1 = new ArrayList<HashMap<String, Object>>();       //û�б�ѡ�е�ֵ�����Ҫ����list1
		List<HashMap<String, Object>> gridlist2 = new ArrayList<HashMap<String, Object>>();       //ѡ�е�ֵ��Ҫ���µ�
		for(HashMap<String, Object> hm : gridlist) {                                              //����list1���grid
			if(hm.get("selected")==null || "".equals(hm.get("selected"))) {                       //���û�б�ѡ��
				gridlist2.add(hm);
			}
			if(hm.get("selected") instanceof Boolean) {                                           
				if(!(Boolean)hm.get("selected")){        //���û�б�ѡ��
					gridlist2.add(hm);
				} else {
					gridlist1.add(hm);                   //��ѡ�еĸ�list1
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
		//���ڶ����������ݺ͵�һ���������ݺϲ�
		gridlist.addAll(CodeValueDeliverCuePageModel.gridlist);
		grid1.setValueList(gridlist);
		return EventRtnType.NORMAL_SUCCESS;
	}
}
