package com.baio.crawlerapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baio.crawlerapi.dto.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baio.crawlerapi.dto.MagnetDto;
import com.baio.crawlerapi.dto.MagnetsLinksDto;
import com.baio.crawlerapi.dto.MoviesCatalogDto;

public class CrawlerService {
    public static Page crawler(String url) {

        List<MoviesCatalogDto> moviesCatalogDto = new ArrayList<MoviesCatalogDto>();
        try {
            // Conecta e faz o parsing do HTML da página
            Document doc = Jsoup.connect(url).get();

            // Seleciona todos os elementos <a> com atributos href
            Elements linksMovies = doc.select(".entry-header h2.entry-title a[href]");
            Elements linksPages = doc.select("a[href]");
            Elements articles = doc.select("article div.entry-content img");

            List<String> listPages = new ArrayList<String>();

            // Itera sobre os links e os imprime
            for (Element link : linksPages) {
                String urlPage = link.attr("href");
                if (urlPage.contains("page")) {
                    listPages.add(urlPage);
                }

            }

            for(int i = 0; i< linksMovies.size(); i++){
                Element link = linksMovies.get(i);
                Element article = articles.get(i);
                String titleMovie = link.text();
                String linkMovie = link.attr("href");
                String urlImg = article.attr("src");

                MoviesCatalogDto movieCatalogDto = new MoviesCatalogDto(titleMovie, urlImg, linkMovie);
                moviesCatalogDto.add(movieCatalogDto);

            }

            String regex = "\\b\\d+\\b";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            System.out.println(listPages.get( listPages.size() -1));

            Matcher matcher = pattern.matcher(listPages.get(0));

            Integer total = 0;
            if (matcher.find()){
                total = Integer.valueOf(matcher.group());
            }

            System.out.println(total);

            return new Page<>(listPages.size() ,moviesCatalogDto.size() , moviesCatalogDto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MagnetsLinksDto getMagnet(String url) {

        try {
            // Conecta e faz o parsing do HTML da página
            Document doc = Jsoup.connect(url).get();

            // Seleciona todos os elementos <a> com atributos href
            Elements linksTitle = doc.select("title");
            String title = linksTitle.get(0).text();

            Elements linksMagnetsButtons = doc.select(".customButton");
            List<MagnetDto> magnets = new ArrayList<>();

            if (linksMagnetsButtons.size() > 0) {
                extractMagnetUrl(linksMagnetsButtons, magnets);
            } else {
                Elements linksMagnetsLinks = doc.select("p a[href]");
                if (linksMagnetsLinks.size() > 0)
                    extractMagnetUrl(linksMagnetsLinks, magnets);
            }

            MagnetsLinksDto magnetsLinksDto = new MagnetsLinksDto(title, magnets);

            return magnetsLinksDto;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void extractMagnetUrl(Elements linksMagnets, List<MagnetDto> magnets) {
        for (Element link : linksMagnets) {
            String linkMovie = link.attr("href");
            String titleButton = link.text();
            if (linkMovie.contains("magnet:")) {
                if (linkMovie.toLowerCase().contains("dublado.eng"))
                    titleButton += " - Lengendado";
                else if (linkMovie.toLowerCase().contains("dublado"))
                    titleButton += " - Dublado";
                else if (linkMovie.toLowerCase().contains("dual"))
                    titleButton += " - Dual Audio";
                else
                    titleButton += " - Legendado";

                magnets.add(new MagnetDto(titleButton, linkMovie));

            }

        }
    }

}
