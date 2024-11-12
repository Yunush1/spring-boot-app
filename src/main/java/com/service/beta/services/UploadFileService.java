package com.service.beta.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Service
public class UploadFileService {
    private final S3Client s3Client;
    private final String bucketName;
    private final AmazonS3 amazonS3Client;

    public UploadFileService(@Value("${aws.s3.bucket-name}") String bucketName,
                             @Value("${aws.s3.region}") String region,
                             @Value("${aws.access-key-id}") String accessKeyId,
                             @Value("${aws.secret-access-key}") String secretAccessKey) {
        this.bucketName = bucketName;

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        this.amazonS3Client = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public URL uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Unique file name

        try (InputStream inputStream = file.getInputStream()) {
            // Create PutObjectRequest with file's metadata
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            // Upload file to S3
            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, file.getSize()));

            if (response.sdkHttpResponse().isSuccessful()) {
                // Generate the public URL for the uploaded file
                return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName));
            } else {
                throw new RuntimeException("Failed to upload file: " + response.sdkHttpResponse().statusText().orElse("Unknown error"));
            }
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.awsErrorDetails().errorMessage());
        }
    }

    public String preSignedUrl(String file) throws IOException {
        Date expiration = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7));

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName,file )
                        .withMethod(com.amazonaws.HttpMethod.GET)
                        .withExpiration(expiration);

        // Generate the pre-signed URL
        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();

    }
    public byte[] downloadFile(String fileName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(bucketName,fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) throws IOException {
     amazonS3Client.deleteObject(bucketName,fileName);
        return fileName+" deleted successfully";
    }
}
