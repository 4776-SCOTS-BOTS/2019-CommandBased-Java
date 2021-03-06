/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.operations;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/**
 * <b>This</b> is the <i>TEST COMMAND</i> for testing!
 */
public class ToggleRear extends Command {
  public ToggleRear() {
    requires(Robot.climber);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.toggleRear();
    System.out.println("TOGGLED REAR CLIMBERS");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //SmartDashboard.putNumber("Left Joystick Y Value", -Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    //Robot.driveTrain.stop();
    //Robot.shoulder.power(Robot.oi.getDriverAxis(XBox.LEFT_TRIGGER_AXIS) - Robot.oi.getDriverAxis(XBox.RIGHT_TRIGGER_AXIS));
    
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
    
  }
}
