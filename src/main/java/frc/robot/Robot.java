/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//Version: 3/1/19
package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
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
  private RobotName currentRobot = RobotName.PracticeBot;
  public static JeVoisSubsystem jeVois;
  public static DriveTrainSubsystem driveTrain;
  public static ElevatorSusbsystem elevator;
  public static IntakeSubsystem intake;
  public static ShoulderSubsystem shoulder;
  public static ClimberSubsystem climber;
  //Potentiometer p = new AnalogPotentiometer(0);
  public static OI oi;
  public static boolean readData;
  private boolean debugJeVois;
  public static Timer t;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //CameraServer.getInstance().addAxisCamera("ultimate camera boi", "10.47.76.11");
    t = new Timer();
    t.reset();
    t.start();
    System.out.println("BEGINNING_ROBOT_INIT - Instantiating subystems for \'" + currentRobot + "\'!");
    readData = false; //Reading data every loop is VERY performance heavy, so make sure readData is off when not needed!
    debugJeVois = false;

    //SUBSYSTEMS: (Please note: when switching robots, change the currentRobot variable, not the subsystem constructors)
    //PLEASE NOTE!!!!! Currently, cheesydrive only using left joystick on driver (turning isnt on right joystick)
    driveTrain = new DriveTrainSubsystem(currentRobot);
    //driveTrain = new DriveTrainSubsystem();//blank subsystem

    jeVois = new JeVoisSubsystem(currentRobot, false); //Proper subsystem (doesn't require different constructors for different robots)
    //jeVois = new JeVoisSubsystem(); //Blank subsystem for jevois
    //jeVois = new JeVoisSubsystem(false); //Actual subsystem for jevois - boolean= use two cameras?

    elevator = new ElevatorSusbsystem(currentRobot);
    //elevator = new ElevatorSusbsystem();//blank subsystem

    intake = new IntakeSubsystem(currentRobot);
    //intake = new IntakeSubsystem();//blank subsystem

    shoulder = new ShoulderSubsystem(currentRobot);
    //shoulder = new ShoulderSubsystem();//blank subsystem

    climber = new ClimberSubsystem(currentRobot);
    //climber = new ClimberSubsystem();//blank subsystem
    oi = new OI(false);//false = doubleplayer, true = singleplayer

    //Create chooser tool for different autonomouses
    chooser.setDefaultOption("Default Auto", new TestCommand());
    chooser.addOption("Basic Autonomous", new TestCommand());
    //Display the chooser tool on the SmartDashboard
    SmartDashboard.putData("Auto Mode:", chooser);
    
    Command mouth = new ToggleMouthOpen();
    SmartDashboard.putData("TOGGLE MOUTH", mouth);
    Command all = new ToggleClimbers();
    SmartDashboard.putData("CLIMB ALL", all);
    Command front = new ToggleFront();
    SmartDashboard.putData("CLIMB FRONT", front);
    Command rear = new ToggleRear();
    SmartDashboard.putData("CLIMB REAR", rear);

    Command forward = new ToggleShoulder();
    SmartDashboard.putData("FORWARD SHOULDER", forward);
    Command reverse = new ReverseShoulder();
    SmartDashboard.putData("REVERSE SHOULDER", reverse);
    
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
      //jeVois.safeRead();
      //jeVois.newPrint();
      //System.out.println("X:" + jeVois.getXAvg(false));
    }
    if (readData && !debugJeVois) {
      //jeVois.safeRead();
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
    //System.out.println("TeleopRunning");
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
