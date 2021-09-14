package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class AppointmentPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.getExecuteSG().addExecuteCode("showdata()");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("personShow")
	public int showTemByPid() throws RadowException{
		//��ȡ��Աid
		String id = this.getPageElement("subWinIdBussessId").getValue();
		String script = "";
		script += "var cell = document.getElementById('cellweb1');\n";
		script += "var path = getPath();\n";
		script += "cell.openfile(ctpath+'/template/SGHZRMSPB.cll','');\n";
		script += "cell.WorkbookReadonly=true;\n";//���ò��ɱ༭
		//==============================================================��������Ա������Ϣ
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select a01.a0101,a01.a0104,cv.code_name,a01.a0111a,a01.a0114a,a01.a0134,a01.a0128,a01.a0196,a01.a0187a,a01.a0192a,a01.a0107,a01.a0140,a01.a14z101,a01.a15z101 from a01 a01,code_value cv where a01.a0117 = cv.code_value  and cv.code_type =  'GB3304' and a01.a0000 = '"+id+"'").list();
		if(list != null && list.size()>0){
			String messages ="";
			messages = (String)list.get(0)[0];//����
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += fountsize(10,3,messages);
				script += "\n";
			}
			messages  = (String)list.get(0)[1];//�Ա�
			if("1".equals(messages)){
				messages = "��";
			}else if("2".equals(messages)){
				messages = "Ů";
			}else{
				messages = "";
			}
			if(messages != null && !"".equals(messages)){
				script += "cell.SetCellString('30','3','0','"+ messages +"');";
				script += "cell.SetCellAlign('30','3','0','36');";//36ˮƽ���д�ֱ����
				script += "cell.SetCellFontSize('30','3','0','14');";//�����С
			}
			messages  = (String)list.get(0)[2];//����
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += fountsize(10,5,messages);
				script += "\n";
			}
			 messages  = (String)list.get(0)[3];//����
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += fountsize(30,5,messages);
				script += "\n";
			}
			messages  = (String)list.get(0)[4];//������
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += fountsize(53,5,messages);
				script += "\n";
			}
			messages  = (String)list.get(0)[5];//�μӹ���ʱ��
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				messages = messages.replace(".", "");
				if(0 < messages.length() && messages.length() <= 4){
					messages = messages.substring(0,messages.length());
				}else if(messages.length()>4){
					messages = messages.substring(0, 4)+"."+messages.substring(4, 6);
				}
				script += "cell.SetCellString('30','7','0','"+ messages +"');";
				script += "cell.SetCellAlign('30','7','0','36');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('30','7','0','14');";//�����С
			}
			messages  = (String)list.get(0)[6];//����״��
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += fountsize(53,7,messages);
				script += "\n";
			}
			messages  = (String)list.get(0)[7];//רҵ����ְ��
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script +=  getInfo(10,9,messages,"רҵ����ְ��");
				script += "\n";
			}
			messages  = (String)list.get(0)[8];//ר��
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script +=  getInfo(42,9,messages,"ר��");
				script += "\n";
			}
			messages  = (String)list.get(0)[9];//����ְ�� 
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += zwandnrm(21,15,messages);
			}
			messages  = (String)list.get(0)[10];//��������
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				String mes = messages.substring(0, 4)+"."+messages.substring(4, 6);
				script += "cell.SetCellString('53','3','0','"+ mes +"');";
				script += "cell.SetCellFontSize('53','3','0','14');";//�����С
				script += "cell.SetCellAlign('53','3','0','36');";
				//��������
				String endtime = DateUtil.getcurdate();
				String mesa = "";
				List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
				mesa ="("+listj.get(0)+")��";
				script += "cell.SetCellString('53','4','0','"+ mesa +"');";
				script += "cell.SetCellFontSize('53','4','0','14');";//�����С
				script += "cell.SetCellAlign('53','4', '0','36');";
			}
			//�뵳ʱ��
			messages  = (String)list.get(0)[11];//�뵳ʱ��
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
//				int length = 0;
				String mes = "";
				if(messages.contains("(")&&messages.contains(")")&& messages.contains(".")){
					mes = messages.replace(".", "");
					int length = mes.length();
					int indexOf = mes.indexOf("(");
					int indexOf2 = mes.indexOf(")");
					String sub = mes.substring(indexOf, length);
					String year = sub.substring(1,5)+".";
					String yue = sub.substring(5,7);
					mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
				}else if(messages.contains("(")&&messages.contains(")") && !messages.contains(".")){
					mes = messages;
				}else if(messages.length()>=6){
					mes = messages.replace(".", "");
					mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
				}else{
					mes = messages;
				}
				script += "cell.SetCellString('10','7','0','"+ mes +"');";
				script += "cell.SetCellFontSize('10','7','0','14');";//�����С
				script += "cell.SetCellAlign('10','7','0','36');";
			}
			messages  = (String)list.get(0)[12];//�������
			messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
			if(messages != null && !"".equals(messages)){
				if(messages.length() > 552){
					messages = messages.substring(0, 552);
				}
				script += "cell.SetCellFontSize('8','46','0','14');";//�����С
				script += "cell.SetCellString('8','46','0','"+ messages +"');";
				script += "cell.SetCellAlign('8','46','0','33');";
				script+="cell.SetCellTextStyle('8','46','0','2');";//�Զ�����
				if(messages.length() > 124 ){
					script+="cell.SetCellFontAutoZoom('8','46','0','1');";//�����Զ���С
				}
			}
			messages  = (String)list.get(0)[13];//��ȿ��˽��
			messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
			if(messages != null && !"".equals(messages)){//93
				if(messages.length() > 414){
					messages = messages.substring(0, 414);
				}
				script += "cell.SetCellFontSize('8','50','0','14');";//�����С
				script += "cell.SetCellString('8','50','0','"+ messages +"');";
				script += "cell.SetCellAlign('8','50','0','33');";
				script+="cell.SetCellTextStyle('8','50','0','2');";//�Զ�����
				if(messages.length() > 93 ){
					script+="cell.SetCellFontAutoZoom('8','50','0','1');";//�����Զ���С
				}
			}
			
			//����
		/*	ResultSet rs;
			try {
				rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
				int t = rs.getRow();
				if(t==0){
					script += "cell.SetCellString('9','22','0','"+"".replace("\n", "\\\\r\\\\n")+"');";
					script += "cell.SetCellNumType('9','22','0','0');";
				}
				//rs.previous();
				String mes = "";
				while(rs.next()){
					int zihao = 0;//�ֺ�
					StringBuffer pinjieJLgs = new StringBuffer("");
					if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
						mes = "";
					}else{
						int[] arr = getcountsize(rs.getString(1));//��ȡ������ȥ����֮���������
						int countrow = arr[0];//����
						int countsize = arr[1];//����
//						mes = formatJL2(rs.getString(1),countsize);
						if(rs.getString(1)!=null){
							String[] jianli = rs.getString(1).split("\r\n|\r|\n");//�з�
							for(int i=0;i<jianli.length;i++){
								String jl = jianli[i].trim();
								Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
						        Matcher matcher = pattern.matcher(jl);     
						        if (matcher.find()) {//�Ƿ������ڿ�ͷ�ļ�������
						        	String line1 = matcher.group(0);  
						        	int index = jl.indexOf(line1);
						        	if(index==0){//�����ڿ�ͷ  (һ��)
						        		pinjieJLgs.append(line1).append("  ");
						        		String line2 = jl.substring(line1.length()).trim();
						        		zihao = parseJL(line2, pinjieJLgs,countsize,countrow);
						        	}
						        }else{
						        	zihao =parseJL2(jl, pinjieJLgs,countsize,countrow);
						        }
							}
						}
					}
					int zihaoto = 0;
					if(zihao == 21){
						zihaoto = 14;
					}else if(zihao == 27){
						zihaoto = 12;
					}else if(zihao == 29){
						zihaoto = 11;
					}
					script+="cell.SetCellString('9','22','0','"+pinjieJLgs.toString().replace("\n", "\\\\r\\\\n")+"');";
					script+="cell.SetCellNumType('9','22','0','0');";
					script += "cell.SetCellFontSize('9','22','0','"+zihaoto+"');";//�����С
				}
				//���������Զ���С
				int length = mes.length();
				if(mes.length()>1044){
					script+="cell.SetCellFontAutoZoom('9','22','0','1');";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			ResultSet rs;
			try {
				rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
				int t = rs.getRow();
				if(t==0){
					script += "cell.SetCellString('9','22','0','"+"".replace("\n", "\\\\r\\\\n")+"');";
					script += "cell.SetCellNumType('9','22','0','0');";
				}
				//rs.previous();
				String mes = "";
				while(rs.next()){
					int length = 0;  
					int rowsoze = 0;//��ͬ������ÿ����ʾ��������ͬ��
					int zihao = 0;//�ֺ�
					StringBuffer pinjieJLgs = new StringBuffer("");
					String jianadd = ""; 
					if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
						mes = "";
					}else{
						int j = 0;//�ж��ǲ���û�����ڵ���
						StringBuffer jladd = new StringBuffer("");
						jianadd = formatJL(rs.getString(1),jladd);
						jladd.delete(0, jladd.length());	
						int[] arr = getcountsize(jianadd);//��ȡ������ȥ����֮���������
						int countrow = arr[0];//����
						int countsize = arr[1];//����
						if(countsize <=525 && countrow <25){
							rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
						}else if((countsize<=810) && (25<=countrow &&countrow <30)){
							rowsoze = 26;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
						}else if((countsize<900)&&(30<=countrow && countrow<32)){
							rowsoze = 28;//11���ֿɴ�32��ÿ��29���� ��928���֡�
						}else if((countsize<1250)&&(32<=countrow && countrow<37)){
							rowsoze = 34;//10���ֿɴ�37��ÿ��34���� ��1250���֡�
						}else if((countsize<1282)&&(37<=countrow && countrow<39)){
							rowsoze = 38;//9���ֿɴ�39��ÿ��38���� ��1282���֡�
						}else if((countsize<1314)&&(39<=countrow && countrow<41)){
							rowsoze = 42;//8���ֿɴ�41��ÿ��42���� ��1314���֡�
						}else if((countsize<1346)&&(41<=countrow && countrow<43)){
							rowsoze = 46;//7���ֿɴ�43��ÿ��46���� ��1346���֡�
						}else if((countsize<1378)&&(43<=countrow && countrow<45)){
							rowsoze = 50;//6���ֿɴ�45��ÿ��50���� ��1378���֡�
						}else if((countsize<1402)&&(45<=countrow && countrow<47)){
							rowsoze = 54;//5���ֿɴ�47��ÿ��54���� ��1402���֡�
						}else{
							this.setMainMessage("�����С�޷���ʾ");
							break;
						}
//						mes = formatJL2(rs.getString(1),countsize);
						String jianlimes  = "";
						if(rs.getString(1)!=null){
							String type = "0";
							String rrr = rs.getString(1);
							String[] jianli = rs.getString(1).split("\r\n|\r|\n");//�з�
							for(int i=0;i<jianli.length;i++){
								String jl = jianli[i].trim();
								Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
						        Matcher matcher = pattern.matcher(jl);     
						        if (matcher.find()) {//�Ƿ������ڿ�ͷ�ļ�������
						        	String line1 = matcher.group(0);  
						        	int index = jl.indexOf(line1);
						        	if(index==0){//�����ڿ�ͷ  (һ��)
//						        		if(!"".equals(jianlimes)){
						        		if(j==1){
						        			if(length<0){
						        				length = -length;
						        			}
						        			parseJLto(pinjieJLgs,rowsoze,jianlimes.replace("\r\n", "").replace("\r", "").replace("\n", ""),length);
						        			j = 0;
						        			length = 0;
						        		}
						        		if(length > 0){
//						        			type = parseJLto(pinjieJLgs,rowsoze,jianlimes,length);
						        			pinjieJLgs.append("\r\n");
						        		}
						        		length = 0;
						        		pinjieJLgs.append(line1).append("  ");
						        		String line2 = jl.substring(line1.length()).trim();
						        		length = parseJL(line2.replace("\r\n", "").replace("\\r\\n", ""), pinjieJLgs,rowsoze,type,length);
						        		jianlimes  = "";
						        	}
						        }else{
//						        	parseJL2(jl, pinjieJLgs,rowsoze);
						        	jianlimes += jl;
						        	j=1;
						        }
							}
							if(!"".equals(jianlimes)){
			        			parseJLtoo(pinjieJLgs,rowsoze,jianlimes.replace("\r\n", ""),length);
			        		}
						}
					}
					int zihaoto = 0;
					if(rowsoze == 21){
						zihaoto = 14;
					}else if(rowsoze == 26){
						zihaoto = 12;
					}else if(rowsoze == 28){
						zihaoto = 11;
					}else if(rowsoze == 34){
						zihaoto = 10;
					}else if(rowsoze == 38){
						zihaoto = 9;
					}else if(rowsoze == 42){
						zihaoto = 8;
					}else if(rowsoze == 46){
						zihaoto = 7;
					}else if(rowsoze == 50){
						zihaoto = 6;
					}else if(rowsoze == 54){
						zihaoto = 5;
					}
					script+="cell.SetCellString('9','22','0','"+pinjieJLgs.toString().replace("\n", "\\\\r\\\\n")+"');";
					script+="cell.SetCellNumType('9','22','0','0');";
					script += "cell.SetCellFontSize('9','22','0','"+zihaoto+"');";//�����С
				}
				//���������Զ���С
				int length = mes.length();
				if(mes.length()>1044){
					script+="cell.SetCellFontAutoZoom('9','22','0','1');";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//��Ƭ
			String mes = "";
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
			if(listj != null && listj.size() ==0){
				mes = "";
				script+="cell.SetCellString('65','3', '0','"+mes+"');";
				script += "cell.SetCellFontSize('65','3','0','14');";//�����С
				script += "cell.SetCellAlign('65','3','0','36');";
			}else{
				Object[] sz = listj.get(0);
				Object photopath = sz[0];
				Object photoname = sz[1];
				String ptpath = photopath.toString().toUpperCase();
				//��������
				//2017.04.19 yinl �޸�ͼƬ��ַ
				script+="cell.SetCellString('65','3', '0','"+mes+"');";
				String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
				script+="cell.SetCellImage('65','3','0',cell.AddImage('"+imagepath+"'),'1','1','1');";
			}
		}
		//===============================================================��������Աѧ��ѧλ��Ϣ
		List<Object[]> XLXW = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL,a01.QRZXW,a01.QRZXLXX,a01.QRZXWXX,a01.ZZXL,a01.ZZXW,a01.ZZXLXX,a01.ZZXWXX  from a01 a01 where a01.a0000 ='"+id+"'").list();
		if(XLXW != null && XLXW.size()>0){
			String QRZXL = (String)XLXW.get(0)[0];//ȫ����ѧ��
			String QRZXW = (String)XLXW.get(0)[1];//ȫ����ѧλ
			if(QRZXL != null && !"".equals(QRZXL) && QRZXW != null && !"".equals(QRZXW) && QRZXW.equals(QRZXL)){
				QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','11','41','12');";
				script += xlxw(21,11,QRZXL,"1");
			}else if(QRZXL != null && !"".equals(QRZXL) && QRZXW != null && !"".equals(QRZXW) && !QRZXW.equals(QRZXL)){
				QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				QRZXW = QRZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += xlxw(21,11,QRZXL,"1");
				script += xlxw(21,12,QRZXW,"1");
			}else if(QRZXL != null && !"".equals(QRZXL) && !QRZXL.equals(QRZXW) && (QRZXW == null||"".equals(QRZXW)||"null".equals(QRZXW))){
				QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','11','41','12');";
				script += xlxw(21,11,QRZXL,"1");
			}else if(QRZXW != null && !"".equals(QRZXW) && !QRZXW.equals(QRZXL)&&(QRZXL == null||"".equals(QRZXL)||"null".equals(QRZXL))){
				QRZXW = QRZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','11','41','12');";
				script += xlxw(21,11,QRZXW,"1");
			}else{}
			String QRZXLXX = (String)XLXW.get(0)[2];//ȫ����ѧλ��Ϣ
			String QRZXWXX = (String)XLXW.get(0)[3];//ȫ����ѧλ��Ϣ
			if(QRZXLXX != null && !"".equals(QRZXLXX) && QRZXWXX != null && !"".equals(QRZXWXX) && QRZXLXX.equals(QRZXWXX)){
				QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','11','82','12');";
				script += xlxw(53,11,QRZXLXX,"2");
			}else if(QRZXLXX != null && !"".equals(QRZXLXX) && QRZXWXX != null && !"".equals(QRZXWXX) && !QRZXLXX.equals(QRZXWXX)){
				QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				QRZXWXX = QRZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += xlxw(53,11,QRZXLXX,"2");
				script += xlxw(53,12,QRZXWXX,"2");
			}else if(QRZXLXX != null && !"".equals(QRZXLXX) && !QRZXLXX.equals(QRZXWXX)&&(QRZXWXX == null || "".equals(QRZXWXX) || "null".equals(QRZXWXX))){
				QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','11','82','12');";
				script += xlxw(53,11,QRZXLXX,"2");
			}else if(QRZXWXX != null && !"".equals(QRZXWXX) && !QRZXWXX.equals(QRZXLXX)&&(QRZXLXX == null || "".equals(QRZXLXX) && "null".equals(QRZXLXX))){
				QRZXWXX = QRZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','11','82','12');";
				script += xlxw(53,11,QRZXWXX,"2");
			}else{}
			String ZZXL = (String)XLXW.get(0)[4];//ȫ����ѧ��
			String ZZXW = (String)XLXW.get(0)[5];//ȫ����ѧ��
			if(ZZXL != null && !"".equals(ZZXL) && ZZXW != null && !"".equals(ZZXW) && ZZXL.equals(ZZXW)){
				ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','13','41','14');";
				script += xlxw(21,13,ZZXL,"1");
			}else if(ZZXL != null && !"".equals(ZZXL) && ZZXW != null && !"".equals(ZZXW) && !ZZXL.equals(ZZXW)){
				ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				ZZXW = ZZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += xlxw(21,13,ZZXL,"1");
				script += xlxw(21,14,ZZXW,"1");
			}else if(ZZXL != null && !"".equals(ZZXL) && !ZZXL.equals(ZZXW)&&(ZZXW == null || "".equals(ZZXW) || "null".equals(ZZXW))){
				ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','13','41','14');";
				script += xlxw(21,13,ZZXL,"1");
			}else if(ZZXW != null && !"".equals(ZZXW) && !ZZXW.equals(ZZXL)&&(ZZXL == null || "".equals(ZZXL) || "null".equals(ZZXL))){
				ZZXW = ZZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('21','13','41','14');";
				script += xlxw(21,13,ZZXW,"1");
			}else{}
			String ZZXLXX = (String)XLXW.get(0)[6];//ȫ����ѧλ��Ϣ
			String ZZXWXX = (String)XLXW.get(0)[7];//ȫ����ѧλ��Ϣ
			if(ZZXLXX != null && !"".equals(ZZXLXX) && ZZXWXX != null && !"".equals(ZZXWXX) && ZZXLXX.equals(ZZXWXX)){
				ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','13','82','14');";
				script += xlxw(53,13,ZZXLXX,"2");
			}else if(ZZXLXX != null && !"".equals(ZZXLXX) && ZZXWXX != null && !"".equals(ZZXWXX) && !ZZXLXX.equals(ZZXWXX)){
				ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				ZZXWXX = ZZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script += xlxw(53,13,ZZXLXX,"2");
				script += xlxw(53,14,ZZXWXX,"2");
			}else if(ZZXLXX != null && !"".equals(ZZXLXX) && !ZZXLXX.equals(ZZXWXX)&&(ZZXWXX == null || "".equals(ZZXWXX) || "null".equals(ZZXWXX))){
				ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','13','82','14');";
				script += xlxw(53,13,ZZXLXX,"2");
			}else if(ZZXWXX != null && !"".equals(ZZXWXX) && !ZZXWXX.equals(ZZXLXX)&&(ZZXLXX == null || "".equals(ZZXLXX) || "null".equals(ZZXLXX))){
				ZZXWXX = ZZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
				script+="cell.MergeCells('53','13','82','14');";
				script += xlxw(53,13,ZZXWXX,"2");
			}else{}
		}
		//=========================================================������������
		List<Object[]> NRM = HBUtil.getHBSession().createSQLQuery("select a53.a5315,a53.a5304,a53.a5317,a53.a5319 from a53 a53 where a53.a0000 ='"+id+"'").list();
//		List<Object[]> NRM = HBUtil.getHBSession().createSQLQuery("select a53.a5319 from a53 a53 where a53.a0000 ='"+id+"'").list();
		if(NRM != null && NRM.size()>0){
			String messages = "";
			/*messages = (String)NRM.get(0)[0];//����ְ��
			script += zwandnrm(21,19,messages);
			script += "\n";
			messages = (String)NRM.get(0)[1];//����ְ��
			if(messages != null && !"".equals(messages)){
				script += zwandnrm(21,17,messages);
				script += "\n";
			}
			messages = (String)NRM.get(0)[2];//��������
			if(messages != null && !"".equals(messages)){
				if(messages.length() > 552){
					messages = messages.substring(0, 552);
				}
				script += "cell.SetCellFontSize('8','53','0','14');";//�����С
				script += "cell.SetCellString('8','53','0','"+ messages +"');";
				script += "cell.SetCellAlign('8','53','0','33');";
				if(messages.length() > 124 ){
					script+="cell.SetCellFontAutoZoom('8','53','0','1');";//�����Զ���С
				}
			}*/
			messages = (String)NRM.get(0)[0];//�ʱ���λ
			if(messages != null && !"".equals(messages)){
				messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
				if(messages.length() <= 12){
					script += "cell.SetCellString('52','77','0','"+ messages +"');";
					script += "cell.SetCellFontSize('52','77','0','14');";//�����С
					script += "cell.SetCellAlign('52','77',0,1);";
				}else if(12 < messages.length()  && messages.length() <= 30){
					script += "cell.MergeCells('7','77','52','77');";// �ϲ���Ԫ��
					script += "cell.SetCellString('7','77','0','"+ messages +"');";
					script += "cell.SetCellFontSize('7','77','0','14');";//�����С
					script += "cell.SetCellAlign('7','77','0','2');";
				}else if(30 < messages.length()  && messages.length() <= 120){
					int rowa = 0;
					int number = (messages.length()%30);
					if(number == 0){
						rowa = messages.length()/30;
					}else{
						rowa = messages.length()/30+1;
					}
					int b = 1;
					for(int a = 0 ;a < rowa; a++){
						script += "cell.MergeCells(7, 75, 52, 77);";// �ϲ���Ԫ��
						String messagesa = messages.substring(0*b, 30*b);
						script += "cell.SetCellString('7','"+(75+rowa)+"','0','"+ messagesa +"');";
						b++;
						script += "cell.SetCellFontSize('7','"+(75+rowa)+"','0','14');";//�����С
						script += "cell.SetCellAlign('7','"+  (75+rowa)+"','0','1');";
					}
					
				}else{
					script += "cell.MergeCells('7','74','52','77');";// �ϲ���Ԫ��
					script += "cell.SetCellString('7','74','0','"+ messages +"');";
					script+="cell.SetCellTextStyle('7','74','0','2');";//�Զ�����
					script += "cell.SetCellFontSize('7','74','0','14');";//�����С
					script+="cell.SetCellFontAutoZoom('7','74','0','1');";//�����Զ���С
				}
			}
		}
		
		script += "cell.SetCellAlign('52','74','0','2');";
		//============================================================����������Ϣ
		String endtime1 = DateUtil.getcurdate();
		List<Object[]> qsmess = HBUtil.getHBSession().createSQLQuery("select a36.A3604A,a36.A3601,GET_BIRTHDAY(a36.a3607,'"+endtime1+"') age, cvb.code_name as zzmm,a36.a3611 from a36 a36,code_value cvb where (cvb.code_type='GB4762' and cvb.code_value=a36.a3627)and a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
		int Q = 0;
		String type = "";
		if(qsmess != null && qsmess.size()>0){
			for (Object[] objects : qsmess) {
				//��ν
				String cwmesq = (String)objects[0];
				if(cwmesq != null && !"".equals(cwmesq)){
					cwmesq = cwmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
					if(cwmesq.length()>18){
						cwmesq = cwmesq.substring(0, 18);
					}
					if(cwmesq.length()<=3){
						script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+cwmesq+"');";
						script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('7',"+(59+Q)+", '0','36');";
					}else if(cwmesq.length() == 4){
						cwmesq = cwmesq.substring(0, 2)+"\\\\r\\\\n"+cwmesq.substring(2, cwmesq.length());
						script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+cwmesq+"');";
						script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('7',"+(59+Q)+", '0','36');";
					}else if(cwmesq.length() == 5||cwmesq.length() == 6){
						cwmesq = cwmesq.substring(0, 3)+"\\\\r\\\\n"+cwmesq.substring(3, cwmesq.length());
						script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+cwmesq+"');";
						script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('7',"+(59+Q)+", '0','33');";
					}else if(cwmesq.length()>8){
						script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+cwmesq+"');";
						script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
						script+="cell.SetCellTextStyle('7','"+(59+Q)+"','0','2');";//�Զ�����
						script+="cell.SetCellFontAutoZoom('7','"+(59+Q)+"','0','1');";//�����Զ���С
					}
				}else{
					script += "cell.SetCellString('7','"+(59+Q)+"', '0','');";
				}
				
				//����
				String xmmesq = (String)objects[1];
				if(xmmesq != null && !"".equals(xmmesq)){
					xmmesq = xmmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
					if(xmmesq.length()>18){
						xmmesq = xmmesq.substring(0, 18);
					}
					if(xmmesq.length() <=4 ){
						script += "cell.SetCellString('16','"+(59+Q)+"', '0','"+xmmesq+"');";
						script += "cell.SetCellFontSize('16','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('16',"+(59+Q)+", '0','36');";
					}else if(4 < xmmesq.length() && xmmesq.length() <=8){
						xmmesq = xmmesq.substring(0, 4)+"\\\\r\\\\n"+xmmesq.substring(4, xmmesq.length());
						script += "cell.SetCellString('16','"+(59+Q)+"', '0','"+xmmesq+"');";
						script += "cell.SetCellFontSize('16','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('16',"+(59+Q)+", '0','33');";
					}else if(xmmesq.length() > 8){
						script += "cell.SetCellString('16','"+(59+Q)+"', '0','"+xmmesq+"');";
						script += "cell.SetCellFontSize('16','"+(59+Q)+"','0','14');";//�����С
						script+="cell.SetCellTextStyle('16','"+(59+Q)+"','0','2');";//�Զ�����
						script+="cell.SetCellFontAutoZoom('16','"+(59+Q)+"','0','1');";//�����Զ���С
					}
				}else{
					script += "cell.SetCellString('16','"+(59+Q)+"', '0','');";
				}
				//������λ��ְλ
				String mesA = (String)objects[4];
				if(mesA != null && !"".equals(mesA)){
					mesA = mesA.replace("\r\n", "").replace("\r", "").replace("\n", "");
					if(mesA.contains("��ȥ��")||mesA.contains("�ѹ�")||mesA.contains("����")||mesA.contains("����")||mesA.contains("����")){
						type = "ȥ��";
					}
					if(mesA.length()>100){
						mesA = mesA.substring(0, 100);
					}
					if(mesA.length()<=16){
						script += "cell.SetCellString('39','"+(59+Q)+"', '0','"+mesA+"');";
						script += "cell.SetCellFontSize('39','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('39',"+(59+Q)+", '0','33');";
					}else if(16<mesA.length() && mesA.length()<=32){
						mesA = mesA.subSequence(0, 16)+"\\\\r\\\\n"+mesA.subSequence(16, mesA.length());
						script += "cell.SetCellString('39','"+(59+Q)+"', '0','"+mesA+"');";
						script += "cell.SetCellFontSize('39','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('39',"+(59+Q)+", '0','33');";
					}else {
						script += "cell.SetCellString('39','"+(59+Q)+"', '0','"+mesA+"');";
						script += "cell.SetCellFontSize('39','"+(59+Q)+"','0','14');";//�����С
						script+="cell.SetCellTextStyle('39','"+(59+Q)+"','0','2');";//�Զ�����
						script+="cell.SetCellFontAutoZoom('39','"+(59+Q)+"','0','1');";//�����Զ���С
						script += "cell.SetCellAlign('39',"+(59+Q)+", '0','33');";
					}
				}else{
					script += "cell.SetCellString('39','"+(59+Q)+"', '0','');";

				}
				
				//����
				String nlmesq = (String)objects[2];
				if("ȥ��".equals(type)){
					nlmesq = "";
					type = "";
				}
				script += "cell.SetCellString('27',"+(59+Q)+",'0','"+nlmesq+"');";
				script += "cell.SetCellAlign('27',"+(59+Q)+",'0','36');";
				//������ò 
				String zzmesq = (String)objects[3];
				if(zzmesq != null && !"".equals(zzmesq)){
					zzmesq = zzmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
					if(zzmesq.length()>18){
						zzmesq = zzmesq.substring(0, 18);
					}
					if(zzmesq.length()<=3){
						script += "cell.SetCellString('31','"+(59+Q)+"', '0','"+zzmesq+"');";
						script += "cell.SetCellFontSize('31','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('31',"+(59+Q)+", '0','36');";
					}else if(zzmesq.length() == 4){
						zzmesq = zzmesq.substring(0, 2)+"\\\\r\\\\n"+zzmesq.substring(2, zzmesq.length());
						script += "cell.SetCellString('31','"+(59+Q)+"', '0','"+zzmesq+"');";
						script += "cell.SetCellFontSize('31','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('31',"+(59+Q)+", '0','36');";
					}else if(zzmesq.length() == 5||zzmesq.length() == 6){
						zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
						script += "cell.SetCellString('31','"+(59+Q)+"', '0','"+zzmesq+"');";
						script += "cell.SetCellFontSize('31','"+(59+Q)+"','0','14');";//�����С
						script += "cell.SetCellAlign('31',"+(59+Q)+", '0','33');";
					}else if(6 < zzmesq.length() && zzmesq.length() <= 9){
						zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, 5)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
						script += "cell.SetCellString('31','"+(59+Q)+"', '0','"+zzmesq+"');";
						script += "cell.SetCellFontSize('31','"+(59+Q)+"','0','11');";//�����С
						script += "cell.SetCellAlign('31',"+(59+Q)+", '0','33');";
					}else{
						script += "cell.SetCellString('31','"+(59+Q)+"', '0','"+zzmesq+"');";
						script += "cell.SetCellFontSize('31','"+(59+Q)+"','0','14');";//�����С
						script+="cell.SetCellTextStyle('31','"+(59+Q)+"','0','2');";//�Զ�����
						script+="cell.SetCellFontAutoZoom('31','"+(59+Q)+"','0','1');";//�����Զ���С
						script += "cell.SetCellAlign('31',"+(59+Q)+", '0','33');";
					}
				}else{
					script += "cell.SetCellString('31','"+(59+Q)+"', '0','');";

				}
				
				
				if(Q == 0){
					Q = Q+3;
				}else{
					Q = Q+2;
				}
				if(Q==15)
				break;
			}
		}	
		/*//��ν
		List CW = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A order by a36.sortId,a36.a3600").list();
		String mesq = "";
		int Q = 0;
		if(CW !=  null && CW.size()>0){
			for(int i1=0;i1<CW.size();i1++){
				if("null".equals(CW.get(i1))||CW.get(i1)==null ||"".equals(CW.get(i1))){
					mesq = "";
				}else{
					mesq = (String)CW.get(i1);
					mesq = mesq.replace("\n", "");
					if(mesq.length()>18){
						mesq = mesq.substring(0, 18);
					}
				}
				if(mesq.length()<=3){
					script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+mesq+"');";
					script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
					script += "cell.SetCellAlign('7',"+(59+Q)+", '0','36');";
				}else if(mesq.length() == 4){
					mesq = mesq.substring(0, 2)+"\\\\r\\\\n"+mesq.substring(2, mesq.length());
					script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+mesq+"');";
					script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
					script += "cell.SetCellAlign('7',"+(59+Q)+", '0','36');";
				}else if(mesq.length() == 5||mesq.length() == 6){
					mesq = mesq.substring(0, 3)+"\\\\r\\\\n"+mesq.substring(3, mesq.length());
					script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+mesq+"');";
					script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
					script += "cell.SetCellAlign('7',"+(59+Q)+", '0','33');";
				}else if(mesq.length()>8){
					script += "cell.SetCellString('7','"+(59+Q)+"', '0','"+mesq+"');";
					script += "cell.SetCellFontSize('7','"+(59+Q)+"','0','14');";//�����С
					script+="cell.SetCellTextStyle('7','"+(59+Q)+"','0','2');";//�Զ�����
					script+="cell.SetCellFontAutoZoom('7','"+(59+Q)+"','0','1');";//�����Զ���С
				}
				if(i1 == 0){
					Q = Q+3;
				}else{
					Q = Q+2;
				}
				if(Q==15)
				break;
				
			}
		}
		//����
		List XM = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
		String mesJ = "";
		int J = 0;
		if(XM != null && XM.size()>0){
			for(int i1=0;i1<XM.size();i1++){
				if("null".equals(XM.get(i1))||"".equals(XM.get(i1)) || XM.get(i1)==null ){
					mesJ = "";
				}else{
					mesJ = (String)XM.get(i1);
					mesJ = mesJ.replace("\n", "");
					if(mesJ.length()>18){
						mesJ = mesJ.substring(0, 18);
					}
				}
				if(mesJ.length() <=4 ){
					script += "cell.SetCellString('16','"+(59+J)+"', '0','"+mesJ+"');";
					script += "cell.SetCellFontSize('16','"+(59+J)+"','0','14');";//�����С
					script += "cell.SetCellAlign('16',"+(59+J)+", '0','36');";
				}else if(4 < mesJ.length() && mesJ.length() <=8){
					mesJ = mesJ.substring(0, 4)+"\\\\r\\\\n"+mesJ.substring(4, mesJ.length());
					script += "cell.SetCellString('16','"+(59+J)+"', '0','"+mesJ+"');";
					script += "cell.SetCellFontSize('16','"+(59+J)+"','0','14');";//�����С
					script += "cell.SetCellAlign('16',"+(59+J)+", '0','33');";
				}else if(mesJ.length() > 8){
					script += "cell.SetCellString('16','"+(59+J)+"', '0','"+mesJ+"');";
					script += "cell.SetCellFontSize('16','"+(59+J)+"','0','14');";//�����С
					script+="cell.SetCellTextStyle('16','"+(59+J)+"','0','2');";//�Զ�����
					script+="cell.SetCellFontAutoZoom('16','"+(59+J)+"','0','1');";//�����Զ���С
				}
				if(i1 == 0){
					J = J+3;
				}else{
					J = J+2;
				}
				if(J==15)
				break;	
			}
		}

		//����
		String endtime = DateUtil.getcurdate();
		List NL = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
		String mesk = "";
		int K = 0;
		if(NL != null && NL.size()>0){
			for(int i1=0;i1<NL.size();i1++){
				if("null".equals(NL.get(i1))||NL.get(i1)==null || "".equals(NL.get(i1))){
					mesk = "";
				}else{
					mesk = (String)NL.get(i1);
				}
				script += "cell.SetCellString('27',"+(59+K)+",'0','"+mesk+"');";
				script += "cell.SetCellAlign('27',"+(59+K)+",'0','36');";
				if(i1 == 0){
					K = K+3;
				}else{
					K = K+2;
				}
				if(K==15)
				break;
			}
		}
		//������ò 
		List ZZMM = HBUtil.getHBSession().createSQLQuery("SELECT (select cv.code_name from code_value cv where cv.code_type='GB4762' and cv.code_value=a36.a3627) a from a36 a36 where a36.a0000='"+id+"' order by a36.sortId,a36.a3600").list();
		String mesW = "";
		int W = 0;
		if(ZZMM != null && ZZMM.size()!=0){
			for(int i1=0;i1<ZZMM.size();i1++){
				if("null".equals(ZZMM.get(i1))||ZZMM.get(i1)==null || "".equals(ZZMM.get(i1))){
					mesW = "";
				}else{
					mesW = (String)ZZMM.get(i1);
					mesW = mesW.replace("\n","");
					if(mesW.length()>18){
						mesW = mesW.substring(0, 18);
					}
				}
				if(mesW.length()<=3){
					script += "cell.SetCellString('31','"+(59+W)+"', '0','"+mesW+"');";
					script += "cell.SetCellFontSize('31','"+(59+W)+"','0','14');";//�����С
					script += "cell.SetCellAlign('31',"+(59+W)+", '0','36');";
				}else if(mesW.length() == 4){
					mesW = mesW.substring(0, 2)+"\\\\r\\\\n"+mesW.substring(2, mesW.length());
					script += "cell.SetCellString('31','"+(59+W)+"', '0','"+mesW+"');";
					script += "cell.SetCellFontSize('31','"+(59+W)+"','0','14');";//�����С
					script += "cell.SetCellAlign('31',"+(59+W)+", '0','36');";
				}else if(mesW.length() == 5||mesW.length() == 6){
					mesW = mesW.substring(0, 3)+"\\\\r\\\\n"+mesW.substring(3, mesq.length());
					script += "cell.SetCellString('31','"+(59+W)+"', '0','"+mesW+"');";
					script += "cell.SetCellFontSize('31','"+(59+W)+"','0','14');";//�����С
					script += "cell.SetCellAlign('31',"+(59+W)+", '0','33');";
				}else if(6 < mesW.length() && mesW.length() <= 9){
					mesW = mesq.substring(0, 3)+"\\\\r\\\\n"+mesW.substring(3, 5)+"\\\\r\\\\n"+mesW.substring(3, mesq.length());
					script += "cell.SetCellString('31','"+(59+W)+"', '0','"+mesW+"');";
					script += "cell.SetCellFontSize('31','"+(59+W)+"','0','11');";//�����С
					script += "cell.SetCellAlign('31',"+(59+W)+", '0','33');";
				}else{
					script += "cell.SetCellString('31','"+(59+W)+"', '0','"+mesW+"');";
					script += "cell.SetCellFontSize('31','"+(59+W)+"','0','14');";//�����С
					script+="cell.SetCellTextStyle('31','"+(59+W)+"','0','2');";//�Զ�����
					script+="cell.SetCellFontAutoZoom('31','"+(59+W)+"','0','1');";//�����Զ���С
					script += "cell.SetCellAlign('31',"+(59+W)+", '0','33');";
				}
				if(i1 == 0){
					W = W+3;
				}else{
					W = W+2;
				}
				if(W==15)
				break;
			}
		}
	//������λ��ְλ
		List GZDW = HBUtil.getHBSession().createSQLQuery("select a36.a3611 from a36 a36 where a36.a0000 = '"+id+"'").list();
		String mesA = "";
		int A = 0;
		if(GZDW != null && GZDW.size()>0){
			for(int i1=0;i1<GZDW.size();i1++){
				if("null".equals(GZDW.get(i1))||GZDW.get(i1)==null || "".equals(GZDW)){
					mesA = "";
				}else{
					mesA = (String)GZDW.get(i1);
					mesA = mesA.replace("\n", "");
					if(mesA.length()>100){
						mesA = mesA.substring(0, 100);
					}
				}
				if(mesA.length()<=16){
					script += "cell.SetCellString('39','"+(59+A)+"', '0','"+mesA+"');";
					script += "cell.SetCellFontSize('39','"+(59+A)+"','0','14');";//�����С
					script += "cell.SetCellAlign('39',"+(59+A)+", '0','36');";
				}else if(16<mesA.length() && mesA.length()<=32){
					mesA = mesA.subSequence(0, 16)+"\\\\r\\\\n"+mesA.subSequence(16, mesA.length());
					script += "cell.SetCellFontSize('39','"+(59+A)+"','0','14');";//�����С
					script += "cell.SetCellAlign('39',"+(59+A)+", '0','33');";
				}else {
					script += "cell.SetCellString('31','"+(59+A)+"', '0','"+mesA+"');";
					script += "cell.SetCellFontSize('31','"+(59+A)+"','0','14');";//�����С
					script+="cell.SetCellTextStyle('7','"+(59+A)+"','0','2');";//�Զ�����
					script+="cell.SetCellFontAutoZoom('7','"+(59+A)+"','0','1');";//�����Զ���С
					script += "cell.SetCellAlign('31',"+(59+A)+", '0','33');";
				}
				if(i1 == 0){
					A = A+3;
				}else{
					A = A+2;
				}
				if(A==15)
				break;
			}
		}*/
	
		script += "cell.ShowSideLabel('0','0');";//�����б�
		script += "cell.ShowTopLabel('0','0');";//�����б�
//		System.out.println(script);
		this.getPageElement("selectedid").setValue(script);
		this.getExecuteSG().addExecuteCode("docellshow()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����ְ������ְ������ְ�񣬳ʱ���λ
	 * @param line
	 * @param row
	 * @param messages
	 * @return
	 */
	public String zwandnrm(int line,int row, String messages){
		String script = "";
		if(messages != null && !"".equals(messages)){
			script += "cell.SetCellString('"+line+"','"+row+"', '0','"+ messages.replace("\n", "") +"');";
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
			script += "cell.SetCellAlign('"+line+"','"+row+"',0,33);";
			script+="cell.SetCellTextStyle('"+line+"','"+row+"','0','2');";//�Զ�����
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";
			if(messages.length()>52){
				script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','0','1');";//�����Զ���С
			}
		}else{
			script += "cell.SetCellString('"+line+"','"+row+"', '0','');";

		}
		
		return script;
	}
	/**
	 * ѧ��ѧλ���øĵķ���
	 * @param line
	 * @param row
	 * @param xlxu
	 * @param type ������ѧ��ѧλ����ѧ��ѧλ��Ϣ
	 * @return
	 */
	public String xlxw(int line,int row,String xlxu,String type){
		String script = "";
		int number = 0;//��������
		if("1".equals(type)){
			number = 7;
		}else{
			number = 11;
		}
		script+="cell.SetCellString('"+line+"','"+row+"','0','"+xlxu+"');";
		script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
		if(xlxu.length() <= number){
			if("2".equals(type)){
				script+="cell.SetCellAlign('"+line+"','"+row+"','0','33');";
			}else{
				script+="cell.SetCellAlign('"+line+"','"+row+"','0','36');";
			}
		}else{
			script+="cell.SetCellTextStyle('"+line+"','"+row+"','0','2');";//�Զ�����
			script+="cell.SetCellAlign('"+line+"','"+row+"','0','33');";//���䷽ʽ
			script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','0','1');";//�����Զ���С
		}
		return script;
	}
	/**
	 * רҵ����ְ�����Ϥרҵ�к�ר�����øĵķ���
	 * @param line �� 
	 * @param row �� 
	 * @param messages ��ѯ������
	 * @param name ������רҵ����ְ����������Ϥ�к�ר��
	 * @return
	 */
	public String getInfo(int line,int row,String messages,String name){
		messages = messages.replace("\r\n", "").replace("\r", "").replace("\n", "");
		String script = "";
		int rownumma =  0;//ÿ�е�����
		int rownummb =  0;//ÿ�е�����
		if("רҵ����ְ��".equals(name)){
			rownumma = 8;
			rownummb = 16;
		}else{
			rownumma = 9;
			rownummb = 18;
		}
		if(messages.length() <= rownumma){
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ messages +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','36');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
		}else if(rownumma < messages.length() && messages.length() <= rownummb ){
			String infomess = "";
			infomess = messages.substring(0, rownumma)+"\\\\r\\\\n"+messages.substring(rownumma,  messages.length());
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ infomess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
		}else if(rownummb < messages.length()){
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ messages +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
//			script += "cell.SetCellFontSize('"+line+"','"+row+"','0',14);";//�����С
			script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','0','1');";//�����Զ���С
		}else{}
		return script;
	}
	//����18���ֵĵ�Ԫ���ͳһ����
	public String fountsize(int line,int row,String messages){
		String script = "";
		if(messages.length()<=4){
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ messages +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','36');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
		}else if(4 < messages.length() && messages.length() <= 8 ){
			String infomess = "";
			infomess = messages.substring(0, 4)+"\\\\r\\\\n"+messages.substring(4,  messages.length());
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ infomess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','14');";//�����С
		}else if(8 < messages.length() && messages.length() <= 10){
			String infomess = "";
			infomess = messages.substring(0, 5)+"\\\\r\\\\n"+messages.substring(5,  messages.length());
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ infomess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','12');";//�����С
		}else if(10 < messages.length() && messages.length() <= 15){
			String infomess = "";
			infomess = messages.substring(0, 5)+"\\\\r\\\\n"+messages.substring(5,10)+"\\\\r\\\\n"+messages.substring(10,messages.length());
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ infomess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','11');";//�����С
		}else if(15 < messages.length() && messages.length() <= 18){
			String infomess = "";
			infomess = messages.substring(0, 6)+"\\\\r\\\\n"+messages.substring(6,12)+"\\\\r\\\\n"+messages.substring(12,messages.length());
			script += "cell.SetCellString('"+line+"','"+row+"','0','"+ infomess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','0','10');";//�����С
		}else{}
		return script;
	}
	//��ȡ��������ĸ���������
	public int[] getcountsize(String a1701) {
		int[] arr = new int[2];
		int countzishu = 0;
		String[] jianli = a1701.split("\r\n|\r|\n");//�з�
		int countrow = jianli.length;
		arr[0] = countrow;
		for(int i=0;i<jianli.length;i++){
			String jl = jianli[i].trim();
			Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
	        Matcher matcher = pattern.matcher(jl);     
	        if (matcher.find()) {
	        	String line1 = matcher.group(0);  
	        	int index = jl.indexOf(line1);
	        	if(index==0){//�����ڿ�ͷ  (һ��)
	        		String line2 = jl.substring(line1.length()).trim();
	        		countzishu += line2.length();
	        	}
	        }else{
	        	countzishu += jl.length();
	        }
		}
		arr[1] = countzishu;
		return arr;
	}
	//������ʽ���������ڵ�
	private int  parseJL(String line2, StringBuffer jlsb,int countsize,int countrow){
		String mess = line2;//���ո��м����ַ�
		int rowsoze = 0;//��ͬ������ÿ����ʾ��������ͬ��
		if(countsize <=525 && countrow <=25){
			rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
		}else if((525<countsize && countsize<=810) && (25<countrow &&countrow <=30)){
			rowsoze = 27;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
		}else if((810<countsize && countsize<928)&&(30<countrow && countrow<=32)){
			rowsoze = 29;//11���ֿɴ�32��ÿ��29���� ��928���֡�
		}else{
			rowsoze = 35;//��ʱ��д
		}
		int llength = line2.length();//�����ַ����ܳ�
		int messcount = mess.length();//�����ַ����ܳ�
		if(messcount > rowsoze){
			int b =  1;//���������ַ���λ�ã�
			int xunh = 0;
			if(messcount%rowsoze ==0){
				xunh = messcount/rowsoze;
			}else{
				xunh = (messcount/rowsoze)+1;
			}
			for(int a = 0;a < xunh;a++){
				String messa = "";
				if(messcount - a*rowsoze > rowsoze){
					messa = mess.substring(a*rowsoze, b*rowsoze);
					jlsb.append(messa).append("\r\n").append("                  ");
				}else {
					messa = mess.substring(a*rowsoze);
					jlsb.append(messa).append("\r\n");
				}
				b++;
				
			}
		}else{
			jlsb.append(mess).append("\r\n");
		}
		return rowsoze;
	}
	//������ʽ�����������ڵ�
	private int parseJL2(String line2, StringBuffer jlsb,int countsize,int countrow){
		String mess = line2;//���ո��м����ַ�
		int rowsoze = 0;//��ͬ������ÿ����ʾ��������ͬ��
		if(countsize <=525 && countrow <=25){
			rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
		}else if((525<countsize && countsize<=810) && (25<countrow &&countrow <=30)){
			rowsoze = 27;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
		}else if((810<countsize && countsize<928)&&(30<countrow && countrow<=32)){
			rowsoze = 29;//11���ֿɴ�32��ÿ��29���� ��928���֡�
		}else{
			rowsoze = 35;//��ʱ��д
		}
		int llength = line2.length();//�����ַ����ܳ�
		int messcount = mess.length();//�����ַ����ܳ�
		if(messcount > rowsoze){
			int b =  1;//���������ַ���λ�ã�
			int xunh = 0;
			if(messcount%rowsoze ==0){
				xunh = messcount/rowsoze;
			}else{
				xunh = (messcount/rowsoze)+1;
			}
			for(int a = 0;a < xunh;a++){
				String messa = "";
				if(messcount - a*rowsoze > rowsoze){
					messa = mess.substring(a*rowsoze, b*rowsoze);
				}else {
					messa = mess.substring(a*rowsoze);
				}
				b++;
				jlsb.append("                  ").append(messa).append("\r\n");
			}
		}else{
			jlsb.append("                  ").append(mess).append("\r\n");
		}
		return rowsoze;
	}
	@PageEvent("openExpPathWin")
	 public int openExpPathWin() throws RadowException{
		this.setRadow_parent_data("ExpPathWin"); 
		this.openWindow("ExpPathWin", "pages.publicServantManage.PDFExpPath");
		return EventRtnType.NORMAL_SUCCESS;
	 }
	private String parseJLto(StringBuffer jlsb, int rowsoze, String jianlimes, int length) {
		if(!"".equals(jianlimes)){
			String jianlik = jlsb.toString();
			String jianlikk = jianlik.substring((jianlik.length()-2));
			if("\r\n".equals(jianlikk)){
				jlsb.delete(0, jlsb.length());
				jlsb.append(jianlik.substring(0, (jianlik.length()-2)));
			}
			int c = 1;
			String messa = "";
			int zc = jianlimes.length();
			if(zc>(rowsoze - length)){
				messa = jianlimes.substring(0, (rowsoze - length));
				jlsb.append(messa).append("\r\n").append("                  ");
				String messo = jianlimes.substring((rowsoze - length));
				int jlcount = messo.length();//�����ַ����ܳ�
				int xunhy = 0;
				if(jlcount%rowsoze ==0){
					xunhy = jlcount/rowsoze;
				}else{
					xunhy = (jlcount/rowsoze)+1;
				}
				for(int a =0 ;a<xunhy;a++){
					if(jlcount - a*rowsoze > rowsoze){
						messa = messo.substring(a*rowsoze, c*rowsoze);
						jlsb.append(messa).append("\r\n").append("                  ");
//						jlsb.append(messa).append("\r\n");
					}else {
						messa = messo.substring(a*rowsoze);
						jlsb.append(messa).append("\r\n");
					}
					c++;
				}
			}else{
				jlsb.append(jianlimes).append("\r\n");
			}
		}
		return "1";
		
	}
	//������ʽ���������ڵ�
	private int  parseJL(String line2, StringBuffer jlsb,int rowsoze,String type,int length){
		/*if("1".equals(type)){
			jlsb.append("\r\n");
		}*/
		String mess = line2;//���ո��м����ַ�
		int llength = line2.length();//�����ַ����ܳ�
		int messcount = mess.length();//�����ַ����ܳ�
		if(messcount > rowsoze){
			int b =  1;//���������ַ���λ�ã�
			int xunh = 0;
			if(messcount%rowsoze ==0){
				xunh = messcount/rowsoze;
			}else{
				xunh = (messcount/rowsoze)+1;
			}
			if(xunh<=1){
				for(int a = 0;a < xunh;a++){
					String messa = "";
					if(messcount - a*rowsoze > rowsoze){
						messa = mess.substring(a*rowsoze, b*rowsoze);
						jlsb.append(messa).append("\r\n").append("                  ");
//						jlsb.append(messa).append("\r\n");
					}else {
						messa = mess.substring(a*rowsoze);
						jlsb.append(messa).append("\r\n");
//						jlsb.append(messa);
					}
					b++;
				}
			}else{
				for(int a = 0;a < xunh;a++){
					String messa = "";
					if(messcount - a*rowsoze > rowsoze){
						messa = mess.substring(a*rowsoze, b*rowsoze);
						jlsb.append(messa).append("\r\n").append("                  ");
//						jlsb.append(messa).append("\r\n");
					}else {
						messa = mess.substring(a*rowsoze);
						length = messa.length();
						jlsb.append(messa);
						
					}
					b++;
				}
				
			}
		
		}else{
			jlsb.append(mess).append("\r\n");
			length = -mess.length();
		}
		return length;
	}
	private void parseJLtoo(StringBuffer jlsb, int rowsoze, String jianlimes, int length) {
		String jianlikk ="";
		String type ="";
		if(!"".equals(jianlimes)){
			String jianlik = jlsb.toString();
			if(!"".equals(jianlik)){
				jianlikk = jianlik.substring((jianlik.length()-2));
			}
			if("".equals(jianlik)){
				type = "1";
			}
			if("\r\n".equals(jianlikk)){
				int jls = jianlimes.length();
				int xunhy = 0;
				if(jls%rowsoze ==0){
					xunhy = jls/rowsoze;
				}else{
					xunhy = (jls/rowsoze)+1;
				}
				int c = 1;
				String messa = "";
				for(int a =0 ;a<xunhy;a++){
					if(jls - a*rowsoze > rowsoze){
						messa = jianlimes.substring(a*rowsoze, c*rowsoze);
						jlsb.append("                  ").append(messa).append("\r\n");
//						jlsb.append(messa).append("\r\n");
					}else {
						messa = jianlimes.substring(a*rowsoze);
						jlsb.append("                  ").append(messa).append("\r\n");
					}
					c++;
				}
			}else{
				int c = 1;
				length = -length;
				String messa = "";
				int zc = jianlimes.length();
				if(zc>(rowsoze - length)){
					messa = jianlimes.substring(0, (rowsoze - length));
					if(("1").equals(type)){
						jlsb.append("                  ").append(messa).append("\r\n");
					}else{
						jlsb.append(messa).append("\r\n").append("                  ");
					}
					String messo = jianlimes.substring((rowsoze - length));
					int jlcount = messo.length();//�����ַ����ܳ�
					int xunhy = 0;
					if(jlcount%rowsoze ==0){
						xunhy = jlcount/rowsoze;
					}else{
						xunhy = (jlcount/rowsoze)+1;
					}
					for(int a =0 ;a<xunhy;a++){
						if(jlcount - a*rowsoze > rowsoze){
							messa = messo.substring(a*rowsoze, c*rowsoze);
							jlsb.append(messa).append("\r\n").append("                  ");
//							jlsb.append(messa).append("\r\n");
						}else {
							messa = messo.substring(a*rowsoze);
							if(("1").equals(type)){
								jlsb.append("                  ").append(messa).append("\r\n");
							}else{
								jlsb.append(messa).append("\r\n");
							}
						}
						c++;
					}
				}else{
					jlsb.append(jianlimes).append("\r\n");
				}
			}
			
		}
		
	}
	//
	public String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��|-]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//�����ڿ�ͷ  (һ��)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	//originaljl.append(jl);
			        	originaljl.append(jl).append("\r\n");//һ�μ�������ƴ�ϻس�
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
			if(jlsb.lastIndexOf("\r\n")==jlsb.length()-2 ){
				jlsb.delete(jlsb.length()-2, jlsb.length());
        	}
			return jlsb.toString();
			
		}
		return a1701;
	}
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//�ܳ�
		//25����һ�С�
		int oneline = 21;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//���� 50���ֽ�����ƫ�ƣ�ֱ���㹻Ϊֹ��
    		boolean hass = false;
    		while((end+offset)<llength){//25����һ�У����з��ָ�
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//25����һ�е�����50���ֽ� ������
    				loffset++;
    				if((end+offset+loffset)>llength){//�����ܳ��� �˳�ѭ��
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//���ܻ����һ��51���ֽڣ���ǰ��һ��
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				//jlsb.append(l);
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				//jlsb.append("                  ").append(l);
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			//jlsb.append(line2);
    			jlsb.append(line2).append("\r\n");
    		}else{
    			//jlsb.append("                  ").append(line2);
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
}
