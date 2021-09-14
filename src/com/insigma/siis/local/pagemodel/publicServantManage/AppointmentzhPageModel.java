package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class AppointmentzhPageModel extends PageModel {
	public static int i = 0;
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.getExecuteSG().addExecuteCode("showdata()");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {//this.request.getSession().getAttribute("allSelect"��

		return EventRtnType.NORMAL_SUCCESS;
	}
	public String init(String personids) throws RadowException{
		String pid = personids.replace("|", "'").replace("@", ",");
		String script = "";
		script += "<script type='text/javascript'>\n";
		script += "function pageinit(){\n";
		int i =pid.split(",").length;
//		int i = getcharnu(pid,",");
		for(int j=0;j<i;j++){
			String querypid = "";
			if(pid.indexOf(",") == -1){
				querypid = pid;
			}else{
				querypid = pid.substring(0, pid.indexOf(","));
			}
			try {
				String sql = "select a01.a0101 from a01 where a01.a0000="+querypid+"";
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select a01.a0101 from a01 where a01.a0000="+querypid+"").executeQuery();
				while(res.next()){
					script += "document.getElementById('personinfo').options.add(new Option('"+res.getString(1)+"',"+querypid+"));\n";
					
				}
				pid = pid.substring(pid.indexOf(",")+1,pid.length());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		script +="}\n";
		script +="</script>";
		return script;
	}
	//���ĳһ�ַ�������ĳһ�ַ��ĸ���
	public int getcharnu(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			i++;
			getcharnu(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return i;
		}
		return 0;
	}
	@PageEvent("personShow")
	public int showTemByzh(String id) throws RadowException, HibernateException, SQLException{
		String attribute = (String)request.getSession().getAttribute("allSelect");
		String script = "";
		//�ò����Ǵ�customquerypagemodel��д�����Ϣ
//		request.getSession().removeAttribute("personidy");
		String tpid = this.getPageElement("subWinIdBussessId").getValue();
		String idc = "40e9b81c-5a53-445f-a027-6e00a9f6,3de1c725-d71b-476a-b87c-6c8d2184";
		String idc2 = "47b1011d70f34aefb89365bbfce,eebdefc2-4d67-4452-a973";//����ģ��
		if(idc.contains(tpid)){//�����Ƿ�Ϊ������͸ɲ�����
			String ids = "";
			String idss = (String)request.getSession().getAttribute("personidy");
			if(idss != null && !"".equals(idss)){
				ids = idss;
			}else{
				ids = (String)request.getSession().getAttribute("personidall");
			}
			BeiAnBiaoKeep bbk = null;
			if(bbk ==null ){
				bbk = new BeiAnBiaoKeep();
				this.getExecuteSG().addExecuteCode("hide()");
				script += bbk.keep(tpid,ids);
			}
		}else if(idc2.contains(tpid)){
			/*String ids = "";
			String idss = (String)request.getSession().getAttribute("personidy");
			if(idss != null && !"".equals(idss)){
				ids = idss;
			}else{
				ids = (String)request.getSession().getAttribute("personidall");
			}*/
			String ids = (String)request.getSession().getAttribute("personidy");
			RegiterBook  rb = null;
			if(rb == null){
				rb = new RegiterBook();
				this.getExecuteSG().addExecuteCode("hide()");
				script += rb.book(tpid,ids,attribute);
				}
		}else{
			String tpname = "";
			String tptype = "";
			script += "var cell = document.getElementById('cellweb1');\n";
			script += "var path = getPath();\n";
			script += "cell.WorkbookReadonly=true;\n";//���ò��ɱ༭
			try {
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("select TPName,TPID,TPType from listoutput2 where TPID = '"+tpid+"' group by TPName,TPID,TPType").executeQuery();
				while(rs.next()){
					tpname = rs.getString(1);
					tptype = rs.getString(3);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if("1".equals(tptype)){
				script += "var  aa = cell.openfile(ctpath+'/template/'+pinyin.getCamelChars('"+tpname+"').replace('��','').replace('��','').replace('��','').replace('��','')+'.cll','');\n";
			}else{
//				script += "cell.openfile(ctpath+'/template/'+'"+tpid+"'+'.cll','');\n";
			}
			StringBuffer sf = new StringBuffer();
			String sql = "select messagee,zbline,zbrow,pagenu from listoutput2 where tpid = '"+tpid+"'";
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			String tabel = "";//��¼����
			String tabelwher = "";//����
			int a = 1;
			Map<String,String> maps = null;
			List<Map<String,String>> lists = null;//��ŵ�������ѯ����Ϣ��
	 		String mosaic = "";
	 		String society = "cw,xm,nljy,zzmmjy,gzdwjzw";//����ϵ
	 		String nrm = "a5319-a53.a5319,a5304-a53.a5304,a5317-a53.a5317,a5315-a53.a5315";//������
	 		String aa = "nl,rzsj,rgzwccsj,mzsj,tbsjn,XGSJ,dqsj,a0107-a01.a0107,a0134-a01.a0134";
			for (Object[] obj : list) {
				String mess = (String)obj[0];
				String messline = (String)obj[1];
				String messrow = (String)obj[2];
				int pagenuu = (Integer.parseInt((String)obj[3])-1);
				String pagenu = pagenuu+"";
				if(mess != null &&("zp".equals(mess) ||"rdsj".equals(mess) ||"jl".equals(mess)
						|| "cw".equals(mess)||"xm".equals(mess)||"csnyjy".equals(mess)
						|| "nljy".equals(mess)||"zzmmjy".equals(mess)||"gzdwjzw".equals(mess) 
						|| "dqyhm".equals(mess))|| "a5319-a53.a5319".equals(mess)|| "a5304-a53.a5304".equals(mess)|| "a5317-a53.a5317".equals(mess)|| "a5315-a53.a5315".equals(mess)){//��������ѯ��
					if(society.contains(mess)){
						continue;
					}
					if(nrm.contains(mess)){//��������Ϣ������ѯ
						continue;
					}
					if(maps ==null){
						maps = new HashMap<String, String>();
					}
					if(lists == null){
						lists = new ArrayList<Map<String,String>>();
					}
					mosaic = "wy"+messline+"wy"+messrow+"wy"+pagenu; //ƴ�ӵ���Ϣ�����к�ҳ��
					maps.put(mess, mosaic);
					lists.add(maps);
				}else{
					if(aa.contains(mess)){//����дsql��
						 mess = special(mess);
						}
					String[] split = mess.split("-");
					mess = split[1];
					if(tpid.equals("04f59673-9c3a-4d9c-b016-a5b789d636e2") && mess.equals("a01.a0192a")){
						//��������������λ��ְ��ȫ������дsql
						if(DBUtil.getDBType()==DBType.MYSQL){
							mess = "substr(a01.a0192a,1,instr(a01.a0192a,(select GROUP_CONCAT(a02.a0215a) from a02 a02 where a02.a0000 = 'id' and a0255 = '1'))-1)";
						}else{
							mess = "substr(a01.a0192a,1,instr(a01.a0192a,(select wm_concat(a02.a0215a) from a02 a02 where a02.a0000 = 'id' and a0255 = '1'),1,1)-1)";
						}
			 		}
					if(mess.contains("select")){
						mess = "("+mess+")";
					}else{
						if(mess != null && !mess.contains("\'.\'")){//���˳�������
							String tabel2 = mess.substring(0, 3);
							if(!tabel.contains(tabel2)){//��ȡ��
								if(a%2 != 0){
									tabelwher += " and "+ tabel2+".a0000 = ";
								}else{
									tabelwher += tabel2+".a0000,";
								}
								a++;
								tabel += tabel2+" "+tabel2+ ",";
							}
						}
					}
					mosaic = mess +" as "+split[0]+"wy"+messline+"wy"+messrow+"wy"+pagenu+","; 
					sf.append(mosaic);
				}
			}
			if(a==2)tabelwher = "";
			for(int o = 0;o<1;o++){
				script += selectInto(sf.toString(), tabel, id, tabelwher, lists,tpname,o,tpname);//���в�ѯ
				//��Ҫ���⴦��Ͷ�����Ϣ�ĵ�����ѯ
				if(lists != null && lists.size()>0){
					for(int b = 0;b<lists.size();b++){
						Map<String, String> map = lists.get(b);
						Set<String> keySet = map.keySet();
						Object[] array = keySet.toArray();
						String string = (String)array[b];//��ȡ��key
						String string2 = map.get(string);
						String[] split = string2.split("wy");
						String line = split[1];
//						String row = (String)split[2];
						String row = (String)split[2];
						int rowi = Integer.valueOf(split[2]);
//						String pagenu = split[3];
						int pagenu = o;
						if("zp".equals(string)){
							String mes = "";
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
							if(listj != null && listj.size() ==0){
								mes = "";
								script+="cell.SetCellString('"+line+"','"+row+"', '"+pagenu+"','"+mes+"');";
								script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
								script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";
							}else{
								Object[] sz = listj.get(0);
								Object photopath = sz[0];
								Object photoname = sz[1];
								String ptpath = photopath.toString().toUpperCase();
								//��������
								//2017.04.19 yinl �޸�ͼƬ��ַ
								script+="cell.SetCellString('"+line+"','"+row+"', '"+pagenu+"','"+mes+"');";
								String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
								script+="cell.SetCellImage('"+line+"','"+row+"','"+pagenu+"',cell.AddImage('"+imagepath+"'),'1','1','1');";
							}
						}else if("rdsj".equals(string)){
//							List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select a01.a0101,a01.a0104,cv.code_name,a01.a0111a,a01.a0114a,a01.a0134,a01.a0128,a01.a0196,a01.a0187a,a01.a0192a,a01.a0107,a01.a0140,a01.a14z101,a01.a15z101 from a01 a01,code_value cv where a01.a0117 = cv.code_value  and cv.code_type =  'GB3304' and a01.a0000 = '8AF1B8D5-2ADA-48C2-A8D9-00BA9D5119B8'").list();
							List list2 = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.a0000 = '"+id+"'").list();
							String messages  = (String)list2.get(0);//�뵳ʱ��---------------------------------------------
							if(messages != null && !"".equals(messages)){
//								int length = 0;
								String mes = "";
								if(messages.contains("(")&&messages.contains(")")&& messages.contains(".")){
									int length = messages.length();
									int indexOf = messages.indexOf("(");
									String sub = messages.substring(indexOf, length);
									mes = messages.substring(0, indexOf)+"\\\\r\\\\n"+sub;
								}else if(messages.contains("(")&&messages.contains(")") && !messages.contains(".")){
									mes = messages;
								}else if(messages.length()>=6){
									mes = messages.replace(".", "");
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}else{
									mes = messages;
								}
								script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ mes +"');";
								script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
								script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";
							}
						}else if("jl".equals(string)){
							ResultSet rs;
							try {
								rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
								int t = rs.getRow();
								if(t==0){
									script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+"".replace("\n", "\\\\r\\\\n")+"');";
									script += "cell.SetCellNumType('"+line+"','"+row+"','"+pagenu+"','0');";
								}
								//rs.previous();
								String mes = "";
								while(rs.next()){
									int length = 0;  
									int rowsoze = 0;//��ͬ������ÿ����ʾ��������ͬ��
									int zihao = 0;//�ֺ�
									String jianadd = ""; 
									StringBuffer pinjieJLgs = new StringBuffer("");
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
										if("�ɲ�����������".equals(tpname)||"����Ա�ǼǱ�".equals(tpname) || "���յǼǱ�".equals(tpname) || "����������".equals(tpname)){
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
												this.setMainMessage("���������С�޷���ʾ");
												break;
											}
										}else if("����������".equals(tpname)){
											if(countsize <=336 && countrow <=16){
												rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
											}else if((countsize<=494) && (16<countrow &&countrow <19)){
												rowsoze = 26;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
											}else if((countsize<560)&&(19<=countrow && countrow<20)){
												rowsoze = 28;//11���ֿɴ�32��ÿ��29���� ��928���֡�
											}else if((countsize<759)&&(20<=countrow && countrow<23)){
												rowsoze = 33;//10���ֿɴ�37��ÿ��34���� ��1250���֡�
											}else if((countsize<950)&&(23<=countrow && countrow<25)){
												rowsoze = 38;//9���ֿɴ�39��ÿ��38���� ��1282���֡�
											}else if((countsize<1148)&&(25<=countrow && countrow<28)){
												rowsoze = 41;//8���ֿɴ�41��ÿ��42���� ��1314���֡�
											}else if((countsize<1836)&&(28<=countrow && countrow<34)){
												rowsoze = 54;//7���ֿɴ�43��ÿ��46���� ��1346���֡�
											}else if((countsize<2418)&&(34<=countrow && countrow<39)){
												rowsoze = 62;//6���ֿɴ�45��ÿ��50���� ��1378���֡�
											}else{
												this.setMainMessage("���������С�޷���ʾ");
												break;
											}
										}else if("����Ա¼�ñ�".equals(tpname)){
											if(countsize <=210 && countrow <10){
												rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
											}else if((countsize<=324) && (10<=countrow &&countrow <12)){
												rowsoze = 27;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
											}else if((countsize<377)&&(12<=countrow && countrow<13)){
												rowsoze = 29;//11���ֿɴ�32��ÿ��29���� ��928���֡�
											}else if((countsize<510)&&(13<=countrow && countrow<15)){
												rowsoze = 34;//10���ֿɴ�37��ÿ��34���� ��1250���֡�
											}else if((countsize<624)&&(15<=countrow && countrow<16)){
												rowsoze = 39;//9���ֿɴ�39��ÿ��38���� ��1282���֡�
											}else if((countsize<756)&&(16<=countrow && countrow<18)){
												rowsoze = 42;//8���ֿɴ�41��ÿ��42���� ��1314���֡�
											}else if((countsize<1188)&&(18<=countrow && countrow<22)){
												rowsoze = 54;//7���ֿɴ�43��ÿ��46���� ��1346���֡�
											}else if((countsize<1512)&&(22<=countrow && countrow<24)){
												rowsoze = 63;//6���ֿɴ�45��ÿ��50���� ��1378���֡�
											}else{
												this.setMainMessage("���������С�޷���ʾ");
												break;
											}
										}
										
//										mes = formatJL2(rs.getString(1),countsize);
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
//										        		if(!"".equals(jianlimes)){
										        		if(j==1){
										        			if(length<0){
										        				length = -length;
										        			}
										        			parseJLto(pinjieJLgs,rowsoze,jianlimes.replace("\r\n", "").replace("\r", "").replace("\n", ""),length);
										        			j = 0;
										        			length = 0;
										        		}
										        		if(length > 0){
//										        			type = parseJLto(pinjieJLgs,rowsoze,jianlimes,length);
										        			pinjieJLgs.append("\r\n");
										        		}
										        		length = 0;
										        		pinjieJLgs.append(line1).append("  ");
										        		String line2 = jl.substring(line1.length()).trim();
										        		length = parseJL(line2.replace("\r\n", "").replace("\\r\\n", ""), pinjieJLgs,rowsoze,type,length);
										        		jianlimes  = "";
										        	}
										        }else{
//										        	parseJL2(jl, pinjieJLgs,rowsoze);
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
									if("�ɲ�����������".equals(tpname)||"����Ա�ǼǱ�".equals(tpname) || "���յǼǱ�".equals(tpname) || "����������".equals(tpname)){
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
									}else if("����������".equals(tpname)){
										if(rowsoze == 21){
											zihaoto = 14;
										}else if(rowsoze == 26){
											zihaoto = 12;
										}else if(rowsoze == 28){
											zihaoto = 11;
										}else if(rowsoze == 33){
											zihaoto = 10;
										}else if(rowsoze == 38){
											zihaoto = 9;
										}else if(rowsoze == 41){
											zihaoto = 8;
										}else if(rowsoze == 54){
											zihaoto = 7;
										}else if(rowsoze == 62){
											zihaoto = 6;
										}else if(rowsoze == 54){
											zihaoto = 5;
										}
									}else if("����Ա¼�ñ�".equals(tpname)){
										if(rowsoze == 21){
											zihaoto = 14;
										}else if(rowsoze == 27){
											zihaoto = 12;
										}else if(rowsoze == 29){
											zihaoto = 11;
										}else if(rowsoze == 34){
											zihaoto = 10;
										}else if(rowsoze == 39){
											zihaoto = 9;
										}else if(rowsoze == 42){
											zihaoto = 8;
										}else if(rowsoze == 54){
											zihaoto = 7;
										}else if(rowsoze == 63){
											zihaoto = 6;
										}else if(rowsoze == 54){
											zihaoto = 5;
										}
									}
									
									script+="cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+pinjieJLgs.toString().replace("\n", "\\\\r\\\\n")+"');";
									script+="cell.SetCellNumType('"+line+"','"+row+"','"+pagenu+"','0');";
									script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','"+zihaoto+"');";//�����С
								}
								//���������Զ���С
								int length = mes.length();
								if(mes.length()>1044){
									script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}else if("cw".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
							String cwmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										cwmesq = "";
									}else{
										cwmesq = (String)listj.get(i1);
									}
									cwmesq = cwmesq.replace("\n", "");
									if(cwmesq.length()>18){
										cwmesq = cwmesq.substring(0, 18);
									}
									if(cwmesq.length()<=3){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(cwmesq.length() == 4){
										cwmesq = cwmesq.substring(0, 2)+"\\\\r\\\\n"+cwmesq.substring(2, cwmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(cwmesq.length() == 5||cwmesq.length() == 6){
										cwmesq = cwmesq.substring(0, 3)+"\\\\r\\\\n"+cwmesq.substring(3, cwmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(cwmesq.length()>8){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("xm".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
							String xmmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										xmmesq = "";
									}else{
										xmmesq = (String)listj.get(i1);
									}
									xmmesq = xmmesq.replace("\n", "");
									if(xmmesq.length()>18){
										xmmesq = xmmesq.substring(0, 18);
									}
									if(xmmesq.length() <=4 ){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(4 < xmmesq.length() && xmmesq.length() <=8){
										xmmesq = xmmesq.substring(0, 4)+"\\\\r\\\\n"+xmmesq.substring(4, xmmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(xmmesq.length() > 8){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("nljy".equals(string)){
							String endtime = DateUtil.getcurdate();
							List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
							String nlmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										nlmesq = "";
									}else{
										nlmesq = (String)listj.get(i1);
									}
									script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"','"+pagenu+"','"+nlmesq+"');";
									script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"','"+pagenu+"','36');";
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("zzmmjy".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
							String zzmesq = "";
							int Q = 0;
							if(listj.size()!=0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										zzmesq = "";
									}else{
										zzmesq = (String)listj.get(i1);
									}
									zzmesq = zzmesq.replace("\n","");
									if(zzmesq.length()>18){
										zzmesq = zzmesq.substring(0, 18);
									}
									if(zzmesq.length()<=3){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(zzmesq.length() == 4){
										zzmesq = zzmesq.substring(0, 2)+"\\\\r\\\\n"+zzmesq.substring(2, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(zzmesq.length() == 5||zzmesq.length() == 6){
										zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(6 < zzmesq.length() && zzmesq.length() <= 9){
										zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, 5)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','11');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else{
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("gzdwjzw".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
							String mesA = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										mesA = "";
									}else{
										mesA = (String)listj.get(i1);
									}
									mesA = mesA.replace("\n", "");
									if(mesA.length()>100){
										mesA = mesA.substring(0, 100);
									}
									if(mesA.length()<=16){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+mesA+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(16<mesA.length() && mesA.length()<=32){
										mesA = mesA.subSequence(0, 16)+"\\\\r\\\\n"+mesA.subSequence(16, mesA.length());
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else {
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+mesA+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}
									Q++;
									if(Q==7)
										break;
								}
							}	
						}else if("dqyhm".equals(string)){
							UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
							String loginnname=user.getLoginname();
							script += "cell.SetCellString('"+line+"','"+ row +"','"+pagenu+"','"+loginnname+"');";
							script += "cell.SetCellAlign('"+line+"','"+ row +"','"+pagenu+"','36');";
						}
					}
				}
				script += "\n";
			}
		}
		
		script += "cell.ShowSideLabel('0','0');";//�����б�
		script += "cell.ShowTopLabel('0','0');";//�����б�
		this.getPageElement("selectedid").setValue(script);
//		System.out.println(script);
		this.getExecuteSG().addExecuteCode("docellshow()");
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
	//����������ַ�
	private String special(String mess){
		String messa = "";
		if(DBUtil.getDBType()==DBType.MYSQL){
			if("nl".equals(mess)){
				String endtime = DateUtil.getcurdate();
				messa = "nla0107-select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='id'";
			}else if("rzsj".equals(mess)){
//				messa = "a0243-select GROUP_CONCAT(substr(a02.a0243,1,4)||'.'||substr(a02.a0243,5,2)) from a02 a02 where a02.a0000 = 'id'";
				messa = "a0243-select  GROUP_CONCAT(CONCAT_WS('.',substring(a02.a0243, 1, 4),substring(a02.a0243, 5, 2))) from a02 a02 where a02.a0000 = 'id' and a0255 = '1' ";
			}else if("rgzwccsj".equals(mess)){
				messa = "a0288-select CONCAT_WS('.',substr(a01.a0288, 1, 4),substr(a01.a0288, 5, 2)) from a01 a01 where a02.a0000 = 'id'";
			}else if("mzsj".equals(mess)){
				messa = "A0265-select CONCAT_WS('.',substr(a02.a0265, 1, 4),substr(a02.a0265, 5, 2)) from a02 a02 where a02.a0000 = 'id'";
			}else if("tbsjn".equals(mess)){
				messa = "a5323-select CONCAT_WS('.',substr(a53.a5323, 1, 4),substr(a53.a5323, 5, 2)) from a53 a53 where a53.a0000 = 'id'";
			}else if("XGSJ".equals(mess)){
				messa = "XGSJ-select CONCAT_WS('.',substr(a01.XGSJ, 1, 4),substr(a01.XGSJ, 5, 2)) from a01 a01 where a01.a0000 = 'id'";
			}else if("dqsj".equals(mess)){
				messa = "dqsj-select DATE_FORMAT(sysdate,'%Y.%m.%d') from a01 where a0000 = 'id'";
			}else if("a0107-a01.a0107".equals(mess)){
				messa = "a0107-CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2))";
			}else if("a0134-a01.a0134".equals(mess)){
				messa = "a0134-CONCAT_WS('.',substr(a01.a0134, 1, 4),substr(a01.a0134, 5, 2))";
			}
		}else{
			if("nl".equals(mess)){
				String endtime = DateUtil.getcurdate();
				messa = "nla0107-select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='id'";
			}else if("rzsj".equals(mess)){
				messa = "a0243-select wm_concat(substr(a02.a0243,1,4)||'.'||substr(a02.a0243,5,2)) from a02 a02 where a02.a0000 = 'id'";
			}else if("rgzwccsj".equals(mess)){
				messa = "a0288-select substr(a01.a0288,1,4)||'.'||substr(a01.a0288,5,2) from a01 a01 where a02.a0000 = 'id'";
			}else if("mzsj".equals(mess)){
				messa = "A0265-select substr(a02.A0265,1,4)||'.'||substr(a02.A0265,5,2) from a02 a02 where a02.a0000 = 'id'";
			}else if("tbsjn".equals(mess)){
				messa = "a5323-select substr(a53.a5323,1,4)||'.'||substr(a53.a5323,5,2) from a53 a53 where a53.a0000 = 'id'";
			}else if("XGSJ".equals(mess)){
				messa = "XGSJ-select substr(a01.XGSJ,1,4)||'.'||substr(a01.XGSJ,5,2) from a01 a01 where a01.a0000 = 'id'";
			}else if("dqsj".equals(mess)){
				messa = "dqsj-select to_char(sysdate,'yyyy.mm.dd') from a01 where a0000 = 'id'";
			}else if("a0107-a01.a0107".equals(mess)){
				messa = "a0107-substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2)";
			}else if("a0134-a01.a0134".equals(mess)){
				messa = "a0134-substr(a01.a0134, 1, 4) || '.' || substr(a01.a0134, 5, 2)";
			}
		}
		return messa;
	} 
	/**
	 * ��ƴ�ӵ���sql ����ѯ
	 * @param sql    from ǰ���
	 * @param table  ���еı�
	 * @param id     ��Աid
	 * @param where  ����
	 * @param list   ������ѯ����Ϣ��
	 * @return
	 * @throws RadowException
	 */
	public String selectInto(String sqla ,String table,String id,String wherea ,List list,String tpname,int o,String name) throws RadowException{
		String xlxw = "QRZXL,QRZXW,QRZXLXX,QRZXWXX,ZZXL,ZZXW,ZZXLXX,ZZXWXX";
		Map<String,String> map = null;//���ѧ��ѧλ��Ϣ�ϲ���Ԫ��
		if(map == null){
			map = new HashMap<String, String>();
		}
		String script = "";
		String sql = sqla.substring(0, sqla.length()-1);
		String table2 = table.substring(0,table.length()-1);
		String table3 = table.substring(0,3);
		String wher = "";
		if(wherea != null && !"".equals(wherea)){//�ж�ƴ�������ǲ��ǵ��ڿ�
			wher = wherea.substring(0, wherea.length()-1);
		}
		String sqlq = "select "+sql+" from "+table2+" where "+table3+".a0000 = '"+id+"'  "+wher+"";
//		System.out.println(sqlq);
		if(DBUtil.getDBType()==DBType.MYSQL){
			sqlq = sqlq.replace("wm_concat", "GROUP_CONCAT");
		}
		sqlq = sqlq.replace("id",id );
//		List<Object[]> qsmess = HBUtil.getHBSession().createSQLQuery(sqlq).list();
		try {
			ResultSet rs = HBUtil.getHBSession().getSession().connection().prepareStatement(sqlq).executeQuery();
			if(rs != null){
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while(rs.next()){
					for(int a = 1;a<=columnCount;a++){
						String string = rs.getString(a);
						String columnName = metaData.getColumnName(a).toUpperCase();
						String[] messzh = columnName.split("WY");
						String ziduan = messzh[0];
						String line = messzh[1];
						String row = messzh[2];
//						String page = messzh[3];
						int page = o;
						if(xlxw.contains(ziduan)){
							if(string != null && !"".equals(string) && !"null".equals(string)){
								map.put(ziduan+"mess", string.replace("\r\n", "").replace("\r", "").replace("\n", ""));
							}else{
								map.put(ziduan+"mess", string);
							}
							continue;
						}
					
						if(string != null && !"".equals(string) && !"null".equals(string)){
							string = string.replace("\r\n", "").replace("\r", "").replace("\n", "");
							script += intomess(string,line,row,page,ziduan,o,tpname);
						} else {
							script += intomess("",line,row,page,ziduan,o,tpname);
						}
					}
				}
				if(map != null && map.size()>0){
					script += xlxwFunction(map,tpname,o);
				}
				if("�ɲ�����������".equals(tpname)){
					script += world(tpname,o,id);
					script += A53Nrm(tpname,o,id);
				}else if("����������".equals(tpname)){
					script += world(tpname,o,id);
				}else if("����Ա¼�ñ�".equals(tpname)){
					script += world(tpname,o,id);
				}else if("��ȿ��˵ǼǱ�".equals(tpname)){
					String yearMonth = DateUtil.getYearMonth();
					yearMonth = yearMonth.substring(0, 4);
					String mess = "("+yearMonth+"���)";
					script += "cell.SetCellString('1','5', '"+o+"','"+mess+"');";
					script += "cell.SetCellFontSize('1','5','"+o+"','16');";//�����С
					script += "cell.SetCellAlign('1','5', '"+o+"','36');";
				}
				map.clear();
				
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return script;
	}

	private String A53Nrm(String tpname, int o, String id) {
		String script = "";
		/*List<Object[]> qsmess = HBUtil.getHBSession().createSQLQuery("select a53.a5319,a53.a5304,a53.a5317,a53.a5315 from a53 a53 where a53.a0000 = '"+id+"'").list();
		if(qsmess != null && qsmess.size()>0){
			for (Object[] objects : qsmess) {
				String cbdw = (String)objects[0];//�ʱ���λ
				if(cbdw != null && !"null".equals(cbdw) && !"".equals(cbdw)){
					cbdw = cbdw.replace("\r\n", "").replace("\r", "").replace("\n", "");
//					script += "cell.SetCellString('52','77', '"+o+"','"+cbdw+"');";
					String line = "52";
					String row = "77";
					script += zhanjt(line,row,o,cbdw);
				}else{
					script += "cell.SetCellString('52','77', '"+o+"','');";
				}
				String nrzw = (String)objects[1];//����ְ��
				if(nrzw != null && !"null".equals(nrzw) && !"".equals(nrzw)){
					nrzw = nrzw.replace("\r\n", "").replace("\r", "").replace("\n", "");
//					script += "cell.SetCellString('21','17', '"+o+"','"+nrzw+"');";
					String line = "21";
					String row = "17";
					script += zhanjt(line,row,o,nrzw);
				}else{
					script += "cell.SetCellString('21','17', '"+o+"','');";
				}
				String renmly = (String)objects[2];//��������
				if(renmly != null && !"null".equals(renmly) && !"".equals(renmly)){
					renmly = renmly.replace("\r\n", "").replace("\r", "").replace("\n", "");
//					script += "cell.SetCellString('8','53', '"+o+"','"+renmly+"');";
					String line = "8";
					String row = "53";
					script += zhanjt(line,row,o,renmly);
				}else{
					script += "cell.SetCellString('8','53', '"+o+"','');";
				}
				String nmzw = (String)objects[3];//����ְ��
				if(nmzw != null && !"null".equals(nmzw) && !"".equals(nmzw)){
					nmzw = nmzw.replace("\r\n", "").replace("\r", "").replace("\n", "");
//					script += "cell.SetCellString('21','19', '"+o+"','"+nmzw+"');";
					String line = "21";
					String row = "19";
					script += zhanjt(line,row,o,nmzw);
				}else{
					script += "cell.SetCellString('21','19', '"+o+"','');";
				}
			}
		}else{
			script += "cell.SetCellString('52','77', '"+o+"','');";
			script += "cell.SetCellString('21','17', '"+o+"','');";
			script += "cell.SetCellString('8','53', '"+o+"','');";
			script += "cell.SetCellString('21','19', '"+o+"','');";
		}*/
		script += "cell.SetCellString('52','77', '"+o+"','');";
		script += "cell.SetCellString('21','17', '"+o+"','');";
		script += "cell.SetCellString('8','53', '"+o+"','');";
		script += "cell.SetCellString('21','19', '"+o+"','');";
		return script;
	}
	public String zhanjt(String line,String row,int pagenu, String messages){
		String script = "";
		if(messages == null ||"".equals(messages) || "null".equals(messages)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
		}else{
			script += "cell.SetCellString('"+line+"','"+row+"', '"+pagenu+"','"+ messages.replace("\n", "") +"');";
			script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			script += "cell.SetCellAlign('"+line+"','"+row+"',"+pagenu+",33);";
			script+="cell.SetCellTextStyle('"+line+"','"+row+"','"+pagenu+"','2');";//�Զ�����
			script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";
			if(messages.length()>52){
				script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','0','1');";//�����Զ���С
			}
		}
		return script;
	}
	/**
	 * �������ϵ
	 * @param mapso
	 * @param tpname
	 * @param o
	 * @return
	 */
	private String world( String tpname, int o,String id) {
		String script = "";
		String gzdwjzw = "";
		List<Object[]> qsmess = null;
		int cwline = 0;
		int cwrow = 0;
		int xmline = 0;
		int nlline = 0;
		int zzline = 0;
		int gzline = 0;
		if("�ɲ�����������".equals(tpname)){
			cwline = 7;
			cwrow = 59;
			xmline = 16;
			nlline = 27;
			zzline = 31;
			gzline = 39;
			String endtime1 = DateUtil.getcurdate();
			qsmess = HBUtil.getHBSession().createSQLQuery("select a36.A3604A,a36.A3601,GET_BIRTHDAY(a36.a3607,'"+endtime1+"') age, cvb.code_name as zzmm,a36.a3611 from a36 a36,code_value cvb where (cvb.code_type='GB4762' and cvb.code_value=a36.a3627)and a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
		}else if("����������".equals(tpname)){
			cwline = 7;
			cwrow = 54;
			xmline = 16;
			nlline = 27;
			zzline = 35;
			gzline = 45;
			if(DBUtil.getDBType()==DBType.MYSQL){
				//'.',substr(a01.a0288, 1, 4),substr(a01.a0288, 5, 2)
				qsmess = HBUtil.getHBSession().createSQLQuery("select a36.A3604A,a36.A3601,('.',substr(a36.a3607, 1, 4),substr(a36.a3607, 5, 2)) age, cvb.code_name as zzmm,a36.a3611 from a36 a36,code_value cvb where (cvb.code_type='GB4762' and cvb.code_value=a36.a3627)and a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
			}else{
				qsmess = HBUtil.getHBSession().createSQLQuery("select a36.A3604A,a36.A3601,substr(a36.a3607,1,4)||'.'||substr(a36.a3607,5,2) age, cvb.code_name as zzmm,a36.a3611 from a36 a36,code_value cvb where (cvb.code_type='GB4762' and cvb.code_value=a36.a3627)and a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
			}
		}else if("����Ա¼�ñ�".equals(tpname)){
			cwline = 9; 
			cwrow = 38;
			xmline = 21;
			gzline = 33;
			qsmess = HBUtil.getHBSession().createSQLQuery("select a36.A3604A,a36.A3601,a36.a3611 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
		}
		int Q = 0;
		if(qsmess != null && qsmess.size()>0){
			if("����Ա¼�ñ�".equals(tpname)){
				for (Object[] objects : qsmess) {
					//��ν
					String cwmesq = (String)objects[0];
					if(cwmesq == null || "".equals(cwmesq)){
						script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						cwmesq = cwmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
						if(cwmesq.length()>18){
							cwmesq = cwmesq.substring(0, 18);
						}//script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";
						if(cwmesq.length()<=4){
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+cwline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else{
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+cwline+"',"+(cwrow+Q)+", '"+o+"','36');";
							script+="cell.SetCellFontAutoZoom('"+cwline+"','"+(cwrow+Q)+"','"+o+"','1');";
						}
					}
					
					//����
					String xmmesq = (String)objects[1];
					if(xmmesq == null || "".equals(xmmesq)){
						script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						xmmesq = xmmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
						if(xmmesq.length()>18){
							xmmesq = xmmesq.substring(0, 18);
						}
						if(xmmesq.length() <=5 ){
							script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','"+xmmesq+"');";
							script += "cell.SetCellFontSize('"+xmline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+xmline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else{
							script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','"+xmmesq+"');";
							script += "cell.SetCellFontSize('"+xmline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+xmline+"',"+(cwrow+Q)+", '"+o+"','36');";
							script+="cell.SetCellFontAutoZoom('"+xmline+"','"+(cwrow+Q)+"','"+o+"','1');";//�����Զ���С
						}
					}
					//������λ��ְλ
					String mesA = (String)objects[2];
					if(mesA == null ||"".equals(mesA)){
						script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						mesA = mesA.replace("\r\n", "").replace("\r", "").replace("\n", "");
						gzdwjzw = mesA;
						if(mesA.length()>100){
							mesA = mesA.substring(0, 100);
						}
						if(mesA.length()<=19){
							script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
							script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}else {
							script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
							script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script+="cell.SetCellFontAutoZoom('"+gzline+"','"+(cwrow+Q)+"','0','1');";//�����Զ���С
							script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}
					}
					Q = Q+1;
					if(Q==7)
					break;
				}
			}else{
				for (Object[] objects : qsmess) {
					//��ν
					String cwmesq = (String)objects[0];
					if(cwmesq == null || "".equals(cwmesq)){
						script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						cwmesq = cwmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
						if(cwmesq.length()>18){
							cwmesq = cwmesq.substring(0, 18);
						}
						if(cwmesq.length()<=3){
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+cwline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else if(cwmesq.length() == 4){
							cwmesq = cwmesq.substring(0, 2)+"\\\\r\\\\n"+cwmesq.substring(2, cwmesq.length());
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+cwline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else if(cwmesq.length() == 5||cwmesq.length() == 6){
							cwmesq = cwmesq.substring(0, 3)+"\\\\r\\\\n"+cwmesq.substring(3, cwmesq.length());
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+cwline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}else if(cwmesq.length()>8){
							script += "cell.SetCellString('"+cwline+"','"+(cwrow+Q)+"', '"+o+"','"+cwmesq+"');";
							script += "cell.SetCellFontSize('"+cwline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script+="cell.SetCellTextStyle('"+cwline+"','"+(cwrow+Q)+"','"+o+"','2');";//�Զ�����
							script+="cell.SetCellFontAutoZoom('"+cwline+"','"+(cwrow+Q)+"','"+o+"','1');";//�����Զ���С
						}
					}
					
					//����
					String xmmesq = (String)objects[1];
					if(xmmesq == null || "".equals(xmmesq)){
						script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','');";

					}else{
						xmmesq = xmmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
						if(xmmesq.length()>18){
							xmmesq = xmmesq.substring(0, 18);
						}
						if(xmmesq.length() <=4 ){
							script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','"+xmmesq+"');";
							script += "cell.SetCellFontSize('"+xmline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+xmline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else if(4 < xmmesq.length() && xmmesq.length() <=8){
							xmmesq = xmmesq.substring(0, 4)+"\\\\r\\\\n"+xmmesq.substring(4, xmmesq.length());
							script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','"+xmmesq+"');";
							script += "cell.SetCellFontSize('"+xmline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+xmline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}else if(xmmesq.length() > 8){
							script += "cell.SetCellString('"+xmline+"','"+(cwrow+Q)+"', '"+o+"','"+xmmesq+"');";
							script += "cell.SetCellFontSize('"+xmline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script+="cell.SetCellTextStyle('"+xmline+"','"+(cwrow+Q)+"','"+o+"','2');";//�Զ�����
							script+="cell.SetCellFontAutoZoom('"+xmline+"','"+(cwrow+Q)+"','"+o+"','1');";//�����Զ���С
						}
					}
					//������λ��ְλ
					String mesA = (String)objects[4];
					if(mesA == null ||"".equals(mesA)){
						script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						mesA = mesA.replace("\r\n", "").replace("\r", "").replace("\n", "");
						gzdwjzw = mesA;
						if(mesA.length()>100){
							mesA = mesA.substring(0, 100);
						}
						if("�ɲ�����������".equals(tpname)){
							if(mesA.length()<=17){
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}else if(17<mesA.length() && mesA.length()<=33){
								mesA = mesA.subSequence(0, 17)+"\\\\r\\\\n"+mesA.subSequence(17, mesA.length());
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}else {
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script+="cell.SetCellTextStyle('"+gzline+"','"+(cwrow+Q)+"','"+o+"','2');";//�Զ�����
								script+="cell.SetCellFontAutoZoom('"+gzline+"','"+(cwrow+Q)+"','0','1');";//�����Զ���С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}
						}else if("����������".equals(tpname)){
							if(mesA.length()<=10){
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}else if(10<mesA.length() && mesA.length()<=26){
								mesA = mesA.subSequence(0, 10)+"\\\\r\\\\n"+mesA.subSequence(10, mesA.length());
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}else {
								script += "cell.SetCellString('"+gzline+"','"+(cwrow+Q)+"', '"+o+"','"+mesA+"');";
								script += "cell.SetCellFontSize('"+gzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
								script+="cell.SetCellTextStyle('"+gzline+"','"+(cwrow+Q)+"','"+o+"','2');";//�Զ�����
								script+="cell.SetCellFontAutoZoom('"+gzline+"','"+(cwrow+Q)+"','0','1');";//�����Զ���С
								script += "cell.SetCellAlign('"+gzline+"',"+(cwrow+Q)+", '"+o+"','33');";
							}
						}
					}
					//����
					if("�ɲ�����������".equals(tpname)){
						String nlmesq = (String)objects[2];
						if(nlmesq == null || "".equals(nlmesq)){
							script += "cell.SetCellString('"+nlline+"',"+(cwrow+Q)+",'"+o+"','');";
						}else{
							if(gzdwjzw != null && (gzdwjzw.contains("�ѹ�")||gzdwjzw.contains("��ȥ��")||gzdwjzw.contains("����")||gzdwjzw.contains("����"))||gzdwjzw.contains("����")){
								nlmesq = "";//��������ѹ�,��ȥ��,����,����,���������䲻��ʾ
							}
							script += "cell.SetCellString('"+nlline+"',"+(cwrow+Q)+",'"+o+"','"+nlmesq+"');";
							script += "cell.SetCellAlign('"+nlline+"',"+(cwrow+Q)+",'"+o+"','36');";
						}
					}else if("����������".equals(tpname)){
						String nlmesq = (String)objects[2];
						if(nlmesq == null || "".equals(nlmesq)){
							script += "cell.SetCellString('"+nlline+"',"+(cwrow+Q)+",'"+o+"','');";
						}else{
							script += "cell.SetCellString('"+nlline+"',"+(cwrow+Q)+",'"+o+"','"+nlmesq+"');";
							script += "cell.SetCellFontSize('"+nlline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+nlline+"',"+(cwrow+Q)+",'"+o+"','36');";
						}
					}
					//������ò 
					String zzmesq = (String)objects[3];
					if(zzmesq == null || "".equals(zzmesq)){
						script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','');";
					}else{
						zzmesq = zzmesq.replace("\r\n", "").replace("\r", "").replace("\n", "");
						if(zzmesq.length()>18){
							zzmesq = zzmesq.substring(0, 18);
						}
						if(zzmesq.length()<=3){
							script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','"+zzmesq+"');";
							script += "cell.SetCellFontSize('"+zzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+zzline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else if(zzmesq.length() == 4){
							zzmesq = zzmesq.substring(0, 2)+"\\\\r\\\\n"+zzmesq.substring(2, zzmesq.length());
							script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','"+zzmesq+"');";
							script += "cell.SetCellFontSize('"+zzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+zzline+"',"+(cwrow+Q)+", '"+o+"','36');";
						}else if(zzmesq.length() == 5||zzmesq.length() == 6){
							zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
							script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','"+zzmesq+"');";
							script += "cell.SetCellFontSize('"+zzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script += "cell.SetCellAlign('"+zzline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}else if(6 < zzmesq.length() && zzmesq.length() <= 9){
							zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, 5)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
							script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','"+zzmesq+"');";
							script += "cell.SetCellFontSize('"+zzline+"','"+(cwrow+Q)+"','"+o+"','11');";//�����С
							script += "cell.SetCellAlign('"+zzline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}else{
							script += "cell.SetCellString('"+zzline+"','"+(cwrow+Q)+"', '"+o+"','"+zzmesq+"');";
							script += "cell.SetCellFontSize('"+zzline+"','"+(cwrow+Q)+"','"+o+"','14');";//�����С
							script+="cell.SetCellTextStyle('"+zzline+"','"+(cwrow+Q)+"','"+o+"','2');";//�Զ�����
							script+="cell.SetCellFontAutoZoom('"+zzline+"','"+(cwrow+Q)+"','"+o+"','1');";//�����Զ���С
							script += "cell.SetCellAlign('"+zzline+"',"+(cwrow+Q)+", '"+o+"','33');";
						}
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
			
		}else{
			if("����Ա¼�ñ�".equals(tpname)){
				script += "cell.SetCellString('9','37', '"+o+"','');";
				script += "cell.SetCellString('21','37', '"+o+"','');";
				script += "cell.SetCellString('33','37', '"+o+"','');";
			}else{
				script += "cell.SetCellString('7','59', '"+o+"','');";
				script += "cell.SetCellString('16','59', '"+o+"','');";
				script += "cell.SetCellString('27','59', '"+o+"','');";
				script += "cell.SetCellString('31','59', '"+o+"','');";
				script += "cell.SetCellString('39','59', '"+o+"','');";
			}
		}
		return script;
	}
	/**
	 * ����ѧ��ѧ����
	 * @param map
	 * @param name
	 * @param o
	 * @return
	 */
	private String xlxwFunction(Map<String, String> map,String name,int o ) {
//		String xlxw = "QRZXL,QRZXW,QRZXLXX,QRZXWXX,ZZXL,ZZXW,ZZXLXX,ZZXWXX";
		String script = "";
		int QRZXLLINE = 0;
		int QRZXLROW = 0;
		int QRZXWENDLINE = 0;
		int QRZXWENDROW = 0;
		int QRZXWLINE = 0;
		int QRZXWROW = 0;
		int QRZXLXXLINE = 0;
		int QRZXLXXROW = 0;
		int QRZXWXXENDLINE = 0;
		int QRZXWXXENDROW = 0;
		int QRZXWXXLINE = 0;
		int QRZXWXXROW = 0;
		
		int ZZXLLINE = 0;
		int ZZXLROW = 0;
		int ZZXWENDLINE = 0;
		int ZZXWENDROW = 0;
		int ZZXWLINE = 0;
		int ZZXWROW = 0;
		int ZZXLXXLINE = 0;
		int ZZXLXXROW = 0;
		int ZZXWXXENDLINE = 0;
		int ZZXWXXENDROW = 0;
		int ZZXWXXLINE = 0;
		int ZZXWXXROW = 0;
		if("���յǼǱ�".equals(name) || "����Ա�ǼǱ�".equals(name)){
			QRZXLLINE = 20;
			QRZXLROW = 14;
			QRZXWENDLINE = 41;
			QRZXWENDROW = 15;
			QRZXWLINE = 20;
			QRZXWROW = 15;
			QRZXLXXLINE = 53;
			QRZXLXXROW = 14;
			QRZXWXXENDLINE = 80;
			QRZXWXXENDROW = 15;
			QRZXWXXLINE = 53;
			QRZXWXXROW = 15;
			
			ZZXLLINE = 20;
			ZZXLROW = 16;
			ZZXWENDLINE = 41;
			ZZXWENDROW = 17;
			ZZXWLINE = 20;
			ZZXWROW = 17;
			ZZXLXXLINE = 53;
			ZZXLXXROW = 16;
			ZZXWXXENDLINE = 80;
			ZZXWXXENDROW = 17;
			ZZXWXXLINE = 53;
			ZZXWXXROW = 17;
			
		}else if("�ɲ�����������".equals(name)||"����������".equals(name)){
			QRZXLLINE = 21;
			QRZXLROW = 11;
			QRZXWENDLINE = 41;
			QRZXWENDROW = 12;
			QRZXWLINE = 21;
			QRZXWROW = 12;
			QRZXLXXLINE = 53;
			QRZXLXXROW = 11;
			QRZXWXXENDLINE = 82;
			QRZXWXXENDROW = 12;
			QRZXWXXLINE = 53;
			QRZXWXXROW = 12;
			
			ZZXLLINE = 21;
			ZZXLROW = 13;
			ZZXWENDLINE = 41;
			ZZXWENDROW = 14;
			ZZXWLINE = 21;
			ZZXWROW = 14;
			ZZXLXXLINE = 53;
			ZZXLXXROW = 13;
			ZZXWXXENDLINE = 82;
			ZZXWXXENDROW = 14;
			ZZXWXXLINE = 53;
			ZZXWXXROW = 14;
			
		}else if("����Ա¼�ñ�".equals(name)){
			QRZXLLINE = 21;
			QRZXLROW = 13;
			QRZXWENDLINE = 40;
			QRZXWENDROW = 14;
			QRZXWLINE = 21;
			QRZXWROW = 14;
			QRZXLXXLINE = 53;
			QRZXLXXROW = 13;
			QRZXWXXENDLINE = 82;
			QRZXWXXENDROW = 14;
			QRZXWXXLINE = 53;
			QRZXWXXROW = 14;
			
			ZZXLLINE = 21;
			ZZXLROW = 15;
			ZZXWENDLINE = 40;
			ZZXWENDROW = 16;
			ZZXWLINE = 21;
			ZZXWROW = 16;
			ZZXLXXLINE = 53;
			ZZXLXXROW = 15;
			ZZXWXXENDLINE = 82;
			ZZXWXXENDROW = 16;
			ZZXWXXLINE = 53;
			ZZXWXXROW = 16;
		}
		String QRZXL = map.get("QRZXLmess");//ȫ����ѧ��
		String QRZXW = map.get("QRZXWmess");//ȫ����ѧλ
		if(QRZXL != null && !"".equals(QRZXL) && QRZXW != null && !"".equals(QRZXW) && QRZXW.equals(QRZXL)){
			QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			QRZXW = QRZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLLINE+"','"+QRZXLROW+"','"+QRZXWENDLINE+"','"+QRZXWENDROW+"');";
			script += xlxw(QRZXLLINE,QRZXLROW,QRZXL,"1",o);//��ͬ
		}else if(QRZXL != null && !"".equals(QRZXL) && QRZXW != null && !"".equals(QRZXW) && !QRZXW.equals(QRZXL)){
			QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			QRZXW = QRZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += xlxw(QRZXLLINE,QRZXLROW,QRZXL,"1",o);//ѧ��ѧλ��ͬ
			script += xlxw(QRZXWLINE,QRZXWROW,QRZXW,"1",o);
		}else if(QRZXL != null && !"".equals(QRZXL) && !QRZXL.equals(QRZXW) && (QRZXW == null||"".equals(QRZXW)||"null".equals(QRZXW))){
			QRZXL = QRZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLLINE+"','"+QRZXLROW+"','"+QRZXWENDLINE+"','"+QRZXWENDROW+"');";
			script += xlxw(QRZXLLINE,QRZXLROW,QRZXL,"1",o);
		}else if(QRZXW != null && !"".equals(QRZXW) && !QRZXW.equals(QRZXL)&&(QRZXL == null||"".equals(QRZXL)||"null".equals(QRZXL))){
			QRZXW = QRZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLLINE+"','"+QRZXLROW+"','"+QRZXWENDLINE+"','"+QRZXWENDROW+"');";
			script += xlxw(QRZXWLINE,QRZXWROW,QRZXW,"1",o);
		}else{
			script+="cell.SetCellString('"+QRZXLLINE+"','"+QRZXLROW+"','"+o+"','');";
			script+="cell.SetCellString('"+QRZXWLINE+"','"+QRZXWROW+"','"+o+"','');";
		}
		String QRZXLXX = map.get("QRZXLXXmess");//ȫ����ѧλ��Ϣ
		String QRZXWXX = map.get("QRZXWXXmess");//ȫ����ѧλ��Ϣ
		if(QRZXLXX != null && !"".equals(QRZXLXX) && QRZXWXX != null && !"".equals(QRZXWXX) && QRZXLXX.equals(QRZXWXX)){
			QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			QRZXWXX = QRZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLXXLINE+"','"+QRZXLXXROW+"','"+QRZXWXXENDLINE+"','"+QRZXWXXENDROW+"');";
			script += xlxw(QRZXLXXLINE,QRZXLXXROW,QRZXLXX,"2",o);
		}else if(QRZXLXX != null && !"".equals(QRZXLXX) && QRZXWXX != null && !"".equals(QRZXWXX) && !QRZXLXX.equals(QRZXWXX)){
			QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			QRZXWXX = QRZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += xlxw(QRZXLXXLINE,QRZXLXXROW,QRZXLXX,"2",o);
			script += xlxw(QRZXWXXLINE,QRZXWXXROW,QRZXWXX,"2",o);
		}else if(QRZXLXX != null && !"".equals(QRZXLXX) && !QRZXLXX.equals(QRZXWXX)&&(QRZXWXX == null || "".equals(QRZXWXX) || "null".equals(QRZXWXX))){
			QRZXLXX = QRZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLXXLINE+"','"+QRZXLXXROW+"','"+QRZXWXXENDLINE+"','"+QRZXWXXENDROW+"');";
			script += xlxw(QRZXLXXLINE,QRZXLXXROW,QRZXLXX,"2",o);
		}else if(QRZXWXX != null && !"".equals(QRZXWXX) && !QRZXWXX.equals(QRZXLXX)&&(QRZXLXX == null || "".equals(QRZXLXX) && "null".equals(QRZXLXX))){
			QRZXWXX = QRZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+QRZXLXXLINE+"','"+QRZXLXXROW+"','"+QRZXWXXENDLINE+"','"+QRZXWXXENDROW+"');";
			script += xlxw(QRZXLXXLINE,QRZXLXXROW,QRZXWXX,"2",o);
		}else{
			script+="cell.SetCellString('"+QRZXLXXLINE+"','"+QRZXLXXROW+"','"+o+"','');";
			script+="cell.SetCellString('"+QRZXWXXLINE+"','"+QRZXWXXROW+"','"+o+"','');";
		}
		String ZZXL = map.get("ZZXLmess");//ȫ����ѧ��
		String ZZXW = map.get("ZZXWmess");//ȫ����ѧ��
		if(ZZXL != null && !"".equals(ZZXL) && ZZXW != null && !"".equals(ZZXW) && ZZXL.equals(ZZXW)){
			ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			ZZXW = ZZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLLINE+"','"+ZZXLROW+"','"+ZZXWENDLINE+"','"+ZZXWENDROW+"');";
			script += xlxw(ZZXLLINE,ZZXLROW,ZZXL,"1",o);
		}else if(ZZXL != null && !"".equals(ZZXL) && ZZXW != null && !"".equals(ZZXW) && !ZZXL.equals(ZZXW)){
			ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			ZZXW = ZZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += xlxw(ZZXLLINE,ZZXLROW,ZZXL,"1",o);
			script += xlxw(ZZXWLINE,ZZXWROW,ZZXW,"1",o);
		}else if(ZZXL != null && !"".equals(ZZXL) && !ZZXL.equals(ZZXW)&&(ZZXW == null || "".equals(ZZXW) || "null".equals(ZZXW))){
			ZZXL = ZZXL.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLLINE+"','"+ZZXLROW+"','"+ZZXWENDLINE+"','"+ZZXWENDROW+"');";
			script += xlxw(ZZXLLINE,ZZXLROW,ZZXL,"1",o);
		}else if(ZZXW != null && !"".equals(ZZXW) && !ZZXW.equals(ZZXL)&&(ZZXL == null || "".equals(ZZXL) || "null".equals(ZZXL))){
			ZZXW = ZZXW.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLLINE+"','"+ZZXLROW+"','"+ZZXWENDLINE+"','"+ZZXWENDROW+"');";
			script += xlxw(ZZXLLINE,ZZXLROW,ZZXW,"1",o);
		}else{
			script+="cell.SetCellString('"+ZZXLLINE+"','"+ZZXLROW+"','"+o+"','');";
			script+="cell.SetCellString('"+ZZXWLINE+"','"+ZZXWROW+"','"+o+"','');";
		}
		String ZZXLXX = map.get("ZZXLXXmess");//ȫ����ѧλ��Ϣ
		String ZZXWXX = map.get("ZZXWXXmess");;//ȫ����ѧλ��Ϣ
		if(ZZXLXX != null && !"".equals(ZZXLXX) && ZZXWXX != null && !"".equals(ZZXWXX) && ZZXLXX.equals(ZZXWXX)){
			ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			ZZXWXX = ZZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLXXLINE+"','"+ZZXLXXROW+"','"+ZZXWXXENDLINE+"','"+ZZXWXXENDROW+"');";
			script += xlxw(ZZXLXXLINE,ZZXLXXROW,ZZXLXX,"2",o);
		}else if(ZZXLXX != null && !"".equals(ZZXLXX) && ZZXWXX != null && !"".equals(ZZXWXX) && !ZZXLXX.equals(ZZXWXX)){
			ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			ZZXWXX = ZZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script += xlxw(ZZXLXXLINE,ZZXLXXROW,ZZXLXX,"2",o);
			script += xlxw(ZZXWXXLINE,ZZXWXXROW,ZZXWXX,"2",o);
		}else if(ZZXLXX != null && !"".equals(ZZXLXX) && !ZZXLXX.equals(ZZXWXX)&&(ZZXWXX == null || "".equals(ZZXWXX) || "null".equals(ZZXWXX))){
			ZZXLXX = ZZXLXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLXXLINE+"','"+ZZXLXXROW+"','"+ZZXWXXENDLINE+"','"+ZZXWXXENDROW+"');";
			script += xlxw(ZZXLXXLINE,ZZXLXXROW,ZZXLXX,"2",o);
		}else if(ZZXWXX != null && !"".equals(ZZXWXX) && !ZZXWXX.equals(ZZXLXX)&&(ZZXLXX == null || "".equals(ZZXLXX) || "null".equals(ZZXLXX))){
			ZZXWXX = ZZXWXX.replace("\r\n", "").replace("\r", "").replace("\n", "");
			script += "cell.setCurSheet("+o+");";//��ȡ��һҳ�ķ���";
			script+="cell.MergeCells('"+ZZXLXXLINE+"','"+ZZXLXXROW+"','"+ZZXWXXENDLINE+"','"+ZZXWXXENDROW+"');";
			script += xlxw(ZZXLXXLINE,ZZXLXXROW,ZZXWXX,"2",o);
		}else{
			script+="cell.SetCellString('"+ZZXLXXLINE+"','"+ZZXLXXROW+"','"+o+"','');";
			script+="cell.SetCellString('"+ZZXWXXLINE+"','"+ZZXWXXROW+"','"+o+"','');";
		}
		return script;
	}
	
	
	
	/**
	 * �ѻ�ȡ������Ϣ���뵥Ԫ��
	 * @param mess     ��Ϣ
	 * @param line     �к�
	 * @param row	   �к�
	 * @param pagenu   ҳ��
	 * @param ziduan   ƥ�����ָ��ӽ��и�ʽ��
	 */
	private String intomess(String mess,String line,String row,int pagenu,String ziduan,int o,String name){
		String jiben = "A0101,A0117,A0111A,A0114A,A0128,A0141,A0221,A0120,A0801A,A0901A";//������Ϣ��Ҫ�����ָ�����Ҫ�����
		String zhijie = "A0104,A0134,A0107,A0184,A0243,A0215A,A0197";//��������ֱ�Ӳ������Ϣ
		String nirmxx = "A0192A,A5304,A5315,A5319,A5317";//��������Ϣ
		String buchuli = "A0201A";
		String script = "";//ƴ���ַ�����
		 if("A0101".equals(ziduan)){
			script += "cell.setCurSheet('"+o+"');";//�����С
			script += "cell.SetSheetLabel('"+o+"','"+mess+"');";//�����С
		 }
		if("NLA0107".equals(ziduan)){
			String mesn = "("+mess+"��)";
			script += "cell.SetCellString('"+line+"','"+row+"','"+o+"','"+ mesn +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','"+o+"','36');";//36ˮƽ���д�ֱ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','"+o+"','14');";//�����С
		} else if(jiben.contains(ziduan)){
			script += fountsize(line,row,pagenu,mess);
		}else if(zhijie.contains(ziduan)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+o+"','"+ mess +"');";
			script += "cell.SetCellAlign('"+line+"','"+row+"','"+o+"','36');";//36ˮƽ���д�ֱ����
			script += "cell.SetCellFontSize('"+line+"','"+row+"','"+o+"','14');";//�����С
		}else if(buchuli.contains(ziduan)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+o+"','"+ mess +"');";
			if("����Ա¼�ñ�".equals(name)){
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+o+"','33');";//36ˮƽ���д�ֱ����
			}else{
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+o+"','36');";//36ˮƽ���д�ֱ����
			}
			script += "cell.SetCellFontSize('"+line+"','"+row+"','"+o+"','14');";//�����С
		}else if(ziduan.equals("A0196") || ziduan.equals("A0187A")){
			script += getInfo(line,row,pagenu,mess,ziduan);
		}else if(nirmxx.contains(ziduan)){
			script += zwandnrm(line,row,pagenu,mess);
		}else if("A14Z101".equals(ziduan)){//�������
			mess  = mess.replace("\n","");
			if(mess != null && !"".equals(mess)){
				if(mess.length() > 552){
					mess = mess.substring(0, 552);
				}
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ mess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";
				script+="cell.SetCellTextStyle('"+line+"','"+row+"','"+pagenu+"','2');";//�Զ�����
				if(mess.length() > 124 ){
					script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";//�����Զ���С
				}
			}else{
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
			}
		}else if("A15Z101".equals(ziduan)){
			mess  = mess.replace("\n","");
			if(mess != null && !"".equals(mess)){
				if(mess.length() > 414){
					mess = mess.substring(0, 414);
				}
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ mess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";
				script+="cell.SetCellTextStyle('"+line+"','"+row+"','"+pagenu+"','2');";//�Զ�����
				if(mess.length() > 93 ){
					script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";//�����Զ���С
				}
			}else{
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
			}
		}
		return script ;
		
	}
	
	
	/**
	 * ����ְ������ְ������ְ�񣬳ʱ���λ
	 * @param line
	 * @param row
	 * @param messages
	 * @return
	 */
	public String zwandnrm(String line,String row,int pagenu, String messages){
		String script = "";
		if(messages == null ||"".equals(messages) || "null".equals(messages)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
		}else{
			script += "cell.SetCellString('"+line+"','"+row+"', '"+pagenu+"','"+ messages.replace("\n", "") +"');";
			script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			script += "cell.SetCellAlign('"+line+"','"+row+"',"+pagenu+",33);";
			script+="cell.SetCellTextStyle('"+line+"','"+row+"','"+pagenu+"','2');";//�Զ�����
			script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";
			if(messages.length()>52){
				script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','0','1');";//�����Զ���С
			}
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
	public String xlxw(int line,int row,String xlxu,String type,int o){
		String script = "";
		int number = 0;//��������
		if("1".equals(type)){
			number = 7;
		}else{
			number = 11;
		}
		script+="cell.SetCellString('"+line+"','"+row+"','"+o+"','"+xlxu+"');";
		script += "cell.SetCellFontSize('"+line+"','"+row+"','"+o+"','14');";//�����С
		if(xlxu.length() <= number){
			if("2".equals(type)){
				script+="cell.SetCellAlign('"+line+"','"+row+"','"+o+"','33');";
			}else{
				script+="cell.SetCellAlign('"+line+"','"+row+"','"+o+"','36');";
			}
		}else{
			script+="cell.SetCellTextStyle('"+line+"','"+row+"','"+o+"','2');";//�Զ�����
			script+="cell.SetCellAlign('"+line+"','"+row+"','"+o+"','33');";//���䷽ʽ
			script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+o+"','1');";//�����Զ���С
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
	public String getInfo(String line,String row,int pagenu,String messages,String ziduan){
		String script = "";
		int rownumma =  0;//ÿ�е�����
		int rownummb =  0;//ÿ�е�����
		if("A0196".equals(ziduan)){
			rownumma = 8;
			rownummb = 16;
		}else{
			rownumma = 9;
			rownummb = 18;
		}
		if(messages == null ||"".equals(messages) || "null".equals(messages)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
		}else{
			if(messages.length() <= rownumma){
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ messages +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			}else if(rownumma < messages.length() && messages.length() <= rownummb ){
				String infomess = "";
				infomess = messages.substring(0, rownumma)+"\\\\r\\\\n"+messages.substring(rownumma,  messages.length());
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ infomess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			}else if(rownummb < messages.length()){
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ messages +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
//				script += "cell.SetCellFontSize('"+line+"','"+row+"','0',14);";//�����С
				script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";//�����Զ���С
			}else{}
		}
		return script;
	}
	//����18���ֵĵ�Ԫ���ͳһ����
	public String fountsize(String line,String row,int pagenu,String messages){
		String script = "";
		if(messages == null ||"".equals(messages) || "null".equals(messages)){
			script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','');";
		}else{
			if(messages.length()<=4){
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ messages +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			}else if(4 < messages.length() && messages.length() <= 8 ){
				String infomess = "";
				infomess = messages.substring(0, 4)+"\\\\r\\\\n"+messages.substring(4,  messages.length());
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ infomess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
			}else if(8 < messages.length() && messages.length() <= 10){
				String infomess = "";
				infomess = messages.substring(0, 5)+"\\\\r\\\\n"+messages.substring(5,  messages.length());
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ infomess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','12');";//�����С
			}else if(10 < messages.length() && messages.length() <= 15){
				String infomess = "";
				infomess = messages.substring(0, 5)+"\\\\r\\\\n"+messages.substring(5,10)+"\\\\r\\\\n"+messages.substring(10,messages.length());
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ infomess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','11');";//�����С
			}else if(15 < messages.length() && messages.length() <= 18){
				String infomess = "";
				infomess = messages.substring(0, 6)+"\\\\r\\\\n"+messages.substring(6,12)+"\\\\r\\\\n"+messages.substring(12,messages.length());
				script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ infomess +"');";
				script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','33');";//36ˮƽ���д�ֱ���У�33�����ˮƽ����
				script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','10');";//�����С
			}else{}
		}
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
	//������ʽ�����������ڵ�
	private int parseJL2(String line2, StringBuffer jlsb,int rowsoze){
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
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("openExpPathWin")
	 public int openExpPathWin() throws RadowException{
		this.setRadow_parent_data("ExpPathWin"); 
		this.openWindow("ExpPathWin", "pages.publicServantManage.PDFExpPath");
		return EventRtnType.NORMAL_SUCCESS;
	 }
	/**
	 * ��������
	 * @return
	 * @throws RadowException
	 * @throws SQLException 
	 */
	@PageEvent("muchtoo")
	public int muchtoo() throws RadowException, SQLException{
		String script = "";
		String id = "";
		String idall = "";
		//�ò����Ǵ�customquerypagemodel��д�����Ϣ
		String idall1 = (String)request.getSession().getAttribute("personidall");
		String idall2 = (String)request.getSession().getAttribute("personidy");
		if(idall1 != null && !"".equals(idall1)&&!"null".equals(idall1)){
			idall = idall1;
		}else if(idall2 != null && !"".equals(idall2)&&!"null".equals(idall2)){
			idall = idall2;
		}
//		request.getSession().removeAttribute("personidy");
		String tpid = this.getPageElement("subWinIdBussessId").getValue();
		String idc = "40e9b81c-5a53-445f-a027-6e00a9f6,3de1c725-d71b-476a-b87c-6c8d2184";
		if(idc.contains(tpid)){//�����Ƿ�Ϊ������
			
		}else{
			String society = "cw,xm,nljy,zzmmjy,gzdwjzw";
			String tpname = "";
			String tptype = "";
			script += "var cell = document.getElementById('cellweb1');\n";
			script += "var path = getPath();\n";
			script += "cell.WorkbookReadonly=true;\n";//���ò��ɱ༭
			try {
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("select TPName,TPID,TPType from listoutput2 where TPID = '"+tpid+"' group by TPName,TPID,TPType").executeQuery();
				while(rs.next()){
					tpname = rs.getString(1);
					tptype = rs.getString(3);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if("1".equals(tptype)){
				script += "var  aa = cell.openfile(ctpath+'/template/'+pinyin.getCamelChars('"+tpname+"').replace('��','').replace('��','').replace('��','').replace('��','')+'.cll','');\n";
			}else{
//				script += "cell.openfile(ctpath+'/template/'+'"+tpid+"'+'.cll','');\n";
			}
			StringBuffer sf = new StringBuffer();
			String sql = "select messagee,zbline,zbrow,pagenu from listoutput2 where tpid = '"+tpid+"'";
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			String tabel = "";//��¼����
			String tabelwher = "";//����
			int a = 1;
			Map<String,String> maps = null;
			List<Map<String,String>> lists = null;//��ŵ�������ѯ����Ϣ��
	 		String mosaic = "";
	 		String nrm = "a5319-a53.a5319,a5304-a53.a5304,a5317-a53.a5317,a5315-a53.a5315";//������
			String aa = "nl,rzsj,rgzwccsj,mzsj,tbsjn,XGSJ,dqsj,a0107-a01.a0107,a0134-a01.a0134";
			for (Object[] obj : list) {
				String mess = (String)obj[0];
				String messline = (String)obj[1];
				String messrow = (String)obj[2];
				int pagenuu = (Integer.parseInt((String)obj[3])-1);
				String pagenu = pagenuu+"";
				if(mess != null &&("zp".equals(mess) ||"rdsj".equals(mess) ||"jl".equals(mess)
						|| "cw".equals(mess)||"xm".equals(mess)||"csnyjy".equals(mess)
						|| "nljy".equals(mess)||"zzmmjy".equals(mess)||"gzdwjzw".equals(mess) 
						|| "dqyhm".equals(mess))|| "a5319-a53.a5319".equals(mess)|| "a5304-a53.a5304".equals(mess)|| "a5317-a53.a5317".equals(mess)|| "a5315-a53.a5315".equals(mess)){//��������ѯ��
					if(society.contains(mess)){
						continue;
					}
					if(nrm.contains(mess)){//��������Ϣ������ѯ
						continue;
					}
					if(maps ==null){
						maps = new HashMap<String, String>();
					}
					if(lists == null){
						lists = new ArrayList<Map<String,String>>();
					}
					mosaic = "wy"+messline+"wy"+messrow+"wy"+pagenu; //ƴ�ӵ���Ϣ�����к�ҳ��
					maps.put(mess, mosaic);
					lists.add(maps);
				}else{
					if(aa.contains(mess)){//����дsql��
						 mess = special(mess);
						}
					String[] split = mess.split("-");
					mess = split[1];
					if(mess.contains("select")){
						mess = "("+mess+")";
					}else{
						if(mess != null && !mess.contains("\'.\'")){//���˳�������
							String tabel2 = mess.substring(0, 3);
							if(!tabel.contains(tabel2)){//��ȡ��
								if(a%2 != 0){
									tabelwher += " and "+ tabel2+".a0000 = ";
								}else{
									tabelwher += tabel2+".a0000,";
								}
								a++;
								tabel += tabel2+" "+tabel2+ ",";
							}
						}
					}
					mosaic = mess +" as "+split[0]+"wy"+messline+"wy"+messrow+"wy"+pagenu+","; 
					sf.append(mosaic);
				}
			}
			if(a==2)tabelwher = "";
			idall = idall.replace("|", "").replace("@", ",");
			String[] allid = idall.split(",");
//			String id = idss[0];
			int lv = 1;
			for(int oo = 0;oo<(allid.length-1);oo++){
				script += "cell.InsertSheet('"+lv+"','1');";
				script += "cell.CopySheet('"+lv+"','0');";
				lv++;
			}
			for(int o = 0;o<allid.length;o++){
				id = (String) allid[o];
				script += selectInto(sf.toString(), tabel, id, tabelwher, lists,tpname,o,tpname);//���в�ѯ
				//��Ҫ���⴦��Ͷ�����Ϣ�ĵ�����ѯ
				if(lists != null && lists.size()>0){
					for(int b = 0;b<lists.size();b++){
						Map<String, String> map = lists.get(b);
						Set<String> keySet = map.keySet();
						Object[] array = keySet.toArray();
						String string = (String)array[b];//��ȡ��key
						String string2 = map.get(string);
						String[] split = string2.split("wy");
						String line = split[1];
//						String row = (String)split[2];
						String row = (String)split[2];
						int rowi = Integer.valueOf(split[2]);
//						String pagenu = split[3];
						int pagenu = o;
						if("zp".equals(string)){
							String mes = "";
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
							if(listj != null && listj.size() ==0){
								mes = "<����Ƭ>";
								script+="cell.SetCellString('"+line+"','"+row+"', '"+pagenu+"','"+mes+"');";
								script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
								script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";
							}else{
								Object[] sz = listj.get(0);
								Object photopath = sz[0];
								Object photoname = sz[1];
								String ptpath = photopath.toString().toUpperCase();
								//��������
								//2017.04.19 yinl �޸�ͼƬ��ַ
								String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
								script+="cell.SetCellImage('"+line+"','"+row+"','"+pagenu+"',cell.AddImage('"+imagepath+"'),'1','1','1');";
							}
						}else if("rdsj".equals(string)){
//							List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select a01.a0101,a01.a0104,cv.code_name,a01.a0111a,a01.a0114a,a01.a0134,a01.a0128,a01.a0196,a01.a0187a,a01.a0192a,a01.a0107,a01.a0140,a01.a14z101,a01.a15z101 from a01 a01,code_value cv where a01.a0117 = cv.code_value  and cv.code_type =  'GB3304' and a01.a0000 = '8AF1B8D5-2ADA-48C2-A8D9-00BA9D5119B8'").list();
							List list2 = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.a0000 = '"+id+"'").list();
							String messages  = (String)list2.get(0);//�뵳ʱ��---------------------------------------------
							if(messages != null && !"".equals(messages)){
//								int length = 0;
								String mes = "";
								if(messages.contains("(")&&messages.contains(")")&& messages.contains(".")){
									int length = messages.length();
									int indexOf = messages.indexOf("(");
									String sub = messages.substring(indexOf, length);
									mes = messages.substring(0, indexOf)+"\\\\r\\\\n"+sub;
								}else if(messages.contains("(")&&messages.contains(")") && !messages.contains(".")){
									mes = messages;
								}else if(messages.length()>=6){
									mes = messages.replace(".", "");
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}else{
									mes = messages;
								}
								script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+ mes +"');";
								script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','14');";//�����С
								script += "cell.SetCellAlign('"+line+"','"+row+"','"+pagenu+"','36');";
							}
						}else if("jl".equals(string)){
							ResultSet rs;
							try {
								rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
								int t = rs.getRow();
								if(t==0){
									script += "cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+"".replace("\n", "\\\\r\\\\n")+"');";
									script += "cell.SetCellNumType('"+line+"','"+row+"','"+pagenu+"','0');";
								}
								//rs.previous();
								String mes = "";
								while(rs.next()){
									int length = 0;  
									int rowsoze = 0;//��ͬ������ÿ����ʾ��������ͬ��
									int zihao = 0;//�ֺ�
									String jianadd = "";
									StringBuffer pinjieJLgs = new StringBuffer("");
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
										if(countsize <=525 && countrow <=25){
											rowsoze = 21; //14���ֿ�д25��ÿ��21����,����525���֡�
										}else if((countsize<=810) && (25<countrow &&countrow <=30)){
											rowsoze = 26;//12���ֿɴ�30�У�ÿ��д27���ֹ�810����
										}else if((countsize<928)&&(30<countrow && countrow<=32)){
											rowsoze = 28;//11���ֿɴ�32��ÿ��29���� ��928���֡�
										}else{
											rowsoze = 35;//��ʱ��д
										}
//										mes = formatJL2(rs.getString(1),countsize);
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
//										        		if(!"".equals(jianlimes)){
										        		if(j==1){
										        			if(length<0){
										        				length = -length;
										        			}
										        			parseJLto(pinjieJLgs,rowsoze,jianlimes.replace("\r\n", "").replace("\r", "").replace("\n", ""),length);
										        			j = 0;
										        			length = 0;
										        		}
										        		if(length > 0){
//										        			type = parseJLto(pinjieJLgs,rowsoze,jianlimes,length);
										        			pinjieJLgs.append("\r\n");
										        		}
										        		length = 0;
										        		pinjieJLgs.append(line1).append("  ");
										        		String line2 = jl.substring(line1.length()).trim();
										        		length = parseJL(line2.replace("\r\n", "").replace("\\r\\n", ""), pinjieJLgs,rowsoze,type,length);
										        		jianlimes  = "";
										        	}
										        }else{
//										        	parseJL2(jl, pinjieJLgs,rowsoze);
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
									}
									script+="cell.SetCellString('"+line+"','"+row+"','"+pagenu+"','"+pinjieJLgs.toString().replace("\n", "\\\\r\\\\n")+"');";
									script+="cell.SetCellNumType('"+line+"','"+row+"','"+pagenu+"','0');";
									script += "cell.SetCellFontSize('"+line+"','"+row+"','"+pagenu+"','"+zihaoto+"');";//�����С
								}
								//���������Զ���С
								int length = mes.length();
								if(mes.length()>1044){
									script+="cell.SetCellFontAutoZoom('"+line+"','"+row+"','"+pagenu+"','1');";
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}else if("cw".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
							String cwmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										cwmesq = "";
									}else{
										cwmesq = (String)listj.get(i1);
									}
									cwmesq = cwmesq.replace("\n", "");
									if(cwmesq.length()>18){
										cwmesq = cwmesq.substring(0, 18);
									}
									if(cwmesq.length()<=3){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(cwmesq.length() == 4){
										cwmesq = cwmesq.substring(0, 2)+"\\\\r\\\\n"+cwmesq.substring(2, cwmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(cwmesq.length() == 5||cwmesq.length() == 6){
										cwmesq = cwmesq.substring(0, 3)+"\\\\r\\\\n"+cwmesq.substring(3, cwmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(cwmesq.length()>8){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+cwmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("xm".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
							String xmmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										xmmesq = "";
									}else{
										xmmesq = (String)listj.get(i1);
									}
									xmmesq = xmmesq.replace("\n", "");
									if(xmmesq.length()>18){
										xmmesq = xmmesq.substring(0, 18);
									}
									if(xmmesq.length() <=4 ){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(4 < xmmesq.length() && xmmesq.length() <=8){
										xmmesq = xmmesq.substring(0, 4)+"\\\\r\\\\n"+xmmesq.substring(4, xmmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(xmmesq.length() > 8){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+xmmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("nljy".equals(string)){
							String endtime = DateUtil.getcurdate();
							List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
							String nlmesq = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										nlmesq = "";
									}else{
										nlmesq = (String)listj.get(i1);
									}
									script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"','"+pagenu+"','"+nlmesq+"');";
									script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"','"+pagenu+"','36');";
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("zzmmjy".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
							String zzmesq = "";
							int Q = 0;
							if(listj.size()!=0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										zzmesq = "";
									}else{
										zzmesq = (String)listj.get(i1);
									}
									zzmesq = zzmesq.replace("\n","");
									if(zzmesq.length()>18){
										zzmesq = zzmesq.substring(0, 18);
									}
									if(zzmesq.length()<=3){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(zzmesq.length() == 4){
										zzmesq = zzmesq.substring(0, 2)+"\\\\r\\\\n"+zzmesq.substring(2, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(zzmesq.length() == 5||zzmesq.length() == 6){
										zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else if(6 < zzmesq.length() && zzmesq.length() <= 9){
										zzmesq = zzmesq.substring(0, 3)+"\\\\r\\\\n"+zzmesq.substring(3, 5)+"\\\\r\\\\n"+zzmesq.substring(3, zzmesq.length());
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','11');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else{
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+zzmesq+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}
									Q++;
									if(Q==7)
										break;
								}
							}
						}else if("gzdwjzw".equals(string)){
							List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
							String mesA = "";
							int Q = 0;
							if(listj.size()>0){
								for(int i1=0;i1<listj.size();i1++){
									if("null".equals(listj.get(i1))||listj.get(i1)==null){
										mesA = "";
									}else{
										mesA = (String)listj.get(i1);
									}
									mesA = mesA.replace("\n", "");
									if(mesA.length()>100){
										mesA = mesA.substring(0, 100);
									}
									if(mesA.length()<=16){
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+mesA+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','36');";
									}else if(16<mesA.length() && mesA.length()<=32){
										mesA = mesA.subSequence(0, 16)+"\\\\r\\\\n"+mesA.subSequence(16, mesA.length());
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}else {
										script += "cell.SetCellString('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','"+mesA+"');";
										script += "cell.SetCellFontSize('"+line+"','"+(rowi+Q)+"','"+pagenu+"','14');";//�����С
										script+="cell.SetCellTextStyle('"+line+"','"+(rowi+Q)+"','"+pagenu+"','2');";//�Զ�����
										script+="cell.SetCellFontAutoZoom('"+line+"','"+(rowi+Q)+"','"+pagenu+"','1');";//�����Զ���С
										script += "cell.SetCellAlign('"+line+"','"+(rowi+Q)+"', '"+pagenu+"','33');";
									}
									Q++;
									if(Q==7)
										break;
								}
							}	
						}else if("dqyhm".equals(string)){
							UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
							String loginnname=user.getLoginname();
							script += "cell.SetCellString('"+line+"','"+ row +"','"+pagenu+"','"+loginnname+"');";
							script += "cell.SetCellAlign('"+line+"','"+ row +"','"+pagenu+"','36');";
						}
					}
				}
				script += "\n";
			}
		
		}
		
		script += "cell.ShowSideLabel('0','0');";//�����б�
		script += "cell.ShowTopLabel('0','0');";//�����б�
		this.getPageElement("selectedid").setValue(script);
//		System.out.println(script);
		this.getExecuteSG().addExecuteCode("docellshow()");
		return EventRtnType.NORMAL_SUCCESS;
	}
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
