package com.mciter.commonbeans;

// Generated 6 Aug, 2012 8:03:33 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblStudentresultDetails generated by hbm2java
 */
public class TblStudentresultDetails implements java.io.Serializable {

	private Integer studrestId;
	private TblStudentexamConduct tblStudentexamConduct;
	private TblQuestioncategoryMaster tblQuestioncategoryMaster;
	private String studrestMonthandyear;
	private String studrestCourseduration;
	private int studrestMaxmarks;
	private int studrestMarksobtain;
	private int studrestPercentage;
	private String studrestGrade;
	private String studentId;
	private String studrestPlace;
	private String studrestCentercode;
	private String studrestStudentfullname;
	private String studrestCoursename;
	private String studrestNameofanp;
	private int studrestTag;

	public TblStudentresultDetails() {
	}

	public TblStudentresultDetails(TblStudentexamConduct tblStudentexamConduct,
			TblQuestioncategoryMaster tblQuestioncategoryMaster,
			String studrestMonthandyear, String studrestCourseduration,
			int studrestMaxmarks, int studrestMarksobtain,
			int studrestPercentage, String studrestGrade, String studentId,
			String studrestPlace, String studrestCentercode,
			String studrestStudentfullname, String studrestCoursename,
			String studrestNameofanp, int studrestTag) {
		this.tblStudentexamConduct = tblStudentexamConduct;
		this.tblQuestioncategoryMaster = tblQuestioncategoryMaster;
		this.studrestMonthandyear = studrestMonthandyear;
		this.studrestCourseduration = studrestCourseduration;
		this.studrestMaxmarks = studrestMaxmarks;
		this.studrestMarksobtain = studrestMarksobtain;
		this.studrestPercentage = studrestPercentage;
		this.studrestGrade = studrestGrade;
		this.studentId = studentId;
		this.studrestPlace = studrestPlace;
		this.studrestCentercode = studrestCentercode;
		this.studrestStudentfullname = studrestStudentfullname;
		this.studrestCoursename = studrestCoursename;
		this.studrestNameofanp = studrestNameofanp;
		this.studrestTag = studrestTag;
	}

	public Integer getStudrestId() {
		return this.studrestId;
	}

	public void setStudrestId(Integer studrestId) {
		this.studrestId = studrestId;
	}

	public TblStudentexamConduct getTblStudentexamConduct() {
		return this.tblStudentexamConduct;
	}

	public void setTblStudentexamConduct(
			TblStudentexamConduct tblStudentexamConduct) {
		this.tblStudentexamConduct = tblStudentexamConduct;
	}

	public TblQuestioncategoryMaster getTblQuestioncategoryMaster() {
		return this.tblQuestioncategoryMaster;
	}

	public void setTblQuestioncategoryMaster(
			TblQuestioncategoryMaster tblQuestioncategoryMaster) {
		this.tblQuestioncategoryMaster = tblQuestioncategoryMaster;
	}

	public String getStudrestMonthandyear() {
		return this.studrestMonthandyear;
	}

	public void setStudrestMonthandyear(String studrestMonthandyear) {
		this.studrestMonthandyear = studrestMonthandyear;
	}

	public String getStudrestCourseduration() {
		return this.studrestCourseduration;
	}

	public void setStudrestCourseduration(String studrestCourseduration) {
		this.studrestCourseduration = studrestCourseduration;
	}

	public int getStudrestMaxmarks() {
		return this.studrestMaxmarks;
	}

	public void setStudrestMaxmarks(int studrestMaxmarks) {
		this.studrestMaxmarks = studrestMaxmarks;
	}

	public int getStudrestMarksobtain() {
		return this.studrestMarksobtain;
	}

	public void setStudrestMarksobtain(int studrestMarksobtain) {
		this.studrestMarksobtain = studrestMarksobtain;
	}

	public int getStudrestPercentage() {
		return this.studrestPercentage;
	}

	public void setStudrestPercentage(int studrestPercentage) {
		this.studrestPercentage = studrestPercentage;
	}

	public String getStudrestGrade() {
		return this.studrestGrade;
	}

	public void setStudrestGrade(String studrestGrade) {
		this.studrestGrade = studrestGrade;
	}

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudrestPlace() {
		return this.studrestPlace;
	}

	public void setStudrestPlace(String studrestPlace) {
		this.studrestPlace = studrestPlace;
	}

	public String getStudrestCentercode() {
		return this.studrestCentercode;
	}

	public void setStudrestCentercode(String studrestCentercode) {
		this.studrestCentercode = studrestCentercode;
	}

	public String getStudrestStudentfullname() {
		return this.studrestStudentfullname;
	}

	public void setStudrestStudentfullname(String studrestStudentfullname) {
		this.studrestStudentfullname = studrestStudentfullname;
	}

	public String getStudrestCoursename() {
		return this.studrestCoursename;
	}

	public void setStudrestCoursename(String studrestCoursename) {
		this.studrestCoursename = studrestCoursename;
	}

	public String getStudrestNameofanp() {
		return this.studrestNameofanp;
	}

	public void setStudrestNameofanp(String studrestNameofanp) {
		this.studrestNameofanp = studrestNameofanp;
	}

	public int getStudrestTag() {
		return this.studrestTag;
	}

	public void setStudrestTag(int studrestTag) {
		this.studrestTag = studrestTag;
	}

}