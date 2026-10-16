class Solution {
    static final int MOD = 1_000_000_007;
    long[] sum, sq, lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            update(1, 0, n - 1, last[nums[i]] + 1, i);
            last[nums[i]] = i;
            ans = (ans + sq[1]) % MOD;
        }
        return (int) ans;
    }

    private void apply(int o, int len, long v) {
        sq[o] += 2 * v * sum[o] + v * v * len;
        sum[o] += v * len;
        lazy[o] += v;
    }

    private void push(int o, int l, int r) {
        if (lazy[o] != 0) {
            int m = (l + r) / 2;
            apply(2 * o, m - l + 1, lazy[o]);
            apply(2 * o + 1, r - m, lazy[o]);
            lazy[o] = 0;
        }
    }

    private void update(int o, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            apply(o, r - l + 1, 1);
            return;
        }
        push(o, l, r);
        int m = (l + r) / 2;
        if (ql <= m) update(2 * o, l, m, ql, qr);
        if (qr > m) update(2 * o + 1, m + 1, r, ql, qr);
        sum[o] = sum[2 * o] + sum[2 * o + 1];
        sq[o] = sq[2 * o] + sq[2 * o + 1];
    }
}