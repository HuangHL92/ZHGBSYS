package com.insigma.siis.local.pagemodel.xbrm;

import com.fr.stable.core.UUID;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.*;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.CommonQueryBS;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class NiRenQcjsPageModel extends PageModel {
    private LogUtil applog = new LogUtil();

    @Override
    @NoRequiredValidate
    public int doInit() throws RadowException {
        this.setNextEventName("initX");
        return EventRtnType.NORMAL_SUCCESS;
    }


    @PageEvent("initX")
    @NoRequiredValidate
    public int initX() throws RadowException {
       // this.getExecuteSG().addExecuteCode("setValue();");
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String prope=param[1];
    	if(!StringUtil.isEmpty(prope)){
    		this.getPageElement("subproperty").setValue(prope);
    	}else{
    		this.getPageElement("subproperty").setValue("");
    	}
        //���ε�λ��ְ���б�
        this.setNextEventName("NiRenGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("NiRenGrid.dogridquery")
    @NoRequiredValidate
    public int  niRenGridQuery(int start,int limit) throws RadowException{
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
    	String sql="select * from js22 where a0000='"+a0000+"' and js0100='"+JS0100+"' order by sortid";
    	this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
    
    @PageEvent("savechecked")
    public int savechecked(){
    	
    	
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("save")
    public int save() throws RadowException{
    	String js2201=this.getPageElement("js2201").getValue();
    	String js2202=this.getPageElement("js2202").getValue();
    	String js2203=this.getPageElement("js2203").getValue();
    	String js2204=this.getPageElement("js2204").getValue();
    	
    	String js2200=UUID.randomUUID().toString();
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	try {
			HBUtil.executeUpdate("insert into js22(js2200,js2201,js2202,js2203,a0000,js2204) values(?,?,?,?,?,?)",new Object[]{js2200,js2201,js2202,js2203,a0000,js2204});
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��!");
			return EventRtnType.NORMAL_SUCCESS;
		}
    	this.setNextEventName("NiRenGrid.dogridquery");
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("savecheck")
    public int savecheck() throws RadowException{
    	String js2200s=this.getPageElement("js2200s").getValue();
    	String js2200s_old = js2200s;
    	if(!StringUtil.isEmpty(js2200s)){
    		js2200s=js2200s.substring(0,js2200s.length()-1).replaceAll(",", "','");
    	}
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
    	
    	String js2203s="";
    	String js2201s="";
    	String js2202s="";
    	String js2204s="";
    	try {
    		String upsql1="update js22 set js2205='0' where js0100 ='"+JS0100+"'";
			HBUtil.executeUpdate(upsql1);
			CommonQueryBS cq=new CommonQueryBS();
			/*List<HashMap<String, Object>> list = cq.getListBySQL("select a0000,js2201,js2202,js2203,js2200,js2204 from js22 where js2200 in('"+js2200s+"')");
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				String js2201 = (String)map.get("js2201");//��λ
				String js2203 = (String)map.get("js2203");//ְ��
				String js2202 = (String)map.get("js2202");//��λID
				js2203s=js2203s+js2203+"("+js2201+"),";
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			if(!StringUtil.isEmpty(js2203s)){
				js2203s=js2203s.substring(0, js2203s.length()-1);
			}
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}*/
    		List<HashMap<String, Object>> list = cq.getListBySQL("select * from js22 where js2200 in('"+js2200s+"')");
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				String js2201 = (String)map.get("js2201");//��λ
				String js2203 = (String)map.get("js2203");//ְ��
				String js2202 = (String)map.get("js2202");//��λID
				String js2201b = (String)map.get("js2201b");//��λ
				String js2201c = (String)map.get("js2201c");//��λ
				js2204s=js2204s+js2201c+js2203+",";
				js2203s=js2203s+js2201b+js2203+",";
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			if(!StringUtil.isEmpty(js2203s)){
				js2203s=js2203s.substring(0, js2203s.length()-1);
			}
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}
			
			//������ѡ��״̬
			String upsql="update js22 set js2205='1' where js2200 in('"+js2200s+"')";
			HBUtil.executeUpdate(upsql);
			this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+js2203s+"','"+js2200s_old+"','"+js2204s+"')");
			//this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+js2203s+"','"+js2202s+"')");
    	} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��!");
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
	 * ��������
	 * @return
	 * @throws RadowException
     * @throws AppException 
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException, AppException {
		String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
		
		String js2200s=this.getPageElement("js2200s").getValue();
		String js2200s_old = js2200s;
    	if(!StringUtil.isEmpty(js2200s)){
    		js2200s=js2200s.substring(0,js2200s.length()-1).replaceAll(",", "','");
    	}
    	String js2201s="";
    	String js2202s="";
		HBSession sess = HBUtil.getHBSession();
		/*String upsql1="update js22 set js2205='0' where js0100 ='"+JS0100+"'";
		sess.createSQLQuery(upsql1);*/
		String sql = "select * from js22 where js2200 in ('"+js2200s+"') order by sortid";//-1 ������λand a0201b!='-1'
		CommonQueryBS cq=new CommonQueryBS();
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//���� ���
			
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String zrjc = "";//���
			String ymjc = "";
			for(HashMap<String, Object> js22 : list){
				//String a0255 = a02.getA0255();//��ְ״̬
				String jgbm = js22.get("js2202")+"";//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				jgmcList.add(js22.get("js2201")==null?"":js22.get("js2201")+"");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(js22.get("js2201a")==null?"":js22.get("js2201a")+"");//�������� ���
				String zwmc = js22.get("js2203")==null?"":js22.get("js2203")+"";//ְ������
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + "1";
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc);
										
										
									}
									//���
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + "1";
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "��" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + "1";
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key ����_$_��������_$_�Ƿ�����
						}else{
							desc_full.put(key_full, value_full + "��" + zwmc);
						}
						
						//���
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + "1";
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "��" +  zwmc);
						}
					}
					
				}
				
				String js2201 = (String)js22.get("js2201");//��λ
				String js2202 = (String)js22.get("js2202");//��λID
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else{//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//���
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "��";
					}
				}else{//����
					if(!"".equals(jgzw)){
						ymjc += jgzw + "��";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(ԭ"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(ԭ"+ymjc+")";
			}
			
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}
			
			//������ѡ��״̬
			/*String upsql="update js22 set js2205='1' where js2200 in('"+js2200s+"')";
			HBUtil.executeUpdate(upsql);*/
			/*a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);*/
			this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+zrjc+"','"+js2200s_old+"','"+zrqm+"')");
		}else{
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
    
    @PageEvent("deleterow")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleterow(String js2200) {
    	try {
			HBUtil.executeUpdate("delete from js22 where js2200='"+js2200+"'");
			this.setNextEventName("NiRenGrid.dogridquery");
			this.setMainMessage("ɾ���ɹ�!");
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage("ɾ��ʧ��!");
		}	
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("selectFun.click")
    @Transaction
    public int selectFuns(String index) throws RadowException {
        //String a0000 = this.getPageElement("a0000").getValue();
        String a0525 = this.getPageElement("a0525").getValue();
        //int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
        String[] arr = index.split("_");
        String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", Integer.parseInt(arr[0])).toString();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A05 a05 = (A05) sess.get(A05.class, a0500);
            a05.setA0000(a0000);
            String sql = "update a05 set a0525 = '0' where a0000='" + a05.getA0000() + "'";
            sess.createSQLQuery(sql).executeUpdate();
            sess.flush();
            a05.setA0525(a0525);
            sess.saveOrUpdate(a05);
            sess.flush();
            A01 a01 = (A01) sess.get(A01.class, a0000);
            if (arr[1] != null && arr[1].equals("0")) {
                a01.setA0221(null);
                sess.saveOrUpdate(a01);
                sess.flush();
            } else if (arr[1] != null && arr[1].equals("1")) {
                String a0501b = a05.getA0501b();
                a01.setA0221(a0501b);
                sess.saveOrUpdate(a01);
                sess.flush();
            }

            String a0501b = a05.getA0501b();

            //���ְ��������
            String a0501bName = "";

            if (a0501b != null) {
                a0501bName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE", " code_type='ZB09' and code_value = '" + a0501b + "'");

            }
            this.getExecuteSG().addExecuteCode("realParent.setA0221Value('" + (a0501b == null ? "" : a0501b) + "','" + a0501bName + "')");//ҳ������ ������Ϣ�����ְ����

            //this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a01.getA0221()==null?"":a01.getA0221())+"')");
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','���������ʶʧ�ܣ�',null,'220')");
            return EventRtnType.FAILD;
        }
    }

    /**
     * ��ʾְ��ְ��grid���
     *
     * @param start
     * @param limit
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoGrid.dogridquery")
    @NoRequiredValidate
    public int trainingInforGridQuery(int start, int limit) throws RadowException {
        // String a0000 = this.getPageElement("a0000").getValue();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        String sql = "select * from A05 where a0000='" + a0000 + "' and a0531='0'";
        this.pageQuery(sql, "SQL", start, limit); // �����ҳ��ѯ
        return EventRtnType.SPE_SUCCESS;
    }

    /**
     * ���������ť�¼�
     *
     * @param
     * @param
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoAddBtn.onclick")
    @NoRequiredValidate
    public int trainingInforAddBtnWin(String id) throws RadowException {
        // String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        if (a0000 == null || "".equals(a0000)) {//
            this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','���ȱ�����Ա������Ϣ��',null,'220')");
            return EventRtnType.NORMAL_SUCCESS;
        }
        A05 a05 = new A05();
        a05.setA0000(a0000);
        PMPropertyCopyUtil.copyObjValueToElement(a05, this);
        this.getExecuteSG().addExecuteCode("setA0517Disabled();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * �޸��¼�
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoGrid.rowclick")
    @GridDataRange
    @NoRequiredValidate
    public int trainingInforGridOnRowClick() throws RadowException {
        int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
        String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A05 a05 = (A05) sess.get(A05.class, a0500);
            a05.setA0000(a0000);
            PMPropertyCopyUtil.copyObjValueToElement(a05, this);
        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','��ѯʧ�ܣ�',null,'220')");
            return EventRtnType.FAILD;
        }
        this.getExecuteSG().addExecuteCode("setA0517Disabled();");
        //this.getExecuteSG().addExecuteCode("a0501bChange();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * ��ʵ��POJOת��ΪJSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> String objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "{}";
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }

}
