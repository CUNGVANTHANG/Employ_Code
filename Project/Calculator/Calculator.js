export default class Calculator {

    constructor(container) {
        container.innerHTML = Calculator.getHTML()

        this.elements = {
            screenCalculation: document.querySelector(".screen-calculation"),
            screenResult: document.querySelector(".screen-result"),
            calcButtons: document.querySelector(".calc-buttons"),
            calculations: ""
        }

        this.elements.calcButtons.onclick = (e) => {
            this.buttonClick(e.target.innerText)
        }
    }

    get updateScreenCalculation() {
        return this.elements.screenCalculation.value
    } 

    set updateScreenCalculation(newValue) {
        this.elements.screenCalculation.value = newValue
    }

    get updateScreenResult() {
        return this.elements.screenResult.value
    } 

    set updateScreenResult(newValue) {
        this.elements.screenResult.value = newValue
    } 

    get updateHandleCalculations() {
        return this.elements.calculations
    }

    set updateHandleCalculations(newValue) {
        this.elements.calculations = newValue
    }

    calculateResult() {
        try {
            const result = eval(this.updateHandleCalculations)
            this.updateScreenResult = result
        }
        catch(error) {
            this.updateScreenResult = "Math Error"
        }
    }

    handleCalculations(value) {
        switch(value) {
            case "%":
                this.updateHandleCalculations += "/100"
                break
            case "÷":
                this.updateHandleCalculations += "/"
                break
            case "×":
                this.updateHandleCalculations += "*"
                break
            case "−":
                this.updateHandleCalculations += "-"
                break
            case "+":
                this.updateHandleCalculations += "+"
                break
            case ".":
                this.updateHandleCalculations += "."
                break
            default:
                this.updateHandleCalculations += value
                break
        }
    }

    handleSymbol(symbol) {
        switch(symbol) {
            case "C":
                this.updateScreenCalculation = null
                this.updateScreenResult = null
                this.updateHandleCalculations = ""
                break
            case "←":
                if (this.updateScreenCalculation.includes("%")) {
                    this.updateHandleCalculations = this.updateHandleCalculations.slice(0, -4)
                }
                else {
                    this.updateHandleCalculations = this.updateHandleCalculations.slice(0, -1)
                }
                this.updateScreenCalculation = this.updateScreenCalculation.slice(0, -1)
                break
            case "%":
            case "÷":
            case "×":
            case "−":
            case "+":
            case ".":
                this.updateScreenCalculation += symbol
                this.handleCalculations(symbol)
                break
            case "=":
                this.calculateResult()
                break
        }
    }

    handleNumber(numberString) {
        this.updateScreenCalculation += numberString
        this.handleCalculations(numberString)
    }

    buttonClick(value) {
        if (isNaN(value)) {
            this.handleSymbol(value)
        }
        else {
            this.handleNumber(value)
        }
    }

    static getHTML() {
        return `
        <header class="name-app">Calculator</header>
        <section class="screen">
            <input type="text" class="screen-calculation">
            <input type="text" class="screen-result">
        </section>
        <section class="calc-buttons">
            <button data-value="C" class="calc-button">C</button>
            <button data-value="DEL" class="calc-button">&larr;</button>
            <button data-value="%" class="calc-button operator">&percnt;</button>
            <button data-value="/" class="calc-button operator">&divide;</button>
            <button data-value="7" class="calc-button">7</button>
            <button data-value="8" class="calc-button">8</button>
            <button data-value="9" class="calc-button">9</button>
            <button data-value="*" class="calc-button operator">&times;</button>
            <button data-value="4" class="calc-button">4</button>
            <button data-value="5" class="calc-button">5</button>
            <button data-value="6" class="calc-button">6</button>
            <button data-value="-" class="calc-button operator">&minus;</button>
            <button data-value="1" class="calc-button">1</button>
            <button data-value="2" class="calc-button">2</button>
            <button data-value="3" class="calc-button">3</button>
            <button data-value="+" class="calc-button operator">&plus;</button>
            <button data-value="00" class="calc-button">00</button>
            <button data-value="0" class="calc-button">0</button>
            <button data-value="." class="calc-button">.</button>
            <button data-value="=" class="calc-button operator">&equals;</button>
        </section>
        `
    }
}