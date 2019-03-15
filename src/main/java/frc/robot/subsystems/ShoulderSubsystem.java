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
  Potentiometer shoulderPot;
  public boolean facingBack;
  public boolean angledUp;
  double pot;

  public void powerShoulder(double power) {
    shoulderMotor.set(power);
    //System.out.println(";;; " + power);
  }
  public void stopShoulder() {
    shoulderMotor.stopMotor();
  }
  public void powerIntake(double power) {
    //intakeMotor.set(power);
  }
  public void stopIntake() {
    //intakeMotor.stopMotor();
  }
  public double getPotValue() {
    if (shoulderPot != null && (shoulderPot.get() > 0.2)) {
      pot = shoulderPot.get();
      return shoulderPot.get();
    } else {
      return pot;
    }
  }

  //Blank constructor: Do not use anything
  public ShoulderSubsystem () {
    System.out.println("Blank Subsystem for ShoulderSubsystem was instantiated.");
  }

  public ShoulderSubsystem(RobotMap.RobotType type) {
    facingBack = false;
    shoulderMotor = new PWMVictorSPX(type.SHOULDER_PWM);
    shoulderPot = new AnalogPotentiometer(type.SHOULDER_POT_AI);
    System.out.println(type.name + "\'s ShoulderSubsystem correctly instantiated.");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ShoulderManipulator());
  }
}
