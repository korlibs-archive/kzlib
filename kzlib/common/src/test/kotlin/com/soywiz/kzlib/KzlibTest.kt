package com.soywiz.kzlib

import org.junit.Test

class KzlibTest {
	@Test
	fun name() {
		val res = "HELLO HELLO HELLO HELLO WORLD".toSimpleByteArray().deflate()

	}
}