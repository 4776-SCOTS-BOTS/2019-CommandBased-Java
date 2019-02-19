/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.OI.XBox;

/**
 * Command to operate/manipulate the intake.
 */
public class IntakeManipulator extends Command {
  public IntakeManipulator() {
    requires(Robot.intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.intake.setSpeed(Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS));
    //Robot.intake.vacuum(Robot.oi.getDriverAxis(XBox.RIGHT_TRIGGER_AXIS) - Robot.oi.getDriverAxis(XBox.LEFT_TRIGGER_AXIS));
    //Robot.intake.setClosed();
    
    /*if (Robot.oi.getManipulatorPOV() == XBox.TOP_POV) {
      Robot.intake.setClosed(false);
    } else if (Robot.oi.getManipulatorPOV() == XBox.BOTTOM_POV) {
      Robot.intake.setClosed(true);
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
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
