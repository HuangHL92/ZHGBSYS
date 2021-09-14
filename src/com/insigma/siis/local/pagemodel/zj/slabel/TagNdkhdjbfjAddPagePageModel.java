package com.insigma.siis.local.pagemodel.zj.slabel;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.slabel.TagNdkhdjbfj;

public class TagNdkhdjbfjAddPagePageModel extends PageModel {

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
	this.setNextEventName("tagNdkhdjbfjGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("tagNdkhdjbfjGrid.dogridquery")
    @NoRequiredValidate
    public int tagNdkhdjbfjGrid(int start, int limit) throws RadowException {
	String a0000 = this.getPageElement("subWinIdBussessId").getValue();
	String sql = "select tagid, file_url fileurl, file_name filename, file_size filesize, updatedate, note from zjs_tag_ndkhdjbfj where a0000='" + a0000 + "' order by updatedate desc";
	this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
	return EventRtnType.SPE_SUCCESS;
    }

    @SuppressWarnings("rawtypes")
    @PageEvent("updateRmbFresh")
    public int updateRmbFresh() throws RadowException {
	HBSession sess = HBUtil.getHBSession();
	String a0000 = this.getPageElement("subWinIdBussessId").getValue();
	String sqlTagNdkhdjbfj = "from com.insigma.siis.local.business.slabel.TagNdkhdjbfj where a0000='" + a0000 + "' order by updatedate desc";
	List tagNdkhdjbfjList = sess.createQuery(sqlTagNdkhdjbfj).list();

	TagNdkhdjbfj tagNdkhdjbfj0 = null;
	TagNdkhdjbfj tagNdkhdjbfj1 = null;
	if (null == tagNdkhdjbfjList || 0 == tagNdkhdjbfjList.size()) {
	    tagNdkhdjbfj0 = new TagNdkhdjbfj();
	    tagNdkhdjbfj1 = new TagNdkhdjbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0id').style.display='none'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1id').style.display='none'");
	} else if (1 == tagNdkhdjbfjList.size()) {
	    tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
	    tagNdkhdjbfj1 = new TagNdkhdjbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filename').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filesize').innerHTML=''");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1id').style.display='none'");
	} else if (2 <= tagNdkhdjbfjList.size()) {
	    tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
	    tagNdkhdjbfj1 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(1);
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1id').style.display='block'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filename').innerHTML='" + tagNdkhdjbfj1.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filesize').innerHTML='" + tagNdkhdjbfj1.getFilesize() + "'");
	} else {
	    tagNdkhdjbfj0 = new TagNdkhdjbfj();
	    tagNdkhdjbfj1 = new TagNdkhdjbfj();
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filename').innerHTML='" + tagNdkhdjbfj1.getFilename() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1_filesize').innerHTML='" + tagNdkhdjbfj1.getFilesize() + "'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj0id').style.display='none'");
	    this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tagNdkhdjbfj1id').style.display='none'");
	}

	return EventRtnType.NORMAL_SUCCESS;
    }
}
