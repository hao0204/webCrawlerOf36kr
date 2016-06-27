package com.shichaohao.project.data;

import java.util.ArrayList;

import com.shichaohao.project.dao.DB;
import com.shichaohao.project.entity.ArticleInfo;

/**
 * 得到特定时间后的数据库中的文章
 * @author Think
 *
 */
public class GetArticleData {
	
	/**
	 * 返回所有文章，存储在类型为ArticleInfo的list表中
	 * @param date 特定日期以后
	 * @return 返回类型为ArticleInfo类型的list表
	 */
	public static ArrayList<ArticleInfo> getArticleData(String date) {
		ArrayList<ArticleInfo> articleList = new ArrayList<ArticleInfo>();
		articleList = DB.selectArticle(date); //"2015-07-01 00:00:00"
		return articleList;
	}
}
