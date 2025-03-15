package com.wespot.view

class TextLinesComponent(
    val type: String,
    val textLines: List<TextLineComponent>
) {

    companion object {

        private const val TYPE = "textListComponent"

        fun from(textLines: List<Pair<String, String>>): TextLinesComponent {
            val values: List<TextLineComponent> = textLines.stream()
                .map { TextLineComponent.of(it.first, it.second) }
                .toList()

            return TextLinesComponent(TYPE, values)
        }

    }

}
