package com.mciter.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

//import com.mciter.commonbeans.MainQPatternDetails;
//import com.mciter.commonbeans.PaperMainQDetails;
//import com.mciter.commonbeans.QuestionOptions;
import com.mciter.commonbeans.TblCategoryMaster;
import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblDemousers;
import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblMainqpatternDetails;
import com.mciter.commonbeans.TblMarksMaster;
import com.mciter.commonbeans.TblPapermainqDetails;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestionOptions;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblQuestionsMaster;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.commonbeans.TblStudentpaperSession;
import com.mciter.commonbeans.TblStudentpaperSessionHome;
import com.mciter.commonbeans.TblStudentresultDetails;
import com.mciter.utils.CommonParams2;

public class StudentExamPaperController {

	private String count;
	private Boolean stop;
	private Integer stopincrement = 60;
	private Integer interval = 60;
	private TblStudentexamConduct studentexamconduct;
	private TblExaminationConduct examinationconduct;
	private List<TblQuestionsMaster> allquestions;
	private TblQuestionsMaster currentquestion;
	private TblStudentDetails studentdetails;
	private ArrayList<TblMainqpatternDetails> mainqpatterndetailslist = new ArrayList<TblMainqpatternDetails>();

	private List<TblQuestionOptions> questionOptionsList;
	private TblQuestionOptions[] questionOptionsSelected;
	private TblQuestionOptions questionOptionsSingleSelected;
	private Integer INDEX = 0;
	private Integer ENDINDEX = 0;

	// private ArrayList<TblQuestionOptions[]> savedanswersList;
	private HashMap<Integer, TblQuestionOptions[]> savedanswersList;
	private HashMap<Integer, List<TblQuestionOptions>> savedshowanswersList;
	private HashMap<Integer, TblStudentpaperSession> savedstudentpapersession;
	private HashMap<Integer, String> exammqname;
	private String currentquestiontypename = "";
	private Integer noofcorrectanswers = 0;
	private Integer totalmarks = 0;
	private Integer totalmarksattained = 0;

	private Integer totalpercentageattained = 0;
	private String totalgradeattained = "";

	private ScheduledFuture<?> scheduleAtFixedRate = null;
	private ScheduledExecutorService scheduler = null;
	private Integer countdownseconds = 0;
	private String RELOGIN = null;
	private TblStudentresultDetails tblstudentresultdetails2 = null;

	private TreeNode rootTreeNode = new DefaultTreeNode("Root", null);

	private String currentmonthyear;
	private TblCenterDetails centerdetails;
	private String coursename = "";
	private String courseduration = "";

	private String idofbutton = "idtree";
	private TreeNode showthisnode = null;
	private ArrayList<List<HashMap<String, Integer>>> accordianlist = null;
	private String clientidofnode;
	private HashMap<String, String> clientidhashmap = null;

	private String username, password;

	public ArrayList<List<HashMap<String, Integer>>> getAccordianlist() {
		return accordianlist;
	}

	public void setAccordianlist(
			ArrayList<List<HashMap<String, Integer>>> accordianlist) {
		this.accordianlist = accordianlist;
	}

	public List<TblQuestionOptions> getQuestionOptionsList() {
		return questionOptionsList;
	}

	public void setQuestionOptionsList(
			List<TblQuestionOptions> questionOptionsList) {
		this.questionOptionsList = questionOptionsList;
	}

	public TblQuestionOptions[] getQuestionOptionsSelected() {
		return questionOptionsSelected;
	}

	public void setQuestionOptionsSelected(
			TblQuestionOptions[] questionOptionsSelected) {
		this.questionOptionsSelected = questionOptionsSelected;
	}

	public TblQuestionOptions getQuestionOptionsSingleSelected() {
		return questionOptionsSingleSelected;
	}

	public void setQuestionOptionsSingleSelected(
			TblQuestionOptions questionOptionsSingleSelected) {
		this.questionOptionsSingleSelected = questionOptionsSingleSelected;
	}

	public ArrayList<TblMainqpatternDetails> getMainqpatterndetailslist() {
		return mainqpatterndetailslist;
	}

	public void setMainqpatterndetailslist(
			ArrayList<TblMainqpatternDetails> mainqpatterndetailslist) {
		this.mainqpatterndetailslist = mainqpatterndetailslist;
	}

	public TblStudentDetails getStudentdetails() {
		return studentdetails;
	}

	public void setStudentdetails(TblStudentDetails studentdetails) {
		this.studentdetails = studentdetails;
	}

	public TblQuestionsMaster getCurrentquestion() {
		return currentquestion;
	}

	public void setCurrentquestion(TblQuestionsMaster currentquestion) {
		this.currentquestion = currentquestion;
	}

	public StudentExamPaperController() {
		stop = false;
		stopincrement = 10;
		count = "00:00";
		studentexamconduct = new TblStudentexamConduct();

		allquestions = new ArrayList<TblQuestionsMaster>();
		studentdetails = new TblStudentDetails();
		savedanswersList = new HashMap<Integer, TblQuestionOptions[]>();
		savedstudentpapersession = new HashMap<Integer, TblStudentpaperSession>();
		savedshowanswersList = new HashMap<Integer, List<TblQuestionOptions>>();
		// questionOptionsSelected=new TblQuestionOptions[];
	}

	private void doJavaScript2(boolean show) {
		RequestContext requestcontext = RequestContext.getCurrentInstance();
		System.out.println("calling java script");
		if (show == true) {
			requestcontext.execute("alert('...Exam Loading!')");
		} else {
			// requestcontext.execute("bar.hide()");//hideStatusDialog()");
		}
		System.out.println("Done calling java script");
	}

	private Boolean doLoadDemoLogin() {
		Boolean returnvalue = false;

		try {
			System.out.println("StudentExamPaperController.doLoadDemoLogin()");
			List<TblDemousers> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblDemousers(), "TblDemousers",
							"studentid='" + username + "'");
			if (retrieveWherClause.size() > 0) {
				returnvalue = true;
				TblDemousers tblDemousers = retrieveWherClause.get(0);
				Integer seId = studentexamconduct.getSeId();
				int deleteFromDB = QuestionsUtil.deleteFromDB(
						"TblStudentpaperSession", "Se_Id=" + seId);
				int deleteFromDB2 = QuestionsUtil.deleteFromDB(
						"TblStudentresultDetails", "SE_Id=" + seId);
				studentexamconduct.setSeStatus("active");
				QuestionsUtil.updateToQuestions(studentexamconduct);
				System.out.println("Ok delted from session=" + deleteFromDB
						+ " deleted from result=" + deleteFromDB2);

			} else {
				returnvalue = false;
			}
		} catch (Exception e) {
			System.err.println("error here =" + e.getMessage());
			e.printStackTrace();
		}

		return returnvalue;
	}

	public String doStudentLogin() {
		// String username2 = studentdetails.getStudentid();
		// String password2 = studentexamconduct.getSePassword();

		doJavaScript2(true);

		try {
			List<TblStudentexamConduct> list2 = QuestionsUtil
					.retrieveWherClause(new TblStudentexamConduct(),
							"TblStudentexamConduct", " Studentid='" + username
									+ "'  AND sePassword='" + password + "' ");

			if (list2 != null) {
				if (list2.size() > 0) {
					studentexamconduct = list2.get(0);
					doLoadDemoLogin();

					if (!doCheckIfExaminationConductIsOk(studentexamconduct)) {
						CommonParams2
								.showMessageOnScreen("Sorry Examination Status has expired!");
						return null;
					}

					List<TblStudentDetails> retrieveWherClause2 = QuestionsUtil
							.retrieveWherClause(new TblStudentDetails(),
									"TblStudentDetails", "studentid='"
											+ username + "' ");
					studentdetails = retrieveWherClause2.get(0);

					String anpcode2 = studentdetails.getTblCenterDetails()
							.getAnpcode();
					List<TblCenterDetails> retrieveWherClause3 = QuestionsUtil
							.retrieveWherClause(new TblCenterDetails(),
									"TblCenterDetails", "anpcode='" + anpcode2
											+ "' ");
					centerdetails = retrieveWherClause3.get(0);

					if (!studentexamconduct.getSeStatus().equalsIgnoreCase(
							"active")) {
						CommonParams2
								.showMessageOnScreen("Sorry Student Your Status is INACTIVE");
						return null;
					}
					if (doCheckIfReExam()) {
						if (studentexamconduct.getSeRemaintime().intValue() == 0) {
							reloadTheStudent();
							// endofexam();
							CommonParams2.showMessageOnLog(this.getClass(),
									"Hi   end of exam since relogin! EndINdex="
											+ ENDINDEX);
							stop = true;
							return "testexampaperend";
						}
						reloadTheStudent();
					} else {
						loadTheStudent();
					}
					// return "testexampaper";
					return "testexampaper";

				} else {
					CommonParams2.showMessageOnScreen("Invalid Student Login!");
				}
			} else {
				CommonParams2.showMessageOnScreen("Null Student Login!");
				CommonParams2.showMessageOnLog(this.getClass(),
						"Nulll login of student");
			}

		} catch (Exception ex) {
			System.out.println("doStudentLogin error:" + ex.getMessage());
			CommonParams2
					.showMessageOnScreen("Sorrry You have a Exception contact Admin");
			ex.printStackTrace();
		} finally {
			doJavaScript2(false);
		}
		return null;
	}

	/**
	 * Examination table is queried and checked if date is valid for exam to be
	 * conducted
	 * 
	 * @param paramstudentexamconduct2
	 * @return
	 */
	private boolean doCheckIfExaminationConductIsOk(
			TblStudentexamConduct paramstudentexamconduct2) {

		Integer excId = paramstudentexamconduct2.getTblExaminationConduct()
				.getExcId();
		List<TblExaminationConduct> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblExaminationConduct(),
						"TblExaminationConduct", "Exc_Id=" + excId);
		if (retrieveWherClause == null || retrieveWherClause.size() == 0) {
			return false;
		}
		TblExaminationConduct localexamconduct2 = retrieveWherClause.get(0);
		String excActive = localexamconduct2.getExcActive();
		Date excendtime2 = localexamconduct2.getExcStudentendtime();
		Date excstarttime2 = localexamconduct2.getExcStudentstarttime();
		Date currenttime = CommonParams2.getIndiancalendar().getTime();
		if (!excActive.equalsIgnoreCase("active")) {
			return false;
		}

		if (currenttime.after(excstarttime2) && currenttime.before(excendtime2)) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Check if the student is a re exam
	 */
	private boolean doCheckIfReExam() throws Exception {

		Integer seRemaintime = studentexamconduct.getSeRemaintime();
		Integer seId = studentexamconduct.getSeId();
		List<TblStudentpaperSession> retrievestudentpapersession = QuestionsUtil
				.retrieveWherClause(new TblStudentpaperSession(),
						"TblStudentpaperSession", "Se_Id=" + seId);
		if (retrievestudentpapersession.size() >= 1) {
			return true;
		}
		return false;
	}

	/**
	 * Method to call in beginning when the student appears for exam that was
	 * earlier discontinued.
	 */
	public void reloadTheStudent() {

		System.out.println("RE - Loadthestudent method started");
		CommonParams2.showMessageOnScreen("Hi reached REloadTheStudent");
		try { // TODO DONE all remaining time related thing here.

			TblExaminationConduct tblExaminationConduct = studentexamconduct
					.getTblExaminationConduct();
			examinationconduct = tblExaminationConduct;
			// examinationconduct=QuestionsUtil.retrieveWherClause(tblExaminationConduct,
			// "TblExaminationConduct", " ")
			// TODO REMEMBER BELOW excduration is in minutes.
			Integer excDuration = tblExaminationConduct.getExcDuration();
			Integer seRemaintime = studentexamconduct.getSeRemaintime();

			// studentexamconduct.setSeRemaintime(excDuration - 1);

			count = doConvertSecondsToCountdown2(seRemaintime); // "00:00";//excDuration;
			countdownseconds = seRemaintime;

			TimeZone indiantimezone = CommonParams2.getIndiancalendar()
					.getTimeZone();
			Date today = Calendar.getInstance(indiantimezone).getTime();
			studentexamconduct.setSeStarttime(today);

			Calendar cal = Calendar.getInstance(indiantimezone);
			cal.setTime(today);
			cal.add(Calendar.SECOND, seRemaintime);
			Date endtime = cal.getTime();
			studentexamconduct.setSeEndtime(endtime);

			QuestionsUtil.updateToQuestions(studentexamconduct);
			// saveToNewQuestions(studentexamconduct);

			/*
			 * allquestions , mainqpatterndetailslist , exammqname
			 * ,savedstudentpapersession ,savedanswersList
			 * listofmainqpatterndetails
			 * 
			 * , , questionOptionsList , savedshowanswersList
			 */
			allquestions = new ArrayList<TblQuestionsMaster>();
			// ArrayList<TblStudentpaperSession> studentpapersession=new
			// ArrayList<TblStudentpaperSession>();
			mainqpatterndetailslist = new ArrayList<TblMainqpatternDetails>();
			exammqname = new HashMap<Integer, String>();

			TblPaperpatternMaster tblPaperpatternMaster = studentexamconduct
					.getTblPaperpatternMaster();
			Integer ppid = tblPaperpatternMaster.getPpId();
			Integer seId = studentexamconduct.getSeId();
			List<TblStudentpaperSession> retrievestudentpapersession = QuestionsUtil
					.retrieveWherClause(new TblStudentpaperSession(),
							"TblStudentpaperSession", "SE_Id=" + seId);

			for (int i = 0; i < retrievestudentpapersession.size(); i++) {
				TblStudentpaperSession studentpapersession = retrievestudentpapersession
						.get(i);
				Integer mpId = studentpapersession.getTblMainqpatternDetails()
						.getMpId();

				List<TblMainqpatternDetails> retrievemainqpatterndetails = QuestionsUtil
						.retrieveWherClause(new TblMainqpatternDetails(),
								"TblMainqpatternDetails", "MP_Id=" + mpId);

				TblMainqpatternDetails mainqpatterndetails = retrievemainqpatterndetails
						.get(0);

				Integer qId = mainqpatterndetails.getTblQuestionsMaster()
						.getQId();
				List<TblQuestionsMaster> retrievequestionmaster = QuestionsUtil
						.retrieveWherClause(new TblQuestionsMaster(),
								"TblQuestionsMaster", "Q_ID=" + qId);

				TblQuestionsMaster tblQuestionsMaster = retrievequestionmaster
						.get(0);

				Integer mqId = mainqpatterndetails.getTblPapermainqDetails()
						.getMqId();
				List<TblPapermainqDetails> retrievepapermainqDetails = QuestionsUtil
						.retrieveWherClause(new TblPapermainqDetails(),
								"TblPapermainqDetails", "MQ_ID=" + mqId);
				TblPapermainqDetails tblPapermainqDetails = retrievepapermainqDetails
						.get(0);

				// TODO DONE LAODING BELOW THREE ARRAYLIST AND HASHMAP
				exammqname.put(i, tblPapermainqDetails.getMqName());

				allquestions.add(tblQuestionsMaster);
				mainqpatterndetailslist.add(mainqpatterndetails);
				savedstudentpapersession.put(i, studentpapersession);

				// TODO DONE LOADED SAVED ANSWERS
				ArrayList<TblQuestionOptions> tempsavedanswer2 = new ArrayList<TblQuestionOptions>();
				TblQuestionOptions tempsavedanswer3 = null;
				if (studentpapersession.isStudpapAttempt()) {
					ArrayList<Integer> qoid = retrieveCaAns(studentpapersession
							.getCaAns());
					for (int j = 0; j < qoid.size(); j++) {
						Integer a = qoid.get(j);
						List<TblQuestionOptions> retrievequestionoptions = QuestionsUtil
								.retrieveWherClause(new TblQuestionOptions(),
										"TblQuestionOptions", "QO_ID=" + a);
						TblQuestionOptions tblQuestionOptions = retrievequestionoptions
								.get(0);
						if (tblQuestionsMaster.isQMultiSelect()) {
							tempsavedanswer2.add(tblQuestionOptions);
						} else {
							tempsavedanswer3 = tblQuestionOptions;
						}
					}
					if (tblQuestionsMaster.isQMultiSelect()) {
						TblQuestionOptions[] a = tempsavedanswer2
								.toArray(new TblQuestionOptions[0]);
						savedanswersList.put(i, a);
					} else {
						TblQuestionOptions[] a = { tempsavedanswer3 };
						savedanswersList.put(i, a);
					}

					// TODO DONE LOADING QUESTION OPTIOSN FOR only those
					// selected earlier
					Integer qId2 = tblQuestionsMaster.getQId();
					List<TblQuestionOptions> options = QuestionsUtil
							.retrieveWherClause(new TblQuestionOptions(),
									"TblQuestionOptions", " Q_Id=" + qId2);
					savedshowanswersList.put(i, options);

				} else {
					if (tblQuestionsMaster.isQMultiSelect()) {
						TblQuestionOptions[] a = null;

						savedanswersList.put(i, a);
					} else {
						TblQuestionOptions[] a = new TblQuestionOptions[1];
						a[0] = null;
						savedanswersList.put(i, a);
					}
				}

			}
			RELOGIN = "(R)";
			ENDINDEX = allquestions.size();
			// TODO CONFIRM BELOW COMMENT
			doLoadTree2();
			doCreateTreeClientid();
			// doLoadAccordian();
			doLoadnextquestion();
			// TODO have added below method call
			runThreadScheduler2();
			increment();
			stopThreadScheduler2(countdownseconds);

		} catch (Exception ex) {
			System.out.println("reloadTheStudent" + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void loadTheStudent() {
		System.out.println("Loadthestudent method started");
		CommonParams2.showMessageOnScreen("Hi reached loadTheStudent");
		try {

			Integer localIndex = 0;

			// studentexamconduct.setSeStatus("active");
			Date today = Calendar.getInstance().getTime();

			studentexamconduct.setSeStarttime(today);
			TblExaminationConduct tblExaminationConduct = studentexamconduct
					.getTblExaminationConduct();
			examinationconduct = tblExaminationConduct;
			// examinationconduct=QuestionsUtil.retrieveWherClause(tblExaminationConduct,
			// "TblExaminationConduct", " ")
			Integer excDuration = tblExaminationConduct.getExcDuration();
			studentexamconduct.setSeRemaintime(excDuration - 1);

			System.err.println("MCITER PROBLEM excDuration=" + excDuration
					+ " " + tblExaminationConduct.getExcDuration()
					+ " stud obj remaintime="
					+ studentexamconduct.getSeRemaintime()
					+ " studobj starttime="
					+ studentexamconduct.getSeStarttime());

			count = "00:00";// excDuration;

			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.add(Calendar.MINUTE, excDuration);
			Date endtime = cal.getTime();
			studentexamconduct.setSeEndtime(endtime);

			QuestionsUtil.updateToQuestions(studentexamconduct);
			// saveToNewQuestions(studentexamconduct);
			// allquestions = new ArrayList<TblQuestionsMaster>();
			// mainqpatterndetailslist = new
			// ArrayList<TblMainqpatternDetails>();
			// String username="MCITER/AJ121";
			// String password="abcd";
			// List<TblStudentexamConduct> list2=
			// QuestionsUtil.retrieveWherClause(new TblStudentexamConduct(),
			// "TblStudentexamConduct",
			// " Studentid='"+username+"'  AND sePassword='"+password+"' " );
			// System.out.println("Mith DB Loading Student exam ");
			// TblStudentexamConduct studentexamconduct=null;
			// for (TblStudentexamConduct obj : list2) {
			// System.out.println(obj.getSePassword());
			// System.out.println("Anp code"+obj.getTblExaminationConduct().getTblCenterDetails().getAnpcode());
			// studentexamconduct=obj;
			// }

			try {
				// TblExaminationConduct tblExaminationConduct =
				// studentexamconduct.getTblExaminationConduct();

				// Integer excDuration = tblExaminationConduct.getExcDuration();
				System.out.println("Student is working "
						+ studentexamconduct.getSeStarttime());
				// QuestionsUtil.saveToNewQuestions(studentexamconduct);
				allquestions = new ArrayList<TblQuestionsMaster>();
				// ArrayList<TblStudentpaperSession> studentpapersession=new
				// ArrayList<TblStudentpaperSession>();
				mainqpatterndetailslist = new ArrayList<TblMainqpatternDetails>();
				exammqname = new HashMap<Integer, String>();

				TblPaperpatternMaster tblPaperpatternMaster = studentexamconduct
						.getTblPaperpatternMaster();
				Integer ppid = tblPaperpatternMaster.getPpId();
				// System.out.println("tblPaperpatternmaster here "+tblPaperpatternMaster.getTblPapermainqDetailses().size());

				// List<TblPapermainqDetails>
				// settblPapermainqDetailses=QuestionsUtil.retrieveALLwithHB(new
				// TblPapermainqDetails(), "TblPapermainqDetails","");
				List<TblPapermainqDetails> settblPapermainqDetailses = QuestionsUtil
						.retrieveWherClause(new TblPapermainqDetails(),
								"TblPapermainqDetails", "PP_Id=" + ppid);
				// = tblPaperpatternMaster.getTblPapermainqDetailses();

				// for (TblPapermainqDetails tblPapermainqDetails :
				// settblPapermainqDetailses) {
				for (int i2 = 0; i2 < settblPapermainqDetailses.size(); i2++) {
					TblPapermainqDetails tblPapermainqDetails = settblPapermainqDetailses
							.get(i2);
					// if(tblPapermainqDetails.getTblPaperpatternMaster().getPpId().intValue()!=ppid.intValue())
					// {

					// continue;
					// }
					System.out.println("TBlPapermainqdetails ="
							+ tblPapermainqDetails.getMqName());
					Integer mqid = tblPapermainqDetails.getMqId();
					Integer noofquestions2 = tblPapermainqDetails
							.getMqNosofquestion();

					System.out.println("MQID is " + mqid);
					List<TblMainqpatternDetails> listofmainqpatterndetails = QuestionsUtil
							.retrieveWherClause(new TblMainqpatternDetails(),
									"TblMainqpatternDetails", " MQ_ID=" + mqid);

					// Set<TblMainqpatternDetails> tblMainqpatternDetailses =
					// tblPapermainqDetails.getTblMainqpatternDetailses();
					// TODO MAKE A METHOD TO RETURN AN ARRAYLIST CONTAING INDEX
					// OF below ARRAY IN A RANDOM MANNER
					// TODO SAVE EACH QUESTION on CLICKIING NEXT.
					ArrayList<Integer> indexarray2 = doGetRandomArrayIndex(
							noofquestions2, listofmainqpatterndetails.size());

					System.out.println("TblMainqpatterndetails list2 "
							+ listofmainqpatterndetails.size());
					// for (TblMainqpatternDetails objtblMainqpatternDetails :
					// listofmainqpatterndetails) {
					for (int j3 = 0; j3 < indexarray2.size(); j3++) {
						TblMainqpatternDetails objtblMainqpatternDetails = listofmainqpatterndetails
								.get(indexarray2.get(j3));

						System.out.println("Made inside core");
						Integer qid = objtblMainqpatternDetails
								.getTblQuestionsMaster().getQId();
						List<TblQuestionsMaster> tempquestionmasterlist = QuestionsUtil
								.retrieveWherClause(new TblQuestionsMaster(),
										"TblQuestionsMaster", " QId=" + qid);

						allquestions.add(tempquestionmasterlist.get(0));
						mainqpatterndetailslist.add(objtblMainqpatternDetails);

						// objtblMainqpatternDetails.getTblQuestionsMaster());

						System.out.println("Made inside  "
								+ objtblMainqpatternDetails
										.getTblQuestionsMaster().getQId());
						TblStudentpaperSession newobj = new TblStudentpaperSession(
								objtblMainqpatternDetails, studentexamconduct,
								"", false);
						newobj.setStudPapId(QuestionsUtil.doGetNextPK(
								"TblStudentpaperSession", "studPapId"));
						// new TblStudentpaperSessionHome().persist(newobj);
						QuestionsUtil.saveToNewQuestions(newobj);
						savedstudentpapersession.put(localIndex, newobj);
						exammqname.put(localIndex,
								tblPapermainqDetails.getMqName());

						++localIndex;

					}
				}
				System.out.println("Question list" + allquestions.size());

				for (int i = 0; i < allquestions.size(); i++) {
					TblQuestionsMaster m = allquestions.get(i);
					TblMainqpatternDetails m2 = mainqpatterndetailslist.get(i);
					// System.out.println("Question is "+m.getTblQuestionOptionses().size());
					System.out.println("Question is " + m.getQDesc());
					System.out.println("Mainqpatterndetails  is "
							+ m2.getMpMarks());

				}
			} catch (Exception e) {
				System.out.println("error here" + e.getMessage());
				e.printStackTrace();
			}
			ENDINDEX = allquestions.size();
			savedanswersList = new HashMap<Integer, TblQuestionOptions[]>(
					ENDINDEX);
			// doNext()
			// TODO DEMO APP CONFIRM
			doLoadTree2();
			doCreateTreeClientid();
			// idofbutton="btn"+"0";

			// doLoadAccordian();
			doLoadnextquestion();
			// TODO have added below method call
			runThreadScheduler2();
			increment();
			stopThreadScheduler2(countdownseconds);

			// TblStudentpaperSession newobj=new
			// TblStudentpaperSession(tblMainqpatternDetails,
			// tblStudentexamConduct, caAns, studpapAttempt)
			// List<PaperMainQDetails> list_papermainqdetails =
			// QuestionsUtil.retrieveWherClause(new PaperMainQDetails(),
			// "PaperMainQDetails", " PP_ID="+ppid);

			// studentexamconduct.setSeEndtime(seEndtime)

		} catch (Exception e) {
			System.out.println("Loadthestudent method error " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void doLoadTree2() {
		// TODO Auto-generated method stub
		rootTreeNode = new DefaultTreeNode("Root", null);
		String tempquestiontype = exammqname.get(0);
		TreeNode anotherroot = new DefaultTreeNode(tempquestiontype,
				rootTreeNode);
		showthisnode = rootTreeNode;
		anotherroot.setExpanded(true);
		for (int i = 0; i < ENDINDEX; i++) {
			String questiontype = exammqname.get(i);
			if (!questiontype.equals(tempquestiontype)) {
				tempquestiontype = questiontype;
				anotherroot = new DefaultTreeNode(questiontype, rootTreeNode);
				anotherroot.setExpanded(true);

			}

			TreeNode t1 = new DefaultTreeNode("qno", (i + 1) + "", anotherroot);
			t1.setExpanded(true);
		}
	}

	private void doLoadAccordian() {
		System.out.println("doLoadaccordian started");
		accordianlist = new ArrayList<List<HashMap<String, Integer>>>();
		String tempquestiontype = exammqname.get(0);
		List a = new ArrayList<HashMap<String, Integer>>();
		int index = 0, indexrow = 0;
		HashMap<String, Integer> b = new HashMap<String, Integer>();
		b.put("a", 0);
		b.put("b", 0);
		b.put("c", 0);
		b.put("d", 0);

		for (int i = 0; i < ENDINDEX; i++) {
			String questiontype = exammqname.get(i);
			if ((!questiontype.equals(tempquestiontype)) || i == 0) {
				tempquestiontype = questiontype;
				indexrow = 0;

				if (a.size() > 0 && i > 0) {
					System.out
							.println("Should nt be added when index is 0 index="
									+ i);
					accordianlist.add(a); // main component
				}
				a = new ArrayList<HashMap<String, Integer>>(); // summary
				b = new HashMap<String, Integer>(); // commond buttons
				b.put("a", 0);
				b.put("b", 0);
				b.put("c", 0);
				b.put("d", 0);
				index = 0;

			}
			if (index == 0) {
				index = 1;
				b.put("a", i + 1);
			} else if (index == 1) {
				index = 2;
				b.put("b", i + 1);
			} else if (index == 2) {
				index = 3;
				b.put("c", i + 1);
			} else if (index == 3) {
				index = 0;
				b.put("d", i + 1);
				a.set(indexrow, b);
				b = new HashMap<String, Integer>();
				b.put("a", 0);
				b.put("b", 0);
				b.put("c", 0);
				b.put("d", 0);

				indexrow++;
			}
			// size=0 1
			// indexrow=0 1

			if (a.size() == indexrow || a.get(indexrow) == null) {
				a.add(indexrow, b);
				System.out.println("adding in list here index=" + index);
			} else {
				System.out.println("setting in list here index=" + index);
				a.set(indexrow, b);
			}
			// a.add(i+1);
		}
		if (a.size() > 0) {
			System.out.println("Should  be added when index is last");
			accordianlist.add(a); // main component
		}

		System.out.println("Print content of Accordian list");
		for (int i = 0; i < accordianlist.size(); i++) {
			List<HashMap<String, Integer>> list = accordianlist.get(i);
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Integer> hashMap = list.get(j);
				System.out.println("Map contains at i=" + i + "j=" + j);
				System.out.println("Hash map has " + hashMap.toString());
			}
		}
		System.out.println("End Printing content of Accordian list");
	}

	private void doCreateTreeClientid() {
		String tempquestiontype = exammqname.get(0);
		clientidhashmap = new HashMap<String, String>();
		String a = "idform2:idtree:1_9:btn2";
		int one = 0;
		int two = 0;
		for (int i = 0; i < ENDINDEX; i++) {
			String questiontype = exammqname.get(i);
			String idindex = "idform2:idtree:";

			if (!questiontype.equals(tempquestiontype)) {
				tempquestiontype = questiontype;
				one++;
				two = 0;

			}
			idindex += one + "_" + two + ":btn2";
			clientidhashmap.put((i + 1) + "", idindex);
			two++;

		}
	}

	public HashMap<Integer, String> getExammqname() {
		return exammqname;
	}

	public void setExammqname(HashMap<Integer, String> exammqname) {
		this.exammqname = exammqname;
	}

	public void doNavigate3(String indxstr) {
		System.out.println("doNavigate3 index is =" + indxstr);
		Integer indx5 = (Integer.parseInt(indxstr)) - 1;
		doSaveCurrentAnswers(INDEX);
		INDEX = indx5;
		doLoadnextquestion();
		idofbutton = "btn_" + indxstr;

	}

	public void onNodeSelect(NodeSelectEvent event) {
		String clientId = event.getComponent().getClientId();
		clientidofnode = clientId;
		TreeNode treeNode = event.getTreeNode();
		showthisnode = treeNode;
		int child = treeNode.getChildCount();
		if (child == 0)

		{

			System.out
					.println("The object is " + treeNode.getData().getClass());
		}
		System.out
				.println("OnNode Selected Event is fired here childer it has ="
						+ treeNode.getChildCount());

	}

	public void onNodeExpand(NodeExpandEvent event) {

	}

	private ArrayList<Integer> doGetRandomArrayIndex(Integer noofquestions2,
			Integer totalquestions) {
		// TODO Auto-generated method stub
		ArrayList<Integer> ret = new ArrayList<Integer>();
		// for(int i=0;i<noofquestions2;i++)
		int i = 0;
		while (i < noofquestions2) {
			Random rand = new Random();
			Integer val = rand.nextInt(totalquestions);
			if (ret.isEmpty() == false && ret.contains(val)) {
				continue;
			}
			ret.add(val);
			i++;
		}
		return ret;
	}

	private void doLoadnextquestion() {
		System.out.println("Mith loading options");
		// if (currentquestion != null) {
		//
		//
		// }
		currentquestion = allquestions.get(INDEX);
		clientidofnode = clientidhashmap.get((INDEX + 1) + "");

		// showthisnode.setSelected(true);
		// showthisnode.setExpanded(true);
		System.out.println("The Node is expanded here");
		if (doLoadFromCache(INDEX)) {
			return;
		}
		TblQuestionsMaster temp2 = allquestions.get(INDEX);
		Integer qid = temp2.getQId();
		List<TblQuestionOptions> options = QuestionsUtil.retrieveWherClause(
				new TblQuestionOptions(), "TblQuestionOptions", " Q_Id=" + qid);
		System.out.println("Temp Options has so many items:" + options.size());
		if (questionOptionsList == null) {

			questionOptionsList = new ArrayList<TblQuestionOptions>();
		} else {
			questionOptionsList.clear();
		}
		// =new ArrayList<TblQuestionOptions>();
		questionOptionsList = options;
		// .addAll(options);
		System.out.println("Real Options has so many items:"
				+ questionOptionsList.size());

		// questio
		System.out.println("Mith done Loaded options");

	}

	private boolean doLoadFromCache(Integer indx3) {
		System.out.println(indx3 + " = mith doLOadFromCache "
				+ savedshowanswersList.containsKey(indx3));

		if (savedshowanswersList.containsKey(indx3)) {
			System.out.println("mith doLOadFromCache key is there "
					+ savedanswersList.get(indx3).length);
			// TODO BELOW CODE NOT REFLECTING IN GUI
			questionOptionsList = savedshowanswersList.get(indx3);
			// demo
			Set<Integer> keySet = savedshowanswersList.keySet();
			Integer[] integer = keySet.toArray(new Integer[0]);
			System.out.println("Hi mith keyset length is =" + keySet.size()
					+ " int array size is=" + integer.length);
			try {
				for (Integer d : integer) {
					System.out.println("Hi mith The keys are:" + d);
					System.out.println("Hi mith The values are:"
							+ savedshowanswersList.get(d).size());

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i8 = 0; i8 < integer.length; i8++) {

				List<TblQuestionOptions> list = savedshowanswersList
						.get(integer[i8]);

				if (list != null) {
					for (TblQuestionOptions abc : list) {
						if (abc != null)
							System.out.println(integer[i8]
									+ "Hi mith content of doLoadFromCache "
									+ abc.getQoName());
						else
							System.out
									.println(integer[i8]
											+ "Hi mith content of doLoadFromCache its NULL");
					}
				} else {
					System.out.println("Hi mith at index " + integer[i8]
							+ " list is null");
				}

			}
			// demo end
			if (currentquestion.isQMultiSelect()) {
				questionOptionsSelected = savedanswersList.get(indx3);
			} else {
				TblQuestionOptions[] b = savedanswersList.get(indx3);
				questionOptionsSingleSelected = b[0];
			}
			return true;
		}
		System.out.println("mith doLOadFromCache key is NOT there");
		return false;
	}

	private void doSaveCurrentAnswers(Integer indx2) {

		TblQuestionOptions[] a = null;
		boolean attempted = false;
		// clientidofnode= clientidhashmap.get((indx2+1)+"");
		// System.out.println("ATUL INDEX IS ="+indx2+" client id is ="+clientidofnode);
		//
		// System.out.println("ATUL HASHMAP is "+clientidhashmap.toString());
		// System.out.println("ATUL END HASHMAP  ");

		// TODO demo
		System.out.println("hi mith dosavecurrentanswers size is "
				+ questionOptionsList.size());
		for (int i = 0; i < questionOptionsList.size(); i++) {
			TblQuestionOptions abc = questionOptionsList.get(i);
			System.out.println(i + "hi mith dosavecurrentanswers content is "
					+ abc.getQoName());
		}
		// TODO END DEMO

		List<TblQuestionOptions> templist = new ArrayList<TblQuestionOptions>();

		templist.addAll(questionOptionsList);

		// templist=questionOptionsList.subList(0, questionOptionsList.size());

		savedshowanswersList.put(indx2, templist);
		if (currentquestion.isQMultiSelect()) {

			a = questionOptionsSelected;
			if (a != null)
				System.out.println("Multi choice selected is:" + a.length);
			else
				System.out.println("Multi choicce is null");
		} else {
			a = new TblQuestionOptions[1];
			a[0] = questionOptionsSingleSelected;
			if (a != null && a[0] != null)
				System.out.println("Single choice selected is:"
						+ a[0].getQoName());
			else
				System.out.println("Single choicce is null");
		}
		// TODO INSERT OR UPDATE TBLSTUDENTPAPERSESSION

		if (a != null && a.length > 0 && a[0] != null) {

			attempted = true;
		} else {
			attempted = false;
		}
		TblStudentpaperSession obj2 = savedstudentpapersession.get(indx2);
		// new TblStudentpaperSession();
		obj2.setStudpapAttempt(attempted);
		// obj2.setTblMainqpatternDetails(mainqpatterndetailslist.get(indx2));
		// obj2.setTblStudentexamConduct(studentexamconduct);
		String append = "";

		for (int i4 = 0; i4 < a.length; i4++) {
			if (a[i4] != null) {
				append += (a[i4].getQoId() + ",");
			}
		}
		obj2.setCaAns(append);
		QuestionsUtil.updateToQuestions(obj2);

		// if (savedstudentpapersession.containsKey(indx2)) {
		// TblStudentpaperSession tempobj = savedstudentpapersession
		// .get(indx2);
		// obj2.setStudPapId(tempobj.getStudPapId());
		// QuestionsUtil.updateToQuestions(obj2);
		// } else {
		// obj2.setStudPapId(QuestionsUtil.doGetNextPK(
		// "TblStudentpaperSession", "studPapId"));
		//
		// QuestionsUtil.saveToNewQuestions(obj2);
		//
		// }

		savedstudentpapersession.put(indx2, obj2);
		savedanswersList.put(indx2, a);

	}

	public String doGetTreeAttempted(String node2) {
		Integer index = Integer.parseInt(node2) - 1;
		String ret = "";
		String notattempted = "color:blue";
		String attempted = "color:red";
		if (savedanswersList.containsKey(index)) {
			TblQuestionOptions[] a = savedanswersList.get(index);
			if (a != null && a.length > 0 && a[0] != null) {

				ret = attempted;
			} else {
				ret = notattempted;
			}
		} else {
			ret = notattempted;
		}
		return ret;
	}

	public void doPrintallanswers() {
		System.out.println("Mith doPrintallanswers is here "
				+ savedanswersList.size());

		for (int i = 0; i < ENDINDEX; i++) {
			TblQuestionOptions[] a = savedanswersList.get(i);
			if (a != null && a.length > 0) {
				for (int j = 0; j < a.length; j++) {
					System.out.println(i + " answer is :" + a[j].getQoName());

				}
			} else {
				System.out.println(i + " is notanswered");
			}
		}
		System.out.println("Mith doPrintallanswers is over ");
	}

	public List<TblQuestionsMaster> getAllquestions() {
		return allquestions;
	}

	public void setAllquestions(List<TblQuestionsMaster> allquestions) {
		this.allquestions = allquestions;
	}

	public TblStudentexamConduct getStudentexamconduct() {
		return studentexamconduct;
	}

	public void setStudentexamconduct(TblStudentexamConduct studentexamconduct) {
		this.studentexamconduct = studentexamconduct;
	}

	public Boolean getStop() {
		return stop;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void increment() {
		// count++;
		// if (count >= stopincrement) {
		// stop = true;
		// }
		// TODO UPDATE TBLSTUDENTEXAMCONDUCT

		Integer it = studentexamconduct.getSeRemaintime();
		String countremaining = "00:00";
		Date starttime = studentexamconduct.getSeStarttime();
		Date endtime = studentexamconduct.getSeEndtime();
		Date currenttime = new Date();
		Calendar endcal = Calendar.getInstance();
		Calendar currentcal = Calendar.getInstance();
		endcal.setTime(endtime);
		currentcal.setTime(currenttime);
		System.err.println("MCITER PROBLEM from DB starttime="
				+ studentexamconduct.getSeStarttime() + " endtime="
				+ studentexamconduct.getSeEndtime());
		System.err.println("MCITER PROBLEM from variables currcal="
				+ currentcal.getTime() + "endcal=" + endcal.getTime());
		Integer newremtime = it - 1; // (interval / 60);

		if (endcal.after(currentcal)) {
			//MITH COMMENTED BELOW WAS A BUG for calculating the remiantime when time is 12 am morning
			
			/*Integer endtimeinseconds = endcal.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			endtimeinseconds += (endcal.get(Calendar.MINUTE) * 60);
			endtimeinseconds += (endcal.get(Calendar.SECOND));

			Integer currenttimeinseconds = currentcal.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			currenttimeinseconds += (currentcal.get(Calendar.MINUTE) * 60);
			currenttimeinseconds += (currentcal.get(Calendar.SECOND));

			newremtime = endtimeinseconds - currenttimeinseconds;*/
			long  endtimeinseconds=(endcal.getTimeInMillis()/1000);
			long currenttimeinseconds=(currentcal.getTimeInMillis()/1000);
			newremtime=(int)(endtimeinseconds-currenttimeinseconds);
			countdownseconds = newremtime;
			System.err.println("MCITER PROBLEM currtime" + currenttimeinseconds
					+ " endtime=" + endtimeinseconds);

			// countremaining=(endcal.get(Calendar.HOUR_OF_DAY)-currentcal.get(Calendar.HOUR_OF_DAY))+"";
			countremaining = doConvertSecondsToCountdown2(newremtime);

			// countremaining=newminutes+":"+newseconds;

		} else {
			newremtime = 0;
			countremaining = "00:00";
		}

		String abcd = "New rem time is =" + newremtime;

		// if(newremtime<=0)
		// {
		// newremtime=0;
		// }

		studentexamconduct.setSeRemaintime(newremtime);
		QuestionsUtil.updateToQuestions(studentexamconduct);

		abcd += ";studentexamconduct.getSeRemaintime()="
				+ studentexamconduct.getSeRemaintime();

		count = countremaining;

		// newremtime;
		if (newremtime <= 0) {
			stop = true;
			// stopThreadScheduler2(1);
			endofexam();
		}
		abcd += ";count=" + countremaining;
		CommonParams2.showMessageOnLog(this.getClass(), abcd);

	}

	/**
	 * @param newremtime
	 * @return
	 */
	private String doConvertSecondsToCountdown2(Integer newremtime) {
		String countremaining;
		int newminutes = newremtime / 60;
		int newseconds = newremtime % 60;
		if (newminutes < 10) {
			countremaining = "0" + newminutes;
		} else {
			countremaining = newminutes + "";
		}
		countremaining += ":";
		if (newseconds < 10) {
			countremaining += "0" + newseconds;
		} else {
			countremaining += newseconds + "";
		}
		return countremaining;
	}

	public void runThreadScheduler2() {
		scheduler = Executors.newScheduledThreadPool(2);
		// newSingleThreadScheduledExecutor();

		Runnable a = new Runnable() {
			public void run() {
				increment();
			}
		};

		scheduleAtFixedRate = scheduler.scheduleAtFixedRate(a, 0, 30,
				TimeUnit.SECONDS);

		// scheduleAtFixedRate.
	}

	public void stopThreadScheduler2(long delay2) {
		Runnable b = new Runnable() {
			public void run() {
				System.out.println("reached run stopThreadScheduler2");
				endofexam();
				try {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("testexampaperend.jsf?redirect=true");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("stopThreadScheduler2 run is executed");
			}
		};
		// ScheduledFuture<?> schedule2 =
		scheduler.schedule(b, delay2, TimeUnit.SECONDS);
		// try {
		// schedule2.get();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@PreDestroy
	public void stop2() {
		if (scheduler != null) {
			stop = true;
			boolean thirtysecondclosed = scheduleAtFixedRate.cancel(true);
			scheduler.shutdownNow();
			System.out.println("Final Shutdown! 30 sec closed"
					+ thirtysecondclosed);
		}

	}

	public void endofexam() {
		try {
			stop = true;
			studentexamconduct.setSeStatus("dormant");

			QuestionsUtil.updateToQuestions(studentexamconduct);
			doCalculateMarks();

			doSaveResult();

			if (scheduler != null) {
				if (!scheduleAtFixedRate.isDone()) {
					scheduleAtFixedRate.cancel(true);
				}
				// scheduler.shutdownNow();
				System.out.println("Scheduler shutdown");
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("testexampaperend.jsf?redirect=true");
			FacesContext.getCurrentInstance().responseComplete();

			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("endofexam error " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void doSaveResult() {

		System.out.println("Start save result to db");
		if (tblstudentresultdetails2 != null) {
			System.out.println("recalled");
			return;
		}
		Integer seId = studentexamconduct.getSeId();
		Boolean existinginresult = false;
		Integer studrestId = 0;

		List<TblStudentresultDetails> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblStudentresultDetails(),
						"TblStudentresultDetails", "SE_Id=" + seId);
		if (retrieveWherClause != null && retrieveWherClause.size() > 0) {
			existinginresult = true;
			TblStudentresultDetails temp = retrieveWherClause.get(0);
			studrestId = temp.getStudrestId();
		} else {
			existinginresult = false;
		}

		Integer qcId = studentexamconduct.getTblQuestioncategoryMaster()
				.getQcId();

		List<TblQuestioncategoryMaster> retrievecatmaster = QuestionsUtil
				.retrieveWherClause(new TblQuestioncategoryMaster(),
						"TblQuestioncategoryMaster", "QC_ID=" + qcId);
		TblQuestioncategoryMaster tblQuestioncategoryMasterlocal = retrievecatmaster
				.get(0);

		coursename = tblQuestioncategoryMasterlocal.getQcName();

		Integer catId = tblQuestioncategoryMasterlocal.getTblCategoryMaster()
				.getCatId();
		List<TblCategoryMaster> retrievecategory = QuestionsUtil
				.retrieveWherClause(new TblCategoryMaster(),
						"TblCategoryMaster", "Cat_ID=" + catId);
		TblCategoryMaster tblCategoryMaster = retrievecategory.get(0);
		courseduration = tblCategoryMaster.getCatDuration();

		tblstudentresultdetails2 = new TblStudentresultDetails();
		Integer pk = QuestionsUtil.doGetNextPK("TblStudentresultDetails",
				"studrest_tag", false);
		if (existinginresult == true) {
			tblstudentresultdetails2.setStudrestId(studrestId);
			tblstudentresultdetails2.setStudrestTag(studrestId);
		} else {
			tblstudentresultdetails2.setStudrestId(pk);
			tblstudentresultdetails2.setStudrestTag(pk);
		}
		tblstudentresultdetails2.setStudrestPlace(studentdetails
				.getCitytownvillage());
		tblstudentresultdetails2.setStudrestCentercode(studentdetails
				.getTblCenterDetails().getAnpcode());
		tblstudentresultdetails2.setStudentId(studentdetails.getStudentid());
		tblstudentresultdetails2.setStudrestMonthandyear(this
				.getCurrentmonthyear());
		tblstudentresultdetails2.setStudrestCourseduration(courseduration);
		tblstudentresultdetails2.setStudrestStudentfullname(studentdetails
				.getFirstname() + " " + studentdetails.getLastname());
		tblstudentresultdetails2.setStudrestCoursename(coursename);
		tblstudentresultdetails2.setStudrestMaxmarks(this.getTotalmarks());
		tblstudentresultdetails2.setStudrestPercentage(this
				.getTotalpercentageattained());
		tblstudentresultdetails2.setStudrestGrade(this.getTotalgradeattained());
		tblstudentresultdetails2.setStudrestMarksobtain(this
				.getTotalmarksattained());
		tblstudentresultdetails2.setStudrestNameofanp(centerdetails
				.getNameofinstitute()
				+ ","
				+ centerdetails.getDistofinstitute());
		tblstudentresultdetails2
				.setTblQuestioncategoryMaster(this.studentexamconduct
						.getTblQuestioncategoryMaster());
		tblstudentresultdetails2
				.setTblStudentexamConduct(this.studentexamconduct);
		if (existinginresult) {
			QuestionsUtil.updateToQuestions(tblstudentresultdetails2);
			System.out.println("Exising result but  updated!");
		} else {
			QuestionsUtil.saveToNewQuestions(tblstudentresultdetails2);
			System.out.println("Save Student Result to db");
		}

	}

	private void doCalculateMarks() {
		// TODO
		Integer answeredmarks = 0;
		Integer totalmarks = 0;
		Integer correctnumberofanswers = 0;
		HashMap<Integer, ArrayList<Integer>> correctanswerssinglemap = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> correctanswersmultiplemap = new HashMap<Integer, ArrayList<Integer>>();

		HashMap<Integer, Boolean> isitcorrectansmap = new HashMap<Integer, Boolean>();
		HashMap<Integer, Integer> eachanswermarks = new HashMap<Integer, Integer>();

		// CALCULATE THE TOTAL MARKS FOR THE ENTIRE EXAM.
		Integer ppId = studentexamconduct.getTblPaperpatternMaster().getPpId();
		List<TblPapermainqDetails> arr4 = QuestionsUtil.retrieveWherClause(
				new TblPapermainqDetails(), "TblPapermainqDetails", "PP_ID="
						+ ppId);
		Integer index2 = 0;
		for (int k = 0; k < arr4.size(); k++) {
			TblPapermainqDetails def5 = arr4.get(k);
			Integer a = def5.getMqNosofquestion();
			Integer b = def5.getMqTotalQmarks();
			Integer total = a * b;
			totalmarks += total;
			// for(int l=0;l<a;l++)
			// {
			// eachanswermarks.put(index2, b);
			// index2++;
			// }
		}

		for (int j2 = 0; j2 < ENDINDEX; j2++) {
			TblMainqpatternDetails def5 = mainqpatterndetailslist.get(j2);
			Integer mqId = def5.getTblPapermainqDetails().getMqId();

			List<TblPapermainqDetails> defarr5 = QuestionsUtil
					.retrieveWherClause(new TblPapermainqDetails(),
							"TblPapermainqDetails", "MQ_ID=" + mqId);
			TblPapermainqDetails abc5 = defarr5.get(0);
			eachanswermarks.put(j2, abc5.getMqTotalQmarks());

			Integer qId = def5.getTblQuestionsMaster().getQId();
			List<TblQuestionOptions> abcarr6 = QuestionsUtil
					.retrieveWherClause(new TblQuestionOptions(),
							"TblQuestionOptions", "Q_ID=" + qId);
			List<TblQuestionsMaster> defarr7 = QuestionsUtil
					.retrieveWherClause(new TblQuestionsMaster(),
							"TblQuestionsMaster", "Q_ID=" + qId);
			TblQuestionsMaster abcquestionmaster = defarr7.get(0);
			boolean ismulti = abcquestionmaster.isQMultiSelect();

			ArrayList<Integer> lst = new ArrayList<Integer>();

			for (TblQuestionOptions abc3 : abcarr6) {

				if (abc3.isQSelected()) {
					lst.add(abc3.getQoId());
				}

			}
			if (!ismulti) {
				correctanswerssinglemap.put(j2, lst);
			} else {
				correctanswersmultiplemap.put(j2, lst);
			}

		}

		for (int i = 0; i < ENDINDEX; i++) {
			if (savedstudentpapersession.containsKey(i)) {

				TblStudentpaperSession savedsession2 = savedstudentpapersession
						.get(i);
				if (savedsession2.isStudpapAttempt()) {
					ArrayList<Integer> qoid = retrieveCaAns(savedsession2
							.getCaAns());
					// Integer subansweredmarks=0;

					if (qoid.size() == 0) {
						System.out
								.println("Error in endexam doCalculateMarks null CaAns");
					}

					if (correctanswerssinglemap.containsKey(i)) {
						for (int j9 = 0; j9 < qoid.size(); j9++) {
							if (correctanswerssinglemap.get(i).contains(
									qoid.get(j9))) {
								isitcorrectansmap.put(i, true);
							} else {
								isitcorrectansmap.put(i, false);
							}
						}
					} else if (correctanswersmultiplemap.containsKey(i)) {
						ArrayList<Boolean> iscorrecttemp = new ArrayList<Boolean>();
						ArrayList<Integer> temp2 = correctanswersmultiplemap
								.get(i);
						// WHEN NO of options selected by user is greater than
						// correct selections
						if (qoid.size() >= temp2.size()) {
							for (int i8 = 0; i8 < qoid.size(); i8++) {
								int a = qoid.get(i8);
								iscorrecttemp.add(false);
								for (int i88 = 0; i88 < temp2.size(); i88++) {
									int b = temp2.get(i88);
									if (a == b) {
										iscorrecttemp.set(i8, true);
									}
								}
							}
						}// WHEN NO OF options selected by user is lesser than
							// corrrect selections
						else {
							for (int i8 = 0; i8 < temp2.size(); i8++) {
								int a = temp2.get(i8);
								iscorrecttemp.add(false);
								for (int i88 = 0; i88 < qoid.size(); i88++) {
									int b = qoid.get(i88);
									if (a == b) {
										iscorrecttemp.set(i8, true);
									}
								}
							}
						}
						if (iscorrecttemp.contains(false)) {
							isitcorrectansmap.put(i, false);
						} else {
							isitcorrectansmap.put(i, true);
						}

						// TODO remove below
						System.out.println("Mith here correcttemp exam index="
								+ i);

						for (int kkk = 0; kkk < iscorrecttemp.size(); kkk++) {
							System.out.println("Mith here correcttemp=" + kkk
									+ " = " + iscorrecttemp.get(kkk));
						}
						// END debug
					} else {
						System.out
								.println("ALErt mithun here no key found single or multiple maps");
						isitcorrectansmap.put(i, false);
					}
					/*
					 * for(int i2=0;i2<qoid.length;i2++) {
					 * List<TblQuestionOptions> arr =
					 * QuestionsUtil.retrieveWherClause(new
					 * TblQuestionOptions(), "TblQuestionOptions",
					 * "QO_ID="+qoid[i2]); TblQuestionOptions abc2=arr.get(0);
					 * Integer qId = abc2.getTblQuestionsMaster().getQId();
					 * List<TblQuestionsMaster> arr6 =
					 * QuestionsUtil.retrieveWherClause(new
					 * TblQuestionsMaster(), "TblQuestionsMaster", "Q_ID="+qId);
					 * TblQuestionsMaster tempmaster2 = arr6.get(0); boolean
					 * multiselect=tempmaster2.isQMultiSelect();
					 * 
					 * 
					 * 
					 * TblMainqpatternDetails temp2 =
					 * mainqpatterndetailslist.get(i); Integer mqId =
					 * temp2.getTblPapermainqDetails().getMqId();
					 * List<TblPapermainqDetails> pmdarr2 =
					 * QuestionsUtil.retrieveWherClause(new
					 * TblPapermainqDetails(), "TblPapermainqDetails",
					 * "MQ_ID="+mqId); TblPapermainqDetails temp3 =
					 * pmdarr2.get(0); if(abc2.isQSelected()) {
					 * 
					 * 
					 * subansweredmarks+=temp3.getMqTotalQmarks(); } else {
					 * if(multiselect==true) { subansweredmarks=0; } }
					 * 
					 * }
					 */

				} else {
					isitcorrectansmap.put(i, false);
				}
			}
		}

		for (int k = 0; k < isitcorrectansmap.size(); k++) {
			if (isitcorrectansmap.get(k).booleanValue() == true) {
				++correctnumberofanswers;
				Integer a = eachanswermarks.get(k);
				answeredmarks += a;
			}
		}
		setTotalmarks(totalmarks);
		setTotalmarksattained(answeredmarks);

		setNoofcorrectanswers(correctnumberofanswers);

	}

	private ArrayList retrieveCaAns(String a) {
		ArrayList arr = new ArrayList();

		StringTokenizer b = new StringTokenizer(a, ",");

		while (b.hasMoreTokens()) {
			String c = b.nextToken();
			if (c != null && c.length() > 0) {
				Integer d = Integer.parseInt(c);
				arr.add(d);
			}
		}

		// ret = (Integer[])arr.toArray(new Integer[0]);
		return arr;

		// return ret;
	}

	public Integer getTotalpercentageattained() {
		if (getTotalmarks() != null && getTotalmarksattained() != null) {
			Integer a = totalmarksattained * 100;
			totalpercentageattained = a / totalmarks;

			// totalpercentageattained=Integer.parseInt(a+"");
		} else {
			totalpercentageattained = -1;
		}
		return totalpercentageattained;
	}

	public void setTotalpercentageattained(Integer totalpercentageattained) {
		this.totalpercentageattained = totalpercentageattained;
	}

	public String getTotalgradeattained() {

		Integer percentage = getTotalpercentageattained();

		if (percentage >= 90) {
			totalgradeattained = "A++";
		} else if (percentage >= 80) {
			totalgradeattained = "A+";
		} else if (percentage >= 65) {
			totalgradeattained = "A";
		} else if (percentage >= 50) {
			totalgradeattained = "B";
		} else if (percentage >= 40) {
			totalgradeattained = "C";
		} else if (percentage < 40 && percentage >= 0) {
			totalgradeattained = "Fail";
		} else {
			totalgradeattained = "AB";
		}

		return totalgradeattained;
	}

	public void setTotalgradeattained(String totalgradeattained) {
		this.totalgradeattained = totalgradeattained;
	}

	public void doPrevious() {
		if (INDEX.intValue() <= 0) {
			CommonParams2
					.showMessageOnScreen("Sorry No Previous questions to show!");
			System.out.println("Sorry No Prevoius questions to show");
			if (INDEX.intValue() == 0) {
				doSaveCurrentAnswers(INDEX);

			}
			return;
		}

		INDEX--;
		doSaveCurrentAnswers(INDEX + 1);

		doLoadnextquestion();
	}

	public void doNext() {
		System.out.println("Reached Next button" + " INDEX=" + INDEX
				+ " ENDINDEX=" + ENDINDEX);
		if (INDEX.intValue() == ENDINDEX.intValue() - 1) {
			CommonParams2
					.showMessageOnScreen("Sorry No Next questions to show!");
			System.out.println("Sorry No Next questions to show");

			doSaveCurrentAnswers(INDEX);
			return;
		}

		INDEX++;
		doSaveCurrentAnswers(INDEX - 1);
		doLoadnextquestion();
	}

	public void doSubmitAnswer() {
		System.out.println("Pressed Submitted Answer!");
		doSaveCurrentAnswers(INDEX);
		CommonParams2.showMessageOnScreen("Answer Submitted!" + (INDEX + 1));
	}

	public Integer getINDEX() {
		return INDEX;
	}

	public void setINDEX(Integer iNDEX) {
		INDEX = iNDEX;
	}

	public Integer getENDINDEX() {
		return ENDINDEX;
	}

	public void setENDINDEX(Integer eNDINDEX) {
		ENDINDEX = eNDINDEX;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public TblExaminationConduct getExaminationconduct() {
		return examinationconduct;
	}

	public void setExaminationconduct(TblExaminationConduct examinationconduct) {
		this.examinationconduct = examinationconduct;
	}

	public TreeNode getRootTreeNode() {
		return rootTreeNode;
	}

	public void setRootTreeNode(TreeNode rootTreeNode) {
		this.rootTreeNode = rootTreeNode;
	}

	public String getCurrentquestiontypename() {
		if (exammqname.containsKey(INDEX)) {
			currentquestiontypename = exammqname.get(INDEX);
		} else {
			currentquestiontypename = "Error";
		}
		return currentquestiontypename;
	}

	public Integer getNoofcorrectanswers() {
		return noofcorrectanswers;
	}

	public void setNoofcorrectanswers(Integer noofcorrectanswers) {
		this.noofcorrectanswers = noofcorrectanswers;
	}

	public Integer getTotalmarks() {
		return totalmarks;
	}

	public void setTotalmarks(Integer totalmarks) {
		this.totalmarks = totalmarks;
	}

	public Integer getTotalmarksattained() {
		return totalmarksattained;
	}

	public void setTotalmarksattained(Integer totalmarksattained) {
		this.totalmarksattained = totalmarksattained;
	}

	public String getRELOGIN() {
		return RELOGIN;
	}

	public void setRELOGIN(String rELOGIN) {
		RELOGIN = rELOGIN;
	}

	public String getCurrentmonthyear() {
		Calendar indiancalendar = CommonParams2.getIndiancalendar();
		currentmonthyear = "";
		currentmonthyear += " "
				+ indiancalendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
						Locale.ENGLISH);
		currentmonthyear += " " + indiancalendar.get(Calendar.YEAR);
		return currentmonthyear;
	}

	public void setCurrentmonthyear(String currentmonthyear) {
		this.currentmonthyear = currentmonthyear;
	}

	public TblCenterDetails getCenterdetails() {
		return centerdetails;
	}

	public void setCenterdetails(TblCenterDetails centerdetails) {
		this.centerdetails = centerdetails;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCourseduration() {
		return courseduration;
	}

	public void setCourseduration(String courseduration) {
		this.courseduration = courseduration;
	}

	public String getIdofbutton() {
		return idofbutton;
	}

	public void setIdofbutton(String idofbutton) {
		this.idofbutton = idofbutton;
	}

	public String getClientidofnode() {
		return clientidofnode;
	}

	public void setClientidofnode(String clientidofnode) {
		this.clientidofnode = clientidofnode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
