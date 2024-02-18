package search;

public class BinarySearchClosestD {
    // All arrays are indexed from 0.

    // Pre: |args| > 0, ∀ e ∈ args: e ∈ ℤ.
    // Post: void, sout <- e ∈ args.
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        int sum = 0;
        for (int i = 1; i < args.length; i++) {
            int number = Integer.parseInt(args[i]);
            a[i - 1] = number;
            sum += number;
        }
        if (sum % 2 == 0) {
            System.out.println(recursiveBinarySearch(x, a));
        } else {
            System.out.println(iterativeBinarySearch(x, a));
        }
    }

    // Pre: (int) x, (int array) a: ∀ i ∈ [0; |a| - 1]: a[i] ⩾ a[i + 1].
    // Post: n ∈ args: ∀ i ∈ [0; |a| - 1]  |x - n| ⩽ |x - a[i]|.
    public static int iterativeBinarySearch(int x, int[] a) {
        int l = 0;
        // l = 0
        int r = a.length - 1;
        // r = |a| - 1
        if (x < a[r]) {
            // x < a[r]
            return a[r];
        } else if (x > a[l]) {
            // x > a[l]
            return a[l];
        } else {
            // a[l] < x < a[r]
            // I: ∀ i ∈ [0; |a| - 2]: a[i] ⩾ a[i + 1],
            //    l, r ∈ [0, |a| - 1], l ⩽ r
            while (r - l > 1) {
                // I && (r' - l' > 1)
                int m = l + (r - l) / 2;
                // m' = (r' + l') / 2, m' ∈ [l', r']
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
        }
        // l', r' ∈ [l, r], ∀ m' ∈ [l', r'] => ∀ l', r': l' ⩽ r' => I = true
        if (Math.abs(x - a[l]) <= Math.abs(x - a[r])) {
            // |x - a[l]| ⩽ |x - a[r]|
            return a[l];
        } else {
            // |x - a[r]| > |x - a[l]|
            return a[r];
        }
    }

    // Pre: (int) x, (int array) a: ∀ i ∈ [0; |a| - 1]: a[i] ⩾ a[i + 1].
    // Post: n ∈ args: ∀ i ∈ [0; |a| - 1]  |x - n| ⩽ |x - a[i]|.
    public static int recursiveBinarySearch(int x, int[] a) {
        // l  = 0.
        // r = |a| - 1.
        // I: same with invariant of callBinarySearchRecursion.
        return callBinarySearchRecursion(x, a, 0, a.length - 1);
    }

    // Pre: (int) x, (int array) a: ∀ i ∈ [0; |a| - 1]: a[i] ⩾ a[i + 1],
    //      l ⩾ 0, r ⩽ |a|, l ⩽ r.
    // Post: n ∈ args: ∀ i ∈ [0; |a| - 1]  |x - n| ⩽ |x - a[i]|.
    private static int callBinarySearchRecursion(int x, int[] a, int l, int r) {
        // I: ∀ i ∈ [0; |a| - 2]: a[i] ⩾ a[i + 1],
        //    l, r ∈ [0, |a| - 1], l ⩽ r
        if (x < a[r]) {
            // x < a[r]
            return a[r];
        } else if (x > a[l]) {
            // x > a[l]
            return a[l];
        } else {
            // a[l] < x < a[r]
            if (r - l < 2) {
                // r' - l' < 2
                if (Math.abs(x - a[l]) <= Math.abs(x - a[r])) {
                    // |x - a[l]| ⩽ |x - a[r]|
                    return a[l];
                } else {
                    // |x - a[r]| > |x - a[l]|
                    return a[r];
                }
            }
            // r' - l' >= 2
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
            // l', r' ∈ [l, r], ∀ m' ∈ [l', r'] => ∀ l', r': l' ⩽ r' => I = true
            return callBinarySearchRecursion(x, a, l, r);
        }
    }
}
