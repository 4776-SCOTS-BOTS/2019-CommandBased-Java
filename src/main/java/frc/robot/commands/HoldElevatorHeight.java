/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.RobotMap.*;

public class HoldElevatorHeight extends Command {
  double myTarget;
  MoveElevator myBase;

  double lowHeight;
  double midHeight;
  double highHeight;
  double threshold;
  double feed;

  public HoldElevatorHeight(MoveElevator base, RobotType type) {
    requires(Robot.elevator);
    myBase = base;
    
    lowHeight = type.HIGH_HEIGHT;
    midHeight = type.MID_HEIGHT;
    highHeight = type.LOW_HEIGHT;
    threshold = type.ELEVATOR_THRESHOLD;
    feed = type.ELEVATOR_FEED_FORWARD;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    myTarget = myBase.myTarget;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("SPEED: " + Robot.elevator.getRightPot());
    if (Robot.elevator.getRightPot() < highHeight) {
      Robot.elevator.disableElevator();
      return;
    } 
    else if(Robot.elevator.getRightPot() > lowHeight) {
      Robot.elevator.disableElevator();
      return;
    }
    if (Robot.elevator.getRightPot() > (myTarget + threshold)) {
      //System.out.println("GOING A BIT lower");
      Robot.elevator.setPower(-0.50);
    }
    else if (Robot.elevator.getRightPot() < (myTarget - threshold)) {
      //System.out.println("GOING A BIT higher");
      Robot.elevator.setPower(0.50);
    } else {
      //System.out.println("HOLDING HEIGHT");
      Robot.elevator.setPower(feed);
    }
    //System.out.println("running hold method - my target is " + myTarget);
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
