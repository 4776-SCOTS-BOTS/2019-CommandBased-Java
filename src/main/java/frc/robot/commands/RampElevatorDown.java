/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class RampElevatorDown extends Command {
  
  Timer timer;
  double currentSpeed;
  double maxSpeed;

  //for exponential
  //double a = 0.0127;
  /*double a = 1.19;//old=1.15
  double b = -19.7;//old=-17.4
  double c = 1.0;//old=4.0*/
  double a = 1.15;
  double b = -17.4;
  double c = 4.0;

  //for line
  double m = 13.0;
  double k = -1.1;

  MoveElevator myBase;
  boolean goUp;
  double highHeight;
  double lowHeight;

  public RampElevatorDown(MoveElevator base, RobotMap.RobotName robot) {
    requires(Robot.elevator);
    timer = new Timer();
    //goUp = base.goingUp;
    myBase = base;
    switch(robot) {
      case CompBot: {
        maxSpeed = RobotMap.CompBot.ELEVATOR_MAX_SPEED;
        highHeight = RobotMap.CompBot.HIGH_HEIGHT;
        lowHeight = RobotMap.CompBot.LOW_HEIGHT;
      }
      break;
      case PracticeBot: {
        maxSpeed = RobotMap.PracticeBot.ELEVATOR_MAX_SPEED;
        highHeight = RobotMap.PracticeBot.HIGH_HEIGHT;
        lowHeight = RobotMap.PracticeBot.LOW_HEIGHT;
      }
      break;
      default: {
        maxSpeed = RobotMap.CompBot.ELEVATOR_MAX_SPEED;
        highHeight = RobotMap.CompBot.HIGH_HEIGHT;
        lowHeight = RobotMap.CompBot.LOW_HEIGHT;
        System.out.println(robot + " doesn't have a case in \'RampElevatorDown\'!");
      }
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
    goUp = myBase.goingUp;
    System.out.println("STARTING SLOW DOWN");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("SLOWING DOWN!!!!!!!!!! up?: " + goUp);
    //System.out.println("SSSS - " + timer.get());
    //double scale = (goUp) ? 1 : -1;
    //use different ramps for up/down
    if (goUp) {
      currentSpeed = Math.exp(b * timer.get() / c) * a;
    } else {
      currentSpeed = m *timer.get() + k;
    }
    currentSpeed = Math.max(-maxSpeed, Math.min(maxSpeed, currentSpeed));
    Robot.elevator.rawSetPower(currentSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (goUp) {
      return (currentSpeed < 0.125) || (Robot.elevator.getRightPot() < highHeight);
    } else {
      return (currentSpeed > -0.05) || (Robot.elevator.getRightPot() > highHeight);
    }
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