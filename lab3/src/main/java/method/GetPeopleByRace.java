package method;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Atom;

import java.util.ArrayList;

public class GetPeopleByRace {
    String race, classGame;
    public GetPeopleByRace(String race, String classGame) {
        this.race = race;
        this.classGame = classGame;
    }

    public void run(Projog prolog){
        ArrayList<String> ans = new ArrayList<>();
        QueryStatement query1 = prolog.createStatement("player_race(X, Y).");
        query1.setTerm("Y", new Atom(String.format("%s", race)));
        QueryResult res1 = query1.executeQuery();
        while (res1.next()){
            ans.add(String.valueOf(res1.getTerm("X")));
        }
        QueryStatement query2 = prolog.createStatement("player_class(X, Y).");
        query2.setTerm("Y", new Atom(String.format("%s", classGame)));
        QueryResult res2 = query2.executeQuery();
        while (res2.next()){
            if (!ans.contains(String.valueOf(res2.getTerm("X")))) {
                ans.add(String.valueOf(res2.getTerm("X")));
            }
        }
        answer(ans);
    }
    public void answer(ArrayList<String> ans) {
        if (ans.size() != 0){
            System.out.println("Участники, принадлежащие к расе  "+ race + " или классу " + classGame+":");
            for (String an : ans) {
                System.out.println(an);
            }
        }else System.out.println("Подходящих участников нет");
    }
}
