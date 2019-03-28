/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.manipulators;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.OI.XBox;

public class ElevatorManipulator extends Command {
  double limitChange = 0.10;
  double oldOutput;
  boolean ramp = true;
  
  Timer t;
  public ElevatorManipulator() {
    requires(Robot.elevator);
    t = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    oldOutput = 0;
    t.reset();
    t.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //timer power pot
    double change = -Robot.oi.getManipulatorAxis(XBox.LEFT_Y_AXIS) - oldOutput;
    change = Math.max(-limitChange, Math.min(change, limitChange));//clamp change
    oldOutput += change;
    oldOutput = Math.min(0.99, Math.max(oldOutput, -0.99));
    Robot.elevator.setPower(oldOutput);
    //System.out.println(t.get() + " " + oldOutput + " " + Robot.elevator.getRightPot());
    
    
    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
