Hello!

Welcome to my Hadoop Depression Frequency algorithm.
All code was written by Eli Asimow between 11,1,2020 and 12,2,2020.
The steps to run the MapReduce function yourself are quite simple. They are:


1) Get Access to the database. The database is from the U.S Department of Health and Human Services and is free for public use. 
IMPORTANT: this is a constantly updated database. New entries will break some of my code's assumptions, especially at the start of 2021.

Here is the link to the data:

https://catalog.data.gov/dataset/indicators-of-anxiety-or-depression-based-on-reported-frequency-of-symptoms-during-last-7-

Luckily the file is relatively small, so it can easily be moved to Hadoop via the steps layed out in the data_ingest directory.

2) Clean code. Now we will run the Cleaning program on our code. This removes all unnecessary columns and any useless rows.
To run the Cleaning program, first compile the jar files via:

$ javac -classpath `yarn classpath`:. -d . CleanMapper.java
$ javac -classpath `yarn classpath`:. -d . CleanReducer.java
$ javac -classpath `yarn classpath`:. -d . Clean.java
$ jar -cvf clean.jar *.class

Then run the program:

$ hadoop jar clean.jar Clean /PATH_TO_DATA_SET  /DESIRED_OUTPUT_PATH/CleanedOutput


If you check the resulting output after running your code, you should see descriptions paired with 'Date as an int, value as a float.'

3) Run the Analytic. First, follow the same steps of creating your jar files:

$ javac -classpath `yarn classpath`:. -d . DepressionStatsMapper.java
$ javac -classpath `yarn classpath`:. -d . DepressionStatsReducer.java
$ javac -classpath `yarn classpath`:. -d . DepressionStats.java
$ jar -cvf depressionStats.jar *.class

Then run Hadoop with your Cleaned Data from Step 2:

$ hadoop jar depressionStats.jar DepressionStats /PATH_TO_PREVIOUSOUTPUT/part-r-00000  /DESIRED_OUTPUT_PATH/FinalResults

Depending on your settings, the name of the cleaned data set can vary. Check the directory of your CleanedOutput and change the line accordingly.

The final output should be some 50 lines of Depression trends for each State, some age ranges, and some ethnicities.

Here is the result of a all categories for reference:

Over all the Categories, best fit line has depression    increasing at a rate of 0.016495258 since April. The highest depression rating was recorded on October-29.0 with value 58.7, and the lowest depression rating was recorded on May-8.0 with value 9.1. 





