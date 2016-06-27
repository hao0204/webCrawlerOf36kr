package com.shichaohao.project.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shichaohao.project.entity.ArticleInfo;

/**
 * 标签计算的类
 * @author Think
 *
 */
public class CountTags {
	
	/**
	 * 求各个标签的和
	 * @param articleList ArticleInfo的表
	 * @return 返回（String, Integer）类型的键值对
	 */
	public static HashMap<String, Integer> sum(ArrayList<ArticleInfo> articleList){
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		for (int i = 0; i < articleList.size(); ++i){
			ArticleInfo articleInfo = articleList.get(i);
			String str[] = articleInfo.getArticleTags().split(",");
			for (int j = 0; j < str.length; ++j){
				if (count.containsKey(str[j]))
					count.put(str[j], count.get(str[j]).intValue() + 1);
				else
					count.put(str[j], 1);
			}
		}
		count.remove("");
		return count;
	}
	
	/**
	 * 用来求热度最高的标签
	 * @param count 所有标签计数的（String, Integer）键值对
	 * @return 最高热度的标签
	 */
	public static String getHottestTag(HashMap<String, Integer> count){
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(count.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		Collections.reverse(list);
		if (list.size() == 0)
			return "无";
		return list.get(0).getKey();
	}
}
