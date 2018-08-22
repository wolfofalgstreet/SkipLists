# SkipList

## 1. Objective

Build a skip list data structure to support the traversal, searching, addition, and deletion of
integers from a skip list. This implementation will support building a skip list to support
some number of occurrences of integers in the range of 1 to 1000. The objective of this
assignment requires the reading of an input file which contains commands and data to build,
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

**Insert**

The insert command uses the single character i as the command token. The command token
will be followed by a single space, then a single integer. This integer will then be inserted
into the skip list. Note that a skip list requires that data be inserted into the skip list in
ascending order.




