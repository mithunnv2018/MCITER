package com.mciter.utils;

import com.mciter.commonbeans.TblMainqpatternDetails;

public class WrapperTblMainqpatterndetails {

	public TblMainqpatternDetails tblMainqpatternDetails;
	public String questionText="";
	public WrapperTblMainqpatterndetails()
	{
		tblMainqpatternDetails=new TblMainqpatternDetails();
		
	}
	public TblMainqpatternDetails getTblMainqpatternDetails() {
		return tblMainqpatternDetails;
	}
	public void setTblMainqpatternDetails(
			TblMainqpatternDetails tblMainqpatternDetails) {
		this.tblMainqpatternDetails = tblMainqpatternDetails;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
}
