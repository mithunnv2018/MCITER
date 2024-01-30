/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mciter.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.FileUploadEvent;

import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblCoursemap;
import com.mciter.commonbeans.TblMarksMaster;
import com.mciter.commonbeans.TblQuestionOptions;
import com.mciter.commonbeans.TblQuestioncategoryMaster;
import com.mciter.commonbeans.TblQuestionsMaster;
import com.mciter.commonbeans.TblStudentDetails;
import com.mciter.commonbeans.TblUserMaster;
import com.mciter.utils.CommonParams2;
import com.sun.org.apache.bcel.internal.generic.LSTORE;

/**
 * 
 * @author mithun
 */
public class ToImportExcel {

	private HashMap<String, Integer> mapofcourses;
	private ArrayList<TblStudentDetails> listofstudents;
	private ArrayList<TblQuestionsMaster> listofquestionmaster;
	private HashMap<Integer,ArrayList<TblQuestionOptions>> listofquestionoptions;
	private HashMap<String,Integer> answermap2;//['A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'I':9,'J':10]
	                
	                
	public ToImportExcel() {
		listofstudents = new ArrayList<TblStudentDetails>();
		loadmapofcourses();
		loadmapofanswer();
		listofquestionmaster=new ArrayList<TblQuestionsMaster>();
		listofquestionoptions=new HashMap<Integer,ArrayList<TblQuestionOptions>>();
		
	}
	
	private void loadmapofanswer()
	{
		answermap2=new HashMap<String,Integer>();
		answermap2.put("A", 1);
		answermap2.put("B",2);
		answermap2.put("C",3);
		answermap2.put("D",4);
		answermap2.put("E",5);
		answermap2.put("F",6);
		answermap2.put("G",7);
		answermap2.put("H",8);
		answermap2.put("I",9);
		answermap2.put("J",10);
		//['A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'I':9,'J':10]
	}

	private void loadmapofcourses() {
		
		mapofcourses = new HashMap<String, Integer>();
/*		mapofcourses.put("ACDP", 20);
		mapofcourses.put("CBAS", 13);
		mapofcourses.put("CCFA", 40);
		mapofcourses.put("CCAD", 12);
		mapofcourses.put("CPHM", 14);
		mapofcourses.put("CPCC", 9);
		mapofcourses.put("CCL", 3);
		mapofcourses.put("COCS", 8);
		mapofcourses.put("CCT", 2);
		mapofcourses.put("CPP", 4);
		mapofcourses.put("DDAP", 26);
		mapofcourses.put("ACJP",41);
		
		mapofcourses.put("MTS",52);*/
		
		List<TblCoursemap> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblCoursemap(), "TblCoursemap", "");
		for (TblCoursemap map2 : retrieveALLwithHB) {
			mapofcourses.put(map2.getMapCoursecode(),map2.getQcId());
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		System.out.println("ToImportExcel.handleFileUpload()");
		if(listofstudents!=null)
		{
			listofstudents.clear();
		}
		try {
			if(isValidEXCELforstudentdetails(event.getFile().getInputstream()))
			{
				System.out.println("Correct file format");
				doImportStudentDetails(event.getFile().getInputstream());
			}
			else
			{
				System.err.println("Sorry not valid file "+event.getFile().getFileName());
			}
			System.out.println("Hi mith Good Upload done without error");
		} catch (IOException e) {
			FacesMessage msg2 = new FacesMessage("Error",
					"Sorry Error in upload.");

			FacesContext.getCurrentInstance().addMessage(null, msg2);
			e.printStackTrace();
			System.err.println("Hi problem with file :" + e.getMessage());
		}
	}

	public void handleFileUploadQBank(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		System.out.println("ToImportExcel.handleFileUploadQBank()");
		 
		if(listofquestionmaster!=null)
		{
			listofquestionmaster.clear();
			listofquestionoptions.clear();
		}
		try {
			if(isValidEXCELforquestionbank(event.getFile().getInputstream()))
			{
				System.out.println("Correct file format");
				doImportQuestionBank(event.getFile().getInputstream());
			}
			else
			{
				System.err.println("Sorry not valid file "+event.getFile().getFileName());
			}
			System.out.println("Hi mith Good Upload done without error");
		} catch (IOException e) {
			FacesMessage msg2 = new FacesMessage("Error",
					"Sorry Error in upload.");

			FacesContext.getCurrentInstance().addMessage(null, msg2);
			e.printStackTrace();
			System.err.println("Hi problem with file :" + e.getMessage());
		}
	}

	
	public boolean isValidEXCELforquestionbank(InputStream is3) {
		try {
			Workbook wb = new HSSFWorkbook(is3);
			Sheet sheet = wb.getSheetAt(0);

			if (sheet == null) {
				System.err.println("Sorry no sheet available");
				CommonParams2.showMessageOnScreen("Sorry no sheet available ");
				return false;
			}
			int lastRowNum = sheet.getLastRowNum();

			Row row = sheet.getRow(0);
			if (row == null || lastRowNum <= 1) {
				System.err.println("Sorry no rows available");
				CommonParams2.showMessageOnScreen("Sorry no rows available");
				return false;
			}
			
			for(int i=0;i<17;i++)
			{
				String processtext1 = processtext(row.getCell(i)).trim();
				if(processtext1.isEmpty())
				{
					System.err.println("Sorry Column names donot match");
					CommonParams2.showMessageOnScreen("Sorry Column names donot match");
					return false;
				}
			}
			
 			
			return true;
		} catch (IOException e) {
			System.err.println("Sorry Mith File Exception ");
			CommonParams2.showMessageOnScreen("Sorry Mith File Exception ");
			e.printStackTrace();
			return false;
		}
		}
	
	public boolean isValidEXCELforstudentdetails(InputStream is3) {
		try {
			Workbook wb = new HSSFWorkbook(is3);
			Sheet sheet = wb.getSheetAt(0);

			if (sheet == null) {
				System.err.println("Sorry no sheet available");
				CommonParams2.showMessageOnScreen("Sorry no sheet available ");
				return false;
			}
			int lastRowNum = sheet.getLastRowNum();

			Row row = sheet.getRow(0);
			if (row == null || lastRowNum <= 1) {
				System.err.println("Sorry no rows available");
				CommonParams2.showMessageOnScreen("Sorry no rows available");
				return false;
			}
			String processtext1 = processtext(row.getCell(0)).trim();
			String processtext2 = processtext(row.getCell(1)).trim();
			String processtext3 = processtext(row.getCell(2)).trim();
			String processtext4 = processtext(row.getCell(3)).trim();
			String processtext5 = processtext(row.getCell(4)).trim();
			String processtext6 = processtext(row.getCell(5)).trim();
			String processtext7 = processtext(row.getCell(6)).trim();
			String processtext8 = processtext(row.getCell(7)).trim();
			String processtext9 = processtext(row.getCell(8)).trim();
			String processtext10 = processtext(row.getCell(9)).trim();

			if (!processtext1.equalsIgnoreCase("Fname") || !processtext2.equalsIgnoreCase("Mname")
					|| !processtext3.equalsIgnoreCase("Lname") || !processtext4.equalsIgnoreCase("Course name")
					|| !processtext5.equalsIgnoreCase("CenterID") || !processtext6.equalsIgnoreCase("Taluka")
					|| !processtext7.equalsIgnoreCase("District") || !processtext8.equalsIgnoreCase("State")
					|| !processtext9.equalsIgnoreCase("emailid") || !processtext10.equalsIgnoreCase("Phoneno")) {
					
					System.err.println("Sorry Column names donot match");
					CommonParams2.showMessageOnScreen("Sorry Column names donot match");
					return false;
			}
			
			return true;
		} catch (IOException e) {
			System.err.println("Sorry Mith File Exception ");
			CommonParams2.showMessageOnScreen("Sorry Mith File Exception ");
			e.printStackTrace();
			return false;
		}
		

	}
	
	public void doImportQuestionBank(InputStream is3)
	{
		try {
			
			Workbook wb=new HSSFWorkbook(is3);
			Sheet sheet=wb.getSheetAt(0);
			int noofrows=sheet.getLastRowNum();
			System.out.println("ToImportExcel.doImportQuestionBank()");
			System.out.println("No of rows="+noofrows);
			listofquestionmaster=new ArrayList<TblQuestionsMaster>();
			
			for(int i=1;i<=noofrows;i++)
			{
				TblQuestionsMaster master=new TblQuestionsMaster();
				ArrayList<TblQuestionOptions> lstqoptions=new ArrayList<TblQuestionOptions>();
				
				Row row=sheet.getRow(i);
				if(row==null)
				{
					System.err.println("Null row at="+i);
					continue;
				}
				
				Cell cell1=row.getCell(0);
				String qname=processtext(cell1);
				if(qname==null || qname.trim().isEmpty())
				{
					System.err.println("Null column at ="+i);
					continue;
				}
				Integer courseid = (int) Math.round(row.getCell(1).getNumericCellValue());
				Integer marksid = (int) Math.round(row.getCell(2).getNumericCellValue());
				Integer userid = (int) Math.round(row.getCell(3).getNumericCellValue());
				
				Integer multiple = (int) Math.round(row.getCell(4).getNumericCellValue());
				Boolean realmultiple=multiple==0?false:true;
				
				Integer noofoptions = (int) Math.round(row.getCell(5).getNumericCellValue());
				
				master.setQDesc(qname);
				master.setQOption(noofoptions);
				master.setQMultiSelect(realmultiple);
				
				TblMarksMaster tblmarks=new TblMarksMaster();
				tblmarks.setMId(marksid);
				master.setTblMarksMaster(tblmarks);
				
				TblQuestioncategoryMaster tblcat=new TblQuestioncategoryMaster();
				tblcat.setQcId(courseid);
				master.setTblQuestioncategoryMaster(tblcat);
				
				TblUserMaster tbluser=new TblUserMaster();
				tbluser.setUId(338);
				master.setTblUserMaster(tbluser);
				
				String correctans = processtext(row.getCell(16));
				
				Integer columnnumber=6+noofoptions;
				Integer index=1;
				
				for(int ans=6;ans<columnnumber;ans++)
				{
					TblQuestionOptions options=new TblQuestionOptions();
					String anstext = processtext(row.getCell(ans));
					options.setQoName(anstext);
					
					if(answermap2.get(correctans).equals(index))
					{
						options.setQSelected(true);
					}
					else
					{
						options.setQSelected(false);
					}
					lstqoptions.add(options);
					index++;
				}
				listofquestionmaster.add(master);
				listofquestionoptions.put(i-1	, lstqoptions);
				
				System.out.println("Cell at row="+i+" no of options="+lstqoptions.size());
			}
			
			System.out.println("no of rows after process="+listofquestionmaster.size());
			System.out.println("Done QuestionMaster & option correclty");
		} catch (Exception e) {
			Logger.getLogger(ToImportExcel.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	public void doImportStudentDetails(InputStream is2) {

		try {
			// FileOutputStream fout=new FileOutputStream(inputfile);

			// FileInputStream fin = new FileInputStream(inputfile);

			// Workbook wb = new HSSFWorkbook(fin);
			Workbook wb = new HSSFWorkbook(is2);
			Sheet sheet = wb.getSheetAt(0);
			int noofrows = sheet.getLastRowNum();
			System.out.println("Now of rows=" + noofrows);
			listofstudents = new ArrayList<TblStudentDetails>();
			for (int i = 1; i <= noofrows; i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					System.out.println("Null row at=" + i);
					continue;
				}
				Cell cell1 = row.getCell(0);
				String firstname = processtext(cell1);// .getStringCellValue();
				if (firstname == null) {
					System.out.println("Null column at=" + i);
					continue;
				}

				String middlename = processtext(row.getCell(1));// .getStringCellValue();
				String lastname = processtext(row.getCell(2));// .getStringCellValue();
				String coursename = processtext(row.getCell(3));// .getStringCellValue();
				String centerid = processtext(row.getCell(4));// .getStringCellValue();
				String city = processtext(row.getCell(5));// .getStringCellValue();
				String district = processtext(row.getCell(6));// .getStringCellValue();
				String state = processtext(row.getCell(7));// .getStringCellValue();
				String emailid = processtext(row.getCell(8));// .getStringCellValue();
				String mobileno = processtext(row.getCell(9));// .getStringCellValue();

				Integer courseid = mapofcourses.get(coursename);

				TblStudentDetails std = new TblStudentDetails();
				std.setFirstname(firstname);
				std.setLastname(lastname);
				std.setMiddlename(middlename);
				std.setActive("active");
				std.setCitytownvillage(city);
				std.setDistrict(district);
				std.setState(state);
				std.setEmailid(emailid);
				std.setMobileown(mobileno);

				TblCenterDetails center = new TblCenterDetails();
				center.setAnpcode(centerid);

				std.setTblCenterDetails(center);

				TblQuestioncategoryMaster cat = new TblQuestioncategoryMaster();
				cat.setQcId(courseid);
				std.setTblQuestioncategoryMaster(cat);

				System.out.println("CONTENT IS" + middlename + lastname
						+ courseid + centerid + city + state + emailid
						+ mobileno);
				listofstudents.add(std);
			}
			System.out.println("Done Student properly");
			// doSaveToDBStudentDetails();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ToImportExcel.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (Exception ex2) {
			Logger.getLogger(ToImportExcel.class.getName()).log(Level.SEVERE,
					null, ex2);
		}

	}
	public String doSaveToDBQuestionBank() {
	
		System.out.println("ToImportExcel.doSaveToDBQuestionBank()");
		System.out.println("Size of array of questionbank "+listofquestionmaster.size());
		
		for(int i=0;i<listofquestionmaster.size();i++)
		{
			TblQuestionsMaster tblqm = listofquestionmaster.get(i);
			tblqm.setQName("not yet implemented");
			Integer pk = QuestionsUtil.doGetNextPK("TblQuestionsMaster", "Q_ID",false);
			tblqm.setQId(pk);
			tblqm.setQTag(pk);
			QuestionsUtil.saveToNewQuestions(tblqm);
			
			ArrayList<TblQuestionOptions> list = listofquestionoptions.get(i);
			for(int j=0;j<list.size();j++)
			{
				TblQuestionOptions tblqoptions = list.get(j);
				Integer qoId=QuestionsUtil.doGetNextPK("TblQuestionOptions", "QO_ID	", false);
				tblqoptions.setQoId(qoId);
				tblqoptions.setQoTag(qoId);
				tblqoptions.setTblQuestionsMaster(tblqm);
				QuestionsUtil.saveToNewQuestions(tblqoptions);
				System.out.println("Done saving a row of question option ");
			}
			
			System.out.println("Done saving a row of question master");


		}
		
		return "index.jsf";
	}
	
	public String doSaveToDBStudentDetails() {
		System.out.println("Size of arraylist of students is="
				+ listofstudents.size());
		for (int i = 0; i < listofstudents.size(); i++) {
			TblStudentDetails tblStudentDetails = listofstudents.get(i);
			String ret2 = "MCITER/AJ12";

			// System.out.println("Content of student is:"+tblStudentDetails.toString());

			Integer pk2 = QuestionsUtil.doGetNextPK("TblStudentDetails", "tag");
			ret2 += pk2;
			tblStudentDetails.setStudentid(ret2);
			tblStudentDetails.setTag(pk2);
			System.out.println("Content of student is:"
					+ tblStudentDetails.toString());

			QuestionsUtil.saveToNewQuestions(tblStudentDetails);
			System.out.println("Done saving");
		}
		System.out.println("DONE SAVING ALL STUDENT DETAILS");
		return "index.jsf";
	}

	public String processtext(Cell c) {
		String ret = "";

		if (c == null) {
			return ret;
		}
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			System.out.println("is blank");
			ret = "";
			break;
		case Cell.CELL_TYPE_STRING:
			System.out.println(c.getRichStringCellValue().getString());
			ret = c.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(c)) {
				System.out.println("date " + c.getDateCellValue());
				ret = c.getDateCellValue().toString();
			} else {
				System.out.println("number "
						+ Math.round(c.getNumericCellValue()));

				ret = "" + Math.round(c.getNumericCellValue());
				// c.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println("bool " + c.getBooleanCellValue());
			ret += "" + c.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			System.out.println("is formula not reequired here "
					+ c.getCellFormula());
			break;
		default:
			System.out.println("default something wrong");
		}

		return ret.trim();
	}

	public ArrayList<TblStudentDetails> getListofstudents() {
		return listofstudents;
	}

	public void setListofstudents(ArrayList<TblStudentDetails> listofstudents) {
		this.listofstudents = listofstudents;
	}

	public ArrayList<TblQuestionsMaster> getListofquestionmaster() {
		return listofquestionmaster;
	}

	public void setListofquestionmaster(
			ArrayList<TblQuestionsMaster> listofquestionmaster) {
		this.listofquestionmaster = listofquestionmaster;
	}
}
