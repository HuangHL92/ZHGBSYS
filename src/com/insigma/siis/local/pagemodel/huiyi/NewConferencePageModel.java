package com.insigma.siis.local.pagemodel.huiyi;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HY07;

import java.io.File;

/**
 * @author genggaopeng
 * @date 2019-12-5 14:51
 */
public class NewConferencePageModel extends PageModel {
    @Override
    public int doInit() throws RadowException {
        return 0;
    }

    @PageEvent("deleteFiles")
    public void deleteFiles() {
        String[] fileIds = request.getParameter("fileId").split(" ");
        HBSession session = HBUtil.getHBSession();
        for (String fileId : fileIds) {
            HY07 hy07 = (HY07) session.createSQLQuery("select * from hy07 where hy0700 = '" + fileId + "'").addEntity(HY07.class).list().get(0);
            String filePath = request.getRealPath("/" + HuiyiUploadServlet.dir + "/" + hy07.getHy0700() + hy07.getHy0704());
            File file = new File(filePath);
            file.delete();
            session.createSQLQuery("delete from hy07 where hy0700 = '" + fileId + "'").executeUpdate();
        }
    }
}
