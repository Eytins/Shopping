package com.zte.shopping.util;

/**
 * Created by Eytins
 */

public class ParameterUtil {
    public static boolean isnull(String s) {
        if ("".equals(s) || null == s) {
            return true;
        }
        return false;
    }
}
