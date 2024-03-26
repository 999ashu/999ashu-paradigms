'use strict';

const varMap = ['x', 'y', 'z'];
const variable = (v) => (...args) => args[varMap.indexOf(v)];

const cnst = (a) => () => (a);
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const operation = (f) => (...operands) => (...args) =>
    f(...operands.map(operand => operand(...args)));
const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation((a) => -a);

const square = operation((a) => a * a);
const sqrt = operation((a) => Math.sqrt(a));