package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;




import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;

public class AddPerson2PageModel extends PageModel {

	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//�ܳ�
		//32����һ�С�
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//���� 64���ֽ�����ƫ�ƣ�ֱ���㹻Ϊֹ��
    		boolean hass = false;
    		while((end+offset)<llength){//32����һ�У����з��ָ�
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32����һ�е�����64���ֽ� ������
    				loffset++;
    				if((end+offset+loffset)>llength){//�����ܳ��� �˳�ѭ��
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//���ܻ����һ��65���ֽڣ���ǰ��һ��
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	
	@PageEvent("initX")
	@Transaction
	public int initX() throws RadowException, AppException, JSONException, IOException {
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A01 where a0000='"+a0000+"'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);		
			
			
			//������־��¼
			LogMain logmain = new LogMain();
			logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//����id
			logmain.setUserlog(SysManagerUtils.getUserName()); 	//�����û����˴�Ϊ�û���¼������
			logmain.setSystemoperatedate(new Date()); 			//ϵͳ����ʱ��
			logmain.setEventtype("������Ա��Ϣ¼��ҳ��"); 		//����
			logmain.setEventobject("���������Ϣ"); 				//����������
			logmain.setObjectid(a0000); 						//�����漰�������
			logmain.setObjectname(a01.getA0101());   			//��ǰ�������漰���������
			sess.save(logmain);
			sess.flush();
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//�����ڿ�ͷ  (һ��)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	originaljl.append(jl).append("\r\n");
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}



	
	 /**
     * ��ʵ��POJOת��ΪJSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    
	@PageEvent("codetype2js.onclick")
	@Transaction
	@Synchronous(true)
	public int codetype2js()throws RadowException, AppException{
		CodeType2js.getCodeTypeJS();
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * ��PDFԤ������
	 * 
	 * @param a0000AndFlag a0000 ����ԱID���� flag���Ƿ��ӡ��������Ϣ��ƴ�ӵĲ������ö��ŷָ�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @date 2016-06-03
	 */
	@PageEvent("exportLrmBtnNrm.onclick")
	@NoRequiredValidate
	public int pdfViewNrmBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("���ȱ�����Ա��Ϣ��");
		}
		pdfView("true");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("exportLrmBtn.onclick")
	@NoRequiredValidate
	public int pdfViewBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("���ȱ�����Ա��Ϣ��");
		}
		pdfView("false");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("printView")
	public int pdfView(String nrmFlag) throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		Boolean flag = nrmFlag.equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
		
		String pdfPath = "";  											//pdf�ļ�·��
		
		List<String> list = new ArrayList<String>();
		list.add(a0000);
		
		List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		//this.getPageElement("pdfPath").setValue(pdfPath);
		this.getExecuteSG().addExecuteCode("document.getElementById('pdfPath').value='"+pdfPath+"'");

		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',850,450,document.getElementById('a0000').value,ctxPath)");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static String DateFmt(String date){
		if(date!=null&&date.length()>=6){
			return date.substring(0, 4)+"."+date.substring(4,6);
		}else{
			return date;
		}
	}
}
