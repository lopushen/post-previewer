package com.lopushen.postpreviewer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LinkPreviewRequestDTO {
    @NotNull
    private String link;
}
