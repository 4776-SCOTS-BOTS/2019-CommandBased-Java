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
import frc.robot.*;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem handling movement of the elevator.
 */
public class ElevatorSusbsystem extends Subsystem {
  /**
   * <b>Currently NOT in use.</b>
   * <p>Determine if to use joystick input (override) or press buttons and go to height.
   * <p><b>True</b> - Use the button mode
   * <p><b>False</B> - Use the joystick override (default)
   */
  public boolean commandOperated;
  PWMVictorSPX leftElevatorMotor;
  PWMVictorSPX rightElevatorMotor;
  Potentiometer leftElevatorPot;
  Potentiometer rightElevatorPot;
  Encoder leftElevatorEncoder;
  Encoder rightElevatorEncoder;
  RobotMap.ElevatorHeight currentHeight;

  //Blank Constructor: Do not create anything for this subsytem.
  public ElevatorSusbsystem () {
    this(RobotMap.RobotName.TestBoard);
    System.out.println("Blank Subsystem for ElevatorSubsystem was instantiated (as TestBoard).");
  }
  public double getLeftPot() {
    return leftElevatorPot.get();
  }
  public double getRightPot() {
    return rightElevatorPot.get();
  }

  public void setHeight (RobotMap.ElevatorHeight newHeight) {
    currentHeight = newHeight;
  }

  public void setPower (double power) {
    leftElevatorMotor.set(power);
    rightElevatorMotor.set(power);
  }
  public void rawSetPower (double power) {
    leftElevatorMotor.set(power);
    rightElevatorMotor.set(power);
    //System.out.println(power + " " + getLeftPot() + " " + getRightPot());
  }
  public void setPower (double power, double rightMod) {
    if (rightMod > 1) {
      leftElevatorMotor.set(power / rightMod);
      rightElevatorMotor.set(power);
      System.out.println(power/rightMod + " " + power  + " " + getLeftPot() + " " + getRightPot());
  
    } else {
      leftElevatorMotor.set(power);
      rightElevatorMotor.set(power * rightMod);
      System.out.println(power + " " + power*rightMod  + " " + getLeftPot() + " " + getRightPot());
    }
  }
  /**
   * Stop the motors on the elevator.
   */
  public void disableElevator() {
    leftElevatorMotor.stopMotor();
    rightElevatorMotor.stopMotor();
  }
  public ElevatorSusbsystem (RobotMap.RobotName robotName) {//Instantiate the driveTrain based on which robot we are using
    switch (robotName) {
      case CompBot: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.RIGHT_ELEVATOR_PWM);
        leftElevatorPot = new AnalogPotentiometer(RobotMap.CompBot.LEFT_ELEVATOR_POT_AI);
        rightElevatorPot = new AnalogPotentiometer(RobotMap.CompBot.RIGHT_ELEVATOR_POT_AI);
      }
      break;
      case Steve: {
        leftElevatorMotor = null;
        rightElevatorMotor = null;
        leftElevatorPot = null;
        rightElevatorPot = null;
      }
      break;
      case PracticeBot: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.PracticeBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.PracticeBot.RIGHT_ELEVATOR_PWM);
        leftElevatorPot = new AnalogPotentiometer(RobotMap.PracticeBot.LEFT_ELEVATOR_POT_AI);
        rightElevatorPot = new AnalogPotentiometer(RobotMap.PracticeBot.RIGHT_ELEVATOR_POT_AI);
      }
      break;
      case TestBoard: {
        //Don't assign the driveWheels to anything since there aren't any motors
        leftElevatorMotor = null;
        rightElevatorMotor = null;
        leftElevatorPot = null;
        rightElevatorPot = null;
      }
      break;
      case OldCompBot: {
        leftElevatorMotor = null;
        rightElevatorMotor = null;
        leftElevatorPot = null;
        rightElevatorPot = null;
      }
      break;
      //Default: Assume robot is CompBot, so perform case CompBot and print the error
      default: {
        leftElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.LEFT_ELEVATOR_PWM);
        rightElevatorMotor = new PWMVictorSPX(RobotMap.CompBot.RIGHT_ELEVATOR_PWM);
        leftElevatorPot = null;
        rightElevatorPot = null;
        System.out.println("ERROR in ElevatorSubsystem: Invalid RobotName selected when instantiating this susbsystem. Instantiating CompBot anyways.");
      }
      break;
    }
    System.out.println(robotName + "\'s ElevatorSubsystem correctly instantiated.");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ElevatorManipulator());
  }
}
