 (* 1 *)
val only_capitals = List.filter (fn s => Char.isUpper(String.sub (s,0)))

(* 2 *)
val longest_string1 = List.foldl (fn (s, acc) => if String.size(s) > String.size(acc) then s else acc) ""

(* 3 *)
val longest_string2 = List.foldl (fn (s, acc) => if String.size(s) >= String.size(acc) then s else acc) ""

(* 4 *)
fun longest_string_helper f = List.foldl (fn (s, acc) => if f(String.size(s), String.size(acc)) then s else acc) ""

val longest_string3 = longest_string_helper (fn (a,b) => a > b)

val longest_string4 = longest_string_helper (fn (a,b) => a >= b)

(* 5 *)
val longest_capitalized = longest_string1 o only_capitals

(* 6 *)
val rev_string = String.implode o List.rev o String.explode
					       
(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

(* 7 *)
fun first_answer f ls =
  case ls of
      [] => raise NoAnswer
    | hd::ls' => case f hd of
		     NONE => first_answer f ls'
		   | SOME x => x

(* 8 *)
fun all_answers f ls =
  let
      fun aux(acc, ls) =
	case ls of
	    [] => SOME acc
	  | hd::ls' => case f hd of
			   NONE => NONE
			 | SOME x => aux(acc@x, ls')
  in
      aux([], ls)
  end

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(* 9a *)
val count_wildcards = g (fn _ => 1) (fn _ => 0)

(* 9b *)
val count_wild_and_variable_lengths = g (fn _ => 1) (fn x => String.size x)

(* 9c *)
fun count_some_var (s, p) = g (fn _ => 0) (fn x => if s = x then 1 else 0) p

(* 10 *)
val check_pat =
    let
	fun all_strings p =
	  let
	      fun aux(acc, p) =
		case p of
		    Variable x => x::acc
		  | TupleP ps => List.foldl (fn(p, acc) => aux(acc, p)) acc ps
		  | ConstructorP(_,p) => aux(acc, p)
		  | _ => acc
	  in
	      aux([], p)
	  end

	fun no_repeat ss =
	  case ss of
	      [] => true
	    | s::ss' => if List.exists (fn x => x=s) ss'
			then false
			else no_repeat ss'
    in
	no_repeat o all_strings 
    end



(* 11 *)
fun match (v, p) =
  case (v, p) of
      (_, Wildcard) => SOME []
    | (value, Variable s) => SOME [(s, value)]
    | (Unit, UnitP) => SOME []
    | (Const a, ConstP b) => if a = b then SOME[] else NONE
    | (Tuple vs, TupleP ps) => if List.length vs = List.length ps
			       then all_answers match (ListPair.zip(vs, ps))
						else NONE
    | (Constructor(s2, value), ConstructorP(s1, pattern)) => if s1 = s2 then match(value, pattern) else NONE
    | _=> NONE

(* 12 *)
fun first_match v ps =
  SOME(first_answer (fn p => match(v,p)) ps) handle NoAnswer => NONE
				
(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)
