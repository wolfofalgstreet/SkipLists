# SkipList

## 1. Objective

Build a skip list data structure to support the traversal, searching, addition, and deletion of
integers from a skip list. This implementation will support building a skip list to support
some number of occurrences of integers in the range of 1 to 1000. The objective of this
program requires the reading of an input file which contains commands and data to build,
search, modify, and print a skip list containing integers.

## 2. Requirements

Program will read the input file formatted as follows. The input file will contain at least one command
per line, either insert, delete, search, or print. (The program will terminate upon successfully
reading the last command in the input file.) The commands are defined in detail below. The
second parameter, if appropriate, will always be an integer for this assignment. There will
be only one parameter per command. For example, there will only be a single integer for
the insert, search, or delete commands. In the event that no parameter is specified for these
commands, it is acceptable to ignore the command and continue to process the input file.
No parameter is required for a print command.

#### Commands:

| Command| Description |                Parameter(s)              |
| ------ |:------:| ---------------------------------------------:|
| **i**  | Insert | expects a space, followed by a single integer |
| **s**  | Search | expects a space, followed by a single integer |
| **d**  | Delete | expects a space, followed by a single integer |
| **p**  | Print  | **does not** expect any additional data       |

### 2.1 Design Constraints

1. The input file(s) provided will have the following properties.
  * Each record in the input file will consist of a command, described above, appropriately
followed by an integer.

  * In the event there are multiple insert commands, those insert commands will be
for integers where the inserted integers are in **any order**. There will be one
integer per insert command.

  * Given that the integers will be arriving in any order it is required to implement,
per Pugh’s design, a −∞ and +∞. *(It is acceptable to use a high magnitude
negative integer as the −∞ sentinel, and likewise for +∞ a very high magnitude
positive integer. This mechanism will prevent always creating a new highest/lowest
node. For example consider inserting the following integers, 1, 2, 3, 4, 5, ... etc
in ascending arrival order, without the +∞ sentinel every one of those integers
would incorrectly become the highest value.)*

  * Duplicate insertions are not allowed and can be discarded.

2. There will be a no **arbitrary maximum** of list levels. It will be dependent upon the
randomness of the fair coin.

3. The test input integers will be within the range of 1 to 1000.

4. There is no requirement for persistence. The data stored in the skip list does not need
to be stored.

### 2.2 Execution

The commands are in an input file, described previously, and listed in more detail later. The
program expects the following command line usage:

```
java Hw02 inputfilename [rR]
```

* Where `inputfilename` is the filename containing the command list.

* And `r` or `R` is the optional parameter to cause the random number generator (RNG)
to be seeded with a unique seed. This unique number is typically the milliseconds
since the beginning of the epoch. If the parameter is not specified, the RNG should
be seeded with the integer 42. Using the same seed value will produce a consistent
sequence of random numbers, therebey streamlining troubleshooting and testing.

### 2.3 Execution Commands

#### Insert

---

The insert command uses the single character `i` as the command token. The command token
will be followed by a single space, then a single integer. This integer will then be inserted
into the skip list. Note that a skip list requires that data be inserted into the skip list in
ascending order.

1. Insertion of the lowest rank integer requires that this integer becomes the first element
in the skip list after the −∞ or equivalent sentinel.
2. Insertion of an integer requires a probabilistic mechanism to decide if this integer will
also be on the next higher level. The method chosen is flipping a fair
coin. The flipping of a fair coin can be emulated using the Random object in Java to
generate a “random number” then taking that number modulo 2 to generate a 1 or
2
0. The promotion method used to generate the test cases used a 1 to represent heads
and correspondingly a 0 to represent tails. Each flip of the coin that produces a heads
causes that integer to be promoted and appropriately linked into the skip list. This
process is terminated when a tails has been “flipped”.
3. In the event that a new skip level has been “flipped” the new level will be added
accordingly.1 This process would then connect to the beginning and ending sentinels
(−∞ and +∞) appropriately.
4. Insertion of the highest rank integer requires that this integer becomes the element in
the skip list before the +∞ or equivalent sentinel.
5. In the event that an integer is being inserted that already exists, it is acceptable to
discard the integer without notice and continue to process the input file. (This will
prevent duplicate entries in the skip list.)

*Input* - `i xx` where xx is an integer between 1 and 1000.

*Outputs* - N/A

#### Delete

---

The delete command uses the single character `d` as the command token. The command
token will be followed by a single integer. In order to successfully delete an entry from the
skip list, the intger must be found.
In the event that the integer cannot be found, the program will issue the error message
"xx integer not found - delete not successful", where xx is the specified integer. The
program will recover gracefully to continue to accept commands.
Once the integer is found, it will be deleted from the base level, and any additional
level(s) that the integer had been promoted to upon insertion.

*Input* - `d xx` where xx is an integer between 1 and 1000.

*Outputs* 
* (Success) "xx deleted", where xx is the integer being deleted
* (Failure) "xx integer not found - delete not successful", where xx is the integer being deleted
was not found


#### Search

---

The search command uses the single character `s` as the command token. The command
token will be followed by a single space, then the integer that is to be searched
The search command will take advantage of the skip list structure and implement the
following algorithm. A search for a target element begins at the head element in the top list,
and proceeds horizontally until the current element is greater than or equal to the target. If
the current element is equal to the target, it has been found. If the current element is greater
than the target, or the search reaches the end of the linked list, the procedure is repeated after
returning to the previous element and dropping down vertically to the next lower list.2
([See
also Pugh’s paper](https://github.com/VegaIsaias/SkipLists/blob/master/pugh90b-skipList.pdf))
Upon completion of the search for the target integer, the following messages shall be
output.

*Input* - `s xx` where xx is an integer between 1 and 1000.

*Outputs*
* (Success) "xx found", where xx is the integer being searched for
* (Failure) "xx NOT FOUND", where xx is the integer being searched for and was not found

#### Print

---

The print command uses the single character `p` as the command token. This command will
invoke the *printAll* function described in detail below.
This command is critical for verification of all the commands specified above.

*Input* - p

*Outputs*

```
$java Hw02 bogusTestFile.txt
For the input file named bogusTestFile.txt
With the RNG unseeded,
the current Skip List is shown below:

10; 10; 10; 10; 10; 10; 10
20
30; 30; 30
40
50; 50; 50
60
70; 70; 70; 70; 70; 70; 70
---End of Skip List---
```

## 3. Test Files


| Input File| Description |     Expected Output File              |
| ------ |:------:| ---------------------------------------------:|
| H.2in-a1.txt  | 10 numbers input, with a print command | H2ExpectedOutputa1.txt |
| H.2in-a2.txt  | 25 numbers input, with a print command | H2ExpectedOutputa2.txt |
| H.2in-a3.txt  | 50 numners input, with a print command | H2ExpectedOutputa3.txt |
| H.2in-a4.txt  | 100 numbers input, with a print command | H2ExpectedOutputa4.txt |
| H.2in-a5.txt  | 25 numbers input, with print & valid/invalid delete commands | H2ExpectedOutputa5.txt |
| H.2in-a6.txt  | 25 numbers input, with print, valid delete, valid search, duplicate integer inserts & print commands | H2ExpectedOutputa6.txt |
| H.2in-a7.txt  | 25 numbers input, with print & valid/invalid search commands | H2ExpectedOutputa7.txt |



