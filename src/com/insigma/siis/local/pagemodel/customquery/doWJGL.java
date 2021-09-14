package com.insigma.siis.local.pagemodel.customquery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.WJGL;
import com.insigma.siis.local.epsoft.config.AppConfig;


public class doWJGL {

	public int insertWJ() throws AppException {
		HBSession session = HBUtil.getHBSession();
		String wj00="";
		String type="";
		String wj01="";
		String a0000="";
		String b0111="";
		String year="";
		String wj02="";
		String wj03="";
		String wj04="";
		String wj05="";
		String wj06="";		
		String b0101="";
		System.out.println("========�ļ�����");
		HBUtil.executeUpdate("delete from wjgl where 1=1");
		String path = AppConfig.HZB_PATH + "/�ļ�����/";// �ϴ�·��
        File file = new File(path);
        if (file.exists()) {
            File[] filelist1 = file.listFiles();
            //��һ�� �ж���û������ļ���
            if ( filelist1.length > 0) {
            	//������һ�� �����������ļ���
            	for (File file2 : filelist1) {   
//                    System.out.println("=����ļ��У�"+file2.getAbsolutePath());
                    int one = file2.getAbsolutePath().lastIndexOf("\\");
                    //�������
                    year=file2.getAbsolutePath().substring((one+1),file2.getAbsolutePath().length());
                    File[] filelist2 = file2.listFiles();
                    //�ڶ��� �ж���û�������ļ���
                    if ( filelist2.length > 0) {
                    	//�����ڶ��� �������µ����в�������
                        for (File file3 : filelist2) {
//                        	System.out.println("==�����ļ��У�"+file3.getAbsolutePath());
                        	int two = file3.getAbsolutePath().lastIndexOf("\\");
                        	//���ò�������
                        	wj02=file3.getAbsolutePath().substring((two+1),file3.getAbsolutePath().length());
//                        	System.out.println("��������"+wj02);
                        	File[] filelist3 = file3.listFiles();
                        	//������ �ж����������µĵ�λ
                        	if(filelist3.length>0) {
                        		//���������� ������е�λ�ļ���
                            	for (File file4 : filelist3) {  
//                            		System.out.println("===��λ�ļ��У�"+file4.getAbsolutePath());
                            		int three=file4.getAbsolutePath().lastIndexOf("\\");
                            		//���õ�λ�� 
                            		b0101=file4.getAbsolutePath().substring((three+1),file4.getAbsolutePath().length());
//                            		System.out.println("��λ��"+wj01);
                            		//��õ�λ���
                        			@SuppressWarnings("unchecked")
                        			List<String> b0111s= HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b0104='"+b0101+"'").list();
                            		if(b0111s.size()>0) {
                            			//���õ�λ���
                            			b0111=b0111s.get(0);
                            		}else {
                            			b0111="";
                            		}
                        			File[] filelist4 = file4.listFiles();
                            		//���Ĳ� �жϵ�λ�����޵�λ���ϻ���Ա�����ļ���
                            		if(filelist4.length>0) {
                            			//�������Ĳ� ��õ�λ����˲����ļ���
                            			for (File file5 : filelist4) {
                        					int four=file5.getAbsolutePath().lastIndexOf("\\");
                        					//���õ�λ�ļ��л�����ļ���
                        					String typename=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
                        					if("���˲���".equals(typename)) {
                        						File[] filelist5 = file5.listFiles();
                        						//�������˲����ļ���
                        						if(filelist5.length>0) {
                        							for (File file6 : filelist5) {
                        								WJGL wjgl=new WJGL();
                                        				int five=file6.getAbsolutePath().lastIndexOf("\\");
                                        				//�����ļ�����
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//�����ļ�����
                                        				type="2";
                                        				wjgl.setType(type);
                                        				wjgl.setWj01(wj01);
                                        				wjgl.setA0000(a0000);
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                        				//���ò�����
                                        				wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                        				wjgl.setWj03(wj03);
                                        				//�����ļ�����
                                    					wj04=String.valueOf(file6.length()/1024)+" KB";
                                    					wjgl.setWj04(wj04);
                                    					wjgl.setWj05(file6.getAbsolutePath());
                                    					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    					wj06 = sf.format(System.currentTimeMillis());
                                    					wjgl.setWj06(wj06);
                                    					session.save(wjgl); 
                                    					session.flush();
                        							}
                        						}
                        					}else if("��λ����".equals(typename)) {
                        						File[] filelist5 = file5.listFiles();
                        						//������λ�����ļ���
                        						if(filelist5.length>0) {
                        							for (File file6 : filelist5) {
                        								int five=file6.getAbsolutePath().lastIndexOf("\\");
                                    					WJGL wjgl=new WJGL();
                                    					//�����ļ�����
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//�����ļ�����
                                    					type="1";
                                    					wjgl.setType(type);
                                    					wjgl.setWj01(b0101);
                                    					//��λ���ϲ���a0000;
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                    					//���ò�����
                                    					wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                    					wjgl.setWj03(wj03);
                                    					//�����ļ�����
                                    					wj04=String.valueOf(file6.length()/1024)+" KB";                					
                                    					wjgl.setWj04(wj04);
                                    					wjgl.setWj05(file6.getAbsolutePath());
                                    					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    					wj06 = sf.format(System.currentTimeMillis());
                                    					wjgl.setWj06(wj06);
                                    					session.save(wjgl);
                                    					session.flush();
                        							}
                        						}
                        					}
                            			}
                            		}
                            	}
                        	}
                        }    
                    }    
                }
            }
        }
        
        return EventRtnType.NORMAL_SUCCESS;
	}

	
	public int insertWJ1() throws AppException {
		HBSession session = HBUtil.getHBSession();
		String wj00="";
		String type="";
		String wj01="";
		String a0000="";
		String b0111="";
		String year="";
		String wj02="";
		String wj03="";
		String wj04="";
		String wj05="";
		String wj06="";		
		String b0101="";
		System.out.println("========�ļ�����");
		HBUtil.executeUpdate("delete from wjgl where 1=1");
		String path = AppConfig.HZB_PATH + "/�ļ�����/";// �ϴ�·��
        File file = new File(path);
        if (file.exists()) {
            File[] filelist1 = file.listFiles();
            //��һ�� �ж���û������ļ���
            if ( filelist1.length > 0) {
            	//������һ�� �����������ļ���
            	for (File file2 : filelist1) {   
//                    System.out.println("=����ļ��У�"+file2.getAbsolutePath());
                    int one = file2.getAbsolutePath().lastIndexOf("\\");
                    //�������
                    year=file2.getAbsolutePath().substring((one+1),file2.getAbsolutePath().length());
                    File[] filelist2 = file2.listFiles();
                    //�ڶ��� �ж���û�������ļ���
                    if ( filelist2.length > 0) {
                    	//�����ڶ��� �������µ����в�������
                        for (File file3 : filelist2) {
//                        	System.out.println("==�����ļ��У�"+file3.getAbsolutePath());
                        	int two = file3.getAbsolutePath().lastIndexOf("\\");
                        	//���ò�������
                        	wj02=file3.getAbsolutePath().substring((two+1),file3.getAbsolutePath().length());
//                        	System.out.println("��������"+wj02);
                        	File[] filelist3 = file3.listFiles();
                        	//������ �ж����������µĵ�λ
                        	if(filelist3.length>0) {
                        		//���������� ������е�λ�ļ���
                            	for (File file4 : filelist3) {  
//                            		System.out.println("===��λ�ļ��У�"+file4.getAbsolutePath());
                            		int three=file4.getAbsolutePath().lastIndexOf("\\");
                            		//���õ�λ�� 
                            		b0101=file4.getAbsolutePath().substring((three+1),file4.getAbsolutePath().length());
//                            		System.out.println("��λ��"+wj01);
                            		//��õ�λ���
                        			@SuppressWarnings("unchecked")
                        			List<String> b0111s= HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b0104='"+b0101+"'").list();
                            		if(b0111s.size()>0) {
                            			//���õ�λ���
                            			b0111=b0111s.get(0);
                            		}else {
                            			b0111="";
                            		}
                        			File[] filelist4 = file4.listFiles();
                            		//���Ĳ� �жϵ�λ�����޲��ϻ���Ա
                            		if(filelist4.length>0) {
                            			//�������Ĳ� ���������Ա��λ����
                            			for (File file5 : filelist4) {
                            				if (file5.isDirectory()){
//                            					System.out.println("====��Ա�ļ��У�"+file5.getAbsolutePath());
                            					int four=file5.getAbsolutePath().lastIndexOf("\\");
                            					//��������
                            					wj01=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
//                            					System.out.println("����"+wj01);
                            					//�����Ա���
                                    			@SuppressWarnings("unchecked")
                                    			List<String> a0000s= HBUtil.getHBSession().createSQLQuery("select a01.a0000 from a01,a02 where  a01.a0000=a02.a0000 and a0101='"+wj01+"' and a0201b ='"+b0111+"'").list();
                                    			if(a0000s.size()>0) {
                                        			//������Ա���
                                        			a0000=a0000s.get(0);
                                        		}else {
                                        			a0000="";
                                        		}
                            					File[] filelist5 = file5.listFiles();
                            					//����� �ж������µĲ����б�
                                        		if(filelist5.length>0) {
                                        			for (File file6 : filelist5) {
                                        				WJGL wjgl=new WJGL();
                                        				int five=file6.getAbsolutePath().lastIndexOf("\\");
                                        				//�����ļ�����
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//�����ļ�����
                                        				type="2";
                                        				wjgl.setType(type);
                                        				wjgl.setWj01(wj01);
                                        				wjgl.setA0000(a0000);
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                        				//���ò�����
                                        				wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                        				wjgl.setWj03(wj03);
                                        				//�����ļ�����
                                    					wj04=String.valueOf(file6.length());
                                    					wjgl.setWj04(wj04);
                                    					wjgl.setWj05(file6.getAbsolutePath());
                                    					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    					wj06 = sf.format(System.currentTimeMillis());
                                    					wjgl.setWj06(wj06);
                                    					session.save(wjgl); 
                                    					session.flush();
                                        			}
                                        		}
                            				}else {
                            					int four=file5.getAbsolutePath().lastIndexOf("\\");
                            					WJGL wjgl=new WJGL();
                            					//�����ļ�����
                                				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                				wjgl.setWj00(wj00);
                                				//�����ļ�����
                            					type="1";
                            					wjgl.setType(type);
                            					wjgl.setWj01(b0101);
                            					//��λ���ϲ���a0000;
                                				wjgl.setB0111(b0111);
                                				wjgl.setYear(year);
                                				wjgl.setWj02(wj02);
                            					//���ò�����
                            					wj03=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
                            					wjgl.setWj03(wj03);
                            					//�����ļ�����
                            					wj04=String.valueOf(file5.length());                       					
                            					wjgl.setWj04(wj04);
                            					wjgl.setWj05(file5.getAbsolutePath());
                            					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            					wj06 = sf.format(System.currentTimeMillis());
                            					wjgl.setWj06(wj06);
                            					session.save(wjgl);
                            					session.flush();
                            				}
                            			}
                            		}
                            	}
                        	}
                        }    
                    }    
                }
            }
        }
        
        return EventRtnType.NORMAL_SUCCESS;
	}
}
