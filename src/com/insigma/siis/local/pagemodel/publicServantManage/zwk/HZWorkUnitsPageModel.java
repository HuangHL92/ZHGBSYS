package com.insigma.siis.local.pagemodel.publicServantManage.zwk;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A02Zwzc;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.hzentity.B01Zzs;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class HZWorkUnitsPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
    	this.setNextEventName("memberGridMain.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }



    @PageEvent("save.onclick")
    public int save() throws Exception {
    	//单位b0111
        String a0201bSeclect = this.getPageElement("a0201bSeclect").getValue();
        String a0201bSeclectText = this.getPageElement("a0201bSeclect_combo").getValue();
        String b01id = HBUtil.getValueFromTab("b01id", "B01", "b0111='"+a0201bSeclect+"'");
        //职务id
        String zwzc00 = this.getPageElement("a0192aSeclect").getValue();
        
        //其他职务
        String a0192aQT = this.getPageElement("a0192aQT").getValue();
        
        //职级
        String a0192e = this.getPageElement("a0192e").getValue();
        //职级时间
        String a0192c = this.getPageElement("a0192c").getValue();
        
        String a0000 = this.getPageElement("a0000").getValue();
        //新增是统一任职时间  默认当前时间
        String a0243Seclect = this.getPageElement("a0243Seclect").getValue();
        
        
        HBSession sess = HBUtil.getHBSession();
        if (StringUtils.isBlank(a0000)) {
        	this.setMainMessage("请选择人员！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        if (StringUtils.isBlank(zwzc00)&&StringUtils.isBlank(a0192aQT)) {
        	this.setMainMessage("请选择工作单位及职务或填写其他职务！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        
        //职务库
        if(!StringUtils.isBlank(zwzc00)){
        	
        	//获取职务信息
        	A02Zwzc a02Zwzc = (A02Zwzc)sess.get(A02Zwzc.class,zwzc00);
        	if(a02Zwzc==null){
        		this.setMainMessage("请选择工作单位及职务！");
        		return EventRtnType.NORMAL_SUCCESS;
        	}
        	//获取a02 的集体内排序号 a0201a 0,a0201c 1,a0201b 2,b0111 3,a0223 4,a0225 5,a0256a 6,a0256 7,a0229 8,a0283g 9,a0245 10,a0247 11,a0251b 12,
        	String a02Sql = "select a0201a,a0201c,a0201b,"
        			+ "(select b01id from b01 where b01.b0111=a.a0201b) b0111,a0223,"
        			+ "a0225,a0256a,a0256,a0229,a0283g,a0245,a0247,a0251b from A02 a where a0000='"+a0000+"'";
        	Map<String,Object[]> a02sortMap = new HashMap<String,Object[]>();
        	List<Object[]> a02list = sess.createSQLQuery(a02Sql).list();
        	if(a02list!=null&&a02list.size()>0){
        		Object[] o = a02list.get(0);
        		if(o[2]!=null){
        			a02sortMap.put(o[3].toString(), o);
        		}
        		
        	}
        	
        	//删除a02
        	// HBUtil.executeUpdate("delete from a02 where a0000=? and exists(select 1 from a01_zw_a02 x where x.a0200=a02.a0200)",new Object[]{a0000});
        	HBUtil.executeUpdate("delete from a02 where a0000=? and not exists(select 1 from a01_zw x where x.zwzc00=a02.a0200)",new Object[]{a0000});
        	HBUtil.executeUpdate("delete from a01_zw_a02 where a0000=?",new Object[]{a0000});
        	HBUtil.executeUpdate("delete from a01_zw where a0000=? and zwtype='1'",new Object[]{a0000});
        	
        	//新增a02职务关联
        	String inserta01SQL = "insert into a01_zw(zwzc00,a0192a,a0165,a0221,a0192e,jzaz,a0000,b01id,zwtype,a0243seclect ) values"
        			+ "(?,?,?,?,?,?,?,?,'1',?)";
        	HBUtil.executeUpdate(inserta01SQL,new Object[]{a02Zwzc.getZwzc00(),
        			a02Zwzc.getA0192a(),a02Zwzc.getA0165(),a02Zwzc.getA0221(),a02Zwzc.getA0192e(),a02Zwzc.getJzaz(),a0000,b01id,a0243Seclect});
        	//新增a02职务  及关联
        	//获取职务库职务明细
        	String zwmxSQL = "from A02ZwzcMx where zwzc00='"+zwzc00+"'";
        	List<A02ZwzcMx> zwmxlist = sess.createQuery(zwmxSQL).list();
        	String inserta01_a02SQL = "insert into a01_zw_a02(a0000,a0200,zwmx00,zwzc00) values"
        			+ "(?,?,?,?)";
        	String inserta02SQL = "insert into a02(a0000,a0200,a0201a,a0201b,a0201d,a0201e,a0215a,"
        			+ "a0223,a0225,a0243,a0245,a0247,a0251b,a0255,a0281,"
        			+ "a0256a,a0256,a0229,a0201c,a0279,a0248,a0283g) values"
        			+ "(?,?,?,?,?,?,?"
        			+ ",?,?,?,?,?,?,?,?"
        			+ ",?,?,?,?,?,?,?)";
        	if(zwmxlist!=null&&zwmxlist.size()>0){
        		for(A02ZwzcMx a02ZwzcMx : zwmxlist){
        			
        			String a0200 = UUID.randomUUID().toString();
        			String b01idref = a02ZwzcMx.getB01id();
        			List<B01> b01list = sess.createQuery("from B01 where b01id='"+b01idref+"'").list();
        			if(b01list.size()>0){
        				B01 b01 = b01list.get(0);
        				
        				Object[] a02InfoArray = a02sortMap.get(b01idref);
        				if(a02InfoArray == null){
        					a02InfoArray = new Object[20];
        				}
        				Object[] a02ParmsArray = new Object[]{a0000, a0200, a02ZwzcMx.getA0201a(), b01.getB0111(), a02ZwzcMx.getA0201d(), 
        						a02ZwzcMx.getA0201e(), a02ZwzcMx.getA0215a()
        						,a02InfoArray[4], a02InfoArray[5], a0243Seclect,a02InfoArray[10], a02InfoArray[11], a02InfoArray[12], "1", "true"
        						,a02InfoArray[6], a02InfoArray[7], a02InfoArray[8], b01.getB0104(), a02ZwzcMx.getA0279(), a02ZwzcMx.getA0248(), a02InfoArray[9]};
        				
        				Object[] a01_a02ParmsArray = new Object[]{a0000, a0200, a02ZwzcMx.getZwmx00(), zwzc00};
        				
        				HBUtil.executeUpdate(inserta02SQL,a02ParmsArray);
        				HBUtil.executeUpdate(inserta01_a02SQL,a01_a02ParmsArray);
        			}
        			
        		}
        		
        	}
        }else{//其他职务
        	zwzc00 = this.getPageElement("zwzc00").getValue();
        	if(StringUtils.isEmpty(zwzc00)){//新增
        		//获取a02 的集体内排序号 a0201a 0,a0201c 1,a0201b 2,b0111 3,a0223 4,a0225 5,a0256a 6,a0256 7,a0229 8,a0283g 9,a0245 10,a0247 11,a0251b 12,
            	String a02Sql = "select a0201a,a0201c,a0201b,"
            			+ "(select b01id from b01 where b01.b0111=a.a0201b) b0111,a0223,"
            			+ "a0225,a0256a,a0256,a0229,a0283g,a0245,a0247,a0251b from A02 a where a0000='"+a0000+"'";
            	Map<String,Object[]> a02sortMap = new HashMap<String,Object[]>();
            	List<Object[]> a02list = sess.createSQLQuery(a02Sql).list();
            	if(a02list!=null&&a02list.size()>0){
            		Object[] o = a02list.get(0);
            		if(o[2]!=null){
            			a02sortMap.put(o[3].toString(), o);
            		}
            		
            	}
            	
            	
            	String a0200 = UUID.randomUUID().toString();
            	zwzc00 = a0200;
            	//新增a02职务关联
            	String inserta01SQL = "insert into a01_zw(zwzc00,a0192a,a0165,a0221,a0192e,jzaz,a0000,b01id,zwtype,a0192c,a0243seclect ) values"
            			+ "(?,?,?,?,?,?,?,?,'2',?,?)";
            	//其他职务
                //String a0192aQT;
                //职级
                //String a0192e;
                //职级时间
                //String a0192c;
            	HBUtil.executeUpdate(inserta01SQL,new Object[]{a0200,
            			a0192aQT,"","",a0192e,"",a0000,b01id,a0192c,a0243Seclect});
            	
            	//新增a02职务  及关联
            	/*String inserta01_a02SQL = "insert into a01_zw_a02(a0000,a0200) values"
            			+ "(?,?)";*/
            	String inserta02SQL = "insert into a02(a0000,a0200,a0201a,a0201b,a0201d,a0201e,a0215a,"
            			+ "a0223,a0225,a0243,a0245,a0247,a0251b,a0255,a0281,"
            			+ "a0256a,a0256,a0229,a0201c,a0279,a0248,a0283g) values"
            			+ "(?,?,?,?,?,?,?"
            			+ ",?,?,?,?,?,?,?,?"
            			+ ",?,?,?,?,?,?,?)";
            	
            	List<B01> b01list = sess.createQuery("from B01 where b01id='"+b01id+"'").list();
    			if(b01list.size()>0){
    				B01 b01 = b01list.get(0);
    				
    				Object[] a02InfoArray = a02sortMap.get(b01id);
    				if(a02InfoArray == null){
    					a02InfoArray = new Object[20];
    				}
    				
    				
    				Object[] a02ParmsArray = new Object[]{a0000, a0200, a0201bSeclectText, a0201bSeclect, "0", 
    						"", a0192aQT
    						,a02InfoArray[4], a02InfoArray[5], a0243Seclect,"", "", "0", "1", "true"
    						,"", "", "", b01.getB0104(), "0", "1", a0192aQT};
    				
    				//Object[] a01_a02ParmsArray = new Object[]{a0000, a0200};
    				
    				HBUtil.executeUpdate(inserta02SQL,a02ParmsArray);
    				//HBUtil.executeUpdate(inserta01_a02SQL,a01_a02ParmsArray);
    				
    			}
        	}else{//修改
        		//以后再改
        		String updatea01SQL = "update a01_zw set a0192a=?,a0192e=?,b01id=?,a0192c=?,a0243Seclect=?"
        				+ " where zwzc00=?";
        		//a0201a a0201b a0215a a0201c
        		String updatea02SQL = "update a02 set a0201a=?,a0201b=?"
        				+ " where a0200=?";
            	//其他职务
                //String a0192aQT;
                //职级
                //String a0192e;
                //职级时间
                //String a0192c;
            	/*HBUtil.executeUpdate(updatea01SQL,new Object[]{zwzc00,
            			a0192aQT,"","",a0192e,"",a0000,b01id,a0192c,a0243Seclect});*/
        	}
        	this.getPageElement("zwzc00").setValue(zwzc00);
        	
        }
        
        
        
        this.setNextEventName("memberGridMain.dogridquery");
        this.setMainMessage("保存工作单位及职务成功！");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @NoRequiredValidate
    @PageEvent("saveZWMX.onclick")
    public int saveZWMX() throws Exception {
        String a0200 = this.getPageElement("a0200").getValue();
        
        
        HBSession sess = HBUtil.getHBSession();
        if (StringUtils.isBlank(a0200)) {
        	this.setMainMessage("请选择职务！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        
        //获取职务信息
        A02 a02 = (A02)sess.get(A02.class,a0200);
        if(a02 == null){
        	this.setMainMessage("请选择职务！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        String a0247 = this.getPageElement("a0247").getValue();
        String a0251b = this.getPageElement("a0251b").getValue();
        String a0243 = this.getPageElement("a0243").getValue();
        String a0245 = this.getPageElement("a0245").getValue();
        String a0283g = this.getPageElement("a0283g").getValue();
        String a0229 = this.getPageElement("a0229").getValue();
        String a0256 = this.getPageElement("a0256").getValue();
        String a0256a = this.getPageElement("a0256a").getValue();
        a02.setA0247(a0247);
        a02.setA0251b(a0251b);
        a02.setA0243(a0243);
        a02.setA0245(a0245);
        a02.setA0283g(a0283g);
        a02.setA0229(a0229);
        a02.setA0256(a0256);
        a02.setA0256a(a0256a);
        sess.update(a02);
        sess.flush();
        
        this.setNextEventName("memberGrid.dogridquery");
        this.setMainMessage("保存职务成功");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
   
    @NoRequiredValidate
    @PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
    	String a0000 = this.getPageElement("a0000").getValue();
    	String zwtype = this.getPageElement("zwtype").getValue();
    	
		//定义用来组装sql的变量
    	String sql = "select  * from A02 where a0000='"+a0000+"'"
    			+ " and exists(select 1 from a01_zw_a02 x where x.a0200=a02.a0200) order by a0223 ";
		if("2".equals(zwtype)){
			sql = "select  * from A02 where a0000='"+a0000+"'"
	    			+ " and exists(select 1 from a01_zw x where x.zwzc00=a02.a0200) order by a0223 ";
		}
		
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
    
    @NoRequiredValidate
    @PageEvent("memberGridMain.dogridquery")
    public int doMemberGridMainQuery(int start,int limit) throws RadowException, AppException{
    	String a0000 = this.getPageElement("a0000").getValue();
        HBSession session = HBUtil.getHBSession();
        if (StringUtils.isBlank(a0000)) {
        	this.setMainMessage("请选择人员！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
    	//定义用来组装sql的变量
    	StringBuffer str = new StringBuffer();
    	str.append("select * from A01_ZW where a0000 ='"+a0000+"'");
    	
    	
    	
    	this.pageQuery(str.toString(), "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
    
    /**
	 * 选择行修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGridMain.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int assessmentInfoGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("memberGridMain").getCueRowIndex();
		PageElement gridrow = this.getPageElement("memberGridMain");
		String zwzc00 = gridrow.getValue("zwzc00",index).toString();
		String b01id = gridrow.getValue("b01id",index).toString();
		String a0192a = gridrow.getValue("a0192a",index).toString();
		String a0243Seclect = gridrow.getValue("a0243seclect",index).toString();
		String a0192e = gridrow.getValue("a0192e",index).toString();
		String a0192c = gridrow.getValue("a0192c",index).toString();
		String zwtype = gridrow.getValue("zwtype",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//获取职务信息
	        String b0111 = HBUtil.getValueFromTab("b0111", "B01", "b01id='"+b01id+"'");
	        String b0101 = HBUtil.getValueFromTab("b0101", "B01", "b01id='"+b01id+"'");
	        
			this.getPageElement("zwzc00").setValue(zwzc00);
			this.getPageElement("zwtype").setValue(zwtype);
			
			
			this.getPageElement("a0201bSeclect").setValue(b0111);
			this.getPageElement("a0201bSeclect_combo").setValue(b0101);
			
			
			
			
			
	        
	        if("1".equals(zwtype)){
	        	this.getPageElement("a0192aSeclect").setValue(zwzc00);
		        this.getPageElement("a0192aSeclect_combo").setValue(a0192a);
		        this.getPageElement("a0192aQT").setValue("");
		        this.getExecuteSG().addExecuteCode("var record={};record['data']={};record['data']['key']='"+b0111+"';setZWInfo(record);");
			}else{
				this.getExecuteSG().addExecuteCode("var record={};record['data']={};record['data']['key']='';setZWInfo(record);");
				this.getPageElement("a0192aQT").setValue(a0192a);
				
				this.getPageElement("a0192aSeclect").setValue("");
		        this.getPageElement("a0192aSeclect_combo").setValue("");
			}
	        this.getPageElement("a0243Seclect").setValue(a0243Seclect);
	        
	        this.getPageElement("a0192e").setValue(a0192e);
	        this.getPageElement("a0192c").setValue(a0192c);
			
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
    
    
    
    
    
    @PageEvent("deleteRowMain")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleteRowMain(String zzs00)throws RadowException, AppException{
    	try {
    		
    		HBUtil.executeUpdate("delete from b01_zzs_ld where zzs00=?",
    				new Object[]{zzs00});
    		HBUtil.executeUpdate("delete from b01_zzs where zzs00=?",
    				new Object[]{zzs00});
    		this.setNextEventName("memberGridMain.dogridquery");
    		
    		
    	} catch (Exception e) {
    		this.setMainMessage("删除失败！");
    		return EventRtnType.FAILD;
    	}
    	
    	
    	
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
	@PageEvent("setZWInfo")
	@NoRequiredValidate
	@Transaction
	public int setZWInfo(String id)throws RadowException{
		
    	
    	if(StringUtils.isEmpty(id)){
    		((Combo)this.getPageElement("a0192aSeclect")).setValueListForSelect(new LinkedHashMap<String, Object>());
    		return EventRtnType.NORMAL_SUCCESS;
    	}
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+id+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		
		if(B0194 != null && B0194.equals("3")) {

			String msg = "不可选择机构分组单位！";

			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','" + msg + "');");
			this.getPageElement("a0201bSeclect").setValue("");
			this.getPageElement("a0201bSeclect_combo").setValue("");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			//String b0101 = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			String b01id = HBUtil.getValueFromTab("b01id", "B01", "b0111='"+id+"'");
			
			String selSqL = "select t.zwzc00,t.a0192a from A02_ZWZC t where t.zwzc00 in(select zwzc00 from A02_ZWZC_MX where b01id='"+b01id+"') order by sortid";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode;
			listCode = cqbs.getListBySQL(selSqL);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				mapCode.put(listCode.get(i).get("zwzc00").toString(), listCode.get(i).get("a0192a")==null?"":listCode.get(i).get("a0192a"));
			}
			((Combo)this.getPageElement("a0192aSeclect")).setValueListForSelect(mapCode);
			
		} catch (AppException e) {
			this.setMainMessage("查询出错！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    @PageEvent("AddBtn.onclick")
	@NoRequiredValidate
	public int AddBtn(String id)throws RadowException{
		
    	this.getPageElement("a0201bSeclect").setValue("");
		this.getPageElement("a0201bSeclect_combo").setValue("");
		
        this.getPageElement("a0192aSeclect").setValue("");
        this.getPageElement("a0192aSeclect_combo").setValue("");
        //新增是统一任职时间  默认当前时间
        this.getPageElement("a0243Seclect").setValue("");
        this.getPageElement("a0192aQT").setValue("");
        
        
        this.getPageElement("a0192e").setValue("");
        this.getPageElement("a0192c").setValue("");
        
        
		return EventRtnType.NORMAL_SUCCESS;
	}
    
}
