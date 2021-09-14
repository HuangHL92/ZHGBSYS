package com.insigma.siis.local.epsoft.task;

import java.util.TimerTask;

/**
 * »ù´¡ÈÎÎñTimerTask
 * @author gezh
 *
 */
public abstract class BaseTimerTask extends TimerTask {

	@Override
	public void run() {
		perform();
	}

	abstract public void perform();
	
}
