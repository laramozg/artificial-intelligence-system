package method;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;

import java.util.ArrayList;
import java.util.Objects;

public class DefeatingMonster {
    String name, bonus, monster;
    int player_level, monster_level;
    public DefeatingMonster(String name, String monster, String bonus) {
        this.name = name;
        this.monster = monster;
        this.bonus = bonus;
    }
    public void run(Projog prolog){
        QueryStatement query1 = prolog.createStatement("player_level(X, Y).");
        query1.setTerm("X", new Atom(String.format("%s", name)));
        QueryResult res1 = query1.executeQuery();
        while (res1.next()){
            player_level = Integer.parseInt(String.valueOf(res1.getTerm("Y")));
        }
        QueryStatement query2 = prolog.createStatement("monster_level(X, Y).");
        query2.setTerm("X", new Atom(String.format("%s", monster)));
        QueryResult res2 = query2.executeQuery();
        while (res2.next()){
            monster_level = Integer.parseInt(String.valueOf(res2.getTerm("Y")));
        }
        if (Objects.equals(bonus, "с")){
            ArrayList<String> list = new ArrayList<>();
            QueryStatement query3 = prolog.createStatement("treasure_player(X, Y).");
            query3.setTerm("Y", new Atom(String.format("%s", name)));
            QueryResult res3 = query3.executeQuery();
            while (res3.next()){
                list.add(String.valueOf(res3.getTerm("X")));
            }
            QueryStatement query4 = prolog.createStatement("bonus_in_treasure(X, Y).");
            for (String s : list) {
                query4.setTerm("X", new Atom(s));
                QueryResult res4 = query4.executeQuery();
                while (res4.next()) {
                    player_level += Integer.parseInt(String.valueOf(res4.getTerm("Y")));
                }
            }
        }
        answer();
    }
    public void answer() {
        if (!Objects.equals(bonus, "с") || !Objects.equals(bonus, "без")){
            System.out.println("Некорректный запрос! Введите с/без");
        }
        else if (player_level > monster_level){
            System.out.println("Игрок "+ name + " сможет одолеть монстра "+ monster);
        }else System.out.println("Игрок "+ name + " не сможет одолеть монстра "+ monster);
    }
}
