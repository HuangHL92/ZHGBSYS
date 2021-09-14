package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import com.insigma.odin.framework.tree.CheckTreeNode;

public class CheckTreeNodeOfEnableChk extends CheckTreeNode {
	  private boolean disabled;
	  
	  public boolean getDisabled()
	  {
	    return this.disabled;
	  }
	  
	  public void setDisabled(boolean paramBoolean)
	  {
	    this.disabled = paramBoolean;
	  }
}
