package com.xw.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XWCHQ on 2017/7/12-10:28
 */

public class ListUtils {

    /**
     *使用了{@link List#subList(int, int)}方法，因而分割后的list不能随意修改结构
     * @return
     */
    public static <T> int splitPage(List<T> list, List<List<T>> outlist, int splitNum) {
        List<List<T>> splitList = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < list.size()) {
            int endIndex = startIndex + splitNum;
            if (endIndex >= list.size()) {
                endIndex = list.size();
            }
            List<T> subList = list.subList(startIndex, endIndex);
            splitList.add(subList);
            startIndex = endIndex;
        }
        outlist.addAll(splitList);
        return splitList.size();
    }

    public static <K, T> int group(List<T> list, Map<K, List<T>> outMap, Grouptor<K, T> grouptor) {
        Map<K, List<T>> map = new HashMap<>();
        for (T data : list) {
            K groupKey = grouptor.getGroupKey(data);
            List<T> groupList = map.get(groupKey);
            if (groupList == null) {
                groupList = new ArrayList<>();
            }
            groupList.add(data);
            map.put(groupKey, groupList);
        }
        outMap.putAll(map);
        return map.size();
    }

    public static interface Grouptor<K, T> {
        K getGroupKey(T data);
    }

    public static <T, K> List<T> convertList(List<K> srcList, Converter<K, T> converter) {
        if (srcList == null || converter == null) {
            return null;
        }
        List<T> destList = new ArrayList<>();
        for (int i = 0; i < srcList.size(); i++) {
            destList.add(converter.convert(srcList.get(i)));
        }
        return destList;
    }

    public static <T, K> int indexOf(List<K> srcList, T data, Comparable<K, T> comparable) {
        for (int i = 0; i < srcList.size(); i++) {
            if (comparable.compare(srcList.get(i), data) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEmpty(List list){
        return list == null || list.isEmpty();
    }

    public static interface Converter<K, T> {
        T convert(K src);
    }

    public static interface Comparable<K, T> {
        int compare(K src, T comp);
    }
}
