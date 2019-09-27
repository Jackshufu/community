package com.community.test;

/**
 * Created by 舒先亮 on 2019/9/26.
 */
public class SimpleAddOneTo100 {

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println("l = " + l);
        long total = 0;
        long j = 2000000000;
        /*for(long i = 1;i <= 1000000000;i++){
            total = i + total;
        }*/

        long l3 = (1 + j) * j / 2;
        long l2 = System.currentTimeMillis();

        long l1 = l2 - l;
        System.out.println("total = " + l3+"   Time: "+ l1);
    }
}
