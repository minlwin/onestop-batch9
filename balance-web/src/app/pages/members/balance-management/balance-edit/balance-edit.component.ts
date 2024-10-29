import { Component, computed, effect, input, signal } from '@angular/core';
import { WidgetsModule } from '../../../../widgets/widgets.module';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LedgerEntryService } from '../../../../services/api/ledger-entry.service';
import { Router } from '@angular/router';
import { LedgerManagementService } from '../../../../services/api/ledger-management.service';

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

  ledgerCode = signal<string | undefined>(undefined)
  ledger = signal<any>(undefined)

  form:FormGroup

  constructor(
    private builder:FormBuilder,
    private service:LedgerEntryService,
    ledgerService:LedgerManagementService,
    private router:Router
  ) {
    this.form = builder.group({
      issueAt: ['', Validators.required],
      ledgerCode: ['', Validators.required],
      particular: '',
      items: builder.array([])
    })

    this.addItem()

    effect(() => {

      this.ledger.set(undefined)
      const code = this.ledgerCode()

      if(code) {

        if(!this.type().startsWith(code.charAt(0))) {
          throw {error : ["Invalid ledger code."]}
        }

        ledgerService.findById(code).subscribe(result => {
          this.ledger.set(result)
        })
      }
    }, {allowSignalWrites: true})
  }

  save() {
    if(this.form.valid) {
      this.service.create(this.type(), this.form.value).subscribe(result => {
        this.router.navigate(['/members', 'management', 'details', result.id])
      })
    }
  }

  get items():FormArray {
    return this.form.get('items') as FormArray
  }

  addItem() {
    this.items.push(this.builder.group({
      itemInfo: ['', Validators.required],
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
