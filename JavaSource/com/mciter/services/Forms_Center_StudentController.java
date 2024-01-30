package com.mciter.services;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;


import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblUserMaster;
import com.mciter.utils.CommonParams2;

public class Forms_Center_StudentController {

	private TblCenterDetails centerdetails;
	private TblStudentDetails studentdetails;
	
	private List<SelectItem> centercode;
	private ArrayList<TblStudentDetails> listofstudents;
	private TblQuestioncategoryMaster questioncategorymaster;
	private TblUserMaster centerusermaster;
	
	private TblStudentDetails editstudentdetails;
	
	public TblUserMaster getCenterusermaster() {
		return centerusermaster;
	}

	public void setCenterusermaster(TblUserMaster centerusermaster) {
		this.centerusermaster = centerusermaster;
	}

	public List<SelectItem> getCentercode() {
		return centercode;
	}

	public void setCentercode(List<SelectItem> centercode) {
		this.centercode = centercode;
	}

	public Forms_Center_StudentController()
	{
		centerdetails=new TblCenterDetails();
		studentdetails=new TblStudentDetails("",new TblQuestioncategoryMaster(),0,"");
		studentdetails.setTblCenterDetails(new TblCenterDetails());
		
		List<TblCenterDetails> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblCenterDetails(), "TblCenterDetails", "");
		centercode=new ArrayList<SelectItem>();
		for (TblCenterDetails centerDetails : retrieveALLwithHB) {
			
			centercode.add(new SelectItem(centerDetails.getAnpcode(),centerDetails.getNameofinstitute()));
//					new SelectItem(centerDetails.getAnpcode()));
		 }
		questioncategorymaster=new TblQuestioncategoryMaster();
		centerusermaster=new TblUserMaster();
		
		editstudentdetails=new TblStudentDetails("",new TblQuestioncategoryMaster(),0,"");
		editstudentdetails.setTblCenterDetails(new TblCenterDetails());
		
	}
	public void showStudentsList()
	{
		listofstudents=new ArrayList<TblStudentDetails>();
		String anpcode=centerdetails.getAnpcode();
//			centerdetails.getAnpcode();
		Integer qcid=questioncategorymaster.getQcId();
		List<TblStudentDetails> retrieveWherClause =QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "anpcode='"+anpcode+"' AND QC_ID="+qcid +" AND active='active' order by registerationdate desc" );
		listofstudents.addAll(retrieveWherClause);
		
		System.out.println("Done retrieveing Student details!");
	}
	
	public void showEditableStudentsList()
	{
		listofstudents=new ArrayList<TblStudentDetails>();
		String anpcode=centerdetails.getAnpcode();
//			centerdetails.getAnpcode();
		Integer qcid=questioncategorymaster.getQcId();
		List<TblStudentDetails> retrieveWherClause =QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "anpcode='"+anpcode+"' AND QC_ID="+qcid +" AND active='active' order by registerationdate desc" );
		listofstudents.addAll(retrieveWherClause);
		studentdetails=null;
		System.out.println("Done retrieveing showEditableStudentsList Student details!");
	}
	
	public void showEditableStudentDetails()
	{
		studentdetails= editstudentdetails;
		 listofstudents.clear();
	}
	public String checkLogin()
	{
		String username=centerdetails.getEmailid();
		String password=centerdetails.getCenterpassword();
		
		List<TblCenterDetails> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblCenterDetails(), "TblCenterDetails", "emailid='"+username+"'  AND centerpassword='"+password+"' ");
		if(retrieveWherClause!=null && retrieveWherClause.size()>0)
		{
			centerdetails=retrieveWherClause.get(0);
			return "centerindex";
		}
		else
		{
			CommonParams2.showMessageOnScreen("Not enough credentials to login!");
			return reset();
		}
	}
	public String reset()
	{
		centerdetails=new TblCenterDetails();
		return "centerloginpage.jsf";
	}
	public TblCenterDetails getCenterdetails() {
		return centerdetails;
	}
	public void setCenterdetails(TblCenterDetails centerdetails) {
		this.centerdetails = centerdetails;
	}
	public TblStudentDetails getStudentdetails() {
		return studentdetails;
	}
	public void setStudentdetails(TblStudentDetails studentdetails) {
		this.studentdetails = studentdetails;
	}
	
	public String saveNewInstituteDetails()
	{
		String ret="index";
		
		String ret2="ANPMCITER12";
		Integer val=QuestionsUtil.doGetNextPK("TblCenterDetails", "tag");
		ret2+=val;
		
		
		String anpcode=ret2;
		this.centerdetails.setTag(val);
		this.centerdetails.setAnpcode(anpcode);
//		this.centerdetails.setAddressofinstitute() DONE AUTOMATICALLY
		this.centerdetails.setAffiliationfor("");
		this.centerdetails.setAreainsqft("");
		this.centerdetails.setCentercoordinatorname("");
		this.centerdetails.setCentercoordinatorphoneno("");
		this.centerdetails.setCentercoordinatorqualification("");
		this.centerdetails.setCentercoordinatorresidenceaddress("");
		this.centerdetails.setCenterheadname("");
		this.centerdetails.setCenterheadphoneno("");
		this.centerdetails.setCenterheadqualification("");
		this.centerdetails.setCenterheadresidenceaddress("");
//		this.centerdetails.setDistofinstitute("") DONE AUTOMATICALLY
//		this.centerdetails.setEmailid(emailid) DONE AUTOMATICALLY
		this.centerdetails.setInstitutepremises("");
//		this.centerdetails.setNameofinstitute(nameofinstitute) DONE AUTOMATICALLY
//		this.centerdetails.setStateofinstitute(stateofinstitute) DONE AUTOMATICALLY
		this.centerdetails.setStatusofinstitute("");
//		this.centerdetails.setTalukaofinstitute("") DONE AUTOMATICALLY
		this.centerdetails.setYearofestablishment("");
		
		QuestionsUtil.saveToNewQuestions(centerdetails);
		
		System.out.println("Saved New Institue Details");
		
		
		return ret;
		
		
	}
	
	
	public String saveNewStudentDetails()
	{
		String ret="index";
		String ret2="MCITER/AJ12";
		Integer val=QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
		ret2+=val;
		
		studentdetails.setTag(val );
		studentdetails.setStudentid(ret2);
		studentdetails.setActive("active");
		if(studentdetails.getTblCenterDetails().getAnpcode()==null)
		{
			studentdetails.setTblCenterDetails(centerdetails);
//		studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		if(studentdetails.getTblQuestioncategoryMaster().getQcId()==null)
		{
			studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		QuestionsUtil.saveToNewQuestions(studentdetails);
		System.out.println("Save New Student Details");
		refresh("admin");
		return ret;

	}
	public String saveOldStudentDetails()
	{
		String ret="index";
//		String ret2="MCITER/AJ12";
//		Integer val=QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
//		ret2+=val;
		
//		studentdetails.setTag(val );
//		studentdetails.setStudentid(ret2);
//		studentdetails.setActive("active");
		if(studentdetails.getTblCenterDetails().getAnpcode()==null)
		{
			studentdetails.setTblCenterDetails(centerdetails);
//		studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		if(studentdetails.getTblQuestioncategoryMaster().getQcId()==null)
		{
			studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
//		System.out.println("studentdetails toString()"+studentdetails.toString());
//		System.out.println("edit studentdetails toString()"+editstudentdetails.toString());
		
		QuestionsUtil.updateToQuestions(studentdetails);
//		QuestionsUtil.saveToNewQuestions(studentdetails);
		System.out.println("Update Old Student Details");
		refresh("admin");
		return ret;

	}
	public String saveNewStudentDetailsAtCenter()
	{
		String ret="centerindex";
		String ret2="MCITER/AJ12";
		Integer val=QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
		ret2+=val;
		
		studentdetails.setTag(val );
		studentdetails.setStudentid(ret2);
		studentdetails.setActive("active");
		if(studentdetails.getTblCenterDetails().getAnpcode()==null)
		{
			studentdetails.setTblCenterDetails(centerdetails);
//		studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		QuestionsUtil.saveToNewQuestions(studentdetails);
		System.out.println("Save New Student Details At center");
		refresh("center");
		return ret;

	}
	
	public String saveOldStudentDetailsAtCenter()
	{
		String ret="centerindex";
//		String ret2="MCITER/AJ12";
//		Integer val=QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
//		ret2+=val;
		
//		studentdetails.setTag(val );
//		studentdetails.setStudentid(ret2);
//		studentdetails.setActive("active");
		if(studentdetails.getTblCenterDetails().getAnpcode()==null)
		{
			studentdetails.setTblCenterDetails(centerdetails);
//		studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		if(studentdetails.getTblQuestioncategoryMaster().getQcId()==null)
		{
			studentdetails.setTblQuestioncategoryMaster(questioncategorymaster);
		}
		System.out.println("edit at center studentdetails toString()"+studentdetails.toString());
//		System.out.println("edit studentdetails toString()"+editstudentdetails.toString());
		
		QuestionsUtil.updateToQuestions(studentdetails);
//		QuestionsUtil.saveToNewQuestions(studentdetails);
		System.out.println("Update Old Student Details");
		refresh("center");
		return ret;

	}
	
	public String refresh(String client)
	{
		 
		studentdetails=new TblStudentDetails("",new TblQuestioncategoryMaster(),0,"");
		studentdetails.setTblCenterDetails(new TblCenterDetails());
		if(client.equalsIgnoreCase("center"))
		return "centerindex";
		else
			return "index";
	}

	public ArrayList<TblStudentDetails> getListofstudents() {
		return listofstudents;
	}

	public void setListofstudents(ArrayList<TblStudentDetails> listofstudents) {
		this.listofstudents = listofstudents;
	}

	public TblQuestioncategoryMaster getQuestioncategorymaster() {
		return questioncategorymaster;
	}

	public void setQuestioncategorymaster(
			TblQuestioncategoryMaster questioncategorymaster) {
		this.questioncategorymaster = questioncategorymaster;
	}

	public TblStudentDetails getEditstudentdetails() {
		return editstudentdetails;
	}

	public void setEditstudentdetails(TblStudentDetails editstudentdetails) {
		this.editstudentdetails = editstudentdetails;
	}
 
	 
	
}
