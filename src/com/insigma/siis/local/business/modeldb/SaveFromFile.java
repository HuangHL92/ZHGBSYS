package com.insigma.siis.local.business.modeldb;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.util.FileUtil;
import com.insigma.siis.local.util.JsonUtil;
import com.insigma.siis.local.util.up_down.IAfterFileUpload;

public class SaveFromFile implements IAfterFileUpload {
	private static Logger log = Logger.getLogger(SaveFromFile.class); 
	public Object DoSomethingElse(List<HashMap<String, Object>> list)
			throws Exception {
		
		for(HashMap<String, Object> map : list){
			String fullpath =  map.get("attachmentadds").toString(); 
			log.info(fullpath);
			File file = new File(fullpath);
			Configs configs = null;
			if(file.exists()){
				String s = FileUtil.read4String(file);
				if(fullpath.endsWith(".json")){
					configs = JsonString2Configs(s);
				}
				if(fullpath.endsWith(".xml")){
					configs = XmlString2Configs(s);
				}
				//¼ÇÂ¼ÈÕÖ¾
				ModeldbBS.compareImpChanges4Log(configs);
				
				
				ModeldbBS.SaveConfigs(configs);
			}
			file.delete();
		}
		return null;
	}
	public void  saveFromJsonString(String jsonStr,String root){
		JSONObject configs = JSONObject.fromObject(jsonStr);
		JSONArray  a = configs.getJSONArray(root);
		for(int i=0;i<a.size();i++){
			JSONObject o = a.getJSONObject(i);
			Config config = (Config) JsonUtil.JSON2Obj(o.toString(),Config.class );
			try{ModeldbBS.SaveConfig(config);}catch(Exception e){};
		}
		
	}
	public Configs JsonString2Configs(String jsonStr){
		Configs configs = (Configs) JsonUtil.JSON2Obj(jsonStr,Configs.class );
		return configs;
	}
	public Configs XmlString2Configs(String xmlStr) throws Exception{
		Configs configs = (Configs) JXUtil.Xml2Object(xmlStr, Configs.class);
		return configs;
	}

}
