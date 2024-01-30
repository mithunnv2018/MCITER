package com.mciter.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.mciter.utils.CommonParams2;
import com.mciter.utils.Theme2;

public class ThemeSwitcher2 implements Serializable{

	private String theme="afterwork";//redmond";
	private Theme2 guesttheme=null;
	
	public Theme2 getGuesttheme() {
		return guesttheme;
	}

	public void setGuesttheme(Theme2 guesttheme) {
		this.guesttheme = guesttheme;
	}

	public ThemeSwitcher2() {
		
		theme="redmond";
		guesttheme=new Theme2(); 
	}
	
	@PostConstruct
	public void init()
	{

		theme="redmond";
		guesttheme=new Theme2();
	}
	
	public void savetheme()
	{
		guesttheme.setTheme(theme);
		CommonParams2.showMessageOnLog(this.getClass(), "Mith enterd savetheme:"+theme);
	}
//	PostConstruct
//	public void init()
//	{
//		themes=new ArrayList<String>();
//		themes.add("aristo");
//		themes.add("afternoon");
//		themes.add("afterwork");
//		themes.add("redmond ");
//		theme="redmond";
//	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	 
	
}
