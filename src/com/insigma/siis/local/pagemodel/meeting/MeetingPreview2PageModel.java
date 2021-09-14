package com.insigma.siis.local.pagemodel.meeting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class MeetingPreview2PageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryMeetingTime")
	@NoRequiredValidate
	public String queryMeetingTime(String publishid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		String time="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from meetingtheme where meetingid =(select meetingid from publish where publishid='"+publishid+"')";
			list = commQuery.getListBySQL(sql); 
			time=list.get(0).get("time").toString();
			if(time.length()==6) {
				time=time.substring(0,4)+"年"+time.substring(4).replace("0", "")+"月";
			}else if(time.length()==8) {
				time=time.substring(0,4)+"年"+time.substring(4,6).replace("0", "")+"月"+time.substring(6).replace("0", "")+"日";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return time;
	}
	
	@PageEvent("queryMeeting")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryMeeting(String publishid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		String time="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from meetingtheme where meetingid =(select meetingid from publish where publishid='"+publishid+"')";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryPublish")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish(String publishid) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from publish where publishid='"+publishid+"'";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryTitle")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryTitle(String publishid,String title04) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select * from hz_sh_title where publishid='"+publishid+"' and title04='"+title04+"' order by sortid";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解压路径
        return upload_file;
    }
	
	public final static String disk = getPath();
	
	@PageEvent("queryPenson")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPenson(String publishid,String titleid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select a0000,tp0111,tp0112,tp0114,a0101,a0192a,"
					+ " '（'||decode(a0104,'1','男','2','女')||'，'||substr(a0107,1,4)||'.'||substr(a0107,5,2)||'，'||zgxl||"
					+ "case when zgxldj>zgxwdj then '、'||zgxw else '' end"
					+ "||decode(a0141,'','','，'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=t.a0141))||'）' a0102"
					+ " from "
					+ "(select a.a0000,a.tp0111,a.tp0112,a.tp0114,a.a0101,a.a0192a,a0104,a0107,zgxl,a0141,sh001,"
					+ "case when zgxw like '%博士%'  then '博士' when zgxw like '%硕士%'  then '硕士' when zgxw like '%学士%'  then '学士' else '' end  zgxw, "
					+ "case when zgxl like '%研究生%' or zgxl like '%硕士%' then '2' when zgxl like '%大学%' or zgxl like '%学士%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%博士%'  or zgxw like '%硕士%'  then '2' when zgxw like '%学士%'  then '3' else '4' end  zgxwdj"
					+ "    from (select * from hz_sh_a01 where titleid='"+titleid+"' or sh000 in (select sh000 from personcite where titleid_new='"+titleid+"')) a ) t  order by sh001,a0101";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryDYPenson")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryDYPenson(String publishid,String titleid) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select a.a0000,b.a0101,b.a0192a,"
					+ " decode(a.a0104,'1','男','2','女')||'，'||substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2)||'生'||decode(a.a0141,'','','，'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=b.a0141))||'，'||a.zgxl||'，'"
					+ " ||a.a0192a||decode(a0221,'','','，'||substr(a0288,1,4)||'.'||substr(a0288,5,2)||'任'||(select c.code_name from code_value c where code_type='ZB09' and c.code_value=b.a0221))  a0102"
					+ "    from hz_sh_a01 a,a01 b where a.a0000=b.a0000 and a.publishid='"+publishid+"' and a.titleid='"+titleid+"' order by sh001,b.a0101";
			list = commQuery.getListBySQL(sql); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("queryNum")
	@NoRequiredValidate
	public String queryNum(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += queryNum(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += queryNum(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += queryNum(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += queryNum(intInput % 10000);
        }

        return sd;
    }

    private  String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }
	
	@PageEvent("queryImg")
	@NoRequiredValidate
	public void queryImg(String wordPath,String perpath,String imgPath) throws Exception {
		File file1 = new File(perpath);
		if(!file1.isDirectory()){
			file1.mkdirs();
		}
		File f = new File(imgPath);
		if(f.isDirectory()){
			f.delete();
		}
		File file2 = new File(wordPath);
		InputStream inStream = new FileInputStream(file2);
		List<BufferedImage> wordToImg = wordToImg(inStream,20);
		BufferedImage mergeImage = mergeImage(false, wordToImg);
		ImageIO.write(mergeImage, "jpg", new File(imgPath));
//		for(int i=1;i<=wordToImg.size();i++) {
//			ImageIO.write(wordToImg.get(i-1), "jpg", new File(imgPath+"_"+i));
//		}
	}
	
	/**    
	* @Description: word和txt文件转换图片
	*/ 
	private static List<BufferedImage> wordToImg(InputStream inputStream, int pageNum) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			long old = System.currentTimeMillis();
			Document doc = new Document(inputStream); 
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG); 
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			int pageCount = doc.getPageCount();  
			if (pageCount > pageNum) {
				//生成前pageCount张 
				pageCount = pageNum; 
			}           
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			for (int i = 0; i < pageCount; i++) {
				OutputStream output = new ByteArrayOutputStream();
				options.setPageIndex(i); 
				//options.setResolution(132);
				//options.setScale(2);
				doc.save(output, options); 
				ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output)); 
				imageList.add(javax.imageio.ImageIO.read(imageInputStream));
			}           
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	}
	
	/**   
	* 合并任数量的图片成一张图片
	*  
	* @param isHorizontal true代表水平合并，fasle代表垂直合并
	* @param imgs         待合并的图片数组    
	* @return    
	* @throws IOException 
	*/    
	public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException { 
		// 生成新图片   
		BufferedImage destImage = null; 
		// 计算新图片的长和高 
		int allw = 0, allh = 0, allwMax = 0, allhMax = 0; 
		// 获取总长、总宽、最长、最宽 
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);    
			allw += img.getWidth();    
			if (imgs.size() != i + 1) {  
				allh += img.getHeight() + 5; 
			} else {     
				allh += img.getHeight(); 
			}        
			if (img.getWidth() > allwMax) {  
				allwMax = img.getWidth();     
			}   
			if (img.getHeight() > allhMax) { 
				allhMax = img.getHeight();  
			}    
		}     
		// 创建新图片 
		if (isHorizontal) {  
			destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);  
		} else {  
			destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB); 
		}     
		Graphics2D g2 = (Graphics2D) destImage.getGraphics(); 
		g2.setBackground(Color.LIGHT_GRAY);  
		g2.clearRect(0, 0, allw, allh);   
		g2.setPaint(Color.RED);  
		// 合并所有子图片到新图片  
		int wx = 0, wy = 0;   
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);  
			int w1 = img.getWidth();    
			int h1 = img.getHeight();
			// 从图片中读取RGB   
			int[] ImageArrayOne = new int[w1 * h1];  
			ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中 
			if (isHorizontal) { // 水平方向合并 
				destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB 
			} else { // 垂直方向合并  
				destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB 
			}    
			wx += w1;    
			wy += h1 + 5;    
		}     
		return destImage; 
	} 
	
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ByteArrayInputStream parse(OutputStream out) throws Exception { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		baos = (ByteArrayOutputStream) out; 
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray()); 
		return swapStream;    
	}   
	
	public String isnull (Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;	
	}
	
	public String repNum(Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString().replaceAll("1", "<span class='rmfont'>1</span>").replaceAll("2", "<span class='rmfont'>2</span>")
				.replaceAll("3", "<span class='rmfont'>3</span>").replaceAll("4", "<span class='rmfont'>4</span>")
				.replaceAll("5", "<span class='rmfont'>5</span>").replaceAll("6", "<span class='rmfont'>6</span>")
				.replaceAll("7", "<span class='rmfont'>7</span>").replaceAll("8", "<span class='rmfont'>8</span>")
				.replaceAll("9", "<span class='rmfont'>9</span>").replaceAll("0", "<span class='rmfont'>0</span>");
		}
		return str;
	}
	
}
