package com.insigma.siis.local.business.test;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HList;
import com.insigma.odin.framework.commform.local.sys.BusinessLog;
import com.insigma.odin.framework.util.commform.BuildUtil;
import com.insigma.siis.local.comm.BusinessBSSupport;
import com.insigma.odin.framework.util.commform.BuildUtil.ButtonForToolBar;
import com.insigma.odin.framework.util.commform.BuildUtil.Item;
/**
 * ���̲��ԣ���ӡ
 * @author feng
 *
 */
public class TestImpBS extends BusinessBSSupport{
	public void buildForm() throws AppException {
		if (mdParams.inProc("P0")) {
			form.newToolBar(new BuildUtil.Div() {
				public HList config() throws AppException {
					HList toolbar = BuildUtil.toolbarConfig();
					toolbar.add(BuildUtil.buttonForToolBarConfig("doCommImp").set(ButtonForToolBar.property, "daoru"));
					toolbar.add(BuildUtil.buttonForToolBarConfig("doSave"));
					toolbar.add(BuildUtil.buttonForToolBarConfig("doReset"));
					return toolbar;
				}
			});
		}else if (mdParams.inProc("P1")) {
			form.newDefaultRepToolBar();
		}
		form.newTable(new BuildUtil.Div() {
			public HList config() throws AppException {
				div_1 = BuildUtil.tableConfig("div_1", "��������");
				div_1.add(BuildUtil.colsConfig("query", "psquery", "��Ա��ѯ", "R", true));
				div_1.add(BuildUtil.colsConfig("text", "aac003", "����", "D"));
				div_1.add(BuildUtil.colsConfig("text", "eaz252", "ת����Ŀֵ", p("P0:R,P1:H"),true));
				div_1.add(BuildUtil.colsConfig("date", "aac006", "��������", "D").set(Item.isDateTime, true));
				div_1.add(BuildUtil.colsConfig("select", "aac004", "�Ա�", "D"));
				return div_1;
			}
		});
		if (mdParams.inProc("P0")) {
			form.newGrid(new BuildUtil.Div() {
				public HList config() throws AppException {
					div_2 = BuildUtil.gridConfig("div_2", 240, "pagetoolbar", "10", false);
					div_2.add(BuildUtil.gridColsConfig("select", "aae140", "����", 80));
					div_2.add(BuildUtil.gridColsConfig("number", "aae180", "�ɷѻ���", 80));
					div_2.add(BuildUtil.gridColsConfig("text", "flag", "���", 80,"E"));
					div_2.add(BuildUtil.gridColsConfig("text", "aaz289", "����", 50));
					div_2.add(BuildUtil.gridColsConfig("date", "aac049", "����ʱ��", 80));
					return div_2;
				}});
		}
		form.newTable(new BuildUtil.Div() {
			public HList config() throws AppException {
				div_3 = BuildUtil.tableConfig("div_3", "��������");
				div_3.add(BuildUtil.colsConfig("text", "aab033", "����", "D"));
				return div_3;
			}
		});
		
	}
	/**
	 * ��ʼ������
	 */
	public void initForm() throws AppException {
		hl.retrieve("select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') aac006  from dual");
		div_1.set("aac006", hl.getDate("aac006"));
		if (mdParams.inProc("P0")) {
//			form.setOpItem("aac006", "disabled", "true");
		}
	}
	/**
	 * У��
	 */
	public void doCheck() throws AppException {
		if(mdParams.inProc("P0") && form.paramsCheckWithoutSave("psquery")){
			div_2.retrieve("select * from ac02  where aaz157="+div_1.getLong("aaz157")+"");
			div_3.retrieve("select aac003 aab033 from sbdv_ac20 where aaz157="+div_1.getLong("aaz157")+"");
			div_3.set("aab033", "����");
			
			
		}
		if (mdParams.inProc("P0") && form.paramsCheck("eaz252") ) {
//			div_2.set("flag", "2",1);
			div_2.set("flag", "1",3);
//			throw form.appException("��Ŀֵ�������0");
		}
	}
	/**
	 * ��־����
	 */
	public void doSave() throws AppException {
		BusinessLog.save(form, null, div_1.getLong("aaz157"));
	}
	/**
	 * ���
	 */
	public void doAudit() throws AppException {
		div_1.save("acf7","eaz250=sb_prseno.nextval;aaz160=:aaz157;eaz251='001';eaz252",null,HList.ROWSTATUS_INSERT);
		form.setReflushAfterSave(true);
	}
}
