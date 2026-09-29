class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;

        if ((m + n - 1) % 2 == 1) return false;
        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') return false;

        int maxBal = (m + n) / 2;

        boolean[][][] dp = new boolean[m][n][maxBal + 2];
        
        dp[0][0][1] = true; 
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;

                for (int b = 0; b <= maxBal; b++) {

                    int prev = (grid[i][j] == '(') ? b - 1 : b + 1;
                    if (prev < 0) continue;

                    boolean fromTop = i > 0 && dp[i - 1][j][prev];
                    boolean fromLeft = j > 0 && dp[i][j - 1][prev];
                    dp[i][j][b] = fromTop || fromLeft;
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}