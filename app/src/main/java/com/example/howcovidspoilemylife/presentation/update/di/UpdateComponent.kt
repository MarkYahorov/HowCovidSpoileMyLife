package com.example.howcovidspoilemylife.presentation.update.di

import com.example.howcovidspoilemylife.presentation.update.ui.UpdateProductFragment
import com.example.howcovidspoilemylife.core.di.AppComponent
import com.example.howcovidspoilemylife.core.di.ScreenScope
import dagger.Component

@Component(dependencies = [AppComponent::class])
@ScreenScope
interface UpdateComponent {

    companion object {
        fun create(appComponent: AppComponent, fragment: UpdateProductFragment) {
            DaggerUpdateComponent.factory().create(appComponent).inject(fragment)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): UpdateComponent
    }

    fun inject(fragment: UpdateProductFragment)
}