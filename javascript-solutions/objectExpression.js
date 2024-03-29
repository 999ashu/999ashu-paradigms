'use strict'

function value(object, evaluate, toString) {
    object.prototype.evaluate = evaluate;
    object.prototype.toString = toString;
}

const opMap = {};
function operation(object, operator, sign) {
    object.prototype.sign = sign;
    value(
        object,
        function (...args) {return (operator(...this.values.map((e) => e.evaluate(...args))))},
        function () {return (this.values.join(' ') + ' ' + this.sign)}
        );
    opMap[sign] = [(...args) => new object(...args), object.length];
}


function Const(c) {this.value = c}
function Variable(v) {this.value = v}
function Add(e1, e2) {this.values = [e1, e2]}
function Subtract(e1, e2) {this.values = [e1, e2]}
function Multiply(e1, e2) {this.values = [e1, e2]}
function Divide(e1, e2) {this.values = [e1, e2]}
function Hypot(e1, e2) {this.values = [e1, e2]}
function HMean(e1, e2) {this.values = [e1, e2]}
function Negate(e) {this.values = [e]}
function Sin(e) {this.values = [e]}
function Cos(e) {this.values = [e]}


const vArr = ['x', 'y', 'z']
value(
    Const,
    function () {return this.value},
    function () {return (this.value).toString()}
    );
value(
    Variable,
    function (...args) {return parseFloat(args[vArr.indexOf(this.value)])},
    function () {return (this.value).toString()}
    );

operation(Add, (a, b) => (a + b), '+');
operation(Subtract, (a, b) => (a - b), '-');
operation(Multiply, (a, b) => (a * b), '*');
operation(Divide, (a, b) => (a / b), '/');
operation(Negate, (a) => (-a), 'negate');
operation(Sin, Math.sin, 'sin');
operation(Cos, Math.cos, 'cos');

const constVar = (v) => ((vArr.indexOf(v) === -1) ? new Const(parseFloat(v)) : new Variable(v));

const makeExpression = (stack) => {
    let peek = stack.pop();
    if (peek in opMap) {
        let args = []
        for (let i = opMap[peek][1] - 1; i >= 0; i--) {
            args[i] = (makeExpression(stack))
        }
        return opMap[peek][0](...args);
    }
    return constVar(peek)
}

const parse = (expression) => {
    return makeExpression(expression.split(' ').filter(e => e.length > 0));
}