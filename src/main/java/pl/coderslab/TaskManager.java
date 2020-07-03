package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.sql.CommonDataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {

        String[][] tasks = tasks("tasks.csv");


        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option:");


        while (true) {
            System.out.println(ConsoleColors.RESET + " add \n remove \n list \n exit");
            String line = scan.nextLine();

            if (line.equals("add")) {

                tasks = add(tasks);

            } else if (line.equals("remove")) {
                tasks = remove(tasks);

            } else if (line.equals("list")) {
                list(tasks);

            } else if (line.equals("exit")) {
                writeToFile("tasks.csv", tasks);

                break;
            }

        }

    }


    public static String[][] tasks(String fileName) {
        File file = new File(fileName);
        StringBuilder build = new StringBuilder();
        try (Scanner scan1 = new Scanner(file)) {

            while (scan1.hasNextLine()) {
                build.append(scan1.nextLine() + ", ");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Brak pliku");
        }
        String s = build.toString();
        String[] tab = s.split(", ");
        String[][] tab1 = new String[3][(tab.length) / 3];
        int counter = 0;
        for (int i = 0; i < tab1.length; i++) {
            for (int j = 0; j < tab1[i].length; j++) {
                tab1[i][j] = tab[counter];
                counter++;
            }
        }

        return tab1;

    }

    public static void writeToFile(String fileName, String[][] taskTab) {
        Path path1 = Paths.get(fileName);
        List<String> outList = new ArrayList<>();
        String s = "";
        try {
            for (int i = 0; i < taskTab.length; i++) {
                s = "";
                for (int j = 0; j < taskTab[i].length; j++) {
                    s += taskTab[i][j] + ", ";

                }
                s = s.substring(0, s.length() - 2);
                outList.add(s);
                Files.write(path1, outList);
            }
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }


    public static void list(String[][] tab) {
        try {
            for (int i = 0; i < tab.length; i++) {
                String txt = i + " : ";
                for (int j = 0; j < tab[i].length; j++) {
                    txt += tab[i][j] + ", ";
                }
                txt = txt.substring(0, txt.length() - 2);
                System.out.print(txt);
                System.out.println();
            }
        } catch (NullPointerException exx) {
            exx.fillInStackTrace();
        }

    }

    public static String[][] remove(String[][] tab) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type task index to remove:");
        int i = Integer.parseInt(scan.next());
        tab = ArrayUtils.remove(tab, i);
        return tab;
    }

    public static String[][] add(String[][] tab) {
        Scanner scan = new Scanner(System.in);
        String task = "";
        String date = "";
        String importance = "";
        System.out.println("Please add task description: ");
        task = scan.nextLine();
        System.out.println("Please add due date: ");
        date = scan.nextLine();
        System.out.println("Is this task important? true/false ");
        importance = scan.nextLine();
        try {
            tab = Arrays.copyOf(tab, tab.length + 1);
            tab[tab.length - 1] = new String[3];
            tab[tab.length - 1][0] = task;
            tab[tab.length - 1][1] = date;
            tab[tab.length - 1][2] = importance;

        } catch (NullPointerException exxxx) {
            exxxx.fillInStackTrace();
        }

        return tab;
    }

}
