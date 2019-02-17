/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public Joystick driverJoystick, manipulatorJoystick;
  boolean singlePlayer;
  //DRIVER-------------------------------
  Button autoAllignButton,
  testButton,
  rampButton,
  connectButton;
  //MANIPULATOR--------------------------
  Button setCargoHeightButton,
  setHatchHeightButton,
  setLowHeightButton,
  setMediumHeightButton,
  setHighHeightButton,
  setFaceFrontButton,
  setFaceBackButton;

  public OI () {
    //If this is used, then "just in case" use competition settings
    this(false);
  }
  public OI (boolean useSinglePlayer) {
    singlePlayer = useSinglePlayer;
    //DRIVER COMMANDS--------------------------------------------------------------------------------
    driverJoystick = new Joystick(XBox.DRIVER);
    autoAllignButton = new JoystickButton(driverJoystick, XBox.A_BUTTON);
    //autoAllignButton.whileHeld(new RampToTape());
    autoAllignButton.whileHeld(new AutoTapeAllign());//turn then ramp
    //autoAllignButton.whileHeld(new JeVoisCenter());
    rampButton = new JoystickButton(driverJoystick, XBox.B_BUTTON);
    rampButton.whenPressed(new RampToTape());
    testButton = new JoystickButton(driverJoystick, XBox.X_BUTTON);
    testButton.whileHeld(new TestCommand());
    connectButton = new JoystickButton(driverJoystick, XBox.Y_BUTTON);
    connectButton.whenPressed(new ReconnectJeVois(false));
    if (useSinglePlayer) {
      //No manipulator!
      manipulatorJoystick = null;
    } else {
      //MANIPULATOR COMMANDS------------------------------------------------------------------------
      manipulatorJoystick = new Joystick(XBox.MANIPULATOR);
      //Make robot place cargo
      setCargoHeightButton = new JoystickButton(manipulatorJoystick, XBox.RIGHT_START_BUTTON);
      setCargoHeightButton.whenPressed(new SetPickupHeight(true));
      //Make robot place hatches
      setHatchHeightButton = new JoystickButton(manipulatorJoystick, XBox.X_BUTTON);
      setHatchHeightButton.whenPressed(new SetPickupHeight(false));
      //Make elevator go to low level on rocket
      setLowHeightButton = new JoystickButton(manipulatorJoystick, XBox.A_BUTTON);
      setLowHeightButton.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeight.Low));
      //Make elevator go to medium level on rocket
      setMediumHeightButton = new JoystickButton(manipulatorJoystick, XBox.B_BUTTON);
      setMediumHeightButton.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeight.Medium));
      //Make elevator go to high level on rocket
      setHighHeightButton = new JoystickButton(manipulatorJoystick, XBox.Y_BUTTON);
      setHighHeightButton.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeight.High));
      //Make shoulder face the front
      setFaceFrontButton = new JoystickButton(manipulatorJoystick, XBox.LEFT_BUMPER_BUTTON);
      setFaceFrontButton.whenPressed(new SetShoulderSide(false));
      //Make shoulder face the back
      setFaceBackButton = new JoystickButton(manipulatorJoystick, XBox.RIGHT_BUMPER_BUTTON);
      setFaceBackButton.whenPressed(new SetShoulderSide(true));
    }
  }

  //Driver
  public double getDriverAxis(int axisIndex) {
    return driverJoystick.getRawAxis(axisIndex);
  }
  public boolean getDriverButton(int index) {
    return driverJoystick.getRawButton(index);
  }
  public int getDriverPOV() {
    return driverJoystick.getPOV();
  }
  //Manipulator
  public double getManipulatorAxis(int axisIndex) {
    if (singlePlayer)
      return 0;
    else
      return manipulatorJoystick.getRawAxis(axisIndex);
  }
  public boolean getManipulatorButton(int index) {
    if (singlePlayer)
      return false;
    else
      return manipulatorJoystick.getRawButton(index);
  }
  public int getManipulatorPOV() {
    if (singlePlayer)
      return XBox.NO_POV;
    else
      return manipulatorJoystick.getPOV();
  }

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  /**
   * Contained in this class are the mappings of joystick buttons and axes.
   */
  public static final class XBox {
    public static final int
    DRIVER = 0,
    MANIPULATOR = 1,
    A_BUTTON = 1,
    B_BUTTON = 2,
    X_BUTTON = 3,
    Y_BUTTON = 4,
    LEFT_BUMPER_BUTTON = 5,
    RIGHT_BUMPER_BUTTON = 6,
    LEFT_START_BUTTON = 7,
    RIGHT_START_BUTTON = 8,
    LEFT_STICK_BUTTON = 9,
    RIGHT_STICK_BUTTON = 10,
    LEFT_X_AXIS = 0,
    LEFT_Y_AXIS = 1,
    LEFT_TRIGGER_AXIS = 2,
    RIGHT_TRIGGER_AXIS = 3,
    RIGHT_X_AXIS = 4,
    RIGHT_Y_AXIS = 5,
    NO_POV = -1,
    TOP_POV = 0,
    TOP_RIGHT_POV = 45,
    RIGHT_POV = 90,
    BOTTOM_RIGHT_POV = 135,
    BOTTOM_POV = 180,
    BOTTOM_LEFT_POV = 225,
    LEFT_POV = 270,
    TOP_LEFT_POV = 315;
  }
}
