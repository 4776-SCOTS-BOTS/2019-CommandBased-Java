/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.OI.*;
import frc.robot.RobotMap.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private RobotName currentRobot = RobotName.OldCompBot;

  public static JeVoisSubsystem jeVois;
  public static DriveTrainSubsystem driveTrain;
  public static ElevatorSusbsystem elevator;
  public static IntakeSubsystem intake;
  public static ShoulderSubsystem shoulder;

  public static OI oi;
  public static boolean readData = false;
  private boolean debugJeVois = false;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("BEGINNING_ROBOT_INIT!");
    readData = false; //Reading data every loop is VERY performance heavy, so make sure readData is off when not needed!
    debugJeVois = false;

    //SUBSYSTEMS:
    driveTrain = new DriveTrainSubsystem(currentRobot);
    System.out.println("BEGINNING_ROBOT_INIT!");
    //jeVois = new JeVoisSubsystem(); //Blank subsystem for jevois
    jeVois = new JeVoisSubsystem(false); //Actual subsystem for jevois - boolean= use two cameras?
    System.out.println("creating elevator!");
    elevator = new ElevatorSusbsystem(currentRobot); //0=dont use
    System.out.println("creating intake!");
    intake = new IntakeSubsystem(currentRobot);
    System.out.println("creating shoulder!");
    shoulder = new ShoulderSubsystem(currentRobot);
    System.out.println("creatign oit!");
    oi = new OI(true);

    //Create chooser tool for different autonomouses
    chooser.setDefaultOption("Default Auto", new TestCommand());
    chooser.addOption("Basic Autonomous", new TestCommand());
    //Display the chooser tool on the SmartDashboard
    SmartDashboard.putData("Auto Mode:", chooser);
  }

  /**
   * <i>This</i> function is called <b>every</b> robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //SmartDashboard.putData("Current Commands Running", Scheduler.getInstance());
    if (debugJeVois) { //display data on the jevois for debugging purposes
      jeVois.safeRead();
      jeVois.newPrint();
    }
    if (readData && !debugJeVois) {
      jeVois.safeRead();
    }
  }

  /**
   * This function is called <i>once</i> each time the <b>robot</b> enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    SmartDashboard.putBoolean("Enabled", false);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    autonomousCommand = chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    System.out.println("TELEOP_INIT");
    SmartDashboard.putBoolean("Enabled", true);
    //System.out.println("{\"FD\": 0, \"X1\": 0, \"Y1\": 0, \"W1\": 0, \"H1\": 0, \"X2\": 0, \"Y2\": 0, \"W2\": 0, \"H2\": 0}\n{".getBytes().length);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run(); 
    //elevator.setPower(oi.getDriverAxis(XBox.RIGHT_Y_AXIS));   
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
