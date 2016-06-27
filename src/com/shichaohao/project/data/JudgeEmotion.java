package com.shichaohao.project.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.shichaohao.project.entity.ArticleContent;

public class JudgeEmotion {
	
	static ArrayList<String> negativeLexicon = null;
	static ArrayList<String> positiveLexicon = null;
	
	public static void judgeEmotion(ArrayList<ArticleContent> articleContents){
		for (int i = 0; i < articleContents.size(); ++i){
			String attitude = "";
			CleanTags cleanTags = new CleanTags();
			StringBuilder sb = new StringBuilder();
			String content = articleContents.get(i).getContent();
			for (int j = 0; j < content.length(); ++j){
				char c = content.charAt(j);
				String temp = String.valueOf(c);
				if (temp.matches("^[\u4e00-\u9fa5]+$"))
					sb.append(temp);
			}
//			System.out.println(articleContents.get(i).getContent().substring(0,10) + cleanTags.split(sb.toString()));
			ArrayList<String> splitContents = cleanTags.split(sb.toString());
			attitude = judgeNegativeOrPositive(splitContents);
			articleContents.get(i).setAttitude(attitude);
//			System.out.println(articleContents.get(i).getAttitude() + " " +articleContents.get(i).getContent().substring(0, 10));
		}
		Collections.sort(articleContents, new DateComparator());
		for(int i = 0; i < articleContents.size(); ++i)
			System.out.println(articleContents.get(i).getDate()+":"+articleContents.get(i).getAttitude());
	}
	
	static class DateComparator implements Comparator<Object> {  
        public int compare(Object object1, Object object2) {
        	ArticleContent a1 = (ArticleContent) object1;
        	ArticleContent a2 = (ArticleContent) object2;  
            return a1.getDate().compareTo(a2.getDate());
        }  
    }  
	
	static String judgeNegativeOrPositive(ArrayList<String> splitContents){
		
		if (negativeLexicon == null){
			negativeLexicon = new ArrayList<String>();
			File negativeFile = new File("ntusd-negative.txt");
			Scanner input;
			try {
				input = new Scanner(negativeFile);
				while(input.hasNextLine()){
					negativeLexicon.add(input.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (positiveLexicon == null){
			positiveLexicon = new ArrayList<String>();
			File positiveFile = new File("ntusd-positive.txt");
			Scanner input;
			try {
				input = new Scanner(positiveFile);
				while(input.hasNextLine()){
					positiveLexicon.add(input.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		int negative = 0, positive = 0;
		for (String str: splitContents){
			if (negativeLexicon.contains(str))
				++negative;
			if (positiveLexicon.contains(str))
				++positive;
		}
		if (negative > positive)
			return "消极";
		else if (negative == positive)
			return "中立";
		else
			return "积极";
	}		
}
