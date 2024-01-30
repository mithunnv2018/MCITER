package com.mciter.commonbeans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mciter.utils.CommonParams2;
import com.mciter.utils.HibernateUtils;

@Entity
public class UserMaster implements Serializable {

	private Integer U_ID;
	private String U_FullName,U_UserName,U_Pass,U_Tag;
	
	public Integer getU_ID() {
		return U_ID;
	}

	public void setU_ID(Integer u_ID) {
		U_ID = u_ID;
	}

	public String getU_FullName() {
		return U_FullName;
	}

	public void setU_FullName(String u_FullName) {
		U_FullName = u_FullName;
	}

	public String getU_UserName() {
		return U_UserName;
	}

	public void setU_UserName(String u_UserName) {
		U_UserName = u_UserName;
	}

	public String getU_Pass() {
		return U_Pass;
	}

	public void setU_Pass(String u_Pass) {
		U_Pass = u_Pass;
	}

	public String getU_Tag() {
		return U_Tag;
	}

	public void setU_Tag(String u_Tag) {
		U_Tag = u_Tag;
	}
	public String saveToDB()
	{
		Session session=HibernateUtils.currentSession();
		Transaction beginTransaction = session.beginTransaction();
		try
		{
			UserMaster u2=new UserMaster();
			beginTransaction.begin();
			u2.setU_ID(new java.util.Random().nextInt(1000));
			u2.setU_FullName(U_UserName+U_Pass);
			u2.setU_Tag("Hi Testing");
			u2.setU_UserName(U_UserName);
			u2.setU_Pass(U_Pass);
			
			session.save(u2);
			
			beginTransaction.commit();
		}
		catch(Exception ex)
		{
			System.out.println("savetodb metyhod error!"+ex.toString());
			if(beginTransaction!=null)
			beginTransaction.rollback();
			return "failure";
		}
		finally
		{
			if(session!=null )
				HibernateUtils.closeSession();
		}
		return "success";
	}
	public String reset()
	{
		this.U_UserName="";
		this.U_Pass="";
		U_FullName="";
		U_Tag="";
		return "loginpage.jsf";
	}
	public String checklogin()
	{
		Session session = HibernateUtils.currentSession();
		try {
			Query query = session.createSQLQuery("SELECT u_id , u_fullname "
                    + " FROM tbl_user_master WHERE u_username='" + U_UserName + "' AND u_pass='"+U_Pass+"'");
			List dataList = query.list();
			
			for(Iterator it=dataList.iterator();it.hasNext();){
				 Object[] row = (Object[]) it.next();
				 this.U_ID=(Integer)row[0];
				 this.U_Tag=U_ID.toString();
				 this.U_FullName=(String)row[1];
				   
				 
				 } 
			 
			if(dataList.size()>0)
			{
				CommonParams2.showMessageOnScreen("Successfull Login for Admin!");
				return "successlogin";
			}
			else
			{
//				 reset();
				 System.out.println("BAD LOG");
//				FacesContext.getCurrentInstance().addMessage("idpanelgrid2", new FacesMessage("Your Login was not successfull!","Please Reenter your credentials."));
				CommonParams2.showMessageOnScreen("Your Login was not successfull!,Please Reenter your credentials.");
				
				return reset();
			}
		} catch (Exception e) {
			System.out.println("checklogin method error:"+e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}
	public UserMaster() {
		// TODO Auto-generated constructor stub
	}
	

}
