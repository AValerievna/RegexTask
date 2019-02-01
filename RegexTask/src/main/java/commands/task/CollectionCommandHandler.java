package commands.task;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CollectionCommandHandler {
    private Map<String, LinkedHashSet<Integer>> collectionsMap;
    private Logger log;


    public CollectionCommandHandler() {
        collectionsMap = new HashMap<>();
        log = Logger.getLogger(CollectionCommandHandler.class.getName());
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
            return Stream.of(coll1, coll2)
                    .flatMap(Collection::stream)
                    .filter(el -> !(coll2.contains(el) && coll1.contains(el)))
                    .collect(Collectors.toSet());
        } else {
            log.info("These collections were not created!");
            return null;
        }
    }

    Set<Integer> findCollectionsCommon(String collName1, String collName2) {
        LinkedHashSet<Integer> coll1 = collectionsMap.get(collName1);
        LinkedHashSet<Integer> coll2 = collectionsMap.get(collName2);
        if ((coll1 != null) && (coll2 != null)) {
            return coll1.stream()
                    .filter(coll2::contains)
                    .collect(Collectors.toSet());
        } else {
            log.info("These collections were not created!");
            return null;
        }

    }

    Set<Integer> delNumberOfLinesFromCollection(String collName, int number) {
        LinkedHashSet<Integer> coll = collectionsMap.get(collName);
        LinkedHashSet<Integer> deleted = null;
        if (coll != null) {
            int collSize = coll.size();
            if (number <= collSize) {
                List deletedList = new ArrayList<>(coll);
                deletedList = deletedList.subList(collSize - number - 1, collSize - 1);
                coll.removeAll(deletedList);
                deleted = new LinkedHashSet<Integer>(deletedList);
            } else {
                log.info("The argument is bigger, than collection size");
            }
        } else {
            log.info("This collection was not created!");
        }
        return deleted;
    }

    Set<Integer> delElemsFromCollection(String collName) {
        LinkedHashSet<Integer> coll = collectionsMap.get(collName);
        Set<Integer> deleted = null;
        if (coll != null) {
            deleted = new LinkedHashSet<>(coll);
            coll.clear();
        } else {
            log.info("This collection was not created!");
        }
        return deleted;
    }
}
