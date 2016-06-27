package com.shichaohao.project.capture;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shichaohao.project.dao.DB;
import com.shichaohao.project.entity.ArticleContent;
import com.shichaohao.project.entity.ArticleInfo;

/**
 * 爬取36氪网站上的资源
 * @author Think
 *
 */
public class CaptureWeb {
	
	/**
	 * 抓取36氪网站上的内容，包括文章名称、文章作者、文章发布日期、文章标签、文章链接
	 */
	public static void captureBase() {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(100000)
				.setConnectTimeout(100000)
				.setConnectionRequestTimeout(100000)
				.build();
		String url = "http://36kr.com/";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1); //爬取一个月前发布的文章
		String limit = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(cal.getTime()); 
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(config);
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200){
				ArrayList<ArticleInfo> array = new ArrayList<>();
				Label:do{
					String result = EntityUtils.toString(response.getEntity());
					Document doc = Jsoup.parse(result);
					Elements eles = doc.select("div.article-list > div > article");
					Element more = doc.select("a#info_flows_next_link").first();
					for (int i = 0; i < eles.size(); ++i){
						ArticleInfo articleInfo = new ArticleInfo();
						doc = Jsoup.parse(eles.get(i).toString());
						Element ele = doc.select("div.desc > a.info_flow_news_title").first();
						if (ele != null && ele.hasText())
							articleInfo.setArticleName(ele.text());
						else
							continue;
						if (ele != null && ele.hasAttr("href"))
							articleInfo.setArticleLink("http://36kr.com" + ele.attr("href"));
						ele = doc.select("div.desc > div.author > a > span.name").first();
						if (ele != null && ele.hasText())
							articleInfo.setArticleAuthor(ele.text());
						ele = doc.select("div.desc > div.author > span.time > [title]").first();
						if (ele != null && ele.hasAttr("title")){
							String date = ele.attr("title");
							articleInfo.setArticleDate(date.substring(0, date.length() - 6));
						}
						if (articleInfo.getArticleDate().compareTo(limit) < 0)
							break Label;
						httpGet = new HttpGet(articleInfo.getArticleLink());
						response = httpClient.execute(httpGet);
						result = EntityUtils.toString(response.getEntity());
						doc = Jsoup.parse(result);
						Elements tags = doc.select("section.single-post-tags > span.tag-item");
						for (int j = 0; j < tags.size(); ++j)
							if (tags.get(j).hasText())
								articleInfo.addArticleTags(tags.get(j).text());
						articleInfo.deleteCommaOfArticleTags();
						array.add(articleInfo);
						Thread.sleep(1000);
					}
					if (more.hasAttr("href")){
						httpGet = new HttpGet("http://36kr.com" + more.attr("href"));
						response = httpClient.execute(httpGet);
						Thread.sleep(1000);
					}else
						break Label;
				}while(true);
				String sql = "insert ignore into articleBase(article_name, article_author, article_date, article_tags, article_link) values(?, ?, ?, ?, ?)";
				DB.insertIntoArticleBase(sql, array);
			}else
				System.out.println("响应返回不成功");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public static void captureContent(ArrayList<ArticleInfo> articleList, String tag){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(100000)
				.setConnectTimeout(100000)
				.setConnectionRequestTimeout(100000)
				.build();
		ArrayList<ArticleContent> array = new ArrayList<ArticleContent>();
		for (int i = 0; i < articleList.size(); ++i){
			if (articleList.get(i).getArticleTags().contains(tag)){
				String url = articleList.get(i).getArticleLink();
				HttpGet httpGet = new HttpGet(url);
				httpGet.setConfig(config);
				try {
					response = httpClient.execute(httpGet);
					String result = EntityUtils.toString(response.getEntity());
					Document doc = Jsoup.parse(result);
					Elements eles = doc.select("section.article");
					for (int j = 0; j < eles.size(); ++j){
						String content = eles.get(j).text();
						ArticleContent articleContent = new ArticleContent();
						articleContent.setContent(content);
						articleContent.setTag(tag);
						articleContent.setDate(articleList.get(i).getArticleDate());
						array.add(articleContent);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		DB.insertIntoArticleContent(array);
	}
}
