package com.mciter.commonbeans;

// Generated 26 Jun, 2013 4:49:45 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblCoursemap generated by hbm2java
 */
public class TblCoursemap implements java.io.Serializable {

	private int mapId;
	private String mapCoursecode;
	private Integer qcId;

	public TblCoursemap() {
	}

	public TblCoursemap(int mapId) {
		this.mapId = mapId;
	}

	public TblCoursemap(int mapId, String mapCoursecode, Integer qcId) {
		this.mapId = mapId;
		this.mapCoursecode = mapCoursecode;
		this.qcId = qcId;
	}

	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getMapCoursecode() {
		return this.mapCoursecode;
	}

	public void setMapCoursecode(String mapCoursecode) {
		this.mapCoursecode = mapCoursecode;
	}

	public Integer getQcId() {
		return this.qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

}
