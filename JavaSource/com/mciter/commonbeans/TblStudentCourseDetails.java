package com.mciter.commonbeans;

// Generated 6 Aug, 2012 8:03:33 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblStudentCourseDetails generated by hbm2java
 */
public class TblStudentCourseDetails implements java.io.Serializable {

	private Integer scdId;
	private TblQuestioncategoryMaster tblQuestioncategoryMaster;
	private TblStudentDetails tblStudentDetails;

	public TblStudentCourseDetails() {
	}

	public TblStudentCourseDetails(
			TblQuestioncategoryMaster tblQuestioncategoryMaster,
			TblStudentDetails tblStudentDetails) {
		this.tblQuestioncategoryMaster = tblQuestioncategoryMaster;
		this.tblStudentDetails = tblStudentDetails;
	}

	public Integer getScdId() {
		return this.scdId;
	}

	public void setScdId(Integer scdId) {
		this.scdId = scdId;
	}

	public TblQuestioncategoryMaster getTblQuestioncategoryMaster() {
		return this.tblQuestioncategoryMaster;
	}

	public void setTblQuestioncategoryMaster(
			TblQuestioncategoryMaster tblQuestioncategoryMaster) {
		this.tblQuestioncategoryMaster = tblQuestioncategoryMaster;
	}

	public TblStudentDetails getTblStudentDetails() {
		return this.tblStudentDetails;
	}

	public void setTblStudentDetails(TblStudentDetails tblStudentDetails) {
		this.tblStudentDetails = tblStudentDetails;
	}

}
