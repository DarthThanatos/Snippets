def spread_trail(self,num,store = []):
        cards = self.resources.cards_list
        # for card in cards:
        #     if card.bombs_around is 0 and not card.mine:
        self.spreadArea(num)
        state_changed = True
        store_changed = False
        print '=========================================='
        while state_changed and not store_changed:
            state_changed = False
            for card in cards:
                if card.bombs_around is 0 and not card.mine and not card.active:
                    self.spreadArea(card.id)
                if not card.mine and not card.active:
                    res = self.determine_one_field(card.id)
                    if res[0] is not 0 and res[1] is not 0 and card.id is not num:
                        print \
                            card.id/self.resources.x_amount,\
                            card.id%self.resources.x_amount,\
                            res
                        if res[0] is res[1]:
                           state_changed = True
                           self.make_unactive(card.id)
                        if res[0] is res[2]:
                           state_changed = True
                           self.make_unactive(card.id)
                        if res[0] is res[1] + res[2]:
                           state_changed = True
                           self.make_unactive(card.id)

                        if res[2] >= res[1]:
                            store = self.add_to_store(card.id,store)
                        if res[2] < res[1] or res[1] - res[0] <= res[0]:
                            self.take_from_store(card.id,store)
                            store_changed = True
                            state_changed = True
                            self.determineBombs()
                            # break
                        if res[2] is 0:
                           store = self.add_to_store(card.id,store)
                           # store_changed = True
                           # state_changed = True
                           # break

        if store_changed:
            self.determineBombs()
            for card in cards:
                card.active = True
            self.spread_trail(num)

        for card in cards:
            if card.mine:
                card.hide_bomb = False

    def clear_start(self,num):
        variants = self.all_possibilities(num)
        variants.append(num)
        cards = self.resources.cards_list
        for i in variants:
            if self.resources.cards_list[i].mine:
                for j in cards:
                    if not j.mine:
                        j.mine = True
                        break
                self.resources.cards_list[i].mine = False
                self.resources.playground.determineBombs()

    def take_from_store(self,id,store):
        if store == []:
            return
        index = store.pop(0)
        cards = self.resources.cards_list
        cards[index].mine = False
        variants = self.all_possibilities(id)
        for i in variants:
            if not cards[i].mine:
                cards[i].mine = True
                break


    def add_to_store(self,num,store):
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.active and card.mine:
                store.append(card.id)
        print "add",num/self.resources.x_amount,num%self.resources.x_amount,store
        return store
		
	def make_unactive(self,num):
        i = num/self.resources.x_amount
        j = num%self.resources.x_amount
        res = [0,0]
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.active:
                card.active = False

    def determine_one_field(self,num):
        res = [0, 0, 0]
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.mine:
                res[0] += 1
            if card.active:
                res[1] += 1
            if card.mine and not card.active:
                res[2] += 1
        return res
		
		
	====================================================================================================================================================================================
	def spread_trail(self,num):
        self.spreadArea(num)
        cards = self.resources.cards_list

        givers = []
        takers = []
        rest = []

        active = []
        non_active = []

        for card in cards:
            if card.active:
                active.append(card)
            else:
                non_active.append(card)
            card.hide_bomb = False

        # while active != []:
        state_changed = True
        card = None
        while state_changed:
            state_changed = False
            print "new loop"
            for card in non_active:
                worked = True
                all_mines,active_around, non_active_mines= self.determine_one_field(card.id)
                if all_mines is not 0 and active_around is not 0 and card.id is not num and not card.mine:
                        print "======================================"
                        if non_active_mines is 0 and all_mines is active_around:
                           print "0: "
                           self.make_unactive(card,active,non_active)
                           rest.append(card)
                           state_changed = True
                           break
                        elif all_mines is non_active_mines:
                           print "1: "
                           self.make_unactive(card,active,non_active)
                           rest.append(card)
                           state_changed = True
                           break
                        elif all_mines is active_around + non_active_mines and non_active_mines is not 0:
                           # print all_mines,active_around,non_active_mines
                           print "2: "
                           self.make_unactive(card,active,non_active)
                           rest.append(card)
                           state_changed = True
                           break

                if all_mines is 0 and not card.mine:
                    self.make_unactive(card,active,non_active)
                for card in self.resources.cards_list:
                    id = card.place()
                pygame.display.update()
                for event in pygame.event.get():
                    pass
                while pygame.mouse.get_pressed()[0] is not 1:
                        for event in pygame.event.get():
                            if event.type is pygame.QUIT:
                                pygame.quit()
                                quit()

        self.store_part(non_active,givers,rest)
        if active != []:
            self.spread_trail(num)

    def store_part(self,non_active,givers,rest):
        state_changed = True
        # while state_changed:
        for card in non_active:
            worked = True
            all_mines,active_around, non_active_mines= self.determine_one_field(card.id)
            state_changed = False
            if non_active_mines > active_around:
                self.add_to_store(card,givers)
            elif non_active_mines < active_around or active_around - all_mines < all_mines and active_around - all_mines + non_active_mines is 1:
                print "taken",card.id
                worked = self.take_from_store(card,givers,rest)
                # if not worked:
                #     self.add_to_store(card,givers)
                state_changed = True
                # break
            elif non_active_mines is 0:
               self.add_to_store(card,givers)

            for card in self.resources.cards_list:
                id = card.place()
            pygame.display.update()
            for event in pygame.event.get():
                pass
            while pygame.mouse.get_pressed()[0] is not 1:
                    for event in pygame.event.get():
                        if event.type is pygame.QUIT:
                            pygame.quit()
                            quit()

    def add_to_store(self,parent,givers):
        num = parent.id
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.mine:
                givers.append(card)

    def take_from_store(self,parent,givers,rest):
        num = parent.id
        mine = None
        if givers != []:
            mine = givers.pop(0)
        loaded = False

        while not loaded:
            if rest == []:
                loaded = True
            for awaken in rest:
                variants = self.all_possibilities(awaken.id)
                for i in variants:
                    card = self.resources.cards_list[i]
                    if card.mine:
                        mine = card
                        loaded = True
                        break
                if not loaded:
                    rest.remove(awaken)

        if mine is None:
            print "returned from take_from_store"
            return False
        self.take_bomb_away(mine)
        self.add_bomb(parent)

    def add_bomb(self,parent):
        variants = self.all_possibilities(parent.id)
        card = None
        for i in variants:
            card = self.resources.cards_list[i]
            if not card.mine:
                card.mine = True
                card.hide_bomb = False
                print "bomb placed at", card.id
                # card.place()
                # pygame.display.update()
                # pygame.time.wait(2000)
                break
        if card is not None:
            self.update_after_adding(card)

    def update_after_adding(self,mine):
        # mine.mine = False
        variants = self.all_possibilities(mine.id)
        for i in variants:
            card = self.resources.cards_list[i]
            card.bombs_around += 1
            print card.id,"+ 1 updated at", card.id,","

    def take_bomb_away(self,mine):
        print "bomb away: ",mine.id
        mine.mine = False
        variants = self.all_possibilities(mine.id)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.bombs_around > 0:
                card.bombs_around -= 1
                print "bombs for", card.id, card.bombs_around

    def make_unactive(self,parent,active,non_active):
        num = parent.id
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.active:
                print card.id,
                card.active = False
                active.remove(card)
                non_active.append(card)
        try:
            non_active.remove(parent)
        except ValueError:
            print "\n",parent.id, "not in list"
        print "par ",parent.id

    def determine_one_field(self,num):
        res = [0, 0, 0]
        variants = self.all_possibilities(num)
        for i in variants:
            card = self.resources.cards_list[i]
            if card.mine:
                res[0] += 1
            if card.active:
                res[1] += 1
            if card.mine and not card.active:
                res[2] += 1
        return res