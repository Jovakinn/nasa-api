package com.example.coolapp.controller;

import com.example.coolapp.dto.PictureSubmission;
import com.example.coolapp.dto.PictureSubmissionRequest;
import com.example.coolapp.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    public void submit(@RequestBody PictureSubmission pictureSubmission, HttpServletRequest httpServletRequest) {
        var request = new PictureSubmissionRequest(pictureSubmission, httpServletRequest.getRemoteAddr());
        pictureService.submit(request);
    }
}
