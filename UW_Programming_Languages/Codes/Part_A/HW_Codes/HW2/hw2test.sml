 use "hw2.sml";
(* use "hw2pr2.sml"; *)

(* Homework2 Simple Test *)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1 = all_except_option ("string", ["string"]) = SOME []

val test1_1 = all_except_option ("string", ["strin"]) = NONE

val test1_2 = all_except_option ("string", ["ab","ac","string","ac","ab"]) = SOME ["ab","ac","ac","ab"]
							    
val test2 = get_substitutions1 ([["foo"],["there"]], "foo") = []


val test2_1 = get_substitutions1([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]],"Fred") = ["Fredrick","Freddie","F"]
														  
val test3 = get_substitutions2 ([["foo"],["there"]], "foo") = []


val test3_1 = get_substitutions2([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]],"Fred") = ["Fredrick","Freddie","F"]
														  
val test4 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Fred", middle="W", last="Smith"}) =
	    [{first="Fred", last="Smith", middle="W"}, {first="Fredrick", last="Smith", middle="W"},
	     {first="Freddie", last="Smith", middle="W"}, {first="F", last="Smith", middle="W"}]

val test5 = card_color (Clubs, Num 2) = Black

val test5_1 = card_color (Spades, Num 2) = Black

val test5_2 = card_color (Diamonds, Num 2) = Red
					    
val test6 = card_value (Clubs, Num 2) = 2

val test6_1 = card_value (Clubs, Queen) = 10

val test6_2 = card_value (Clubs, Ace) = 11
					    
val test7 = remove_card ([(Hearts, Ace)], (Hearts, Ace), IllegalMove) = []

val test8 = all_same_color [(Hearts, Ace), (Hearts, Ace)] = true

val test8_1 = all_same_color [(Clubs,Ace),(Spades,Ace),(Diamonds,Ace)] = false

val test9 = sum_cards [(Clubs, Num 2),(Clubs, Num 2)] = 4

val test10 = score ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4

val test10_1 = score ([(Spades, Num 2),(Clubs, Num 4),(Clubs, Num 10)],10) = 9
						       
val test11 = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6

val test12 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        42)
             = 3

val test13 = ((officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Hearts,Jack)],
                         42);
               false) 
              handle IllegalMove => true)
             

val test13_1 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Clubs,Jack)],
                         42)
               = 21

val test13_2 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Clubs,Jack),Draw,Discard(Spades,Num(8))],
                         42)
               = 21
		     
val test13_3 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Draw,Discard(Clubs,Jack),Discard(Spades,Num(8))],
                         42)
               = 21

		       
val test13_4 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Draw,Discard(Clubs,Jack)],
                         42)
               = 17

val test13_5 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [],
                         42)
               = 21

val test13_6 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Draw,Draw,Discard(Clubs,Jack)],
                         42)
               = 12

val test13_7 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw],
                         42)
               = 16

val test13_8 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Draw,Discard(Clubs,Jack)],
                         8)
               = 3	     

val test13_9 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Draw,Discard(Clubs,Jack)],
                         16)
               = 3
(*
val test14_1 = raw_score([(Clubs, Ace), (Clubs, Ace)], 2) = 2

val test14_2 = raw_score([(Clubs, Ace), (Clubs, Ace)], 33) = 22

val test14_3 = raw_score([(Clubs, Ace), (Clubs, Ace)], 1) = 2	   

val test14_4 = officiate_challenge([(Clubs, Ace), (Clubs, Ace), (Clubs, Ace)], [Draw, Draw], 5) = 1

val test14_5 = officiate_challenge([(Clubs, Ace), (Clubs, Ace), (Clubs, Ace)], [Draw, Draw], 12) = 0

val test14_6 = officiate_challenge([(Clubs, Ace), (Clubs, Ace), (Clubs, Ace)], [Draw, Draw], 23) = 0	

val test14_7 = score_challenge([(Clubs, Ace), (Clubs, Ace)], 12) = 0

val test14_8 = raw_score([(Clubs, Ace), (Clubs, Ace)], 12) = 12
*)
