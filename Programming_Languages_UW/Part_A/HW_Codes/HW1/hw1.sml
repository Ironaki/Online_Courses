fun is_older (d1: int*int*int, d2: int*int*int) =
     #1 d1 < #1 d2
     orelse (#1 d1 = #1 d2 andalso #2 d1 < #2 d2)
     orelse (#1 d1 = #1 d2 andalso #2 d1 = #2 d2 andalso #3 d1 < #3 d2)
      

fun number_in_month (ds: (int*int*int) list, m: int) =
  if null ds
  then 0
  else
      if #2 (hd ds) = m
      then 1+number_in_month(tl ds, m)
      else 0+number_in_month(tl ds, m)

fun number_in_months (ds: (int*int*int) list, ms: int list) =
  if null ms
  then 0
  else number_in_month(ds, hd ms)+number_in_months(ds, tl ms)


fun dates_in_month (ds: (int*int*int) list, m: int) =
  if null ds
  then []
  else
      if #2 (hd ds) = m
      then hd ds::dates_in_month(tl ds, m)
      else dates_in_month(tl ds, m)

fun dates_in_months (ds: (int*int*int) list, ms: int list) =
  if null ms
  then []
  else dates_in_month(ds, hd ms)@dates_in_months(ds, tl ms)

fun get_nth (ss: string list, i: int) =
  if i = 1
  then hd ss
  else get_nth(tl ss, i-1)

fun date_to_string (d: (int*int*int)) =
  let
      val all_month = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
  in
      get_nth(all_month, #2 d) ^ " " ^ Int.toString(#3 d) ^ ", " ^ Int.toString(#1 d)
  end

fun number_before_reaching_sum (sum: int, is: int list) =
  if sum <= hd is
  then 0
  else 1+number_before_reaching_sum((sum-hd is), tl is)

fun what_month (day: int) =
  let
      val day_in_month = [31,28,31,30,31,30,31,31,30,31,30,31]
  in
      1+number_before_reaching_sum(day, day_in_month)
  end

fun month_range (day1: int, day2: int) =
  if day1 > day2
  then []
  else what_month(day1)::month_range(day1+1,day2)

fun oldest (ds: (int*int*int) list) =
  if null ds
  then NONE
  else
      let
	  fun oldest_nonempty(ds: (int*int*int) list) =
	    if null (tl ds)
	    then hd ds
	    else
		let val tl_ans = oldest_nonempty(tl ds)
		in
		    if is_older(hd ds, tl_ans)
		    then hd ds
		    else tl_ans
		end
      in
	  SOME(oldest_nonempty ds)
      end
