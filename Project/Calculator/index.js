const theme = document.querySelectorAll('.theme i');
let currentIndex = 0

function toggleTheme() {
    theme[currentIndex].classList.remove("active");
    currentIndex = (currentIndex + 1) % theme.length;
    theme[currentIndex].classList.add("active");
    const root = document.documentElement

    Object.assign(root.style, {
        '--text': '#fff',
        '--screen': '##36474f',
        '--calc': '#18212a',
        '--theme': '#fff'
    })
}

theme[currentIndex].addEventListener("click", toggleTheme);
theme[currentIndex + 1].addEventListener("click", toggleTheme)


const screenCal = document.querySelector('.screen-calculation')
const screenRes = document.querySelector('.screen-result')
let updateScreenCal = ""
let updateScreenRes = ""
let buffer = ""
let runningTotal = 0
let operator

function buttonClick(value) {
    if (isNaN(value)) {
        handleSymbol(value)
    }
    else {
        handleNumber(value)
    }
    screenCal.innerText = updateScreenCal;
    screenRes.innerText = updateScreenRes;
}


function handleNumber(numberString) {
    if (buffer === "") {
        buffer += numberString
        updateScreenCal += buffer
    }
    else {
        buffer = updateScreenCal
        buffer += numberString
        updateScreenCal = buffer
    }
}

function handleSymbol(symbol) {
    switch(symbol) {
        case "←":
            if (updateScreenCal.length === 1) {
                updateScreenCal = ""
            }
            else {
                updateScreenCal = updateScreenCal.slice(0, -1)
            }
            break
        case "C":
            updateScreenCal = ""
            updateScreenRes = ""
            buffer = ""
            break
        case "%":
        case "+":
        case "÷": 
        case "×":
        case "−":
            updateScreenCal += symbol
            handleMath(symbol)
            break
        case ".":
            buffer += symbol
            updateScreenCal += symbol
            handleMath(symbol)
            break
        case "=":
            break
    }
}

function handleMath(symbol) {
    if (buffer === '0') {
        return;
    }

    console.log(buffer)
    console.log(runningTotal)

    const floatScreen = parseFloat(buffer)
    
    if (runningTotal === 0) {
        runningTotal = floatScreen
    }
    else {
        
        flushOperation(floatScreen)
        console.log(runningTotal)

    }
    operator = symbol
    buffer = ""
    
}

function flushOperation(floatScreen) {
    switch(operator) {
        case "%":
            runningTotal *= floatScreen/100
            break;
        case "+":
            runningTotal += floatScreen
            break;
        case "÷": 
            runningTotal /= floatScreen
            break;
        case "×":
            runningTotal *= floatScreen
            break;
        case "−":
            runningTotal -= floatScreen
            break;
    }
}

function init() {
    document.querySelector('.calc-buttons')
    .addEventListener('click', event => {
        console.log(event.target.innerText)
        buttonClick(event.target.innerText)
    })
}

init()

