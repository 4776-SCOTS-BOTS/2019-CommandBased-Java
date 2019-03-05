/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.manipulators;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI.XBox;

public class ClimberManipulator extends Command {
  Timer t;
  double offset;
  double wait = 0.2;
  public ClimberManipulator() {
    requires(Robot.climber);
    t = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    t.reset();
    t.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.climber.isRearExtended) {
      //put code here so driving only works on thingys
    }
    Robot.climber.powerClimbWheels(-Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.stopClimbWheels();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}