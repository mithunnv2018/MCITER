package com.mciter.commonbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mciter.services.QuestionsUtil;

@Entity
public class QuestionMaster implements Serializable{

	private Integer Q_ID,QC_ID,U_ID,M_ID;
	private String Q_Name,Q_Desc;
	private Integer Q_Tag;
	private Double marks;

	private String categoryname;

	private ArrayList<QuestionOptions>  answerOptionsList;
	private Integer[] answerNumberList;
	private Integer answernumber2;
	private Integer[] correctanswerList;
	private boolean donotload=false;
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}	
	@Override
	public String toString() {
		return "QuestionMaster [Q_ID=" + Q_ID + ", QC_ID=" + QC_ID + ", U_ID="
				+ U_ID + ", M_ID=" + M_ID + ", Q_Name=" + Q_Name + ", Q_Desc="
				+ Q_Desc + ", Q_Tag=" + Q_Tag + ", Q_MultiSelect="
				+ Q_MultiSelect + ", Q_Option=" + Q_Option + "]";
	}

	public Integer[] getCorrectanswerList() {
		return correctanswerList;
	}

	public void setCorrectanswerList(Integer[] correctanswerList) {
		this.correctanswerList = correctanswerList;
	}

	public Integer getAnswernumber2() {
		return answernumber2;
	}

	public void setAnswernumber2(Integer answernumber2) {
		this.answernumber2 = answernumber2;
	}

	public Integer[] getAnswerNumberList() {
		return answerNumberList;
	}

	public void setAnswerNumberList(Integer[] answerNumberList) {
		this.answerNumberList = answerNumberList;
	}

	public QuestionMaster()
	{
		answerOptionsList=new ArrayList<QuestionOptions>();
		correctanswerList=new Integer[1];
		donotload=false;
//		for(int i=0;i<10;i++)
//		{
//			QuestionOptions op=new QuestionOptions();
//			op.setQ_ID(new Random().nextInt(1000));
//			op.setQ_Selected(false);
//			op.setQO_Name("good");
//			op.setQO_Tag(op.getQ_ID()+"");
//			answerOptionsList.add(op);
//		}
	}
	
	public ArrayList<QuestionOptions> getAnswerOptionsList() {
		return answerOptionsList;
	}
	public void setAnswerOptionsList(ArrayList<QuestionOptions> answerOptionsList) {
		this.answerOptionsList = answerOptionsList;
	}
	public Double getMarks() {
		return marks;
	}
	public void setMarks(Double marks) {
		this.marks = marks;
	}
	public Integer getQ_ID() {
		return Q_ID;
	}
	public void setQ_ID(Integer q_ID) {
		Q_ID = q_ID;
	}
	public Integer getQC_ID() {
		return QC_ID;
	}
	public void setQC_ID(Integer qC_ID) {
		QC_ID = qC_ID;
	}
	public Integer getU_ID() {
		return U_ID;
	}
	public void setU_ID(Integer u_ID) {
		U_ID = u_ID;
	}
	public Integer getM_ID() {
		return M_ID;
	}
	public void setM_ID(Integer m_ID) {
		M_ID = m_ID;
	}
	public String getQ_Name() {
		return Q_Name;
	}
	public void setQ_Name(String q_Name) {
		Q_Name = q_Name;
	}
	public String getQ_Desc() {
		return Q_Desc;
	}
	public void setQ_Desc(String q_Desc) {
		Q_Desc = q_Desc;
	}
	public Integer getQ_Tag() {
		return Q_Tag;
	}
	public void setQ_Tag(Integer q_Tag) {
		Q_Tag = q_Tag;
	}
	public Boolean getQ_MultiSelect() {
		return Q_MultiSelect;
	}
	public void setQ_MultiSelect(Boolean q_MultiSelect) {
		Q_MultiSelect = q_MultiSelect;
	}
	public Integer getQ_Option() {
		return Q_Option;
	}
	public void setQ_Option(Integer q_Option) {
		Q_Option = q_Option;
		if(donotload==false)
		{
		 answerOptionsList=new ArrayList<QuestionOptions>(Q_Option);
		}
		answerNumberList=new Integer[Q_Option];
		for(int i=0;i<q_Option;i++)
		{
			QuestionOptions op=new QuestionOptions();
			op.setQ_ID(new Random().nextInt(1000));
			op.setQ_Selected(false);
			op.setQO_Name("good");
			op.setQO_Tag(op.getQ_ID());
			if(donotload==false)
			{
				answerOptionsList.add(op);
			}
			answerNumberList[i]=i+1;
		}
//		new QuestionOptions().setQO_Name(qO_Name)
	}
	public String doUpdateQuestion()
	{
		String ret="index";
		
		QuestionMaster mainobj=this;
		mainobj.setQ_Name("Updated Q_Name");
//		mainobj.setQC_ID()//DONE  automatically
//		mainobj.setQ_Desc()//DONE automatically

		UserMaster foreignkey3=this.getUserInfo();
		
		if(foreignkey3!=null)
		{
			mainobj.setU_ID(foreignkey3.getU_ID());
			System.out.println("Foreign key is it there:"+foreignkey3);
		}
		else
		{
			mainobj.setU_ID(338);
		}
//		mainobj.setM_ID(foreignkey2.getM_ID());//DONE automatically
//		mainobj.setQ_MultiSelect()//DONE automatically
//		mainobj.setQ_Option(q_Option)//DONE automatically
		QuestionsUtil.updateToQuestions(mainobj);
		int rowsdeleted=QuestionsUtil.deleteFromDB("QuestionOptions", "Q_ID="+mainobj.getQ_ID());
		dosSaveQuestionOptions(mainobj);
		resetSessionMith("questionMaster2");
		return ret;
	}
	public String doSaveQuestion()
	{
		QuestionMaster mainobj=this;
		mainobj.setQ_ID(QuestionsUtil.doGetNextPK("QuestionMaster", "Q_Tag"));
		mainobj.setQ_Name("DONOT KNOW");//TODO DONOT KNOW WHAT IS Q_NAME
//		mainobj.setQC_ID()//DONE  automatically
//		mainobj.setQ_Desc()//DONE automatically
//		MarksMaster foreignkey2=new MarksMaster();
//		foreignkey2.setM_ID(QuestionsUtil.doGetNextPK("", ""));
//		foreignkey2.setM_Marks(this.marks);
//		foreignkey2.setM_Name(this.marks.toString());
//		foreignkey2.setM_Tag(foreignkey2.getM_ID().toString());
		UserMaster foreignkey3=this.getUserInfo();
		
		if(foreignkey3!=null)
		{
			mainobj.setU_ID(foreignkey3.getU_ID());
			System.out.println("Foreign key is it there:"+foreignkey3);
		}
		else
		{
			mainobj.setU_ID(338);
		}
		
//		mainobj.setM_ID(foreignkey2.getM_ID());//DONE automatically
//		mainobj.setQ_MultiSelect()//DONE automatically
//		mainobj.setQ_Option(q_Option)//DONE automatically
		mainobj.setQ_Tag(mainobj.getQ_ID());
		
//		QuestionsUtil.saveToNewQuestions(foreignkey2);
		QuestionsUtil.saveToNewQuestions(mainobj);
		dosSaveQuestionOptions(mainobj);
//		resetSessionMith();
//		getUserInfo();
		return "index";
//		return "testaddnewquestionmaster?redirect=true";
		 
		
	}

	/**
	 * @param mainobj
	 */
	private void dosSaveQuestionOptions(QuestionMaster mainobj) {
		Integer index=0;
		for (QuestionOptions obj2 : mainobj.answerOptionsList) {
			System.out.println("Mith Inside loop");
			obj2.setQO_ID(QuestionsUtil.doGetNextPK("QuestionOptions", "QO_Tag"));
			obj2.setQ_ID(mainobj.getQ_ID());
			if(this.Q_MultiSelect)
			{
				System.out.println("Mith Length of corrrectanswerlist "+correctanswerList.length);
//				for(Integer a:this.correctanswerList)
				
				for(int i=0;i<correctanswerList.length;i++)
				{
					Integer a=correctanswerList[i]-1;
					if(index.intValue()==a.intValue())
					{
						obj2.setQ_Selected(true);
						System.out.println("Mith index is: "+index+" a="+a);
						break;
					}
					else
					{
						obj2.setQ_Selected(false);
					}
					System.out.println("Cotent of correctanswrlist is:"+a.intValue());
					
				}
				
				
			}
			else
			{
				if(index.intValue()==(answernumber2.intValue()-1))
				{
					obj2.setQ_Selected(true);
					System.out.println("Mith radio answer to true" +index+"naswer ="+answernumber2);
				}
				else
				{
					obj2.setQ_Selected(false);
					System.out.println("Mith radio answer to false index=" +index+"naswer ="+answernumber2);
				}
				
			}
			index++;
//			obj2.setQ_Selected()//DONE manually
//			obj2.setQO_Name()//DONE automatically
			obj2.setQO_Tag(obj2.getQO_ID() );
			QuestionsUtil.saveToNewQuestions(obj2);
		}
	}
	public UserMaster getUserInfo()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		Map<String, Object> sessionMap = externalContext.getSessionMap();
//		System.out.println(" Mithun Working Session map");
//
//		for (String va2 : sessionMap.keySet()) {
//			
//			System.out.println(" Key is :"+va2+" Value is :"+sessionMap.get(va2));
//		}
		if(sessionMap.get("userMaster")!=null)
		{
			return (UserMaster)sessionMap.get("userMaster");
		}
		else
		{
			return null;
		}
	}
	public String resetSessionMith(String sessionname)
	{
		ExternalContext ctx= FacesContext.getCurrentInstance().getExternalContext();
//		Map<String, Object> sessionMap = ctx.getSessionMap();
//		QuestionMaster m=(QuestionMaster)sessionMap.get("questionMaster");
		
		HttpSession session=(HttpSession)ctx.getSession(false);
		session.removeAttribute(sessionname);
		return null;
//		session.invalidate();
	}
	
	public void doParameterQuestionMaster(ActionEvent e)
	{
		ExternalContext ctx= FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest req=(HttpServletRequest) ctx.getRequest();
		HttpSession session = (HttpSession)ctx.getSession(false);
		
		doForUpdateAnswerOptionList();
		
		System.out.println("Mith QuestionMasterBean session here "+session.getAttribute("questionMaster2"));
		session.setAttribute("questionMaster2", this);
		req.setAttribute("questionMaste2", this);
		System.out.println("Mith QuestionMasterBean DONE here ");
	}
	
	/*
	 * //DONE HERE I RETIEVE QUESTIONOPTIONS WITH REPECT TO THE Q_ID
	 */
	private void doForUpdateAnswerOptionList()
	{
		
		this.donotload=true;
		List<QuestionOptions> result= QuestionsUtil.retrieveWherClause(new QuestionOptions(), "QuestionOptions", " Q_ID="+this.Q_ID);
		this.answerOptionsList.clear();
		this.answerOptionsList.addAll(result);
		ArrayList<Integer> tempcorrectanswerlist=new ArrayList<Integer>();
		for(int i=0;i<result.size();i++)
		{
			QuestionOptions p2=result.get(i);
			if(Q_MultiSelect==false)
			{
				if(p2.getQ_Selected()==true)
				{
					this.answernumber2=new Integer(i+1);
				}
				
			}
			else
			{
				if(p2.getQ_Selected()==true)
				{
					tempcorrectanswerlist.add(new Integer(i+1));
					correctanswerList=tempcorrectanswerlist.toArray(new Integer[0]);
				}
			}
		}
		
	}
	
	
	private Boolean Q_MultiSelect;
	private Integer Q_Option;
	
	
}
