package com.WhatAreYou.WhatAreYou;

import org.junit.jupiter.api.Test;

public class QueryStringTest {
    @Test
    public void query1Test() throws Exception {
        //given
        String query = "제목#태그1";
        //when
        String title;
        String[] tag;
        String[] split = query.split("#");
        if (query.substring(0,1).equals("#")) {
            tag = split;
            for (String s : tag) {
                if(!s.isEmpty())
                    System.out.println("tag = " + s);
            }
        } else {
            title = split[0];
            System.out.println("title = " + title);
        }
    }
    @Test
    public void query2Test() throws Exception {
        //given
        String query2 = "#태그1 #태그2";
        //when
        String title;
        String[] tag;
        String[] split = query2.split("#");
        if (query2.substring(0,1).equals("#")) {
            tag = split;
            for (String s : tag) {
                if(!s.isEmpty())
                    System.out.println("tag = " + s);
            }
        } else {
            title = split[0];
            System.out.println("title = " + title);
        }

    }
    @Test
    public void query3Test() throws Exception {
        String query3 = "#태그 제목";
        //when
        String title;
        String[] tag;
        String[] split = query3.split("#");
        if (query3.substring(0,1).equals("#")) {
            tag = split;
            for (String s : tag) {
                if(!s.isEmpty())
                    System.out.println("tag = " + s);
            }
        } else {
            title = split[0];
            System.out.println("title = " + title);
        }
    }
}
