package model;

import controller.DuelController;
import javafx.scene.image.Image;
import view.DuelView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MonsterCard implements Card {
    COMMAND_KNIGHT(4, Attribute.FIRE, MonsterType.WARRIOR, CardType.EFFECT, 1200, 1900,
            "All Warrior-Type monsters you control gain 400 ATK. If you control another monster, monsters your " +
                    "opponent controls cannot target this card for an attack.",
            2100, true, "/images/Cards/CommandKnight.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            MonsterAction.getInstance().addToMonsterAttackPoints(duelController, 400, takeActionCase, owner, targetNumber);
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return MonsterAction.getInstance().canBeAttackedCommandKnight(duelController, monsterNumber);
        }
    },

    BATTLE_OX(4, Attribute.EARTH, MonsterType.BEAST_WARRIOR, CardType.NORMAL, 1700, 1000,
            "A monster with tremendous power, it destroys enemies with a swing of its axe.", 2900, true, "/images/Cards/BattleOx.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    AXE_RAIDER(4, Attribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL, 1700, 1150,
            "An axe-wielding monster of tremendous strength and agility.", 3100, true, "/images/Cards/AxeRaider.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    HORN_IMP(4, Attribute.DARK, MonsterType.FIEND, CardType.NORMAL, 1300, 1000,
            "A small fiend that dwells in the dark, its single horn makes it a formidable opponent.", 2500, true, "/images/Cards/HornImp.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    YOMI_SHIP(3, Attribute.WATER, MonsterType.AQUA, CardType.EFFECT, 800, 1400,
            "If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.",
            1700, true, "/images/Cards/YomiShip.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            MonsterAction.getInstance().destroyAttackerYomiShip(duelController, takeActionCase);
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    SILVER_FANG(3, Attribute.EARTH, MonsterType.BEAST, CardType.NORMAL, 1200, 800,
            "A snow wolf that's beautiful to the eye, but absolutely vicious in battle.", 1700, true, "/images/Cards/SilverFang.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    SUIJIN(7, Attribute.WATER, MonsterType.AQUA, CardType.EFFECT, 2500, 2400,
            "During damage calculation in your opponent's turn, if this card is being attacked: You can target " +
                    "the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). " +
                    "This effect can only be used once while this card is face-up on the field.", 8700, true, "/images/Cards/Suijin.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            MonsterAction.getInstance().makeAttackerAttackPoint0Suijin(duelController, takeActionCase, targetNumber);
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    FIREYAROU(4, Attribute.FIRE, MonsterType.PYRO, CardType.NORMAL, 1300, 1000,
            "A malevolent creature wrapped in flames that attacks enemies with intense fire.", 2500, true, "/images/Cards/Fireyarou.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    CURTAIN_OF_DARK_ONES(2, Attribute.DARK, MonsterType.SPELLCASTER, CardType.NORMAL, 600, 500,
            "A curtain that a spellcaster made, it is said to raise a dark power.", 700, true, "/images/Cards/CurtainOfTheDarkOnes.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    FERAL_IMP(4, Attribute.DARK, MonsterType.FIEND, CardType.NORMAL, 1300, 1400,
            "A playful little fiend that lurks in the dark, waiting to attack an unwary enemy.", 2800, true, "/images/Cards/FeralImp.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    DARK_MAGICIAN(7, Attribute.DARK, MonsterType.SPELLCASTER, CardType.NORMAL, 2500,
            2100, "The ultimate wizard in terms of attack and defense.", 8300, true, "/images/Cards/DarkMagician.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    WATTKID(3, Attribute.LIGHT, MonsterType.THUNDER, CardType.NORMAL, 1000, 500,
            "A creature that electrocutes opponents with bolts of lightning.", 1300, true, "/images/Cards/Wattkid.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    BABY_DRAGON(3, Attribute.WIND, MonsterType.DRAGON, CardType.NORMAL, 1200, 700,
            "Much more than just a child, this dragon is gifted with untapped power.", 1600, true, "/images/Cards/BabyDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    HERO_OF_THE_EAST(3, Attribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL, 1100, 1000,
            "Feel da strength ah dis sword-swinging samurai from da Far East.", 1700, true, "/images/Cards/HeroOfTheEast.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    BATTLE_WARRIOR(3, Attribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL, 700, 1000,
            "A warrior that fights with his bare hands!!!", 1300, true, "/images/Cards/BattleWarrior.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    CRAWLING_DRAGON(5, Attribute.EARTH, MonsterType.DRAGON, CardType.NORMAL, 1600, 1400,
            "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with.", 3900, true, "/images/Cards/CrawlingDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    FLAME_MANIPULATOR(3, Attribute.FIRE, MonsterType.SPELLCASTER, CardType.NORMAL, 900, 1000,
            "This Spellcaster attacks enemies with fire-related spells such as \"Sea of Flames\" and \"Wall of Fire\".",
            1500, true, "/images/Cards/FlameManipulator.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    BLUE_EYES_WHITE_DRAGON(8, Attribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL, 3000, 2500,
            "This legendary dragon is a powerful engine of destruction. Virtually invincible, very few have " +
                    "faced this awesome creature and lived to tell the tale.", 11300, true, "/images/Cards/BlueEyesWhiteDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },
    CRAB_TURTLE(8, Attribute.WATER, MonsterType.AQUA, CardType.RITUAL, 2550, 2500,
            "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Turtle Oath\". You must " +
                    "also offer monsters whose total Level Stars equal 8 or more as a Tribute from the field or your hand.",
            10200, false, "/images/Cards/CrabTurtle.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },
    SKULL_GUARDIAN(7, Attribute.LIGHT, MonsterType.WARRIOR, CardType.RITUAL, 2050, 2500,
            "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Novox's Prayer\". You must " +
                    "also offer monsters whose total Level Stars equal 7 or more as a Tribute from the field or your hand.",
            7900, false, "/images/Cards/SkullGuardian.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    SLOT_MACHINE(7, Attribute.DARK, MonsterType.MACHINE, CardType.NORMAL, 2000, 2300,
            "The machine's ability is said to vary according to its slot results.", 7500, true
            , "/images/Cards/SlotMachine.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    HANIWA(2, Attribute.EARTH, MonsterType.ROCK, CardType.NORMAL, 500, 500,
            "An earthen figure that protects the tomb of an ancient ruler.", 600, true
            , "/images/Cards/Haniwa.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    MAN_EATER_BUG(2, Attribute.EARTH, MonsterType.INSECT, CardType.EFFECT, 450, 600,
            "FLIP: Target 1 monster on the field; destroy that target.", 600, true
            , "/images/Cards/ManEaterBug.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.FLIP_SUMMONED)) {
                if (duelController.getPlayer().getUsername().equals("@AI@"))
                    duelController.handleAITurn();
                else if (duelController.getCountOfMonsterCardsInGround(duelController.getRival()) != 0) {
                    DuelView.printText("select one of opponent's monster cards by number to destroy");
                    String givenNumber = DuelView.scan();
                    int monsterNumber = DuelController.getPlayerGroundNumbers()[Integer.parseInt(givenNumber) - 1] - 1;
                    while (duelController.getRival().getBoard().getMonsterByNumber(monsterNumber) == null) {
                        DuelView.printText("there is no monster in this place. enter another number");
                        givenNumber = DuelView.scan();
                        monsterNumber = DuelController.getPlayerGroundNumbers()[Integer.parseInt(givenNumber) - 1] - 1;
                    }
                    duelController.getRival().getBoard().getMonsterByNumber(monsterNumber).takeAction(duelController, TakeActionCase.REMOVE_FROM_MONSTERZONE, duelController.getRival(), duelController.getSelectedCard().getNumber());
                    duelController.getRival().getBoard().removeMonster(monsterNumber, duelController, duelController.getRival());
                    duelController.getRival().getBoard().putInGraveYard(duelController.getRival().getBoard().getMonsterByNumber(monsterNumber));
                }
            }
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    GATE_GUARDIAN(11, Attribute.DARK, MonsterType.WARRIOR, CardType.EFFECT, 3750, 3400,
            "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your hand) by Tributing 1 " +
                    "\"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".", 20000, false
            , "/images/Cards/GateGuardian.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.SPECIAL_SUMMONED)) {
                if (duelController.getCountOfMonsterCardsInGround(owner) < 3) {
                    DuelView.printText("there is no way you could special summon this monster");
                    return;
                }
                DuelView.printText("select 3 monsters from ground to tribute");
                String input1 = DuelView.scan();
                if (input1.equals("cancel")) return;
                int address1 = Integer.parseInt(input1) - 1;
                address1 = DuelController.getPlayerGroundNumbers()[address1] - 1;
                while (duelController.getPlayer().getBoard().getMonsterByNumber(address1) == null) {
                    DuelView.printText("there is no monster in this address! please enter another number");
                    input1 = DuelView.scan();
                    if (input1.equals("cancel")) return;
                    address1 = Integer.parseInt(input1) - 1;
                    address1 = DuelController.getPlayerGroundNumbers()[address1] - 1;
                }
                String input2 = DuelView.scan();
                if (input2.equals("cancel")) return;
                int address2 = Integer.parseInt(input2) - 1;
                address2 = DuelController.getPlayerGroundNumbers()[address2] - 1;
                while (duelController.getPlayer().getBoard().getMonsterByNumber(address2) == null || address2 == address1) {
                    DuelView.printText("there is no monster in this address! please enter another number");
                    input2 = DuelView.scan();
                    if (input2.equals("cancel")) return;
                    address2 = Integer.parseInt(input2) - 1;
                    address2 = DuelController.getPlayerGroundNumbers()[address2] - 1;
                }
                String input3 = DuelView.scan();
                if (input3.equals("cancel")) return;
                int address3 = Integer.parseInt(input3) - 1;
                address3 = DuelController.getPlayerGroundNumbers()[address3] - 1;
                while (duelController.getPlayer().getBoard().getMonsterByNumber(address3) == null || address3 == address1 || address3 == address2) {
                    DuelView.printText("there is no monster in this address! please enter another number");
                    input3 = DuelView.scan();
                    if (input3.equals("cancel")) return;
                    address3 = Integer.parseInt(input3) - 1;
                    address3 = DuelController.getPlayerGroundNumbers()[address3] - 1;
                }
                duelController.getPlayer().getBoard().removeMonster(address1, duelController, duelController.getPlayer());
                duelController.getPlayer().getBoard().removeMonster(address2, duelController, duelController.getPlayer());
                duelController.getPlayer().getBoard().removeMonster(address3, duelController, duelController.getPlayer());
                duelController.removeMonsterPlayer(address1);
                duelController.removeMonsterPlayer(address2);
                duelController.removeMonsterPlayer(address3);
                DuelView.printText("enter the position you want to summon monster in(attack or defence)");
                String position = DuelView.scan();
                if (position.equals("cancel")) return;
                while (!(position.equals("attack") || position.equals("defence"))) {
                    DuelView.printText("please enter a valid position!");
                    position = DuelView.scan();
                    if (position.equals("cancel")) return;
                }
                switch (position) {
                    case "attack":
                        position = "OO";
                        break;
                    case "defence":
                        position = "DO";
                        break;
                }
                int targetPlace = duelController.getPlayer().getBoard().putMonster((MonsterCard) duelController.getSelectedCard().getCard(), position);
                ((MonsterCard) duelController.getSelectedCard().getCard()).takeAction(duelController, TakeActionCase.SUMMONED, duelController.getPlayer(), targetPlace);
                duelController.getPlayer().getBoard().getCardsInHand().remove((int) duelController.getSelectedCard().getNumber());
                duelController.setSelectedCard(null);
                DuelView.printText("special summoned successfully");
            }
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    SCANNER(1, Attribute.LIGHT, MonsterType.MACHINE, CardType.EFFECT, 0, 0,
            "Once per turn, you can select 1 of your opponent's monsters that is removed from play. Until the" +
                    " End Phase, this card's name is treated as the selected monster's name, and this card has the same " +
                    "Attribute, Level, ATK, and DEF as the selected monster. If this card is removed from the field " +
                    "while this effect is applied, remove it from play.", 8000, true
            , "/images/Cards/Scanner.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    BITRON(2, Attribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL, 200, 2000,
            "A new species found in electronic space. There's not much information on it.", 1000, true
            , "/images/Cards/Bitron.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    MARSHMALLON(3, Attribute.LIGHT, MonsterType.FAIRY, CardType.EFFECT, 300, 500,
            "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was " +
                    "face-down at the start of the Damage Step: The attacking player takes 1000 damage.", 700, true
            , "/images/Cards/Marshmallon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    BEAST_KING_BARBAROS(8, Attribute.EARTH, MonsterType.BEAST_WARRIOR, CardType.EFFECT, 3000, 1200,
            "You can Normal Summon/Set this card without Tributing, but its original ATK becomes 1900. You can " +
                    "Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this way: Destroy all " +
                    "cards your opponent controls.", 9200, true
            , "/images/Cards/BeastKingBarbaros.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    TEXCHANGER(1, Attribute.DARK, MonsterType.CYBERSE, CardType.EFFECT, 100, 100,
            "Once per turn, when your monster is targeted for an attack: You can negate that attack, then " +
                    "Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.", 200, true
            , "/images/Cards/Texchanger.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    LEOTRON(4, Attribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL, 2000, 0,
            "A territorial electronic monster that guards its own domain.", 2500, true
            , "/images/Cards/Leotron.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    THE_CALCULATOR(2, Attribute.LIGHT, MonsterType.THUNDER, CardType.EFFECT, 0, 0,
            "The ATK of this card is the combined Levels of all face-up monsters you control x 300.",
            8000, true, "/images/Cards/TheCalculator.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    ALEXANDRITE_DRAGON(4, Attribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL, 2000, 100,
            "Many of the czars' lost jewels can be found in the scales of this priceless dragon. Its creator " +
                    "remains a mystery, along with how they acquired the imperial treasures. But whosoever finds this" +
                    " dragon has hit the jackpot... whether they know it or not.", 2600, true
            , "/images/Cards/AlexandriteDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    MIRAGE_DRAGON(4, Attribute.LIGHT, MonsterType.DRAGON, CardType.EFFECT, 1600, 600,
            "Your opponent cannot activate Trap Cards during the Battle Phase.", 2500, true
            , "/images/Cards/MirageDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    HERALD_OF_CREATION(4, Attribute.LIGHT, MonsterType.SPELLCASTER, CardType.EFFECT, 1800, 600,
            "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your Graveyard; " +
                    "add that target to your hand.", 2700, true
            , "/images/Cards/HeraldOfCreation.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    EXPLODER_DRAGON(3, Attribute.EARTH, MonsterType.DRAGON, CardType.EFFECT, 1000, 0,
            "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed" +
                    " it. Neither player takes any battle damage from attacks involving this attacking card.",
            1000, true, "/images/Cards/ExploderDragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.DIED_BY_BEING_ATTACKED)) {
                duelController.getPlayer().getBoard().putInGraveYard(duelController.getSelectedCard().getCard());
                duelController.getPlayer().getBoard().removeMonster(duelController.getSelectedCard().getNumber(), duelController, duelController.getRival());
                duelController.removeMonsterPlayer(duelController.getSelectedCard().getNumber());
            }
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    WARRIOR_DAI_GREPHER(4, Attribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL, 1700, 1600,
            "The warrior who can manipulate dragons. Nobody knows his mysterious past.",
            3400, true, "/images/Cards/WarriorDaiGrepher.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    DARK_BLADE(4, Attribute.DARK, MonsterType.WARRIOR, CardType.NORMAL, 1800, 1500,
            "They say he is a dragon-manipulating warrior from the dark world. His attack is tremendous, using " +
                    "his great swords with vicious power.", 3500, true
            , "/images/Cards/DarkBlade.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    WATTAILDRAGON(6, Attribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL, 2500, 1000,
            "Capable of indefinite flight. Attacks by wrapping its body with electricity and ramming into opponents.\n" +
                    "IMPORTANT: Capturing the \"Wattaildragon\" is forbidden by the Ancient Rules and is a Level 6 " +
                    "offense, the minimum sentence for which is imprisonment for no less than 2500 heliocycles.",
            5800, true, "/images/Cards/Wattaildragon.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    TERRATIGER_THE_EMPOWERED_WARRIOR(4, Attribute.EARTH, MonsterType.WARRIOR, CardType.EFFECT, 1800, 1200,
            "When this card is Normal Summoned: You can Special Summon 1 Level 4 or lower Normal Monster from" +
                    " your hand in Defense Position.", 3200, true,
            "/images/Cards/Terratiger.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    THE_TRICKY(5, Attribute.WIND, MonsterType.SPELLCASTER, CardType.EFFECT, 2000, 1200,
            "You can Special Summon this card (from your hand) by discarding 1 card.", 4300, true
            , "/images/Cards/TheTricky.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    },

    SPIRAL_SERPENT(8, Attribute.WATER, MonsterType.SEA_SERPENT, CardType.NORMAL, 2900, 2900,
            "When huge whirlpools lay cities asunder, it is the hunger of this sea serpent at work. No one has" +
                    " ever escaped its dreaded Spiral Wave to accurately describe the terror they experienced.", 11700,
            true, "/images/Cards/SpiralSerpent.jpg") {
        public void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
        }

        public boolean canBeAttacked(DuelController duelController, int monsterNumber) {
            return true;
        }
    };

    private int level;
    private Attribute attribute;
    private MonsterType monsterType;
    private CardType cardType;
    private int attack;
    private int defence;
    private String description;
    private int price;
    private boolean canBeNormalSummoned;
    private Image image;

    MonsterCard(int level, Attribute attribute, MonsterType monsterType, CardType cardType, int attack, int defence, String description, int price, boolean canBeNormalSummoned, String image) {
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        this.cardType = cardType;
        this.attack = attack;
        this.defence = defence;
        this.description = description;
        this.price = price;
        this.canBeNormalSummoned = canBeNormalSummoned;
        this.image = new Image(image);
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean getCanBeNormalSummoned() {
        return this.canBeNormalSummoned;
    }

    public MonsterType getMonsterType() {
        return this.monsterType;
    }

    public String getName() {
        return this.name();
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

    public Image getImage() {
        return this.image;
    }

    public abstract void takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber);

    public abstract boolean canBeAttacked(DuelController duelController, int monsterNumber);

    @Override
    public String toString() {
        String name = this.getNamePascalCase();
        String toReturn = "Name: " + name + "\n" +
                "Level: " + this.level + "\n";
        String monsterType = this.monsterType.getNamePascalCase();
        toReturn = toReturn + "Type: " + monsterType + "\n" +
                "ATK: " + this.attack + "\n" +
                "DEF: " + this.defence + "\n" +
                "Description: " + this.description;
        return toReturn;
    }
}
