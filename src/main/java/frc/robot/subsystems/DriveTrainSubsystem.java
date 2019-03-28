/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap.*;
import frc.robot.commands.drivetrain.*;
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
    System.out.println("Blank Subsystem for DriveTrainSubsystem was instantiated.");
  }
  public DriveTrainSubsystem (RobotType type) {
    //Instantiate the driveTrain based on which robot we are using
    System.out.println(type.name + "\'s DriveTrainSubsystem correctly instantiated (With ports " + type.LEFT_DRIVE_PWM + " and " + type.RIGHT_DRIVE_PWM + ").");
    driveWheels = new DifferentialDrive(new PWMVictorSPX(type.LEFT_DRIVE_PWM), new PWMVictorSPX(type.RIGHT_DRIVE_PWM));
  }
  /**
   * Tank Drive on the <b>DriveTrainSubsystem</b>.
   * @param leftSpeed - the left side's speed (-1.0 to 1.0).
   * @param rightSpeed - the right side's speed (-1.0 to 1.0).
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    driveWheels.tankDrive(leftSpeed, rightSpeed);
  }
  /**
   * Arcade Drive on the <b>DriveTrainSubsystem</b>.
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
   * Stop <b>all</b> movement of the <i>drive wheels</i>.
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
    setDefaultCommand(new CheesyDrive(false, true));
  }
}
