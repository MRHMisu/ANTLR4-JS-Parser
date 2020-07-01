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

// (7) Function declaration in conditionals with self calling function
(function () {
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

// (4) Function expression: starts with "const"
const isTruthy = function (value) {
    return !!value;
};

// (5) Function expression: an argument for .filter()
const numbers = ([1, false, 5]).filter(function (item) {
    return typeof item === 'number';
});

// (6) Function expression (IIFE): starts with "("
(function messageFunction(message) {
    return message + ' World!';
})('Hello');


// (7) Function declaration inside as function
const methods = {
    numbers: [1, 5, 8],
    sum: function () { // Function expression
        return this.numbers.reduce(function (acc, num) { // func. expression
            return acc + num;
        });
    }
}


    // Anonymous function, which name is an empty string.
    (
        function (variable) {
            return typeof variable;
        }
    ).name; // => ''


//anonymous is assigned to a variable:
const myFunctionVar = function (variable) {
    return typeof variable;
};

//shorthand method definition in an object literal:
const collection = {
    items: [],
    add(...items) {
        this.items.push(...items);
    },
    get(index) {
        return this.items[index];
    }
};

//the method definition looks this way:
const addMethod = 'add',
    getMethod = 'get';
const collection = {
    items: [],
    [addMethod](...items) {
        this.items.push(...items);
    },
    [getMethod](index) {
        return this.items[index];
    }
};

//Arrow function
const absValue = (number) => {
    if (number < 0) {
        return -number;
    }
    return number;
}

//Context transparency
class Numbers {
    constructor(array) {
        this.array = array;
    }

    addNumber(number) {
        if (number !== undefined) {
            this.array.push(number);
        }
        return (number) => {
            console.log(this === numbersObject); // => true
            this.array.push(number);
        };
    }
}

const numbersObject = new Numbers([]);
const addMethod = numbersObject.addNumber();


//new Function
function sum1(a, b) {
    return a + b;
}

const sum2 = function (a, b) {
    return a + b;
}
const sum3 = (a, b) => a + b;


const numberA = 'numberA', numberB = 'numberB';
const sumFunction = new Function(numberA, numberB,
    'return numberA + numberB'
);


//Generator function
function* indexGenerator(a, b) {
    var index = 0;
    while (true) {
        yield index++;
    }
}

//Function expression form function* ():
const indexGenerator = function* () {
    let index = 0;
    while (true) {
        yield index++;
    }
};

//Shorthand method definition form *<name>():
const obj = {
    * indexGenerator() {
        var index = 0;
        while (true) {
            yield index++;
        }
    }
}


