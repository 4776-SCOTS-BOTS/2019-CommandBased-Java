/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.OI.XBox;

public class ElevatorManipulator extends Command {
  double limitChange = 0.10;
  double oldOutput;
  boolean ramp = true;
  public ElevatorManipulator() {
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    oldOutput = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double change = -Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS) - oldOutput;
    change = Math.max(-limitChange, Math.min(change, limitChange));//clamp change
    oldOutput += change;
    oldOutput = Math.min(0.70, Math.max(oldOutput, -0.70));
    Robot.elevator.rawSetPower(oldOutput);
    
    
    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.disableElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
