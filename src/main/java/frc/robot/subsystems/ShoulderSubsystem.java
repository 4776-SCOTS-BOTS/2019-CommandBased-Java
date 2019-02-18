/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;

/**
 * Subsystem handling control of the wrist.
 */
public class ShoulderSubsystem extends Subsystem {
  PWMVictorSPX shoulderMotor;
  boolean facingBack;

  public void power(double power) {
    shoulderMotor.set(power);
  }

  public ShoulderSubsystem () {
    
  }

  public ShoulderSubsystem(RobotMap.RobotName robotName) {
    facingBack = false;
    switch (robotName) {
      case CompBot: {
        shoulderMotor = new PWMVictorSPX(RobotMap.CompBot.SHOULDER_PWM);
      }
      break;
      case Steve: {
        shoulderMotor = new PWMVictorSPX(6);
      }
      break;
      case OldCompBot: {
        shoulderMotor = null;
      }
      break;
      case PracticeBot: {
        //shoulderMotor = new PWMVictorSPX(RobotMap.PracticeBot.SHOULDER_PWM);
      }
      case TestBoard: {
        //Don't assign the driveWheels to anything since there aren't any motors
        shoulderMotor = null;
      }
      break;
      //Default: Assume robot is CompBot, so perform case CompBot and print the error
      default: {
        shoulderMotor = null;
        System.out.println("ERROR in ShoulderSubsystem: Invalid RobotName selected when instantiating this susbsystem. Instantiating CompBot anyways.");
      }
      break;
    }
  }

  public void setFacingSide (boolean faceBack) {
    facingBack = faceBack;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //esetDefaultCommand(new TestCommand());
  }
}
