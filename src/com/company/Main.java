package com.company;

public class Main {

    public static void main(String[] args) {

/*
        String[] test = {
                "(3+12)*8/2=",
                "2*(11-8)=",
                "(31+1)*8/2=",
                "3*(11-9)/4=",
                "~((~2)+4)=",
                "~(~2)+4=",
                "5*((3-7)*2-3*(5+1))-3=",
                "4*(2-8)=",
                "(2+4)*(4-6)=",
                "4-7=",
                "5+3+(~5-2)=",
                "((2+7)/3+(14-3)*4)/2="
        };

        for(String str : test) {
            String[] post = ONP.toONP(str);
            System.out.print(("INF: " + str));
            System.out.print((", ONP: " + String.join("", post)));
            System.out.println((", Wynik: " + ONP.calculate(post)));
        }
*/
        for(String str : args) {
            String[] post = ONP.toONP(str);
            System.out.print(("INF: " + str));
            System.out.print((", ONP: " + String.join("", post)));
            System.out.println((", Wynik: " + ONP.calculate(post)));
        }
    }
}
