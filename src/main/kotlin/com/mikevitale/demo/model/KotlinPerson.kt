package com.mikevitale.demo.model

import javax.validation.Valid

data class KotlinPerson(
		@field:Valid val username: KotlinUsername,
		@field:Valid val phoneNumber: KotlinPhoneNumber
		)
