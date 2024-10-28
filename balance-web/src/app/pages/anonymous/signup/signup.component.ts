import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { Router, RouterLink } from '@angular/router';
import { SecurityService } from '../../../services/api/security.service';
import { LoginUserState } from '../../../services/security/login-user.state';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, WidgetsModule, RouterLink],
  templateUrl: './signup.component.html',
  styles: ``
})
export class SignupComponent {

  form:FormGroup

  constructor(builder:FormBuilder,
    private security:SecurityService,
    private loginUser:LoginUserState,
    private router:Router) {
    this.form = builder.group({
      name: ['', Validators.required],
      username: ['', [Validators.required, Validators.minLength(6)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  signUp() {
    if(this.form.valid) {
      this.security.signUp(this.form.value).subscribe(result => {
        this.loginUser.setUser(result)
        this.router.navigate(['/member'])
      })
    }
  }
}
