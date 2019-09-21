package net.streets.common.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by tsungai.kaviya on 2015-09-14.
 */
public class IOUtils {
    private static final Logger logger = Logger.getLogger(IOUtils.class.getSimpleName());

    private static final long DEFAULT_MAX_BUFFER_SIZE = 1024L;

    private static final boolean DEFAULT_ENABLE_BUFFERING = true;

    private static final boolean DEFAULT_ENABLE_OVERWRITING = true;

    public static File writeToFile(InputStream inputStream, String absoluteFilePath, boolean enableOverwriting,
                                   boolean useBufferedWrite, long bufferSize) throws Exception {
        logger.info("=============================================================");
        logger.info(format("Writing inputStream to file %s", absoluteFilePath));
        logger.info(format("Buffer is %s", useBufferedWrite ? format("enabled. Buffer size: %s", bufferSize) : "disabled."));
        Date startTime = new Date();

        OutputStream outputStream = null;
        try {
            File outputFile = new File(absoluteFilePath);

            if (outputFile.exists() && enableOverwriting) {
                if (!outputFile.delete()) {
                    throw new IOException("Failed to delete existing file");
                }
            } else if (outputFile.exists() && !enableOverwriting) {
                return outputFile;
            }

            if (useBufferedWrite)
                outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            else
                outputStream = new FileOutputStream(outputFile);

            int read, totalLen = 0;
            byte[] bytes = new byte[(int) bufferSize];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
                totalLen += read;
            }

            logger.info(format("Wrote %s bytes in %s", totalLen, new Date().getTime() - startTime.getTime()));
            logger.info("=============================================================");
            return outputFile;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public static File writeToFile(InputStream inputStream, String absoluteFilePath) throws Exception {
        return writeToFile(inputStream, absoluteFilePath, DEFAULT_ENABLE_BUFFERING, DEFAULT_ENABLE_OVERWRITING, DEFAULT_MAX_BUFFER_SIZE);
    }

    public static File writeToFile(InputStream inputStream, String absoluteFilePath, boolean useBufferedWrite) throws Exception {
        return writeToFile(inputStream, absoluteFilePath, useBufferedWrite, useBufferedWrite, DEFAULT_MAX_BUFFER_SIZE);
    }

    public static String readFromFile(String filename) throws Exception {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String sCurrentLine;
            StringBuilder sFullString = new StringBuilder();

            while ((sCurrentLine = br.readLine()) != null) {
                sFullString.append(sCurrentLine).append("\n");
            }
            return sFullString.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static ArrayList<String> readArrayFromFile(String filename) throws Exception {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String sCurrentLine;
            ArrayList<String> sFullString = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                sFullString.add(sCurrentLine);
            }
            return sFullString;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static String getOSDefaultTempDir() {
        String fileSystemSeparatorChar = File.separator;
        String tempFileDirectory = System.getProperty("java.io.tmpdir");
        return tempFileDirectory.endsWith(fileSystemSeparatorChar) ? tempFileDirectory : tempFileDirectory + fileSystemSeparatorChar;
    }
}
