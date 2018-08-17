//
//  Created by Isaias Perez
//  ====================
//  Skip Lists
//
//  Reads from text file a set of commands, one per line.
//  Commands: i, s, d, p (insert, search, delete, and print respectevly)
//  All but the print command are expected as the following format: i 24
//  Command should be followed by a space and an integer.
//  Insert/Delete/Search operations achieved in O(log n) best.
//  To run: java SkipList commands.txt or java SkipList commands.txt x (seed set to 42)

    import java.io.File;
    import java.util.Scanner;
    import java.util.Random;
    import java.lang.Math;
    
public class SkipLists {

    public static void main(String[] args) {
        
        
        // Terminal Input Setup
        if (args.length > 0) {
            
            // Random number seed
            long seed = 42;
            int epoch = 0;
            File fileName = new File(args[0]);
            
            // 2nd command line argument sets the random generators seed to 42
            if (args.length == 2) {
                epoch = 1;
            }
        SkipLists mClass = new SkipLists();
        skipList list = new SkipLists().new skipList();
            
        mClass.readFile(fileName, list, epoch);
        
        } else {
            System.err.println("Input file not specified!, try again");
        }
        
    }
    
    // ENTRY NODE //
    public class Node {
        
        public String key; 
        public long value;
        
        public Node left;
        public Node right;
        public Node up;
        public Node down;
        
        // Initializing Node values
        public Node(long value){
            this.left = null;
            this.right = null;
            this.up = null;
            this.down = null;
            this.value = value;
        }
    }
    
    
    // SKIPLIST //
    public class skipList {
        public long posInf = 100000000;
        public long negInf = -100000000;
        
        public Node head, tail;
        public int size, maxLevel;
        public long flip;
        
        // Initializing SkipList 
        public skipList() {
            Node nInf = new Node(negInf);
            Node pInf = new Node(posInf);
            
            nInf.right = pInf;
            pInf.left = nInf; 
            
            this.head = nInf;
            this.tail = pInf;
            this.maxLevel = 1;
            this.size = 0;
            
        }
        
        // Creates empty level
        public void addLevel() {
            Node newnInf = new Node(this.negInf);
            Node newpInf = new Node(this.posInf);
            
            // Linking to lower level
            newnInf.down = head;
            newnInf.right = newpInf;
            newpInf.down = tail;
            newpInf.left = newnInf;
            head.up = newnInf;
            tail.up = newpInf;
            
            // Update logic markers
            head = newnInf;
            tail = newpInf;
            maxLevel++;
        }
 
    }
    
    // READS INPUT FILE AND EXECUTES COMMANDS //
    public void readFile(File fileName, skipList list, int epoch) {
        
        try {
            // Random sequence
            Random randomizer = new Random();
            if (epoch == 0) {
                randomizer.setSeed(42);
            }
            
            Node tmp; 
            Scanner scan = new Scanner(fileName);
            System.out.println("For the input file named " + fileName);
            if (epoch == 0) {
                System.out.println("With the RNG unseeded,");
            } else {
                System.out.println("With the RNG seeded,"); 
            }                    
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] command = line.split(" ");
                
                switch(command[0]) {
                    case "i":   // Insert to SkipList
                        insert(list, Integer.parseInt(command[1]), randomizer);
                        break;
                    case "s":   // Search through SkipList
                        tmp = search(list, Integer.parseInt(command[1]));
                        if (tmp.value == Integer.parseInt(command[1])) {
                            System.out.println(tmp.value + " found");
                        } else {
                            System.out.println(command[1] + " NOT FOUND");  
                        }
                        break;
                    case "d":   // Delete from SkipList
                        delete(list, Integer.parseInt(command[1]));
                        break;
                    case "p":   // Print all values of SkipList
                        printList(list);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    // FINDS THE NODE WITH VALUE <= KEY AT LOWEST LEVEL //
    public Node search(skipList list, int key) {
        Node x = list.head;
        
        // Traverse Levels, top to bottom
        for (int y = list.maxLevel; y > 0; y--) {
            // Traverse Level
            while (x.right.value != list.posInf && x.right.value <= key) {
                x = x.right;
            }
            // Attempt to go down the levels
            if (x.down != null) {
                x = x.down;
            } else { 
                break; // Reached bottom level
            }
        }
        return x;
    }
    
    
    // INSERTS NEW VALUE INTO SKIPLIST, DISCARDS DUPLICATE ATTEMPTS //
    public void insert(skipList list, int value, Random randomizer) {
        Node newEntry = new Node(value);
        Node current = search(list, value);
        int stackHeight;
        
        // Already in skipList, discard. Add otherwise
        if (current.value != value) {
            
            newEntry.left = current;
            newEntry.right = current.right;
            current.right = newEntry;
            newEntry.right.left = newEntry;
            
            // Promote node randomly
            stackHeight = 1; 
            while ((randomizer.nextInt() % 2) == 1) {

                // Add level if necessary
                if (stackHeight >= list.maxLevel) {
                    list.addLevel();
                }
                
                // From current position, climb up
                while (current.up == null) {
                    current = current.left;
                }
                current = current.up;
                
                // New level Linkeage 
                Node levelUp = new Node(value);
                levelUp.left = current;
                levelUp.right = current.right;
                current.right.left = levelUp;
                current.right = levelUp;
                levelUp.down = newEntry;
                newEntry.up = levelUp;
                
                // Link in case value gets promoted again
                newEntry = levelUp;
                
                stackHeight++;
            }
            
            // Update logic markers
            list.size++;
            if (stackHeight > list.maxLevel) {
                list.maxLevel = stackHeight;
            }
        }
    }
    
    // PRINTS ALL VALUES IN CURRENT SKIPLIST //
    public void printList(skipList list) {
        Node current = list.head;
        
        // Level 1 contains all values in list, level down
        while (current.down != null) {
            current = current.down;
        }
        
        // At level 1, traverse level and print each node stack
        System.out.println("the current Skip List is shown below:");
        System.out.println("---infinity");
        while (current.value != list.tail.value) {
            current = current.right;
            if (current.value != list.posInf) { 
                printStack(current);
            }
        }
        System.out.println("+++infinity");
        System.out.println("---End of Skip List---");
    }
    
    // HELPS PRINT THE SKIPLIST, PRINT ONLY ONE STACK //
    public void printStack(Node stackBottom) {
        System.out.print(" " + stackBottom.value + "; ");
        if (stackBottom.up == null) {
            System.out.print("\n");
        }
        while (stackBottom.up != null) {
            stackBottom = stackBottom.up;
            System.out.print(" " + stackBottom.value + "; ");
            if (stackBottom.up == null) {
                System.out.print("\n");
                break;
            }
        }
    }

    // DELETES VALUE FROM ALL LEVELS IF IT EXISTS //
    public void delete(skipList list, int key) {
        Node current = search(list, key);
        if (current.value == key) {
            while (current != null) {
                current.left.right = current.right;
                current.right.left = current.left;
                current = current.up;
            }
            System.out.println(key + " deleted");
            list.size--;
        } else {
            System.out.println(key + " integer not found - delete not successful");
        }
    }
    
}





