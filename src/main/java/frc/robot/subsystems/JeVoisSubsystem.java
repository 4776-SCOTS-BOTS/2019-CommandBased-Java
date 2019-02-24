/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.JeVoisData;
import frc.robot.RobotMap;
import frc.robot.RobotMap.RobotName;

//import org.json.simple.JSONObject;

/**
 * Subsystem handling all interaction with the JeVois Smart Camera(s).
 */
public class JeVoisSubsystem extends Subsystem{// implements PIDSource{
  //Subsystem Variables:
  boolean doubleCams;
  boolean pidUseSecondCam;
  SerialPort jeVoisCam0;
  JeVoisData data0;
  SerialPort jeVoisCam1;
  JeVoisData data1;
  PIDSourceType pidSourceType;
  /*//PIDSource REQUIRED INTERFACE COMMANDS---------------------------------------------------------------
  public void setPIDSourceType(PIDSourceType pidSource) {
    pidSourceType = pidSource;
  }
  public PIDSourceType getPIDSourceType() {
    return pidSourceType;
  }
  public double pidGet() {
    return getAvg(pidUseSecondCam);
  }
  *///END OF REQUIRED INTERFACE COMMANDS------------------------------------------------------------------------

  public boolean getDoubleCams() {
    return doubleCams;
  }
  public double getXAvg(boolean secondCam) {
    if (secondCam) {
      return data1.x_avg;
    } else {
      return data0.x_avg;
    }
  }
  public double getWAvg(boolean secondCam) {
    if (secondCam) {
      return data1.w_avg;
    } else {
      return data0.w_avg;
    }
  }
  public double getSideX(boolean leftSide, boolean secondCam) {
    if (!secondCam) {
      if (data0.is1Left) {
        if (leftSide) {
          return data0.x_one;
        } else {
          return data0.x_two;
        }
      } else {
        if (leftSide) {
          return data0.x_two;
        } else {
          return data0.x_one;
        }
      }
    } else {
      if (data1.is1Left) {
        if (leftSide) {
          return data1.x_one;
        } else {
          return data1.x_two;
        }
      } else {
        if (leftSide) {
          return data1.x_two;
        } else {
          return data1.x_one;
        }
      }
    }
  }
  public double getSideW(boolean leftSide, boolean secondCam) {
    if (!secondCam) {
      if (data0.is1Left) {
        if (leftSide) {
          return data0.w_one;
        } else {
          return data0.w_two;
        }
      } else {
        if (leftSide) {
          return data0.w_two;
        } else {
          return data0.w_one;
        }
      }
    } else {
      if (data1.is1Left) {
        if (leftSide) {
          return data1.w_one;
        } else {
          return data1.w_two;
        }
      } else {
        if (leftSide) {
          return data1.w_two;
        } else {
          return data1.w_one;
        }
      }
    }
  }
  public int getBytes() {
    return jeVoisCam0.getBytesReceived();
  }
  public JeVoisSubsystem (RobotName robotName, boolean enableSystem) {
    if (enableSystem) {
      switch(robotName) {
        case CompBot: {
          //this();
        }
        break;
        case PracticeBot: {
          //this();
        }
        break;
        case OldCompBot: {
          //this(false);
          createSystem(false);
        }
        break;
        case Steve: {
          //this();
        }
        break;
        default: {
          //this();
        }
      }
      System.out.println(robotName + "\'s JeVoisSubsystem correctly instantiated and ENABLED.");
    } else {
      System.out.println(robotName + "\'s JeVoisSubsystem correctly instantiated and DISABLED.");
    }
  }
  public JeVoisSubsystem (RobotName robotName) {
    switch(robotName) {
      case CompBot: {
        //this();
      }
      break;
      case PracticeBot: {
        //this();
      }
      break;
      case OldCompBot: {
        //this(false);
        createSystem(false);
      }
      break;
      case Steve: {
        //this();
      }
      break;
      default: {
        //this();
      }
    }
    System.out.println(robotName + "\'s JeVoisSubsystem correctly instantiated.");
  }
  //Default Constructor: Use main constructor with only 1 camera
  public JeVoisSubsystem () {
    //this(false);
    this(RobotName.TestBoard);
    System.out.println("Blank Subsystem for JeVoisSubsystem was instantiated (as TestBoard).");
  }
  //Main Constructor runs main "constructor"
  public JeVoisSubsystem (boolean _doubleCams) {
    createSystem(_doubleCams);
  }
  //Main "Contstructor": Create 1 or 2 cameras
  public void createSystem (boolean _doubleCams) {
    doubleCams = _doubleCams;
    reconnect(false);
    if (_doubleCams)
      reconnect(true);
    else
      System.out.println("PLEASE NOTE: Not connecting to \'jeVoisCam1\' since it was selected not to do so.");
  }
  public void reconnect(boolean secondCam) {
    if (secondCam) {
      data1 = new JeVoisData();
      for(int i = 0; i < RobotMap.JeVois.MAX_CONNECTION_ATTEMPTS; i++) {
        try {
          jeVoisCam1 = new SerialPort(RobotMap.JeVois.BAUD_RATE, SerialPort.Port.kUSB);
        } catch (Exception e) {
          System.out.println("ERROR in JeVoisSubsystem: Failed to connect \'jeVoisCam1\' in attempt #" + (i + 1));
          jeVoisCam1 = null;
        }
        if (jeVoisCam1 != null) break;
      }
      if (jeVoisCam1 == null)
        System.out.println("ERROR in JeVoisSubsystem: Failed to connect \'jeVoisCam1\' after attempting to do so!");
      else
        System.out.println("SUCCESS connecting \'jeVoisCam1\'!");
    } else {
      data0 = new JeVoisData();
      for(int i = 0; i < RobotMap.JeVois.MAX_CONNECTION_ATTEMPTS; i++) {
        try {
          jeVoisCam0 = new SerialPort(RobotMap.JeVois.BAUD_RATE, SerialPort.Port.kUSB);
        } catch (Exception e) {
          System.out.println("ERROR in JeVoisSubsystem: Failed to connect \'jeVoisCam0\' in attempt #" + i);
          jeVoisCam0 = null;
        }
        if (jeVoisCam0 != null) break;
      }
      if (jeVoisCam0 == null)
        System.out.println("ERROR in JeVoisSubsystem: Failed to connect \'jeVoisCam0\' after attempting to do so!");
      else
        System.out.println("SUCCESS connecting \'jeVoisCam0\'!");
    }
  }
  /**
   * Read data from the JeVois if able, then save the data and print it. 
   * Note: This may read clips of data, leading to errors. To prevent this, use safeRead() instead.
   */
  public void read () {
    if (jeVoisCam0.getBytesReceived() <= 0) {
      System.out.println("ERROR in JeVoisSubsystem: Failed to read any data from \'jeVoisCam0\'!");
    } else {
      String data = jeVoisCam0.readString();
      /** 
      * /PLEASE NOTE: When using readString(), it will display ALL data sent since the last time it was cleared,
      * so the JSON files will pile up if you do not run readString() enough (every loop is 'enough'?)!
      * To fix this, we will clip the data so we only have the last (COMPLETE) JSON file (The very last file is cut off
      * when read, so we will use the second to last file).
      **/
      String[] d = data.split("\n");
      data = d[d.length - 2];
      data0.fromData(data);
      data0.print();
    }
  }
  /**
   * SAFELY read data from the JeVois if able, then save the data and print it.
   * Unlike read(), this only reads if a FULL clip of data is available to read, so no errors occur.
   */
  public void safeRead () {
    if (jeVoisCam0.getBytesReceived() <= 82) {
      //Not enough data to read, so use old data (don't format).
    } else {
      String data = jeVoisCam0.readString();
      /** 
      * PLEASE NOTE: When using readString(), it will display ALL data sent since the last time it was cleared,
      * so the JSON files will pile up if you do not run readString() enough (every loop is 'enough'?)!
      * To fix this, we will clip the data so we only have the last (COMPLETE) JSON file (The very last file is cut off
      * when read, so we will use the second to last file).
      **/
      String[] d = data.split("\n");
      switch (d.length) {
        case (0): {
          //An error occured: Why would it be empty? So just keep act like there wasn't enough data (don't format).
          System.out.println("ERROR in JeVoisSubsystem: Data read contains nothing! Using old data...");
        }
        break;
        case (1): {
          data = d[0];
          data0.fromData(data);
        }
        break;
        default: {
          data = d[d.length - 2];
          data0.fromData(data);
        }
        break;
      }
    }
    //data0.print(); We dont want to HAVE to print, so this is disabled.
  }
  public void display () {
    if (jeVoisCam0.getBytesReceived() > 0) {
      System.out.println(jeVoisCam0.readString());
    }
  }

  public void print1() {
    data0.print();
  }
  public void newPrint() {
    System.out.println("W: " + (data0.w_one + data0.w_two) / 2 + ", H:" + (data0.h_one + data0.h_two) / 2);
    data0.print();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //PLEASE NOTE: Do not have JeVoisCenter the default command!
    //You only use JeVoisCenter when centering, so don't have it run automatically!
  }
}
