import {Component, OnInit} from '@angular/core';
import {MySubscriptionsService} from '../../services/my-subscriptions.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MyProfileDto} from '../../types/interfaces';

@Component({
    selector: 'app-my-profile',
    templateUrl: './my-profile.component.html',
    styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {
    fileToUpload: File = null;
    form: FormGroup;
    email = '';
    submitted = false;
    loaded = false;

    constructor(
        private myService: MySubscriptionsService,
    ) {
    }

    ngOnInit() {
        this.myService.getMyProfile().subscribe(
            profile => {
                this.form = new FormGroup({
                        email: new FormControl(profile.email, [Validators.required, Validators.email]),
                        password: new FormControl('', [Validators.minLength(6)]),
                        confirmPassword: new FormControl('', [Validators.minLength(6)])
                    },
                    [this.checkPasswords]);
                this.loaded = true;
            }
        );
    }

    update() {
        const profile: MyProfileDto = {};
        if (this.form.value.email.trim()) {
            profile.email = this.form.value.email.trim();
        }
        if (this.form.value.password.trim()) {
            profile.password = this.form.value.password.trim();
        }
        if (!profile.email.trim() && !profile.password.trim()) {
            return;
        }
        this.submitted = true;
        this.myService.updateMyProfile(profile).subscribe(
            // tslint:disable-next-line:no-shadowed-variable
            profile => {
                console.log(profile);
                this.submitted = false;
            }
        );
    }

    checkPasswords(group: FormGroup) { // here we have the 'passwords' group
        const pass = group.get('password').value;
        const confirmPass = group.get('confirmPassword').value;

        return pass === confirmPass ? null : {notSame: true};
    }

    /*   handleFileInput(files: FileList) {
           this.fileToUpload = files.item(0);
       }

       uploadFileToActivity() {
           this.myService.postFile(this.fileToUpload).subscribe(data => {
               // do something, if upload success
           }, error => {
               console.log(error);
           });
       }*/
}
