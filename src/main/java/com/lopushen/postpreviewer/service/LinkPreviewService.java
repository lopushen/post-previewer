package com.lopushen.postpreviewer.service;

import com.lopushen.postpreviewer.dto.LinkPreviewDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LinkPreviewService {

    public LinkPreviewDTO getLinkPreview(String link) {
        assert link != null;
        LinkPreviewDTO result = new LinkPreviewDTO();
        try {
            Document document = Jsoup.connect(link).get();
            result.setPictureLink(findOpenGraphPropertyValue(document, "image"));
            result.setTitle(findOpenGraphPropertyValue(document, "title"));
            result.setDescription(findOpenGraphPropertyValue(document, "description"));
            result.setLink(link);
        } catch (IOException e) {
            // TODO
        }
        return result;
    }

    private String findOpenGraphPropertyValue(Document document, String propertyName) {
        String tag = null;
        String cssQuery = "meta[property='og:" + propertyName + "']";
        Elements elements = document.select(cssQuery);

        if (elements != null && elements.size() >= 1) {
            tag = elements.first().attr("content");
        }

        return tag;
    }
}
