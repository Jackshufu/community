package com.community.test;

/**
 * Created by 舒先亮 on 2019/9/26.
 */
public class TestArray {

    public static void main(String[] args) {
        /**
         * 数组增加一个元素
         */
//        int[] arrays = new int[3];
//        初始数组
        int[] arrays = new int[]{10, 11, 12, 13};
        for (int array : arrays) {
            System.out.println("array = " + array);
        }

//        数组扩容，增加一个元素
        int[] newArrays = new int[arrays.length + 1];
//        newArrays = arrays;
        newArrays[arrays.length] = 5;
        for (int i = 0; i < arrays.length; i++) {
            newArrays[i] = arrays[i];
        }
        arrays = newArrays;

        for (int array : arrays) {
            System.out.println("增加后的array = " + array);
        }

        /**
         * 数组删除一个元素
         */
//        初始数组为arrays
//        新产生一个数组，将其长度减小1
        int[] reduceArray = new int[arrays.length - 1];
//        删除的目标角标
        int dst = 2;
        for(int i = 0; i < reduceArray.length;i++){
            if(i<dst){
                reduceArray[i] = arrays[i];
            }else {
                reduceArray[i] = arrays[i + 1];
            }
        }
        arrays = reduceArray;
        for (int i : arrays) {
            System.out.println("删除角标为"+dst+"后的数组 = " + i);
        }


    }
}
