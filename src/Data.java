import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Data {
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<String> passwords = new ArrayList<>();
    public static ArrayList<Integer> indexesOfChosen = new ArrayList<>();
    public static ArrayList<Integer> indexesOfImpostors = new ArrayList<>();
    public static ArrayList<String> tasks = new ArrayList<>();
    public static int crewmates = 0;
    public static int impostors = 0;
    public static boolean isThrownOut = false;
    public static int indexOfDetective = 0;
    public static int indexOfDefender = 0;
    public static boolean isDetectiveDead = false;
    public static boolean isChecked = false;
    public static boolean resurectionUsed = false;

    public static void generateData() {
        createPlayers();
        createTasks();
    }

    private static void createPlayers() {
        players.add(new Player("red"));
        players.add(new Player("blue"));
        players.add(new Player("green"));
        players.add(new Player("pink"));
        players.add(new Player("orange"));
        players.add(new Player("yellow"));
        players.add(new Player("black"));
        players.add(new Player("white"));
        players.add(new Player("purple"));
        players.add(new Player("brown"));
        players.add(new Player("cyan"));
        players.add(new Player("lime"));
        players.add(new Player("maroon"));
        players.add(new Player("rose"));
        players.add(new Player("banana"));
        players.add(new Player("gray"));
        players.add(new Player("tan"));
        players.add(new Player("coral"));
        players.add(new Player("fortegreen"));
    }

    private static void createTasks() {
        tasks.add("Wypij szota bez popity.");
        tasks.add("Zadzwon do kogos i zaspiewaj sto lat.");
        tasks.add("Zrob 5 pompek.");
        tasks.add("Wypij cytrynowego szota z poker facem.");
        tasks.add("Zjedz lyzeczke majonezu.");
        tasks.add("Zanuc jakaś piosenke, ktoś musi ja zgadnać.");
        tasks.add("Odwzoruj zdj na insta osoby z twoich relacji.");
        tasks.add("Zrob 15 pajacykow.");
        tasks.add("Zrob 10 przysiadow.");
        tasks.add("Przejdz przez caly pokoj z ksiazka na glowie.");
        tasks.add("Niech kazdy sprzeda ci sledzia.");
        tasks.add("Przytul osobe najblizej ciebie.");
        tasks.add("Nie odzywaj sie do nastepnego zadania.");
        tasks.add("Wyrecytuj czwarta zwrotke hymnu.");
        tasks.add("Wymień wszystkich sasiadów Polski.");
        tasks.add("Nie ruszaj sie poki kazdy nie wykona zadania.");
        tasks.add("Do konca rundy mow tylko po niemiecku.");
        tasks.add("Do konca rundy mow do melodii \"Sto lat\".");
        tasks.add("Niech kazdy chetny cie ozdobi.");
        tasks.add("Beatboxuj do zakonczenia zadan przez innych.");
        tasks.add("Pocaluj swoja stope.");
        tasks.add("Opowiedz kawal.");
        tasks.add("Rob deske poki inni nie skoncza zadan.");
        tasks.add("Trzymaj w ustach kostke lodu poki sie nie roztopi.");
        tasks.add("Pocaluj w czolo osobe najblizej ciebie.");
        tasks.add("Umyj rece osobie najblizej ciebie.");
        tasks.add("Zatancz breakdance.");
        tasks.add("Pocaluj każdego gracza w czolo.");
        tasks.add("Do konca rundy mow jak Dzoana Krupa.");
        tasks.add("Przytul każdego gracza.");
        tasks.add("Wez na barana osobe najblizej ciebie.");
        tasks.add("Chodz na kolanach przez 2 rundy.");
        tasks.add("Stoj na prawej nodze poki wszyscy nie skoncza zadan.");
        tasks.add("Powiedz nazwe swojej miejscowosci od tylu.");
        tasks.add("Wez dlugopis w usta i podpisz sie na kartce.");
        tasks.add("Przeliteruj swoje imie swoim cialem.");
        tasks.add("Zatancz choreografie z gangnam style.");
        tasks.add("Nie uzywaj slowa \"Nie\" do konca rundy.");
        tasks.add("Do konca rundy poruszaj sie w zwolnionym tempie.");
        tasks.add("Tancz do konca rundy.");
        tasks.add("Zatancz kaczuszki.");
        tasks.add("Wypij 2 szoty.");
        tasks.add("Krec niewidzialnym hula-hopem do konca zadan.");
        tasks.add("Chodz na czworaka do konca rundy.");
        tasks.add("Zrób mostek.");
        tasks.add("Wykonaj taska wymyslonego przez reszte grupy.");
        tasks.add("Zatancz ze szczotka.");
        tasks.add("Niech najbliższy gracz napisze do kogo chce z twojego ig.");
        tasks.add("Miej zamkniete oczy do konca rundy.");
        tasks.add("Pokaz innym wszystkie rzeczy ze swojego portfela.");
        tasks.add("Pokaa swoja ostatnia wiadomosc na msg.");
        tasks.add("Wypij szota bez uzycia rak.");
        tasks.add("Do konca rundy uzywaj \"Kurwa\" zamiast kropki.");
        tasks.add("Papuguj do konca rundy osobe najblizej ciebie.");
        tasks.add("Pokaz magiczna sztuczke.");
        tasks.add("Zacytuj dode.");
        tasks.add("Daj sie spoliczkowac osobie najblizej ciebie.");
        tasks.add("Zafreestyluj 8 linijek.");
        tasks.add("Kalambury bez powtorzen: przyslowie.");
        tasks.add("Do konca zadan nasladuj zwierze ktore ktos powie.");
        tasks.add("Kalambury bez powtorzen: jedzenie.");
        tasks.add("Kalambury bez powtorzen: zanuc.");
        tasks.add("Kalambury bez powtorzen: panstwo.");
        tasks.add("Zaspiewaj hymn o osobie najblizej ciebie.");
        tasks.add("Lez na brzuchu na podlodze do konca rundy.");
        tasks.add("Niech ktos dokonczy rozpoczety przez ciebie hit internetu.");
        tasks.add("Powiedz bezblednie \"Stol z powylamywanymi nogami\".");
        tasks.add("Wypij lyzeczke oliwy.");
        tasks.add("Powiedz bez otwierania ust jakies slowo aby ktos zgadnal.");
        tasks.add("Obroc sie 10 razy dookola.");
        tasks.add("Powiedz bezblednie \"Konstantynopolitanczykowianeczka\".");
    }

    public static String randomRTask() {
        return tasks.get(new Random().nextInt(tasks.size()));
    }

    public static ImageIcon resize(ImageIcon image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(//  ww  w  . jav  a2 s. c o m
                new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image.getImage(), 0, 0, width, height, null);
        g2d.dispose();
        return new ImageIcon(bi);
    }

    public static void drawImpostors() {
        int n;
        for (int i=0; i<indexesOfImpostors.size(); i++) {
            while (indexesOfImpostors.contains(indexesOfChosen.get(n = new Random().nextInt(indexesOfChosen.size()))));
            indexesOfImpostors.set(i, indexesOfChosen.get(n));
        }
        Data.impostors = indexesOfImpostors.size();
        Data.crewmates = indexesOfChosen.size() - impostors;
        while (indexesOfImpostors.contains(indexesOfChosen.get(n = new Random().nextInt(indexesOfChosen.size()))));
        indexOfDetective = indexesOfChosen.get(n);
        while (indexesOfImpostors.contains(indexesOfChosen.get(n = new Random().nextInt(indexesOfChosen.size()))) || indexOfDetective == indexesOfChosen.get(n));
        indexOfDefender = indexesOfChosen.get(n);
    }

    public static void serialize() {
        for (Player p : players)
            passwords.add(p.getPassword());
        Gson gson = new Gson();
        String json = gson.toJson(indexesOfChosen);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("chosen.json")))) {
            bw.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json1 = gson.toJson(passwords);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("passwords.json")))) {
            bw.write(json1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deserialize() {
        try { Scanner skaner = new Scanner(new File("chosen.json"));
            String json = skaner.nextLine();
            Type listType = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            indexesOfChosen = new Gson().fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { Scanner skaner = new Scanner(new File("passwords.json"));
            String json = skaner.nextLine();
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            passwords = new Gson().fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0; i<players.size(); i++)
            players.get(i).setPassword(passwords.get(i));
    }

    private static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream(url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void playTasksSound() {
        playSound("tasks1.wav");
    }

    public static void playDefenderSound() {
        playSound("defender1.wav");
    }

    public static void playDetectiveSound() {
        playSound("detective1.wav");
    }

    public static void playImpostorsSound() {
        playSound("impostors1.wav");
    }

    public static void playDeathSound() {
        playSound("death.wav");
    }

    public static void playCrewmatesVictorySound() {
        playSound("crewmates_victory.wav");
    }

    public static void playImpostorsVictorySound() {
        playSound("impostors_victory.wav");
    }

    public static void playSusSound() {
        playSound("sus.wav");
    }

    public static void playEmergencyMeetingSound() {
        playSound("emergency_meeting.wav");
    }

    public static void playVoteOutSound() {
        playSound("vote_out.wav");
    }

    public static void playVotedOutSound() {
        playSound("voted_out.wav");
    }

    public static void playDoorSound() {
        playSound("door.wav");
    }

    public static void playButtonSound() {
        playSound("button1.wav");
    }

    public static void playRegisteredSound() {
        playSound("registered.wav");
    }

    public static void playRemovedSound() {
        playSound("removed.wav");
    }

}