#python3
#installation :
#sudo pip3 install requests
#sudo pip3 install faker
from random import randint
import requests as rq
import argparse
from faker import Faker

fake = Faker()


def generate_user_json(user_name):
    return {"userName": user_name, "avatarId": "avatar" + str(randint(1, 8)),
            "deviceInfo": "generated {}".format(fake.company())}


def end_game_json(uid, game_nr, passed):
    return {
        "eventType": "endGame",
        "userId": uid,
        "body": {
            "gameId": "game" + str(game_nr),
            "gamePassed": passed,
            "secInGame": randint(4, 20),
            "score": randint(0, 130)}
    }


def add_campaign(url, user_name=None):
    if user_name is None:
        user_name = fake.first_name()[:13] + (str(randint(0, 99)) if randint(0, 1) < 1 else "")
    user_json = generate_user_json(user_name)

    user_resp = rq.post(url + "users/", json=user_json)

    try:
        user_id = user_resp.json()["userId"]
        for i in range(1, 4):
            if randint(0, 3) < 3:
                ev_resp = rq.post(url + "events/", json=end_game_json(user_id, game_nr=i, passed=False))
                print("user: {} code:{}".format(user_name, str(ev_resp.status_code)))

            ev_resp = rq.post(url + "events/", json=end_game_json(user_id, game_nr=i, passed=True))
            print("user: {} code:{}".format(user_name, str(ev_resp.status_code)))
    except Exception:
        print(user_resp.status_code, user_name)


def positive_int(value):
    int_val = int(value)
    if int_val <= 0:
        raise argparse.ArgumentTypeError("{} is an invalid positive int value".format(value))
    return int_val


if __name__ == '__main__':
    local_url = "http://localhost:8080/"
    remote_url = "https://gameinn.sosoftware.pl/akka/"

    parser = argparse.ArgumentParser()
    parser.add_argument("-n", "--usernumber", help="number of users to add", default=30, type=positive_int)

    group = parser.add_mutually_exclusive_group()

    group.add_argument("-r", "--remote", help="use remote url: {} instead of local {}".format(remote_url, local_url),
                       action="store_true",
                       )
    args = parser.parse_args()

    url = remote_url if args.remote else local_url

    for i in range(args.usernumber):
        add_campaign(url)
