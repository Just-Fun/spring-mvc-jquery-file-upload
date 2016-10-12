package com.hmkcode.spring.mvc.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Serzh on 10/8/16.
 */
public class BufferedReaderExample {
    static int count = 0;
    public static void main(String[] args) {

//        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/ua/com/serzh/Question.docx"))) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/ua/com/serzh/1.rtf"))) {

            String sCurrentLine;
            String find = "o";

            while ((sCurrentLine = br.readLine()) != null) {
                count += Parser.findAllOccurrences(sCurrentLine, find);
                System.out.println(sCurrentLine);
                System.out.println(count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(count);
    }
}
