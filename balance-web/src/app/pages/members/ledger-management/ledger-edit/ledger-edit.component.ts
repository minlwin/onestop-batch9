import { AfterViewInit, Component, effect, EventEmitter, Output, signal } from '@angular/core';
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
      accountName: ['', Validators.required]
    })

    effect(() => {
      if(this.update()) {
        this.form.get("type")?.disable()
      } else {
        this.form.get("type")?.enable()
      }
    })
  }

  show(data:any) {
    if(data) {
      this.form.patchValue(data)
      this.update.set(true)
    } else {
      this.form.patchValue({
        type: '',
        accountName: ''
      })
      this.update.set(false)
    }
    this.dialog?.show()
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
