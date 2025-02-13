package org.alexander.project.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alexander.project.service.VideoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "video_methods")
@RestController
@RequestMapping("/v1/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService service;

    @PostMapping
    public String uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam String name) {
        service.uploadVideo(name, file);
        return "Video uploaded!";
    }

    @GetMapping(produces = {"video/mp4", "video/webm"})
    public InputStreamResource getVideo(@RequestParam String name) {
        return new InputStreamResource(service.getVideo(name));
    }


    @GetMapping(value = "/stream", produces = {"video/mp4", "video/webm"})
    public ResponseEntity<InputStreamResource> streamVideo(@RequestParam String name, @RequestHeader(value = "Range", required = false) String rangeHeader) {
        return service.streamVideo(name, rangeHeader);
    }
}
