package com.mciter.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mciter.commonbeans.MarksMaster;
import com.mciter.commonbeans.QuestionCategoryMaster;
import com.mciter.commonbeans.QuestionMaster;
import com.mciter.commonbeans.TblCenterDetails;
import com.mciter.commonbeans.TblEmailconfigMaster;
import com.mciter.commonbeans.UserMaster;
import com.mciter.services.Forms_Center_StudentController;
import com.mciter.services.QuestionsUtil;
import com.mciter.services.StudentExamPaperController;
import com.mciter.services.TypingStudentExamPaperController;

public class CommonParams2 {

	// private String[] noofoptions={"1","2","3","4","5","6","7","8","9","10"};
	private Integer[] noofoptions = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private String[] showABCoptions = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J" };

	private String[] course = { "Java", "C#", "Ruby" };

	private static TimeZone indiantimezone;
	private static Calendar indiancalendar;
	private String browsername="";
	private String browserversion="";
	private String fonturl="";
	public CommonParams2()
	{
		getBrowserInfo();
	}
	
	public static Boolean sendMailAlert(String emailto, String message2,String subject) {

		System.out.println("CommonParams2.sendMailAlert()");
		final StringBuffer username = new StringBuffer();//"atulmith@gmail.com";
		final StringBuffer password = new StringBuffer();//"opentaps";

		List<TblEmailconfigMaster> retrieveALLwithHB =QuestionsUtil.retrieveALLwithHB(new TblEmailconfigMaster(), "TblEmailconfigMaster", "");
		
		if(retrieveALLwithHB.size()<=0)
		{
			System.err.println("empty email config");
			return false;
		}
		TblEmailconfigMaster tblEmailconfigMaster = retrieveALLwithHB.get(0);
		username.append(tblEmailconfigMaster.getEmailconfigEmailid());
		password.append(tblEmailconfigMaster.getEmailconfigPass());
				
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", tblEmailconfigMaster.getEmailconfigOuthost());//"smtp.gmail.com");
		props.put("mail.smtp.port", tblEmailconfigMaster.getEmailconfigOutport());//"587");
		
		

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username.toString(), password.toString());
					}
				});
//		String message2 = "Hello , \n";
//		message2+="This is a alert message because the user "+ adminUsername +" has exceeded its assigned quota.";
//		message2+="\n";
//		message2+="His skimmed entries ="+surveydetails.getAdminSurveySkimming();
//		message2+="\n";
//		message2+="His edit entries ="+surveydetails.getAdminSurveyEdit();
//		message2+="\n";
//		message2+="His date of occurence ="+surveydetails.getAdminSurveyDate().toString();
//		
		
		
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username.toString()));//"atulmith@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailto));
			message.setSubject(subject);//"Alert for user quota");
			message.setText(message2);

			Transport.send(message);

			System.out.println("Email send Done");
			return true;

		} catch (Exception e) {
			System.out.println("CommonParams2.sendMailAlert()");
			System.err.println("Sorry sending error " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public String doGetEmailofCenter(String anpcode)
	{
		List<TblCenterDetails> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblCenterDetails(), "TblCenterDetails", "anpcode='"+anpcode+"' ");
		String mailid="mciter1@gmail.com";
		if(retrieveWherClause!=null && retrieveWherClause.size()>0)
		{
			TblCenterDetails obj = retrieveWherClause.get(0);
			mailid= obj.getEmailid();
		}
		
		return mailid;
	}

	public String doGetCourseNameFromID(Integer obj)
	{
		List<QuestionCategoryMaster> courseList2 = getCourseList2();
		for (QuestionCategoryMaster row : courseList2) {
			if(obj.equals(row.getQC_ID()))
			{
				return row.getQC_Name();
			}
		}
		return "";
	}
	
	public static String encodeToAscii(String str)
	{
		String ret="";
		String prepend="&#";
		if(str!=null && str.length()>0)
		{
			for(int i=0;i<str.length();i++)
			{
				char charAt = str.charAt(i);
				int asciicahr=(int)charAt;
				String abc=prepend+""+asciicahr;
				ret+=abc;
			}
		}
		return ret;
	}
//	&#64&#65&#66
	public static String decodeFromAscii(String str2)
	{
		String ret2="";
		String prepend="&#";
		System.out.println("string="+str2);
		try {
			if(str2!=null && str2.length()>0)
			{
				String[] split = str2.split(prepend);
				for(int i=0;i<split.length;i++)
				{
					if(split[i].isEmpty())
					{
						continue;
					}
					int parseInt = Integer.parseInt(split[i]);
					System.out.println("ascii integer is="+parseInt);
					char a=(char)parseInt;
					System.out.println("ascii value is="+a);
					ret2+=""+a;
				}
			}
			System.out.println("return is "+ret2);
		} catch (NumberFormatException e) {
			System.out.println("CommonParams2.decodeFromAscii()");
			e.printStackTrace();
		}
		return ret2;
	}
	public static Date getIndianDate() 
	{
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	    df.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));//GMT+5:30"));
	    String format = df.format(new Date());
	    System.out.println("Format="+format);
	    Date parse=null;
		try {
			parse = df.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("date is "+parse.toString());
	    return parse;
//	    Calendar cal=df.getCalendar();
//	    cal.setTime(parse);
//	    return cal;
	    
	}
	public static Calendar getIndiancalendar() {
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT+5:30");
		java.util.Calendar c = java.util.Calendar.getInstance(tz);
		indiancalendar = c;
		return indiancalendar;
	}
	public boolean isValidBorwser(String redirecturl)
	{
		ExternalContext extcontext = FacesContext.getCurrentInstance().getExternalContext();
		String [] browserinfo=(String[]) getBrowserInfo().toArray(new String[0]);
		
		boolean retvalue=true;
		if(browserinfo==null)
		{
			return true;
		}
		try {
			String browsername=browserinfo[0];
			Double version=Double.parseDouble(browserinfo[1]);
			
			if(browsername.indexOf("Chrome")!=-1 && version>=20.0 )
			{
				retvalue=true;
			}
			else if(browsername.indexOf("Firefox")!=-1 && version>=7.0 )
			{
				retvalue=true;
			}
			else
			{
				retvalue=false;
			}
			if(retvalue==false)
			{
				extcontext.redirect(redirecturl);
				
			}
			
		} catch (Exception e) {
			System.out.println("isValidBrowser method error here: "+e.getMessage());
			e.printStackTrace();
		}
		
		return retvalue;
	}

	public ArrayList getBrowserInfo() {
		ExternalContext extcontext = FacesContext.getCurrentInstance().getExternalContext();
		String Information = extcontext.getRequestHeaderMap().get("User-Agent");
		ArrayList retvalue=new ArrayList();
		
//		String browsername = "";
//		String browserversion = "";
		String browser = Information;
		if (browser.contains("MSIE")) {
			String subsString = browser.substring(browser.indexOf("MSIE"));
			String Info[] = (subsString.split(";")[0]).split(" ");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Firefox")) {

			String subsString = browser.substring(browser.indexOf("Firefox"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Chrome")) {

			String subsString = browser.substring(browser.indexOf("Chrome"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Opera")) {

			String subsString = browser.substring(browser.indexOf("Opera"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Safari")) {

			String subsString = browser.substring(browser.indexOf("Safari"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		}
		if(browserversion.indexOf(".")!=-1)
		{
			StringTokenizer token=new StringTokenizer(browserversion,".");
			browserversion=token.nextToken();
			
		}
		retvalue.add(browsername);
		retvalue.add(browserversion);
		
		return retvalue;
		//new String[] { browsername, browserversion };
	}

	/**
	 * If user is not logged then redirect to loginpage
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	public void checkCredentialsForLoggedOut(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);

		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();
			if (sessionname.equals("studentExamPaperController")) {
				StudentExamPaperController exampaper = (StudentExamPaperController) session
						.getAttribute(sessionname);
				if (exampaper.getCurrentquestiontypename() != null) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials studentexampaper is not null");
					return;
				}
			}
			if (sessionname.equals("forms_CenterController")) {
				Forms_Center_StudentController center2 = (Forms_Center_StudentController) session
						.getAttribute(sessionname);
				if (center2.getCenterdetails().getAnpcode() != null) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkcredentials center2 is not null");
					return;
				}
			}
			if (sessionname.equals("userMaster")) {
				UserMaster user2 = (UserMaster) session
						.getAttribute(sessionname);
				if (user2.getU_UserName() != null
						&& user2.getU_UserName().isEmpty() == false) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkcredentials user2 is not null");
					return;
				}
			}
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working after redirect");

		} else {
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

		}

	}

	/**
	 * If user is already logged in direct to the required page
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	public void checkCredentialsForLoggedIn(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpSession session = (HttpSession) ctx.getSession(false);
		Map<String, Object> sessionMap = ctx.getSessionMap();
		for (String va2 : sessionMap.keySet()) {

			System.out.println(" Key is :" + va2 + " Value is :"
					+ sessionMap.get(va2));
		}
		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();
			if (sessionname.equals("typingStudentExamPaperController")) {
				TypingStudentExamPaperController exampaper = (TypingStudentExamPaperController) session
						.getAttribute(sessionname);
				Integer seId = null;
				try
				{
					seId=exampaper.getStudentexamconduct().getSeId();
				
				}
				catch(NullPointerException ex)
				{
					System.out.println("Nullpointexception in TYPINGSTUDENTEXAMPAPERCONTROLLER of commonparams");
				}
				if ( seId== null) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials studentexampaper is null");
					return;
				}
			}
			if (sessionname.equals("studentExamPaperController")) {
				StudentExamPaperController exampaper = (StudentExamPaperController) session
						.getAttribute(sessionname);
				String currentquestiontypename =null;
				try
				{
					currentquestiontypename=exampaper.getCurrentquestiontypename();
				}
				catch(NullPointerException e)
				{
					System.out.println("Nullpointerecxception in studentexampapercontroller of commonparams");
				}
				if ( currentquestiontypename== null) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials studentexampaper is null");
					return;
				}
			}
			if (sessionname.equals("forms_CenterController")) {
				Forms_Center_StudentController center2 = (Forms_Center_StudentController) session
						.getAttribute(sessionname);
				if (center2.getCenterdetails().getAnpcode() == null
						|| center2.getCenterdetails().getAnpcode().isEmpty()) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials formscenter2 is null");
					return;
				}
			}
			if (sessionname.equals("userMaster")) {
				UserMaster user2 = (UserMaster) session
						.getAttribute(sessionname);
				if (user2.getU_UserName() == null
						|| user2.getU_UserName().isEmpty()) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials usermaster is null");
					return;
				}
			}
			try {
				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working before redirect");
				// resp.sendRedirect(urltoredirect);
				ctx.redirect(urltoredirect);

				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working after redirect");

			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedIn mehod cannot redirect");
				e.printStackTrace();
			}
		}

	}

	public TimeZone getIndiantimezone() {
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT+5:30");
		java.util.Calendar c = java.util.Calendar.getInstance(tz);
		indiantimezone = tz;
		return indiantimezone;
	}

	public void setIndiantimezone(TimeZone indiantimezone) {
		this.indiantimezone = indiantimezone;
	}

	public String[] getShowABCoptions() {
		return showABCoptions;
	}

	public void setShowABCoptions(String[] showABCoptions) {
		this.showABCoptions = showABCoptions;
	}

	public List<SelectItem> getNoofoptionsList() {

		List<SelectItem> ret = new ArrayList<SelectItem>();
		ret.add(new SelectItem("", "--Select--"));
		for (Integer opts : noofoptions) {
			ret.add(new SelectItem(opts.toString()));

		}
		return ret;

	}

	/**
	 * here selectItem(courseid,coursename) courseid comes foreign key
	 * 
	 * @return
	 */
	public List<SelectItem> getCourseList() {
		ArrayList<SelectItem> ret = new ArrayList<SelectItem>();
		// Integer i=0;
		// for (String cour : course) {
		// i++;
		// ret.add(new SelectItem(i,cour));
		// }
		ret.add(new SelectItem("", "--Select--"));
		List query = QuestionsUtil.retrieveALL(new QuestionCategoryMaster(),
				"QuestionCategoryMaster", " QC_ID , QC_Name");
		for (Iterator it = query.iterator(); it.hasNext();) {
			Object[] row = (Object[]) it.next();
			Integer id2 = (Integer) row[0];
			String name = (String) row[1];
			ret.add(new SelectItem(id2, name));

		}
		return ret;
	}

	/**
	 * here QuestionCategoryMaster( ) courseid comes foreign key
	 * 
	 * @return
	 */
	public List<QuestionCategoryMaster> getCourseList2() {
		List<QuestionCategoryMaster> ret = new ArrayList<QuestionCategoryMaster>();
		// Integer i=0;
		// for (String cour : course) {
		// i++;
		// ret.add(new SelectItem(i,cour));
		// }
		// ret.add(new SelectItem("--Select--"));
		ret = QuestionsUtil.retrieveALLwithHB(new QuestionCategoryMaster(),
				"QuestionCategoryMaster", " QC_ID , QC_Name");
		// for(Iterator it=query.iterator();it.hasNext();){
		// Object[] row = (Object[]) it.next();
		// Integer id2=(Integer) row[0];
		// String name=(String) row[1];
		// ret.add(new SelectItem(id2,name));
		//
		// }
		return ret;
	}

	/**
	 * here selectItem(marksid,marksname)
	 * 
	 * @return
	 */
	public List<SelectItem> getMarksList() {
		ArrayList<SelectItem> ret = new ArrayList<SelectItem>();
		// Integer i=0;
		// for (String cour : course) {
		// i++;
		// ret.add(new SelectItem(i,cour));
		// }
		ret.add(new SelectItem("", "--Select--"));
		List query = QuestionsUtil.retrieveALL(new MarksMaster(),
				"MarksMaster", " M_ID , M_Name");
		for (Iterator it = query.iterator(); it.hasNext();) {
			Object[] row = (Object[]) it.next();
			Integer id2 = (Integer) row[0];
			String name = (String) row[1];
			ret.add(new SelectItem(id2, name));

		}
		return ret;
	}

	/**
	 * here selectItem(marksid,marksname)
	 * 
	 * @return
	 */
	public List<MarksMaster> getMarksList2() {
		List<MarksMaster> ret = new ArrayList<MarksMaster>();
		// Integer i=0;
		// for (String cour : course) {
		// i++;
		// ret.add(new SelectItem(i,cour));
		// }
		// ret.add(new SelectItem("--Select--"));
		ret = QuestionsUtil.retrieveALLwithHB(new MarksMaster(), "MarksMaster",
				" M_ID , M_Name");
		// for(Iterator it=query.iterator();it.hasNext();){
		// Object[] row = (Object[]) it.next();
		// Integer id2=(Integer) row[0];
		// String name=(String) row[1];
		// ret.add(new SelectItem(id2,name));
		//
		// }
		return ret;
	}

	public List<QuestionMaster> getQuestionMasterList() {
		ArrayList<QuestionMaster> ret = new ArrayList<QuestionMaster>();

		List query = QuestionsUtil
				.retrieveALL(
						new QuestionMaster(),
						"QuestionMaster",
						" Q_ID, Q_Name, "
								+ "QC_ID, Q_Desc, Q_MultiSelect, Q_Option, Q_Tag, U_ID, M_ID");
		List<SelectItem> markslist2 = this.getMarksList();
		List query2 = QuestionsUtil.retrieveALL(new QuestionCategoryMaster(),
				"QuestionCategoryMaster", "QC_ID, QC_Name");

		for (Iterator it = query.iterator(); it.hasNext();) {
			Object[] row = (Object[]) it.next();
			QuestionMaster obj2 = new QuestionMaster();
			obj2.setQ_ID((Integer) row[0]);
			obj2.setQ_Name((String) row[1]);
			obj2.setQC_ID((Integer) row[2]);
			obj2.setQ_Desc((String) row[3]);
			obj2.setQ_MultiSelect((Boolean) row[4]);
			obj2.setQ_Option((Integer) row[5]);
			obj2.setQ_Tag((Integer) row[6]);
			obj2.setU_ID((Integer) row[7]);
			obj2.setM_ID((Integer) row[8]);

			for (int jj = 1; jj < markslist2.size(); jj++) {

				SelectItem selectItem = markslist2.get(jj);
				Integer midis = (Integer) selectItem.getValue();

				Double d = Double.parseDouble(selectItem.getLabel());
				if (obj2.getM_ID().intValue() == midis.intValue()) {
					obj2.setMarks(d);
				}
			}
			for (Iterator it2 = query2.iterator(); it2.hasNext();) {
				Object[] row2 = (Object[]) it2.next();
				Integer pk = (Integer) row2[0];
				if (pk.intValue() == obj2.getQC_ID().intValue()) {
					obj2.setCategoryname((String) row2[1]);
				}
			}
			ret.add(obj2);

		}

		return ret;
	}

	public String getHi() {
		return "luck";
	}

	public void setHi(String o) {
		ExternalContext ext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Map<String, Object> requestMap = ext.getRequestMap();
		boolean haskey = requestMap.containsKey("questionMaster");
		if (haskey)
			System.out.println("Mith Has Key :"
					+ requestMap.get("questionMaster"));
		else
			System.out.println("Mith No such param");
		for (String keys : requestMap.keySet()) {
			System.out.println("Mith Key is:" + keys + " value is:"
					+ requestMap.get(keys));
		}
		System.out.println("Mith see above");
	}

	public static UserMaster getUserInfo() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Map<String, Object> sessionMap = externalContext.getSessionMap();
		// System.out.println(" Mithun Working Session map");
		//
		// for (String va2 : sessionMap.keySet()) {
		//
		// System.out.println(" Key is :"+va2+" Value is :"+sessionMap.get(va2));
		// }
		if (sessionMap.get("userMaster") != null) {
			return (UserMaster) sessionMap.get("userMaster");
		} else {
			return null;
		}
	}

	public static void showMessageOnScreen(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message, message));
	}

	public static void showMessageOnLog(Class a, String message) {
		Logger l = Logger.getLogger(a.getName());
		l.setLevel(Level.INFO);
		// l.warning(message);
		l.info(message);
	}
	public String getBrowsername() {
		
		return browsername;
	}
	public void setBrowsername(String browsername) {
		this.browsername = browsername;
	}
	public String getBrowserversion() {
		return browserversion;
	}
	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}
	public String getFonturl() {
		return fonturl;
	}
	public void setFonturl(String fonturl) {
		this.fonturl = fonturl;
	}
}
