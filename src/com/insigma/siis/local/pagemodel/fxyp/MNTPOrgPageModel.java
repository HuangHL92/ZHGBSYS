package com.insigma.siis.local.pagemodel.fxyp;

import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.a00hz.HzMntpOrgQt;
import com.insigma.siis.local.business.entity.a00hz.HzMntpOrgQxs;

public class MNTPOrgPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
        return EventRtnType.NORMAL_SUCCESS;
    }



    @PageEvent("save.onclick")
    public int save() throws RadowException, AppException {
        String mntp05 = this.getPageElement("mntp05").getValue();
        String mntp00 = this.getPageElement("mntp00").getValue();
        
        if("2".equals(mntp05)){
        	HzMntpOrgQxs qxsOrg = new HzMntpOrgQxs();
            //PMPropertyCopyUtil.copyObjValueToElement(zzs, this);
            PMPropertyCopyUtil.copyElementsValueToObj(qxsOrg, this);
            qxsOrg.setBztype("2");
            qxsOrg.setB01id(UUID.randomUUID().toString());
            HBSession sess = HBUtil.getHBSession();
            sess.save(qxsOrg);
            sess.flush();
        }else{
        	String b0101 = this.getPageElement("b0101").getValue();
        	HzMntpOrgQt qxsQt = new HzMntpOrgQt();
            //PMPropertyCopyUtil.copyObjValueToElement(zzs, this);
            PMPropertyCopyUtil.copyElementsValueToObj(qxsQt, this);
            qxsQt.setJgmc(b0101);
            qxsQt.setBztype(mntp05);
            qxsQt.setB01id(UUID.randomUUID().toString());
            qxsQt.setB0234(qxsQt.getZzhd());
            qxsQt.setB0235(qxsQt.getFzhd());
            HBSession sess = HBUtil.getHBSession();
            sess.save(qxsQt);
            sess.flush();
        }
        
        //this.setMainMessage("±£´æ³É¹¦");
        this.getExecuteSG().addExecuteCode("realParent.searchn();parent.Ext.getCmp('MNTPOrg').close();" );
        return EventRtnType.NORMAL_SUCCESS;
    }

    
    
    
    
    
   
}
