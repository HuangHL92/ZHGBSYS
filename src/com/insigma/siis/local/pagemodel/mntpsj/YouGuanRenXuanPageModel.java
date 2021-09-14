package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.entity.Fxyp;
import com.insigma.siis.local.business.entity.FxypSJFA;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;

public class YouGuanRenXuanPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		if (fxyp00 != null && !"".equals(fxyp00)) {
			FxypSJFA fxyp = (FxypSJFA) HBUtil.getHBSession().get(FxypSJFA.class, fxyp00);
			if (fxyp == null) {
				this.setMainMessage("�޸�λ��Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getPageElement("gwmcH").setValue(fxyp.getFxyp02());
			
			//�����ж�,����ǻ�ȡ���ɲ������ID����ֱ�ӽ��иɲ������ѯ
			String gbmcSql = (String) this.request.getSession().getAttribute("gbmcSql");
			if(gbmcSql!=null&&!"".equals(gbmcSql)) {
				String gbmcName = (String) this.request.getSession().getAttribute("gbmcName");
				this.getPageElement("rxtjH").setValue(gbmcName);
				this.getExecuteSG().addExecuteCode("setGWXX()");
				//this.setNextEventName("elearningGrid.dogridquery");
				this.getExecuteSG().addExecuteCode("Photo_List.ajaxSubmit('ShowData','pages.mntpsj.YouGuanRenXuan')");
			}else {
				if (fxyp.getFxyp03() != null) {
					this.getPageElement("rxtjH").setValue(fxyp.getFxyp03().replaceAll("\n", "��"));
				}
				this.getExecuteSG().addExecuteCode("setGWXX()");
				//this.setNextEventName("elearningGrid.dogridquery");
				this.getExecuteSG().addExecuteCode("Photo_List.ajaxSubmit('ShowData','pages.mntpsj.YouGuanRenXuan')");
			}
		} else {
			this.setMainMessage("�޸�λ��Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("elearningGrid.dogridquery")
	@NoRequiredValidate
	public int grid1Query(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String querysql = getQuerySql();
		//System.out.println(sql);
		this.pageQuery(querysql, "SQL", start, 20);
		return EventRtnType.SPE_SUCCESS;
	}
	
	private static String RETSQL   = "select  a01.a0000,a01.a0101,a01.a0104,a01.a0107,a0117,a0111a,a0140,a0141,a0144,a0134,a0196,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,a01.a0288,a0192f ";

	
	private String getQuerySql() throws AppException, RadowException {
		String userid = SysManagerUtils.getUserId();
		String temSQL = "select  a01.a0000 ";
		String sid = this.request.getSession().getId();
		String sql = "";
		
		//�����ж�,����ǻ�ȡ���ɲ������ID����ֱ�ӽ��иɲ������ѯ
		String gbmcSql = (String) this.request.getSession().getAttribute("gbmcSql");
		if(gbmcSql!=null&&!"".equals(gbmcSql)) {
			sql = gbmcSql;
			
			String replaceSql2 = "SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a";
			String replaceSql = "select   a01.a0000, a01.a1701 ";
			if(sql.contains(replaceSql2)) {
				sql = sql.replaceFirst(replaceSql2, temSQL);
			}else if (sql.contains(replaceSql)) {
				sql = sql.replaceFirst(replaceSql, temSQL);
			} 
			
		}else {
			sql = getSQL(userid);
		}
		
		if (sql.contains(RETSQL)) {
			sql = sql.replaceFirst(RETSQL, temSQL);
		}
		
		
		//String radioC = this.getPageElement("queryType").getValue();
		String radioC = this.request.getParameter("queryType");
		
		
		String tem_sql = "";
		String resultOptSQL = "";
		String tem_sql2 = "";
		//����в�ѯ
        if("2".equals(radioC)){

    		tem_sql = "insert into cdttttt select a0000,'"+sid+"' sessionid from (select a0000 from  A01SEARCHTEMP where sessionid='"+sid+"' minus {sql})";
		    resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"+sid+"'";

        //׷�Ӳ�ѯ
        }else if("3".equals(radioC)){
        	tem_sql = "update A01SEARCHTEMP set sessionid='"+sid+"temp' where sessionid='"+sid+"'";
 		    resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
 		    tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='"+sid+"temp'";
        }else if("4".equals(radioC)){//�Ų��ѯ
        	tem_sql = "insert into cdttttt select a0000,'"+sid+"' sessionid from ( {sql} )";
    		resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"+sid+"'";

        }else{
        	this.deleteResult(sid);
			resultOptSQL = "insert into A01SEARCHTEMP {sql}";
		}
		
        if(("2".equals(radioC)||"4".equals(radioC))){
  			this.optResult(sql, tem_sql,resultOptSQL);
  		}else if("3".equals(radioC)){
			String	qsql = sql + " union all select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"temp'";

  		    this.optResult(qsql, tem_sql, tem_sql2, resultOptSQL);
  		}else{
			this.optResult(sql, resultOptSQL);

  		}
        
        String querysql = RETSQL + " from A01 a01 join (SELECT sort, a0000 from A01SEARCHTEMP "
        		+ " where sessionid = '"+sid+"') tp   /*"+UUID.randomUUID()+"*/  "
        		+ " on a01.a0000 = tp.a0000 ";
        
        String fxyp00 = this.request.getParameter("fxyp00");
        String a0200 = HBUtil.getValueFromTab("a0200", "HZ_FXYP_SJFA", "fxyp00='"+fxyp00+"'");
        if(!StringUtils.isEmpty(a0200)){
        	String a0000 = HBUtil.getValueFromTab("a0000", "a02", "a0200='"+a0200+"'");
        	querysql = querysql + " and a01.a0000 not in('"+a0000+"') ";
        }
        
        Object flag=this.request.getSession().getAttribute("gbmcFlag");
        if(flag==null||"".equals(flag)) {
            querysql = querysql + CommSQL.OrderByF(userid, null, "001.001", null);
        }else {
        	querysql = querysql+flag;
        }
		return querysql;
	}

	public void deleteResult(String sid){
		try {
			HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='"+sid+"'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void optResult(String sql,String resultOptSQL){
		try {

			String sid = this.request.getSession().getId();
			sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + " ) tmp";
			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	//oracle: ����ѯ�����ݽ�����浽A01SEARCHTEMP��
	private void optResult(String sql,String tmp_sql, String resultOptSQL){
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			//System.out.println(tmp_sql.replace("{sql}", sql));
			stat.executeUpdate(tmp_sql.replace("{sql}", sql));
			stat.executeUpdate(resultOptSQL);
			con.commit();

		} catch (Exception e) {
			if(con!=null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	
	private void optResult(String sql,String tmp_sql,String tem_sql2, String resultOptSQL){
		String sid = this.request.getSession().getId();
		Connection con = null;
			try {
				con = HBUtil.getHBSession().connection();
				con.setAutoCommit(false);
				Statement stat = con.createStatement();
				stat.executeUpdate(tmp_sql);

				sql = "select tmp.a0000,'"+sid+"' sessionid,rownum i from ( " + sql + ") tmp";
				stat.executeUpdate(resultOptSQL.replace("{sql}", sql));
				stat.executeUpdate(tem_sql2);
				con.commit();

			} catch (Exception e) {
				if(con!=null){
					try {
						con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println(resultOptSQL.replace("{sql}", sql));
				e.printStackTrace();
			}
	}
	
	@PageEvent("saveP")
	@NoRequiredValidate
	public int saveP() throws RadowException, AppException {
		String a0000s = this.getPageElement("a0000s").getValue();
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		if (a0000s != null && !"".equals(a0000s)) {
			this.getExecuteSG().addExecuteCode(
					"window.realParent.doAddPerson.queryByNameAndIDS('" + a0000s + "','" + fxyp00 + "','');");
		} else {
			this.setMainMessage("��ѡ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	String getSQL(String userID) throws AppException, RadowException {
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");

		//String fxyp00 = this.getPageElement("fxyp00").getValue();
		String fxyp00 = this.request.getParameter("fxyp00");

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
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '" + a0101 + "%'");
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
		String a0165C = cxtjMap.get("a0165C");
		if (a0165C != null && !"".equals(a0165C)) {
			String[] strs = a0165C.split(",");
			a01sb.append(" and (");
			for (int i = 0; i < strs.length; i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if (i != strs.length - 1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
		}

		// �Ա�
		String a0104 = cxtjMap.get("a0104");
		if (a0104 != null && !"".equals(a0104)) {
			a01sb.append(" and (");
			if ("0".equals(a0104)) {
				a01sb.append(" a01.a0104 is not null ");
			} else {
				a01sb.append(" a01.a0104 = '" + a0104 + "' ");
			}
			a01sb.append(") ");
		}

		// ����
		String a0117 = cxtjMap.get("a0117");
		if (a0117 != null && !"".equals(a0117)) {
			a01sb.append(" and (");
			if ("0".equals(a0117)) {
				a01sb.append(" a01.a0117 is not null ");
			} else if ("1".equals(a0117)) {
				a01sb.append(" a01.a0117 = '01' ");
			} else if ("2".equals(a0117)) {
				a01sb.append(" a01.a0117 <> '01' ");
			}
			a01sb.append(") ");
		}

		// ����
		String a0141 = cxtjMap.get("a0141");
		if (a0141!=null&&!"".equals(a0141)) {
			String[] strs = a0141.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0141 = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
		}

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
			a01sb.append(" and substr(" + jiezsj + "-substr(a01.a0107,1,6),1,2)<lpad('" + ageB + "',2,'0')");
		}

		// ��������
		String a0107A = cxtjMap.get("a0107A");
		String a0107B = cxtjMap.get("a0107B");// ��������
		if (a0107A!=null&&!"".equals(a0107A)) {
			a01sb.append(" and a01.a0107>='" + a0107A + "'");
		}
		if (a0107B!=null&&!"".equals(a0107B)) {
			a01sb.append(" and a01.a0107<='" + a0107B + "'");
		}

		// ѧ��ѧλ
		String xlxw = cxtjMap.get("xlxw");
		if (xlxw != null && !"".equals(xlxw)) {
			String[] strs = xlxw.split(",");
			a01sb.append(" and (");

			String qrz = cxtjMap.get("qrz");
			for (int i = 0; i < strs.length; i++) {
				if(qrz!=null&&!"".equals(qrz)&&"1".equals(qrz)) {
					a01sb.append(" a01.qrzxl like '%" + strs[i] + "%' or a01.qrzxw like '%" + strs[i] + "%' ");
				}else {
					a01sb.append(" a01.zgxl like '%" + strs[i] + "%' or a01.zgxw like '%" + strs[i] + "%' ");
				}
				if (i != strs.length - 1) {
					a01sb.append(" or ");
				}
			}

			a01sb.append(") ");
		}

		// ��ְ��ʱ��
		String a0192fA = cxtjMap.get("a0192fA");// ��ְ��ʱ��
		String a0192fB = cxtjMap.get("a0192fB");// ��ְ��ʱ��
		if (a0192fA!=null&&!"".equals(a0192fA)) {
			a01sb.append(" and a01.a0192f >= '" + a0192fA + "'");
		}
		if (a0192fB!=null&&!"".equals(a0192fB)) {
			a01sb.append(" and a01.a0192f <= '" + a0192fB + "'");
		}

		// ְ����
		String a0221A = cxtjMap.get("a0221A");// ְ����
		if (a0221A!=null&&!"".equals(a0221A) && !"null".equals(a0221A)) {
			a01sb.append(" and a01.a0221 in('" + (a0221A.replace(",", "','")) + "')");
		}

		// ְ����ʱ��
		String a0288A = cxtjMap.get("a0288A");// ����ְ����ʱ��
		String a0288B = cxtjMap.get("a0288B");// ����ְ����ʱ��
		if (a0288A!=null&&!"".equals(a0288A)) {
			a01sb.append(" and a01.a0288>='" + a0288A + "'");
		}
		if (a0288B!=null&&!"".equals(a0288B)) {
			a01sb.append(" and a01.a0288<='" + a0288B + "'");
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
		String a0144age= cxtjMap.get("a0144age");
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
		
		//�ɲ���������
		String gbgzmd= cxtjMap.get("gbgzmd");
		if(gbgzmd!=null&&!"".equals(gbgzmd)) {
			orther_sb.append(" and  a01.a0000 in (select a0000 from historyper where mdid='"+gbgzmd+"' ) ");
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
		
		//רҵ ����
		String a0824= cxtjMap.get("a0824");
		if(a0824!=null&&!"".equals(a0824)) {
			a01sb.append(" and exists( select a08.a0824 from a08 where a08.a0000=a01.a0000 and a08.a0824 like '%"+a0824+"%')");
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
			orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0196_TAG where 1=2 or a0196 in (");
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
		/*
		 * String a0195 = cxtjMap.get("a0195");//ͳ�ƹ�ϵ���ڵ�λ if
		 * (a0195!=null&&!"".equals(a0195)){ a01sb.append(" and ");
		 * a01sb.append("a01.a0195 = '"+a0195+"'"); }
		 * 
		 * 
		 * String a0101 = cxtjMap.get("a0101");//��Ա���� if
		 * (a0101!=null&&!"".equals(a0101)){ a01sb.append(" and ");
		 * a01sb.append("a01.a0101 like '"+a0101+"%'"); }
		 * 
		 * String a0184 = cxtjMap.get("a0184");//���֤�� if
		 * (a0184!=null&&!"".equals(a0184)){ a01sb.append(" and ");
		 * a01sb.append("a01.a0184 like '"+a0184.toUpperCase()+"%'"); }
		 * 
		 * String a0111 = cxtjMap.get("a0111");//���� if (a0111!=null&&!"".equals(a0111)){
		 * a0111 = a0111.replaceAll("(0){1,}$", ""); a01sb.append(" and ");
		 * a01sb.append(" a01.a0111 like '"+a0111+"%' "); }
		 * 
		 * 
		 * 
		 * String a0104 = cxtjMap.get("a0104");//�Ա� if
		 * (a0104!=null&&!"".equals(a0104)&&!"0".equals(a0104)){ a01sb.append(" and ");
		 * a01sb.append("a01.a0104 = '"+a0104+"'"); }
		 * 
		 * String ageA = cxtjMap.get("ageA");//���� String ageB = cxtjMap.get("ageB");//����
		 * if(ageB!=null&&!"".equals(ageB) &&
		 * org.apache.commons.lang.StringUtils.isNumeric(ageB)){//�Ƿ����� ageB =
		 * Integer.valueOf(ageB)+1+""; } String jiezsj = cxtjMap.get("jiezsj");//��ֹʱ��
		 * String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj); String dateStart =
		 * GroupPageBS.getDateformY(ageB, jiezsj);
		 * if(!"".equals(dateEnd)&&!"".equals(dateStart)&&dateEnd.compareTo(dateStart)<0
		 * ){ throw new AppException("���䷶Χ����"); } if(!"".equals(dateStart)){
		 * a01sb.append(" and a01.a0107>='"+dateStart+"'"); } if(!"".equals(dateStart)){
		 * a01sb.append(" and a01.a0107<'"+dateEnd+"'"); }
		 * 
		 * String a0160 = cxtjMap.get("a0160");//��Ա��� if
		 * (a0160!=null&&!"".equals(a0160)){ a01sb.append(" and ");
		 * a01sb.append("a01.a0160 = '"+a0160+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0107A = cxtjMap.get("a0107A");//�������� String a0107B =
		 * cxtjMap.get("a0107B");//�������� if(a0107A!=null&&!"".equals(a0107A)){
		 * a01sb.append(" and a01.a0107>='"+a0107A+"'"); }
		 * if(a0107B!=null&&!"".equals(a0107B)){
		 * a01sb.append(" and a01.a0107<='"+a0107B+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * String a0144A = cxtjMap.get("a0144A");//�μ��й�ʱ�� String a0144B =
		 * cxtjMap.get("a0144B");//�μ��й�ʱ�� if(a0144A!=null&&!"".equals(a0144A)){
		 * a01sb.append(" and a01.a0144>='"+a0144A+"'"); }
		 * if(a0144B!=null&&!"".equals(a0144B)){
		 * a01sb.append(" and a01.a0144<='"+a0144B+"'"); }
		 * 
		 * 
		 * 
		 * String a0141 = cxtjMap.get("a0141");//������ò
		 * if(a0141!=null&&!"".equals(a0141)){ a0141 = a0141.replace(",", "','");
		 * a01sb.append(" and a01.a0141 in('"+a0141+"')");
		 * //a01sb.append(" and a01.a0141='"+a0141+"'"); }
		 * 
		 * 
		 * String a0192a = cxtjMap.get("a0192a");//ְ��ȫ��
		 * if(a0192a!=null&&!"".equals(a0192a)){
		 * a01sb.append(" and a01.a0192a like '%"+a0192a+"%'"); }
		 * 
		 * 
		 * 
		 * String a0134A = cxtjMap.get("a0134A");//�μӹ���ʱ�� String a0134B =
		 * cxtjMap.get("a0134B");//�μӹ���ʱ�� if(a0134A!=null&&!"".equals(a0134A)){
		 * a01sb.append(" and a01.a0134>='"+a0134A+"'"); }
		 * if(a0134B!=null&&!"".equals(a0134B)){
		 * a01sb.append(" and a01.a0134<='"+a0134B+"'"); }
		 * 
		 * 
		 * 
		 * String a0114 = cxtjMap.get("a0114");//������ if
		 * (a0114!=null&&!"".equals(a0114)){ a0114 = a0114.replaceAll("(0){1,}$", "");
		 * a01sb.append(" and a01.a0114 like '"+a0114+"%' "); }
		 * 
		 * 
		 * 
		 * String a0221A = cxtjMap.get("a0221A");//ְ���� String a0221B =
		 * cxtjMap.get("a0221B");//ְ���� if(!StringUtil.isEmpty(a0221A) &&
		 * !StringUtil.isEmpty(a0221B)){ CodeValue dutyCodeValue
		 * =RuleSqlListBS.getCodeValue("ZB09", a0221A); CodeValue duty1CodeValue
		 * =RuleSqlListBS.getCodeValue("ZB09", a0221B);
		 * if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.
		 * getSubCodeValue())){ throw new AppException("ְ���η�Χ������ͬһ������飡"); } //ְ����
		 * ֵԽС ������˼Խ�߼�
		 * if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
		 * throw new AppException("ְ���η�Χ����ȷ�����飡"); } }
		 * if(a0221A!=null&&!"".equals(a0221A)){
		 * a01sb.append(" and a01.a0221<='"+a0221A+"'"); }
		 * if(a0221B!=null&&!"".equals(a0221B)){
		 * a01sb.append(" and a01.a0221>='"+a0221B+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0288A = cxtjMap.get("a0288A");//����ְ����ʱ�� String a0288B =
		 * cxtjMap.get("a0288B");//����ְ����ʱ�� if(a0288A!=null&&!"".equals(a0288A)){
		 * a01sb.append(" and a01.a0288>='"+a0288A+"'"); }
		 * if(a0288B!=null&&!"".equals(a0288B)){
		 * a01sb.append(" and a01.a0288<='"+a0288B+"'"); }
		 * 
		 * String a0117 = cxtjMap.get("a0117");//���� if(a0117!=null&&!"".equals(a0117)){
		 * String[] a0117s = a0117.split(","); if(a0117s.length==1){
		 * if("01".equals(a0117s[0])) a01sb.append(" and a01.a0117 ='01'"); else
		 * a01sb.append(" and a01.a0117 !='01'"); }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * String a1701 = cxtjMap.get("a1701");//���� if(a1701!=null&&!"".equals(a1701)){
		 * a01sb.append(" and a01.a1701 like '%"+a1701+"%'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0192e = cxtjMap.get("a0192e");//��ְ��
		 * if(a0192e!=null&&!"".equals(a0192e)){
		 * a01sb.append(" and a01.a0192e='"+a0192e+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0192cA = cxtjMap.get("a0192cA");//��ְ��ʱ�� String a0192cB =
		 * cxtjMap.get("a0192cB");//��ְ��ʱ�� if(a0192cA!=null&&!"".equals(a0192cA)){
		 * a01sb.append(" and a01.a0192c>='"+a0192cA+"'"); }
		 * if(a0192cB!=null&&!"".equals(a0192cB)){
		 * a01sb.append(" and a01.a0192c<='"+a0192cB+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0165 = cxtjMap.get("a0165");//��Ա�������
		 * if(a0165!=null&&!"".equals(a0165)){ a0165 = a0165.replace(",", "','");
		 * a01sb.append(" and a01.a0165 in('"+a0165+"')"); }
		 * 
		 * 
		 * 
		 * //ְ�� StringBuffer a02sb = new StringBuffer("");
		 * 
		 * String a0216a = cxtjMap.get("a0216a");//ְ������
		 * if(a0216a!=null&&!"".equals(a0216a)){
		 * a02sb.append(" and a02.a0216a like '%"+a0216a+"%'"); }
		 * 
		 * 
		 * 
		 * String a0201d = cxtjMap.get("a0201d");//�Ƿ���ӳ�Ա
		 * if(a0201d!=null&&!"".equals(a0201d)){
		 * a02sb.append(" and a02.a0201d='"+a0201d+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0219 = cxtjMap.get("a0219");//�Ƿ��쵼ְ��
		 * if(a0219!=null&&!"".equals(a0219)){
		 * a02sb.append(" and a02.a0219='"+a0219+"'"); }
		 * 
		 * 
		 * 
		 * 
		 * String a0201e = cxtjMap.get("a0201e");//��Ա���
		 * if(a0201e!=null&&!"".equals(a0201e)){ a0201e = a0201e.replace(",", "','");
		 * a02sb.append(" and a02.a0201e in('"+a0201e+"')"); }
		 * 
		 * 
		 * //���ѧ�� String xla0801b = cxtjMap.get("xla0801b");//���ѧ�� ѧ������ String xla0814 =
		 * cxtjMap.get("xla0814");//��ҵԺУ String xla0824 = cxtjMap.get("xla0824");//רҵ
		 * xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");
		 * 
		 * 
		 * //���ѧλ String xwa0901b = cxtjMap.get("xwa0901b");//���ѧλ ѧλ���� String xwa0814 =
		 * cxtjMap.get("xwa0814");//��ҵԺУ String xwa0824 = cxtjMap.get("xwa0824");//רҵ
		 * xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //ְ�� String a0601 = cxtjMap.get("a0601");//רҵ������ְ�ʸ�
		 * 
		 * if (a0601!=null&&!"".equals(a0601)) { boolean is9 = false; orther_sb.
		 * append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' ");
		 * StringBuffer like_sb = new StringBuffer(""); String[] fArray =
		 * a0601.split(","); for (int i = 0; i < fArray.length; i++) {
		 * 
		 * if("9".equals(fArray[i])){
		 * like_sb.append(" a0601 is null or a0601='999' or ");//��ְ�� is9 = true; }else{
		 * like_sb.append(" a0601 like '%" + fArray[i] + "' or "); } }
		 * like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
		 * orther_sb.append(" and (" + like_sb.toString() + ")"); orther_sb.append(")");
		 * if(is9){//��ְ�� orther_sb.
		 * append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')"
		 * ); } orther_sb.append(")"); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //��ͥ��Ա String a3601 = cxtjMap.get("a3601");//���� String a3611 =
		 * cxtjMap.get("a3611");//������λ��ְ�� if ((a3601!=null&&!"".equals(a3601)) ||
		 * (a3611!=null&&!"".equals(a3611))) {
		 * orther_sb.append(" and a01.a0000 in (select a0000 from a36 where 1=1 ");
		 * if(a3601!=null&&!"".equals(a3601)){
		 * orther_sb.append(" and a3601 like '"+a3601+"%'"); }
		 * 
		 * if(a3611!=null&&!"".equals(a3611)){
		 * orther_sb.append(" and a3611 like '%"+a3611+"%'"); }
		 * 
		 * orther_sb.append(")"); }
		 */
		StringBuilder lablesql = new StringBuilder();
		/*
		 * StringBuilder lablesql = new StringBuilder(); //�ɲ����� for(String lableid :
		 * cxtjMap.keySet()){ if(lableid.indexOf("attr")==0){ if(lableid.contains(",")){
		 * lablesql.append(cxtjMap.get(lableid)); String[] lableids =
		 * lableid.split(","); for(String lid : lableids){ if(!"".equals(lid)){
		 * 
		 * } }
		 * 
		 * }else{ lablesql.append(" and "+lableid+"='1' "); } } }
		 * if(lablesql.length()>0){ lablesql.insert(0,
		 * " select a0000 from ATTRIBUTEN where 1=1 "); }
		 */

		String a0163 = cxtjMap.get("a0163") == null ? "" : cxtjMap.get("a0163");// ��Ա״̬
		String qtxzry = cxtjMap.get("qtxzry") == null ? "" : cxtjMap.get("qtxzry");
		String a02_a0201b_sb = cxtjMap.get("a02_a0201b_sb") == null ? "" : cxtjMap.get("a02_a0201b_sb");
		String finalsql = getCondiQuerySQL(userID, a01sb,a01sb1,a02sb, a02_a0201b_sb, orther_sb, a0163, "0", lablesql);
		return finalsql;
	}

	public static String getCondiQuerySQL(String userid, StringBuffer a01sb, String a01sb1,StringBuffer a02sb, String a02_a0201b_sb,
			StringBuffer orther_sb, String a0163, String qtxzry, StringBuilder lablesql) {

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
		
		}else {
			a02str = " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0201b in(select b0111 from b01 where b0111!='-1')  "
					+ a02sb + ")   " + " and a01.status!='4' " + a0163sql;
		}
		String retsql = "";
		if (lablesql == null || "".equals(lablesql.toString())) {
			retsql = RETSQL + " from A01 a01 "
					+ " where  1=1 " + orther_sb +
					// xzry +
					a02str + a01sb;
		} else {
			retsql = RETSQL + " from A01 a01  inner join ("
					+ lablesql + ") b on  b.a0000=a01.a0000 " + " where  1=1 " + orther_sb +
					// xzry +
					a02str + a01sb;
		}
		// a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a
		return retsql;
	}

	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
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
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("elearningGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick(String a0000) throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		//��õ�ǰҳ�������  ����  �༭  ������
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		//String a0000=this.getPageElement("elearningGrid").getValue("a0000",this.getPageElement("elearningGrid").getCueRowIndex()).toString();
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
	
	
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		try {
			String querysql = getQuerySql();
			String limit = this.request.getParameter("limit");
			String start = this.request.getParameter("start");
			//System.out.println(sql);
			this.pageQuery(querysql, "SQL", Integer.valueOf(start), Integer.valueOf(limit));
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.SPE_SUCCESS;
	}

}
