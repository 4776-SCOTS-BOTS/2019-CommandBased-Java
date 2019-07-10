/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.operations;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LowerClimbers extends Command {

  //#region Variables
  double potSlope = 10.0;
  double increment = 0.02;
  int movingAverage = 5;
  double deadband = 0.0005;
  double fullSpeed = 1;//Decrease this if RearLeftClimber is not the slowest
  double speed1FL;
  double speed2FL;
  double speed3FL;
  double speed4FL;
  double speed5FL;
  double speed1FR;
  double speed2FR;
  double speed3FR;
  double speed4FR;
  double speed5FR;
  double speed1RL;
  double speed2RL;
  double speed3RL;
  double speed4RL;
  double speed5RL;
  double speed1RR;
  double speed2RR;
  double speed3RR;
  double speed4RR;
  double speed5RR;
  double averageFL;
  double averageFR;
  double averageRL;
  double averageRR;
  boolean rampingup;//if you are still ramping up speed
  boolean rampingdown;//rampingup but for down
  double prevFL;
  double prevFR;
  double prevRL;
  double prevRR;
  double powerFL;
  double powerFR;
  double powerRL;
  double powerRR;
  double rampupOffset = 0.1;
  double rampdownOffset = 0.1;
  int count;//count the frames
  //#endregion

  public LowerClimbers() {
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    rampingup = true;
    rampingdown = false;
    speed1FL=0;
    speed2FL=0;
    speed3FL=0;
    speed4FL=0;
    speed5FL=0;
    speed1FR=0;
    speed2FR=0;
    speed3FR=0;
    speed4FR=0;
    speed5FR=0;
    speed1RL=0;
    speed2RL=0;
    speed3RL=0;
    speed4RL=0;
    speed5RL=0;
    speed1RR=0;
    speed2RR=0;
    speed3RR=0;
    speed4RR=0;
    speed5RR=0;
    averageFL = 0;
    averageFR = 0;
    averageRL = 0;
    averageRR = 0;
    rampingup = true;
    count = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    powerFL=0;
    powerFR=0;
    powerRL=0;
    powerRR=0;
    //#region Ramp Up
    if ((Robot.climber.getFrontLeftPot(false) < (Robot.robotType.FRONT_LEFT_CLIMBING_IN - rampupOffset)) && //check signs
      (Robot.climber.getFrontLeftPot(false) < (Robot.robotType.FRONT_LEFT_CLIMBING_IN - rampupOffset)) &&  //check signs
      (Robot.climber.getFrontLeftPot(false) < (Robot.robotType.FRONT_LEFT_CLIMBING_IN - rampupOffset)) && //check signs
      (Robot.climber.getFrontLeftPot(false) < (Robot.robotType.FRONT_LEFT_CLIMBING_IN - rampupOffset))){ //check signs
      powerFL= potSlope * Robot.climber.getFrontLeftPot(false);
      powerFR= potSlope * Robot.climber.getFrontRightPot(false);
      powerRL= potSlope * Robot.climber.getRearLeftPot(false);
      powerRR= potSlope * Robot.climber.getRearLeftPot(false);
      rampingup= true;

      speed1FL= Robot.climber.getFrontLeftPot(false)-prevFL;
      speed1FR= Robot.climber.getFrontRightPot(false)-prevFL;
      speed1RL= Robot.climber.getRearLeftPot(false)-prevFL;
      speed1RL= Robot.climber.getRearRightPot(false)-prevFL;
      prevFL=Robot.climber.getFrontLeftPot(false);
      prevFR=Robot.climber.getFrontRightPot(false);
      prevRL=Robot.climber.getRearLeftPot(false);
      prevRR=Robot.climber.getRearRightPot(false);
      if (speed1FL>(potSlope+deadband)){//Check sign to check if too slow
        powerFL+=increment;//Check sign to increase speed
      } else if(speed1FL<potSlope-deadband){//Check sign to check if too fast
        powerFL-=increment;//Check sign to decrease speed
      }
      if (speed1FR>(potSlope+deadband)){//Check sign to check if too slow
        powerFR+=increment;//Check sign to increase speed
      } else if(speed1FR<potSlope-deadband){//Check sign to check if too fast
        powerFR-=increment;//Check sign to decrease speed
      }
      if (speed1RL>(potSlope+deadband)){//Check sign to check if too slow
        powerRL+=increment;//Check sign to increase speed
      } else if(speed1RL<potSlope-deadband){//Check sign to check if too fast
        powerRL-=increment;//Check sign to decrease speed
      }
      if (speed1RR>(potSlope+deadband)){//Check sign to check if too slow
        powerRR+=increment;//Check sign to increase speed
      } else if(speed1RR<potSlope-deadband){//Check sign to check if too fast
        powerRR-=increment;//Check sign to decrease speed
      }
    } else if(rampingup){
      powerFL=fullSpeed;
      powerFR=fullSpeed;
      powerRL=fullSpeed;
      powerRR=fullSpeed;
      rampingup=false;
    }
    //#endregion
    
    //#region Ramp Down
    if ((Robot.climber.getFrontLeftPot(false) > (Robot.robotType.FRONT_LEFT_CLIMBING_OUT - rampdownOffset)) && //check signs
      (Robot.climber.getFrontRightPot(false) > (Robot.robotType.FRONT_RIGHT_CLIMBING_OUT - rampdownOffset)) &&  //check signs
      (Robot.climber.getRearLeftPot(false) > (Robot.robotType.REAR_LEFT_CLIMBING_OUT - rampdownOffset)) && //check signs
      (Robot.climber.getRearRightPot(false) > (Robot.robotType.REAR_RIGHT_CLIMBING_OUT - rampdownOffset))){ //check signs
      powerFL= potSlope * Robot.climber.getFrontLeftPot(false);
      powerFR= potSlope * Robot.climber.getFrontRightPot(false);
      powerRL= potSlope * Robot.climber.getRearLeftPot(false);
      powerRR= potSlope * Robot.climber.getRearLeftPot(false);
      rampingdown= true;//you are ramping down
      speed1FL= Robot.climber.getFrontLeftPot(false)-prevFL;
      speed1FR= Robot.climber.getFrontRightPot(false)-prevFL;
      speed1RL= Robot.climber.getRearLeftPot(false)-prevFL;
      speed1RL= Robot.climber.getRearRightPot(false)-prevFL;
      prevFL=Robot.climber.getFrontLeftPot(false);
      prevFR=Robot.climber.getFrontRightPot(false);
      prevRL=Robot.climber.getRearLeftPot(false);
      prevRR=Robot.climber.getRearRightPot(false);
      if (speed1FL>(potSlope+deadband)){//Check sign to check if too slow
        powerFL+=increment;//Check sign to increase speed
      } else if(speed1FL<potSlope-deadband){//Check sign to check if too fast
        powerFL-=increment;//Check sign to decrease speed
      }
      if (speed1FR>(potSlope+deadband)){//Check sign to check if too slow
        powerFR+=increment;//Check sign to increase speed
      } else if(speed1FR<potSlope-deadband){//Check sign to check if too fast
        powerFR-=increment;//Check sign to decrease speed
      }
      if (speed1RL>(potSlope+deadband)){//Check sign to check if too slow
        powerRL+=increment;//Check sign to increase speed
      } else if(speed1RL<potSlope-deadband){//Check sign to check if too fast
        powerRL-=increment;//Check sign to decrease speed
      }
      if (speed1RR>(potSlope+deadband)){//Check sign to check if too slow
        powerRR+=increment;//Check sign to increase speed
      } else if(speed1RR<potSlope-deadband){//Check sign to check if too fast
        powerRR-=increment;//Check sign to decrease speed
      }
    } else if(rampingup){
      powerFL=fullSpeed;
      powerFR=fullSpeed;
      powerRL=fullSpeed;
      powerRR=fullSpeed;
      rampingup=false;
    }
    //#endregion
    

    //#region Steady State
    if (!rampingup && !rampingdown){
      speed5FL=speed4FL;
      speed4FL=speed3FL;
      speed3FL=speed2FL;
      speed2FL=speed1FL;
      speed1FL=Robot.climber.getFrontLeftPot(false) - prevFL;
      speed5FR=speed4FR;
      speed4FR=speed3FR;
      speed3FR=speed2FR;
      speed2FR=speed1FR;
      speed1FR=Robot.climber.getFrontRightPot(false) - prevFR;
      speed5RL=speed4RL;
      speed4RL=speed3RL;
      speed3RL=speed2RL;
      speed2RL=speed1RL;
      speed1RL=Robot.climber.getRearLeftPot(false) - prevRL;
      speed5RR=speed4RR;
      speed4RR=speed3RR;
      speed3RR=speed2RR;
      speed2RR=speed1RR;
      speed1RR=Robot.climber.getRearRightPot(false) - prevRR;
      prevFL = Robot.climber.getFrontLeftPot(false);
      prevFR = Robot.climber.getFrontRightPot(false);
      prevRL = Robot.climber.getRearLeftPot(false);
      prevRR = Robot.climber.getRearRightPot(false);
      count++;
      if (count>6){
        averageFL=(speed1FL+speed2FL+speed3FL+speed4FL+speed5FL)/5;
        averageFR=(speed1FR+speed2FR+speed3FR+speed4FR+speed5FR)/5;
        averageRL=(speed1RL+speed2RL+speed3RL+speed4RL+speed5RL)/5;
        averageRR=(speed1RR+speed2RR+speed3RR+speed4RR+speed5RR)/5;
      }
      //when extending:
      //fl speed/slope is NEGATIVE
      //fr speed/slope is POSITIVE
      //rl speed/slope is POSITIVE
      //rr speed/slope is NEGATIVE
      if (-averageRL > (averageRR + deadband)){
        powerRR += increment;
      } else if (-averageRL < (averageRR - deadband)){
        powerRR -= increment;
      }
      if (averageFL > (-averageFR + deadband)){
        powerFR += increment;
      } else if (averageFL < (-averageFR - deadband)){
        powerFR -= increment;
      }
    } else {
      count = 0;
    }
    //#endregion
    
    Robot.climber.powerClimbers(powerFL, powerFR, powerRL, powerRR);
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
