package com.bhw.wechatsell.util;

import com.bhw.wechatsell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Byte code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
