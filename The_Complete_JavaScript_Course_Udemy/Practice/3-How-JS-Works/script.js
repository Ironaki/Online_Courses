///////////////////////////////////////
// Lecture: Hoisting


// The following code works, but it would not work for languages like python
exp(15);

function exp(cost) {
    console.log(cost);
}




var demand = 17;



function foo() {
    console.log(demand); // It will be undefined here. The global variable does not matter here.
    var demand = 71; // But if you comment out this line, the previous expression will go and find the gloabal variable. 
    console.log(demand); 
}

foo();

console.log(demand);


///////////////////////////////////////
// Lecture: Scoping


// First scoping example


var a = 'Hello!';
first();

function first() {
    var b = 'Hi!';
    second();

    function second() {
        var c = 'Hey!';
        console.log(a + b + c);
    }
}



// Example to show the differece between execution stack and scope chain

/*
var a = 'Hello!';
first();


function first() {
    var b = 'Hi!';
    second();

    function second() {
        var c = 'Hey!';
        third()
    }
}

function third() {
    var d = 'John';
    console.log(a + b + c + d);
}
*/



///////////////////////////////////////
// Lecture: The this keyword

console.log(this);

foo2();
function foo2() {
    console.log(this);
}







