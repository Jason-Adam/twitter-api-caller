package com.aptiveresources.sptwitterapi.modules

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

object ResourceLoader {

    fun loadFileAsResource(): String? {
        javaClass.getResourceAsStream("/twitter-search.json").use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                return reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()))
            }
        }
    }
}
