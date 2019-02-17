/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
//Current Format: {"FD": 0, "X1": 0, "Y1": 0, "W1": 0, "H1": 0, "X2": 0, "Y2": 0, "W2": 0, "H2": 0}
/**
 * Basic class for holding and formatting data given by the JeVois.
 */
public class JeVoisData {
    public boolean found;
    public double x_one;
    public double y_one;
    public double w_one;
    public double h_one;
    public double x_two;
    public double y_two;
    public double w_two;
    public double h_two;
    public double x_avg;
    public double w_avg;
    public boolean is1Left;
    private int i;

    //Default Constructor: Set all values to 0 (By calling the reset() function).
    public JeVoisData () {
        reset();
    }
    public JeVoisData (String data) {
        reset();
        fromData(data);
    }
    
    //Read the string given from the JeVois and save the values.
    public void fromData(String data) {
        String[] datas = data.split(",");
        if (datas.length != 9) {
            System.out.println("ERROR in JeVoisData: JSON data has an incorrect size (it is " + datas.length + ")!");
            reset();
        } else {
            String d = datas[0].substring(i, i + 1);
            if (d.equalsIgnoreCase("0")) {
                found = false;
                //PLEASE NOTE: Since no object was found, no data was found! So, we will not update the values
                //(In case we need to access them anyways)
            }
            else if (d.equalsIgnoreCase("1")) {
                found = true;
                x_one = Double.parseDouble(datas[1].substring(i, datas[1].length()));
                y_one = Double.parseDouble(datas[2].substring(i, datas[2].length()));
                w_one = Double.parseDouble(datas[3].substring(i, datas[3].length()));
                h_one = Double.parseDouble(datas[4].substring(i, datas[4].length()));
                x_two = Double.parseDouble(datas[5].substring(i, datas[5].length()));
                y_two = Double.parseDouble(datas[6].substring(i, datas[6].length()));
                w_two = Double.parseDouble(datas[7].substring(i, datas[7].length()));
                h_two = Double.parseDouble(datas[8].substring(i, datas[8].length() - 2));
                x_avg = (x_one + x_two) / 2;
                w_avg = (w_one + w_two) / 2;
                //Determine if left is one
                is1Left = (x_two >= x_one);
            }
            else {
                found = false;
                System.out.println("ERROR in JeVoisData: FD value in JSON data is neither 0 nor 1 (it is " + d + ")!");
            }
            //print();
        }
    }

    //Reset all data to 0.
    public void reset() {
        found = false;
        x_one = 0;
        y_one = 0;
        w_one = 0;
        h_one = 0;
        x_two = 0;
        y_two = 0;
        w_two = 0;
        h_two = 0;
        x_avg = 0.0;
        w_avg = 0.0;
        i = RobotMap.JeVois.DATA_INDEX;
    }
    public void print() {
        System.out.println("PRINTING_DATA: Found:"+found+", XOne:"+x_one+", YOne:"+y_one+", WOne:"+w_one+", HOne:"+h_one+
        ", XTwo:"+x_two+", YTwo:"+y_two+", WTwo:"+w_two+", HTwo:"+h_two+", XAvg:"+x_avg+", WAvg:"+w_avg);
    }
}
