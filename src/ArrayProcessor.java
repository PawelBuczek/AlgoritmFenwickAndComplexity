import java.util.*;

public class ArrayProcessor {

    public static List<Integer> processArray(int[] inputNumbers) {
        LinkedList<Integer> results = new LinkedList<>();
        ArrayList<Integer> bigPositiveValues = new ArrayList<>();

        for (int i : inputNumbers) {
            if (i < 0) {
                results.add(i);
            } else if (i > 0) {
                if (i <= results.size()) {
                    if (i <= 255 ) {
                        results.remove(i - 1);
                    } else {
                        bigPositiveValues.add(i);
                    }
                }
            }
        }

        List<Integer> indicesToRemove = computeIndicesToRemove(results.size(), bigPositiveValues);

        Iterator<Integer> resultIterator = results.iterator();
        int currentIndex = 0;
        int removeIndexPointer = 0;

        while (resultIterator.hasNext() && removeIndexPointer < indicesToRemove.size()) {
            resultIterator.next();
            if (currentIndex + 1 == indicesToRemove.get(removeIndexPointer)) {
                resultIterator.remove();
                removeIndexPointer++;
            }
            currentIndex++;
        }

        return results;
    }

    // method should transform the given indices in such a way that they can be removed in order from the list in one iteration
    public static List<Integer> computeIndicesToRemove(int originalRemovalListLength, List<Integer> originalIndicesToRemove) {
        List<Integer> indicesToBeRemoved = new ArrayList<>();

        for (int idx : originalIndicesToRemove) {
            if (idx > originalRemovalListLength) continue;

            int insertPos = Collections.binarySearch(indicesToBeRemoved, idx);
            if (insertPos < 0) {
                insertPos = -insertPos - 1;
            } else {
                insertPos = insertPos + 1;
            }

            int adjustedIndex = idx + insertPos;
            if (adjustedIndex <= originalRemovalListLength) {
                indicesToBeRemoved.add(insertPos, adjustedIndex);
            }
        }

        return indicesToBeRemoved;
    }

}
