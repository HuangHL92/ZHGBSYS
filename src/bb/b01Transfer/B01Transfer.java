package bb.b01Transfer;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
/**
 * damengb01机构编码转换 uuid转001.001
 * @author epsoft
 *
 */
public class B01Transfer {

	public static void main(String[] args) throws Exception {
		/*String photoName = "asdsx.dsa";
		photoName = photoName.substring(0,photoName.lastIndexOf("."));
		System.out.println(photoName);*/
		Connection conn = getOracleConn();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select dmcod,dmparentcod,replace(inpfrq,'-','') inpfrq from B01_GROUP1_HIBER t where t.inpfrq in('001','004','003','002') and t.dmparentlev='1'");
		int i = 0;
		while(rs.next()){
			String b00 = rs.getString("dmcod");
			String b0144b = rs.getString("dmparentcod");
			String inpfrq = rs.getString("inpfrq");
			String type = "1";
			String b0121_o = b0144b;
			String b0111 = "";
			if("41C7736D-9089-4517-A6D1-3ADD2BE77235".equals(b00)){
				b0111 = "001.001.001";
			}else if("9D98A533-572F-4B25-8793-BC90FEEA025A".equals(b00)){
				b0111 = "001.001.002";
			}else if("250B7541-3E68-46D9-9EF7-96F03C7344DF".equals(b00)){
				b0111 = "001.001.003";
			}else if("886115D2-F7B6-4509-A16A-AF34606B370F".equals(b00)){
				b0111 = "001.001.004";
			}
			
			System.out.println(b00);
			conn.createStatement().executeUpdate("insert into b01_transf_info(b0111_o,b0111,b0121_o,b0121,type,sort) values('"+b00+"','"+b0111+"','"+b0121_o+"','-1','"+type+"',"+inpfrq+")");
			execChildren(b00,conn,b0111,type);
			
		}
		rs.close();
		st.close();
	}
	
	
	
	private static void execChildren(String b00p, Connection conn, String b0121, String type2) throws Exception {
		Statement st = conn.createStatement();
		
		ResultSet rs = st.executeQuery("select dmcod,dmparentcod,replace(inpfrq,'-','') inpfrq from B01_GROUP1_HIBER t where t.dmparentcod ='"+b00p+"' and t.dmparentlev='1'");
		while(rs.next()){
			String b00 = rs.getString("dmcod");
			String b0144b = rs.getString("dmparentcod");
			String sort = rs.getString("inpfrq");
			String type = "2";
			String b0121_o = b0144b;
			String b0111 = selectB0111BySubId(b0121,conn);
			conn.createStatement().executeUpdate("insert into b01_transf_info(b0111_o,b0111,b0121_o,b0121,type,sort) values('"+b00+"','"+b0111+"','"+b0121_o+"','"+b0121+"','"+type+"',"+sort+")");
			execChildren(b00,conn,b0111,type);
			System.out.println(b00);
		}
		rs.close();
		st.close();
		
	}
	
	
	
	
	
	
	
	
	/*private static void execChildren(String b00p, Connection conn, String b0121, String type2) throws Exception {
		Statement st = conn.createStatement();
		String parentCol = "b0144b";
		if("2".equals(type2)){
			parentCol = "zdyxb0114";
		}
		
		ResultSet rs = st.executeQuery("select b00,t.b0101,b0144b,t.zdyxb0114 from B01 t where t."+parentCol+" ='"+b00p+"'");
		while(rs.next()){
			String b00 = rs.getString("b00");
			String b0144b = rs.getString("b0144b");
			String zdyxb0114 = rs.getString("zdyxb0114");
			String type = "2";
			String b0121_o = b0144b;
			if(StringUtils.isEmpty(b0144b)){
				type = "1";
				b0121_o = zdyxb0114;
			}
			String b0111 = selectB0111BySubId(b0121,conn);
			conn.createStatement().executeUpdate("insert into b01_transf_info(b0111_o,b0111,b0121_o,b0121,type) values('"+b00+"','"+b0111+"','"+b0121_o+"','"+b0121+"','"+type+"')");
			execChildren(b00,conn,b0111,type);
			System.out.println(b00);
		}
		rs.close();
		st.close();
		
	}*/
	public static Connection getOracleConn(){
		//设置oracle信息
		String forname = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "dameng";
		String password = "dameng";
		Connection con = null;
		try {
			Class.forName(forname).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	// 通过上级编码 生成主键
	public static String selectB0111BySubId(String subId, Connection conn) throws Exception {
		if (subId.equals("-1")) {
			return "001.001";
		} else {
			String sql = "select max(substr(t.b0111,-3,3)) aa from  b01_transf_info t where t.B0121='"
					+ subId.trim() + "'";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			Object substrb0111 = null;
			if(rs.next()){
				substrb0111 = rs.getString("aa");
			}
			if (substrb0111 != null) {
				String b0111 = getB0111(substrb0111.toString());
				b0111=getB0111Three(subId,b0111,conn);//去重 
				return subId + "." + b0111;
			} else {
				return subId + ".001";
			}
		}
	}
	public static String getB0111Three(String subId,String b0111, Connection conn) throws Exception{
		ResultSet rs = conn.createStatement().executeQuery("select count(*) aa from b01_transf_info where b0111='"+subId + "." + b0111+"' ");
		String tmp = null;
		if(rs.next()){
			tmp = rs.getString("aa");
		}
		
		if(tmp.equals("1")){//主键重复、重新生成
			//继续+1
			b0111 = getB0111(b0111);
			b0111=getB0111Three(subId,b0111, conn);
			return b0111;
		}else{
			return b0111;
		}
		
	}
	
	// 主键生成方式
	public static String getB0111(String str) throws RadowException {
		str = str.toUpperCase();
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		// System.out.println(str.substring(2));//个位数
		// System.out.println(str.substring(1,2));//十位数
		// System.out.println(str.substring(0,1));//百位数
		// 如果个位数不为Z 那么+1
		if (!str.substring(2).equals("Z")) {
			for (int i = 0; i < key.length; i++) {
				if (key[i].equals(str.substring(2))) {
					return str.substring(0, 2) + key[i + 1];
				}
			}
		} else {
			// 个位是Z 十位数不是Z,那么十位数+1,个位置变为0
			if (!str.substring(1, 2).equals("Z")) {
				for (int i = 0; i < key.length; i++) {
					if (key[i].equals(str.substring(1, 2))) {
						return str.substring(0, 1) + key[i + 1] + "0";
					}
				}
			} else {
				// 个位是Z 十位数是Z,那么十位数+1,个位置变为0
				for (int i = 0; i < key.length; i++) {
					if (key[i].equals(str.substring(0, 1))) {
						return key[i + 1] + "00";
					}
				}
			}
		}
		throw new RadowException("该机构主键不规范："+str);
	}

	
	
	static String sourceDisk = "D:/hzphotos";
	/**
	 * 照片分发
	 * @throws Exception
	 */
	public static void deployPhotos() throws Exception{
		
		HBSession sess = HBUtil.getHBSession();
		File f = new File(sourceDisk);
		Map<String, String> m = new HashMap<String, String>();
		for(File photo : f.listFiles()){
			A57 a57 = new A57();
			String photoName = photo.getName();
			
			photoName = photoName.substring(0,photoName.lastIndexOf(".")).replaceAll("\\{|\\}", "").replaceAll("temp", "");
			
			if("1".equals(m.get(photoName))){
				continue;
			}
			m.put(photoName, "1");
			
			A01 a01 = (A01)sess.get(A01.class, photoName);
			
			if(a01!=null){
				a57.setA0000(photoName);
				System.out.println(photoName);
				byte[] data = new byte[(int)photo.length()];
				FileInputStream inStream = new FileInputStream(photo); 
				inStream.read(data);
				inStream.close();
				PhotosUtil.savePhotoData(a57, sess, data);
				
			}
			
			
		}
	}
	
	
}
