%БАЗА ЗНАНИЙ О НАСТОЛЬНОЙ ИГРЕ "МАНЧКИН"


%Факты о том, какие расы существуют в игре
race_in_game(people).
race_in_game(elf).
race_in_game(dwarf).
race_in_game(halfling).

%Факты о том, какие классы существуют в игре
class_in_game(warrior).
class_in_game(thief).
class_in_game(magicion).
class_in_game(cleric).

%Факты о том, какие монстры существуют в игре
monster_in_game(amazon).
monster_in_game(goblin).
monster_in_game(bullrog).
monster_in_game(rygachu).
monster_in_game(vampire).
monster_in_game(orc).
monster_in_game(demon).
monster_in_game(agent).

%Факты об уровне монстра (имя - уровень)
monster_level(amazon, 8).
monster_level(goblin, 1).
monster_level(bullrog, 18).
monster_level(rygachu, 6).
monster_level(vampire, 12).
monster_level(orc, 10).
monster_level(demon, 2).
monster_level(agent, 14).

%Факты о том, какие сокровища существуют в игре
treasure_in_game(big_sword).
treasure_in_game(blister).
treasure_in_game(holy_palitsa).
treasure_in_game(cloak_furbidite).
treasure_in_game(archer).
treasure_in_game(helmet).

%Факты о бонусах, которые приносят сокровища (название - бонус)
bonus_in_treasure(big_sword, 3).
bonus_in_treasure(blister, 2).
bonus_in_treasure(holy_palitsa, 3).
bonus_in_treasure(cloak_furbidite, 2).
bonus_in_treasure(archer, 4).
bonus_in_treasure(helmet, 2).

%Факты о том, что человек является игроком 
player_now(larisa).
player_now(ivan).
player_now(oleg).
player_now(maria).

%Факты об уравне игрока
player_level(larisa,4).
player_level(ivan,9).
player_level(oleg,2).
player_level(maria,3).

%ОТНОШЕНИЯ

%Факты о том, что сокровища могут принадлежать игрокам опреденной расы (отношение сокровищ и   расы)
player_bonus(holy_palitsa, dwarf).
player_bonus(archer, elf).
player_bonus(cloak_furbidite, thief).
player_bonus(big_sword, halfling).
player_bonus(helmet, people).
player_bonus(helmet, dwarf).
player_bonus(helmet, halfling).
player_bonus(blister, people).
player_bonus(blister, elf).
player_bonus(blister, halfling).

%Факты о принадлежности игрока к классу
player_class(larisa, warrior).
player_class(ivan, magicion).
player_class(oleg, cleric).
player_class(maria, warrior).

%Факты о принадлежности игрока к расе
player_race(larisa, people).
player_race(ivan, people).
player_race(oleg, elf).
player_race(maria, halfling).

%Факты о принадлежности сокровищ игроку
treasure_player(archer, oleg).
treasure_player(big_sword, maria).
treasure_player(blister, larisa).
treasure_player(helmet, ivan).

%ПРАВИЛА
	
%Правило о принадлежности к волшебникам
is_mag(Name):-
    player_now(Name),
    player_class(Name,magicion).

%Правило о принадлежности к людям
is_people(Name):-
    player_now(Name),
    player_race(Name,people).

%Правило о наличии сокровища "большой меч" у игрока
has_sword(Name):-
     player_now(Name),
     treasure_player(big_sword,Name).

%Правило, проверяющее возможность получить сокровище
ability_to_get_treasure(Name, Treasure):-
   player_now(Name),
   player_race(Name, Race),
   treasure_in_game(Treasure),
   player_bonus(Treasure, Race).

%Правило, проверяющее возможность одержать победу над монстром без бонусов
ability_to_win_the_monster_no_bonuses(Name,Monster) :-
    player_now(Name),
    monster_in_game(Monster),
    player_level(Name, X),
    monster_level(Monster, Y),
    (  X > Y -> true ;  false).

%Правило, проверяющее возможность одержать победу над монстром с бонусами
ability_to_win_the_monster_with_bonuses(Name,Monster) :-
    player_now(Name),
    monster_in_game(Monster),
    treasure_player(Treasure,Name),
    bonus_in_treasure(Treasure,Z),
    player_level(Name, X),
    monster_level(Monster, Y),
    (  X + Z > Y -> true ;  false).


  
