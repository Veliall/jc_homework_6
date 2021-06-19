package jc_homework_6;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(100, 1, 1, 100);
        GameProgress gameProgress2 = new GameProgress(200, 2, 2, 200);
        GameProgress gameProgress3 = new GameProgress(300, 3, 3, 300);

        if (saveGame("Z://Games/saveGames/gameProgress1.dat", gameProgress1)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }
        if (saveGame("Z://Games/saveGames/gameProgress2.dat", gameProgress2)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }
        if (saveGame("Z://Games/saveGames/gameProgress3.dat", gameProgress3)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }

        if (zipFiles("Z://Games/saveGames/save.zip",
                "Z://Games/saveGames/gameProgress1.dat",
                "Z://Games/saveGames/gameProgress2.dat",
                "Z://Games/saveGames/gameProgress3.dat")) {
            System.out.println("Файлы сохранений заархивированы");
        } else {
            System.out.println("Произошла ошибка архивации");
        }

    }

    public static boolean saveGame(String dirPath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(dirPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean zipFiles(String dirPath, String... args) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dirPath))) {
            for (String filePath : args) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                fis.close();
                zos.closeEntry();
            }
            deleteZipedFiles(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void deleteZipedFiles(String... args) {
        for (String filePath : args) {
            new File(filePath).delete();
        }
    }
}
