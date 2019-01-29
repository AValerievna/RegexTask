import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandWorker {


    public CommandWorker() {
    }

    ArrayList<Integer> createArray() {
        return new ArrayList<>();
    }

    ArrayList<Integer> addIntoArray(ArrayList<Integer> arr, int number) {
        arr.add(number);
        return arr;
    }

    ArrayList<Integer> findArraysDiff(ArrayList<Integer> firstList, ArrayList<Integer> secondList) {
        firstList.removeAll(secondList);
        return firstList;
    }

    List<Integer> findArraysCommon(ArrayList<Integer> firstList, ArrayList<Integer> secondList) {
        return firstList.stream().filter(secondList::contains).collect(Collectors.toList());
    }

    ///REWRITE!!!
    ArrayList<Integer> delNumberOfLinesInArray(ArrayList<Integer> arr, int number) {
        arr.add(number);
        return arr;
    }

    ///REWRITE!!!
    ArrayList<Integer> delElemsArray(ArrayList<Integer> arr) {
        return arr.rem;
    }

    private void arrayOutput(ArrayList<Integer> arr) {
        System.out.println("Result array:");
        for (int s : arr) {
            System.out.println(s);
        }
    }
}
