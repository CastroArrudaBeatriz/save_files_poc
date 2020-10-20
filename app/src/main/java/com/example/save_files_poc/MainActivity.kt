package com.example.save_files_poc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private val fileName1 = "encript_file.txt"
    private lateinit var jetpackFile: File
    private lateinit var encryptedFile: EncryptedFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    @SuppressLint("LongLogTag")
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        /**
         *  Criar arquivo nome dir interno
         * */
        val fileName = "file_name.txt"
        val fileBody = "Beatriz"
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
            output.write(fileBody.toByteArray())
            output.flush()
            output.close()
        }

        /**
         *  ler arquivo nome dir interno
         * */
        this.openFileInput(fileName).use { fileInputStream ->
            println("File Name Conteúdo: ${fileInputStream.readBytes().decodeToString()}");
        }
        println("Path FileName: ${getFileStreamPath(fileName).canonicalPath}")
        //this.deleteFile(fileName)


        /**
         *  Criar arquivo nome dir externo
         * */
        val fileIdade = "file_idade.txt"
        val fileBodyIdade = "23"
        val myFile = File(getExternalFilesDir(null), fileIdade)
        FileOutputStream(myFile).use {output ->
            output.write(fileBodyIdade.toByteArray())
            output.flush()
            output.close()
        }

        /**
         *  ler arquivo nome dir externo
         * */
        FileInputStream(myFile).use { fileInputStream ->
            println("File Idade Conteúdo: ${fileInputStream.bufferedReader().readLine()}");
        }
        println("Path FileIdade: ${myFile.canonicalPath}")
        //myFile.delete()


        /**
         * salvar arquivos utilizando Jetpack
         * */
        initEncryptedVariables()
        jetpackFile.delete()
        encryptedFile.openFileOutput().use {
            it.write("Eu sou encript YAYAYAY".toByteArray())
        }

        encryptedFile.openFileInput().use {
            println(it.readBytes().decodeToString())
        }



    }

    private fun initEncryptedVariables(){
        jetpackFile = File(filesDir, fileName1)
        encryptedFile = EncryptedFile.Builder(
            this,
            jetpackFile,
            MasterKey.Builder(this).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }
}