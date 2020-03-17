import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../types/interfaces';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
    form: FormGroup;
    submitted = false;

    constructor(public auth: AuthService,
                private router: Router) {
    }

    ngOnInit() {
        this.form = new FormGroup({
            name: new FormControl('', Validators.required),
            password: new FormControl('', [Validators.required, Validators.minLength(6)])
        });
    }

    submit() {
        if (this.form.invalid) {
            return;
        }
        this.submitted = true;
        const user: User = {
            name: this.form.value.name,
            password: this.form.value.password
        };
        console.log(user);
        this.auth.login(user).subscribe(res => {
                this.form.reset();
                this.submitted = false;
                this.router.navigate(['']);
            },
            err => {
            this.submitted = false;
            });
    }
}
