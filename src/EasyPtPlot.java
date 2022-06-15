/**AP Lab 7
 *Haibo Sun
 *April 4th, 2022
 */
import ptolemy.plot.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class EasyPtPlot {
  public static final double Xmin = 1995;
  public static final double Xmax = 2022; // Graph domain
  public static final int Npoint = 1;
  private static ArrayList<Double[]> university;
  private static ArrayList<Double[]> gonzales;
  private static ArrayList<Double[]> averageDaily;
  private static ArrayList<Double[]> monthlyTotal;
  private static ArrayList<Double[]> tenDays = new ArrayList<Double[]>();
  private static ArrayList<Double[]> tenMonths = new ArrayList<Double[]>();
  private static ArrayList<Double[]> twoDaysPerMonth;
  private static ArrayList<Double[]> indexTwoDaysPerMonth = new ArrayList<Double[]>();
  private static ArrayList<Double[]> TopTenTwoDaysPerMonth = new ArrayList<Double[]>();
  private static int[] leapRange = {31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
  private static int[] nonLeapRange = {31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
  
  public EasyPtPlot() throws IOException{
	university = initalization("1018598");
	gonzales  = initalization("1018611");
	averageDaily = calcDailyAverage();
	monthlyTotal = totalMonthlyRainfall();
	twoDaysPerMonth = calcTwoDaysPerMonth();
  }
  
  public static void main(String[] args) throws IOException{
	EasyPtPlot p = new EasyPtPlot(); 
	DecimalFormat df = new DecimalFormat("#.#");
	
	Plot plotObj1 = new Plot (); 
    plotObj1.setTitle("Maximum Monthly Rainfall (mm)");
    plotObj1.setXLabel("Year");
    plotObj1.setYLabel ("Monthly Rainfall(mm)");
    for (double x = Xmin; x <= Xmax; x++) {
    	for (double month = 1; month < 13; month++) {
    		double y = monthlyTotal.get(((int)(x) - 1995))[(int)(month)];
    		plotObj1.addPoint(0,(x+(month/12)),y,true);
    	}
    }
    PlotApplication app1 = new PlotApplication(plotObj1);
    
   
	Plot plotObj = new Plot () ; 
    plotObj.setTitle("Maximum Monthly Rainfall Over a 2-Day Period (mm) ");
    plotObj.setXLabel("Year");
    plotObj.setYLabel ("Monthly Rainfall (mm)");
    double xStep = (Xmax - Xmin ) / Npoint;
    // Plotting loop
    for (double x = Xmin; x <= Xmax; x++) {
    	for (double month = 1; month < 13; month++) {
    		double y = twoDaysPerMonth.get(((int)(x) - 1995))[(int)(month)];
    		plotObj.addPoint(0, (x+(month/12)), y, true);
    	}
    }
    PlotApplication app = new PlotApplication ( plotObj ); 
    
	System.out.println("Top 10 Monthly Rainfall (mm)");
	p.findTenMonths();
	System.out.println();
    System.out.println("Top Ten Two Consecutive Days of Rainfall from 1995 to 2022 (mm)");
    p.findTenDays();
    System.out.println();
    System.out.println("Top 10 Maximum Rainfall Over a 2-Day Period (mm)");
    p.findTopTenTwoDaysPerMonth();
  }
  
/**
 * Initialization Method
 * reads and extracts data from the given .csv files into ArrayList<Double[]> based on the station number 
 * @param String number
 * @return ArrayList<Double[]>
 * @throws IOException
 */
  public static ArrayList<Double[]> initalization(String number) throws IOException{
	  ArrayList<Double[]> list = new ArrayList<Double[]>();
	  for(int year = 1995; year <= 2022; year++) {
			BufferedReader in = new BufferedReader(new FileReader("src/weather/en_climate_daily_BC_"+number+"_"+year+"_P1D.csv"));
			String[] lines = in.lines().toArray(String[] ::new);
			Double[] s1 = new Double[lines.length];
			for(int i = 0; i < lines.length; i++) {
			   	String temp = lines[i];
			   	String[] s = temp.split(",");
			   	if(i == 0) s1[0] = (double)(year);
			   	else if(s[23].equals("\"\"")) s1[i] = -1.0;
			   	else s1[i] = Double.parseDouble(s[23].substring(1,s[23].length()-1));
			}
			list.add(s1);
		}
	  
	  return list;
  }
  
  /**
   * calcDailyAverage Method
   * calculate the average of daily rainfall of the two stations 
   * @return ArrayList<Double[]>
   */
  public static ArrayList<Double[]> calcDailyAverage(){
	  ArrayList<Double[]> average = new ArrayList<Double[]>();
	  DecimalFormat df = new DecimalFormat("#.#");
	  for(int i = 0; i < university.size(); i++) {
			Double[] Uyear = university.get(i);
			Double[] Gyear = gonzales.get(i);
			Double[] Myear = new Double[Uyear.length];
			Myear[0] = Uyear[0];
			for(int j = 1; j < Uyear.length; j++) {
				if(Uyear[j] == -1.0 && Gyear[j] == -1.0) Myear[j] = -1.0;
				else if(Uyear[j] == -1.0 || Gyear[j] == -1.0) {
					if(Uyear[j] == -1.0) Myear[j] = Gyear[j];
					else Myear[j] = Uyear[j];
				}else {
					double mean = (Uyear[j] + Gyear[j])/2;
					Myear[j] = Double.parseDouble(df.format((mean)));
				}
			}
			average.add(Myear);
		}
	  return average;
  }
  
  /**
   * calcMonthlyAverage Method
   * calculate the sum of rainfall from startIndex to endIndex
   * @param int yearIndex, int startIndex, int endIndex
   * @return double
   */
  public static double calcMonthlyRainfall(int yearIndex, int startIndex, int endIndex) {
	  DecimalFormat df = new DecimalFormat("#.#");
	  double array;
	  double amount = 0;
	  for(int i = startIndex; i <= endIndex; i++) {
		  double current = averageDaily.get(yearIndex)[i];
		  if(current != -1) {
			  amount += current;
		  }
	  }
	  array = Double.parseDouble(df.format(amount));
	  return array;
  }
  
  /**
   * isLeapYear Methods
   * determines whether the given year is boolean or not
   * @param year
   * @return boolean
   */
  public static boolean isLeapYear(double year) {
	  return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
  }
  
  /**
   * totalMonthlyRainfall Method
   * calculate the total monthly fall for every month from 1995 to 2022
   * @return ArrayList<Double[]>
   */
  public static ArrayList<Double[]> totalMonthlyRainfall(){
	  ArrayList<Double[]> month = new ArrayList<Double[]>();
		for(int i = 0; i < averageDaily.size(); i++) {
			Double[] result = new Double[13];
			Double[] current = averageDaily.get(i);
			result[0] = averageDaily.get(i)[0];
			if(isLeapYear(current[0])) {
				int index = 1;
				for(int j = 0; j < 12; j++) {
					if(j == 0) {
						double m = calcMonthlyRainfall(i,1,leapRange[j]);
						result[index] = m;
					}else {
						double m = calcMonthlyRainfall(i,leapRange[j-1]+1,leapRange[j]);
						result[index] = m;
					}
					index = index +1;
				}
			}else {
				int index = 1;
				for(int j = 0; j < 12; j++) {
					if(j == 0) {
						double m = calcMonthlyRainfall(i,1,nonLeapRange[j]);
						result[index] = m;
					}else {
						double m = calcMonthlyRainfall(i,nonLeapRange[j-1]+1,nonLeapRange[j]);
						result[index] = m;
					}
					index++;
				}
			}
			month.add(result);
		}
	  return month;
  }
  
  /**
   * calcTwoDaysPerMonth Method
   * calculates the maximum rainfall over a two days period per month
   * @return ArrayList<Double[]>
   */
  public static ArrayList<Double[]> calcTwoDaysPerMonth() {
	  DecimalFormat df = new DecimalFormat("#.#");
	  ArrayList<Double[]> t = new ArrayList<Double[]>();
	  for(int i = 0; i < averageDaily.size(); i++) {
			Double[] currentYear = averageDaily.get(i);
			Double[] addit = new Double[13];
			addit[0] = currentYear[0];
			Double[] addDayIndex = new Double[13];
			addDayIndex[0] = currentYear[0];
			if(isLeapYear(currentYear[0])){
				int addIndex = 1;
				int index = 0;
				double max = 0;
				Double largest = currentYear[1] + currentYear[2];
				Double largestIndex = 1.0;
				for(int j = 1; j < currentYear.length; j++) {
					if(j == leapRange[index]) {
						addit[addIndex] = Double.parseDouble(df.format(max));
						addDayIndex[addIndex] = largestIndex;
						addIndex = addIndex + 1;
						index = index + 1;
						max = 0;
						largestIndex = 1.0;
					}else {
						double temp = currentYear[j] + currentYear[j+1];
						if(max < temp) {
							max = temp;
							largestIndex = (double)j;
						}
					}
				}
			}else {
				int addIndex = 1;
				int index = 0;
				double max = 0;
				Double largest = currentYear[1] + currentYear[2];
				Double largestIndex = 1.0;
				for(int j = 1; j < currentYear.length; j++) {
					if(j == nonLeapRange[index]) {
						addit[addIndex] = Double.parseDouble(df.format(max));
						addDayIndex[addIndex] = largestIndex;
						addIndex = addIndex + 1;
						index = index + 1;
						max = 0;
						largestIndex = 1.0;
					}else {
						double temp = currentYear[j] + currentYear[j+1];
						if(max < temp) {
							max = temp;
							largestIndex = (double)j;
						}
					}
				}
			}
			t.add(addit);
			indexTwoDaysPerMonth.add(addDayIndex);
		}
	  return t;
  }
  
  /**
   * findTenDays Method
   * find ten days of maximum two days rainfall from 1995 to 2022 
   */
  public static void findTenDays() {
	  DecimalFormat df = new DecimalFormat("#.#");
	  double sum = 0;
		for(int i = 0; i < 10; i++) {
			Double[] arr = {0.0,0.0,0.0,0.0};
			Double[] arr1 = {0.0,0.0,0.0};
			tenDays.add(arr);
		}
		
		for(int i = 0; i < averageDaily.size(); i++) {
			Double[] currentYear = averageDaily.get(i);
			for(int j = 1; j < currentYear.length-1; j++) {
				if(currentYear[j] == -1.0) {
					continue;
				}else {
					sum = currentYear[j] + currentYear[j+1];
					Double[] addDay = {(double)i, (double)j, (double)(j)+1, Double.parseDouble(df.format(sum))};
					insertTenDays(addDay);
					sum = 0;
				}
			}
		}
		for(int i = 0; i < tenDays.size(); i++) {
	    	Double[] array = tenDays.get(i);
	    	System.out.print(array[0]+1995+" ");
	    	if(array[1] <= 304) {
	    		System.out.println("October "+(array[2]-274)+"/"+(array[2]-273)+"   "+(array[3])+"mm");
	    	}else if(array[1] <= 334) {
	    		System.out.println("November "+(array[2]-305)+"/"+(array[2]-304)+"   "+(array[3])+"mm");
	    	}else if(array[1] <= 365) {
	    		System.out.println("December "+(array[2]-334)+"/"+(array[2]-333)+"   "+(array[3])+"mm");
	    	}
	    }
  }
  
  /**
   * insertTenDays Method
   * insert the arr and sort it based on rainfall amount
   * @param arr
   */
  public static void insertTenDays(Double[] arr) {
	  for(int i = 0; i < tenDays.size(); i++) {
		  if(arr[3] > tenDays.get(i)[3]) {
			  tenDays.add(i, arr);
			  tenDays.remove(10);
			  break;
		  }
	  }
  }
  
  /**
   * findTenMonths Method
   * find ten days of maximum two days rainfall from 1995 to 2022 
   */
  public static void findTenMonths() {
	  DecimalFormat df = new DecimalFormat("#.#");
	  double sum = 0;
		for(int i = 0; i < 10; i++) {
			Double[] arr = {0.0,0.0,0.0,0.0};
			Double[] arr1 = {0.0,0.0,0.0};
			tenMonths.add(arr1);
		}
	  
	  for(int i = 0; i < monthlyTotal.size(); i++) {
			Double[] currentYear = monthlyTotal.get(i);
			for(int j = 1; j < currentYear.length; j++) {
				Double[] addMonth = {(double)(i+1995), (double)j, currentYear[j]};
				insertTenMonths(addMonth);
			}
		}
		
		for(int i = 0; i < tenMonths.size(); i++) {
			System.out.println(Arrays.toString(tenMonths.get(i)));
		}
  }
  /**
   * insertTenMonths Method
   * insert the arr and sort it based on rainfall amount
   * @param arr
   */
  public static void insertTenMonths(Double[] arr) {
	  for(int i = 0; i < tenMonths.size(); i++) {
		  if(arr[2] > tenMonths.get(i)[2]) {
			  tenMonths.add(i, arr);
			  tenMonths.remove(10);
			  break;
			  }
		  }
	  }
/**
 * findTopTenTwoDaysPerMonth method
 * find and print the top ten monthly two heaviest consecutive days of rainfall
 */
  
 public static void findTopTenTwoDaysPerMonth() {
		for(int i = 0; i < 10; i++) {
			Double[] arr = {0.0,0.0,0.0};
			TopTenTwoDaysPerMonth.add(arr);
		}
		
		for(int i = 0; i < twoDaysPerMonth.size(); i++) {
			Double[] currentYear = twoDaysPerMonth.get(i);
			for(int j = 1; j < currentYear.length; j++) {
				Double[] addDay = {(double)i, indexTwoDaysPerMonth.get(i)[j], currentYear[j]};
				insertTen(addDay);
			}
		}
		
		for(int i = 0; i < TopTenTwoDaysPerMonth.size(); i++) {
	    	Double[] array = TopTenTwoDaysPerMonth.get(i);
	    	System.out.print(array[0]+1995+" ");
	    	if(array[1] <= 59) {
	    		System.out.println("February "+(array[1]-31)+"/"+(array[1]-30)+"   "+(array[2])+"mm");
	    	}else if(array[1] <= 273) {
	    		System.out.println("September "+(array[1]-243)+"/"+(array[1]-242)+"   "+(array[2])+"mm");
	    	}else if(array[1] <= 304) {
	    		System.out.println("October "+(array[1]-273)+"/"+(array[1]-272)+"   "+(array[2])+"mm");
	    	}else if(array[1] <= 334) {
	    		System.out.println("November "+(array[1]-304)+"/"+(array[1]-303)+"   "+(array[2])+"mm");
	    	}else if(array[1] <= 365) {
	    		double a = array[1]-335;
	    		if(a==10.0) {
		    		System.out.println("December "+(array[1]-334)+"/"+(array[1]-333)+"   "+(array[2])+"mm");

	    		}else {
		    		System.out.println("December "+(array[1]-335)+"/"+(array[1]-334)+"   "+(array[2])+"mm");
	    		}
	    	}
	    }
 }
 
 /**
  * insertTen method
  * a method that supports findTopTenTwoDaysPerMonth methods to arrange the top ten results
  * @param arr
  */
 public static void insertTen(Double[] arr) {
	  for(int i = 0; i < TopTenTwoDaysPerMonth.size(); i++) {
		  if(arr[2] > TopTenTwoDaysPerMonth.get(i)[2]) {
			  TopTenTwoDaysPerMonth.add(i, arr);
			  TopTenTwoDaysPerMonth.remove(10);
			  break;
			  }
	  }
 }


}
  
	
	