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

public class SetPickupHeight extends Command {
  RobotType myType;

  Timer timer = new Timer();

  boolean usingCargo;
  boolean facingBackwards;
  double threshold;
  double targetAngle;
  double maxSpeed;
  boolean needCargo;
  boolean needSide;

  double a = 0.09139;
  double c = -0.09139;
  double b = 5;
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param useCargo - Set to true when using cargo. Set to false when using hatches.
   */
  public SetPickupHeight(GamePiece gamePiece) {
    myType = Robot.robotType;
    requires(Robot.shoulder);
    usingCargo = gamePiece==GamePiece.Cargo;
    facingBackwards = Robot.shoulder.facingBack;
    
    needCargo = false;
    needSide = true;
  }
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param useCargo - Set to true when using cargo. Set to false when using hatches.
   * @param faceBack - Set what side you want to face
   */
  public SetPickupHeight(GamePiece gamePiece, boolean faceBack) {
    myType = Robot.robotType;
    requires(Robot.shoulder);
    usingCargo = gamePiece==GamePiece.Cargo;
    facingBackwards = faceBack;
    
    needCargo = false;
    needSide = false;
  }
  /**
   * Set what shoulder height is used: straight (for hatches) or angled up (for cargo)
   * @param faceBack - Set what side you want to face
   */
  public SetPickupHeight(boolean faceBack) {
    myType = Robot.robotType;
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
    setTargets(myType);
    Robot.shoulder.angledUp = usingCargo;
    Robot.shoulder.facingBack = facingBackwards;
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("ME: " + Robot.shoulder.getPotValue() + "Target: " + targetAngle);
    if (Robot.shoulder.getPotValue() > targetAngle) {
      //decrease shoulder
      if (closeEnough()) {
        
        Robot.shoulder.powerShoulder(-myType.SHOULDER_FEED_FORWARD);
      } else {

        Robot.shoulder.powerShoulder(curve(timer.get(), maxSpeed));
      }
    } else {
      //increase shoulder
      if (closeEnough()) {
        Robot.shoulder.powerShoulder(myType.SHOULDER_FEED_FORWARD);
      } else {
        
      Robot.shoulder.powerShoulder(-curve(timer.get(), maxSpeed));
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  
  boolean closeEnough() {
    //System.out.println((Math.abs(Robot.shoulder.getPotValue() - targetAngle) < threshold));
    return (Math.abs(Robot.shoulder.getPotValue() - targetAngle) < threshold);
    //return ((Robot.shoulder.getPotValue() > (targetAngle - threshold)) && (Robot.shoulder.getPotValue() < (targetAngle + threshold)));
  }
  @Override
  protected boolean isFinished() {
    return closeEnough();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("GOSH I STOPPED WHEN I: " + isFinished());
    Robot.shoulder.stopShoulder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
  private void setTargets(RobotType type) {
    
    maxSpeed = type.SHOULDER_MAX_SPEED;
    threshold = type.SHOULDER_THRESHOLD;
    if (facingBackwards) {
      if (usingCargo) {
        targetAngle = type.REVERSE_UP_SHOULDER;
      } else {
        targetAngle = type.REVERSE_STRAIGHT_SHOULDER;
      }
    } else {
      if (usingCargo) {
        targetAngle = type.FORWARD_UP_SHOULDER;
      } else {
        targetAngle = type.FORWARD_STRAIGHT_SHOULDER;
      }
    }
  }
  private double curve(double x, double max) {
    return Math.min(max, a * Math.exp(b * x) + c);
  }
}
