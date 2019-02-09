package frc.robot;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
//import edu.wpi.first.wpilibj.smartdashboard.*;

public class Robot extends TimedRobot {
  public static SerialPort mySerialPort;
  Joystick driveStick = new Joystick(0);
  Joystick mechStick = new Joystick(1);
  TalonSRX FL = new TalonSRX(0);
  TalonSRX BL = new TalonSRX(1);
  TalonSRX FR = new TalonSRX(2);
  TalonSRX BR = new TalonSRX(3);
  TalonSRX leftBall = new TalonSRX(4);
  TalonSRX rightBall = new TalonSRX(5);
  TalonSRX lift = new TalonSRX(6);
  Servo leftServo = new Servo(0);
  Servo rightServo = new Servo(1);
  String command;

  public static SerialPort serialPort;
 // SmartDashboard dashboard;

  @Override
  public void robotInit() {
    try {
      mySerialPort = new SerialPort(9600, SerialPort.Port.kMXP);
      mySerialPort.setTimeout(0.8);
    }  

    catch (Exception e) {
      DriverStation.getInstance();
      DriverStation.reportWarning("Cannot connect to arduino :(", false);
    }
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() { 
  }

  @Override
  public void teleopInit() {
    leftServo.setAngle(180);
    rightServo.setAngle(43);
  }

  @Override
  public void teleopPeriodic() { 
    //Vision
    while(driveStick.getRawButton(4))
    {
      command = serialPort.readString(1);

      DriverStation.getInstance();
      DriverStation.reportWarning(serialPort.readString(1), false);

      if (command.equals("R"))
      {
        FL.set(ControlMode.PercentOutput, 0.4);
        BL.set(ControlMode.PercentOutput, 0.4);
      }

      else
      {
        break;
      }

      if (command.equals("L"))
      {
        FR.set(ControlMode.PercentOutput, 0.4);
        BR.set(ControlMode.PercentOutput, 0.4);
      }

      else
      {
        break;
      }

    }
    

    //Tank Drive
    FR.set(ControlMode.PercentOutput, driveStick.getRawAxis(1));
    BR.set(ControlMode.PercentOutput, driveStick.getRawAxis(1));
    FL.set(ControlMode.PercentOutput, driveStick.getRawAxis(5));
    BL.set(ControlMode.PercentOutput, driveStick.getRawAxis(5));

    //Disc Mech
    while (driveStick.getRawButton(5)) {
      leftServo.setAngle(80);
      rightServo.setAngle(143);
    }
    while (driveStick.getRawButton(6)) {
      leftServo.setAngle(180);
      rightServo.setAngle(43);
    }

    //Ball Mech
    while (mechStick.getRawAxis(2) > 0) {
      leftBall.set(ControlMode.PercentOutput, mechStick.getRawAxis(2));
      rightBall.set(ControlMode.PercentOutput, mechStick.getRawAxis(2));
    }
    
    while (mechStick.getRawAxis(3) > 0) {
      leftBall.set(ControlMode.PercentOutput, -mechStick.getRawAxis(3));
      rightBall.set(ControlMode.PercentOutput, -mechStick.getRawAxis(3));
    }

    //Lift Mech
    while (mechStick.getRawButton(1)) {
      
    }

  }

  @Override
  public void testPeriodic() {
  }

  
}
