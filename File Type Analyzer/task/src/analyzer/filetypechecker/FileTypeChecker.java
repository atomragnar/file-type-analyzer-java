package analyzer.filetypechecker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileTypeChecker {

    private FileTypeCheckingMethod algorithm;

    public FileTypeChecker(String algorithm) {
        this.setAlgorithm(algorithm);

    }
    public FileTypeChecker() {
        this.algorithm = new KMPMethod();
    }

    private void setAlgorithm(String algorithm) {
        if (algorithm.equals("--naive")) {
            this.algorithm = new NaiveMethod();
        } else if (algorithm.equals("--KMP")) {
            this.algorithm = new KMPMethod();
        } else if (algorithm.equals("--RK")) {
            this.algorithm = new RabinKarpMethod();
        }

    }

    public boolean checkFileType(String filepath, String pattern) {
        File file = new File(filepath);
        try (InputStream targetStream = Files.newInputStream(file.toPath())) {

            if (algorithm.doPatternSearch(new String(targetStream.readAllBytes(), StandardCharsets.UTF_8), pattern)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting bytes to String");
        }
    }

}
