// Variable

// console.log("Hello World, Again");

// var name = "Asuna";
// var surname = "Yuki";

// console.log(surname);
// console.log(name);

// var age = 17;
// console.log(age);


// Variable 2
var name = "Asuna";
var age = 17;

console.log(name + age);
console.log(age + age);

var job;
console.log(job); // Undefined at this time

job = "Student";
console.log(job);

// var anotherName = prompt("What is another character?");
// console.log(anotherName);

// alert("The name of the other charcter is " + anotherName);

// Double equal allows type coercion, but triple equal does not

if (23 == "23") {
    console.log("Double equal allows type coersion.");
}

if (23 === "23") {
    console.log("Something wrong here.");
} else {
    console.log("Triple equal does not.");
}

// Coding Challenge
// var johnHeight = Number(prompt("What is John's height?"));
// var johnAge = Number(prompt("What is John's age?"));
// var otherHeight = Number(prompt("What is the other's age?"));
// var otherAge = Number(prompt("What is the other's age?"));

// var johnScore = johnHeight + johnAge * 5;
// var otherScore = otherHeight + otherAge * 5;

// if (johnScore > otherScore) {
//     console.log("John wins");
// } else if (johnScore < otherScore) {
//     console.log("Other wins");
// } else {
//     console.log("Draw");
// }


// function
var fun = function() {
    console.log("called function");
}

fun();

// Object
var asuna = {
    name: "Asuna",
    surname: "Yuki",
    age: 17,
    marry: function() {
        this.husband = {
            name: "Kazuto",
            surname: "Kirigaya",
            age: 16
        };
    }
};

console.log(asuna);
console.log(asuna.name);
console.log(asuna["name"]);
asuna.marry();
console.log(asuna);
