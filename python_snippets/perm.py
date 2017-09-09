
def perm(P,k):
    if k == 0:
        print P
    else:
        for i in range(k):
            # print "i:",i,"k:",k,P
            swapp = swap(P[i],P[k - 1])
            P[i] = swapp[0]
            P[k - 1] = swapp[1]
            # print "swap",P
            perm(P,k-1)
            swapp = swap(P[i],P[k - 1])
            P[i] = swapp[0]
            P[k - 1] = swapp[1]
            # print "po",P


def swap(p1,p2):
    return p2,p1

def main():
    P = [1,2,3,4]
    N = P.__len__()
    perm (P,N)

main()