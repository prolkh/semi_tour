package com.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {
	/**
	 * @param rows			한 화면에 표시할 데이터 수
	 * @param dataCount		전체 데이터 개수
	 * @return				총 페이지 수
	 */
	public int pageCount(int rows, int dataCount) {
		if(dataCount<=0)
			return 0;
		
		return dataCount / rows + (dataCount % rows > 0 ? 1 : 0);
	}
	
	/**
	 * 페이징 처리(GET 방식)
	 * @param current_page		현재 페이지
	 * @param total_page		전체 페이지
	 * @param list_url			링크를 설정할 URL
	 * @return					페이지 처리 결과
	 */
	public String paging(int current_page, int total_page, String list_url) {
		StringBuffer sb=new StringBuffer();
		
		int numPerBlock=10;
		int currentPageSetup;
		int n;
		int page;
		
		if(current_page<1 || total_page < 1 )
			return "";
		
		if(list_url.indexOf("?")!=-1)
			list_url+="&";
		else
			list_url+="?";
		
		currentPageSetup=(current_page/numPerBlock)*numPerBlock;
		if(current_page%numPerBlock==0)
			currentPageSetup=currentPageSetup-numPerBlock;
		
		sb.append("<div id='paginate'>");
		n=current_page-numPerBlock;
		if(total_page > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='"+list_url+"page=1'>처음</a>");
			sb.append("<a href='"+list_url+"page="+n+"'>이전</a>");
		}
		
		// 바로가기
		page=currentPageSetup+1;
		while(page<=total_page && page <=(currentPageSetup+numPerBlock)) {
			if(page==current_page) {
				sb.append("<span class='curBox'>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"' class='numBox'>"+page+"</a>");
			}
			page++;
		}
		//다음(10페이지 후), 마지막 페이지
		n=current_page+numPerBlock;
		if(n>total_page) n=total_page;
		if(total_page-currentPageSetup>numPerBlock) {
			sb.append("<a href='"+list_url+"page="+n+"'>다음</a>");
			sb.append("<a href='"+list_url+"page="+total_page+"'>끝</a>");
		}
		sb.append("</div>");
		
		return sb.toString();
	}
	
	/**
	 * 특수문자를 html 문자로 변경, 엔터를 <br>로 변경
	 * @param str 		변경할 문자열 
	 * @return			변경한 결과
	 */
	public String htmlSymbols(String str) {
		if(str==null || str.length()==0)
			return "";
		
		str=str.replaceAll("&", "&amp;");
		str=str.replaceAll("\"", "&quot;");
		str=str.replaceAll(">", "&gt;");
		str=str.replaceAll("<", "&lt;");

		str=str.replaceAll("\n", "<br>");
		str=str.replaceAll("\\s", "&nbsp;");  // \\s가 엔터도 변경하므로 \n보다 뒤에

		return str;
	}
	
	public String replaceAll(String str, String oldStr, String newStr) throws Exception {
		if(str == null)
			return "";
		
		Pattern p = Pattern.compile(oldStr);
		
		// 입력 문자열과 함께 매쳐 클래스 생성
		Matcher m = p.matcher(str);
		
		StringBuffer sb = new StringBuffer();
		//패턴과 일치하는 문자열을 newStr로 교체해가면서 새로운 문자열을 만든다.
		while(m.find()) {
			m.appendReplacement(sb, newStr);
		}
		
		// 나머지 부분을 새로운 문자열 끝에 덧붙인다.
		m.appendTail(sb);
		
		return sb.toString();
	}
	
	/**
	 * 이메일의 유효성을 검사하는 함수
	 * @param email			입력된 이메일
	 * @return				조건을 만족하면 true, 만족하지 못하면 false
	 */
	public boolean isValidEmail(String email) {
		if(email==null) return false;
		boolean b = Pattern.matches(
				"[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",
				email.trim());
		return b;
	}
}
