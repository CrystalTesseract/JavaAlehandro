package org.alexander.project.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alexander.project.service.VideoService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "video_methods")
@RestController
@RequestMapping("/v1/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService service;

    @PostMapping
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        service.uploadVideo(file);
        return "Video uploaded!";
    }

    @GetMapping(produces = {"video/mp4", "video/webm"})
    public Resource getVideo(@RequestParam int id) {
        return service.getVideo(id);
    }
}
