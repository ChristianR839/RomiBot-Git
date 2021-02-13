// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.lang.reflect.Array;
import java.util.function.Supplier;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final Supplier<Double> m_xaxisSpeedSupplier;
  private final Supplier<Double> m_zaxisRotateSupplier;
  public static double speedMultiplier = 0.6;
  // private int currentLoop;
  // private int previousLoop;
  private int loopCount = 0;
  private int changeSum = 0;
  private double currentAngle = 0;
  private double lastAngle = 0;

  // private double[] valueArray = new double[1000];
  // private double[] changeArray = new double[1000];

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to
   * the speed supplier lambdas. This command does not terminate.
   *
   * @param drivetrain          The drivetrain subsystem on which this command
   *                            will run
   * @param xaxisSpeedSupplier  Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public ArcadeDrive(Drivetrain drivetrain, Supplier<Double> xaxisSpeedSupplier, Supplier<Double> zaxisRotateSupplier) {
    m_drivetrain = drivetrain;
    m_xaxisSpeedSupplier = xaxisSpeedSupplier;
    m_zaxisRotateSupplier = zaxisRotateSupplier;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // currentLoop = 0;
    // previousLoop = 0;
    loopCount = 0;
    currentAngle = 0;
    lastAngle = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println("gyro Z: " + m_drivetrain.getGyroAngleZ());
    //System.out.println("average z_angle change: " + driftCheck());
    //System.out.println("current angle- " + m_drivetrain.getGyroAngleZ());
    driftCheck();
    
    // SmartDashboard.putNumber("z_angle ROC", zRateChange());
    m_drivetrain.arcadeDrive((speedMultiplier * m_xaxisSpeedSupplier.get()),
        (speedMultiplier * m_zaxisRotateSupplier.get()));
    // System.out.println(currentAngle);
    // for (int i = 0; i < 15; i++)
    // System.out.println(valueArray[i]);
  }

  public double driftCheck() {
    loopCount++;
    double currentChange = currentAngle - lastAngle;
    currentAngle = m_drivetrain.getGyroAngleZ();
    if (loopCount < 5) { lastAngle = currentAngle; }
    if (currentChange < 0.25 && currentChange > -0.25) { currentAngle = 0; } 
    //if (currentAngle - lastAngle != 0) { System.out.println("current difference- " + currentChange); }
    lastAngle = currentAngle;
    return (double) currentChange;

    /*
    changeSum += currentAngle - lastAngle;
    lastAngle = currentAngle;
    return (double) changeSum / loopCount;
    */

    // if (loopCount % 4 == 100) {
    // changeSum += currentAngle - lastAngle;
    // lastAngle = currentAngle;
    // return (double) changeSum / loopCount;
    // }
    // return (double) changeSum / loopCount;
    
  }

  // public double zRateChange() {
  // ++currentLoop;
  // previousLoop = currentLoop - 1;
  // valueArray[currentLoop] = currentAngle;
  // // Array.set(valueArray, currentLoop, currentAngle);
  // if (currentLoop > 1) {
  // changeArray[currentLoop] = currentAngle - valueArray[previousLoop];
  // for (int i = 0; i < changeArray.length; i++)
  // changeSum += changeArray[i];
  // double avgChange = changeSum / currentLoop;
  // return avgChange;
  // } else {
  // return 999999999;
  // }
  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
