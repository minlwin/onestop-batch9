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

    this.addItem()
  }

  get items():FormArray {
    return this.form.get('items') as FormArray
  }

  addItem() {
    this.items.push(this.builder.group({
      item: ['', Validators.required],
      unitPrice: [0, Validators.required],
      quantity: [0, Validators.required]
    }))
  }

  removeItem(index:number) {
    this.items.removeAt(index)

    if(this.items.controls.length == 0) {
      this.addItem()
    }
  }

  getItemTotal(index:number) {
    const itemGroup = this.items.controls[index] as FormGroup
    const unitPrice = itemGroup.get('unitPrice')?.value || 0
    const quanitty = itemGroup.get('quantity')?.value || 0
    return unitPrice * quanitty
  }

  getTotal() {
    let total = 0

    for(const item of this.items.controls) {
      const itemGroup = item as FormGroup
      const unitPrice = itemGroup.get('unitPrice')?.value || 0
      const quanitty = itemGroup.get('quantity')?.value || 0
      total += (unitPrice * quanitty)
    }

    return total
  }
}
