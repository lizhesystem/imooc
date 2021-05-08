// 定义User类
var User = /** @class */ (function () {
    function User(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + ' ' + lastName;
    }
    return User;
}());
// function greeter(person: string) {
//     return 'Hello ' + person
// }

// 传入Person类型
// 因为实例化的 user 对象里有 firstName 和 lastName，所以 Person 接口可以使用。
function greeter(person) {
    return 'Hello ' + person.firstName + person.lastName;
}
// let person = 'lz'
// let User = {
//     firstName: 'L',
//     lastName: 'z'
// }
var user = new User('L', 'Z');
console.log(greeter(user));
