package com.lopushen.postpreviewer.rest;

import com.lopushen.postpreviewer.dto.LinkPreviewDTO;
import com.lopushen.postpreviewer.dto.LinkPreviewRequestDTO;
import com.lopushen.postpreviewer.service.LinkPreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkPreviewResource {

    @Autowired
    private LinkPreviewService linkPreviewService;

    @PostMapping("/link-preview")
    public LinkPreviewDTO retrieveLinkPreview(@RequestBody LinkPreviewRequestDTO request) {
        return linkPreviewService.getLinkPreview(request.getLink(), request.isTwitter());
    }
}
