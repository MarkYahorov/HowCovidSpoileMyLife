package com.example.howcovidspoilemylife.presentation.list.di

import com.example.howcovidspoilemylife.core.di.AppComponent
import com.example.howcovidspoilemylife.core.di.ScreenScope
import com.example.howcovidspoilemylife.presentation.list.screen.ProductsListsFragment
import dagger.Component

@Component(dependencies = [AppComponent::class])
@ScreenScope
interface ListComponent {

    companion object {
        fun create(appComponent: AppComponent, fragment: ProductsListsFragment) {
            DaggerListComponent.factory().create(appComponent).inject(fragment)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ListComponent
    }

    fun inject(fragment: ProductsListsFragment)
}