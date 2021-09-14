package com.insigma.siis.local.pagemodel.gzgl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.IDUtil;
//import com.insigma.siis.local.business.entity.Rztx;
import com.insigma.siis.local.business.entity.Wpgz;

public class WpgzAddPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
    	this.setNextEventName("memberGrid.dogridquery");
        this.setNextEventName("loaddata");
        return EventRtnType.NORMAL_SUCCESS;
    }
    @PageEvent("loaddata")
    public int dogridquery() throws RadowException{
        String id=this.getPageElement("id").getValue();
        StringBuilder sb = new StringBuilder("select a01.A0000, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, nvl(a01.A0221,' ') as zw, nvl(a01.A0192E,' ') as zj, ")
                .append(" nvl(to_char(wm_concat(a02.A0201A)),' ') as szcs, g.id, nvl(g.xpdw,' '), nvl(g.gzlx,' '), nvl(g.gzrw,' '), nvl(g.qzsj,' '), nvl(g.khqk,' '), nvl(g.dybz,' '), nvl(g.grzw,' '),nvl(g.kssj,' '),nvl(g.jssj,' '),nvl(g.yxnr,' '),nvl(g.jsgz,' ')")
                .append(" from a01 ")
                .append(" join a02 on a02.a0000 = a01.a0000 ")
                .append(" join wpgz g on g.a0000 = a01.a0000 ")
                .append(" where 1 = 1 and id='"+id.toString()+"'");
        sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E,g.id, g.xpdw, g.gzlx, g.gzrw, g.qzsj, g.khqk, g.dybz, g.grzw,g.kssj,g.jssj,g.yxnr,g.jsgz");
        List<Object[]> a=HBUtil.getHBSession().createSQLQuery(sb.toString()).list();

        Object[] mapdata=null;
        if(a.size()>0){
            mapdata=a.get(0);
            //this.getPageElement("a0000").setValue(mapdata[0].toString());
            //this.getPageElement("a0200").setValue(mapdata[18].toString());
            this.getPageElement("personId").setValue(mapdata[0].toString());
            this.getPageElement("xm").setValue(mapdata[1].toString());
            this.getPageElement("xb").setValue(mapdata[2].toString());
            this.getPageElement("csny").setValue(mapdata[3].toString());
            this.getPageElement("zw").setValue(mapdata[4].toString());
            this.getPageElement("zj").setValue(mapdata[5].toString());
            this.getPageElement("szcs").setValue(mapdata[6].toString());
            this.getPageElement("id").setValue(mapdata[7].toString());
            this.getPageElement("xpdw").setValue(mapdata[8].toString());
            this.getPageElement("gzlx").setValue(mapdata[9].toString());
            this.getPageElement("gzrw").setValue(mapdata[10].toString());
            this.getPageElement("qzsj").setValue(mapdata[11].toString());
            this.getPageElement("khqk").setValue(mapdata[12].toString());
            this.getPageElement("dybz").setValue(mapdata[13].toString());
            this.getPageElement("grzw").setValue(mapdata[14].toString());
            this.getPageElement("kssj").setValue(mapdata[15].toString());
            this.getPageElement("jssj").setValue(mapdata[16].toString());
            this.getPageElement("yxnr").setValue(mapdata[17].toString());
            this.getPageElement("jsgz").setValue(mapdata[18].toString());
        }
/*        String a0000 = this.getPageElement("a0000").getValue();
        String ynId = this.getPageElement("id").getValue();
        HBSession sess = HBUtil.getHBSession();

       try {
            //设置文件信息
            List<TpbAttCjqk> tpbAttlist = sess.createQuery("from TpbAttCjqk where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();

            if(tpbAttlist!=null){
                List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
                for(TpbAttCjqk jsatt : tpbAttlist){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", jsatt.getJsa00());
                    map.put("name", jsatt.getJsa04());
                    map.put("fileSize", jsatt.getJsa08());
                    listmap.add(map);
                }
                this.setFilesInfo("file",listmap);

            }
        } catch (Exception e) {
            this.setMainMessage("查询失败！");
            e.printStackTrace();
            return EventRtnType.FAILD;
        }*/
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("freshInfo")
    public int freshInfo() throws RadowException, AppException {
        String personId = this.getPageElement("personId").getValue();
        // 拼接sql，获取信息
        StringBuffer sb = new StringBuffer();
        sb.append("select a01.A0000 as id, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, a01.A0221 as zw, a01.A0192E as zj, ");
        sb.append(" to_char(wm_concat(a02.A0201A)) as rzjg ");
        sb.append(" from a01 ");
        sb.append(" join a02 on a02.a0000 = a01.a0000 ");
        sb.append(" where 1 = 1 ");
        sb.append(" and a01.a0000 = '" + personId + "' ");
        sb.append(" and a02.a0255 = '1' ");
        sb.append(" and a02.A0201A is not null ");
        sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E ");
        Object[] result = (Object[]) HBUtil.getHBSession().createSQLQuery(sb.toString()).uniqueResult();
        // 页面赋值
        this.getPageElement("xm").setValue(toString(result[1]));
        this.getPageElement("xb").setValue(toString(result[2]));
        this.getPageElement("csny").setValue(formatDate(toString(result[3])));
        String zw = HBUtil.getCodeName("ZB09", toString(result[4]));
        this.getPageElement("zw").setValue(zw);
        this.getPageElement("zj").setValue(toString(result[5]));
        this.getPageElement("szcs").setValue(toString(result[6]));

        return EventRtnType.NORMAL_SUCCESS;
    }

    private String toString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    private String formatDate(String date) {
        if (date.length() == 6 || date.length() == 8) {
            return date.substring(0, 4) + "." + date.substring(4, 6);
        } else {
            return date;
        }
    }

    @PageEvent("save.onclick")
    public int save() throws RadowException, AppException {
        PageElement id = this.getPageElement("id");
        HBSession session = HBUtil.getHBSession();
        if (StringUtils.isBlank(id.getValue())) {
            // 新增数据
            //Rztx  record2=new Rztx();
            Wpgz record = new Wpgz();
            String uuid=IDUtil.generateUUID();
            record.setId(uuid);
            record.setA0000(this.getPageElement("personId").getValue());
            record.setGrzw(this.getPageElement("grzw").getValue());
            record.setXpdw(this.getPageElement("xpdw").getValue());
            record.setGzlx(this.getPageElement("gzlx").getValue());
            record.setGzrw(this.getPageElement("gzrw").getValue());
            record.setQzsj(this.getPageElement("qzsj").getValue());
            record.setKhqk(this.getPageElement("khqk").getValue());
            record.setDybz(this.getPageElement("dybz").getValue());
            record.setKssj(this.getPageElement("kssj").getValue());
            record.setJssj(this.getPageElement("jssj").getValue());
            record.setYxnr(this.getPageElement("yxnr").getValue());
            record.setJsgz(this.getPageElement("jsgz").getValue());
            /*record2.setId(uuid);
            record2.setA0000(uuid);
            record2.setA0101(this.getPageElement("xm").getValue());
            record2.setRztxsj(this.getPageElement("qzsj").getValue());
            record2.setRztxnr(this.getPageElement("yxnr").getValue());
            record2.setState("0");//默认状态未读
            session.save(record2);*/
            session.save(record);
            session.flush();
            id.setValue(record.getId());
        } else {
            // 修改记录
            //Rztx record2 = (Rztx) session.get(Rztx.class,id.getValue());
            Wpgz record = (Wpgz) session.load(Wpgz.class, id.getValue());
            record.setA0000(this.getPageElement("personId").getValue());
            record.setGrzw(this.getPageElement("grzw").getValue());
            record.setXpdw(this.getPageElement("xpdw").getValue());
            record.setGzlx(this.getPageElement("gzlx").getValue());
            record.setGzrw(this.getPageElement("gzrw").getValue());
            record.setQzsj(this.getPageElement("qzsj").getValue());
            record.setKhqk(this.getPageElement("khqk").getValue());
            record.setDybz(this.getPageElement("dybz").getValue());
            record.setKssj(this.getPageElement("kssj").getValue());
            record.setJssj(this.getPageElement("jssj").getValue());
            record.setYxnr(this.getPageElement("yxnr").getValue());
            record.setJsgz(this.getPageElement("jsgz").getValue());
            /*if(record2!=null) {
                record2.setA0101(this.getPageElement("xm").getValue());
                record2.setRztxsj(this.getPageElement("qzsj").getValue());
                record2.setRztxnr(this.getPageElement("yxnr").getValue());
                session.update(record2);
            }*/
            session.update(record);
            session.flush();
        }

        this.setMainMessage("保存成功");
        this.getExecuteSG().addExecuteCode("realParent.PersonQuery()");
        //this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('wpgzAdd').close()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    
    
    
    
    @PageEvent("addETCInfo")
	@Transaction
	public int addInfo() throws Exception{
    	String id = this.getPageElement("id").getValue();
    	
		String gzbz01 = this.getPageElement("gzbz01").getValue();
		String gzbz02 = this.getPageElement("gzbz02").getValue();
		String gzbz00 = this.getPageElement("gzbz00").getValue();
		if(StringUtils.isEmpty(id)){
			this.setMainMessage("请先保存挂职信息！");
			this.setNextEventName("memberGrid.dogridquery");
		}
		if(StringUtils.isEmpty(gzbz00)){
			gzbz00 = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into WPGZ_bz(gzbz00,gzbz01,gzbz02,gzid) values(?,?,?,?)",
					new Object[]{gzbz00,gzbz01,gzbz02,id});
		}else{
			HBUtil.executeUpdate("update WPGZ_bz set gzbz01=?,gzbz02=? where gzbz00=?",
					new Object[]{gzbz01,gzbz02,gzbz00});
		}
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    @PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String id = this.getPageElement("id").getValue();
		//定义用来组装sql的变量
		StringBuffer str = new StringBuffer();
		str.append("select * from WPGZ_bz where gzid='"+id+"' ");
		
		
		
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
    
    
    @PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String gzbz00)throws RadowException, AppException{
		try {
			
			HBUtil.executeUpdate("delete from WPGZ_bz where gzbz00=?",
					new Object[]{gzbz00});
			this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery');");
			
			
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    
}
