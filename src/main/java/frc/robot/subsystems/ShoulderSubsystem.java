/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.RobotMap;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem handling control of the <b>wrist/shoulder</b> and <b>intake belts</b>.
 */
public class ShoulderSubsystem extends Subsystem {
  PWMVictorSPX shoulderMotor;
  PWMVictorSPX intakeMotor;//the belts holding/shooting cargo
  Potentiometer shoulderPot;
  public boolean facingBack;
  public boolean angledUp;

  public void powerShoulder(double power) {
    shoulderMotor.set(power);
  }
  public void stopShoulder() {
    shoulderMotor.stopMotor();
  }
  public void powerIntake(double power) {
    intakeMotor.set(power);
  }
  public void stopIntake() {
    intakeMotor.stopMotor();
  }
  public double getPotValue() {
    if (shoulderPot != null) {
      return shoulderPot.get();
    } else {
      return 0.0;
    }
  }

  //Blank constructor: Do not use anything
  public ShoulderSubsystem () {
    this(RobotMap.RobotName.TestBoard);
    System.out.println("Blank Subsystem for ShoulderSubsystem was instantiated (as TestBoard).");
  }

  public ShoulderSubsystem(RobotMap.RobotName robotName) {
    facingBack = false;
    switch (robotName) {
      case CompBot: {
        shoulderMotor = new PWMVictorSPX(RobotMap.CompBot.SHOULDER_PWM);
        shoulderPot = new AnalogPotentiometer(RobotMap.CompBot.SHOULDER_POT_AI);
        intakeMotor = new PWMVictorSPX(RobotMap.CompBot.INTAKE_BELTS_PWM);
      }
      break;
      case Steve: {
        shoulderMotor = null;
        shoulderPot = null;
        intakeMotor = null;
      }
      break;
      case OldCompBot: {
        shoulderMotor = null;
        shoulderPot = null;
        intakeMotor = null;
      }
      break;
      case PracticeBot: {
        shoulderMotor = new PWMVictorSPX(RobotMap.PracticeBot.SHOULDER_PWM);
        shoulderPot = new AnalogPotentiometer(RobotMap.PracticeBot.SHOULDER_POT_AI);
        intakeMotor = new PWMVictorSPX(RobotMap.PracticeBot.INTAKE_BELTS_PWM);
      }
      break;
      case TestBoard: {
        //Don't assign the driveWheels to anything since there aren't any motors
        shoulderMotor = null;
        shoulderPot = null;
        intakeMotor = null;
      }
      break;
      //Default: Assume robot is CompBot, so perform case CompBot and print the error
      default: {
        shoulderPot = null;
        shoulderMotor = null;
        intakeMotor = null;
        System.out.println("ERROR in ShoulderSubsystem: Invalid RobotName selected when instantiating this susbsystem. Instantiating CompBot anyways.");
      }
      break;
    }
    System.out.println(robotName + "\'s ShoulderSubsystem correctly instantiated.");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ShoulderManipulator());
  }
}
