restore_forward_deleted_list
restore_back_deleted_list
restore_forward_deleted_int
restore_back_deleted_int
restore_forward_added_list
restore_back_added_list
restore_forward_added_int
restore_back_added_int



def restore(self, direction):
state = self.resources.action_chain[self.resources.chain_index]
action,item,number = state
if action == "deleted":
if type(item) is list:
if direction == "right":
	# if self.resources.chain_index != self.resources.action_chain.__len__() - 1:
	#     next_action,next_item,next_number =  self.resources.action_chain[self.resources.chain_index + 1]
	#     if next_action == "added" and type(next_item) is int:
	#         field = self.resources.fields[next_number]
	#         field.small_numbers = []
	#         field.has_value = True
	#         field.value = next_item
	#         self.resources.numbers[next_number - 1].value += 1
	#         self.resources.numbers_put += 1
	#         self.resources.chain_index += 1
	#     else:
	#         pass
	# else:
	#     for i in item:
	#         self.resources.fields[number].small_numbers.remove(i)
else:
	# self.resources.fields[number].small_nubers = self.resources.fields[number].small_nubers.__add__(item)
else:
if direction =="right":
	# field = self.resources.fields[number]
	# field.has_value = False
	# field.value = None
	# self.resources.numbers[item - 1].value -= 1
	# self.resources.numbers_put -= 1
else:
	# field = self.resources.fields[number]
	# field.has_value = True
	# field.value = item
	# self.resources.numbers[item - 1].value += 1
	# self.resources.numbers_put += 1

else:
if type(item) is list:
if direction == "right":
	# self.resources.fields[number].small_numbers = self.resources.fields[number].small_numbers.__add__(item)
else:
	# for i in item:
	#     self.resources.fields[number].small_numbers.remove(i)
else:
if direction == "right":
	# field = self.resources.fields[number]
	# field.has_value = True
	# field.value = item
	# self.resources.numbers[item - 1].value += 1
	# self.resources.numbers_put += 1
else:
	 # if self.resources.chain_index != 0:
	 #    prev_action,prev_item,prev_number =  self.resources.action_chain[self.resources.chain_index - 1]
	 #    if prev_action == "deleted" and type(prev_item) is list:
	 #        field = self.resources.fields[prev_number]
	 #        field.small_numbers = field.small_numbers.__add__(prev_item)
	 #        field.has_value = False
	 #        field.value = None
	 #        self.resources.numbers[prev_number - 1].value -= 1
	 #        self.resources.numbers_put -= 1
	 #        self.resources.chain_index -= 1
	 #    else:
	 #        field = self.resources.fields[number]
	 #        field.has_value = False
	 #        field.value = None
	 #        self.resources.numbers[item - 1].value -= 1
	 #        self.resources.numbers_put -= 1
	 # else:
	 #    field = self.resources.fields[number]
	 #    field.has_value = False
	 #    field.value = None
	 #    self.resources.numbers[item - 1].value -= 1
	 #    self.resources.numbers_put -= 1