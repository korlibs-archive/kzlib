package com.soywiz.kzlib

object KZlib {
	val VERSION = KZLIB_VERSION
}

fun ByteArray.inflate(nowrap: Boolean = false): ByteArray {
	val bos = ByteArrayOutputStream()
	InflaterInputStream(ByteArrayInputStream(this), nowrap).copyTo(bos)
	return bos.toByteArray()
}

fun ByteArray.deflate(level: Int = 7, nowrap: Boolean = false): ByteArray {
	val bos = ByteArrayOutputStream()
	val def = DeflaterOutputStream(bos, Deflater(level, nowrap))
	def.write(this)
	def.flush()
	def.finish()
	return bos.toByteArray()
}

