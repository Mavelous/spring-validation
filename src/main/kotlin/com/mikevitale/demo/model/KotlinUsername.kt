package com.mikevitale.demo.model

import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

// An explanation of @field:Pattern and @field:Size in Kotlin code:
// https://stackoverflow.com/questions/36515094/kotlin-and-valid-spring-annotation
data class KotlinUsername(
		@field:Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
		@field:Size(min = 2, max = 15, message = "Username Size Validation Message")
		val username: String = ""
)
