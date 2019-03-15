/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.calibrations;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.RobotMap.*;

/**
 * Run this command when the elevator is at the bottom of its stage, setting the constant to the current height.
 * <p><b>PLEASE NOTE:</b> This command should only be run through the <i>SmartDashboard</i>, not during teleop.
 */
public class ResetBottomElevatorHeight extends Command {
  RobotType myType;
  public ResetBottomElevatorHeight(RobotType type) {
    myType = type;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    myType.HIGH_HEIGHT = Robot.elevator.getRightPot();
    System.out.println("The bottom elevator height for the " + myType.name + " has been set to " + Robot.elevator.getRightPot() + ".");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
