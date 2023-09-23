package method;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;

import java.util.ArrayList;

public class FindPlayerTreasures {
    String name;
    public FindPlayerTreasures(String name) {
        this.name = name;
    }
    public void run(Projog prolog){
        String race = null;
        ArrayList<String> ans = new ArrayList<>();
        QueryStatement query1 = prolog.createStatement("player_race(X, Y).");
        query1.setTerm("X", new Atom(String.format("%s", name)));
        QueryResult res1 = query1.executeQuery();
        while (res1.next()){
            race = String.valueOf(res1.getTerm("Y"));
        }
        QueryStatement query2 = prolog.createStatement("player_bonus(X, Y).");
        query2.setTerm("Y", new Atom(race));
        QueryResult res2 = query2.executeQuery();
        while (res2.next()){
            ans.add(String.valueOf(res2.getTerm("X")));
        }
        answer(ans);
    }
    public void answer(ArrayList<String> ans) {
        if (ans.size() != 0){
            System.out.println("Игрок "+ name + " может получить сокровища:");
            for (String an : ans) {
                System.out.println(an);
            }
        }else System.out.println("Игрок "+name+" не может получить сокровища");
    }
}
