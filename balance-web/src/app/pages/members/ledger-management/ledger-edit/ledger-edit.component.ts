import { AfterViewInit, Component } from '@angular/core';
import { WidgetsModule } from '../../../../widgets/widgets.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

declare const bootstrap:any

@Component({
  selector: 'app-ledger-edit',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule],
  templateUrl: './ledger-edit.component.html',
  styles: ``
})
export class LedgerEditComponent implements AfterViewInit{

  form:FormGroup

  dialog:any

  constructor(builder:FormBuilder) {
    this.form = builder.group({
      type: ['', Validators.required],
      code: ['', Validators.required],
      accountName: ['', Validators.required]
    })
  }

  show(data:any) {
    if(data) {
      this.form.patchValue(data)
    } else {
      this.form.patchValue({
        type: '',
        code: '',
        accountName: ''
      })
    }
    this.dialog?.show()
  }

  hide() {
    this.dialog?.hide()
  }

  ngAfterViewInit(): void {
    this.dialog = new bootstrap.Modal('#ledgerEdit')
  }
}
