package com.hmkcode.spring.mvc.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Serzh on 10/8/16.
 */
public class Parser {

    private static final byte DELIMITER = (byte) '\n';

    public static void main(String[] args) {
        /*String big = "ihbwl5kef;5ljl6;k;k2lkde4;k;k5";
        int allOccurrences = findAllOccurrences(big, "5");
        System.out.println(allOccurrences);*/
        String s = "ksdckbs\nfvl\ndfvf\n\n";
//        byte[] bytes = s.getBytes();
        byte[] bytes = s.getBytes(Charset.forName("UTF-8"));
    }

    private static void findStringFromBytes(byte[] bytes) {
        String s = new String(bytes);
        String[] splitS = s.split("\n");
        for (int i = 0; i < splitS.length; i++) {
            if (!splitS[i].isEmpty()) {
                System.out.println(splitS[i]);
            }
        }
    }

    private static void findLines(String s) {
        String[] splitS = s.split("\n");
        for (int i = 0; i < splitS.length; i++) {
            if (!splitS[i].isEmpty()) {
                System.out.println(splitS[i]);
            }
        }
    }

    public static int findAllOccurrences(String fromFile, String find) {
        int count = StringUtils.countMatches(fromFile, find);
        return count;
    }
    /*Spring Framework's oneliner for this is:
        int occurance = StringUtils.countOccurrencesOf("a.b.c.d", ".");*/

}
