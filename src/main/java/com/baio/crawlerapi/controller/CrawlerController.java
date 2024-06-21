package com.baio.crawlerapi.controller;

import java.util.List;

import com.baio.crawlerapi.dto.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baio.crawlerapi.dto.MagnetsLinksDto;
import com.baio.crawlerapi.service.CrawlerService;

@RestController
public class CrawlerController {

    @GetMapping("/catalog")
    public Page getSites(@RequestParam(defaultValue="1", required=false) Integer page){
        CrawlerService crawlerService = new CrawlerService();
        return crawlerService.crawler("http://comando.la/page/"+page);
    }

    @GetMapping("/magnet")
    public MagnetsLinksDto getMagnet(@RequestParam String url){
        CrawlerService crawlerService = new CrawlerService();

        return crawlerService.getMagnet(url);
    }

    @GetMapping("/search")
    public  List getAllBySearch(
            @RequestParam(required=false) Integer page
            ,@RequestParam String search){
        CrawlerService crawlerService = new CrawlerService();

        String url = (page == null) ? "https://comando.la/?s="+search : "https://comando.la/page/" + page + "/?s=" + search;

        return crawlerService.crawler(url).content();
    }


}
