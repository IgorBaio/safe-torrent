package com.baio.crawlerapi.controller;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baio.crawlerapi.dto.MagnetsLinksDto;
import com.baio.crawlerapi.dto.Page;
import com.baio.crawlerapi.service.CrawlerService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://safe-torrent-front.onrender.com"})
public class CrawlerController {

    @GetMapping("/catalog")
    public Page getSites(@RequestParam (required=false) String search ,@RequestParam(defaultValue="1", required=false) Integer page){
        CrawlerService crawlerService = new CrawlerService();

        String queryParam = String.valueOf(page);

        if(search != null){
            queryParam += "/?s="+search;
        }

        return crawlerService.crawler("https://comando.la/page/"+queryParam);
    }

    @GetMapping("/magnet")
    public MagnetsLinksDto getMagnet(@RequestParam String url) throws XPathExpressionException{
        CrawlerService crawlerService = new CrawlerService();

        return crawlerService.getMagnet(url);
    }

}
