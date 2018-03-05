package test;

import java.util.ArrayList;

import com.zxl.javaBean.UrlData;
import com.zxl.reverseIndex.ReverseIndex;

public class SearchTest {

	public static void main(String[] args) {
		ArrayList<UrlData> searchResults = ReverseIndex.reverseIndex("reverseIndex", "鸡蛋", true);
		for (UrlData urlData : searchResults) {
			System.out.println(urlData.getUrl());
			System.out.println(urlData.getTitle());
			System.out.println(urlData.getHtml());
			System.out.println(urlData.getRank());
		}
	}
}
