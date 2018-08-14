import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * 
 * Lamport Algorithm
 * 
 * @author Miles & Viraj
 * 
 *
 */
public class mylamportimpl {

    public static void main(String args[]) {
        String matrix[][];
        int row, column;
        int matrix2[][];
        boolean choice = false;

            
        do
        {
            Scanner s = new Scanner(System.in);
            System.out.println("Would you like to run part 1 or part 2 (enter 1 or 2)\n");
            int n = s.nextInt();
            if(n == 1) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter number of processes, should be less than 5 and more than 1: ");
                int noOfProcesses = scanner.nextInt();
    
                validateNoOfProcesses(noOfProcesses, scanner);
    
                row = noOfProcesses;
                System.out.println("\nMaximum number of columns(Events) :");
                column = scanner.nextInt();
                validateNoOfEvents(column, scanner);
                matrix = new String[row][column];
                System.out.println("\nEnter the data :");
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        matrix[i][j] = scanner.next();
                    }
                }
                display(row, column, matrix);
                evaluteTheAlgorithm(matrix);
                    
            }
            else if (n == 2)
            {
                
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("\nEnter number of processes, should be less than 5 and more than 1: ");
                int noOfProcesses = scanner2.nextInt();
                validateNoOfProcesses(noOfProcesses, scanner2);
                row = noOfProcesses;
                System.out.println("\nMaximum number of columns(Events) :");
                column = scanner2.nextInt();
                validateNoOfEvents(column, scanner2);
                matrix2 = new int[row][column];
                System.out.println("\nEnter the data :");
                
                
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        matrix2[i][j] = scanner2.nextInt();
                    }
                }
                display(row, column, matrix2);
                evaluatePart2(matrix2, row, column);
                
            }
            else 
            {
                System.out.println("Please only (enter a 1 or 2)\n");
                choice = true;
            }
            
        }while(choice);
    }
    /**
     * Validate the No Of Columns(Events) in the Matrix 1-25 is permissible
     * 
     * @param column
     * @param scanner
     */
    private static void validateNoOfEvents(int column, Scanner scanner) {
        boolean valid = true;
        if (column < 1 || column > 25) {
            valid = false;
            System.out.println(
                    "\nInvalid input, number of Events should be atleast 1 and not more than 25, Input Again...");
        }

        if (!valid) {
            System.out.println("\nYour program will restart, Enter the data again");
            String key = scanner.nextLine();
            String[] args = {};
            if (key.equals("")) {
                mylamportimpl.main(args);
            }
        } else {
            System.out.println("\nYou have choosen the No of Maximum Events to be " + column);
        }
    }

    private static void evaluatePart2(int[][] matrix2, int row, int column) {
//        int rowLength = matrix.length;
//        int columnLength = matrix[0].length;
        //System.out.println("Rows:" + rowLength + "column:" + columnLength);
        String outputMatrix[][] = new String [row][column];
        //ZeroStep(matrix2, rowLength, columnLength, outputMatrix);
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < column; j++)
            {
                outputMatrix[i][j] = "NULL";
            }
        }
        
        evaluateTwo(matrix2, outputMatrix, row, column);
        
        System.out.println("\n\nPrinting the new outputMatrix!");
        display(row, column, outputMatrix);
    }
    
    private static void evaluateTwo(int[][] matrix2, String[][] outputMatrix, int row, int col)
    {
        String[] internal = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","t","u","v","w","x","y","z"};
        int internalCount = 0;
        boolean found = false;
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++ )
            {
                if(matrix2[i][j] == 0)
                {
                    //then it can be send or internal
                    //this makes it internal for now
                 outputMatrix[i][j] = "NULL";
                }
                //step 1
                if(j == 0 && matrix2[i][j] == 1)
                {
                    outputMatrix[i][j] = internal[internalCount++];
                }
                //this section is for a recieve in the first column STEP 2
                else if(j == 0 && matrix2[i][j] > 1)
                {
                    outputMatrix[i][j] = "r";
                    for(int k = 0; k < row; k++)
                    {
                        for(int l = 0; l < col; l++)
                        {    
                            if(matrix2[k][l] == matrix2[i][j]-1)
                            {
                                outputMatrix[k][l] = "s";
                            }
                            
                        }
                    }
                }
//                //STEP 3
                else if ( j > 0 && matrix2[i][j] > 1 && matrix2[i][j-1]+1 == matrix2[i][j])
                {
                    //then it can be send or internal    
                    //this makes it internal for now
                    if(found)
                    {
                        
                    }
                    else
                    {
                        outputMatrix[i][j] = internal[internalCount++];
                    }
                }
                // STEP 4
                else if ( j > 0 && matrix2[i][j] - matrix2[i][j -1] > 1)
                {
                    outputMatrix[i][j] = "r";
                    //then the receive is 1 more then the corresponding send event 
                        for(int k = 0; k < row; k++)
                        {
                            for(int l = 0; l < col; l++)
                            {    
                                if(matrix2[k][l] == matrix2[i][j] - 1)
                                {
                                    //so then matrix2[i][j] is a send event 
                                    outputMatrix[k][l] = "s";
                                    found = true;
                                    
                                }
                            }
                        }    
                }
            }    
        }// End of for loops 
        
        internalCount = 0;
        for(int i = 0; i < row; i++)
        {
            for(int j = 1; j < col; j++)
            {
                if(outputMatrix[i][j] == "NULL" && matrix2[i][j] != 0)
                {
                    outputMatrix[i][j] = internal[++internalCount];
                }
            }
        }
        
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int r = 0; r < row; r++)
        {
            for(int c = 0; c < col; c++)
            {
                if(outputMatrix[r][c] == "s")
                {
                    list.add(matrix2[r][c]);
                }
            }
        }
    
        Collections.sort(list);

        ArrayList<Integer> rList = new ArrayList<Integer>();
        for(int r = 0; r < row; r++)
        {
            for(int c = 0; c < col; c++)
            {
                if(outputMatrix[r][c] == "r")
                {
                    rList.add(matrix2[r][c]);
                }
            }
        }
        
        Collections.sort(rList);
        
        System.out.print("THE R LIST IS : " + rList);
        System.out.print("THE S LIST IS : " + list);
        
        int listLen2 = rList.size();
        int listLen = list.size();
        int counterS = 0;
    
        int counterR = 0;

    
        int found_index = 0;
        //updating the suffix's for the s events 
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if(counterS < listLen && counterR < listLen)
                {
                    
                    for(int p = 0; p < list.size();p++)
                    {
                        if(list.get(p) == matrix2[i][j])
                        {
                            found_index = p;
                        }
                    }
                    if(matrix2[i][j] == list.get(found_index))
                    {
                        outputMatrix[i][j] = "s" + append(found_index);
                        counterS++;    
                    }
                }
            }
        }//end of loops
    
        //Updating the suffix's for the r events 
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if(counterR < listLen2)
                {
                    for(int p = 0; p < rList.size();p++)
                    {
                        if(rList.get(p) == matrix2[i][j])
                        {
                            found_index = p;
                        }
                    }
                    if(matrix2[i][j] == rList.get(found_index))
                    {
                        outputMatrix[i][j] = "r" + append(found_index);
                        counterR++;    
                    }
                }
            }
        }//end of loops
        
    } //End of evaluateTwo
    
     private static String append(int num)
    {
    
            String test = "NULL";
            switch(num)
            {
            case 0: test = "1";
            break;
            case 1: test = "2";
            break;
            case 2: test = "3";
            break;
            case 3: test = "4";
            break;
            case 4: test = "5";
            break;
            case 5: test = "6";
            break;
            case 6: test = "7";
            break;
            case 7: test = "8";
            break;
            case 8: test = "9";
            default: test = "NULL";
            }
            return test;
    }
    
    /**
     * 
     * @param matrix
     */
    private static void evaluteTheAlgorithm(String[][] matrix) {
        int rowLength = matrix.length;
        int columnLength = matrix[0].length;
        //System.out.println("Rows:" + rowLength + "column:" + columnLength);
        int outputMatrix[][] = new int[rowLength][columnLength];
        int counter = 0;
        // Step 0:
        do {
            // Step 1:
            firstStep(matrix, rowLength, columnLength, outputMatrix);
            //display(rowLength, columnLength, outputMatrix);
            // Step 3:
            thirdStep(matrix, rowLength, columnLength, outputMatrix);
            //display(rowLength, columnLength, outputMatrix);
            // Step 2:
            secondStep(matrix, rowLength, columnLength, outputMatrix);
            //display(rowLength, columnLength, outputMatrix);

            fourthStep(matrix, rowLength, columnLength, outputMatrix);
            // Display the Evaluated Matrix
            //display(rowLength, columnLength, outputMatrix);
            counter++;
        } while (counter < 5 || checkIfZeroIsLeft(rowLength, columnLength, outputMatrix));

        ZeroStep(matrix, rowLength, columnLength, outputMatrix);
        display(rowLength, columnLength, outputMatrix);
    }

    private static void ZeroStep(String[][] matrix, int rowLength, int columnLength, int[][] outputMatrix) {
        // TODO Auto-generated method stub
        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {

                if ((matrix[i][j]).equalsIgnoreCase("NULL")) {
                    outputMatrix[i][j] = 0;
                }
            }
    }

    private static void fourthStep(String[][] matrix, int rowLength, int columnLength, int[][] outputMatrix) {
        // TODO Auto-generated method stub

        int b;
        int lcOfSendEvent;
        for (int i = 0; i < rowLength; i++)
            for (int j = 1; j < columnLength; j++) {

                if ((matrix[i][j]).startsWith("r")) {
                    String event = matrix[i][j];
                    String sendEventToBeSearched = "s" + event.charAt(1);
                    for (int k = 0; k < rowLength; k++) {
                        for (int l = 1; l < columnLength; l++) {
                            if (matrix[k][l].equals(sendEventToBeSearched)) {
                                lcOfSendEvent = outputMatrix[k][l];
                                outputMatrix[i][j] = (b = max(lcOfSendEvent, outputMatrix[k][l - 1]) + 1);
                            }
                        }
                    }

                }

            }
    }

    public static int max(int a, int b) {

        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * @param matrix
     * @param rowLength
     * @param columnLength
     * @param outputMatrix
     */
    private static void firstStep(String[][] matrix, int rowLength, int columnLength, int[][] outputMatrix) {
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                // Step 1: If the first Event is internal or send event
                if (!(matrix[i][0]).startsWith("r")) {
                    outputMatrix[i][0] = 1;
                }
            }
        }
    }

    /**
     * Algorithm:
     * 
     * @param matrix
     * @param rowLength
     * @param columnLength
     * @param outputMatrix
     */
    private static void thirdStep(String[][] matrix, int rowLength, int columnLength, int[][] outputMatrix) {
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (!(j == 0) && !(matrix[i][j]).startsWith("r")) {
                    outputMatrix[i][j] = outputMatrix[i][j - 1] + 1;
                }
            }
        }
    }

    /**
     * Algorithm: 1. Check if the Events in the first column Starts with "r" 2. If
     * yes then check the number associated with it and assign the same to s and
     * store 3. With the Stored event search it in the matrix 4. If found, Get its
     * corresponding value from the output matrix and assign the value + 1 to the r
     * LOC from Step1
     * 
     * @param matrix
     * @param rowLength
     * @param columnLength
     * @param outputMatrix
     */
    private static void secondStep(String[][] matrix, int rowLength, int columnLength, int[][] outputMatrix) {
        int lcOfSendEvent;
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if ((matrix[i][0]).startsWith("r")) {
                    String event = matrix[i][0];
                    String sendEventToBeSearched = "s" + event.charAt(1);
                    for (int k = 0; k < rowLength; k++) {
                        for (int l = 0; l < columnLength; l++) {
                            if (matrix[k][l].equals(sendEventToBeSearched)) {
                                lcOfSendEvent = outputMatrix[k][l];
                                outputMatrix[i][0] = lcOfSendEvent + 1;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if there is any zero left in the Output Matrix
     * 
     * @param rowLength
     * @param columnLength
     * @param outputMatrix
     */
    private static boolean checkIfZeroIsLeft(int rowLength, int columnLength, int[][] outputMatrix) {
        boolean flag = false;
        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (outputMatrix[i][j] == 0) {
                    flag = true;
                    break;
                }
            }
        return flag;
    }

    /**
     * Check if the Number of processes are less than 6 and greater then 1
     * 
     * @param noOfProcesses
     * @param scanner
     */
    private static void validateNoOfProcesses(int noOfProcesses, Scanner scanner) {
        boolean valid = true;
        if (noOfProcesses < 2 || noOfProcesses > 5) {
            valid = false;
            System.out.println(
                    "\nInvalid input,number of processes should be less than 6 and more than 1, Input Again...");
        }

        if (!valid) {
            String key = scanner.nextLine();
            String[] args = {};
            if (key.equals("")) {
                mylamportimpl.main(args);
            }
        } else {
            System.out.println("\nYou have choosen the No of Processes to be " + noOfProcesses);
        }
    }

    /**
     * Display the created Matrix for the process and Events
     * 
     * @param row
     * @param column
     * @param matrix
     */
    static void display(int row, int column, String[][] matrix) {
        System.out.println("\nThe Matrix is :");
        for (int i = 0; i < row; i++) {
            System.out.print("\tP" + i + ":");
            for (int j = 0; j < column; j++) {
                System.out.print("\t" + matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Display the created Matrix for the process and Events
     * 
     * @param row
     * @param column
     * @param matrix
     */
    static void display(int row, int column, int[][] matrix) {
        System.out.println("\nThe Evaluated Matrix is :");
        for (int i = 0; i < row; i++) {
            System.out.print("\tP" + i + ":");
            for (int j = 0; j < column; j++) {
                System.out.print("\t" + matrix[i][j]);
            }
            System.out.println();
        }
    }
}



//Output:

//Would you like to run part 1 or part 2 (enter 1 or 2)

//1

//Enter number of processes, should be less than 5 and more than 1: 
//3

//You have choosen the No of Processes to be 3

//Maximum number of columns(Events) :
//4

//You have choosen the No of Maximum Events to be 4

//Enter the data :

//a s1 r2 b
//c s3 d r1
//e s2 r3 f

//The Matrix is :
//	P0:	a	s1	r2	b
//	P1:	c	s3	d	r1
//	P2:	e	s2	r3	f

//The Evaluated Matrix is :
//	P0:	1	2	3	4
//	P1:	1	2	3	3
//	P2:	1	2	3	4



//Would you like to run part 1 or part 2 (enter 1 or 2)

//2

//Enter number of processes, should be less than 5 and more than 1: 
//3

//You have choosen the No of Processes to be 3

//Maximum number of columns(Events) :
//4

//You have choosen the No of Maximum Events to be 4

//Enter the data :
//1    2    8    9
//1    6    7    0
//3    4    5    6

//The Evaluated Matrix is :
//	P0:	1	2	8	9
//	P1:	1	6	7	0
//	P2:	3	4	5	6
//THE R LIST IS : [3, 6, 8]THE S LIST IS : [2, 5, 7]

//Printing the new outputMatrix!

//The Matrix is :
//	P0:	a	s1	r3	b
//	P1:	c	r2	s3	NULL
//	P2:	r1	c	s2	d

