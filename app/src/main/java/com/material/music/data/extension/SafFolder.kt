package com.material.music.data.extension

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import java.io.File

/**
 * Minimal SAF helpers for writing downloads into a user-visible folder.
 *
 * Kept deliberately small: create a document, copy bytes, sync. No persistent
 * tree permission is assumed beyond the one the picker granted.
 */
object SafFolder {

    fun createFile(
        context: Context,
        treeUri: String,
        mimeType: String,
        displayName: String,
    ): Uri? = runCatching {
        val parent = DocumentsContract.buildDocumentUriUsingTree(
            Uri.parse(treeUri),
            DocumentsContract.getTreeDocumentId(Uri.parse(treeUri)),
        )
        DocumentsContract.createDocument(
            context.contentResolver,
            parent,
            mimeType,
            displayName,
        )
    }.getOrNull()

    fun copy(context: Context, source: File, target: Uri): Boolean = runCatching {
        context.contentResolver.openOutputStream(target, "wt")?.use { output ->
            source.inputStream().use { input -> input.copyTo(output) }
        } ?: return false
        true
    }.getOrDefault(false)

    /** A stable display name for the document, without path separators. */
    fun displayName(raw: String, extension: String): String {
        val base = raw.replace(Regex("""[\\/:*?"<>|]"""), "_").trim().take(120)
        val name = base.ifBlank { "download" }
        return if (extension.startsWith(".") && !name.endsWith(extension)) "$name$extension" else name
    }
}