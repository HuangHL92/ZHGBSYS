package com.insigma.siis.local.jftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;

/**
 * FTP·þÎñÆ÷¼àÌý
 * @author gezh
 *
 */
public class JFtplet extends DefaultFtplet {

	public static List<String> fsList = new ArrayList<String>();
	
	@Override
	public FtpletResult onConnect(FtpSession session) throws FtpException,IOException {
		fsList.add(session.getSessionId().toString());
		return super.onConnect(session);
	}

	@Override
	public FtpletResult onDisconnect(FtpSession session) throws FtpException,
			IOException {
		fsList.remove(session.getSessionId().toString());
		return super.onDisconnect(session);
	}

	
}
