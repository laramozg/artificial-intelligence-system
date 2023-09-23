
import method.*;
import org.projog.api.Projog;
import org.projog.core.ProjogException;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        String text =
                "\nМеня зовут Анна, я хочу иметь уровень больше n, иметь расу people и класс warrior" +
                "\nКакие сокровища дают больше n бонусов и могут принадлежать расе halfling?" +
                "\nКакие сокровища может получить maria?" +
                "\nВыведи список участников расы people или класса cleric" +
                "\nМожет ли ivan одержать победу над монстром orc - без/с бонус(ов,ами)?";

        String[] patterns = {
                "Меня зовут (.+), я хочу иметь уровень больше (.+), иметь расу (.+) и класс (.+)",
                "Какие сокровища дают больше (.+) бонусов и могут принадлежать расе (.+)\\?",
                "Какие сокровища может получить (.+)\\?",
                "Выведи список участников расы (.+) или класса (.+)",
                "Может ли (.+) одержать победу над монстром (.+) - (.+) бонус\\(ов,ами\\)\\?"
        };


        Scanner in = new Scanner(System.in);
        Projog projog = new Projog();
        projog.consultFile(new File("src/main/resources/lab1.pl"));
        System.out.println("Шаблоны для ввода данных:"+text);
        while (true) {
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            boolean isMatched = false;

            for (int i = 0; i < patterns.length; i++) {
                Pattern pattern = Pattern.compile(patterns[i], Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);if (matcher.matches()) {
                    isMatched = true;
                    try {
                        switch (i) {
                            case 0:
                                MainQuery mainQuery = new MainQuery(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                                mainQuery.run(projog);
                                break;
                            case 1:
                                FindTreasuresByBonusesAndRace findBonusPlayer = new FindTreasuresByBonusesAndRace(matcher.group(1), matcher.group(2));
                                findBonusPlayer.run(projog);
                                break;
                            case 2:
                                FindPlayerTreasures findPlayerTreasures = new FindPlayerTreasures(matcher.group(1));
                                findPlayerTreasures.run(projog);
                                break;
                            case 3:
                                GetPeopleByRace getPeopleByRace = new GetPeopleByRace(matcher.group(1),matcher.group(2));
                                getPeopleByRace.run(projog);
                                break;
                            case 4:
                                DefeatingMonster defeatingMonster = new DefeatingMonster(matcher.group(1), matcher.group(2), matcher.group(3));
                                defeatingMonster.run(projog);
                                break;
                        }
                    }catch (ProjogException e){
                        System.out.println("Неверный запрос - n должно быть числом");
                    }
                    break;
                }
            }

            if (!isMatched) {
                System.out.println("Неверный запрос");
            }
        }

    }
}