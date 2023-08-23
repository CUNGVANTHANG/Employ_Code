const stars = document.querySelector('.stars')
const moon = document.querySelector('.moon')

window.addEventListener('scroll', 
function () {
    let value = window.scrollY
    stars.style.left = value * 0.25 + 'px'
    moon.style.top  = value * 1.05 + 'px'
})

const behind = document.querySelector('.behind')
const front = document.querySelector('.front')

window.addEventListener('scroll',
function () {
    let value = window.scrollY
    behind.style.top = value * 0.5 + 'px'
    front.style.top = value * 0 + 'px'
})

const txt = document.querySelector('.txt')
const btn = document.querySelector('.btn')

window.addEventListener('scroll', 
function () {
    let value = window.scrollY
    txt.style.marginRight = value * 4 + 'px'
    txt.style.marginTop = value * 1.5 + 'px'
    btn.style.marginTop = value * 1.5 + 'px'
})

const header = document.querySelector('header')

window.addEventListener('scroll', 
function () {
    let value = window.scrollY
    header.style.top = value * 0.5 + 'px'
})