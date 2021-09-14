package com.insigma.siis.local.pagemodel.edu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.entity.Fxyp;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class mnpbPageModel extends PageModel{
	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
        return 0;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX(String a) throws RadowException{		
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
//        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
		this.getExecuteSG().addExecuteCode("document.getElementById('existsCheckbox').click();odin.setSelectValue('a0163', '1');");
		
		
		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		Fxyp fxyp = (Fxyp)HBUtil.getHBSession().get(Fxyp.class, fxyp00);
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
		/* this.getPageElement("a0165C").setValue(""); */
		this.getPageElement("a0165A_combo").setValue("");
		this.getPageElement("a0165B_combo").setValue("");
		/* this.getPageElement("a0165C_combo").setValue(""); */

		/* this.getExecuteSG().addExecuteCode("$('#a01410').click();"); */
		this.getPageElement("ageA").setValue("");
		this.getPageElement("ageB").setValue("");
		this.getPageElement("a0107A").setValue("");
		this.getPageElement("a0107B").setValue("");
		this.getPageElement("jiezsj").setValue("");



		this.getPageElement("a0288A").setValue("");
		this.getPageElement("a0288B").setValue("");
//		this.getPageElement("a0192e").setValue("");
//		this.getPageElement("a0192e_combotree").setValue("");
		this.getPageElement("a0192cA").setValue("");
		this.getPageElement("a0192cB").setValue("");
		/*
		 * this.getPageElement("a0194z").setValue("");
		 * this.getPageElement("a0194z_combo").setValue("");
		 */
		this.getPageElement("a0144age").setValue("");
//		this.getPageElement("a0144age_combo").setValue("");
		this.getPageElement("a0194c").setValue("");
//		this.getPageElement("a0194c_combotree").setValue("");
		
		this.getPageElement("zdgwq").setValue("");
//		this.getPageElement("zdgwq_combotree").setValue("");
		
		this.getPageElement("a1706").setValue("");
//		this.getPageElement("a1706_combotree").setValue("");
		
		this.getPageElement("sfwxr").setValue("");
		
		this.getPageElement("newRZJL").setValue("");
//		this.getPageElement("newRZJL_combotree").setValue("");
		this.getPageElement("A0194_TAG").setValue("");
//		this.getPageElement("A0194_TAG_combotree").setValue("");
		
		this.getPageElement("b0131A").setValue("");
//		this.getPageElement("b0131A_combo").setValue("");
//   	this.getPageElement("a0824").setValue("");
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
        
        this.getPageElement("fxyp00").setValue(fxyp00);	
        this.getExecuteSG().addExecuteCode("collapseGroupWin();");
        
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
		//�Ա�
		String a0104 = this.getPageElement("a0104").getValue();
		if (a0104!=null&&!"".equals(a0104)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�Ա�");
			m.put("cxtj07", "a0104");
			m.put("cxtj04", a0104);
			m.put("cxtj08", "select2");
			String a0104_combo = this.getPageElement("a0104_combo").getValue();//��Ա״̬
			m.put("cxtj09", a0104_combo);
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
//			String a0163_combo = this.getPageElement("a0163_combo").getValue();//��Ա״̬
			m.put("cxtj09", "��ְ��Ա");
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
		/*
		 * String a0165C = this.getPageElement("a0165C").getValue();
		 * if(a0165C!=null&&!"".equals(a0165C)){ Map<String,String> m = new
		 * HashMap<String, String>(); m.put("cxtj02", "�������в㣩�ɲ�"); m.put("cxtj07",
		 * "a0165C"); m.put("cxtj04", a0165C); m.put("cxtj08", "select2"); String
		 * a0165_combo = this.getPageElement("a0165C_combo").getValue(); m.put("cxtj09",
		 * a0165_combo); condList.add(m); }
		 * 
		 */

		
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
		
		//����ʱ������
		String xrdx05 = this.getPageElement("xrdx05").getValue().replace("-", "");//��ְ��ʱ��
		if(xrdx05!=null&&!"".equals(xrdx05)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ѵʱ������");
			m.put("cxtj07", "xrdx05");
			m.put("cxtj04", xrdx05.substring(0,6));
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xrdx05);
			condList.add(m);
		}
		
		
		
		//��ѵ����
		String zxs1 = this.getPageElement("zxs1").getValue();//��ְ��ʱ��
		String zxs2 = this.getPageElement("zxs2").getValue();//��ְ��ʱ��
		if(zxs1!=null&&!"".equals(zxs1)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����ѵʱ������");
			m.put("cxtj07", "zxs1");
			m.put("cxtj04", zxs1);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", zxs1);
			condList.add(m);
		}
		if(zxs2!=null&&!"".equals(zxs2)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "����ѵʱ��С��");
			m.put("cxtj07", "zxs2");
			m.put("cxtj04", zxs2);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", zxs2);
			condList.add(m);
		}
		
		//��ѧʱ
		String xs1 = this.getPageElement("xs1").getValue();//��ְ��ʱ��
		String xs2 = this.getPageElement("xs2").getValue();//��ְ��ʱ��
		if(xs1!=null&&!"".equals(xs1)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ѧʱ����");
			m.put("cxtj07", "xs1");
			m.put("cxtj04", xs1);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xs1);
			condList.add(m);
		}
		if(xs2!=null&&!"".equals(xs2)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ѧʱС��");
			m.put("cxtj07", "xs2");
			m.put("cxtj04", xs2);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xs2);
			condList.add(m);
		}
		
		//���ѧ�Ʒ�Χ
		String bc1 = this.getPageElement("bc1").getValue();//��ְ��ʱ��
		String bc2 = this.getPageElement("bc2").getValue();//��ְ��ʱ��
		if(bc1!=null&&!"".equals(bc1)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ѧʱ����");
			m.put("cxtj07", "bc1");
			m.put("cxtj04", bc1);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", bc1);
			condList.add(m);
		}
		if(bc2!=null&&!"".equals(bc2)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "��ѧʱС��");
			m.put("cxtj07", "bc2");
			m.put("cxtj04", bc2);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", bc2);
			condList.add(m);
		}
		//�ϴγ�����ѵʱ��___֮ǰ
		String scpxsj = this.getPageElement("scpxsj").getValue().replace("-","");//��ְ��ʱ��
		if(scpxsj!=null&&!"".equals(scpxsj)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�ϴ���ѵ����ʱ������");
			m.put("cxtj07", "scpxsj");
			m.put("cxtj04", scpxsj.substring(0,6));
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", scpxsj.substring(0,6));
			condList.add(m);
		}
		//�ϴ���ѵʱ��
		String scpxsc = this.getPageElement("scpxsc").getValue();//��ְ��ʱ��
		if(scpxsc!=null&&!"".equals(scpxsc)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�ϴ���ѵʱ��");
			m.put("cxtj07", "scpxsc");
			m.put("cxtj04", scpxsc);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", scpxsc);
			condList.add(m);
		}
		
		//ʡ�ܸɲ�
		String sggb = this.getPageElement("sggb").getValue();//��ְ��ʱ��
		if(sggb!=null&&!"".equals(sggb)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "�Ƿ�ʡ�ܸɲ�");
			m.put("cxtj07", "sggb");
			m.put("cxtj04", sggb);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", sggb);
			condList.add(m);
		}
		//��Ϥ����
//		String a0194z = this.getPageElement("a0194z").getValue();//��Ϥ����
//		if(a0194z!=null&&!"".equals(a0194z)){
//			Map<String,String> m = new HashMap<String, String>();
//			m.put("cxtj02", "��Ϥ����");
//			m.put("cxtj07", "a0194z");
//			m.put("cxtj04", a0194z);
//			m.put("cxtj08", "select2");
//			String a0194z_combo = this.getPageElement("a0194z_combo").getValue();
//			m.put("cxtj09", a0194z_combo);
//			condList.add(m);
//		}
//		
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
		
		//�Ƿ�Ϊѡ����
		String a99z103 = this.getPageElement("a99z103").getValue();
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "�Ƿ�Ϊѡ����");
			m2.put("cxtj07", "a99z103");
			m2.put("cxtj04", a99z103);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "��ѡ��");
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
						desc.append(tiaoJianXuHao++ +"��"+("".equals(m.get("cxtj02"))?"":(m.get("cxtj02")+"��"))+m.get("cxtj09")+"");
					}
				}
				if(fxyp00==null||"".equals(fxyp00)){//����
					ps = conn.prepareStatement("insert into fxyp(fxyp00,fxyp03) values(?,?)");
					fxyp00 = UUID.randomUUID().toString();
					ps.setString(1, fxyp00);
					ps.setString(2, desc.toString());
					ps.executeUpdate();
				}else{//�������
					ps = conn.prepareStatement("update fxyp set fxyp03=? where fxyp00=?");
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
				throw new AppException("��ѯʧ��");
				
			}
			e.printStackTrace();
			throw new AppException("��ѯʧ��");
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

	

    @PageEvent("ryGrid.dogridquery")
	@NoRequiredValidate
	public int ryGridQuery(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String userID = SysManagerUtils.getUserId();
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		
		String sql = "";
		
		//�����ж�,����ǻ�ȡ���ɲ������ID����ֱ�ӽ��иɲ������ѯ

		
		sql = getSQL(userID,fxyp00);
		String ordersql=" ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=a01.a0000))";
//		sql = sql + CommSQL.OrderByF(userID, null, "001.001", null);
		System.out.println(sql);
		this.pageQuery(sql+" order by "+ordersql, "SQL", start, limit);
			/*
			 * CommQuery cqbs1=new CommQuery(); List<HashMap<String, Object>> list
			 * =cqbs1.getListBySQL(sql.toString()); if(list.size()>0) { JSONArray
			 * updateunDataStoreObject1 = JSONArray.fromObject(list);
			 * System.out.println("xcy1");
			 * this.getExecuteSG().addExecuteCode("Add("+updateunDataStoreObject1+");");
			 * System.out.println("xcy2"); }
			 */

		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("saveP")
	@NoRequiredValidate
	public int saveP() throws RadowException, AppException {
		String a0000s = this.getPageElement("a0000s").getValue();
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		if (a0000s != null && !"".equals(a0000s)) {
			this.getExecuteSG().addExecuteCode(
					"window.realParent.doAddPerson.queryByNameAndIDS('" + a0000s + "','" + fxyp00 + "');");
		} else {
			this.setMainMessage("��ѡ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	String getSQL(String userID,String fxyp00) throws AppException, RadowException {
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");


		List<Cxtj> cxtjList = HBUtil.getHBSession().createQuery("from Cxtj where fxyp00=?").setString(0, fxyp00).list();

		if (cxtjList.size() == 0) {
			return null;
		}
		Map<String, String> cxtjMap = new HashMap<String, String>();
		for (Cxtj cxtj : cxtjList) {
			cxtjMap.put(cxtj.getCxtj07(), cxtj.getCxtj04());
		}

		// ��Ա����
		String a0101 = cxtjMap.get("a0101");
		if (a0101!=null&&!"".equals(a0101)) {
//			a01sb.append(" and ");
//			a01sb.append("a01.a0101 like '" + a0101 + "%'");
			String[] names = a0101.trim().split(",|��");
			//������ڶ������
			if(names.length>1){
				a01sb.append(" and ( ");
				for(int i=0;i<names.length;i++){
					a01sb.append( "  a01.a0101  like '%"+names[i]+"%'  ");
					if(i!=names.length-1) {
						a01sb.append(" or ");
					}
				}
				a01sb.append(" ) ");
			//��������
			}else {
				a01sb.append( "  and a01.a0101 like '" + a0101 + "%'");
			}
		}

		// ʡ�ܸɲ�
		String a0165A = cxtjMap.get("a0165A");
		if (a0165A != null && !"".equals(a0165A)) {
			String[] strs = a0165A.split(",");
			a01sb.append(" and (");
			for (int i = 0; i < strs.length; i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if (i != strs.length - 1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
		}
		
		//�Ƿ�ʡ�ܸɲ�
		String sggb = cxtjMap.get("sggb");
		if(!"1".equals(sggb)) {
			a01sb.append(" and ( instr(a01.a0165,'02')<=0  and instr(a01.a0165,'05')<=0 ) ");
		}

		// �йܸɲ�
		String a0165B = cxtjMap.get("a0165B");
		if (a0165B != null && !"".equals(a0165B)) {
			String[] strs = a0165B.split(",");
			a01sb.append(" and (");
			for (int i = 0; i < strs.length; i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if (i != strs.length - 1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
		}

		// �������в㣩�ɲ�
		/*
		 * String a0165C = cxtjMap.get("a0165C"); if (a0165C != null &&
		 * !"".equals(a0165C)) { String[] strs = a0165C.split(",");
		 * a01sb.append(" and ("); for (int i = 0; i < strs.length; i++) {
		 * a01sb.append(" a01.a0165 like '%" + strs[i] + "%' "); if (i != strs.length -
		 * 1) { a01sb.append(" or "); } } a01sb.append(") "); }
		 */



		// ����
		String ageA = cxtjMap.get("ageA");// ����
		String ageB = cxtjMap.get("ageB");// ����
		if (ageB!=null&&!"".equals(ageB) && StringUtils.isNumeric(ageB)) {// �Ƿ�����
			ageB = Integer.valueOf(ageB) + 1 + "";
		}
		String jiezsj = cxtjMap.get("jiezsj");// ��ֹʱ��
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if (dateEnd!=null&&!"".equals(dateEnd) && !"".equals(dateStart) && dateEnd.compareTo(dateStart) < 0) {
			throw new AppException("���䷶Χ����");
		}
		if (ageA!=null&&!"".equals(ageA)) {
			a01sb.append(" and substr(" + jiezsj + "-substr(a01.a0107,1,6),1,2)>=lpad('" + ageA + "',2,'0')");
		}
		if (ageB!=null&&!"".equals(ageB)) {
			a01sb.append(" and substr(" + jiezsj + "-substr(a01.a0107,1,6),1,2)<=lpad('" + ageB + "',2,'0')");
		}

		// ��������
		String a0107A = cxtjMap.get("a0107A");
		String a0107B = cxtjMap.get("a0107B");// ��������
		if (a0107A!=null&&!"".equals(a0107A)) {
			a01sb.append(" and substr(a01.a0107,0,6)>='" + a0107A.substring(0,6) + "'");
		}
		if (a0107B!=null&&!"".equals(a0107B)) {
			a01sb.append(" and substr(a01.a0107,0,6)<='" + a0107B.substring(0,6) + "'");
		}



		// ְ����ʱ��
		String a0288A = cxtjMap.get("a0288A");// ����ְ����ʱ��
		String a0288B = cxtjMap.get("a0288B");// ����ְ����ʱ��
		if (a0288A!=null&&!"".equals(a0288A)) {
			a01sb.append(" and substr(a01.a0288,0,6)>='" + a0288A.substring(0,6) + "'");
		}
		if (a0288B!=null&&!"".equals(a0288B)) {
			a01sb.append(" and substr(a01.a0288,0,6)<='" + a0288B.substring(0,6) + "'");
		}

		// ��ְ��
		String a0192e = cxtjMap.get("a0192e");
		if (a0192e!=null&&!"".equals(a0192e)) {
			String[] strs = a0192e.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0192e = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
		}

		// ��ְ��ʱ��
		String a0192cA = cxtjMap.get("a0192cA");
		String a0192cB = cxtjMap.get("a0192cB");
		if (a0192cA!=null&&!"".equals(a0192cA)) {
			a01sb.append(" and a01.a0192c>='" + a0192cA + "'");
		}
		if (a0192cB!=null&&!"".equals(a0192cB)) {
			a01sb.append(" and a01.a0192c<='" + a0192cB + "'");
		}
		//��η�Χ
		String bc1 = cxtjMap.get("bc1");
		String bc2 = cxtjMap.get("bc2");
		String bc1sql=" ";
		String bc2sql=" ";
		if(bc1!=null && !"".equals(bc1)) {
			bc1sql=" and t.xrdx08 >= "+bc1+" ";
		}
		if(bc2!=null && !"".equals(bc2)) {
			bc2sql=" and t.xrdx08 <= "+bc2+" ";
		}
		
		//����ʱ������
		String xrdx05 = cxtjMap.get("xrdx05");
		String xrdx05sql="";
		if(xrdx05!=null && !"".equals(xrdx05)) {
			xrdx05sql=" and substr(t.xrdx05,0,6) >= "+xrdx05+" ";
		}
		
		
		
		// ����ѵ����
		String zxs1 = cxtjMap.get("zxs1");
		String zxs2 = cxtjMap.get("zxs2");
		String zxssql=" (select sum(t.xrdx08)  from edu_xrdx t, edu_xrdx_ry t1 "
				+ "  where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203') zts,"
			    + " (select sum(t.xrdx09) from edu_xrdx t, edu_xrdx_ry t1 " + 
              "where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203') zxs, ";
		if (zxs1!=null&&!"".equals(zxs1)) {
			a01sb.append(" and (select sum(t.xrdx08) zxs from edu_xrdx t, edu_xrdx_ry t1 " + 
					"where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203' " + 
					")>="+zxs1+" ");
		}
		if (zxs2!=null&&!"".equals(zxs2)) {
			a01sb.append(" and ((select sum(t.xrdx08) zxs from edu_xrdx t, edu_xrdx_ry t1 " + 
					"where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203' " + 
					")<="+zxs2+" or a01.a0000 not in (select a0000 from edu_xrdx_ry )) ");
		}
		
		// ��ѧʱ
		String xs1 = cxtjMap.get("xs1");
		String xs2 = cxtjMap.get("xs2");
		if (xs1!=null&&!"".equals(xs1)) {
			a01sb.append(" and (select sum(t.xrdx09) zxs from edu_xrdx t, edu_xrdx_ry t1 " + 
					"where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203' " + 
					")>="+xs1+" ");
		}
		if (xs2!=null&&!"".equals(xs2)) {
			a01sb.append(" and ((select sum(t.xrdx09) zxs from edu_xrdx t, edu_xrdx_ry t1 " + 
					"where t.xrdx00=t1.xrdx00 "+bc1sql+bc2sql+xrdx05sql+" and t1.a0000=a01.a0000 and t.xrdx02<>'0203' " + 
					")<="+xs2+" or a01.a0000 not in (select a0000 from edu_xrdx_ry))");
		}

		// �ϴγ�����ѵ����ʱ������
		String scpxsj = cxtjMap.get("scpxsj");
		String scpxsc = cxtjMap.get("scpxsc");
		String sc="14";
		if(scpxsc!=null&&!"".equals(scpxsc)) {
			sc=scpxsc;
		}
		if (scpxsj!=null&&!"".equals(scpxsj)) {
			a01sb.append(" and  not exists (select 1 from edu_xrdx t3, edu_xrdx_ry t4 where t3.xrdx00=t4.xrdx00 and t4.a0000=a01.a0000  and t3.xrdx02<>'0203'  and substr(t3.xrdx06,0,6)>="+scpxsj.substring(0,6)+" and t3.xrdx08>="+sc+") ");
		}
		// ��Ϥ���� 
		String a0194z = cxtjMap.get("a0194z");
		if(a0194z!=null&&!"".equals(a0194z)) { 
			String[] strs = a0194z.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		
		
		//����
		String a0144age=cxtjMap.get("a0144age");
		if(a0144age!=null&&!"".equals(a0144age)) {
			orther_sb.append(" and  TRUNC((to_char(sysdate, 'yyyyMM') - substr(a01.a0144,0,6)) /100)>="+a0144age+"  ");

		}
		
		
		// ��Ҫ��ְ����
		String a0194c = cxtjMap.get("a0194c");
		if(a0194c!=null&&!"".equals(a0194c)) {
			String[] strs = a0194c.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		//20210223������ѯ����
		// b0131A 
		String a01sb1 = "";
		String b0131A= cxtjMap.get("b0131A");
		if(b0131A!=null && !"".equals(b0131A)) {
			String sz_flag=" ((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.002') or ";
			String qx_flag=" ((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.004') or ";
			String[] strs = b0131A.split(",");
			a01sb1=a01sb1+" and (";
			if(b0131A.indexOf("01")>-1||b0131A.indexOf("02")>-1) {//��ֱ
				if(b0131A.indexOf("01")==-1) {//��ί������
					sz_flag=sz_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("02")==-1) {//����������
					sz_flag=sz_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+sz_flag;
			}
			if(b0131A.indexOf("03")>-1||b0131A.indexOf("04")>-1) {//����
				if(b0131A.indexOf("03")==-1) {//��ί������
					qx_flag=qx_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("04")==-1) {//����������
					qx_flag=qx_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+qx_flag;
			}
			a01sb1=a01sb1.substring(0, a01sb1.length()-3);
			a01sb1=a01sb1+") ";
		}
		// �Ա�
		String a0104 = cxtjMap.get("a0104");
		if(a0104!=null&&!"".equals(a0104)) {
			orther_sb.append(" and a01.a0104='"+a0104+"' ");
		}

		// �������򣨽ֵ���������ְ����
		String a0188 = cxtjMap.get("a0188");
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {
			orther_sb.append(" and a01.a0188='1' ");
		}
		// �������򣨽ֵ�����������ί���
		String a0132 = cxtjMap.get("a0132");
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {
			orther_sb.append(" and a01.a0132='1' ");
		}
		// �������򣨽ֵ����򳤣����Σ�
		String a0133 = cxtjMap.get("a0133");
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {
			orther_sb.append(" and a01.a0133='1' ");
		}
		
		//����
		String a1701 =cxtjMap.get("a1701");
		if(a1701!=null&&!"".equals(a1701)) {
			a01sb.append("  and a01.a1701 like '%" +a1701+ "%' ");
		}
		
		//��Ϥ����
		String a0196z = cxtjMap.get("a0196z");
		if(a0196z!=null&&!"".equals(a0196z)) { 
			String[] strs = a0196z.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		
		//��ͷ�ɲ�
		String a0196c = cxtjMap.get("a0196c");
		if(a0196c!=null&&!"".equals(a0196c)) { 
			String[] strs = a0196c.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from extra_tags where 1=2 or a0196c in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		
		//zdgwq�ص��λ
		String zdgwq = cxtjMap.get("zdgwq"); 
		if(zdgwq!=null&&!"".equals(zdgwq)) {
			String[] strs = zdgwq.split(",");
			orther_sb.append(" and ( a01.a0000 in  ");
			for(int i=0;i<strs.length;i++) {
				List list = HBUtil.getHBSession().createSQLQuery("select code_remark   from code_value where code_type='ZDGWBQ' and  code_value='"+strs[i]+"'").list();
				String ids = org.apache.commons.lang.StringUtils.join(list.toArray(),",");
				if(ids!=null&&!"".equals(ids)) {
					orther_sb.append(" "+ids+"  ");
					if(i!=strs.length-1) {
						orther_sb.append(" or a0000 in ");
					}
				}
				
			}
			orther_sb.append(") ");
		}
		
		//�ֹܹ�������
		String a1706 =cxtjMap.get("a1706");
		if(a1706!=null&&!"".equals(a1706)) {
			String[] strs = a1706.split(","); 
			orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" a1706 = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		
		//�Ƿ�Ϊ����
		String sfwxr =cxtjMap.get("sfwxr");
		if(sfwxr!=null&&!"".equals(sfwxr)&&"01".equals(sfwxr)) {
			String a=orther_sb.toString().replaceAll("from hz_a17 where","from hz_a17 where a1701 is not null and a1702 is null and ");
			orther_sb=new StringBuffer(a);
		}
		
		//��ְ������
		String newRZJL =cxtjMap.get("newRZJL"); 
		int flag1=0;
		int flag2=0;
		if(newRZJL!=null&&!"".equals(newRZJL)) { 
			String[] strs = newRZJL.split(",");
	//		orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
			//ѭ������1
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("1".equals(type)) {
					if(flag1==0) {
						orther_sb.append(" and (a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag1+=1;		
				}	
			}
			if(flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}
			//ѭ������2
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("2".equals(type)) {
					if(flag2==0 && flag1==0) {
						orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}else if(flag2==0 && flag1>0) {
						orther_sb.append(" or a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag2+=1;		
				}	
			}
			if(flag2>0 && flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append("))) ");
			}else if(flag2>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}else {
				orther_sb.append(") ");
			}
		}
		
		//��Ϥ����
		String A0194_TAG = cxtjMap.get("A0194_TAG");
		if(A0194_TAG!=null&&!"".equals(A0194_TAG)) { 
			String[] strs = A0194_TAG.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0194_TAG where 1=2 or a0194 in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		
		
		//ѡ����
		String a99z103= cxtjMap.get("a99z103");
		String a99z104A = cxtjMap.get("a99z104A");
		String a99z104B =cxtjMap.get("a99z104B");// ��������
		System.out.println("a99z103==================="+a99z103);
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z103='1') ");
		}
		if (a99z104A!=null&&!"".equals(a99z104A)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104>='"+a99z104A+"') ");
		}
		if (a99z104B!=null&&!"".equals(a99z104B)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104<='"+a99z104B+"') ");
		}
		StringBuilder lablesql = new StringBuilder();


		String a0163 = cxtjMap.get("a0163") == null ? "" : cxtjMap.get("a0163");// ��Ա״̬
		String qtxzry = cxtjMap.get("qtxzry") == null ? "" : cxtjMap.get("qtxzry");
		String a02_a0201b_sb = cxtjMap.get("a02_a0201b_sb") == null ? "" : cxtjMap.get("a02_a0201b_sb");
		String finalsql = getCondiQuerySQL(userID, a01sb,a01sb1, a02sb, a02_a0201b_sb, orther_sb, a0163, "0", lablesql,zxssql);
		return finalsql;
	}

	public static String getCondiQuerySQL(String userid, StringBuffer a01sb, String a01sb1,StringBuffer a02sb, String a02_a0201b_sb,
			StringBuffer orther_sb, String a0163, String qtxzry, StringBuilder lablesql,String zxssql) {

		/*
		 * ���ˣ������˹�����ѡ ���ݡ����ݡ���ְ�� ��ʾ��������Ա�� ��ѯ��������Ա��
		 * 
		 * �˳����� ѡ�� ��������ת���� �µ�ѡ��ʱ�� ��ʾ��������Ա�� ��ѯ����ʷ��Ա
		 * 
		 * ѡ�������˳���ʽ���е� �������ݡ� ��ʾ��������Ա�� ��ѯ��������Ա ����ѡ���˳��Ǽǡ��������������ˡ�����ȥ��ְ���������� ��ʾ��������Ա��
		 * ��ѯ����ʷ��Ա �������� ��ʾ����ȥ���� ��ѯ����ʷ��Ա
		 */
		String a0163sql = "";
		if ("2".equals(a0163)) {
			a0163sql = " and a0163 in('2','21','22','23','29')";
		} else if ("".equals(a0163)) {

		} else {
			a0163sql = " and a0163='" + a0163 + "'";
		}
		String a02str = "";
		if ("0".equals(qtxzry) && a01sb1!=null && !"".equals(a01sb1)) {
			a02str = " and " + CommSQL.concat("a01.a0000", "''") + " in " + "(select a02.a0000 " + "from a02 "
					+ "where a02.A0201B in " + "(select cu.b0111 " + "from competence_userdept cu ,b01 b  "
					+ "where cu.b0111=b.b0111 and cu.userid = '" + userid + "'"+a01sb1+") " + " " + a02_a0201b_sb + a02sb + ") "
					+ a0163sql;
		}else if("0".equals(qtxzry)) {
			a02str = " and " + CommSQL.concat("a01.a0000", "''") + " in " + "(select a02.a0000 " + "from a02 "
					+ "where a02.A0201B in " + "(select cu.b0111 " + "from competence_userdept cu   "
					+ "where cu.userid = '" + userid + "') " + " " + a02_a0201b_sb + a02sb + ") "
					+ a0163sql;
		
		} else {
			a02str = " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0201b in(select b0111 from b01 where b0111!='-1')  "
					+ a02sb + ")   " + " and a01.status!='4' " + a0163sql;
		}
		String retsql = "";
		if (lablesql == null || "".equals(lablesql.toString())) {
			retsql = " select  "+zxssql+"a01.a0000,a01.a0101,(select code_name from code_value where a01.a0104=code_value and code_type='GB2261') a0104, (select b0101 from b01,a02 where a02.a0000=a01.a0000 and b01.b0111=a02.a0201b and a0279='1' and a0281='true' ) b0101,(select  to_char(wm_concat(code_name)) " + 
					" from (select * from code_value order by inino)" + 
					" where code_type = 'ZB130'  and a01.a0165 like '%' || code_value || '%') a0165,(substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2)) a0107,a0117,a0111a,a0140,a0134,a0196,a0184,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,decode(substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2),  '.', null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288,a0192f  from A01 a01 "
					+ " where  1=1  and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') " + orther_sb +
					// xzry +
					a02str + a01sb;
		} else {
			retsql = " select  "+zxssql+"a01.a0000,a01.a0101,(select code_name from code_value where a01.a0104=code_value and code_type='GB2261') a0104, (select b0101 from b01,a02 where a02.a0000=a01.a0000 and b01.b0111=a02.a0201b and a0279='1' and a0281='true' ) b0101,(select  to_char(wm_concat(code_name)) " + 
					" from (select * from code_value order by inino)" + 
					" where code_type = 'ZB130'  and a01.a0165 like '%' || code_value || '%') a0165,(substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2)) a0107,a0117,a0111a,a0140,a0134,a0196,a0184,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,decode(substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2),  '.', null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288,a0192f  from A01 a01  inner join ("
					+ lablesql + ") b on  b.a0000=a01.a0000 " + " where  1=1  and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') " + orther_sb +
					// xzry +
					a02str + a01sb;
		}
		// a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a
		return retsql;
	}

	private void xuelixueweiSQL2(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (a0801b != null && !"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if ((a0801b != null && !"".equals(a0801b)) || (a0814 != null && !"".equals(a0814))
				|| (a0824 != null && !"".equals(a0824))) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			if (a0801b != null && !"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (a0814 != null && !"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (!StringUtils.isEmpty(a0824)) {
				StringBuffer a0824_sb = new StringBuffer("");
				String[] a0824Array = a0824.split(",");
				for (int i = 0; i < a0824Array.length; i++) {
					if ("1".equals(a0824Array[i])) {
						a0824_sb.append(" a0827 like '101%' or ");
					} else if ("10".equals(a0824Array[i])) {
						a0824_sb.append(" a0827 like '100%' or ");
					} else {
						a0824_sb.append(" a0827 like '" + a0824Array[i] + "%' or ");
					}
				}
				a0824_sb.delete(a0824_sb.length() - 4, a0824_sb.length() - 1);
				orther_sb.append(" and (" + a0824_sb.toString() + ")");
			}
			orther_sb.append(")");
		}

	}

	@PageEvent("tpbj.onclick")
	public int tpbj() throws RadowException {
		LinkedHashSet<String> selected = new LinkedHashSet<String>();
		// ��cookie�еĻ�ȡ֮ǰѡ�����Աid
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("jggl.tpbj.ids".equals(cookie.getName())) {
					String cookieValue = cookie.getValue();
					String[] ids = cookieValue.split("#");
					for (String id : ids) {
						if (!StringUtils.isEmpty(id)) {
							selected.add(id);
						}
					}
				}
			}
		}
		// ���б����С�����л�ȡѡ�����Ա
		String a0000s = this.getPageElement("a0000s").getValue();
		if (!StringUtils.isEmpty(a0000s)) {
			String[] a0000Array = a0000s.split(",");
			for (int i = 0; i < a0000Array.length; i++) {
				selected.add(a0000Array[i]);
			}
		}

		if (selected.size() == 0) {
//			this.setMainMessage("��ѡ����Ա");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
			return EventRtnType.FAILD;
		} else {
			String json = JSON.toJSONString(selected);
			this.getExecuteSG()
					.addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','ͬ���Ƚ�',1500,731,null,'"
							+ this.request.getContextPath() + "',null,{"
							+ "maximizable:false,resizable:false,RMRY:'ͬ���Ƚ�',data:" + json + "},true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}

	
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("ryGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		//��õ�ǰҳ�������  ����  �༭  ������
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("ryGrid").getValue("a0000",this.getPageElement("ryGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("�Ѿ�����");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}

	
}
