const calculation = document.querySelector('.calculation')
const result = document.querySelector('.result')
const btn = document.querySelectorAll('.btn')
const specialChars = ["%", "+", "-", "*", "/", "="]

let res

const update = btnValue => {
    calculation.value += btnValue
}

const calculate = btnValue => {
    switch(btnValue) {
        case 'AC':
            calculation.value = ""
            result.value = ""
            break;
        case 'DEL':
            calculation.value = calculation.value.slice(0, -1)
            break;
        case '=':
            calculation.value = eval(calculation.value.replace("=", "-"))
            result.value = calculation.value
        default:
            calculation.value += btnValue
    }
}

btn.forEach(button => {
    button.addEventListener("click", e => (
        calculate(e.target.dataset.value))
    )
    
})