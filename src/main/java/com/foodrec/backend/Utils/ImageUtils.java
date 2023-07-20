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

    public Object upload(MultipartFile multipartFile, String folder, String fileName) {
        String fileUrl = null;
        try {
            // Get unique file name
            fileName = folder + "-" + fileName.concat(getFileExtension(multipartFile));
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

    public void deleteImage(String fileUrl) throws IOException {
        Storage storage = initializeStorage();
        Blob blob;

        // Extract the file name from the URL
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        // Check if the file name contains the "default" word
        if (fileName.contains("default")) {
            return;
        }

        blob = storage.get(BlobId.of("foodrec-389515.appspot.com", fileName));
        if (blob != null) {
            blob.delete();
        }
    }

    public String updateImage(String existingImage, MultipartFile image, String folder, String userId) {
        try {
            this.deleteImage(existingImage);
            return (String) this.upload(image, folder, userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final byte[] JPEG_SIGNATURE = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0};
    private static final byte[] PNG_SIGNATURE = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47};
    private static final byte[] GIF_SIGNATURE = new byte[]{(byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38};

    private static boolean isSignatureMatch(byte[] fileSignature, byte[] expectedSignature) {
        for (int i = 0; i < expectedSignature.length; i++) {
            if (fileSignature[i] != expectedSignature[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isImage(MultipartFile file) {
        if (file == null) {
            return true;
        }
        try (InputStream inputStream = file.getInputStream()) {
            byte[] fileSignature = new byte[4];
            inputStream.read(fileSignature);

            // Check the file signature against known image format signatures
            return isSignatureMatch(fileSignature, JPEG_SIGNATURE) ||
                    isSignatureMatch(fileSignature, PNG_SIGNATURE) ||
                    isSignatureMatch(fileSignature, GIF_SIGNATURE) ;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
