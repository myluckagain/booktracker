import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../types/interfaces';

@Pipe({
    name: 'searchUsers'
})
export class SearchUserPipe implements PipeTransform {
    transform(users: User[], searchStr): User[] {
        if (!searchStr.trim()) {
            return users;
        } else {
            return users.filter(b => b.name.toLowerCase().includes(searchStr.toLowerCase()));
        }

    }

}
