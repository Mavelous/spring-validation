package com.mikevitale.demo.model

import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class KotlinPhoneNumber(
		@field:Pattern(regexp = "^1[0-9]{3}[0-9]{3}[0-9]{4}$", message = "Phone Number Pattern Validation Message")
		@field:Size(min = 11, max = 11, message = "Phone Number Size Validation Message")
		val phoneNumber: String
		)
