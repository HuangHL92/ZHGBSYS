package com.insigma.siis.local.pagemodel.meeting;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.DataSHPadImpDBThread;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class MeetingThemePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		this.setNextEventName("meetingGrid.dogridquery");
		return 0;
	}
	@PageEvent("meetingGrid.dogridquery")
	@NoRequiredValidate         
	public int contentList10(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String userid = SysManagerUtils.getUserId();
		String sql = "select meetingid,meetingname,meetingtype,time,userid,(select username from smt_user m where m.userid=t.userid) username from "
				+ "(select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where userid='"+userid+"'"
				+ " union "
				+ "  select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where "
				+ "		meetingid in (select b.meetingid from publish b,publishuser c where b.publishid=c.publishid and c.userid='"+userid+"' and c.islook='1')"
				+ "	) t order by time desc";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("allDelete")
	@Transaction
	@NoRequiredValidate
	public int delete(String meetingid)throws RadowException, AppException{
		//查出会议下所有的发布内容id
		HBSession session = HBUtil.getHBSession();
		UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
		String meetingname = session.createSQLQuery("select meetingname from meetingTheme where meetingid='"+meetingid+"'").uniqueResult().toString();
		List<String> list_publishid = session.createSQLQuery("select publishid from publish where meetingid='"+meetingid+"'").list();
		if(list_publishid.size()==0){
			session.createSQLQuery("delete from MeetingTheme where meetingid='"+meetingid+"'").executeUpdate();
			this.setNextEventName("meetingGrid.dogridquery");
			this.setMainMessage("删除成功！");
			new LogUtil(user).createLogNew(user.getId(),"删除会议","meetingtheme",user.getId(),meetingname, new ArrayList());
			this.setNextEventName("meetingGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//数据删除
		session.createSQLQuery("delete from MeetingTheme where meetingid='"+meetingid+"'").executeUpdate();
		session.createSQLQuery("delete from personcite a where a.publishid_new in (select b.publishid from publish b where  b.meetingid='"+meetingid+"')").executeUpdate();
		session.createSQLQuery("delete from hz_sh_a01 a where a.publishid in (select b.publishid from publish b where  b.meetingid='"+meetingid+"')").executeUpdate();
		session.createSQLQuery("delete from hz_sh_title where meetingid='"+meetingid+"'").executeUpdate();
		session.createSQLQuery("delete from publish where meetingid='"+meetingid+"'").executeUpdate();
		for(String publishid:list_publishid){
			session.createSQLQuery("delete from publish_att where publishid='"+publishid+"'").executeUpdate();
		}
		//附件删除
		for(String publishid:list_publishid){
			String directory = disk+"meeting"+File.separator+publishid;
			deleteDirectory(directory);
		}
		this.setMainMessage("删除成功！");
		new LogUtil(user).createLogNew(user.getId(),"删除会议","meetingtheme",user.getId(),meetingname, new ArrayList());
		this.setNextEventName("meetingGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile2(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile2(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
	
	@PageEvent("out")
	public int outFile(String meetingid) throws IOException{
		HBSession session = HBUtil.getHBSession();
		String meetingname = session.createSQLQuery("select meetingname from meetingTheme where meetingid='"+meetingid+"'").uniqueResult().toString();
		List<Object[]> list_db = session.createSQLQuery("select publishid,agendaname from publish where meetingid='"+meetingid+"'").list();
		for(Object[] o:list_db){
				//文件复制
				//List<Object[]> list = session.createSQLQuery("select pa.pat00,pa.pat04,pa.pat07 from publish p,publish_att pa where p.meetingid='"+meetingid+"' and p.publishid = pa.publishid and pa.pat07 is not null").list();
				List<Object[]> list = session.createSQLQuery("select pat00,pat04,pat07 from publish_att where publishid='"+o[0]+"' and pat07 is not null").list();
				for(Object[] obj :list){
					//from_file
					File file_from = new File(disk+obj[2]+obj[0]);
					//to
					File file = new File(disk + "meeting_out"+File.separator+meetingname+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"$"+meetingname+File.separator+o[1]);
					if(!file.isDirectory()){
						file.mkdirs();
					}
					File file_to = new File(disk + "meeting_out"+File.separator+meetingname+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"$"+meetingname+File.separator+o[1]+File.separator+obj[1]);
					copyFile(file_from, file_to);
				}
		}
		String zip_name = meetingname;
		String path = disk+"meeting_out/"+zip_name;
		Zip7z.zip7Z(disk+"meeting_out/"+meetingname, path, null);
		request.getSession().setAttribute("meeting_path", path+".zip");
		this.getExecuteSG().addExecuteCode("outFileZip()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
     * 复制文件
     * @param fromFile
     * @param toFile
     * @throws IOException 
     */
    public static void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        
        ins.close();
        out.close();
    }
    public static void main(String[] args) throws IOException {
		/*File f1 = new File("C:"+File.separator+"test"+File.separator+"demo.xls");
		File f2 = new File("C:"+File.separator+"HZB"+File.separator+"meeting"+File.separator+"402882e5663ca60301663ca926080003"+File.separator+"3b4acf77655d4419b476ba181211c9af");
		copyFile(f2,f1);*/
    	List<Object[]> s = new ArrayList<Object[]>();
    	while (true) {
    		
			s.add(new String[1000]);
		}
    	
	}
    public static void downFile(HttpServletRequest request, HttpServletResponse response) {
    	String path_zip = request.getSession().getAttribute("meeting_path").toString();
		try {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(path_zip.split("/")[path_zip.split("/").length-1].getBytes("GBK"), "ISO8859-1"));
			// 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path_zip));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            /*创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            servletOS.write(buffer);
            servletOS.flush();
            servletOS.close();
            //清空meeting_out
            deleteDirectory(disk+"meeting_out");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static String disk = JSGLBS.HZBPATH;
	
	//导出个人数据
	@PageEvent("importSHPadBtn")
	public int expHzbWin(String gsType) throws RadowException, UnsupportedEncodingException {
		StringBuffer checkIds = new StringBuffer();
		PageElement pe = this.getPageElement("meetingGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("mcheck");
				if(usercheck.toString().equals("true")){
					checkIds.append("'").append(map.get("meetingid").toString()).append("',");
				}
			}
		}
		if(checkIds.length() == 0){
			this.setMainMessage("请至少勾选一个导出会议！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String meetingid = checkIds.toString();
		meetingid = meetingid.substring(0, meetingid.length()-1);
		HBSession session = HBUtil.getHBSession();
		StringBuffer ids = new StringBuffer();

		String sql = "";
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			Runnable thr = null;
			String zipid=UUID.randomUUID().toString().replace("-", "");
			try {
				KingbsconfigBS.saveImpDetailInit3(zipid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String userid = SysManagerUtils.getUserId();
			thr=new DataSHPadImpDBThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),meetingid,userid);
			new Thread(thr,"Thread_shexp").start();
			this.setRadow_parent_data(zipid);
		    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+zipid+"',g_contextpath)");
			return EventRtnType.NORMAL_SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("createMess")
	public int createMess() throws RadowException, UnsupportedEncodingException {
		first1();
		first4();
		first5();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String first1() throws RadowException, UnsupportedEncodingException {
		String str="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select sum(b0183+b0185) ypzs   from b01 where (b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ) or (substr(b0121,1,12)='001.001.004.' and b0131 in ('1001','1002','1004')) ";
			List<HashMap<String, Object>> list1 = commQuery.getListBySQL(sql); 
			int ypzs=Integer.valueOf(list1.get(0).get("ypzs").toString());
			sql="select count(1) spzs from a01,a02,b01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
					+ "       and a02.a0201e in ('1','3') and  (a02.a0248 = '0' or a02.a0248 is null) and a02.a0201b=b0111"
					+ "       and ((b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ) or (substr(b0121,1,12)='001.001.004.' and b0131 in ('1001','1002','1004')))"
					+ "       and a01.a0163='1' and a01.status='1' and (a01.a0165 like '%03%' or a01.a0165 like '%05%')";
			List<HashMap<String, Object>> list2 = commQuery.getListBySQL(sql); 
			int spzs=Integer.valueOf(list2.get(0).get("spzs").toString());
			str=str+"我市共有市管党政领导职数"+ypzs+"名，目前实配"+spzs+"名";
			if(ypzs>spzs) {
				str=str+"，缺配"+(ypzs-spzs)+"名";
			}else if(ypzs<spzs) {
				str=str+"，超配"+(spzs-ypzs)+"名";
			}
			
			System.out.println("111111111111表述：："+str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}

	public String first4() throws RadowException, UnsupportedEncodingException {
		String str="";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select count(1) qxz_num,sum(case when age<43 then 1 else 0 end ) qxnq_num from QXDZLDGBB  ";
			List<HashMap<String, Object>> list1 = commQuery.getListBySQL(sql); 
			String qxz_num=list1.get(0).get("qxz_num").toString();
			String qxnq_num=list1.get(0).get("qxnq_num").toString();
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  

			String d1 = df.format((float)Integer.valueOf(qxnq_num)*100/Integer.valueOf(qxz_num));
			sql="select  distinct (select b.b0101 from b01 b where b.b0111= substr(a02.a0201b,1,15)) ssxq,(select count(1) from b01 where b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ) sznq_num "
					+ "  from A01 a01, A02 a02, B01 b01"
					+ " WHERE a01.a0000 = a02.a0000"
					+ "   and a02.a0201b = b01.b0111 "
					+ "   and (a01.A0165 like '%03%' or a01.A0165 like '%05%') "
					+ "  and b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34')  "
					+ "  and a02.A0201E in ('1', '3') "
					+ "  and a01.A0163 = '1' "
					+ "  and a01.status = '1' "
					+ "  and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<43";
			List<HashMap<String, Object>> list2 = commQuery.getListBySQL(sql); 
			String szz_num=list2.get(0).get("sznq_num").toString();
			String sznq_num=String.valueOf(list2.size());
			String d2 = df.format((float)Integer.valueOf(sznq_num)*100/Integer.valueOf(szz_num));
			
			sql="select count(1) zs_num,sum(rynum) xl_num from ("
					+ "select a.a0000,case when qrzxl like '%研究生%' or qrzxl like '%硕士%' or qrzxl like '%大学%' or qrzxl like '%学士%' then 1 else 0 end rynum from QXDZLDGBB a,a01 b where a.a0000=b.a0000 and a.age<43 "
					+ "  union "
					+ "select  distinct a01.a0000,case when qrzxl like '%研究生%' or qrzxl like '%硕士%' or qrzxl like '%大学%' or qrzxl like '%学士%' then 1 else 0 end rynum"
					+ "  from A01 a01, A02 a02, B01 b01"
					+ " WHERE a01.a0000 = a02.a0000"
					+ "   and a02.a0201b = b01.b0111"
					+ "   and (a01.A0165 like '%03%' or a01.A0165 like '%05%')"
					+ "   and b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') "
					+ "   and a02.A0201E in ('1', '3')"
					+ "   and a01.A0163 = '1'"
					+ "   and a01.status = '1'"
					+ "   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<43)";
			List<HashMap<String, Object>> list3 = commQuery.getListBySQL(sql); 
			String zs_num=list3.get(0).get("zs_num").toString();
			String xl_num=list3.get(0).get("xl_num").toString();
			String d3 = df.format((float)Integer.valueOf(xl_num)*100/Integer.valueOf(zs_num));
			
			str="我市所辖县（市、区）党政班子实配"+qxz_num+"人，其中40岁左右及以下的年轻干部"+qxnq_num+"人，占"+d1+"%；市级党政工作部门配备40岁左右年轻干部的班子有"
					+sznq_num+"个，占"+d2+"%；上述年轻干部中，全日制本科及以上学历的占"+d3+"%";
			System.out.println("444444444444表述：："+str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	public String first5() throws RadowException, UnsupportedEncodingException {
		String str="我市所辖县（市、区）党政班子中均已配备";
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select b0111,b0101 from b01 where b0121='001.001.004' and b0101 not in (select ssxq from QXDZLDGBB a,a01 b where a.a0000=b.a0000 and b.a0104='2')  ";
			List<HashMap<String, Object>> list1 = commQuery.getListBySQL(sql);
			if(list1==null||list1.size()==0) {
				
			}else if(list1.size()>0) {
				str=str+"（还有"+list1.size()+"个班子尚未配备）";
			}
			str=str+"女干部";
			sql="select distinct a.a0000,a0200 from QXDZLDGBB a,a02 b,a01 c where a.a0000 = b.a0000 and b.a0000=c.a0000 and b.a0255='1' and b.a0281='true' "
					+ " and b.A0201E='1' and c.a0104 = '2'";
			List<HashMap<String, Object>> list2 = commQuery.getListBySQL(sql); 
			if(list2==null||list2.size()==0) {
				str=str+"；";
			}else if(list2.size()>0) {
				str=str+"，其中担任党政正职的"+list2.size()+"名；";
			}
			
			sql="select distinct a0201b from a01,a02,b01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and a01.a0104='2'"
					+ "       and a02.a0201e in ('1','3')  and a02.a0201b=b0111"
					+ "       and (b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ) "
					+ "       and a01.a0163='1' and a01.status='1' and (a01.a0165 like '%03%' or a01.a0165 like '%05%')";
			List<HashMap<String, Object>> list3 = commQuery.getListBySQL(sql); 
			sql="select b0101,b0111 from b01 WHERE  b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ";
			List<HashMap<String, Object>> list4 = commQuery.getListBySQL(sql);
			
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			String d1 = df.format((float)list3.size()*100/Integer.valueOf(list4.size()));
			str=str+"市级党政工作部门配备女干部的班子有"+list3.size()+"个，占"+d1+"%";
			
			sql="select distinct a01.a0000 from a01,a02,b01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and a01.a0104='2'"
					+ "       and a02.a0201e='1'  and a02.a0201b=b0111"
					+ "       and (b0121='001.001.002' and substr(b0131,1,2) in ('31','32','34') ) "
					+ "       and a01.a0163='1' and a01.status='1' and (a01.a0165 like '%03%' or a01.a0165 like '%05%')";
			List<HashMap<String, Object>> list5 = commQuery.getListBySQL(sql);
			str=str+"，其中担任正职的"+list5.size()+"人。";
			
			
			System.out.println("55555555表述：："+str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
}
