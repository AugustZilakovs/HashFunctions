package LA3Q1;

import javax.swing.table.TableCellEditor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AugustsDemoHashingWithLinearProbing {
    //create variables
    public static Integer items=0;
    public static Scanner input = new Scanner(System.in);
    public static double lf;
    public static Integer tableCapacity;
    public static AugustsValueEntry[] hashTable = new AugustsValueEntry[0];
    public static AugustsValueEntry[] workingHashTable = new AugustsValueEntry[0];
    public static void main(String[] args){

        myHeader(4,1);
        try {//get user inputs
            System.out.println("Let's decide on the initial table capacity based on the load factor and dataset size");
            System.out.println("How many data items?");
            int userIn = input.nextInt();input.nextLine();
            System.out.println("What is the load factor (Recommended <=0.5):");
            lf = input.nextDouble();input.nextLine();
            //calculate load factor. Comparing ratios
            int x = (int)(userIn*lf);
            double y =((double)userIn/(double)(x+userIn));
            if(y<lf){
                //System.out.println(y);
                //System.out.println("In first");
                tableCapacity = checkPrime(userIn+1);
            }else{
                //System.out.println("In second");
                tableCapacity = checkPrime(userIn*2);
            }
            //create hashtables
            hashTable = new AugustsValueEntry[tableCapacity];
            workingHashTable = new AugustsValueEntry[tableCapacity];
            System.out.println("The minimum required table capacity would be:  "+tableCapacity);
            for(int i=0;i<tableCapacity;i++){//create a node for every index
                hashTable[i] = new AugustsValueEntry();
                workingHashTable[i]=new AugustsValueEntry();
            }
            for(int i=1; i<=userIn;i++){//get user input for keys
                //System.out.println("Items value: "+items);
                System.out.println("Enter Integer "+i+": ");
                String in = input.nextLine();
                int a = Integer.valueOf(in);
                addValueLinearProbe(a);
            }
            printHashTable();
            System.out.println("\n\nLets remove two items from the table and then add one......");

            for(int i = 0; i<2;i++){//get user input to remove keys
                System.out.print("\nEnter a value you want to remove: ");
                String in = input.nextLine();
                int a = Integer.valueOf(in);
                removeValueLinearProbe(a);
                printHashTable();
            }
            System.out.print("\nEnter a value to add to the table: ");//get user input to add key
            addValueLinearProbe(Integer.valueOf(input.nextLine()));
            printHashTable();

            System.out.println("\nRehashing the table...");//rehash table and print
            reHashingWithLinearProbe();
            printHashTable();

            myFooter(4,1);

        }catch(Exception E){
            System.out.println("Please only enter input of the requested data type");
        }
    }
    public static void reHashingWithLinearProbe(){
        tableCapacity = checkPrime(tableCapacity*2);//find nearest prime twice the size of the current table
        System.out.println("The rehashed table capacity is: "+tableCapacity);
        for(int i = 0;i<workingHashTable.length;i++){//copy hash table into working hash table
            if(hashTable[i].getKey()==(-111)){
                workingHashTable[i].setKey(-1);
                continue;
            }
            workingHashTable[i].setKey(hashTable[i].getKey());
        }
        AugustsValueEntry[] x = new AugustsValueEntry[tableCapacity];//recreate new hashtable
        hashTable = x;
        for(int i=0;i<tableCapacity;i++){
            hashTable[i]= new AugustsValueEntry();//create nodes for each index of new hash table
        }
        for(int i=0; i<workingHashTable.length;i++){//rehash all previously had nodes.
            addValueLinearProbe(workingHashTable[i].getKey());
        }
    }
    public static void removeValueLinearProbe(Integer key){
        items--;
        int index = Math.floorMod(key, tableCapacity);//find remainder
        System.out.println("Removing: "+key);
        for(int i = index; i<tableCapacity;i++){//find key
            if(hashTable[i].getKey()==key){
                hashTable[i].setKey(-111);//if key is found set to available
                return;
            }
        }
        System.out.println("Value not found");//if not found tell user
    }
    public static void printHashTable(){
        System.out.println("\nHash Table: ");
        ArrayList x = new ArrayList();
        for(int i=0; i< tableCapacity;i++){//go through hash table and check what key it has. Then add it to an array list and print
            if(hashTable[i].getKey()==-1){
                x.add("null");
                continue;
            }
            if(hashTable[i].getKey()==-111){
                x.add("available");
                continue;
            }
            x.add(hashTable[i].getKey());
        }
        System.out.println(x);
    }
    public static void myHeader(int a, int b){
        System.out.printf("%15s%n","=========================================================");
        System.out.println("Lab Exercise " + a+ " "+b);
        System.out.println("Prepared By: Augusts Zilakovs" +
                "\nStudent Number: 251223010" +
                "\nGoal of this Exercise: Implement different types of probing in a hash table. ");
        System.out.printf("%15s%n","=========================================================");
    }
    public static int checkPrime(int n) {
        //since in hashing, the prime number has to be greater than 2, we can discard 2; 0 and 1 are not prime numbers by definition
        int m = n / 2;
        //we just need to check half of the n factors
        for (int i = 3; i <= m; i++) {
            if (n % i == 0) {//if n is not a prime number
                i = 2; //reset i to 2 so that it is incremented to 3 in the for
                //System.out.printf("i = %d\n",i);
                n++;//next n value
                m = n / 2;//we just need to check half of the n factors
            }
        }
        return n;
    }
    public static void myFooter(int a, int b){
        System.out.printf("%15s%n","=========================================================");
        System.out.println("Completion of Lab Assignment " + a + ", "+b+" is successful!\nSigning off - Augusts.");
        System.out.printf("%15s%n","=========================================================");
    }
    public static void addValueLinearProbe(Integer key){
        items++;
        int index = key%tableCapacity;
        index = Math.floorMod(key, tableCapacity);
        //System.out.println("Number being operated on: "+Math.abs(key));

        for(int i=index;i<=tableCapacity;i++){
            //System.out.println("Index is: "+index);
            if(hashTable[i].getKey() == -1||hashTable[i].getKey()==-111){
                hashTable[i].setKey(key);
                break;
            }
            if(i==tableCapacity-1){
                i=0;
            }

        }
    }
}
