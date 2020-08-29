interface LabelValue {
    label: string
}

function printLabel(labelledObj: LabelValue) {
    console.log(labelledObj.label)
}

printLabel({label: "lz", age: 10} as LabelValue)
let myObj = {label: 'lz', size: 10}
printLabel(myObj) // 可以