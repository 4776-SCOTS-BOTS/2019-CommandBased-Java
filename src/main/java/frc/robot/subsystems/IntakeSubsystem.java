/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
       
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.RobotMap.RobotName;
import frc.robot.commands.*;

/**
 * Subsystem handling control of the intake.
 */
public class IntakeSubsystem extends Subsystem {
  Solenoid leftJaw;
  Solenoid rightJaw;
  PWMVictorSPX intakeWheels;

  public double currentSpeed;
  public boolean isClosed;

  public IntakeSubsystem () {
    this(RobotName.CompBot);
  }

  public IntakeSubsystem (RobotName name) {
    switch (name) {
      case CompBot: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
      }break;
      case PracticeBot: {
        leftJaw = new Solenoid(RobotMap.PracticeBot.INTAKE_LEFT_JAW_PORT);
        rightJaw = new Solenoid(RobotMap.PracticeBot.INTAKE_RIGHT_JAW_PORT);
        intakeWheels = new PWMVictorSPX(RobotMap.PracticeBot.INTAKE_WHEELS_PWM);
      }break;
      case OldCompBot: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
      }break;
      case Steve: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
      }break;
      case TestBoard: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
      }break;
    }
  }

  public void setSpeed(double newSpeed) {
    if (intakeWheels != null) {
      intakeWheels.set(newSpeed);
      currentSpeed = newSpeed;
    }
  }
  public void setClosed(boolean nowIsClosed) {
    if (leftJaw != null && rightJaw !=null) {
      leftJaw.set(!nowIsClosed);
      rightJaw.set(!nowIsClosed);
      isClosed = nowIsClosed;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new IntakeManipulator());
  }
}
