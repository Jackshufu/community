package com.community.test;

public class TestRegex {
    public static void main(String []args) {
       System.out.println("Hello World!");
        String tempAuthorStr="张三,李四;拿破仑，王五；曹操;66；加";
        String[] tmpAuthors=tempAuthorStr.split(";|,|；|，");
        for(String au:tmpAuthors){
        System.out.println(au);
        }
        
    }
}