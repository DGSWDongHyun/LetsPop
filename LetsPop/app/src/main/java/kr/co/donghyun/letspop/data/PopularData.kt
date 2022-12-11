package kr.co.donghyun.letspop.data

data class PopularData(
    var dislike : Int = 0,
    var like : Int = 0,
    val link : String? = "",
    val name : String? = ""
) {
    fun copy() : PopularData {
        return this
    }
}