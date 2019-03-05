/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class HoldElevatorHeight extends Command {
  double myTarget;
  MoveElevator myBase;

  double lowHeight;
  double midHeight;
  double highHeight;
  double threshold;
  double feed;

  public HoldElevatorHeight(MoveElevator base, RobotMap.RobotName robot) {
    requires(Robot.elevator);
    myBase = base;
    switch (robot) {
      case CompBot: {
        lowHeight = RobotMap.CompBot.HIGH_HEIGHT;
        midHeight = RobotMap.CompBot.MID_HEIGHT;
        highHeight = RobotMap.CompBot.LOW_HEIGHT;
        threshold = RobotMap.CompBot.ELEVATOR_THRESHOLD;
        feed = RobotMap.CompBot.ELEVATOR_FEED_FORWARD;
      }
      break;
      case PracticeBot: {
        lowHeight = RobotMap.PracticeBot.HIGH_HEIGHT;
        midHeight = RobotMap.PracticeBot.MID_HEIGHT;
        highHeight = RobotMap.PracticeBot.LOW_HEIGHT;
        threshold = RobotMap.PracticeBot.ELEVATOR_THRESHOLD;
        feed = RobotMap.PracticeBot.ELEVATOR_FEED_FORWARD;
      }
      break;
      default: {
        lowHeight = RobotMap.CompBot.HIGH_HEIGHT;
        midHeight = RobotMap.CompBot.MID_HEIGHT;
        highHeight = RobotMap.CompBot.LOW_HEIGHT;
        threshold = RobotMap.CompBot.ELEVATOR_THRESHOLD;
        feed = RobotMap.CompBot.ELEVATOR_FEED_FORWARD;
        System.out.println(robot + " doesn't have a case in \'HoldElevatorHeight\'!");
      }
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    myTarget = myBase.myTarget;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.elevator.getRightPot() < highHeight) {
      Robot.elevator.disableElevator();
      return;
    } 
    else if(Robot.elevator.getRightPot() > lowHeight) {
      Robot.elevator.disableElevator();
      return;
    }
    if (Robot.elevator.getRightPot() > (myTarget + threshold)) {
      System.out.println("GOING A BIT HIGHER");
      Robot.elevator.rawSetPower(0.50);
    }
    else if (Robot.elevator.getRightPot() < (myTarget - threshold)) {
      System.out.println("GOING A BIT LOWER");
      Robot.elevator.rawSetPower(-0.50);
    } else {
      System.out.println("HOLDING HEIGHT");
      Robot.elevator.rawSetPower(feed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
