package com.example.parahabit.timer

class Timer {

     var started = false
        get() {
            return field
        }
        set(value) {
            field = value}
    // TODO: pomyśleć, jak to powinno działać

    fun start(){
        started = true
        // TODO: zaimplementować to
    }

    fun stop(){
        started = false
        // TODO: zaimplementować to
    }

    fun pause(){

    }



    fun getTime():Int{
        return 1
    }
}