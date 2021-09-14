package com.insigma.siis.local.util.up_down;

import java.util.HashMap;
import java.util.List;

public interface IBeforeFileDownload {
	public abstract Object DoSomethingElse(List<HashMap<String,Object>> list)throws Exception;
}
