/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
       
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.*;
import frc.robot.RobotMap.*;
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
  PWMVictorSPX newIntakeBelts;//the belts holding/shooting cargo
  DigitalInput limitSwitch;
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
    //this(RobotName.TestBoard, false, false);
    System.out.println("Blank Subsystem for IntakeSubsystem was instantiated.");
  }

  public IntakeSubsystem (RobotType type, boolean enableCompressor, boolean closeServos) {
    newIntakeBelts = new PWMVictorSPX(type.INTAKE_BELTS_PWM);
    
    jaw = new Solenoid(type.INTAKE_JAW_PORT);
    intakeWheels = new PWMVictorSPX(type.INTAKE_WHEELS_PWM);
    hatchVacuum = new PWMVictorSPX(type.HATCH_VACUUM);
    topServoRelease = new Servo(type.TOP_SERVO_RELEASE_PWM);
    bottomServoRelease = new Servo(type.BOTTOM_SERVO_RELEASE_PWM);
    compressor = new Compressor(0);
    //pdp = new PowerDistributionPanel();
    enableCompressor(enableCompressor);
    
    if (!enableCompressor) {
      compressor.stop();
    }
    if (closeServos) {
      closeServos();
    }
    minLeftVacuumCurrent = type.MIN_LEFT_VACUUM_CURRENT;
    maxLeftVacuumCurrent = type.MAX_LEFT_VACUUM_CURRENT;
    minRightVacuumCurrent = type.MIN_RIGHT_VACUUM_CURRENT;
    maxRightVacuumCurrent = type.MAX_RIGHT_VACUUM_CURRENT;
    System.out.println(type.name + "\'s IntakeSubsystem correctly instantiated.");
  }
  public void enableCompressor(boolean enable) {
    if (compressor != null) {
      compressor.setClosedLoopControl(enable);
      if (enable) {
        //compressor.start(); //disabled for safety
      } else {
        compressor.stop();
      }
    }
  }
  public void powerVacuum(double power, boolean autoDisable, boolean useRumble) {
    //autoDisable = false;
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
      if (newIntakeBelts != null) {
        newIntakeBelts.set(power);
      }
      if (!autoDisable || isOpen) {
        intakeWheels.set(power);
      } else {
        intakeWheels.set(0);
      }
    }
  }
  public void disableIntake() {
    if (newIntakeBelts != null) {
      newIntakeBelts.stopMotor();
    }
    if (intakeWheels != null) {
      intakeWheels.stopMotor();
    }
  }
  public double getLeftVacuumCurrent() {
    if (pdp != null) {
      //return pdp.getCurrent(7);
      return 0;
    } else {
      return 0;
    }
  }
  public double getRightVacuumCurrent() {
    if (pdp != null) {
      //return pdp.getCurrent(9);
      return 0;
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
    System.out.println("TOP SERVOS are acutally " + topServoRelease.get() + ", bottom: " + bottomServoRelease.get());
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeManipulator());
  }
}
