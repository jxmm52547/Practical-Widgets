package xyz.jxmm.tools;

import java.util.Comparator;
import java.util.Map;

public class MyComparatorValue implements Comparator<Map.Entry> {//按值排序

    public int compare(Map.Entry o1, Map.Entry o2) {
        return ((Integer) o2.getValue()).compareTo((Integer) o1.getValue());
    }
}
