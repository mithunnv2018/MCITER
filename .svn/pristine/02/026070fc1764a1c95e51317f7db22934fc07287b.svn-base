package com.mciter.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mciter.commonbeans.TblFontMaster;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblTypingtestMaster;
import com.mciter.utils.CommonParams2;

public class TypingMasterController implements Serializable {

	private TblQuestioncategoryMaster tblquestioncategorymaster;
	private TblTypingtestMaster tbltypingtestmaster;
	private TblFontMaster tblfontmaster;
	private ArrayList<TblFontMaster> listtblfontmaster;
	private String fonturl="kiran.TTF";
	
	private ArrayList<HashMap> listofoldtypingtestmaster;
	public TypingMasterController() {
		tblquestioncategorymaster=new TblQuestioncategoryMaster();
		tbltypingtestmaster=new TblTypingtestMaster();
		tblfontmaster=new TblFontMaster();
		doloadFonts();
		doloadOldListtypingmaster();
	}
	private void doloadOldListtypingmaster()
	{
		List<TblTypingtestMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblTypingtestMaster(), "TblTypingtestMaster", "");
		listofoldtypingtestmaster=new ArrayList<HashMap>();
		HashMap<String,String> mapofoldtypingtestmaster;
		System.out
				.println("TypingMasterController.doloadOldListtypingmaster()");
		if(retrieveALLwithHB.size()>0)
		{
			
			System.out.println("Made it tostep one typingtestmaster");
			for(int i=0;i<retrieveALLwithHB.size();i++)
			{
				mapofoldtypingtestmaster=new HashMap<String,String>();
				TblTypingtestMaster tblTypingtestMaster2 = retrieveALLwithHB.get(i);
				String typetId = tblTypingtestMaster2.getTypetId()+"";
				int fontId = tblTypingtestMaster2.getFontId();
				int qcId = tblTypingtestMaster2.getQcId();
				System.out.println("fontid="+fontId+" qcid="+qcId);
				List<TblFontMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblFontMaster(), "TblFontMaster", "font_id="+fontId);
				List<TblQuestioncategoryMaster> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblQuestioncategoryMaster(), "TblQuestioncategoryMaster", "QC_ID="+qcId );
				if(retrieveWherClause.size()>0 && retrieveWherClause2.size()>0)
				{
					
					TblFontMaster tblFontMaster2 = retrieveWherClause.get(0);
					TblQuestioncategoryMaster tblQuestioncategoryMaster2 = retrieveWherClause2.get(0);
					mapofoldtypingtestmaster.put("fontname", tblFontMaster2.getFontName());
					mapofoldtypingtestmaster.put("course", tblQuestioncategoryMaster2.getQcName());
					System.out.println("Made it tostep two added to hashmap");
				}
				
				mapofoldtypingtestmaster.put("paragraph", tblTypingtestMaster2.getTypetAlias());
				mapofoldtypingtestmaster.put("typetid",typetId);
				System.out.println("Made it tostep three hasmap="+mapofoldtypingtestmaster.toString());
				listofoldtypingtestmaster.add(mapofoldtypingtestmaster);
			}
		}
	}
	private void doloadFonts()
	{
		listtblfontmaster=new ArrayList<TblFontMaster>();
		List<TblFontMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblFontMaster(), "TblFontMaster", "");
		listtblfontmaster.addAll(retrieveALLwithHB);
		
	}

	public void doUpdateFont()
	{
		Integer fontId = tblfontmaster.getFontId();
		List<TblFontMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblFontMaster(), "TblFontMaster", "font_id="+fontId);
		if(retrieveWherClause.size()>0)
		{
			TblFontMaster tblFontMaster2 = retrieveWherClause.get(0);
			fonturl=tblFontMaster2.getFontPath();
			
		}
	}
	public String doSaveAll()
	{
		try {
			System.out.println("TypingMasterController.doSaveAll()");
			if (tblquestioncategorymaster == null
					|| tblquestioncategorymaster.getQcId() == null) {
				CommonParams2.showMessageOnScreen("No Course Selected");
				System.err.println("No Course Selected");
				return "";
			}
			if (tblfontmaster.getFontId() == null) {
				CommonParams2.showMessageOnScreen("No font Selected");
				System.err.println("No font Selected");
				return "";
			}
			tbltypingtestmaster.setFontId(tblfontmaster.getFontId());
			tbltypingtestmaster.setQcId(tblquestioncategorymaster.getQcId());
			String ecoded=CommonParams2.encodeToAscii(tbltypingtestmaster.getTypetDesc());
			tbltypingtestmaster.setTypetDesc(ecoded);
			//		tbltypingtestmaster.setTypetAlias() DONE AUTOMATICALLY
			Integer doGetNextPK = QuestionsUtil.doGetNextPK(
					"TblTypingtestMaster", "typet_ID", false);
			tbltypingtestmaster.setTypetId(doGetNextPK);
			tbltypingtestmaster.setTypetTag(doGetNextPK);
			tbltypingtestmaster.setTypetTypingtype("SPEED");
			QuestionsUtil.saveToNewQuestions(tbltypingtestmaster);
			System.out.println("Done Saveing TypingTestMaster");
			return "index";
		} catch (Exception e) {
			System.err.println("Exception here "+e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	public TblQuestioncategoryMaster getTblquestioncategorymaster() {
		return tblquestioncategorymaster;
	}

	public void setTblquestioncategorymaster(
			TblQuestioncategoryMaster tblquestioncategorymaster) {
		this.tblquestioncategorymaster = tblquestioncategorymaster;
	}

	public TblTypingtestMaster getTbltypingtestmaster() {
		return tbltypingtestmaster;
	}

	public void setTbltypingtestmaster(TblTypingtestMaster tbltypingtestmaster) {
		this.tbltypingtestmaster = tbltypingtestmaster;
	}

	public TblFontMaster getTblfontmaster() {
		return tblfontmaster;
	}

	public void setTblfontmaster(TblFontMaster tblfontmaster) {
		this.tblfontmaster = tblfontmaster;
	}

	public ArrayList<TblFontMaster> getListtblfontmaster() {
		return listtblfontmaster;
	}

	public void setListtblfontmaster(ArrayList<TblFontMaster> listtblfontmaster) {
		this.listtblfontmaster = listtblfontmaster;
	}
	public ArrayList<HashMap> getListofoldtypingtestmaster() {
		return listofoldtypingtestmaster;
	}
	public void setListofoldtypingtestmaster(
			ArrayList<HashMap> listofoldtypingtestmaster) {
		this.listofoldtypingtestmaster = listofoldtypingtestmaster;
	}
	public String getFonturl() {
		return fonturl;
	}
	public void setFonturl(String fontname) {
		this.fonturl = fontname;
	}
	 
	
}
