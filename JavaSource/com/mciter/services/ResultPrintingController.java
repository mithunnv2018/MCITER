package com.mciter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblExaminationConduct;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblStudentexamConduct;
import com.mciter.commonbeans.TblStudentresultDetails;
import com.mciter.utils.CommonParams2;

public class ResultPrintingController {
	private TblExaminationConduct tblexaminationconduct;
	private ArrayList<SelectItem> centercodes;
	private ArrayList<SelectItem> examlist;
	private ArrayList<TblStudentexamConduct> listofappearedstudents;
	private TblStudentDetails tblstudentdetails;
	private TblStudentexamConduct tblstudentexamconduct;
	private TblStudentresultDetails tblstudentresultdetails;

	public ResultPrintingController() {
		boolean doLoadResult = doLoadResultFromRequest();

		if (doLoadResult == true) {
				
		} else {
			List<TblCenterDetails> retrieveALLwithHB = QuestionsUtil
					.retrieveALLwithHB(new TblCenterDetails(),
							"TblCenterDetails", "");
			centercodes = new ArrayList<SelectItem>();
			for (TblCenterDetails centerDetails : retrieveALLwithHB) {

				centercodes.add(new SelectItem(centerDetails.getAnpcode(),
						centerDetails.getNameofinstitute()));

			}
			tblexaminationconduct = new TblExaminationConduct();
			tblstudentexamconduct = new TblStudentexamConduct();
			setTblstudentexamconduct(tblstudentexamconduct);

			setTblexaminationconduct(tblexaminationconduct);
			getTblexaminationconduct().setTblCenterDetails(
					new TblCenterDetails());
			doLoadForCenter();
		}
	}
	
	private boolean doLoadForCenter()
	{
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext ctx = currentInstance.getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);

		if(session.getAttribute("forms_CenterController")!=null)
		{
			Forms_Center_StudentController center2 = (Forms_Center_StudentController) session
					.getAttribute("forms_CenterController");
			
			tblexaminationconduct.setTblCenterDetails(center2.getCenterdetails());
			doShowExamConduct();
			return true;
		}
		
		return false;
	}

	public void doShowExamConduct()
	{
		String anpcode = tblexaminationconduct.getTblCenterDetails().getAnpcode();
		examlist = new ArrayList<SelectItem>();
		List<TblExaminationConduct> retrieveexamconduct = QuestionsUtil
				.retrieveWherClause(new TblExaminationConduct(),
						"TblExaminationConduct", "Exc_Active='active' AND anpcode='"+anpcode+"' ");
		for (TblExaminationConduct local2 : retrieveexamconduct) {
			examlist.add(new SelectItem(local2.getExcId(), local2
					.getExcName()));
		}
	}
	private boolean doLoadResultFromRequest() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();
		Map<String, String> requestParammap = externalContext
				.getRequestParameterMap();
		String req1 = requestParammap.get("se_id");

		System.out.println("The Values are req1=" + req1);
		if (req1 != null) {
			Integer abc = Integer.parseInt(req1);
			showResultPrintingPage(abc);
		}

		if (req1 == null) {
			return false;
		} else {
			return true;
		}
	}

	public void showResultPrintingPage(Integer seId) {
		String ret = "";
		 
		// Integer seId = tblstudentexamconduct.getSeId();
		List<TblStudentresultDetails> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblStudentresultDetails(),
						"TblStudentresultDetails", "SE_Id=" + seId);
		if (retrieveWherClause.size() > 0) {
			tblstudentresultdetails = retrieveWherClause.get(0);
		} else {
			CommonParams2
					.showMessageOnScreen("No Result available for this student ");
			return;// null;
		}
//		String windowopen = "window.open('testexampaperresult.jsf','_blank')";
//		RequestContext currentInstance = RequestContext.getCurrentInstance();
//		currentInstance.execute(windowopen);
		return;// null;
	}

	public void showAppearedStudentList() {
		System.out.println("Reached shoapperedstudentlist");
		
		Integer excId = tblexaminationconduct.getExcId();
		String anpcode = tblexaminationconduct.getTblCenterDetails()
				.getAnpcode();
		List<TblExaminationConduct> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblExaminationConduct(),
						"TblExaminationConduct", "Exc_Id=" + excId
								+ "  AND anpcode='" + anpcode + "' ");
		if (retrieveWherClause.size() == 0) {
			CommonParams2
					.showMessageOnScreen("No Exam found for this center code!");
			return;
		}
		tblexaminationconduct = retrieveWherClause.get(0);
		listofappearedstudents = new ArrayList<TblStudentexamConduct>();

		List<TblStudentexamConduct> retrieveWherClause2 = QuestionsUtil
				.retrieveWherClause(new TblStudentexamConduct(),
						"TblStudentexamConduct", "Exc_Id=" + excId + "  ");
		for (int i = 0; i < retrieveWherClause2.size(); i++) {
			TblStudentexamConduct local3 = retrieveWherClause2.get(i);
			System.out.println("Checking in start studexamcdt value of seid="
					+ local3.getSeId());
			String studentid = local3.getTblStudentDetails().getStudentid();
			List<TblStudentDetails> retrieveWherClause3 = QuestionsUtil
					.retrieveWherClause(new TblStudentDetails(),
							"TblStudentDetails", "studentid='" + studentid
									+ "' ");
			TblStudentDetails tblStudentDetails = retrieveWherClause3.get(0);
			local3.setTblStudentDetails(tblStudentDetails);
			System.out.println("Checking last studexamcdt value of seid="
					+ local3.getSeId());
			listofappearedstudents.add(local3);
		}

	}

	public TblExaminationConduct getTblexaminationconduct() {
		return tblexaminationconduct;
	}

	public void setTblexaminationconduct(
			TblExaminationConduct tblexaminationconduct) {
		this.tblexaminationconduct = tblexaminationconduct;
	}

	public ArrayList<SelectItem> getCentercodes() {
		return centercodes;
	}

	public void setCentercodes(ArrayList<SelectItem> centercodes) {
		this.centercodes = centercodes;
	}

	public ArrayList<SelectItem> getExamlist() {
		return examlist;
	}

	public void setExamlist(ArrayList<SelectItem> examlist) {
		this.examlist = examlist;
	}

	public ArrayList<TblStudentexamConduct> getListofappearedstudents() {
		return listofappearedstudents;
	}

	public void setListofappearedstudents(
			ArrayList<TblStudentexamConduct> listofappearedstudents) {
		this.listofappearedstudents = listofappearedstudents;
	}

	public TblStudentDetails getTblstudentdetails() {
		return tblstudentdetails;
	}

	public void setTblstudentdetails(TblStudentDetails tblstudentdetails) {
		this.tblstudentdetails = tblstudentdetails;
	}

	public TblStudentexamConduct getTblstudentexamconduct() {
		return tblstudentexamconduct;
	}

	public void setTblstudentexamconduct(
			TblStudentexamConduct tblstudentexamconduct) {
		this.tblstudentexamconduct = tblstudentexamconduct;
	}

	public TblStudentresultDetails getTblstudentresultdetails() {
		return tblstudentresultdetails;
	}

	public void setTblstudentresultdetails(
			TblStudentresultDetails tblstudentresultdetails) {
		this.tblstudentresultdetails = tblstudentresultdetails;
	}
}
