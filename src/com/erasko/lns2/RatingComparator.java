package com.erasko.lns2;

import java.util.Comparator;

public class RatingComparator implements Comparator<Player> {

    @Override
    public int compare(Player player1, Player player2) {
        Integer winsCount1 = player1.getWinsCount();
        Integer winsCount2 = player2.getWinsCount();
        int wComp = -winsCount1.compareTo(winsCount2);
        if (wComp != 0) {
            return wComp;
        }
        String pName1 = player1.getName();
        String pName2 = player1.getName();
        return pName1.compareTo(pName2);
    }
}
