package org.alexander.project.service;

import lombok.RequiredArgsConstructor;
import org.alexander.project.cantremembernameofthispackage.GeneralError;
import org.alexander.project.entity.Video;
import org.alexander.project.repository.spec.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository db;

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    public void save(Video video) {
        db.save(video);
    }

    public Resource getVideo(int id) {
        Video video = db.findById(id).orElseThrow(() -> new GeneralError("400", "Invalid video id"));
        Path filePath = Paths.get(video.getPath());

        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new GeneralError("400", "Invalid video path");
        }
    }

    public void uploadVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(fileUploadDir, fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new GeneralError("400", "Bad file");
        }

        db.save(new Video(fileName, filePath.toString()));
    }
}
