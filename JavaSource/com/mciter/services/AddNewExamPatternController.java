package com.mciter.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mciter.commonbeans.*;

public class AddNewExamPatternController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3745545778092942867L;
	private MarksMaster marksmaster;
	private QuestionCategoryMaster questioncategorymaster;

	private List<QuestionMaster> questionmasterList;
	private List<QuestionMaster> selectedquestionmasterlist;

	private List<MainQPatternDetails> mainqpatterndetailslist;
	private MainQPatternDetails[] selectedmainqpatterndetailslist;
	private ArrayList<MainQPatternDetails[]> savethisselectedmainqpatterndetailslist;

	private PaperPatternMaster paperpatternmaster;
	private PaperMainQDetails papermainqdetails;

	
	private List<PaperMainQDetails> addedpapermainqdetailslist;
	private PaperMainQDetails[] selectedaddedpapermainqdetailslist;

	//for update
	private List<PaperPatternMaster> updatepaperpatternmasterlist;
	
	private Boolean  showmainquestionname;

	private List<TblPaperpatternMaster> listoldpaperpatternmaster;
	 

	public Boolean getShowmainquestionname() {
		return showmainquestionname;
	}

	public void setShowmainquestionname(Boolean showmainquestionname) {
		this.showmainquestionname = showmainquestionname;
	}

	public List<PaperPatternMaster> getUpdatepaperpatternmasterlist() {
		
		updatepaperpatternmasterlist=QuestionsUtil.retrieveALLwithHB(new PaperPatternMaster(), "PaperPatternMaster", "");
		
		return updatepaperpatternmasterlist;
	}

	public void setUpdatepaperpatternmasterlist(
			List<PaperPatternMaster> updatepaperpatternmasterlist) {
		this.updatepaperpatternmasterlist = updatepaperpatternmasterlist;
	}

	public List<PaperMainQDetails> getAddedpapermainqdetailslist() {
		return addedpapermainqdetailslist;
	}

	public void setAddedpapermainqdetailslist(
			List<PaperMainQDetails> addedpapermainqdetailslist) {
		this.addedpapermainqdetailslist = addedpapermainqdetailslist;
	}

	public PaperMainQDetails[] getSelectedaddedpapermainqdetailslist() {
		return selectedaddedpapermainqdetailslist;
	}

	public void setSelectedaddedpapermainqdetailslist(
			PaperMainQDetails[] selectedaddedpapermainqdetailslist) {
		this.selectedaddedpapermainqdetailslist = selectedaddedpapermainqdetailslist;
	}

	public PaperMainQDetails getPapermainqdetails() {
		return papermainqdetails;
	}

	public void setPapermainqdetails(PaperMainQDetails papermainqdetails) {
		this.papermainqdetails = papermainqdetails;
	}

	public PaperPatternMaster getPaperpatternmaster() {
		return paperpatternmaster;
	}

	public void setPaperpatternmaster(PaperPatternMaster paperpatternmaster) {
		this.paperpatternmaster = paperpatternmaster;
	}

	public List<MainQPatternDetails> getMainqpatterndetailslist() {
		return mainqpatterndetailslist;
	}

	public void setMainqpatterndetailslist(
			List<MainQPatternDetails> mainqpatterndetailslist) {
		this.mainqpatterndetailslist = mainqpatterndetailslist;
	}

	public MainQPatternDetails[] getSelectedmainqpatterndetailslist() {
		return selectedmainqpatterndetailslist;
	}

	public void setSelectedmainqpatterndetailslist(
			MainQPatternDetails[] selectedmainqpatterndetailslist) {
		this.selectedmainqpatterndetailslist = selectedmainqpatterndetailslist;
	}

	public List<QuestionMaster> getSelectedquestionmasterlist() {
		return selectedquestionmasterlist;
	}

	public void setSelectedquestionmasterlist(
			List<QuestionMaster> selectedquestionmasterlist) {
		this.selectedquestionmasterlist = selectedquestionmasterlist;
	}

	public AddNewExamPatternController() {
		marksmaster = new MarksMaster();
		questioncategorymaster = new QuestionCategoryMaster();
		selectedquestionmasterlist = new ArrayList<QuestionMaster>();
		// selectedmainqpatterndetailslist=new ArrayList<MainQPatternDetails>();
		mainqpatterndetailslist = new ArrayList<MainQPatternDetails>();
		paperpatternmaster = new PaperPatternMaster();
		papermainqdetails = new PaperMainQDetails();
		showmainquestionname=false;
		savethisselectedmainqpatterndetailslist=new ArrayList<MainQPatternDetails[]>();
		loadPaperPatternMaster();
	}
	
	private void loadPaperPatternMaster()
	{
		listoldpaperpatternmaster=new ArrayList<TblPaperpatternMaster>();
		listoldpaperpatternmaster = QuestionsUtil.retrieveALLwithHB(new TblPaperpatternMaster(), "TblPaperpatternMaster", "");
	}

	public List<QuestionMaster> getQuestionmasterList() {
		return questionmasterList;
	}

	public void setQuestionmasterList(List<QuestionMaster> questionmasterList) {
		this.questionmasterList = questionmasterList;
	}

	public MarksMaster getMarksmaster() {
		return marksmaster;
	}

	public void setMarksmaster(MarksMaster marksmaster) {
		this.marksmaster = marksmaster;
	}

	public QuestionCategoryMaster getQuestioncategorymaster() {
		return questioncategorymaster;
	}

	public void setQuestioncategorymaster(
			QuestionCategoryMaster questioncategorymaster) {
		this.questioncategorymaster = questioncategorymaster;
	}
//addedpapermainqdetailslist
	public void doShowQuestions() {
		System.out.println("doShowQuestions is start here");
		mainqpatterndetailslist = new ArrayList<MainQPatternDetails>();
		if (marksmaster != null && questioncategorymaster != null) {
			Integer fk1 = questioncategorymaster.getQC_ID();
			Integer fk2 = marksmaster.getM_ID();
			questionmasterList = QuestionsUtil.retrieveWherClause(
					new QuestionMaster(), "QuestionMaster", " QC_ID=" + fk1
							+ " AND M_ID=" + fk2);
			MainQPatternDetails pd = new MainQPatternDetails();
//			OK MITHUN HERE ERROR MQ_ID IS BEING MADE WITH OUT COMMITING THE EARLIER ENTRY U WILL NEED RANDOM NUMBERS HERE
			papermainqdetails.setMQ_ID(QuestionsUtil.doGetNextPKdate());
//					doGetNextPK("PaperMainQDetails", "MQ_Tag",true));
			papermainqdetails.setMQ_Tag(papermainqdetails.getMQ_ID());
			
			for (QuestionMaster elem : questionmasterList) {
				pd = new MainQPatternDetails();
				pd.setMP_ID(QuestionsUtil.doGetNextPKdate());
				System.err.println("HI mith pk for mainqpatterndetails ="+pd.getMP_ID());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						//doGetNextPK("MainQPatternDetails","MP_ID",true));
//						("MainQPatternDetails", "MP_Tag"));
				pd.setMP_Tag(pd.getMP_ID());
				// pd.setMQ_ID(mQ_ID)DONE ADD FOREIGNKEY HERE
				pd.setQ_ID(elem.getQ_ID());
				pd.setMP_Marks(papermainqdetails.getMQ_TotalQmarks());
				pd.setMQ_ID(papermainqdetails.getMQ_ID());
				
				pd.setQuestionText(elem.getQ_Desc());

				mainqpatterndetailslist.add(pd);
				//TODO DONE HERE loaded no of questions to gui defaults to total questions shown
				papermainqdetails.setMQ_Nosofquestion(mainqpatterndetailslist.size());

			}
			System.out.println("doShowQuestions its not null. Size is"
					+ questionmasterList.size());
		} else {
			System.out.println("doShowQuestions its null");
		}
	}

	public void doAddMainQuestions() {
		System.out.println("Made it to start doAddmainQuestions");
		if (addedpapermainqdetailslist == null) {
			addedpapermainqdetailslist = new ArrayList<PaperMainQDetails>();
		}
		showmainquestionname=true;
		PaperMainQDetails obj=new PaperMainQDetails();
		
//			this.papermainqdetails
		 
		obj.setMQ_ID(this.papermainqdetails.getMQ_ID());
		obj.setMQ_Name(this.papermainqdetails.getMQ_Name()); //DONE AUTOMATICALLY
//		obj.setPP_ID() TODO WHEN MAIN SAVING HAPPENS
		obj.setMQ_Tag(this.papermainqdetails.getMQ_ID());// DONE EARLIER
		obj.setMQ_Nosofquestion(this.papermainqdetails.getMQ_Nosofquestion());
		Integer totalmarks=0;
		int index=0;
		// selectedmainqpatterndetailslist
		for (MainQPatternDetails elem : selectedmainqpatterndetailslist) {
			Integer a=elem.getMP_Marks();
			Integer removethisid=elem.getMP_ID();
//			List <MainQPatternDetails> temparr2=new ArrayList<MainQPatternDetails>();
//			temparr2.addAll(mainqpatterndetailslist);
//			int size=mainqpatterndetailslist.size();
			for(int i2=0;i2<mainqpatterndetailslist.size();i2++)
			{
				MainQPatternDetails temp= mainqpatterndetailslist.get(i2);
				
				if(temp.getMP_ID().intValue()==removethisid.intValue())
				{
					mainqpatterndetailslist.remove(i2);
					
				}
			}
			//TODO done add as per the noofquestion the total is calculated for papermainqdetails table
			if(index<obj.getMQ_Nosofquestion())
			{
				totalmarks+=a;
			}
			index++;
		}
		//TODO URGENT ATUL WHAT TO DO ABOUT NOOFQUESTIONS WHEN CALCULATING PP_TOTAL  HOWEVER SEE ABOVE CODE 3 LINES ABOVE
//		obj.setMQ_TotalQmarks(totalmarks);
		obj.setMQ_TotalQmarks(this.papermainqdetails.getMQ_TotalQmarks());
		
		//TODO ADDED below property to act on behalf of MQ_TotalQmarks
		obj.setRealTotalMarks(totalmarks);
//		THIS IS PROBLEM BELOW
		savethisselectedmainqpatterndetailslist.add(selectedmainqpatterndetailslist);
		
		//=Arrays.copyOf(selectedmainqpatterndetailslist,selectedmainqpatterndetailslist.length);
		System.out.println("Mith size of savethisselectedmainqpatterndetailslist"+savethisselectedmainqpatterndetailslist.size()+" No of questiosn is "+selectedmainqpatterndetailslist.length);
		
		selectedmainqpatterndetailslist=null;
		addedpapermainqdetailslist.add(obj);
		paperpatternmaster.setPP_Total(doSum(addedpapermainqdetailslist));
		
		System.out.println("Made it to end doAddmainQuestions");
	}
	
	private <T extends PaperMainQDetails>Integer doSum(List<T> arr)
	{
		Integer ret=0;
		for (T t : arr) {
			 ret+=t.getRealTotalMarks();//getMQ_TotalQmarks();
			
		}
		 
		return ret;
	}
	
	public String doSaveAllQuestions()
	{
		String ret="index";
		
		PaperPatternMaster table2=this.paperpatternmaster;
//		table2.setPP_ID(QuestionsUtil.doGetNextPKdate());
		table2.setPP_ID(QuestionsUtil.doGetNextPK("PaperPatternMaster", "PP_Tag",true));
		
		
//		table2.setPP_Name(pP_Name) DONE Automatically
//		table2.setPP_GraceMarks() DONE AUTOMATICALLY
//		table2.setPP_PassingMarks() DONE AUTOMATICALLY
//		table2.setPP_Total() DONE AUTOMATICALLY
//		table2.setPP_Negativemarks() DONE AUTOMATICALLY
		table2.setPP_Tag(table2.getPP_ID() );
		table2.setPpPaperType("MULTIPLECH");
		System.out.println("Mith Here table 1"+table2.toString());
		QuestionsUtil.saveToNewQuestions(table2);
		
//		for(int i3=0;i3<selectedaddedpapermainqdetailslist.length;i3++)
		for(int i3=0;i3<addedpapermainqdetailslist.size();i3++)
		{
			PaperMainQDetails table3=addedpapermainqdetailslist.get(i3);
//				selectedaddedpapermainqdetailslist[i3];
//			table3.setMQ_ID() DONE EARLIER
//			table3.setMQ_Name() DONE AUTOMATICALLY
//			table3.setMQ_TotalQmarks() DONE AUTOMATICALLY
			table3.setPP_ID(table2.getPP_ID());
//			table3.setMQ_Tag() DONE EARLIER
			System.out.println("Mith Here tabel 2"+table3.toString());
			QuestionsUtil.saveToNewQuestions(table3);
		}
//		for(int i4=0;i4<savethisselectedmainqpatterndetailslist.size();i4++)
//		{
			for(int j1=0;j1<savethisselectedmainqpatterndetailslist.size();j1++)
			{
				MainQPatternDetails[] table4arr=savethisselectedmainqpatterndetailslist.get(j1);
				for (MainQPatternDetails table4 : table4arr) {
					
					System.out.println("Mith Here table 3 "+table4.toString());
					QuestionsUtil.saveToNewQuestions(table4);
				}
			}
			
//			
			
//			table4.setMP_ID() DONE EARLIER
//			table4.setMP_Marks() DONE AUTOMATICALLY
//			table4.setMP_Tag() DONE EARLIER
//			table4.setMQ_ID(mQ_ID) DONE EARLIER
//			table4.setQ_ID(q_ID) DONE EARLIER
			
//		}
		
		return ret;
	}
	
	public void doShowPapers()
	{
		
	}

	public List<TblPaperpatternMaster> getListoldpaperpatternmaster() {
		return listoldpaperpatternmaster;
	}

	public void setListoldpaperpatternmaster(
			List<TblPaperpatternMaster> listoldpaperpatternmaster) {
		this.listoldpaperpatternmaster = listoldpaperpatternmaster;
	}
}
