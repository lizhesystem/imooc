"use strict";
/**
 * 常用语法之基础类型
 */
Object.defineProperty(exports, "__esModule", { value: true });
// 基础类型
// 布尔
var isDone = false;
var isShow = true;
// 数字
var decLiteral = 10;
var hexLiteral = 0x14;
var binaryLiteral = 20;
var octalLiteral = 20;
// 字符串
var name = 'lz';
var age = 20;
var sentence = "my name is " + name + " age is " + age;
// 数组 2种方式。
var list = [1, 2, 3];
var list1 = [3, 4, 5];
// 元祖 Tuple:元祖允许表一个已知元素数量和类型的数组，各元素的类型不必相同。
var y;
y = ['hello', 20]; // ok
// x = [10, 'hello'] // error
console.log(y[0].substr(1));
// console.log(y[1].substr(1))  // error：number不存在 substr 方法
// y[2] = 'eeeeeeeee'
// y[3] = '33333' // 这俩都error：越界了
// y[1] = '22' // error：索引1是number类型
y[1] = 20;
y[0] = 'yyy';
// 枚举:默认情况下，从 0 开始为元素编号,也可以手工修改编号
var Color;
(function (Color) {
    Color[Color["Red"] = 0] = "Red";
    Color[Color["Green"] = 2] = "Green";
    Color[Color["Blue"] = 3] = "Blue";
})(Color || (Color = {}));
var c = Color.Red;
var colorName = Color[2];
console.log(c);
console.log(colorName);
// enum Color {  也可以自己手工赋值
//     Red = 2, Green = 3, Blue = 4
// }
// any ：代表任何属性
/**
 * 有时候，我们会想要为那些在编程阶段还不清楚类型的变量指定一个类型。
 * 这些值可能来自于动态的内容，比如来自用户输入或第三方代码库。
 * 这种情况下，我们不希望类型检查器对这些值进行检查而是直接让它们通过编译阶段的检查。
 * 那么我们可以使用 any 类型来标记这些变量：
 */
var notSure = 3;
notSure = 'name';
notSure = 20; // 也可以是 number
var anyList = [1, 2, true, 'hello'];
console.log(anyList[2]);
// void :某种程度上来说，void 类型像是与 any 类型相反，它表示没有任何类型
function warnUser() {
    console.log('This is my warning message');
}
warnUser();
var unusable = undefined; //声明一个 void 类型的变量没有什么大用，因为你只能为它赋予 undefined 和 null：
// null 和 undefined
var u = null;
var n = undefined;
var uu = null;
// never :never 类型表示的是那些永不存在的值的类型。
// 例如， never 类型是那些总是会抛出异常或根本就不会有返回值的函数表达式或箭头函数表达式的返回值类型； 变量也可能是 never 类型，当它们被永不为真的类型保护所约束时。
// 返回never的函数必须存在无法达到的终点
function error(message) {
    throw new Error(message);
}
// 推断的返回值为never
function fail() {
    return error('something failed');
}
// 或者返回never的函数必须存在无法达到的终点
function infiniteLoop() {
    while (true) {
    }
}
// object :object 表示非原始类型，也就是除 number，string，boolean，symbol，null或undefined 之外的类型。
var create = function (o) {
};
create({ prop: 0 }); // OK
create(null); // OK
// create(42) // Error
// create('string') // Error
// create(false) // Error
// create(undefined) // Error


// 类型 断言
// 通过类型断言这种方式可以告诉编译器，“相信我，我知道自己在干什么”。
// 类型断言好比其它语言里的类型转换，但是不进行特殊的数据检查和解构。 它没有运行时的影响，只是在编译阶段起作用。
// TypeScript 会假设你，程序员，已经进行了必须的检查。

// 两种形式是等价的。 至于使用哪个大多数情况下是凭个人喜好；
// 然而，当你在 TypeScript 里使用 JSX 时，只有 as 语法断言是被允许的。
var someValue = 'this is a string';
var strLength = someValue.length;
var someValue1 = 'this is a string';
var strLength1 = someValue1.length;
