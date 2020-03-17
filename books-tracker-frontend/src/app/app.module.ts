import {BrowserModule} from '@angular/platform-browser';
import {NgModule, Provider} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';


import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AuthorsComponent} from './authors/authors.component';
import {BooksComponent} from './books/books.component';
import {MainLayoutComponent} from './main-layout/main-layout.component';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {SearchBookPipe} from './pipes/search-book.pipe';
import {GenresComponent} from './genres/genres.component';

import {VisitComponent} from './visit/visit.component';

import {registerLocaleData} from '@angular/common';
import ruLocale from '@angular/common/locales/ru';
import {LoginPageComponent} from './login-page/login-page.component';
import {AuthInterceptor} from './services/auth.interceptor';
import {UsersListComponent} from './user/users-list/users-list.component';
import {SearchUserPipe} from './pipes/search-user.pipe';
import {SubscriptionComponent} from './my/subscription/subscription.component';
import {MyGenresComponent} from './my/my-genres/my-genres.component';
import {MyBooksComponent} from './my/my-books/my-books.component';
import {MyAuthorsComponent} from './my/my-authors/my-authors.component';
import {MyProfileComponent} from './my/my-profile/my-profile.component';
import {PaginatorComponent} from './paginator/paginator.component';
import {MyBookNamesComponent} from './my/my-book-names/my-book-names.component';
import {BooksForVisitComponent} from './books-for-visit/books-for-visit.component';

registerLocaleData(ruLocale, 'ru');
const INTERCEPTOR_PROVIDER: Provider = {
    provide: HTTP_INTERCEPTORS,
    multi: true,
    useClass: AuthInterceptor
};

@NgModule({
    declarations: [
        AppComponent,
        MainLayoutComponent,


        AuthorsComponent,

        BooksComponent,


        SearchBookPipe,
        SearchUserPipe,
        GenresComponent,

        VisitComponent,
        LoginPageComponent,
        UsersListComponent,

        SubscriptionComponent,
        MyGenresComponent,
        MyBooksComponent,
        MyAuthorsComponent,
        MyProfileComponent,
        PaginatorComponent,
        MyBookNamesComponent,
        BooksForVisitComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [INTERCEPTOR_PROVIDER],
    bootstrap: [AppComponent]
})
export class AppModule {
}
