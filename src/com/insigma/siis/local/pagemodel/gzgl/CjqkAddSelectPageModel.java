package com.insigma.siis.local.pagemodel.gzgl;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;

public class CjqkAddSelectPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("search.onclick")
    public int search() {
        this.getExecuteSG().addExecuteCode("search()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("grid.dogridquery")
    public int doGridQuery(int start, int limit) throws RadowException {
        StringBuffer sb = new StringBuffer("select a01.A0000 as id, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, a01.A0221 as zw, a01.A0192E as zj, ");
        sb.append(" wm_concat(a02.A0201A) as rzjg ");
        sb.append(" from a01");
        sb.append(" join a02 on a02.a0000 = a01.a0000 ");
        sb.append(" where 1 = 1 ");
        // 拼接姓名
        String name = this.getPageElement("q_name").getValue();
        if (StringUtils.isNotBlank(name)) {
            sb.append(" and a01.a0101 like '%" + name + "%'");
        }
        // 拼接姓名
        String sex = this.getPageElement("q_sex").getValue();
        if (StringUtils.isNotBlank(sex)) {
            sb.append(" and a01.A0104 = " + sex);
        }
        // 拼接机构查询条件
        String codeValue = this.getPageElement("checkedgroupid").getValue();
        // 判断单位是否选择
        if (com.fr.stable.StringUtils.isNotEmpty(codeValue)) {
            // 是否包含下级
            String isContain = this.getPageElement("isContain").getValue();
            if ("1".equals(isContain)) {
                sb.append(" and (a02.a0201b ='" + codeValue + "' or a02.a0201b like '" + codeValue + "%') ");
            } else {
                sb.append(" and a02.a0201b = '" + codeValue + "' ");
            }
        }
        sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E ");
        sb.append(" order by a01.a0101 ");
        this.pageQuery(sb.toString(), "SQL", start, limit);
        return EventRtnType.SPE_SUCCESS;
    }

    @PageEvent("grid.rowdbclick")
    @GridDataRange(GridData.cuerow)
    public int gridDbClick() throws RadowException {
        Grid grid = (Grid) this.getPageElement("grid");
        String id = (String) grid.getValue("id");
        this.getExecuteSG().addExecuteCode("realParent.callback('" + id + "')");
        return EventRtnType.NORMAL_SUCCESS;
    }

}
