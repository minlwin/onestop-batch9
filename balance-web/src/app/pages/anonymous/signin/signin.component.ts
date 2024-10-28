import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { Router, RouterLink } from '@angular/router';
import { SecurityService } from '../../../services/api/security.service';
import { LoginUserState } from '../../../services/security/login-user.state';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [ReactiveFormsModule, WidgetsModule, RouterLink],
  templateUrl: './signin.component.html',
  styles: ``
})
export class SigninComponent {

  form:FormGroup

  constructor(builder:FormBuilder,
    private service:SecurityService,
    private loginUserState:LoginUserState,
    private router:Router) {
    this.form = builder.group({
      username: ['', [Validators.required, Validators.minLength(6)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  signIn() {
    if(this.form.valid) {
      this.service.signIn(this.form.value).subscribe(result => {
        this.loginUserState.setUser(result)

        if(result.role == "Admin") {
          this.router.navigate(['/admin'])
        } else if(result.role == "Member") {
          this.router.navigate(['/members'])
        }
      })
    }
  }
}
