# Insurance-Claims-Forensics
A report for an insurance company to determine if a recent increase in the number of insurance claims made for flood damage in Greater Victoria region are valid.

**Abstract**:<br/>
The purpose of this lab was to determine if a recent increase in the number of insurance claims made for flood damage in the Greater Victoria region are valid. The method being used here is to gather and analyze the past weather precipitation data from Environment Canada and find out months that are most likely to have heavy rainfalls. After finding out the months with most likely rainfall, we can make the judgment of whether the recent increase in insurance claims made for flood damage are valid or not. The statistics and results retrieved from the analysis of data suggests that the recent increase in the number of insurance claims are invalid because April is a month with relatively lower precipitation compared to other months in the year. 

**Introduction**:<br/>
The purpose of this lab was to determine if a recent increase in the number of insurance claims made for flood damage in the Greater Victoria region are valid and analyze when flooding would likely have occurred in the past based on two days of heavy rainfall. By collecting data from Environment Canada, we can make the conclusion of which months have the most heaviest rainfalls based on past data. Weathers and climate usually follow similar patterns over the years so the conclusions being reached can be representative and decisive for the precipitation pattern of this year, which can have an effect on the number of insurance claims. At the same time, precipitation can be one of the major causes for flooding and analyzing precipitation gives insurance companies insights about whether the flooding claims are real or not. 

**Methodology**:<br/> 
The weather and precipitation data are collected from the Weather Canada in two of the Victoria stations, university and gonzales. The time range of the precipitation data is taken from 1995 January 1st to 2022 March 9th. The statistics are first read and stored in a java program, and a plot generator tool called Ptolemy is used to support the analysis of the data. Two plots and two tables are generated based on the previous precipitation data. The two plots are “Maximum Monthly Rainfall (mm)” and "Maximum Monthly Rainfall Over a 2-Day Period (mm) ", while the two tables are “Top 10 Monthly Rainfall (mm)" and "Top 10 Maximum Rainfall Over a 2-Day Period (mm)". 

Inside the program, ArrayList<Double[]> data type is used to store the precipitation data. All the yearly precipitation data is stored in the Double array and all the years are then stored in the ArrayList<Double[]>. All the different ArrayList<Double[]> are initialized in the constructor of the class EasyPtPlot, and the plotting of the graph using Ptomeley is done in the main method while the tables are generated in the different methods of the class. When processing the data, the data from the two stations are averaged if both stations have the precipitation data. If none of the stations have data, then a value of -1.0 is assigned to the day without the precipitation data. If only one of the stations has data and the other one does not, then the one that has data would be assigned as the average of two stations. The day with an average value of -1.0 is eventually omitted during the calculation process. 

**Result:**<br/>
1, Top 10 Maximum Rainfall Over a 2-Day Period
| Year | Day             | Precipitation |
| ---- |:---------------:| -------------:|
| 2003 | October 16/17   | 104.0mm       |
| 1995 | November 7/8    | 101.4mm       |
| 2021 | November 14/15  | 100.0mm       |
| 2006 | November 5/6    | 84.4mm        |
| 1999 | October 7/8     | 83.7mm        |
| 2010 | December 11/12  | 75.9mm        |
| 2020 | December 21/22  | 71.6mm        |
| 2013 | September 28/29 | 69.3mm        |
| 2015 | November 12/13  | 67.7mm        |
| 2016 | February 14/15  | 66.0mm        |

2, Maximum Monthly Rainfall Over a 2-Day Period<br/>
<p>
  <img src="mmr2.png" width="350" title="hover text">
</p>






