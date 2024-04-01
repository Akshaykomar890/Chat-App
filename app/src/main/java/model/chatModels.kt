package model

data class chatModels( val name:String?= null,
                       val id:String? = null,
                       val number:String? =null,
                       val password:String? =null
){
constructor():this("","",""){

}
}
