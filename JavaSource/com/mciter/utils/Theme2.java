package com.mciter.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import com.mciter.services.ThemeSwitcher2;

public class Theme2 implements Serializable {

	private String theme="redmond";
	
	public String getTheme() {
//		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		/*Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//        if(params.containsKey("themeSwitcher2")) {

//                ThemeSwitcher2 t2=(ThemeSwitcher2) params.get("themeSwitcher2");
//                if(t2==null || t2.getTheme()==null)
//                {
//                	return theme;
//                }
//                theme=t2.getTheme();
//                CommonParams2.showMessageOnLog(this.getClass(), "MITH get theme got in request!" );
//        }
		if(params.containsKey("theme2")) {

          Theme2 t2=(Theme2) params.get("theme2");
          if(t2==null || t2.theme==null)
          {
          	return theme;
          }
          theme=t2.theme;
          CommonParams2.showMessageOnLog(this.getClass(), "MITH get theme got in request!" );
  }
        else
        {
        	CommonParams2.showMessageOnLog(this.getClass(), "MITH did not get theme got in request!" );
        }
        for (String keys2 : params.keySet()) {
			System.out.println("session maps keys are="+keys2+" the values are="+params.get(keys2));
		}*/
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public List<String> getThemes() {
		
		return themes;
	}
	public void setThemes(List<String> themes) {
		this.themes = themes;
	}
	private List<String> themes;
	public Theme2() {
		
		theme="redmond";
		 
	}
	@PostConstruct
	public void init()
	{
		themes=new ArrayList<String>();
		themes.add("aristo");
		themes.add("afternoon");
		themes.add("afterwork");
		themes.add("redmond");
		theme="redmond";
	}
}
