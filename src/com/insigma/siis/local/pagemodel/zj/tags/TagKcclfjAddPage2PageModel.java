package com.insigma.siis.local.pagemodel.zj.tags;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.extra.TagKcclfj2;

public class TagKcclfjAddPage2PageModel extends PageModel {

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
		this.setNextEventName("tagKcclfjGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("tagKcclfjGrid.dogridquery")
	@NoRequiredValidate
	public int tagKcclfjGrid(int start, int limit) throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select tagid, file_url fileurl, file_name filename, file_size filesize, updatedate, note from tag_kcclfj2 where a0000='"
				+ a0000 + "' order by updatedate desc";
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询

		this.setNextEventName("updateRmb");
		return EventRtnType.SPE_SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	@PageEvent("updateRmbFresh")
	public int updateRmbFresh() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sqlTagKcclfj2 = "from TagKcclfj2 where a0000='" + a0000 + "' order by updatedate desc";
		List tagKcclfj2List = sess.createQuery(sqlTagKcclfj2).list();

		TagKcclfj2 tagKcclfj20 = null;
		TagKcclfj2 tagKcclfj21 = null;
		if (null == tagKcclfj2List || 0 == tagKcclfj2List.size()) {
			tagKcclfj20 = new TagKcclfj2();
			tagKcclfj21 = new TagKcclfj2();
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filename').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filesize').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filename').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filesize').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20id').style.display='none'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21id').style.display='none'");
		} else if(1 == tagKcclfj2List.size()){
			tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
			tagKcclfj21 = new TagKcclfj2();
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20id').style.display='block'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filename').innerHTML='" + tagKcclfj20.getFilename() + "'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filesize').innerHTML='" + tagKcclfj20.getFilesize() + "'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filename').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filesize').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21id').style.display='none'");
		} else if(2 <= tagKcclfj2List.size()){
			tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
			tagKcclfj21 = (TagKcclfj2) tagKcclfj2List.get(1);
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20id').style.display='block'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21id').style.display='block'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filename').innerHTML='" + tagKcclfj20.getFilename() + "'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filesize').innerHTML='" + tagKcclfj20.getFilesize() + "'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filename').innerHTML='" + tagKcclfj21.getFilename() + "'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filesize').innerHTML='" + tagKcclfj21.getFilesize() + "'");
		} else {
			tagKcclfj20 = new TagKcclfj2();
			tagKcclfj21 = new TagKcclfj2();
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filename').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20_filesize').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filename').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21_filesize').innerHTML=''");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj20id').style.display='none'");
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('tagKcclfj21id').style.display='none'");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
