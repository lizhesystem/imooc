/**
 * 常用语法之原始基础类型
 */

// 基础类型

// 布尔
let isDone: boolean = false
let isShow: boolean = true;
// let isFlag:boolean = new Boolean(1)  error

// 数字
let decLiteral: number = 10
let hexLiteral: number = 0x14
let binaryLiteral: number = 0b10100
let octalLiteral: number = 0o24

// 字符串
let name: string = 'lz'
let age: number = 20
let sentence: string = `my name is ${name} age is ${age}`

// 数组 2种方式。
let list: number[] = [1, 2, 3]
let list1: Array<number> = [3, 4, 5]

interface NumberArray {
    [index:number]:number;
}

let listArray:NumberArray = [1,2,3,4,5]

function sum() {
    let args: {
        [index: number]: number;
        length: number;
        callee: Function;
    } = arguments;
}
function sum1() {
    let args: IArguments = arguments;
}

// 元祖 Tuple:元祖允许表一个已知元素数量和类型的数组，各元素的类型不必相同。
let y: [string, number]
y = ['hello', 20]  // ok

// y = [10, 'hello'] // error
console.log(y[0].substr(1))
// console.log(y[1].substr(1))  // error：number不存在 substr 方法

// y[2] = 'eeeeeeeee'
// y[3] = '33333' // 这俩都error：越界了不能访问
// y[1] = '22' // error：索引1是number类型
y[1] = 20;
y[0] = 'yyy'

// 枚举:默认情况下，从 0 开始为元素编号,也可以手工修改编号
enum Color {
    Red = 0, Green = 2, Blue = 3
}

let c: Color = Color.Red
let colorName: string = Color[2]
console.log(c)
console.log(colorName)

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
let notSure: any = 3
notSure = 'name'
notSure = 20 // 也可以是 number
let anyList: any[] = [1, 2, true, 'hello']
console.log(anyList[2])

let something; // 等价于 let something:any;
something = 'seven'
something = 2
something.setName('lz')

// void :某种程度上来说，void 类型像是与 any 类型相反，它表示没有任何类型

function warnUser(): void {
    console.log('This is my warning message')
}

warnUser()
let unusable: void = undefined //声明一个 void 类型的变量没有什么大用，因为你只能为它赋予 undefined 和 null：

// null 和 undefined
let u: null = null
let n: undefined = undefined
let uu: undefined = null

let u1: undefined
let num: number = u1
let num1: string = u1

let i: void
// let i1:undefined = i  error
// let i1:null = i

// never :never 类型表示的是那些永不存在的值的类型。
// 例如， never 类型是那些总是会抛出异常或根本就不会有返回值的函数表达式或箭头函数表达式的返回值类型； 变量也可能是 never 类型，当它们被永不为真的类型保护所约束时。

// 返回never的函数必须存在无法达到的终点
function error(message: string): never {
    throw new Error(message)
}

// 推断的返回值为never
function fail() {
    return error('something failed')
}

// 或者返回never的函数必须存在无法达到的终点
function infiniteLoop(): never {
    while (true) {
    }
}

interface Foo {
    type: 'foo'
}

interface Bar {
    type: 'bar'
}

type All = Foo | Bar


// object :object 表示非原始类型，也就是除 number，string，boolean，symbol，null或undefined 之外的类型。
const create = (o: object | null): void => {};

create({prop: 0}) // OK
create(null) // OK

// create(42) // Error
// create('string') // Error
// create(false) // Error
// create(undefined) // Error

// 类型 断言
let someValue: any = 'this is a string'
let strLength: number = (<string>someValue).length

let someValue1: any = 'this is a string'
let strLength1: number = (someValue1 as string).length


// name 变量冲突，我们在脚本的最后一行，添加了 export {};。将文件声明为 module，
// 变量 name 被限制在了 module 的作用域下，因此不会与全局的name产生冲突。
export {}