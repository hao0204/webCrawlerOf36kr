package com.shichaohao.project.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.shichaohao.project.dao.DB;
import com.shichaohao.project.entity.ArticleInfo;
import com.shichaohao.project.entity.CommonTag;

/**
 * 对数据库中的标签进行清理，去除通用行业（例如：公司之类的无用标签）
 * @author Think
 *
 */
public class CleanTags {
	
	private double threshold = 0.1; //jaccard相似度的阈值
	private double edge = 0.00001; //double比较大小的边界值
	static ArrayList<String> lexicon = null; //字典
	
	/**
	 * 去除通用标签，留下行业标签
	 * @param articleList ArticleInfo类的ArrayList容器
	 * @return 返回ArticleInfo类的ArrayList容器
	 */
	public ArrayList<ArticleInfo> cleanCommonTag(ArrayList<ArticleInfo> articleList) {
		ArrayList<CommonTag> commonTagList = new ArrayList<CommonTag>();
		commonTagList = DB.selectCommonTag();
		for (int i = 0; i < articleList.size(); ++i){
			deal(articleList.get(i), commonTagList);
		}
		return articleList;
	}
	
	/**
	 * 供cleanCommonTag方法调用，用来完成tag的处理
	 * @param articleInfo ArticleInfo类的实例
	 * @param commonTagList CommonTag类的ArrayList容器
	 */
	void deal(ArticleInfo articleInfo, ArrayList<CommonTag> commonTagList){
		if (articleInfo == null)
			return;
		if (articleInfo.getArticleTags().equals("") || articleInfo.getArticleTags().length() == 0)
			return;
		String[] oldTags = articleInfo.getArticleTags().split(",");
		articleInfo.setArticleTags("");
		for (int i = 0; i < oldTags.length; ++i){
			if (jaccard(oldTags[i], commonTagList) - threshold < edge){
				articleInfo.addArticleTags(oldTags[i]);
			}
		}
		articleInfo.deleteCommaOfArticleTags();
	}
	
	/**
	 * 求jaccard相似度方法，供deal方法用来求jaccard相似度
	 * @param tag String 要比较的标签
	 * @param commonTagList 通用标签表
	 * @return jaccard相似度，为double值
	 */
	double jaccard(String tag, ArrayList<CommonTag> commonTagList){
		ArrayList<String> splitTags = split(tag);
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < commonTagList.size(); ++i)
			set.add(commonTagList.get(i).getCommonTag());
		double count = 0;
		for (int i = 0; i < splitTags.size(); ++i){
			if (set.contains(splitTags.get(i)))
				++count;
			set.add(splitTags.get(i));
		}
		return count / set.size();
	}
	
	/**
	 * 逆向最大匹配分词算法，词库较小（10万多一点）。供jaccard方法分词后求两集合相似度。
	 * @param tag 要分词的标签，String类型，原来只打算分tag后来也用来分文章
	 * @return 返回String类的ArrayList容器
	 */
	public ArrayList<String> split(String tag){
		if (lexicon == null){
			File file = new File("百度分词词库.txt");
			try {
				lexicon = new ArrayList<String>();
				Scanner input = new Scanner (file);
				while(input.hasNextLine()){
					lexicon.add(input.nextLine().split(" ")[1]);
				}
				input.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
		ArrayList<String> result = new ArrayList<String>();
		if (!tag.matches("^[\u4e00-\u9fa5]+$")){
			result.add(tag);
			return result;
		}
		int tagLength = tag.length();
		if (tagLength == 1 || lexicon.contains(tag)){
			result.add(tag);
			return result;
		}
		int max = tagLength < 10 ? tagLength - 1 : 10;
		for (int i = tagLength; i > 0;){
			String subString = tag.substring(i-max < 0 ? 0 : i-max, i);
			if (lexicon.contains(subString)){
				result.add(subString);
				i = i - max;
			}else {
				int j = subString.length();
				for (; j > 0; --j){
					String temp = tag.substring(i-j < 0 ? 0 : i-j, i);
					if (lexicon.contains(temp) || temp.length() == 1){ //==1是为了防止词库没有陷入死循环
						result.add(temp);
						break;
					}
				}
				i = i - j;
			}
		}
		return result;
	}
}