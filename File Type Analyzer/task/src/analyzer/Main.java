package analyzer;

import analyzer.filetypechecker.FileSearcher;
import analyzer.filetypechecker.FileTypeChecker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


public class Main {




    public static void main(String[] args) {


        String filePath = args[0];
        String stringPattern = args[1];

        if (!filePath.equals("") || !stringPattern.equals("")) {

            FileSearcher fileSearcher = new FileSearcher(filePath);

            if (fileSearcher.isDirectory()) {
                fileSearcher.searchDirectory();

            }

        }


    }

}



