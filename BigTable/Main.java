/*

Coding Assignment 2 - BIG TABLE IMPLEMENTATION

Name: Patrick Loranger
Student Number: 300112374
Course: CSI 2110

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

    // declaring my variables
    public static int n;
    public static int L;
    public static int bestK;
    public static int[] currX;
    public static int[] bestX;
    public static int[] length;
    public static boolean[][] visited;

    // the pseudocode provided in Figure 1, converted to Java code
    static void BacktrackSolve(int currK, int currS) {
        if (currK > bestK) {
            bestK = currK;
            for (int i = 0; i < currK; i++) {
                bestX[i] = currX[i];
            }
        }
        if (currK < length.length) {
            if (length[currK] <= currS && visited[currK + 1][currS - length[currK]] == false) {
                currX[currK] = 1;
                int newS = currS - length[currK];
                BacktrackSolve(currK + 1, newS);
                visited[currK + 1][newS] = true;
            }
            if (length[currK] <= howMuchSpaceRight(currK, currS) && visited[currK + 1][currS] == false) {
                currX[currK] = 0;
                BacktrackSolve(currK + 1, currS);
                visited[currK + 1][currS] = true;
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
    public static void main(String[] args) throws FileNotFoundException {

        // FOR MY TESTING
        Scanner opening = new Scanner(System.in);
        System.out.print("Please enter the name of the file you would like to open: ");
        String filename = opening.nextLine();
        opening.close();
        File temp = new File(filename);
        Scanner scanner = new Scanner(temp);

        // FOR ONLINE JUDGE
        //Scanner scanner = new Scanner(System.in);

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
            visited = new boolean[length.length + 1][L + 1];
            bestX = new int[length.length];
            currX = new int[length.length];

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