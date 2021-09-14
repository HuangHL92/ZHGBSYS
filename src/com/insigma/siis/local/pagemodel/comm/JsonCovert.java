package com.insigma.siis.local.pagemodel.comm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json文本与Map或List对象互转  
 * @author zepeng
 */
public class JsonCovert {
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;

	public JsonCovert() {
		objectMapper = new ObjectMapper();
		try {
//			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
//					System.out, JsonEncoding.UTF8); //PYJ update
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					new File("Generator.json"), JsonEncoding.UTF8); 
		} catch (IOException e) {
			e.printStackTrace();
			try {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
				System.out, JsonEncoding.UTF8);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} //PYJ update
		}
	}
	/**
	 * <b>function:</b>将list集合转换成json字符串
	 * @author hoojo
	 * @createDate 2010-11-23 下午06:05:59
	 */
	/*
	 * 		    	JsonCovert js = new JsonCovert();
			    	for(Map value: resultList){
			    		Map map=new LinkedMap();
			    		map.put("setId", value.get("setId"));
			    		map.put("description",value.get("description"));
			    		if(selectedSub!=null){
			    			 map.put("fieldName",value.get("fieldName"));
			 		    }
			    		list.add(map);
			    	}
			    	jsonstr=js.writeUtilJSON(list);
	 */
	public String writeUtilJSON(Object util) {
		try {
			jsonGenerator.writeObject(util);
			return objectMapper.writeValueAsString(util);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	/*
	 * 	String j = "[{{\"A203006\":{\"name\":\"A001001\",\"action\":\"overwrite\"}},{\"A203006\":{\"name\":\"A001004\",\"action\":\"overwrite\"}}}]";
		JsonCovert js = new JsonCovert();
		Map<String, String> m = js.readJson2Map(j);
	 */
	public Map<String, String> readJson2Map(String json) {
		if(json.startsWith("[")&&json.endsWith("]")){
			json=json.substring(1, json.length()-1);
		}
		Map<String, String> maps = new HashMap<String, String>();;
		try {
			maps = objectMapper.readValue(
					json, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return maps;
	}
	
	public static String writeMisc2JSON(List<Map> l) throws JsonProcessingException, IOException {
		JsonGenerator jsonGeneratorItem = null;
		ObjectMapper objectMapperItem = null;
		objectMapperItem = new ObjectMapper();
		try {
			jsonGeneratorItem = objectMapperItem.getJsonFactory().createJsonGenerator(
					System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jsonGeneratorItem.writeObject(l);
		return objectMapperItem.writeValueAsString(l);
	}


	public static void main3(String[] args) {
		String j = "[{{\"A203006\":{\"name\":\"A001001\",\"action\":\"overwrite\"}},{\"A203006\":{\"name\":\"A001004\",\"action\":\"overwrite\"}}}]";
		JsonCovert js = new JsonCovert();
		Map<String, String> m = js.readJson2Map(j);
		System.out.println(m.size());
	}
	public static void main(String[] args) {
		Map<String, Map<String, String>> ap = new HashMap<String, Map<String, String>>();;
		Map<String, String> p1 = new HashMap<String, String>();;
		Map<String, String> p2 = new HashMap<String, String>();;
		Map<String, String> p3 = new HashMap<String, String>();;
		Map<String, String> p4 = new HashMap<String, String>();;
		Map<String, String> p5 = new HashMap<String, String>();;
		p1.put("p1k1", "p1v1");
		p1.put("p1k2", "p1v2");
		p1.put("p1k3", "p1v3");
		p1.put("p1k4", "p1v4");
		ap.put("p1",p1);
		p2.put("p2k1", "p2v1");
		p2.put("p2k2", "p2v2");
		p2.put("p2k3", "p2v3");
		p2.put("p2k4", "p2v4");
		ap.put("p2",p2);
		p3.put("p3k1", "p3v1");
		p3.put("p3k2", "p3v2");
		p3.put("p3k3", "p3v3");
		p3.put("p3k4", "p3v4");
		ap.put("p3",p3);
		p4.put("p4k1", "p4v1");
		p4.put("p4k2", "p4v2");
		p4.put("p4k3", "p4v3");
		p4.put("p4k4", "p4v4");
		ap.put("p4",p4);
		p5.put("p5k1", "p5v1");
		p5.put("p5k2", "p5v2");
		p5.put("p5k3", "p5v3");
		p5.put("p5k4", "p5v4");
		ap.put("p5",p5);
		JsonCovert js = new JsonCovert();
		System.out.print(js.writeUtilJSON(ap));
		System.out.println(js.readJson2Map(js.writeUtilJSON(ap)));
	}
	public static void main2(String[] args) throws InstantiationException, IllegalAccessException {
		Map<String, AccountBean> ap = new HashMap<String, AccountBean>();;
		AccountBean p1 = null;
		AccountBean p2 = null;
		AccountBean p3 = null;
		AccountBean p4 = null;
		AccountBean p5 = null;
		p1 = new AccountBean();
		p1.setId(2);
		p1.setAddress("address2");
		p1.setEmail("email2");
		p1.setName("haha2");
		ap.put("p1",p1);
		p2 = new AccountBean();
		p2.setId(2);
		p2.setAddress("address2");
		p2.setEmail("email2");
		p2.setName("haha2");
		ap.put("p2",p2);
		p3 = new AccountBean();
		p3.setId(2);
		p3.setAddress("address2");
		p3.setEmail("email2");
		p3.setName("haha2");
		ap.put("p3",p3);
		p4 = new AccountBean();
		p4.setId(2);
		p4.setAddress("address2");
		p4.setEmail("email2");
		p4.setName("haha2");
		ap.put("p4",p4);
		p5 = new AccountBean();
		p5.setId(2);
		p5.setAddress("address2");
		p5.setEmail("email2");
		p5.setName("haha2");
		ap.put("p5",p5);
		JsonCovert js = new JsonCovert();
		System.out.println(js.writeUtilJSON(ap));
	}
	
	public static class AccountBean {
		private int id;
		private String name;
		private String email;
		private String address;
		private Birthday birthday;

		//getter、setter

		@Override
		public String toString() {
			return this.getName() + "#" + this.getId() + "#" + this.getAddress()
					+ "#" + this.getBirthday() + "#" + this.getEmail();
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Birthday getBirthday() {
			return birthday;
		}

		public void setBirthday(Birthday birthday) {
			this.birthday = birthday;
		}
		public class Birthday {
			private String birthday;

			public Birthday(String birthday) {
				super();
				this.birthday = birthday;
			}

			//getter、setter

			public Birthday() {
			}

			@Override
			public String toString() {
				return this.birthday;
			}
		}
	}
}
