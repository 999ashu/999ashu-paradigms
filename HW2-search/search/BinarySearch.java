package search;

public class BinarySearch {
    // All arrays are indexed from 0

    // Pre: |args| > 0, ∀ e ∈ args: e ∈ ℤ
    // Post: void; sout <- n ∈ [0; |args|]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(iterativeBinarySearch(x, a));
    }

    // Pre: (int) x, ∀ i ∈ [0, |a| - 1]: a[i] ⩾ a[i + 1], ] a[|a|] = x
    // Post: i ∈ [0, |a|]: x = a[i]
    public static int iterativeBinarySearch(int x, int[] a) {
        int l = -1;
        // l = -1
        int r = a.length;
        // r = |a|
        // I: ∀ i ∈ [0; |a| - 1]: a[i] ⩾ a[i + 1],
        //    l, r ∈ [0, |a|], l ⩽ r
        while (r - l > 1) {
            // I && (r' - l' > 1)
            int m = l + (r - l) / 2;
            // m' = (r' + l') / 2, m ∈ [l', r']
            if (x >= a[m]) {
                // x ⩾ a[m']
                r = m;
                // r' = m'
            } else {
                // x < a[m']
                l = m;
                // l' = m'
            }
        }
        // ∀ m' ∈ [l, r] & l, r ∈ [0, |a|] => ∀ m': ∈ [0, |a|] =>
        //   => l', r' ∈ [0, |a|] => I = true
        return r;
    }

    // Pre: (int) x, ∀ i ∈ [0, |a| - 1]: a[i] ⩾ a[i + 1], ] a[|a|] = x
    // Post: i ∈ [0, |a|]: x = a[i]
    public static int recursiveBinarySearch(int x, int[] a) {
        // l  = -1
        // r = |a|
        // I: same with I of callBinarySearchRecursion
        return callBinarySearchRecursion(x, a, -1, a.length);
    }

    // Pre:(int) x, ∀ i ∈ [0, |a| - 1]: a[i] ⩾ a[i + 1],
    //     ] a[|a|] = x, (int) l >= -1, (int) r <= |a|, l <= r
    // Post: i ∈ [l, r]: x = a[i]
    private static int callBinarySearchRecursion(int x, int[] a, int l, int r) {
        //  I: ∀ i ∈ [0; |a| - 1]: a[i] ⩾ a[i + 1],
        //      l, r ∈ [0, |a|], l ⩽ r
        if (r - l < 2) {
            // r' - l' < 2
            return r;
        }
        // r - l >= 2
        int m = l + (r - l) / 2;
        // m' = (r' + l') / 2, m ∈ [l', r']
        if (x >= a[m]) {
            // x ⩾ a[m']
            r = m;
            // r' = m'
        } else {
            // x < a[m']
            l = m;
            // l' = m'
        }
        // ∀ m' ∈ [l, r] & l, r ∈ [0, |a|] => ∀ m': ∈ [0, |a|] =>
        //   => l', r' ∈ [0, |a|] => I = true
        return callBinarySearchRecursion(x, a, l, r);
    }
}