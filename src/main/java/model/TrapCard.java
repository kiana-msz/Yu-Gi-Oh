package model;

import javafx.scene.image.Image;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TrapCard implements Card {

    TRAP_HOLE(Icon.NORMAL, "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: " +
            "Target that monster; destroy that target.", Status.UNLIMITED, 2000,"/images/Cards/TrapHole.jpg"),

    MIRROR_FORCE(Icon.NORMAL, "When an opponent's monster declares an attack: Destroy all your opponent's " +
            "Attack Position monsters.", Status.UNLIMITED, 2000,"/images/Cards/MirrorForce.jpg"),

    MAGIC_CYLINDER(Icon.NORMAL, "When an opponent's monster declares an attack: Target the attacking monster; " +
            "negate the attack, and if you do, inflict damage to your opponent equal to its ATK.", Status.UNLIMITED, 2000,
            "/images/Cards/MagicCylinder.jpg"),

    MIND_CRUSH(Icon.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all" +
            " copies of it, otherwise you discard 1 random card.", Status.UNLIMITED, 2000,"/images/Cards/MindCrush.jpg"),

    TORRENTIAL_TRIBUTE(Icon.NORMAL, "When a monster(s) is Summoned: Destroy all monsters on the field.",
            Status.UNLIMITED, 2000,"/images/Cards/TorrentialTribute.jpg"),

    TIME_SEAL(Icon.NORMAL, "Skip the Draw Phase of your opponent's next turn.", Status.LIMITED, 2000,
            "/images/Cards/TimeSeal.jpg"),

    NEGATE_ATTACK(Icon.COUNTER, "When an opponent's monster declares an attack: Target the attacking monster;" +
            " negate the attack, then end the Battle Phase", Status.UNLIMITED, 3000,"/images/Cards/NegateAttack.jpg"),

    SOLEMN_WARNING(Icon.COUNTER, "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster " +
            "effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the" +
            " Summon or activation, and if you do, destroy it.", Status.UNLIMITED, 3000,"/images/Cards/SolemnWarning.jpg"),

    MAGIC_JAMMER(Icon.COUNTER, "When a Spell Card is activated: Discard 1 card; negate the activation, and if" +
            " you do, destroy it.", Status.UNLIMITED, 3000,"/images/Cards/MagicJammer.png"),

    CALL_OF_THE_HAUNTED(Icon.CONTINUOUS, "Activate this card by targeting 1 monster in your GY; Special Summon" +
            " that target in Attack Position. When this card leaves the field, destroy that monster. When that monster" +
            " is destroyed, destroy this card.", Status.UNLIMITED, 3500,"/images/Cards/Call of the Hunted.jpg"),

    VANITYS_EMPTINESS(Icon.CONTINUOUS, "Neither player can Special Summon monsters. If a card is sent from the" +
            " Deck or the field to your Graveyard: Destroy this card.", Status.LIMITED, 3500,"/images/Cards/VanitysEmptiness.jpg"),
    WALL_OF_REVEALING_LIGHT(Icon.CONTINUOUS, "Neither player can Special Summon monsters. If a card is sent" +
            " from the Deck or the field to your Graveyard: Destroy this card.", Status.LIMITED, 3500,"/images/Cards/WallOfRevealingLight.jpg");


    private Icon icon;
    private Status status;
    private String description;
    private int price;
    private Image image;


    TrapCard(Icon icon, String description, Status status, int price,String image) {
        this.icon = icon;
        this.status = status;
        this.description = description;
        this.price = price;
        this.image = new Image(image);
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public Image getImage() {
        return image;
    }

    public String getNamePascalCase() {
        String name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        Pattern pattern = Pattern.compile("_([a-z])[a-z]+");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find())
            name = name.replace("_" + matcher.group(1), "_" + matcher.group(1).toUpperCase());
        name = name.replaceAll("_", " ");
        return name;
    }

    @Override
    public String toString() {
        String name = this.getNamePascalCase();
        String toReturn = "Name: " + name + "\n" +
                "Trap" + "\n";
        String type = this.icon.getNamePascalCase();
        toReturn = toReturn + "Type: " + type + "\n" +
                "Description: " + this.description;
        return toReturn;
    }
}