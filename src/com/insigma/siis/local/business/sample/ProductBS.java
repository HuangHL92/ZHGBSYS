package com.insigma.siis.local.business.sample;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.siis.local.business.entity.Products;

public class ProductBS extends BSSupport {

	/**
	 * 保存商品
	 * @param p
	 */
	public void saveProduct(Products p)throws AppException{
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("name", p.getName());
		List<Object> list = this.query("from Products p where p.name=:name", param);
		if(list.size()>0){
			throw new AppException("产品名称”"+p.getName()+"“已经存在了！");
		}
		this.save(p);
	}
	
	/**
	 * 更新商品信息
	 * @param p
	 */
	public void updateProduct(Products p)throws AppException{
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("name", p.getName());
		param.put("id", p.getId());
		List<Object> list = this.query("from Products p where p.name=:name and p.id!=:id", param);
		if(list.size()>0){
			throw new AppException("产品名称”"+p.getName()+"“已经存在了！");
		}
		this.update(p);
	}
	
	/**
	 * 删除产品信息
	 * @param id
	 */
	public void deleteProduct(String id){
		this.delete(this.get(Products.class, id));
	}
}
