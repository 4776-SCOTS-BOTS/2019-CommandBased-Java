/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.RobotMap.*;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem that handles the climber.
 */
public class ClimberSubsystem extends Subsystem {
  PWMVictorSPX climbWheels;
  PWMVictorSPX frontLeftClimbMotor;
  PWMVictorSPX frontRightClimbMotor;
  PWMVictorSPX rearLeftClimbMotor;
  PWMVictorSPX rearRightClimbMotor;
  Potentiometer frontLeftClimbPot;
  Potentiometer frontRightClimbPot;
  Potentiometer rearLeftClimbPot;
  Potentiometer rearRightClimbPot;
  public boolean isFrontExtended;
  public boolean isRearExtended;

  //Blank Constructor: Do not use anything
  public ClimberSubsystem () {
    //this(RobotMap.RobotName.CompBot);
    System.out.println("Blank Subsystem for ClimberSubsystem was instantiated.");
  }

  public ClimberSubsystem (RobotType type) {
    climbWheels = new PWMVictorSPX(type.CLIMBING_WHEELS_PWM);
    frontLeftClimbMotor = new PWMVictorSPX(type.FRONT_LEFT_CLIMBING_PWM);
    frontRightClimbMotor = new PWMVictorSPX(type.FRONT_RIGHT_CLIMBING_PWM);
    rearLeftClimbMotor = new PWMVictorSPX(type.REAR_LEFT_CLIMBING_PWM);
    rearRightClimbMotor = new PWMVictorSPX(type.REAR_RIGHT_CLIMBING_PWM);
    frontLeftClimbPot = new AnalogPotentiometer(type.FRONT_LEFT_CLIMBING_POT_AI);
    frontRightClimbPot = new AnalogPotentiometer(type.FRONT_RIGHT_CLIMBING_POT_AI);
    rearLeftClimbPot = new AnalogPotentiometer(type.REAR_LEFT_CLIMBING_POT_AI);
    rearRightClimbPot = new AnalogPotentiometer(type.REAR_RIGHT_CLIMBING_POT_AI);
    isRearExtended = false;
    isFrontExtended = false;
    System.out.println(type.name + "\'s ClimberSubsystem correctly instantiated.");
  }

  public void toggleFront() {
    isFrontExtended = !isFrontExtended;
    //frontCylinders.set(isFrontExtended);
  }
  public void toggleRear() {
    isRearExtended = !isRearExtended;
    //rearCylinders.set(isRearExtended);
  }
  public void raiseAllClimbers() {
    isRearExtended = true;
    isFrontExtended = true;
    //frontCylinders.set(true);
    //rearCylinders.set(true);
  }
  public void lowerAllClimbers() {
    isRearExtended = false;
    isFrontExtended = false;
    //frontCylinders.set(false);
    //rearCylinders.set(false);
  }
  /**
   * Power the mini wheels on the climber to drive onto the HAB.
   * @param power - how much power - positive drives torward to HAB platform.
   */
  public void powerClimbWheels(double power) {
    //System.out.println("POWWW: " + power);
    climbWheels.set(power);
  }
  /**
   * Power all four climb motors to the same power.
   * @param power - positive makes the robot go up (Maybe?)
   */
  public void powerClimbers(double power) {
    frontLeftClimbMotor.set(power);
    frontRightClimbMotor.set(power);
    rearLeftClimbMotor.set(power);
    rearRightClimbMotor.set(power);
  }
  /**
   * Power the front side climb motors one power and the rear motors another power.
   * @param frontPower
   * @param rearPower
   */
  public void powerClimbers(double frontPower, double rearPower) {
    frontLeftClimbMotor.set(frontPower);
    frontRightClimbMotor.set(frontPower);
    rearLeftClimbMotor.set(rearPower);
    rearRightClimbMotor.set(rearPower);
  }
  /**
   * Power the climb motors, all to different powers. <b>Please be VERY careful when doing this!</b>
   * @param frontLeftPower
   * @param frontRightPower
   * @param rearLeftPower
   * @param rearRightPower
   */
  public void powerClimbers(double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower) {
    frontLeftClimbMotor.set(frontLeftPower);
    frontRightClimbMotor.set(frontRightPower);
    rearLeftClimbMotor.set(rearLeftPower);
    rearRightClimbMotor.set(rearRightPower);
  }

  public void stopClimbWheels() {
    climbWheels.stopMotor();
  }
  public void stopClimbers() {
    frontLeftClimbMotor.stopMotor();
    frontRightClimbMotor.stopMotor();
    rearLeftClimbMotor.stopMotor();
    rearRightClimbMotor.stopMotor();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ClimberManipulator());
  }
}
