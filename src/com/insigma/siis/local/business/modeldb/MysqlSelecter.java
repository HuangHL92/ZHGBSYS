package com.insigma.siis.local.business.modeldb;

import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.siis.local.business.entity.Sublibrariescolumns;
import com.insigma.siis.local.business.entity.Sublibrariescondition;

/******************
 * @category MySQL数据库查询语句生成器的实现类
 * @author 李小宁
 *
 */
public class MysqlSelecter implements ISelecter {
	
	public MysqlSelecter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSelecter(ModelDB db) throws AppException {
		StringBuffer selecter = new StringBuffer();
		String select = getSelect(db.getColumns());
		String from = getFrom(db.getColumns(),db.getConditions()); 
		String where = getCondition(db.getConditions());
		selecter.append(select + from + where);
		return selecter.toString();
	}
	public  String getSelect(List<Sublibrariescolumns> list){
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
	public  String getFrom(List<Sublibrariescolumns> colunms,List<Sublibrariescondition> conditions){
		List<String> list = new ArrayList<String>();
		list.add("A01");//A01是驱动表
		//查询列中的表
		for(Sublibrariescolumns colunm: colunms){
			String table = colunm.getId().getInformation_set();
			if(!list.contains(table)){
				list.add(table);
			}
		}
		//查询条件中的表
		for(Sublibrariescondition condition: conditions){
			String table = condition.getInformation_set();
			if(!list.contains(table)){
				list.add(table);
			}
		}
		StringBuffer from = new StringBuffer(" from "+list.get(0)+" ");
		for(int i=1;i<list.size();i++){
			from.append(" left join "+list.get(i)+" on "+list.get(0)+".a0000 = "+list.get(i)+".a0000 ");
		}
		return from.toString();
	}
	public String getCondition(List<Sublibrariescondition> conditions) throws AppException{
		StringBuffer where = new StringBuffer(" where 1 = 1 ");
		if(conditions.size()==0){
			return null;
		}
		where.append(" and ");
		for(int i=0;i<conditions.size();i++){
			Sublibrariescondition c = conditions.get(i);
			if("1".equals(c.getBrackets())||"3".equals(c.getBrackets())){
				where.append(" ( ");
			}
			Operator o = ModeldbBS.getMapper().get(c.getCondition_operator());
			ColFuture f =  ModeldbBS.getColFuture(c.getInformation_set(),c.getInformation_set_field());
			int p = Integer.parseInt(c.getCondition_operator());
			switch (p)
			{
				case 1 ://=
				case 2 ://<>
				case 3 ://>
				case 4 ://>=
				case 5 ://<
				case 6 ://<=
					if(f.getData_type().equalsIgnoreCase("DATE")){
						String df = getFmt(c.getField_value());
						if(!"A01".equals(f.getInformation_set())){
							where.append("(");
						}
						where.append("date_format(");
						where.append(c.getInformation_set()+"."+c.getInformation_set_field());
						where.append(",'"+df+"')  ");
						where.append(o.getLeft()+"'"+c.getField_value()+"'");
						if(!"A01".equals(f.getInformation_set())){
							where.append("  or "+f.getInformation_set()+".a0000 is null)");
						}
						
					}else if(f.getData_type().equalsIgnoreCase("VARCHAR2")){
						if(!"A01".equals(f.getInformation_set())){
							where.append("(");
						}
						where.append(c.getInformation_set()+"."+c.getInformation_set_field());
						where.append(" ");
						where.append(o.getLeft()+"'"+c.getField_value()+"'");
						if(!"A01".equals(f.getInformation_set())){
							where.append("  or "+f.getInformation_set()+".a0000 is null)");
						}
					}else{
						if(!"A01".equals(f.getInformation_set())){
							where.append("(");
						}
						where.append(c.getInformation_set()+"."+c.getInformation_set_field());
						where.append(" ");
						where.append(o.getLeft()+c.getField_value());
						if(!"A01".equals(f.getInformation_set())){
							where.append("  or "+f.getInformation_set()+".a0000 is null)");
						}
					}		
					break;
				case 7 ://like ' %'
				case 8 ://not like ' %'
				case 9 ://like '% '
				case 10 ://not like '% '
				case 11 ://like '% %'
				case 12 ://not like '% %'
					if(!"A01".equals(f.getInformation_set())){
						where.append("(");
					}
					where.append(c.getInformation_set()+"."+c.getInformation_set_field());
					where.append(" ");
					where.append(o.getLeft()+c.getField_value()+o.getRight());
					if(!"A01".equals(f.getInformation_set())){
						where.append("  or "+f.getInformation_set()+".a0000 is null)");
					}
					break;		
				case 13 ://is null
				case 14 :// is not null
					if(!"A01".equals(f.getInformation_set())){
						where.append("(");
					}
					where.append(c.getInformation_set()+"."+c.getInformation_set_field());
					where.append(" ");
					where.append(o.getLeft());
					if(!"A01".equals(f.getInformation_set())){
						where.append("  or "+f.getInformation_set()+".a0000 is null)");
					}
				    break;
				default : ;
			}
			where.append(" ");
			if("2".equals(c.getBrackets())||"3".equals(c.getBrackets())){
				where.append(" ) ");
			}
			if(i<conditions.size()-1){//如果没有连接符默认是and
				where.append("".equals(c.getCondition_connector())||c.getCondition_connector()==null?" and ":c.getCondition_connector()+" ");
			}
		}
		return where.toString();
	}
	public String getFmt(String s){
		switch (s.length()){
			case 4 : 
				return  "%Y";
			case 6 : 
				return  "%Y%m";
			case 7 : 
				return  "%Y-%m";	
			case 8 : 
				return  "%Y%m%d";
			case 10 :
				return  "%Y-%m-%d";
			case 17 :
				return  "%Y%m%d %H:%i:%s";
			case 19 :
				return  "%Y-%m-%d %H:%i:%s";
			default : 
				return null;
		}
	} 

}
