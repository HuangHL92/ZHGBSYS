package com.insigma.siis.local.util;

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 * EhCacheUtil
 *  
 * @author wengsh
 * @version 1.0
 * @date 2016-07-23
 */
public class EhCacheUtil {
	
    private static CacheManager manager;
    
    static{
    	if(manager==null){
			InputStream in = EhCacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml");
			manager = CacheManager.create(in);
		}
    }
	
	/**
	 * 将值设置到缓存中
	 * @param key
	 * @param value
	 */
	public static void setCache(String key,String value){
		Cache cache = manager.getCache("gwycache"); 
		cache.remove(key); //先清除之前的
		cache.put(new Element(key,value));
	}
	
	
	/**
	 * 将值设置到缓存中
	 * @param key
	 * @param value
	 */
	public static String getCache(String key){
		Cache  cache= manager.getCache("gwycache"); 
		return cache.get(key).getValue().toString();
	}
	
	
	/**
	 * 将值设置到缓存中
	 * @param key
	 * @param value
	 */
	public static void setCache(String key,Object value){
		Cache cache = manager.getCache("gwycache"); 
		cache.remove(key); //先清除之前的
		cache.put(new Element(key,value));
	}
	
	
	/**
	 * 将值设置到缓存中
	 * @param key
	 * @param value
	 */
	public static Object  getObjectInCache(String key){
		Cache  cache= manager.getCache("gwycache"); 
		if(cache!=null) {
		if(cache.get(key)!=null) {
			return cache.get(key).getValue();
		}else {
			return null;
		}
		}else {
			return null;
		}
	}
}