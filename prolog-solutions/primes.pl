prime(2).
prime(N) :- N > 2, \+ divides_by(N, 2).

divides_by(N, D) :- N mod D =:= 0.
divides_by(N, D) :- D * D < N, D1 is D + 1, divides_by(N, D1).

composite(N) :- N > 1, \+ prime(N).


nth_prime(N, P, Pos, Cur) :-
    prime(Cur),
    (
        Pos is N -> P is Cur;
        NextPos is Pos + 1,
        Next is Cur + 1,
        nth_prime(N, P, NextPos, Next)
    ).

nth_prime(N, P, Pos, Cur) :-
    composite(Cur),
    Next is Cur + 1,
    nth_prime(N, P, Pos, Next).

nth_prime(N, P) :- nth_prime(N, P, 1, 2).


get_divisor(N, Cur, Cur) :- N mod Cur =:= 0.
get_divisor(N, Cur, D) :- Cur * Cur < N, Next is Cur + 1, get_divisor(N, Next, D).
get_divisor(N, Cur, D) :- Cur * Cur >= N, D is N.

prime_product(N, N, []).
prime_product(N, Cur, [H | T]) :-
    prime(H),
    Next is Cur * H,
    prime_product(N, Next, T).

prime_divisors(1, Divisors, Divisors).
prime_divisors(N, List, Divisors) :-
    N > 1,
    get_divisor(N, 2, D),
    N1 is N // D,
    prime(D),
    prime_divisors(N1, [D | List], Divisors).

not_decreasing_order([]).
not_decreasing_order([_]).
not_decreasing_order([C, H | T]) :- C =< H, not_decreasing_order([H | T]).

prime_divisors(N, Divisors) :-
    (\+ number(N) -> prime_product(N, 1, Divisors);
    prime_divisors(N, [], RDivisors),
    reverse(RDivisors, Divisors), !),
    not_decreasing_order(Divisors).
