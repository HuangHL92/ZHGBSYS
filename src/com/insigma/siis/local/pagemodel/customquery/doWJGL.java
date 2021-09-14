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
		System.out.println("========文件归类");
		HBUtil.executeUpdate("delete from wjgl where 1=1");
		String path = AppConfig.HZB_PATH + "/文件归类/";// 上传路径
        File file = new File(path);
        if (file.exists()) {
            File[] filelist1 = file.listFiles();
            //第一层 判断有没有年份文件夹
            if ( filelist1.length > 0) {
            	//遍历第一层 获得所有年份文件夹
            	for (File file2 : filelist1) {   
//                    System.out.println("=年份文件夹："+file2.getAbsolutePath());
                    int one = file2.getAbsolutePath().lastIndexOf("\\");
                    //设置年份
                    year=file2.getAbsolutePath().substring((one+1),file2.getAbsolutePath().length());
                    File[] filelist2 = file2.listFiles();
                    //第二层 判断有没有类型文件夹
                    if ( filelist2.length > 0) {
                    	//遍历第二层 获得年份下的所有材料类型
                        for (File file3 : filelist2) {
//                        	System.out.println("==类型文件夹："+file3.getAbsolutePath());
                        	int two = file3.getAbsolutePath().lastIndexOf("\\");
                        	//设置材料类型
                        	wj02=file3.getAbsolutePath().substring((two+1),file3.getAbsolutePath().length());
//                        	System.out.println("材料类型"+wj02);
                        	File[] filelist3 = file3.listFiles();
                        	//第三层 判断有无类型下的单位
                        	if(filelist3.length>0) {
                        		//遍历第三层 获得所有单位文件夹
                            	for (File file4 : filelist3) {  
//                            		System.out.println("===单位文件夹："+file4.getAbsolutePath());
                            		int three=file4.getAbsolutePath().lastIndexOf("\\");
                            		//设置单位名 
                            		b0101=file4.getAbsolutePath().substring((three+1),file4.getAbsolutePath().length());
//                            		System.out.println("单位名"+wj01);
                            		//获得单位编号
                        			@SuppressWarnings("unchecked")
                        			List<String> b0111s= HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b0104='"+b0101+"'").list();
                            		if(b0111s.size()>0) {
                            			//设置单位编号
                            			b0111=b0111s.get(0);
                            		}else {
                            			b0111="";
                            		}
                        			File[] filelist4 = file4.listFiles();
                            		//第四层 判断单位下有无单位材料或人员材料文件夹
                            		if(filelist4.length>0) {
                            			//遍历第四层 获得单位或个人材料文件夹
                            			for (File file5 : filelist4) {
                        					int four=file5.getAbsolutePath().lastIndexOf("\\");
                        					//设置单位文件夹或材料文件夹
                        					String typename=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
                        					if("个人材料".equals(typename)) {
                        						File[] filelist5 = file5.listFiles();
                        						//遍历个人材料文件夹
                        						if(filelist5.length>0) {
                        							for (File file6 : filelist5) {
                        								WJGL wjgl=new WJGL();
                                        				int five=file6.getAbsolutePath().lastIndexOf("\\");
                                        				//设置文件主键
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//设置文件类型
                                        				type="2";
                                        				wjgl.setType(type);
                                        				wjgl.setWj01(wj01);
                                        				wjgl.setA0000(a0000);
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                        				//设置材料名
                                        				wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                        				wjgl.setWj03(wj03);
                                        				//设置文件长度
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
                        					}else if("单位材料".equals(typename)) {
                        						File[] filelist5 = file5.listFiles();
                        						//遍历单位材料文件夹
                        						if(filelist5.length>0) {
                        							for (File file6 : filelist5) {
                        								int five=file6.getAbsolutePath().lastIndexOf("\\");
                                    					WJGL wjgl=new WJGL();
                                    					//设置文件主键
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//设置文件类型
                                    					type="1";
                                    					wjgl.setType(type);
                                    					wjgl.setWj01(b0101);
                                    					//单位材料不存a0000;
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                    					//设置材料名
                                    					wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                    					wjgl.setWj03(wj03);
                                    					//设置文件长度
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
		System.out.println("========文件归类");
		HBUtil.executeUpdate("delete from wjgl where 1=1");
		String path = AppConfig.HZB_PATH + "/文件归类/";// 上传路径
        File file = new File(path);
        if (file.exists()) {
            File[] filelist1 = file.listFiles();
            //第一层 判断有没有年份文件夹
            if ( filelist1.length > 0) {
            	//遍历第一层 获得所有年份文件夹
            	for (File file2 : filelist1) {   
//                    System.out.println("=年份文件夹："+file2.getAbsolutePath());
                    int one = file2.getAbsolutePath().lastIndexOf("\\");
                    //设置年份
                    year=file2.getAbsolutePath().substring((one+1),file2.getAbsolutePath().length());
                    File[] filelist2 = file2.listFiles();
                    //第二层 判断有没有类型文件夹
                    if ( filelist2.length > 0) {
                    	//遍历第二层 获得年份下的所有材料类型
                        for (File file3 : filelist2) {
//                        	System.out.println("==类型文件夹："+file3.getAbsolutePath());
                        	int two = file3.getAbsolutePath().lastIndexOf("\\");
                        	//设置材料类型
                        	wj02=file3.getAbsolutePath().substring((two+1),file3.getAbsolutePath().length());
//                        	System.out.println("材料类型"+wj02);
                        	File[] filelist3 = file3.listFiles();
                        	//第三层 判断有无类型下的单位
                        	if(filelist3.length>0) {
                        		//遍历第三层 获得所有单位文件夹
                            	for (File file4 : filelist3) {  
//                            		System.out.println("===单位文件夹："+file4.getAbsolutePath());
                            		int three=file4.getAbsolutePath().lastIndexOf("\\");
                            		//设置单位名 
                            		b0101=file4.getAbsolutePath().substring((three+1),file4.getAbsolutePath().length());
//                            		System.out.println("单位名"+wj01);
                            		//获得单位编号
                        			@SuppressWarnings("unchecked")
                        			List<String> b0111s= HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b0104='"+b0101+"'").list();
                            		if(b0111s.size()>0) {
                            			//设置单位编号
                            			b0111=b0111s.get(0);
                            		}else {
                            			b0111="";
                            		}
                        			File[] filelist4 = file4.listFiles();
                            		//第四层 判断单位下有无材料或人员
                            		if(filelist4.length>0) {
                            			//遍历第四层 获得所有人员或单位材料
                            			for (File file5 : filelist4) {
                            				if (file5.isDirectory()){
//                            					System.out.println("====人员文件夹："+file5.getAbsolutePath());
                            					int four=file5.getAbsolutePath().lastIndexOf("\\");
                            					//设置人名
                            					wj01=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
//                            					System.out.println("人名"+wj01);
                            					//获得人员编号
                                    			@SuppressWarnings("unchecked")
                                    			List<String> a0000s= HBUtil.getHBSession().createSQLQuery("select a01.a0000 from a01,a02 where  a01.a0000=a02.a0000 and a0101='"+wj01+"' and a0201b ='"+b0111+"'").list();
                                    			if(a0000s.size()>0) {
                                        			//设置人员编号
                                        			a0000=a0000s.get(0);
                                        		}else {
                                        			a0000="";
                                        		}
                            					File[] filelist5 = file5.listFiles();
                            					//第五层 判断人物下的材料列表
                                        		if(filelist5.length>0) {
                                        			for (File file6 : filelist5) {
                                        				WJGL wjgl=new WJGL();
                                        				int five=file6.getAbsolutePath().lastIndexOf("\\");
                                        				//设置文件主键
                                        				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                        				wjgl.setWj00(wj00);
                                        				//设置文件类型
                                        				type="2";
                                        				wjgl.setType(type);
                                        				wjgl.setWj01(wj01);
                                        				wjgl.setA0000(a0000);
                                        				wjgl.setB0111(b0111);
                                        				wjgl.setYear(year);
                                        				wjgl.setWj02(wj02);
                                        				//设置材料名
                                        				wj03=file6.getAbsolutePath().substring((five+1),file6.getAbsolutePath().length());
                                        				wjgl.setWj03(wj03);
                                        				//设置文件长度
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
                            					//设置文件主键
                                				wj00=UUID.randomUUID().toString().replaceAll("-", "");
                                				wjgl.setWj00(wj00);
                                				//设置文件类型
                            					type="1";
                            					wjgl.setType(type);
                            					wjgl.setWj01(b0101);
                            					//单位材料不存a0000;
                                				wjgl.setB0111(b0111);
                                				wjgl.setYear(year);
                                				wjgl.setWj02(wj02);
                            					//设置材料名
                            					wj03=file5.getAbsolutePath().substring((four+1),file5.getAbsolutePath().length());
                            					wjgl.setWj03(wj03);
                            					//设置文件长度
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
