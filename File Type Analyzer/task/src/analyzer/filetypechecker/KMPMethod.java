package analyzer.filetypechecker;

public class KMPMethod implements FileTypeCheckingMethod {


    @Override
    public boolean doPatternSearch(String fileString, String pattern) {
        int patLength = pattern.length();
        int txtLength = fileString.length();
        int[] lpsArray = new int[patLength];
        calculateLPSArray(pattern, patLength, lpsArray);
        int i = 0, j = 0;
        while ((txtLength - i) >=(patLength - j)) {
            if (pattern.charAt(j) == fileString.charAt(i)) {
                j++;
                i++;
                if (j == patLength) {
                    return true;
                }
            } else if (i < txtLength) {
                if (j != 0) {
                    j = lpsArray[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return  false;
    }

    void calculateLPSArray(String pat, int M, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0;
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) { len = lps[len - 1];}
                else { lps[i] = len; i++;}
            }
        }
    }
}
