// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class MondrianMadness extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive based on distance. This will drive out for a specified distance,
   * turn around and drive back.
   * 
   * Left turn is negative speed, right turn is positive speed.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   */
  public MondrianMadness(Drivetrain drivetrain) {
    addCommands(
        new DriveDistance(0.7, 17.5, drivetrain),
        new TurnDegreesGyro(-0.4, 85, drivetrain),
        new DriveDistance(0.7, 17, drivetrain),
        new TurnDegreesGyro(-0.4, 85, drivetrain),
        new DriveDistance(0.7, 8, drivetrain),
        new TurnDegreesGyro(-0.4, 45, drivetrain),
        new DriveDistance(0.7, 13.5, drivetrain), // 12.12
        new TurnDegreesGyro(0.4, 45, drivetrain),
        new DriveDistance(0.7, 7.1, drivetrain),
        new TurnDegreesGyro(0.4, 92.5, drivetrain),
        new DriveDistance(0.7, 16.05, drivetrain),
        new TurnDegreesGyro(0.4, 88, drivetrain),
        new DriveDistance(0.7, 17.75, drivetrain)

        // WORKED with mult 0.9, speed here 0.7
        // new DriveDistance(0.7, 17.5, drivetrain),
        // new TurnDegreesGyro(-0.4, 85, drivetrain),
        // new DriveDistance(0.7, 17.5, drivetrain),
        // new TurnDegreesGyro(-0.4, 85, drivetrain),
        // new DriveDistance(0.7, 8.5, drivetrain),
        // new TurnDegreesGyro(-0.4, 45, drivetrain),
        // new DriveDistance(0.7, 12, drivetrain), // 12.12
        // new TurnDegreesGyro(0.4, 45, drivetrain),
        // new DriveDistance(0.7, 9, drivetrain),
        // new TurnDegreesGyro(0.4, 95, drivetrain),
        // new DriveDistance(0.7, 17.5, drivetrain),
        // new TurnDegreesGyro(0.4, 85, drivetrain),
        // new DriveDistance(0.7, 17.5, drivetrain)

        // WORKED with mult 0.8, speed here 0.6
        // new DriveDistance(0.7, 18, drivetrain),
        // new TurnDegreesGyro(-0.4, 85, drivetrain),
        // new DriveDistance(0.7, 18, drivetrain),
        // new TurnDegreesGyro(-0.4, 85, drivetrain),
        // new DriveDistance(0.7, 9, drivetrain),
        // new TurnDegreesGyro(-0.4, 45, drivetrain),
        // new DriveDistance(0.7, 12.5, drivetrain), // 12.12
        // new TurnDegreesGyro(0.4, 45, drivetrain),
        // new DriveDistance(0.7, 10.5, drivetrain),
        // new TurnDegreesGyro(0.4, 95, drivetrain),
        // new DriveDistance(0.7, 17.5, drivetrain),
        // new TurnDegreesGyro(0.4, 85, drivetrain),
        // new DriveDistance(0.7, 17, drivetrain)
        );
  }
}
