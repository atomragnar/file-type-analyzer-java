package analyzer.filetypechecker;

public class NaiveMethod implements FileTypeCheckingMethod {


    @Override
    public boolean doPatternSearch(String fileString, String pattern) {
        int l1 = fileString.length();
        int l2 = pattern.length();
        for (int i = 0, j = l2 - 1; j < l1; i++, j++) {
            if (pattern.equals(fileString.substring(i, j+1))) {
                return true;
            }
        }
        return false;
    }

}
