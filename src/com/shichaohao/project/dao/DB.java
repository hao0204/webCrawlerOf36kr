package com.shichaohao.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.shichaohao.project.entity.ArticleContent;
import com.shichaohao.project.entity.ArticleInfo;
import com.shichaohao.project.entity.CommonTag;

/**
 * 连接数据库，目前只有插入函数
 * @author Think
 *
 */
public class DB {
	
	/**
	 * 向数据库article表插入数据
	 * @param sql sql语句
	 * @param array ArticleInfo类的容器
	 */
	public static void insertIntoArticleBase(String sql, ArrayList<ArticleInfo> array) {

		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DBConnUtil.getConn();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < array.size(); ++i){
				ArticleInfo articleInfo = array.get(i);
				ps.setString(1, articleInfo.getArticleName());
				ps.setString(2, articleInfo.getArticleAuthor());
				ps.setString(3, articleInfo.getArticleDate());
				ps.setString(4, articleInfo.getArticleTags());
				ps.setString(5, articleInfo.getArticleLink());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnUtil.closeAll(conn, ps, null);
		}
	}
	
	/**
	 * 向数据库查询文章数据
	 * @param date 从某个日期开始
	 * @return 返回Article类的ArrayList容器
	 */
	public static ArrayList<ArticleInfo> selectArticle(String date){
		ArrayList<ArticleInfo> array = new ArrayList<ArticleInfo>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnUtil.getConn();
			String sql = "select * from articleBase where article_date > \"" + date + "\"";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ArticleInfo article = new ArticleInfo();
				article.setArticleName(rs.getString("article_name"));
				article.setArticleAuthor(rs.getString("article_author"));
				article.setArticleDate(rs.getString("article_date"));
				article.setArticleTags(rs.getString("article_tags"));
				article.setArticleLink(rs.getString("article_link"));
				array.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnUtil.closeAll(conn, ps, rs);
		}
		return array;
	}
	
	/**
	 * 向数据库查询通用标签数据
	 * @return 返回CommonTag类的ArrayList容器
	 */
	public static ArrayList<CommonTag> selectCommonTag(){
		ArrayList<CommonTag> array = new ArrayList<CommonTag>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnUtil.getConn();
			String sql = "select * from commonTag";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CommonTag commonTag = new CommonTag();
				commonTag.setCommonTag(rs.getString("common_tag"));
				array.add(commonTag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnUtil.closeAll(conn, ps, rs);
		}
		return array;
	}
	
	/**
	 * 向数据库articleContent表插入数据
	 * @param sql sql语句
	 * @param array ArticleContent类的容器
	 */
	public static void insertIntoArticleContent(ArrayList<ArticleContent> array) {

		Connection conn = null;
		PreparedStatement ps = null;
		String sql1 = "delete from articleContent where tag = ?";
		String sql2 = "insert into articleContent(tag, content, date) values(?, ?, ?)";
		try {
			conn = DBConnUtil.getConn();
			ps = conn.prepareStatement(sql1);
			ps.setString(1, array.get(0).getTag());
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			for (int i = 0; i < array.size(); ++i){
				ArticleContent articleContent = array.get(i);
				ps.setString(1, articleContent.getTag());
				ps.setString(2, articleContent.getContent());
				ps.setString(3, articleContent.getDate());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnUtil.closeAll(conn, ps, null);
		}
	}
	
	/**
	 * 向数据库查询特定标签对应的文章内容数据
	 * @return 返回ArticleContent类的ArrayList容器
	 */
	public static ArrayList<ArticleContent> selectArticleContent(String tag){
		ArrayList<ArticleContent> array = new ArrayList<ArticleContent>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnUtil.getConn();
			String sql = "select * from articleContent where tag = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, tag);
			rs = ps.executeQuery();
			while(rs.next()){
				ArticleContent articleContent = new ArticleContent();
				articleContent.setTag(rs.getString("tag"));
				articleContent.setContent(rs.getString("content"));
				articleContent.setDate(rs.getString("date"));
				array.add(articleContent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnUtil.closeAll(conn, ps, rs);
		}
		return array;
	}
}
