(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* 1a *)
fun all_except_option (s, ss) =
  case ss of
      [] => NONE
    | first::ss' => if same_string(s, first)
		    then SOME(ss')
		    else
			case all_except_option (s, ss') of
			    NONE => NONE
			  | SOME tl => SOME(first::tl)

(* 1b *)
fun get_substitutions1 (sss, s) =
  case sss of
      [] => []
    | ss::sss' => case all_except_option (s, ss) of
		      NONE => get_substitutions1 (sss', s)
		    | SOME ls => ls @ get_substitutions1 (sss', s)

(* 1c *)
fun get_substitutions2 (sss, s) =
  let
      fun aux (ls_ss, acc) =
	case ls_ss of
	    [] => acc
	  | ss::ls_ss' => case all_except_option (s, ss) of
			      NONE => aux(ls_ss', acc)
			    | SOME ls => aux(ls_ss', acc @ ls)
  in
      aux(sss, [])
  end


(* 1d *)
fun similar_names (sss, {first=f, middle=m, last=l}) =
  let
      val alt_name = f::get_substitutions2(sss, f)
      fun helper (ss) =
	case ss of
	    [] => []
	  | s::ss' => {first=s, middle=m, last=l}::helper(ss') 
  in
      helper (alt_name)
  end

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

(* 2a *)
fun card_color card =
  case card of
      (Spades,_) => Black
    | (Clubs,_) => Black
    | _ => Red 


(* 2b *)
fun card_value card =
  case card of
      (_,Num i) => i
    | (_,Ace) => 11
    | _ => 10

(* 2c *)
fun remove_card (cs, c, e) =
  case cs of
      [] => raise e
    | hd::cs' => if hd = c
		 then cs'
		 else hd::remove_card(cs', c , e)

(* 2d *)
fun all_same_color cs =
  case cs of
      [] => true
    | _::[] => true
    | c1::(c2::cs') => (card_color c1 = card_color c2) andalso all_same_color (c2::cs') 

(* 2e *)
fun sum_cards cs =
  let
      fun aux (cs, acc) =
	case cs of
	    [] => acc
	  | c::cs' => aux(cs', acc+card_value(c))
  in
      aux(cs, 0)
  end

(* 2f *)
fun score (cs, goal) =
  let
      val sum_c = sum_cards cs
      val pre_score = if sum_c > goal then (sum_c - goal)*3 else goal-sum_c
  in
      case all_same_color cs of
	  true => pre_score div 2
	| false => pre_score
  end

(* 2g *)
fun officiate (cs, ms, goal) =
  let
      fun aux (cs, ms, hcs) =
	case (cs, ms) of
	    (c::cs', m::ms') => (if sum_cards hcs > goal
				 then hcs 
				 else case m of
					  Draw => aux (cs', ms', c::hcs)
					| Discard ctd => aux (cs, ms', remove_card(hcs, ctd, IllegalMove)))
	  | ([], m::ms') => (if sum_cards hcs > goal
			     then hcs
			     else case m of
				      Draw => hcs
				    | Discard ctd => aux ([], ms', remove_card(hcs, ctd, IllegalMove)))
	  | (_,[]) => hcs 
  in
      score(aux (cs, ms, []), goal)
  end
      


(* 3a *)

(* Substitutes first occurance of Ace in a card list, with Num 1 *)
fun remove_ace (cs) =
  case cs of
      [] => raise List.Empty
    | c::cs' => case c of
		    (Clubs, Ace) => (Clubs, Num 1)::cs'
		  | (Spades, Ace) => (Spades, Num 1)::cs'
		  | (Diamonds, Ace) => (Diamonds, Num 1)::cs'
		  | (Hearts, Ace) => (Hearts, Num 1)::cs'
		  | _ => c::remove_ace(cs') 
							    
fun raw_score (cs, goal) =
  let
      val sumC = sum_cards(cs) 
      val pre_score = if sumC > goal
		      then raw_score (remove_ace(cs), goal) handle List.Empty => sumC
		      else sumC
  in
      pre_score
  end

fun score_challenge (cs, goal) =
  let
      val r_score = raw_score (cs, goal)
      val pre_score = if r_score > goal then 3 * (r_score - goal) else goal - r_score
  in
      if all_same_color cs
      then pre_score div 2
      else pre_score
  end

fun card_value_c card =
  case card of
      (_,Num i) => i
    | (_,Ace) => 1
    | _ => 10
      
fun sum_cards_c cs =
  let
      fun aux (cs, acc) =
	case cs of
	    [] => acc
	  | c::cs' => aux(cs', acc+card_value_c(c))
  in
      aux(cs, 0)
  end
      
fun officiate_challenge (cs, ms, goal) =
  let
      fun aux (cs, ms, hcs) =
	case (cs, ms) of
	    (c::cs', m::ms') => (if sum_cards_c hcs > goal
				 then hcs 
				 else case m of
					  Draw => aux (cs', ms', c::hcs)
					| Discard ctd => aux (cs, ms', remove_card(hcs, ctd, IllegalMove)))
	  | ([], m::ms') => (if sum_cards_c hcs > goal
			     then hcs
			     else case m of
				      Draw => hcs
				    | Discard ctd => aux ([], ms', remove_card(hcs, ctd, IllegalMove)))
	  | (_,[]) => hcs 
  in
      score_challenge (aux (cs, ms, []), goal)
  end


(* 3b *)

fun find_card_to_drop (cs, c, diff) =
  let
      val ctd = card_value c - diff
  in
      if ctd < 0 orelse ctd > 10
      then (Spades, Num 0)
      else
	  case cs of
	      [] => (Spades, Num 0)
	    | hd::cs' => if card_value hd = ctd
			 then hd
			 else find_card_to_drop (cs', c, diff)
  end

 
fun careful_player (cs, goal) =
  let
      fun aux (cs, hcs, ms) =
	case cs of
	    [] =>  if (goal - sum_cards hcs ) > 10
		   then ms@[Draw]
		   else ms
	  | c::cs' => (let
			  val diff = (goal - sum_cards hcs )
		      in
			  if diff > 10
			  then aux (cs', c::hcs, ms@[Draw])
			  else
			      if diff = 0
			      then ms
			      else
				  case  find_card_to_drop (hcs, c, diff) of
				      (Spades, Num 0) => ms
				    | c => aux (cs, remove_card(hcs, c, IllegalMove), ms@[Discard c, Draw])
		      end)
  in
      aux(cs, [], [])
  end	      
	       
