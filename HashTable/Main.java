/*

Coding Assignment 2 - HASH TABLE IMPLEMENTATION

Name: Patrick Loranger
Student Number: 300112374
Course: CSI 2110

To help with this implementation I got my help from this website, and oracle

- https://howtodoinjava.com/java/collections/hashmap/design-good-key-for-hashmap/
- https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html

*/

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // declaring my variables
    public static int n;
    public static int L;
    public static int bestK;
    public static int[] currX;
    public static int[] bestX;
    public static int[] length;
    public static HashMap<HashMapImplementation, Boolean> hashmap;

    // the pseudocode provided in Figure 1, converted to Java code
    static void BacktrackSolve(int currK, int currS) {
        if (currK > bestK) {
            bestK = currK;
            for (int i = 0; i < currK; i++) {
                bestX[i] = currX[i];
            }
        }
        if (currK < length.length) {
            if (length[currK] <= currS && hashmap.containsKey(new HashMapImplementation(currK + 1, currS - length[currK])) == false) {
                currX[currK] = 1;
                int newS = currS - length[currK];
                BacktrackSolve(currK + 1, newS);
                hashmap.put(new HashMapImplementation(currK + 1, newS), true);
            }
            if (length[currK] <= howMuchSpaceRight(currK, currS) && hashmap.containsKey(new HashMapImplementation(currK + 1, currS)) == false) {
                currX[currK] = 0;
                BacktrackSolve(currK + 1, currS);
                hashmap.put(new HashMapImplementation(currK + 1, currS), true);
            }
        }
    }

    // helper method to figure out how much space is left on the right side of the
    // ferry
    static int howMuchSpaceRight(int currK, int currS) {
        int lengthAllOfCars = 0;
        for (int i = 0; i < currK; i++) {
            lengthAllOfCars = lengthAllOfCars + length[i];
        }
        int spaceOccupiedLeft = L - currS;
        int spaceOccupiedRight = lengthAllOfCars - spaceOccupiedLeft;
        return L - spaceOccupiedRight;
    }

    // my main method. this includes the initialization and the output
    public static void main(String[] args) {

        /*
        // FOR MY TESTING
        Scanner opening = new Scanner(System.in);
        System.out.print("Please enter the name of the file you would like to open: ");
        String filename = opening.nextLine();
        opening.close();
        File temp = new File(filename);
        Scanner scanner = new Scanner(temp);
        */
        // FOR ONLINE JUDGE
        Scanner scanner = new Scanner(System.in);

        // initializing some variables
        n = scanner.nextInt();
        scanner.nextLine();
        for(int i = 0; i < n ; i++){
            bestK = -1;
            L = scanner.nextInt() * 100;
            int limit = 1;
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            while(limit != 0){
                int toAdd = scanner.nextInt();
                if(toAdd == 0){
                    limit = toAdd;
                }
                else{
                    tempList.add(toAdd);
                    limit = toAdd;
                }
            }
            length = new int[tempList.size()];
            for(int x = 0; x < tempList.size(); x++){
                length[x] = tempList.get(x);
            }
            int number = length.length;
            bestX = new int[number];
            currX = new int[number];
            hashmap = new HashMap<>(L*number);

            // everything is initialized, time to run the algorithm
            BacktrackSolve(0, L);

            // printing my output
            System.out.println(bestK);
            for(int y = 0; y < bestK; y++){
                if(bestX[y] == 0){
                    System.out.println("starboard");
                }
                if(bestX[y] == 1){
                    System.out.println("port");
                }
            }
            if(i == n-1){
                break;
            }else{
                System.out.println("");
            }
        }
        scanner.close();
    }
}

class HashMapImplementation {

    private int newKey;
    private int newValue;

    public HashMapImplementation(int key, int value) {
        this.newKey = key;
        this.newValue = value;
    }

    @Override
    public int hashCode() {
        return (47 * (newKey)) + (31 * (newValue));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HashMapImplementation other = (HashMapImplementation) obj;
        if (newKey != other.newKey || newValue != other.newValue) {
            return false;
        }
        return true;
    }
}