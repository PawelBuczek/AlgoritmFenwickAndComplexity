import java.util.ArrayList;
import java.util.List;

public class ArrayProcessorFenwick {

    public static List<Integer> processArray(int[] inputNumbers) {
        // Backing list of all appended (negative) values in insertion order
        ArrayList<Integer> values = new ArrayList<>();
        // Fenwick tree to mark "alive" items (1) or "deleted" (0).
        Fenwick fenwick = new Fenwick(inputNumbers.length); // upper bound on negatives

        for (int v : inputNumbers) {
            if (v < 0) {
                // add negative value
                values.add(v);
                fenwick.update(values.size(), 1); // mark alive at position = size (1-based)
            } else if (v > 0) {
                // remove the v-th alive element (1-based). If out of bounds, do nothing.
                int aliveCount = fenwick.sum(values.size());
                if (v <= aliveCount) {
                    int pos = fenwick.findByOrder(v); // 1-based index in values
                    if (pos >= 1 && pos <= values.size()) {
                        fenwick.update(pos, -1); // mark deleted
                    }
                }
            }
            // v == 0 => do nothing
        }

        // Build result list preserving order of alive elements.
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= values.size(); i++) {
            int isAlive = fenwick.sum(i) - fenwick.sum(i - 1);
            if (isAlive == 1) {
                result.add(values.get(i - 1));
            }
        }
        return result;
    }

    // Fenwick / Binary Indexed Tree for point updates and prefix sums.
    private static class Fenwick {
        private final int n;
        private final int[] bit;

        Fenwick(int capacity) {
            // allocate to capacity (we'll use indices 1..capacity)
            this.n = Math.max(1, capacity);
            this.bit = new int[n + 1];
        }

        // add delta at position idx (1-based)
        void update(int idx, int delta) {
            if (idx <= 0) return;
            for (int i = idx; i <= n; i += i & -i) bit[i] += delta;
        }

        // prefix sum 1..idx (1-based)
        int sum(int idx) {
            if (idx <= 0) return 0;
            if (idx > n) idx = n;
            int s = 0;
            for (int i = idx; i > 0; i -= i & -i) s += bit[i];
            return s;
        }

        // find smallest index such that prefix sum >= k (k >= 1 and k <= total)
        // returns index in range [1..n]. If not found returns n (caller should validate).
        int findByOrder(int k) {
            if (k <= 0) return 0;
            int idx = 0;
            int bitMask = highestOneBit(n);
            for (int d = bitMask; d != 0; d >>= 1) {
                int next = idx + d;
                if (next <= n && bit[next] < k) {
                    idx = next;
                    k -= bit[next];
                }
            }
            return idx + 1;
        }

        private int highestOneBit(int x) {
            int hb = 1;
            while (hb << 1 <= x) hb <<= 1;
            return hb;
        }
    }
}
