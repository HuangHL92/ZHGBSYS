package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class DelPChoosePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		return 0;
	}
	@PageEvent("set.onclick")
	@Synchronous(true)
    public int set(String str) throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String rbId = this.getPageElement("rbId").getValue();
		String js0100s = this.getPageElement("js0100s").getValue();
		String dellx = this.getPageElement("dellx").getValue();
		String sm = this.getPageElement("detxx").getValue();
		try {
			if(dellx==null || dellx.equals("")) {
				this.setMainMessage("请选择一个移除类型。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String js01sql = "select a0000,js0122,js0100,js0102 from js01 where js0100 in ('"+(js0100s.replace(",", "','"))+"')";
			List<Object[]> list = sess.createSQLQuery(js01sql).list();
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			for (int i = 0; i < list.size(); i++) {
				Object[] o = list.get(i);
				String a0000 = o[0].toString();
				String js0122 = o[1].toString();
				String js0100new = o[2].toString();
				String js0102 = o[3].toString();
				if(dellx.equals("1") || dellx.equals("2") || dellx.equals("3")) {
					//查询原有备份库数据，原有信息，更新标识，删除原有备份
					String sql = "select JS_HIS_ID,js0100 from JS_HIS t where a0000='"+a0000+"' and js0122='"+js0122+"'";
					rs = stmt.executeQuery(sql);
					if(rs.next()) {
						String js_his_id = rs.getString(1);
						String js0100 = rs.getString(2);
						String u1 = "update JS_HIS set JSH004='0' where JS_HIS_ID='"+js_his_id+"'";
						stmt2.addBatch(u1);
						
						String d1 = "delete from js01_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//动议表
						d1 = "delete from js02_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//民主推荐表
						d1 = "delete from js03_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//考察人选确定表 ？
						d1 = "delete from js04_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//酝酿表？
						d1 = "delete from js06_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//任前公示表
						d1 = "delete from js08_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//依法办理任免表
						d1 = "delete from js09_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//试用期表
						d1 = "delete from js10_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//其他情况表
						d1 = "delete from js11_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//组织考察表（无锡）
						d1 = "delete from js14_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//讨论决定表（无锡）
						d1 = "delete from js15_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//推荐
						d1 = "delete from js18_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js19_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js20_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//新的推荐表js33
						d1 = "delete from js33_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						
						//保存考核与听取意见
						d1 = "delete from js21_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//发文
						d1 = "delete from js22_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js23_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js24_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//删除附件
						stmt3 = conn.createStatement();
						rs2 = stmt3.executeQuery("select * from js_att_his where js0100 ='"+js0100+"'");
						while(rs2.next()) {
							String p = AppConfig.HZB_PATH + "/jshis/";
							String jsa00 = rs2.getString("jsa00");
							String jsa07 = rs2.getString("jsa07");
							File f = new File(p + jsa07 + jsa00);
	                        if (f.isFile()) {
	                            f.delete();
	                        }
						}
						rs2.close();
						stmt3.close();
						d1 = "delete from js_att_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js_hj_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
					}
					//插入备份库信息
					if(dellx.equals("1") || dellx.equals("3")) {
						String i1 = "insert into js01_his select * from js01 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						i1 = "insert into js02_his select * from js02 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js03_his select * from js03 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js04_his select * from js04 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js06_his select * from js06 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js08_his select * from js08 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js09_his select * from js09 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js10_his select * from js10 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js11_his select * from js11 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js14_his select * from js14 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js15_his select * from js15 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js18_his select * from js18 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js19_his select * from js19 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js20_his select * from js20 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js21_his select * from js21 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js22_his select * from js22 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js23_his select * from js23 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js24_his select * from js24 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						
						i1 = "insert into js33_his select * from js33 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						//删除附件
						stmt3 = conn.createStatement();
						rs2 = stmt3.executeQuery("select * from js_att where js0100 ='"+js0100new+"'");
						while(rs2.next()) {
							String p = AppConfig.HZB_PATH + "/";
							String jsa00 = rs2.getString("jsa00");
							String jsa07 = rs2.getString("jsa07");
							String r = AppConfig.HZB_PATH + "/jshis/" + jsa07;
							File f = new File(r);
	                        if (!f.exists()) {
	                            f.mkdirs();
	                        }
	                        File f2 = new File(p + jsa07 + jsa00);
	                        if(f2.exists() && f2.isFile()) {
	                        	 PhotosUtil.copyFile(f2, new File(r + jsa00));
	                        }
	                       
						}
						rs2.close();
						stmt3.close();
						
						i1 = "insert into js_att_his select * from js_att where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						i1 = "insert into js_hj_his select * from js_hj where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						
						i1 = "insert into JS_HIS values (sys_guid(),'"+js0100new+"','"+a0000+"','"+js0122+"','"+dellx
								+"','"+sm+"',sysdate,'1','"+js0102+"','')";
						stmt2.addBatch(i1);
					} else {
						String i1 = "insert into JS_HIS values (sys_guid(),'"+js0100new+"','"+a0000+"','"+js0122+"','"+dellx
								+"','"+sm+"',sysdate,'1','"+js0102+"','')";
						stmt2.addBatch(i1);
					}
				}
				//移除正式库信息
				String d1 = "delete from js01 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//动议表
				d1 = "delete from js02 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//民主推荐表
				d1 = "delete from js03 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//考察人选确定表 ？
				d1 = "delete from js04 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//酝酿表？
				d1 = "delete from js06 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//任前公示表
				d1 = "delete from js08 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//依法办理任免表
				d1 = "delete from js09 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//试用期表
				d1 = "delete from js10 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//其他情况表
				d1 = "delete from js11 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//组织考察表（无锡）
				d1 = "delete from js14 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//讨论决定表（无锡）
				d1 = "delete from js15 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//推荐
				d1 = "delete from js18 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js19 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js20 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//新的推荐表
				d1 = "delete from js33 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//保存考核与听取意见
				d1 = "delete from js21 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//发文
				d1 = "delete from js22 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js23 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js24 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js_hj where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//删除附件
				stmt3 = conn.createStatement();
				rs2 = stmt3.executeQuery("select * from js_att where js0100 ='"+js0100new+"'");
				while(rs2.next()) {
					String p = AppConfig.HZB_PATH + "/";
					String jsa00 = rs2.getString("jsa00");
					String jsa07 = rs2.getString("jsa07");
					File f = new File(p + jsa07 + jsa00);
                    if (f.isFile()) {
                        f.delete();
                    }
				}
				rs2.close();
				stmt3.close();
				d1 = "delete from js_att where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
			}
			stmt2.executeBatch();
			conn.commit();
			//this.setMainMessage("移除成功");
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gridcq.dogridquery');alert('移除成功');window.close();");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn!=null) 
					conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw new RadowException("数据异常！");
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(stmt2!=null) {
				stmt2.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
