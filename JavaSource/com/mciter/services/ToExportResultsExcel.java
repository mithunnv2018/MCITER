package com.mciter.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.commonbeans.TblStudentresultDetails;

public class ToExportResultsExcel implements Serializable {

	private ArrayList<TblExaminationConduct> listofexams;
	private Integer selectedExcId;
	private String selectedExamName;
	private ArrayList<TblStudentresultDetails> listofstudentresults;
	
	public ToExportResultsExcel() {
	
		listofexams=new ArrayList<TblExaminationConduct>();
		
		List<TblExaminationConduct> retrieveALLwithHB = QuestionsUtil.retrieveWherClause(new TblExaminationConduct(), "TblExaminationConduct", "Exc_Active='active'");
		
		if(retrieveALLwithHB!=null && retrieveALLwithHB.size()>0)
		{
			listofexams.addAll(retrieveALLwithHB);
		}
		
	}
	
	public void doLoadResults()
	{
		ArrayList<TblStudentresultDetails> templist=new ArrayList<TblStudentresultDetails>();
		System.out.println("ToExportResultsExcel.doLoadResults()");
		if(selectedExcId!=null  )
		{
			System.out.println("excId = "+selectedExcId);
			List<TblStudentexamConduct> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", "Exc_Id="+selectedExcId+" AND Se_Status='dormant' ");
			if(retrieveWherClause!=null && retrieveWherClause.size()>0)
			{
				for(int i=0;i<retrieveWherClause.size();i++)
				{
					TblStudentexamConduct tblStudentexamConduct = retrieveWherClause.get(i);
					Integer seId = tblStudentexamConduct.getSeId();
					System.out.println("Student id= "+seId);
					List<TblStudentresultDetails> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblStudentresultDetails(), "TblStudentresultDetails", "SE_Id="+seId);
					if(retrieveWherClause2!=null && retrieveWherClause2.size()>0)
					{
						TblStudentresultDetails tblStudentresultDetails = retrieveWherClause2.get(0);
						templist.add(tblStudentresultDetails);
						System.out.println("Added to templist size="+templist.size());
					}
					
				}
			}
			listofstudentresults=new ArrayList<TblStudentresultDetails>();
			listofstudentresults.addAll(templist);
			System.out.println("Mith Loaded listofstudentlist size="+listofstudentresults.size());
			
		}
	}
	
	public ArrayList<TblExaminationConduct> getListofexams() {
		return listofexams;
	}
	public void setListofexams(ArrayList<TblExaminationConduct> listofexams) {
		this.listofexams = listofexams;
	}

	public Integer getSelectedExcId() {
		return selectedExcId;
	}

	public void setSelectedExcId(Integer selectedExcId) {
		this.selectedExcId = selectedExcId;
	}

	public ArrayList<TblStudentresultDetails> getListofstudentresults() {
		return listofstudentresults;
	}

	public void setListofstudentresults(
			ArrayList<TblStudentresultDetails> listofstudentresults) {
		this.listofstudentresults = listofstudentresults;
	}

	public String getSelectedExamName() {
		return selectedExamName;
	}

	public void setSelectedExamName(String selectedExamName) {
		this.selectedExamName = selectedExamName;
	}
	
	
	
	
}
