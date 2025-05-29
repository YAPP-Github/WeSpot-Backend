package com.wespot.view.text

class TextListComponent(
    val type: String,
    val textList: List<TextLineComponent>
) {

    companion object {

        private const val TYPE = "textListComponent"

        fun from(textLines: List<Pair<String, RichText>>): TextListComponent {
            val values: List<TextLineComponent> = textLines.stream()
                .map { TextLineComponent.of(it.first, it.second) }
                .toList()

            return TextListComponent(TYPE, values)
        }

    }

}
