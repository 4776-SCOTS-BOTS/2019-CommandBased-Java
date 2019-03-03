/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.OI.XBox;

public class ShoulderManipulator extends Command {
  public ShoulderManipulator() {
    requires(Robot.shoulder);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("POT: " + Robot.shoulder.getPotValue());
    SmartDashboard.putNumber("SHOULDER POT VALUE", Robot.shoulder.getPotValue());
    
    Robot.shoulder.powerShoulder(Robot.oi.getManipulatorAxis(XBox.RIGHT_Y_AXIS));
    Robot.shoulder.powerIntake(Robot.oi.getManipulatorAxis(XBox.RIGHT_TRIGGER_AXIS) - Robot.oi.getManipulatorAxis(XBox.LEFT_TRIGGER_AXIS));
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
    end();
  }
}
