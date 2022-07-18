//package com.remindme.preference.di
//
//import com.remindme.domain.usecase.category.LoadAllCategories
//import com.remindme.domain.usecase.preferences.LoadAppTheme
//import com.remindme.domain.usecase.preferences.UpdateAppTheme
//import com.remindme.preference.AppThemeOptionsMapper
//import com.remindme.preference.presentation.PreferenceViewModel
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.scopes.ViewModelScoped
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
////import org.koin.androidx.viewmodel.dsl.viewModel
////import org.koin.dsl.module
//
///**
// * Preferences dependency injection module.
// */
////val preferenceModule = module {
////
////    viewModel { PreferenceViewModel(get(), get(), get()) }
////
////    factory { AppThemeOptionsMapper() }
////}
//@Module
//@InstallIn(SingletonComponent::class)
//object PreferenceModule {
//
//    @Provides
//    @ViewModelScoped
//    fun getPreferenceViewModel(
//        updateAppTheme: UpdateAppTheme,
//        loadAppTheme: LoadAppTheme,
//        appThemeOptionsMapper:AppThemeOptionsMapper
//    ): PreferenceViewModel {
//        return PreferenceViewModel(updateAppTheme, loadAppTheme,appThemeOptionsMapper)
//    }
//
//    @Provides
//    @Singleton
//    fun appThemeOptionsMapper(
//    ): AppThemeOptionsMapper {
//        return AppThemeOptionsMapper()
//    }
//}