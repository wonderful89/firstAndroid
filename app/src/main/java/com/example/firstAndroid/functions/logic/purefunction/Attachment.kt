package com.example.firstAndroid.functions.logic.purefunction

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.NonNull
import androidx.room.PrimaryKey

open class BaseAttach(
    /** 文档名称 */
    var name: String? = null,
    /** 微课文件名*/
    var docName: String? = null,
    /** 文档类型 */
    var docType: Int? = 0,
    /** 文件播放地址 */
    var fileUrl: String? = null,
    /** 源文件地址 */
    var originUrl: String? = null,
    /** 文件后缀名 */
    var fileExtendName: String? = null,
    var fileSize: Long? = 0L,
    /** 文档的转换状态  转换状态(0:未转换,1:转换成功,<0:转换失败) */
    var convertStatus: Int? = 0,
    /** 文件封面地址 */
    var coverUrl: String? = null,

    /** 文档ID */
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var docId: String = "",
    /** 音频时长 **/
    var metaDuration: Long? = 0
)

open class Attachment(
    /** 文档名称 */
    name: String? = null,
    /** 微课文件名*/
    docName: String? = null,
    /** 文档类型 */
    docType: Int? = 0,
    /** 文件播放地址 */
    fileUrl: String? = null,
    /** 源文件地址 */
    originUrl: String? = null,
    /** 文件后缀名 */
    fileExtendName: String? = null,
    fileSize: Long? = null,
    convertStatus: Int? = 0,
    coverUrl: String? = null,
    docId: String = "",
    /** 音频时长 **/
    metaDuration: Long? = 0,
    /** 是否为本次作业的试卷*/
    var attachmentPurpose: Boolean? = null,
    var extName: String? = null,
    var type: String? = null,
    var fileId: String? = null,
    var taskAttachmentId: String? = null,
    var updateDatetime: Long? = null,
    var thirdExampaperSource: Int? = 0,// 第三方试卷来源： 1-朗鹰口语作业
    var thirdExampaperId: String? = null,// 第三方试卷id
    var exampaperZipVersion: Long? = null,//EINK试卷2.0的版本，用来判断是否需要更新试卷
    var exampaperEinkMakeFlag: Int? = 0,//EINK试卷2.0制作状态。0：没有，1：有
    var exampaperZipUrl: String? = null,
    var poempaperZipUrl:String?=null
) : BaseAttach(
    name, docName, docType,
    fileUrl, originUrl, fileExtendName, fileSize, convertStatus, coverUrl, docId, metaDuration
), Parcelable {
    constructor(parcel: Parcel) : this(
        docId = ""
    ) {
        try {
            name = parcel.readString()
            docName = parcel.readString()
            docType = parcel.readString()?.convertInt()
            fileUrl = parcel.readString()
            originUrl = parcel.readString()
//
            fileExtendName = parcel.readString()
            fileSize = parcel.readString()?.convertLong()
            convertStatus = parcel.readString()?.convertInt()
            coverUrl = parcel.readString()
            docId = (parcel.readString() ?: "")
//
            metaDuration = parcel.readString()?.convertLong()
            attachmentPurpose = parcel.readString()?.convertBool()
            extName = parcel.readString()
            type = parcel.readString()
            fileId = parcel.readString()
//
            taskAttachmentId = parcel.readString()
            updateDatetime = parcel.readString()?.convertLong()
            thirdExampaperSource = parcel.readString()?.convertInt()
            thirdExampaperId = parcel.readString()
            exampaperZipVersion = parcel.readString()?.convertLong()

            exampaperEinkMakeFlag = parcel.readString()?.convertInt()
            exampaperZipUrl = parcel.readString()
            poempaperZipUrl = parcel.readString()
        } catch (e: Throwable) {
            Log.e("TaskMeta", "tool-bar Attachment 解析发生异常 $e fileId = $fileId, attachName = xx, taskAttachmentId = $taskAttachmentId")
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name ?: "myname")
        parcel.writeString(docName)
        parcel.writeString(docType?.toString())
        parcel.writeString(fileUrl)
        parcel.writeString(originUrl)
//
        parcel.writeString(fileExtendName)
        parcel.writeString(fileSize?.toString())
        parcel.writeString(convertStatus?.toString())
        parcel.writeString(coverUrl)
        parcel.writeString(docId)
//
        parcel.writeString(metaDuration?.toString())
        parcel.writeString(attachmentPurpose?.toString())
        parcel.writeString(extName)
        parcel.writeString(type)
        parcel.writeString(fileId)
//
        parcel.writeString(taskAttachmentId)
        parcel.writeString(updateDatetime?.toString())
        parcel.writeString(thirdExampaperSource.toString())
        parcel.writeString(thirdExampaperId)
        parcel.writeString(exampaperZipVersion?.toString())

        parcel.writeString(exampaperEinkMakeFlag?.toString())
        parcel.writeString(exampaperZipUrl)
        parcel.writeString(poempaperZipUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attachment> {
        override fun createFromParcel(parcel: Parcel): Attachment {
            return Attachment(parcel)
        }

        override fun newArray(size: Int): Array<Attachment?> {
            return arrayOfNulls(size)
        }
    }

}

fun String.convertInt(): Int {
    return this.toInt()
}

fun String.convertLong (): Long {
    return this.toLong()
}


fun String.convertBool(): Boolean {
    return this.toBoolean()
}

/**


open class Attachment(
/** 文档名称 */
name: String? = null,
/** 微课文件名*/
docName: String? = null,
/** 文档类型 */
docType: Int? = 0,
/** 文件播放地址 */
fileUrl: String? = null,
/** 源文件地址 */
originUrl: String? = null,
/** 文件后缀名 */
fileExtendName: String? = null,
fileSize: Long? = null,
convertStatus: Int? = 0,
coverUrl: String? = null,
docId: String = "",
/** 音频时长 **/
metaDuration: Long? = 0,
/** 是否为本次作业的试卷*/
var attachmentPurpose: Boolean? = null,
var extName: String? = null,
var type: String? = null,
var fileId: String? = null,
var taskAttachmentId: String? = null,
var updateDatetime: Long? = null,
var thirdExampaperSource: Int? = 0,// 第三方试卷来源： 1-朗鹰口语作业
var thirdExampaperId: String? = null,// 第三方试卷id
var exampaperZipVersion: Long? = null,//EINK试卷2.0的版本，用来判断是否需要更新试卷
var exampaperEinkMakeFlag: Int? = 0,//EINK试卷2.0制作状态。0：没有，1：有
var exampaperZipUrl: String? = null,
var poempaperZipUrl:String?=null
) : BaseAttach(
name, docName, docType,
fileUrl, originUrl, fileExtendName, fileSize, convertStatus, coverUrl, docId, metaDuration
), Parcelable {
constructor(parcel: Parcel) : this(
docId = ""
) {
try {
name = parcel.readString()
docName = parcel.readString()
docType = parcel.readValue(Int::class.java.classLoader) as? Int
fileUrl = parcel.readString()
originUrl = parcel.readString()

fileExtendName = parcel.readString()
fileSize = parcel.readValue(Long::class.java.classLoader) as? Long
convertStatus = parcel.readValue(Int::class.java.classLoader) as? Int
coverUrl = parcel.readString()
docId = (parcel.readString() ?: "")
//
//            metaDuration = parcel.readValue(Long::class.java.classLoader) as? Long
//            attachmentPurpose = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
//            extName = parcel.readString()
//            type = parcel.readString()
//            fileId = parcel.readString()
//
//            taskAttachmentId = parcel.readString()
//            updateDatetime = parcel.readValue(Long::class.java.classLoader) as? Long
//            thirdExampaperSource = parcel.readInt()
//            thirdExampaperId = parcel.readString()
//            exampaperZipVersion = parcel.readValue(Long::class.java.classLoader) as? Long

EinkLog.e("TaskMeta", "tool-bar Attachment exampaperZipVersion = $name")

//            exampaperEinkMakeFlag = parcel.readValue(Int::class.java.classLoader) as? Int
//            exampaperZipUrl = parcel.readString()
//            poempaperZipUrl = parcel.readString()
} catch (e: Throwable) {
EinkLog.e("TaskMeta", "tool-bar Attachment 解析发生异常 $e fileId = $fileId, attachName = $attachName, taskAttachmentId = $taskAttachmentId")
}
}

override fun writeToParcel(parcel: Parcel, flags: Int) {
parcel.writeString(name ?: "myname")
parcel.writeString(docName)
//        parcel.writeValue(docType)
//        parcel.writeString(fileUrl)
//        parcel.writeString(originUrl)
//
//        parcel.writeString(fileExtendName)
//        parcel.writeValue(fileSize)
//        parcel.writeValue(convertStatus)
//        parcel.writeString(coverUrl)
//        parcel.writeString(docId)
//
//        parcel.writeValue(metaDuration)
//        parcel.writeValue(attachmentPurpose)
//        parcel.writeString(extName)
//        parcel.writeString(type)
//        parcel.writeString(fileId)
//
//        parcel.writeString(taskAttachmentId)
//        parcel.writeValue(updateDatetime)
//        parcel.writeValue(thirdExampaperSource)
//        parcel.writeValue(thirdExampaperId)
//        parcel.writeValue(exampaperZipVersion ?: 100)

//        parcel.writeValue(exampaperEinkMakeFlag)
//        parcel.writeString(exampaperZipUrl)
//        parcel.writeString(poempaperZipUrl)
}

override fun describeContents(): Int {
return 0
}

companion object CREATOR : Parcelable.Creator<Attachment> {
override fun createFromParcel(parcel: Parcel): Attachment {
return Attachment(parcel)
}

override fun newArray(size: Int): Array<Attachment?> {
return arrayOfNulls(size)
}
}

}
 * fs
 */