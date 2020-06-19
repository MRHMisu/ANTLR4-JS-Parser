// regular
function sum(a, b) {
    return a + b;
}

sum(5, 6);           // => 11
([3, 7]).reduce(sum) // => 10

function factorial(n) {
    if (n === 0) {
        return 1;
    }
    return n * factorial(n - 1);
}

factorial(4); // => 24

// nested
function myFunction(a, b) {
    function nestFunction1(c, d) {
        return c + d;
    }

    return a * b;
}


// Function expression: starts with "const"
const isTruthy = function(value) {
    return !!value;
};

// Function expression: an argument for .filter()
const numbers = ([1, false, 5]).filter(function(item) {
    return typeof item === 'number';
});

// Function expression (IIFE): starts with "("
(function messageFunction(message) {
    return message + ' World!';
})('Hello');


//Function declaration in conditionals
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