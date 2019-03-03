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
  Solenoid jaw;
  PWMVictorSPX intakeWheels;
  PWMVictorSPX hatchVacuum;
  Servo topServoRelease;
  Servo bottomServoRelease;
  double currentAngle;

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
        jaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
        topServoRelease = null;
        bottomServoRelease = null;
      }break;
      case PracticeBot: {
        jaw = new Solenoid(RobotMap.PracticeBot.INTAKE_JAW_PORT);
        intakeWheels = new PWMVictorSPX(RobotMap.PracticeBot.INTAKE_WHEELS_PWM);
        hatchVacuum = new PWMVictorSPX(RobotMap.PracticeBot.HATCH_VACUUM);
        System.out.println("HEY! Vacuum is at pwm " + RobotMap.PracticeBot.HATCH_VACUUM + "!");
        compressor = new Compressor(0);
        //Currently disabled because they dont want compressor on right now
        compressor.setClosedLoopControl(true);
        //compressor.stop();
        //System.out.println("PLEASE NOTE: The compressor is disabled and will not run!");
        
        //System.out.println("CREATED AT: " + RobotMap.PracticeBot.HATCH_VACUUM);
        topServoRelease = new Servo(RobotMap.PracticeBot.TOP_SERVO_RELEASE_PWM);
        bottomServoRelease = new Servo(RobotMap.PracticeBot.BOTTOM_SERVO_RELEASE_PWM);
      }break;
      case OldCompBot: {
        jaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
        topServoRelease = null;
        bottomServoRelease = null;
      }break;
      case Steve: {
        jaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
        topServoRelease = null;
        bottomServoRelease = null;
      }break;
      case TestBoard: {
        jaw = null;
        intakeWheels = null;
        hatchVacuum = null;
        compressor = null;
        topServoRelease = null;
        bottomServoRelease = null;
      }break;
    }
    setServoAngle(90);
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
    if (jaw != null) {
      jaw.set(!nowIsClosed);
      isClosed = nowIsClosed;
    }
  }
  public void toggleMouth() {
    if (jaw != null) {
      isClosed = !isClosed;
      jaw.set(isClosed);
    }
  }

  public void setServoAngle(double angle) {
    currentAngle = angle;
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
  }
  public void rotateServoAngle(double rotation) {
    currentAngle += rotation;
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
  }
  public void toggleServos() {
    //90=open
    //0=close
    currentAngle = Math.min((currentAngle + 90) % 180, 170);
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
    System.out.println("Servos toggled to " + currentAngle + " degrees.");
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeManipulator());
  }
}
