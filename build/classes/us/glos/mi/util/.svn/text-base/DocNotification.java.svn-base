package us.glos.mi.util;


import org.glc.xmlconfig.LogLevel;
import org.glc.Logger;
import org.glc.utils.ServerUtilities;

public class DocNotification extends Thread{
	private String title;
	private String from;
	private String[] to;
	private String content;
	public DocNotification(String title,String from,String[] to,String content)
	{
		this.title=title;
		this.from=from;
		this.to=to;
		this.content=content;
	}
	public void run()
	{
		if(false==ServerUtilities.sendMail(title, content, from, to))
			Logger.writeLog("DocNotification:Failed to send email", LogLevel.SEVERE);
	}
	
}
