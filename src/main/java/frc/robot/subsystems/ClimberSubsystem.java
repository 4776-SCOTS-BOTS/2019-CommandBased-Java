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
import frc.robot.RobotMap;
import frc.robot.commands.*;

/**
 * Subsystem that handles the climber.
 */
public class ClimberSubsystem extends Subsystem {
  Solenoid frontCylinders;
  Solenoid rearCylinders;
  PWMVictorSPX climbWheels;
  boolean isFrontExtended;
  boolean isRearExtended;

  //Blank Constructor: Do not use anything
  public ClimberSubsystem () {
    //this(RobotMap.RobotName.CompBot);
    this(RobotMap.RobotName.TestBoard);
    System.out.println("Blank Subsystem for ClimberSubsystem was instantiated (as TestBoard).");
  }

  public ClimberSubsystem (RobotMap.RobotName robotName) {
    switch (robotName) {
      case CompBot: {
        frontCylinders = null;
        rearCylinders = null;
        climbWheels = null;
      }break;
      case PracticeBot: {
        frontCylinders = new Solenoid(RobotMap.PracticeBot.CLIMBER_FRONT_PORT);
        rearCylinders = new Solenoid(RobotMap.PracticeBot.CLIMBER_REAR_PORT);
        climbWheels = new PWMVictorSPX(RobotMap.PracticeBot.CLIMBING_WHEELS_PWM);
      }break;
      case OldCompBot: {
        frontCylinders = null;
        rearCylinders = null;
        climbWheels = null;
      }break;
      case Steve: {
        frontCylinders = null;
        rearCylinders = null;
        climbWheels = null;
      }break;
      case TestBoard: {
        frontCylinders = null;
        rearCylinders = null;
        climbWheels = null;
      }break;
    }
    isRearExtended = false;
    isFrontExtended = false;
    System.out.println(robotName + "\'s ClimberSubsystem correctly instantiated.");
  }

  public void toggleFront() {
    isFrontExtended = !isFrontExtended;
    frontCylinders.set(isFrontExtended);
  }
  public void toggleRear() {
    isRearExtended = !isRearExtended;
    rearCylinders.set(isRearExtended);
  }
  public void powerClimbWheels(double power) {
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
