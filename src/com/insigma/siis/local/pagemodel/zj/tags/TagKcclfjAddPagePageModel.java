package com.insigma.siis.local.pagemodel.zj.tags;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.extra.TagKcclfj;
import com.insigma.siis.local.pagemodel.zj.JUpload.JUpload;

public class TagKcclfjAddPagePageModel extends PageModel implements JUpload {

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("save.onclick")
	public int save() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("tagid").setValue(UUID.randomUUID().toString().replaceAll("-", ""));
		this.upLoadFile("kcclfiles");
		this.getExecuteSG().addExecuteCode("saveCallBack('�ϴ��ɹ�','1');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				// ��ȡ����Ϣ
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// ����ļ�����1M����ʾM��С������ʾkb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi, fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public static String disk = JSGLBS.HZBPATH;

	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		String tagid = formDataMap.get("tagid");
		String filename = formDataMap.get("Filename");
		String a0000 = formDataMap.get("subWinIdBussessId");
		TagKcclfj tagKcclfj = new TagKcclfj();
		tagKcclfj.setTagid(tagid);	// �����������
		tagKcclfj.setA0000(a0000);	// ��ά����Ա����
		tagKcclfj.setCreatedate(System.currentTimeMillis());	// �ϴ�ʱ��
		tagKcclfj.setFjsize(fileSize);	// ��������ļ���С
		tagKcclfj.setFjname(filename);	// ��������ļ�����

		String directory = "tag\\kcclfjfiles" + File.separator + a0000 + File.separator;
		String filePath = directory + tagKcclfj.getTagid();
		File f = new File(disk + directory);

		if (!f.isDirectory()) {
			f.mkdirs();
		}
		fi.write(new File(disk + filePath));
		tagKcclfj.setDirectory(directory);
		HBUtil.getHBSession().save(tagKcclfj);
		HBUtil.getHBSession().flush();

		return tagKcclfj.getTagid();
	}

	@Override
	public String deleteFile(String tagid) {
		try {
			HBSession sess = HBUtil.getHBSession();
			TagKcclfj tagKcclfj = (TagKcclfj) sess.get(TagKcclfj.class, tagid);
			if (null == tagKcclfj) {
				return null;// ɾ��ʧ��
			}
			String directory = disk + tagKcclfj.getDirectory();
			File f = new File(directory + tagid);
			if (f.isFile()) {
				f.delete();
			}
			sess.delete(tagKcclfj);
			sess.flush();

			return tagid;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
