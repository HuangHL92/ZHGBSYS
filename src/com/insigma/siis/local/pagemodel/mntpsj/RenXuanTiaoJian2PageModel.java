package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.entity.FxypSJFA;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

import net.sf.json.JSONObject;

public class RenXuanTiaoJian2PageModel extends PageModel{

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		//���ø�λ��������ѡ
		CommQuery cqbs=new CommQuery();
		try {
			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select id,name "
					+ " from hz_GwMC   order by sortid  ");
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String id = m.get("id")==null?"":m.get("id").toString();
				String name = m.get("name")==null?"":m.get("name").toString();
				mapCode.put(id, name);
			}
			((Combo)this.getPageElement("gwmc")).setValueListForSelect(mapCode);
			
			String userid=SysManagerUtils.getUserId();
			List<HashMap<String, Object>> mcCode = cqbs.getListBySQL("select mdid,mdmc from (select mdid,mdmc,createdate "+ 
				"  from historymd y" + 
				" where userid = '"+userid+"'" + 
				" union all" + 
				" select mdid,mdmc,createdate " + 
				"  from historymd y" + 
				" where exists (select 1" + 
				"          from hz_LSMD_userref u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and u.mdid = y.mdid)) order by createdate desc");
			HashMap<String, Object> mapMC=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : mcCode){
				String id = m.get("mdid")==null?"":m.get("mdid").toString();
				String name = m.get("mdmc")==null?"":m.get("mdmc").toString();
				mapMC.put(id, name);
			}
			((Combo)this.getPageElement("gbgzmd")).setValueListForSelect(mapMC);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setNextEventName("initX");
        return 0;
	}
	
	@PageEvent("setgwmc")
	public int gwmcclick(String id) throws RadowException, AppException{
		String uuid = this.getPageElement("gwmc").getValue();
		String name = this.getPageElement("gwmc_combo").getValue();
		Object[] query = (Object[]) HBUtil.getHBSession().createSQLQuery("select sql,name from hz_gwmc a where a.id = '"+uuid+"'").uniqueResult();
		
		String sql = query[0].toString();
		
		this.request.getSession().setAttribute("gbmcName",name);
		this.request.getSession().setAttribute("gbmcSql",sql);
		this.getExecuteSG().addExecuteCode("collapseGroupWin($('#fxyp00').val());");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX(String a) throws RadowException, AppException{
		
		
		
		
		
		
		
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
		this.getExecuteSG().addExecuteCode("document.getElementById('existsCheckbox').click();odin.setSelectValue('a0163', '1');");
		
		
		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		FxypSJFA fxyp = (FxypSJFA)HBUtil.getHBSession().get(FxypSJFA.class, fxyp00);
		if(fxyp!=null&&!"clear".equals(a)){
			this.setNextEventName("initData");
		}
		
		this.request.getSession().setAttribute("gbmcName","");
		this.request.getSession().setAttribute("gbmcSql","");
        return 0;
	}
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("initData")
	@NoRequiredValidate
	@AutoNoMask
	public int initData() throws RadowException{		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		HBSession sess = HBUtil.getHBSession();
		List<Cxtj> cxtjlist = sess.createSQLQuery("select * from Cxtj "
				+ "where fxyp00='"+fxyp00+"' order by cxtj01")
				.addEntity(Cxtj.class).list();
		if(cxtjlist.size()>0){
			for(Cxtj cxtj:cxtjlist){
				if("textEdit".equals(cxtj.getCxtj08())||"NewDateEditTag".equals(cxtj.getCxtj08())
						||"numberEdit".equals(cxtj.getCxtj08())||"select2".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
				}else if("PublicTextIconEdit".equals(cxtj.getCxtj08())||"PublicTextIconEdit3".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
					this.getPageElement(cxtj.getCxtj07()+"_combo").setValue(cxtj.getCxtj09());
				}else if("radio".equals(cxtj.getCxtj08())){
					this.getExecuteSG().addExecuteCode("$('#"+cxtj.getCxtj07()+(cxtj.getCxtj04().replace(",", ""))+"').click();");
				}else if("checkbox".equals(cxtj.getCxtj08())&&"1".equals(cxtj.getCxtj04())){
					this.getExecuteSG().addExecuteCode("$('#"+cxtj.getCxtj07()+"').click();");
				}else if("checkbox".equals(cxtj.getCxtj08())&&cxtj.getCxtj07().contains(",")){
					String[] lableids = cxtj.getCxtj07().split(",");
					for(String lid : lableids){
						if(!"".equals(lid)){
							this.getExecuteSG().addExecuteCode("$('#"+lid+"').click();");
						}
					}
					
				}else if("comboTree".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
					this.getPageElement(cxtj.getCxtj07()+"_combotree").setValue(cxtj.getCxtj09());
				}
			}
		}
		
		
        return 0;
	}
	
	
	@PageEvent("clearReset")
	public int clearReset() throws RadowException {
		this.getPageElement("a0101").setValue("");
		this.getPageElement("a0165A").setValue("");
		this.getPageElement("a0165B").setValue("");
		this.getPageElement("a0165C").setValue("");
		this.getPageElement("a0165A_combo").setValue("");
		this.getPageElement("a0165B_combo").setValue("");
		this.getPageElement("a0165C_combo").setValue("");
		this.getExecuteSG().addExecuteCode("$('#a01040').click();");
		this.getExecuteSG().addExecuteCode("$('#a01170').click();");
		/* this.getExecuteSG().addExecuteCode("$('#a01410').click();"); */
		this.getPageElement("a0141").setValue("");
		this.getPageElement("a0141_combotree").setValue("");
		this.getPageElement("ageA").setValue("");
		this.getPageElement("ageB").setValue("");
		this.getPageElement("a0107A").setValue("");
		this.getPageElement("a0107B").setValue("");
		this.getPageElement("jiezsj").setValue("");
		this.getPageElement("a0192fA").setValue("");
		this.getPageElement("a0192fB").setValue("");
		this.getPageElement("a0221A").setValue("");
		this.getPageElement("a0221A_combotree").setValue("");
		this.getPageElement("xlxw").setValue("");
		this.getPageElement("xlxw_combo").setValue("");
		this.getPageElement("a0288A").setValue("");
		this.getPageElement("a0288B").setValue("");
		this.getPageElement("a0192e").setValue("");
		this.getPageElement("a0192e_combotree").setValue("");
		this.getPageElement("a0192cA").setValue("");
		this.getPageElement("a0192cB").setValue("");
		/*
		 * this.getPageElement("a0194z").setValue("");
		 * this.getPageElement("a0194z_combo").setValue("");
		 */
		this.getPageElement("a0144age").setValue("");
		this.getPageElement("a0144age_combo").setValue("");
		this.getPageElement("a0194c").setValue("");
		this.getPageElement("a0194c_combotree").setValue("");
		
		this.getPageElement("zdgwq").setValue("");
		this.getPageElement("zdgwq_combotree").setValue("");
		
		this.getPageElement("a1706").setValue("");
		this.getPageElement("a1706_combotree").setValue("");
		
		this.getPageElement("sfwxr").setValue("");
		
		this.getPageElement("newRZJL").setValue("");
		this.getPageElement("newRZJL_combotree").setValue("");
		this.getPageElement("A0194_TAG").setValue("");
		this.getPageElement("A0194_TAG_combotree").setValue("");
		
		this.getPageElement("b0131A").setValue("");
		this.getPageElement("b0131A_combo").setValue("");
		this.getPageElement("a0824").setValue("");
		this.getPageElement("a1701").setValue("");
		this.getPageElement("a0196z").setValue("");
		this.getPageElement("a0196c").setValue("");
		this.getPageElement("a99z103").setValue("");
		this.getPageElement("a99z104A").setValue("");
		this.getPageElement("a99z104B").setValue("");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��ѯ��ע����Χ��ѯ�д���Ĵ�С�������߼��ĸߵ������෴�������ж��߼�Ҳ���෴�Ĵ���
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query(String param) throws RadowException, AppException{
		
		
		String userID = SysManagerUtils.getUserId();
		//this.request.getSession().setAttribute("queryTypeEX", "�¸Ĳ�ѯ��ʽ");
		
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		 
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        StringBuffer b0111desc = new StringBuffer("");
       
        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// ����jsonObject���ݣ���ӵ�Map����
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[�������ƣ��Ƿ�����¼����Ƿ񱾼�ѡ��]
				b0111desc.append(types[0]+"��");
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
				b0111desc.deleteCharAt(b0111desc.length()-1);
			}
        }    
        return newquery(cu_b0111_sb,a02_a0201b_sb,b0111desc,userID,param);
        
	}
	
	
	
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc, String userID, String param) throws RadowException, AppException {
		
        String fxyp00 = saveCondition(cu_b0111_sb, a02_a0201b_sb,b0111desc,param);
        
        	
        this.getExecuteSG().addExecuteCode("collapseGroupWin('"+fxyp00+"');");
        
		return EventRtnType.NORMAL_SUCCESS;
	}




	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (!"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if (!"".equals(a0801b) || !"".equals(a0814) || !"".equals(a0824)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			if (!"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (!"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (!"".equals(a0824)) {
				StringBuffer a0824_sb = new StringBuffer("");
				String[] a0824Array = a0824.split(",");
				for (int i = 0; i < a0824Array.length; i++) {
					if("1".equals(a0824Array[i])){
						a0824_sb.append(" a0827 like '101%' or ");
					}else if("10".equals(a0824Array[i])){
						a0824_sb.append(" a0827 like '100%' or ");
					}else{
						a0824_sb.append(" a0827 like '" + a0824Array[i] + "%' or ");
					}
				}
				a0824_sb.delete(a0824_sb.length() - 4, a0824_sb.length() - 1);
				orther_sb.append(" and (" + a0824_sb.toString() + ")");
			}
			orther_sb.append(")");
		}

	}



	
	
	
	@SuppressWarnings("unchecked")
	private String saveCondition(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc, String param) throws AppException, RadowException{
		List<Map<String,String>> condList = new ArrayList<Map<String,String>>();
		String fxyp00 =  this.getPageElement("fxyp00").getValue();//��������id
		
		if (!"".equals(a02_a0201b_sb.toString())){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "������Χ");
			m.put("cxtj07", "a02_a0201b_sb");
			m.put("cxtj04", a02_a0201b_sb.toString());
			m.put("cxtj08", "org");
			m.put("cxtj09", b0111desc.toString());
			condList.add(m);
		}else{
			List<Cxtj> cxtjlist = HBUtil.getHBSession().createQuery("from Cxtj where fxyp00=? and cxtj08='org'")
					.setString(0, fxyp00).list();
			if(cxtjlist.size()>0){
				Cxtj cxtj = cxtjlist.get(0);
				Map<String,String> m = new HashMap<String, String>();
				m.put("cxtj02", "������Χ");
				m.put("cxtj07", "a02_a0201b_sb");
				m.put("cxtj04", cxtj.getCxtj04());
				m.put("cxtj08", "org");
				m.put("cxtj09", cxtj.getCxtj09());
				condList.add(m);
			}
		}
		
		//��Ա����
		String a0101 = this.getPageElement("a0101").getValue();//��Ա����
		if (a0101!=null&&!"".equals(a0101)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��Ա����");
			m.put("cxtj07", "a0101");
			m.put("cxtj04", a0101);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0101);
			condList.add(m);
		}
		
		//��Ա״̬
		String a0163 = this.getPageElement("a0163").getValue();//��Ա״̬
		if(a0163!=null&&!"".equals(a0163)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��Ա״̬");
			m.put("cxtj07", "a0163");
			m.put("cxtj04", a0163);
			m.put("cxtj08", "select2");
			String a0163_combo = this.getPageElement("a0163_combo").getValue();//��Ա״̬
			m.put("cxtj09", a0163_combo);
			condList.add(m);
		}
		
		//ʡ�ܸɲ�
		String a0165A = this.getPageElement("a0165A").getValue();
		if(a0165A!=null&&!"".equals(a0165A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "ʡ�ܸɲ�");
			m.put("cxtj07", "a0165A");
			m.put("cxtj04", a0165A);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165A_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//�йܸɲ�
		String a0165B = this.getPageElement("a0165B").getValue();
		if(a0165B!=null&&!"".equals(a0165B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�йܸɲ�");
			m.put("cxtj07", "a0165B");
			m.put("cxtj04", a0165B);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165B_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//�������в㣩�ɲ�
		String a0165C = this.getPageElement("a0165C").getValue();
		if(a0165C!=null&&!"".equals(a0165C)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�������в㣩�ɲ�");
			m.put("cxtj07", "a0165C");
			m.put("cxtj04", a0165C);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165C_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//�Ա�
		String a0104 = this.getPageElement("a0104").getValue();
		if (a0104!=null&&!"".equals(a0104)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�Ա�");
			m.put("cxtj07", "a0104");
			m.put("cxtj04", a0104);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "1".equals(a0104)?"��":("0".equals(a0104)?"ȫ��":"Ů"));
			condList.add(m);
		}
		
		//����
		String a0117 = this.getPageElement("a0117").getValue();
		if (a0117!=null&&!"".equals(a0117)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����");
			m.put("cxtj07", "a0117");
			m.put("cxtj04", a0117);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "1".equals(a0117)?"����":("0".equals(a0117)?"ȫ��":"��������"));
			condList.add(m);
		}
		
		//����
		String a0141 = this.getPageElement("a0141").getValue();//������ò
		if (a0141!=null&&!"".equals(a0141)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����");
			m.put("cxtj07", "a0141");
			m.put("cxtj04", a0141);
			m.put("cxtj08", "comboTree");
			String a0141_combo = this.getPageElement("a0141_combotree").getValue();//����
			m.put("cxtj09", a0141_combo);
			
			condList.add(m);
		}
		
		//����
		String ageA = this.getPageElement("ageA").getValue();//����
		String ageB = this.getPageElement("ageB").getValue();//����
		if (ageA!=null&&!"".equals(ageA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�������");
			m.put("cxtj07", "ageA");
			m.put("cxtj04", ageA);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageA);
			condList.add(m);
		}
		if (ageB!=null&&!"".equals(ageB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����С��");
			m.put("cxtj07", "ageB");
			m.put("cxtj04", ageB);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageB);
			condList.add(m);
		}
		
		//��������
		String a0107A = this.getPageElement("a0107A").getValue();//��������
		String a0107B = this.getPageElement("a0107B").getValue();//��������
		if(a0107A!=null&&!"".equals(a0107A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�������´���");
			m.put("cxtj07", "a0107A");
			m.put("cxtj04", a0107A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107A);
			condList.add(m);
		}
		if(a0107B!=null&&!"".equals(a0107B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��������С��");
			m.put("cxtj07", "a0107B");
			m.put("cxtj04", a0107B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107B);
			condList.add(m);
		}
		
		//��ֹʱ��
		String jiezsj = this.getPageElement("jiezsj").getValue();//��ֹʱ��
		if (jiezsj!=null&&!"".equals(jiezsj)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ֹʱ��");
			m.put("cxtj07", "jiezsj");
			m.put("cxtj04", jiezsj);
			m.put("cxtj08", "NewDateEditTag");
			//m.put("cxtj09", jiezsj);
			condList.add(m);
		}
		
		//ѧ��ѧλ
		String xlxw = this.getPageElement("xlxw").getValue();
		if(xlxw!=null&&!"".equals(xlxw)){
			Map<String,String> m = new HashMap<String, String>();
			
			String qrz = this.getPageElement("qrz").getValue();
			if(qrz!=null&&!"".equals(qrz)&&"1".equals(qrz)) {
				m.put("cxtj02", "ȫ����ѧ��ѧλ");
				
				Map<String,String> m2 = new HashMap<String, String>();
				m2.put("cxtj02", "ȫ���Ʊ�ʶ");
				m2.put("cxtj07", "qrz");
				m2.put("cxtj04", qrz);
				m2.put("cxtj08", "checkbox");
				m2.put("cxtj09", "��ѡ��");
				condList.add(m2);
			}else {
				m.put("cxtj02", "���ѧ��ѧλ");
			}
			m.put("cxtj07", "xlxw");
			m.put("cxtj04", xlxw);
			m.put("cxtj08", "select2");
			String xlxw_combo = this.getPageElement("xlxw_combo").getValue();
			m.put("cxtj09", xlxw_combo);
			condList.add(m);
		}
		
		//��ְ��ʱ��
		String a0192fA = this.getPageElement("a0192fA").getValue();//��ְ��ʱ��
		String a0192fB = this.getPageElement("a0192fB").getValue();//��ְ��ʱ��
		if(a0192fA!=null&&!"".equals(a0192fA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ��ʱ�����");
			m.put("cxtj07", "a0192fA");
			m.put("cxtj04", a0192fA);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192fA);
			condList.add(m);
		}
		if(a0192fB!=null&&!"".equals(a0192fB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ��ʱ��С��");
			m.put("cxtj07", "a0192fB");
			m.put("cxtj04", a0192fB);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192fB);
			condList.add(m);
		}
		
		//��ְ����
		String a0221A = this.getPageElement("a0221A").getValue();//��ְ����
		if(a0221A!=null&&!"".equals(a0221A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ����");
			m.put("cxtj07", "a0221A");
			m.put("cxtj04", a0221A);
			m.put("cxtj08", "comboTree");
			String a0221A_combo = this.getPageElement("a0221A_combotree").getValue();//��ְ����
			m.put("cxtj09", a0221A_combo);
			condList.add(m);
		}
		
		//����ְ����ʱ��
		String a0288A = this.getPageElement("a0288A").getValue();//����ְ����ʱ��
		String a0288B = this.getPageElement("a0288B").getValue();//����ְ����ʱ��
		if(a0288A!=null&&!"".equals(a0288A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����ְ����ʱ�����");
			m.put("cxtj07", "a0288A");
			m.put("cxtj04", a0288A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288A);
			condList.add(m);
		}
		if(a0288B!=null&&!"".equals(a0288B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����ְ����ʱ��С��");
			m.put("cxtj07", "a0288B");
			m.put("cxtj04", a0288B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288B);
			condList.add(m);
		}
		
		//��ְ��
		String a0192e = this.getPageElement("a0192e").getValue();//��ְ��
		if(a0192e!=null&&!"".equals(a0192e)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ��");
			m.put("cxtj07", "a0192e");
			m.put("cxtj04", a0192e);
			m.put("cxtj08", "comboTree");
			String a0192e_combo = this.getPageElement("a0192e_combotree").getValue();//��ְ��
			m.put("cxtj09", a0192e_combo);
			condList.add(m);
		}
		
		//��ְ��ʱ��
		String a0192cA = this.getPageElement("a0192cA").getValue();//��ְ��ʱ��
		String a0192cB = this.getPageElement("a0192cB").getValue();//��ְ��ʱ��
		if(a0192cA!=null&&!"".equals(a0192cA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ��ʱ��С��");
			m.put("cxtj07", "a0192cA");
			m.put("cxtj04", a0192cA);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cA);
			condList.add(m);
		}
		if(a0192cB!=null&&!"".equals(a0192cB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ְ��ʱ��С��");
			m.put("cxtj07", "a0192cB");
			m.put("cxtj04", a0192cB);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cB);
			condList.add(m);
		}
		
		//��Ϥ����
		/*
		 * String a0194z = this.getPageElement("a0194z").getValue();//��Ϥ����
		 * if(a0194z!=null&&!"".equals(a0194z)){ Map<String,String> m = new
		 * HashMap<String, String>(); m.put("cxtj02", "��Ϥ����"); m.put("cxtj07",
		 * "a0194z"); m.put("cxtj04", a0194z); m.put("cxtj08", "select2"); String
		 * a0194z_combo = this.getPageElement("a0194z_combo").getValue();
		 * m.put("cxtj09", a0194z_combo); condList.add(m); }
		 */
		
		//����
		String a0144age = this.getPageElement("a0144age").getValue();
		if(a0144age!=null&&!"".equals(a0144age)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����");
			m.put("cxtj07", "a0144age");
			m.put("cxtj04", a0144age);
			m.put("cxtj08", "select2");
			String a0144age_combo = this.getPageElement("a0144age_combo").getValue();
			m.put("cxtj09", a0144age_combo);
			condList.add(m);
		}
		
		//��Ҫ��ְ����
		String a0194c = this.getPageElement("a0194c").getValue();
		if(a0194c!=null&&!"".equals(a0194c)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��Ҫ��ְ����");
			m.put("cxtj07", "a0194c");
			m.put("cxtj04", a0194c);
			m.put("cxtj08", "comboTree");
			String a0194c_combo = this.getPageElement("a0194c_combotree").getValue();
			m.put("cxtj09", a0194c_combo);
			condList.add(m);
		}
		
		//20210223������ѯ����
		//�������
		String b0131A = this.getPageElement("b0131A").getValue();
		if(b0131A!=null&&!"".equals(b0131A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�������");
			m.put("cxtj07", "b0131A");
			m.put("cxtj04", b0131A);
			m.put("cxtj08", "select2");
			String b0131A_combo = this.getPageElement("b0131A_combo").getValue();
			m.put("cxtj09", b0131A_combo);
			condList.add(m);
		}
		
		//רҵ��������
		String a0824 = this.getPageElement("a0824").getValue();//��Ա����
		if (a0824!=null&&!"".equals(a0824)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "רҵ��������");
			m.put("cxtj07", "a0824");
			m.put("cxtj04", a0824);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0824);
			condList.add(m);
		}
		
		//�������򣨽ֵ���������ְ����
		String a0188 = this.getPageElement("a0188").getValue();
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�������򣨽ֵ���������ְ����");
			m2.put("cxtj07", "a0188");
			m2.put("cxtj04", a0188);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "��ѡ��");
			condList.add(m2);
		}
		
		//������������
		String a1701 = this.getPageElement("a1701").getValue();//��Ա����
		if (a1701!=null&&!"".equals(a1701)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "������������");
			m.put("cxtj07", "a1701");
			m.put("cxtj04", a1701);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a1701);
			condList.add(m);
		}
		
		//�������򣨽ֵ�����������ί���
		String a0132 = this.getPageElement("a0132").getValue();
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�������򣨽ֵ�����������ί���");
			m2.put("cxtj07", "a0132");
			m2.put("cxtj04", a0132);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "��ѡ��");
			condList.add(m2);
		}
		
		//רҵ����
		String a0196z = this.getPageElement("a0196z").getValue();
		if(a0196z!=null&&!"".equals(a0196z)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "רҵ����");
			m.put("cxtj07", "a0196z");
			m.put("cxtj04", a0196z);
			m.put("cxtj08", "select2");
			String a0196z_combo = this.getPageElement("a0196z_combo").getValue();
			m.put("cxtj09", a0196z_combo);
			condList.add(m);
		}
		
		//��ͷ�ɲ�
		String a0196c = this.getPageElement("a0196c").getValue();
		if(a0196c!=null&&!"".equals(a0196c)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ͷ�ɲ�");
			m.put("cxtj07", "a0196c");
			m.put("cxtj04", a0196c);
			m.put("cxtj08", "select2");
			String a0196c_combo = this.getPageElement("a0196c_combo").getValue();
			m.put("cxtj09", a0196c_combo);
			condList.add(m);
		}
		
		//�������򣨽ֵ����򳤣����Σ�
		String a0133 = this.getPageElement("a0133").getValue();
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�������򣨽ֵ����򳤣����Σ�");
			m2.put("cxtj07", "a0133");
			m2.put("cxtj04", a0133);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "��ѡ��");
			condList.add(m2);
		}
		
		//zdgwq�ص��λ
		String zdgwq = this.getPageElement("zdgwq").getValue();
		if(zdgwq!=null&&!"".equals(zdgwq)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�ص��λ");
			m2.put("cxtj07", "zdgwq");
			m2.put("cxtj04", zdgwq);
			m2.put("cxtj08", "comboTree");
			String zdgwq_combo = this.getPageElement("zdgwq_combotree").getValue();
			m2.put("cxtj09", zdgwq_combo);
			condList.add(m2);
		}
		
		//�ֹܹ�������
		String a1706 = this.getPageElement("a1706").getValue();
		if(a1706!=null&&!"".equals(a1706)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�ֹܹ�������");
			m2.put("cxtj07", "a1706");
			m2.put("cxtj04", a1706);
			m2.put("cxtj08", "comboTree");
			String a1706_combo = this.getPageElement("a1706_combotree").getValue();
			m2.put("cxtj09", a1706_combo);
			condList.add(m2);
		}
		
		//�Ƿ�Ϊ����
		String sfwxr = this.getPageElement("sfwxr").getValue();
		if(sfwxr!=null&&!"".equals(sfwxr)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�ֹ��Ƿ�Ϊ��������");
			m2.put("cxtj07", "sfwxr");
			m2.put("cxtj04", sfwxr);
			m2.put("cxtj08", "select2");
			m2.put("cxtj09", "01".equals(sfwxr)?"����":"����");
			condList.add(m2);
		}
		
		//��ְ������
		String newRZJL = this.getPageElement("newRZJL").getValue();
		if(newRZJL!=null&&!"".equals(newRZJL)) { 
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "��ְ������");
			m2.put("cxtj07", "newRZJL");
			m2.put("cxtj04", newRZJL);
			m2.put("cxtj08", "comboTree");
			String newRZJL_combo = this.getPageElement("newRZJL_combotree").getValue();
			m2.put("cxtj09", newRZJL_combo);
			condList.add(m2);	
		}
		
		// ��Ϥ����
		String A0194_TAG = this.getPageElement("A0194_TAG").getValue();
		if (!"".equals(A0194_TAG)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "��Ϥ����");
			m2.put("cxtj07", "A0194_TAG");
			m2.put("cxtj04", A0194_TAG);
			m2.put("cxtj08", "comboTree");
			String A0194_TAG_combo = this.getPageElement("A0194_TAG_combotree").getValue();
			m2.put("cxtj09", A0194_TAG_combo);
			condList.add(m2);
		}
		
		// �ɲ���������
		String gbgzmd = this.getPageElement("gbgzmd").getValue();
		if (!"".equals(gbgzmd)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�����ɲ�����");
			m2.put("cxtj07", "gbgzmd");
			m2.put("cxtj04", gbgzmd);
			m2.put("cxtj08", "select2");
			String gbgzmd_combo = this.getPageElement("gbgzmd_combo").getValue();
			m2.put("cxtj09", gbgzmd_combo);
			condList.add(m2);
		}
		
		//�Ƿ�Ϊѡ����
		String a99z103 = this.getPageElement("a99z103").getValue();
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�Ƿ�Ϊѡ����");
			m2.put("cxtj07", "a99z103");
			m2.put("cxtj04", a99z103);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "��");
			condList.add(m2);
		}
		
		//ѡ����ʱ��
		String a99z104A = this.getPageElement("a99z104A").getValue();//��������
		String a99z104B = this.getPageElement("a99z104B").getValue();//��������
		if(a99z104A!=null&&!"".equals(a99z104A)&&a99z104B.length()>0){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "ѡ��Ϊѡ����ʱ�����");
			m.put("cxtj07", "a99z104A");
			m.put("cxtj04", a99z104A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a99z104A);
			condList.add(m);
		}
		if(a99z104B!=null&&!"".equals(a99z104B)&&a99z104B.length()>0){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "ѡ��Ϊѡ����ʱ��С��");
			m.put("cxtj07", "a99z104B");
			m.put("cxtj04", a99z104B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a99z104B);
			condList.add(m);
		}
		
		
		
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			if(condList.size()>0){
				
				StringBuilder desc = new StringBuilder();
				int tiaoJianXuHao = 1;
				for(Map<String, String> m : condList){
					if(m.get("cxtj09")!=null){
						desc.append(tiaoJianXuHao++ +"��"+("".equals(m.get("cxtj02"))?"":(m.get("cxtj02")+"��"))+m.get("cxtj09")+"\n");
					}
				}
				if(fxyp00==null||"".equals(fxyp00)){//����
					ps = conn.prepareStatement("insert into hz_fxyp_SJFA(fxyp00,fxyp03) values(?,?)");
					fxyp00 = UUID.randomUUID().toString();
					ps.setString(1, fxyp00);
					ps.setString(2, desc.toString());
					ps.executeUpdate();
				}else{//�������
					ps = conn.prepareStatement("update hz_fxyp_SJFA set fxyp03=? where fxyp00=?");
					ps.setString(1, desc.toString());
					ps.setString(2, fxyp00);
					ps.executeUpdate();
					
					ps = conn.prepareStatement("delete from cxtj where fxyp00=?");
					ps.setString(1, fxyp00);
					ps.executeUpdate();
				}
				
				
				int paixu = 0;
				StringBuilder sql = new StringBuilder("insert into cxtj(cxtj00,cxtj01,cxtj02,cxtj04,"
						+ "cxtj07,cxtj08,cxtj09,fxyp00)"
						+ "values(?,?,?,?,?,?,?,?) ");
				ps = conn.prepareStatement(sql.toString());
				for(Map<String, String> m : condList){
					int fsize = 1;
					ps.setString(fsize++, UUID.randomUUID().toString());
					ps.setInt(fsize++, paixu);
					ps.setString(fsize++, m.get("cxtj02"));
					ps.setString(fsize++, m.get("cxtj04"));
					ps.setString(fsize++, m.get("cxtj07"));
					ps.setString(fsize++, m.get("cxtj08"));
					ps.setString(fsize++, m.get("cxtj09"));
					ps.setString(fsize++, fxyp00);
					ps.addBatch();
					paixu++;
				}
				ps.executeBatch();
					
				ps.close();
				conn.commit();
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e.printStackTrace();
				throw new AppException("����ʧ��");
				
			}
			e.printStackTrace();
			throw new AppException("����ʧ��");
		}
		return fxyp00;
	}
	
	public static Map<String,String> labelDescMap = new HashMap<String, String>(){
		{
			put("attr001", "��ֱ��λ");
			put("attr002", "������");
			put("attr003", "�����У����Ժ��");
			put("attr004", "��ί");
			put("attr005", "������");
			put("attr006", "���˴�");
			put("attr008", "����Э");
			put("attr009", "��ҵ�������쵼");
			put("attr010", "����Ժ");
			put("attr011", "����Ժ��ְ");
			put("attr012", "�м��Ժ");
			put("attr013", "�м��Ժ��ְ");
			put("attr014", "���أ��У���ԺԺ��");
			put("attr015", "���أ��У���쳤");
			put("attr016", "�м�ί�����");
			put("attr017", "���أ��У�ί���");
			put("attr018", "���أ��У���");
			put("attr019", "����ƾֳ�");
			put("attr020", "��У");
			put("attr021", "����");
			put("attr022", "��ְ");
			put("attr023", "��");
			put("attr024", "��");
			put("attr025", "�й���ְ");
			put("attr026", "����һ����");
			put("attr027", "�йܸ�ְ");
			put("attr028", "�������ֳ���");
			put("attr029", "�������ֳ���");
			put("attr030", "����һ����");
			put("attr031", "���γ�һ���������ֳ���");
			put("attr032", "���θ��ֳ���");
			put("attr033", "���飨��ί������ί����Ա");
			put("attr034", "�����ڳ�Ա��İ��ӳ�Ա������ʦ�����⸱�ֳ�");
			put("attr035", "�����쵼ְ��ķǰ��ӳ�Ա");
			put("attr036", "�ֵ�λ�����أ��ɳ��ɲ�");
			put("attr037", "�ֵ�λרҵ�ɲ�");
			put("attr038", "1��");
			put("attr039", "2��");
			put("attr040", "3������");
			put("attr041", "�����ۺϲ���");
			put("attr042", "�˴���Э����");
			put("attr043", "�ͼ��첿��");
			put("attr044", "��֯���²���");
			put("attr045", "��ʶ��̬����");
			put("attr046", "ͳս���Ȳ���");
			put("attr047", "����ά�Ȳ���");
			put("attr048", "���ò�ó����");
			put("attr049", "���轻ͨ����");
			put("attr050", "ũ�ʻ�������");
			put("attr051", "�ƽ���������");
			put("attr052", "���������");
			put("attr053", "��Ʋ���");
			put("attr054", "Ⱥ������");
			put("attr055", "����Ժ��");
			put("attr056", "��ְԺУ");
			put("attr057", "ҽ�ƻ���");
			put("attr058", "��ҵ");
			put("attr059", "������(԰��)");
			put("attr060", "���أ��У��������ӳ�Ա");
			put("attr061", "���أ��У�ί���");
			put("attr062", "���أ��У���");
			put("attr063", "���أ��У���ί������ְ");
			put("attr064", "���أ��У�����������ְ");
			put("attr065", "���磨�ֵ�����ί���");
			put("attr066", "���磨�ֵ���������Ҫ������");
			put("attr067", "���磨�ֵ��������쵼");
			put("attr068", "�Կ�֧Ԯ");
			put("attr069", "��ѧ�����");
			put("attr070", "��ɲ�");
			put("attr071", "��������㹤������");
			put("attr072", "��ίίԱ");
			put("attr073", "��ί��ίԱ");
			put("attr074", "�м�ί��ί");
			put("attr075", "�м�ίίԱ");
			put("attr076", "���˴�ί(רְ)");
			put("attr077", "���˴�ί(��ְ)");
			put("attr078", "����Э��ί(רְ)");
			put("attr079", "����Э��ί(��ְ)");
			put("attr080", "�˴����鳤");
			put("attr081", "�˴����鳤");
			put("attr082", "�˴�칫��");
			put("attr083", "�˴�ί������");
			put("attr084", "�˴�ί�Ҹ�����");
			put("attr085", "��Э���鳤");
			put("attr086", "��Э�����鳤");
			put("attr087", "��Э�칫��");
			put("attr088", "��Эί������");
			put("attr089", "��Эί�Ҹ�����");
			put("attr090", "�鳤");
			put("attr091", "���鳤");
			put("attr092", "���ֳ���Ѳ��רԱ");
			put("attr093", "��ʦ");
			put("attr094", "����");
			put("attr095", "����");
			put("attr096", "�����ʡֱ���ع�ְ");
			put("attr097", "��ֱ���ع�ְ");
			put("attr098", "�ص㹤�����ص㹤�̹�ְ");
			put("attr099", "����(�ֵ�)��ְ ");
			put("attr100", "��УԺ����ְ");
			put("attr101", "��ҵ��ְ");
			put("attr102", "�����ְ");
			put("attr103", "������ְ");
			put("attr104", "�Կ�֧Ԯ");
			put("attr105", "����");
			put("attr106", "ʡ");
			put("attr107", "��");
			put("attr108", "ѡ����");
			put("attr109", "���Ҽ�");
			put("attr110", "ʡ��");
			put("attr111", "��ֱ����");
			put("attr112", "������");
			put("attr113", "���Ҽ�");
			put("attr114", "ʡ��");
			put("attr115", "��ֱ����");
			put("attr116", "������");
			put("attr117", "���Ҽ�");
			put("attr118", "ʡ��");
			put("attr119", "��ֱ����");
			put("attr120", "������");
			put("attr121", "˫�ع���λ�ɲ�");
			put("attr122", "�ֵ�λ��֯���²��Ÿ�����");
		}
	};
}




