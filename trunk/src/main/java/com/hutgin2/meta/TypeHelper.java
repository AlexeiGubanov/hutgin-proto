package com.hutgin2.meta;

import org.apache.commons.lang3.StringUtils;

public class TypeHelper {

    public static final String BOOLEAN_TRUE = "true";
    public static final String BOOLEAN_FALSE = "false";

    /**
     * Compare with 'true' or 'false' ignore case and return corresponding boolean value only if equals.
     * Otherwise return null.
     */
    public static Boolean asBoolean(String value) {
        if (StringUtils.equalsIgnoreCase(value, BOOLEAN_TRUE)) {
            return Boolean.TRUE;
        } else if (StringUtils.equalsIgnoreCase(value, BOOLEAN_TRUE)) {
            return Boolean.FALSE;
        }
        return null;
    }

    /**
     * Return true only if value equals 'true' ignore case.
     * Otherwise false
     */
    public static boolean isTrue(String value) {
        return Boolean.TRUE.equals(asBoolean(value));
    }


}
