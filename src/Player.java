import javax.swing.*;
import java.util.Objects;

public class Player {
    private String name;
    private ImageIcon icon;
    private ImageIcon icon_dead;
    private String password;

    public Player(String name) {
        this.name = name;
        icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(name + ".png")));
        icon_dead = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(name + "_dead.png")));
        password = "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getIcon_dead() {
        return icon_dead;
    }

    public String getPassword() {
        return password;
    }
}
