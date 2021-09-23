package com.qa.util;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {

    Reporter reporter;

    public FileUtil() {
        reporter = new Reporter();
    }

    public byte[] convertFileToByteArray(File fileData) throws IOException {
        return Files.readAllBytes(fileData.toPath());
    }

    public void getFileContent(InputStream responseStream) throws UnsupportedEncodingException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
        String responseLine = br.readLine();
        String tempResponseString = "";
        while (responseLine != null) {
            tempResponseString = tempResponseString.concat(responseLine) + System.getProperty("line.separator");
            responseLine = br.readLine();
        }
        br.close();
        if (tempResponseString.length() > 0) {
            System.out.println(tempResponseString);
        }
    }

    public File writeStringInFile(String filePath, String text) {
        File file = new File(filePath);
        BufferedWriter writer = null;
        try {
            if (file.exists()) {
                boolean flag = file.delete();
                if (!flag) {
                    System.out.println("Unable to delete the existing file");
                }
            }
            if (file.createNewFile()) {
                writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(text);
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (filePath.toUpperCase().contains("REQUEST")) {
                    File[] files = { file };
                    reporter.addAllureAttachment("Request file : " + file.getName().toString(), files);
                } else if (filePath.toUpperCase().contains("RESPONSE")) {
                    File[] files = { file };
                    reporter.addAllureAttachment("Response file : " + file.getName().toString(), files);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

}
