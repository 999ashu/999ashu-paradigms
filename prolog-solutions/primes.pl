prime(2).
prime(N) :- N > 2, \+ divides_by(N, 2).

divides_by(N, D) :- N mod D =:= 0.
divides_by(N, D) :- D * D < N, D1 is D + 1, divides_by(N, D1).


composite(N) :- N > 1, \+ prime(N).


nth_prime(N, P, Pos, Cur) :-
    prime(Cur),
    (
        Pos is N
        ->
        P is Cur
        ;
        NextPos is Pos + 1,
        Next is Cur + 1,
        nth_prime(N, P, NextPos, Next)
    ).

nth_prime(N, P, Pos, Cur) :-
    composite(Cur),
    Next is Cur + 1,
    nth_prime(N, P, Pos, Next).

nth_prime(N, P) :- nth_prime(N, P, 1, 2).


prime_divisors(1, Divisors, Divisors, _).
% :NOTE: лишние аргументы
prime_divisors(N, Divisors, List, Cur) :-
    N > 1,
    (
        N mod Cur =:= 0
        ->
        N_Div is N // Cur,
        prime_divisors(N_Div, Divisors, [Cur | List], Cur)
        ;
        Next is Cur + 1,
        prime_divisors(N, Divisors, List, Next)
    ).

% :NOTE: prime_divisors(N, [2,2,2,3])
prime_divisors(N, Divisors) :-
    prime_divisors(N, ReversedDivisors, [], 2),
    reverse(ReversedDivisors, Divisors).
