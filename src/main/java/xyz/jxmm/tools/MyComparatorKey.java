package xyz.jxmm.tools;

import java.util.Comparator;

public class MyComparatorKey implements Comparator<String> {
    public int compare(String o2, String o1) {
        return o1.compareTo(o2);
    }
}
