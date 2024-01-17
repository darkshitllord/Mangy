package data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class Compress {

    public static String decompress(byte[] compressedData) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData));
             InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {

            // Read the decompressed content
            StringBuilder responseBody = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                responseBody.append(buffer, 0, bytesRead);
            }

            return responseBody.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
