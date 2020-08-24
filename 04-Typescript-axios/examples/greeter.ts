// 定义User类
class User {
    fullName: string
    firstName: string
    lastName: string

    constructor(firstName: string, lastName: string) {
        this.firstName = firstName
        this.lastName = lastName
        this.fullName = firstName + ' ' + lastName
    }
}

// 定义接口
interface Person {
    firstName: string
    lastName: string
}


// function greeter(person: string) {
//     return 'Hello ' + person
// }

// 传入Person类型
// 因为实例化的 user 对象里有 firstName 和 lastName，所以 Person 接口可以使用。
function greeter(person: Person) {
    return 'Hello ' + person.firstName + person.lastName
}

// let person = 'lz'

// let User = {
//     firstName: 'L',
//     lastName: 'z'
// }

let user = new User('L','Z');

console.log(greeter(user))
