// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
  private final Drivetrain m_drive;
  private final double m_distance;
  private final double m_speed;
  private double m_startingAngle;

  /**
   * Creates a new DriveDistance. This command will drive your your robot for a
   * desired distance at a desired speed.
   *
   * @param speed  The speed at which the robot will drive
   * @param inches The number of inches the robot will drive
   * @param drive  The drivetrain subsystem on which this command will run
   */
  public DriveDistance(double speed, double inches, Drivetrain drive) {
    m_distance = inches;
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
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
    m_startingAngle = m_drive.setStartingAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.tankDriveAuto(m_speed, m_speed, m_startingAngle);
    // System.out.println("left: " + m_drive.getLeftDistanceInch() + "\t\tright: " + m_drive.getRightDistanceInch());
    // System.out.println(" ");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Compare distance travelled from start to desired distance
    return (Math.abs(m_drive.getAverageDistanceInch()) + 0.5) >= m_distance - 1;
  }
}
