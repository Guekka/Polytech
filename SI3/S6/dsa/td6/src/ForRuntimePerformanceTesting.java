package ads.poo2.lab5.sorting;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.*;
import java.util.logging.Formatter;

public class ForRuntimePerformanceTesting {

    private static final Logger LOGGER = Logger.getLogger(ForRuntimePerformanceTesting.class.getName());


    static {
        LOGGER.setLevel(Level.INFO);
        Logger mainLogger = Logger.getLogger("ads.poo2.lab5");
        mainLogger.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        Formatter formatter = new Formatter() {
            private static final String FORMAT = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            public String format(LogRecord log) {
                return String.format(FORMAT,
                        new Date(log.getMillis()),
                        log.getLevel().getLocalizedName(),
                        log.getMessage());
            }

        };

        handler.setLevel(Level.INFO);
        // Affecter le formatter personnalisé au handler
        handler.setFormatter(formatter);

        // Ajouter le handler au logger
        mainLogger.addHandler(handler);
        LOGGER.addHandler(handler);
        LOGGER.fine("Logger initialized : " + LOGGER.getName());
    }
    record MinFor(long duration, String identifier) {}

    private static MinFor minFor(Long[] durations, String[] identifiers) {
        if (durations.length != identifiers.length) {
            throw new IllegalArgumentException("The two arrays must have the same length" + Arrays.toString(durations) + " : " + Arrays.toString(identifiers) );
        }
        LOGGER.info(() -> "durations : " + Arrays.toString(durations) + " for  identifiers : " + Arrays.toString(identifiers));

        long min = Long.MAX_VALUE;
        String minIdentifier = "";

        for (int i = 0; i < durations.length; i++) {
            if (durations[i] < min) {
                min = durations[i];
                minIdentifier = identifiers[i];
            }
        }
        return new MinFor(min, minIdentifier);
    }



    public static <T extends Comparable<T>> void sortByJava(T[] array) {
        Arrays.sort(array);
    }

    public static final int NUMBER_OF_ITERATIONS = 10;
    public static <T> long timeFor(Consumer<T[]> sortFunction, T[] array, String message) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++){
            sortFunction.accept(array);
        }
        long end = System.currentTimeMillis();
        long duration = end - begin;
        LOGGER.info(() -> message+ " : runtime for " + NUMBER_OF_ITERATIONS + " iterations : " + duration + " ms" );
        return duration;
    }

    private static final Random RANDOM = new Random();

    private static Integer[]  generateRandomArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(size);
        }
        return array;
    }

    private static Integer[] generateSortedArray(int size) {
        Integer[] sortedArray = new Integer[size];
        for (int j = 0; j < size; j++) {
            sortedArray[j] = j;
        }
        return sortedArray;
    }


    record DurationFor(Consumer<Integer[]> consumer, long durationForSortedArray, long durationForReverseSortedArray, long durationForRandomArray) {}


    private static Integer[] copy(Integer[] array){
        return Arrays.copyOf(array, array.length);
    }

    //Quick sort fails on 1000000 elements the stack is too deep
    public static void main(String[] args)  {
        LOGGER.setLevel(Level.INFO);

        int arraySize = 10000;
        Integer[] sortedArray = generateSortedArray(arraySize);
        Integer[]  reverseSortedArray = Arrays.stream(sortedArray).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);
        Integer[] randomArray = generateRandomArray(arraySize);


        //initialize the array of consumers (algorithms to test)
        String[] algoNamesToTest = new String[]{
                "MergeSort::sort",
                "QuickSort::sort",
                "ForRuntimePerformanceTesting::sortByJava",
                "SimpleSorting::selection",
                "SimpleSorting::insertion",
                "HeapSort::sort"};
        Consumer<Integer[]> [] algoToTestConsumer = new Consumer[algoNamesToTest.length];
        algoToTestConsumer[0] = MergeSort::sort;
        algoToTestConsumer[1] = QuickSort::sort;
        algoToTestConsumer[2] = ForRuntimePerformanceTesting::sortByJava;
        algoToTestConsumer[3] = SimpleSorting::selection;
        algoToTestConsumer[4] = SimpleSorting::insertion;
        //Tests on heapsort includes building the heap... not so good !! 
        algoToTestConsumer[5] = HeapSort::sort;


        //initialize the map of averages
        Map<String, DurationFor> averages = new HashMap<>();
        int i = 0;
        for(String eachAlgo : algoNamesToTest) {
            averages.put(eachAlgo, new DurationFor(algoToTestConsumer[i],0, 0, 0));
            i++;
        }

        //test the algorithms

        for(Map.Entry<String, DurationFor> algoEntry : averages.entrySet()) {
            LOGGER.info(">>>> Testing " + algoEntry.getKey());

            //sorted array
            long localtime = timeFor(algoEntry.getValue().consumer(), copy(sortedArray), algoEntry.getKey() + " on sorted array: ");
            long recordedValue = algoEntry.getValue().durationForSortedArray();
            algoEntry.setValue(new DurationFor(algoEntry.getValue().consumer(), recordedValue + localtime, algoEntry.getValue().durationForReverseSortedArray(), algoEntry.getValue().durationForRandomArray()));
            LOGGER.info(()->"Average for " + algoEntry.getKey() + " on sorted array: " + algoEntry.getValue().durationForSortedArray()/10);

            //reverse sorted array
            localtime = timeFor(algoEntry.getValue().consumer(), copy(reverseSortedArray), algoEntry.getKey() + " on reverse sorted array: ");
            recordedValue = algoEntry.getValue().durationForReverseSortedArray();
            algoEntry.setValue(new DurationFor(algoEntry.getValue().consumer(), algoEntry.getValue().durationForSortedArray(), recordedValue + localtime, algoEntry.getValue().durationForRandomArray()));
            LOGGER.info(()->"Average for " + algoEntry.getKey() + " on reverse sorted array: " + algoEntry.getValue().durationForReverseSortedArray()/10);

            //random array
            localtime = timeFor(algoEntry.getValue().consumer(), copy(randomArray), algoEntry.getKey() + " on random array: ");
            recordedValue = algoEntry.getValue().durationForRandomArray();
            algoEntry.setValue(new DurationFor(algoEntry.getValue().consumer(), algoEntry.getValue().durationForSortedArray(), algoEntry.getValue().durationForReverseSortedArray(), recordedValue + localtime));
            LOGGER.info(()->"Average for " + algoEntry.getKey() + " on random array: " + algoEntry.getValue().durationForRandomArray()/10);

        }


        List<Long> durations = new ArrayList<>();

        //find the best algorithm for each array type
        Arrays.stream(algoNamesToTest).parallel().forEach(algo -> durations.add(averages.get(algo).durationForSortedArray()));

        MinFor minForSortedArray = minFor(durations.toArray(new Long[0]), algoNamesToTest);
        durations.clear();
        for (String algo : algoNamesToTest){
            durations.add(averages.get(algo).durationForReverseSortedArray());
        }
        MinFor minForReverseSortedArray = minFor(durations.toArray(new Long[0]), algoNamesToTest);
        durations.clear();

        for (String algo : algoNamesToTest){
            durations.add(averages.get(algo).durationForRandomArray());
        }
        MinFor minForRandomArray = minFor(durations.toArray(new Long[0]), algoNamesToTest);
        durations.clear();


        System.out.println("min for sorted array: " + minForSortedArray);
        System.out.println("min for reverse sorted array: " + minForReverseSortedArray);
        System.out.println("min for random array: " + minForRandomArray);

    }



}
