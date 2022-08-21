package com.rk.WebsocketsMGS.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Utils {

    public static String getSHA256(String data){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<Integer> createPrimesArray(int limit) {
        // write your code here
        return new Random().ints(0, 1000)
                .filter(Utils::isPrime)
                .limit(limit)
                .boxed().toList();
    }

    private static boolean isPrime(int n) {
        return n > 1 && IntStream
                .rangeClosed(2, n - 1)
                .noneMatch(divisor -> n % divisor == 0);
    }
}
