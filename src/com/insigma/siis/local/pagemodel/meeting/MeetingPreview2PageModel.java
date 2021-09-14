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
				time=time.substring(0,4)+"��"+time.substring(4).replace("0", "")+"��";
			}else if(time.length()==8) {
				time=time.substring(0,4)+"��"+time.substring(4,6).replace("0", "")+"��"+time.substring(6).replace("0", "")+"��";
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
            //����ļ��в������򴴽�
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //��ѹ·��
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
					+ " '��'||decode(a0104,'1','��','2','Ů')||'��'||substr(a0107,1,4)||'.'||substr(a0107,5,2)||'��'||zgxl||"
					+ "case when zgxldj>zgxwdj then '��'||zgxw else '' end"
					+ "||decode(a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=t.a0141))||'��' a0102"
					+ " from "
					+ "(select a.a0000,a.tp0111,a.tp0112,a.tp0114,a.a0101,a.a0192a,a0104,a0107,zgxl,a0141,sh001,"
					+ "case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "case when zgxl like '%�о���%' or zgxl like '%˶ʿ%' then '2' when zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj"
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
					+ " decode(a.a0104,'1','��','2','Ů')||'��'||substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2)||'��'||decode(a.a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=b.a0141))||'��'||a.zgxl||'��'"
					+ " ||a.a0192a||decode(a0221,'','','��'||substr(a0288,1,4)||'.'||substr(a0288,5,2)||'��'||(select c.code_name from code_value c where code_type='ZB09' and c.code_value=b.a0221))  a0102"
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
        if (si.length() == 1) // ��
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// ʮ
        {
            if (si.substring(0, 1).equals("1"))
                sd += "ʮ";
            else
                sd += (GetCH(intInput / 10) + "ʮ");
            sd += queryNum(intInput % 10);
        } else if (si.length() == 3)// ��
        {
            sd += (GetCH(intInput / 100) + "��");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "��";
            sd += queryNum(intInput % 100);
        } else if (si.length() == 4)// ǧ
        {
            sd += (GetCH(intInput / 1000) + "ǧ");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "��";
            sd += queryNum(intInput % 1000);
        } else if (si.length() == 5)// �f
        {
            sd += (GetCH(intInput / 10000) + "�f");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "��";
            sd += queryNum(intInput % 10000);
        }

        return sd;
    }

    private  String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "һ";
                break;
            case 2:
                sd = "��";
                break;
            case 3:
                sd = "��";
                break;
            case 4:
                sd = "��";
                break;
            case 5:
                sd = "��";
                break;
            case 6:
                sd = "��";
                break;
            case 7:
                sd = "��";
                break;
            case 8:
                sd = "��";
                break;
            case 9:
                sd = "��";
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
	* @Description: word��txt�ļ�ת��ͼƬ
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
				//����ǰpageCount�� 
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
	* �ϲ���������ͼƬ��һ��ͼƬ
	*  
	* @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
	* @param imgs         ���ϲ���ͼƬ����    
	* @return    
	* @throws IOException 
	*/    
	public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException { 
		// ������ͼƬ   
		BufferedImage destImage = null; 
		// ������ͼƬ�ĳ��͸� 
		int allw = 0, allh = 0, allwMax = 0, allhMax = 0; 
		// ��ȡ�ܳ����ܿ������� 
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
		// ������ͼƬ 
		if (isHorizontal) {  
			destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);  
		} else {  
			destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB); 
		}     
		Graphics2D g2 = (Graphics2D) destImage.getGraphics(); 
		g2.setBackground(Color.LIGHT_GRAY);  
		g2.clearRect(0, 0, allw, allh);   
		g2.setPaint(Color.RED);  
		// �ϲ�������ͼƬ����ͼƬ  
		int wx = 0, wy = 0;   
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);  
			int w1 = img.getWidth();    
			int h1 = img.getHeight();
			// ��ͼƬ�ж�ȡRGB   
			int[] ImageArrayOne = new int[w1 * h1];  
			ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // ����ɨ��ͼ���и������ص�RGB�������� 
			if (isHorizontal) { // ˮƽ����ϲ� 
				destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
			} else { // ��ֱ����ϲ�  
				destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
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
					.getResourceAsStream("Aspose.Words.lic"); // license.xmlӦ����..\WebRoot\WEB-INF\classes·����
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
