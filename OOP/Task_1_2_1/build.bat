mkdir Task_1_2_1/bin
javac -sourcepath Task_1_2_1/src -d Task_1_2_1/bin Task_1_2_1/src/main/java/io/github/jokerhasnopersonality/HeapSort.java

mkdir Task_1_2_1/doc
javadoc -d Task_1_2_1/doc -charset utf-8 -sourcepath Task_1_2_1/src/main/java -subpackages io.github.jokerhasnopersonality
