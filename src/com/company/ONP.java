package com.company;

import java.util.Arrays;

/**
* Klasa <code>ONP</code> umożliwia przekształcanie postaci infiksowej wyrażenia na postfiksową
* */
public class ONP {
    /**indeks pozycji na stosie*/
    static int index = 0;
    /**stos wykorzystywany do przekształceń*/
    static String stos = "";
    /**wynikowe wyrażenie w postaci postfiksowej*/
    static String[] postfix;


    /**
    * Sprawdza priorytet operacji
    * @param operation operacja której priorytet należy sprawdzić
     * @return  priorytet operacji
    * */
    public static int priority(char operation) {
        return switch (operation) {
            case '=' -> 0;
            case '<', '>' -> 1;
            case '+', '-' -> 2;
            case '*', '/', '%' -> 3;
            case '^' -> 4;
            case '~' -> 5;
            default -> -1;
        };

    }

    /**
    * Zwraca negację przekazanej zmiennej, operacja negowania
     * @param a liczba do zanegowania
     * @return negacja liczby
    * */
    public static double operation(double a) {
        return -a;
    }


    /**
     * Metoda wykonuje obliczenia na podstawie przesłanych zmiennych
     * @param a pierwsza liczba
     * @param b druga liczba
     * @param operation operator między liczbami
     * @return wykonane obliczenia
     * */
    public static double operation(double a, double b, char operation){
        switch (operation) {
            case '+': return a+b;
            case '-': return a-b;
            case '~': return -a;
            case '*': return a*b;
            case '%': return a%b;
            case '^': return Math.pow(a,b);
            case '/':
                if(b!=0)
                    return a/b;
                else
                    System.exit(0);
            default: return -1;
        }
    }

    /**
     * Metoda sprawdza czy przesłany string zawiera liczbę
     * @param liczba string do sprawdzenia
     * @return prawda jeśli ciąg składa się tylko z liczb
     * */
    public static boolean isNumeric(String liczba){
        try {
            Double.parseDouble(liczba);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Metoda oblicza wyrażenie zapisane w ONP
     * @param post wyrażenie w postaci postfiksowej
     * @return wynik wyrażenia
     * */
    public static double calculate(String[] post){

        double a, b;
        double[] stos = new double[post.length];
        int index = 0;
        char operator;

        for (String s : post) {

            // jesli pobrano = zakoncz dzialanie
            if (s.equals("="))
                return stos[0];

            // jesli pobierana wartosc to liczba dodaj ja na stos
            if (isNumeric(s)) {
                stos[index++] = Double.parseDouble(s);
            }
            // sciagnij liczby ze stosu i wykonaj operacje
            else {
                operator = s.charAt(0);

                b = stos[--index];

                // jesli nastepna operacja to negacja, pobierz ze stosu tylko jedna wartosc
                if (operator == '~') {
                    stos[index++] = operation(b);
                }
                else {
                    a = stos[--index];
                    stos[index++] = operation(a, b, operator);
                }
            }

        }

        return stos[0];
    }

    /**
     * Metoda pobiera ostatni znak ze stosu
     * @return znak na szczycie stosu
     * */
    public static char getValue() {
        if (stos.length() > 0)
            return stos.charAt(stos.length() - 1);
        else
            return '\0';
    }

    /**
     * Metoda usuwa znak ze szczytu stosu
     * */
    public static void deleteTop() {
        if (stos.length() > 0)
            stos = stos.substring(0, stos.length() - 1);
    }

    /**
     * Metoda porównuje szczyt stosu z nowo pobranym operatorem
     * @param nowyOperator priorytet tego operatora będzie porównywany z tymi znajdującymi sie na stosie
     * */
    public static void checkStack(char nowyOperator) {

        // jeśli dostarczono ) przepisz ze stosu wszystko do momentu (
        if (nowyOperator == ')') {
            while (!stos.isEmpty() && getValue() != '(') {
                postfix[index++] = String.valueOf(getValue());
                deleteTop();
            }
            deleteTop();
            return;
        }

        // sprawdz priorytet nowego operatora
        int nowyPriorytet = priority(nowyOperator);

        // jeśli nowy element to =, ~ albo ^ i na szczycie stosu jest ten sam operator lub nowy operator to (
        // to dodaj go bez usuwania starych wartości ze stosu
        if (!((getValue() == nowyOperator && (nowyOperator == '=' || nowyOperator == '~' || nowyOperator == '^')) || nowyOperator == '(')) {

            // sciagaj operatory ze stosu do momentu trafienia na operator z wyzszym priorytetem
            while (!stos.isEmpty() && priority(getValue()) >= nowyPriorytet) {
                postfix[index++] = String.valueOf(getValue());
                deleteTop();
            }
        }

        stos += nowyOperator;
    }

    /**
     * Metoda przekształca wyrażenie infiksowe do prefiksowego
     * @param infix string zawierający wyrażenie infiksowe
     * @return tablica stringów zawierająca elementy wyrażenia postfiksowego
     * */
    public static String[] toONP(String infix) {

        // ustaw zmienne
        postfix = new String[infix.length()];
        index = 0;

        for (int j = 0; j < infix.length(); j++) {

            // dopóki pobierany znak jest liczbą
            if (infix.charAt(j) >= 48 && infix.charAt(j) <= 57) {
                postfix[index] = "";
                while (infix.charAt(j) >= 48 && infix.charAt(j) <= 57) {
                    postfix[index] += String.valueOf(infix.charAt(j));
                    if(j + 1 >= infix.length())
                        break;
                    j++;
                }
                index++;
            }

            // jesli cos jest na stosie sprawdz priorytety
            if (!stos.isEmpty())
                checkStack(infix.charAt(j));
            else
                stos = String.valueOf(infix.charAt(j));
        }

        // jeśli zostaly jakies operatory na stosie, sciagnij je
        while (!stos.isEmpty()) {
            if (getValue() != '(') {
                postfix[index++] = String.valueOf(getValue());
            }
            deleteTop();
        }

        return Arrays.copyOfRange(postfix, 0, index);
    }


}