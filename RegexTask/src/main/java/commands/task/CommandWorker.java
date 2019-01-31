package commands.task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static commands.task.RegexWorker.log;

public class CommandWorker {
    Map<String, LinkedHashSet<Integer>> collectionsMap;


    public CommandWorker() {
        collectionsMap = new HashMap<>();
    }

    void createCollection(String collName) {
        collectionsMap.put(collName, new LinkedHashSet<>());
    }

    void addIntoCollection(String collName, int number) {
        LinkedHashSet<Integer> coll = collectionsMap.get(collName);
        if (coll != null) {
            coll.add(number);
        } else {
            log.info("This collection was not created!");
        }

    }

    Set<Integer> findCollectionsDiff(String collName1, String collName2) {
        LinkedHashSet<Integer> coll1 = collectionsMap.get(collName1);
        LinkedHashSet<Integer> coll2 = collectionsMap.get(collName2);
        if ((coll1 != null) && (coll2 != null)) {
            return Stream.of(coll1, coll2).flatMap(Collection::stream).filter(el -> !coll2.contains(el)).collect(Collectors.toSet());
        } else {
            log.info("These collections were not created!");
            return null;
        }
    }

    Set<Integer> findCollectionsCommon(String collName1, String collName2) {
        LinkedHashSet<Integer> coll1 = collectionsMap.get(collName1);
        LinkedHashSet<Integer> coll2 = collectionsMap.get(collName2);
        if ((coll1 != null) && (coll2 != null)) {
            return coll1.stream().filter(coll2::contains).collect(Collectors.toSet());
        } else {
            log.info("These collections were not created!");
            return null;
        }

    }

    void delNumberOfLinesFromCollection(String collName, int number) {
        LinkedHashSet<Integer> coll = collectionsMap.get(collName);
        if (coll != null) {
            int collSize = coll.size();
            if (number <= collSize) {
                for (int i = number; i == 0; i--) {
                    coll.remove(coll.size() - 1);
                }
            } else {
                log.info("The argument is bigger, than collection size");
            }
        } else {
            log.info("This collection was not created!");
        }

    }

    void delElemsFromCollection(String collName) {
        LinkedHashSet<Integer> coll = collectionsMap.get(collName);
        if (coll != null) {
            coll.clear();
        } else {
            log.info("This collection was not created!");
        }

    }

    private void collectOutput(List<Integer> coll) {
        System.out.println("Result collay:");
        for (int s : coll) {
            System.out.println(s);
        }
    }
}
