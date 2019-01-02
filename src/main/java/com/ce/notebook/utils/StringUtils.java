package com.ce.notebook.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author: ce
 * @create: 2018-10-28 15:26
 **/
public class StringUtils {

    private static String delimiter = ",";

    public static String getDelimiter() {
        return delimiter;
    }

    public static void setDelimiter(String delimiter) {
        StringUtils.delimiter = delimiter;
    }

    public static <T> List<T> split (String str, String delimiter) {
        List<T> list = new ArrayList<>();
        for (String s : str.split(delimiter))
            list.add((T) s);
        return list;
    }

    public static <T> List<T> split (String str) {
        return split(str, delimiter);
    }
}
