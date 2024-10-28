import { AfterViewInit, Component, EventEmitter, Output, signal } from '@angular/core';
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
  update = signal<boolean>(false)

  dialog:any

  @Output()
  onSave = new EventEmitter

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
    this.update.set(data != undefined)
  }

  hide() {
    this.dialog?.hide()
  }

  ngAfterViewInit(): void {
    this.dialog = new bootstrap.Modal('#ledgerEdit')
  }

  save() {
    if(this.form.valid) {
      this.onSave.emit(this.form.value)
    }
  }
}
