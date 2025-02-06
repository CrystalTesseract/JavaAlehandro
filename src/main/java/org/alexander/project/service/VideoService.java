package org.alexander.project.service;

import lombok.RequiredArgsConstructor;
import org.alexander.project.entity.Video;
import org.alexander.project.exception.GeneralError;
import org.alexander.project.repository.spec.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ResponseEntity<Resource> streamVideo(int id, String rangeHeader) {
        Video video = db.findById(id).orElseThrow(() -> new GeneralError("400", "Invalid video id"));
        Path filePath = Paths.get(video.getPath());

        try {
            File videoFile = filePath.toFile();
            long fileSize = videoFile.length();
            HttpHeaders headers = new HttpHeaders();
            Resource resource = new UrlResource(filePath.toUri());

            if (rangeHeader == null) {
                // Если Range-запроса нет, отправляем всё видео целиком
                headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .body(resource);
            }

            long rangeStart = 0;
            long rangeEnd = fileSize - 1;

            Matcher matcher = Pattern.compile("bytes=(\\d+)-(\\d+)?").matcher(rangeHeader);

            if (matcher.find()) {
                rangeStart = Long.parseLong(matcher.group(1));
                if (matcher.group(2) != null) {
                    rangeEnd = Long.parseLong(matcher.group(2));
                }
            }

            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }

            long contentLength = rangeEnd - rangeStart + 1;
            InputStream inputStream = new FileInputStream(videoFile);
            inputStream.skip(rangeStart);

            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
            headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(inputStreamResource);


        } catch (IOException e) {
            throw new GeneralError("400", "Can't read file");
        }
    }

    public String clear() {
        db.deleteAll();
        return "Cleared!";
    }
}

