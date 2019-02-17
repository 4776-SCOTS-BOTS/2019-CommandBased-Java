/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;

/**
 * Subsystem handling movement of the elevator.
 */
public class ElevatorSusbsystem extends Subsystem {
  PWMVictorSPX leftElevatorMotor;
  PWMVictorSPX rightElevatorMotor;
  Encoder leftElevatorEncoder;
  Encoder rightElevatorEncoder;
  boolean useCargo;
  RobotMap.ElevatorHeight currentHeight;

  public ElevatorSusbsystem () {
    this(RobotMap.RobotName.CompBot);
  }
  public boolean getUseCargo () {
    return useCargo;
  }
  public void setUseCargo(boolean _useCargo) {
    useCargo = _useCargo;
  }
  public void toggleUseCargo() {
    useCargo = !useCargo;
  }

  public void setHeight (RobotMap.ElevatorHeight newHeight) {
    currentHeight = newHeight;
  }

  public void setPower (double power) {
    //leftElevatorMotor.set(power);
    //rightElevatorMotor.set(power);
    
  }
  public ElevatorSusbsystem (RobotMap.RobotName robotName) {//Instantiate the driveTrain based on which robot we are using
    switch (robotName) {
      case CompBot: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.RIGHT_ELEVATOR_PWM);
      }
      break;
      case Steve: {
        leftElevatorMotor = null;
        rightElevatorMotor = null;
      }
      break;
      case PracticeBot: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.PracticeBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.PracticeBot.RIGHT_ELEVATOR_PWM);
      }
      case TestBoard: {
        //Don't assign the driveWheels to anything since there aren't any motors
        leftElevatorMotor = null;
        rightElevatorMotor = null;
      }
      break;
      case OldCompBot: {
        leftElevatorMotor = null;
        rightElevatorMotor = null;
      }
      break;
      //Default: Assume robot is CompBot, so perform case CompBot and print the error
      default: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.RIGHT_ELEVATOR_PWM);
        System.out.println("ERROR in ElevatorSubsystem: Invalid RobotName selected when instantiating this susbsystem. Instantiating CompBot anyways.");
      }
      break;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
