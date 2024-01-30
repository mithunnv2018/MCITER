package com.mciter.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.junit.Test;

import com.mciter.commonbeans.CenterDetails;
import com.mciter.commonbeans.QuestionCategoryMaster;
import com.mciter.commonbeans.QuestionMaster;
import com.mciter.commonbeans.QuestionOptions;
import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblMainqpatternDetails;
import com.mciter.commonbeans.TblMainqpatternDetailsHome;
import com.mciter.commonbeans.TblPapermainqDetails;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestionOptions;
import com.mciter.commonbeans.TblQuestionsMaster;
import com.mciter.commonbeans.TblQuestionsMasterHome;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.commonbeans.TblStudentexamConductHome;
import com.mciter.commonbeans.TblStudentpaperSession;
import com.mciter.commonbeans.TblStudentpaperSessionHome;
import com.mciter.commonbeans.UserMaster;
import com.mciter.services.QuestionsUtil;
import com.mciter.services.StudentExamPaperController;
import com.mciter.utils.CommonParams2;

public class TestCase1 {

	 
	public void testSaveToDB() {
//		UserMaster u=new UserMaster();
//		u.setU_UserName("Mithun");
//		u.setU_Pass("mypass");
//		System.out.println("Here!");
//		String result=u.saveToDB();
//		assertEquals("success", result);
//		System.out.println("Done here!"+result);
	}

	 
	public void testchecklogin()
	{
		UserMaster u=new UserMaster();
		u.setU_UserName("Mithun");
		u.setU_Pass("mypass");
		System.out.println("Here!");
		String result=u.checklogin();
		assertEquals("successlogin", result);
		System.out.println("Done here!"+result);
	}

	 
	public void testretrieveall()
	{
		System.out.println("Start test retrieval");
		List query = QuestionsUtil.retrieveALL(new QuestionCategoryMaster(), "QuestionCategoryMaster"," QC_ID , QC_Name");
		assertNotNull(query);
		for(Iterator it=query.iterator();it.hasNext();){
			 Object[] row = (Object[]) it.next();
			 System.out.println("ID: " + row[0]);
			 System.out.println("Name: " + row[1]);
			 
			 }
	 
		
		System.out.println("Ok done");
	}
	
	public void testsavetodb()
	{
		//PUT @ Test annotatiobn to run this test
		System.out.println("Start test save to db");
		QuestionCategoryMaster an=new QuestionCategoryMaster();
		an.setQC_ID(QuestionsUtil.doGetNextPK("", ""));
		an.setQC_Name("Math version:"+an.getQC_ID().toString());
		an.setQC_Tag(an.getQC_ID());
		QuestionsUtil.saveToNewQuestions(an);
		assertTrue(true);
		System.out.println("Worked check in DB now");
	}
	 
	public void testUserInfo()
	{
		//WILL WORK ONLY ON SERVERS SIDE NOT HERE
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		System.out.println("Working Session map");

		for (String va2 : sessionMap.keySet()) {
			
			System.out.println("Key is :"+va2+" Value is :"+sessionMap.get(va2));
		}
		 
	}
	
	 
	public void testQuestionMasterList()
	{
		CommonParams2 p=new CommonParams2();
		List<QuestionMaster> questionMasterList = p.getQuestionMasterList();
		for (QuestionMaster q : questionMasterList) {
			
			System.out.println(""+q.toString());
			
		}
	}
	 
	public void testRetrieveWhereClause()
	{
//		QuestionOptions op=new QuestionOptions();
//		Integer fk=4606;
//		List<QuestionOptions> result= QuestionsUtil.retrieveWherClause(op, "QuestionOptions", " Q_ID="+fk);
		System.out.println("Here testRetrieveWhereClause");
//		for (QuestionOptions questionOptions : result) {
//			
//			System.out.println("data is"+questionOptions.toString());
//		}
		/*Integer fk2=1,fk3=89;
		List<QuestionMaster> result2= QuestionsUtil.retrieveWherClause(new QuestionMaster(), "QuestionMaster", " QC_ID="+fk2+" AND M_ID="+fk3);
		 for (QuestionMaster qm : result2) {
			System.out.println("data is:"+qm.toString());
		}
		 String username="MCITER/AJ121";
		 String password="abcd";
		 List<TblStudentexamConduct> list2= QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", " Studentid='"+username+"'  AND sePassword='"+password+"' " );
		 System.out.println("Mith Student exam");
		 
		 for (TblStudentexamConduct obj : list2) {
			System.out.println(obj.getSePassword());
			System.out.println("Anp code"+obj.getTblExaminationConduct().getTblCenterDetails().getAnpcode());
		}*/
		List<TblPapermainqDetails> result2=QuestionsUtil.retrieveWherClause(new TblPapermainqDetails(), "TblPapermainqDetails","PP_Id="+1);
		List<TblQuestionOptions> options = QuestionsUtil.retrieveWherClause(new TblQuestionOptions(), "TblQuestionOptions", " Q_Id="+1);
		System.out.println("mqname "+result2.get(0).getMqName());
		System.out.println("options "+options.get(0).getQoName());

		assertNotNull("Yes query is working",result2);
		
	}
	 
	public void testDeleteWhereClause()
	{
		System.out.println("Here testDeleteWhereClause");
		
		int rows=QuestionsUtil.deleteFromDB("QuestionOptions", "Q_ID=2753");
		System.out.println("So may rows deleed "+rows);
	}
	
	 
	public void testdoGetNextPK()
	{
//		int a=QuestionsUtil.doGetNextPK("CenterDetails", "tag");
		int b=QuestionsUtil.doGetNextPK("TblStudentpaperSession", "studPapId");
				//"QuestionOptions", "QO_Tag");
		int c=QuestionsUtil.doGetNextPK("MainQPatternDetails","MP_ID",true);
//				"TblStudentpaperSession", "studPapId",true);
		Integer excId=QuestionsUtil.doGetNextPK("TblExaminationConduct", "Exc_Tag",true);
//		int pk=a+1;
		Integer val=QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
		Integer ppdId=QuestionsUtil.doGetNextPK("TblPaperpatternMaster","PP_ID");
//		System.out.println("testdogetnextpk:"+a);
		System.out.println("testdogetnextpk 2nd:"+b);
		System.out.println("testdogetnextpk 3rd:"+c);
		System.out.println("testdogetnextpk 4th:"+excId);
		System.out.println("testdogetnextpk 5th:"+val);
		System.out.println("testdogetnextpk 6th:"+ppdId);

		
	}
	 
	public void testgetMarksList()
	{
		CommonParams2 p=new CommonParams2();
		List<SelectItem> marksList = p.getMarksList();
		System.out.println("testgetmarksList here"+marksList.size());
		for(int i=0;i<marksList.size();i++)
		{
			SelectItem m=marksList.get(i);
			if(i>=1)
			{
				Integer r=(Integer)m.getValue();
			}
			System.out.println("value is"+m.getValue());
			System.out.println("label is"+m.getLabel());
			
		}
	}
	

	public void testPaperDBLoading()
	{
		String username="MCITER/AJ121";
		 String password="abcd";
		 List<TblStudentexamConduct> list2= QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", " Studentid='"+username+"'  AND sePassword='"+password+"' " );
		 System.out.println("Mith DB Loading Student exam ");
		 TblStudentexamConduct studentexamconduct=null;
		 for (TblStudentexamConduct obj : list2) {
			System.out.println(obj.getSePassword());
			System.out.println("Anp code"+obj.getTblExaminationConduct().getTblCenterDetails().getAnpcode());
			studentexamconduct=obj;
		}
		
		try
		{
		TblExaminationConduct tblExaminationConduct = studentexamconduct.getTblExaminationConduct();
		
		 Integer excDuration = tblExaminationConduct.getExcDuration();
		studentexamconduct.setSeRemaintime(excDuration);
		System.out.println("Student is working "+studentexamconduct.getSeStarttime());
//		QuestionsUtil.saveToNewQuestions(studentexamconduct);
		ArrayList<TblQuestionsMaster> allquestions=new ArrayList<TblQuestionsMaster>();
		ArrayList<TblStudentpaperSession> studentpapersession=new ArrayList<TblStudentpaperSession>();
		ArrayList<TblMainqpatternDetails> mainqpatterndetailslist=new ArrayList<TblMainqpatternDetails>();
		
		
		TblPaperpatternMaster tblPaperpatternMaster = studentexamconduct.getTblPaperpatternMaster();
		System.out.println("tblPaperpatternmaster here "+tblPaperpatternMaster.getTblPapermainqDetailses().size());
		Set<TblPapermainqDetails> settblPapermainqDetailses = tblPaperpatternMaster.getTblPapermainqDetailses();
		
		for (TblPapermainqDetails tblPapermainqDetails : settblPapermainqDetailses) {
			System.out.println("TBlPapermainqdetails ="+tblPapermainqDetails.getMqName());
			Integer mqid=tblPapermainqDetails.getMqId();
			System.out.println("MQID is "+mqid);
			List<TblMainqpatternDetails> listofmainqpatterndetails = QuestionsUtil.retrieveWherClause(new TblMainqpatternDetails(), "TblMainqpatternDetails", " MQ_ID="+mqid);
			 
//			Set<TblMainqpatternDetails> tblMainqpatternDetailses = tblPapermainqDetails.getTblMainqpatternDetailses();
			
			System.out.println("TblMainqpatterndetails list2 "+listofmainqpatterndetails.size());
			for (TblMainqpatternDetails objtblMainqpatternDetails : listofmainqpatterndetails) {
				
				System.out.println("Made inside core");
				Integer qid=objtblMainqpatternDetails.getTblQuestionsMaster().getQId();
				List<TblQuestionsMaster> tempquestionmasterlist = QuestionsUtil.retrieveWherClause(new TblQuestionsMaster(), "TblQuestionsMaster", " QId="+qid);

				allquestions.add(tempquestionmasterlist.get(0));
				mainqpatterndetailslist.add(objtblMainqpatternDetails);
//						objtblMainqpatternDetails.getTblQuestionsMaster());
				
				System.out.println("Made inside  "+objtblMainqpatternDetails.getTblQuestionsMaster().getQId());
				TblStudentpaperSession newobj=new TblStudentpaperSession(objtblMainqpatternDetails, studentexamconduct, "",false);
				newobj.setStudPapId(QuestionsUtil.doGetNextPK("TblStudentpaperSession", "studPapId"));
//				new TblStudentpaperSessionHome().persist(newobj);
//				QuestionsUtil.saveToNewQuestions(newobj);
				
				
			}
		}
		System.out.println("Question list"+allquestions.size());
		
		for(int i=0;i<allquestions.size();i++)
		{
			TblQuestionsMaster m=allquestions.get(i);
			TblMainqpatternDetails m2 = mainqpatterndetailslist.get(i);
//			System.out.println("Question is "+m.getTblQuestionOptionses().size());
			System.out.println("Question is "+m.getQDesc());
			System.out.println("Mainqpatterndetails  is "+m2.getMpMarks());
			
			
		}
		}
		catch(Exception e)
		{
			System.out.println("error here"+e.getMessage());
			e.printStackTrace();
		}

	}
	
	 
	public void testRandomIndexArray()
	{
//		StudentExamPaperController p=new StudentExamPaperController();
//		ArrayList<Integer> do2 = p.doGetRandomArrayIndex(5, 20);
//		for (Integer integer : do2) {
//			System.out.println("The Indexs are :"+integer);
//		}
//		
//		assertNotNull(do2);
	}
	
	 
	public void testLogFeature()
	{
		CommonParams2.showMessageOnLog(this.getClass(), "can u see me hi");
	}
	
	 
	public void testCountdown()
	{
		
		List<TblStudentexamConduct> abc = QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(), "TblStudentexamConduct", "Se_Id=1");
		System.out.println("testcountdown sieze="+abc.size());
		
		TblStudentexamConduct def = abc.get(0);
		
		
		Date starttime = def.getSeStarttime();
		Date endtime = def.getSeEndtime();
		Date currenttime=new Date();
		Calendar cd2=Calendar.getInstance();
		Calendar cd3=Calendar.getInstance();
		Calendar cd4=Calendar.getInstance();
		Calendar result=Calendar.getInstance();
		
		cd2.setTime(starttime);
		cd3.setTime(endtime);
		cd4.setTime(starttime);//currenttime);
		result.setTime(endtime);
		int currenthour = cd4.get(Calendar.HOUR_OF_DAY);//15
		int endhour = cd3.get(Calendar.HOUR_OF_DAY);//15
		
		int endmins = cd3.get(Calendar.MINUTE)*60;//19
		int currentmins = cd4.get(Calendar.MINUTE)*60;//10
		
		endmins+=cd3.get(Calendar.SECOND);
		currentmins+=cd4.get(Calendar.SECOND);
		
		
//		result.add(Calendar.HOUR_OF_DAY, -currenthour);
		
		
		
		int diffhour=endhour-currenthour;//0
		int diffseconds=endmins-currentmins;//9
		final int HOUR2=60*60;
		final int MINUTES2=60;
		
		int resulthourinseconds=diffhour*HOUR2;//0
		int resultminutesinsecond=diffseconds;//540
		
		int result3=resulthourinseconds+resultminutesinsecond;
		
//		Date d=new Date(difference);
		
//		difference/=86400000;
		System.out.println("Curent time is:");
		System.out.println();
		
		System.out.println("difference in seconds ="+result3+" ; " );
	}
	
	 
	public void testwherewithsort()
	{
		String anpcode="ANPMCITER12";
		Integer qcid=1;
		
		List<TblStudentDetails> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblStudentDetails(), "TblStudentDetails", "anpcode='"+anpcode+"' AND QC_ID="+qcid +"  AND active='active' order by registerationdate desc" );
		for (TblStudentDetails stddetails : retrieveWherClause) {
			System.out.println("Student "+stddetails.getRegisterationdate()+ "Name is :"+stddetails.getFirstname());
			
		}
		assertNotNull(retrieveWherClause);
		
	}
	
	 
	public void testcalendarasperIndia()
	{
//		Calendar indiancalendar = CommonParams2.getIndianDate();//getIndiancalendar();
		Date dt=CommonParams2.getIndianDate();
		System.out.println("Date is ="+dt.toString());
	}
	 
	public void testencodedecode()
	{
		System.out.println("TestCase1.testencodedecode()");
		String hello="abc \" def";
		String decoded="",encoded="";
		encoded=CommonParams2.encodeToAscii(hello);
		decoded=CommonParams2.decodeFromAscii(encoded);
		System.out.println("endoced="+encoded+" decoded="+decoded);
		
	}
	 
	public void testdoGetNextPKdate()
	{
		Integer doGetNextPKdate = QuestionsUtil.doGetNextPKdate();
		System.out.println("pk= "+doGetNextPKdate);
	}
	
	@Test
	public void testTimeProblem()
	{
		int excDuration=60;

		Calendar cal2 = Calendar.getInstance();
		cal2.set(2013, 5, 27, 23, 50);
		Date today = cal2.getTime();
		System.out.println("Start time"+today);
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		cal.add(Calendar.MINUTE, excDuration);
		Date endtime = cal.getTime();
		System.out.println("End time="+endtime);
		
		long endtimeInMillis = cal.getTimeInMillis()/1000;
//		endtimeInMillis/=1000;
		long currtimeInMillis = cal2.getTimeInMillis()/1000;
//		currtimeInMillis/=1000;
		
		int newremtimeInMillis=(int)(endtimeInMillis-currtimeInMillis);
		
		System.out.println("real time in millis="+newremtimeInMillis);
		
		Integer endtimeinseconds = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60;
		endtimeinseconds += (cal.get(Calendar.MINUTE) * 60);
		endtimeinseconds += (cal.get(Calendar.SECOND));

		Integer currenttimeinseconds = cal2.get(Calendar.HOUR_OF_DAY) * 60 * 60;
		currenttimeinseconds += (cal2.get(Calendar.MINUTE) * 60);
		currenttimeinseconds += (cal2.get(Calendar.SECOND));

		int newremtime = endtimeinseconds - currenttimeinseconds;

		System.out.println("endtimeinseconds="+endtimeinseconds+"currenttimeinseconds="+currenttimeinseconds+"newremtime="+newremtime);
		
		
	}
}
