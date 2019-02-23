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
  Compressor compressor;
  Solenoid leftJaw;
  Solenoid rightJaw;
  PWMVictorSPX intakeWheels;
  PWMVictorSPX hatchVacuum;

  public boolean isClosed;

  //Blank Constructor: Do not use naything.
  public IntakeSubsystem () {
    //this(RobotName.CompBot);
    this(RobotName.TestBoard);
    System.out.println("Blank Subsystem for IntakeSubsystem was instantiated (as TestBoard).");
  }

  public IntakeSubsystem (RobotName robotName) {
    switch (robotName) {
      case CompBot: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
      }break;
      case PracticeBot: {
        leftJaw = new Solenoid(RobotMap.PracticeBot.INTAKE_LEFT_JAW_PORT);
        rightJaw = new Solenoid(RobotMap.PracticeBot.INTAKE_RIGHT_JAW_PORT);
        intakeWheels = new PWMVictorSPX(RobotMap.PracticeBot.INTAKE_WHEELS_PWM);
        hatchVacuum = new PWMVictorSPX(RobotMap.PracticeBot.HATCH_VACUUM);
        compressor = new Compressor(0);
        //Currently disabled because they dont want compressor on right now
        //compressor.setClosedLoopControl(true);
        compressor.stop();
        System.out.println("PLEASE NOTE: The compressor is disable and will not run!");
        
        //System.out.println("CREATED AT: " + RobotMap.PracticeBot.HATCH_VACUUM);
      }break;
      case OldCompBot: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
      }break;
      case Steve: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
      }break;
      case TestBoard: {
        leftJaw = null;
        rightJaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
      }break;
    }
    System.out.println(robotName + "\'s IntakeSubsystem correctly instantiated.");
  }
  public void powerVacuum(double power) {
    hatchVacuum.set(power);
  }
  public void powerIntake(double power) {
    if (intakeWheels != null) {
      intakeWheels.set(power);
    }
  }
  public void disableIntake() {
    if (intakeWheels != null) {
      intakeWheels.stopMotor();
    }
  }

  public void setClosed(boolean nowIsClosed) {
    if (leftJaw != null && rightJaw !=null) {
      leftJaw.set(!nowIsClosed);
      rightJaw.set(!nowIsClosed);
      isClosed = nowIsClosed;
    }
  }
  public void toggleMouth() {
    if (leftJaw != null && rightJaw !=null) {
      isClosed = !isClosed;
      leftJaw.set(isClosed);
      rightJaw.set(isClosed);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeManipulator());
  }
}
