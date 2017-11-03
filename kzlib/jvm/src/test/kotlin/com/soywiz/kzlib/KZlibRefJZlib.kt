package com.soywiz.kzlib

import org.junit.Test
import kotlin.test.assertEquals

class KZlibRefJZlib {
	@Test
	fun name() {
		val original = "HELLO HELLO HELLO HELLO WORLD".toSimpleByteArray()

		val jcompressed = original.jzlibDeflate()
		val kcompressed = original.deflate()

		assertEquals(jcompressed.toList(), kcompressed.toList())
	}
}

fun ByteArray.jzlibInflate(nowrap: Boolean = false): ByteArray {
	val bos = java.io.ByteArrayOutputStream()
	com.jcraft.jzlib.InflaterInputStream(java.io.ByteArrayInputStream(this), nowrap).copyTo(bos)
	return bos.toByteArray()
}

fun ByteArray.jzlibDeflate(level: Int = 7): ByteArray {
	val bos = java.io.ByteArrayOutputStream()
	val def = com.jcraft.jzlib.DeflaterOutputStream(bos)
	def.write(this)
	def.flush()
	def.finish()
	return bos.toByteArray()
}
