package com.shichaohao.project.entity;

/**
 * 通用行业对应的Bean
 * @author Think
 *
 */
public class CommonTag {
	
	private String commonTag; //通用标签
	
	public String getCommonTag(){
		return commonTag;
	}
	public void setCommonTag(String commonTag){
		this.commonTag = commonTag;
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		else if (o instanceof CommonTag){
			if (this.commonTag.equals(((CommonTag) o).commonTag))
				return true;
		}
		return false;
	}
}
