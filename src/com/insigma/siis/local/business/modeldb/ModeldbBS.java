package com.insigma.siis.local.business.modeldb;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Sublibrariescolumns;
import com.insigma.siis.local.business.entity.SublibrariescolumnsId;
import com.insigma.siis.local.business.entity.Sublibrariescondition;
import com.insigma.siis.local.business.entity.SublibrariesconditionDTO;
import com.insigma.siis.local.business.entity.Sublibrariesmodel;
import com.insigma.siis.local.business.entity.Sysmodelrole;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.FileUtil;
import com.insigma.siis.local.util.JsonUtil;
/**********************
 * 主题分库核心处理类
 * @author Lee
 *
 */
public class ModeldbBS extends BSSupport {
	public static final String dataSet[] = {"A01","A02","A06","A08","A11", "A14","A15","A29","A30","A31", "A36","A37","A41","A53","A57"};
	 private static final String sys =System.getProperty("os.name").toUpperCase();
	 private static final String DOWNLOAD_PATH = GlobalNames.sysConfig.get("DOWNLOAD_PATH"); 
	 private static final String DOWNLOAD_PATH_WIN = GlobalNames.sysConfig.get("DOWNLOAD_PATH_WIN"); 
	
	public static Sublibrariesmodel LoadSublibrariesmodel(String id){
		try{
			return (Sublibrariesmodel) HBUtil.getHBSession().get(Sublibrariesmodel.class, id);
		}catch(Exception e){
			return null;
		}
	}
	public static Sublibrariesmodel LoadModelByKey(String key){
		HBSession sess =HBUtil.getHBSession();
		Sublibrariesmodel model = (Sublibrariesmodel) sess.createQuery(" from Sublibrariesmodel where sub_libraries_model_key ='"+key+"'").uniqueResult();
		return model;
	}
	public static int saveOrUpdateModel(Sublibrariesmodel model){
		HBSession sess =HBUtil.getHBSession();
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String groupname;
		try{
			SmtGroup grp = (SmtGroup) sess.get(SmtGroup.class, user.getDept());
			groupname = grp.getName();
		}catch(Exception e){
			groupname = null;
		}
		//id存在，修改
		if(model.getSub_libraries_model_id() != null &&!"".equals(model.getSub_libraries_model_id())){
			model.setChange_time(new Date());
			model.setChange_user_id(user.getId());
			model.setChange_user_name(user.getName());
			sess.update(model);
			return 1;
		} else {//新增
			model.setCreate_groupid(user.getDept());
			model.setCreate_group_name(groupname);
			model.setCreate_time(new Date());
			model.setCreate_user_id(user.getId());
			model.setCreate_username(user.getName());
			model.setIs_effective("1");
			model.setRun_state("0");
			model.setSelf_create_mark("1");
			Long l = System.currentTimeMillis();
			model.setSub_libraries_model_key(l.toString());
			sess.save(model);
			return 0;
		}
	}
	/******************
	 * 删除主题分库
	 * @param id 主题分库ID
	 * @param type 如果是0则全删否则只删除定义和数据
	 * @param delDefine 是否删除定义(若type=0,该参数无效，定义一定会被删除)
	 * @return
	 */
	public static int delSublibrariesmodel(String id,int type,boolean delDefine){
		HBSession sess = HBUtil.getHBSession();
		Sublibrariesmodel model = (Sublibrariesmodel) sess.get(Sublibrariesmodel.class, id);
		String key = model.getSub_libraries_model_key();
		if("1".equals(model.getSub_libraries_model_type())){//删除逻辑
			try{sess.createSQLQuery("drop view v_ztfk_view_base_"+key).executeUpdate();}catch(Exception e){};
			for (int i = 0; i < dataSet.length; i++) {
				StringBuffer s = new StringBuffer();
				s.append("drop view " + dataSet[i] +"_" + key);
				try{sess.createSQLQuery(s.toString()).executeUpdate();}catch(Exception e){};
			}
		} else {//删除数据
			for (int i = 0; i < dataSet.length; i++) {
				StringBuffer exsql = new StringBuffer();
				exsql.append("delete from " + dataSet[i] +"_ztfk where ztfk_key='" + key +"'");
				sess.createSQLQuery(exsql.toString()).executeUpdate();
			}
		}
		//删除定义
		if(delDefine || type==0){
			sess.createQuery("delete from Sublibrariescondition where sub_libraries_model_id='" + id +"'").executeUpdate();
			sess.createQuery("delete from Sublibrariescolumns where id.sub_libraries_model_id ='" + id +"'").executeUpdate();
		}
		//删除主表
		if(type==0){
			sess.delete(model);
			
			//记录日志使用
			SublibrariesmodelDTO dto = new SublibrariesmodelDTO();
			//记录日志:删除主题分库
			try {
				new LogUtil().createLog("652", "SUB_LIBRARIES_MODEL",
						model.getSub_libraries_model_id(),
						model.getSub_libraries_model_name(), null,
						Map2Temp.getLogInfo(dto, model));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return 0;
	}
	
	/******************
	 * 删除主题分库:删除启用结果和定义信息
	 * @param id 主题分库ID
	 * @param type 如果是0则全删否则只删除定义和数据
	 * @return
	 */
	public static int delSublibrariesmodel(String id,int type){
		return delSublibrariesmodel(id,type,true);
	}
	
	
	public static int saveDB(ModelDB o) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		//先清除历史定义和历史数据
		delSublibrariesmodel(o.getId(),1);
		//过滤重复的列
		List<Sublibrariescolumns> columns = filterColumns(o.getColumns());
		//校验条件的合法性
		List<Sublibrariescondition> conditions = o.getConditions();
		//保存列定义
		for(Sublibrariescolumns column : columns){
			sess.save(column);
		}
		//保存条件定义
		for(Sublibrariescondition condition : conditions ){
			sess.save(condition);
		}
		//生成sql
		//获取当前数据库类型对应数据库下的sql生成器
		ISelecter is = SelecterFactory.createSelecter(DBUtil.getDBType());
		//ISelecter is = SelecterFactory.createSelecter(DBType.MYSQL);
		String sql = is.getSelecter(o);
		Sublibrariesmodel model = LoadSublibrariesmodel(o.getId());
		model.setSqltext(sql);
		sess.update(model);
		return 0;
	}
	
	/**
	 * 比较ModelDB，并记录日志
	 * @param mdOld
	 * @param moNew
	 * @return
	 * @throws Exception
	 */
	public static void compareChanges4Log(ModelDBDTO mdOld,ModelDB mdNew) throws Exception{
		String flag ;
		if(mdOld.getColumns()==null || mdOld.getColumns().size()<1){
			flag = "655"; //保存主题分库
		}else{
			flag = "656"; //修改主题分库
		}
		 List<SublibrariesconditionDTO> conditionsOld = mdOld.getConditions()!=null?mdOld.getConditions():new ArrayList<SublibrariesconditionDTO>();
		 List<Sublibrariescondition> conditionsNew = mdNew.getConditions()!=null?mdNew.getConditions():new ArrayList<Sublibrariescondition>();
		 List<SublibrariescolumnsDTO> columnsOld = mdOld.getColumns();
		 List<Sublibrariescolumns> columnsNew = mdNew.getColumns();
		
		
		//记录日志 mengl 20160628
		//获取改变信息
		List<String[]> changesList = new ArrayList<String[]>();
		
		changesList.addAll( Map2Temp.getLogInfo(mdOld.getModel()==null?new SublibrariesmodelDTO():mdOld.getModel(),mdNew.getModel()));
		
		
		Sublibrariescondition conditionNew = new Sublibrariescondition();//空对象，新对象比对为空时使用
		Sublibrariescolumns colnumnNew = new Sublibrariescolumns();//空对象，新对象比对为空时使用
		SublibrariesconditionDTO conditionDTONew = new SublibrariesconditionDTO();//空对象，新对象比对为空时使用
		SublibrariescolumnsDTO colnumnDTONew = new SublibrariescolumnsDTO();//空对象，新对象比对为空时使用
			//循环次数，取最大值
		int size = conditionsOld.size()>conditionsNew.size()?conditionsOld.size():conditionsNew.size();
		for(int x = 0;x<size;x++){
			//超过List范围，则赋值为空对象来比较
			SublibrariesconditionDTO odlCon = conditionsOld.size()>x && conditionsOld.get(x)!=null?conditionsOld.get(x):conditionDTONew;
			Sublibrariescondition newCon = conditionsNew.size()>x && conditionsNew.get(x)!=null?conditionsNew.get(x):conditionNew ;
			changesList.addAll( Map2Temp.getLogInfo(odlCon,newCon));
		}
			//循环次数，取最大值
		int sizeClo = columnsOld.size()>columnsNew.size()?columnsOld.size():columnsNew.size();
		for(int x = 0;x<sizeClo;x++){
			//超过List范围，则赋值为空对象来比较
			SublibrariescolumnsDTO odlCol = columnsOld.size()>x && columnsOld.get(x)!=null?columnsOld.get(x):colnumnDTONew;
			Sublibrariescolumns newCol = columnsNew.size()>x && columnsNew.get(x)!=null?columnsNew.get(x):colnumnNew ;
			changesList.addAll( Map2Temp.getLogInfo(odlCol,newCol));
		}
		
			//排除主键改变因素
		Iterator<String[]> it = changesList.iterator();
		while(it.hasNext()){
			String[] changeItem = it.next();
			if(changeItem!=null && changeItem.length>0 && ("sub_libraries_condition_id".toUpperCase().equalsIgnoreCase(changeItem[0]) || "id".toUpperCase().equalsIgnoreCase(changeItem[0])) ){
				it.remove();
			}
		}
		
		//记录日志:授权主题分库
		try {
			new LogUtil().createLog(flag, "SUB_LIBRARIES_MODEL",
					mdNew.getId(),
					mdNew.getModel().getSub_libraries_model_name(), null,changesList
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 比较Configs，并记录日志
	 * @param mdOld
	 * @param moNew
	 * @return
	 * @throws Exception
	 */
	public static void compareImpChanges4Log(Configs configs) throws Exception{
		Session sess = HBUtil.getHBSession().getSession();
		String flag = "660"; //导入
		if(configs==null||configs.getConfig()==null ||configs.getConfig().size()<1){
			return;
		}
		for(Config config :configs.getConfig()){
			sess.evict(config.getModel());
			List<Sublibrariescondition> conditionsOld = config.getConditions()!=null?config.getConditions():new ArrayList<Sublibrariescondition>();
			List<Sublibrariescolumns> columnsOld = config.getColumns();
			
			//记录日志 mengl 20160628
			//获取改变信息
			Sublibrariesmodel model = new Sublibrariesmodel();
			Sublibrariescondition conditionNew = new Sublibrariescondition();//空对象，新对象比对为空时使用
			Sublibrariescolumns colnumnNew = new Sublibrariescolumns();//空对象，新对象比对为空时使用
			
			List<String[]> changesList = new ArrayList<String[]>();
			
			changesList.addAll( Map2Temp.getLogInfo(config.getModel()==null?model:config.getModel(),model));
			
			//循环次数，取最大值
			int size = conditionsOld.size();
			for(int x = 0;x<size;x++){
				//超过List范围，则赋值为空对象来比较
				Sublibrariescondition odlCon =  conditionsOld.get(x);
				changesList.addAll( Map2Temp.getLogInfo(odlCon,conditionNew));
				sess.evict(odlCon);
			}
			//循环次数，取最大值
			int sizeClo = columnsOld.size();
			for(int x = 0;x<sizeClo;x++){
				//超过List范围，则赋值为空对象来比较
				Sublibrariescolumns odlCol = columnsOld.get(x);
				changesList.addAll( Map2Temp.getLogInfo(odlCol,colnumnNew));
				sess.evict(odlCol);
			}
			
			//排除主键改变因素
			Iterator<String[]> it = changesList.iterator();
			while(it.hasNext()){
				String[] changeItem = it.next();
				if(changeItem!=null && changeItem.length>0 && ("sub_libraries_condition_id".toUpperCase().equalsIgnoreCase(changeItem[0]) || "id".toUpperCase().equalsIgnoreCase(changeItem[0])) ){
					it.remove();
				}
			}
			
			//记录日志:授权主题分库
			try {
				new LogUtil().createLog(flag, "SUB_LIBRARIES_MODEL",
						config.getModel().getSub_libraries_model_id(),
						config.getModel().getSub_libraries_model_name(), null,changesList
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	public static ModelDBDTO getModelDB(String id) throws AppException{
		ModelDBDTO mdb = new ModelDBDTO();
		HBSession sess = HBUtil.getHBSession();
		Session sessReal = sess.getSession();
		Sublibrariesmodel model = (Sublibrariesmodel) sess.get(Sublibrariesmodel.class, id);
		SublibrariesmodelDTO modelDTO = new SublibrariesmodelDTO();
		BeanUtil.copy(model, modelDTO);
		sessReal.evict(model);
		
		List<Sublibrariescondition> conditions = sess.createQuery(" from Sublibrariescondition where sub_libraries_model_id='" + id +"'").list();
		List<Sublibrariescolumns> columns = sess.createQuery(" from Sublibrariescolumns where id.sub_libraries_model_id ='" + id +"'").list();
		
		List<SublibrariesconditionDTO> conditionDTOs = new ArrayList<SublibrariesconditionDTO>();
		List<SublibrariescolumnsDTO> columnDTOs = new ArrayList<SublibrariescolumnsDTO>();
		
		for(Sublibrariescondition condition :conditions){
			SublibrariesconditionDTO dto = new SublibrariesconditionDTO();
			BeanUtil.copy(condition, dto);
			conditionDTOs.add(dto);
			sessReal.evict(condition);
			
		}
		
		for(Sublibrariescolumns column : columns){
			SublibrariescolumnsDTO dto = new SublibrariescolumnsDTO();
			BeanUtil.copy(column, dto);
			columnDTOs.add(dto);
			sessReal.evict(column);
		}
		
		mdb.setModel(modelDTO);
		mdb.setColumns(columnDTOs);
		mdb.setConditions(conditionDTOs);
		mdb.setId(id);
		return mdb;
	}
	
	
	@SuppressWarnings("unchecked")
	public static  ModelDB LoadDB(String id){
		 HBSession sess = HBUtil.getHBSession();
		 ModelDB db = new ModelDB();
		 Sublibrariesmodel model= LoadSublibrariesmodel(id);
		 List<Sublibrariescolumns> columns = sess.createQuery(" select t from Sublibrariescolumns t where t.id.sub_libraries_model_id='"+id+"' order by t.columns_no ").list();
		 List<Sublibrariescondition> conditions =sess.createQuery(" select t from Sublibrariescondition t where t.sub_libraries_model_id='"+id+"' order by t.condition_no").list();
		 db.setId(id);
		 db.setModel(model);
		 db.setColumns(columns);
		 db.setConditions(conditions);
		 return db;
	}
	@SuppressWarnings("unchecked")
	public static  Config LoadConfig(String key){
		 HBSession sess = HBUtil.getHBSession();
		 Config c = new Config();
		 Sublibrariesmodel model= LoadModelByKey(key);
		 List<Sublibrariescolumns> columns = sess.createQuery(" select t from Sublibrariescolumns t where t.id.sub_libraries_model_id='"+model.getSub_libraries_model_id()+"' order by t.columns_no ").list();
		 List<Sublibrariescondition> conditions =sess.createQuery(" select t from Sublibrariescondition t where t.sub_libraries_model_id='"+model.getSub_libraries_model_id()+"' order by t.condition_no").list();
		 c.setModel(model);
		 c.setColumns(columns);
		 c.setConditions(conditions);
		 return c;
	}

	public static int runDB(List<String> list) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans =null;
		try{
			 trans = sess.beginTransaction(); 
			for(String id : list){
				ModelDB db = LoadDB(id);
				if(db==null){
					throw new AppException(" 无法获取该分库模型！分库模型ID-"+id);
				}
				if(db.getColumns()==null || db.getColumns().size()<1){
					throw new AppException(" 该分库模型未配置查询列！分库模型ID-"+id);
				}
				if(db.getConditions()==null || db.getConditions().size()<1){
					throw new AppException(" 该分库模型未配置查询条件！分库模型ID-"+id);
				}
				String dbkey = db.getModel().getSub_libraries_model_key();
				ISelecter is = SelecterFactory.createSelecter(DBUtil.getDBType());
				String exists = is.getSelecter(db);//获取子查询
				if("1".equals(db.getModel().getSub_libraries_model_type())){//逻辑分库
					
					//MySQL中创建视图，查询语句中不能有子查询修改：①先建立exists的子视图； mengl
					if(DBType.MYSQL == DBUtil.getDBType() ){
						String tempViewName = "v_ztfk_view_base_"+dbkey;
						String createTempViewSql = "create or replace view "+tempViewName+" as "+exists;
						sess.createSQLQuery(createTempViewSql).executeUpdate();
						for (int i = 0; i < dataSet.length; i++) {
							
							StringBuffer executer = new StringBuffer();
							executer.append("create or replace view ");
							executer.append(dataSet[i] +"_" + dbkey + " as select * from " + dataSet[i] + " where Exists ( ");
							executer.append(" select 1 from "+tempViewName+" t where t.a0000 = "+dataSet[i]+".a0000 ");
							executer.append(")");
							sess.createSQLQuery(executer.toString()).executeUpdate();
						}
					}else if(DBType.ORACLE == DBUtil.getDBType()){
						for (int i = 0; i < dataSet.length; i++) {
							StringBuffer executer = new StringBuffer();
							executer.append("create or replace view ");
							executer.append(dataSet[i] +"_" + dbkey + " as select * from " + dataSet[i] + " where Exists ( ");
							executer.append(" select 1 from ("+exists+") t where t.a0000 = "+dataSet[i]+".a0000 ");
							executer.append(")");
							sess.createSQLQuery(executer.toString()).executeUpdate();
						}
					}
				}else{//物理分库
					for (int i = 0; i < dataSet.length; i++) {
						StringBuffer executer = new StringBuffer();
						String columns = getColumns(dataSet[i]);
						executer.append(" insert into "+ dataSet[i]+"_ztfk (").append(columns).append(",ztfk_key)");
						executer.append(" select "+columns+","+dbkey+" from " +dataSet[i] +" where Exists(");
						executer.append(" select 1 from ("+exists+") t where t.a0000 = "+dataSet[i]+".a0000 ");
						executer.append(")");
						sess.createSQLQuery(executer.toString()).executeUpdate();
					}
				}
				Sublibrariesmodel model= db.getModel();
				//记录日志使用
				SublibrariesmodelDTO dto = new SublibrariesmodelDTO();
				BeanUtil.copy(model, dto);
				
				model.setRun_state("1");
				sess.update(model);
				
				//记录日志:启动主题分库
				try {
					new LogUtil().createLog("653", "SUB_LIBRARIES_MODEL",
							model.getSub_libraries_model_id(),
							model.getSub_libraries_model_name(), null,
							Map2Temp.getLogInfo(dto, model));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			trans.commit();
			return 0;
		}catch(Exception e){
			trans.rollback();
			e.printStackTrace();
			throw new AppException("启用失败："+e.getMessage());
		}	
	}
	@SuppressWarnings("unchecked")
	public static List<Sublibrariesmodel> getTaskList(){
		HBSession sess = HBUtil.getHBSession();
		List<Sublibrariesmodel> list = sess.createQuery(" select t from Sublibrariesmodel t where t.run_state ='1' and t.sub_libraries_model_type='2' ").list();
		return list;
	}
	public static int dataSyn(List<Sublibrariesmodel> list){
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans =null;
		try{
			 trans = sess.beginTransaction(); 
			for(Sublibrariesmodel o : list){
				    String extend = o.getSub_libraries_model_key();
				    String exists = o.getSqltext();
					for (int i = 0; i < dataSet.length; i++) {
						//先删除
						sess.createSQLQuery(" delete from "+dataSet[i]+"_ztfk where ztfk_key='"+extend+"'").executeUpdate();
						//插入
						StringBuffer executer = new StringBuffer();
						executer.append(" insert into "+ dataSet[i]+"_ztfk ");
						executer.append(" select "+dataSet[i]+".*,"+extend+" from " +dataSet[i] +" where Exists(");
						executer.append(" select 1 from ("+exists+") t where t.a0000 = "+dataSet[i]+".a0000 ");
						executer.append(")");
						sess.createSQLQuery(executer.toString()).executeUpdate();
					}
				}
			trans.commit();
			return 0;
		}catch(Exception e){
			try {
				trans.rollback();
			} catch (AppException e1) {
				return -1;
			}
			return -1;
		}	
	}
	
	public static String getSelecter(ModelDB db) throws AppException{
		StringBuffer selecter = new StringBuffer();
		String select = getSelect(db.getColumns());
		String from = "";
		String where = "";
		if(DBUtil.getDBType() == DBType.ORACLE){
			 from = getFrom(db.getColumns()); 	
			 where = getCondition(db.getConditions());
		}else if(DBUtil.getDBType() == DBType.MYSQL){
			
		}else{
			throw new AppException("服务不支持当前数据库类型");
		}
		selecter.append(select + from + where);
		return selecter.toString();
	}
	
	public static String getSelect(List<Sublibrariescolumns> list){
		StringBuffer selecter = new StringBuffer("select ");
		for(int i=0;i<list.size();i++){
			Sublibrariescolumns c = list.get(i);
			selecter.append(c.getId().getInformation_set()+"."+c.getId().getInformation_set_field());
			if(i<list.size()-1){
				selecter.append(",");
			}
		}
		return selecter.toString();
	}
	public static String getFrom(List<Sublibrariescolumns> colunms){
		List<String> list = new ArrayList<String>();
		list.add("A01");//A01是驱动表
		for(Sublibrariescolumns colunm: colunms){
			String table = colunm.getId().getInformation_set();
			if(!list.contains(table)){
				list.add(table);
			}
		}
		StringBuffer from = new StringBuffer(" from ");
		StringBuffer tables = new StringBuffer();
		StringBuffer where  = new StringBuffer(" where  1 = 1 ");
		for(int i=0;i<list.size();i++){
			tables.append(list.get(i));
			if(i<list.size()-1){
				tables.append(",");
				where.append(" and "+list.get(0)+".a0000 = "+list.get(i+1)+".a0000(+) ");
			}
		}
		from.append(tables);
		from.append(where);
		return from.toString();
	}
	public static String getCondition(List<Sublibrariescondition> conditions) throws AppException{
		HashMap<String,Operator> mapper = getMapper();
		StringBuffer where = new StringBuffer();
		if(conditions.size()==0){
			return null;
		}
		where.append(" and ");
		for(int i=0;i<conditions.size();i++){
			Sublibrariescondition c = conditions.get(i);
			if("1".equals(c.getBrackets())||"3".equals(c.getBrackets())){
				where.append(" ( ");
			}
			Operator o = mapper.get(c.getCondition_operator());
			String type = getColFuture(c.getInformation_set(),c.getInformation_set_field()).getData_type();
			int p = Integer.parseInt(c.getCondition_operator());
			switch (p)
			{
				case 1 ://=
				case 2 ://<>
				case 3 ://>
				case 4 ://>=
				case 5 ://<
				case 6 ://<=
					if(type.equalsIgnoreCase("DATE")){
						where.append("to_char("+c.getInformation_set()+"."+c.getInformation_set_field()+",'yyyymmdd' )");
						where.append(" ");
						where.append(o.getLeft()+"'"+c.getField_value()+"'");
					}else if(type.equalsIgnoreCase("VARCHAR2")){
						where.append(c.getInformation_set()+"."+c.getInformation_set_field());
						where.append(" ");
						where.append(o.getLeft()+"'"+c.getField_value()+"'");
					}else{
						where.append(c.getInformation_set()+"."+c.getInformation_set_field());
						where.append(" ");
						where.append(o.getLeft()+c.getField_value());
					}		
					break;
				case 7 ://like ' %'
				case 8 ://not like ' %'
				case 9 ://like '% '
				case 10 ://not like '% '
				case 11 ://like '% %'
				case 12 ://not like '% %'
					where.append(c.getInformation_set()+"."+c.getInformation_set_field());
					where.append(" ");
					where.append(o.getLeft()+c.getField_value()+o.getRight());
					break;		
				case 13 ://is null
				case 14 :// is not null
					where.append(c.getInformation_set()+"."+c.getInformation_set_field());
					where.append(" ");
					where.append(o.getLeft());
				    break;
				default : ;
			}
			where.append(" ");
			if("2".equals(c.getBrackets())||"3".equals(c.getBrackets())){
				where.append(" ) ");
			}
			if(i<conditions.size()-1){//如果没有连接符默认是and
				where.append("".equals(c.getCondition_connector())||c.getCondition_connector()==null?" and":c.getCondition_connector()+" ");
			}
		}
		return where.toString();
	}
	
	public static HashMap<String,Operator> getMapper() throws AppException{
		HashMap<String,Operator> map = new HashMap<String,Operator>();
		String sql = " select * from v_Operator ";
		List<HashMap> list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		for(HashMap m : list){
			String key = m.get("code").toString();
			Operator o = new Operator();
			o.setCode(key);
			o.setName(m.get("name")==null?"":m.get("name").toString());
			o.setLeft(m.get("left_flag")==null?"":m.get("left_flag").toString());
			o.setRight(m.get("right_flag")==null?"":m.get("right_flag").toString());
			map.put(key, o);
		}
		return map;
	}
	/*****************
	 * 过滤重复的查询列
	 * @param list_in
	 * @return
	 */
	public static List<Sublibrariescolumns> filterColumns(List<Sublibrariescolumns> list_in){
		List<Sublibrariescolumns> list_out = new ArrayList<Sublibrariescolumns>();
		List<String> list =new ArrayList<String>();
		int j = 0;
		for(int i=0;i<list_in.size();i++){
			Sublibrariescolumns c = list_in.get(i);
			if(!list.contains(c.getId().getInformation_set_field())&&!"A0000".equalsIgnoreCase(c.getId().getInformation_set_field())){//先剔除a0000
				c.setColumns_no(j);
				list.add(c.getId().getInformation_set_field());
				list_out.add(c);
				j++;
			}
		}
		//添加a01.A0000 强制附带该列
		SublibrariescolumnsId id = new SublibrariescolumnsId(list_out.get(0).getId().getSub_libraries_model_id(),"A01","A0000");
		Sublibrariescolumns a  = new Sublibrariescolumns(id,j++);
		list_out.add(a);
		return list_out;
		
	}
	
	/*******************
	 * 判断map是否存在于list中
	 * @param list 查找源
	 * @param map  查找项
	 * @param excludeKey 剔除键
	 * @return
	 */
	public static boolean  contains(List<HashMap<String,Object>> list,HashMap<String,Object> map,String[] excludeKey){
		List<Object> tempList = new ArrayList<Object>(); 
		for(int i=0;i<excludeKey.length;i++){
			map.remove(excludeKey[i]);
		}
		for(HashMap<String,Object> m : list){
			for(int i=0;i<excludeKey.length;i++){
				m.remove(excludeKey[i]);
			}
			tempList.add(m);
		}
		return tempList.contains(map);
	}
	/*************
	 * 检测sql合法性
	 * @param sql
	 * @return
	 */
	public static String  testSQL(String sql){
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement stmt ;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			return "可执行的查询语句";
		} catch (SQLException e) {
			return "查询语句有误,请检查配置:"+e.getMessage();
		}
	}
	public static ColFuture  getColFuture(String table,String name) throws AppException{
		ColFuture future = new ColFuture();
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct information_set,information_set_field,data_type,code_type from v_tab_cols where 1=1 ");
		sb.append(" and information_set ='"+table+"'");
		sb.append(" and information_set_field ='"+name+"'");
		@SuppressWarnings("rawtypes")
		List<HashMap> list = CommonQueryBS.getQueryInfoByManulSQL(sb.toString());
		if(list.size()==1){
			HashMap map = list.get(0);
			BeanUtil.copyHashMap(map, future);
//			future.setInformation_set(map.get("information_set").toString());
//			future.setInformation_set_field(map.get("information_set_field").toString());
//			future.setData_type(map.get("data_type").toString());
//			future.setData_type(map.get("code_type")==null?"":map.get("code_type").toString());
		}
		return future;
	}
	public static String FileBuilder(List<String> list,String extname) throws Exception{
		StringBuffer filename = new StringBuffer();
		List<Config> objectlist = new ArrayList<Config>();
		for(int i=0;i<list.size();i++){
			String[] a = list.get(i).split("-");
			filename.append(a[1]);
			if(i<list.size()-1){
				filename.append("_");
			}
			Config conf = LoadConfig(a[0]);
			objectlist.add(conf);
		}
		Configs  configs = new Configs(objectlist);
		String dir = DOWNLOAD_PATH;
		if(sys.startsWith("WIN")){
			dir = DOWNLOAD_PATH_WIN;
	    }
		String content ="";
		if(".json".equals(extname)){
			content = JsonContenBuilder(configs);
		}
		if(".xml".equals(extname)){
			content = XmlContentBuilder(configs);
		}
		File file = FileUtil.createFile(dir, filename.toString(),extname);
		FileUtil.writeFile(file, content);
		return file.getName();
	}
	public static String XmlContentBuilder(Object o) throws Exception{
		//return JXUtil.Object2Xml(o);
		return JXUtil.Object2Xml(o, true, "GBK");
	}
	public static String JsonContenBuilder(Object o) throws Exception{
		return JsonUtil.object2Json(o).toString();
	}
	public static String JsonContenBuilder1(List<?> list) throws Exception{
		JSONArray  contentArr = new JSONArray();
		for(int i=0;i<list.size();i++){
			Object conf = list.get(i);
			JSONObject json =JsonUtil.object2Json(conf);
			contentArr.put(json);
		}
		return contentArr.toString();
	}
	public static void FileDistroy(String name) throws Exception{
		String dir = DOWNLOAD_PATH;
		if(sys.startsWith("WIN")){
			dir = DOWNLOAD_PATH_WIN;
	    }
		String fullpath = dir+name+".json"; 
		File file = new File(fullpath);
		if (file.exists()) {
			file.delete();
		} 
		
	}
	public static void SaveConfigs(Configs configs) throws Exception{
		for(Config config : configs.getConfig()){
			SaveConfig(config);
		}
		
	}
	public static void SaveConfig(Config conf) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans= sess.beginTransaction();
		try{
			Sublibrariesmodel model = new Sublibrariesmodel();
			BeanUtil.copy(conf.getModel(), model);
			model.setCreate_time(DateUtil.getTimestamp());
			model.setChange_time(DateUtil.getTimestamp());
			model.setChange_user_id(SysUtil.getCacheCurrentUser().getId());
			model.setChange_user_name(SysUtil.getCacheCurrentUser().getName());
			model.setSub_libraries_model_id(UUID.randomUUID().toString());
			Long l = System.currentTimeMillis();
			model.setSub_libraries_model_key(l.toString());
			model.setRun_state("0");
			sess.save(model);
			for(int i=0;i<conf.getColumns().size();i++){
				Sublibrariescolumns c = new Sublibrariescolumns();
				SublibrariescolumnsId id = new SublibrariescolumnsId();
				BeanUtil.copy(conf.getColumns().get(i).getId(), id);
				id.setSub_libraries_model_id(model.getSub_libraries_model_id());
				c.setColumns_no(conf.getColumns().get(i).getColumns_no());
				c.setId(id);
				sess.save(c);
			}
			for(int i=0;i<conf.getConditions().size();i++){
				Sublibrariescondition c = new Sublibrariescondition();
				BeanUtil.copy(conf.getConditions().get(i), c);
				c.setSub_libraries_model_id(model.getSub_libraries_model_id());
				sess.save(c);
			}
			trans.commit();
		}catch(Exception e){
			trans.rollback();
			throw new Exception(e.getMessage());
		}
	}
	public static void SaveGrant(String[] privileges,List<String> userlist) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<privileges.length;i++){
			
			//记录日志使用 mengl
			List<Sysmodelrole> oldSysmodelroles =  new LinkedList<Sysmodelrole>();//被删除的授权
			List<Sysmodelrole> newSysmodelroles =  new LinkedList<Sysmodelrole>();//新增的授权
			
			List<Sysmodelrole> srList = sess.createQuery("from Sysmodelrole where sublibrariesmodelid = '"+privileges[i].trim()+"'").list();
			for(Sysmodelrole sr :srList){
				sess.delete(sr);
				
				oldSysmodelroles.add(sr);
			}
			
//			sess.createQuery("delete from Sysmodelrole where sublibrariesmodelid = '"+privileges[i].trim()+"'").executeUpdate();
			for(String user : userlist){
				Sysmodelrole sys =  new Sysmodelrole();
				sys.setUserid(user);
				sys.setSublibrariesmodelid(privileges[i].trim());
				sess.saveOrUpdate(sys);
				
				newSysmodelroles.add(sys);
			}
			
			//记录日志 mengl 20160628
			//获取改变信息
			List<String[]> changesList = new ArrayList<String[]>();
			
			Sysmodelrole srNew = new Sysmodelrole();//空对象，新对象比对为空时使用
				//循环次数，取最大值
			int size = oldSysmodelroles.size()>newSysmodelroles.size()?oldSysmodelroles.size():newSysmodelroles.size();
			for(int x = 0;x<size;x++){
				//超过List范围，则赋值为空对象来比较
				Sysmodelrole odlSr = oldSysmodelroles.size()>x && oldSysmodelroles.get(x)!=null?oldSysmodelroles.get(x):srNew;
				Sysmodelrole newSr = newSysmodelroles.size()>x && newSysmodelroles.get(x)!=null?newSysmodelroles.get(x):srNew ;
				changesList.addAll( Map2Temp.getLogInfo(odlSr,newSr));
			}
			
				//排除主键改变因素
			Iterator<String[]> it = changesList.iterator();
			while(it.hasNext()){
				String[] changeItem = it.next();
				if(changeItem!=null && changeItem.length>0 && "MODELROLEID".equalsIgnoreCase(changeItem[0])){
					it.remove();
				}
			}
			
			//记录日志:授权主题分库
			try {
				new LogUtil().createLog("653", "SUB_LIBRARIES_MODEL",
						privileges[i].trim(),
						((Sublibrariesmodel)(sess.get(Sublibrariesmodel.class, privileges[i].trim()))).getSub_libraries_model_name(), null,changesList
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 获取表中所有列，以【列1,列2,列3...】形式返回
	 * @author mengl
	 * @return
	 * @throws AppException 
	 */
	public static String getColumns(String tabName) throws AppException{
		String columns = "";
		HBSession sess = HBUtil.getHBSession();
		String sql = null;
		if(DBType.MYSQL == DBUtil.getDBType() ){
			sql = "select group_concat(column_name) from information_schema.columns a where table_name = upper('"+tabName+"') and a.TABLE_SCHEMA = 'ZWHZYQ'";
		}else if(DBType.ORACLE == DBUtil.getDBType()){
			sql = "Select To_Char(Wmsys.Wm_Concat(column_name)) From User_Col_Comments a Where Table_Name =upper('"+tabName+"')";
		}
		
		List list = sess.createSQLQuery(sql).list();
		if(list==null || list.size()<1 || list.get(0)==null){
			throw new AppException("获取【"+tabName+"】表列信息失败！");
		}else{
			columns = list.get(0).toString();
		}
		
		return columns;
	}
	
	/**
	 *  为List<HashMap<String,Object>>类型的Map中表（information_set）、列（information_set_field）分别设定汉字名称
	 */
	public static List<HashMap<String,Object>> putComments4MapList(List<HashMap<String,Object>> list){
		if(list==null || list.size()<1 ){
			return list;
		}
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String,Object> map : list){
			String information_set = (String) map.get("information_set");
			String information_set_field = (String) map.get("information_set_field");
			if(!StringUtil.isEmpty(information_set)){
				map.put("information_set_comment", RuleSqlListBS.getTableName(information_set));
			}
			if(!StringUtil.isEmpty(information_set_field)){
				map.put("information_set_field_comment", RuleSqlListBS.getColName(information_set,information_set_field));
			}
			newList.add(map);
		}
		
		return newList;
	}

}