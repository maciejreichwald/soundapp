package com.rudearts.soundapp

import android.app.Application
import android.content.Context
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.di.MainModule
import com.rudearts.soundapp.di.TestModule
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

class TestApplication : SongApplication() {

    override fun createModule() = TestModule(this)
}