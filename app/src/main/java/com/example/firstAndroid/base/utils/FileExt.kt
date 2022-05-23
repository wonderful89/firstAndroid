package com.example.firstAndroid.base.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.blankj.utilcode.util.FileUtils
import com.example.firstAndroid.functions.logic.storage.runOnFixedThread
import okhttp3.ResponseBody
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

//============================== create ============================================
/**
 * 如果一个文件不存在创建文件，否则不做任何事
 */
fun File.createOrExistsFile(): Boolean {
    if (this.exists()) return this.isFile
    if (this.parentFile?.createOrExistsDir() != true) return false
    return try {
        this.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

/**
 * 如果文件不存在则创建文件，否则删除文件后再创建
 */
fun File.createFileByDeleteOldFile(): Boolean {
    if (this.exists() && !this.delete()) return false
    return this.createOrExistsFile()
}

/**
 * 如果文件不存在则创建文件，否则删除文件后再创建
 */
fun String.createFileByDeleteOldFile(): Boolean {
    return this.getFileByPath()?.createFileByDeleteOldFile() ?: false
}

/**
 * 如果一个文件不存则创建文件，否则不做任何事
 */
fun String.createOrExistFile(): Boolean = getFileByPath()?.createOrExistsFile() ?: false

/**
 * 如果一个文件目录不存则创建文件目录，否则不做任何事
 */
fun File.createOrExistsDir(): Boolean {
    return if (this.exists()) this.isDirectory else this.mkdirs()
}

//============================== create & delete ============================================

/**
 * 删除文件
 */
fun File.deleteFileSafe(): Boolean {
    return (!this.exists() || (this.isFile() && this.delete()))
}

/**
 * 删除文件
 */
fun String.deleteFileSafe(): Boolean {
    return this.getFileByPath()?.deleteFileSafe() ?: true
}

/**
 * 删除文件夹
 */
fun File.deleteDir(): Boolean {
    if (!this.exists()) return true
    if (!this.isDirectory) return true
    val files = this.listFiles()
    files?.forEach { file ->
        if (file.isFile) {
            if (!file.delete()) return false
        } else if (file.isDirectory) {
            if (!file.deleteDir()) return false
        }
    }
    return this.delete()
}

/**
 * 删除文件夹
 */
fun String.deleteDir(): Boolean {
    return this.getFileByPath()?.deleteDir() ?: true
}

/**
 * 删除文件或文件夹
 */
fun File.deleteFileOrDirSafe() =
    if (!this.exists()) true
    else if (this.isFile) this.deleteFileSafe()
    else this.deleteDir()

/**
 * 删除文件或文件夹
 */
fun String.deleteFileOrDirSafe(): Boolean {
    return this.getFileByPath()?.deleteFileOrDirSafe() ?: true
}
//============================== delete & get ============================================

/**
 * 获取文件名称带有后缀
 */
fun String.getFileNameWithExt(): String {
    if (this.isBlank()) return ""
    val lastSep = this.lastIndexOf(File.separatorChar)
    return if (lastSep == -1) this else this.substring(lastSep + 1)
}

/**
 * 获取文件名
 */
fun String.getFileName(): String {
    val index: Int = this.lastIndexOf(File.separatorChar)
    val dotIndex = this.lastIndexOf(".")
    return if (dotIndex < index) this.substring(index + 1)
    else this.substring(index + 1, dotIndex)
}

/**
 * 获取文件名
 */
fun File.getFileName(): String = this.path.getFileName()


/**
 * 根据路径返回文件
 */
fun String.getFileByPath(): File? {
    return if (this.isNotBlank()) File(this) else null
}

/**
 * 返回文件的扩展名
 */
fun File.getFileExtension() = this.path.getFileExtension()

/**
 * 返回文件的扩展名
 */
fun String.getFileExtension(): String {
    if (this.isBlank()) return ""
    val lastPoi: Int = this.lastIndexOf('.')
    val lastSep: Int = this.lastIndexOf(File.separator)
    return if (lastPoi == -1 || lastSep >= lastPoi) "" else this.substring(lastPoi + 1)
}

/**
 * 返回文件的扩展名
 */
fun File.getExtName() = this.getFileExtension()

/**
 * 获取下载文件的同名目录 F://99/88.zip -> F://99/88 文件夹
 */
fun File.getSameNameDir(): String {
    val extNameIndex = this.absolutePath.lastIndexOf("." + this.getFileExtension())
    return this.absolutePath.substring(0, extNameIndex) + File.separator
}

//============================== get & write ============================================

/**
 * 安全的关闭流
 */
fun Closeable.closeSafe() {
    try {
        this.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 将字符串写入到文件中
 */
fun File.writeString(content: String, append: Boolean = false): Boolean {
    this.readText()
    if (!createOrExistsFile()) return false
    var bw: BufferedWriter? = null
    return try {
        bw = BufferedWriter(FileWriter(this, append))
        bw.write(content)
        bw.flush()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    } finally {
        bw?.closeSafe()
    }
}

/**
 * 将字符串写入到文件中
 */
fun String.writeString(content: String, append: Boolean = false): Boolean {
    val file = getFileByPath() ?: return false
    return file.writeString(content, append)
}


/**
 * 将字节数组写到文件中，通过流的方式
 */
fun File.writeBytesByStream(bytes: ByteArray, append: Boolean = false): Boolean {
    if (!createOrExistsFile()) return false
    var bos: BufferedOutputStream? = null
    return try {
        bos = BufferedOutputStream(FileOutputStream(this, append))
        bos.write(bytes)
        bos.flush()
        true
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        false
    } finally {
        bos?.closeSafe()
    }
}

/**
 * 将字节数组写到文件中，通过流的方式
 */
fun String.writeBytesByStream(bytes: ByteArray, append: Boolean = false): Boolean {
    val file = getFileByPath() ?: return false
    return file.writeBytesByStream(bytes, append)
}

/**
 * 将 InputStream 写入文件中
 */
fun File.writeInputStream(ins: InputStream): Boolean {
    if (!createFileByDeleteOldFile()) return false
    ins.use { `is` ->
        this.outputStream().use { fos ->
            `is`.copyTo(fos)
            fos.flush()
            return true
        }
    }
}

/**
 * 将 InputStream 写入文件中
 */
fun String.writeInputStream(ins: InputStream): Boolean {
    val file = getFileByPath() ?: return false
    return file.writeInputStream(ins)
}


//=============================== write & read ================================

/**
 * 读取文件转换为字符串
 */
fun File.read2String(charsetName: String? = null): String? {
    if (!this.exists() || this.isDirectory) return null
    val bytes: ByteArray = this.read2BytesByStream() ?: return null
    return if (charsetName.isNullOrEmpty()) {
        String(bytes)
    } else {
        try {
            String(bytes, Charset.forName(charsetName))
        } catch (e: UnsupportedCharsetException) {
            e.printStackTrace()
            ""
        }
    }
}

/**
 * 读取文件转换为字符串
 */
fun String.read2String(charsetName: String? = null): String? {
    val file = this.getFileByPath() ?: return null
    return file.read2String(charsetName)
}

/**
 * 读取文件转换为 Long
 */
fun File.read2Long(): Long? {
    return this.read2String()?.toLongOrNull()
}

/**
 * 读取文件转换为 Long
 */
fun String.read2Long(): Long? {
    return this.getFileByPath()?.read2Long()
}

/**
 * 读取文件转换为字节数据，通过流的方式
 */
fun File.read2BytesByStream(): ByteArray? {
    if (!this.exists()) return null
    return try {
        this.readBytes()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        null
    }
}


//=================================== read =================================
/**
 * 将文件路径 转换成 文件
 */
fun String.toFile(): File = File(this)

fun File.safeDelete(): Boolean {
    if (this.exists()) {
        return this.delete()
    }
    return false
}

/**
 * 获取图片文件的宽高
 * */
fun File.getBitmapOption(): BitmapFactory.Options? {
    return if (isExistAndNotEmpty()) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(this.path, options) // 此时返回的bitmap为null
        return options
    } else {
        null
    }
}

/**
 * 获取文件的Bitmap
 */
fun File.getBitmap(): Bitmap? {
    return if (isExistAndNotEmpty()) {
        val options = BitmapFactory.Options()
        BitmapFactory.decodeFile(path, options)
    } else {
        null
    }
}

fun File.isExistAndNotEmpty(): Boolean {
    return (if (this.isFile) this.exists() else this.list() != null && this.list().isNotEmpty()) && this.length() > 0L
}

/**
 * 从 InputStream 写入数据到 file 中
 */
fun File.writeInputStream(ins: InputStream, onSuccess: (() -> Unit)? = null) {
    if (this.exists()) {
        this.delete()
    }
    FileUtils.createOrExistsFile(this)
    ins.use { input ->
        this.outputStream().use { fileOut ->
            input.copyTo(fileOut)
            fileOut.flush()
            onSuccess?.invoke()
        }
    }

}

/**
 * 从 ResponseBody 中将数据写到 file 中
 */
fun File.writeResponseBody(
    body: ResponseBody?,
    onFailed: (e: Exception) -> Unit,
    onSuccess: (() -> Unit)? = null
) {
    if (body == null) {
        onFailed.invoke(Exception())
        return
    }
    try {
        this.writeInputStream(body.byteStream(), onSuccess)
    } catch (e: Exception) {
        e.printStackTrace()
        onFailed.invoke(e)
    }
}

fun File.writeResponseBodyAsync(
    body: ResponseBody?,
    onFailed: (e: Exception) -> Unit,
    onSuccess: (() -> Unit)? = null
) {
    runOnFixedThread {
        writeResponseBody(body, onFailed, onSuccess)
    }

}