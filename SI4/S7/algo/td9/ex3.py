from timeit import timeit
from dataclasses import dataclass
from typing import Set, Dict, Tuple
import itertools

Transitions = Dict[Tuple[int, str], int]

@dataclass
class AFD:
    alphabet: Set[str]
    etats: Set[int]
    transitions: Transitions
    etat_initial: int
    etats_acceptants: Set[int]

def generate_words(alphabet: Set[str], k: int) -> Set[str]:
    return set(''.join(word) for word in itertools.product(alphabet, repeat=k))

def accept_word(afd: AFD, word: str):
    """Retourne True si le mot word est accepté par l'automate afd, False sinon."""
    current_state = afd.etat_initial
    for letter in word:
        try:
            current_state = afd.transitions[(current_state, letter)]
        except KeyError:
            return False

    return current_state in afd.etats_acceptants

@timeit
def nombre_mots(afd: AFD, k: int) -> int:
    """Retourne le nombre de mots de longueur k acceptés par l'automate afd."""

    return len({word for word in generate_words(afd.alphabet, k) if accept_word(afd, word)})

def nombre_mots_dp_impl(afd: AFD, k: int, init: int) -> int:
    if k == 0:
        return 1 if init in afd.etats_acceptants else 0

    nb_mots = 0
    for letter in afd.alphabet:
        try:
            nb_mots += nombre_mots_dp_impl(afd, k - 1, afd.transitions[(init, letter)])
        except KeyError:
            pass

    return nb_mots

@timeit
def nombre_mots_dp(afd: AFD, k: int) -> int:
    return nombre_mots_dp_impl(afd, k, afd.etat_initial)

def main():
    import sys
    sys.setrecursionlimit(10000)

    afd = AFD({'a', 'b'}, {0, 1, 2}, {(0, 'a') : 1, (0, 'b') : 0, (1, 'a') : 2, (1, 'b') : 0, (2, 'a') : 0, (2, 'b') : 1}, 0, {1})
    print(nombre_mots(afd, 20))
    print(nombre_mots_dp(afd, 500))

if __name__ == '__main__':
    main()