package com.y3tu.yao.batch.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

class Collections3 {
    public Collections3() {
    }

    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection        来源集合.
     * @param keyPropertyName   要提取为Map中的Key值的属性名.
     * @param valuePropertyName 要提取为Map中的Value值的属性名.
     */
    /*public static Map extractToMap(Collection collection, String keyPropertyName, String valuePropertyName) {
        HashMap map = new HashMap(collection.size());

        try {
            Iterator var4 = collection.iterator();

            while (var4.hasNext()) {
                Object obj = var4.next();
                map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
            }

            return map;
        } catch (Exception var6) {
            throw Reflections.convertReflectionExceptionToUnchecked(var6);
        }
    }

    *//**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     *//*
    public static <T> List<T> extractToList(Collection collection, String propertyName) {
        ArrayList list = new ArrayList(collection.size());

        try {
            Iterator var3 = collection.iterator();

            while (var3.hasNext()) {
                Object obj = var3.next();
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }

            return list;
        } catch (Exception var5) {
            throw Reflections.convertReflectionExceptionToUnchecked(var5);
        }
    }*/

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param separator    分隔符.
     */
    public static String extractToString(Collection collection, String propertyName, String separator) {
        /*List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);*/
        return null;
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    public static String convertToString(Collection collection, String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
     **/
    public static String convertToString(Collection collection, String prefix, String postfix) {
        StringBuilder builder = new StringBuilder();
        Iterator var4 = collection.iterator();

        while (var4.hasNext()) {
            Object o = var4.next();
            builder.append(prefix).append(o).append(postfix);
        }

        return builder.toString();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断是否不为空.
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 获取第一个元素.
     */
    public static <T> T getFirst(Collection<T> collection) {
        return isEmpty(collection) ? null : collection.iterator().next();
    }


    /**
     * 获取最后一个元素.
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        } else if (collection instanceof List) {
            List<T> list = (List) collection;
            return list.get(list.size() - 1);
        } else {
            Iterator iterator = collection.iterator();

            Object current;
            do {
                current = iterator.next();
            } while (iterator.hasNext());

            return (T) current;
        }
    }

    /**
     * 返回a+b的新List.
     **/
    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        List<T> result = new ArrayList(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a-b的新List.
     **/
    public static <T> List<T> subtract(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList(a);
        Iterator var3 = b.iterator();

        while (var3.hasNext()) {
            T element = (T) var3.next();
            list.remove(element);
        }

        return list;
    }

    /**
     * 返回a与b的交集的新List.
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList();
        Iterator var3 = a.iterator();

        while (var3.hasNext()) {
            T element = (T) var3.next();
            if (b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public static Long[] strConvert2Long(String[] ids) {
        int len = ids.length;
        Long[] longarr = new Long[len];

        for (int i = 0; i < len; ++i) {
            longarr[i] = Long.valueOf(ids[i]);
        }

        return longarr;
    }

    public static <T> void ListRandomSort(List<T> list) {
        if (isNotEmpty(list)) {
            Collections.shuffle(list);
        }

    }
}