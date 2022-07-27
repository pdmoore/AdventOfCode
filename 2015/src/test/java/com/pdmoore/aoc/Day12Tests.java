package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @Test
    void dumbAttempt() {
        String input = "[1,2,3]";

        int actual = sumAllNumbersIn(input);

        assertEquals(6, actual);
    }

    private int sumAllNumbersIn(String json) {
        Object parsed = null;
        try {
            parsed = new JSONParser().parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jo = (JSONArray) parsed;

        int sum = 0;

        for (Iterator it = jo.iterator(); it.hasNext(); ) {
            Long l = (Long) it.next();
            sum += l;
        }



        return sum;
    }
}
