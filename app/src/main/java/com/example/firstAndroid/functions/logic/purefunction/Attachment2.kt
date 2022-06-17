package com.example.firstAndroid.functions.logic.purefunction

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

open class AttachmentProblem(
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

            metaDuration = parcel.readValue(Long::class.java.classLoader) as? Long
            attachmentPurpose = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
            extName = parcel.readString()
            type = parcel.readString()
            fileId = parcel.readString()

            taskAttachmentId = parcel.readString()
            updateDatetime = parcel.readValue(Long::class.java.classLoader) as? Long
            val test1 = false
            if (test1) {
                // 解析异常
                thirdExampaperSource = parcel.readInt()
            } else {
                /// android.os.BadParcelableException: not enough data
                thirdExampaperSource = parcel.readValue(Int::class.java.classLoader) as? Int
            }

            thirdExampaperId = parcel.readString()
            exampaperZipVersion = parcel.readValue(Long::class.java.classLoader) as? Long

            Log.e("TaskMeta", "tool-bar Attachment exampaperZipVersion = $name")

            exampaperEinkMakeFlag = parcel.readValue(Int::class.java.classLoader) as? Int
            exampaperZipUrl = parcel.readString()
            poempaperZipUrl = parcel.readString()
        } catch (e: Throwable) {
            Log.e("TaskMeta", "tool-bar Attachment 解析发生异常 $e fileId = $fileId, attachName = $docName, taskAttachmentId = $taskAttachmentId")
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name ?: "myname")
        parcel.writeString(docName)
        parcel.writeValue(docType)
        parcel.writeString(fileUrl)
        parcel.writeString(originUrl)

        parcel.writeString(fileExtendName)
        parcel.writeValue(fileSize)
        parcel.writeValue(convertStatus)
        parcel.writeString(coverUrl)
        parcel.writeString(docId)

        parcel.writeValue(metaDuration)
        parcel.writeValue(attachmentPurpose)
        parcel.writeString(extName)
        parcel.writeString(type)
        parcel.writeString(fileId)

        parcel.writeString(taskAttachmentId)
        parcel.writeValue(updateDatetime)
        parcel.writeValue(thirdExampaperSource)
        parcel.writeValue(thirdExampaperId)
        parcel.writeValue(exampaperZipVersion ?: 100)

        parcel.writeValue(exampaperEinkMakeFlag)
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