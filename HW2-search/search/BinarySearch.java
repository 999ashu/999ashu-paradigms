package search;

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(RecursiveBinarySearch(x, a));
    }

    public static int IterativeBinarySearch(int x, int[] a) {
        int l = -1;
        int r = a.length;
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (x >= a[m]) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    public static int RecursiveBinarySearch(int x, int[]a) {
        return RecursiveBinarySearch(x, a, -1, a.length);
    }

    private static int RecursiveBinarySearch(int x, int[] a, int l, int r) {
        if (r - l < 2) {
            return r;
        }
        int m = l + (r - l) / 2;
        if (x >= a[m]) {
            r = m;
        } else {
            l = m;
        }
        return RecursiveBinarySearch(x, a, l, r);
    }
}