import {Component, OnInit} from '@angular/core';
import {MySubscriptionsService} from '../../services/my-subscriptions.service';

@Component({
  selector: 'app-my-book-names',
  templateUrl: './my-book-names.component.html',
  styleUrls: ['./my-book-names.component.scss']
})
export class MyBookNamesComponent implements OnInit {
  bookNames: string[];
  name = '';

  constructor(private subscriptionsService: MySubscriptionsService) {
  }

  ngOnInit() {
    this.subscriptionsService.getMyBookNames().subscribe(res => {
      this.bookNames = res;
    });

  }

  create() {
    if (!this.name.trim()) {
      return;
    }
    if (this.bookNames.includes(this.name.trim())) {
      return;
    }
    const bookNames: string[] = this.bookNames;
    bookNames.push(this.name.trim());
    this.updateMyBookNames(bookNames);
  }

  updateMyBookNames(bookNames: string[]) {
    this.subscriptionsService.updateMyBookNames(bookNames).subscribe(
        // tslint:disable-next-line:no-shadowed-variable
        res => {
          this.name = '';
          this.bookNames = res;
        }
    );
  }

  remove(bookName: string) {
    const bookNames: string[] = this.bookNames.filter(g => g !== bookName);
    this.subscriptionsService.updateMyBookNames(bookNames).subscribe(
        res => {
          this.bookNames = res;
        });
  }

}
