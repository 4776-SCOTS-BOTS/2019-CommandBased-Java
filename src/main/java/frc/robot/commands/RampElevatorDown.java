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

public class RampElevatorDown extends Command {
  
  Timer timer;
  double currentSpeed;
  //double a = 0.0127;
  double a = 1.15;
  double b = -17.4;
  double c = 4.0;

  boolean goUp;

  public RampElevatorDown(MoveElevator base) {
    requires(Robot.elevator);
    timer = new Timer();
    goUp = base.goingUp;
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
    System.out.println("SLOWING DOWN!!!!!!!!!!");
    //System.out.println("SSSS - " + timer.get());
    double scale = (goUp) ? 1 : -1;
    currentSpeed = Math.exp(b * timer.get() / c) * a;
    currentSpeed = Math.min(0.99, currentSpeed);
    Robot.elevator.rawSetPower(scale * currentSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(currentSpeed) < 0.05;
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
