package com.insigma.siis.local.pagemodel.audit;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.PersonAuditInfo;


public class AuditPersonInfoPageModel extends PageModel {
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//this.getExecuteSG().addExecuteCode("hideCol();");
		String oid = this.getPageElement("subWinIdBussessId").getValue();
		PersonAuditInfo api= (PersonAuditInfo) sess.get(PersonAuditInfo.class,oid);

		String a0184=api.getA0184();
		this.getPageElement("oid").setValue(api.getOid());
		this.getPageElement("a0000").setValue(api.getA0000());
		this.getPageElement("a0101").setValue(api.getA0101());
		this.getPageElement("a0184").setValue(api.getA0184());
		this.getPageElement("a0192a").setValue(api.getA0192a());
		this.getPageElement("audit_type").setValue(api.getAuditType());

		//if(user.getDept().equals("001")||user.getDept().equals("0")) {
			this.getPageElement("auditResult").setValue(api.getAuditResult());
			this.getPageElement("auditDetails").setValue(api.getAuditDetails());
			this.getPageElement("auditRemark").setValue(api.getAuditRemark());
		//}
		/*// 一、省纪委省监委 19

			JwAuditInfo  jwai=null;
			List<JwAuditInfo> jwais = sess.createQuery("from JwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
			if (jwais != null && !jwais.isEmpty()) {
				jwai = jwais.get(0);
				this.getPageElement("p1001").setValue(jwai.getP1001());
				this.getPageElement("p1002").setValue(jwai.getP1002());
				this.getPageElement("p1003").setValue(jwai.getP1003());
				this.getPageElement("p1004").setValue(jwai.getP1004());
				this.getPageElement("p1005").setValue(jwai.getP1005());
				this.getPageElement("p10auresult").setValue(jwai.getAuditResult());
				this.getPageElement("p10auremark").setValue(jwai.getAuditRemark());
			}
		//二、省委组织部 （一）干部综合处 91
			ZzbGbAuditInfo zzbgbai = null;
			List<ZzbGbAuditInfo> zzbgbais = sess.createQuery("from ZzbGbAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
			if (zzbgbais != null && !zzbgbais.isEmpty()) {
				zzbgbai = zzbgbais.get(0);
				this.getPageElement("p20101").setValue(zzbgbai.getP20101());
				this.getPageElement("p20102").setValue(zzbgbai.getP20102());
				this.getPageElement("p201auresult").setValue(zzbgbai.getAuditResult());
				this.getPageElement("p201auremark").setValue(zzbgbai.getAuditRemark());
			}
		//二、省委组织部 （二）干部监督室
			ZzbJdAuditInfo zzbjdai = null;
			List<ZzbJdAuditInfo> zzbjdais = sess.createQuery("from ZzbJdAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
			if (zzbjdais != null && !zzbjdais.isEmpty()) {
				zzbjdai = zzbjdais.get(0);
				this.getPageElement("p20201").setValue(zzbjdai.getP20201());
				this.getPageElement("p20202").setValue(zzbjdai.getP20202());
				this.getPageElement("p20203").setValue(zzbjdai.getP20203());
				this.getPageElement("p20204").setValue(zzbjdai.getP20204());
				this.getPageElement("p202auresult").setValue(zzbjdai.getAuditResult());
				this.getPageElement("p202auremark").setValue(zzbjdai.getAuditRemark());
			}
		//三、省信访局 31
			XfAuditInfo xfai = null;
			List<XfAuditInfo> xfais = sess.createQuery("from XfAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
			if (xfais != null && !xfais.isEmpty()) {
				xfai = xfais.get(0);
				this.getPageElement("p3001").setValue(xfai.getP3001());
				this.getPageElement("p3002").setValue(xfai.getP3002());
				this.getPageElement("p3003").setValue(xfai.getP3003());
				this.getPageElement("p30auresult").setValue(xfai.getAuditResult());
				this.getPageElement("p30auremark").setValue(xfai.getAuditRemark());
			}
		//四、省法院
		FyAuditInfo fyai=null;
		List<FyAuditInfo> fyais = sess.createQuery("from FyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (fyais != null && !fyais.isEmpty()) {
			fyai = fyais.get(0);
			this.getPageElement("p4001").setValue(fyai.getP4001());
			this.getPageElement("p4002").setValue(fyai.getP4002());
			this.getPageElement("p4003").setValue(fyai.getP4003());
			this.getPageElement("p4004").setValue(fyai.getP4004());
			this.getPageElement("p40auresult").setValue(fyai.getAuditResult());
			this.getPageElement("p40auremark").setValue(fyai.getAuditRemark());
		}

		//五、省检察院
		JcyAuditInfo jcyai=null;
		List<JcyAuditInfo> jcyais = sess.createQuery("from JcyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (jcyais != null && !jcyais.isEmpty()) {
			jcyai = jcyais.get(0);
			this.getPageElement("p5001").setValue(jcyai.getP5001());
			this.getPageElement("p5002").setValue(jcyai.getP5002());
			this.getPageElement("p5003").setValue(jcyai.getP5003());
			this.getPageElement("p50auresult").setValue(jcyai.getAuditResult());
			this.getPageElement("p50auremark").setValue(jcyai.getAuditRemark());
		}

		//六、省发改委
		FgwAuditInfo fgwai=null;
		List<FgwAuditInfo> fgwais = sess.createQuery("from FgwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (fgwais != null && !fgwais.isEmpty()) {
			fgwai = fgwais.get(0);
			this.getPageElement("p6001").setValue(fgwai.getP6001());
			this.getPageElement("p6002").setValue(fgwai.getP6002());
			this.getPageElement("p6003").setValue(fgwai.getP6003());
			this.getPageElement("p60auresult").setValue(fgwai.getAuditResult());
			this.getPageElement("p60auremark").setValue(fgwai.getAuditRemark());
		}

		// 七、省公安厅
		GaAuditInfo gaai=null;
		List<GaAuditInfo> gaais = sess.createQuery("from GaAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (gaais != null && !gaais.isEmpty()) {
			gaai = gaais.get(0);
			this.getPageElement("p7001").setValue(gaai.getP7001());
			this.getPageElement("p7002").setValue(gaai.getP7002());
			this.getPageElement("p7003").setValue(gaai.getP7003());
			this.getPageElement("p7004").setValue(gaai.getP7004());
			this.getPageElement("p7005").setValue(gaai.getP7005());
			this.getPageElement("p7006").setValue(gaai.getP7006());
			this.getPageElement("p7007").setValue(gaai.getP7007());
			this.getPageElement("p70auresult").setValue(gaai.getAuditResult());
			this.getPageElement("p70auremark").setValue(gaai.getAuditRemark());
		}


		// 八、省人力社保厅
		RlsbAuditInfo rlsbai=null;
		List<RlsbAuditInfo> rlsbais = sess.createQuery("from RlsbAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (rlsbais != null && !rlsbais.isEmpty()) {
			rlsbai = rlsbais.get(0);
			this.getPageElement("p8001").setValue(rlsbai.getP8001());
			this.getPageElement("p8002").setValue(rlsbai.getP8002());
			this.getPageElement("p80auresult").setValue(rlsbai.getAuditResult());
			this.getPageElement("p80auremark").setValue(rlsbai.getAuditRemark());
		}
		// 九、省自然资源厅
		ZrzyAuditInfo zrzyai=null;
		List<ZrzyAuditInfo> zrzyais = sess.createQuery("from ZrzyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (zrzyais != null && !zrzyais.isEmpty()) {
			zrzyai = zrzyais.get(0);
			this.getPageElement("p9001").setValue(zrzyai.getP9001());
			this.getPageElement("p9002").setValue(zrzyai.getP9002());
			this.getPageElement("p90auresult").setValue(zrzyai.getAuditResult());
			this.getPageElement("p90auremark").setValue(zrzyai.getAuditRemark());
		}

		// 十、省生态环境厅
		SthjAuditInfo sthjai=null;
		List<SthjAuditInfo> sthjais = sess.createQuery("from SthjAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (sthjais != null && !sthjais.isEmpty()) {
			sthjai = sthjais.get(0);
			this.getPageElement("p10001").setValue(sthjai.getP10001());
			this.getPageElement("p10002").setValue(sthjai.getP10002());
			this.getPageElement("p100auresult").setValue(sthjai.getAuditResult());
			this.getPageElement("p100auremark").setValue(sthjai.getAuditRemark());
		}
		//十一、省卫健委
		WjwAuditInfo wjwai=null;
		List<WjwAuditInfo> wjwais = sess.createQuery("from WjwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (wjwais != null && !wjwais.isEmpty()) {
			wjwai = wjwais.get(0);
			this.getPageElement("p11001").setValue(wjwai.getP11001());
			this.getPageElement("p11002").setValue(wjwai.getP11002());
			this.getPageElement("p110auresult").setValue(wjwai.getAuditResult());
			this.getPageElement("p110auremark").setValue(wjwai.getAuditRemark());
		}
		//十二、省应急管理厅
		YjglAuditInfo yjglai=null;
		List<YjglAuditInfo> yjglais = sess.createQuery("from YjglAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (yjglais != null && !yjglais.isEmpty()) {
			yjglai = yjglais.get(0);
			this.getPageElement("p12001").setValue(yjglai.getP12001());
			this.getPageElement("p12002").setValue(yjglai.getP12002());
			this.getPageElement("p120auresult").setValue(yjglai.getAuditResult());
			this.getPageElement("p120auremark").setValue(yjglai.getAuditRemark());
		}
		//十三、省审计厅
		SjAuditInfo sjai=null;
		List<SjAuditInfo> sjais = sess.createQuery("from SjAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (sjais != null && !sjais.isEmpty()) {
			sjai = sjais.get(0);
			this.getPageElement("p13001").setValue(sjai.getP13001());
			this.getPageElement("p13002").setValue(sjai.getP13002());
			this.getPageElement("p13003").setValue(sjai.getP13003());
			this.getPageElement("p130auresult").setValue(sjai.getAuditResult());
			this.getPageElement("p130auremark").setValue(sjai.getAuditRemark());
		}
		//十四、省市场监管局
		ScjgAuditInfo  scjgai=null;
		List<ScjgAuditInfo > scjgais = sess.createQuery("from ScjgAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (scjgais != null && !scjgais.isEmpty()) {
			scjgai = scjgais.get(0);
			this.getPageElement("p14001").setValue(scjgai.getP14001());
			this.getPageElement("p14002").setValue(scjgai.getP14002());
			this.getPageElement("p14003").setValue(scjgai.getP14003());
			this.getPageElement("p140auresult").setValue(scjgai.getAuditResult());
			this.getPageElement("p140auremark").setValue(scjgai.getAuditRemark());
		}
		//十五、省统计局
		TjAuditInfo  tjai=null;
		List<TjAuditInfo > tjais = sess.createQuery("from TjAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (tjais != null && !tjais.isEmpty()) {
			tjai = tjais.get(0);
			this.getPageElement("p15001").setValue(tjai.getP15001());
			this.getPageElement("p15002").setValue(tjai.getP15002());
			this.getPageElement("p150auresult").setValue(tjai.getAuditResult());
			this.getPageElement("p150auremark").setValue(tjai.getAuditRemark());
		}


		//十六、省总工会
		GhAuditInfo  ghai=null;
		List<GhAuditInfo > ghais = sess.createQuery("from GhAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (ghais != null && !ghais.isEmpty()) {
			ghai = ghais.get(0);
			this.getPageElement("p16001").setValue(ghai.getP16001());
			this.getPageElement("p16002").setValue(ghai.getP16002());
			this.getPageElement("p160auresult").setValue(ghai.getAuditResult());
			this.getPageElement("p160auremark").setValue(ghai.getAuditRemark());
		}

		//十七、国家税务总局浙江省税务局
		SwAuditInfo  swai=null;
		List<SwAuditInfo > swais = sess.createQuery("from SwAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (swais != null && !swais.isEmpty()) {
			swai = swais.get(0);
			this.getPageElement("p17001").setValue(swai.getP17001());
			this.getPageElement("p17002").setValue(swai.getP17002());
			this.getPageElement("p170auresult").setValue(swai.getAuditResult());
			this.getPageElement("p170auremark").setValue(swai.getAuditRemark());
		}*/

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveLsDatas")
	public int savePerson(String confirm)throws RadowException, AppException, ParseException, SQLException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

		HBSession sess = HBUtil.getHBSession();
		String oid=this.getPageElement("oid").getValue();
		String a0184=this.getPageElement("a0184").getValue();
		String a0000=this.getPageElement("a0000").getValue();


		String auditResult=this.getPageElement("auditResult").getValue();
		String auditDetails=this.getPageElement("auditDetails").getValue();
		String auditRemark=this.getPageElement("auditRemark").getValue();

		PersonAuditInfo api= (PersonAuditInfo) sess.get(PersonAuditInfo.class,oid);
		if(api!=null){
			api.setAuditResult(auditResult);
			api.setAuditDetails(auditDetails);
			api.setAuditRemark(auditRemark);
		}

		String p1001= this.getPageElement("p1001").getValue();
		String p1002= this.getPageElement("p1002").getValue();
		String p1003= this.getPageElement("p1003").getValue();
		String p1004= this.getPageElement("p1004").getValue();
		String p1005= this.getPageElement("p1005").getValue();
		String p20101=this.getPageElement("p20101").getValue();
		String p20102=this.getPageElement("p20102").getValue();
		String p20201=this.getPageElement("p20201").getValue();
		String p20202=this.getPageElement("p20202").getValue();
		String p20203=this.getPageElement("p20203").getValue();
		String p20204=this.getPageElement("p20204").getValue();
		String p3001=this.getPageElement("p3001").getValue();
		String p3002=this.getPageElement("p3002").getValue();
		String p3003=this.getPageElement("p3003").getValue();
		String p4001=this.getPageElement("p4001").getValue();
		String p4002=this.getPageElement("p4002").getValue();
		String p4003=this.getPageElement("p4003").getValue();
		String p5001=this.getPageElement("p5001").getValue();
		String p5002=this.getPageElement("p5002").getValue();
		String p5003=this.getPageElement("p5003").getValue();
		String p6001=this.getPageElement("p6001").getValue();
		String p6002=this.getPageElement("p6002").getValue();
		String p6003=this.getPageElement("p6003").getValue();
		String p7001=this.getPageElement("p7001").getValue();
		String p7002=this.getPageElement("p7002").getValue();
		String p7003=this.getPageElement("p7003").getValue();
		String p7004=this.getPageElement("p7004").getValue();
		String p7005=this.getPageElement("p7005").getValue();
		String p7006=this.getPageElement("p7006").getValue();
		String p7007=this.getPageElement("p7007").getValue();
		String p8001=this.getPageElement("p8001").getValue();
		String p8002=this.getPageElement("p8002").getValue();
		String p9001=this.getPageElement("p9001").getValue();
		String p9002=this.getPageElement("p9002").getValue();
		String p10001=this.getPageElement("p10001").getValue();
		String p10002=this.getPageElement("p10002").getValue();
		String p11001=this.getPageElement("p11001").getValue();
		String p11002=this.getPageElement("p11002").getValue();
		String p12001=this.getPageElement("p12001").getValue();
		String p12002=this.getPageElement("p12002").getValue();
		String p13001=this.getPageElement("p13001").getValue();
		String p13002=this.getPageElement("p13002").getValue();
		String p13003=this.getPageElement("p13003").getValue();
		String p14001=this.getPageElement("p14001").getValue();
		String p14002=this.getPageElement("p14002").getValue();
		String p14003=this.getPageElement("p14003").getValue();
		String p15001=this.getPageElement("p15001").getValue();
		String p15002=this.getPageElement("p15002").getValue();
		String p16001=this.getPageElement("p16001").getValue();
		String p16002=this.getPageElement("p16002").getValue();
		String p17001=this.getPageElement("p17001").getValue();
		String p17002=this.getPageElement("p17002").getValue();

		String p10auresult=this.getPageElement("p10auresult").getValue();
		String p10auremark=this.getPageElement("p10auremark").getValue();
		String p201auresult=this.getPageElement("p201auresult").getValue();
		String p201auremark=this.getPageElement("p201auremark").getValue();
		String p202auresult=this.getPageElement("p202auresult").getValue();
		String p202auremark=this.getPageElement("p202auremark").getValue();
		String p30auresult=this.getPageElement("p30auresult").getValue();
		String p30auremark=this.getPageElement("p30auremark").getValue();
		String p40auresult=this.getPageElement("p40auresult").getValue();
		String p40auremark=this.getPageElement("p40auremark").getValue();
		String p50auresult=this.getPageElement("p50auresult").getValue();
		String p50auremark=this.getPageElement("p50auremark").getValue();
		String p60auresult=this.getPageElement("p60auresult").getValue();
		String p60auremark=this.getPageElement("p60auremark").getValue();
		String p70auresult=this.getPageElement("p70auresult").getValue();
		String p70auremark=this.getPageElement("p70auremark").getValue();
		String p80auresult=this.getPageElement("p80auresult").getValue();
		String p80auremark=this.getPageElement("p80auremark").getValue();
		String p90auresult=this.getPageElement("p90auresult").getValue();
		String p90auremark=this.getPageElement("p90auremark").getValue();
		String p100auresult=this.getPageElement("p100auresult").getValue();
		String p100auremark=this.getPageElement("p100auremark").getValue();
		String p110auresult=this.getPageElement("p110auresult").getValue();
		String p110auremark=this.getPageElement("p110auremark").getValue();
		String p120auresult=this.getPageElement("p120auresult").getValue();
		String p120auremark=this.getPageElement("p120auremark").getValue();
		String p130auresult=this.getPageElement("p130auresult").getValue();
		String p130auremark=this.getPageElement("p130auremark").getValue();
		String p140auresult=this.getPageElement("p140auresult").getValue();
		String p140auremark=this.getPageElement("p140auremark").getValue();
		String p150auresult=this.getPageElement("p150auresult").getValue();
		String p150auremark=this.getPageElement("p150auremark").getValue();
		String p160auresult=this.getPageElement("p160auresult").getValue();
		String p160auremark=this.getPageElement("p160auremark").getValue();
		String p170auresult=this.getPageElement("p170auresult").getValue();
		String p170auremark=this.getPageElement("p170auremark").getValue();

		/*// 一、省纪委省监委 19
		JwAuditInfo  jwai=null;
		List<JwAuditInfo> jwais = sess.createQuery("from JwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (jwais != null && !jwais.isEmpty()) {
			jwai = jwais.get(0);
		}else{
			jwai=new JwAuditInfo();
			jwai.setOid(StringUtil.getUuid());
			jwai.setA0000(a0000);
			jwai.setA0184(a0184);
		}
		jwai.setP1001(p1001);
		jwai.setP1002(p1002);
		jwai.setP1003(p1003);
		jwai.setP1004(p1004);
		jwai.setP1005(p1005);
		jwai.setAuditResult(p10auresult);
		jwai.setAuditRemark(p10auremark);
		sess.saveOrUpdate(jwai);


		//二、省委组织部 （一）干部综合处 91
		ZzbGbAuditInfo zzbgbai = null;
		List<ZzbGbAuditInfo> zzbgbais = sess.createQuery("from ZzbGbAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (zzbgbais != null && !zzbgbais.isEmpty()) {
			zzbgbai = zzbgbais.get(0);
		}else{
			zzbgbai=new ZzbGbAuditInfo();
			zzbgbai.setOid(StringUtil.getUuid());
			zzbgbai.setA0000(a0000);
			zzbgbai.setA0184(a0184);
		}
		zzbgbai.setP20101(p20101);
		zzbgbai.setP20102(p20102);
		zzbgbai.setAuditResult(p201auresult);
		zzbgbai.setAuditRemark(p201auremark);
		sess.saveOrUpdate(zzbgbai);

		//二、省委组织部 （二）干部监督室
		ZzbJdAuditInfo zzbjdai = null;
		List<ZzbJdAuditInfo> zzbjdais = sess.createQuery("from ZzbJdAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (zzbjdais != null && !zzbjdais.isEmpty()) {
			zzbjdai = zzbjdais.get(0);
		}else{
			zzbjdai=new ZzbJdAuditInfo();
			zzbjdai.setOid(StringUtil.getUuid());
			zzbjdai.setA0000(a0000);
			zzbjdai.setA0184(a0184);
		}
		zzbjdai.setP20201(p20201);
		zzbjdai.setP20202(p20202);
		zzbjdai.setP20203(p20203);
		zzbjdai.setP20204(p20204);
		zzbjdai.setAuditResult(p202auresult);
		zzbjdai.setAuditRemark(p202auremark);
		sess.saveOrUpdate(zzbjdai);
		//三、省信访局 31
		XfAuditInfo xfai = null;
		List<XfAuditInfo> xfais = sess.createQuery("from XfAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (xfais != null && !xfais.isEmpty()) {
			xfai = xfais.get(0);
		}else{
			xfai=new XfAuditInfo();
			xfai.setOid(StringUtil.getUuid());
			xfai.setA0000(a0000);
			xfai.setA0184(a0184);
		}
		xfai.setP3001(p3001);
		xfai.setP3002(p3002);
		xfai.setP3003(p3003);
		xfai.setAuditResult(p30auresult);
		xfai.setAuditRemark(p30auremark);
		sess.saveOrUpdate(xfai);

		//四、省法院
		FyAuditInfo fyai=null;
		List<FyAuditInfo> fyais = sess.createQuery("from FyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (fyais != null && !fyais.isEmpty()) {
			fyai = fyais.get(0);
		}else{
			fyai=new FyAuditInfo();
			fyai.setOid(StringUtil.getUuid());
			fyai.setA0000(a0000);
			fyai.setA0184(a0184);
		}
		fyai.setP4001(p4001);
		fyai.setP4002(p4002);
		fyai.setP4003(p4003);
		fyai.setP4004(p4003);
		fyai.setAuditResult(p40auresult);
		fyai.setAuditRemark(p40auremark);
		sess.saveOrUpdate(fyai);

		//五、省检察院
		JcyAuditInfo jcyai=null;
		List<JcyAuditInfo> jcyais = sess.createQuery("from JcyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (jcyais != null && !jcyais.isEmpty()) {
			jcyai = jcyais.get(0);
		}else{
			jcyai=new JcyAuditInfo();
			jcyai.setOid(StringUtil.getUuid());
			jcyai.setA0000(a0000);
			jcyai.setA0184(a0184);
		}
		jcyai.setP5001(p5001);
		jcyai.setP5002(p5002);
		jcyai.setP5003(p5003);
		jcyai.setAuditResult(p50auresult);
		jcyai.setAuditRemark(p50auremark);
		sess.saveOrUpdate(jcyai);

		//六、省发改委
		FgwAuditInfo fgwai=null;
		List<FgwAuditInfo> fgwais = sess.createQuery("from FgwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (fgwais != null && !fgwais.isEmpty()) {
			fgwai = fgwais.get(0);
		}else{
			fgwai=new FgwAuditInfo();
			fgwai.setOid(StringUtil.getUuid());
			fgwai.setA0000(a0000);
			fgwai.setA0184(a0184);
		}

		fgwai.setP6001(p6001);
		fgwai.setP6002(p6002);
		fgwai.setP6003(p6003);
		fgwai.setAuditResult(p60auresult);
		fgwai.setAuditRemark(p60auremark);
		sess.saveOrUpdate(fgwai);


		// 七、省公安厅
		GaAuditInfo gaai=null;
		List<GaAuditInfo> gaais = sess.createQuery("from GaAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (gaais != null && !gaais.isEmpty()) {
			gaai = gaais.get(0);
		}else{
			gaai=new GaAuditInfo();
			gaai.setOid(StringUtil.getUuid());
			gaai.setA0000(a0000);
			gaai.setA0184(a0184);
		}

		gaai.setP7001(p7001);
		gaai.setP7002(p7002);
		gaai.setP7003(p7003);
		gaai.setP7004(p7004);
		gaai.setP7005(p7005);
		gaai.setP7006(p7006);
		gaai.setP7007(p7007);
		gaai.setAuditResult(p70auresult);
		gaai.setAuditRemark(p70auremark);
		sess.saveOrUpdate(gaai);

		// 八、省人力社保厅
		RlsbAuditInfo rlsbai=null;
		List<RlsbAuditInfo> rlsbais = sess.createQuery("from RlsbAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (rlsbais != null && !rlsbais.isEmpty()) {
			rlsbai = rlsbais.get(0);
		}else{
			rlsbai=new RlsbAuditInfo();
			rlsbai.setOid(StringUtil.getUuid());
			rlsbai.setA0000(a0000);
			rlsbai.setA0184(a0184);
		}

		rlsbai.setP8001(p8001);
		rlsbai.setP8002(p8002);
		rlsbai.setAuditResult(p80auresult);
		rlsbai.setAuditRemark(p80auremark);
		sess.saveOrUpdate(rlsbai);

		// 九、省自然资源厅
		ZrzyAuditInfo zrzyai=null;
		List<ZrzyAuditInfo> zrzyais = sess.createQuery("from ZrzyAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (zrzyais != null && !zrzyais.isEmpty()) {
			zrzyai = zrzyais.get(0);
		}else{
			zrzyai=new ZrzyAuditInfo();
			zrzyai.setOid(StringUtil.getUuid());
			zrzyai.setA0000(a0000);
			zrzyai.setA0184(a0184);
		}
		zrzyai.setP9001(p9001);
		zrzyai.setP9002(p9002);
		zrzyai.setAuditResult(p90auresult);
		zrzyai.setAuditRemark(p90auremark);
		sess.saveOrUpdate(zrzyai);

		// 十、省生态环境厅
		SthjAuditInfo sthjai=null;
		List<SthjAuditInfo> sthjais = sess.createQuery("from SthjAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (sthjais != null && !sthjais.isEmpty()) {
			sthjai = sthjais.get(0);
		}else{
			sthjai=new SthjAuditInfo();
			sthjai.setOid(StringUtil.getUuid());
			sthjai.setA0000(a0000);
			sthjai.setA0184(a0184);
		}

		sthjai.setP10001(p10001);
		sthjai.setP10002(p10002);
		sthjai.setAuditResult(p100auresult);
		sthjai.setAuditRemark(p100auremark);
		sess.saveOrUpdate(sthjai);

		//十一、省卫健委
		WjwAuditInfo wjwai=null;
		List<WjwAuditInfo> wjwais = sess.createQuery("from WjwAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (wjwais != null && !wjwais.isEmpty()) {
			wjwai = wjwais.get(0);
		}else{
			wjwai=new WjwAuditInfo();
			wjwai.setOid(StringUtil.getUuid());
			wjwai.setA0000(a0000);
			wjwai.setA0184(a0184);
		}

		wjwai.setP11001(p11001);
		wjwai.setP11002(p11002);
		wjwai.setAuditResult(p110auresult);
		wjwai.setAuditRemark(p110auremark);
		sess.saveOrUpdate(wjwai);

		//十二、省应急管理厅
		YjglAuditInfo yjglai=null;
		List<YjglAuditInfo> yjglais = sess.createQuery("from YjglAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (yjglais != null && !yjglais.isEmpty()) {
			yjglai = yjglais.get(0);
		}else{
			yjglai=new YjglAuditInfo();
			yjglai.setOid(StringUtil.getUuid());
			yjglai.setA0000(a0000);
			yjglai.setA0184(a0184);
		}
		yjglai.setP12001(p12001);
		yjglai.setP12002(p12002);
		yjglai.setAuditResult(p120auresult);
		yjglai.setAuditRemark(p120auremark);
		sess.saveOrUpdate(yjglai);

		//十三、省审计厅
		SjAuditInfo sjai=null;
		List<SjAuditInfo> sjais = sess.createQuery("from SjAuditInfo where a0184=:a0184").setString("a0184", a0184).list();
		if (sjais != null && !sjais.isEmpty()) {
			sjai = sjais.get(0);
		}else{
			sjai=new SjAuditInfo();
			sjai.setOid(StringUtil.getUuid());
			sjai.setA0000(a0000);
			sjai.setA0184(a0184);
		}
		sjai.setP13001(p13001);
		sjai.setP13002(p13002);
		sjai.setP13003(p13003);
		sjai.setAuditResult(p130auresult);
		sjai.setAuditRemark(p130auremark);
		sess.saveOrUpdate(sjai);
		//十四、省市场监管局
		ScjgAuditInfo  scjgai=null;
		List<ScjgAuditInfo > scjgais = sess.createQuery("from ScjgAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (scjgais != null && !scjgais.isEmpty()) {
			scjgai = scjgais.get(0);
		}else{
			scjgai=new ScjgAuditInfo();
			scjgai.setOid(StringUtil.getUuid());
			scjgai.setA0000(a0000);
			scjgai.setA0184(a0184);
		}
		scjgai.setP14001(p14001);
		scjgai.setP14002(p14002);
		scjgai.setP14003(p14003);
		scjgai.setAuditResult(p140auresult);
		scjgai.setAuditRemark(p140auremark);

		sess.saveOrUpdate(scjgai);
		//十五、省统计局
		TjAuditInfo  tjai=null;
		List<TjAuditInfo > tjais = sess.createQuery("from TjAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (tjais != null && !tjais.isEmpty()) {
			tjai = tjais.get(0);
		}else{
			tjai=new TjAuditInfo();
			tjai.setOid(StringUtil.getUuid());
			tjai.setA0000(a0000);
			tjai.setA0184(a0184);
		}
		tjai.setP15001(p15001);
		tjai.setP15002(p15002);
		tjai.setAuditResult(p150auresult);
		tjai.setAuditRemark(p150auremark);

		sess.saveOrUpdate(tjai);

		//十六、省总工会
		GhAuditInfo  ghai=null;
		List<GhAuditInfo > ghais = sess.createQuery("from GhAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (ghais != null && !ghais.isEmpty()) {
			ghai = ghais.get(0);
		}else{
			ghai=new GhAuditInfo();
			ghai.setOid(StringUtil.getUuid());
			ghai.setA0000(a0000);
			ghai.setA0184(a0184);
		}
		ghai.setP16001(p16001);
		ghai.setP16002(p16002);
		ghai.setAuditResult(p160auresult);
		ghai.setAuditRemark(p160auremark);
		sess.saveOrUpdate(ghai);

		//十七、国家税务总局浙江省税务局
		SwAuditInfo  swai=null;
		List<SwAuditInfo > swais = sess.createQuery("from SwAuditInfo  where a0184=:a0184").setString("a0184", a0184).list();
		if (swais != null && !swais.isEmpty()) {
			swai = swais.get(0);
		}else{
			swai=new SwAuditInfo();
			swai.setOid(StringUtil.getUuid());
			swai.setA0000(a0000);
			swai.setA0184(a0184);
		}
		swai.setP17001(p17001);
		swai.setP17002(p17002);
		swai.setAuditResult(p170auresult);
		swai.setAuditRemark(p170auremark);

		sess.saveOrUpdate(swai);*/
		//初次保存
		api.setJwResult(p10auresult);
		api.setZzbGbResult(p201auresult);
		api.setZzbJdResult(p202auresult);
		api.setXfResult(p30auresult);
		api.setFyResult(p40auresult);
		api.setJcyResult(p50auresult);
		api.setFgwResult(p60auresult);
		api.setGaResult(p70auresult);
		api.setRlsbResult(p80auresult);
		api.setZrzyResult(p90auresult);
		api.setSthjResult(p100auresult);
		api.setWjwResult(p110auresult);
		api.setYjglResult(p120auresult);
		api.setSjResult(p130auresult);
		api.setScjgResult(p140auresult);
		api.setTjResult(p150auresult);
		api.setGhResult(p160auresult);
		api.setSwResult(p170auresult);

		sess.saveOrUpdate(api);
		sess.flush();
		this.getExecuteSG().addExecuteCode("saveCallBack('保存成功!');");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
