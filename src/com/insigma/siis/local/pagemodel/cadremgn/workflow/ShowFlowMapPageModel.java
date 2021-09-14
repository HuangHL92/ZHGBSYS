//package com.insigma.siis.local.pagemodel.cadremgn.workflow.ShowFlowMap;
package com.insigma.siis.local.pagemodel.cadremgn.workflow;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;

/**
 * 显示流程当前流程的效果
 * @author desire
 *
 */
public class ShowFlowMapPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		System.out.println("流程追踪");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
