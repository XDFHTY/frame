package com.cj.common.utils.timeUtil;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtil {

    /**
     * 计算日期
     */
    public static LocalDate plus(LocalDate localDate,int between, ChronoUnit chronoUnit) {

        return localDate.plus(between,chronoUnit);

    }

    public static void main(String[] args) {
        System.out.println(plus(LocalDate.now(),-1,ChronoUnit.YEARS));
        System.out.println(plus(LocalDate.now(),1,ChronoUnit.MONTHS));
        System.out.println(plus(LocalDate.now(),1,ChronoUnit.DAYS));
    }

}
