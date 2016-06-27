package com.shichaohao.project.entity;

/**
 * 与数据库article表一致
 * @author Think
 *
 */
public class ArticleInfo {
	
	private String articleName = ""; //文章标题
	private String articleAuthor = ""; //文章作者
	private String articleDate = ""; //发布文章日期
	private String articleTags = ""; //文章标签
	private String articleLink = ""; //文章链接
	
	public String getArticleName(){
		return articleName;
	}
	public void setArticleName(String articleName){
		this.articleName = articleName;
	}
	
	public String getArticleAuthor(){
		return articleAuthor;
	}
	public void setArticleAuthor(String articleAuthor){
		this.articleAuthor = articleAuthor;
	}
	
	public String getArticleDate(){
		return articleDate;
	}
	public void setArticleDate(String articleDate){
		this.articleDate = articleDate;
	}
	
	public String getArticleTags(){
		return articleTags;
	}
	public void setArticleTags(String articleTags){
		this.articleTags = articleTags;
	}
	/**
	 * 向已有标签添加标签
	 * @param articleTags String类型
	 */
	public void addArticleTags(String articleTags){
		this.articleTags += (articleTags + ",");
	}
	/**
	 * 删除最后的逗号
	 */
	public void deleteCommaOfArticleTags(){
		if (articleTags != null && articleTags.length() != 0)
			articleTags = articleTags.substring(0, articleTags.length() - 1);
	}
	
	public String getArticleLink(){
		return articleLink;
	}
	public void setArticleLink(String articleLink){
		this.articleLink = articleLink;
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		else if (o instanceof ArticleInfo){
			ArticleInfo a = (ArticleInfo)o;
			if (this.articleName.equals(a.articleName) 
					&& this.articleAuthor.equals(a.articleAuthor)
					&& this.articleDate.equals(a.articleDate) 
					&& this.articleLink.equals(a.articleLink)
					&& this.articleTags.equals(a.articleTags))
				return true;
		}
		return false;
	}
}
