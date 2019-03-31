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
import frc.robot.OI.XBox;
import frc.robot.RobotMap.RobotType;

/**
 * During TeleOp, run this command for climbing capability. Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
 */
public class ClimberManipulator extends Command {
  double threshold = 0.03; //How close a climber can be to be at its target
  double power = 0.4; //How much power should normally be applied to climbers
  boolean autoDisable=true; //Once a climber has met its target, should it stop moving anymore or try to stay at its target?
  boolean autoSinc=false; //Should left and right climbers slow and speed up to stay in sinc?
  double sincAffect = 0.1; //How much speed to change to stay in sinc
  double sincThreshold = 0.07;

  //#region Static Variables - Do Not Edit!
  //Overrides
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

/**
 * Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
 */
  public ClimberManipulator() {
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
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
    if (Robot.climber.isRearExtended) {
      //put code here so driving only works on thingys
      //Robot.climber.powerClimbWheels(Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    }
    //Robot.climber.powerClimbWheels(Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    //Robot.climber.powerClimbWheels(0);
    //double speed = 
    //((Robot.oi.getDriverButton(XBox.Y_BUTTON)) ? 1 : 0) +
    //((Robot.oi.getDriverButton(XBox.A_BUTTON)) ? -1 : 0);
    //Robot.climber.powerClimbers(speed);
    //#endregion
    //#region Button Input
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
      System.out.println("GOINT OT LEVEL 2, targ="+frontLeftTarget);
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
    }
    if (Robot.oi.getDriverButton(XBox.LEFT_START_BUTTON)) {
      //Override Button!
      moveFrontLeft=false;
      moveFrontRight=false;
      moveRearLeft=false;
      moveRearRight=false;
    }
    //#endregion
    //#region Move Climbers
    double fl=0,fr=0,rl=0,rr=0;
    //#region Front
    //Front Left
    if (moveFrontLeft) {
    if (!autoSinc) {
        if (Robot.climber.getRawFrontLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getFrontLeftPot(true)-frontLeftTarget)<threshold) {
          //Target met or broken, so stop motors
          fl=0;
          if (autoDisable) moveFrontLeft=false;
        }
        else if (Robot.climber.getFrontLeftPot(true) > frontLeftTarget){
          fl = -power;
        } else {
          fl = power;
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
          fl = -power - sincAffect;
        } else if (Robot.climber.getFrontLeftPot(true) > (Robot.climber.getFrontRightPot(true) + sincThreshold)) {
          //Slow down
          fl = -power + sincAffect;
        }
        else {
          //In sinc!
          fl = -power;
        }
      } else {
        //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
        if (Robot.climber.getFrontLeftPot(true) < (Robot.climber.getFrontRightPot(true) - sincThreshold)) {
          //Slow down
          fl = power - sincAffect;
        } else if (Robot.climber.getFrontLeftPot(true) > (Robot.climber.getFrontRightPot(true) + sincThreshold)) {
          //Speed up
          fl = power + sincAffect;
        }
        else {
          //In sinc!
          fl = power;
        }
        }
      }
    }
    //Front Right
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
    }
    //#endregion
    //#region Rear
    //"Rear" Left
    if (moveRearLeft) {
      if (!autoSinc) {
        System.out.println("RUNNing");
        if (Robot.climber.getRawRearLeftPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearLeftPot(true)-rearLeftTarget)<threshold) {
          //Target met or broken, so stop motors
          rl=0;
          System.out.println("YEET! bcz "+Robot.climber.getRearLeftPot(true)+", so "+rearLeftTarget);
          if (autoDisable) moveRearRight=false;
        }
        else if (Robot.climber.getRearLeftPot(true) < rearLeftTarget){
          System.out.println("on- "+Robot.climber.getRearLeftPot(true)+", when "+rearLeftTarget);
          rl = -power;
        } else {
          System.out.println("teowoo+ "+Robot.climber.getRearLeftPot(true)+", as "+rearLeftTarget);
          rl = power;
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
            rl = -power - sincAffect;
          } else if (Robot.climber.getRearLeftPot(true) > (Robot.climber.getRearRightPot(true) + sincThreshold)) {
            //Slow down
            rl = -power + sincAffect;
          }
          else {
            //In sinc!
            rl = -power;
          }
        } else {
          //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
          if (Robot.climber.getRearLeftPot(true) < (Robot.climber.getRearLeftPot(true) - sincThreshold)) {
            //Slow down
            rl = power - sincAffect;
          } else if (Robot.climber.getRearLeftPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
            //Speed up
            rl = power + sincAffect;
          }
          else {
            //In sinc!
            rl = power;
          }
        }
      }
    } else {
      
      System.out.println("ok, thtas j=not god");
    }
    //"Rear" Right
    if (moveRearRight) {
      if (!autoSinc) {
        //Old
        if (Robot.climber.getRawRearRightPot()<Robot.climber.brokenPot || Math.abs(Robot.climber.getRearRightPot(true)-rearRightTarget)<threshold) {
          //Target met or broken, so stop motors
          rr=0;
          if (autoDisable) moveRearRight=false;
        }
        else if (Robot.climber.getRearRightPot(true) > rearRightTarget){
          rr = -power;
        } else {
          rr = power;
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
            rr = -power - sincAffect;
          } else if (Robot.climber.getRearRightPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
            //Slow down
            rr = -power + sincAffect;
          }
          else {
            //In sinc!
            rr = -power;
          }
        } else {
          //Go down (negative lowers climbers (?) and lower pot=sucked in climber)
          if (Robot.climber.getRearRightPot(true) < (Robot.climber.getRearLeftPot(true) - sincThreshold)) {
            //Slow down
            rr = power - sincAffect;
          } else if (Robot.climber.getRearRightPot(true) > (Robot.climber.getRearLeftPot(true) + sincThreshold)) {
            //Speed up
            rr = power + sincAffect;
          }
          else {
            //In sinc!
            rr = power;
          }
        }
      }
    }
    //#endregion
  /*  //#region old rear (DONT USE! - It is commented out)
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
  */  //#endregion
    System.out.println("releet:"+rl);
    Robot.climber.powerClimbers(fl, fr, rl, rr);
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
}
