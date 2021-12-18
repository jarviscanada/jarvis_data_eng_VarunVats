## **Introduction**

The grep app in Java Programming language simulates the grep command used in Linux programming language to find a regex pattern the user wants to search. In this project, the simulation is being perfformed considering three input parameters.

1. The regex pattern user wants to search
2. The root directory in which the file is stored
3. The output file name and locationls

As a result, the main function used in this project goes through couple of methods to find the pattern. Once, the pattern is found, the output is further stored in a output.txt file. This text file prints out those lines containing the pattern that user wants to search.

## **Quick Start**

1. Compile and package Java Code:

     
         mvn clean compile package

2. Inspect Compiled Bytecode

         tree target

3. Launch JVM and run the app

Approach 1: Classpath and class files

`java -classpath target/classes ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data /out/grep.txt`

Approach 2: Jar File

`java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt`

## **Implementation**

The implementation of grep app is done in Intellij IDE using MAVEN which contains all necessary dependencies and configurations.
It is further followed by the steps indicated below.

a. Writing a main function to handle CLI arguments
b. Saving arguments to private member variables via getters and setters
c. Using self4j to log the messages

### **1. Pseudocode**

`matchedLines = []`

`for file in listFilesRecursively(rootDir)`

`for line in readLines(file)`

`if containsPattern(line)`

`matchedLines.add(line)`

`writeToFile(matchedLines)`

### **2. Performance Issues**
In terms of memory usage, the primary method to address this concern would require modifying the heap space. Apart from heap space modifications, memory issues can be addressed using Stream APIs. In this scenario, grep app interface needs to be updated by replacing List with Stream.

# **Testing**
The testing was performed by using sample test file placed in root directory. A very famous Romeo Juiet pattern was used as a regex pattern and inputted onto Shakespeare file for testing purposes. The result successfully returns those lines containing the pattern.

## **Deployment**
The java application is dockerized using docker and finally the docker image is being built and pushed onto dockerhub for verification purposes.

## **Improvements:**

1. Improve the method for searching files
2. More clear understanding of Stream APIs
3. More clear understanding of MAVEN as project building tool


