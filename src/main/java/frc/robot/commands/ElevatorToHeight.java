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
import frc.robot.RobotMap;

public class ElevatorToHeight extends Command {
  
  Timer timer;
  double currentSpeed;
  //double a = 0.0127;
  double a = 1.15;
  double b = 17.4;
  double c = 2.5;

  double myTarget;
  boolean goUp;

  public ElevatorToHeight(RobotMap.ElevatorHeight newHeight, MoveElevator base) {
    requires(Robot.elevator);
    timer = new Timer();
    switch(newHeight) {
      case Low: {
        myTarget = RobotMap.PracticeBot.LOW_HEIGHT;
      }
      break;
      case Medium: {
        myTarget = RobotMap.PracticeBot.MID_HEIGHT;
      }
      break;
      case High: {
        myTarget = RobotMap.PracticeBot.HIGH_HEIGHT;
      }
      break;
    }
    goUp = (Robot.elevator.getRightPot() > myTarget);
    base.goingUp = goUp;
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
    //boolean goUp = (Robot.elevator.getRightPot() > myTarget);
    double scale = (goUp) ? 1 : -1;
    currentSpeed = Math.exp(b * timer.get() / c) * a;
    currentSpeed = Math.min(0.99, currentSpeed);
    Robot.elevator.rawSetPower(scale * currentSpeed);
    System.out.println("Going up? " + goUp);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return currentSpeed > 0.95;
    if (goUp) {
      return (Robot.elevator.getRightPot() - RobotMap.PracticeBot.RAMP_DISTANCE < myTarget);
    } else {
      return (Robot.elevator.getRightPot() - RobotMap.PracticeBot.RAMP_DISTANCE > myTarget);
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //Robot.elevator.disableElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
