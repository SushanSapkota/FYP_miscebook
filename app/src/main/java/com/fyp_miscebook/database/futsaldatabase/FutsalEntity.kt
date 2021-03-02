package com.fyp_miscebook.database.futsaldatabase

class FutsalEntity {

    var id: Int = 0
    var name: String = ""
    var address: String = ""
    var contact: String = ""

    constructor(
        name: String,
        address: String,
        contact: String
    ) {
        this.name = name
        this.address = address
        this.contact = contact
    }

    constructor()
}