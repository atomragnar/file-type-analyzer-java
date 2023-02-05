package analyzer.filetypechecker;

public class RabinKarpMethod implements FileTypeCheckingMethod {

    private static final int D = 10;
    private static final int Q = 13;

    @Override
    public boolean doPatternSearch(String fileString, String pattern) {

        int m = pattern.length();
        int n = fileString.length();

        int i, j;
        int h = 1;

        int pHashValue = 0;
        int tHashValue = 0;

        for (i = 0; i <= m - 1; i++)
            h = (h * D) % Q;


        for (i = 0; i < m - 1; i++) {
            pHashValue = (D * pHashValue + pattern.charAt(i)) % Q;
            tHashValue = (D * tHashValue + fileString.charAt(i)) % Q;
        }



        for (i = 0; i <= n - m; i++) {
            if (pHashValue == tHashValue) {
                for (j = 0; j < m; j++) {
                    if (fileString.charAt(i + j) != pattern.charAt(j))
                        break;
                }
                    if (j == m)
                        return true;
                }
            if (i < n - m) {
                    tHashValue = (D * (tHashValue - fileString.charAt(i) * h) + fileString.charAt(i + m)) % Q;
                    if (tHashValue < 0)
                        tHashValue = (tHashValue + Q);
                }
            }
        return false;
    }
}
