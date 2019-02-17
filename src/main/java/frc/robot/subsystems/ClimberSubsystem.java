/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Subsystem that handles the climber.
 */
public class ClimberSubsystem extends Subsystem {
  Solenoid frontLeftCylinder;
  Solenoid frontRightCylinder;
  Solenoid rearLeftCylinder;
  Solenoid rearRightCylinder;

  public ClimberSubsystem () {
    this(RobotMap.RobotName.CompBot);
  }

  public ClimberSubsystem (RobotMap.RobotName name) {
    switch (name) {
      case CompBot: {
        frontLeftCylinder = new Solenoid(RobotMap.CompBot.CLIMBER_FRONT_LEFT_PORT);
        frontRightCylinder = new Solenoid(RobotMap.CompBot.CLIMBER_FRONT_RIGHT_PORT);
        frontLeftCylinder = new Solenoid(RobotMap.CompBot.CLIMBER_REAR_LEFT_PORT);
        frontRightCylinder = new Solenoid(RobotMap.CompBot.CLIMBER_REAR_RIGHT_PORT);
      }break;
      case PracticeBot: {
        frontLeftCylinder = new Solenoid(RobotMap.PracticeBot.CLIMBER_FRONT_LEFT_PORT);
        frontRightCylinder = new Solenoid(RobotMap.PracticeBot.CLIMBER_FRONT_RIGHT_PORT);
        frontLeftCylinder = new Solenoid(RobotMap.PracticeBot.CLIMBER_REAR_LEFT_PORT);
        frontRightCylinder = new Solenoid(RobotMap.PracticeBot.CLIMBER_REAR_RIGHT_PORT);
      }break;
      case OldCompBot: {
        frontLeftCylinder = null;
        frontRightCylinder = null;
        rearLeftCylinder = null;
        rearRightCylinder = null;
      }break;
      case Steve: {
        frontLeftCylinder = null;
        frontRightCylinder = null;
        rearLeftCylinder = null;
        rearRightCylinder = null;
      }break;
      case TestBoard: {
        frontLeftCylinder = null;
        frontRightCylinder = null;
        rearLeftCylinder = null;
        rearRightCylinder = null;
      }break;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
