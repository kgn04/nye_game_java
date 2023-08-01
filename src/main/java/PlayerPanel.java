import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;

public class PlayerPanel {
    final private JPanel panel;
    final private JLabel l_background;
    final private JLabel l_icon;
    final private ImageIcon icon;
    final private ImageIcon icon_dead;
    final private JLabel task;
    final private JButton OK;
    private String role;
    private Color role_color;
    private boolean isDead;
    final private JButton b_throw_out;
    private boolean gotThrownOut;
    final private JButton b_check;
    private boolean shouldBeKilled;
    final private JButton b_save;

    public PlayerPanel(int i) {
        isDead = false;
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null);
        l_icon = new JLabel();
        icon = Data.players.get(i).getIcon();
        icon_dead = Data.players.get(i).getIcon_dead();
        l_icon.setIcon(Data.resize(icon, 80, 80));
        l_icon.setBounds(10,10, 80, 80);
        panel.add(l_icon);
        l_background = new JLabel();
        l_background.setIcon(Data.resize(new ImageIcon("img/nameplate.png"), 600, 100));
        l_background.setBounds(0,0,600,100);
        OK = new JButton("OK");
        panel.add(OK);
        OK.setFont(new Font("Monospaced", Font.BOLD, 20));
        OK.setBorder(null);
        OK.setBounds(525, 25, 50, 50);
        task = new JLabel("<html>"+Data.randomRTask()+"</html>");
        task.setFont(new Font("Monospaced", Font.BOLD, 20));
        task.setBounds(110, 5, 400, 80);
        task.setForeground(Color.white);
        panel.add(task);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.playButtonSound();
                if (task.getForeground().equals(Color.white))
                    task.setForeground(Color.green);
                else task.setForeground(Color.white);
            }
        });
        panel.add(l_background);
        findRole(i);

        gotThrownOut = false;

        b_throw_out = new JButton("WYRZUC");
        b_throw_out.setFont(new Font("Monospaced", Font.BOLD, 20));
        b_throw_out.setBorder(null);
        b_throw_out.setBounds(250, 25, 180, 50);
        b_throw_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Data.isThrownOut) return;
                Data.isThrownOut = true;
                Data.playVoteOutSound();
                l_icon.setIcon(Data.resize(icon_dead, 80, 80));
                panel.remove(b_throw_out);
                panel.updateUI();
                if (Data.resurectionUsed || role.equals("OBRONCA"))
                    gotThrownOut = true;
                else shouldBeKilled = true;
            }
        });

        b_check = new JButton("SPRAWDZ");
        b_check.setFont(new Font("Monospaced", Font.BOLD, 20));
        b_check.setBorder(null);
        b_check.setBounds(250, 25, 180, 50);
        b_check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Data.isChecked) return;
                Data.isChecked = true;
                panel.remove(l_background);
                panel.remove(b_check);
                task.setText(role);
                task.setForeground(role_color);
                task.setFont(new Font("Monospaced", Font.BOLD, 40));
                task.setHorizontalAlignment(CENTER);
                panel.add(task);
                panel.add(l_background);
                panel.updateUI();
                Data.playSusSound();
            }
        });

        shouldBeKilled = false;

        b_save = new JButton("URATUJ");
        b_save.setFont(new Font("Monospaced", Font.BOLD, 20));
        b_save.setBorder(null);
        b_save.setBounds(250, 25, 180, 50);
        b_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_icon.setIcon(Data.resize(icon, 80, 80));
                panel.remove(b_save);
                panel.updateUI();
                Data.resurectionUsed = true;
                shouldBeKilled = false;
            }
        });
    }

    private void findRole(int i) {
        if (Data.indexesOfImpostors.contains(i)) {
            role = "IMPOSTOR";
            role_color = Color.red;
        } else if (Data.indexOfDetective == i) {
            role = "DETEKTYW";
            role_color = Color.yellow;
        } else if (Data.indexOfDefender == i) {
            role = "OBRONCA";
            role_color = Color.green;
        } else {
            role = "NORMIK";
            role_color = Color.white;
        }
    }

    public void refresh() {
        if (isDead) return;
        task.setText("<html>"+Data.randomRTask()+"</html>");
        task.setFont(new Font("Monospaced", Font.BOLD, 20));
        task.setHorizontalAlignment(LEFT);
        task.setBounds(110, 5, 400, 80);
        task.setForeground(Color.white);
        panel.remove(b_throw_out);
        panel.remove(b_check);
    }

    public boolean isTaskDone() {
        return task.getForeground().equals(Color.green) || isDead;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void kill() {
        isDead = true;
        if (role.equals("IMPOSTOR"))
            Data.impostors--;
        else {
            Data.crewmates--;
            if (role.equals("DETEKTYW"))
                Data.isDetectiveDead = true;
            else if (role.equals("OBRONCA"))
                Data.resurectionUsed = true;
        }
        l_icon.setIcon(Data.resize(icon_dead, 80, 80));
        task.setText(role);
        task.setForeground(role_color);
        task.setFont(new Font("Monospaced", Font.BOLD, 40));
        task.setHorizontalAlignment(CENTER);
        panel.remove(OK);
        panel.remove(b_save);
        panel.updateUI();
    }

    public void throwOutPanel() {
        panel.remove(l_background);
        panel.remove(task);
        panel.remove(OK);
        panel.add(b_throw_out);
        panel.add(l_background);
        panel.updateUI();
    }

    public void taskPanel() {
        panel.remove(l_background);
        panel.add(task);
        panel.add(OK);
        panel.remove(b_throw_out);
        panel.add(l_background);
        panel.updateUI();
    }

    public void impostorPanel() {
        panel.remove(b_check);
        if (role.equals("IMPOSTOR")) {
            panel.remove(l_background);
            panel.remove(b_throw_out);
            panel.remove(task);
            panel.remove(OK);
            panel.add(l_background);
            panel.updateUI();
        } else
            throwOutPanel();
    }

    public void detectivePanel() {
        if (role.equals("DETEKTYW")) {
            panel.remove(l_background);
            panel.remove(b_check);
            panel.remove(task);
            panel.remove(OK);
            panel.add(l_background);
            panel.updateUI();
        } else
            checkPanel();
    }

    public void checkPanel() {
        panel.remove(l_background);
        panel.remove(task);
        panel.remove(OK);
        panel.add(b_check);
        panel.add(l_background);
        panel.updateUI();
    }

    public void defenderPanel() {
        panel.remove(l_background);
        panel.remove(task);
        panel.remove(OK);
        if (shouldBeKilled)
            panel.add(b_save);
        panel.add(l_background);
        panel.updateUI();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getIcon_dead() {
        return icon_dead;
    }

    public String getRole() {
        return role;
    }

    public Color getRole_color() {
        return role_color;
    }

    public JLabel getTask() {
        return task;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean gotThrownOut() {
        return gotThrownOut;
    }

    public boolean shouldBeKilled() {
        return shouldBeKilled;
    }
}
