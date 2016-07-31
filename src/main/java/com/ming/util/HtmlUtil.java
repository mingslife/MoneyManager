package com.ming.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML工具类<br />
 * 参数资料： <a
 * href="http://blog.sina.com.cn/s/blog_53b91ff30100kbv2.html">http://blog
 * .sina.com.cn/s/blog_53b91ff30100kbv2.html</a>
 * 
 * @author Ming
 * @date 2015-10-18
 */
public class HtmlUtil {
	public static String removeHTMLTags(String str, String tags) {
		if (str == null)
			return null;
		if (tags == null)
			return str;
		String regx = "(</?)(" + tags + ")([^>]*>)";
		Matcher matcher;
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);// 不区分大小写
		// 此处需要循环匹配，防止恶意构造的字符串
		while ((matcher = pattern.matcher(str)).find()) {
			str = matcher.replaceAll("");
		}

		return str;
	}

	public static String removeEvents(String content) {

		String regx = "(<[^<]*)(on\\w*\\x20*=|javascript:)";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);// 不区分大小写
		Matcher matcher;
		String ts = content;
		// 此处需要循环匹配，防止恶意构造的字符串如 onclick=onclick=xxx
		while ((matcher = pattern.matcher(ts)).find()) {
			ts = matcher.replaceAll("$1");
		}
		return ts;
	}

	public static String makeSafe(String content) {
		return removeEvents(removeHTMLTags(
				content,
				"html|body|head|title|style|video|canvas|script|iframe|frameset|frame|object|embed|xml|input|button|textarea|select|pre|option|plaintext|form"));
	}

	public static String makeSafe(String content, String tags) {
		if (tags == null)
			return makeSafe(content);
		return removeEvents(removeHTMLTags(content, tags));
	}

	/**
	 * 获取网页内容<br>
	 * 参考：<a
	 * href="http://www.cnblogs.com/langlang/archive/2010/12/10/1901744.html"
	 * >http://www.cnblogs.com/langlang/archive/2010/12/10/1901744.html</a>
	 * 
	 * @param url
	 *            网页URL
	 * @return 网页内容
	 */
	public static String getContent(String url) {
		try {
			StringBuilder stringBuilder = new StringBuilder();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(url).openStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}

			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除HTML文本中的所有HTML标签<br>
	 * 参考：<a href="http://www.cnblogs.com/newsouls/p/3995394.html">http://www.
	 * cnblogs.com/newsouls/p/3995394.html</a>
	 * 
	 * @param htmlStr HTML文本
	 * @return 去除HTML标签的文本
	 */
	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}
	
	public static String substring(String content, int length) {
		if (content == null)
			return "";
		content = content.replaceAll("\n", " ");
		if (content.length() <= length) {
			return content;
		} else {
			return content.substring(0, length);
		}
	}
	
	public static String formatHtmlSpecialChars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
}
