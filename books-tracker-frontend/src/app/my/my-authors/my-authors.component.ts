import {Component, OnInit} from '@angular/core';
import {MySubscriptionsService} from '../../services/my-subscriptions.service';

@Component({
    selector: 'app-my-authors',
    templateUrl: './my-authors.component.html',
    styleUrls: ['./my-authors.component.scss']
})
export class MyAuthorsComponent implements OnInit {

    authors: string[];
    name = '';

    constructor(private profileService: MySubscriptionsService) {
    }

    ngOnInit() {
        this.profileService.getMyAuthors().subscribe(res => {
            this.authors = res;
        });

    }

    create() {

        if (!this.name.trim()) {
            return;
        }
        if (this.authors.includes(this.name.trim())) {
            return;
        }
        const authors: string[] = this.authors;
        authors.push(this.name.trim());
        this.updateMyAuthors(authors);


    }

    remove(author: string) {
        const authors: string[] = this.authors.filter(a => a !== author);
        console.log('new authors:', this.authors);
        this.updateMyAuthors(authors);
    }

    updateMyAuthors(authors: string[]) {
        this.profileService.updateMyAuthors(authors).subscribe(
            res => {
                this.authors = res;
            });
    }
}
