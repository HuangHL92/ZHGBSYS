package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.insigma.siis.local.business.helperUtil.DateUtil;

public class Data2Long implements UserType{
	private static final int[] SQL_TYPES={Types.DATE}; 
	@Override
	public Object assemble(Serializable serializable, Object obj)
			throws HibernateException {
		return null;
	}

	@Override
	public Object deepCopy(Object obj) throws HibernateException {
		return obj; 
	}

	@Override
	public Serializable disassemble(Object obj) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if(x==y){  
            return true;  
        }else if(x==null||y==null){  
            return false;  
        }else {  
            return x.equals(y);  
        }  
	}

	@Override
	public int hashCode(Object obj) throws HibernateException {
		return obj.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet resultset, String[] as, Object obj)
			throws HibernateException, SQLException {
		String data = resultset.getString(as[0]); 
		if(resultset.wasNull()){  
            return null;  
        }else{  
            //Timestamp ts = new Timestamp(System.currentTimeMillis());  
            if(data==null){
            	return null;
            }
            Timestamp ts = Timestamp.valueOf(data);
            return ts.getTime();  
        }  
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedstatement, Object obj,
			int i) throws HibernateException, SQLException {
		if(obj==null){  
			preparedstatement.setNull(i, Types.TIMESTAMP);  
        }else {  
        	Timestamp ts = new Timestamp((Long)obj);
            preparedstatement.setTimestamp(i, ts);  
        }
		
	}

	@Override
	public Object replace(Object obj, Object obj1, Object obj2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class returnedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return SQL_TYPES;
	}

	

	

}
