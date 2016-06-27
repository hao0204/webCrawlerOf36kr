package com.shichaohao.project.entity;

public class ArticleContent {
	
	private String content;
	private String tag;
	private String date;
	private String attitude;
	
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	public String getTag(){
		return tag;
	}
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	
	public String getAttitude(){
		return attitude;
	}
	public void setAttitude(String attitude){
		this.attitude = attitude;
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		else if (o instanceof ArticleContent){
			if (this.content.equals(((ArticleContent) o).content)
					&& this.tag.equals(((ArticleContent) o).tag))
				return true;
		}
		return false;
	}
}
