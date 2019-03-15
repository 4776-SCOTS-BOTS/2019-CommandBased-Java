/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.operations;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap.RobotType;
/**
 * <b>Toggle</b> the mouth <i>open</i> or <i>closed</i>.
 */
public class ToggleMouthOpen extends Command {
  boolean movingShoulderAlso;
  double threshold;
  double targetAngle;
  double shoulderMaxSpeed;
  public ToggleMouthOpen(boolean moveShoulderToPickup, RobotType type) {
    movingShoulderAlso = moveShoulderToPickup;
    if (movingShoulderAlso) {
      requires(Robot.shoulder);
      //determine what the target is and the thresholds
      threshold = type.SHOULDER_THRESHOLD;
      targetAngle = type.CARGO_PICKUP_SHOULDER;
      shoulderMaxSpeed = type.SHOULDER_MAX_SPEED;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.intake.toggleMouth();//toggle the mouth open / closed
    System.out.println("TOGGLED MOUTH");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("Target(toPickUpCargo): " + targetAngle + "PotPickupCargo: " + Robot.shoulder.getPotValue());
    if (Robot.shoulder.getPotValue() > targetAngle) {
      //decrease shoulder
      Robot.shoulder.powerShoulder(shoulderMaxSpeed);
    } else {
      //increase shoulder
      Robot.shoulder.powerShoulder(-shoulderMaxSpeed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (movingShoulderAlso && Robot.intake.isOpen) {
      return ((Robot.shoulder.getPotValue() > (targetAngle - threshold)) && (Robot.shoulder.getPotValue() < (targetAngle + threshold)));
    } else {
      return true;
    }
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
