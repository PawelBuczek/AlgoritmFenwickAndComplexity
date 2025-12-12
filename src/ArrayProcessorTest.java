import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    void main() {
        int size = 1_000_000;

        int[] inputNumbers = generateInput(size);

        long start = System.currentTimeMillis();
        List<Integer> result = ArrayProcessor.processArray(inputNumbers);
        long end = System.currentTimeMillis();

        System.out.println("Result size: " + result.size());
        System.out.println("Execution time: " + (end - start) + " ms");

        List<Integer> testResult = ArrayProcessor.processArray(new int[]{-1, -2, -3, 2});
        System.out.println("Test result: " + testResult);  // expected result is: [-1, -3]

        int[] testArray = new int[295];

        for (int i = 0; i < 290; i++) {
            testArray[i] = -(i + 2);
        }

        testArray[290] = 288;
        testArray[291] = 290;
        testArray[292] = 3000;
        testArray[293] = 260;
        testArray[294] = 261;
        List<Integer> hardTestResult = ArrayProcessor.processArray(testArray);
        System.out.println("Hard test result: " + hardTestResult); // expected result should be missing -261, -263 and -289


        List<Integer> bigPositiveValues = new ArrayList<>();
        bigPositiveValues.add(8);
        bigPositiveValues.add(10);
        bigPositiveValues.add(2);
        bigPositiveValues.add(5);
        bigPositiveValues.add(1);
        List<Integer> indicesToRemove = ArrayProcessor.computeIndicesToRemove(10, bigPositiveValues);
        System.out.println("indicesToRemove: " + indicesToRemove); // expected result is: [1, 2, 6, 8]


        List<Integer> anotherBigPositiveValues = new ArrayList<>();
        anotherBigPositiveValues.add(2);
        anotherBigPositiveValues.add(2);
        anotherBigPositiveValues.add(3);
        List<Integer> anotherIndicesToRemove = ArrayProcessor.computeIndicesToRemove(5, anotherBigPositiveValues);
        System.out.println("anotherIndicesToRemove: " + anotherIndicesToRemove); // expected result is [2, 3, 5]


    }

    private static int[] generateInput(int size) {
        Random random = new Random();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            // 60% of values in -255..255 range
            if (random.nextDouble() < 0.6) {
                arr[i] = random.nextInt(511) - 255; // -255 to 255
            } else {
                // occasionally larger values (negative or positive)
                int big = random.nextInt(2_000_000) - 1_000_000;
                arr[i] = big;
            }
        }
        return arr;
    }
