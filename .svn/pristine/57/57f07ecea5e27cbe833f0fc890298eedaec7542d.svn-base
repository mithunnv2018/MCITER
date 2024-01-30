package com.mciter.services;

import java.util.ArrayList;
import java.util.List;

import com.mciter.commonbeans.TblPapermainqDetails;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblTypingtestDetails;
import com.mciter.commonbeans.TblTypingtestMaster;
import com.mciter.utils.CommonParams2;

public class AddNewTypingExamPaperpatternController {

	public TblPaperpatternMaster tblpaperpatternmaster;
	public TblQuestioncategoryMaster tblquestioncategorymaster;
	public ArrayList<TblTypingtestMaster> tbltypingtestmasterlist;
	private TblTypingtestMaster tbltypingtestmaster;
	private TblTypingtestDetails tbltypingtestdetails;
	private ArrayList<TblPaperpatternMaster> listoldpaperpatternmaster;
	
	public AddNewTypingExamPaperpatternController() {
		tblpaperpatternmaster=new TblPaperpatternMaster();
		tblquestioncategorymaster=new TblQuestioncategoryMaster();
		tbltypingtestmaster=new TblTypingtestMaster();
		tbltypingtestdetails=new TblTypingtestDetails();
		doLoadOldPaperPatternMaster();
	}
	
	public String doSaveNewPaperpattern()
	{
		Integer typetId = tbltypingtestmaster.getTypetId();
		
		List<TblTypingtestMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblTypingtestMaster(), "TblTypingtestMaster", "typet_ID="+typetId);
		if(retrieveWherClause.size()>0)
		{
			TblTypingtestMaster tblTypingtestMaster2 = retrieveWherClause.get(0);
			
//			TblPaperpatternMaster tblPaperpatternMaster=new TblPaperpatternMaster();
			Integer ppdId=QuestionsUtil.doGetNextPK("TblPaperpatternMaster","PP_Tag",false);
			tblpaperpatternmaster.setPpId(ppdId);
			tblpaperpatternmaster.setPpTag(ppdId);
//			tblpaperpatternmaster.setPpName() DONE AUTOMATICALLY
//			tblpaperpatternmaster.setPpGraceMarks() NOT REQUIRED 
//			tblpaperpatternmaster.setPpNegativeMarks() NOT REQUIRED
			tblpaperpatternmaster.setPpPaperType("TYPING");
//			tblpaperpatternmaster.setPpPassingMarks() DONE AUTOMATICALLY
//			tblpaperpatternmaster.setPpTotal() DONE AUTOMATICALLY
			QuestionsUtil.saveToNewQuestions(tblpaperpatternmaster);
			
			TblPapermainqDetails  tblPapermainqDetails=new TblPapermainqDetails();
			Integer mqId=QuestionsUtil.doGetNextPK("TblPapermainqDetails", "MQ_ID", false);
			tblPapermainqDetails.setMqId(mqId);
			tblPapermainqDetails.setMqTag(mqId);
			tblPapermainqDetails.setMqName(tblpaperpatternmaster.getPpName()); 
			tblPapermainqDetails.setMqNosofquestion(1);
			tblPapermainqDetails.setMqTotalQmarks(tblpaperpatternmaster.getPpTotal());
			tblPapermainqDetails.setTblPaperpatternMaster(tblpaperpatternmaster);
			QuestionsUtil.saveToNewQuestions(tblPapermainqDetails);
			
			Integer typedetId = QuestionsUtil.doGetNextPK("TblTypingtestDetails", "typedet_tag", false);
			tbltypingtestdetails.setMqId(tblPapermainqDetails.getMqId());
			tbltypingtestdetails.setTypedetId(typedetId);
			tbltypingtestdetails.setTypedetTag(typedetId);
//			tbltypingtestdetails.setTypedetMistakes() DONE AUTOMATICALLY
//			tbltypingtestdetails.setTypedetNegativeoneachmistake() DONE AUTOMATICALLY
//			tbltypingtestdetails.setTypedetTime() DONE AUTOMATICALLY
			tbltypingtestdetails.setTypedetType("SPEED");
			tbltypingtestdetails.setTypetId(typetId);
			QuestionsUtil.saveToNewQuestions(tbltypingtestdetails);
			
			
			
			System.out
					.println("AddNewTypingExamPaperpatternController.doSaveNewPaperpattern()");
			System.out.println("Done Saving");
			return "index";
		}
		else
		{
			CommonParams2.showMessageOnScreen("Improper Course Name Selected");
			System.out
					.println("AddNewTypingExamPaperpatternController.doSaveNewPaperpattern()");
			System.out.println("Improper Course Name Selected");
		}
		return "";
	}
	
	public void doLoadOldPaperPatternMaster()
	{
		listoldpaperpatternmaster=new ArrayList<TblPaperpatternMaster>();
		List retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblPaperpatternMaster(), "TblPaperpatternMaster", "PP_paperType='TYPING'");
		listoldpaperpatternmaster.addAll(retrieveWherClause);
	}
	
	public void doGetParagraphSelectionList()
	{
		if(tblquestioncategorymaster==null || tblquestioncategorymaster.getQcId()==null)
		{
			System.out
					.println("AddNewTypingExamPaperpatternController.doGetParagraphSelectionList()");
			System.out.println("Empty Selected");
			return;
		}
		Integer qcId = tblquestioncategorymaster.getQcId();
		tbltypingtestmasterlist=new ArrayList<TblTypingtestMaster>();
		
		List<TblTypingtestMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblTypingtestMaster(), "TblTypingtestMaster", "QC_ID="+qcId);
		if(retrieveWherClause.size()>0)
		{
			for(int i=0;i<retrieveWherClause.size();i++)
			{
				tbltypingtestmasterlist.add(retrieveWherClause.get(i));
			}
		}
		else
		{
			CommonParams2.showMessageOnScreen("No Such Paper exist for typing test");
			System.out.println("doGetParagraphSelectionList =No Such Paper exist for typing test");
		}
	}
	
	public TblPaperpatternMaster getTblpaperpatternmaster() {
		return tblpaperpatternmaster;
	}

	public void setTblpaperpatternmaster(TblPaperpatternMaster tblpaperpatternmaster) {
		this.tblpaperpatternmaster = tblpaperpatternmaster;
	}


	public TblQuestioncategoryMaster getTblquestioncategorymaster() {
		return tblquestioncategorymaster;
	}


	public void setTblquestioncategorymaster(
			TblQuestioncategoryMaster tblquestioncategorymaster) {
		this.tblquestioncategorymaster = tblquestioncategorymaster;
	}


	public ArrayList<TblTypingtestMaster> getTbltypingtestmasterlist() {
		return tbltypingtestmasterlist;
	}


	public void setTbltypingtestmasterlist(
			ArrayList<TblTypingtestMaster> tbltypingtestmasterlist) {
		this.tbltypingtestmasterlist = tbltypingtestmasterlist;
	}


	public TblTypingtestMaster getTbltypingtestmaster() {
		return tbltypingtestmaster;
	}


	public void setTbltypingtestmaster(TblTypingtestMaster tbltypingtestmaster) {
		this.tbltypingtestmaster = tbltypingtestmaster;
	}


	public TblTypingtestDetails getTbltypingtestdetails() {
		return tbltypingtestdetails;
	}


	public void setTbltypingtestdetails(TblTypingtestDetails tbltypingtestdetails) {
		this.tbltypingtestdetails = tbltypingtestdetails;
	}

	public ArrayList<TblPaperpatternMaster> getListoldpaperpatternmaster() {
		return listoldpaperpatternmaster;
	}

	public void setListoldpaperpatternmaster(
			ArrayList<TblPaperpatternMaster> listoldpaperpatternmaster) {
		this.listoldpaperpatternmaster = listoldpaperpatternmaster;
	}
	
}
