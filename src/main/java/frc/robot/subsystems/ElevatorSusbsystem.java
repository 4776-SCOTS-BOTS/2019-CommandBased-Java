/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.*;
import frc.robot.RobotMap.*;
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
  double oldHeight;

  public double getLeftPot() {
    return leftElevatorPot.get();
  }
  public double getRightPot() {
    if (leftElevatorPot.get() > 0.1) {
      oldHeight = leftElevatorPot.get();
    return leftElevatorPot.get();//TODO: dont use left on right
    } else {
      return oldHeight;
    }
  }

  public void setHeight (RobotMap.ElevatorHeight newHeight) {
    currentHeight = newHeight;
  }

  public void setPower (double power) {
    leftElevatorMotor.set(power);
    rightElevatorMotor.set(power);
    //System.out.println("THE SPEED: " + power);
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
  public ElevatorSusbsystem () {//Instantiate the driveTrain based on which robot we are using
    if (Robot.robotType.hasAnElevator) {
      leftElevatorMotor = new PWMVictorSPX(Robot.robotType.LEFT_ELEVATOR_PWM);
      rightElevatorMotor = new PWMVictorSPX(Robot.robotType.RIGHT_ELEVATOR_PWM);
      leftElevatorPot = new AnalogPotentiometer(Robot.robotType.LEFT_ELEVATOR_POT_AI);
      rightElevatorPot = new AnalogPotentiometer(Robot.robotType.RIGHT_ELEVATOR_POT_AI);
      System.out.println(Robot.robotType.name + "\'s ElevatorSubsystem correctly instantiated.");
    } else {
      System.out.println("Blank Subsystem for ElevatorSubsystem was instantiated.");
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    if (Robot.robotType.hasAnElevator) {
      setDefaultCommand(new ElevatorManipulator());
    }
  }
}
