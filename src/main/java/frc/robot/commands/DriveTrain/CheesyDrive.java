/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;
import frc.robot.OI.*;

/**
 * Command to drive the robot using cheesy (AKA curvature or car) drive.
 */
public class CheesyDrive extends Command {
  boolean fieldView; //When driving backwards, turning left turns the robot to the left. Normal cheesyDrive does not do this.
  boolean useRamp;
  double oldOutput;
  double limitChange = 0.10;
  int i;
  /**
   * To use cheesy drive during teleop/sandstorm/autonomous, make this command the default command for the drivetrain.
   * @param useFieldView - Enable this so when driving backwards, turning left makes the robot turn left (normally it is the opposite).
   */
  public CheesyDrive(boolean useFieldView, boolean useRampingCapabilites) {
    requires(Robot.driveTrain);
    fieldView = useFieldView;
    useRamp = useRampingCapabilites;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    i = 0;
    oldOutput = 0;
    //Robot.readData = true;
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    //i++;
    //System.out.println("C: " + i);
    //System.out.println("RIGHT: " + turn);
    if (false && Robot.oi.getDriverButton(XBox.RIGHT_BUMPER_BUTTON)) {

      double change = -Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS) - oldOutput;
      change = Math.max(-limitChange, Math.min(change, limitChange));//clamp change
      oldOutput += change;
      boolean reverse = ((oldOutput < -0.06) && fieldView);
      double turn = (reverse ? -1 : 1) * Robot.oi.getManipulatorAxis(XBox.RIGHT_X_AXIS);
      Robot.driveTrain.cheesyDrive(oldOutput, turn, false);
    } else {

      double change = -Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS) - oldOutput;
      change = Math.max(-limitChange, Math.min(change, limitChange));//clamp change
      oldOutput += change;
      boolean reverse = ((oldOutput < -0.06) && fieldView);
      double turn = (reverse ? -1 : 1) * Robot.oi.getDriverAxis(XBox.RIGHT_X_AXIS);
      Robot.driveTrain.cheesyDrive(oldOutput, turn, false);
    }
    //Robot.driveTrain.cheesyDrive(0, 0, false);
    //SmartDashboard.putBoolean("GoingReverse", reverse);
    //System.out.println(Robot.jeVois.getXAvg(false) +" "+-Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
