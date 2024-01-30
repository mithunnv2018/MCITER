package com.mciter.services;

import java.util.ArrayList;
import java.util.List;

import com.mciter.commonbeans.TblPapermainqDetails;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblTypingtestDetails;
import com.mciter.commonbeans.TblTypingtestMaster;
import com.mciter.utils.CommonParams2;

public class EditTypingExamPaperpatternController {

	public TblPaperpatternMaster tblpaperpatternmaster;
	public TblQuestioncategoryMaster tblquestioncategorymaster;
	public ArrayList<TblTypingtestMaster> tbltypingtestmasterlist;
	private TblTypingtestMaster tbltypingtestmaster;
	private TblTypingtestDetails tbltypingtestdetails;
	private ArrayList<TblPaperpatternMaster> listoldpaperpatternmaster;
	private TblPapermainqDetails tblPapermainqDetails;
	private boolean reloadnow=false;
	
	public EditTypingExamPaperpatternController() {
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
	
	public String doUpdatePaperpattern()
	{
		try {
			if(tblpaperpatternmaster==null || tblpaperpatternmaster.getPpId()==null)
			{
				CommonParams2.showMessageOnScreen("You have not selected a paper pattern");
				return "";
			}
			QuestionsUtil.updateToQuestions(tblpaperpatternmaster);
			QuestionsUtil.updateToQuestions(tblPapermainqDetails);
			QuestionsUtil.updateToQuestions(tbltypingtestdetails);
			System.out.println("Done Updating typing paepr pattern");
			return "index";
			
			
		} catch (Exception e) {
			System.out
					.println("EditTypingExamPaperpatternController.doUpdatePaperpattern()"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			reset();
		}
		return "";
	}
	
	public void doLoadNow2(org.primefaces.event.SelectEvent event)
	{
//		tblpaperpatternmaster.getPpName()
		
		Integer ppId = tblpaperpatternmaster.getPpId();
		try {
			List<TblPapermainqDetails> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblPapermainqDetails(), "TblPapermainqDetails", "PP_ID="+ppId);
			if(retrieveWherClause.size()>0)
			{
				tblPapermainqDetails = retrieveWherClause.get(0);
				Integer mqId = tblPapermainqDetails.getMqId();
				List<TblTypingtestDetails> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblTypingtestDetails(), "TblTypingtestDetails", "MQ_ID="+mqId);
				if(retrieveWherClause2.size()>0)
				{
					tbltypingtestdetails = retrieveWherClause2.get(0);
					Integer typetId = tbltypingtestdetails.getTypetId();
					List<TblTypingtestMaster> retrieveWherClause3 = QuestionsUtil.retrieveWherClause(new TblTypingtestMaster(), "TblTypingtestMaster", "typet_ID="+typetId);
					if(retrieveWherClause3.size()>0)
					{
						tbltypingtestmaster = retrieveWherClause3.get(0);
						
						tblquestioncategorymaster.setQcId(tbltypingtestmaster.getQcId());
						doGetParagraphSelectionList();
						reloadnow=true;
						System.out
								.println("EditTypingExamPaperpatternController.doLoadNow2()");
						System.out.println("Made it retrieved the data for edit paper pattern");
					}
					else
					{
						System.err.println("Hi error no entry in TblTypingtestMaster");
						reloadnow=false;
					}
				}
				else
				{
					System.err.println("Hi error no entry in TblTypingtestDetails ");
					reloadnow=false;
				}
			}
			else
			{
				CommonParams2.showMessageOnScreen("No Matching paperpattern ");
				System.out.println("No Matching paperpattern ");
				reloadnow=false;
			}
			
		} catch (Exception e) {
			System.err.println("doLoadNow2 excpetion"+e.getMessage());
			e.printStackTrace();
		}
	}
	private void reset()
	{
		tblpaperpatternmaster=new TblPaperpatternMaster();
		tblquestioncategorymaster=new TblQuestioncategoryMaster();
		tbltypingtestdetails=new TblTypingtestDetails();
		tbltypingtestmasterlist.clear();
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

	public boolean isReloadnow() {
		return reloadnow;
	}

	public void setReloadnow(boolean reloadnow) {
		this.reloadnow = reloadnow;
	}
	
}
