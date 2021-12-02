package com.pdmoore.aoc;

import java.util.ArrayList;
import java.util.List;

public class day3 {
    public static int solution_1(String filename) {
        List<String> claims = utilities.getFileContentsAsStrings(filename);

        return solution_1(claims);
    }

    public static int solution_1(List<String> claims) {
        String[][] squares = new String[1000][1000];
        List<String> intactClaims = new ArrayList<>();

        for (String eachClaim :
                claims) {

            Claim claim = parseClaim(eachClaim);
            intactClaims.add(claim.claimId);

            for (int i = claim.upperLeftX; i < claim.upperLeftX + claim.width; i++) {
                for (int j = claim.upperLeftY; j < claim.upperLeftY + claim.height; j++) {
                    if (squares[i][j] == null) {
                        squares[i][j] = claim.claimId;
                    } else {
                        String overlapsWith = squares[i][j];
                        intactClaims.remove(overlapsWith);
                        intactClaims.remove(claim.claimId);

                        squares[i][j] = "X";
                    }
                }
            }
        }

        // @TODO - wrap the unique claim ID and the overlap area in a return parameter,then assert for both
        System.out.println(intactClaims);

        return findOverlap(squares);
    }

    public static Claim parseClaim(String claim) {
        String elements[] = claim.split("\\s|@|,|:");

        String claimId = elements[0];
        int upperLeftX = Integer.parseInt(elements[3]);
        int upperLeftY = Integer.parseInt(elements[4]);

        String area[] = elements[6].split("x");
        int width  = Integer.parseInt(area[0]);
        int height = Integer.parseInt(area[1]);

        return new Claim(claimId,
                         upperLeftX, upperLeftY,
                         width, height);
    }

    static int findOverlap(String[][] squares) {
        int overlapCount = 0;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                if ((null != squares[i][j]) && squares[i][j].equals("X")) overlapCount++;
            }
        }
        return overlapCount;
    }
}
