import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        firstMethod(createFile("A", 50));
        secondMethod(new String[]{"B", "C", "D", "E", "F"}, 100);
        thirdMethod();
    }

    private static File createFile(String character, int size){
        File file = new File("files/file" + character + ".txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < size; i++) {
                bw.write(character);
            }
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    private static void firstMethod(File file){
        int size = 50;
        byte[] arr = new byte[size];
        InputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(file));
            while (in.read(arr, 0, size) != -1) {
                for (int i = 0; i < size; i++) {
                    System.out.print(arr[i]);
                }
            }
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e){}
        }
    }

    private static void secondMethod(String[] letters, int size){
        SequenceInputStream seq = null;
        FileOutputStream out = null;

        try {
            ArrayList<FileInputStream> al = new ArrayList<>();
            for (String let : letters) {
                al.add(new FileInputStream(createFile(let, size)));
            }

            Enumeration<FileInputStream> e = Collections.enumeration(al);
            seq = new SequenceInputStream(e);

            out = new FileOutputStream(new File("files/fileSum" + letters[0] + ".txt"));
            int rb = seq.read();
            while (rb != -1){
                out.write(rb);
                rb = seq.read();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {seq.close();} catch (IOException e) {}
            try {out.close();} catch (IOException e) {}
        }
    }

    private static void thirdMethod(){
//        secondMethod(new String[]{"G", "H", "I", "K", "L"}, 2048000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner in = new Scanner(System.in);
                while (true){
                    String string = in.nextLine();
                    readFile(Integer.valueOf(string));
                }
            }
        }).start();
    }

    private static void readFile(int pageNum){
        try (RandomAccessFile raf = new RandomAccessFile("files/fileSumG.txt", "r")){
            int pageSize = 1800;

            if (pageNum > (raf.length()/1800))
                System.out.println("There is no such page");
            else {
                raf.seek(pageNum * pageSize);
                byte[] array = new byte[pageSize];
                raf.read(array);
                for (byte b : array) {
                    System.out.print((char) b);
                }
                System.out.println();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
