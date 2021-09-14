package com.insigma.siis.local.lrmx;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Person")
public class PersonXml {

	private String XingMing;
	private String XingBie;
	private String ChuShengNianYue;
	private String MinZu;
	private String JiGuan;
	private String ChuShengDi;
	private String RuDangShiJian;
	private String CanJiaGongZuoShiJian;
	private String JianKangZhuangKuang;
	private String ZhuanYeJiShuZhiWu;
	private String ShuXiZhuanYeYouHeZhuanChang;
	private String QuanRiZhiJiaoYu_XueLi;
	private String QuanRiZhiJiaoYu_XueWei;
	private String QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	private String QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	private String ZaiZhiJiaoYu_XueLi;
	private String ZaiZhiJiaoYu_XueWei;
	private String ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	private String ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	private String XianRenZhiWu;
	private String NiRenZhiWu;
	private String NiMianZhiWu;
	private String JianLi;
	private String JiangChengQingKuang;
	private String NianDuKaoHeJieGuo;
	private String RenMianLiYou;
	private String ChengBaoDanWei;
	private String JiSuanNianLingShiJian;
	private String TianBiaoShiJian;
	private String TianBiaoRen;
	private String ShenFenZheng;
	private List<JiaTingChengYuanXml> JiaTingChengYuan;//note
	private byte[] ZhaoPian;//’’∆¨

	public PersonXml(String xingMing, String xingBie, String chuShengNianYue,
			String minZu, String jiGuan, String chuShengDi,
			String ruDangShiJian, String canJiaGongZuoShiJian,
			String jianKangZhuangKuang, String zhuanYeJiShuZhiWu,
			String shuXiZhuanYeYouHeZhuanChang, String quanRiZhiJiaoYu_XueLi,
			String quanRiZhiJiaoYu_XueWei,
			String quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi,
			String quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi,
			String zaiZhiJiaoYu_XueLi, String zaiZhiJiaoYu_XueWei,
			String zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi,
			String zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi, String xianRenZhiWu,
			String niRenZhiWu, String niMianZhiWu, String jianLi,
			String jiangChengQingKuang, String nianDuKaoHeJieGuo,
			String renMianLiYou, String chengBaoDanWei,
			String tianBiaoRen,
			String shenFenZheng,
			String jiSuanNianLingShiJian, String tianBiaoShiJian,
			List<JiaTingChengYuanXml> jiaTingChengYuan, byte[] zhaoPian) {
		super();
		XingMing = xingMing;
		XingBie = xingBie;
		ChuShengNianYue = chuShengNianYue;
		MinZu = minZu;
		JiGuan = jiGuan;
		ChuShengDi = chuShengDi;
		RuDangShiJian = ruDangShiJian;
		CanJiaGongZuoShiJian = canJiaGongZuoShiJian;
		JianKangZhuangKuang = jianKangZhuangKuang;
		ZhuanYeJiShuZhiWu = zhuanYeJiShuZhiWu;
		ShuXiZhuanYeYouHeZhuanChang = shuXiZhuanYeYouHeZhuanChang;
		QuanRiZhiJiaoYu_XueLi = quanRiZhiJiaoYu_XueLi;
		QuanRiZhiJiaoYu_XueWei = quanRiZhiJiaoYu_XueWei;
		QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
		QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
		ZaiZhiJiaoYu_XueLi = zaiZhiJiaoYu_XueLi;
		ZaiZhiJiaoYu_XueWei = zaiZhiJiaoYu_XueWei;
		ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
		ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
		XianRenZhiWu = xianRenZhiWu;
		NiRenZhiWu = niRenZhiWu;
		NiMianZhiWu = niMianZhiWu;
		JianLi = jianLi;
		JiangChengQingKuang = jiangChengQingKuang;
		NianDuKaoHeJieGuo = nianDuKaoHeJieGuo;
		RenMianLiYou = renMianLiYou;
		ChengBaoDanWei = chengBaoDanWei;
		JiSuanNianLingShiJian = jiSuanNianLingShiJian;
		TianBiaoShiJian = tianBiaoShiJian;
		TianBiaoRen = tianBiaoRen;
		ShenFenZheng = shenFenZheng;
		JiaTingChengYuan = jiaTingChengYuan;
		ZhaoPian = zhaoPian;
	}

	public PersonXml() {
		super();
	}

	public String getXingMing() {
		return XingMing;
	}

	public void setXingMing(String xingMing) {
		XingMing = xingMing;
	}

	public String getXingBie() {
		return XingBie;
	}

	public void setXingBie(String xingBie) {
		XingBie = xingBie;
	}

	public String getChuShengNianYue() {
		return ChuShengNianYue;
	}

	public void setChuShengNianYue(String chuShengNianYue) {
		ChuShengNianYue = chuShengNianYue;
	}

	public String getMinZu() {
		return MinZu;
	}

	public void setMinZu(String minZu) {
		MinZu = minZu;
	}

	public String getJiGuan() {
		return JiGuan;
	}

	public void setJiGuan(String jiGuan) {
		JiGuan = jiGuan;
	}

	public String getChuShengDi() {
		return ChuShengDi;
	}

	public void setChuShengDi(String chuShengDi) {
		ChuShengDi = chuShengDi;
	}

	public String getRuDangShiJian() {
		return RuDangShiJian;
	}

	public void setRuDangShiJian(String ruDangShiJian) {
		RuDangShiJian = ruDangShiJian;
	}

	public String getCanJiaGongZuoShiJian() {
		return CanJiaGongZuoShiJian;
	}

	public void setCanJiaGongZuoShiJian(String canJiaGongZuoShiJian) {
		CanJiaGongZuoShiJian = canJiaGongZuoShiJian;
	}

	public String getJianKangZhuangKuang() {
		return JianKangZhuangKuang;
	}

	public void setJianKangZhuangKuang(String jianKangZhuangKuang) {
		JianKangZhuangKuang = jianKangZhuangKuang;
	}

	public String getZhuanYeJiShuZhiWu() {
		return ZhuanYeJiShuZhiWu;
	}

	public void setZhuanYeJiShuZhiWu(String zhuanYeJiShuZhiWu) {
		ZhuanYeJiShuZhiWu = zhuanYeJiShuZhiWu;
	}

	public String getShuXiZhuanYeYouHeZhuanChang() {
		return ShuXiZhuanYeYouHeZhuanChang;
	}

	public void setShuXiZhuanYeYouHeZhuanChang(String shuXiZhuanYeYouHeZhuanChang) {
		ShuXiZhuanYeYouHeZhuanChang = shuXiZhuanYeYouHeZhuanChang;
	}

	public String getQuanRiZhiJiaoYu_XueLi() {
		return QuanRiZhiJiaoYu_XueLi;
	}

	public void setQuanRiZhiJiaoYu_XueLi(String quanRiZhiJiaoYu_XueLi) {
		QuanRiZhiJiaoYu_XueLi = quanRiZhiJiaoYu_XueLi;
	}

	public String getQuanRiZhiJiaoYu_XueWei() {
		return QuanRiZhiJiaoYu_XueWei;
	}

	public void setQuanRiZhiJiaoYu_XueWei(String quanRiZhiJiaoYu_XueWei) {
		QuanRiZhiJiaoYu_XueWei = quanRiZhiJiaoYu_XueWei;
	}

	public String getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() {
		return QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	}

	public void setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
			String quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi) {
		QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	}

	public String getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() {
		return QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	}

	public void setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
			String quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi) {
		QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	}

	public String getZaiZhiJiaoYu_XueLi() {
		return ZaiZhiJiaoYu_XueLi;
	}

	public void setZaiZhiJiaoYu_XueLi(String zaiZhiJiaoYu_XueLi) {
		ZaiZhiJiaoYu_XueLi = zaiZhiJiaoYu_XueLi;
	}

	public String getZaiZhiJiaoYu_XueWei() {
		return ZaiZhiJiaoYu_XueWei;
	}

	public void setZaiZhiJiaoYu_XueWei(String zaiZhiJiaoYu_XueWei) {
		ZaiZhiJiaoYu_XueWei = zaiZhiJiaoYu_XueWei;
	}

	public String getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() {
		return ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	}

	public void setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
			String zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi) {
		ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
	}

	public String getZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() {
		return ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	}

	public void setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
			String zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi) {
		ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
	}

	public String getXianRenZhiWu() {
		return XianRenZhiWu;
	}

	public void setXianRenZhiWu(String xianRenZhiWu) {
		XianRenZhiWu = xianRenZhiWu;
	}

	public String getNiRenZhiWu() {
		return NiRenZhiWu;
	}

	public void setNiRenZhiWu(String niRenZhiWu) {
		NiRenZhiWu = niRenZhiWu;
	}

	public String getNiMianZhiWu() {
		return NiMianZhiWu;
	}

	public void setNiMianZhiWu(String niMianZhiWu) {
		NiMianZhiWu = niMianZhiWu;
	}

	public String getJianLi() {
		return JianLi;
	}

	public void setJianLi(String jianLi) {
		JianLi = jianLi;
	}

	public String getJiangChengQingKuang() {
		return JiangChengQingKuang;
	}

	public void setJiangChengQingKuang(String jiangChengQingKuang) {
		JiangChengQingKuang = jiangChengQingKuang;
	}

	public String getNianDuKaoHeJieGuo() {
		return NianDuKaoHeJieGuo;
	}

	public void setNianDuKaoHeJieGuo(String nianDuKaoHeJieGuo) {
		NianDuKaoHeJieGuo = nianDuKaoHeJieGuo;
	}

	public String getRenMianLiYou() {
		return RenMianLiYou;
	}

	public void setRenMianLiYou(String renMianLiYou) {
		RenMianLiYou = renMianLiYou;
	}

	public String getChengBaoDanWei() {
		return ChengBaoDanWei;
	}

	public void setChengBaoDanWei(String chengBaoDanWei) {
		ChengBaoDanWei = chengBaoDanWei;
	}

	public String getJiSuanNianLingShiJian() {
		return JiSuanNianLingShiJian;
	}

	public void setJiSuanNianLingShiJian(String jiSuanNianLingShiJian) {
		JiSuanNianLingShiJian = jiSuanNianLingShiJian;
	}

	public String getTianBiaoShiJian() {
		return TianBiaoShiJian;
	}

	public void setTianBiaoShiJian(String tianBiaoShiJian) {
		TianBiaoShiJian = tianBiaoShiJian;
	}

	public List<JiaTingChengYuanXml> getJiaTingChengYuan() {
		return JiaTingChengYuan;
	}

	public void setJiaTingChengYuan(List<JiaTingChengYuanXml> jiaTingChengYuan) {
		JiaTingChengYuan = jiaTingChengYuan;
	}

	public byte[] getZhaoPian() {
		return ZhaoPian;
	}

	public void setZhaoPian(byte[] zhaoPian) {
		ZhaoPian = zhaoPian;
	}

	public String getTianBiaoRen() {
		return TianBiaoRen;
	}

	public void setTianBiaoRen(String tianBiaoRen) {
		TianBiaoRen = tianBiaoRen;
	}
	
	public String getShenFenZheng() {
		return ShenFenZheng;
	}

	public void setShenFenZheng(String shenFenZheng) {
		ShenFenZheng = shenFenZheng;
	}
	
}
