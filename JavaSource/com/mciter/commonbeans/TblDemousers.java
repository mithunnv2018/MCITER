package com.mciter.commonbeans;

// Generated 27 Sep, 2012 9:32:53 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblDemousers generated by hbm2java
 */
public class TblDemousers implements java.io.Serializable {

	private Integer demoId;
	private String studentid;
	private String demoTag;

	public TblDemousers() {
	}

	public TblDemousers(String studentid, String demoTag) {
		this.studentid = studentid;
		this.demoTag = demoTag;
	}

	public Integer getDemoId() {
		return this.demoId;
	}

	public void setDemoId(Integer demoId) {
		this.demoId = demoId;
	}

	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getDemoTag() {
		return this.demoTag;
	}

	public void setDemoTag(String demoTag) {
		this.demoTag = demoTag;
	}

}
