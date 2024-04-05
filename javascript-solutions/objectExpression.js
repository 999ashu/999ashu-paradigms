'use strict'

function value(object, evaluate, toString, prefix) {
    object.prototype.evaluate = evaluate;
    object.prototype.toString = toString;
    object.prototype.prefix = prefix;
    return object;
}

const opMap = {};
const opLen = (op) => (opMap[op].length === 0 ? Infinity : opMap[op].length);
function operation(object, operator, sign) {
    object.prototype.sign = sign;
    value(object,
        function (...args) {return (operator(...this.values.map((e) => e.evaluate(...args))))},
        function () {return (this.values.join(' ') + ' ' + this.sign)},
        function () {return ('(' + this.sign + ' ' + (this.values.map((e) => e.prefix())).join(' ') + ')')}
        );
    opMap[sign] = object;
    return object;
}

const vArr = ['x', 'y', 'z']
const mean = (...args) => (args.reduce((a, b) => a + b, 0) / args.length)

// :NOTE: generalize
const Const = value(function (c) {this.value = c}, function () {return this.value}, function () {return (this.value).toString()}, function () {return (this.value).toString()});
const Variable = value(function (c) {this.value = c}, function (...args) {return parseFloat(args[vArr.indexOf(this.value)])}, function () {return (this.value).toString()}, function () {return (this.value).toString()});
const Add = operation(function (e1, e2) {this.values = [e1, e2]}, (a, b) => (a + b), '+');
const Subtract = operation(function (e1, e2) {this.values = [e1, e2]}, (a, b) => (a - b), '-');
const Multiply = operation(function (e1, e2) {this.values = [e1, e2]}, (a, b) => (a * b), '*');
const Divide = operation(function (e1, e2) {this.values = [e1, e2]}, (a, b) => (a / b), '/');
const Negate = operation(function (e) {this.values = [e]}, (a) => (-a), 'negate');
const Sin = operation(function (e) {this.values = [e]}, Math.sin, 'sin');
const Cos = operation(function (e) {this.values = [e]}, Math.cos, 'cos');
const Mean = operation(function (...args) {this.values = args}, (...args) => (mean(...args)), 'mean');
const Var = operation(function (...args) {this.values = args}, (...args) => (mean(...args.map(e => e * e)) - (e => e * e)(mean(...args))), 'var');

function ParsingError(message) {
    this.message = message;
}
ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.name = "ParsingError";

function error(error, name) {
    error.prototype = Object.create(ParsingError.prototype);
    error.prototype.name = name;
    return error;
}

const IncorrectBracketSequenceError = error(function (message) {this.message = message}, 'IncorrectBracketSequenceError')
const UnexpectedSymbolError = error(function (message) {this.message = message}, 'UnexpectedSymbolError')
const InvalidTokenError = error(function (message) {this.message = message}, 'InvalidTokenError')
const InvalidExpStructureError = error(function (message) {this.message = message}, 'InvalidExpStructureError')


const constVar = (v) => ((vArr.indexOf(v) === -1) ? new Const(parseFloat(v)) : new Variable(v));

const makeExpression = (stack) => {
    let peek = stack.pop();
    if (peek in opMap) {
        let args = []
        for (let i = opMap[peek].length - 1; i >= 0; i--) {
            args[i] = (makeExpression(stack))
        }
        return new opMap[peek](...args);
    }
    return constVar(peek)
}

const parse = (expression) => {
    return makeExpression(expression.split(/\s/).filter(Boolean));
}

const checkBrackets = (stack, inProgress = true) => {
    if (stack.length === 0) {
        throw new InvalidExpStructureError('Empty expression.');
    }
    if (stack[0] === '(') {
        stack.shift()
        if (!(stack[0] in opMap)) {
            throw new InvalidExpStructureError('Incorrect prefix form. Not operation received: ' + stack[0] + '.');
        }
        let expr = makeExpressionByPrefix(stack);
        if (stack[0] === ')') {
            stack.shift();
            return expr;
        } else {
            throw new IncorrectBracketSequenceError('No closing parenthesis.');
        }
    } else if (inProgress) {
        if (stack[0] in opMap) {
            throw new InvalidTokenError('Received incorrect token of argument: ' + stack[0] + '.');
        }
        return makeExpressionByPrefix(stack)
    } else {
        let expr = makeExpressionByPrefix(stack)
        if (stack.length !== 0) {
            throw new InvalidExpStructureError('End of expression expected, but received: ' + stack[0] + '.');
        }
        return expr;
    }
}

const makeExpressionByPrefix = (stack) => {
    let peek = stack.shift();
    if (peek in opMap) {
        let args = []
        while (stack[0] !== ')' && args.length < opLen(peek)) {
            args.push(checkBrackets(stack));
        }
        if (opMap[peek].length !== 0 && args.length < opMap[peek].length) {
            throw new InvalidTokenError('Not enough arguments for ' + peek + ' operation.');
        }
        return new opMap[peek](...args);
    } else if (vArr.indexOf(peek) !== -1 || !isNaN(peek)) {
        return constVar(peek);
    } else {
        throw new UnexpectedSymbolError('Unexpected symbol received: ' + peek + '.');
    }
}

const parsePrefix = (expression) => {
    let stack = expression.split(/\s+|([()])/).filter(Boolean);
    let expr = checkBrackets(stack, false);
    if (stack.length !== 0) {
        throw new ParsingError();
    }
    return expr;
}
