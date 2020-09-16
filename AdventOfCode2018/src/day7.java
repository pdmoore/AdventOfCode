import java.util.*;

public class day7 {
    public static String solution1(String filename) {
        List<String> inputLines = utilities.getFileContentsAsStrings(filename);

        Map<String, List<String>> rules = parseInput(inputLines);

        return calculateStepOrder(rules);
    }

    public static int solution2(String filename, int workerCount, int stepCost) {
        List<String> inputLines = utilities.getFileContentsAsStrings(filename);

        Map<String, List<String>> rules = parseInput(inputLines);

        return timeToCompleteSteps(rules, workerCount, stepCost);
    }

    private static Map<String, List<String>> parseInput(List<String> inputLines) {
        Map<String, List<String>> rules = new HashMap<>();

        for (String inputLine :
                inputLines) {
            // Step X must be finished before step Y can begin.
            //      5                              36
            String dependency = String.valueOf(inputLine.charAt(5));
            String key = String.valueOf(inputLine.charAt(36));

            if (rules.containsKey(key)) {
                List<String> existingDependencies = new ArrayList<>(rules.get(key));
                existingDependencies.add(dependency);
                rules.replace(key, existingDependencies);
            } else {
                List<String> existingDependencies = new ArrayList<>();
                existingDependencies.add(dependency);
                rules.put(key, existingDependencies);

                if (!rules.containsKey(dependency)) {
                    rules.put(dependency, new ArrayList<>());
                }
            }
        }

        return rules;
    }

    public static String calculateStepOrder(Map<String, List<String>> rules) {
        String stepOrder = "";

        while (!rules.keySet().isEmpty()) {
            String readyRule = findKeyWithNoDependency(rules);

            stepOrder += readyRule;

            rules.remove(readyRule);

            removeDependencyFromRules(rules, readyRule);
        }

        return stepOrder;
    }

    private static int timeToCompleteSteps(Map<String, List<String>> rules, int workerCount, int stepCost) {
        String stepOrder = "";

        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < workerCount; i++) {
            workers.add(new Worker());
        }

        int minutes = 0;
        while (!rules.keySet().isEmpty()) {

            decrementWorkers(workers, rules);

            // while there is a worker idle and a step can be processed
            while (workerAvailable(workers) && (findUnassignedWork(workers, rules) != null)) {
                String readyRule = findUnassignedWork(workers, rules);
                if (!stepOrder.contains(readyRule)) {

                    stepOrder += readyRule;

                    assignWork(workers, readyRule, stepCost);
                }
            }
            minutes++;
        }

        return minutes - 1;
    }

    private static String findUnassignedWork(List<Worker> workers, Map<String, List<String>> rules) {
        for (String key :
                rules.keySet()) {
            if (rules.get(key).isEmpty()) {
                boolean keyIsAssigned = false;
                for (Worker worker :
                        workers) {
                    if (worker.rule == key) {
                        keyIsAssigned = true;
                    }
                }

                if (!keyIsAssigned) return key;
            }
        }

        return null;
    }

    private static void assignWork(List<Worker> workers, String readyRule, int stepCost) {
        for (Worker worker :
                workers) {
            if (worker.rule == null) {
                worker.rule = readyRule;
                worker.workRemain = stepCost + ruleCost(readyRule);
                return;
            }
        }
    }

    public static int ruleCost(String rule) {
        int cost = rule.charAt(0) - 'A' + 1;
        return cost;
    }

    private static boolean workerAvailable(List<Worker> workers) {
        for (Worker worker :
                workers) {
            if (worker.rule == null) return true;
        }

        return false;
    }

    private static void decrementWorkers(List<Worker> workers, Map<String, List<String>> rules) {
        for (Worker worker :
                workers) {

            if (worker.workRemain > 0) {

                worker.workRemain -= 1;

                if (worker.workRemain == 0) {
                    rules.remove(worker.rule);

                    removeDependencyFromRules(rules, worker.rule);

                    worker.rule = null;
                }
            }
        }
    }


    private static void removeDependencyFromRules(Map<String, List<String>> rules, String readyRule) {
        Set<String> keys = rules.keySet();
        for (String key :
                keys) {
            List<String> depends = new ArrayList<String>(rules.get(key));
            depends.remove(readyRule);
            rules.replace(key, depends);
        }
    }

    private static String findKeyWithNoDependency(Map<String, List<String>> rules) {
        for (String key :
                rules.keySet()) {
            if (rules.get(key).isEmpty()) return key;
        }

        return null;
    }

    private static class Worker {
        String rule;
        int workRemain;
    }
}
