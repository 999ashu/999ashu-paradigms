'use strict';
const operation = (f) => (...operands) => (...args) =>
    f(...operands.map(operand => operand(...args)));
const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation((a) => -parseFloat(a));

const square = operation((a) => a * a);
const sqrt = operation((a) => Math.sqrt(a));
const avg5 = operation((a, b, c, d, e) => (a + b + c + d + e) / 5);
const med3 = operation((a, b, c) => (a + b + c) - Math.max(a, b, c) - Math.min(a, b, c));

const opMap = {
    'avg5': [avg5, 5], 'med3': [med3, 3], '+': [add, 2], '-': [subtract, 2], '*': [multiply, 2],
    '/': [divide, 2], 'negate': [negate, 1], 'square': [square, 1], 'sqrt': [sqrt, 1]
};

const cnst = (n) => () => parseFloat(n);

const pi = cnst(Math.PI);
const e = cnst(Math.E);

const cnstMap = {'pi': pi, 'e': e};

const vArr = ['x', 'y', 'z']
const variable = (v) => (...args) => parseFloat(args[vArr.indexOf(v)]);

const value = (v) => ((vArr.indexOf(v) !== -1) ? variable(v) : cnst(parseFloat(v)));

const makeExpression = (stack) => {
    let peek = stack.pop();
    if (opMap.hasOwnProperty(peek)) {
        let args = [];
        for (let i = opMap[peek][1] - 1; i >= 0; i--) args[i] = makeExpression(stack);
        return opMap[peek][0](...args);
    } else {
        return (cnstMap.hasOwnProperty(peek) ? cnstMap[peek] : value(peek));
    }
}

const parse = (expression) => (...args) => {
    return makeExpression(expression.split(' ').filter(e => e.length > 0))(...args);
}
