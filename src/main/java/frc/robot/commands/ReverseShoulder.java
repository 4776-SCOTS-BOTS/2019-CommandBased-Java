/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReverseShoulder extends Command {
  boolean goF;
  public ReverseShoulder() {
    requires(Robot.shoulder);
    goF = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("Time:"+Robot.t.get()+" Pot:"+Robot.shoulder.getPotValue());
    if (goF) {
      Robot.shoulder.powerShoulder(1);
    } else {
      Robot.shoulder.powerShoulder(-1);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (goF) {
      return (Robot.shoulder.getPotValue() < 0.45);
    } else {
      return (Robot.shoulder.getPotValue() > 0.54);
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.shoulder.stopShoulder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
