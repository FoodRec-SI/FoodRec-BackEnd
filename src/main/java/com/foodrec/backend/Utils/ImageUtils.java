package com.foodrec.backend.Utils;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class ImageUtils {

    private Storage initializeStorage() throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("firebase-config.json"));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("foodrec-389515.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Storage storage = initializeStorage();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String FILE_URL = "https://storage.googleapis.com/foodrec-389515.appspot.com/%s";
        return String.format(FILE_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getFileExtension (MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Object upload(MultipartFile multipartFile, String folder, String userId) {
        String fileName;
        String fileUrl = null;
        try {
            // Get unique file name
            fileName = folder + "-" + userId.concat(getFileExtension(multipartFile));
            // Convert multipartFile to File
            File file = this.convertToFile(multipartFile, fileName);
            fileUrl= this.uploadFile(file, fileName);
            // Delete the copy of uploaded file stored in the project folder
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public boolean delete(String fileName) throws IOException {
        Storage storage = initializeStorage();
        Blob blob;

        // List of supported file extensions
        String[] fileExtensions = {".png", ".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp"};

        boolean deleted = false;

        // Loop through each file extension and delete the corresponding file
        for (String extension : fileExtensions) {
            String fullFileName = fileName.concat(extension);
            blob = storage.get(BlobId.of("foodrec-389515.appspot.com", fullFileName));
            if (blob != null) {
                if (blob.delete()) {
                    deleted = true;
                    break; // Exit the loop if deletion is successful
                }
            }
        }
        // Return true if at least one file was deleted, false otherwise
        return deleted;
    }


}
