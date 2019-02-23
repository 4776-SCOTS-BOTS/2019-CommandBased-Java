/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI.XBox;

public class ElevatorManipulator extends Command {
  public ElevatorManipulator() {
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //control up/down power of elevator
    Robot.elevator.setPower(-Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS));
    //System.out.println(-Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS));
    //print says power-left-right
    System.out.println(-Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS) + " " + Robot.elevator.getLeftPot() + " " + Robot.elevator.getRightPot());
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
