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
  public enum RobotName {
    CompBot,
    Steve,
    PracticeBot,
    TestBoard,
    OldCompBot;
  }
  public final RobotName ROBOT_NAME = RobotName.Steve;
  public enum ElevatorHeight {
    High,
    Medium,
    Low;
  }
  /**
   * Contained in this class are constants used for the competition robot (for Deep Space).
   */
  public static class CompBot {
    //Motor PWMs
    public static final int 
    LEFT_DRIVE_PWM = 99, //Motors operating left side of the chassis
    RIGHT_DRIVE_PWM = 99, //Motors operating right side of the chassis
    LEFT_ELEVATOR_PWM = 99, //Motor that operates left elevator up and down
    RIGHT_ELEVATOR_PWM = 99, //Motor that operates right elevator up and down
    SHOULDER_PWM = 99, //Motor that rotates the arm to face the front and back
    INTAKE_WHEELS_PWM = 99, //Wheels picking up cargo on the stationary intake
    INTAKE_BELTS_PWM = 99, //Wheels picking up cargo on the stationary intake
    CLIMBING_WHEELS_PWM = 99, //Wheels on the rear pneumatic climbing cylinders (PORT = PCM's PWM)
    //Pneumatic ports
    INTAKE_JAW_PORT = 99, //Pneumatic cylinders opening the stationary intake (PORT = PCM's PWM)
    CLIMBER_FRONT_PORT = 99, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    CLIMBER_REAR_PORT = 99, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    HATCH_VACUUM = 99, //Suction cup motors
    //Potentiometer analog input ports
    SHOULDER_POT_AI = 99,
    RIGHT_ELEVATOR_POT_AI = 99,
    LEFT_ELEVATOR_POT_AI = 99,
    //Servo PWMs
    TOP_SERVO_RELEASE_PWM = 99,
    BOTTOM_SERVO_RELEASE_PWM = 99;
    //Calibration constants
    public static double 
    RIGHT_ELEVATOR_POT_OFFSET= -0.0541499, //how much smaller is the right side
    RIGHT_ELEVATOR_OFFSET_SCALE = 6.0,//how much does the elevator try to be balanced
    RAMP_UP_DISTANCE = 0.06915508,// + 0.08011591,//0.149 is too much
    RAMP_DOWN_DISTANCE = 0.0306812998,//how far does the elevator ramp drive?
    LOW_HEIGHT = 0.735012,//for elevator
    MID_HEIGHT = 0.524817,//for elevator
    HIGH_HEIGHT = 0.40,//0.3537480,//for elevator
    ELEVATOR_THRESHOLD = 0.02,//how close to the correct height does the elevator have to be?
    ELEVATOR_FEED_FORWARD = 0.117,//how much power is needed to keep the elevator at its height?
    ELEVATOR_MAX_SPEED = 0.70,//how fast can the elevator go?
    FORWARD_STRAIGHT_SHOULDER = 0.4545366,
    FORWARD_UP_SHOULDER = 0.47725756,
    REVERSE_UP_SHOULDER = 0.5379968,
    REVERSE_STRAIGHT_SHOULDER = 0.5515320,
    CARGO_PICKUP_SHOULDER = 0.56589,
    SHOULDER_THRESHOLD = 0.005,
    JEVOIS_CENTER = 160.0,
    //FOR VACUUMS:
    MIN_LEFT_VACUUM_CURRENT = 0,
    MAX_LEFT_VACUUM_CURRENT = 0,
    MIN_RIGHT_VACUUM_CURRENT = 0,
    MAX_RIGHT_VACUUM_CURRENT = 0;
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
  public static  class PracticeBot {
    //Motor PWMs
    public static final int 
    LEFT_DRIVE_PWM = 11, //Motors operating left side of the chassis
    RIGHT_DRIVE_PWM = 12, //Motors operating right side of the chassis
    LEFT_ELEVATOR_PWM = 13, //Motor that operates left elevator up and down
    RIGHT_ELEVATOR_PWM = 16, //Motor that operates right elevator up and down
    SHOULDER_PWM = 10, //Motor that rotates the arm to face the front and back
    INTAKE_WHEELS_PWM = 15, //Wheels picking up cargo on the stationary intake
    INTAKE_BELTS_PWM = 19, //Wheels picking up cargo on the stationary intake
    CLIMBING_WHEELS_PWM = 14, //Wheels on the rear pneumatic climbing cylinders (PORT = PCM's PWM)
    //Pneumatic ports
    INTAKE_JAW_PORT = 6, //Pneumatic cylinders opening the stationary intake (PORT = PCM's PWM)
    CLIMBER_FRONT_PORT = 4, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    CLIMBER_REAR_PORT = 5, //Pneumatic cylinders to climb (PORT = PCM's PWM)
    HATCH_VACUUM = 17, //Suction cup motors
    //Potentiometer analog input ports
    SHOULDER_POT_AI = 6,
    RIGHT_ELEVATOR_POT_AI = 7,
    LEFT_ELEVATOR_POT_AI = 5,
    //Servo PWMs
    TOP_SERVO_RELEASE_PWM = 9,
    BOTTOM_SERVO_RELEASE_PWM = 18;
    //Calibration constants
    public static double 
    RIGHT_ELEVATOR_POT_OFFSET= -0.0541499, //how much smaller is the right side
    RIGHT_ELEVATOR_OFFSET_SCALE = 6.0,//how much does the elevator try to be balanced
    RAMP_UP_DISTANCE = 0.06915508,// + 0.08011591,//0.149 is too much
    RAMP_DOWN_DISTANCE = 0.0306812998,//how far does the elevator ramp drive?
    LOW_HEIGHT = 0.735012,//for elevator
    MID_HEIGHT = 0.524817,//for elevator
    HIGH_HEIGHT = 0.40,//0.3537480,//for elevator
    ELEVATOR_THRESHOLD = 0.02,//how close to the correct height does the elevator have to be?
    ELEVATOR_FEED_FORWARD = 0.117,//how much power is needed to keep the elevator at its height?
    ELEVATOR_MAX_SPEED = 0.70,//how fast can the elevator go?
    FORWARD_STRAIGHT_SHOULDER = 0.4545366,
    FORWARD_UP_SHOULDER = 0.47725756,
    REVERSE_UP_SHOULDER = 0.5379968,
    REVERSE_STRAIGHT_SHOULDER = 0.5515320,
    CARGO_PICKUP_SHOULDER = 0.56589,
    SHOULDER_THRESHOLD = 0.005,
    JEVOIS_CENTER = 160.0,
    //FOR VACUUMS:
    MIN_LEFT_VACUUM_CURRENT = 0,
    MAX_LEFT_VACUUM_CURRENT = 0,
    MIN_RIGHT_VACUUM_CURRENT = 0,
    MAX_RIGHT_VACUUM_CURRENT = 0;
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
}
