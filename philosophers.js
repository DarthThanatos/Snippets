var N = 20;
var globalTime = 0;

var Fork = function() {
    this.state = 0;
    return this;
}

var Conductor = function(){
	this.state = N-1;
	return this;
}

Conductor.prototype.enter = function(cb){
	//it behaves a little different than the 
	//usual fork -> active states are between 
	//1 - 4 values, 0 is the waiting state
	var self = this;
	var check = function(time_to_wait_cond){
		if (self.state === 0){
			globalTime += time_to_wait_cond;
			setTimeout(check,time_to_wait_cond,2*time_to_wait_cond);
		}
		else{
			self.state--;
			cb();
		}
	}
	setTimeout(check,1,1);
}

Conductor.prototype.leave = function(id) { 
	//console.log("Philosopher " + id + " leaves the bar\n");
    this.state++; 
}

Fork.prototype.acquire = function(cb) {
	var self = this;
	var check = function(time_to_wait) {
		if (self.state === 0) {
			//  ^  The identity (===) operator behaves
			//identically to the equality (==) 
			//operator except no type conversion 
			//is done, and the types must be the 
			//same to be considered equal.
			self.state = 1;
			cb(); 
		}
		else{
			globalTime += time_to_wait;
			setTimeout(check,time_to_wait,2*time_to_wait);
			// ^ setTimeout(callback, delay[, arg][, ...])
		}
	}
	setTimeout(check,1,1);
}

Fork.prototype.release = function(id, fork_id) { 
	console.log("Philosopher " + id + " released fork no " + fork_id);
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
		f1 = this.f1,
		f2 = this.f2,
		id = this.id;
    for (var i = 0; i < count; i++) {
		forks[f1].acquire(
			function(){
				console.log("philosopher " + id + " picked up fork", f1);
				forks[f2].acquire(
					function() {
						console.log("philosopher " + id + " picked up fork", f2);
						console.log("philosopher " + id +" eating");
						forks[f1].release(f1);
						forks[f2].release(f2);
					}
				);
			}

		);
    }
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startAsym = function(count) {
	var forks = this.forks,
		f1 = this.f1,
		f2 = this.f2,
		id = this.id,
		j1,j2;
	if(id % 2 == 1){
		j1 = id;
		j2 = (id+1)%N;
	}
	else{
		j1 = (id+1)%N;
		j2 = id;
	}
	for (var i = 0; i < count; i++) {
		forks[j1].acquire(
			function() {
				console.log("philosopher " + id + " picked up fork", j1);
				forks[j2].acquire(
					function() {
						console.log("philosopher " + id + " picked up fork", j2);
						console.log("philosopher " + id + " eating");
						forks[j2].release(id, j2);
						forks[j1].release(id, j1);
						console.log("Global time: " +  globalTime + " mili seconds \n");
					}
				)
			}
		);
    }
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

var conductor = new Conductor();

Philosopher.prototype.startConductor = function(count) {
	var forks = this.forks,
		f1 = this.f1,
		f2 = this.f2,
		id = this.id;
	for (var i = 0; i < count; i++) {
		conductor.enter(
			function(){
				forks[f1].acquire(
					function(){
						console.log("philosopher " + id + " picked up fork", f1);
						forks[f2].acquire(
							function() {
								console.log("philosopher " + id + " picked up fork", f2);
								console.log("philosopher " + id +" eating");
								forks[f1].release(id,f1);
								forks[f2].release(id,f2);
								conductor.leave(id);
								console.log("Global time: " +  globalTime + " mili seconds \n");
							}
						);
					}
				);
			}, id
		);

    }
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

var forks = [];
var philosophers = []

for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    philosophers[i].startNaive(4);
}
