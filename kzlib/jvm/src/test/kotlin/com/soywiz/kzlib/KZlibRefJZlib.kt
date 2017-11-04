package com.soywiz.kzlib

import org.junit.Test
import kotlin.test.assertEquals

class KZlibRefJZlib {
	@Test
	fun testBoth() {
		testDeflateInflate("HELLO HELLO HELLO HELLO WORLD WORLD AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".toSimpleByteArray())
		testDeflateInflate((0 until 10000).map { it.toByte() }.toByteArray())
		testDeflateInflate((0 until 10000).map { 0.toByte() }.toByteArray())
	}

	private fun testDeflateInflate(original: ByteArray) {
		val jcompressed = original.jzlibDeflate()
		val kcompressed = original.deflate()
		val compressed = kcompressed

		assertEquals(jcompressed.toList(), kcompressed.toList())

		val juncompressed = compressed.jzlibInflate()
		val kuncompressed = compressed.inflate()
		val uncompressed = kcompressed

		assertEquals(original.toList(), juncompressed.toList())
		assertEquals(original.toList(), kuncompressed.toList())
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
