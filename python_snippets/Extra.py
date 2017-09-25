from random import *
import matplotlib.pyplot as plt
import numpy as np
import itertools

def dist(a, b):
    return ((a[0] - b[0])**2 + (a[1] - b[1])**2)**0.5

def energy(points):
    N = len(points)
    d = 0
    for i in range(N):
        d += dist(points[i], points[(i + 1) % N])  		# also computing way to the starting point
    return d

def generate_points(N):
    points = []
    # X = []
    # Y = []
    for i in range(N):
        x, y = randrange(100), randrange(100)
        points.append((x, y))
        # X.append(x)
        # Y.append(y)
    # X = np.array(X)
    # Y = np.array(Y)
    return points

def generate_groups(N):
    N_groups = 9
    S = 240
    Smax = 250
    supernodes_X = [randrange(S)]
    for i in range(1, N_groups):
        supernodes_X.append(supernodes_X[-1] + randrange(S, Smax))
    supernodes_Y = [randrange(S)]
    for i in range(1, N_groups):
        supernodes_Y.append(supernodes_Y[-1] + randrange(S, Smax))
    shuffle(supernodes_X)
    shuffle(supernodes_Y)
    supernodes = zip(supernodes_X, supernodes_Y)
    # plt.plot(supernodes_X, supernodes_Y, 'ro')
    points = []
    for p in supernodes:
        for i in range(N / N_groups):
            x = randrange(int(p[0] - S/2.0), int(p[0] + S/2.0))
            y = randrange(int(p[1] - S/2.0), int(p[1] + S/2.0))
            points.append((x, y))
    return supernodes + points

def normal_distribution(N):
    sigma = 300
    mu = 500
    points = list(sigma * np.random.randn(N, 2) + mu)
    print "normal: ",points
    return points

def consecutive_swap(points):
    _points = points[:]
    i = randrange(len(_points) - 1)
    _points[i], _points[i + 1] = _points[i + 1], _points[i]
    return _points

def arbitrary_swap(points):
    _points = points[:]
    i = randrange(len(_points))
    j = randrange(len(_points))
    while (i == j):
        j = randrange(len(_points))
    _points[i], _points[j] = _points[j], _points[i]
    return _points


def acceptance_probability(current_energy, considered_energy, temperature):
    if considered_energy < current_energy:		# 'cause we're looking for minimum
        print 1
        return 1.0						# and 've found better place - go to it
    res = np.e**((current_energy - considered_energy) / temperature)
    print "res:",res,"delta:",(current_energy - considered_energy)
    return res


def T(temperature, cooling_rate):			# function to decrease temprerature
    return temperature * (1 - cooling_rate)


def anneal(points):
    best = points[:] 		# it's out best solution for the time being
    current = points[:]		# les't start from random combination
    energies = []
    temperatures = []
    cost = energy(points)
    temperature = 10000
    cooling_rate = .003
    iters = 1 		# initial
    temperatures.append(temperature)
    energies.append(energy(current))
    while (temperature > 1):
        successor = consecutive_swap(current)
        if acceptance_probability(energy(current), energy(successor), temperature) > random():
            current = successor[:]
            print "accepted"
        if energy(current) < energy(best):
            best = current[:]
        temperature = T(temperature, cooling_rate)
        iters += 1
        temperatures.append(temperature)
        energies.append(energy(current))
        #print(energy(current))
    print("iters = " + str(iters))
    return best, iters, temperatures, energies


def brute_force(points):
    permutations = list(itertools.permutations(points))
    best = permutations[0]
    for path in permutations:
        if energy(path) < energy(best):
            best = path[:]
    return best

def main():
    n = 10
    points = generate_points(n)
    # points = generate_groups(n)
    # points = normal_distribution(n)
    X = [x[0] for x in points]
    Y = [x[1] for x in points]
    print("Energy: ",energy(points))
    # shuffle(points)

    print("\nWith simulated annealing:")
    best, iters, temperatures, energies = anneal(points)
    print(best)
    print"----------------------------------------"
    print(energy(best))

    # print("\nWith brute force:")
    # best_brute = brute_force(points)
    # # print(best_brute)
    # print(energy(best_brute))

    plt.plot(X, Y, 'ro')

    for i in range(len(best)):
        plt.plot([best[i][0], best[(i + 1)  % len(best)][0]], [best[i][1], best[(i + 1)  % len(best)][1]], lw=1, c="b")

    print(min(energies))
    for i in range(len(energies)):
        if abs(energies[i] - energy(best)) < energies[i]*0.0005:
            print(i, energies[i])
            break

    plt.show()

main()
a = [1,2,3,4,5,6,7]
print "arbitrary", a,arbitrary_swap(a),"\n", a,consecutive_swap(a)