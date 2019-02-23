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
import frc.robot.OI.*;
/**
 * <b>This</b> is the <i>TEST COMMAND</i> for testing!
 */
public class TestCommand extends Command {
  Timer t;
  boolean goF;//forward power is positve
  public TestCommand() {
    requires(Robot.shoulder);
    t = new Timer();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    goF = true;
    t.reset();
    t.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //SmartDashboard.putNumber("Left Joystick Y Value", -Robot.oi.getDriverAxis(XBox.LEFT_Y_AXIS));
    //Robot.driveTrain.stop();
    //Robot.shoulder.power(Robot.oi.getDriverAxis(XBox.LEFT_TRIGGER_AXIS) - Robot.oi.getDriverAxis(XBox.RIGHT_TRIGGER_AXIS));
    if (goF && Robot.shoulder.getPotValue() < 0.45) {
      //you have reached the forward mode
      
      System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
      goF = false;
    }
    if (!goF && Robot.shoulder.getPotValue() > 0.54) {
      //you have reached the back mode
      
      System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
      goF = true;
    }
    double power = (goF ? 1 : -1);
    Robot.shoulder.powerShoulder(power);
    System.out.println("Time:"+t.get()+" Pot:"+Robot.shoulder.getPotValue()+"F: " + goF+", GOFPOW: "+power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    
  }
}
