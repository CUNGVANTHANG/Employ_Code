export default class Stopwatch {
    constructor(container) {
        container.innerHTML = Stopwatch.getHTML()

        this.elements = {
            hours: document.querySelector(".hours"),
            minutes: document.querySelector(".minutes"),
            seconds: document.querySelector(".seconds"),
            btnControl: document.querySelector('.btn-control'),
            btnReset: document.querySelector(".btn-reset"),
            btnChange: document.querySelector(".btn-change"),
            btnMode: document.querySelector(".btn-mode")
        }

        this.interval = null
        this.remainingSeconds = 0

        this.elements.btnControl.onclick = (e) => {
            if (this.interval == null) {
                this.play()
            }
            else {
                this.pause()
            }
        }
    }

    updateInterfaceControl() {
        if (this.interval == null) {
                
            this.elements.btnControl.innerHTML(`<i class="fa-solid fa-play"></i>`)
        }
    }

    updateInterfaceTime() {

    }

    play() {

    }

    pause() {

    }

    change() {

    }

    mode() {

    }


    static getHTML() {
        return `
        <header class="name-app">Stopwatch</header>
        <section class="timer">
            <div class="circle"></div>
            <div class="time">
                <div class="hours">00</div>
                <span>:</span>
                <div class="minutes">00</div>
                <span>:</span>
                <div class="seconds">00</div>
            </div>
        </section>
        <section class="btn">
            <button class="btn-control">
                <i class="fa-solid fa-play"></i>
            </button>
            <button class="btn-reset">
                <i class="fa-solid fa-rotate-left"></i>
            </button>
            <button class="btn-change">
                <i class="fa-solid fa-clock"></i>
            </button>
            <button class="btn-mode">
                <i class="fa-solid fa-gears"></i>
            </button>
        </section>
        `
    }
}