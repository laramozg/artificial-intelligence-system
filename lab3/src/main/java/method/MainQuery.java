package method;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.predicate.builtin.clp.In;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainQuery {
    String name, level, race, classGame;

    public MainQuery(String name, String level, String race, String classGame) {
        this.name = name;
        this.level = level;
        this.race = race;
        this.classGame = classGame;
    }

    public void run(Projog prolog) {

        HashMap<Term, Integer> list = new HashMap<>();
        ArrayList<String> ans = new ArrayList<>();
        QueryResult res2 = prolog.executeQuery(String.format("player_level(X, Y),Y > %s.", level));
        while (res2.next()){
            list.put(res2.getTerm("X"),1);
        }
        QueryStatement query3 = prolog.createStatement("player_race(X, Y).");
        query3.setTerm("Y", new Atom(String.format("%s", race)));
        QueryResult res3 = query3.executeQuery();
        while (res3.next()){
            if (list.containsKey(res3.getTerm("X"))){
                list.replace(res3.getTerm("X"),2);
            }
        }
        QueryStatement query4 = prolog.createStatement("player_class(X, Y).");
        query4.setTerm("Y", new Atom(String.format("%s", classGame)));
        QueryResult res4 = query4.executeQuery();
        while (res4.next()){
            if (list.containsKey(res4.getTerm("X")) ){
                if (list.get(res4.getTerm("X")) == 2) {
                    ans.add(String.valueOf(res4.getTerm("X")));
                }
            }
        }
        answer(ans);

    }

    public void answer(ArrayList<String> ans) {
        if (ans.size() != 0){
            System.out.println(name + ", для вас подойдут данные игроки:");
            for (String an : ans) {
                System.out.println(an);
            }
        }else System.out.println("Подходящих игроков нет");
    }


}
