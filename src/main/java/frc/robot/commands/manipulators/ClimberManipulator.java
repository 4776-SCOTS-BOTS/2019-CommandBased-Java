/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.manipulators;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.OI.XBox;
import frc.robot.RobotMap.RobotType;
enum ClimbStyle{
  Classic, //Press a button and it goes to that height
  Joystick, //Climb using joysticks
  Button //Classic but only runs when a button (any of the height buttons) is pressed

}
/**
 * During TeleOp, run this command for climbing capability. Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
 */
public class ClimberManipulator extends Command {
  ClimbStyle style = ClimbStyle.Joystick;
  double threshold = 0.015; //How close a climber can be to be at its target
  double frontPower = 0.75; //How much power should normally be applied to climbers
  double rearPower = 0.65; //How much power should normally be applied to climbers
  boolean autoDisable=false; //Once a climber has met its target, should it stop moving anymore or try to stay at its target?
  boolean autoSinc=false; //Should left and right climbers slow and speed up to stay in sinc?
  double sincAffect = 0.1; //How much speed to change to stay in sinc
  double sincThreshold = 0.07;
  double slower=1.5;

  //#region Static Variables - Do Not Edit!
  //Overrides
  boolean joystickInput;

  boolean moveFrontLeft;
  boolean moveFrontRight;
  boolean moveRearLeft;
  boolean moveRearRight;
  double frontLeftTarget;
  double frontRightTarget;
  double rearLeftTarget;
  double rearRightTarget;
  //FL
  double flMin;
  double flMid;
  double flMax;
  //FR
  double frMin;
  double frMid;
  double frMax;
  //RL
  double rlMin;
  double rlMid;
  double rlMax;
  //RR
  double rrMin;
  double rrMid;
  double rrMax;
  //#endregion
  double max = 0.98;
  double min = 0.02;
  //Ramping:
  double limitChange = 0.05;
  double frontOldOutput = 0;
  double rearOldOutput = 0;
  boolean useRamp = true;
  double slowedSpeed = 0.1;
  boolean independence = true;
  boolean slowMax = true;
  boolean slowMin = true;
  boolean useFeed = false;
  double leftFeedPoint=map(0.5962392285008346, Robot.robotType.FRONT_LEFT_CLIMBING_IN, Robot.robotType.FRONT_LEFT_CLIMBING_OUT, 0, 1);
  double rightFeedPoint=map(0.9404858708145847, Robot.robotType.FRONT_RIGHT_CLIMBING_IN, Robot.robotType.FRONT_RIGHT_CLIMBING_OUT, 0, 1);
/**
 * Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
 */
  public ClimberManipulator() {
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    joystickInput=false;
    moveFrontLeft=false;
    moveFrontRight=false;
    moveRearLeft=false;
    moveRearRight=false;
    flMin=0;
    frMin=0;
    rlMin=0;
    rrMin=0;
    flMax=1;
    frMax=1;
    rlMax=1;
    rrMax=1;
    flMid=map(Robot.robotType.FRONT_LEFT_CLIMBING_MID, Robot.robotType.FRONT_LEFT_CLIMBING_IN, Robot.robotType.FRONT_LEFT_CLIMBING_OUT, 0, 1);
    frMid=map(Robot.robotType.FRONT_RIGHT_CLIMBING_MID, Robot.robotType.FRONT_RIGHT_CLIMBING_IN, Robot.robotType.FRONT_RIGHT_CLIMBING_OUT, 0, 1);
    rlMid=map(Robot.robotType.REAR_LEFT_CLIMBING_MID, Robot.robotType.REAR_LEFT_CLIMBING_IN, Robot.robotType.REAR_LEFT_CLIMBING_OUT, 0, 1);
    rrMid=map(Robot.robotType.REAR_RIGHT_CLIMBING_MID, Robot.robotType.REAR_RIGHT_CLIMBING_IN, Robot.robotType.REAR_RIGHT_CLIMBING_OUT, 0, 1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    //#region Old Code
    
    //Robot.climber.powerClimbWheels(Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    //Robot.climber.powerClimbWheels(0);
    //double speed = 
    //((Robot.oi.getDriverButton(XBox.Y_BUTTON)) ? 1 : 0) +
    //((Robot.oi.getDriverButton(XBox.A_BUTTON)) ? -1 : 0);
    //Robot.climber.powerClimbers(speed);
    //#endregion
    
    //#region Button Input
    switch(style) {
      case Classic: {
        classic();
      }break;
      case Joystick: {
        joystick();
      }break;
      case Button: {
        button();
      }break;
      default: {
        System.out.println("No climb style detected! Running classic system!");
        classic();
      }
    }
    //#endregion
    //#region Move Climbers
    double fl=0,fr=0,rl=0,rr=0;
    if (style!=ClimbStyle.Joystick){
          //#region Front Left
        if (moveFrontLeft) {
          if (!autoSinc) {
              if (Robot.climber.getRawFrontLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getFrontLeftPot(true)-frontLeftTarget)<threshold) {
                //Target met or broken, so stop motors
                fl=0;
                if (autoDisable) moveFrontLeft=false;
              }
              else if (Robot.climber.getFrontLeftPot(true) > frontLeftTarget){
                fl = -frontPower/slower;
              } else {
                fl = frontPower;
              }
            
          } else {
            if (Robot.climber.getRawFrontRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getFrontRightPot(true)-frontRightTarget)<threshold) {
              //Target met or broken, so stop motors
              fl=0;
              if (autoDisable) moveFrontLeft=false;
            }
            else if (Robot.climber.getFrontLeftPot(true) < frontLeftTarget){
              //Go up (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getFrontLeftPot(true) < (Robot.climber.getFrontRightPot(true) - sincThreshold)) {
                //Speed up
                fl = -frontPower - sincAffect;
              } else if (Robot.climber.getFrontLeftPot(true) > (Robot.climber.getFrontRightPot(true) + sincThreshold)) {
                //Slow down
                fl = -frontPower + sincAffect;
              }
              else {
                //In sinc!
                fl = -frontPower;
              }
            } else {
              //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getFrontLeftPot(true) < (Robot.climber.getFrontRightPot(true) - sincThreshold)) {
                //Slow down
                fl = frontPower - sincAffect;
              } else if (Robot.climber.getFrontLeftPot(true) > (Robot.climber.getFrontRightPot(true) + sincThreshold)) {
                //Speed up
                fl = frontPower + sincAffect;
              }
              else {
                //In sinc!
                fl = frontPower;
              }
              }
            }
        } else {
          fl=0;
        }
        //#endregion
      /*  //#region Front Right
        if (moveFrontRight) {
          if (!autoSinc) {
            //Old
            if (Robot.climber.getRawFrontRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getFrontRightPot(true)-frontRightTarget)<threshold) {
              //Target met or broken, so stop motors
              fr=0;
              if (autoDisable) moveFrontRight=false;
            }
            else if (Robot.climber.getFrontRightPot(true) < frontRightTarget){
              fr = -power;
            } else {
              fr = power;
            }
          } else {
            //New
            if (Robot.climber.getRawFrontRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getFrontRightPot(true)-frontRightTarget)<threshold) {
              //Target met or broken, so stop motors
              fr=0;
              if (autoDisable) moveFrontRight=false;
            }
            else if (Robot.climber.getFrontRightPot(true) < frontRightTarget){
              //Go up (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getFrontRightPot(true) < (Robot.climber.getFrontLeftPot(true) - sincThreshold)) {
                //Speed up
                fr = -power - sincAffect;
              } else if (Robot.climber.getFrontRightPot(true) > (Robot.climber.getFrontLeftPot(true) + sincThreshold)) {
                //Slow down
                fr = -power + sincAffect;
              }
              else {
                //In sinc!
                fr = -power;
              }
            } else {
              //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getFrontRightPot(true) < (Robot.climber.getFrontLeftPot(true) - sincThreshold)) {
                //Slow down
                fr = power - sincAffect;
              } else if (Robot.climber.getFrontRightPot(true) > (Robot.climber.getFrontLeftPot(true) + sincThreshold)) {
                //Speed up
                fr = power + sincAffect;
              }
              else {
                //In sinc!
                fr = power;
              }
            }
          }
        } else {
          fr=0;
        }
      */  //#endregion
        //#region Rear Left
        if (moveRearLeft) {
          if (!autoSinc) {
            if (Robot.climber.getRawRearLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearLeftPot(true)-rearLeftTarget)<threshold) {
              //Target met or broken, so stop motors
              rl=0;
              //System.out.println("YEET! bcz "+Robot.climber.getRearLeftPot(true)+", so "+rearLeftTarget);
              if (autoDisable) moveRearRight=false;
            }
            else if (Robot.climber.getRearLeftPot(true) > rearLeftTarget){
              //System.out.println("on- "+Robot.climber.getRearLeftPot(true)+", when "+rearLeftTarget);
              rl = -rearPower/slower;
            } else {
              //System.out.println("teowoo+ "+Robot.climber.getRearLeftPot(true)+", as "+rearLeftTarget);
              rl = rearPower;
            }
          } else {
            if (Robot.climber.getRawRearLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearLeftPot(true)-rearLeftTarget)<threshold) {
              //Target met or broken, so stop motors
              rl=0;
              if (autoDisable) moveRearLeft=false;
            }
            else if (Robot.climber.getRearLeftPot(true) < rearLeftTarget){
              //Go up (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getRearLeftPot(true) < (Robot.climber.getRearRightPot(true) - sincThreshold)) {
                //Speed up
                rl = -rearPower - sincAffect;
              } else if (Robot.climber.getRearLeftPot(true) > (Robot.climber.getRearRightPot(true) + sincThreshold)) {
                //Slow down
                rl = -rearPower + sincAffect;
              }
              else {
                //In sinc!
                rl = -rearPower;
              }
            } else {
              //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getRearLeftPot(true) < (Robot.climber.getRearLeftPot(true) - sincThreshold)) {
                //Slow down
                rl = rearPower - sincAffect;
              } else if (Robot.climber.getRearLeftPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
                //Speed up
                rl = rearPower + sincAffect;
              }
              else {
                //In sinc!
                rl = rearPower;
              }
            }
          }
        } else {
          rl=0;
          //System.out.println("Right Left has been overriden");
        }
        //#endregion
        //#region Rear Right
        if (moveRearRight) {
          if (!autoSinc) {
            //Old
            if (Robot.climber.getRawRearRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearRightPot(true)-rearRightTarget)<threshold) {
              //Target met or broken, so stop motors
              rr=0;
              if (autoDisable) moveRearRight=false;
            }
            else if (Robot.climber.getRearRightPot(true) > rearRightTarget){
              rr = -rearPower/slower;
            } else {
              rr = rearPower;
            }
          } else {
            //New
            if (Robot.climber.getRawRearRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearRightPot(true)-rearRightTarget)<threshold) {
              //Target met or broken, so stop motors
              rr=0;
              if (autoDisable) moveRearRight=false;
            }
            else if (Robot.climber.getRearRightPot(true) < rearRightTarget){
              //Go up (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getRearRightPot(true) < (Robot.climber.getRearLeftPot(true) - sincThreshold)) {
                //Speed up
                rr = -rearPower - sincAffect;
              } else if (Robot.climber.getRearRightPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
                //Slow down
                rr = -rearPower + sincAffect;
              }
              else {
                //In sinc!
                rr = -rearPower;
              }
            } else {
              //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
              if (Robot.climber.getRearRightPot(true) < (Robot.climber.getRearLeftPot(true) - sincThreshold)) {
                //Slow down
                rr = rearPower - sincAffect;
              } else if (Robot.climber.getRearRightPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
                //Speed up
                rr = rearPower + sincAffect;
              }
              else {
                //In sinc!
                rr = rearPower;
              }
            }
          }
        } else {
          rr=0;
        }
        //#endregion
      /*//#region old rear (DONT USE! - It is commented out)
        //Rear Left
        if (moveRearLeft) {
          if (Robot.climber.getRawRearLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearLeftPot(true)-rearLeftTarget)<threshold) {
            //Target met or broken, so stop motors
            rl=0;
            if (autoDisable) moveRearLeft=false;
          }
          else if (Robot.climber.getRearLeftPot(true) < rearLeftTarget){
            rl = -power;
          } else {
            rl = power;
          }
        }
        //Rear Right
        if (moveRearRight) {
          if (Robot.climber.getRawRearRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearRightPot(true)-rearRightTarget)<threshold) {
            //Target met or broken, so stop motors
            rr=0;
            if (autoDisable) moveRearRight=false;
          }
          else if (Robot.climber.getRearRightPot(true) < rearRightTarget){
            rr = -power;
          } else {
            rr = power;
          }
        }
      *///#endregion
    } else {
      if (joystickInput) {
        //#region Ramping
        if (useRamp) {
          double frontDown = -Robot.oi.getManipulatorAxis(XBox.RIGHT_Y_AXIS);
          //double favorFrontLeft = Robot.oi.getDriverAxis(XBox.RIGHT_X_AXIS);
          double rearDown = -Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS);
          //double favorRearLeft = Robot.oi.getDriverAxis(XBox.LEFT_X_AXIS);
          double frontChange = frontDown - frontOldOutput;
          frontChange = Math.max(-limitChange, Math.min(frontChange, limitChange));//clamp change
          frontOldOutput += frontChange;
          frontOldOutput = Math.min(0.99, Math.max(frontOldOutput, -0.99));
          double rearChange = rearDown - rearOldOutput;
          rearChange = Math.max(-limitChange, Math.min(rearChange, rearChange));//clamp change
          rearOldOutput += rearChange;
          rearOldOutput = Math.min(0.99, Math.max(rearOldOutput, -0.99));
        } else {
          frontOldOutput=-Robot.oi.getManipulatorAxis(XBox.RIGHT_Y_AXIS);
          rearOldOutput=-Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS);
        }
        //#endregion
        //#region Limit Speed
        boolean frontLeftMaxed=((Robot.climber.getFrontLeftPot(true))>max);
        boolean frontRightMaxed=(Robot.climber.getFrontRightPot(true)>max);
        boolean rearLeftMaxed = ((Robot.climber.getRearLeftPot(true))>max);
        boolean rearRightMaxed = (Robot.climber.getRearRightPot(true)>max);
        boolean frontLeftMinned=((Robot.climber.getFrontLeftPot(true))<min);
        boolean frontRightMinned=(Robot.climber.getFrontRightPot(true)<min);
        boolean rearLeftMinned = ((Robot.climber.getRearLeftPot(true))<min);
        boolean rearRightMinned = ((Robot.climber.getRearRightPot(true))<min);
        frontLeftMinned = frontLeftMinned && (frontOldOutput>0);
        frontRightMinned = frontRightMinned && (frontOldOutput>0);
        rearLeftMinned = rearLeftMinned && (rearOldOutput>0);
        rearRightMinned = rearRightMinned && (rearOldOutput>0);
        frontLeftMaxed = frontLeftMaxed && (frontOldOutput<0);
        frontRightMaxed = frontRightMaxed && (frontOldOutput<0);
        rearLeftMaxed = rearLeftMaxed && (rearOldOutput<0);
        rearRightMaxed = rearRightMaxed && (rearOldOutput<0);
        boolean frontAlmost=((Robot.climber.getFrontLeftPot(true))>leftFeedPoint||(Robot.climber.getFrontRightPot(true)>rightFeedPoint));

        double frontSlowDown = (useFeed && frontAlmost) ? slowedSpeed : 1;
        double rearSlowDown = (useFeed && frontAlmost) ? slowedSpeed : 1;
        double frontLeftSlowDown = (slowMax && frontLeftMaxed) || (slowMin && frontLeftMinned) ? 0 : 1;
        double frontRightSlowDown = (slowMax && frontRightMaxed) || (slowMin && frontRightMinned) ? 0 : 1;
        double rearLeftSlowDown = (slowMax && rearLeftMaxed) || (slowMin && rearLeftMinned) ? 0 : 1;
        double rearRightSlowDown = (slowMax && rearRightMaxed) || (slowMin && rearRightMinned) ? 0 : 1;
        //#endregion
        //System.out.println("frontleft- "+frontLeftMinned+frontLeftSlowDown+":"+fl+", frontright- "+frontRightMinned+frontRightSlowDown+":"+fr);
        //System.out.println("frMin: "+frontMinned+", frMax: "+frontMaxed+", reMin: "+rearMinned+", reMax: "+rearMaxed);
        fl=frontOldOutput*1*frontLeftSlowDown;
        fr=frontOldOutput*1*frontRightSlowDown;
        rl=rearOldOutput*1*rearLeftSlowDown;
        rr=rearOldOutput*1*rearRightSlowDown;
        //System.out.println("AAAAAA"+rearOldOutput);
        //System.out.println("frontleft- "+rearLeftMinned+rearLeftSlowDown+":"+rl+", reantright- "+rearRightMinned+rearRightSlowDown+":"+rr);
      } else {
        //Turn off wheels
        fl=0;
        fr=0;
        rl=0;
        rr=0;
        frontOldOutput=0;
        rearOldOutput=0;
      }
    }
    
    //System.out.println("Right Left Power:"+rl + " and the real is "+Robot.climber.getRawRearLeftPot()+" and rr="+rr);
    System.out.println("FL: "+Robot.climber.getRawFrontLeftPot()+", FR: "+Robot.climber.getRawFrontRightPot()+
          ", RL: "+Robot.climber.getRawRearLeftPot()+", RR: "+Robot.climber.getRawRearRightPot());
    //System.out.println("fl="+moveFrontLeft+", fr="+moveFrontRight+", rl="+moveRearLeft+", rr"+moveRearRight);
    Robot.climber.powerClimbers(fl, fr, rl, rr);
    if (Robot.oi.getDriverButton(XBox.RIGHT_BUMPER_BUTTON)) {
      Robot.climber.powerClimbWheels(Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    } else {
      Robot.climber.powerClimbWheels(0);
    }
    //#endregion
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.stopClimbWheels();
    Robot.climber.stopClimbers();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
  double map(double x, double in_min, double in_max, double out_min, double out_max) {
    return (x-in_min) * (out_max-out_min) / (in_max - in_min) + out_min;
  }
  double deadband(double x, double target, double threshold) {
    if (Math.abs(x-target)<threshold){
      return target;
    } else {
      return x;
    }
  }

  void classic(){
    //Take button input (A, B, X, Y)
    if (Robot.oi.getDriverButton(XBox.Y_BUTTON)){
      //Climb up to level 3 - Lower all climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_OUT;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_OUT;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_OUT;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_OUT;*/
      frontLeftTarget=flMax;
      frontRightTarget=frMax;
      rearLeftTarget=rlMax;
      rearRightTarget=rrMax;
      moveFrontLeft=true;
      moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.03;
    }
    if (Robot.oi.getDriverButton(XBox.A_BUTTON)) {
      //Climb up to level 2 - Partially lower all climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_MID;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_MID;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_MID;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_MID;*/
      frontLeftTarget=flMid;
      frontRightTarget=frMid;
      rearLeftTarget=rlMid;
      rearRightTarget=rrMid;
      moveFrontLeft=true;
      moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.03;
    }
    if (Robot.oi.getDriverButton(XBox.X_BUTTON)) {
      //Raise jaw side (the first side) climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_IN;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_IN;
      //rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_IN;
      //rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_IN;*/
      //frontLeftTarget=flMin;
      //frontRightTarget=frMin;
      rearLeftTarget=rlMin;
      rearRightTarget=rrMin;
      //moveFrontLeft=true;
      //moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.015;
    }
    if (Robot.oi.getDriverButton(XBox.B_BUTTON)) {
      //Raise beak side (the last side) climbers
      /*//frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_IN;
      //frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_IN;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_IN;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_IN;*/
      frontLeftTarget=flMin;
      frontRightTarget=frMin;
      //rearLeftTarget=rlMin;
      //rearRightTarget=rrMin;
      moveFrontLeft=true;
      moveFrontRight=true;
      //moveRearLeft=true;
      //moveRearRight=true;
      threshold=0.015;
    }
    if (Robot.oi.getDriverButton(XBox.LEFT_START_BUTTON)) {
      //Override Button!
      moveFrontLeft=false;
      moveFrontRight=false;
      moveRearLeft=false;
      moveRearRight=false;
    }
  }
  void joystick(){
    autoDisable=false;
    joystickInput=Robot.oi.getDriverButton(XBox.RIGHT_BUMPER_BUTTON);
  }
  void button(){
    //Take button input (A, B, X, Y)
    boolean oopsie = true;
    if (Robot.oi.getDriverButton(XBox.Y_BUTTON)){
      oopsie = false;
      //Climb up to level 3 - Lower all climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_OUT;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_OUT;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_OUT;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_OUT;*/
      frontLeftTarget=flMax;
      frontRightTarget=frMax;
      rearLeftTarget=rlMax;
      rearRightTarget=rrMax;
      moveFrontLeft=true;
      moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.03;
    }

    if (Robot.oi.getDriverButton(XBox.A_BUTTON)) {
      oopsie = false;
      //Climb up to level 2 - Partially lower all climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_MID;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_MID;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_MID;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_MID;*/
      frontLeftTarget=flMid;
      frontRightTarget=frMid;
      rearLeftTarget=rlMid;
      rearRightTarget=rrMid;
      moveFrontLeft=true;
      moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.03;
    }
    if (Robot.oi.getDriverButton(XBox.X_BUTTON)) {
      oopsie = false;
      //Raise jaw side (the first side) climbers
      /*frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_IN;
      frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_IN;
      //rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_IN;
      //rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_IN;*/
      //frontLeftTarget=flMin;
      //frontRightTarget=frMin;
      rearLeftTarget=rlMin;
      rearRightTarget=rrMin;
      //moveFrontLeft=true;
      //moveFrontRight=true;
      moveRearLeft=true;
      moveRearRight=true;
      threshold=0.015;
    }
    if (Robot.oi.getDriverButton(XBox.B_BUTTON)) {
      oopsie = false;
      //Raise beak side (the last side) climbers
      /*//frontLeftTarget=Robot.robotType.FRONT_LEFT_CLIMBING_IN;
      //frontRightTarget=Robot.robotType.FRONT_RIGHT_CLIMBING_IN;
      rearLeftTarget=Robot.robotType.REAR_LEFT_CLIMBING_IN;
      rearRightTarget=Robot.robotType.REAR_RIGHT_CLIMBING_IN;*/
      frontLeftTarget=flMin;
      frontRightTarget=frMin;
      //rearLeftTarget=rlMin;
      //rearRightTarget=rrMin;
      moveFrontLeft=true;
      moveFrontRight=true;
      //moveRearLeft=true;
      //moveRearRight=true;
      threshold=0.015;
    }
    if (Robot.oi.getDriverButton(XBox.LEFT_START_BUTTON)) {
      oopsie = true;
      //Override Button!
      moveFrontLeft=false;
      moveFrontRight=false;
      moveRearLeft=false;
      moveRearRight=false;
    }
    if (oopsie){
      //Oopsie! I didn't press anything!
      moveFrontLeft=false;
      moveFrontRight=false;
      moveRearLeft=false;
      moveRearRight=false;
    }
  }
}
