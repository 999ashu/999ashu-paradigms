prime(2).
prime(N) :- N > 2, \+ divides_by(N, 2).

divides_by(N, D) :- N mod D =:= 0.
divides_by(N, D) :- D * D < N, D1 is D + 1, divides_by(N, D1).

composite(N) :- N > 1, \+ prime(N).


nth_prime(N, P, Pos, Cur) :-
    prime(Cur),
    (
        Pos =:= N
        -> P is Cur
        ; NextPos is Pos + 1,
          Next is Cur + 1,
          nth_prime(N, P, NextPos, Next)
    ).

nth_prime(N, P, Pos, Cur) :-
    composite(Cur),
    Next is Cur + 1,
    nth_prime(N, P, Pos, Next).

nth_prime(N, P) :- nth_prime(N, P, 1, 2).


get_divisor(N, Cur, D) :-
    (N mod Cur =:= 0, D is Cur);
    (Cur * Cur < N, Next is Cur + 1, get_divisor(N, Next, D));
    (Cur * Cur >= N, D is N).

prime_divisors(1, Divisors, Divisors).
prime_divisors(N, List, Divisors) :-
    N > 1,
    get_divisor(N, 2, D),
    N1 is N // D,
    prime_divisors(N1, [D | List], Divisors).

prime_divisors(N, Divisors) :-
%    prime_product(N, 1, Divisors),
    prime_divisors(N, [], RDivisors),
    reverse(RDivisors, Divisors), !.

% :NOTE: prime_divisors(N, [2,2,2,3])
%prime_product(N, N, []).
%prime_product(N, Cur, [H | T]) :-
%    prime(H),
%    Next is Cur * H,
%    prime_product(N, Next, T).
