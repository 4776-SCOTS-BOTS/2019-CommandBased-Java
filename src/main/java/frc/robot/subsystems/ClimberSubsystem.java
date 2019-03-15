/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.*;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem that handles the climber.
 */
public class ClimberSubsystem extends Subsystem {
  Solenoid frontCylinders;
  Solenoid rearCylinders;
  PWMVictorSPX climbWheels;
  public boolean isFrontExtended;
  public boolean isRearExtended;

  //Blank Constructor: Do not use anything
  public ClimberSubsystem () {
    //this(RobotMap.RobotName.CompBot);
    System.out.println("Blank Subsystem for ClimberSubsystem was instantiated.");
  }

  public ClimberSubsystem (RobotType type) {
    frontCylinders = new Solenoid(type.CLIMBER_FRONT_PORT);
    rearCylinders = new Solenoid(type.CLIMBER_REAR_PORT);
    climbWheels = new PWMVictorSPX(type.CLIMBING_WHEELS_PWM);
    isRearExtended = false;
    isFrontExtended = false;
    System.out.println(type.name + "\'s ClimberSubsystem correctly instantiated.");
  }

  public void toggleFront() {
    isFrontExtended = !isFrontExtended;
    frontCylinders.set(isFrontExtended);
  }
  public void toggleRear() {
    isRearExtended = !isRearExtended;
    rearCylinders.set(isRearExtended);
  }
  public void raiseAllClimbers() {
    isRearExtended = true;
    isFrontExtended = true;
    frontCylinders.set(true);
    rearCylinders.set(true);
  }
  public void lowerAllClimbers() {
    isRearExtended = false;
    isFrontExtended = false;
    frontCylinders.set(false);
    rearCylinders.set(false);
  }
  public void powerClimbWheels(double power) {
    //System.out.println("POWWW: " + power);
    climbWheels.set(power);
  }
  public void stopClimbWheels() {
    climbWheels.stopMotor();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ClimberManipulator());
  }
}
