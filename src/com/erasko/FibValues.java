package com.erasko;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibValues {

    // Контейнер для хранения чисел Фибоначчи
    // (при необходимости можно использовать массив)
    public List<BigInteger> values = new ArrayList<>();

    BigInteger prev = BigInteger.ZERO;
    BigInteger next = BigInteger.ONE;

    // Сразу добавим первые два значения
    public FibValues() {
        values.add(prev);
        values.add(next);
    }

    // Получение n-го числа
    public BigInteger getFibValue(int n) {
        if(values.size() >= n) { // при возможности достаем из контейнера
            return values.get(n-1);
        } else {
            while (values.size() < n){
                values.add(nextValue());
            }
            return next;
        }
    }
    // служебный метод
    private BigInteger nextValue() {
        BigInteger temp = next;
            next = prev.add(next);
            prev = temp;
            return next;
    }

    public List<BigInteger> getValues() {
        return values;
    }
}
