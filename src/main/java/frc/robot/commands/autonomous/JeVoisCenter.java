/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

/**
 * A command that centers the robot with the tape using the JeVois.
 */
public class JeVoisCenter extends Command {
  boolean secondCam;
  double target;
  double P, I, D, integral, previousError;
  double multiplier;
  double threshold = 7;
  double min = 0.5;
  Timer timer;
  double centerTime = 1;
  double currentTime = 0;
  boolean accurate;

  double currentPower;
  double a, b, c;
  //Requirements: DriveTrainSubsystem and JeVoisSusbsystem.
  public JeVoisCenter() {
    this(false);
  }
  public JeVoisCenter(boolean useSecondCam) {
    timer = new Timer();
    //To run this command we need the jevois and the drivetrain
    requires(Robot.jeVois);
    requires(Robot.driveTrain);
    secondCam = useSecondCam;
    if (useSecondCam && !Robot.jeVois.getDoubleCams()) {
      System.out.println("ERROR in JeVoisCenter: Using the second camera is selected, but there is no second camera! This command will be ended.");
      end();
    }
    //target = RobotMap.JeVois.CAMERA_WIDTH / 2; //target = 160
    target = 160;

    //P = RobotMap.JeVois.PID_P;
    P = 0.0045;
    I = 0.004; //RobotMap.JeVois.PID_I;
    D = 0.0; //RobotMap.JeVois.PID_D;
    multiplier = 1.2;
    integral = 0;
    previousError = 0;
    a = 0.6;
    b = 0.042;//.028
    c = 0.04;//.09
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("JC initialized.");
    Robot.readData = true;
    timer.reset();
    timer.start();
    currentTime = 0;
    integral = 0;
    accurate = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double error = target - Robot.jeVois.getXAvg(secondCam); //error = target - actual (target=160)
    integral += error * 0.02;
    double derivative = (error - previousError) / 0.02;
    double turnPower = multiplier*P*error + I*integral + D*derivative;
    turnPower = Math.min(0.1, Math.max(turnPower, -0.1));
    //currentPower = a * Math.exp(-b * Robot.jeVois.getSideW(true, false)) + c;

    Robot.driveTrain.cheesyDrive(0, Math.min(min, Math.max(-min, -turnPower)), true); //Clamp turnPower to prevent motor problems
    //Robot.driveTrain.tankDrive(Math.min(min, Math.max(-min, turnPower)), Math.min(min, Math.max(-min, -turnPower)));
    //System.out.println(Robot.jeVois.getXAvg(secondCam) " -> " + error);
    //System.out.println(Robot.jeVois.getWAvg(false) + " " + currentPower + " " + turnPower);
    if (Math.abs(target - Robot.jeVois.getXAvg(secondCam)) < threshold && !accurate) {
      accurate = true;
      currentTime = timer.get();
      System.out.println("STARTED ACCURACY!!!!!!!!!!");
    }
    if (Math.abs(target - Robot.jeVois.getXAvg(secondCam)) > threshold && accurate) {
      accurate = false;
      System.out.println("FAILED!!!!!! ACCURACY!!!!!!!!!!!");
    }
    //System.out.println("TIME: " + currentTime + turnPower);
    System.out.println("X: " + Robot.jeVois.getXAvg(secondCam) + "power:" + turnPower + " p term: " + multiplier*P*error + " i term: " + I*integral);
  } 

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return Math.abs(target - Robot.jeVois.getXAvg(secondCam)) < threshold;
    //return false;
    return (accurate && (timer.get() - currentTime) > centerTime);//for use in turn then drive
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //System.out.println("JC ended.");
    System.out.println("---------------CENTERED!!!!!!!!!!!!----------------");
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
