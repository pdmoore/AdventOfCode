import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Day03Tests {

    @Test
    public void Part1_SimpleExample_TwoIntersectingPoints() {
        String firstWire = "R8,U5,L5,D3";
        String secondWire = "U7,R6,D4,L4";
        int expected = 6;
        int actual = distanceToClosestIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part1_NextExample() {
        String firstWire = "R75,D30,R83,U83,L12,D49,R71,U7,L72";
        String secondWire = "U62,R66,U55,R34,D71,R55,D58,R83";
        int expected = 159;
        int actual = distanceToClosestIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part1_LastExample() {
        String firstWire = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51";
        String secondWire = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        int expected = 135;
        int actual = distanceToClosestIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part1_Solution() {
        String firstWire = "R1003,U476,L876,U147,R127,D10,R857,U199,R986,U311,R536,D930,R276,U589,L515,D163,L660,U69,R181,D596,L37,D359,R69,D50,L876,D867,L958,U201,R91,D127,R385,U646,L779,D309,L577,U535,R665,D669,L640,D50,L841,D32,R278,U302,L529,U679,R225,U697,R94,D205,L749,U110,L132,U664,R122,U476,R596,U399,R145,U995,R821,U80,L853,U461,L775,U57,R726,U299,L706,U500,R520,U608,L349,D636,L352,U617,R790,U947,L377,D995,R37,U445,L706,D133,R519,D194,L473,U330,L788,D599,L466,D100,L23,D68,R412,U566,R43,U333,L159,D18,L671,U135,R682,D222,R651,U138,R904,U546,R871,U264,R133,U19,R413,D235,R830,D376,R530,U18,L476,D120,L190,D252,R105,D874,L544,D705,R351,U527,L30,U283,L971,U199,L736,U36,R868,D297,L581,D888,L786,D865,R732,U394,L786,U838,L648,U434,L962,D862,R897,U116,L661,D848,L829,U930,L171,U959,R416,D855,L13,U941,R122,D678,R909,U536,R206,U39,L222,D501,L133,U360,R703,D928,R603,U793,L601,D935,R482,U444,L23,U331,L427,D349,L949,U147,L253,U757,R242,D307,R182,D371,L174,U518,L447,D851,R661,U432,R334,D240,R937,U625,L49,D105,R727,U504,L520,D126,R331,U176,L81,D168,L158,U774,L314,U623,R39,U743,R162,D646,R583,U523,R899,D419,L635,U958,R426,U482,L513,D624,L37,U669,L611,U167,L904,U163,L831,U222,L320,U561,R126,D7,L330,D313,R698,D473,R163,U527,R161,U823,L409,D734,L507,U277,L821,D341,R587,U902,R857,U386,R858,D522,R780,D754,L973,U1,R806,D439,R141,D621,R983,D546,R899,U566,L443,D147,R558,D820,R181,U351,R625,U60,R521,U225,R757,U673,L267,D624,L306,U531,L202,U854,L138,D725,R364,D813,L787,U183,R98,D899,R945,D363,L797";
        String secondWire = "L993,D9,L41,D892,L493,D174,R20,D927,R263,D65,R476,D884,R60,D313,R175,U4,L957,U514,R821,U330,L973,U876,L856,D15,L988,U443,R205,D662,R753,U74,R270,D232,R56,D409,R2,U257,R198,U644,L435,U16,L914,D584,L909,D222,R919,U649,R77,U213,R949,D272,R893,U717,L939,U310,R637,D912,L347,D755,L895,D305,R460,D214,L826,D847,R680,U821,L688,U472,R721,U2,L755,D84,L716,U466,L833,U12,L410,D453,L462,D782,R59,U491,L235,D827,L924,U964,R443,D544,L904,D383,R259,D12,L538,D194,R945,U356,L85,D362,R672,D741,L556,U696,L994,U576,L201,D912,L282,D328,R322,D277,L269,U799,R150,U584,L479,U69,R313,U628,R114,D870,R660,D929,R964,U412,L790,U948,R949,D955,L555,U478,R967,D850,R569,D705,R30,U434,L948,U711,L507,D729,L256,U740,L60,D127,L95,U93,R260,D74,L267,D637,L658,U831,R882,D798,L173,U835,R960,D583,R411,U967,L515,U302,L456,D322,R963,U788,L516,U845,L131,U741,L246,D215,R233,U621,R420,D679,L8,D962,R514,U51,L891,U705,L699,U909,R408,D664,R324,U846,R503,U769,R32,D495,R154,U403,R145,U581,L708,D315,R556,U582,R363,U495,L722,U210,R718,U927,R994,D136,R744,U107,R316,D222,R796,U755,L69,D877,R661,D378,L215,D105,R333,D780,R335,D691,L263,U603,L582,U95,L140,D651,R414,D420,L497,U106,L470,D826,R706,D166,R500,D258,L225,U310,L866,U720,R247,D500,L340,U726,R296,U16,R227,U839,R537,U125,R700,U372,L310,D444,R214,D121,R151,U351,L767,D815,R537,U392,L595,U178,L961,D366,L216,U392,R645,U195,R231,D734,L441,D680,L226,D212,L142,U131,L427,D159,L538,D270,R553,D841,R115,U346,R673,D421,L403,D320,L296,U831,L655,U690,L105,U474,L687";
        int expected = 2050;
        int actual = distanceToClosestIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Convert_InputToLineSegments() {
        String firstWire = "R8,U5,L5,D3";
        List<LineSegment> lines = lineSegmentsOf(firstWire);
        assertEquals(4, lines.size());

        LineSegment expected1 = new LineSegment(new Point(0, 0), new Point(8, 0));
        LineSegment expected2 = new LineSegment(new Point(8, 0), new Point(8, 5));
        LineSegment expected3 = new LineSegment(new Point(8, 5), new Point(3, 5));
        LineSegment expected4 = new LineSegment(new Point(3, 5), new Point(3, 2));
        assertTrue(expected1.equals(lines.get(0)));
        assertTrue(expected2.equals(lines.get(1)));
        assertTrue(expected3.equals(lines.get(2)));
        assertTrue(expected4.equals(lines.get(3)));
    }

    @Test
    public void LineIntersects() {
        // create a vertical line segment
        LineSegment vertical = new LineSegment(new Point(3, 2), new Point(3, 5));

        // create a horizontal line segment
        LineSegment horizontal = new LineSegment(new Point(2, 3), new Point(6, 3));

        // find the point of the intersection
        Point actual = findIntersectionPoint(horizontal, vertical);

        Point expected = new Point(3, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void LineIntersects_ShouldIntersect() {
        LineSegment line1 = new LineSegment(new Point(6, 7), new Point(6, 3));
        LineSegment line2 = new LineSegment(new Point(8, 5), new Point(3, 5));

        Point actual = findIntersectionPoint(line2, line1);

        Point expected = new Point(6, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void LineContains() {
        Point expected = new Point(6, 5);

        LineSegment line1 = new LineSegment(new Point(6, 7), new Point(6, 3));
        assertTrue("vertical line", line1.contains(expected));

        LineSegment line2 = new LineSegment(new Point(8, 5), new Point(3, 5));
        assertTrue("horizontal line", line2.contains(expected));
    }

    @Test
    public void NoIntersectingPoint() {
        LineSegment vertical = new LineSegment(new Point(3, 2), new Point(3, 5));

        LineSegment horizontal = new LineSegment(new Point(0, 7), new Point(6, 7));

        // find the point of the intersection
        Point actual = findIntersectionPoint(horizontal, vertical);

        assertNull(actual);
    }


    // Tried using same strategy as in part 1, simple examples worked but part 2 solution was too small
    @Test
    public void Part2_SimplestExample_OneIntersectingPoint() {
        String firstWire = "R4,U99";
        String secondWire = "U3,R200";
        int expected = 14;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_SimplestExample_OneIntersectingPoint_NegativeGrid() {
        String firstWire = "L4,D99";
        String secondWire = "D3,L200";
        int expected = 14;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_SimpleExample_TwoIntersectingPoints() {
        String firstWire = "R8,U5,L5,D3";
        String secondWire = "U7,R6,D4,L4";
        int expected = 30;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_NextExample() {
        String firstWire = "R75,D30,R83,U83,L12,D49,R71,U7,L72";
        String secondWire = "U62,R66,U55,R34,D71,R55,D58,R83";
        int expected = 610;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_LastExample() {
        String secondWire = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51";
        String firstWire = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        int expected = 410;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_LastExample_InvertedWires() {
        String secondWire = "L98,D47,L26,U63,L33,D87,R62,U20,L33,D53,L51";
        String firstWire = "D98,L91,U20,L16,U67,L40,D7,L15,D6,L7";
        int expected = 410;
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    @Ignore
    // Basic examples work for Part 2, soln input below results in a "too low" feedback on the answer code gets
    public void Part2_Solution() {
        String firstWire = "R1003,U476,L876,U147,R127,D10,R857,U199,R986,U311,R536,D930,R276,U589,L515,D163,L660,U69,R181,D596,L37,D359,R69,D50,L876,D867,L958,U201,R91,D127,R385,U646,L779,D309,L577,U535,R665,D669,L640,D50,L841,D32,R278,U302,L529,U679,R225,U697,R94,D205,L749,U110,L132,U664,R122,U476,R596,U399,R145,U995,R821,U80,L853,U461,L775,U57,R726,U299,L706,U500,R520,U608,L349,D636,L352,U617,R790,U947,L377,D995,R37,U445,L706,D133,R519,D194,L473,U330,L788,D599,L466,D100,L23,D68,R412,U566,R43,U333,L159,D18,L671,U135,R682,D222,R651,U138,R904,U546,R871,U264,R133,U19,R413,D235,R830,D376,R530,U18,L476,D120,L190,D252,R105,D874,L544,D705,R351,U527,L30,U283,L971,U199,L736,U36,R868,D297,L581,D888,L786,D865,R732,U394,L786,U838,L648,U434,L962,D862,R897,U116,L661,D848,L829,U930,L171,U959,R416,D855,L13,U941,R122,D678,R909,U536,R206,U39,L222,D501,L133,U360,R703,D928,R603,U793,L601,D935,R482,U444,L23,U331,L427,D349,L949,U147,L253,U757,R242,D307,R182,D371,L174,U518,L447,D851,R661,U432,R334,D240,R937,U625,L49,D105,R727,U504,L520,D126,R331,U176,L81,D168,L158,U774,L314,U623,R39,U743,R162,D646,R583,U523,R899,D419,L635,U958,R426,U482,L513,D624,L37,U669,L611,U167,L904,U163,L831,U222,L320,U561,R126,D7,L330,D313,R698,D473,R163,U527,R161,U823,L409,D734,L507,U277,L821,D341,R587,U902,R857,U386,R858,D522,R780,D754,L973,U1,R806,D439,R141,D621,R983,D546,R899,U566,L443,D147,R558,D820,R181,U351,R625,U60,R521,U225,R757,U673,L267,D624,L306,U531,L202,U854,L138,D725,R364,D813,L787,U183,R98,D899,R945,D363,L797";
        String secondWire = "L993,D9,L41,D892,L493,D174,R20,D927,R263,D65,R476,D884,R60,D313,R175,U4,L957,U514,R821,U330,L973,U876,L856,D15,L988,U443,R205,D662,R753,U74,R270,D232,R56,D409,R2,U257,R198,U644,L435,U16,L914,D584,L909,D222,R919,U649,R77,U213,R949,D272,R893,U717,L939,U310,R637,D912,L347,D755,L895,D305,R460,D214,L826,D847,R680,U821,L688,U472,R721,U2,L755,D84,L716,U466,L833,U12,L410,D453,L462,D782,R59,U491,L235,D827,L924,U964,R443,D544,L904,D383,R259,D12,L538,D194,R945,U356,L85,D362,R672,D741,L556,U696,L994,U576,L201,D912,L282,D328,R322,D277,L269,U799,R150,U584,L479,U69,R313,U628,R114,D870,R660,D929,R964,U412,L790,U948,R949,D955,L555,U478,R967,D850,R569,D705,R30,U434,L948,U711,L507,D729,L256,U740,L60,D127,L95,U93,R260,D74,L267,D637,L658,U831,R882,D798,L173,U835,R960,D583,R411,U967,L515,U302,L456,D322,R963,U788,L516,U845,L131,U741,L246,D215,R233,U621,R420,D679,L8,D962,R514,U51,L891,U705,L699,U909,R408,D664,R324,U846,R503,U769,R32,D495,R154,U403,R145,U581,L708,D315,R556,U582,R363,U495,L722,U210,R718,U927,R994,D136,R744,U107,R316,D222,R796,U755,L69,D877,R661,D378,L215,D105,R333,D780,R335,D691,L263,U603,L582,U95,L140,D651,R414,D420,L497,U106,L470,D826,R706,D166,R500,D258,L225,U310,L866,U720,R247,D500,L340,U726,R296,U16,R227,U839,R537,U125,R700,U372,L310,D444,R214,D121,R151,U351,L767,D815,R537,U392,L595,U178,L961,D366,L216,U392,R645,U195,R231,D734,L441,D680,L226,D212,L142,U131,L427,D159,L538,D270,R553,D841,R115,U346,R673,D421,L403,D320,L296,U831,L655,U690,L105,U474,L687";
        int expected = 21001;  // too low per the feedback
        int actual = distanceToFewestStepsIntersectionPointOf(firstWire, secondWire);
        assertEquals(expected, actual);
    }


    // Different strategy for part 2
    // For first wire, track each step along the wire and store [point, stepCount] in a map
    // Then second wire, track step along wire and look up if that point exists in firsWire
    // if point is crossed, total the steps and remember the point with fewest steps.
    @Test
    public void Part2_Take2_SimplestExample_OneIntersectingPoint() {
        String firstWire = "R4,U9";
        String secondWire = "U3,R9";
        int expected = 14;
        int actual = part2_TrackEachPoint(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_Take2_SimpleExample_TwoIntersectingPoints() {
        String firstWire = "R8,U5,L5,D3";
        String secondWire = "U7,R6,D4,L4";
        int expected = 30;
        int actual = part2_TrackEachPoint(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_Take2_NextExample() {
        String firstWire = "R75,D30,R83,U83,L12,D49,R71,U7,L72";
        String secondWire = "U62,R66,U55,R34,D71,R55,D58,R83";
        int expected = 610;
        int actual = part2_TrackEachPoint(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_Take2_LastExample() {
        String secondWire = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51";
        String firstWire = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        int expected = 410;
        int actual = part2_TrackEachPoint(firstWire, secondWire);
        assertEquals(expected, actual);
    }

    @Test
    public void Part2_Take2_Solution() {
        String firstWire = "R1003,U476,L876,U147,R127,D10,R857,U199,R986,U311,R536,D930,R276,U589,L515,D163,L660,U69,R181,D596,L37,D359,R69,D50,L876,D867,L958,U201,R91,D127,R385,U646,L779,D309,L577,U535,R665,D669,L640,D50,L841,D32,R278,U302,L529,U679,R225,U697,R94,D205,L749,U110,L132,U664,R122,U476,R596,U399,R145,U995,R821,U80,L853,U461,L775,U57,R726,U299,L706,U500,R520,U608,L349,D636,L352,U617,R790,U947,L377,D995,R37,U445,L706,D133,R519,D194,L473,U330,L788,D599,L466,D100,L23,D68,R412,U566,R43,U333,L159,D18,L671,U135,R682,D222,R651,U138,R904,U546,R871,U264,R133,U19,R413,D235,R830,D376,R530,U18,L476,D120,L190,D252,R105,D874,L544,D705,R351,U527,L30,U283,L971,U199,L736,U36,R868,D297,L581,D888,L786,D865,R732,U394,L786,U838,L648,U434,L962,D862,R897,U116,L661,D848,L829,U930,L171,U959,R416,D855,L13,U941,R122,D678,R909,U536,R206,U39,L222,D501,L133,U360,R703,D928,R603,U793,L601,D935,R482,U444,L23,U331,L427,D349,L949,U147,L253,U757,R242,D307,R182,D371,L174,U518,L447,D851,R661,U432,R334,D240,R937,U625,L49,D105,R727,U504,L520,D126,R331,U176,L81,D168,L158,U774,L314,U623,R39,U743,R162,D646,R583,U523,R899,D419,L635,U958,R426,U482,L513,D624,L37,U669,L611,U167,L904,U163,L831,U222,L320,U561,R126,D7,L330,D313,R698,D473,R163,U527,R161,U823,L409,D734,L507,U277,L821,D341,R587,U902,R857,U386,R858,D522,R780,D754,L973,U1,R806,D439,R141,D621,R983,D546,R899,U566,L443,D147,R558,D820,R181,U351,R625,U60,R521,U225,R757,U673,L267,D624,L306,U531,L202,U854,L138,D725,R364,D813,L787,U183,R98,D899,R945,D363,L797";
        String secondWire = "L993,D9,L41,D892,L493,D174,R20,D927,R263,D65,R476,D884,R60,D313,R175,U4,L957,U514,R821,U330,L973,U876,L856,D15,L988,U443,R205,D662,R753,U74,R270,D232,R56,D409,R2,U257,R198,U644,L435,U16,L914,D584,L909,D222,R919,U649,R77,U213,R949,D272,R893,U717,L939,U310,R637,D912,L347,D755,L895,D305,R460,D214,L826,D847,R680,U821,L688,U472,R721,U2,L755,D84,L716,U466,L833,U12,L410,D453,L462,D782,R59,U491,L235,D827,L924,U964,R443,D544,L904,D383,R259,D12,L538,D194,R945,U356,L85,D362,R672,D741,L556,U696,L994,U576,L201,D912,L282,D328,R322,D277,L269,U799,R150,U584,L479,U69,R313,U628,R114,D870,R660,D929,R964,U412,L790,U948,R949,D955,L555,U478,R967,D850,R569,D705,R30,U434,L948,U711,L507,D729,L256,U740,L60,D127,L95,U93,R260,D74,L267,D637,L658,U831,R882,D798,L173,U835,R960,D583,R411,U967,L515,U302,L456,D322,R963,U788,L516,U845,L131,U741,L246,D215,R233,U621,R420,D679,L8,D962,R514,U51,L891,U705,L699,U909,R408,D664,R324,U846,R503,U769,R32,D495,R154,U403,R145,U581,L708,D315,R556,U582,R363,U495,L722,U210,R718,U927,R994,D136,R744,U107,R316,D222,R796,U755,L69,D877,R661,D378,L215,D105,R333,D780,R335,D691,L263,U603,L582,U95,L140,D651,R414,D420,L497,U106,L470,D826,R706,D166,R500,D258,L225,U310,L866,U720,R247,D500,L340,U726,R296,U16,R227,U839,R537,U125,R700,U372,L310,D444,R214,D121,R151,U351,L767,D815,R537,U392,L595,U178,L961,D366,L216,U392,R645,U195,R231,D734,L441,D680,L226,D212,L142,U131,L427,D159,L538,D270,R553,D841,R115,U346,R673,D421,L403,D320,L296,U831,L655,U690,L105,U474,L687";
        int expected = 21666;
        int actual = part2_TrackEachPoint(firstWire, secondWire);
        assertEquals(expected, actual);
    }


    /*
    Tests above,
    Code below
     */
    private Point findIntersectionPoint(LineSegment l1, LineSegment l2) {
        double a1 = l1.end.y - l1.start.y;
        double b1 = l1.start.x - l1.end.x;
        double c1 = a1 * l1.start.x + b1 * l1.start.y;

        double a2 = l2.end.y - l2.start.y;
        double b2 = l2.start.x - l2.end.x;
        double c2 = a2 * l2.start.x + b2 * l2.start.y;

        double delta = a1 * b2 - a2 * b1;
        int y = (int) ((a1 * c2 - a2 * c1) / delta);
        int x = (int) ((b2 * c1 - b1 * c2) / delta);

        if (x != 0 && y != 0) {
            Point candidatePoint = new Point(x, y);
            if (l1.contains(candidatePoint) && l2.contains(candidatePoint))
                return candidatePoint;
        }

        return null;
    }

    private List<LineSegment> lineSegmentsOf(String firstWire) {
        List<LineSegment> result = new ArrayList<>();

        String[] paths = firstWire.split(",");
        Point currentPoint = new Point(0, 0);

        for (String path :
                paths) {

            char direction = path.charAt(0);
            int pathLength = Integer.parseInt(path.substring(1));

            Point endPoint = null;
            LineSegment segment;
            switch (direction) {
                case 'R':
                    endPoint = new Point(currentPoint.x + pathLength, currentPoint.y);
                    break;
                case 'U':
                    endPoint = new Point(currentPoint.x, currentPoint.y + pathLength);
                    break;
                case 'L':
                    endPoint = new Point(currentPoint.x - pathLength, currentPoint.y);
                    break;
                case 'D':
                    endPoint = new Point(currentPoint.x, currentPoint.y - pathLength);
                    break;
            }
            segment = new LineSegment(currentPoint, endPoint);
            result.add(segment);
            currentPoint = endPoint;
        }

        return result;
    }


    protected class LineSegment {
        public final Point start;
        public final Point end;

        public LineSegment(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object obj) {
            LineSegment other = (LineSegment) obj;

            return other.start.equals(start) && other.end.equals(end);
        }

        @Override
        public String toString() {
            return start.toString() + "," + end.toString();
        }

        public boolean contains(Point candidatePoint) {
            // line segments are vertical or horizontal
            if (isHorizontal()) {
                if (candidatePoint.y != start.y) {
                    return false;
                }

                return (candidatePoint.x >= Math.min(start.x, end.x) && candidatePoint.x <= Math.max(start.x, end.x));
            } else {
                if (candidatePoint.x != start.x) {
                    return false;
                }

                return (candidatePoint.y >= Math.min(start.y, end.y) && candidatePoint.y <= Math.max(start.y, end.y));
            }
        }

        private boolean isHorizontal() {
            return start.y == end.y;
        }

        public int length() {
            if (isHorizontal()) {
                return Math.abs(end.x - start.x);
            } else {
                return Math.abs(end.y - start.y);
            }
        }
    }

    private int distanceToClosestIntersectionPointOf(String firstWire, String secondWire) {
        List<LineSegment> firstWireLines = lineSegmentsOf(firstWire);
        List<LineSegment> secondWireLines = lineSegmentsOf(secondWire);

        int lowestManhattan = Integer.MAX_VALUE;
        for (LineSegment lineSegment :
                secondWireLines) {

            for (LineSegment potentialCrossingWire :
                    firstWireLines) {
                Point intersectionPoint = findIntersectionPoint(lineSegment, potentialCrossingWire);
                if (intersectionPoint != null) {

                    int manhattan = Math.abs(intersectionPoint.x) + Math.abs(intersectionPoint.y);
                    if ((manhattan != 0) && manhattan < lowestManhattan) {
                        lowestManhattan = manhattan;
                    }
                }
            }
        }
        return lowestManhattan;
    }

    // first attempt at Part 2, this worked for simple examples but failed the Solution input
    private int distanceToFewestStepsIntersectionPointOf(String firstWire, String secondWire) {
        List<LineSegment> firstWireLines = lineSegmentsOf(firstWire);
        List<LineSegment> secondWireLines = lineSegmentsOf(secondWire);

        int stepsInWire2 = 0;
        int fewestSteps = Integer.MAX_VALUE;
        for (LineSegment lineSegment :
                secondWireLines) {

            int stepsInWire1 = 0;
            for (LineSegment potentialCrossingWire :
                    firstWireLines) {

                Point intersectionPoint = findIntersectionPoint(lineSegment, potentialCrossingWire);
                if (intersectionPoint != null) {
                    int wire1Partial = distanceBetween(intersectionPoint, potentialCrossingWire.start);
                    int wire2Partial = distanceBetween(intersectionPoint, lineSegment.start);
                    int totalSteps = stepsInWire1 + stepsInWire2
                            + wire1Partial
                            + wire2Partial;

                    if (totalSteps < fewestSteps) {
                        fewestSteps = totalSteps;
                    }
                } else {
                    stepsInWire1 += potentialCrossingWire.length();
                }
            }

            stepsInWire2 += lineSegment.length();
        }

        return fewestSteps;
    }

    // TODO - only dealing with vert/horz lines so forcing to int
    private int distanceBetween(Point first, Point second) {
        int xDelta = first.x - second.x;
        int yDelta = first.y - second.y;
        double distance = Math.sqrt((xDelta * xDelta) + (yDelta * yDelta));

        return (int) distance;
    }

    private int part2_TrackEachPoint(String firstWire, String secondWire) {
        Map pointToSteps = new HashMap();

        String[] paths = firstWire.split(",");
        Point position = new Point(0, 0);

        int stepCount = 0;
        for (String path :
                paths) {
            int dx = 0;
            int dy = 0;
            switch (path.charAt(0)) {
                case 'U':
                    dx = 0;
                    dy = 1;
                    break;
                case 'D':
                    dx = 0;
                    dy = -1;
                    break;
                case 'L':
                    dx = -1;
                    dy = 0;
                    break;
                case 'R':
                    dx = 1;
                    dy = 0;
                    break;
            }

            int pathLength = Integer.parseInt(path.substring(1));
            for (int i = 0; i < pathLength; i++) {
                position = new Point(dx + position.x, dy + position.y);
                pointToSteps.put(position.toString(), ++stepCount);
            }
        }

        int fewestSteps = Integer.MAX_VALUE;

        paths = secondWire.split(",");
        position = new Point(0, 0);
        stepCount = 0;

        for (String path :
                paths) {
            int dx = 0;
            int dy = 0;
            switch (path.charAt(0)) {
                case 'U':
                    dx = 0;
                    dy = 1;
                    break;
                case 'D':
                    dx = 0;
                    dy = -1;
                    break;
                case 'L':
                    dx = -1;
                    dy = 0;
                    break;
                case 'R':
                    dx = 1;
                    dy = 0;
                    break;
            }

            int pathLength = Integer.parseInt(path.substring(1));
            for (int i = 0; i < pathLength; i++) {
                position = new Point(dx + position.x, dy + position.y);
                ++stepCount;

                Integer found = (Integer) pointToSteps.get(position.toString());
                if (found != null) {
                    int totalSteps = found + stepCount;
                    fewestSteps = Math.min(fewestSteps, totalSteps);
                }
            }
        }

        return fewestSteps;
    }


}
