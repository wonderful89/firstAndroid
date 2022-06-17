package com.example.firstAndroid.functions.logic.purefunction

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.firstAndroid.base.utils.FileConstants.APP_BIN_PATH
import java.io.File

private const val tag = "parcel test: "

fun testParcel() {
//    testBaseParcel()
//    testParcelFile()
//    testBaseParcelSameObj()
//    testBaseParcelDiffObj()
//    testParcelableObjectDiffObj()
//    testNewClass()
    testAttachment()
}

/**
 * Attachment
 */
private fun testAttachment() {
    val parObj = Attachment(
        docId = "docId",
        attachmentPurpose = true,
        exampaperEinkMakeFlag = 1,
        exampaperZipUrl = "fasdfksadfjlaf",
//        exampaperZipVersion = 1655445217000,
        exampaperZipVersion = 100,
        extName = "pdf",
        fileId = "3920239203922",
        taskAttachmentId = "1058479044440215552",
        thirdExampaperId = "",
        type = "doc",
        updateDatetime = null,
        convertStatus = 1,
        coverUrl = "dfasfkjalfsalkfj",
        docName = "fdsafkalfjakf",
        docType = 13,
        fileExtendName = "pdf",
        fileUrl = "sdaflkajsf",
        fileSize = 114629,
        metaDuration = 0,
        originUrl = "adfadjsalfjalkfaklfja",
    )
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain();
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()

    parcel.unmarshall(bytes, 0, bytes.size);
    parcel.setDataPosition(0);
    val parObj2 = Attachment(parcel)
    Log.d(tag, "parObj2 = $parObj2")
    val exampaperZipVersion = parObj2.exampaperZipVersion;
    parcel.recycle();
}

/**
 * NewClass
 */
private fun testNewClass() {
    val parObj = NewClass("xiaoming", 10)
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain();
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()

    parcel.unmarshall(bytes, 0, bytes.size);
    parcel.setDataPosition(0);
    val parObj2 = NewClass2(parcel)
    Log.d(tag, "parObj2 = $parObj2")
    parcel.recycle();
}

/**
 * 基础解析
 */
private fun testBaseParcel() {
    val parObj = ParcelableObject()
    parObj.age = 10
    parObj.name = "name1"
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain();
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()

    parcel.unmarshall(bytes, 0, bytes.size);
    parcel.setDataPosition(0);
    val parObj2 = ParcelableObject(parcel);
    Log.d(tag, "parObj2 = $parObj2")
    parcel.recycle();
}

/**
 * 基础解析-2
 */
private fun testBaseParcelSameObj() {
    val parObj = ParcelableObject()
    parObj.age = 10
    parObj.name = "name1"
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain();
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()

    parcel.unmarshall(bytes, 0, bytes.size);
    parcel.setDataPosition(0);
    val parObj2 = ParcelableObjectSameObj(parcel);
    Log.d(tag, "parObj2 = $parObj2")
    parcel.recycle();
}

/**
 * 基础解析-3-不同对象
 */
private fun testBaseParcelDiffObj() {
    val parObj = ParcelableObject()
    parObj.age = 10
    parObj.name = "name1"
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain();
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()
    parcel.recycle();

    val parcel2 = Parcel.obtain();
    parcel2.unmarshall(bytes, 0, bytes.size);
    parcel2.setDataPosition(0);
    val parObj2 = ParcelableObjectDiffObj(parcel2);
    Log.d(tag, "parObj2 = $parObj2")
    parcel2.recycle();
}

/**
 * ParcelableObjectDiffObj-编解码
 */
//private fun testParcelableObjectDiffObj() {
//    val parObj = ParcelableObjectDiffObj()
//    parObj.age = 10
//    parObj.name = "name1"
//    val book = Book()
//    book.bookId = "bookId-1"
//    book.count = 3
//    parObj.book = book
//    parObj.grade = 13
//    parObj.school = "school-1"
//    Log.d(tag, "parObj = $parObj")
//
//    val parcel = Parcel.obtain();
//    parcel.setDataPosition(0);//设置写的位置从0开始
//    parObj.writeToParcel(parcel, 0);
//    val bytes = parcel.marshall()
//    parcel.recycle();
//
//    val parcel2 = Parcel.obtain();
//    parcel2.unmarshall(bytes, 0, bytes.size);
//    parcel2.setDataPosition(0);
//    val parObj2 = ParcelableObjectDiffObj(parcel2);
//    Log.d(tag, "parObj2 = $parObj2")
//    parcel2.recycle();
//}

/**
 * 基础解析-保存到文件
 */
fun testParcelFile() {
    val parObj = ParcelableObject()
    parObj.age = 10
    parObj.name = "name1"
    Log.d(tag, "parObj = $parObj")

    val parcel = Parcel.obtain()
    parcel.setDataPosition(0);//设置写的位置从0开始
    parObj.writeToParcel(parcel, 0);
    val bytes = parcel.marshall()
    val pathBin = APP_BIN_PATH + "parcel_test_file.bin"
    File(pathBin).writeBytes(bytes)

    val bytes1 = File(pathBin).readBytes()
    parcel.unmarshall(bytes1, 0, bytes1.size);
    parcel.setDataPosition(0);
    val parObj2 = ParcelableObject(parcel);
    Log.d(tag, "parObj2 = $parObj2")
    parcel.recycle();
}

class ParcelableObject() : Parcelable {
    var age: Int = 0;
    var name: String? = "";

    constructor(parcel: Parcel) : this() {
        this.age = parcel.readInt()
        this.name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(age)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ParcelableObject(age=$age, name=$name)"
    }

    companion object CREATOR : Parcelable.Creator<ParcelableObject> {
        override fun createFromParcel(parcel: Parcel): ParcelableObject {
            return ParcelableObject(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableObject?> {
            return arrayOfNulls(size)
        }
    }
}

class ParcelableObjectSameObj() : Parcelable {
    var age: Int = 0;
    var name: String? = "";

    constructor(parcel: Parcel) : this() {
        this.age = parcel.readInt()
        this.name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(age)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ParcelableObjectSameObj(age=$age, name=$name)"
    }

    companion object CREATOR : Parcelable.Creator<ParcelableObjectSameObj> {
        override fun createFromParcel(parcel: Parcel): ParcelableObjectSameObj {
            return ParcelableObjectSameObj(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableObjectSameObj?> {
            return arrayOfNulls(size)
        }
    }
}

/**
 * 添加属性
 */
class ParcelableObjectDiffObj(var age:Int, var grade: Int?) : Parcelable {
//    var age: Int = 0;
    var name: String? = "";
    var school: String? = ""
//    var grade: Int? = 0
    var book: Book? = null

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readValue(Int.javaClass.classLoader) as? Int)

//    constructor(parcel: Parcel) : this() {
//        this.age = parcel.readInt()
//        /// crash
//        this.grade = parcel.readValue(Int.javaClass.classLoader) as Int
//
////        this.book = parcel.readParcelable(Book.javaClass.classLoader)
////        this.grade = parcel.readInt()
////        this.name = parcel.readString()
////        this.school = parcel.readString()
//    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(age)
        parcel.writeParcelable(book, 0)
//        parcel.writeInt(grade)
        parcel.writeString(name)
        parcel.writeString(school)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ParcelableObjectDiffObj(age=$age, name=$name, school=$school, grade=$grade, book=$book)"
    }

    companion object CREATOR : Parcelable.Creator<ParcelableObjectDiffObj> {
        override fun createFromParcel(parcel: Parcel): ParcelableObjectDiffObj {
            return ParcelableObjectDiffObj(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableObjectDiffObj?> {
            return arrayOfNulls(size)
        }
    }
}

class Book() : Parcelable {
    var bookId: String? = ""
    var count: Int = 0

    constructor(parcel: Parcel) : this() {
        bookId = parcel.readString()
        count = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookId)
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Book(bookId=$bookId, count=$count)"
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}

class NewClass(val name: String?, val age: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "NewClass(name=$name, age=$age)"
    }

    companion object CREATOR : Parcelable.Creator<NewClass> {
        override fun createFromParcel(parcel: Parcel): NewClass {
            return NewClass(parcel)
        }

        override fun newArray(size: Int): Array<NewClass?> {
            return arrayOfNulls(size)
        }
    }
}

class NewClass2(val name: String?, val age: Int, val weight: Float) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeFloat(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "NewClass2(name=$name, age=$age, weight=$weight)"
    }

    companion object CREATOR : Parcelable.Creator<NewClass2> {
        override fun createFromParcel(parcel: Parcel): NewClass2 {
            return NewClass2(parcel)
        }

        override fun newArray(size: Int): Array<NewClass2?> {
            return arrayOfNulls(size)
        }
    }
}