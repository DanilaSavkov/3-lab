package file;

import enums.Gender;
import exceptions.AddToCellException;
import nature.*;
import world.Cell;
import world.OceanMap;

import java.io.*;

public final class FileWork {
    private static final File file = new File("Input.txt");

    public static OceanMap read() throws IOException, AddToCellException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        OceanMap result = getEmptyMap(reader);
        String line;
        while ((line = reader.readLine()) != null) {
            if (!emptyCell(line)) {
                Cell temp = getEmptyCell(line);
                while (!endOfCell(line = reader.readLine())) {
                    OceanLife dweller = nextObj(line, temp);
                    result.getCell(temp.getX(), temp.getY()).add(dweller);
                }
            }
        }
        reader.close();
        fileReader.close();
        return result;
    }

    public static void save(OceanMap map) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(map.toString());
        writer.flush();
        writer.close();
    }

    private static OceanMap getEmptyMap(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int beginIndex = line.indexOf('=');
        int endIndex = line.indexOf(',');
        int mapLength = Integer.parseInt(line.substring(beginIndex + 2, endIndex));
        beginIndex = line.indexOf('=', endIndex);
        endIndex = line.indexOf(')');
        int mapWidth = Integer.parseInt(line.substring(beginIndex + 2, endIndex));
        return new OceanMap(mapLength, mapWidth);
    }

    private static boolean emptyCell(String line) {
        return !line.endsWith("{");
    }

    private static boolean endOfCell(String line) {
        return line.endsWith("}");
    }

    private static OceanLife nextObj(String line, Cell cell) throws AddToCellException {
        if (line.contains(Plankton.class.getSimpleName())) return new Plankton(findHealthPoints(line), cell);
        else if (line.contains(Shark.class.getSimpleName())) return new Shark(findGender(line), findSteps(line)[0], findSteps(line)[1], cell);
        else if (line.contains(Fish.class.getSimpleName())) return new Fish(findGender(line), findSteps(line)[0], findSteps(line)[1], cell);
        else return null;
    }

    private static Gender findGender(String line) {
        int commaIndex = line.indexOf(',');
        int beginIndex = line.indexOf('=', commaIndex);
        int endIndex = line.indexOf(')');
        String genderString = line.substring(beginIndex + 2, endIndex);
        Gender gender;
        if (genderString.equals(Gender.MALE.toString())) return Gender.MALE;
        else return Gender.FEMALE;
    }

    private static int findHealthPoints(String line) {
        int beginIndex = line.indexOf('=');
        int endIndex = line.indexOf(')');
        String healthPointsString = line.substring(beginIndex + 2, endIndex);
        return Integer.parseInt(healthPointsString);
    }

    private static int[] findSteps(String line) {
        int beginIndex = line.indexOf('=');
        int endIndex = line.indexOf('/');
        String steps1 = line.substring(beginIndex + 2, endIndex);
        beginIndex = endIndex;
        endIndex = line.indexOf(',');
        String steps2 = line.substring(beginIndex + 1, endIndex);
        int[] result = new int[2];
        result[0] = Integer.parseInt(steps1);
        result[1] = Integer.parseInt(steps2);
        return result;
    }

    private static Cell getEmptyCell(String line) {
        int beginIndex = line.indexOf('=');
        int endIndex = line.indexOf(',');
        int cellX = Integer.parseInt(line.substring(beginIndex + 2, endIndex));
        beginIndex = line.indexOf('=', endIndex);
        endIndex = line.indexOf(')');
        int cellY = Integer.parseInt(line.substring(beginIndex + 2, endIndex));
        return new Cell(cellX, cellY);
    }
}
