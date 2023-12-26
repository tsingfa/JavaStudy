package com.SE.entity;

public class Main {
    public static void main(String[] args){
        Person p1 = new Person();   //实例化一个对象
        p1.name="小明";
        Person p2=p1;   //指向同一个对象
        p2.age=10;
        System.out.println(p1.name+p1.age);
    }
}
