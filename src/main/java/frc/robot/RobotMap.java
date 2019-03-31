/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static final double MINIMUM_POWER = 0.45; //Smallest amount of power the robot can take to move
  //Please note: when adding robots to the RobotName enum, please add a public static final class for it below
  //and add a case for it in the initialization of DriveTrainSubsystem.
  /**
   * Enum deciding which robot is being used - The CompBot, Steve, or The PracticeBot.
   */
  /*public enum RobotName {
    CompBot,
    Steve,
    PracticeBot,
    TestBoard,
    OldCompBot;
  }*/
  //public final RobotName ROBOT_NAME = RobotName.Steve;
  public enum ElevatorHeight {
    High,
    Medium,
    Low;
  }
  public enum GamePiece {
    Cargo,
    Hatch;
  }
  /**
   * Contained in this class are constants used for the competition robot (for Deep Space).
   */
  public static class CompBot extends RobotType {
    public CompBot() {
      super();
      name = "Montgomery Scottie";
      numberOfJeVois = 0;
      canClimb = true;
      hasADriveTrain = true;
      hasAnElevator = true;
      hasAnIntake = true;
      hasAShoulder = true;
      //Motor PWMs
      LEFT_DRIVE_PWM = 10; //Motors operating left side of the chassis
      RIGHT_DRIVE_PWM = 11; //Motors operating right side of the chassis
      LEFT_ELEVATOR_PWM = 17; //Motor that operates left elevator up and down
      RIGHT_ELEVATOR_PWM = 18; //Motor that operates right elevator up and down
      SHOULDER_PWM = 15; //Motor that rotates the arm to face the front and back
      INTAKE_WHEELS_PWM = 16; //Wheels picking up cargo on the stationary intake
      INTAKE_BELTS_PWM = 13; //Wheels picking up cargo on the stationary intake
      CLIMBING_WHEELS_PWM = 6; //Wheels on the rear pneumatic climbing cylinders (PORT = PCM's PWM)
      
      //FOR CLIMBING
      FRONT_LEFT_CLIMBING_PWM = 12;
      FRONT_RIGHT_CLIMBING_PWM = 7;
      REAR_LEFT_CLIMBING_PWM = 14;
      REAR_RIGHT_CLIMBING_PWM = 19;
      FRONT_LEFT_CLIMBING_POT_AI = 1;
      FRONT_RIGHT_CLIMBING_POT_AI = 2;
      REAR_LEFT_CLIMBING_POT_AI = 7;
      REAR_RIGHT_CLIMBING_POT_AI = 0;
      
      //Pneumatic ports
      INTAKE_JAW_PORT = 0; //Pneumatic cylinders opening the stationary intake (PORT = PCM's PWM)
      //DEPRECATED-CLIMBER_FRONT_PORT = 1, //Pneumatic cylinders to climb (PORT = PCM's PWM)
      //DEPRECATED-CLIMBER_REAR_PORT = 2, //Pneumatic cylinders to climb (PORT = PCM's PWM)
      HATCH_BEAK_PORT = 4; //Suction cup motors
      //Potentiometer analog input ports
      SHOULDER_POT_AI = 6;
      RIGHT_ELEVATOR_POT_AI = 4;
      LEFT_ELEVATOR_POT_AI = 5;
      
      HATCH_LIMITSWITCH_DIO = 0;
      /*DEPRECATED-//Servo PWMs
      TOP_SERVO_RELEASE_PWM = 9,
      BOTTOM_SERVO_RELEASE_PWM = 8;*/
      //Calibration constants
      RIGHT_ELEVATOR_POT_OFFSET= 0.0; //how much smaller is the right side
      RIGHT_ELEVATOR_OFFSET_SCALE = 0.0;//how much does the elevator try to be balanced
      RAMP_UP_DISTANCE = 0.00031; // + 0.08011591,//0.149 is too much
      RAMP_DOWN_DISTANCE = 0.011; //how far does the elevator ramp drive?
      LOW_HEIGHT = 0.3235; //for elevator
      MID_HEIGHT = 0.5237330478733418; //for elevator
      HIGH_HEIGHT = 0.685; //0.3537480,//for elevator
      ELEVATOR_THRESHOLD = 0.02;//how close to the correct height does the elevator have to be?
      //with cargo
      ELEVATOR_FEED_FORWARD = 0.109375;//how much power is needed to keep the elevator at its height?
      ELEVATOR_MAX_SPEED = 0.70;//how fast can the elevator go?
      FORWARD_STRAIGHT_SHOULDER = 0.6284652728011417;
      FORWARD_UP_SHOULDER = 0.6479153636394408;
      REVERSE_UP_SHOULDER = 0.7340199673988977;
      REVERSE_STRAIGHT_SHOULDER = 0.7517071955289918;
      SHOULDER_MAX_SPEED = 0.8;
      CARGO_PICKUP_SHOULDER = 0.76486;//when jaw is open, pick cargo off the ground
      SHOULDER_THRESHOLD = 0.0025;
      JEVOIS_CENTER = 160.0;
      SHOULDER_FEED_FORWARD = 0.0;

      FRONT_LEFT_CLIMBING_IN=0.41576655654592964;
      FRONT_RIGHT_CLIMBING_IN=0.47490972104585316;
      REAR_LEFT_CLIMBING_IN=0.45816943387262665;
      REAR_RIGHT_CLIMBING_IN=0.5060114355311496;
      FRONT_LEFT_CLIMBING_MID=0.2;
      FRONT_RIGHT_CLIMBING_MID=0.6;
      REAR_LEFT_CLIMBING_MID=0.29752023560161434;
      REAR_RIGHT_CLIMBING_MID=0.707211930242816;
      FRONT_LEFT_CLIMBING_OUT=0.042298625821150654;
      FRONT_RIGHT_CLIMBING_OUT=0.8490119386377065;
      REAR_LEFT_CLIMBING_OUT=0.11792405069904675;
      REAR_RIGHT_CLIMBING_OUT=0.8867369064483859;
    }
    
  }
  /**
   * Contained in this class are constants used for Steve, the programming robot.
   */
  public final class Steve {
    public static final int 
    LEFT_DRIVE_PWM = 1,
    RIGHT_DRIVE_PWM = 3,
    LEFT_ENCODER_A = 9,
    LEFT_ENCODER_B = 8,
    RIGHT_ENCODER_A = 0,
    RIGHT_ENCODER_B = 1;
  }
  /**
   * Contained in this class are constants used for the practice robot, a prototype of the competition robot.
   */
  public static class PracticeBot extends RobotType {
    public PracticeBot() {
      super();
      name = "El Practico Robo";
      numberOfJeVois = 0;
      canClimb = false;
      hasADriveTrain = true;
      hasAnElevator = true;
      hasAnIntake = true;
      hasAShoulder = true;
      //Motor PWMs
      LEFT_DRIVE_PWM = 11; //Motors operating left side of the chassis
      RIGHT_DRIVE_PWM = 12; //Motors operating right side of the chassis
      LEFT_ELEVATOR_PWM = 13; //Motor that operates left elevator up and down
      RIGHT_ELEVATOR_PWM = 16; //Motor that operates right elevator up and down
      SHOULDER_PWM = 10; //Motor that rotates the arm to face the front and back
      INTAKE_WHEELS_PWM = 17; //Wheels picking up cargo on the stationary intake
      INTAKE_BELTS_PWM = 19; //Wheels picking up cargo on the stationary intake
      CLIMBING_WHEELS_PWM = 14; //Wheels on the rear pneumatic climbing cylinders (PORT = PCM's PWM)
      
      //FOR CLIMBING
      FRONT_LEFT_CLIMBING_PWM = 0;
      FRONT_RIGHT_CLIMBING_PWM = 0;
      REAR_LEFT_CLIMBING_PWM = 0;
      REAR_RIGHT_CLIMBING_PWM = 0;
      FRONT_LEFT_CLIMBING_POT_AI = 0;
      FRONT_RIGHT_CLIMBING_POT_AI = 0;
      REAR_LEFT_CLIMBING_POT_AI = 0;
      REAR_RIGHT_CLIMBING_POT_AI = 0;

      //Pneumatic ports
      INTAKE_JAW_PORT = 6; //Pneumatic cylinders opening the stationary intake (PORT = PCM's PWM)
      //DEPRECATED-CLIMBER_FRONT_PORT = 4, //Pneumatic cylinders to climb (PORT = PCM's PWM)
      //DEPRECATED-CLIMBER_REAR_PORT = 5, //Pneumatic cylinders to climb (PORT = PCM's PWM)
      HATCH_BEAK_PORT = 4; //The beak that grabs hatches
      //Potentiometer analog input ports
      SHOULDER_POT_AI = 6;
      RIGHT_ELEVATOR_POT_AI = 7;
      LEFT_ELEVATOR_POT_AI = 5;
      HATCH_LIMITSWITCH_DIO = 0;
      /*DEPRECATED-//Servo PWMs
      TOP_SERVO_RELEASE_PWM = 9,
      BOTTOM_SERVO_RELEASE_PWM = 18;
      */
      //Calibration constants
      RIGHT_ELEVATOR_POT_OFFSET= -0.0541499; //how much smaller is the right side
      RIGHT_ELEVATOR_OFFSET_SCALE = 6.0;//how much does the elevator try to be balanced
      RAMP_UP_DISTANCE = 0.06915508;// + 0.08011591,//0.149 is too much
      RAMP_DOWN_DISTANCE = 0.0306812998;//how far does the elevator ramp drive?
      LOW_HEIGHT = 0.735012;//for elevator
      MID_HEIGHT = 0.524817;//for elevator
      HIGH_HEIGHT = 0.40;//0.3537480,//for elevator
      ELEVATOR_THRESHOLD = 0.02;//how close to the correct height does the elevator have to be?
      //ROBOTTYPE_SAYS: with cargo
      ELEVATOR_FEED_FORWARD = 0.117;//how much power is needed to keep the elevator at its height?
      ELEVATOR_MAX_SPEED = 0.70;//how fast can the elevator go?
      FORWARD_STRAIGHT_SHOULDER = 0.4545366;
      FORWARD_UP_SHOULDER = 0.47725756;
      REVERSE_UP_SHOULDER = 0.5379968;
      REVERSE_STRAIGHT_SHOULDER = 0.5515320;
      SHOULDER_MAX_SPEED = 0.9;
      CARGO_PICKUP_SHOULDER = 0.56589;
      SHOULDER_THRESHOLD = 0.005;
      JEVOIS_CENTER = 160.0;
      SHOULDER_FEED_FORWARD = 0.0;
      
    }
  }
  /**
   * Contained in this class are constants used for the old competition robot (for FIRST Power Up).
   */
  public static final class OldCompBot {
    //Uses PWMVictorSPX
    public static final int 
    LEFT_DRIVE_PWM = 5,
    RIGHT_DRIVE_PWM = 1,
    LEFT_ENCODER_A = 9,
    LEFT_ENCODER_B = 8,
    RIGHT_ENCODER_A = 0,
    RIGHT_ENCODER_B = 1;
    public static final boolean
    LEFT_REVERSE = true,
    RIGHT_REVERSE = false;
    public static Encoder.EncodingType 
    LEFT_ENCODING_TYPE = Encoder.EncodingType.k4X,
    RIGHT_ENCODING_TYPE = Encoder.EncodingType.k4X;
  }
  /**
   * Contained in this class are constants used for the test board, a robot with no wheels.
   */
  public final class TestBoard {
    //No variables yet.
  }
  /**
   * Contained in this class are constants used for the JeVois.
   */
  public final class JeVois {
    public static final int 
    BAUD_RATE = 9600, //What baud rate do I communicate with the JeVois at?
    MAX_CONNECTION_ATTEMPTS = 10, //How many times do I try to connect to the JeVois Camera?
    DATA_INDEX = 7, //Where in each substring does the data start from a ','?
    CAMERA_WIDTH = 320, //Width resolution of the JeVois camera
    CAMERA_HEIGHT = 240; //Height resolution of the JeVois camera
    public static final double 
    PID_P = 3, //Proportional control of turning using the JeVois - Used in the JeVoisCenter command
    PID_I = 0.0,
    PID_D = 0.0,
    ANGLE_THRESHOLD = 20; //How close can the robot be to the center to give up (units=pixels)?
  }
  public abstract static class RobotType{
    public RobotType() {

    }
    public String name;
    public int numberOfJeVois;
    public boolean canClimb;
    public boolean hasADriveTrain;
    public boolean hasAnElevator;
    public boolean hasAnIntake;
    public boolean hasAShoulder;
    //Motor PWMs
    public int 
    LEFT_DRIVE_PWM, //Motors operating left side of the chassis
    RIGHT_DRIVE_PWM, //Motors operating right side of the chassis
    LEFT_ELEVATOR_PWM , //Motor that operates left elevator up and down
    RIGHT_ELEVATOR_PWM, //Motor that operates right elevator up and down
    SHOULDER_PWM, //Motor that rotates the arm to face the front and back
    INTAKE_WHEELS_PWM, //Wheels picking up cargo on the stationary intake
    INTAKE_BELTS_PWM, //Wheels picking up cargo on the stationary intake
    CLIMBING_WHEELS_PWM, //Wheels on the rear pneumatic climbing cylinders (PORT = PCM's PWM)

    //FOR CLIMBING
    FRONT_LEFT_CLIMBING_PWM,
    FRONT_RIGHT_CLIMBING_PWM,
    REAR_LEFT_CLIMBING_PWM,
    REAR_RIGHT_CLIMBING_PWM,
    FRONT_LEFT_CLIMBING_POT_AI,
    FRONT_RIGHT_CLIMBING_POT_AI,
    REAR_LEFT_CLIMBING_POT_AI,
    REAR_RIGHT_CLIMBING_POT_AI,
    
    

    //Pneumatic ports
    INTAKE_JAW_PORT, //Pneumatic cylinders opening the stationary intake (PORT = PCM's PWM)
    //DEPRECATED-CLIMBER_FRONT_PORT, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    //DEPRECATED-CLIMBER_REAR_PORT, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    HATCH_BEAK_PORT, //Suction cup motors
    //Potentiometer analog input ports
    SHOULDER_POT_AI,
    RIGHT_ELEVATOR_POT_AI,
    LEFT_ELEVATOR_POT_AI,

    HATCH_LIMITSWITCH_DIO;
    /*DEPRECATED-//Servo PWMs
    TOP_SERVO_RELEASE_PWM,
    BOTTOM_SERVO_RELEASE_PWM;*/
    //Calibration constants
    public double 
    RIGHT_ELEVATOR_POT_OFFSET, //how much smaller is the right side
    RIGHT_ELEVATOR_OFFSET_SCALE,//how much does the elevator try to be balanced
    RAMP_UP_DISTANCE,// + 0.08011591,//0.149 is too much
    RAMP_DOWN_DISTANCE,//how far does the elevator ramp drive?
    LOW_HEIGHT,//for elevator
    MID_HEIGHT,//for elevator
    HIGH_HEIGHT,//0.3537480,//for elevator
    ELEVATOR_THRESHOLD,//how close to the correct height does the elevator have to be?
    //with cargo
    ELEVATOR_FEED_FORWARD,//how much power is needed to keep the elevator at its height?
    ELEVATOR_MAX_SPEED,//how fast can the elevator go?
    FORWARD_STRAIGHT_SHOULDER,
    FORWARD_UP_SHOULDER,
    REVERSE_UP_SHOULDER,
    REVERSE_STRAIGHT_SHOULDER,
    SHOULDER_MAX_SPEED,
    CARGO_PICKUP_SHOULDER,//when jaw is open, pick cargo off the ground
    SHOULDER_THRESHOLD,
    JEVOIS_CENTER,
    SHOULDER_FEED_FORWARD,

    FRONT_LEFT_CLIMBING_IN,
    FRONT_RIGHT_CLIMBING_IN,
    REAR_LEFT_CLIMBING_IN,
    REAR_RIGHT_CLIMBING_IN,
    FRONT_LEFT_CLIMBING_MID,
    FRONT_RIGHT_CLIMBING_MID,
    REAR_LEFT_CLIMBING_MID,
    REAR_RIGHT_CLIMBING_MID,
    FRONT_LEFT_CLIMBING_OUT,
    FRONT_RIGHT_CLIMBING_OUT,
    REAR_LEFT_CLIMBING_OUT,
    REAR_RIGHT_CLIMBING_OUT;
  
  }
}
