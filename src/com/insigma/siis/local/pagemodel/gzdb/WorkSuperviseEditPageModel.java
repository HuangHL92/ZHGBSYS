package com.insigma.siis.local.pagemodel.gzdb;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.GZDB;
import com.insigma.siis.local.business.entity.RWWCQK;
import com.insigma.siis.local.comm.search.CommonMethodBS;

public class WorkSuperviseEditPageModel extends PageModel {

	private HBSession sess = HBUtil.getHBSession();
	private static List<HashMap<String, Object>> gbnrmList = null;
	 UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
    /**
     * �ַ���������
     *
     * @param v
     * @return
     */
    private String S(Object v) {
        return v == null ? "" : v.toString();
    }

    public int doInit() throws RadowException {
        this.getExecuteSG().addExecuteCode("Func.init();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initPage")
    @NoRequiredValidate
    public int initPage() throws RadowException, AppException {

        String gzdbid = this.getPageElement("gzdbid").getValue();
        String operation = this.getPageElement("operation").getValue();
        String userid = user.getId(); //�û�id
        MaptoBeanUtil userBs  = new MaptoBeanUtil();
        if (operation.equals("edit") && !StringUtils.isEmpty(gzdbid)) {
        	GZDB gzdbBygzdbid = getGZDBBygzdbid(gzdbid);
            this.copyObjValueToElement(gzdbBygzdbid, this);
            boolean aa=userBs.isLeader();
            if(aa=true){//
     		    this.getExecuteSG().addExecuteCode("dd('"+1+"')");
            }else {
            	this.getExecuteSG().addExecuteCode("dd('"+2+"')");
            }
            this.setNextEventName("grid2.dogridquery");
        } else {
        	UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
            String zrcs = this.getPageElement("zrcs").getValue();//���δ���
        }

        return EventRtnType.NORMAL_SUCCESS;
    }
   
    /**
     * ������������
     *
     * @param start
     * @param limit
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("grid2.dogridquery")
    @NoRequiredValidate
    public int gridQuery(int start, int limit) throws RadowException, AppException {
    	String gzdbid = this.getPageElement("gzdbid").getValue();
        String userid = user.getId(); //�û�id
        //bs.getPa01Bya0000(userid);
        
        StringBuffer sql = new StringBuffer();
        sql.append("select t.rwwcqkid,t.gzdbid,t2.cs001,t.wcqkbj,t.wcqk,t.bz,t2.sxmc,t2.jtwt,t2.zgmb,t2.qtld,t2.zrcs,t2.zgrw,t2.wcsx,t2.wcl,t2.wcbz1, " +
                "    t2.wcbz2,t2.cjr,to_char (t2.createdon, 'YYYY-MM-DD')  createdon " +
                "from RWWCQK t inner join GZDB t2 on t.gzdbid=t2.gzdbid");
        sql.append(" and t.gzdbid='" + gzdbid + "'");
        sql.append(" order by t2.wcl asc ,t2.createdon asc");
        sql.toString();
        System.out.println(sql);
        this.pageQuery(sql.toString(), "sql", start, limit);
        

        //��ȡ��ǰ�б������
        List<HashMap<String, Object>> list = CommonMethodBS.getListBySQL(sql.toString());
        gbnrmList = new ArrayList<HashMap<String, Object>>();
        for (HashMap<String, Object> map : list) {
            gbnrmList.add(map);
        }

        return EventRtnType.SPE_SUCCESS;

    }

    /**
     * ����ɲ��ݼ���Ϣ
     *
     * @return
     * @throws RadowException
     * @throws AppException
     */

    @PageEvent("save.onclick")
    @Transaction
    public int save() throws Exception{
        HBSession sess = HBUtil.getHBSession();
        //��ȡ��ǰ��¼�û���Ϣ
        UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
        String userid = user.getId();
        String username = user.getName(); //�û�����
        String zrcs = this.getPageElement("zrcs").getValue();//���δ���
        String operation = this.getPageElement("operation").getValue();
        String gzdbid = this.getPageElement("gzdbid").getValue(); //��������id
        String sxmc = this.getPageElement("sxmc").getValue(); //��������
        String jtwt = this.getPageElement("jtwt").getValue(); //��������
        String zgmb = this.getPageElement("zgmb").getValue(); //����Ŀ��
        String qtld = this.getPageElement("qtld").getValue(); //ǣͷ�쵼
        String zgrw = this.getPageElement("zgrw").getValue();//��������
        String wcsx = this.getPageElement("wcsx").getValue(); //���ʱ��
        String wcl = this.getPageElement("wcl").getValue(); //�����
       // String wcbz1 = this.getPageElement("wcbz1").getValue(); //��ɱ�־1
        String wcbz2 = this.getPageElement("wcbz2").getValue();  //���ڼ��
        String cs001 = this.getPageElement("cs001").getValue();  //����
        
        GZDB p312 = new GZDB(); // ��ȡ���Ĺ�����������
        GZDB p31 = new GZDB();
        if (operation.equals("add") && StringUtils.isEmpty(gzdbid)) {
        	List list = sess.createSQLQuery("select gzdbid from GZDB where gzdbid='" + gzdbid + "'").list();
        	if (list.size() != 0) {
        		this.setMainMessage("�ù������������Ѵ���");
        		return EventRtnType.NORMAL_SUCCESS;
        	}
        	//int max_sort= Integer.valueOf(getMax_sort(meetingid));
			/*if("".equals(wcl)||wcl==null) {
				p312.setWcl(Integer.valueOf(getMax_sort(wcl))+1);
			}else {
				List list_sort = sess.createSQLQuery("select * from GZDB where wcl='"+wcl+"' and gzdbid='"+gzdbid+"'").list();
				if(list_sort.size()!=0){
					String sql="update GZDB set wcl=wcl+1 where to_number(wcl)>="+wcl+" and gzdbid='"+gzdbid+"'";
					Statement stmt = sess.connection().createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
				}
				p312.setWcl(Integer.valueOf(wcl));
			}*/
            p312.setGzdbid(UUID.randomUUID().toString().replaceAll("-", ""));
            p312.setCreatedon(new Date());
            p312.setCjr(username);
            p312.setSxmc(sxmc);
        	p312.setJtwt(jtwt);
        	p312.setZgmb(zgmb);
        	p312.setQtld(qtld);
        	p312.setZrcs(zrcs);
        	p312.setZgrw(zgrw);
        	p312.setWcsx(wcsx);
        	int wcl1=Integer.parseInt(wcl.trim().equals("")?"0":wcl.trim());
        	p312.setWcl(wcl1);
        	//p312.setWcbz1(wcbz1);
        	p312.setWcbz2(wcbz2);
        	p312.setCs001(cs001);
        
        	sess.save(p312);

        } else if (operation.equals("edit") && !StringUtils.isEmpty(gzdbid)) {
        	p31 = (GZDB) HBUtil.getHBSession().get(GZDB.class, gzdbid);
        	/*if("".equals(wcl)||wcl==null) {
				p31.setWcl(Integer.valueOf(getMax_sort(wcl))+1);
			}else {
				List list_sort = sess.createSQLQuery("select * from GZDB where wcl='"+wcl+"'  and gzdbid='"+gzdbid+"'").list();
				if(list_sort.size()!=0){
					String sql="update GZDB set wcl=wcl+1 where to_number(wcl)>="+wcl+" and gzdbid='"+gzdbid+"'";
					Statement stmt = sess.connection().createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
				}
				p31.setWcl(Integer.valueOf(wcl));
			}*/
        	p31.setGzdbid(gzdbid);
        	//p31.setCreatedon(new Date());
        	p31.setCjr(username);
        	p31.setSxmc(sxmc);
        	p31.setJtwt(jtwt);
        	p31.setZgmb(zgmb);
        	p31.setQtld(qtld);
        	p31.setZrcs(zrcs);
        	p31.setZgrw(zgrw);
        	p31.setWcsx(wcsx);
        	p31.setCs001(cs001);
         	int wcl1=Integer.parseInt(wcl.trim().equals("")?"0":wcl.trim());
         	p31.setWcl(wcl1);
         	//p31.setWcbz1(wcbz1);
         	p31.setWcbz2(wcbz2);
        	sess.update(p31);
        	//�ɲ��ݼټƻ���Ա����
            List<HashMap<String, Object>> list = this.getPageElement("grid2").getValueList();
            if (!list.isEmpty()) {

                //����list
                for (HashMap<String, Object> m : list) {
                    if (!m.isEmpty()) {
                    	RWWCQK p322 = (RWWCQK) MaptoBeanUtil.mapToBean(m, RWWCQK.class);
                            //��������������id��rwwcqkid�����ж������������޸�
                            if (!StringUtils.isEmpty(p322.getRwwcqkid())) {//�޸�
							/*
							 * List list_sort =
							 * sess.createSQLQuery("select * from RWWCQK where wcqkbj=2 and gzdbid='"
							 * +gzdbid+"' and TO_char(createdon,'yyyy-mm')" +
							 * "  in ('2021-06','2021-09','2021-12','2022-03','2022-5')").list();
							 * if(list_sort.size()!=0){
							 */
                                RWWCQK p32 = getRWWCQKyrwwcqkid(p322.getRwwcqkid());
                                p32.setCs001(p322.getCs001());
                                p32.setWcqk(p322.getWcqk());
                                p32.setWcqkbj(p322.getWcqkbj());
                                p32.setBz(p322.getBz());
                                p32.setCjr(username);
                                //p32.setCreatedon(new Date());
                                sess.update(p32);
							/*
							 * }else { this.setMainMessage("���ڼ�ֲ���ָ���·ݲ���¼��!"); return
							 * EventRtnType.NORMAL_SUCCESS; }
							 */
                            } else {//����
                            /*	List list_sort = sess.createSQLQuery("select * from RWWCQK where wcqkbj=2 and gzdbid='"+gzdbid+"' and TO_char(createdon,'yyyy-mm')" + 
                            			"  in ('2021-06','2021-09','2021-12','2022-03','2022-5')").list();
                				if(list_sort.size()!=0){*/
                            	p322.setCs001(p322.getCs001());
                            	p322.setWcqk(p322.getWcqk());
                            	p322.setWcqkbj(p322.getWcqkbj());
                            	p322.setBz(p322.getBz());
                                p322.setRwwcqkid(UUID.randomUUID().toString().replaceAll("-", ""));
                                p322.setGzdbid(p31.getGzdbid());
                                p322.setCjr(username);
                                p322.setCreatedon(new Date());
                                sess.save(p322);
							/*
							 * }else { this.setMainMessage("���ڼ�ֲ���ָ���·ݲ���¼��!"); return
							 * EventRtnType.NORMAL_SUCCESS; }
							 */
                            }
                    }


                }
            }
        }
        	
        	sess.flush();

        this.setMainMessage("����ɹ�!");
        if (operation.equals("add") && StringUtils.isEmpty(p312.getGzdbid())) {
            this.getExecuteSG().addExecuteCode("saveCallBack();");
        }
       this.getExecuteSG().addExecuteCode("success()");
       //this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('grid1.dogridquery')");
      //�رյ�ǰҳ��
	   // addNextBackFunc(NextEventValue.YES, closeCueWindowEX());
       this.getExecuteSG().addExecuteCode("closeSelfWin()");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    public String closeCueWindowEX(){
		return "window.close();";
	}

    /**
     * ɾ��ѡ�е���Ա
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("deleteBtn.onclick")
    @NoRequiredValidate
    public int delete() throws RadowException {

        List<HashMap<String, Object>> list = this.getPageElement("grid2").getValueList(); //
        List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>(); //

        for (HashMap<String, Object> map : list) { //���б��б���ѡ�������������list2��
            if (map.get("checked").toString().equals("true")) {
                list2.add(map);
            }
        }
        if (list2.size() == 0) {
            this.setMainMessage("��ѡ��Ҫɾ����������!");
        } else {

            for (int i = 0; i < list2.size(); i++) {
                String rwwcqkid = list2.get(i).get("rwwcqkid").toString();
                //this.addNextEvent(NextEventValue.YES, "delete", rwwcqkid);
               
               // this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
                //this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
                this.setMainMessage("��ȷʵҪɾ��ѡ�е���������");

        		NextEvent yes = new NextEvent();
        		yes.setNextEventValue(NextEventValue.YES);
        		yes.setNextEventName("grid2.dogridquery");
        		this.addNextEvent(yes);
        		NextEvent cannel = new NextEvent();
        		cannel.setNextEventValue(NextEventValue.CANNEL);
        		this.addNextEvent(cannel);
        		 this.deleRWWCQK(rwwcqkid);
            }
            //this.setNextEventName("grid2.dogridquery");
        }

        return EventRtnType.NORMAL_SUCCESS;

    }
    public void deleRWWCQK(String rwwcqkid){
		HBUtil.getHBSession().createSQLQuery("delete from RWWCQK where rwwcqkid = '"+rwwcqkid+"'").executeUpdate();
	}

    
    /**
	 * ����rwwcqkid ��ѯ����������
	 * @param rwwcqkid
	 * @return
	 */
	public RWWCQK getRWWCQKyrwwcqkid(String rwwcqkid){
		String hql=" from RWWCQK where rwwcqkid='"+rwwcqkid+"'";
		Query query=sess.createQuery(hql);
		return (RWWCQK) query.uniqueResult();

	}
	/**
	 * ����rwwcqkid ��ѯ�����������
	 * @param gzdbid
	 * @return
	 */
	public GZDB getGZDBBygzdbid(String gzdbid){
		String hql=" from GZDB where gzdbid='"+gzdbid+"'";
		Query query=sess.createQuery(hql);
		return (GZDB) query.uniqueResult();

	}
	/**
     * ���� zrcs ��ѯ��ش���
     * @param zrcs
     * @return
     */
    public static String QueryDeptInfoByID(String zrcs) {
    	HBSession session = HBUtil.getHBSession();
    	String hql = "select b.zrcs from GZDB b where b.zrcs = '"+zrcs+"'";
    	Object result = session.createQuery(hql).uniqueResult();	
    	return result.toString();
    }
    /**
	 * ��ȡ���������
	 * @return
	 */
	public String getMax_sort(String gzdbid){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(wcl),0) from GZDB where gzdbid='"+gzdbid+"'").uniqueResult().toString();
		return sort;
	}

}


