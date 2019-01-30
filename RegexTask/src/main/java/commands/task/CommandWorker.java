package commands.task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandWorker {
    Map<String, LinkedHashSet<Integer>> arraysMap;


    public CommandWorker() {
        arraysMap = new HashMap<>();
    }

    void createArray(String arrName) {
        arraysMap.put(arrName, new LinkedHashSet<>());
    }

    void addIntoArray(String arrName, int number) {
        arraysMap.get(arrName).add(number);
    }

    Set<Integer> findArraysDiff(String arrName1, String arrName2) {
        return Stream.of(arraysMap.get(arrName1), arraysMap.get(arrName2)).flatMap(Collection::stream).filter(el -> !arraysMap.get(arrName2).contains(el)).collect(Collectors.toSet());
    }

    Set<Integer> findArraysCommon(String arrName1, String arrName2) {
        return arraysMap.get(arrName1).stream().filter(arraysMap.get(arrName2)::contains).collect(Collectors.toSet());
    }

    void delNumberOfLinesInArray(String arrName, int number) {
        for (int i = number; i == 0; i--) {
            arraysMap.get(arrName).remove(arraysMap.get(arrName).size() - 1);
        }
    }

    void delElemsArray(String arrName) {
        arraysMap.get(arrName).clear();
    }

    private void arrayOutput(List<Integer> arr) {
        System.out.println("Result array:");
        for (int s : arr) {
            System.out.println(s);
        }
    }
}
