package com.example.firebasenotes.playgrounds

fun main () {

    var data =
        getInfo().onSuccess {

        }.onFailure {

        }


}

 fun getInfo(): Result<List<String>> {

    return try {

        var data = emptyList<String>()
        Result.success(data)

    } catch (e: Exception){

        Result.failure(e)
    }

}