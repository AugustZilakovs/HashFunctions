package LA3Q2;
import LA3Q1.*;

import java.util.ArrayList;

import static LA3Q1.AugustsDemoHashingWithLinearProbing.*;
public class AugustsDemoHashingWithAllOpenAddressingTechniques {
    public static void main(String[] args){
        myHeader(4,2);
        //get user input
        System.out.println("How many data items?");
        int userIn = input.nextInt();input.nextLine();
        System.out.println("What is the load factor (Recommended <=0.5):");
        lf = input.nextDouble();input.nextLine();
        //calculate starting hash table size
        int x = (int)(userIn*lf);
        double y =((double)userIn/(double)(x+userIn));
        if(y<lf){
            System.out.println(y);
            tableCapacity = checkPrime(userIn+1);
        }else{
            tableCapacity = checkPrime(userIn*2);
        }
        //create hash tables
        hashTable = new AugustsValueEntry[tableCapacity];
        workingHashTable = new AugustsValueEntry[tableCapacity];
        System.out.println("The minimum required table capacity would be:  "+tableCapacity);
        Integer [] q = {7, 14, -21, -28, 35};//[7, 14, -21, -28, 35], [-11, 22, -33, -44, 55]
        System.out.println("The given data set is: ");printArray(q);
        //create nodes for each index
        for(int i=0;i<tableCapacity;i++){
            hashTable[i] = new AugustsValueEntry();
            workingHashTable[i]=new AugustsValueEntry();
        }
        //populate hash tables using different probing methods.
        System.out.println("\nLet's enter each data item from the above to the hash table: ");
        System.out.print("\nAdding data - linear probing resolves collision");
        for(int i=0;i<q.length;i++){
            addValueLinearProbe(q[i]);
        }
        printHashTable();
        emptyHashTable();
        printHashTable();
        System.out.print("\nAdding data - quadratic probing resolves collision");
        for(int i=0;i<q.length;i++){
            addValueQuadraticProbe(q[i]);
        }
        printHashTable();

        emptyHashTable();
        printHashTable();
        System.out.print("\nAdding data - double-hashing resolves collision");
        for(int i=0;i<q.length;i++){
            addValueDoubleHashing(q[i]);
        }
        printHashTable();
        myFooter(4,2);
    }
    public static void addValueDoubleHashing(Integer key){
        int h1 = key%tableCapacity;
        if(h1<0){
            h1+=tableCapacity;
        }
        int p; //probe
        int h2; //h2
        int j=1; //multiplier
        int a=thePrimeNumberForSecondHashFunction(tableCapacity);//get prime number smaller than table capacity
        if(hashTable[h1].getKey()!=-1&&hashTable[h1].getKey()!=-111){//check if starting node is taken
            int b = key%a;
            h2= (a-b);//get h2
            p=h1+(j*h2);//find starting probe value after starting index has collision
            if(p>=tableCapacity){//loop back to start of hash table
                p=p%tableCapacity;
            }
            while(hashTable[p].getKey()!=-1&&hashTable[p].getKey()!=-111){//while the current node is taken
                p+=h2;//calculate new probe position
                if(p>=tableCapacity){//loop back to start of hash table
                    p=p%tableCapacity;
                }
            }
            hashTable[p].setKey(key);//set key to the node
        }
        else{//if node is empty at index h1, add key
            hashTable[h1].setKey(key);
        }
    }
    public static void addValueQuadraticProbe(Integer key){
        items++;
        boolean s = false;//since my function exists peacefully, we must check if a value was added instead
        int hv = Math.floorMod(key,tableCapacity);//get hash value
        if(hashTable[hv].getKey()==-1||hashTable[hv].getKey()==-111){//check if index at hash value is open
            hashTable[hv].setKey(key);//if yes, add key to that index
        }else{//else, iterate through hashtable using p=h1 + j^i
            try {
                int temp;
                for (int j = 1; j < tableCapacity; j++) {
                    temp = (hv + (j * j));
                    if(temp>=tableCapacity){
                        temp=temp%tableCapacity;
                    }
                    if (hashTable[temp].getKey() == -1 || hashTable[temp].getKey() == -111) {
                        hashTable[temp].setKey(key);
                        s=true;//value added
                        break;//when space found, add key to that node and break
                    }
                }
                if(!s){//if value not added, notify user the probing failed
                    System.out.println("\nQuadratic Probing Failed, please use a load factor of <=0.5");
                }
            }catch(Exception e){
                //if load factor is greater than 0.5, catch the expected error
                System.out.println("\nQuadratic Probing Failed, please use a load factor of <=0.5");
            }

        }
    }
    public static void printArray(Integer [] x){
        ArrayList<Integer> temp = new ArrayList<>();
        //put all values of array into an arraylist and print
        for(int i = 0; i<x.length;i++){
            temp.add(x[i]);
        }
        System.out.print(temp);
    }
    public static void emptyHashTable(){//empty hashtable
        hashTable = new AugustsValueEntry[tableCapacity];
        for(int i=0;i<tableCapacity;i++){
            hashTable[i] = new AugustsValueEntry();
        }
    }
    public static int thePrimeNumberForSecondHashFunction(int n){
        //check if number is odd
        if(n%2 !=0){
            n-=2;//subtract 2 to next lowest odd number
        }else{//if number is even, go to previous odd number as all primes are odd
            n--;
        }
        int j;
        //we just need to check half of the n factors
        for (int i = n; i >2; i-=2) {
            if(i%2==0){
                continue;//ignore even numbers
            }
            for(j=3;j<=Math.sqrt(i);j+=2){//start at smallest prime
                if(i%j==0){//check if i and j are factors.
                    break;
                }
            }
            if(j>Math.sqrt(i)){//return prime if it is bigger than root i
                return i;
            }
        }
        return 2;
    }
}
