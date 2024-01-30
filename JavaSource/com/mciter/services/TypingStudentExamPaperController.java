package com.mciter.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.primefaces.context.RequestContext;

import com.mciter.commonbeans.MarksMaster;
import com.mciter.commonbeans.TblCategoryMaster;
import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblDemousers;
import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblFontMaster;
import com.mciter.commonbeans.TblPapermainqDetails;
import com.mciter.commonbeans.TblPaperpatternMaster;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.commonbeans.TblStudentpaperSession;
import com.mciter.commonbeans.TblStudentresultDetails;
import com.mciter.commonbeans.TblTypingtestDetails;
import com.mciter.commonbeans.TblTypingtestMaster;
import com.mciter.commonbeans.TblTypingtestSession;
import com.mciter.utils.CommonParams2;
import name.fraser.neil.plaintext.*;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

public class TypingStudentExamPaperController {

	private TblStudentexamConduct studentexamconduct;
	private TblStudentDetails studentdetails;
	private TblCenterDetails centerdetails;
	private boolean stop;
	private String typedettype = "";
	private TblTypingtestMaster tblTypingtestMaster;
	private TblTypingtestDetails tblTypingtestDetails;
	private String username = "";
	private String password = "";
	private HashMap<Integer, TblTypingtestSession> savedstudentpapersession;
	private HashMap<Integer, String> exammqname;
	private Integer speedtestendtime;
	private String speedtestenteredtext;
	private TblPapermainqDetails tblPapermainqDetails;
	private Boolean speedtestover = false;

	// COOMENT BELOW 2 LINES
	private String resultfromhashmap = "";
	private String resultinhtml = "";
	private String fonturl;

	// int Extraspace = 0, Extraword = 0, Deletedword = 0,
	// ComaOrFullStopDeletedOrInserted = 0;
	// int SpellingMistakes = 0, DeletedComaFullStop = 0, DeletedExtraSpace = 0;
	Integer mistakes = 0;
	Integer endingseconds = 0, endingminutes = 0;
	private Boolean booldoStartExam = false, booldoStopExam = false;
	private Double negativemarks;
	private Integer maximummistakes;

	private String totalgradeattained;
	private Integer totalpercentageattained = 0;
	private Integer totalmarks;
	private Integer totalmarksattained;
	private Integer noofcorrectanswers;
	private Boolean completedontime = false;
	private TblStudentresultDetails tblstudentresultdetails2;
	private String coursename;
	private String courseduration;

	public TypingStudentExamPaperController() {
		studentexamconduct = new TblStudentexamConduct();
		TblStudentDetails tblStudentDetails = new TblStudentDetails();
		studentexamconduct.setTblStudentDetails(tblStudentDetails);

		typedettype = "SPEED";
		savedstudentpapersession = new HashMap<Integer, TblTypingtestSession>();
		exammqname = new HashMap<Integer, String>();
	}

	private Boolean doLoadDemoLogin() {
		Boolean returnvalue = false;

		try {
			System.out
					.println("TypingStudentExamPaperController.doLoadDemoLogin()");
			List<TblDemousers> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblDemousers(), "TblDemousers",
							"studentid='" + username + "'");
			if (retrieveWherClause.size() > 0) {
				returnvalue = true;
				TblDemousers tblDemousers = retrieveWherClause.get(0);
				Integer seId = studentexamconduct.getSeId();
				int deleteFromDB = QuestionsUtil.deleteFromDB(
						"TblTypingtestSession", "Se_Id=" + seId);
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

	public String doTypingStudentLogin() {
		String pagereturn = "";

		String username = this.username;// studentexamconduct.getTblStudentDetails()
		// .getStudentid();
		String password = this.password;// studentexamconduct.getSePassword();

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

					if (doIsTestingStudent() == false) {
						CommonParams2
								.showMessageOnScreen("Sorry this exam is only for TYPING EXAM!");
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
						// if (studentexamconduct.getSeRemaintime().intValue()
						// == 0) {
						// reloadTheStudent();
						// // endofexam();
						// CommonParams2
						// .showMessageOnLog(this.getClass(),
						// "Hi   end of exam since relogin! EndINdex=");
						// // + ENDINDEX);
						stop = true;
						return "testtypingexampaperend";
						// }
						// reloadTheStudent();
					} else {
						loadTheStudent();
					}
					// return "testexampaper";
					return "typingexamsuccesslogin";

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

		}

		return pagereturn;

	}

	private boolean doIsTestingStudent() {
		Boolean returnvalue = false;
		TblPaperpatternMaster tblPaperpatternMaster = studentexamconduct
				.getTblPaperpatternMaster();
		Integer ppid = tblPaperpatternMaster.getPpId();
		List<TblPaperpatternMaster> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblPaperpatternMaster(),
						"TblPaperpatternMaster", "PP_ID=" + ppid);
		if (retrieveWherClause.size() > 0) {
			TblPaperpatternMaster tblPaperpatternMaster2 = retrieveWherClause
					.get(0);
			if (tblPaperpatternMaster2.getPpPaperType().equalsIgnoreCase(
					"TYPING")) {
				returnvalue = true;
			} else {
				returnvalue = false;
			}
		} else {
			returnvalue = false;
		}
		return returnvalue;

	}

	private void loadTheStudent() {

		try {
			TblPaperpatternMaster tblPaperpatternMaster = studentexamconduct
					.getTblPaperpatternMaster();
			Integer ppid = tblPaperpatternMaster.getPpId();
			List<TblPapermainqDetails> settblPapermainqDetailses = QuestionsUtil
					.retrieveWherClause(new TblPapermainqDetails(),
							"TblPapermainqDetails", "PP_Id=" + ppid);

			for (int i2 = 0; i2 < settblPapermainqDetailses.size(); i2++) {

				tblPapermainqDetails = settblPapermainqDetailses.get(i2);

				Integer mqid = tblPapermainqDetails.getMqId();
				exammqname.put(i2, tblPapermainqDetails.getMqName());

				Integer noofquestions2 = 1;
				System.out.println("MQID is " + mqid);
				List<TblTypingtestDetails> retrievetbltypingtestdetails = QuestionsUtil
						.retrieveWherClause(new TblTypingtestDetails(),
								"TblTypingtestDetails", "MQ_ID=" + mqid
										+ " AND typedet_type='" + typedettype
										+ "' ");

				ArrayList<Integer> indexarray2 = doGetRandomArrayIndex(
						noofquestions2, retrievetbltypingtestdetails.size());

				tblTypingtestDetails = retrievetbltypingtestdetails
						.get(indexarray2.get(0));

				speedtestendtime = tblTypingtestDetails.getTypedetTime();
				negativemarks = tblTypingtestDetails
						.getTypedetNegativeoneachmistake();
				maximummistakes = tblTypingtestDetails.getTypedetMistakes();

				dototaltime(speedtestendtime);

				Integer typetId = tblTypingtestDetails.getTypetId();
				List<TblTypingtestMaster> retrieveTblTypingtestMaster = QuestionsUtil
						.retrieveWherClause(new TblTypingtestMaster(),
								"TblTypingtestMaster", "typet_ID=" + typetId);
				tblTypingtestMaster = retrieveTblTypingtestMaster.get(0);

				int fontId = tblTypingtestMaster.getFontId();
				List<TblFontMaster> retrieveWherClause = QuestionsUtil
						.retrieveWherClause(new TblFontMaster(),
								"TblFontMaster", "font_id=" + fontId);
				TblFontMaster tblFontMaster = retrieveWherClause.get(0);
				fonturl = tblFontMaster.getFontPath();

				/*
				 * TODO UNCOMMENT THIS IN PRODUCTION STAGE TblTypingtestSession
				 * newobj=new TblTypingtestSession();
				 * newobj.setTypesesId(QuestionsUtil
				 * .doGetNextPK("TblTypingtestSession","typeses_ID",false));
				 * newobj.setTypesesTag(newobj.getTypesesId().toString());
				 * newobj.setTblStudentexamConduct(studentexamconduct);
				 * newobj.setTblTypingtestDetails(tblTypingtestDetails); // new
				 * TblStudentpaperSessionHome().persist(newobj);
				 * QuestionsUtil.saveToNewQuestions(newobj);
				 * 
				 * savedstudentpapersession.put(i2, newobj);
				 */

			}

			speedtestover = false;

		} catch (Exception e) {
			System.out.println("loadTheStudent error:" + e.getMessage());

			e.printStackTrace();
		}

	}

	private void reloadTheStudent() {
		// DOES NOTHING

	}

	public void doStartExam() {
		System.out.println("TypingStudentExamPaperController.doStartExam()");
		if (booldoStartExam == true) {
			System.out.println("repeated start button click ");
			return;
		}

		System.out.println("TypingStudentExamPaperController.doStartExam()");
		Date today = Calendar.getInstance().getTime();

		studentexamconduct.setSeStarttime(today);
		TblExaminationConduct tblExaminationConduct = studentexamconduct
				.getTblExaminationConduct();
		// examinationconduct = tblExaminationConduct;

		Integer excDuration = tblTypingtestDetails.getTypedetTime();
		// tblExaminationConduct.getExcDuration();
		studentexamconduct.setSeRemaintime(excDuration - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.SECOND, excDuration);
		Date endtime = cal.getTime();
		studentexamconduct.setSeEndtime(endtime);

		QuestionsUtil.updateToQuestions(studentexamconduct);
		booldoStartExam = true;
	}

	public void doStopExam() {
		System.out.println("TypingStudentExamPaperController.doStopExam()");
		if (booldoStopExam == true) {
			System.out.println("repeated press of stop exam");
			return;
		}

		Date seStarttime = studentexamconduct.getSeStarttime();
		int remaintime = endingminutes * 60;
		remaintime += endingseconds;
		studentexamconduct.setSeRemaintime(remaintime);
		TblTypingtestSession localTblTypingtestSession = new TblTypingtestSession();
		localTblTypingtestSession.setTblStudentexamConduct(studentexamconduct);
		localTblTypingtestSession.setTblTypingtestDetails(tblTypingtestDetails);
		String typesesDesc = CommonParams2.encodeToAscii(speedtestenteredtext);
		localTblTypingtestSession.setTypesesDesc(typesesDesc);
		localTblTypingtestSession.setTypesesMistakesdone(mistakes);

		completedontime = false;

		if (endingminutes <= 0 && endingseconds <= 1) {
			completedontime = false;
		} else {
			completedontime = true;
		}
		localTblTypingtestSession.setTypesesCompletedOnTime(completedontime);

		Integer doGetNextPK = QuestionsUtil.doGetNextPK("TblTypingtestSession",
				"typeses_ID", false);
		localTblTypingtestSession.setTypesesId(doGetNextPK);
		localTblTypingtestSession.setTypesesTag(doGetNextPK.toString());

		QuestionsUtil.saveToNewQuestions(localTblTypingtestSession);
		QuestionsUtil.updateToQuestions(studentexamconduct);
		
		doEndExam();
		booldoStopExam = true;
		speedtestover = true;
	}

	public String doShowResultPage()
	{
		System.out
				.println("TypingStudentExamPaperController.doShowResultPage()");
		Integer seId = studentexamconduct.getSeId();
		System.out.println("Finally send result page for seid="+seId);
//		return "testexampaperresult.jsf?se_id="+seId;
		return seId+"";
	}
	public void doSaveResult() {
		System.out.println("TypingStudentExamPaperController.doSaveResult()");
		try {
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

			Integer catId = tblQuestioncategoryMasterlocal
					.getTblCategoryMaster().getCatId();
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
			tblstudentresultdetails2
					.setStudentId(studentdetails.getStudentid());
			tblstudentresultdetails2.setStudrestMonthandyear(this
					.getCurrentmonthyear());
			tblstudentresultdetails2.setStudrestCourseduration(courseduration);
			tblstudentresultdetails2.setStudrestStudentfullname(studentdetails
					.getFirstname() + " " + studentdetails.getLastname());
			tblstudentresultdetails2.setStudrestCoursename(coursename);
			tblstudentresultdetails2.setStudrestMaxmarks(this.getTotalmarks());
			tblstudentresultdetails2.setStudrestPercentage(this
					.getTotalpercentageattained());
			tblstudentresultdetails2.setStudrestGrade(this
					.getTotalgradeattained());
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

		} catch (Exception e) {
			System.err.println("exception mith " + e.getMessage());
		}

	}

	private String getCurrentmonthyear() {
		Calendar indiancalendar = CommonParams2.getIndiancalendar();
		String currentmonthyear = "";
		currentmonthyear += " "
				+ indiancalendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
						Locale.ENGLISH);
		currentmonthyear += " " + indiancalendar.get(Calendar.YEAR);
		return currentmonthyear;
	}

	public void doEndExam() {
		System.out.println("End of exam reached!");

		doCalculateMarks();
		speedtestover = true;
		doSaveResult();
	}

	private void doCalculateMarks() {
		// marks= totalmarks - (mistakes* negative marks)
		// Perc= marks/totalmarks*100

		// String source = tblTypingtestMaster.getTypetDesc(); REM It is in
		// ASCII format so convert it.
		String destination = speedtestenteredtext;
		// Integer mistakes=this.mistakes;
		Integer minutes = this.endingminutes;
		Integer second = this.endingseconds;
		System.out
				.println("TypingStudentExamPaperController.doCalculateMarks()");

		totalmarks = tblPapermainqDetails.getMqTotalQmarks();
		Double negative = tblTypingtestDetails
				.getTypedetNegativeoneachmistake();

		long round = Math.round((mistakes * negative));
		Integer round2 = (int) round;
		Integer markstemp = totalmarks - round2;
		
		setTotalmarksattained(markstemp);
		totalpercentageattained = getTotalpercentageattained();
		totalgradeattained = getTotalgradeattained();
		System.out.println("typing cal marksattained="+markstemp+" totalpercentage="+totalpercentageattained);
		System.out.println("negative="+negative);
	}

	/*
	 * public HashMap<String, Integer> doComaprison(String a, String b) {
	 * diff_match_patch dmp=new diff_match_patch(); LinkedList<Diff> diff_main =
	 * dmp.diff_main(a, b, false); mistakes = 0;
	 * dmp.diff_cleanupSemantic(diff_main); int index = 0; String delete2 = "";
	 * int current2 = 0; HashMap<String, Integer> mapmarks = new HashMap<String,
	 * Integer>(); String[] keys = { "Extraspace", "Extraword", "Deletedword",
	 * "ComaOrFullStopDeletedOrInserted", "BackspaceWordMistakes",
	 * "SpellingMistakes", "DeletedComaFullStop", "DeletedExtraSpace" };
	 * Extraspace = 0; Extraword = 0; Deletedword = 0;
	 * ComaOrFullStopDeletedOrInserted = 0; SpellingMistakes = 0;
	 * DeletedComaFullStop = 0; DeletedExtraSpace = 0;
	 * 
	 * String regexforspecial = ""; // for (Diff diff : diff_main) for (int ki =
	 * 0; ki < diff_main.size(); ki++) { Diff diff = diff_main.get(ki);
	 * 
	 * if (diff.operation.compareTo(Operation.EQUAL) == 0) {
	 * System.out.println(index + " equals are=" + diff.text); index++; delete2
	 * = ""; // mistakes++;
	 * 
	 * }
	 * 
	 * if (diff.operation.compareTo(Operation.DELETE) == 0) {
	 * System.out.println(index + " deletes are=" + diff.text); Diff tempdiff =
	 * null; if (diff_main.size() != (ki + 1)) { tempdiff = diff_main.get(ki +
	 * 1); } delete2 = diff.text; current2 = index; String diff2 = diff.text; if
	 * (tempdiff != null && (tempdiff.operation.compareTo(Operation.INSERT) ==
	 * 0)) {
	 * 
	 * } else { String[] space2 = diff.text.split(" "); if (space2 != null &&
	 * space2.length >= 1) { for (int k = 0; k < space2.length; k++) { int comas
	 * = checkMatch(space2[k]);
	 * 
	 * if (comas > 0) { DeletedComaFullStop = DeletedComaFullStop + comas;
	 * Deletedword += checkWithSplitMatch(space2[k]); } else if
	 * (space2[k].contains(" ")) { DeletedExtraSpace++; } else { //
	 * if(space2[k]) Deletedword++;
	 * 
	 * } mistakes++;
	 * 
	 * } } else { int comas = checkMatch(diff2);
	 * 
	 * if (comas > 0) { DeletedComaFullStop = DeletedComaFullStop + comas;
	 * Deletedword += checkWithSplitMatch(diff2); } else if
	 * (diff2.contains(" ")) { DeletedExtraSpace++; } else { // if(space2[k])
	 * Deletedword++;
	 * 
	 * }
	 * 
	 * mistakes++;
	 * 
	 * } }
	 * 
	 * } if (diff.operation.compareTo(Operation.INSERT) == 0) {
	 * System.out.println(index + " inserts are=" + diff.text); if
	 * (delete2.trim().equals("")) { // mistakes ++; String[] split =
	 * diff.text.trim().split(" "); if (split.length >= 1) { doLooping2(split);
	 * 
	 * // for (int g = 0; g < split.length; g++) { //
	 * System.out.println("if no delete Split content=" // + split[g]); //
	 * String t3 = split[g].trim(); // if (!t3.equals("")) { // int comas =
	 * checkMatch(split[g]); // // if (comas > 0) { //
	 * ComaOrFullStopDeletedOrInserted = // ComaOrFullStopDeletedOrInserted // +
	 * comas; // Extraword += checkWithSplitMatch(split[g]); // } else if
	 * (split[g].contains(" ")) { // Extraspace++; // // } else { // //
	 * if(space2[k]) // Extraword++; // } // mistakes++; // // // // // } // } }
	 * } if (current2 == index) { // compare of inquality - not equal words -
	 * insetter wrod or // etra words LinkedList<Diff> diff_main2 =
	 * dmp.diff_main(delete2, diff.text); dmp.diff_cleanupSemantic(diff_main2);
	 * for (Diff diff2 : diff_main2) { if
	 * (diff2.operation.compareTo(Operation.INSERT) == 0) { mistakes++;
	 * 
	 * System.out.println("Inside Insert " + diff2.text); if
	 * (diff2.text.trim().equals(diff.text.trim())) { String[] split =
	 * delete2.trim().split(" "); if (split[0].contains(" ")) {
	 * DeletedExtraSpace++; }
	 * 
	 * else if (checkMatch(split[0]) > 0) { DeletedComaFullStop++; } else {
	 * Deletedword++; } if (split.length > 1) { for (int g = 1; g <
	 * split.length; g++) { System.out.println("Split content=" + split[g]);
	 * String t3 = split[g].trim(); if (!t3.equals("")) { mistakes++; //
	 * Extraword++; if (split[g].contains(" ")) { DeletedExtraSpace++; } else if
	 * (checkMatch(split[g]) > 0) { DeletedComaFullStop++; } else {
	 * Deletedword++; } } } } }
	 * 
	 * } } String[] split2 = diff.text.split(" "); if (split2.length > 0) {
	 * doLooping2(split2); } }
	 * 
	 * }
	 * 
	 * } mapmarks.put("Extraspace", Extraspace); mapmarks.put("Extraword",
	 * Extraword); mapmarks.put("Deletedword", Deletedword);
	 * mapmarks.put("ComaOrFullStopDeletedOrInserted",
	 * ComaOrFullStopDeletedOrInserted); mapmarks.put("SpellingMistakes",
	 * SpellingMistakes); mapmarks.put("DeletedComaFullStop",
	 * DeletedComaFullStop); mapmarks.put("DeletedExtraSpace",
	 * DeletedExtraSpace); mapmarks.put("BackspaceWordMistakes", 0);
	 * mapmarks.put("Mistakes", mistakes);
	 * 
	 * resultfromhashmap=mapmarks.toString();
	 * resultinhtml=dmp.diff_prettyHtml(diff_main);
	 * 
	 * // String diff_prettyHtml = dmp.diff_prettyHtml(diff_main); //
	 * System.out.println("---html----"); //
	 * System.out.println(diff_prettyHtml);
	 * 
	 * System.out.println("---html end----");
	 * 
	 * return mapmarks; // return mistakes;
	 * 
	 * }
	 */

	/*
	 * public void doLooping2(String[] split) { for (int g = 0; g <
	 * split.length; g++) { System.out.println("if no delete Split content=" +
	 * split[g]); String t3 = split[g].trim(); if (!t3.equals("")) { int comas =
	 * checkMatch(split[g]);
	 * 
	 * if (comas > 0) { ComaOrFullStopDeletedOrInserted =
	 * ComaOrFullStopDeletedOrInserted + comas; Extraword +=
	 * checkWithSplitMatch(split[g]); } else if (split[g].contains(" ")) {
	 * Extraspace++;
	 * 
	 * } else { // if(space2[k]) Extraword++; } mistakes++;
	 * 
	 * } } }
	 */

	/*
	 * public int checkMatch(String text) { String[] m = { ",", ".", ";", ":",
	 * "'", "`", "\"" }; int ret = 0; for (int a = 0; a < m.length; a++) { if
	 * (text.contains(m[a])) { ret++; } } return ret;
	 * 
	 * }
	 * 
	 * public int checkWithSplitMatch(String text) { String[] m = { ",", ".",
	 * ";", ":", "'", "`", "\"" }; int ret = 0; for (int i = 0; i < m.length;
	 * i++) { String[] b2 = text.split(m[i]); if (b2.length >= 2) { ret =
	 * b2.length; break; } } return ret; }
	 */
	// public int doComaprison(String a, String b) {
	// diff_match_patch dmp=new diff_match_patch();
	// LinkedList<Diff> diff_main = dmp.diff_main(a, b, false);
	// Integer mistakes = 0;
	// dmp.diff_cleanupSemantic(diff_main);
	// int index = 0;
	// String delete2 = "";
	// int current2 = 0;
	// for (Diff diff : diff_main) {
	//
	// if (diff.operation.compareTo(Operation.EQUAL) == 0) {
	// System.out.println(index + " equals are=" + diff.text);
	// index++;
	// delete2 = "";
	// // mistakes++;
	// }
	//
	// if (diff.operation.compareTo(Operation.DELETE) == 0) {
	// System.out.println(index + " deletes are=" + diff.text);
	// mistakes++;
	// delete2 = diff.text;
	// current2 = index;
	//
	// }
	// if (diff.operation.compareTo(Operation.INSERT) == 0) {
	// System.out.println(index + " inserts are=" + diff.text);
	// if (delete2.trim().equals("")) {
	// // mistakes ++;
	// String[] split = diff.text.trim().split(" ");
	// if (split.length >= 1) {
	// for (int g = 0; g < split.length; g++) {
	// System.out.println("if no delete Split content=" + split[g]);
	// String t3 = split[g].trim();
	// if (!t3.equals("")) {
	// mistakes++;
	// }
	// }
	// }
	// }
	// if (current2 == index) {
	// // compare of inquality - not equal words - insetter wrod or
	// // etra words
	// LinkedList<Diff> diff_main2 = dmp.diff_main(delete2,
	// diff.text);
	// dmp.diff_cleanupSemantic(diff_main2);
	// for (Diff diff2 : diff_main2) {
	// if (diff2.operation.compareTo(Operation.INSERT) == 0) {
	// mistakes++;
	// System.out.println("Inside Insert " + diff2.text);
	// if (diff2.text.trim().equals(diff.text.trim())) {
	// String[] split = delete2.trim().split(" ");
	// if (split.length > 1) {
	// for (int g = 1; g < split.length; g++) {
	// System.out.println("Split content="
	// + split[g]);
	// String t3 = split[g].trim();
	// if (!t3.equals("")) {
	// mistakes++;
	// }
	// }
	// }
	// }
	//
	// }
	// }
	//
	// }
	//
	// }
	//
	// }
	// return mistakes;
	//
	// }

	public void dototaltime(Integer r) {
		System.out.println("dototaltime made it!");
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		currentInstance.execute("savetotaltime(" + r + ")");
		currentInstance.execute("alert('Made It to Server Side')");
	}

	private void doSaveCurrentAnswers(Integer indx2) {
		TblTypingtestSession tblTypingtestSession = savedstudentpapersession
				.get(indx2);
		// tblTypingtestSession.setTypesesCompletedOnTime()
		// tblTypingtestSession.setTypesesDesc(typesesDesc)
		// tblTypingtestSession.setTypesesMistakesdone(typesesMistakesdone)
		// QuestionsUtil.updateToQuestions(tblTypingtestSession);
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

	/*
	 * Check if the student is a re exam
	 */
	private boolean doCheckIfReExam() throws Exception {

		Integer seRemaintime = studentexamconduct.getSeRemaintime();
		Integer seId = studentexamconduct.getSeId();
		List<TblTypingtestSession> retrievestudentpapersession = QuestionsUtil
				.retrieveWherClause(new TblTypingtestSession(),
						"TblTypingtestSession", "Se_Id=" + seId);
		if (retrievestudentpapersession.size() >= 1) {
			return true;
		}
		return false;
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

	public TblStudentexamConduct getStudentexamconduct() {
		return studentexamconduct;
	}

	public void setStudentexamconduct(TblStudentexamConduct studentexamconduct) {
		this.studentexamconduct = studentexamconduct;
	}

	public String getTypedettype() {
		return typedettype;
	}

	public void setTypedettype(String typedettype) {
		this.typedettype = typedettype;
	}

	public TblTypingtestMaster getTblTypingtestMaster() {
		return tblTypingtestMaster;
	}

	public void setTblTypingtestMaster(TblTypingtestMaster tblTypingtestMaster) {
		this.tblTypingtestMaster = tblTypingtestMaster;
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

	public HashMap<Integer, String> getExammqname() {
		return exammqname;
	}

	public void setExammqname(HashMap<Integer, String> exammqname) {
		this.exammqname = exammqname;
	}

	public Integer getSpeedtestendtime() {
		return speedtestendtime;
	}

	public void setSpeedtestendtime(Integer speedtestendtime) {
		this.speedtestendtime = speedtestendtime;
	}

	public String getSpeedtestenteredtext() {
		return speedtestenteredtext;
	}

	public void setSpeedtestenteredtext(String speedtestenteredtext) {
		this.speedtestenteredtext = speedtestenteredtext;
	}

	public Boolean getSpeedtestover() {
		return speedtestover;
	}

	public void setSpeedtestover(Boolean speedtestover) {
		this.speedtestover = speedtestover;
	}

	public String getResultfromhashmap() {
		return resultfromhashmap;
	}

	public void setResultfromhashmap(String resultfromhashmap) {
		this.resultfromhashmap = resultfromhashmap;
	}

	public String getResultinhtml() {
		return resultinhtml;
	}

	public void setResultinhtml(String resultinhtml) {
		this.resultinhtml = resultinhtml;
	}

	public String getFonturl() {
		return fonturl;
	}

	public void setFonturl(String fonturl) {
		this.fonturl = fonturl;
	}

	public Integer getMistakes() {
		return mistakes;
	}

	public void setMistakes(Integer mistakes) {
		this.mistakes = mistakes;
	}

	public Integer getEndingseconds() {
		return endingseconds;
	}

	public void setEndingseconds(Integer endingseconds) {
		this.endingseconds = endingseconds;
	}

	public Integer getEndingminutes() {
		return endingminutes;
	}

	public void setEndingminutes(Integer endingminutes) {
		this.endingminutes = endingminutes;
	}

	public Double getNegativemarks() {
		return negativemarks;
	}

	public void setNegativemarks(Double negativemarks) {
		this.negativemarks = negativemarks;
	}

	public Integer getMaximummistakes() {
		return maximummistakes;
	}

	public void setMaximummistakes(Integer maximummistakes) {
		this.maximummistakes = maximummistakes;
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
		
		if(completedontime==false)
		{
			totalgradeattained="Fail";
		}

		return totalgradeattained;
	}

	public void setTotalgradeattained(String totalgradeattained) {
		this.totalgradeattained = totalgradeattained;
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

	public Integer getNoofcorrectanswers() {
		return noofcorrectanswers;
	}

	public void setNoofcorrectanswers(Integer noofcorrectanswers) {
		this.noofcorrectanswers = noofcorrectanswers;
	}

}
