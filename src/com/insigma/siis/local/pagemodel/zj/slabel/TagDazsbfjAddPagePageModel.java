package com.insigma.siis.local.pagemodel.zj.slabel;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.slabel.TagDazsbfj;

public class TagDazsbfjAddPagePageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
	this.setNextEventName("initX");
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initX")
    @NoRequiredValidate
    public int initX() throws RadowException {
	// 获取人员主键a0000
	String a0000 = this.getPageElement("subWinIdBussessId").getValue();
	if (a0000 == null || "".equals(a0000)) {
	    this.setMainMessage("请先保存人员基本信息！");
	    return EventRtnType.NORMAL_SUCCESS;
	}
	this.getPageElement("a0000").setValue(a0000);
	this.setNextEventName("tagDazsbfjGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("tagDazsbfjGrid.dogridquery")
    @NoRequiredValidate
    public int tagDazsbfjGrid(int start, int limit) throws RadowException {
	String a0000 = this.getPageElement("subWinIdBussessId").getValue();
	String sql = "select tagid, file_url fileurl, file_name filename, file_size filesize, updatedate, note from zjs_tag_dazsbfj where a0000='" + a0000 + "' order by updatedate desc";
	this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
	return EventRtnType.SPE_SUCCESS;
    }

    @SuppressWarnings("rawtypes")
    @PageEvent("updateRmbFresh")
    public int updateRmbFresh() throws RadowException {
	HBSession sess = HBUtil.getHBSession();
	String a0000 = this.getPageElement("subWinIdBussessId").getValue();
	String sqlTagDazsbfj = "from com.insigma.siis.local.business.slabel.TagDazsbfj where a0000='" + a0000 + "' order by updatedate desc";
	List tagDazsbfjList = sess.createQuery(sqlTagDazsbfj).list();

	TagDazsbfj tagDazsbfj0 = null;
	TagDazsbfj tagDazsbfj1 = null;
	if (null == tagDazsbfjList || 0 == tagDazsbfjList.size()) {
	    tagDazsbfj0 = new TagDazsbfj();
	    tagDazsbfj1 = new TagDazsbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0id').style.display='none'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1id').style.display='none'");
	} else if (1 == tagDazsbfjList.size()) {
	    tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
	    tagDazsbfj1 = new TagDazsbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filename').innerHTML='" + tagDazsbfj0.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filesize').innerHTML='" + tagDazsbfj0.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1id').style.display='none'");
	} else if (2 <= tagDazsbfjList.size()) {
	    tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
	    tagDazsbfj1 = (TagDazsbfj) tagDazsbfjList.get(1);
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filename').innerHTML='" + tagDazsbfj0.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filesize').innerHTML='" + tagDazsbfj0.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filename').innerHTML='" + tagDazsbfj1.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filesize').innerHTML='" + tagDazsbfj1.getFilesize() + "'");
	} else {
	    tagDazsbfj0 = new TagDazsbfj();
	    tagDazsbfj1 = new TagDazsbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj0id').style.display='none'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagDazsbfj1id').style.display='none'");
	}
	return EventRtnType.NORMAL_SUCCESS;
    }

}
