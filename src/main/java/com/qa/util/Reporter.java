package com.qa.util;


import com.qa.logger.Log;
import io.qameta.allure.Allure;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Reporter {

    public void addAllureAttachment(String name, Object file) throws IOException {
        if (file instanceof File[]) {
            for (File fi : (File[]) file) {
                switch (FilenameUtils.getExtension(fi.getName())) {
                    case "json":
                        Allure.getLifecycle().addAttachment(name, "application/json", "json",
                                convertFileToByteArray(fi));
                        break;
                    case "jpeg":
                        Allure.getLifecycle().addAttachment(name, "application/jpeg", "jpeg",
                                convertFileToByteArray(fi));
                        break;
                    case "png":
                        Allure.getLifecycle().addAttachment(name, "application/png", "png", convertFileToByteArray(fi));
                        break;
                    case "html":
                        Allure.getLifecycle().addAttachment(name, "application/html", "html",
                                convertFileToByteArray(fi));
                        break;
                    case "pdf":
                        Allure.getLifecycle().addAttachment(name, "application/pdf", "pdf", convertFileToByteArray(fi));
                        break;
                    default:
                }
            }
        } else if (file instanceof String) {
            Allure.getLifecycle().addAttachment(name, "application/json", "json", ((String) file).getBytes("UTF-8"));
        }
    }

    public static byte[] convertFileToByteArray(File fileData) throws IOException {
        return Files.readAllBytes(fileData.toPath());
    }

    public void addAttachment(String url, String request, String responseCode, String responseMessage) {
        try {
            addAllureAttachment("URL", url);
            addAllureAttachment("Request", request);
            addAllureAttachment("ResponseCode", responseCode);
            addAllureAttachment("ResponseMessage", responseMessage);
        } catch (Exception e) {
            Log.warn("Error Occured while adding attachments" + e.getMessage());
        }
    }

}
