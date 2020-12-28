import exceptions.AddToCellException;
import file.FileWork;
import world.OceanMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, AddToCellException, InterruptedException {
        System.out.println("Choice work mode:");
        System.out.println("1. Automatic simulation");
        System.out.println("2. Step-by-step simulation");
        System.out.println("Your choice:\t");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        OceanMap map;

        loopEnd:
        switch (choice) {
            case 1:
                while (true) {
                    map = FileWork.read();
                    map.step();
                    System.out.println(map);
                    System.out.println('\n');
                    FileWork.save(map);
                    Thread.sleep(5000);
                    if (System.in.available() != 0) {
                        System.out.println("The end.");
                        break;
                    }
                }
                break;
            case 2:
                while (true) {
                    map = FileWork.read();
                    System.out.println("Choice action:");
                    System.out.println("1. Next step");
                    System.out.println("2. The end");
                    System.out.println("Your choice:\t");
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            System.out.println(map + "\n");
                            map.step();
                            break;
                        case 2:
                            System.out.println(map + "\n");
                            break loopEnd;
                        default:
                            System.out.println("Incorrect input");
                            break;
                    }
                    FileWork.save(map);
                }
            default:
                break;
        }

        scanner.close();
    }
}
