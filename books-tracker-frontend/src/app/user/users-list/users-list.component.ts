import {Component, OnDestroy, OnInit} from '@angular/core';
import {switchMap} from 'rxjs/operators';
import {User} from '../../types/interfaces';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../services/user.service';

@Component({
    selector: 'app-users-list',
    templateUrl: './users-list.component.html',
    styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit, OnDestroy {

    users: User[];
    gSub: Subscription;
    dSub: Subscription;

    searchStr = '';

    constructor(private userService: UserService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.gSub = this.route.queryParams.pipe(
            switchMap(params => {

                    return this.userService.getAll();
                }
            )
        ).subscribe((users) => {
            this.users = users;
        });
    }

    ngOnDestroy() {
        if (this.gSub) {
            this.gSub.unsubscribe();
        }
        if (this.dSub) {
            this.dSub.unsubscribe();
        }
    }

    roleName(role: string) {
        switch (role) {
            case 'ROLE_ADMIN':
                return 'Администратор';
            case 'ROLE_USER':
                return 'Пользователь';
            default:
                return 'Пользователь';
        }
    }

    remove(id: string) {
        this.userService.remove(id).subscribe(() => {
            this.users = this.users.filter(a => a.id !== id);
        });
    }
}
