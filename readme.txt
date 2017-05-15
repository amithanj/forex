The folder deliverables contain following folders and files -

1)src
This folder contains all the java source code and JUnit test cases.

2)config
This folder contains .properties files. These files contain forex details, currencies configuration used in the java code.

3)executable
This folder contains config folder containing configuration properties file(same as mentioned in the above point 2).
It also contains shell script and bat file to execute the FX Calculator.
A lib folder contains the jar file containing binaries.


To run the FX Calculator

1) Go to executables folder
2) Change the permission on script exchange.sh to give execute permission. Run the following command for this
	chmod u+x exchange.sh
3) Make sure config folder is located in the same folder from where this is being run.
4)Run the script exchange.sh with arguments
./executable <Currency 1> <Amount> in <Currency 2> 
Here Currency 1 is base currency
     Currency 2 is term currency
     Amount is amount to be converted

Example -
./exchange.sh AUD 10 in JPY

executable.bat has been provided for windows OS.
