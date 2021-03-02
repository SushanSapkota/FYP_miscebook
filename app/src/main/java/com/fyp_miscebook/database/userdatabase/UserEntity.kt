package com.fyp_miscebook.database.userdatabase

class UserEntity {

    var id : Int = 0
    var firstname : String = ""
    var middlename : String = ""
    var lastname : String = ""
    var email : String = ""
    var username : String = ""
    var password : String = ""
    var address : String = ""
    var mobile : String = ""

    constructor(firstname:String,middlename:String,lastname:String,email:String,username:String,password:String,address:String,mobile:String){
        this.firstname = firstname
        this.middlename = middlename
        this.lastname = lastname
        this.email = email
        this.username = username
        this.password = password
        this.address = address
        this.mobile = mobile
    }

    constructor(){
    }
}