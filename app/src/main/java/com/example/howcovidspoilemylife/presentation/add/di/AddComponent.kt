package com.example.howcovidspoilemylife.presentation.add.di

import com.example.howcovidspoilemylife.core.di.AppComponent
import com.example.howcovidspoilemylife.core.di.ScreenScope
import com.example.howcovidspoilemylife.presentation.add.AddProductFragment
import dagger.Component

@Component(dependencies = [AppComponent::class])
@ScreenScope
interface AddComponent {

    companion object {
        fun create(appComponent: AppComponent, fragment: AddProductFragment) {
            DaggerAddComponent.factory().create(appComponent).inject(fragment)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): AddComponent
    }

    fun inject(addProductFragment: AddProductFragment)
}