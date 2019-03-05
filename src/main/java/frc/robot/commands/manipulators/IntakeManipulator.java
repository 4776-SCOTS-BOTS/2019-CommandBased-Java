/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.manipulators;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.OI.XBox;

/**
 * Command to operate/manipulate the <b>intake</b>.
 */
public class IntakeManipulator extends Command {
  Timer timer;
  double backTime;
  double waitTime = 0.3;

  public IntakeManipulator() {
    requires(Robot.intake);
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("L: " + Robot.intake.getLeftVacuumCurrent() + " R: " + Robot.intake.getRightVacuumCurrent());
    //operate intake mouth wheels using left y axis of manip
    //Robot.intake.powerIntake(Robot.oi.getManipulatorAxis(XBox.RIGHT_Y_AXIS));
    //operate hatch vacuums using back triggers of manip

    //Power intake wheels
    Robot.intake.powerIntake(Robot.oi.getManipulatorAxis(XBox.RIGHT_TRIGGER_AXIS) - Robot.oi.getManipulatorAxis(XBox.LEFT_TRIGGER_AXIS), true);
    //Power vacuum motors and detach servos if needed
    Robot.intake.powerVacuum(Robot.oi.getDriverAxis(XBox.RIGHT_TRIGGER_AXIS) - Robot.oi.getDriverAxis(XBox.LEFT_TRIGGER_AXIS), true, true);
    
    /*//Operate closing / opening of intake jaw
    if (Robot.oi.getManipulatorPOV() == XBox.TOP_POV) {
      Robot.intake.setClosed(false);
    } else if (Robot.oi.getManipulatorPOV() == XBox.BOTTOM_POV) {
      Robot.intake.setClosed(true);
    }*/
      /*if (Robot.oi.getDriverButtonDown(XBox.RIGHT_BUMPER_BUTTON) && (timer.get() - backTime > waitTime)) {
        backTime = timer.get();
        System.out.println("PRESSED RIGHT BUMPER");
      }*/
    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intake.disableIntake();//for safety reasons its a good idea to stop the motors
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}