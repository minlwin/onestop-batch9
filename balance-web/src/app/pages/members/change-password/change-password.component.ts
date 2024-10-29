import { Component } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ChangePasswordService } from '../../../services/api/change-password.service';
import { LoginUserState } from '../../../services/security/login-user.state';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule],
  templateUrl: './change-password.component.html',
  styles: ``
})
export class ChangePasswordComponent {

  form:FormGroup

  constructor(builder:FormBuilder,
    private router:Router,
    private loginUser:LoginUserState,
    private service:ChangePasswordService) {
    this.form = builder.group({
      username: loginUser.user()?.loginId,
      oldPass : ['', [Validators.required, Validators.minLength(6)]],
      newPass : ['', [Validators.required, Validators.minLength(6)]],
    })
  }

  changePass() {
    if(this.form.valid) {
      this.service.changePassword(this.form.value).subscribe(result => {
        this.loginUser.setUser(result)
        this.router.navigate(['/members'])
      })
    }
  }
}
