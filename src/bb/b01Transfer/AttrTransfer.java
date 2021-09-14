package bb.b01Transfer;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.zj.tags.A0193TagsAddPagePageModel;
/**
 * dameng 标签转吗
 * @author epsoft
 *
 */
public class AttrTransfer {

	public static void main(String[] args) throws Exception {
	
		Connection conn = getOracleConn();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select a00,zdyxa0197,zdyxa0193  from dameng.a01 t where t.zdyxa0197 is not null or t.zdyxa0193 is not null");
		while(rs.next()){
			String a00 = rs.getString("a00");
			String zdyxa0197 = rs.getString("zdyxa0197");
			String zdyxa0193 = rs.getString("zdyxa0193");
			StringBuilder sbCol = new StringBuilder();
			StringBuilder sbVal = new StringBuilder();
			StringBuilder a0193z = new StringBuilder();
			StringBuilder a0194z = new StringBuilder();
			StringBuilder a0194s = new StringBuilder();
			if(!StringUtils.isEmpty(zdyxa0197)){
				String[] cols = zdyxa0197.split(",");
				for(int i=0;i<cols.length;i++){
					String col = cols[i];
					if(!StringUtils.isEmpty(col)){
						a0194z.append(A0193TagsAddPagePageModel.attrMap.get("attr"+col)+"，");
						sbCol.append("attr"+col+",");
						sbVal.append("'1',");
					}
				}
				if(a0194z.length()>0){
					a0194z.deleteCharAt(a0194z.length()-1);
				}
			}
			if(!StringUtils.isEmpty(zdyxa0193)){
				String[] cols = zdyxa0193.split(",");
				for(int i=0;i<cols.length;i++){
					String col = cols[i];
					if(!StringUtils.isEmpty(col)){
						a0193z.append(A0193TagsAddPagePageModel.attrMap.get("attr"+col)+"，");
						sbCol.append("attr"+col+",");
						sbVal.append("'1',");
					}
				}
				if(a0193z.length()>0){
					a0193z.deleteCharAt(a0193z.length()-1);
				}
			}
			
			if(sbCol.length()>0){
				sbCol.deleteCharAt(sbCol.length()-1);
				sbVal.deleteCharAt(sbVal.length()-1);
				Statement st2 = conn.createStatement();
				st2.executeUpdate("insert into ATTR_LRZW(a0000,"+sbCol+") "
						+ "values('"+a00+"',"+sbVal+")");
				st2.close();


				PreparedStatement st3 = conn.prepareStatement("insert into extra_tags(a0000,a0193z,a0194z,a0194zcode) "
						+ "values('"+a00+"',?,?,?)");
				st3.setString(1, a0193z.toString());
				st3.setString(2, a0194z.toString());
				st3.setString(3, zdyxa0197);
				st3.execute();
				st3.close();
				
				
				
				
				
			}
			
		}
		rs.close();
		st.close();
	}
	

	
	public static Connection getOracleConn(){
		//设置oracle信息
		String forname = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "ZHGBSYS";
		String password = "ZHGBSYS";
		Connection con = null;
		try {
			Class.forName(forname).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	
	
}
