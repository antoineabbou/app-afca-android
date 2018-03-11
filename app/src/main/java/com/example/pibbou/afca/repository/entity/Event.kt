package com.example.pibbou.afca.repository.entity

import java.util.*


/**
 * Created by arnaudpinot on 02/01/2018.
 */

class Event {
    internal var id: Int? = null
    internal var name: String? = null
    internal var excerpt: String? = null
    internal var placeId: Int? = null
    internal var place: Place? = null
    internal var categoryId: Int? = null
    internal var category: Category? = null
    internal var day: Int? = null
    internal var public: Int? = null
    internal var startingDate: Date? = null
    internal var endingDate: Date? = null
    internal var image: String? = null
    internal var link: String? = null
    internal var author: String? = null
    internal var duration: String? = null
}
