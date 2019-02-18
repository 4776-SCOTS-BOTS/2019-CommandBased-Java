/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.*;
import frc.robot.RobotMap.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.*;

/**
 * Subsystem handling all control of the drive train, no matter what robot is used.
 */
public class DriveTrainSubsystem extends Subsystem{// implements PIDOutput{
  DifferentialDrive driveWheels;
  PIDController pidController;

  /*//PIDOutput INTERFACE REQUIREMENT-----------------------------------------
  public void pidWrite(double output) {
    driveWheels.arcadeDrive(output, 0);
  }
  *///END OF REQUIREMENTS

  //Blank Subsystem: Do not create anything
  public DriveTrainSubsystem () {
    this(RobotName.TestBoard);
    System.out.println("Blank Subsystem for DriveTrainSubsystem was instantiated (as TestBoard).");
  }
  public DriveTrainSubsystem (RobotName robotName) {
    //Instantiate the driveTrain based on which robot we are using
    switch (robotName) {
      case CompBot: {
        driveWheels = new DifferentialDrive(new Victor(RobotMap.CompBot.LEFT_DRIVE_PWM), new Victor(RobotMap.CompBot.LEFT_DRIVE_PWM));
      }
      break;
      case Steve: {
        driveWheels = new DifferentialDrive(new Victor(RobotMap.Steve.LEFT_DRIVE_PWM), new Victor(RobotMap.Steve.RIGHT_DRIVE_PWM));
      }
      break;
      case PracticeBot: {
        driveWheels = new DifferentialDrive(new PWMVictorSPX(RobotMap.PracticeBot.LEFT_DRIVE_PWM), new PWMVictorSPX(RobotMap.PracticeBot.RIGHT_DRIVE_PWM));
        //System.out.println("DRIVETRAINSUBSYSTEM: Left Motor: " + RobotMap.PracticeBot.LEFT_DRIVE_PWM + ", Right Motor: " + RobotMap.PracticeBot.RIGHT_DRIVE_PWM);
        //System.out.println("DRIVEWHEELS: ");
      }
      break;
      case TestBoard: {
        //Don't assign the driveWheels to anything since there aren't any motors
        driveWheels = null;
      }
      break;
      case OldCompBot: {
        driveWheels = new DifferentialDrive(new PWMVictorSPX(RobotMap.OldCompBot.LEFT_DRIVE_PWM), new PWMVictorSPX(RobotMap.OldCompBot.RIGHT_DRIVE_PWM));
        //pidController = new PIDController(RobotMap.JeVois.PID_P, RobotMap.JeVois.PID_I, RobotMap.JeVois.PID_D, Robot.jeVois, driveWheels);
      }
      break;
      //Default: Assume robot is CompBot, so perform case CompBot and print the error
      default: {
        driveWheels = new DifferentialDrive(new Victor(RobotMap.CompBot.LEFT_DRIVE_PWM), new Victor(RobotMap.CompBot.RIGHT_DRIVE_PWM));
        System.out.println("ERROR in DriveTrainSubsystem: Invalid RobotName selected when instantiating this susbsystem. Instantiating CompBot anyways.");
      }
      break;
    }
    System.out.println(robotName + "\'s DriveTrainSubsystem correctly instantiated.");
  }
  /**
   * Tank Drive on the DriveTrainSubsystem.
   * @param leftSpeed - the left side's speed (-1.0 to 1.0).
   * @param rightSpeed - the right side's speed (-1.0 to 1.0).
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    driveWheels.tankDrive(leftSpeed, rightSpeed);
  }
  /**
   * Arcade Drive on the DriveTrainSubsystem.
   * @param ySpeed - Forward speed of the robot (-1.0 to 1.0).
   * @param xTurn - Amount to turn the robot (-1.0 to 1.0).
   */
  public void arcadeDrive(double ySpeed, double xTurn) {
    driveWheels.arcadeDrive(ySpeed, xTurn);
  }
  /**
   * Cheesy Drive on the <b>DriveTrainSubsystem</b> (also known as <i>curvature drive</i> or <i>car drive</i>).
   * @param ySpeed - Forward speed of the robot (-1.0 to 1.0).
   * @param xTurn - Amount to turn the robot (-1.0 to 1.0).
   * @param isQuickTurn - Transforms into arcade drive to allow for quick turning.
   */
  public void cheesyDrive(double ySpeed, double xTurn, boolean isQuickTurn) {
    driveWheels.curvatureDrive(ySpeed, xTurn, isQuickTurn);
    //System.out.println("Drivetrain is cheesydriving.");
    //System.out.println(driveWheels.isAlive());
  }
  /**
   * Stop <b>all</b> movement of the drive wheels.
   */
  public void stop() {
    driveWheels.tankDrive(0, 0);
    //driveWheels.stopMotor();
    //System.out.println("Drivetrain has been stopped.");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new CheesyDrive(true));
  }
}
