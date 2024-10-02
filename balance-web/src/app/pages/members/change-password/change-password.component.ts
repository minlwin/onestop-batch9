import { Component } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule],
  templateUrl: './change-password.component.html',
  styles: ``
})
export class ChangePasswordComponent {

  form:FormGroup

  constructor(builder:FormBuilder) {
    this.form = builder.group({
      oldPass : ['', [Validators.required, Validators.minLength(6)]],
      newPass : ['', [Validators.required, Validators.minLength(6)]],
    })
  }
}
