package com.soywiz.kzlib

object System {
	private inline fun overlaps(src: Any, srcPos: Int, dst: Any, dstPos: Int, count: Int): Boolean {
		return (src === dst) && srcPos >= dstPos
		//return false
	}

	fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, count: Int) {
		if (overlaps(src, srcPos, dst, dstPos, count)) {
			for (n in 0 until count) dst[dstPos + n] = src[srcPos + n]
		} else {
			for (n in count - 1 downTo 0) dst[dstPos + n] = src[srcPos + n]
		}
	}

	fun arraycopy(src: ShortArray, srcPos: Int, dst: ShortArray, dstPos: Int, count: Int) {
		if (overlaps(src, srcPos, dst, dstPos, count)) {
			for (n in 0 until count) dst[dstPos + n] = src[srcPos + n]
		} else {
			for (n in count - 1 downTo 0) dst[dstPos + n] = src[srcPos + n]
		}
	}

	fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, count: Int) {
		if (overlaps(src, srcPos, dst, dstPos, count)) {
			for (n in 0 until count) dst[dstPos + n] = src[srcPos + n]
		} else {
			for (n in count - 1 downTo 0) dst[dstPos + n] = src[srcPos + n]
		}
	}
}

infix fun Byte.and(mask: Long): Long = this.toLong() and mask

infix fun Byte.and(mask: Int): Int = this.toInt() and mask
infix fun Short.and(mask: Int): Int = this.toInt() and mask

infix fun Byte.shl(that: Int): Int = this.toInt() shl that

fun String.toSimpleByteArray(): ByteArray {
	val out = ByteArray(this.length)
	for (n in 0 until length) out[n] = this[n].toByte()
	return out
}

fun ByteArray.toSimpleString(): String {
	val out = StringBuilder()
	for (n in 0 until size) out.append(this[n].toChar())
	return out.toString()
}