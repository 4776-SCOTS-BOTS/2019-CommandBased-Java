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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.Robot;
import frc.robot.RobotMap.*;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem that handles the climber. Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
 */
public class ClimberSubsystem extends Subsystem {
  double min=0;
  double max=1;
  PWMVictorSPX climbWheels;
  PWMVictorSPX frontLeftClimbMotor;
  PWMVictorSPX frontRightClimbMotor;
  PWMVictorSPX rearLeftClimbMotor;
  PWMVictorSPX rearRightClimbMotor;
  Potentiometer frontLeftClimbPot;
  Potentiometer frontRightClimbPot;
  Potentiometer rearLeftClimbPot;
  Potentiometer rearRightClimbPot;
  double oldFrontLeftValue;
  double oldFrontRightValue;
  double oldRearLeftValue;
  double oldRearRightValue;
  public boolean isFrontExtended;
  public boolean isRearExtended;

  public final double brokenPot = -0.1;

  private boolean motorsConnected() {
    if (climbWheels == null) return false;
    if (frontLeftClimbMotor == null) return false;
    if (frontRightClimbMotor == null) return false;
    if (rearLeftClimbMotor == null) return false;
    if (rearRightClimbMotor == null) return false;

    return true;
  }
  private boolean potsConnected() {
    if (frontLeftClimbPot == null) return false;
    if (frontRightClimbPot == null) return false;
    if (rearLeftClimbPot == null) return false;
    if (rearRightClimbPot == null) return false;

    return true;
  }
  /**
   * Please remember, <b>FRONT=BEAK/BATTERY, REAR=JAW/CARGO</b>
   */
  public ClimberSubsystem() {
    if (Robot.robotType.canClimb) {
      oldFrontLeftValue=0;
      oldFrontRightValue=0;
      oldRearLeftValue=0;
      oldRearRightValue=0;
      climbWheels = new PWMVictorSPX(Robot.robotType.CLIMBING_WHEELS_PWM);
      //frontLeftClimbMotor = new SpeedControllerGroup(
      //  new PWMVictorSPX(Robot.robotType.FRONT_LEFT_CLIMBING_PWM), new PWMVictorSPX(Robot.robotType.FRONT_RIGHT_CLIMBING_PWM));
      frontLeftClimbMotor = new PWMVictorSPX(Robot.robotType.FRONT_LEFT_CLIMBING_PWM);
      frontRightClimbMotor = new PWMVictorSPX(Robot.robotType.FRONT_RIGHT_CLIMBING_PWM);//front right doesn't exist, there only is front, which is called frontLeft
      rearLeftClimbMotor = new PWMVictorSPX(Robot.robotType.REAR_LEFT_CLIMBING_PWM);
      rearRightClimbMotor = new PWMVictorSPX(Robot.robotType.REAR_RIGHT_CLIMBING_PWM);
      frontLeftClimbPot = new AnalogPotentiometer(Robot.robotType.FRONT_LEFT_CLIMBING_POT_AI);
      frontRightClimbPot = new AnalogPotentiometer(Robot.robotType.FRONT_RIGHT_CLIMBING_POT_AI);
      rearLeftClimbPot = new AnalogPotentiometer(Robot.robotType.REAR_LEFT_CLIMBING_POT_AI);
      rearRightClimbPot = new AnalogPotentiometer(Robot.robotType.REAR_RIGHT_CLIMBING_POT_AI);
      isRearExtended = false;
      isFrontExtended = false;
      System.out.println(Robot.robotType.name + "\'s ClimberSubsystem correctly instantiated and active.");
    }
    else {
    System.out.println("Blank Subsystem for ClimberSubsystem was instantiated.");
    }
  }

  public boolean isBroken() {
    if (getRawFrontLeftPot() < brokenPot){
      return true;
    }
    if (getRawFrontRightPot() < brokenPot){
      return true;
    }
    if (getRawRearLeftPot() < brokenPot){
      return true;
    }
    if (getRawRearRightPot() < brokenPot){
      return true;
    }
    return false;
  }

  //Get raw pot values
  public double getRawFrontLeftPot() {
    if (frontLeftClimbPot!=null)
    {
      return frontLeftClimbPot.get();
    } else {
      return 0;
    }
  } 
  public double getRawFrontRightPot() {
    if (frontRightClimbPot!=null)
    {
      return frontRightClimbPot.get();
    } else {
      return 0;
    }
  } 
  public double getRawRearLeftPot() {
    if (rearLeftClimbPot!=null)
    {
      return rearLeftClimbPot.get();
    } else {
      return 0;
    }
  } 
  public double getRawRearRightPot() {
    if (rearRightClimbPot!=null)
    {
      return rearRightClimbPot.get();
    } else {
      return 0;
    }
  } 
  //Get pot values using system thresholding
  public double getFrontLeftPot(boolean useScale) {
    double a = getRawFrontLeftPot();
    if (a > brokenPot) {
      oldFrontLeftValue = a;
      if (useScale) return map(a,Robot.robotType.FRONT_LEFT_CLIMBING_IN,Robot.robotType.FRONT_LEFT_CLIMBING_OUT,min,max);
      else return a;
    } else {
      if (useScale) return map(oldRearLeftValue,Robot.robotType.FRONT_LEFT_CLIMBING_IN,Robot.robotType.FRONT_LEFT_CLIMBING_IN,min,max);
      else return oldRearLeftValue;
    }
  }
  public double getFrontRightPot(boolean useScale) {
    double a = getRawFrontRightPot();
    if (a > brokenPot) {
      oldFrontRightValue = a;
      if (useScale) return map(a,Robot.robotType.FRONT_RIGHT_CLIMBING_IN,Robot.robotType.FRONT_RIGHT_CLIMBING_OUT,min,max);
      else return a;
    } else {
      if (useScale) return map(oldRearLeftValue,Robot.robotType.FRONT_RIGHT_CLIMBING_IN,Robot.robotType.FRONT_RIGHT_CLIMBING_IN,min,max);
      else return oldRearLeftValue;
    }
  } 
  public double getRearLeftPot(boolean useScale) {
    double a = getRawRearLeftPot();
    if (a > brokenPot) {
      oldRearLeftValue = a;
      if (useScale) return map(a,Robot.robotType.REAR_LEFT_CLIMBING_IN,Robot.robotType.REAR_LEFT_CLIMBING_OUT,min,max);
      else return a;
    } else {
      if (useScale) return map(oldRearLeftValue,Robot.robotType.REAR_LEFT_CLIMBING_IN,Robot.robotType.REAR_LEFT_CLIMBING_OUT,min,max);
      else return oldRearLeftValue;
    }
  } 
  public double getRearRightPot(boolean useScale) {
    double a = getRawRearRightPot();
    if (a > brokenPot) {
      oldRearRightValue = a;
      if (useScale) return map(a,Robot.robotType.REAR_RIGHT_CLIMBING_IN,Robot.robotType.REAR_RIGHT_CLIMBING_OUT,min,max);
      else return a;
    } else {
      if (useScale) return map(oldRearLeftValue,Robot.robotType.REAR_RIGHT_CLIMBING_IN,Robot.robotType.REAR_RIGHT_CLIMBING_IN,min,max);
      else return oldRearLeftValue;
    }
  } 
  public void toggleFront() {
    if (motorsConnected()) {
      isFrontExtended = !isFrontExtended;
      //frontCylinders.set(isFrontExtended);
    }
  }
  public void toggleRear() {
    if (motorsConnected()) {
      isRearExtended = !isRearExtended;
      //rearCylinders.set(isRearExtended);
    }
  }
  public void raiseAllClimbers() {
    if (motorsConnected()) {
      isRearExtended = true;
      isFrontExtended = true;
      //frontCylinders.set(true);
      //rearCylinders.set(true);
    }
  }
  public void lowerAllClimbers() {
    if (motorsConnected()) {
      isRearExtended = false;
      isFrontExtended = false;
      //frontCylinders.set(false);
      //rearCylinders.set(false);
    }
  }
  /**
   * Power the mini wheels on the climber to drive onto the HAB.
   * @param power - how much power
   */
  public void powerClimbWheels(double power) {
    if (motorsConnected()) {
      //System.out.println("POWWW: " + power);
      climbWheels.set(power);
    } else {
      climbWheels.set(0);
    }
  }
  /**
   * Power all four climb motors to the same power.
   * @param power - positive makes the robot go up (Maybe?)
   */
  public void powerClimbers(double power) {
    if (motorsConnected()) {
      frontLeftClimbMotor.set(power);
      frontRightClimbMotor.set(power);
      rearLeftClimbMotor.set(power);
      rearRightClimbMotor.set(power);
    }
  }
  /**
   * Power the front side climb motors one power and the rear motors another power.
   * @param frontPower
   * @param rearPower
   */
  public void powerClimbers(double frontPower, double rearPower) {
    if (motorsConnected()) {
      frontLeftClimbMotor.set(frontPower);
      frontRightClimbMotor.set(frontPower);
      rearLeftClimbMotor.set(rearPower);
      rearRightClimbMotor.set(rearPower);
    }
  }
  /**
   * Power the climb motors, all to different powers. <b>Please be VERY careful when doing this!</b>
   * @param frontLeftPower
   * @param frontRightPower
   * @param rearLeftPower
   * @param rearRightPower
   */
  public void powerClimbers(double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower) {
    if (motorsConnected()) {
      System.out.println("flPow: "+frontLeftPower+", frPow: "+frontRightPower+", rlPow: "+rearLeftPower+", rrPow: "+rearRightPower+", flSca: "+getFrontLeftPot(true)+", frSca: "+getFrontRightPot(true)+", rlSca: "+getRearLeftPot(true)+", rrSca: "+getRearRightPot(true));
      frontLeftClimbMotor.set(frontLeftPower);
      //frontRightClimbMotor.set(Math.max(-1, Math.min(1, frontLeftPower*1.3)));
      frontRightClimbMotor.set(frontRightPower/1);//10
      rearLeftClimbMotor.set(-rearLeftPower);
      rearRightClimbMotor.set(-rearRightPower/1);//13
    }else {
    }
  }

  public void stopClimbWheels() {
    if (motorsConnected()) {
      climbWheels.stopMotor();
    }
  }
  public void stopClimbers() {
    if (motorsConnected()) {
      frontLeftClimbMotor.stopMotor();
      //frontRightClimbMotor.stopMotor();
      rearLeftClimbMotor.stopMotor();
      rearRightClimbMotor.stopMotor();
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ClimberManipulator());
  }
  //Map is a helpful command. Here it is:
  double map(double x, double in_min, double in_max, double out_min, double out_max) {
    return (x-in_min) * (out_max-out_min) / (in_max - in_min) + out_min;
  }
}
