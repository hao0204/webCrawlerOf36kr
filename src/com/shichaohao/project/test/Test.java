package com.shichaohao.project.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.http.impl.client.cache.CacheConfig;

import com.shichaohao.project.data.CleanTags;
import com.shichaohao.project.data.CountTags;
import com.shichaohao.project.data.GetArticleData;
import com.shichaohao.project.entity.ArticleInfo;

public class Test {
	
	public static void main(String[] args) {
//		ArrayList<ArticleInfo> a = GetArticleData.getArticleData("2015-07-01 00:00:00");
		CleanTags c = new CleanTags();
		ArticleInfo ar = new ArticleInfo();
		ar.setArticleTags("国内创业公司");
		ArrayList<ArticleInfo> a =new ArrayList<ArticleInfo>();
		a.add(ar);
		a = c.cleanCommonTag(a);
		System.out.println(CountTags.getHottestTag(CountTags.sum(a)));
//		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(CountTags.sum(a).entrySet());
//		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
//			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
//				return o1.getValue().compareTo(o2.getValue());
//			}
//		});
//		Collections.reverse(list);
		
	}
}