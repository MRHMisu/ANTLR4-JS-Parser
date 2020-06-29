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



(function messageFunction(message) {
    return message + ' World!';
})('Hello');


