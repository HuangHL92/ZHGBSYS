package com.insigma.siis.local.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
/****************************
 * @comment JSON String and Object Convert to Each Other 
 * @dependency jackson-all-1.7.6.jar  jsoup-1.5.2.jar
 * @author lee
 *
 */
public class JsonUtil {
	 /**
     * Convert JSON string to  object
     * @param jsonStr
     * @param obj
     * @return
     */
    public static<T> Object JSON2Obj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
     
    /**
     * Convert object to JSON string 
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject object2Json(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    public static void main(String[] args) throws JSONException, IOException {

    }

}
