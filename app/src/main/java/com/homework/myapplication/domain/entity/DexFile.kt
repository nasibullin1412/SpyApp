package com.homework.myapplication.domain.entity

import java.io.InputStream

class DexFile(val name: String, val content: InputStream = "".byteInputStream())
