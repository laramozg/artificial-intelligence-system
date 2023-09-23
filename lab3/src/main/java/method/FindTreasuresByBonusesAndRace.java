package method;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class FindTreasuresByBonusesAndRace {
    String bonus, race;
    public FindTreasuresByBonusesAndRace(String bonus, String race) {
        this.bonus = bonus;
        this.race = race;
    }
    public void run(Projog prolog) {
        HashMap<Term, Integer> list = new HashMap<>();
        ArrayList<String> ans = new ArrayList<>();
        QueryResult res1 = prolog.executeQuery(String.format("bonus_in_treasure(X, Y),Y > %s.", bonus));
        while (res1.next()) {
            list.put(res1.getTerm("X"), 1);
        }

        QueryStatement query2 = prolog.createStatement("player_bonus(X, Y).");
        query2.setTerm("Y", new Atom(String.format("%s", race)));
        QueryResult res2 = query2.executeQuery();
        while (res2.next()){
            if (list.containsKey(res2.getTerm("X"))){
                ans.add(String.valueOf(res2.getTerm("X")));
            }
        }
        answer(ans);
    }
    public void answer(ArrayList<String> ans) {
        if (ans.size() != 0){
            System.out.println("Вам подойдут сокровища:");
            for (String an : ans) {
                System.out.println(an);
            }
        }else System.out.println("Подходящих сокровищ нет");
    }
}
