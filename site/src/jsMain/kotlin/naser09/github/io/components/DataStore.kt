package naser09.github.io.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.*
import naser09.github.io.components.model.*
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.fetch.RequestInit

object DataStore {
    private const val  MAIN_URL =  "https://raw.githubusercontent.com/naser09/portfolio_data/refs/heads/main/"
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrintIndent = "  "
    }
    private suspend inline fun <reified T> fetchData(url: String): Result<T> {
        return try {
            val response = window.fetch(url, RequestInit()).await()
            if (!response.ok) {
                console.error("Failed to fetch data ${T::class.simpleName}")
                Result.failure(Exception("HTTP error! status: ${response.status}"))
            } else {
                val text = response.text().await()
                val data: T = json.decodeFromString(text)
                Result.success(data)
            }
        } catch (e: Exception) {
            console.error("Fetch error:", e)
            Result.failure(e)
        }
    }

    // State holders for different data types
    private val _personalInfo = mutableStateOf<List<PersonalInfo>?>(null)
    val personalInfo: State<List<PersonalInfo>?> = _personalInfo

    private val _personalSkill = mutableStateOf<List<Skill>?>(null)
    val personalSkill: State<List<Skill>?> = _personalSkill

    private val _commands = mutableStateOf<List<Command>?>(null)
    val terminalCommands: State<List<Command>?> = _commands

    private val _academic = mutableStateOf<List<Academic>?>(null)
    var academic:State<List<Academic>?> = _academic

    private val _projects = mutableStateOf<List<Project>?>(null)
    var projects:State<List<Project>?> = _projects

    private val _videos = mutableStateOf<List<VideoItem>?>(null)
    var videos:State<List<VideoItem>?> = _videos

    private val _technologies = mutableStateOf<List<Technology>?>(null)
    var technologies:State<List<Technology>?> = _technologies

//    // Generic helper functions for storage operations
//    private suspend inline fun <reified T> saveToCache(key: String, data: T) {
//        try {
//            val jsonData = Json.encodeToString(data)
//            val response = Response(jsonData)
//            window.caches.open("cache_$key").then { cache ->
//                cache.put("/api/$key", response)
//            }.await()
//        } catch (e: Exception) {
//            console.error("Cache save error for $key:", e)
//        }
//    }

//    private suspend fun <T> loadFromCache(key: String): T? {
//        return try {
//            window.caches.open("cache_$key").then { cache ->
//                cache.match("/api/$key").then { response ->
//                    response?.text()?.then { text ->
//                        Json.decodeFromString<T>(text)
//                    }
//                }
//            }.await()
//        } catch (e: Exception) {
//            console.error("Cache load error for $key:", e)
//            null
//        }
//    }

    private inline fun <reified T> saveToLocalStorage(key: String, data: T) {
        try {
            val jsonData = Json.encodeToString(data)
            localStorage["storage_$key"] = jsonData
        } catch (e: Exception) {
            console.error("LocalStorage save error for $key:", e)
        }
    }

    private inline fun <reified T> loadFromLocalStorage(key: String): T? {
        return try {
            localStorage["storage_$key"]?.let { jsonData ->
                Json.decodeFromString<T>(jsonData)
            }
        } catch (e: Exception) {
            console.error("LocalStorage load error for $key:", e)
            null
        }
    }

    // Specific data loading functions
    suspend fun loadPersonalInfo() {
        if (_personalInfo.value == null) {
            // Try loading from cache first
            val cachedData = loadFromLocalStorage<List<PersonalInfo>>("personal_info")
            if (cachedData != null) {
                _personalInfo.value = cachedData
                return
            }

            // If no cache, fetch from API
            _personalInfo.value = fetchData<List<PersonalInfo>?>(MAIN_URL+"about_info.json").getOrNull()

            // Store the fetched data
            _personalInfo.value?.let { data ->
//                saveToCache("personal_info", data)
                saveToLocalStorage("personal_info", data)
            }
        }
    }
    suspend fun loadTerminalCommands() {
        if (_commands.value == null) {
            val cachedData = loadFromLocalStorage<List<Command>?>("terminal_commands")
            if (cachedData != null) {
                _commands.value = cachedData
                return
            }
            _commands.value = fetchData<List<Command>?>(MAIN_URL+"commands.json").getOrNull()
            console.log("Data fetched ${_commands.value}")
            _commands.value?.let { data ->
                saveToLocalStorage("terminal_commands", data)
            }
        }
    }
    suspend fun loadAcademic() {
        if (_academic.value == null) {
            val cachedData = loadFromLocalStorage<List<Academic>?>("academic_data")
            if (cachedData != null) {
                _academic.value = cachedData
                return
            }
            _academic.value = fetchData<List<Academic>?>(MAIN_URL+"academic.json").getOrNull()
            console.log("Data fetched ${_academic.value}")
            _academic.value?.let { data ->
                saveToLocalStorage("academic_data", data)
            }
        }
    }
    suspend fun loadProjects() {
        if (_projects.value == null) {
            val cachedData = loadFromLocalStorage<List<Project>?>("projects_data")
            if (cachedData != null) {
                _projects.value = cachedData
                return
            }
            _projects.value = fetchData<List<Project>?>(MAIN_URL+"projects.json").getOrNull()
            console.log("Data fetched ${_projects.value}")
            _projects.value?.let { data ->
                saveToLocalStorage("projects_data", data)
            }
        }
    }
    suspend fun loadVideos() {
        if (_videos.value == null) {
            val cachedData = loadFromLocalStorage<List<VideoItem>?>("videos_data")
            if (cachedData != null) {
                _videos.value = cachedData
                return
            }
            _videos.value = fetchData<List<VideoItem>?>(MAIN_URL+"videos.json").getOrNull()
            console.log("Data fetched ${_videos.value}")
            _videos.value?.let { data ->
                saveToLocalStorage("videos_data", data)
            }
        }
    }
    suspend fun loadTechStack() {
        if (_technologies.value == null) {
            val cachedData = loadFromLocalStorage<List<Technology>?>("tech_stack_data")
            if (cachedData != null) {
                _technologies.value = cachedData
                return
            }
            _technologies.value = fetchData<List<Technology>?>(MAIN_URL+"technologies.json").getOrNull()
            console.log("Data fetched ${_technologies.value}")
            _technologies.value?.let { data ->
                saveToLocalStorage("tech_stack_data", data)
            }
        }
    }

    suspend fun loadPersonalSkill() {
        if (_personalSkill.value == null) {
            val cachedData = loadFromLocalStorage<List<Skill>>("personal_skill")
            if (cachedData != null) {
                _personalSkill.value = cachedData
                return
            }

            _personalSkill.value = fetchData<List<Skill>?>(MAIN_URL+"about_skill.json").getOrNull()

            _personalSkill.value?.let { data ->
//                saveToCache("personal_skill", data)
                saveToLocalStorage("personal_skill", data)
            }
        }
    }


    // Clear specific data
    suspend fun clearPersonalInfo() {
        _personalInfo.value = null
        clearStorage("personal_info")
    }

    suspend fun clearPersonalSkill() {
        _personalSkill.value = null
        clearStorage("personal_skill")
    }

    // Generic clear storage helper
    private suspend fun clearStorage(key: String) {
        try {
            window.caches.delete("cache_$key").await()
            localStorage.removeItem("storage_$key")
        } catch (e: Exception) {
            console.error("Error clearing storage for $key:", e)
        }
    }
    fun<T> prettyPrint(serializationStrategy: SerializationStrategy<T>,
                       data:T,
                       hideKeys: Boolean = true,
                       useNumbering: Boolean = false,
                       indentSize: Int = 4,
                       itemPerLine:Int = 0

    ): String {
        if (data == null) return "null"

        // Convert to JSON string first
        val jsonString = json.encodeToString(serializationStrategy, data)

        return cleanJsonString(
            jsonString,
            hideKeys, useNumbering,
            indentSize,itemPerLine
        )
    }
    private fun cleanJsonString(
        jsonString: String,
        hideKeys: Boolean = false,
        useNumbering: Boolean = true,
        indentSize: Int = 2,
        pair: Int = 0
    ): String {
        var itemCounter = 1

        fun getIndent(level: Int) = " ".repeat(indentSize * level)

        fun processValue(value: String) = when {
            value.matches("""^-?\d+(\.\d+)?$""".toRegex()) -> value  // Numbers
            value.matches("""^(true|false)$""".toRegex()) -> value   // Booleans
            else -> "\u001B[32m$value\u001B[0m"                     // Strings
        }

        fun formatPairs(lines: List<String>): List<String> {
            if (pair <= 0) return lines

            return lines.chunked(pair) { group ->
                group.joinToString("  →  ")
            }
        }

        // Process JSON string
        val processedLines = jsonString
            // Remove outer braces and brackets
            .replace("""^\{|\}$""".toRegex(), "")
            .replace("""^\[|\]$""".toRegex(), "")

            // Add numbering/bullets for items
            .replace("""\n\s*\{""".toRegex()) {
                "\n${if (useNumbering) "${itemCounter++}. " else "• "}"
            }

            // Handle keys
            .replace(""""(\w+)"\s*:\s*""".toRegex()) { matchResult ->
                if (hideKeys) "" else "${matchResult.groupValues[1]}: "
            }

            // Clean up structure
            .replace("""\n\s*\},""".toRegex(), "\n")
            .replace("""\n\s*\}""".toRegex(), "\n")

            // Process values
            .replace(""": "(.*?)"""".toRegex()) { matchResult ->
                val value = processValue(matchResult.groupValues[1])
                if (hideKeys) value else ": $value"
            }

            // Handle arrays
            .replace("""\[\s*"([^"]+)"(?:,\s*"([^"]+)")*\s*\]""".toRegex()) { matchResult ->
                matchResult.groupValues[0]
                    .replace("""[\[\]]""".toRegex(), "")
                    .split(""",\s*""".toRegex())
                    .mapIndexed { index, value ->
                        "\n${getIndent(1)}${if (useNumbering) "${index + 1}. " else "• "}${
                            value.replace(""""|\s*,\s*""".toRegex(), "").trim()
                        }"
                    }
                    .joinToString("\n")
            }
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        // Format pairs if needed
        val formattedLines = formatPairs(processedLines)

        // Apply indentation and join lines
        return formattedLines
            .mapIndexed { _, line ->
                val indentLevel = if (pair > 0) 0 else
                    line.count { it == '•' || it.toString().matches("""\d+\.""".toRegex()) }
                getIndent(indentLevel) + line.trim()
            }
            .joinToString("\n")
            .trim()
    }
}