package com.mciter.services;

import java.util.ArrayList;

import com.mciter.commonbeans.QuestionMaster;
import com.mciter.commonbeans.QuestionOptions;
import com.mciter.commonbeans.UserMaster;
import com.mciter.utils.CommonParams2;

public class QuestionMasterController {

	private QuestionMaster questionMaster;
	private ArrayList<QuestionOptions> questionOptionsList;
	private QuestionOptions[] questionOptionsSelected;
	private QuestionOptions questionOptionsSingleSelected;

	public QuestionMasterController() {
		questionMaster = new QuestionMaster();
	}

	public QuestionOptions[] getQuestionOptionsSelected() {
		return questionOptionsSelected;
	}

	public void setQuestionOptionsSelected(
			QuestionOptions[] questionOptionsSelected) {
		this.questionOptionsSelected = questionOptionsSelected;
	}

	public QuestionOptions getQuestionOptionsSingleSelected() {
		return questionOptionsSingleSelected;
	}

	public void setQuestionOptionsSingleSelected(
			QuestionOptions questionOptionsSingleSelected) {
		this.questionOptionsSingleSelected = questionOptionsSingleSelected;
	}

	public ArrayList<QuestionOptions> getQuestionOptionsList() {
		return questionOptionsList;
	}

	public void setQuestionOptionsList(
			ArrayList<QuestionOptions> questionOptionsList) {
		this.questionOptionsList = questionOptionsList;
	}

	public QuestionMaster getQuestionMaster() {
		return questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

	public void doShowOptions() {

		questionOptionsList = new ArrayList<QuestionOptions>();

		System.out.println("doShowOptions is working!");
		for (int i = 0; i < questionMaster.getQ_Option(); i++) {
			QuestionOptions opt = new QuestionOptions();
			opt.setQO_ID(QuestionsUtil.doGetNextPK("", ""));
			opt.setQ_Selected(false);
			opt.setQO_Name("Enter Here");
			opt.setQO_Tag(opt.getQO_ID());

			questionOptionsList.add(opt);
		}
	}

	public String doSave() {
		// questionMaster.setM_ID(m_ID) DONE AUTOMATICALLY
		// questionMaster.setQ_Desc(q_Desc) DONE AUTOMATICALLY
		// questionMaster.setMarks(marks) NOT REQUIRED
		questionMaster.setQ_ID(QuestionsUtil.doGetNextPK("QuestionMaster",
				"Q_Tag"));
		// questionMaster.setQ_MultiSelect() DONE AUTOMATICALLY
		questionMaster.setQ_Name("not yet used");
		// questionMaster.setQ_Option() DONE AUTOMATICALLY
		questionMaster.setQ_Tag(questionMaster.getQ_ID());
		// questionMaster.setQC_ID() DONE AUTOMATICALLY

		UserMaster foreignkey3 = CommonParams2.getUserInfo();

		if (foreignkey3 != null) {
			questionMaster.setU_ID(foreignkey3.getU_ID());
			System.out.println("Foreign key is it there:" + foreignkey3);
		} else {
			questionMaster.setU_ID(338);
			System.out.println("You need to login now!");
		}

		System.out.println("Mith here " + questionMaster.toString());
		QuestionsUtil.saveToNewQuestions(questionMaster);
		dosSaveQuestionOptions(questionMaster);
		System.out.println("Saved to db questionmaster");

		return "index";
	}

	private void dosSaveQuestionOptions(QuestionMaster mainobj) {

		for (int j = 0; j < questionOptionsList.size(); j++) {
			QuestionOptions obj2 = questionOptionsList.get(j);
			obj2.setQ_ID(mainobj.getQ_ID());

			if (questionMaster.getQ_MultiSelect()) {
				for (int i = 0; i < questionOptionsSelected.length; i++) {
					if (obj2.getQO_ID().intValue() == questionOptionsSelected[i]
							.getQO_ID().intValue()) {
						obj2.setQ_Selected(true);
					}
				}
			} else {
				System.out.println("Mith single selected is :"
						+ questionOptionsSingleSelected.toString());
				if (questionOptionsSingleSelected.getQO_ID().intValue() == obj2
						.getQO_ID().intValue()) {
					obj2.setQ_Selected(true);
				}
			}
			obj2.setQO_ID(QuestionsUtil
					.doGetNextPK("QuestionOptions", "QO_Tag"));
			// obj2.setQO_Name() DONE AUTOMATICALLY
			obj2.setQO_Tag(obj2.getQO_ID());
			System.out.println("Mith here:" + obj2.toString());
			QuestionsUtil.saveToNewQuestions(obj2);

		}

	}

	public void doResetQ() {
		questionMaster = new QuestionMaster();
	}

	public void doUpdateShowOptionsFromDB() {
		// questionOptionsList
		System.out.println("started doUpdateShowOptions");

		questionOptionsList = new ArrayList<QuestionOptions>();
		questionOptionsList.addAll(QuestionsUtil.retrieveWherClause(
				new QuestionOptions(), "QuestionOptions", "Q_ID="
						+ questionMaster.getQ_ID()));
		ArrayList<QuestionOptions> tempanswers = new ArrayList<QuestionOptions>();

		for (int i = 0; i < questionOptionsList.size(); i++) {
			QuestionOptions opts = questionOptionsList.get(i);
			if (opts.getQ_Selected() == true) {
				if (questionMaster.getQ_MultiSelect()) {
					tempanswers.add(opts);
				} else {
					questionOptionsSingleSelected = opts;
				}
			}
		}
		if (questionMaster.getQ_MultiSelect()) {
			questionOptionsSelected = tempanswers
					.toArray(new QuestionOptions[0]);
		}

	}

	public String doUpdateQuestion() {
		String ret = "index";

		QuestionMaster mainobj = questionMaster;
		mainobj.setQ_Name("Updated Q_Name");
		// mainobj.setQC_ID()//DONE automatically
		// mainobj.setQ_Desc()//DONE automatically

		UserMaster foreignkey3 = CommonParams2.getUserInfo();

		if (foreignkey3 != null) {
			mainobj.setU_ID(foreignkey3.getU_ID());
			System.out.println("Foreign key is it there:" + foreignkey3);
		} else {
			mainobj.setU_ID(338);
		}
		// mainobj.setM_ID(foreignkey2.getM_ID());//DONE automatically
		// mainobj.setQ_MultiSelect()//DONE automatically
		// mainobj.setQ_Option(q_Option)//DONE automatically
		QuestionsUtil.updateToQuestions(mainobj);
		int rowsdeleted = QuestionsUtil.deleteFromDB("QuestionOptions", "Q_ID="
				+ mainobj.getQ_ID());
		dosSaveQuestionOptions(mainobj);
		
		return ret;
	}
}
