package com.wespot.user.dto.response

data class CharacterListResponse(
    val characters: List<CharacterResponse>
) {

    companion object {

        fun from(characters: List<CharacterResponse>): CharacterListResponse {
            return CharacterListResponse(
                characters = characters
            )
        }

    }
}
