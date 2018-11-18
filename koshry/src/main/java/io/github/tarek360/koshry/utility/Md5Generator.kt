package io.github.tarek360.koshry.utility

import java.security.MessageDigest

open class Md5Generator {

  open fun getMd5(text: String): String {
    val md = MessageDigest.getInstance("MD5")
    md.update(text.toByteArray())
    val byteData = md.digest()

    //convert the byte to hex format method 2
    val hexString = StringBuilder()
    for (aByteData in byteData) {
      val hex = Integer.toHexString(0xff and aByteData.toInt())
      if (hex.length == 1) hexString.append('0')
      hexString.append(hex)
    }
    return hexString.toString()
  }
}