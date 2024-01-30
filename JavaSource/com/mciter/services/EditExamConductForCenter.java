package com.mciter.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;


import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.utils.CommonParams2;

public class EditExamConductForCenter implements Serializable {

	
	private ArrayList<SelectItem> centercodes;
	private TblCenterDetails centerdetails;
	private TblQuestioncategoryMaster questioncategorymaster;
	private List<TblStudentDetails> listofstudents;
	private TblStudentDetails[] selectionlistofstudents;
	private List<TblPaperpatternMaster> listofpaperpatternmaster;
	private TblPaperpatternMaster tblpaperpatternmaster;
	private TblExaminationConduct tblexaminationconduct;
	private List<TblExaminationConduct> listofexaminationconduct;
	
	private TblExaminationConduct tempexamconduct=null;
	private List<TblStudentexamConduct> listofexamstudents;
	private TblStudentexamConduct[]  selectionliststudentexamconduct;

	public EditExamConductForCenter()
	{
		
		List<TblCenterDetails> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblCenterDetails(), "TblCenterDetails", "");
		centercodes=new ArrayList<SelectItem>();
		for (TblCenterDetails centerDetails : retrieveALLwithHB) {
			
			centercodes.add(new SelectItem(centerDetails.getAnpcode(),centerDetails.getNameofinstitute()));
//					new SelectItem(centerDetails.getAnpcode()));
			
			
		}
		centerdetails=new TblCenterDetails();
		questioncategorymaster=new TblQuestioncategoryMaster();
		setListofpaperpatternmaster(new ArrayList<TblPaperpatternMaster>());
		setListofpaperpatternmaster(QuestionsUtil.retrieveALLwithHB(new TblPaperpatternMaster(), "TblPaperpatternMaster", " "));
		tblpaperpatternmaster=new TblPaperpatternMaster();
		setTblexaminationconduct(new TblExaminationConduct());
		getTblexaminationconduct().setTblCenterDetails(new TblCenterDetails());
		loadExamConduct();
		listofexamstudents=new ArrayList<TblStudentexamConduct>();
	}
	
	private void loadExamConduct()
	{
		listofexaminationconduct=new ArrayList<TblExaminationConduct>();
		listofexaminationconduct= QuestionsUtil.retrieveWherClause(new TblExaminationConduct(), "TblExaminationConduct", " Exc_Active='active' ");
		
	}
	
	public void doLoadExamConduct2()
	{
		System.out.println("retrieved loaded exam conduct");
		tblexaminationconduct= tempexamconduct;
		Integer excId = tblexaminationconduct.getExcId();
		List<TblStudentexamConduct> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", "Exc_Id="+excId);
		if(retrieveWherClause.size()>0)
		{
			TblStudentexamConduct temp = retrieveWherClause.get(0);
			TblQuestioncategoryMaster temp3 = temp.getTblQuestioncategoryMaster();
			Integer qcId = temp3.getQcId();
			System.out.println("QCID is ="+qcId);
			List<TblQuestioncategoryMaster> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblQuestioncategoryMaster(), "TblQuestioncategoryMaster", "QC_ID="+qcId);
			questioncategorymaster=retrieveWherClause2.get(0);
			
			System.out.println("Course Loaded "+questioncategorymaster.getQcName());
		}
		else
		{
			CommonParams2.showMessageOnScreen("Exam Course Name not available!");
			System.out.println("No course available big problem");
		}
		
	}
	public void showStudentsList()
	{
		listofstudents=new ArrayList<TblStudentDetails>();
		listofexamstudents=new ArrayList<TblStudentexamConduct>();
		ArrayList<String>	localstudentid3=new ArrayList<String>();
		
		String anpcode=tblexaminationconduct.getTblCenterDetails().getAnpcode();
//			centerdetails.getAnpcode();
		Integer qcid=questioncategorymaster.getQcId();
//		List<TblStudentDetails> retrieveWherClause =QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "anpcode='"+anpcode+"' AND QC_ID="+qcid +" AND active='active' order by registerationdate desc" );
//		listofstudents.addAll(retrieveWherClause);
		Integer excId = tblexaminationconduct.getExcId();
		
		List<TblStudentexamConduct> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", "Exc_Id="+excId+"  order by Se_Id desc" );
		for(int i=0;i<retrieveWherClause.size();i++)
		{
			TblStudentexamConduct tempStudentexamConduct = retrieveWherClause.get(i);
			String studentid = tempStudentexamConduct.getTblStudentDetails().getStudentid();
			List<TblStudentDetails> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "studentid='"+studentid+"' ");
			TblStudentDetails tblStudentDetails = retrieveWherClause2.get(0);
			tempStudentexamConduct.setTblStudentDetails(tblStudentDetails);
//			retrieveWherClause.set(i, tempStudentexamConduct);
			listofexamstudents.add(tempStudentexamConduct);
			localstudentid3.add(tblStudentDetails.getStudentid());
//			tempStudentexamConduct.set
//			tblStudentexamConduct.setTblStudentDetails(tblStudentDetails)
		}
		List<TblStudentDetails> retrieveWherClause3 =QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "anpcode='"+anpcode+"' AND QC_ID="+qcid +" AND active='active' order by registerationdate desc" );
		for(int j=0;j<retrieveWherClause3.size();j++)
		{
			TblStudentDetails temp3 = retrieveWherClause3.get(j);
			if(!localstudentid3.contains(temp3.getStudentid()))
			{
				listofstudents.add(temp3);
			}
		}
		System.out.println("Done retrieveing Student details!");
	}
	
	private boolean isExamNameRepeat(String examname)
	{
		List<TblExaminationConduct> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblExaminationConduct(), "TblExaminationConduct", "Exc_Name='"+examname+"' ");
		if(retrieveWherClause.size()>=1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String updateToDB()
	{
		try {
			/*if(selectionliststudentexamconduct==null || selectionliststudentexamconduct.length==0)
			{
				CommonParams2.showMessageOnScreen("Sorry You did not Select any student!");
				System.out.println("Sorry You did not Select any student!");
				return null;
			}*/
			 
//			tblexaminationconduct.setExcActive("active");
//			Integer excId=QuestionsUtil.doGetNextPK("TblExaminationConduct", "Exc_Tag",true);
//			tblexaminationconduct.setExcId(excId);
		tblexaminationconduct.setExcDatetime(new Date());
//		tblexaminationconduct.setExcDuration(excDuration) AUTOMATICALYY
//		tblexaminationconduct.setExcName(excName) AUTOMATICALLY
//		tblexaminationconduct.setExcStudentendtime(excStudentendtime) AUTOMATIC
//		tblexaminationconduct.setExcStudentstarttime(excStudentstarttime) AUTOMATIC
//			tblexaminationconduct.setExcTag(excId);
//		tblexaminationconduct.setTblCenterDetails(tblCenterDetails) AUTOMATIC
//			QuestionsUtil.saveToNewQuestions(tblexaminationconduct);
		QuestionsUtil.updateToQuestions(tblexaminationconduct);
		String password=QuestionsUtil.doGetNextPK("", "")+tblexaminationconduct.getExcStudentendtime().getTime()+"";
		

//		for(int j=0;j<selectionliststudentexamconduct.length;j++)
		for(int j=0;j<listofexamstudents.size();j++)
		{
			TblStudentexamConduct temp2 = listofexamstudents.get(j);
			
			tblpaperpatternmaster=temp2.getTblPaperpatternMaster();
//				selectionliststudentexamconduct[j];
			QuestionsUtil.updateToQuestions(temp2);
			System.out.println("Updated student exam conduct i="+j);
		}
///////////mith added below code on 26th june 2012
		
			String subjectemail=tblexaminationconduct.getTblCenterDetails().getAnpcode();
			subjectemail+="-";
			subjectemail+="";
			String centername="";
			for (SelectItem center2 : centercodes) {
				String anpcode2 = (String) center2.getValue();
				if(anpcode2.equals(tblexaminationconduct.getTblCenterDetails().getAnpcode()))
				{
					centername=center2.getLabel();
					break;
				}
			}
			subjectemail+=centername+"-";
			subjectemail+=tblexaminationconduct.getExcName();
			String course=new CommonParams2().doGetCourseNameFromID(questioncategorymaster.getQcId());
			
			String message="Hi \n";
			message+="Center Name "+centername+" for exam "+tblexaminationconduct.getExcName();
			
			message+="Student ID & password details below for course -"+course+":- \n";
			
			message+="STUDENTID \t Firstname \t Lastname \t Course \t Password ";
			
			

		for(int i=0;i<selectionlistofstudents.length;i++)
		{
			TblStudentDetails localStudentDetails = selectionlistofstudents[i];
			TblStudentexamConduct studentexamconduct=new TblStudentexamConduct();
			studentexamconduct.setSeEndtime(new Date());
			Integer seId=QuestionsUtil.doGetNextPK("TblStudentexamConduct", "Se_Tag",true);
			studentexamconduct.setSeId(seId);
			studentexamconduct.setSePassword(password);
			studentexamconduct.setSeRemaintime(0);
			studentexamconduct.setSeStarttime(tblexaminationconduct.getExcStudentstarttime());
			studentexamconduct.setSeStatus("active");
			studentexamconduct.setSeTag(seId);
			studentexamconduct.setTblExaminationConduct(tblexaminationconduct);
			studentexamconduct.setTblPaperpatternMaster(tblpaperpatternmaster);
			studentexamconduct.setTblQuestioncategoryMaster(questioncategorymaster);
			studentexamconduct.setTblStudentDetails(localStudentDetails);
			QuestionsUtil.saveToNewQuestions(studentexamconduct);
			
			message+=""+localStudentDetails.getStudentid()+" \t";
			message+=""+localStudentDetails.getFirstname()+" \t";
			message+=""+localStudentDetails.getLastname()+" \t";
			message+=""+studentexamconduct.getSePassword()+"\n";
		}
		message+="\n Note:-This is an automated email from mciter.com";
		
		String emailto=new CommonParams2().doGetEmailofCenter(tblexaminationconduct.getTblCenterDetails().getAnpcode());
		CommonParams2.sendMailAlert(emailto, message, subjectemail);
		
			return "index";
			
			
		} catch (Exception e) {
			System.out.println("saveToDB at ExamConductForCenter class"+e.getMessage());
			e.printStackTrace();
		}
		return null;
		
	}

	public ArrayList<SelectItem> getCentercodes() {
		return centercodes;
	}

	public void setCentercodes(ArrayList<SelectItem> centercodes) {
		this.centercodes = centercodes;
	}

	public TblCenterDetails getCenterdetails() {
		return centerdetails;
	}

	public void setCenterdetails(TblCenterDetails centerdetails) {
		this.centerdetails = centerdetails;
	}

	public void setQuestioncategorymaster(TblQuestioncategoryMaster questioncategorymaster) {
		this.questioncategorymaster = questioncategorymaster;
	}

	public TblQuestioncategoryMaster getQuestioncategorymaster() {
		return questioncategorymaster;
	}

	public void setListofstudents(List<TblStudentDetails> listofstudents) {
		this.listofstudents = listofstudents;
	}

	public List<TblStudentDetails> getListofstudents() {
		return listofstudents;
	}

	public TblStudentDetails[] getSelectionlistofstudents() {
		return selectionlistofstudents;
	}

	public void setSelectionlistofstudents(
			TblStudentDetails[] selectionlistofstudents) {
		this.selectionlistofstudents = selectionlistofstudents;
	}

	public void setListofpaperpatternmaster(List<TblPaperpatternMaster> listofpaperpatternmaster) {
		this.listofpaperpatternmaster = listofpaperpatternmaster;
	}

	public List<TblPaperpatternMaster> getListofpaperpatternmaster() {
		return listofpaperpatternmaster;
	}

	public void setTblpaperpatternmaster(TblPaperpatternMaster tblpaperpatternmaster) {
		this.tblpaperpatternmaster = tblpaperpatternmaster;
	}

	public TblPaperpatternMaster getTblpaperpatternmaster() {
		return tblpaperpatternmaster;
	}

	public void setTblexaminationconduct(TblExaminationConduct tblexaminationconduct) {
		this.tblexaminationconduct = tblexaminationconduct;
	}

	public TblExaminationConduct getTblexaminationconduct() {
		return tblexaminationconduct;
	}

	public List<TblExaminationConduct> getListofexaminationconduct() {
		return listofexaminationconduct;
	}

	public void setListofexaminationconduct(
			List<TblExaminationConduct> listofexaminationconduct) {
		this.listofexaminationconduct = listofexaminationconduct;
	}

	public TblExaminationConduct getTempexamconduct() {
		return tempexamconduct;
	}

	public void setTempexamconduct(TblExaminationConduct tempexamconduct) {
		this.tempexamconduct = tempexamconduct;
	}

	public List<TblStudentexamConduct> getListofexamstudents() {
		return listofexamstudents;
	}

	public void setListofexamstudents(List<TblStudentexamConduct> listofexamstudents) {
		this.listofexamstudents = listofexamstudents;
	}

	public TblStudentexamConduct[] getSelectionliststudentexamconduct() {
		return selectionliststudentexamconduct;
	}

	public void setSelectionliststudentexamconduct(
			TblStudentexamConduct[] selectionliststudentexamconduct) {
		this.selectionliststudentexamconduct = selectionliststudentexamconduct;
	}
}
