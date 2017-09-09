package com.hadn13.tst;

import com.hand13.bitmapturn.Tests;

import java.util.*;

/**
 * Created by hd110 on 2017/8/16.
 */

/**
 * hahhah
 * hahahha
 */
public class Mains {
    public static void main(String [] args){
        Thread thread = new Thread(()->System.out.println("ew"));
        thread.start();
        System.out.println(Objects.hash("hello"));
        String str = "a,b,c, ,,";
        String[] strings = str.split(",");
        System.out.println(strings.length);
        Tests tests = new Tests();
        System.out.println(tests.hashCode());
        Tests tests1 = new Tests();
        System.out.println(tests1.hashCode());
    }
}
