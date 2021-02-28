// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoTankDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final Supplier<Double> m_leftAxisSpeedSupplier;
  private final Supplier<Double> m_rightAxisSpeedSupplier;
  public static double speedMultiplier = 0.6;
  // private int currentLoop;
  // private int previousLoop;
  private int loopCount = 0;
  // private int changeSum = 0;
  private double currentAngle = 0;
  private double lastAngle = 0;
  private double startingAngle;
  private double deadband;
  private double angleMod;

  public static boolean IS_AUTO;

  private static FileWriter fw;

  // private double[] valueArray = new double[1000];
  // private double[] changeArray = new double[1000];

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to
   * the speed supplier lambdas. This command does not terminate.
   *
   * @param drivetrain             The drivetrain subsystem on which this command
   *                               will run
   * @param leftAxisSpeedSupplier  Lambda supplier of left wheel speed
   * @param rightAxisSpeedSupplier Lambda supplier of right wheel speed
   */
  public AutoTankDrive(Drivetrain drivetrain, Supplier<Double> leftAxisSpeedSupplier,
      Supplier<Double> rightAxisSpeedSupplier) {
    m_drivetrain = drivetrain;
    m_leftAxisSpeedSupplier = leftAxisSpeedSupplier;
    m_rightAxisSpeedSupplier = rightAxisSpeedSupplier;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.resetGyro();
    loopCount = 0;
    currentAngle = 0;
    lastAngle = 0;
    angleMod = 0;
    try {
      // fw = new FileWriter("D:\\temp\\data.csv", true);
      writeFile("current difference" + "," + "current angle" + "," + "last angle" + "," + "loopcount" + "\n");
    } catch (IOException e) {
    } finally {
    }
    startingAngle = m_drivetrain.getGyroAngleZ();
    deadband = Math.abs(startingAngle) - 0.839;
    // currentLoop = 0;
    // previousLoop = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println("gyro Z: " + m_drivetrain.getGyroAngleZ());
    // System.out.println("average z_angle change: " + driftCheck());
    // System.out.println("current angle- " + m_drivetrain.getGyroAngleZ());
    // driftCheck();

    // SmartDashboard.putNumber("z_angle ROC", zRateChange());

    currentAngle = m_drivetrain.getGyroAngleZ();
    System.out.println("CHECKING FOR DRIFT");
    // double currentChange = currentAngle - lastAngle;

    if (currentAngle < (startingAngle - deadband)) {
      angleMod = -0.3;
    } else if (currentAngle > (startingAngle + deadband)) {
      angleMod = 0.3;
    } else {
      angleMod = 0;
    }

    m_drivetrain.tankDrive((speedMultiplier * m_leftAxisSpeedSupplier.get()),
        (speedMultiplier * m_rightAxisSpeedSupplier.get() + angleMod));

    // System.out.println(lastAngle);
    // for (int i = 0; i < 15; i++)
    // System.out.println(valueArray[i]);
  }

  public static void writeFile(String str) throws IOException {
    FileWriter fw = new FileWriter("d:\\temp\\data.csv", true);
    fw.write(str);
    fw.close();
  }

  // public double driftCheck() {
  // loopCount++;
  // currentAngle = m_drivetrain.getGyroAngleZ();
  // System.out.println("CHECKING FOR DRIFT");
  // // double currentChange = currentAngle - lastAngle;

  // if (currentAngle < (startingAngle - deadband)) {
  // return -0.5;
  // } else if (currentAngle > (startingAngle + deadband)) {
  // return 0.5;
  // } else {
  // return 0;
  // }

  // if (loopCount < 5) {
  // lastAngle = currentAngle;
  // }

  // if (currentChange < 0.15 && currentChange > -0.15) {
  // currentAngle = 0;
  // }

  // try {
  // writeFile(currentChange + "," + currentAngle + "," + lastAngle + "," +
  // loopCount + "\n");
  // } catch (IOException e) {
  // System.out.println("IO excepttion"+e.getMessage());
  // } finally {
  // }

  // lastAngle = currentAngle;
  // if (currentChange != 0) { // currentAngle - lastAngle
  // return (double) currentChange * 0.1;
  // } else {
  // return 0;
  // }
  // }

  // /*
  // changeSum += currentAngle - lastAngle;
  // lastAngle = currentAngle;
  // return (double) changeSum / loopCount;
  // */

  // // if (loopCount % 4 == 100) {
  // // changeSum += currentAngle - lastAngle;
  // // lastAngle = currentAngle;
  // // return (double) changeSum / loopCount;
  // // }
  // // return (double) changeSum / loopCount;

  // }

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
