/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.operations;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SetPickupHeight extends Command {
  RobotMap.RobotName myRobot;
  boolean usingCargo;
  boolean facingBackwards;
  double threshold;
  double targetAngle;
  double maxSpeed;
  boolean needCargo;
  boolean needSide;
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param useCargo - Set to true when using cargo. Set to false when using hatches.
   */
  public SetPickupHeight(boolean useCargo, RobotMap.RobotName robot) {
    myRobot = robot;
    requires(Robot.shoulder);
    usingCargo = useCargo;
    facingBackwards = Robot.shoulder.facingBack;
    
    needCargo = false;
    needSide = true;
  }
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param useCargo - Set to true when using cargo. Set to false when using hatches.
   * @param faceBack - Set what side you want to face
   */
  public SetPickupHeight(boolean useCargo, RobotMap.RobotName robot, boolean faceBack) {
    myRobot = robot;
    requires(Robot.shoulder);
    usingCargo = useCargo;
    facingBackwards = faceBack;
    
    needCargo = false;
    needSide = false;
  }
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param faceBack - Set what side you want to face
   */
  public SetPickupHeight(RobotMap.RobotName robot, boolean faceBack) {
    myRobot = robot;
    requires(Robot.shoulder);
    usingCargo = Robot.shoulder.angledUp;
    facingBackwards = faceBack;
    
    needCargo = true;
    needSide = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (needCargo) {
      usingCargo = Robot.shoulder.angledUp;
    }
    if (needSide) {
      facingBackwards = Robot.shoulder.facingBack;
    }
    //System.out.println("CARGO: " + usingCargo + " BACKWARDS: " + facingBackwards);
    setTargets(myRobot);
    Robot.shoulder.angledUp = usingCargo;
    Robot.shoulder.facingBack = facingBackwards;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("ME: " + Robot.shoulder.getPotValue() + "Target: " + targetAngle);
    if (Robot.shoulder.getPotValue() > targetAngle) {
      //decrease shoulder
      if (closeEnough()) {
        Robot.shoulder.powerShoulder(-RobotMap.CompBot.SHOULDER_FEED_FORWARD);
      } else {

        Robot.shoulder.powerShoulder(maxSpeed);
      }
    } else {
      //increase shoulder
      if (closeEnough()) {
        Robot.shoulder.powerShoulder(RobotMap.CompBot.SHOULDER_FEED_FORWARD);
      } else {
        
      Robot.shoulder.powerShoulder(-maxSpeed);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  
  boolean closeEnough() {
    System.out.println((Math.abs(Robot.shoulder.getPotValue() - targetAngle) < threshold));
    return (Math.abs(Robot.shoulder.getPotValue() - targetAngle) < threshold);
    //return ((Robot.shoulder.getPotValue() > (targetAngle - threshold)) && (Robot.shoulder.getPotValue() < (targetAngle + threshold)));
  }
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //System.out.println("GOSH I STOPPED WHEN I: " + isFinished());
    Robot.shoulder.stopShoulder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
  private void setTargets(RobotMap.RobotName robot) {
    switch (robot) {
      case CompBot: {
        maxSpeed = RobotMap.CompBot.SHOULDER_MAX_SPEED;
        threshold = RobotMap.CompBot.SHOULDER_THRESHOLD;
        if (facingBackwards) {
          if (usingCargo) {
            targetAngle = RobotMap.CompBot.REVERSE_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.CompBot.REVERSE_STRAIGHT_SHOULDER;
          }
        } else {
          if (usingCargo) {
            targetAngle = RobotMap.CompBot.FORWARD_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.CompBot.FORWARD_STRAIGHT_SHOULDER;
          }
        }
      }
      break;
      case PracticeBot: {
        maxSpeed = RobotMap.PracticeBot.SHOULDER_MAX_SPEED;
        threshold = RobotMap.PracticeBot.SHOULDER_THRESHOLD;
        if (facingBackwards) {
          if (usingCargo) {
            targetAngle = RobotMap.PracticeBot.REVERSE_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.PracticeBot.REVERSE_STRAIGHT_SHOULDER;
          }
        } else {
          if (usingCargo) {
            targetAngle = RobotMap.PracticeBot.FORWARD_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.PracticeBot.FORWARD_STRAIGHT_SHOULDER;
          }
        }
      }
      break;
      default: {
        //default: use the comp bot
        System.out.println(robot + " has no case is \'SetPickupheight\'!");
        maxSpeed = RobotMap.CompBot.SHOULDER_MAX_SPEED;
        threshold = RobotMap.CompBot.SHOULDER_THRESHOLD;
        if (facingBackwards) {
          if (usingCargo) {
            targetAngle = RobotMap.CompBot.REVERSE_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.CompBot.REVERSE_STRAIGHT_SHOULDER;
          }
        } else {
          if (usingCargo) {
            targetAngle = RobotMap.CompBot.FORWARD_UP_SHOULDER;
          } else {
            targetAngle = RobotMap.CompBot.FORWARD_STRAIGHT_SHOULDER;
          }
        }
      }
    }
  }
}
