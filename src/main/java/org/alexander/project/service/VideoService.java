package org.alexander.project.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.alexander.project.exception.GeneralError;
import org.alexander.project.repository.spec.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository db;
    private final MinioClient minioClient;

    private static final String BUCKET_NAME = "videos";
    @Value("${file.upload-dir}")
    private String fileUploadDir;

    public void uploadVideo(String fileName, MultipartFile file) {
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(BUCKET_NAME).build()
            );

            if (!found) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(BUCKET_NAME).build()
                );
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new GeneralError("400", e.getMessage());
        }
    }

    public InputStream getVideo(String fileName) {
        try {

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new GeneralError("400", e.getMessage());
        }
    }


    public ResponseEntity<InputStreamResource> streamVideo(String name, String rangeHeader) {

        try {
            String bucketName = "videos";

            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(name).build()
            );
            long fileSize = stat.size();
            HttpHeaders headers = new HttpHeaders();

            String fileExtension = name.substring(name.lastIndexOf(".") + 1);
            String mimeType = Files.probeContentType(Paths.get("file." + fileExtension));
            MediaType mediaType = mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.valueOf("video/mp4");

            if (rangeHeader == null) {
                InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(name)
                        .build());

                headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize));
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + name + "\"");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(mediaType)
                        .body(new InputStreamResource(stream));
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

            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(name)
                    .offset(rangeStart)
                    .length(contentLength)
                    .build());

            headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
            headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .contentType(mediaType)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            throw new GeneralError("400", e.getMessage());
        }
    }
}

