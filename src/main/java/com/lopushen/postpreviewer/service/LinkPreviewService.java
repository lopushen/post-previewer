package com.lopushen.postpreviewer.service;

import com.lopushen.postpreviewer.dto.LinkPreviewDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LinkPreviewService {

    public LinkPreviewDTO getLinkPreview(String link, boolean isTwitter) {
        assert link != null;
        LinkPreviewDTO result = new LinkPreviewDTO();
        try {
            Document document = Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();
            if (isTwitter) {
                enrichWithTwitterCards(result, document);
            } else {
                enrichWithOpenGraphTags(result, document);
            }

            result.setLink(link);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void enrichWithOpenGraphTags(LinkPreviewDTO result, Document document) {
        result.setPictureLink(findOpenGraphPropertyValue(document, "image"));
        result.setTitle(findOpenGraphPropertyValue(document, "title"));
        result.setDescription(findOpenGraphPropertyValue(document, "description"));
    }

    private String findOpenGraphPropertyValue(Document document, String propertyName) {
        String tag = null;
        String cssQuery = "meta[property=og:" + propertyName + "]";
        Elements elements = document.select(cssQuery);

        if (elements != null && elements.size() >= 1) {
            tag = elements.first().attr("content");
        }

        return tag;
    }

    private void enrichWithTwitterCards(LinkPreviewDTO result, Document document) {
        result.setPictureLink(findTwitterPropertyValue(document, "image"));
        result.setTitle(findTwitterPropertyValue(document, "title"));
        result.setDescription(findTwitterPropertyValue(document, "description"));
    }

    private String findTwitterPropertyValue(Document document, String property) {
        String tag = null;
        String cssQuery = "meta[name=twitter:" + property + "]";
        Elements elements = document.select(cssQuery);

        if (elements != null && elements.size() >= 1) {
            tag = elements.first().attr("content");
        }

        return tag;
    }
}
