// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnDegreesGyro extends CommandBase {
  private final Drivetrain m_drive;
  private final double m_degrees;
  private final double m_speed;
  private double startingAngle;
  private double currentAngle;
  private double deadband;
  private double angleMod;

  /**
   * Creates a new TurnDegrees. This command will turn your robot for a desired
   * rotation (in degrees) and rotational speed.
   *
   * @param speed   The speed which the robot will drive. Negative is in reverse.
   * @param degrees Degrees to turn. Leverages encoders to compare distance.
   * @param drive   The drive subsystem on which this command will run
   */
  public TurnDegreesGyro(double speed, double degrees, Drivetrain drive) {
    m_degrees = degrees;
    m_speed = speed;
    m_drive = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    try {
      Thread.sleep(250);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // Set motors to stop, read encoder values for starting point
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
    startingAngle = m_drive.getGyroAngleZ();
    currentAngle = 0;
    deadband = 2;
    angleMod = 0.0000000000001;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = m_drive.getGyroAngleZ();
    // if (m_degrees < 0){ // ((Math.abs(startingAngle) + Math.abs(currentAngle)) -
    // deadband)) {
    // angleMod = (m_degrees - Math.abs(startingAngle) -
    // Math.abs(currentAngle))/100;
    // } else if (m_degrees > 0) { // ((Math.abs(startingAngle) -
    // Math.abs(currentAngle)) + deadband)) {
    // angleMod = -(m_degrees - Math.abs(startingAngle) -
    // Math.abs(currentAngle))/100;
    // } else {
    // angleMod = 0;
    // }
    m_drive.arcadeDrive(0, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if ((currentAngle - startingAngle) <= (-m_degrees + 22)
    || (currentAngle - startingAngle) >= (m_degrees - 22)) {
      return true;
    } else {
      return false;
    }

    // if (m_degrees < 0 && ((Math.abs(startingAngle) + Math.abs(currentAngle)) -
    // deadband) <= m_degrees) {
    // return true;
    // } else if (m_degrees > 0 && ((Math.abs(startingAngle) -
    // Math.abs(currentAngle)) - deadband) >= m_degrees) {
    // return true;
    // } else if (angleMod == 0) {
    // return true;
    // } else {
    // return false;
    // }
  }
}
