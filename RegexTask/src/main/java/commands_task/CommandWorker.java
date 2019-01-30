package commands_task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandWorker {
    Map<String, List<Integer>> arraysMap;


    public CommandWorker() {
        arraysMap = new HashMap<>();
    }

    void createArray(String arrName) {
        arraysMap.put(arrName, new ArrayList<>());
    }

    void addIntoArray(String arrName, int number) {
        arraysMap.get(arrName).add(number);
    }

    List<Integer> findArraysDiff(String arrName1, String arrName2) {
        return arraysMap.get(arrName1).stream().filter(el -> !arraysMap.get(arrName2).contains(el)).collect(Collectors.toList());
    }

    List<Integer> findArraysCommon(String arrName1, String arrName2) {
        return arraysMap.get(arrName1).stream().filter(arraysMap.get(arrName2)::contains).collect(Collectors.toList());
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
