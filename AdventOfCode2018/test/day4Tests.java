import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day4Tests {

    @Test
    public void solution_1_testFile() {
        assertEquals(94542, day4.solution_1("data/aoc18.4.txt"));
    }

    @Test
    public void solution_2_testFile() {
        assertEquals(50966, day4.solution_2("data/aoc18.4.txt"));
    }

    @Test
    public void extractGuardId() {
        assertEquals(2797, day4.extractGuardId("[1518-02-13 23:47] Guard #2797 begins shift"));
    }

    @Test
    public void ParseTimePortion() {
        assertEquals(5, day4.extractTimePortion("[1518-11-01 00:05] falls asleep"));
    }

    @Test
    public void singleGuard_SingleSleep() {
        List<String> activities = Arrays.asList(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up");

        day4.GuardLog actual = day4.singleGuardTracking(activities);
        assertEquals(50, actual.totalsSlept);
        assertEquals(24, actual.sleepiestMinute());
    }

    @Test
    public void singleGuard_Solution2() {
        List<String> activities = Arrays.asList(
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
        );
        day4.GuardLog actual = day4.singleGuardTracking(activities);
        assertEquals(45, actual.sleepiestMinute());

        //assert the count of 45 is whatever (3)
        assertEquals(3, actual.largestSleepCount());
    }


}
