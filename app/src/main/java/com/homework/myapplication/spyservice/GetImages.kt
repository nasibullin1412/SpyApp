package com.homework.myapplication.spyservice

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore

class GetImages : (Context) -> (ArrayList<ImageInfo>) {
    @SuppressLint("Recycle")
    override fun invoke(context: Context): ArrayList<ImageInfo> {
        val imgList: ArrayList<ImageInfo> = arrayListOf()
        val curs = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        ) ?: return imgList
        while (curs.moveToNext()) {
            val path = curs.getString(curs.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            val name = curs.getString(curs.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
            imgList.add(ImageInfo(path, name))
        }
        return imgList
    }
}
