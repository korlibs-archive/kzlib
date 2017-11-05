package com.soywiz.kzlib

import org.junit.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class KZlibRefJZlib {
	val base = File("../..")

	@Test
	fun testHelloWorld() = testDeflateInflate("HELLO HELLO HELLO HELLO WORLD WORLD AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".toSimpleByteArray())

	@Test
	fun testSequence() = testDeflateInflate((0 until 10000).map { it.toByte() }.toByteArray())

	@Test
	fun testOtherSequence() = testDeflateInflate((0 until 10000).map { (it * 1234567).toByte() }.toByteArray())

	@Test
	fun testZero() = testDeflateInflate((0 until 10000).map { 0.toByte() }.toByteArray())

	@Test
	fun testAlice29() = testDeflateInflate(File(base, "corpus/alice29.txt").readBytes())

	@Test
	fun testAsyoulik() = testDeflateInflate(File(base, "corpus/asyoulik.txt").readBytes())

	@Test
	fun testCp() = testDeflateInflate(File(base, "corpus/cp.html").readBytes())

	@Test
	fun testFieldsC() = testDeflateInflate(File(base, "corpus/fields.c").readBytes())

	@Test
	fun testGrammar() = testDeflateInflate(File(base, "corpus/grammar.lsp").readBytes())

	@Test
	fun testKennedyXls() = testDeflateInflate(File(base, "corpus/kennedy.xls").readBytes())

	@Test
	fun testLcet10() = testDeflateInflate(File(base, "corpus/lcet10.txt").readBytes())

	@Test
	fun testPlrabn12() = testDeflateInflate(File(base, "corpus/plrabn12.txt").readBytes())

	@Test
	fun testPtt5() = testDeflateInflate(File(base, "corpus/ptt5").readBytes())

	@Test
	fun testSum() = testDeflateInflate(File(base, "corpus/sum").readBytes())

	@Test
	fun testXArgs1() = testDeflateInflate(File(base, "corpus/xargs.1").readBytes())

	private fun testDeflateInflate(original: ByteArray) {
		val jcompressed = original.jzlibDeflate(level = 7)
		val kcompressed = original.deflate(level = 7)
		val compressed = kcompressed


		if (!Arrays.equals(jcompressed, kcompressed)) {
			println("WARNING: jcompressed: ${jcompressed.size}, kcompressed: ${kcompressed.size}")
		}

		//assertEquals(jcompressed.toList(), kcompressed.toList())

		val juncompressed = compressed.jzlibInflate()
		val kuncompressed = compressed.inflate()
		val uncompressed = kcompressed

		if (!Arrays.equals(original, kuncompressed)) {
			println("original: ${original.size}, kuncompressed: ${kuncompressed.size}")
		}
		assertEquals(original.toList(), juncompressed.toList())
		assertEquals(original.toList(), kuncompressed.toList())
	}
}

fun ByteArray.jzlibInflate(nowrap: Boolean = false): ByteArray {
	val bos = java.io.ByteArrayOutputStream()
	com.jcraft.jzlib.InflaterInputStream(java.io.ByteArrayInputStream(this), nowrap).copyTo(bos)
	return bos.toByteArray()
}

fun ByteArray.jzlibDeflate(level: Int = 7, nowrap: Boolean = false): ByteArray {
	val bos = java.io.ByteArrayOutputStream()
	val def = com.jcraft.jzlib.DeflaterOutputStream(bos, com.jcraft.jzlib.Deflater(level, nowrap))
	def.write(this)
	def.flush()
	def.finish()
	return bos.toByteArray()
}
