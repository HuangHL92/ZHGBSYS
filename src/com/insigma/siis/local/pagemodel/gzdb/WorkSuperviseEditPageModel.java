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
     * 字符串处理方法
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
        String userid = user.getId(); //用户id
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
            String zrcs = this.getPageElement("zrcs").getValue();//责任处室
        }

        return EventRtnType.NORMAL_SUCCESS;
    }
   
    /**
     * 工作督办详情
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
        String userid = user.getId(); //用户id
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
        

        //获取当前列表的数据
        List<HashMap<String, Object>> list = CommonMethodBS.getListBySQL(sql.toString());
        gbnrmList = new ArrayList<HashMap<String, Object>>();
        for (HashMap<String, Object> map : list) {
            gbnrmList.add(map);
        }

        return EventRtnType.SPE_SUCCESS;

    }

    /**
     * 保存干部休假信息
     *
     * @return
     * @throws RadowException
     * @throws AppException
     */

    @PageEvent("save.onclick")
    @Transaction
    public int save() throws Exception{
        HBSession sess = HBUtil.getHBSession();
        //获取当前登录用户信息
        UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
        String userid = user.getId();
        String username = user.getName(); //用户姓名
        String zrcs = this.getPageElement("zrcs").getValue();//责任处室
        String operation = this.getPageElement("operation").getValue();
        String gzdbid = this.getPageElement("gzdbid").getValue(); //工作督办id
        String sxmc = this.getPageElement("sxmc").getValue(); //事项名称
        String jtwt = this.getPageElement("jtwt").getValue(); //具体问题
        String zgmb = this.getPageElement("zgmb").getValue(); //整改目标
        String qtld = this.getPageElement("qtld").getValue(); //牵头领导
        String zgrw = this.getPageElement("zgrw").getValue();//整改任务
        String wcsx = this.getPageElement("wcsx").getValue(); //完成时限
        String wcl = this.getPageElement("wcl").getValue(); //完成率
       // String wcbz1 = this.getPageElement("wcbz1").getValue(); //完成标志1
        String wcbz2 = this.getPageElement("wcbz2").getValue();  //长期坚持
        String cs001 = this.getPageElement("cs001").getValue();  //处室
        
        GZDB p312 = new GZDB(); // 获取到的工作督办数据
        GZDB p31 = new GZDB();
        if (operation.equals("add") && StringUtils.isEmpty(gzdbid)) {
        	List list = sess.createSQLQuery("select gzdbid from GZDB where gzdbid='" + gzdbid + "'").list();
        	if (list.size() != 0) {
        		this.setMainMessage("该工作督办名称已存在");
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
        	//干部休假计划人员详情
            List<HashMap<String, Object>> list = this.getPageElement("grid2").getValueList();
            if (!list.isEmpty()) {

                //遍历list
                for (HashMap<String, Object> m : list) {
                    if (!m.isEmpty()) {
                    	RWWCQK p322 = (RWWCQK) MaptoBeanUtil.mapToBean(m, RWWCQK.class);
                            //根据完成情况主键id（rwwcqkid），判断是新增还是修改
                            if (!StringUtils.isEmpty(p322.getRwwcqkid())) {//修改
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
							 * }else { this.setMainMessage("长期坚持不在指定月份不能录入!"); return
							 * EventRtnType.NORMAL_SUCCESS; }
							 */
                            } else {//新增
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
							 * }else { this.setMainMessage("长期坚持不在指定月份不能录入!"); return
							 * EventRtnType.NORMAL_SUCCESS; }
							 */
                            }
                    }


                }
            }
        }
        	
        	sess.flush();

        this.setMainMessage("保存成功!");
        if (operation.equals("add") && StringUtils.isEmpty(p312.getGzdbid())) {
            this.getExecuteSG().addExecuteCode("saveCallBack();");
        }
       this.getExecuteSG().addExecuteCode("success()");
       //this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('grid1.dogridquery')");
      //关闭当前页面
	   // addNextBackFunc(NextEventValue.YES, closeCueWindowEX());
       this.getExecuteSG().addExecuteCode("closeSelfWin()");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    public String closeCueWindowEX(){
		return "window.close();";
	}

    /**
     * 删除选中的人员
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("deleteBtn.onclick")
    @NoRequiredValidate
    public int delete() throws RadowException {

        List<HashMap<String, Object>> list = this.getPageElement("grid2").getValueList(); //
        List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>(); //

        for (HashMap<String, Object> map : list) { //将列表中被勾选的任务情况放入list2中
            if (map.get("checked").toString().equals("true")) {
                list2.add(map);
            }
        }
        if (list2.size() == 0) {
            this.setMainMessage("请选择要删除的完成情况!");
        } else {

            for (int i = 0; i < list2.size(); i++) {
                String rwwcqkid = list2.get(i).get("rwwcqkid").toString();
                //this.addNextEvent(NextEventValue.YES, "delete", rwwcqkid);
               
               // this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
                //this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
                this.setMainMessage("您确实要删除选中的完成情况？");

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
	 * 根据rwwcqkid 查询任务完成情况
	 * @param rwwcqkid
	 * @return
	 */
	public RWWCQK getRWWCQKyrwwcqkid(String rwwcqkid){
		String hql=" from RWWCQK where rwwcqkid='"+rwwcqkid+"'";
		Query query=sess.createQuery(hql);
		return (RWWCQK) query.uniqueResult();

	}
	/**
	 * 根据rwwcqkid 查询工作督办情况
	 * @param gzdbid
	 * @return
	 */
	public GZDB getGZDBBygzdbid(String gzdbid){
		String hql=" from GZDB where gzdbid='"+gzdbid+"'";
		Query query=sess.createQuery(hql);
		return (GZDB) query.uniqueResult();

	}
	/**
     * 根据 zrcs 查询相关处室
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
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String gzdbid){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(wcl),0) from GZDB where gzdbid='"+gzdbid+"'").uniqueResult().toString();
		return sort;
	}

}


