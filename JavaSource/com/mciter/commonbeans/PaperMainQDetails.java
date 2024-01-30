package com.mciter.commonbeans;

public class PaperMainQDetails {

	@Override
	public String toString() {
		return "PaperMainQDetails [MQ_ID=" + MQ_ID + ", PP_ID=" + PP_ID
				+ ", MQ_Name=" + MQ_Name + ", MQ_TotalQmarks=" + MQ_TotalQmarks
				+ ", MQ_Tag=" + MQ_Tag + "]";
	}
	Integer MQ_ID,PP_ID;
	String MQ_Name;
	Integer MQ_TotalQmarks;
	Integer MQ_Tag;
	Integer MQ_Nosofquestion;
	//TODO DONE INTRODUCING below variable to save the total marks of paper.
	Integer RealTotalMarks;
	
	public Integer getRealTotalMarks() {
		return RealTotalMarks;
	}
	public void setRealTotalMarks(Integer realTotalMarks) {
		RealTotalMarks = realTotalMarks;
	}
	public Integer getMQ_Nosofquestion() {
		return MQ_Nosofquestion;
	}
	public void setMQ_Nosofquestion(Integer mQ_Nosofquestion) {
		MQ_Nosofquestion = mQ_Nosofquestion;
	}
	public Integer getMQ_ID() {
		return MQ_ID;
	}
	public void setMQ_ID(Integer mQ_ID) {
		MQ_ID = mQ_ID;
	}
	public Integer getPP_ID() {
		return PP_ID;
	}
	public void setPP_ID(Integer pP_ID) {
		PP_ID = pP_ID;
	}
	public String getMQ_Name() {
		return MQ_Name;
	}
	public void setMQ_Name(String mQ_Name) {
		MQ_Name = mQ_Name;
	}
	public Integer getMQ_TotalQmarks() {
		return MQ_TotalQmarks;
	}
	public void setMQ_TotalQmarks(Integer mQ_TotalQmarks) {
		MQ_TotalQmarks = mQ_TotalQmarks;
	}
	public Integer getMQ_Tag() {
		return MQ_Tag;
	}
	public void setMQ_Tag(Integer mQ_Tag) {
		MQ_Tag = mQ_Tag;
	}
	
}
