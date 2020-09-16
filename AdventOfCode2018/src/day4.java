import java.util.*;
import java.util.spi.LocaleServiceProvider;

public class day4 {

    public static int solution_1(String filename) {
        List<String> guardActivities = utilities.getFileContentsAsStrings(filename);
        Collections.sort(guardActivities);

        HashMap<Integer, GuardLog> guardLogsById = new HashMap<>();
        createGuardLog(guardActivities, guardLogsById);

        int sleepiestGuardId = findSleepiest(guardLogsById);
        int sleepiestMinute = guardLogsById.get(sleepiestGuardId).sleepiestMinute();

        return sleepiestGuardId * sleepiestMinute;
    }

    public static int solution_2(String filename) {
        List<String> guardActivities = utilities.getFileContentsAsStrings(filename);
        Collections.sort(guardActivities);

        HashMap<Integer, GuardLog> guardLogsById = new HashMap<>();
        createGuardLog(guardActivities, guardLogsById);

        return sleepiestMinuteTimesGuard(guardLogsById);
    }

    private static void createGuardLog(List<String> guardActivities, HashMap<Integer, GuardLog> guardLogsById) {
        int minuteSleepingStarts = 0;
        GuardLog guardLog = new GuardLog(-999);

        for (String activity :
                guardActivities) {

            if (activity.contains("begins shift")) {
                // switch to tracking a new guards sleeping
                int guardId = extractGuardId(activity);
                if (guardLogsById.containsKey(guardId)) {
                    guardLog = guardLogsById.get(guardId);
                } else {
                    guardLog = new GuardLog(guardId);
                    guardLogsById.put(guardId, guardLog);
                }
            } else if (activity.contains("asleep")) {
                minuteSleepingStarts = extractTimePortion(activity);
            } else if (activity.contains("wakes")) {
                int minuteSleepingEnds = extractTimePortion(activity);
                guardLog.sleepSession(minuteSleepingStarts, minuteSleepingEnds);
            }
        }
    }

    private static int findSleepiest(HashMap<Integer, GuardLog> guardLogsById) {
        int mostSleptCount = -1;
        int sleepiestGuard = -1;

        Iterator<Map.Entry<Integer, GuardLog>> iter = guardLogsById.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, GuardLog> guardLog = iter.next();
            if (guardLog.getValue().totalsSlept > mostSleptCount) {
                mostSleptCount = guardLog.getValue().totalsSlept;
                sleepiestGuard = guardLog.getKey().intValue();
            }
        }

        return sleepiestGuard;
    }

    private static int sleepiestMinuteTimesGuard(HashMap<Integer, GuardLog> guardLogsById) {
        int sleepiestMinute = -1;
        int sleepiestCountOfMinute = -1;
        int guardId = -1;

        Iterator<Map.Entry<Integer, GuardLog>> iter = guardLogsById.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, GuardLog> guardLogEntry = iter.next();
            GuardLog guardLog = guardLogEntry.getValue();

            // find sleepiest minute count of this guard
            // if it's more than saved, then save guardId and the minute index
            if (guardLog.largestSleepCount() > sleepiestCountOfMinute) {
                guardId = guardLog.Id;
                sleepiestMinute = guardLog.sleepiestMinute();
                sleepiestCountOfMinute = guardLog.largestSleepCount();
            }

        }

        return guardId * sleepiestMinute;
    }




    public static int extractGuardId(String line) {
        int hashIndex = line.indexOf('#');
        int nextSpaceIndex = line.indexOf(' ', hashIndex);
        int guardId = Integer.parseInt(line.substring(hashIndex + 1, nextSpaceIndex));
        return guardId;
    }

    public static int extractTimePortion(String line) {
        int firstColon = line.indexOf(':');
        String minutesPortion = line.substring(firstColon + 1, firstColon + 3);
        int minutes = Integer.parseInt(minutesPortion);
        return minutes;
    }

    public static GuardLog singleGuardTracking(List<String> activities) {
        HashMap<Integer, GuardLog> guardLogsById = new HashMap<>();
        createGuardLog(activities, guardLogsById);

        return guardLogsById.entrySet().iterator().next().getValue();
    }

    public static class GuardLog {
        public int Id;
        int totalsSlept;
        int largestSleepCount;
        HashMap<Integer, Integer> minutesMap = new HashMap<>();

        public GuardLog(int id) {
            Id = id;
            for (int i = 0; i < 60; i++) {
                minutesMap.put(i, 0);
            }
        }

        public void addSleepMinutes(int minutesAsleep) {
            totalsSlept += minutesAsleep;
        }

        public void sleepSession(int starts, int ends) {
            addSleepMinutes(ends - starts);
            addOneForEachMinuteSlept(starts, ends);
        }

        private void addOneForEachMinuteSlept(int starts, int ends) {
            for (int i = starts; i < ends; i++) {
                int count = minutesMap.get(i);
                count++;
                minutesMap.put(i, count);
                if (count > largestSleepCount) {
                    largestSleepCount = count;
                }
            }
        }

        public int sleepiestMinute() {
            int sleepiestMinute = -1;
            int mostSlept = -1;
            for (int i = 0; i < minutesMap.size(); i++) {
                int count = minutesMap.get(i);
                if (count > mostSlept) {
                    sleepiestMinute = i;
                    mostSlept = count;
                }
            }
            return sleepiestMinute;
        }

        public int largestSleepCount() {
            return largestSleepCount;
        }
    }
}
