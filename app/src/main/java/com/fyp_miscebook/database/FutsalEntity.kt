package com.fyp_miscebook.database

class FutsalEntity {

    var id: Int = 0
    var name: String = ""
    var address: String = ""
    var email: String = ""
    var start: String = ""
    var end: String = ""
    var bookwhen: String = ""
    var no_ofplayers: String = ""
    var image: String = ""

    constructor(
        name: String,
        address: String,
        email: String,
        start: String,
        end: String,
        bookwhen: String,
        no_ofplayers: String,
        image: String
    ) {
        this.name = name
        this.address = address
        this.email = email
        this.start = start
        this.end = end
        this.bookwhen = bookwhen
        this.no_ofplayers = no_ofplayers
        this.image = image
    }

    constructor()
}