/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
       
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMap.RobotName;
import frc.robot.commands.manipulators.*;

/**
 * Subsystem handling control of the <b>intake (wheels)</b>, <b>hatch vacuum/servos</b>, and the <b>compressor</b>.
 */
public class IntakeSubsystem extends Subsystem {
  Compressor compressor;
  PowerDistributionPanel pdp;
  Solenoid jaw;
  PWMVictorSPX intakeWheels;
  PWMVictorSPX hatchVacuum;
  Servo topServoRelease;
  Servo bottomServoRelease;
  double currentAngle;

  double minLeftVacuumCurrent;
  double maxLeftVacuumCurrent;
  double minRightVacuumCurrent;
  double maxRightVacuumCurrent;

  public boolean isOpen;

  //Blank Constructor: Do not use naything.
  public IntakeSubsystem () {
    //this(RobotName.CompBot);
    this(RobotName.TestBoard, false, false);
    System.out.println("Blank Subsystem for IntakeSubsystem was instantiated (as TestBoard).");
  }

  public IntakeSubsystem (RobotName robotName, boolean enableCompressor, boolean closeServos) {
    switch (robotName) {
      case CompBot: {
        jaw = new Solenoid(RobotMap.CompBot.INTAKE_JAW_PORT);
        intakeWheels = new PWMVictorSPX(RobotMap.CompBot.INTAKE_WHEELS_PWM);
        hatchVacuum = new PWMVictorSPX(RobotMap.CompBot.HATCH_VACUUM);
        topServoRelease = new Servo(RobotMap.CompBot.TOP_SERVO_RELEASE_PWM);
        bottomServoRelease = new Servo(RobotMap.CompBot.BOTTOM_SERVO_RELEASE_PWM);
        compressor = new Compressor(0);
        pdp = new PowerDistributionPanel();
        enableCompressor(enableCompressor);
        if (closeServos) {
          closeServos();
        }
        minLeftVacuumCurrent = RobotMap.CompBot.MIN_LEFT_VACUUM_CURRENT;
        maxLeftVacuumCurrent = RobotMap.CompBot.MAX_LEFT_VACUUM_CURRENT;
        minRightVacuumCurrent = RobotMap.CompBot.MIN_RIGHT_VACUUM_CURRENT;
        maxRightVacuumCurrent = RobotMap.CompBot.MAX_RIGHT_VACUUM_CURRENT;
      }break;
      case PracticeBot: {
        jaw = new Solenoid(RobotMap.PracticeBot.INTAKE_JAW_PORT);
        intakeWheels = new PWMVictorSPX(RobotMap.PracticeBot.INTAKE_WHEELS_PWM);
        hatchVacuum = new PWMVictorSPX(RobotMap.PracticeBot.HATCH_VACUUM);
        compressor = new Compressor(0);
        pdp = new PowerDistributionPanel();
        topServoRelease = new Servo(RobotMap.PracticeBot.TOP_SERVO_RELEASE_PWM);
        bottomServoRelease = new Servo(RobotMap.PracticeBot.BOTTOM_SERVO_RELEASE_PWM);
        enableCompressor(enableCompressor);
        if (closeServos) {
          closeServos();
        }
        minLeftVacuumCurrent = RobotMap.PracticeBot.MIN_LEFT_VACUUM_CURRENT;
        maxLeftVacuumCurrent = RobotMap.PracticeBot.MAX_LEFT_VACUUM_CURRENT;
        minRightVacuumCurrent = RobotMap.PracticeBot.MIN_RIGHT_VACUUM_CURRENT;
        maxRightVacuumCurrent = RobotMap.PracticeBot.MAX_RIGHT_VACUUM_CURRENT;
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
    System.out.println(robotName + "\'s IntakeSubsystem correctly instantiated.");
  }
  public void enableCompressor(boolean enable) {
    if (compressor != null) {
      compressor.setClosedLoopControl(enable);
    }
  }
  public void powerVacuum(double power, boolean autoDisable, boolean useRumble) {
    if (hatchVacuum != null) {
      hatchVacuum.set(power);
    }
    if (autoDisable && (power > -0.1)) {
      openServos();
    }
    if (autoDisable && (power < -0.3)) {
      closeServos();
    }
    if (useRumble && (power < -0.3) && ((false && (getLeftVacuumCurrent() > minLeftVacuumCurrent) && getLeftVacuumCurrent() < maxLeftVacuumCurrent) || (getRightVacuumCurrent() > minRightVacuumCurrent && getRightVacuumCurrent() < maxRightVacuumCurrent))) {
      Robot.oi.rumble(1);
    } else {
      Robot.oi.rumble(0);
    }
  }
  public void powerIntake(double power, boolean autoDisable) {
    if (intakeWheels != null) {
      if (!autoDisable || isOpen) {
        intakeWheels.set(power);
      } else {
        intakeWheels.set(0);
      }
    }
  }
  public void disableIntake() {
    if (intakeWheels != null) {
      intakeWheels.stopMotor();
    }
  }
  public double getLeftVacuumCurrent() {
    if (pdp != null) {
      return pdp.getCurrent(7);
    } else {
      return 0;
    }
  }
  public double getRightVacuumCurrent() {
    if (pdp != null) {
      return pdp.getCurrent(9);
    } else {
      return 0;
    }
  }
  public void setJawClosed(boolean nowIsOpen) {
    isOpen = nowIsOpen;
    if (jaw != null) {
      jaw.set(isOpen);
    }
  }
  public void toggleMouth() {
    if (jaw != null) {
      isOpen = !isOpen;
      jaw.set(isOpen);
    }
  }
  public void closeServos() {
    currentAngle = 0;
    if (topServoRelease != null && bottomServoRelease != null) {
      topServoRelease.setAngle(currentAngle);
      bottomServoRelease.setAngle(currentAngle);
    }
  }
  public void openServos() {
    currentAngle = 90;
    if (topServoRelease != null && bottomServoRelease != null) {
      topServoRelease.setAngle(currentAngle);
      bottomServoRelease.setAngle(currentAngle);
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
