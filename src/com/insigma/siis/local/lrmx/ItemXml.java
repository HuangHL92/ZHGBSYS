package com.insigma.siis.local.lrmx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Item")
public class ItemXml {

	private String ChengWei;
	
	private String XingMing;
	
	private String ChuShengRiQi;
	
	private String ZhengZhiMianMao;
	
	private String GongZuoDanWeiJiZhiWu;


	public ItemXml(String ChengWei, String XingMing, String ChuShengRiQi, String ZhengZhiMianMao,
			String GongZuoDanWeiJiZhiWu) {
		super();
		this.ChengWei = ChengWei;
		this.XingMing = XingMing;
		this.ChuShengRiQi = ChuShengRiQi;
		this.ZhengZhiMianMao = ZhengZhiMianMao;
		this.GongZuoDanWeiJiZhiWu = GongZuoDanWeiJiZhiWu;
	}

	public ItemXml() {
		super();
	}

	public String getChengWei() {

		return ChengWei;
	}

	public void setChengWei(String chengWei) {
		ChengWei = chengWei;
	}

	public String getXingMing() {
		return XingMing;
	}

	public void setXingMing(String xingMing) {
		XingMing = xingMing;
	}

	public String getChuShengRiQi() {
		return ChuShengRiQi;
	}

	public void setChuShengRiQi(String chuShengRiQi) {
		ChuShengRiQi = chuShengRiQi;
	}

	public String getZhengZhiMianMao() {
		return ZhengZhiMianMao;
	}

	public void setZhengZhiMianMao(String zhengZhiMianMao) {
		ZhengZhiMianMao = zhengZhiMianMao;
	}

	public String getGongZuoDanWeiJiZhiWu() {
		return GongZuoDanWeiJiZhiWu;
	}

	public void setGongZuoDanWeiJiZhiWu(String gongZuoDanWeiJiZhiWu) {
		GongZuoDanWeiJiZhiWu = gongZuoDanWeiJiZhiWu;
	}
	
}
