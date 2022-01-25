package com.codehunter.cafefnewsnotification.util;

import com.codehunter.cafefnewsnotification.model.NewsSDO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JsoupUtil {

    public static String parsePageHeaderInfo(String urlStr) throws Exception {

        StringBuilder sb = new StringBuilder();
        Connection con = Jsoup.connect(urlStr);

        /* this browseragant thing is important to trick servers into sending us the LARGEST versions of the images */
//        con.userAgent(Constants.BROWSER_USER_AGENT);
        Document doc = con.get();

        String text;
        Elements metaOgTitle = doc.select("meta[property=og:title]");
        text = metaOgTitle.attr("content");

        String imageUrl;
        Elements metaOgImage = doc.select("meta[property=og:image]");
        imageUrl = metaOgImage.attr("content");

        sb.append("<img src='");
        sb.append(imageUrl);
        sb.append("' align='left' hspace='12' vspace='12' width='150px'>");

        sb.append(text);

        return sb.toString();
    }

    public static List<NewsSDO> getHotNewsUrl(String url) {
        List<NewsSDO> newsSDOList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Connection con = Jsoup.connect(url);
        /* this browseragant thing is important to trick servers into sending us the LARGEST versions of the images */
        con.userAgent("Chrome");
        try {
            Document doc = con.get();
            Elements newsList = doc.select(".foreverblock > .viewport > .expandable");
            for (Element element : newsList) {
                String dataId;
                dataId = element.attr("data-id");
//                System.out.println("data-id: " + dataId);

                Elements timeElement = element.select(".nv-details > div > span");
                String time = timeElement.text();
//                System.out.println("time: " + time);

                Elements titleElement = element.select(".news-title");
                String title = titleElement.text();
                String urlLink = titleElement.attr("href");
//                System.out.println("title: " + title);
//                System.out.println("url: " + urlLink);


                Elements detailElement = element.select(".abs");
                String detail = detailElement.text();
//                System.out.println("detail: " + detail);
                newsSDOList.add(new NewsSDO(dataId, time, title, urlLink, detail));
            }
            return newsSDOList;
        } catch (IOException e) {
            e.printStackTrace();
            return newsSDOList;
        }
    }

    public static void main(String[] args) {
        getHotNewsUrl("https://cafef.vn/doc-nhanh.chn");
    }
}
