import { Component, computed, input } from '@angular/core';
import { WidgetsModule } from '../../../../widgets/widgets.module';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-balance-edit',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './balance-edit.component.html',
  styles: ``
})
export class BalanceEditComponent {

  type = input.required<string>()
  id = input<number>()

  icon = computed(() => this.type() == 'Credit' ? 'bi-wallet' : 'bi-cart')
  subTitle = computed(() => `${this.type()} Entry`)
  title = computed(() => this.id() == undefined ? this.subTitle() : `Edit ${this.subTitle()}`)

  form:FormGroup

  constructor(private builder:FormBuilder) {
    this.form = builder.group({
      issueAt: ['', Validators.required],
      ledgerCode: ['', Validators.required],
      particular: '',
      items: builder.array([])
    })
  }

  get items():FormArray {
    return this.form.get('items') as FormArray
  }

  addItem() {
    this.items.push(this.builder.group({
      item: ['', Validators.required],
      unitPrice: ['', Validators.required],
      quantity: ['', Validators.required]
    }))
  }

  getMbClass(index:number) {
    const mbClass = (index < (this.items.controls.length - 1)) ? 'mb-2' : undefined
    return mbClass
  }
}
