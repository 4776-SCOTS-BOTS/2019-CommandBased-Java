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

/**
 * A command that centers the robot with the tape using the JeVois.
 */
public class RampToTape extends Command {
  boolean secondCam;
  double target;
  double P, I, D, integral, previousError;
  double threshold = 7;
  double min = 0.5;
  Timer timer;

  double currentPower;
  double a, b, c, d;
  //Requirements: DriveTrainSubsystem and JeVoisSusbsystem.
  public RampToTape() {
    this(false); //by default it doesnt use the second camera
  }
  public RampToTape(boolean useSecondCam) {
    timer = new Timer();
    //To run this command we need the jevois and the drivetrain
    requires(Robot.jeVois);
    requires(Robot.driveTrain);
    secondCam = useSecondCam;
    if (useSecondCam && !Robot.jeVois.getDoubleCams()) {
      System.out.println("ERROR in RampToTape: Using the second camera is selected, but there is no second camera! This command will be ended.");
      end();
    }
    target = RobotMap.JeVois.CAMERA_WIDTH / 2;
    P = RobotMap.JeVois.PID_P;
    I = RobotMap.JeVois.PID_I;
    D = RobotMap.JeVois.PID_D;
    integral = 0;
    previousError = 0;
    a = 0.6;
    b = 0.042;//.028
    c = 0.04;//.09
    d = 0.25;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //System.out.println("JC initialized.");
    Robot.readData = true;
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //double error = target - Robot.jeVois.getXAvg(secondCam); //error = target - actual
    //integral += error * 0.02;
    //double derivative = (error - previousError) / 0.02;
    //double turnPower = P*error + I*integral + D*derivative;
    double turnPower = 0;
    currentPower = a * Math.exp(-b * Robot.jeVois.getSideW(true, false)) + c;

    Robot.driveTrain.cheesyDrive(currentPower, d * Math.min(min, Math.max(-min, -turnPower)), false); //Clamp turnPower to prevent motor problems
    //Robot.driveTrain.tankDrive(Math.min(min, Math.max(-min, turnPower)), Math.min(min, Math.max(-min, -turnPower)));
    System.out.println("RAMPING: " + currentPower);
    //System.out.println(Robot.jeVois.getWAvg(false) + " " + currentPower + " " + turnPower);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //System.out.println("JC ended.");
    Robot.readData = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    //System.out.println("JC interupted!");
    end();
  }
}
