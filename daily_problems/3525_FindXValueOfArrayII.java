class Solution {
    private int k;
    private int n;
    private long[] tree;   
    private long[] full;  

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            long rem = nums[l] % k;
            tree[node * k + (int) rem] = 1;
            full[node] = rem;
            return;
        }
        int mid = (l + r) >>> 1;
        build(node << 1, l, mid, nums);
        build(node << 1 | 1, mid + 1, r, nums);
        pushUp(node);
    }

    private void pushUp(int node) {
        int lc = node << 1, rc = node << 1 | 1;
        long lf = full[lc];
        long rf = full[rc];

        int base = node * k;
        int lBase = lc * k;
        int rBase = rc * k;

        for (int i = 0; i < k; i++) tree[base + i] = 0;

        for (int x = 0; x < k; x++) {
            tree[base + x] += tree[lBase + x];
        }

        for (int rp = 0; rp < k; rp++) {
            long cnt = tree[rBase + rp];
            if (cnt == 0) continue;
            int combined = (int) ((lf * rp) % k);
            tree[base + combined] += cnt;
        }

        full[node] = (lf * rf) % k;
    }

    private void update(int node, int l, int r, int pos, int val) {
        if (l == r) {
            long rem = val % k;
            int base = node * k;
            for (int i = 0; i < k; i++) tree[base + i] = 0;
            tree[base + (int) rem] = 1;
            full[node] = rem;
            return;
        }
        int mid = (l + r) >>> 1;
        if (pos <= mid) update(node << 1, l, mid, pos, val);
        else update(node << 1 | 1, mid + 1, r, pos, val);
        pushUp(node);
    }

    private long[] qCnt;
    private long qFull;

    private void query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return;
        if (ql <= l && r <= qr) {
            int base = node * k;
            long lf = qFull;
            long nf = full[node];

            for (int rp = 0; rp < k; rp++) {
                long cnt = tree[base + rp];
                if (cnt == 0) continue;
                int combined = (int) ((lf * rp) % k);
                qCnt[combined] += cnt;
            }
            qFull = (lf * nf) % k;
            return;
        }
        int mid = (l + r) >>> 1;
        query(node << 1, l, mid, ql, qr);
        query(node << 1 | 1, mid + 1, r, ql, qr);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;
        this.n = nums.length;

        tree = new long[4 * n * k];
        full = new long[4 * n];
        qCnt = new long[k];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int idx   = queries[i][0];
            int val   = queries[i][1];
            int start = queries[i][2];
            int x     = queries[i][3];

            update(1, 0, n - 1, idx, val);

            for (int j = 0; j < k; j++) qCnt[j] = 0;
            qFull = 1; 

            query(1, 0, n - 1, start, n - 1);

            result[i] = (int) qCnt[x];
        }
        return result;
    }
}