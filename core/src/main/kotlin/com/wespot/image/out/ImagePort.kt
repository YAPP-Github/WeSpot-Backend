package com.wespot.image.out

import com.wespot.image.Image

interface ImagePort {

    fun save(image: Image): Image

    fun deleteByUrl(url: String)

}
