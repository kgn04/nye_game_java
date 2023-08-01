import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import static javax.swing.SwingConstants.CENTER;


public class GameManager {
    private int indexOfChosen = -1;

    private JFrame frame;

    private JPanel p_background;
    private JLabel l_background;
    private ImageIcon img_background;

    private JLabel l_title;
    private JLabel l_message;

    private ArrayList<JLabel> l_objects;

    private JPasswordField pass;

    public void run() {
        createWindow();
        createLoginWindow();
    }

    private void createWindow() {
        frame = new JFrame("amogus");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setIconImage(new ImageIcon("img/main_icon.png").getImage());

        p_background = new JPanel();
        p_background.setLayout(null);
        p_background.setBackground(Color.BLACK);
        frame.add(p_background);

        l_background = new JLabel();
        img_background = new ImageIcon("img/background1.JPG");
        l_background.setIcon(img_background);
        l_background.setBounds(0,0, 1600, 1000);
    }

    private void createLoginWindow() {
        l_message = new JLabel();
        l_message.setBounds(50,770,1500,50);
        l_message.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_message.setForeground(Color.red);
        p_background.add(l_message);

        l_title = new JLabel("WYBIERZ SWOJ KOLOR          WPISZ HASLO");
        l_title.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_title.setForeground(Color.white);
        l_title.setBounds(130,70,1500,50);
        p_background.add(l_title);

        l_objects = new ArrayList<>();
        for (int i=0; i<Data.players.size(); i++) {
            l_objects.add(new JLabel());
            l_objects.get(i).setIcon(Data.resize(Data.players.get(i).getIcon(), 100, 100));
            int finalI = i;
            l_objects.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent avt) {
                    for (int i=0; i<Data.players.size(); i++)
                        if (!Data.indexesOfChosen.contains(i))
                            l_objects.get(i).setIcon(Data.resize(Data.players.get(i).getIcon(), 100, 100));
                    Data.playButtonSound();
                    l_objects.get(finalI).setIcon(Data.resize(Data.players.get(finalI).getIcon_dead(), 100, 100));
                    indexOfChosen = finalI;
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent avt) {
                    l_message.setText(Data.players.get(finalI).getName());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent avt) {
                    l_message.setText("");
                }
            });
        }
        for (int i=0; i<4; i++)
            for (int j=0; j<5 && i*5+j<Data.players.size(); j++)
                l_objects.get(i*5+j).setBounds(50+j*150, 200+i*150, 100, 100);

        for (int i=0; i<Data.players.size(); i++)
            p_background.add(l_objects.get(i));

        pass = new JPasswordField();
        pass.setFont(new Font("", Font.BOLD, 40));
        pass.setBounds(1000, 140, 300, 50);

        JPasswordField passConfirm = new JPasswordField();
        passConfirm.setFont(new Font("", Font.BOLD, 40));
        passConfirm.setBounds(1000, 280, 300, 50);
        p_background.add(pass);
        p_background.add(passConfirm);

        JLabel l_pass = new JLabel("POTWIERDZ HASLO");
        l_pass.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_pass.setForeground(Color.white);
        l_pass.setBounds(920, 210, 500, 50);
        p_background.add(l_pass);

        JComboBox cb_imp_count = new JComboBox(new String[]{""});
        cb_imp_count.setBounds(950, 625, 70, 50);
        cb_imp_count.setFont(new Font("Monospaced", Font.BOLD, 30));
        ((JLabel)cb_imp_count.getRenderer()).setHorizontalAlignment(CENTER);
        p_background.add(cb_imp_count);

        JButton b_register = new JButton("ZAREJESTRUJ");
        b_register.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_register.setBounds(950, 360, 240, 50);
        p_background.add(b_register);
        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Data.indexesOfChosen.contains(indexOfChosen) && indexOfChosen!=-1 && pass.getText().equals(passConfirm.getText()) && !pass.getText().equals("")) {
                    Data.playButtonSound();
                    Data.indexesOfChosen.add(indexOfChosen);
                    Data.players.get(indexOfChosen).setPassword(new String(pass.getPassword()));
                    l_message.setText(Data.players.get(indexOfChosen).getName() + ": zarejestrowano pomyslnie.");
                    Data.playRegisteredSound();
                    pass.setText("");
                    passConfirm.setText("");
                    int N;
                    if (Data.indexesOfChosen.size()%2 == 0)
                        N = Data.indexesOfChosen.size()/2-1;
                    else N = Data.indexesOfChosen.size()/2;
                    String[] arr = new String[N];
                    for (int i=0; i<N; i++)
                        arr[i] = ""+(i+1);
                    cb_imp_count.setModel(new DefaultComboBoxModel(arr));
                }
                else if (Data.indexesOfChosen.contains(indexOfChosen) || indexOfChosen==-1){
                    l_message.setText("Musisz wybrac kolor!");
                }
                else if (!pass.getText().equals(passConfirm.getText())){
                    l_message.setText("Wpisano rozne hasla! sprobuj ponownie.");
                }
                else {
                    l_message.setText("Haslo nie moze byc puste! sprobuj ponownie.");
                }
            }
        });

        JButton b_delete = new JButton("USUN");
        b_delete.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_delete.setBounds(1200, 360, 150, 50);
        p_background.add(b_delete);
        b_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexOfChosen==-1)
                    l_message.setText("Musisz wybrac kolor!");
                else if (!Data.indexesOfChosen.contains(indexOfChosen))
                    l_message.setText(Data.players.get(indexOfChosen).getName() + " niezarejestrowany.");
                else if (Data.players.get(indexOfChosen).getPassword().equals(new String(pass.getPassword()))) {
                    Data.playButtonSound();
                    Data.indexesOfChosen.remove((Object)(indexOfChosen));
                    l_message.setText(Data.players.get(indexOfChosen).getName() + ": usunieto pomyslnie.");
                    Data.playRemovedSound();
                    pass.setText("");
                    passConfirm.setText("");
                    int N;
                    if (Data.indexesOfChosen.size()%2 == 0)
                        N = Data.indexesOfChosen.size()/2-1;
                    else N = Data.indexesOfChosen.size()/2;
                    String[] arr = new String[N];
                    for (int i=0; i<N; i++)
                        arr[i] = ""+(i+1);
                    cb_imp_count.setModel(new DefaultComboBoxModel(arr));
                    l_objects.get(indexOfChosen).setIcon(Data.resize(Data.players.get(indexOfChosen).getIcon(), 100, 100));

                }
                else {
                    l_message.setText("Haslo niepoprawne.");
                }
            }
        });

        JButton b_previous = new JButton("Poprzedni gracze");
        b_previous.setFont(new Font("Monospaced", Font.BOLD, 20));
        b_previous.setBounds(1000, 500, 300, 50);
        p_background.add(b_previous);
        b_previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.deserialize();
                Data.playButtonSound();
                for (int i=0; i<Data.players.size(); i++)
                    if (Data.indexesOfChosen.contains(i))
                        l_objects.get(i).setIcon(Data.resize(Data.players.get(i).getIcon_dead(), 100, 100));
                    else
                        l_objects.get(i).setIcon(Data.resize(Data.players.get(i).getIcon(), 100, 100));
                l_message.setText("Gracze przywroceni.");
                int N;
                if (Data.indexesOfChosen.size()%2 == 0)
                    N = Data.indexesOfChosen.size()/2-1;
                else N = Data.indexesOfChosen.size()/2;
                String[] arr = new String[N];
                for (int i=0; i<N; i++)
                    arr[i] = ""+(i+1);
                cb_imp_count.setModel(new DefaultComboBoxModel(arr));

            }
        });

        JLabel l_imp_count = new JLabel("IMPOSTOROW");
        l_imp_count.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_imp_count.setForeground(Color.white);
        l_imp_count.setBounds(1050, 625, 500, 50);
        p_background.add(l_imp_count);

        JButton allLogged = new JButton("Wszyscy zarejestrowani");
        allLogged.setFont(new Font("Monospaced", Font.BOLD, 20));
        allLogged.setBounds(1000, 700, 300, 50);
        p_background.add(allLogged);
        allLogged.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Data.indexesOfChosen.size()<5)
                    l_message.setText("Minimalna liczba geaczy: 5.");
                else {
                    for (int i=0; i<cb_imp_count.getSelectedIndex()+1; i++)
                        Data.indexesOfImpostors.add(-1);
                    Data.drawImpostors();
                    p_background.remove(cb_imp_count);
                    p_background.remove(l_imp_count);
                    p_background.remove(l_pass);
                    p_background.remove(passConfirm);
                    p_background.remove(b_register);
                    p_background.remove(b_delete);
                    p_background.remove(b_previous);
                    p_background.remove(allLogged);
                    Data.serialize();
                    Data.playDoorSound();
                    createCheckWindow();
                }
            }
        });

        p_background.add(l_background);
        frame.setVisible(true);
    }

    private void createCheckWindow() {
        //System.out.println(Data.indexesOfChosen);
        //System.out.println(Data.indexOfDefender);
        //System.out.println(Data.indexOfDetective);
        p_background.remove(l_background);

        for (int i=Data.players.size()-1; i>=0; i--) {
            p_background.remove(l_objects.get(i));
            l_objects.remove(i);
        }

        l_objects = new ArrayList<>();
        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            int finalI = Data.indexesOfChosen.get(i);
            int finalII = i;
            l_objects.add(new JLabel());
            l_objects.get(i).setIcon(Data.resize(Data.players.get(finalI).getIcon(), 100, 100));
            l_objects.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent avt) {
                    for (int i=0; i<Data.indexesOfChosen.size(); i++)
                            l_objects.get(i).setIcon(Data.resize(Data.players.get(Data.indexesOfChosen.get(i)).getIcon(), 100, 100));
                    Data.playButtonSound();
                    l_objects.get(finalII).setIcon(Data.resize(Data.players.get(finalI).getIcon_dead(), 100, 100));
                    indexOfChosen = finalI;
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent avt) {
                    l_message.setText(Data.players.get(finalI).getName());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent avt) {
                    l_message.setText("");
                }
            });
        }
        for (int i=0; i<4; i++)
            for (int j=0; j<5 && i*5+j<Data.indexesOfChosen.size(); j++)
                l_objects.get(i*5+j).setBounds(50+j*150, 200+i*150, 100, 100);

        for (int i=0; i<Data.indexesOfChosen.size(); i++)
            p_background.add(l_objects.get(i));

        JLabel l_role = new JLabel("");
        l_role.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_role.setBounds(950, 350, 400, 50);
        l_role.setHorizontalAlignment(CENTER);
        p_background.add(l_role);

        JButton b_role = new JButton("SPRAWDZ ROLE");
        b_role.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_role.setBounds(1000, 250, 300, 50);
        p_background.add(b_role);
        b_role.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexOfChosen == -1)
                    l_message.setText("Musisz wybrac kolor!");
                else if (Data.players.get(indexOfChosen).getPassword().equals(new String(pass.getPassword()))) {
                    Data.playSusSound();
                    l_message.setText("");
                     if (Data.indexesOfImpostors.contains(indexOfChosen)) {
                        l_role.setForeground(Color.red);
                        l_role.setText("IMPOSTOR");
                        l_role.updateUI();
                    } else if (indexOfChosen == Data.indexOfDetective) {
                        l_role.setForeground(Color.yellow);
                        l_role.setText("DETEKTYW");
                        l_role.updateUI();
                    } else if (indexOfChosen == Data.indexOfDefender) {
                         l_role.setForeground(Color.green);
                         l_role.setText("OBRONCA");
                         l_role.updateUI();
                     }
                    else {
                        l_role.setForeground(Color.white);
                        l_role.setText("NORMIK");
                    }
                    pass.setText("");
                    Timer timer = new Timer(2000, new ActionListener(){
                        public void actionPerformed(ActionEvent event){
                            l_role.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else
                    l_message.setText("Haslo niepoprawne.");
                        pass.setText("");
            }
        });

        JButton b_start = new JButton("Zacznij gre");
        b_start.setFont(new Font("Monospaced", Font.BOLD, 20));
        b_start.setBounds(1000, 700, 300, 50);
        p_background.add(b_start);
        b_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_message.setText("");
                l_title.setText("");
                p_background.remove(pass);
                for (JLabel l : l_objects)
                    p_background.remove(l);
                p_background.remove(l_role);
                p_background.remove(b_role);
                p_background.remove(b_start);
                Data.playDoorSound();
                createGameWindow();
            }
        });

        p_background.add(l_background);
        p_background.updateUI();
    }



private ArrayList<PlayerPanel> p_players = new ArrayList<>();
private int deaths = 0;
private boolean isTheEnd = false;

    private void displayTasksMessage() {
        p_background.remove(l_background);
        JLabel l_info = new JLabel("ZADANIA ZA");
        l_info.setFont(new Font("Monospaced", Font.BOLD, 120));
        l_info.setForeground(Color.white);
        l_info.setHorizontalAlignment(CENTER);
        l_info.setBounds(0,150, 1530, 300);
        p_background.add(l_info);

        JLabel l_counter = new JLabel();
        l_counter.setFont(new Font("Monospaced", Font.BOLD, 150));
        l_counter.setBounds(115,400, 1300, 200);
        l_counter.setForeground(Color.white);
        l_counter.setHorizontalAlignment(CENTER);
        p_background.add(l_counter);
        for (int i=0; i<10; i++) {
            int finalI = i;
            Timer t = new Timer(1000*i, new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    l_counter.setText(""+(10-finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }
        p_background.add(l_background);
        p_background.updateUI();
        Timer t = new Timer(10000, new ActionListener(){
            public void actionPerformed(ActionEvent event){
                p_background.remove(l_info);
                p_background.remove(l_counter);
                createGameWindow();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void createGameWindow() {
        p_background.remove(l_background);
        Data.playTasksSound();
        if (p_players.size() == 0)
            for (int i=0; i<Data.indexesOfChosen.size(); i++) {
                p_players.add(new PlayerPanel(Data.indexesOfChosen.get(i)));
                p_players.get(i).getPanel().setBounds(140 + 650 * (i % 2), 50 + 120 * (i / 2), 600, 100);
            }

        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            p_players.get(i).refresh();
            p_background.add(p_players.get(i).getPanel());
        }

        JLabel l_crewmates = new JLabel("ZALOGA: " + Data.crewmates);
        l_crewmates.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_crewmates.setForeground(Color.green);
        l_crewmates.setHorizontalAlignment(CENTER);
        l_crewmates.setBounds(0, 780, 615, 50);
        p_background.add(l_crewmates);

        JLabel l_impostors = new JLabel("IMPOSTORZY: " + Data.impostors);
        l_impostors.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_impostors.setForeground(Color.red);
        l_impostors.setHorizontalAlignment(CENTER);
        l_impostors.setBounds(915, 780, 615, 50);
        p_background.add(l_impostors);

        JButton b_endTasks = new JButton("KONIEC ZADAN");
        b_endTasks.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_endTasks.setBounds(615, 780, 300, 50);
        b_endTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_background.remove(b_endTasks);
                p_background.remove(l_crewmates);
                p_background.remove(l_impostors);
                for (int i=0; i<p_players.size(); i++) {
                    p_background.remove(p_players.get(i).getPanel());
                    if (!p_players.get(i).isTaskDone()) {
                        deaths++;
                        p_players.get(i).kill();
                        playersDeath(p_players.get(i));
                    } else if (!p_players.get(i).isDead())
                        p_players.get(i).getTask().setForeground(Color.white);
                }
                Timer t = new Timer((deaths*10000), new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        checkIfTheEnd();
                        if (!isTheEnd) {
                            Data.playEmergencyMeetingSound();
                            createThrowOutWindow();
                        }
                    }
                });
                t.setRepeats(false);
                t.start();
                deaths = 0;
            }
        });
        p_background.add(b_endTasks);

        p_background.add(l_background);
        p_background.updateUI();
    }

    private void createThrowOutWindow() {
        p_background.remove(l_background);

        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            if (!p_players.get(i).isDead())
                p_players.get(i).throwOutPanel();
            p_background.add(p_players.get(i).getPanel());
        }

        JLabel l_crewmates = new JLabel("NARADA ZALOGI");
        l_crewmates.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_crewmates.setForeground(Color.green);
        l_crewmates.setHorizontalAlignment(CENTER);
        l_crewmates.setBounds(0, 780, 615, 50);
        p_background.add(l_crewmates);

        JButton b_continue = new JButton("DALEJ");
        b_continue.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_continue.setBounds(615, 780, 300, 50);
        b_continue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.isThrownOut = false;
                p_background.remove(b_continue);
                p_background.remove(l_crewmates);
                for (int i=0; i<p_players.size(); i++) {
                    if ((p_players.get(i).gotThrownOut() || p_players.get(i).shouldBeKilled()) && !p_players.get(i).isDead()) {
                        deaths++;
                        p_players.get(i).taskPanel();
                        p_players.get(i).kill();
                        playersDeath(p_players.get(i));
                    }
                    if (!p_players.get(i).isDead())
                        p_players.get(i).taskPanel();
                    p_background.remove(p_players.get(i).getPanel());
                }
                Timer t = new Timer((deaths*10000), new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        checkIfTheEnd();
                        if (!isTheEnd) {
                            Data.playDoorSound();
                            if (!Data.isDetectiveDead) {
                                displayDetectiveMessage();
                            } else displayImpostorsMessage();
                        }
                    }
                });
                t.setRepeats(false);
                t.start();
                deaths = 0;
            }
        });
        p_background.add(b_continue);

        p_background.add(l_background);
        p_background.updateUI();
    }

    private void playersDeath(PlayerPanel pan) {
        JLabel l_icon = new JLabel();
        JLabel l_role = new JLabel();
        Timer t0 = new Timer(((deaths-1)*10000), new ActionListener(){
            public void actionPerformed(ActionEvent event){
                Data.playDeathSound();
                p_background.remove(l_background);

                l_icon.setIcon(Data.resize(pan.getIcon(), 300, 300));
                l_icon.setBounds(615,100, 300, 300);
                p_background.add(l_icon);

                l_role.setFont(new Font("Monospaced", Font.BOLD, 200));
                l_role.setBounds(115,500, 1300, 200);
                l_role.setHorizontalAlignment(CENTER);
                p_background.add(l_role);

                p_background.add(l_background);
                p_background.updateUI();

            }
        });
        t0.setRepeats(false);
        t0.start();

        Timer t1 = new Timer(1000 + ((deaths-1)*10000), new ActionListener(){
            public void actionPerformed(ActionEvent event){
                l_icon.setIcon(Data.resize(pan.getIcon_dead(), 300, 300));
            }
        });
        t1.setRepeats(false);
        t1.start();

        Timer t2 = new Timer(4000 + ((deaths-1)*10000), new ActionListener(){
            public void actionPerformed(ActionEvent event){
                Data.playVotedOutSound();
                for (int i=0; i<pan.getRole().length(); i++) {
                    int finalI = i;
                    Timer t = new Timer(100*i, new ActionListener(){
                        public void actionPerformed(ActionEvent event){
                            l_role.setText(l_role.getText()+pan.getRole().charAt(finalI));
                        }
                    });
                    t.setRepeats(false);
                    t.start();
                }
                l_role.setForeground(pan.getRole_color());
            }
        });
        t2.setRepeats(false);
        t2.start();

        Timer t3 = new Timer(9000 + ((deaths-1)*10000), new ActionListener(){
            public void actionPerformed(ActionEvent event){
                p_background.remove(l_icon);
                p_background.remove(l_role);
                p_background.updateUI();
            }
        });
        t3.setRepeats(false);
        t3.start();
    }

    private void checkIfTheEnd() {
        if (Data.impostors >= Data.crewmates)
            theEndWindow(true);
        else if (Data.impostors == 0)
            theEndWindow(false);
    }

    private void displayDetectiveMessage() {
        p_background.remove(l_background);
        JLabel l_info = new JLabel("SLEDZTWO DETEKTYWA ZA");
        l_info.setFont(new Font("Monospaced", Font.BOLD, 120));
        l_info.setForeground(Color.yellow);
        l_info.setHorizontalAlignment(CENTER);
        l_info.setBounds(0,150, 1530, 300);
        p_background.add(l_info);

        JLabel l_counter = new JLabel();
        l_counter.setFont(new Font("Monospaced", Font.BOLD, 150));
        l_counter.setBounds(115,400, 1300, 200);
        l_counter.setForeground(Color.white);
        l_counter.setHorizontalAlignment(CENTER);
        p_background.add(l_counter);
        for (int i=0; i<5; i++) {
            int finalI = i;
            Timer t = new Timer(1000*i, new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    l_counter.setText(""+(5-finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }
        p_background.add(l_background);
        p_background.updateUI();
        Timer t = new Timer(5000, new ActionListener(){
            public void actionPerformed(ActionEvent event){
                p_background.remove(l_info);
                p_background.remove(l_counter);
                Data.playDetectiveSound();
                createDetectiveWindow();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void createDetectiveWindow() {
        p_background.remove(l_background);

        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            if (!p_players.get(i).isDead())
                p_players.get(i).detectivePanel();
            p_background.add(p_players.get(i).getPanel());
        }

        JLabel l_impostors = new JLabel("SLEDZTWO DETEKTYWA");
        l_impostors.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_impostors.setForeground(Color.yellow);
        l_impostors.setHorizontalAlignment(CENTER);
        l_impostors.setBounds(915, 780, 615, 50);
        p_background.add(l_impostors);

        JButton b_continue = new JButton("DALEJ");
        b_continue.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_continue.setBounds(615, 780, 300, 50);
        b_continue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_background.remove(b_continue);
                p_background.remove(l_impostors);
                for (int i=0; i<p_players.size(); i++) {
                    if (p_players.get(i).gotThrownOut() && !p_players.get(i).isDead()) {
                        deaths++;
                        p_players.get(i).taskPanel();
                        p_players.get(i).kill();
                        playersDeath(p_players.get(i));
                    }
                    if (!p_players.get(i).isDead())
                        p_players.get(i).taskPanel();
                    p_background.remove(p_players.get(i).getPanel());
                }
                Timer t = new Timer((deaths*10000), new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        checkIfTheEnd();
                        if (!isTheEnd)
                            Data.isChecked = false;
                            Data.playDoorSound();
                            displayImpostorsMessage();
                    }
                });
                t.setRepeats(false);
                t.start();
                deaths = 0;
            }
        });
        p_background.add(b_continue);

        p_background.add(l_background);
        p_background.updateUI();
    }

    private void displayImpostorsMessage() {
        p_background.remove(l_background);
        JLabel l_info = new JLabel("NARADA IMPOSTOROW ZA");
        l_info.setFont(new Font("Monospaced", Font.BOLD, 120));
        l_info.setForeground(Color.red);
        l_info.setHorizontalAlignment(CENTER);
        l_info.setBounds(0,150, 1530, 300);
        p_background.add(l_info);

        JLabel l_counter = new JLabel();
        l_counter.setFont(new Font("Monospaced", Font.BOLD, 150));
        l_counter.setBounds(115,400, 1300, 200);
        l_counter.setForeground(Color.white);
        l_counter.setHorizontalAlignment(CENTER);
        p_background.add(l_counter);
        for (int i=0; i<10; i++) {
            int finalI = i;
            Timer t = new Timer(1000*i, new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    l_counter.setText(""+(10-finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }
        p_background.add(l_background);
        p_background.updateUI();
        Timer t = new Timer(10000, new ActionListener(){
            public void actionPerformed(ActionEvent event){
                p_background.remove(l_info);
                p_background.remove(l_counter);
                Data.playImpostorsSound();
                createImpostorsWindow();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void createImpostorsWindow() {
        p_background.remove(l_background);

        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            if (!p_players.get(i).isDead())
                p_players.get(i).impostorPanel();
            p_background.add(p_players.get(i).getPanel());
        }

        JLabel l_impostors = new JLabel("NARADA IMPOSTOROW");
        l_impostors.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_impostors.setForeground(Color.red);
        l_impostors.setHorizontalAlignment(CENTER);
        l_impostors.setBounds(915, 780, 615, 50);
        p_background.add(l_impostors);

        JButton b_continue = new JButton("DALEJ");
        b_continue.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_continue.setBounds(615, 780, 300, 50);
        b_continue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.isThrownOut = false;
                p_background.remove(b_continue);
                p_background.remove(l_impostors);
                for (int i=0; i<p_players.size(); i++) {
                    if (p_players.get(i).gotThrownOut() && !p_players.get(i).isDead()) {
                        deaths++;
                        p_players.get(i).taskPanel();
                        p_players.get(i).kill();
                        playersDeath(p_players.get(i));
                    }
                    if (!p_players.get(i).isDead())
                        p_players.get(i).taskPanel();
                    p_background.remove(p_players.get(i).getPanel());
                }
                Timer t = new Timer((deaths*10000), new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        checkIfTheEnd();
                        if (!isTheEnd) {
                            Data.playDoorSound();
                            if (!Data.resurectionUsed)
                                displayDefenderMessage();
                            else displayTasksMessage();
                        }
                    }
                });
                t.setRepeats(false);
                t.start();
                deaths = 0;
            }
        });
        p_background.add(b_continue);

        p_background.add(l_background);
        p_background.updateUI();
    }

    private void displayDefenderMessage() {
        p_background.remove(l_background);
        JLabel l_info = new JLabel("DECYZJA OBRONCY ZA");
        l_info.setFont(new Font("Monospaced", Font.BOLD, 120));
        l_info.setForeground(Color.green);
        l_info.setHorizontalAlignment(CENTER);
        l_info.setBounds(0,150, 1530, 300);
        p_background.add(l_info);

        JLabel l_counter = new JLabel();
        l_counter.setFont(new Font("Monospaced", Font.BOLD, 150));
        l_counter.setBounds(115,400, 1300, 200);
        l_counter.setForeground(Color.white);
        l_counter.setHorizontalAlignment(CENTER);
        p_background.add(l_counter);
        for (int i=0; i<10; i++) {
            int finalI = i;
            Timer t = new Timer(1000*i, new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    l_counter.setText(""+(10-finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }
        p_background.add(l_background);
        p_background.updateUI();
        Timer t = new Timer(10000, new ActionListener(){
            public void actionPerformed(ActionEvent event){
                p_background.remove(l_info);
                p_background.remove(l_counter);
                Data.playDefenderSound();
                createDefenderWindow();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void createDefenderWindow() {
        p_background.remove(l_background);

        for (int i=0; i<Data.indexesOfChosen.size(); i++) {
            if (!p_players.get(i).isDead())
                p_players.get(i).defenderPanel();
            p_background.add(p_players.get(i).getPanel());
        }

        JLabel l_impostors = new JLabel("DECYZJA OBRONCY");
        l_impostors.setFont(new Font("Monospaced", Font.BOLD, 50));
        l_impostors.setForeground(Color.red);
        l_impostors.setHorizontalAlignment(CENTER);
        l_impostors.setBounds(915, 780, 615, 50);
        p_background.add(l_impostors);

        JButton b_continue = new JButton("DALEJ");
        b_continue.setFont(new Font("Monospaced", Font.BOLD, 30));
        b_continue.setBounds(615, 780, 300, 50);
        b_continue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_background.remove(b_continue);
                p_background.remove(l_impostors);
                for (int i=0; i<p_players.size(); i++) {
                    if (p_players.get(i).shouldBeKilled() && !p_players.get(i).isDead()) {
                        deaths++;
                        p_players.get(i).taskPanel();
                        p_players.get(i).kill();
                        playersDeath(p_players.get(i));
                    }
                    if (!p_players.get(i).isDead())
                        p_players.get(i).taskPanel();
                    p_background.remove(p_players.get(i).getPanel());
                }
                Timer t = new Timer((deaths*10000), new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        checkIfTheEnd();
                        if (!isTheEnd) {
                            Data.playDoorSound();
                            displayTasksMessage();
                        }

                    }
                });
                t.setRepeats(false);
                t.start();
                deaths = 0;
            }
        });
        p_background.add(b_continue);

        p_background.add(l_background);
        p_background.updateUI();
    }
    
    private void theEndWindow(boolean impostorsWon) {
        String winners;
        Color color;
        if (impostorsWon) {
            winners = "IMPOSTORZY";
            color = Color.RED;
            Data.playImpostorsVictorySound();
        } else {
            winners = "ZALOGA";
            color = Color.GREEN;
            Data.playCrewmatesVictorySound();
        }
        p_background.remove(l_background);

        JLabel l_winners = new JLabel("ZWYCIEZCY");
        l_winners.setFont(new Font("Monospaced", Font.BOLD, 100));
        l_winners.setForeground(Color.white);
        l_winners.setHorizontalAlignment(CENTER);
        l_winners.setBounds(115,150, 1300, 300);
        p_background.add(l_winners);

        JLabel l_team = new JLabel();
        l_team.setFont(new Font("Monospaced", Font.BOLD, 200));
        l_team.setBounds(115,400, 1300, 200);
        l_team.setForeground(color);
        l_team.setHorizontalAlignment(CENTER);
        p_background.add(l_team);
        for (int i=0; i<winners.length(); i++) {
            int finalI = i;
            Timer t = new Timer(1000 + 100*i, new ActionListener(){
                public void actionPerformed(ActionEvent event){
                   l_team.setText(l_team.getText()+winners.charAt(finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }

        p_background.add(l_background);
        p_background.updateUI();
        isTheEnd = true;
    }
}