/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.operations;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.RobotMap.*;
import frc.robot.commands.*;


public class ElevatorToHeight extends Command {
  Timer timer;
  double currentSpeed;
  double maxSpeed;
  //double a = 0.0127;
  double a = 1.15;
  double b = 17.4;
  double c = 2.5;

  double downA = 0.00475;
  double downB = 8.32;

  double myTarget;
  boolean goUp;
  MoveElevator myBase;

  double highHeight;
  double lowHeight;
  double rampUp;
  double rampDown;

  public ElevatorToHeight(RobotMap.ElevatorHeight newHeight, MoveElevator base, RobotType type) {
    requires(Robot.elevator);
    myBase = base;
    timer = new Timer();
    
    maxSpeed = type.ELEVATOR_MAX_SPEED;
    highHeight = type.HIGH_HEIGHT;
    lowHeight = type.LOW_HEIGHT;
    rampUp = type.RAMP_UP_DISTANCE;
    rampDown = type.RAMP_DOWN_DISTANCE;
    switch(newHeight) {
      case Low: {
        myTarget = type.LOW_HEIGHT;
      }
      break;
      case Medium: {
        myTarget = type.MID_HEIGHT;
      }
      break;
      case High: {
        myTarget = type.HIGH_HEIGHT;
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
    double scale = (goUp) ? -1 : 1;
    if (true || goUp) {
      currentSpeed = scale * Math.exp(b * timer.get() / c) * a;
    } else {
      currentSpeed = 1 * Math.exp(downB *timer.get()) * downA;
    }
    currentSpeed = scale * 0.035 * Math.exp(2.58 *timer.get());
    currentSpeed = Math.max(-maxSpeed, Math.min(maxSpeed, currentSpeed));
    System.out.println("scale is " + scale + " because im up: " + goUp + " and speed= " + currentSpeed);
    Robot.elevator.setPower(currentSpeed);
    //System.out.println("up:  " + goUp);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return currentSpeed > 0.95;
    if (goUp) {
      System.out.println("ramped: " + (Robot.elevator.getRightPot() + rampUp) + ", target: " + myTarget + ", so " + ((Robot.elevator.getRightPot() + rampUp) <= myTarget));
      return ((Robot.elevator.getRightPot() + rampUp) <= myTarget);
    } else {
      return ((Robot.elevator.getRightPot() - rampDown) >= myTarget);
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
