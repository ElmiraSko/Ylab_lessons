package com.erasko;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Для пункта 4. Создаем объект класса FibValues
    static FibValues fib = new FibValues();

    // ArrayList для пункта 1.1
    static ArrayList<BigInteger> listF = new ArrayList<>();

    // Пункт 1. Рекурсия, выполняется очень долго
    // можно получить переполнение стека
    public static BigInteger fValuesWithRecursion(int n) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        } else {
            return fValuesWithRecursion(n-1).add(fValuesWithRecursion(n-2));
        }
    }

    // Пункт 1.1. Рекурсия c использованием ArrayList,
    // выполняется быстрее за счет хранения ранее посчитанных чисел,
    // но можно получить переполнение стека
    public static BigInteger fValueListWithRecursion(int n) {
        if(listF.size() >= n) {
            return listF.get(n-1);
        } else {
            if (n-1 == 0) {
                listF.ensureCapacity(n);
                listF.add(BigInteger.valueOf(0L));
                return BigInteger.valueOf(0L);
            } else if (n-1 == 1) {
                if(listF.size() == 0) {listF.add(BigInteger.valueOf(0L));}
                listF.add(BigInteger.valueOf(1L));
                return BigInteger.valueOf(1L);
            } else {
                BigInteger s = fValueListWithRecursion(n-1).add(fValueListWithRecursion(n-2));
                listF.add(s);
                return s;
            }
        }
    }

    // Пункт 2. метод возвращает массив чисел Фибоначчи
    public static BigInteger[] fValuesArray(int n) {
        // создаем массив для n элементов
        BigInteger[] values = new BigInteger[n];
        values[0] = BigInteger.valueOf(0);
        values[1] = BigInteger.valueOf(1);
        for (int i = 2; i< values.length; i++) {
            values[i] = values[i-2].add(values[i-1]);
        }
        return values;
    }

    // Пункт 2.1 метод возвращает список чисел Фибоначчи
    public static List<BigInteger> fValuesList(int n) {
        // создаем список для n элементов
        List<BigInteger> values = new ArrayList<>(n);
        long prev = 0L;
        long next = 1L;
        values.add(BigInteger.valueOf(prev));
        values.add(BigInteger.valueOf(next));
        long temp;
        long count = 1;
        while (++count < n){
            temp = next;
            next = prev + next;
            prev = temp;
            values.add(BigInteger.valueOf(next));
        }
        return values;
    }

    // Пункт 3. Получение n-го числа в цикле
    public static BigInteger fibValue(int n) {
        BigInteger prev = BigInteger.valueOf(0L);
        BigInteger next = BigInteger.valueOf(1L);
        if (n == 1) {
            return prev;
        } else if (n == 2) {
            return next;
        } else {
            BigInteger temp;
            long count = 1;
            while (++count < n){
                temp = next;
                next = prev.add(next);
                prev = temp;
            }
            return next;
        }
    }

    public static void main(String[] args) {
        // Проверяем методы:

        // Пункт 1. По рекурсии
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            System.out.printf("%d ", fValuesWithRecursion(i));
        }
        System.out.println(System.currentTimeMillis() - time1);  // 386218 миллисекунд

        // Пункт 1.1 Рекурсия с использованием ArrayList
        long time2 = System.currentTimeMillis();
        for (int i = 1; i < 50; i++) {
            System.out.printf("%d ", fValueListWithRecursion(i));
        }
        System.out.println(System.currentTimeMillis() - time2); // 2-3 мс

        // Нахождение отдельных значений:
        System.out.println();
        System.out.println( fValueListWithRecursion(100));
        // 100 число:  218922995834555169026
        System.out.println(fValuesWithRecursion(50));


        // Пункт 2. Цикл с использованием массива, вывод на консоль
        long time3 = System.currentTimeMillis();
        for (BigInteger n: fValuesArray(50)) {
            System.out.printf("%d ", n);
        }
        System.out.println(System.currentTimeMillis() - time3); // 2-3 мс

        // Пункт 2.1.  Вывод на консоль чисел из списка
        long time4 = System.currentTimeMillis();
        for (BigInteger n: fValuesList(50)) {
            System.out.printf("%d " , n);
        }
        System.out.println(System.currentTimeMillis() - time4); // 1-3 мс

        // Пункт 3. Получение n-го числа в цикле
        System.out.println(fibValue(12)); // 12 число: 89
        System.out.println(fibValue(500));
// 500 число: 86168291600238450732788312165664788095941068326060883324529903470149056115823592713458328176574447204501

        // Пункт 4. Метод класса FibValues
        System.out.println(fib.getFibValue(7));  // 8
        System.out.println(fib.getFibValue(3));  // 1
        System.out.println(fib.getValues());        // [0, 1, 1, 2, 3, 5, 8]
        System.out.println(fib.getFibValue(4));  // 2
        System.out.println(fib.getValues());        // [0, 1, 1, 2, 3, 5, 8]
        System.out.println(fib.getFibValue(12)); // 89
        System.out.println(fib.getValues());        // [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]
    }
}
