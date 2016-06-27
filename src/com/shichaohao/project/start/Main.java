package com.shichaohao.project.start;

import java.util.ArrayList;

import com.shichaohao.project.capture.CaptureWeb;
import com.shichaohao.project.dao.DB;
import com.shichaohao.project.data.CleanTags;
import com.shichaohao.project.data.CountTags;
import com.shichaohao.project.data.GetArticleData;
import com.shichaohao.project.data.JudgeEmotion;
import com.shichaohao.project.entity.ArticleContent;
import com.shichaohao.project.entity.ArticleInfo;

/**
 * 程序开始入口
 * @author Think
 *
 */
public class Main {

	public static void main(String[] args) {
		CaptureWeb.captureBase();
		ArrayList<ArticleInfo> articleList = GetArticleData.getArticleData("2015-07-01 00:00:00");
		CleanTags cleanTags = new CleanTags();
		cleanTags.cleanCommonTag(articleList);
		String hottestTag = CountTags.getHottestTag(CountTags.sum(articleList));
		CaptureWeb.captureContent(articleList, hottestTag);
		ArrayList<ArticleContent> articleContents = DB.selectArticleContent(hottestTag);
		JudgeEmotion.judgeEmotion(articleContents);
	}

}
