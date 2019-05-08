package com.bhw.wechatsell.util;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(9000000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);

    }
}
