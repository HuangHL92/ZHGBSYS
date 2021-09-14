package com.insigma.siis.local.business.entity;


/**
 * VerifySqlList entity. @author MyEclipse Persistence Tools
 */

public class VerifySqlList implements java.io.Serializable {

	// Fields

	private String vsl001;
	private String vru001;
	private Long vsl002;
	private String vsl003;
	private String vsl004;
	private String vsl005;
	private String vsl006;
	private String vsl007;
	private String vsl008;
	private String vsl009;
	private String vsl010;
	private Long vsl011;
	private String vsl012;
	private String vsl013;
	private Long vsl014;

	// Constructors

	/** default constructor */
	public VerifySqlList() {
	}

	/**
	 * 不比较主键
	 * @author mengl
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vru001 == null) ? 0 : vru001.hashCode());
		result = prime * result + ((vsl002 == null) ? 0 : vsl002.hashCode());
		result = prime * result + ((vsl003 == null) ? 0 : vsl003.hashCode());
		result = prime * result + ((vsl004 == null) ? 0 : vsl004.hashCode());
		result = prime * result + ((vsl005 == null) ? 0 : vsl005.hashCode());
		result = prime * result + ((vsl006 == null) ? 0 : vsl006.hashCode());
		result = prime * result + ((vsl007 == null) ? 0 : vsl007.hashCode());
		result = prime * result + ((vsl008 == null) ? 0 : vsl008.hashCode());
		result = prime * result + ((vsl009 == null) ? 0 : vsl009.hashCode());
		result = prime * result + ((vsl010 == null) ? 0 : vsl010.hashCode());
		result = prime * result + ((vsl011 == null) ? 0 : vsl011.hashCode());
		result = prime * result + ((vsl012 == null) ? 0 : vsl012.hashCode());
		result = prime * result + ((vsl013 == null) ? 0 : vsl013.hashCode());
		return result;
	}

	/**
	 * 重写equals方法，不比较主键
	 * @author mengl
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerifySqlList other = (VerifySqlList) obj;
		if (vru001 == null) {
			if (other.vru001 != null)
				return false;
		} else if (!vru001.equals(other.vru001))
			return false;
		if (vsl002 == null) {
			if (other.vsl002 != null)
				return false;
		} else if (!vsl002.equals(other.vsl002))
			return false;
		if (vsl003 == null) {
			if (other.vsl003 != null)
				return false;
		} else if (!vsl003.equals(other.vsl003))
			return false;
		if (vsl004 == null) {
			if (other.vsl004 != null)
				return false;
		} else if (!vsl004.equals(other.vsl004))
			return false;
		if (vsl005 == null) {
			if (other.vsl005 != null)
				return false;
		} else if (!vsl005.equals(other.vsl005))
			return false;
		if (vsl006 == null) {
			if (other.vsl006 != null)
				return false;
		} else if (!vsl006.equals(other.vsl006))
			return false;
		if (vsl007 == null) {
			if (other.vsl007 != null)
				return false;
		} else if (!vsl007.equals(other.vsl007))
			return false;
		if (vsl008 == null) {
			if (other.vsl008 != null)
				return false;
		} else if (!vsl008.equals(other.vsl008))
			return false;
		if (vsl009 == null) {
			if (other.vsl009 != null)
				return false;
		} else if (!vsl009.equals(other.vsl009))
			return false;
		if (vsl010 == null) {
			if (other.vsl010 != null)
				return false;
		} else if (!vsl010.equals(other.vsl010))
			return false;
		if (vsl011 == null) {
			if (other.vsl011 != null)
				return false;
		} else if (!vsl011.equals(other.vsl011))
			return false;
		if (vsl012 == null) {
			if (other.vsl012 != null)
				return false;
		} else if (!vsl012.equals(other.vsl012))
			return false;
		if (vsl013 == null) {
			if (other.vsl013 != null)
				return false;
		} else if (!vsl013.equals(other.vsl013))
			return false;
		return true;
	}

	/** minimal constructor */
	public VerifySqlList(String vsl001) {
		this.vsl001 = vsl001;
	}

	/** full constructor */
	public VerifySqlList(String vsl001, String vru001,
			Long vsl002, String vsl003, String vsl004, String vsl005,
			String vsl006, String vsl007, String vsl008, String vsl009,
			String vsl010, Long vsl011, String vsl012, String vsl013) {
		this.vsl001 = vsl001;
		this.vru001 = vru001;
		this.vsl002 = vsl002;
		this.vsl003 = vsl003;
		this.vsl004 = vsl004;
		this.vsl005 = vsl005;
		this.vsl006 = vsl006;
		this.vsl007 = vsl007;
		this.vsl008 = vsl008;
		this.vsl009 = vsl009;
		this.vsl010 = vsl010;
		this.vsl011 = vsl011;
		this.vsl012 = vsl012;
		this.vsl013 = vsl013;
	}

	// Property accessors

	public String getVsl001() {
		return this.vsl001;
	}

	public void setVsl001(String vsl001) {
		this.vsl001 = vsl001;
	}

	public String getVru001() {
		return this.vru001;
	}

	public void setVru001(String vru001) {
		this.vru001 = vru001;
	}

	public Long getVsl002() {
		return this.vsl002;
	}

	public void setVsl002(Long vsl002) {
		this.vsl002 = vsl002;
	}

	public String getVsl003() {
		return this.vsl003;
	}

	public void setVsl003(String vsl003) {
		this.vsl003 = vsl003;
	}

	public String getVsl004() {
		return this.vsl004;
	}

	public void setVsl004(String vsl004) {
		this.vsl004 = vsl004;
	}

	public String getVsl005() {
		return this.vsl005;
	}

	public void setVsl005(String vsl005) {
		this.vsl005 = vsl005;
	}

	public String getVsl006() {
		return this.vsl006;
	}

	public void setVsl006(String vsl006) {
		this.vsl006 = vsl006;
	}

	public String getVsl007() {
		return this.vsl007;
	}

	public void setVsl007(String vsl007) {
		this.vsl007 = vsl007;
	}

	public String getVsl008() {
		return this.vsl008;
	}

	public void setVsl008(String vsl008) {
		this.vsl008 = vsl008;
	}

	public String getVsl009() {
		return this.vsl009;
	}

	public void setVsl009(String vsl009) {
		this.vsl009 = vsl009;
	}

	public String getVsl010() {
		return this.vsl010;
	}

	public void setVsl010(String vsl010) {
		this.vsl010 = vsl010;
	}

	public Long getVsl011() {
		return this.vsl011;
	}

	public void setVsl011(Long vsl011) {
		this.vsl011 = vsl011;
	}

	public String getVsl012() {
		return this.vsl012;
	}

	public void setVsl012(String vsl012) {
		this.vsl012 = vsl012;
	}

	public String getVsl013() {
		return this.vsl013;
	}

	public void setVsl013(String vsl013) {
		this.vsl013 = vsl013;
	}

	public Long getVsl014() {
		return vsl014;
	}

	public void setVsl014(Long vsl014) {
		this.vsl014 = vsl014;
	}

}