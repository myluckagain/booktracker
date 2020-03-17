import {Pipe, PipeTransform} from '@angular/core';
import {Book} from '../types/interfaces';

@Pipe({
    name: 'searchBooks'
})
export class SearchBookPipe implements PipeTransform {
    transform(books: Book[], searchStr): Book[] {
        if (!searchStr.trim()) {
            return books;
        } else {
            return books.filter(b => b.name.toLowerCase().includes(searchStr.toLowerCase()));
        }

    }

}
