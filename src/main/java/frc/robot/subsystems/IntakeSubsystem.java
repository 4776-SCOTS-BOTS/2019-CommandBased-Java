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
  Solenoid heatchBeak;
  //DEPRECATED-PWMVictorSPX hatchVacuum;
  PWMVictorSPX newIntakeBelts;//the belts holding/shooting cargo
  DigitalInput limitSwitch;
  //DEPRECATED-Servo topServoRelease;
  //DEPRECATED-Servo bottomServoRelease;
  //DEPRECATED-double currentAngle;

  public boolean isOpen = false;
  private boolean isBeakOpen = false;

  public IntakeSubsystem (boolean enableCompressor, boolean closeServos) {
    if (Robot.robotType.hasAnIntake) {
      newIntakeBelts = new PWMVictorSPX(Robot.robotType.INTAKE_BELTS_PWM);
      jaw = new Solenoid(Robot.robotType.INTAKE_JAW_PORT);
      intakeWheels = new PWMVictorSPX(Robot.robotType.INTAKE_WHEELS_PWM);
      //DEPRECATED-hatchVacuum = new PWMVictorSPX(type.HATCH_VACUUM);
      //DEPRECATED-topServoRelease = new Servo(type.TOP_SERVO_RELEASE_PWM);
      //DEPRECATED-bottomServoRelease = new Servo(type.BOTTOM_SERVO_RELEASE_PWM);
      heatchBeak = new Solenoid(Robot.robotType.HATCH_BEAK_PORT);
      compressor = new Compressor(0);
      //pdp = new PowerDistributionPanel();
      enableCompressor(enableCompressor);
      
      if (!enableCompressor) {
        compressor.stop();
      }
      if (closeServos) {
        closeServos();
      }
      System.out.println(Robot.robotType.name + "\'s IntakeSubsystem correctly instantiated.");
    } else {
      System.out.println("Blank Subsystem for IntakeSubsystem was instantiated.");
    }
    
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
  @Deprecated
  public void powerVacuum(double power, boolean autoDisable, boolean useRumble) {
    /*
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
    */
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
  /**
   * Open/Close the hatch-beak based on the power given.
   * @param power - If power is greater than 0.1, it opens. Otherwise, it's closed.
   */
  public void powerBeak(double power) {
    isBeakOpen = (power > 0.1);
    heatchBeak.set(isBeakOpen);
  }
  public void toggleBeak() {
    isBeakOpen = !isBeakOpen;
    heatchBeak.set(isBeakOpen);
  }
  public void openBeak(boolean open) {
    isBeakOpen = open;
    heatchBeak.set(isBeakOpen);
  }
  @Deprecated
  public void closeServos() {
    /*DEPRECATED
    currentAngle = 0;
    if (topServoRelease != null && bottomServoRelease != null) {
      topServoRelease.setAngle(currentAngle);
      bottomServoRelease.setAngle(currentAngle);
    }
    */
  }
  @Deprecated
  public void openServos() {
    /*DEPRECATED
    currentAngle = 90;
    if (topServoRelease != null && bottomServoRelease != null) {
      topServoRelease.setAngle(currentAngle);
      bottomServoRelease.setAngle(currentAngle);
    }
    */
  }
  @Deprecated
  public void setServoAngle(double angle) {
    /*DEPRECATED
    currentAngle = angle;
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
    */
  }
  @Deprecated
  public void rotateServoAngle(double rotation) {
    /*DEPRECATED
    currentAngle += rotation;
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
    */
  }
  @Deprecated
  public void toggleServos() {
    /*DEPRECATED
    //90=open
    //0=close
    currentAngle = Math.min((currentAngle + 90) % 180, 170);
    topServoRelease.setAngle(currentAngle);
    bottomServoRelease.setAngle(currentAngle);
    System.out.println("Servos toggled to " + currentAngle + " degrees.");
    System.out.println("TOP SERVOS are acutally " + topServoRelease.get() + ", bottom: " + bottomServoRelease.get());
    */
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeManipulator());
  }
}
