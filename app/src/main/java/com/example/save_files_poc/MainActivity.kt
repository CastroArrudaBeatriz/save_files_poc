package com.example.save_files_poc

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        /**
         *  Criar arquivo nome dir externo
         * */
        val fileName = "file_name.txt"
        val fileBody = "Beatriz"
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
            output.write(fileBody.toByteArray())
            output.flush()
            output.close()
        }
        //this.deleteFile("file_idade.txt")

        /**
         *  ler arquivo nome dir interno
         * */
        this.openFileInput(fileName).use { fileInputStream ->
            Log.d("File Name Conteúdo:", fileInputStream.readBytes().decodeToString());
        }

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
            Log.d("File Idade Conteúdo:", fileInputStream.bufferedReader().readLine());
        }


    }
}