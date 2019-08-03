package com.lwy.dao;


import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class Test01 {
    @Test
    public void test() throws UnsupportedEncodingException {
        System.out.println(0x61c88647);
        System.out.println(new String("你好鸭".getBytes(),"ISO-8859-1"));
    }
}
