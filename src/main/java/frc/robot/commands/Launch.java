/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Launch extends Command {
  Timer timer;
  double filter;
  double loopTime;
  double launchTime;
  
  double startTime;
  double previousTime;
  double currentPower;

  public Launch() {
    requires(Robot.driveTrain);
    timer = new Timer();
    filter = 0.1;
    loopTime = 0.02;
    previousTime = -1;
    currentPower = 0.2;
    startTime = 0;
    launchTime = 1;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
    startTime = timer.get();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if ((timer.get() - previousTime) > loopTime) {
      currentPower = Math.min(0.99, currentPower + filter * (1 - currentPower));
      previousTime = timer.get();
    }
    Robot.driveTrain.arcadeDrive(currentPower, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((timer.get() - startTime) > launchTime);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
