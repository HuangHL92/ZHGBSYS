package com.insigma.siis.local.pagemodel.gzdb;

import java.util.UUID;

import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.GZDB;
import com.insigma.siis.local.business.entity.RWWCQK;
import com.insigma.siis.local.comm.search.CommonMethodBS;

public class WorkSuperviseAddPageModel extends PageModel  {
	
	 HBSession session = HBUtil.getHBSession();
	 UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
	 
	 @Override
	    public int doInit() throws RadowException {
			
			 this.setNextEventName("initX");
		        return EventRtnType.NORMAL_SUCCESS;
	    }
		 @PageEvent("initX")
		    @NoRequiredValidate
		    public int initX() throws RadowException {
			 String thisP0400 = this.getPageElement("subWinIdBussessId").getValue();
		        String gzdbid = "";
		        String rwwcqkid = "";

		        int index = thisP0400.indexOf("@");
		        if (index != -1) {
		        	rwwcqkid = thisP0400.substring(0, thisP0400.indexOf("@")).trim();
		        }
		        if ((index + 1) != -1) {
		        	gzdbid = thisP0400.substring(index + 1).trim();
		        	GZDB gzdb = (GZDB) session.get(GZDB.class, gzdbid);
		            this.copyObjValueToElement(gzdb, this);
		            String username = user.getName(); //用户姓名
		            gzdb.setCjr(username);
		        }
		        //修改
		        if (rwwcqkid != null && !"".equals(rwwcqkid)) {
		            try {
		            	RWWCQK rwwcqByrwwcqkid= getRWWCQrwwcqkid(rwwcqkid);
			            this.copyObjValueToElement(rwwcqByrwwcqkid, this);
		                this.getPageElement("wcqk").setValue(rwwcqByrwwcqkid.getWcqk()); //
		                this.getPageElement("wcqkbj").setValue(rwwcqByrwwcqkid.getWcqkbj()); //
		               this.getPageElement("bz").setValue(rwwcqByrwwcqkid.getBz());
		               //rwwcqByrwwcqkid.setCjr(user.getName());
		               //rwwcqByrwwcqkid.setCreatedon(new Date());

		            } catch (Exception var7) {
		                var7.printStackTrace();
		                 this.setMainMessage("查询失败");
		            }
		        } else {
		        	
		        }

		        return EventRtnType.NORMAL_SUCCESS;
			 
			 
		 }
	 /**
	     * 完成情况详情
	     *
	     * @param start
	     * @param limit
	     * @return
	     * @throws RadowException
	     * @throws AppException
	     */
	    @PageEvent("grid3.dogridquery")
	    @NoRequiredValidate
	    public int gridQuery(int start, int limit) throws RadowException, AppException {
	    	String rwwcqkid = this.getPageElement("rwwcqkid").getValue();
	        UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
	        String userid = user.getId(); //用户id
	        //bs.getPa01Bya0000(userid);
	        
	        StringBuffer sql = new StringBuffer();
	        sql.append("select t.rwwcqkid,t.gzdbid,t.cs001,t.wcqkbj,t.wcqk,t.bz, " +
	                "   t.cjr,to_char (t.createdon, 'YYYY-MM-DD')  createdon " +
	                "from RWWCQK t where 1=1");
	        sql.append(" order by t.createdon asc");
	        sql.toString();
	        System.out.println(sql);
	        this.pageQuery(sql.toString(), "sql", start, limit);
	        return EventRtnType.SPE_SUCCESS;

	    }
	 @PageEvent("save")
	 public int save() throws RadowException, AppException {
		//获取当前登录用户信息
	     UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
	     String username = user.getName(); //用户姓名
	     String gzdbid = this.getPageElement("gzdbid").getValue();
	     String rwwcqkid = this.getPageElement("rwwcqkid").getValue(); //完成情况id
		// String gzdbid= this.getPageElement("gzdbid").getValue();
	     String wcqkbj= this.getPageElement("wcqkbj").getValue();
		 RWWCQK rwwcqk = new RWWCQK();
		 //RWWCQK rwwcqk1 = new RWWCQK();
		 if (StringUtils.isEmpty(rwwcqkid)) {
			 List list = session.createSQLQuery("select rwwcqkid from RWWCQK where rwwcqkid='" + rwwcqkid + "'").list();
	        	if (list.size() != 0) {
	        		this.setMainMessage("该工作情况已存在");
	        		return EventRtnType.NORMAL_SUCCESS;
	        	}
	        	if(wcqkbj.equals("2")) {
	                String sql="select * from RWWCQK where wcqkbj='"+wcqkbj+"' and gzdbid='"+gzdbid+"' and TO_char(createdon,'yyyy-mm') in  ('2021-06','2021-09','2021-12','2022-03','2022-5')";
		        	List list_sort = session.createSQLQuery(sql).list();
					if(wcqkbj!="2"&&list_sort.size()!=0){
	            // 新增数据
	            rwwcqk.setRwwcqkid(UUID.randomUUID().toString().replaceAll("-", ""));
	            rwwcqk.setWcqk(this.getPageElement("wcqk").getValue());
	            rwwcqk.setWcqkbj(wcqkbj);
	            rwwcqk.setBz(this.getPageElement("bz").getValue());
	            rwwcqk.setCjr(username);
	            rwwcqk.setCreatedon(new Date());
	            rwwcqk.setGzdbid(gzdbid);
	            session.save(rwwcqk);
					}else {
						this.setMainMessage("长期坚持不在指定月份不能录入!");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}else {
					rwwcqk.setRwwcqkid(UUID.randomUUID().toString().replaceAll("-", ""));
	                rwwcqk.setWcqk(this.getPageElement("wcqk").getValue());
	                rwwcqk.setWcqkbj(wcqkbj);
	                rwwcqk.setBz(this.getPageElement("bz").getValue());
	                rwwcqk.setCjr(username);
	                rwwcqk.setCreatedon(new Date());
	                rwwcqk.setGzdbid(gzdbid);
	                session.save(rwwcqk);
				}
		 }else {
			 RWWCQK rwwcqByrwwcqkid= getRWWCQrwwcqkid(rwwcqkid);
			if(wcqkbj.equals("2")) {
	             String sql="select * from RWWCQK where wcqkbj='"+wcqkbj+"' and gzdbid='"+gzdbid+"' and TO_char(createdon,'yyyy-mm') in  ('2021-06','2021-09','2021-12','2022-03','2022-5')";
		        	List list_sort = session.createSQLQuery(sql).list();
				 if(list_sort.size()!=0){ 
				 rwwcqByrwwcqkid.setCjr(user.getName());
	             //rwwcqByrwwcqkid.setCreatedon(new Date());
	             rwwcqByrwwcqkid.setWcqk(this.getPageElement("wcqk").getValue());
	             rwwcqByrwwcqkid.setWcqkbj(wcqkbj);
	             rwwcqByrwwcqkid.setBz(this.getPageElement("bz").getValue());
	             session.update(rwwcqByrwwcqkid);
					}else {
						this.setMainMessage("长期坚持不在指定月份不能录入!");
						return EventRtnType.NORMAL_SUCCESS;
					}
			}else {
				 rwwcqByrwwcqkid.setCjr(user.getName());
	             //rwwcqByrwwcqkid.setCreatedon(new Date());
	             rwwcqByrwwcqkid.setWcqk(this.getPageElement("wcqk").getValue());
	             rwwcqByrwwcqkid.setWcqkbj(wcqkbj);
	             rwwcqByrwwcqkid.setBz(this.getPageElement("bz").getValue());
	             session.update(rwwcqByrwwcqkid);
			}
		 }
            session.flush();
        this.setMainMessage("保存成功");
        this.getExecuteSG().addExecuteCode("success()");
        //this.getExecuteSG().addExecuteCode("realParent.radow.doEvent(grid2.dogridquery)");
        //this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('WorkSuperviseAdd').close()");
        this.getExecuteSG().addExecuteCode("closeSelfWin()");
        return EventRtnType.NORMAL_SUCCESS;
    }
	 
		/**
		 * 根据rwwcqkid 查询任务完成情况
		 * @param rwwcqkid
		 * @return
		 */
		public RWWCQK getRWWCQrwwcqkid(String rwwcqkid){
			String hql=" from RWWCQK where rwwcqkid='"+rwwcqkid+"'";
			Query query=session.createQuery(hql);
			return (RWWCQK) query.uniqueResult();

		}


}
