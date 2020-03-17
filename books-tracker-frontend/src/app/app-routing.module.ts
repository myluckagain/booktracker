import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';


import {AuthorsComponent} from './authors/authors.component';
import {MainLayoutComponent} from './main-layout/main-layout.component';
import {BooksComponent} from './books/books.component';

import {GenresComponent} from './genres/genres.component';
import {VisitComponent} from './visit/visit.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {AuthGuard} from './services/auth.guard';
import {UsersListComponent} from './user/users-list/users-list.component';
import {SubscriptionComponent} from './my/subscription/subscription.component';
import {MyGenresComponent} from './my/my-genres/my-genres.component';
import {MyBooksComponent} from './my/my-books/my-books.component';
import {MyAuthorsComponent} from './my/my-authors/my-authors.component';
import {MyProfileComponent} from './my/my-profile/my-profile.component';
import {MyBookNamesComponent} from './my/my-book-names/my-book-names.component';


const routes: Routes = [
    {
        path: '', component: MainLayoutComponent,
        children: [
            {path: '', component: BooksComponent, canActivate: [AuthGuard]},
            {path: 'login', component: LoginPageComponent},
            {
                path: 'my', component: SubscriptionComponent, canActivate: [AuthGuard], children: [
                    {path: 'profile', component: MyProfileComponent},
                    {path: 'bookNames', component: MyBookNamesComponent},
                    {path: 'authors', component: MyAuthorsComponent},
                    {path: 'genres', component: MyGenresComponent},
                    {path: 'books', component: MyBooksComponent}
                ]
            },
            {path: 'authors', component: AuthorsComponent, canActivate: [AuthGuard]},
            {path: 'genres', component: GenresComponent, canActivate: [AuthGuard]},
            {path: 'visits', component: VisitComponent, canActivate: [AuthGuard]},
            {path: 'users', component: UsersListComponent, canActivate: [AuthGuard]},
            {path: 'books', redirectTo: ''},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
