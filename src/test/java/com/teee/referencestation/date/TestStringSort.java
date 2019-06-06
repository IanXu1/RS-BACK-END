package com.teee.referencestation.date;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestStringSort {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("12");
        list.add("15");
        list.add("2");
        list.add("24");
        Collections.sort(list);
        list.forEach(e -> System.out.print(e + " "));
    }
}
