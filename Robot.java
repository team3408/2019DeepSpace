// February 17, 2019, with very good vision code.
package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {
  Joystick driveStick = new Joystick(0);
  Joystick mechStick = new Joystick(1);
  TalonSRX FL = new TalonSRX(3);
  TalonSRX BL = new TalonSRX(2);
  TalonSRX FR = new TalonSRX(0);
  TalonSRX BR = new TalonSRX(1);
  TalonSRX ballMech = new TalonSRX(4);
  TalonSRX lift = new TalonSRX(5);
  TalonSRX extra = new TalonSRX(6);
  Servo leftServo = new Servo(0);
  Servo rightServo = new Servo(1);
  private String command;
  private boolean connected = false;
  private boolean waiting = false;
  private double motorSpeed = 0;
  private double autoTurn = 0.05;
  public static SerialPort serialPort;

  public void semiAuto()
 {
   serialPort.writeString("I");

   command = "";
   waiting = true;

   while(waiting)
   {
     command = serialPort.readString(1);
     motorSpeed = (-driveStick.getRawAxis(1) / 3);
     double strongSide = motorSpeed + autoTurn;
     double weakSide = motorSpeed;

     if (command.equals("R"))
     {
       DriverStation.reportWarning(command, false);
       FL.set(ControlMode.PercentOutput, -strongSide);
       BL.set(ControlMode.PercentOutput, -strongSide);
       FR.set(ControlMode.PercentOutput, -weakSide);
       BR.set(ControlMode.PercentOutput, -weakSide);
       waiting = false;
     }
     else if (command.equals("L"))
     {
       DriverStation.reportWarning(command, false);
       FL.set(ControlMode.PercentOutput, -weakSide);
       BL.set(ControlMode.PercentOutput, -weakSide);
       FR.set(ControlMode.PercentOutput, -strongSide);
       BR.set(ControlMode.PercentOutput, -strongSide);
       waiting = false;
     }
     else if (command.equals("C"))
     {
       DriverStation.reportWarning(command, false);
       FL.set(ControlMode.PercentOutput, -weakSide);
       BL.set(ControlMode.PercentOutput, -weakSide);
       FR.set(ControlMode.PercentOutput, -weakSide);
       BR.set(ControlMode.PercentOutput, -weakSide);
       waiting = false;
     }
     else if (command.equals("X"))
     {
       DriverStation.reportWarning(command, false);
       FL.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) / 2);
       BL.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) / 2);
       FR.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) / 2);
       BR.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) / 2);
       waiting = false;
     }
   }
 }

  @Override
  public void robotInit() {
    try
   {
     serialPort = new SerialPort(115200, SerialPort.Port.kMXP);
     serialPort.setTimeout(0.8);
     connected = true;
   }
   catch (Exception e)
   {
     DriverStation.getInstance();
     DriverStation.reportWarning("Can't Connect to RioDiuno", false);
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
    if (driveStick.getRawButton(4) && connected == true) {
      semiAuto();
    }

    double mechValue = driveStick.getRawAxis(2);
		double mechValue1 = driveStick.getRawAxis(3);

    //Tank Drive
    FL.setInverted(true);
    BL.setInverted(true);
    if (!driveStick.getRawButton(4)) {
      FR.set(ControlMode.PercentOutput, driveStick.getRawAxis(5) * java.lang.Math.abs(driveStick.getRawAxis(5)));
      BR.set(ControlMode.PercentOutput, driveStick.getRawAxis(5) * java.lang.Math.abs(driveStick.getRawAxis(5)));
      FL.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) * java.lang.Math.abs(driveStick.getRawAxis(1)));
      BL.set(ControlMode.PercentOutput, driveStick.getRawAxis(1) * java.lang.Math.abs(driveStick.getRawAxis(1)));
    
    }
    

    //Disc Mech

    //Right is 1, Left is 0.
    while (driveStick.getRawButton(5)) {
      leftServo.setAngle(80);
      rightServo.setAngle(143);
    }
    while (driveStick.getRawButton(6)) {
      leftServo.setAngle(180);
      rightServo.setAngle(43);
    }

    //Ball Mech

    if (mechValue1 > 0) {
      ballMech.set(ControlMode.PercentOutput, (0.5)*(mechValue1*mechValue1));
      
      }
  
      else {
        ballMech.set(ControlMode.PercentOutput, 0);
      }
      if (mechValue > 0) {
      ballMech.set(ControlMode.PercentOutput, (0.5)*(-mechValue*mechValue));
      mechValue1 = driveStick.getRawAxis(3);
      }
  
    //Lift Mech
    //while((mechStick.getRawAxis(1) > 0.1) || (mechStick.getRawAxis(1) < -0.1))
    //{
     // lift.set(ControlMode.PercentOutput, mechStick.getRawAxis(1));
    //}

    if((mechStick.getRawAxis(1)) > 0 && (mechStick.getRawAxis(1) <= 0.1) || (mechStick.getRawAxis(1)) < 0 && (mechStick.getRawAxis(1) >= -0.1))
    {
      lift.set(ControlMode.PercentOutput, 0);
    }

    else
    {
      lift.set(ControlMode.PercentOutput, mechStick.getRawAxis(1));
    }

    }
  @Override
  public void testPeriodic() {

  }

  
}
