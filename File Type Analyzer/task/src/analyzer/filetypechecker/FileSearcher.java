package analyzer.filetypechecker;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileSearcher {

    private static final Map<String, String> fileSignaturesName = new HashMap<>();
    private Map<String, String> checkedFiles;
    private File[] listOfFiles;
    private FileTypeChecker fileTypeChecker;
    private String path;
    private boolean isDirectory;

    private static final String[] PATTERNS = { "%PDF-", "pmview", "PK",
            "vnd.oasis.opendocument.presentation", "W.o.r.d", "P.o.w.e.r.P.o.i", "word/_rels", "ppt/_rels", "xl/_rels",
            "-----BEGIN\\ CERTIFICATE-----", "ftypjp2", "ftypiso2"
    };

    static {

        fileSignaturesName.put("%PDF-", "PDF document");
        fileSignaturesName.put("pmview", "PCP pmview config");
        fileSignaturesName.put("PK", "Zip archive");
        fileSignaturesName.put("vnd.oasis.opendocument.presentation", "OpenDocument presentation");
        fileSignaturesName.put("W.o.r.d", "MS Office Word 2003");
        fileSignaturesName.put("P.o.w.e.r.P.o.i", "MS Office PowerPoint 2003");
        fileSignaturesName.put("word/_rels", "MS Office Word 2007+");
        fileSignaturesName.put("ppt/_rels", "MS Office PowerPoint 2007+");
        fileSignaturesName.put("xl/_rels", "MS Office Excel 2007+");
        fileSignaturesName.put("-----BEGIN\\ CERTIFICATE-----", "PEM certificate");
        fileSignaturesName.put("ftypjp2", "ISO Media JPEG 2000");
        fileSignaturesName.put("ftypiso2", "ISO Media MP4 Base Media v2");

    }


    public FileSearcher(String path, String algorithm) {
        this.checkedFiles = new HashMap<>();
        this.fileTypeChecker = new FileTypeChecker(algorithm);
        setFilePath(path);
    }

    public FileSearcher(String path) {
        this.checkedFiles = new HashMap<>();
        this.fileTypeChecker = new FileTypeChecker();
        setFilePath(path);
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    private void setFilePath(String path) {
        this.path = path;
        if (new File(path).isDirectory()) {
            this.isDirectory = true;
        } else {
            this.isDirectory = false;
        }
    }

    public void printResults() {
        Arrays.stream(listOfFiles).forEach(file -> {
                System.out.println(file.getName() + ": " + checkedFiles.get(file.getPath()));
        });
    }

    public void searchDirectory() {
        this.listOfFiles = new File(this.path).listFiles();

        if (listOfFiles != null) {

            int poolSize = 4;
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);
            for (int i = 0; i < listOfFiles.length; i++) {

                String filepath = listOfFiles[i].getPath();

                executor.submit(() -> {
                    boolean isFound = false;
                    for (String pattern : PATTERNS) {
                        if (fileTypeChecker.checkFileType(filepath, pattern)) {
                            checkedFiles.put(filepath, fileSignaturesName.get(pattern));
                            isFound = true;
                        }
                    }
                    if (!isFound) {
                        checkedFiles.put(filepath, "Unknown file type");
                    }
                });
            }
            try {
                boolean termination = executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        printResults();
    }



}
