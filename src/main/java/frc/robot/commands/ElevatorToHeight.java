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

  double downA = 0.00475;
  double downB = 8.32;

  double myTarget;
  boolean goUp;
  MoveElevator myBase;

  public ElevatorToHeight(RobotMap.ElevatorHeight newHeight, MoveElevator base) {
    requires(Robot.elevator);
    myBase = base;
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
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("mytarget:" + myTarget + ", currently im: " + Robot.elevator.getRightPot());
    goUp = (Robot.elevator.getRightPot() > myTarget);
    myBase.goingUp = goUp;
    myBase.myTarget = myTarget;
    timer.reset();
    timer.start();
    System.out.println("STARTING init: am i goingUp? " + goUp);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //boolean goUp = (Robot.elevator.getRightPot() > myTarget);
    double scale = (goUp) ? 1 : -1;
    if (true || goUp) {
      currentSpeed = scale * Math.exp(b * timer.get() / c) * a;
    } else {
      currentSpeed = -1 * Math.exp(downB *timer.get()) * downA;
    }
    currentSpeed = Math.max(-RobotMap.PracticeBot.MAX_SPEED, Math.min(RobotMap.PracticeBot.MAX_SPEED, currentSpeed));
    Robot.elevator.rawSetPower(currentSpeed);
    System.out.println("Going up? " + goUp);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return currentSpeed > 0.95;
    if (goUp) {
      return (Robot.elevator.getRightPot() < RobotMap.PracticeBot.HIGH_HEIGHT) || ((Robot.elevator.getRightPot() - RobotMap.PracticeBot.RAMP_UP_DISTANCE) < myTarget);
    } else {
      return (Robot.elevator.getRightPot() > RobotMap.PracticeBot.LOW_HEIGHT) || ((Robot.elevator.getRightPot() + RobotMap.PracticeBot.RAMP_DOWN_DISTANCE) > myTarget);
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
