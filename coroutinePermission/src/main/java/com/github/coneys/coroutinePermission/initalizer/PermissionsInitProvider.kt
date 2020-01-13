package com.github.coneys.coroutinePermission.initalizer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.net.Uri

internal class PermissionsInitProvider : ContentProvider() {

    companion object {
        internal lateinit var appContext: Context
    }

    override fun insert(uri: Uri, values: ContentValues?) = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ) = null

    override fun onCreate(): Boolean {
        appContext = context!!.applicationContext
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun getType(uri: Uri): String? = null

}