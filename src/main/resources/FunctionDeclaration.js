// (1) traditional function declaration
function sum(a, b) {
    return a + b;
}

// (2) Function declaration: starts with "function"
function factorial(n) {
    if (n === 0) {
        return 1;
    }
    return n * factorial(n - 1);
}


// (3) nested function declaration with function keywords
function myFunction(a, b) {
    function nestFunction1(c, d) {
        return c + d;
    }

    return a * b;
}

// (7) Function declaration with self-calling anonymous functional expression
(function() {
    'use strict';
    if (true) {
        function ok() {
            return 'true ok';
        }
    } else {
        function ok() {
            return 'false ok';
        }
    }
    console.log(typeof ok === 'undefined'); // => true
    console.log(ok()); // Throws "ReferenceError: ok is not defined"
})();

// (7) Function declaration inside as function
const methods = {
    numbers: [1, 5, 8],
    sum: function() { // Function expression
        return this.numbers.reduce(function(acc, num) { // func. expression
            return acc + num;
        });
    }
}


// (6) Function declaration (IIFE): starts with "("
(function messageFunction(message) {
    return message + ' World!';
})('Hello');


// Anonymous function, which name is an empty string.
(
    function(variable) {
        return typeof variable;
    }
).name; // => ''


//Arrow function
const absValue = (number) => {
    if (number < 0) {
        return -number;
    }
    return number;
}

const numberA = 'numberA', numberB = 'numberB';
const sumFunction = new Function(numberA, numberB,
    'return numberA + numberB'
);

