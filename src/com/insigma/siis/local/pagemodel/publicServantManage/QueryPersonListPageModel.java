package com.insigma.siis.local.pagemodel.publicServantManage;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgImpPageModel;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class QueryPersonListPageModel extends PageModel {

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType="0";//1�����������ѯ2�����ť��ѯ
	public static String tag= "0";//�޸Ķ���0δִ��1��ִ��
	private final static int ON_ONE_CHOOSE=-1;
	private final static int CHOOSE_OVER_TOW=-2;
	
	public QueryPersonListPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "true");
			}else{
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "false");
			}
			if(area!=null ) { 
				if(area[2].equals("1")){
					area[2]="picOrg";
				}else if(area[2].equals("2")){
					area[2]="picInnerOrg";
				}else{
					area[2]="picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ҳ���ʼ��
	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('persongrid').view.refresh();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�������ѯ�¼�
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		this.getPageElement("checkedgroupid").setValue(id);
		String isContain = this.getPageElement("isContain").getValue();
		String xzry = this.getPageElement("xzry").getValue();
		String lsry = this.getPageElement("lsry").getValue();
		String ltry = this.getPageElement("ltry").getValue();
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		m.put("xzry", "1".equals(xzry));
		m.put("lsry", "1".equals(lsry));
		m.put("ltry", "1".equals(ltry));
		this.request.getSession().setAttribute("queryConditions", m);
		if("1".equals(xzry)&&"0".equals(isContain)&&"0".equals(lsry)&&"0".equals(ltry)){//�Ƿ�������жϡ� ֻ����ְ��Ա�������еģ�
			this.getPageElement("isContainHidden").setValue("0");
		}else{
			this.getPageElement("isContainHidden").setValue("1");
		}
		
		if("1".equals(isContain)){//�Ƿ�����¼�
			this.request.getSession().setAttribute("isContain", true);
		}else{
			this.request.getSession().setAttribute("isContain", false);
		}
		try {
			//������ְ��Ա ��ʷ��Ա  ������Ա ְ��Ϊ�յ�������ְ��Ա
			if("X001".equals(id)||"X002".equals(id)||"X003".equals(id)||"X0010".equals(id)){
				//���ò�ѯ��ʽΪ�����
				this.request.getSession().setAttribute("queryType", "1");
				this.setNextEventName("persongrid.dogridquery");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String sql="from B01 where B0111='"+id+"'";
			List list = HBUtil.getHBSession().createQuery(sql).list();
			B01 b01 =(B01) list.get(0);
			if(b01 == null){
				throw new RadowException("��ѯ����");
			}
//			this.getPageElement("optionGroup").setValue(b01.getB0101());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		//���ò�ѯ��ʽΪ�����
		this.request.getSession().setAttribute("queryType", "1");
		//this.getExecuteSG().addExecuteCode("alert(tree.getNodeById('"+id+"').attributes.editable)");
		this.setNextEventName("persongrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//ˢ���б�
	@PageEvent("persongrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//String userorgid = SysManagerUtils.getUserOrgid();
		List alist = new ArrayList();
		String sql="";
		String sql2="";
		queryType=(String) this.request.getSession().getAttribute("queryType");
		String nowdata = DateUtil.getcurdate();
		String authoritySQL = "select t.b0111 from COMPETENCE_USERDEPT t where t.userid='"+SysManagerUtils.getUserId()+"' ";// and (t.type='1' or t.type='0')
		String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
		String comSQL = "select t.a0000,t.a0101,t.a0104,GET_BIRTHDAY(t.a0107,'"+nowdata+"') age,t.a0117,t.a0141,t.a0192a,t.a0148" +
				",t.a0107,qrzxl,zzxl,t.a0140,t.a0134,t.a0165,t.a0160,a0120,t.a0192d,t.a0121,t.a0184 from a01 t";
		if(queryType.equals("1")){
			String groupid = this.getPageElement("checkedgroupid").getValue();
			
			//1��ְ��Ա 2������Ա 3������Ա 4��ȥ�� 5������Ա       ������ְ��Ա  ��ʷ��Ա��ɾ����  ������Ա ְ��Ϊ�յ�������ְ��Ա
			if("X001".equals(groupid)){//������ְ��Ա
				sql2=comSQL+" where exists " +
						"(select 1 from a02 t2 where t2.a0000=t.a0000) " +
						"and not exists " +
						"(select 1 from a02 t2 where t2.a0000=t.a0000 and (t2.a0201b !='-1' and a0255='1')) " +//ְ��Ϊ������λ����ְΪ��ְ�� ����Ҫѡ����
						"   and t.status='1' ";//and t.tbrjg='"+userorgid+"'
				this.request.getSession().setAttribute("allSelect", sql2);
				this.pageQuery(sql2, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
			}else if("X002".equals(groupid)){//������Ա
				sql2=comSQL+" where t.status='3' ";//and t.tbrjg='"+userorgid+"'
				this.request.getSession().setAttribute("allSelect", sql2);
				this.pageQuery(sql2, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
			}else if("X003".equals(groupid)){//��ʷ��Ա
				sql2=comSQL+" where t.status='2' ";//and t.tbrjg='"+userorgid+"'
				this.request.getSession().setAttribute("allSelect", sql2);
				this.pageQuery(sql2, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
			}else if("X0010".equals(groupid)){//ְ��Ϊ�յ�������ְ��Ա
				sql2=comSQL+" where not exists " +
						"(select 1 from a02 t2 where t2.a0000=t.a0000)   and t.status!='4' ";//and t.tbrjg='"+userorgid+"'
				this.request.getSession().setAttribute("allSelect", sql2);
				this.pageQuery(sql2, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
			}
			
			
			//��ͨ������ѯ
			Map<String, Boolean> m = (Map<String, Boolean>)this.request.getSession().getAttribute("queryConditions");
			boolean isContain = (Boolean)this.request.getSession().getAttribute("isContain");
			String a0201bsql = "";
			String ordersql = "";
			String a01Orgidsql = "";
			if(isContain){// 
				a0201bsql = "a0201b like '"+groupid+"%'";
				a01Orgidsql = "t.orgid like '"+groupid+"%'";
				ordersql = " order by t.a0148,(select max(sortid) from b01,a02  where t.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
			}else{
				a0201bsql = "a0201b='"+groupid+"'";
				a01Orgidsql = "t.orgid='"+groupid+"'";
				ordersql = " order by (select max(a0225) from a02 where t.a0000=a02.a0000 and a02.a0201b='"+groupid+"')";
			}
			if(m.get("xzry")){
				sql2=comSQL+"  where  status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and "+a0201bsql+" and a0255='1' and exists ("+authoritySQL+" and b.a0201b=t.b0111)) " +//and exists ("+authoritySQL+" and b.a0201b=t.b0111)
					"  and not exists ("+personViewSQL+" where cu.a0000=t.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') " ;
			}else{
				sql2=comSQL+"  where  1=2";
			}
			
			if(m.get("ltry")){
				sql2=sql2+" union " +
				comSQL+" where t.status='3' and not exists ("+personViewSQL+" where cu.a0000=t.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') " +
						" and "+a01Orgidsql +" and t.orgid in("+authoritySQL+")";
			}
			if(m.get("lsry")){
				sql2=sql2+" union " +
				comSQL+" where t.status='2' and not exists ("+personViewSQL+" where cu.a0000=t.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') " +
						" and "+a01Orgidsql +" and t.orgid in("+authoritySQL+")";
			}
			
			sql2 = "select * from ("+sql2 +") t"+ ordersql;
			this.request.getSession().setAttribute("allSelect", sql2);
			this.pageQuery(sql2, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
			
			
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String loginname = user.getLoginname();
		if(queryType.equals("2")){
			//String b0111= SysManagerUtils.getUserOrgid();
			String name = this.getPageElement("a0101A").getValue();
			String id = this.getPageElement("a0184A").getValue();
			
			String sql4 = comSQL+" where 1=1 and status = '1'";
			if(name.equals("") && id.equals("")){
			}else{
				if(!name.equals("")){
					 sql4 = sql4+" and (t.a0101 like '"+name+"%' or t.a0102 like '"+name.toUpperCase()+"%')";
				}
				if(!id.equals("")){
					 sql4 = sql4+" and t.a0184='"+id+"'";
				}
			}
			sql4=sql4+" and exists (select b.a0000 from a02 b where  b.a0000=t.a0000 and exists (select 1 from b01 c where c.b0111=b.a0201b and exists ("+authoritySQL+" and t.b0111=c.b0111) ) )" +//and b0111 in("+authoritySQL+")
					"  and not exists ("+personViewSQL+" where cu.a0000=t.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"')" +
							" order by t.a0148,(select max(sortid) from b01,a02  where t.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1') ";//and c.b0111 like '"+b0111+"%'     ��Ҫ��ӻ���Ȩ��
//			System.out.println(sql4);
			this.request.getSession().setAttribute("allSelect", sql4);
			this.pageQuery(sql4, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		if(queryType.equals("3")){
			StringBuffer sb = new StringBuffer();
			String a0000s = this.getPageElement("viewValue").getValue();
			String[] num = a0000s.split(",");
			for(int i=0;i<num.length;i++){
				sb.append("'"+num[i]+"',");
			}
			String id = sb.toString();
			id = id.substring(0, id.length()-1);
			String sql5 = comSQL+" where t.a0000 in ("+id+") ";
			CommonQueryBS.systemOut(sql5);
			this.request.getSession().setAttribute("allSelect", sql5);
			this.pageQuery(sql5, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		this.createPageElement("persongrid", ElementType.GRID, false).setValueList(null);
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	
	private String hasChildren(String id){
		String sql="from B01 b where B0121='"+id+"' order by sortid";// -1������ְ��Ա
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	//��ʼ����֯������
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {//+ "\" ,\"leaf\" :\"" + hasChildren	
		String node = this.getParameter("node");
		String sql="from B01 b where B0121='"+node+"' order by sortid";// B0121='-1' ������ְ��Ա
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//�õ���ǰ��֯��Ϣ
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<B01> choose = new ArrayList<B01>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql2="from B01 where B0111='"+node+"'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j).getB0111().equals(groups.get(i).getB0121())){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j).getB0111().equals(list.get(i).getB0111())){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		String companyOrgImg = request.getContextPath()+"/pages/sysorg/org/images/companyOrgImg2.png";
		String insideOrgImg = request.getContextPath()+"/pages/sysorg/org/images/insideOrgImg1.png";
		String groupOrgImg = request.getContextPath()+"/pages/sysorg/org/images/groupOrgImg1.png";
		String path = companyOrgImg;
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (B01 group : list) {
				if(i==0 && last==1) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ "\" ,\"cls\" :\"folder\",");
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {//f
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
							jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}]");
				} else {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	//��ѯ��ť����¼�
	@PageEvent("btn1.onclick")
	public int selectAll() throws RadowException, PrivilegeException{
		this.request.getSession().setAttribute("queryType", "2");
		this.getPageElement("checkedgroupid").setValue("");
		this.setNextEventName("persongrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//���������в鿴��ť�¼�
	@PageEvent("view")
	public int view(String a0000s) throws RadowException, PrivilegeException{
		this.request.getSession().setAttribute("queryType", "3");
		this.getPageElement("viewValue").setValue(a0000s);
		this.setNextEventName("persongrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(strHint); // ������ʾ��Ϣ
	}
	
	/*//ɾ����ť����¼�
	@PageEvent("btn2.onclick")
	@GridDataRange
	public int deletePerson() throws RadowException {
		String personid = this.getPageElement("persongrid").getValue("a0000",this.getPageElement("persongrid").getCueRowIndex()).toString();
		if(personid.trim().equals("")||personid==null){
			throw new RadowException("��ѡ��Ҫɾ������Ա!");
		}
		
		dialog_set("deleteconfirm","ȷ��ɾ��ѡ�еļ�¼��");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
//	//ɾ���¼�
//	@PageEvent("deleteconfirm")
//	@GridDataRange
//	@Transaction
//	public int deleteconfirm() throws RadowException, SQLException {
//		Map map = this.getRequestParamer();
//		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
//		String personid = this.getPageElement("persongrid").getValue("a0000",index).toString();
//		String sql1="delete from a01 where a0000='" + personid+"'";
//		String sql2="delete from a02 where a0000='" + personid+"'";
//		HBUtil.getHBSession().createSQLQuery(sql1).executeUpdate();
//		HBUtil.getHBSession().createSQLQuery(sql2).executeUpdate();
//		this.getExecuteSG().addExecuteCode("radow.doEvent('persongrid.dogridquery')");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	

	//��������
	@PageEvent("warnWinBtn.onclick")
	public int warnWin() throws RadowException {
	    this.openWindow("warnWin", "pages.publicServantManage.WarnWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��д����ʱ��
	@PageEvent("exportLrmxBtn.onclick")
	public int expWin() throws RadowException {
		int i = choose("persongrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("��ѡ��Ҫ��������Ա");
			return EventRtnType.FAILD;
		}
		this.openWindow("expTimeWin", "pages.publicServantManage.ExpTimeWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��д������ӡʱ��
	@PageEvent("batchPrint.onclick")
	public int batchPrintTime() throws RadowException, AppException{
		int i = choose("persongrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("���Ȳ�ѯ��Ա��Ϣ������ѡҪ������ӡ����Ա��");
			return EventRtnType.FAILD;
		}
		this.openWindow("batchPrintTimeWin", "pages.publicServantManage.batchPrintTimeWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		String conp = this.request.getContextPath();
		String id = this.getPageElement("persongrid").getValue("a0000",this.getPageElement("persongrid").getCueRowIndex()).toString();
		this.getExecuteSG().addExecuteCode("addTab('','"+id+"','"+conp+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(id);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	@PageEvent("loadadd.onclick")
	@NoRequiredValidate
	public int openUpdateWin(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		this.getExecuteSG().addExecuteCode("addTab('��Ա��������','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("loadaddnew.onclick")
	@NoRequiredValidate
	public int openAddWin(String id)throws RadowException{
		this.setRadow_parent_data("add");
		this.openWindow("addwinnew", "pages.publicServantManage.AddPersonNew");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("personsort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String pageInfo)throws RadowException{
		String[] pfs = pageInfo.split(",");
		int pn = Integer.valueOf(pfs[1]);
		int pSize = Integer.valueOf(pfs[0]);
		
		List<HashMap<String,String>> list = this.getPageElement("persongrid").getStringValueList();
		int page = list.size();
		String a0200 = this.getPageElement("checkedgroupid").getValue();		
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0;
			if(pn>1){
				i = pSize*pn;
			}
			for(HashMap<String,String> m : list){
				String a0000 = m.get("a0000");
				HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0200+"'");
				i++;
			}
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ɾ���¼�
	@PageEvent("deletePersonBtn.onclick")
	@GridDataRange
	public int deletePerson() throws RadowException, AppException {
		List<HashMap<String,Object>> list = this.getPageElement("persongrid").getValueList();
		int countNum = 0;
		StringBuffer a0000 = new StringBuffer();
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("personcheck");
			if (check1!= null && check1.equals(true)) {
				a0000.append("'").append(map.get("a0000")==null?"":map.get("a0000").toString()).append("',");//����ѡ����Ա�����װ���á������ָ�
				countNum++;
			}
		}
		if(countNum==0){
			throw new AppException("�빴ѡ��Ա��");
		}
//		else if(countNum>1){
//			throw new AppException("�����ѡһ�˽��в�����");
//		}
		if(a0000==null || a0000.toString().trim().equals("")){
			throw new AppException("���ݻ�ȡ�쳣��");
		}
		this.setRadow_parent_data(a0000.toString());
	    this.openWindow("deletePersonWin", "pages.publicServantManage.DeletePersonPage");
		return EventRtnType.NORMAL_SUCCESS;
		}
	//ɾ���ɹ�
	@PageEvent("delete")
	public int delete() throws RadowException {
	    this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��Ա�޸�
	@PageEvent("modifyBtn.onclick")
	public int modify() throws RadowException, AppException {
		List<HashMap<String,Object>> list = this.getPageElement("persongrid").getValueList();
		int countNum = 0;
		String a0000 = null;
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("personcheck");
			if (check1!= null && check1.equals(true)) {
				a0000=map.get("a0000")==null?"":map.get("a0000").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new AppException("�빴ѡ��Ա��");
		}else if(countNum>1){
			throw new AppException("�����ѡһ�˽��в�����");
		}
		if(a0000==null || a0000.trim().equals("")){
			throw new AppException("���ݻ�ȡ�쳣��");
		}
		this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(a0000.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����޸�
	@PageEvent("betchModifyBtn.onclick")
	@GridDataRange
	public int betchModify() throws RadowException{  //�򿪴��ڵ�ʵ��
		betchModifyPageModel.type=2;
		HBSession sess = HBUtil.getHBSession();
		Object obj = this.getPageElement("checkAll").getValue();
		if("1".equals(obj)){
			String allSelect = (String)this.request.getSession().getAttribute("allSelect");
			int i = choose("persongrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				this.setMainMessage("����ѡ��Ҫ�����޸ĵ���Ա");
				return EventRtnType.FAILD;
			}
			if(StringUtil.isEmpty(allSelect)){
				this.setMainMessage("����ѡ��Ҫ�����޸ĵ���Ա");
				return EventRtnType.FAILD;
			}
			this.setRadow_parent_data(allSelect+"@1");
			this.openWindow("betchModifyWin","pages.publicServantManage.betchModify");//�¼��������Ĵ򿪴����¼�
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			int i = choose("persongrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				this.setMainMessage("����ѡ��Ҫ�����޸ĵ���Ա");
				return EventRtnType.FAILD;
			}
			if(i==CHOOSE_OVER_TOW || i>=0){
				StringBuffer ids = new StringBuffer();
				PageElement pe = this.getPageElement("persongrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							//�ж��Ƿ����޸�Ȩ�ޡ�
							String a0000 = map.get("a0000").toString();
							String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
									" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
									" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
							String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
							List elist = sess.createSQLQuery(editableSQL).list();
							List elist2 = sess.createSQLQuery(editableSQL2).list();
							if(elist2==null||elist2.size()==0){
								if(elist!=null&&elist.size()>0){
									
								}else{
									ids.append(map.get("a0000").toString()).append(",");
								}
							}else{
								ids.append(map.get("a0000").toString()).append(",");
							}
						}
					}
				}
				if(ids.length()==0){
					this.setMainMessage("��ѡ��Ա���ɲ�����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String allids = ids.substring(0,ids.length()-1);
				this.setRadow_parent_data(allids);
//				System.out.println(ids.toString());
				this.openWindow("betchModifyWin","pages.publicServantManage.betchModify");//�¼��������Ĵ򿪴����¼�
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	//����Lrm
		@PageEvent("exportLrmBtn.onclick")
		@GridDataRange
		public int exportLrm() throws RadowException{ 
			int i = choose("persongrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				this.setMainMessage("��ѡ��Ҫ��������Ա");
				return EventRtnType.FAILD;
			}
			if(i==CHOOSE_OVER_TOW){
				String a0000 = "";
				PageElement pe = this.getPageElement("persongrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							a0000=this.getPageElement("persongrid").getValue("a0000",j).toString();
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//							System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
										byte[] data = new byte[1024];
										while (is.read(data) != -1) {
											fos.write(data);
										}
										fos.close();
										is.close();
									}
								}
//								String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//								p = rt.exec(cmdd);
//								p.waitFor();
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//						String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//						p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
//				System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			}else {
				PageElement pe = this.getPageElement("persongrid");
				String a0000 ="";
				if(pe!=null){
					StringBuffer ids = new StringBuffer();
					List<HashMap<String, Object>> list = pe.getValueList();
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							ids.append(this.getPageElement("persongrid").getValue("a0000",j).toString());
						}
					}
					a0000 = ids.toString();
				}				
				String lrm = createLrmStr(a0000);
				String name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
				String zippath =ExpRar.expFile();
//				System.out.println(zippath);
				String infile ="";
				try {
					FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
					List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
					if(list15.size()>0){
						A57 a57 = list15.get(0);
						if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
							File f = new File(zippath + name+".pic");
							FileOutputStream fos = new FileOutputStream(f);
							InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
							byte[] data = new byte[1024];
							while (is.read(data) != -1) {
								fos.write(data);
							}
							fos.close();
							is.close();
						}
					}
					infile =zippath.substring(0,zippath.length()-1)+".zip" ;
					Runtime rt = Runtime.getRuntime();
					Process p = null;
//					String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//					p = rt.exec(cmdd);
//					p.waitFor();
//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//					p = Runtime.getRuntime().exec(cmd);
					SevenZipUtil.zip7z(zippath, infile, null);
				} catch (Exception e) {
					e.printStackTrace();
					this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
				}
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			}
			return EventRtnType.NORMAL_SUCCESS;	
		}
		
		//����Pdf
		@PageEvent("exportPdfBtn.onclick")
		@GridDataRange
		public int exportPdf() throws RadowException{ 
			int i = choose("persongrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				this.setMainMessage("��ѡ��Ҫ��������Ա");
				return EventRtnType.FAILD;
			}
			if(i==CHOOSE_OVER_TOW){
				String a0000 = "";
				PageElement pe = this.getPageElement("persongrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							a0000=this.getPageElement("persongrid").getValue("a0000",j).toString();
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//							System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
										byte[] data = new byte[1024];
										while (is.read(data) != -1) {
											fos.write(data);
										}
										fos.close();
										is.close();
									}
								}
								SevenZipUtil.zzbRmb(zippath, name);
//								String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//								p = rt.exec(cmdd);
//								p.waitFor();
								File dec = new File(zippath);
								File[] files = dec.listFiles();
								for (File f0 : files) {
									if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
										f0.delete();
									}
								}
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//						String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//						p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
//				System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			}else {
				PageElement pe = this.getPageElement("persongrid");
				String a0000 ="";
				if(pe!=null){
					StringBuffer ids = new StringBuffer();
					List<HashMap<String, Object>> list = pe.getValueList();
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							ids.append(this.getPageElement("persongrid").getValue("a0000",j).toString());
						}
					}
					a0000 = ids.toString();
				}
				
				String lrm = createLrmStr(a0000);
				String name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
				String zippath =ExpRar.expFile();
//				System.out.println(zippath);
				String infile ="";
				try {
					FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
					List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
					if(list15.size()>0){
						A57 a57 = list15.get(0);
						if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
							File f = new File(zippath + name+".pic");
							FileOutputStream fos = new FileOutputStream(f);
							InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
							byte[] data = new byte[1024];
							while (is.read(data) != -1) {
								fos.write(data);
							}
							fos.close();
							is.close();
						}
					}
					infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//					Runtime rt = Runtime.getRuntime();
//					Process p = null;
					SevenZipUtil.zzbRmb(zippath, name);
//					String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//					p = rt.exec(cmdd);
//					p.waitFor();
					File dec = new File(zippath);
					File[] files = dec.listFiles();
					for (File f0 : files) {
						if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
							f0.delete();
						}
					}
//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";					
//					p = Runtime.getRuntime().exec(cmd);
					SevenZipUtil.zip7z(zippath, infile, null);
				} catch (Exception e) {
					this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
				}
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			}
			return EventRtnType.NORMAL_SUCCESS;	
		}
	
	//����Lrmx
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String time) throws RadowException{ 
		int i = choose("persongrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("��ѡ��Ҫ��������Ա");
			return EventRtnType.FAILD;
		}
		if(i==CHOOSE_OVER_TOW){
			String a0000 = "";
			PageElement pe = this.getPageElement("persongrid");
			if(pe!=null){
				List<HashMap<String, Object>> list = pe.getValueList();
				String zippath =ExpRar.expFile();
				String infile ="";
				String name ="";
				Runtime rt = Runtime.getRuntime();
				Process p = null;
				for(int j=0;j<list.size();j++){
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("personcheck");
					if(usercheck.equals(true)){
						a0000=this.getPageElement("persongrid").getValue("a0000",j).toString();
						PersonXml per = createLrmxStr(a0000,time);
						name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//						System.out.println(zippath);
						try {
							FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
//							String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//							p = rt.exec(cmdd);
//							p.waitFor();
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
				}
				try {
					infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//					p = Runtime.getRuntime().exec(cmd);
					SevenZipUtil.zip7z(zippath, infile, null);
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				} catch (Exception e) {
					this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
				}
			}
//			System.out.println(ids.toString());
			return EventRtnType.NORMAL_SUCCESS;
		}else{
		String a0000 = "";
		PageElement pe = this.getPageElement("persongrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			String zippath =ExpRar.expFile();
			String infile ="";
			String name="";
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("personcheck");
				if(usercheck.equals(true)){
					a0000=this.getPageElement("persongrid").getValue("a0000",j).toString();
					PersonXml per = createLrmxStr(a0000,time);
					name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//						System.out.println(zippath);
					try {
						FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
						A01 a01log = new A01();
						new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
			}
			try {
				infile =zippath+name+".lrmx" ;
				//SevenZipUtil.zip7z(zippath, infile, null);
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			} catch (Exception e) {
				this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
			}
		}
		}
//			System.out.println(ids.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˽�з������Ƿ�ѡ���û�
	 * 
	 * @throws RadowException
	 */
	private int choose(String gridid,String checkId) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checkId);
			if(check1==null){
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// û��ѡ���κ��û�
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// ѡ�ж���һ���û�
		}
		return number;// ѡ�еڼ����û�
	}
	
	/**
	 * ����Lrm�ļ���Ĭ�ϴ�ӡ��������Ϣ
	 * @param ids
	 * @return
	 */
	public String createLrmStr(String ids){
		return createLrmStr(ids,true);
	}
	
	public String createLrmStr(String ids,boolean falg){
		return createLrmStr(ids,falg,null);
	}

	/**
	 * ����Lrm�ļ�
	 * @param ids ��ԱID a0000
	 * @param falg �Ƿ��ӡ��������Ϣ��true-��ӡ��������Ϣ
	 * @return
	 */
	public String createLrmStr(String ids,boolean falg,String printTime){
		
		String useTime = printTime;
		String a0000 = ids;
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		DBType cueDBType = DBUtil.getDBType();
		String str ="";
		String jiatingchengyuan="";
		String laststr2 = "";
		String laststr1 ="";
		if(cueDBType==DBType.MYSQL){
			ResultSet rs = null;
			try {
				rs = HBUtil.getHBSession().connection().prepareStatement("select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''), ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
						+"from a01 t  where t.a0000='"+a0000+"'").executeQuery();
				if(rs != null && rs.next()){
					str = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			str = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''), ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
//					+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();	
			jiatingchengyuan = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , replace(group_concat(t.a3604a order by sortid), ',', '@') , '|\",\"' , replace(group_concat(t.a3601 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3607 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3627 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3611 order by sortid), ',', '@') , '|\"')  "
				 +"from (select a0000, ifnull((select b.code_name from code_value b where b.code_type = 'GB4761' and b.code_value = a3604a and b.code_status = '1'), '#') a3604a, ifnull(a3601, '#') a3601, ifnull(a3607, '#') a3607, ifnull((select b.code_name from code_value b where b.code_type = 'GB4762' and b.code_value = a3627 and b.code_status = '1'), '#') a3627, ifnull(a3611, '#') a3611, sortid from a36 ) t"
				 +" where t.a0000 = '"+a0000+"'"
				 +"order by t.sortid").uniqueResult();
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("+useTime+") , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
			
			laststr1 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"',max(ifnull(t.a0192a,'')), '\"') from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		}else{
			str = (String) HBUtil.getHBSession().createSQLQuery("select to_char('\"'||t.a0101||'\",\"'||( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1')||'\",\"'||T.A0107||'\",\"'||"
					+"( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1')||'\",\"'||T.A0111A||'\",\"'||replace(replace(t.a0140, '(', ''), ')', '')||'\",\"'||"
					+"t.a0128||'\",\"'|| T.a0114A||'\",\"'|| T.a0134||'\",\"'||"
					+"t.qrzxl||'#'||t.qrzxw||'@'||t.zzxl||'#'||t.zzxw||"
					+"'\",\"'||t.qrzxlxx||'#'||t.qrzxwxx||'@'||t.zzxlxx||'#'||t.zzxwxx||'\",\"'||"
					+"T.A0196||'\",\"\",\"\",\"\",\"\",\"'||t.a0187a||'\",\"'||"
					+"t.A1701||'\",\"'||t.A14Z101||'\",\"'||t.A15Z101||'\"')"
					+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();
			jiatingchengyuan = (String) HBUtil.getHBSession().createSQLQuery("select to_char('\"' || replace(wm_concat(t.a3604a), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3601), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3607), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3627), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3611), ',', '@') || '|\"')"
				 +" from (select a0000,  nvl( (select b.code_name from code_value b where b.code_type = 'GB4761'  and b.code_value = a3604a and b.code_status = '1'),'#') a3604a,  nvl(a3601,'#')a3601,  nvl(a3607,'#')a3607, nvl( (select b.code_name from code_value b  where b.code_type = 'GB4762' and b.code_value = a3627 and b.code_status = '1'),'#') a3627,nvl(a3611,'#')a3611,sortid from a36 order by a36.sortid)  t"
				 +" where t.a0000 = '"+a0000+"'").uniqueResult();
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("+useTime+")||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
			
			laststr1 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a0192a,''))|| '\"' from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		}
//		System.out.println(str);
		String count =    HBUtil.getHBSession().createSQLQuery("Select Count(1) From a36 t where t.a0000='"+a0000+"'").list().get(0).toString();
		String append ="";
		if(Integer.valueOf(count)<13){
			for(int j=6 ;j>Integer.valueOf(count)-1;j--){
				append+="@";
			}
		}
		jiatingchengyuan=jiatingchengyuan.replace("#", "");
		CommonQueryBS.systemOut(jiatingchengyuan.replace("|",append));
//		System.out.println(laststr1);
//		System.out.println(str+","+jiatingchengyuan.replace("|",append)+","+laststr);
		//�Ƿ��ӡ��������Ϣ
		if(!falg){
			if(cueDBType==DBType.MYSQL){
				if(StringUtil.isEmpty(useTime)){
					laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
				}else{
					laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("+useTime+") , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
				}
			}else{
				if(StringUtil.isEmpty(useTime)){
					laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
				}else{
					laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("+useTime+")||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
				}
			}
			
		}
		
		String lrm = str+","+jiatingchengyuan.replace("|",append)+","+laststr1+","+laststr2;
		return lrm;
	}
	
	public PersonXml createLrmxStr(String ids,String time){
		String a0000 = ids;
//		String content = "";
//		try {
//			content = QueryPersonListBS.XmlContentBuilder(QueryPersonListBS.getObjXml(a0000));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return content;
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml jiaTingChengYuanXml=new JiaTingChengYuanXml();
		List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		HBSession sess = HBUtil.getHBSession();
		String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid";
		List lista36 = sess.createQuery(sqla36).list();
		String userid = SysUtil.getCacheCurrentUser().getId();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		A57 a57 = (A57)sess.get(A57.class, a0000);
		String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
		List<A53> list = sess.createQuery(sqla53).list();
		Object xb = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"+a01.getA0104()+"'").uniqueResult();
		Object mz = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"+a01.getA0117()+"'").uniqueResult();
        if(a57!=null){
        	byte[] data = PhotosUtil.getPhotoData(a57);
    		if(data!=null){
    			a.setZhaoPian(data);
    		}
        }
		a.setXingMing(a01.getA0101());
		a.setXingBie(xb!=null?xb.toString():"");                                    
		a.setChuShengNianYue(a01.getA0107());                            
		a.setMinZu(mz!=null?mz.toString():"");                                      
		a.setJiGuan(a01.getComboxArea_a0111());                                     
		a.setChuShengDi(a01.getComboxArea_a0114());
		a.setRuDangShiJian(a01.getA0140());
		a.setCanJiaGongZuoShiJian(a01.getA0134());
		a.setJianKangZhuangKuang(a01.getA0128());      
		a.setZhuanYeJiShuZhiWu(a01.getA0196());
		a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
		a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
		a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
		a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
		a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
		a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
		a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
		a.setXianRenZhiWu(a01.getA0192a());                               
		a.setJianLi(a01.getA1701());                                     
		a.setJiangChengQingKuang(a01.getA14z101());                        
		a.setNianDuKaoHeJieGuo(a01.getA15z101());                          
		if(list==null||list.size()==0){
			a.setNiRenZhiWu("");                                 
			a.setNiMianZhiWu("");
			a.setRenMianLiYou("");                               
			a.setChengBaoDanWei("");                             
			a.setJiSuanNianLingShiJian("");                      
			a.setTianBiaoShiJian(time);
			a.setTianBiaoRen("");
		}else{
			List lista53 = sess.createQuery(sqla53).list();
			A53 a53 = (A53)lista53.get(0);
			a.setNiRenZhiWu(a53.getA5304());                                 
			a.setNiMianZhiWu(a53.getA5315());
			a.setRenMianLiYou(a53.getA5317());                               
			a.setChengBaoDanWei(a53.getA5319());                             
			a.setJiSuanNianLingShiJian(a53.getA5321()); 
			if(StringUtil.isEmpty(time)){
				a.setTianBiaoShiJian(a53.getA5323());
			}else{
				a.setTianBiaoShiJian(time);
			}
			a.setTianBiaoRen(a53.getA5327());
		}
		if(lista36!=null&&lista36.size()>0){
			for(int i=1;i<=lista36.size();i++){
				ItemXml b =new ItemXml();
				A36 a36 = (A36) lista36.get(i-1);
				Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
			    b.setChengWei(cw!=null?cw.toString():"");
				b.setChuShengRiQi(a36.getA3607());
				b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
				b.setXingMing(a36.getA3601());
				Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
			    b.setZhengZhiMianMao(zzmm!=null?zzmm.toString():"");
				itemlist.add(b);
			}
		}
		jiaTingChengYuanXml.setItem(itemlist);
		jtcyList.add(jiaTingChengYuanXml);
		a.setJiaTingChengYuan(jtcyList);
		return a;
	}
	public static byte[] toByte(Blob blob) throws SQLException, IOException{
		  byte[] im = new byte[(int) blob.length()];
		  BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());
		  int len = (int) blob.length();
		  int offset = 0;
		  int read = 0;
		  
		  try {
		   while (offset < len && (read = is.read(im, offset, len - offset)) >= 0) {
		     offset += read;
		    }
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }finally{
		   is.close();
		   is=null;
		  }
		  
		  return im;
		 }
	
	
	/**
	 * ��PDFԤ������
	 * 
	 * @param a0000AndFlag a0000 ����ԱID���� flag���Ƿ��ӡ��������Ϣ��ƴ�ӵĲ������ö��ŷָ�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @date 2016-06-03
	 */
	@PageEvent("printView")
	public int pdfView(String a0000AndFlag) throws RadowException, AppException{
		String[] params = a0000AndFlag.split(",");
		String a0000 = params[0]; 										//��ԱID
		Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
		String pdfPath = "";  											//pdf�ļ�·��
		
		List<String> list = new ArrayList<String>();
		list.add(a0000);
		
		List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		
		this.setRadow_parent_data(pdfPath);
		this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �Թ�ѡ����Ա������ӡǰ����֤
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 * @author mengl
	 */
//	@PageEvent("batchPrint.onclick")
//	@NoRequiredValidate
	public int batchPrintBefore() throws RadowException, AppException{
		List<HashMap<String,Object>> list =  this.getPageElement("persongrid").getValueList();
		if(list==null || list.size()<1 ){
			this.setMainMessage("���Ȳ�ѯ��Ա��Ϣ������ѡҪ������ӡ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','��ӡ��������Ƿ������������Ϣ��',200,function(id){" +
				"if(id=='yes'){" +
				"			radow.doEvent('batchPrint','true');" +
					"}else if(id=='no'){" +
					"			radow.doEvent('batchPrint','false');" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �Թ�ѡ����Ա������ӡ
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 * @author mengl
	 */
	@PageEvent("batchPrint")
	@NoRequiredValidate
	public int batchPrint(String flag) throws RadowException, AppException{
		String[] flags = flag.split("@");
		String printTime = "";
		if(flags.length>1){
			printTime = flags[1];
		}
		
		boolean flagNrm =false;	//�Ƿ��ӡ��������Ϣ
		if("true".equalsIgnoreCase(flags[0])){
			flagNrm = true;
		}else if("false".equalsIgnoreCase(flags[0])){
			flagNrm = false;
		}
		String newPDFPath = "";
		List<HashMap<String,Object>> list =  this.getPageElement("persongrid").getValueList();
		try {
			newPDFPath = new QueryPersonListBS().batchPrint(list,flagNrm,printTime);
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("������ӡʧ�ܣ�"+e.getMessage());
		}
		
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		newPDFPath = newPDFPath.substring(newPDFPath.indexOf(contextStr)).replace("\\", "/");
		/*this.setRadow_parent_data(newPDFPath);
		this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");*/
		this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+newPDFPath+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//��д����ʱ��
	@PageEvent("importHzbBtn.onclick")
	public int expHzbWin() throws RadowException {
		int i = choose("persongrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("����ѡ��Ҫ��������Ա");
			return EventRtnType.FAILD;
		}
		StringBuffer ids = new StringBuffer();
		PageElement pe = this.getPageElement("persongrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("personcheck");
				if(usercheck.equals(true)){
					ids.append("'"+map.get("a0000").toString()).append("',");
				}
			}
		}
		String allids = ids.substring(0,ids.length()-1);
		String id = UUID.randomUUID().toString().replace("-", "");
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			KingbsconfigBS.saveImpDetailInit3(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			DataPsnImpThread thr = new DataPsnImpThread(id, user,"","","","","",
					"","","","","","","",userVo,allids);
			new Thread(thr,"Thread_psnexp").start();
			this.setRadow_parent_data(id);
			this.openWindow("refreshWin", "pages.dataverify.RefreshOrgExp");
//			String file =new DataOrgImpPageModel().expByPsnbtn(allids);
//			this.getPageElement("downfile").setValue(file.replace("\\", "/"));
//			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
