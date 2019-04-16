/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//Version: 3/2/19
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.OI.*;
import frc.robot.RobotMap.*;
import frc.robot.commands.autonomous.Autonomous;
import frc.robot.commands.operations.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //DEPRECATED-private RobotName currentRobot = RobotName.CompBot;
  public static final RobotType robotType = new CompBot();
  
  public static JeVoisSubsystem jeVois;
  public static DriveTrainSubsystem driveTrain;
  public static ElevatorSusbsystem elevator;
  public static IntakeSubsystem intake;
  public static ShoulderSubsystem shoulder;
  public static ClimberSubsystem climber;
  public static OI oi;

  public static boolean readData;
  private boolean debugJeVois;
  private int moldPOV;

  Command autonomousCommand;
  Command dPadLeftCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();
  Timer t;
  Command myAuto;
  
  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    t = new Timer();
    //CameraServer.getInstance().startAutomaticCapture();
    //CameraServer.getInstance().addAxisCamera("basic-cam", "10.47.76.5");
    //Add camera streams
    //CameraServer.getInstance().addAxisCamera("super-cam", "10.47.76.4");
    //CameraServer.getInstance().addAxisCamera("bob-cam", "10.47.76.6");
    CameraServer.getInstance().startAutomaticCapture();
    System.out.println("BEGINNING_ROBOT_INIT finaly!!!- Instantiating subsystems for \'" + robotType.name + "\'!");
    readData = false; //Reading data every loop is VERY performance heavy, so make sure readData is off when not needed!
    debugJeVois = false;

    //SUBSYSTEMS: (Please note: when switching robots, change the currentRobot variable, not the subsystem constructors)
    //PLEASE NOTE!!!!! Currently, cheesydrive only using left joystick on driver (turning isnt on right joystick)
    driveTrain = new DriveTrainSubsystem();
    //driveTrain = new DriveTrainSubsystem();//blank subsystem

    jeVois = new JeVoisSubsystem(true); //Proper subsystem (doesn't require different constructors for different robots)
    //jeVois = new JeVoisSubsystem(); //Blank subsystem for jevois
    //jeVois = new JeVoisSubsystem(false); //Actual subsystem for jevois - boolean= use two cameras?

    elevator = new ElevatorSusbsystem();
    //elevator = new ElevatorSusbsystem();//blank subsystem

    intake = new IntakeSubsystem(true, true);
    //intake = new IntakeSubsystem();//blank subsystem

    shoulder = new ShoulderSubsystem();
    //shoulder = new ShoulderSubsystem();//blank subsystem

    climber = new ClimberSubsystem();
    //climber = new ClimberSubsystem();//blank subsystem
    oi = new OI(false);//false = doubleplayer, true = singleplayer
    
    dPadLeftCommand = new EnterPickupCargoMode();
    //Create chooser tool for different autonomouses
    chooser.setDefaultOption("Default Auto", new Autonomous());
    //chooser.addOption("Basic Autonomous", new SuperAutonomous());
    //Display the chooser tool on the SmartDashboard
    SmartDashboard.putData("Auto Mode:", chooser);
    
    //Add Calibrations to the SmartDashboard:
    /*SmartDashboard.putData("Reset Bottom", new ResetBottomElevatorHeight(currentRobot));
    SmartDashboard.putData("Reset Middle", new ResetMiddleElevatorHeight(currentRobot));
    SmartDashboard.putData("Reset High", new ResetHighElevatorHeight(currentRobot));

    Command mouth = new ToggleMouthOpen(false, currentRobot);
    SmartDashboard.putData("a TOGGLE MOUTH", mouth);
    */
    Command all = new RaiseClimbers();
    SmartDashboard.putData("the RAISE ALL", all);
    Command none = new LowerClimbers();
    SmartDashboard.putData("the LOWER ALL", none);
    Command front = new ToggleFront();
    SmartDashboard.putData("the CLIMB FRONT", front);
    Command rear = new ToggleRear();
    SmartDashboard.putData("the CLIMB REAR", rear);
/*
    Command forward = new ToggleShoulder();
    SmartDashboard.putData("FORWARD SHOULDER", forward);
    Command reverse = new ReverseShoulder();
    SmartDashboard.putData("REVERSE SHOULDER", reverse);*/
    
    //myAuto = new InitIntake();
    myAuto = null;
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
      //jeVois.newPrint();
      System.out.println("X:" + jeVois.getXAvg(false));
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
    //SmartDashboard.putBoolean("Enabled", false);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    oi.rumble(0);
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
    Robot.climber.lowerAllClimbers();
    //autonomousCommand = chooser.getSelected();
    autonomousCommand = myAuto;
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
    int mpov = oi.getManipulatorPOV();

    if ((mpov != moldPOV) && (mpov == XBox.LEFT_POV)) {
      System.out.println("Hey, you pressed the left pov button! Good job! :D Ima run the supa command!");
      //dPadLeftCommand.start();
    }

    moldPOV = mpov;
  }

  @Override
  public void teleopInit() {
    t.reset();
    t.start();
    //CameraServer.getInstance().addAxisCamera("super-cam", "10.47.76.4");
    //CameraServer.getInstance().removeCamera("super-cam");
    //CameraServer.getInstance().removeCamera("basic-cam");
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    System.out.println("TELEOP_INIT");
    //SmartDashboard.putBoolean("Enabled", true);
    //System.out.println("{\"FD\": 0, \"X1\": 0, \"Y1\": 0, \"W1\": 0, \"H1\": 0, \"X2\": 0, \"Y2\": 0, \"W2\": 0, \"H2\": 0}\n{".getBytes().length);
    Robot.climber.lowerAllClimbers();
    Robot.intake.setJawClosed(false);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //System.out.println("TeleopRunning");
    //System.out.println("TIME: " + t.get());
    Scheduler.getInstance().run(); 
    int mpov = oi.getManipulatorPOV();

    if ((mpov != moldPOV) && (mpov == XBox.LEFT_POV)) {
      System.out.println("Hey, you pressed the left pov button! Good job! :D Ima run the supa command!");
      //dPadLeftCommand.start();
    }

    moldPOV = mpov;
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
